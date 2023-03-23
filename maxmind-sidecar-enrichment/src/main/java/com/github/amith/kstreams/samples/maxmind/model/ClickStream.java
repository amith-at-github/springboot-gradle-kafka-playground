package com.github.amith.kstreams.samples.maxmind.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ClickStream {
    @JsonProperty("product_id")
    String productId;
    @JsonProperty("user_id")
    String userId;
    @JsonProperty("view_time")
    Integer viewTime;
    @JsonProperty("page_url")
    String pageUrl;
    String adServerContentId;
    String ip;
    long ts;

}
