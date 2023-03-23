package com.github.amith.kstreams.samples.mongodbcacheupdater.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class User {

    @JsonProperty("id")
    public long id;
    @JsonProperty("first_name")
    public String firstName;
    @JsonProperty("last_name")
    public String lastName;
    @JsonProperty("email")
    public String email;
    @JsonProperty("gender")
    public String gender;
    @JsonProperty("ip_address")
    public String ipAddress;

}