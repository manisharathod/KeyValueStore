package com.airtel.KVStore;

import java.io.FileInputStream;
import java.util.*;
import com.airtel.entry.Entry;
import com.airtel.segment.Segment;

/**
 * Created by manisharathore on 31/03/18.
 */
public class KVStoreMap implements KVStore {

    int cap;
    transient Entry[] table;
    FileInputStream in;
    float loadFactor;
    int threshold;
    Segment[] segments;

    public KVStoreMap(int initialCapacity, float loadFactor) {
        int cap = initialCapacity;
        this.loadFactor = loadFactor;

        this.threshold = ((int)(cap * this.loadFactor / 32.0F) + 1);
        segments = new Segment[threshold];

        this.table = newTable(cap);
    }

    public Object get (Object key) {

        Object value = null;
        int hash = hash(key);
        int index = hash & table.length - 1;
        Entry first = table[index];
        for (Entry e = first; e != null; e = e.next) {
            if ((e.hash == hash) && (key.equals(e.key))) {
                value = (Object) e.value;
                if (value == null) {
                    break;
                }
                return value;
            }
        }
       return value;
    }

    public void put(Object key, Object value) {
        int hash = hash(key);
        Segment seg = getSegment(hash);

        synchronized (seg) {

            int index = hash & table.length - 1;
            Entry first = table[index];
            for (Entry e = first; e != null; e = e.next) {
                if ((e.hash == hash) && (key.equals(e.key))) {
                    Object oldValue = (Object) e.value;
                    e.value = value;
                }
            }
            Entry newEntry = new Entry(hash, key, value, first);
            table[index] = newEntry;
        }
    }

    public Segment getSegment(int hash) {

        Segment seg = segments[(hash & 0x1F)];
        if(null == seg) {
            seg = new Segment();
        }
        return seg;
    }

    public int hash(Object x) {
        int h = x.hashCode();
        return (h << 7) - h + (h >>> 9) + (h >>> 17);
    }

    public void delete(Object key) {
        int hash = hash(key);
        Segment seg = segments[(hash & 0x1F)]; //hash we have calculated for key
        synchronized (seg) {
            Entry[] tab = this.table; //table is Entry[] table
            int index = hash & tab.length - 1; //calculating index with help of hash
            Entry first = tab[index]; //Getting the Entry Object
            Entry e = first;
            while(true) {
                if ((e.hash == hash) && (key.equals(e.key))) {
                    break;
                }
                e = e.next;
            }
            Object oldValue = (Object) e.value;
            Entry head = e.next;
            for (Entry p = first; p != e; p = p.next) {
                head = new Entry(p.hash, p.key, p.value, head);
            }
            table[index] = head;
            seg.count -= 1;
        }
    }

    public void clear() {

    }

    public long size() {
        long c = 0L;
        for (int i = 0; i < this.segments.length; i++) {
            c += this.segments[i].getCount();         //here c is an integer initialized with zero
        }
        return c;
    }

    public Entry[] newTable(int capacity) {

        return new Entry[capacity];
    }
}
