package com.pubnub.api;

class PnThread extends Thread {

    void setPnDaemon(boolean daemon) {
        super.setDaemon(daemon);
    }

    PnThread() {
        super();
    }

    PnThread(Runnable r) {
        super(r);
    }

    PnThread(Runnable r, String name) {
        super(r, name);
    }

}
