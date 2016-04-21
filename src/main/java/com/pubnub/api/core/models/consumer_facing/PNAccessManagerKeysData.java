package com.pubnub.api.core.models.consumer_facing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Getter
public class PNAccessManagerKeysData {

    @JsonProperty("auths")
    Map<String, PNAccessManagerKeyData> auths;

}
