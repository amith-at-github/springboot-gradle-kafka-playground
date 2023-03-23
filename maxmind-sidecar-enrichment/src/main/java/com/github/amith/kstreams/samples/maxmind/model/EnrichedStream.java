package com.github.amith.kstreams.samples.maxmind.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrichedStream {
    private ClickStream clickStream;
    private Customer customer;
    private EnrichedIp enrichedIp;
    private Asset enrichedAsset;


}
