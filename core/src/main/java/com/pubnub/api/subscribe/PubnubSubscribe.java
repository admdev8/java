package com.pubnub.api.subscribe;

import java.util.Hashtable;

public class PubnubSubscribe implements PubnubSubscribeInterface {
    
    
    private Pubnub pubnub;
    private String channel;
    private String[] channels;
    private String channelGroup;
    private String[] channelGroups;
    private String filter;
    private String timetoken;
    PubnubSubscribe pns = this;
    private boolean presence = false;
    private boolean subscribe = true;
    
    
    void _connect() throws PubnubException {
        Hashtable args = new Hashtable();
        String[] presenceChannels = null;
        String[] presenceChannelGroups = null;
    
        if (channel != null && channels == null) {
            channels = new String[]{channel};
            presenceChannels = PubnubUtil.getCopyOfStringArray(channels);
            for (int i = 0; i < presenceChannels.length; i++) {
                presenceChannels[i] = presenceChannels[i] + "-pnpres";
            }
        }
    
        if (channelGroup != null && channelGroups == null) {
            channelGroups = new String[]{channelGroup};
            presenceChannelGroups = PubnubUtil.getCopyOfStringArray(channelGroups);
            for (int i = 0; i < presenceChannelGroups.length; i++) {
                presenceChannelGroups[i] = presenceChannelGroups[i] + "-pnpres";
            }
        }
        



        
        if (presence && !subscribe) {
            PubnubUtil.addToHash(args, "channels", presenceChannels);
            PubnubUtil.addToHash(args, "groups", presenceChannelGroups);
        } else if (presence && subscribe) {
            PubnubUtil.addToHash(args, "channels", PubnubUtil.concatStringArrays(channels,presenceChannels));
            PubnubUtil.addToHash(args, "groups", PubnubUtil.concatStringArrays(channelGroups,presenceChannelGroups));
        } else if (subscribe) {
            PubnubUtil.addToHash(args, "channels", channels);
            PubnubUtil.addToHash(args, "groups", channelGroups);
        }
        
    

        PubnubUtil.addToHash(args, "timetoken", timetoken);
        //PubnubUtil.addToHash(args, "filter", filter);
    
        if (pubnub != null && subscribe)
            pubnub.subscribe(args);
        else 
            throw new PubnubException("Pubnub is Null");
        
    }
    
    
    PubnubSubscribe(Pubnub pubnub) {
        this.pubnub = pubnub;
    }
    
    PubnubSubscribeApiState1 i1 = new PubnubSubscribeApiState1(){
        /*
        @Override
        public PubnubSubscribeApiState7 filter(String filter) {
            pns.filter = filter;
            return i7;
        }
        */

        @Override
        public PubnubSubscribeApiState8 timeToken(String timetoken) {
            pns.timetoken = timetoken;
            return i8;
        }

        @Override
        public void connect() throws PubnubException {
            pns._connect();
        }

        @Override
        public PubnubSubscribeApiState3 channelGroup(String channelGroup) {
            pns.channelGroup = channelGroup;
            return i3;
        }

        @Override
        public PubnubSubscribeApiState3 channelGroups(String[] channelGroups) {
            pns.channelGroups = channelGroups;
            return i3;
        }
        
    };
    
    PubnubSubscribeApiState2 i2 = new PubnubSubscribeApiState2(){
        /*
        @Override
        public PubnubSubscribeApiState7 filter(String filter) {
            pns.filter = filter;
            return i7;
        }
        */

        @Override
        public PubnubSubscribeApiState8 timeToken(String timetoken) {
            pns.timetoken = timetoken;
            return i8;
        }

        @Override
        public void connect() throws PubnubException {
            pns._connect();
        }

        @Override
        public PubnubSubscribeApiState3 channel(String channel) {
            pns.channel = channel;
            return i3;
        }

        @Override
        public PubnubSubscribeApiState3 channels(String[] channels) {
            pns.channels = channels;
            return i3;
        }
        
    };
    
    PubnubSubscribeApiState3 i3 = new PubnubSubscribeApiState3() {

        @Override
        public void connect() throws PubnubException {
            pns._connect();
        }
        /*
        @Override
        public PubnubSubscribeApiState7 filter(String filter) {
            pns.filter = filter;
            return i7;
        }
        */

        @Override
        public PubnubSubscribeApiState8 timeToken(String timetoken) {
            pns.timetoken = timetoken;
            return i8;
        }
        
    };
    
    PubnubSubscribeApiState7 i7 = new PubnubSubscribeApiState7() {

        @Override
        public void connect() throws PubnubException {
            pns._connect();
        }

        @Override
        public PubnubSubscribeEnd timeToken(String timetoken) {
            pns.timetoken = timetoken;
            return pubnubSubscribeEnd;
        }
        
    };
    
    PubnubSubscribeApiState8 i8 = new PubnubSubscribeApiState8() {

        @Override
        public void connect() throws PubnubException {
            pns._connect();
            
        }

        @Override
        public PubnubSubscribeEnd filter(String filter) {
            pns.filter = filter;
            return pubnubSubscribeEnd;
        }
        
    };
    
    PubnubSubscribeEnd pubnubSubscribeEnd = new PubnubSubscribeEnd(){

        @Override
        public void connect() throws PubnubException {
            pns._connect();
        }
        
    };
    
    PubnubSubscribeApiState i = new PubnubSubscribeApiState(){

        @Override
        public PubnubSubscribeApiState1 channel(String channel) {
            pns.channel = channel;
            return i1;
        }

        @Override
        public PubnubSubscribeApiState1 channels(String[] channels) {
            pns.channels = channels;
            return i1;
        }

        @Override
        public PubnubSubscribeApiState2 channelGroup(String channelGroup) {
            pns.channelGroup = channelGroup;
            return i2;
        }

        @Override
        public PubnubSubscribeApiState2 channelGroups(String[] channelGroups) {
            pns.channelGroups = channelGroups;
            return i2;
        }
        
    };


    @Override
    public PubnubSubscribeApiState1 channel(String channel) {
        pns.channel = channel;
        return i1;
    }


    @Override
    public PubnubSubscribeApiState1 channels(String[] channels) {
        pns.channels = channels;
        return i1; 
    }


    @Override
    public PubnubSubscribeApiState2 channelGroup(String channelGroup) {
        pns.channelGroup = channelGroup;
        return i2;
    }


    @Override
    public PubnubSubscribeApiState2 channelGroups(String[] channelGroups) {
        pns.channelGroups = channelGroups;
        return i2;
    }


    @Override
    public PubnubSubscribeApiState withPresence() {
        this.presence = true;
        return i;
    }


    @Override
    public PubnubSubscribeApiState onlyPresence() {
        this.subscribe = false;
        return i;
    }


}
