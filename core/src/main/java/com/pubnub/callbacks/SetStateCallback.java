package com.pubnub.callbacks;

public abstract class SetStateCallback extends Callback {
    public abstract void status(ClientStateUpdateStatus status);
    
    @Override
    void successCallback(String channel, Object message, Result result) {
        
    }
    
    @Override
    void errorCallback(String channel, PubnubError error, Result result) {
        ClientStateUpdateStatus status = new ClientStateUpdateStatus();
        status = (ClientStateUpdateStatus) fillErrorStatusDetails(status, error, result);
        status.operation = OperationType.SET_STATE;
        status.errorData.channels = new String[]{channel};
        status(status);  
    }
}
