package com.sharedmemforoutput;

import com.XmlParser.ScanInfo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class SharedMemOut {
    BlockingQueue <ScanInfo> myStack=new LinkedBlockingQueue<ScanInfo>();

    /**
     *sender threads gets all elements currently in the list and prints them
     */
    public synchronized void get(){
        try {
            while (!myStack.isEmpty()) {
                ScanInfo newinfo = this.myStack.take();
                System.out.println("--------------------------------");
                newinfo.printMyInfo();
            }
            return;
        }catch (InterruptedException e){
            new RuntimeException("Inter ",e);
        }
    }
    /**
     *periodic and one_time jobs puts the elements in the list that sender_thread contacts
     */
    public synchronized void put(ScanInfo scan){
        this.myStack.add(scan);
    }
}
