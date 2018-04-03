package com.airtel.segment;

import java.util.*;
/**
 * Created by manisharathore on 31/03/18.
 */
public final class Segment {
    public int count;

    public synchronized int getCount() {
        return this.count;
    }
}