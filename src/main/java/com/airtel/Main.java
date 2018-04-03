package com.airtel;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import com.airtel.KVStore.KVStoreMap;
import com.airtel.KVStore.KVStore;

/**
 * Created by manisharathore on 31/03/18.
 */
public class Main<K,V> {

    static KVStoreMap kv;

    static class WorkerThread implements Runnable {

        private String command;

        public WorkerThread(String s){
            this.command=s;
        }

        public void run() {
            kv.put(new String("test"), command);
        }
    }

    public static void main(String[] args) {

        kv = new KVStoreMap(32, 32);
        ExecutorService e = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 5000; i++){
            Runnable worker = new WorkerThread(" " + i);
            e.submit(worker);
        }
        System.out.print(kv.get("test"));
    }
}
