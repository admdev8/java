package com.pubnub.api.subscribe;

public interface PubnubSubscribeApiState1 extends PubnubSubscribeApiState3  {
    PubnubSubscribeApiState3 channelGroup(String channelGroup);
    PubnubSubscribeApiState3 channelGroups(String[] channelGroups);
    //PubnubSubscribeApiState7 filter(String filter);
    PubnubSubscribeApiState8 timeToken(String timetoken);

}
