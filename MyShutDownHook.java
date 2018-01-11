package com.MyHook;

import StartingMain.StopMain;
import com.oneTimeThreads.prod.producer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;


public class MyShutDownHook extends Thread{
    boolean checker;
    List consumerthreads;
    List periodicthreads;
    Thread prod;
    Thread send;
    Thread mainthr;
    StopMain stopmain;
    Stopper stopp;
    public MyShutDownHook(List consumerthreads, List periodicthreads, Thread mainthr, StopMain stopmain,Stopper stopp,Thread send,Thread prod)
    {
        this.stopp=stopp;
        this.stopmain=stopmain;
        this.consumerthreads=consumerthreads;
        this.periodicthreads=periodicthreads;
        this.mainthr=mainthr;
        this.checker=true;
        this.send=send;
        this.prod=prod;
    }

    /**
     *Run Method of Shutdown Hook-joins all threads
     */
    public void run(){
        try {
        this.stopp.setChecker(false);
        this.stopmain.setStopmain(false);

            System.out.println("Waiting for sender exit code");
            this.send.join();
            System.out.println("Waiting for producer exit code");
            this.prod.interrupt();
            this.prod.join();


            Thread ex;
            System.out.println("Waiting for consumer exit code");
            for(int i=0;i<this.consumerthreads.size();i++){
                ex =(Thread) this.consumerthreads.get(i);
                while (ex.getState().toString().equals("WAITING"))
                {
                    ex.interrupt();
                }
               ex.join();
            }
            System.out.println("Waiting for periodic exit code");
            for(int i=0;i<this.periodicthreads.size();i++){
                ex =(Thread) this.periodicthreads.get(i);
                //ex.interrupt();
                ex.join();
            }


            System.out.println("Waiting for main thread exit code");
            this.mainthr.join();
            System.out.println("Exiting");
        }catch (InterruptedException e)
        {

            e.printStackTrace();
        }

    }

}
