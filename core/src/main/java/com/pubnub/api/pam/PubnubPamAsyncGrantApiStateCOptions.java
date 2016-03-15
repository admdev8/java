package com.pubnub.api.pam;

public interface PubnubPamAsyncGrantApiStateCOptions extends PubnubPamAsyncGrantEnd {
    PubnubPamAsyncGrantApiStateCOptions read(boolean read);
    PubnubPamAsyncGrantApiStateCOptions write(boolean read);
    PubnubPamAsyncGrantApiStateCOptions ttl(int ttl);
    PubnubPamAsyncGrantApiStateCOptions authKey(String authKey);
}
