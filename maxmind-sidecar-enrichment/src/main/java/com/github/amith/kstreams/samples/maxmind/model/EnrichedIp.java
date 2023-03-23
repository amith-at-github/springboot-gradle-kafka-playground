package com.github.amith.kstreams.samples.maxmind.model;

import lombok.Data;

@Data
public class EnrichedIp {
    private String ipAddress;
    private String device;
    private String city;
    private String fullLocation;
    private Double latitude;
    private Double longitude;
}
