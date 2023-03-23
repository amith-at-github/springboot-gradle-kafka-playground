package com.github.amith.kstreams.samples.maxmind.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Customer {
    String id;
    @JsonProperty("first_name")
    String firstName;
    @JsonProperty("last_name")
    String lastName;
    String email;
    String phone;
    @JsonProperty("street_address")
    String streetAddress;
    String state;
    @JsonProperty("zip_code")
    String zipCode;
    String country;
    @JsonProperty("country_code")
    String countryCode;
}
