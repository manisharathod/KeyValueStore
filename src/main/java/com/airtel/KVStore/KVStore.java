package com.airtel.KVStore;

import java.util.*;
/**
 * Created by manisharathore on 31/03/18.
 */
public interface KVStore<K, V> {
    public V get (K key);
    public void put (K key, V value); void delete (K key);
    public void clear ();
    public long size ();
}