package com.oneTimeThreads.prod;

import com.MyHook.MyShutDownHook;
import com.MyHook.Stopper;
import com.oneTimeThreads.mySharedMem.sharedMemOfPool;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by giannis on 10/20/15.
 */

public class producer implements Runnable{
    //h lista pou stelnei h main ston producer
    final BlockingQueue<String> myqueue;
    sharedMemOfPool shared;
    Integer id;
    Stopper stopp;

    public producer(sharedMemOfPool shared, Integer id, Stopper stopp, BlockingQueue<String> myqueue) {
        this.shared=shared;
        this.id=id;
        this.stopp=stopp;
        this.myqueue=myqueue;

    }
    /**
     *Gets from the list shared with main and put in list shared with consumers
     */
    public void run(){
        try {
            while (this.stopp.isChecker() == true) {
                this.shared.put(this.myqueue.take());
            }
            System.out.println("feygw");
            this.shared.put("exit");
            System.out.println("den feygw");
            return;
        }catch (InterruptedException e){
            new RuntimeException("Interrupted",e);
        }
    }
    /**
     *Main uses this to put items in producer
     */
    public void addtomyQueue(String args){
        this.myqueue.add(args);
    }
}

