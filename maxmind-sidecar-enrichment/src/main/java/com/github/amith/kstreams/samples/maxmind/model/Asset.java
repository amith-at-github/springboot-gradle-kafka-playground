package com.github.amith.kstreams.samples.maxmind.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Asset {
     private String importTime;
     private String type;
     private String adServerContentId;
     private String programmeUuid;
     private String contentTitle;
     private String contentRating;
     private String seriesUuid;
     private String seasonNumber;
     private String seasonUuid;
     private String episodeNumber;
     private String episodeName;
     private String tmsId;
     private String genreId;
     private String genre;
     private String subGenre;
     private String nielsenGenre;
     private String language;
     private String duration;
     private int rating;
 }