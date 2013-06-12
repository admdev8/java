package com.pubnub.api;

/**
 * Abstract class to be subclassed by objects being passed as callbacks to
 * Pubnub APIs Default implementation for all methods is blank
 *
 * @author Pubnub
 *
 */
public abstract class Callback {

    /**
     * This callback will be invoked when a message is received on the channel
     *
     * @param channel
     *            Channel Name
     * @param message
     *            Message
     *
     */
    public abstract void successCallback(Object message);

    /**
     * This callback will be invoked when an error occurs
     *
     * @param channel
     *            Channel Name
     * @param message
     *            Message
     */
    public abstract void errorCallback(PubnubError error);

    /**
     * This callback will be invoked on getting connected to a channel
     *
     * @param channel
     *            Channel Name
     */
    public void connectCallback(Object message) {
    }

    /**
     * This callback is invoked on getting reconnected to a channel after
     * getting disconnected
     *
     * @param channel
     *            Channel Name
     */
    public void reconnectCallback(Object message) {
    }

    /**
     * This callback is invoked on getting disconnected from a channel
     *
     * @param channel
     *            Channel Name
     */
    public void disconnectCallback(Object message) {
    }

}
