package com.github.amith.kstreams.samples.maxmind.service;

import com.github.amith.kstreams.samples.maxmind.model.ClickStream;
import com.github.amith.kstreams.samples.maxmind.model.EnrichedIp;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

import static java.util.Objects.nonNull;

@Service
public class GeoIpEnrichmentService {
    private static final String UNKNOWN = "UNKNOWN";
    private final DatabaseReader databaseReader;

    public GeoIpEnrichmentService(DatabaseReader databaseReader) {
        this.databaseReader = databaseReader;
    }

    public EnrichedIp enrichByIp(ClickStream clickStream){

        EnrichedIp enrichedIp = new EnrichedIp();
        String location;
        InetAddress ipAddress=null;
        CityResponse cityResponse=null;
        try {
            ipAddress = InetAddress.getByName(clickStream.getIp());
            cityResponse = databaseReader.city(ipAddress);
        }catch(Exception e){
            System.out.println("loggin exceptoin and moving" + e);
        }

        if (nonNull(cityResponse) && nonNull(cityResponse.getCity())) {
            String continent = (cityResponse.getContinent() != null) ? cityResponse.getContinent().getName() : "";
            String country = (cityResponse.getCountry() != null) ? cityResponse.getCountry().getName() : "";

            location = String.format("%s, %s, %s", continent, country, cityResponse.getCity().getName());
            enrichedIp.setCity(cityResponse.getCity().getName());
            enrichedIp.setFullLocation(location);
            enrichedIp.setLatitude((cityResponse.getLocation() != null) ? cityResponse.getLocation().getLatitude() : 0);
            enrichedIp.setLongitude((cityResponse.getLocation() != null) ? cityResponse.getLocation().getLongitude() : 0);
            enrichedIp.setDevice(clickStream.getIp());
            enrichedIp.setIpAddress(clickStream.getIp());
        }
        return enrichedIp;
    }
}
