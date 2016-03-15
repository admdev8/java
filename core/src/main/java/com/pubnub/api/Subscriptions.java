package com.pubnub.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author PubnubCore
 *
 */
class Subscriptions {
    private Hashtable items;

    JSONObject state;

    void runConnectOnNewThread(final Callback callback, final String name,
                               final JSONArray jsa , final SubscribeResult result) {
        
        if (callback == null) return;
        
        Runnable r = new Runnable() {
            public void run() {
                callback.connectCallback(name, jsa, result);
            }
        };
        PnThread thread = new PnThread(r);
        thread.setPnDaemon(Pubnub.daemonThreads);
        thread.start();
    }

    void runReconnectOnNewThread(final Callback callback, final String name,
                                 final JSONArray jsa, final SubscribeResult result) {
        
        if (callback == null) return;
        
        Runnable r = new Runnable() {
            public void run() {
                callback.disconnectCallback(name, jsa, result);
            }
        };
        PnThread thread = new PnThread(r);
        thread.setPnDaemon(Pubnub.daemonThreads);
        thread.start();
    }

    void runDisconnectOnNewThread(final Callback callback, final String name,
                                  final JSONArray jsa, final SubscribeResult result) {
        
        if (callback == null) return;
        
        Runnable r = new Runnable() {
            public void run() {
                callback.reconnectCallback(name, jsa, result);
            }
        };
        PnThread thread = new PnThread(r);
        thread.setPnDaemon(Pubnub.daemonThreads);
        thread.start();
    }

    public Subscriptions() {
        items = new Hashtable();
        state = new JSONObject();
    }

    public void addItem(SubscriptionItem item) {
        items.put(item.name, item);
    }

    public void removeItem(String name) {
        items.remove(name);
    }

    public void removeAllItems() {
        items.clear();
    }

    public SubscriptionItem getFirstItem() {
        SubscriptionItem ch = null;
        synchronized (items) {
            if (items.size() > 0) {
                ch = (SubscriptionItem) items.elements().nextElement();
            }
        }
        return ch;
    }

    public SubscriptionItem getItem(String name) {
        return (SubscriptionItem) items.get(name);
    }

    public String[] getItemNames() {
        return getItemNames(null);
    }

    public String[] getItemNames(String filter) {
        return PubnubUtil.hashtableKeysToArray(items, filter);
    }

    public String getItemStringNoPresence() {
        return PubnubUtil.hashTableKeysToDelimitedString(items, ",", Pubnub.PRESENCE_SUFFIX);
    }

    // TODO: review & remove
    public String getItemStringSorted() {
        return PubnubUtil.hashTableKeysToSortedSuffixString(items, ",", Pubnub.PRESENCE_SUFFIX);
    }

    public String getItemString() {
        return getItemString(null);
    }

    public String getItemString(String filter) {
        return PubnubUtil.hashTableKeysToDelimitedString(items, ",", filter);
    }

    public void invokeConnectCallbackOnItems(Object message, SubscribeResult result) {
        invokeConnectCallbackOnItems(getItemNames(), message, result);
    }

    public void invokeDisconnectCallbackOnItems(Object message, SubscribeResult result) {
        invokeDisconnectCallbackOnItems(getItemNames(), message, result);
    }

    public void invokeErrorCallbackOnItems(PubnubError error, Result result) {
        /*
         * Iterate over all the items and call error callback for items
         */
        synchronized (items) {
            Enumeration itemsElements = items.elements();
            while (itemsElements.hasMoreElements()) {
                SubscriptionItem _item = (SubscriptionItem) itemsElements.nextElement();
                _item.error = true;
                _item.callback.errorCallback(_item.name, error, result);
            }
        }
    }

    public void invokeConnectCallbackOnItems(String[] items, Object message, SubscribeResult result) {
        synchronized (items) {
            for (int i = 0; i < items.length; i++) {
                SubscriptionItem _item = (SubscriptionItem) this.items.get(items[i]);
                if (_item != null) {
                    if (_item.connected == false) {
                        _item.connected = true;
                        if (_item.subscribed == false) {
                            runConnectOnNewThread(_item.callback, _item.name,
                                    new JSONArray().put(1).put("Subscribe connected").put(message), result);
                        } else {
                            _item.subscribed = true;
                            runReconnectOnNewThread(_item.callback, _item.name,
                                    new JSONArray().put(1).put("Subscribe reconnected").put(message), result);
                        }
                    }
                }
            }
        }
    }

    public void invokeReconnectCallbackOnItems(Object message, SubscribeResult result) {
        invokeReconnectCallbackOnItems(getItemNames(), message, result);
    }

    public void invokeReconnectCallbackOnItems(String[] items, Object message, SubscribeResult result) {
        synchronized (items) {
            for (int i = 0; i < items.length; i++) {
                SubscriptionItem _item = (SubscriptionItem) this.items.get(items[i]);
                if (_item != null) {
                    _item.connected = true;
                    if (_item.error) {
                        runReconnectOnNewThread(_item.callback, _item.name,
                                new JSONArray().put(1).put("Subscribe reconnected").put(message), result);
                        _item.error = false;
                    }
                }
            }
        }
    }

    public void invokeDisconnectCallbackOnItems(String[] items, Object message, SubscribeResult result) {
        synchronized (items) {
            for (int i = 0; i < items.length; i++) {
                SubscriptionItem _item = (SubscriptionItem) this.items.get(items[i]);
                if (_item != null) {
                    if (_item.connected == true) {
                        _item.connected = false;
                        runDisconnectOnNewThread(_item.callback, _item.name,
                                new JSONArray().put(1).put("Subscribe unable to connect").put(message), result);
                    }
                }
            }
        }
    }
}
