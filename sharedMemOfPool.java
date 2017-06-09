package com.oneTimeThreads.mySharedMem;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by giannis on 10/25/15.
 */
public class sharedMemOfPool {
   List<String> myStack=new LinkedList<>();
    boolean valueSet=true;
    /**
     * consumer get from shared mem
     */
    public synchronized String get(){

            String curString = "";

                while (this.myStack.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        new RuntimeException("Interupt",e);
                        break;
                    }
                }


            curString = this.myStack.get(0);


            return curString;


    }
    /**
     * producer put in shared mem
     */
    public synchronized void put(String args) {
            if (!args.equals("exit")) {
                this.myStack.add(args);
                notify();
            }
        else
            {
                System.out.println("I will notify");
                notifyAll();
            }

        return;

    }
}
