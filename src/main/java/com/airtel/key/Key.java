package com.airtel.key;

import java.util.*;

/**
 * Created by manisharathore on 31/03/18.
 */
class Key
{
    String key;
    Key(String key)
    {
        this.key = key;
    }

    @Override
    public int hashCode()
    {
        return (int)key.charAt(0);
    }

    @Override
    public boolean equals(Object obj)
    {
        return key.equals((String)obj);
    }
}