package com.pubnub.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author PubnubCore
 */
class PubnubUtilCore {
    
    
    static String[] getCopyOfStringArray(String[] a) {
        if (a == null)
            return a;
        String[] b = new String[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = new String(a[i]);
        }
        return b;
    }
    
    static String[] concatStringArrays(String[] a, String[] b) {
        if ( a == null && b == null)
            return null;
        if ( b == null) 
            return a;
        if (a == null)
            return b;
        String[] c = new String[a.length + b.length];

        for (int i = 0; i < a.length; i++) {
            c[i] = new String(a[i]);
        }
        for (int i = 0; i < b.length; i++) {
            c[i + a.length] = new String(b[i]);
        }
        return c;
    }
    

    static void addToHash(Hashtable h, String name, Object object) {
        if (object != null) {
            h.put(name, object);
        }
    }

    /**
     * Takes source and delimiter string as inputs and returns splitted string
     * in form of tokens in String array
     *
     * @param source
     *            , input String
     * @param delimiter
     *            , delimiter to split on
     * @return String[] , tokens in and array
     */
    public static String[] splitString(String source, String delimiter) {

        int delimiterCount = 0;
        int index = 0;
        String tmpStr = source;

        String[] splittedList;

        while ((index = tmpStr.indexOf(delimiter)) != -1) {

            tmpStr = tmpStr.substring(index + delimiter.length());
            delimiterCount++;
        }

        splittedList = new String[delimiterCount + 1];

        int counter = 0;
        tmpStr = source;

        do {
            int nextIndex = tmpStr.indexOf(delimiter, index + 1);

            if (nextIndex != -1) {
                splittedList[counter++] = tmpStr.substring(index + delimiter.length(), nextIndex);
                tmpStr = tmpStr.substring(nextIndex);

            } else {
                splittedList[counter++] = tmpStr.substring(index + delimiter.length());
                tmpStr = tmpStr.substring(index + 1);
            }
        } while ((index = tmpStr.indexOf(delimiter)) != -1);

        return splittedList;
    }

    /**
     * Takes String[] of tokens, and String delimiter as input and returns
     * joined String
     *
     * @param sourceArray
     *            , input tokens in String array
     * @param delimiter
     *            , delimiter to join on
     * @return String , string of tokens joined by delimiter
     */
    public static String joinString(String[] sourceArray, String delimiter) {
        if (sourceArray == null || delimiter == null || sourceArray.length <= 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < sourceArray.length - 1; i++) {
            sb.append(sourceArray[i]).append(delimiter);
        }
        sb.append(sourceArray[sourceArray.length - 1]);

        return sb.toString();
    }

    /**
     * Returns string keys in a hashtable as array of string
     *
     * @param ht
     *            , Hashtable
     * @return , string array with hash keys string
     */
    public static synchronized String[] hashtableKeysToArray(Hashtable ht) {
        return hashtableKeysToArray(ht, null);
    }

    public static synchronized String[] hashtableKeysToArray(Hashtable ht, String exclude) {
        Vector v = new Vector();
        String[] sa = null;
        int count = 0;

        Enumeration e = ht.keys();
        while (e.hasMoreElements()) {
            String s = (String) e.nextElement();

            if (exclude != null && s.indexOf(exclude) != -1) {
                continue;
            }

            v.addElement(s);
            count++;
        }

        sa = new String[count];
        v.copyInto(sa);
        return sa;

    }

    /**
     * Returns string keys in a hashtable as delimited string
     *
     * @param ht
     *            , Hashtable
     * @param delimiter
     *            , String
     * @param exclude
     *            , exclude channel if present as substring
     * @return , string array with hash keys string
     */
    public static synchronized String hashTableKeysToDelimitedString(Hashtable ht, String delimiter, String exclude) {

        StringBuffer sb = new StringBuffer();
        boolean first = true;
        Enumeration e = ht.keys();

        while (e.hasMoreElements()) {

            String s = (String) e.nextElement();

            if (exclude != null) {
                if (s.indexOf(exclude) != -1) {
                    continue;
                }
            }
            if (first) {
                sb.append(s);
                first = false;
            } else {
                sb.append(delimiter).append(s);
            }
        }
        return sb.toString();
    }

    public static synchronized String hashTableKeysToSortedSuffixString(Hashtable ht, String delimiter,
            String lastSuffix) {

        StringBuffer sb = new StringBuffer();
        StringBuffer sbPresence = new StringBuffer();
        boolean first = true;
        boolean firstPresence = true;
        Enumeration e = ht.keys();

        while (e.hasMoreElements()) {

            String s = (String) e.nextElement();

            if (s.endsWith(lastSuffix)) {
                if (firstPresence) {
                    sbPresence.append(s);
                    firstPresence = false;
                } else {
                    sbPresence.append(delimiter).append(s);
                }
            } else {
                if (first) {
                    sb.append(s);
                    first = false;
                } else {
                    sb.append(delimiter).append(s);
                }
            }
        }

        if (sb.length() > 0 && sbPresence.length() > 0) {
            return sb.toString() + delimiter + sbPresence.toString();
        } else if (sb.length() > 0 && sbPresence.length() == 0) {
            return sb.toString();
        } else if (sb.length() == 0 && sbPresence.length() > 0) {
            return sbPresence.toString();
        } else {
            return "";
        }
    }

    /**
     * Returns string keys in a hashtable as delimited string
     *
     * @param ht
     *            , Hashtable
     * @param delimiter
     *            , String
     * @return , string array with hash keys string
     */
    public static String hashTableKeysToDelimitedString(Hashtable ht, String delimiter) {
        return hashTableKeysToDelimitedString(ht, delimiter, null);
    }

    static Hashtable hashtableClone(Hashtable ht) {
        if (ht == null)
            return null;

        Hashtable htresp = new Hashtable();
        Enumeration e = ht.keys();

        while (e.hasMoreElements()) {
            Object element = e.nextElement();
            htresp.put(element, ht.get(element));
        }
        return htresp;
    }

    static Hashtable hashtableClone(Hashtable ht1, Hashtable ht2) {
        if (ht1 == null && ht2 == null)
            return null;

        Hashtable htresp = new Hashtable();

        if (ht1 != null) {
            Enumeration e = ht1.keys();
            while (e.hasMoreElements()) {
                Object element = e.nextElement();
                htresp.put(element, ht1.get(element));
            }
        }
        if (ht2 != null) {
            Enumeration e = ht2.keys();
            while (e.hasMoreElements()) {
                Object element = e.nextElement();
                htresp.put(element, ht2.get(element));
            }
        }
        return htresp;
    }

    static Hashtable hashtableMerge(Hashtable dst, Hashtable src) {
        if (dst == null)
            return src;
        if (src == null)
            return dst;

        Enumeration e = src.keys();

        while (e.hasMoreElements()) {
            Object element = e.nextElement();
            dst.put(element, src.get(element));
        }
        return dst;
    }

    /**
     * Parse Json, change json string to string
     *
     * @param obj
     *            JSON data in string format
     *
     * @return JSONArray or JSONObject or String
     */
    static Object parseJSON(Object obj) {
        if (obj instanceof String) {
            if (((String) obj).endsWith("\"") && ((String) obj).startsWith("\""))
                obj = ((String) obj).substring(1, ((String) obj).length() - 1);
        }
        return obj;
    }

    static boolean isEmptyString(String s) {
        return (s == null || s.length() == 0);
    }
}
