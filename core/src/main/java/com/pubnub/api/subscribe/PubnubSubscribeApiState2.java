package com.pubnub.api.subscribe;

public interface PubnubSubscribeApiState2 extends PubnubSubscribeApiState3 {
    PubnubSubscribeApiState3 channel(String channels);
    PubnubSubscribeApiState3 channels(String[] channels);
    
    //PubnubSubscribeApiState7 filter(String filter);
    PubnubSubscribeApiState8 timeToken(String timetoken);    
    
}
