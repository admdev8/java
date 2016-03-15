package com.pubnub.api.subscribe;

public interface PubnubSubscribeApiState {
    PubnubSubscribeApiState1 channel(String channels);
    PubnubSubscribeApiState1 channels(String[] channels);
    PubnubSubscribeApiState2 channelGroup(String channelGroup);
    PubnubSubscribeApiState2 channelGroups(String[] channelGroups);
    
}
