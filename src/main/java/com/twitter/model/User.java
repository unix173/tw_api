package com.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ivsi on 8/21/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @JsonProperty("screen_name")
    private String screenName;

    @JsonProperty("utc_offset")
    private int utcOffset;

    @JsonProperty("name")
    private String name;

    @JsonProperty("profile_image_url")
    private String profileImageUrl;

    @JsonProperty("profile_image_url_https")
    private String profileImageUrlHttps;


}
