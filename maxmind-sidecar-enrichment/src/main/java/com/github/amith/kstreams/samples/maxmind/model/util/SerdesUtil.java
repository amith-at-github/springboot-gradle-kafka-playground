package com.github.amith.kstreams.samples.maxmind.model.util;

import com.github.amith.kstreams.samples.maxmind.model.Asset;
import com.github.amith.kstreams.samples.maxmind.model.EnrichedStream;
import com.github.amith.kstreams.samples.maxmind.model.ClickStream;
import com.github.amith.kstreams.samples.maxmind.model.Customer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import java.util.HashMap;
import java.util.Map;


public class SerdesUtil {


    public static Serde<ClickStream> getJsonSerdeForClickStream(){

        Map<String, Object> serdeProps = new HashMap<>();

        final Serializer<ClickStream> mySerializer = new JsonPOJOSerializer<>();
        serdeProps.put("JsonPOJOClass", ClickStream.class);
        mySerializer.configure(serdeProps, false);

        final Deserializer<ClickStream> myDeserializer = new JsonPOJOBasicDeserializer<>();
        serdeProps.put("JsonPOJOClass", ClickStream.class);
        myDeserializer.configure(serdeProps, false);

        return Serdes.serdeFrom(mySerializer, myDeserializer);
    }

    public static Serde<Customer> getJsonSerdeForCustomer(){

        Map<String, Object> serdeProps = new HashMap<>();

        final Serializer<Customer> mySerializer = new JsonPOJOSerializer<>();
        serdeProps.put("JsonPOJOClass", Customer.class);
        mySerializer.configure(serdeProps, false);

        final Deserializer<Customer> myDeserializer = new JsonPOJOBasicDeserializer<>();
        serdeProps.put("JsonPOJOClass", Customer.class);
        myDeserializer.configure(serdeProps, false);

        return Serdes.serdeFrom(mySerializer, myDeserializer);
    }

    public static Serde<EnrichedStream> getJsonSerdeForEnrichedStream(){

        Map<String, Object> serdeProps = new HashMap<>();

        final Serializer<EnrichedStream> mySerializer = new JsonPOJOSerializer<>();
        serdeProps.put("JsonPOJOClass", EnrichedStream.class);
        mySerializer.configure(serdeProps, false);

        final Deserializer<EnrichedStream> myDeserializer = new JsonPOJOBasicDeserializer<>();
        serdeProps.put("JsonPOJOClass", EnrichedStream.class);
        myDeserializer.configure(serdeProps, false);

        return Serdes.serdeFrom(mySerializer, myDeserializer);
    }

    public static Serde<Asset> getJsonSerdeForAsset(){

        Map<String, Object> serdeProps = new HashMap<>();

        final Serializer<Asset> mySerializer = new JsonPOJOSerializer<>();
        serdeProps.put("JsonPOJOClass", Asset.class);
        mySerializer.configure(serdeProps, false);

        final Deserializer<Asset> myDeserializer = new JsonPOJOBasicDeserializer<>();
        serdeProps.put("JsonPOJOClass", Asset.class);
        myDeserializer.configure(serdeProps, false);

        return Serdes.serdeFrom(mySerializer, myDeserializer);
    }
}
