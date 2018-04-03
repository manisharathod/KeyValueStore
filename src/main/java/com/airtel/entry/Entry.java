package com.airtel.entry;

import java.util.Map;
import java.util.*;

/**
 * Created by manisharathore on 31/03/18.
 */
public class Entry<K,V> {
    public final K key;
    public volatile V value;
    public final int hash;
    public final Entry next;

    public Entry(int hash, K key, V value, Entry next) {
        this.value = value;
        this.hash = hash;
        this.key = key;
        this.next = next;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public Object setValue(V value) {
        return null;
    }

    public int getHash() {
        return hash;
    }

    public Entry getNext() {
        return next;
    }
}