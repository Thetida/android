package com.periodicThreads;

import java.util.Timer;

import com.MyHook.MyShutDownHook;
import com.MyHook.Stopper;
import com.periodicThreads.ScheduledClass;
import com.sharedmemforoutput.SharedMemOut;


public class periodicThread implements Runnable{
    String job;
    Integer periodicwait;
    Integer id;
    SharedMemOut shm;

    Stopper stopp;
    public periodicThread(String job, Integer periodicwait, Integer id, SharedMemOut shm, Stopper stopp){
        this.job=job;
        this.periodicwait=periodicwait;
        this.shm=shm;
        this.stopp=stopp;
    }
    /**
     *Create an instance of Timer Object
     *<br>Create an instance of SheduledTask class</br>
     *Run The schuduled class periodically
     */
    public void run(){
        Timer time = new Timer(); // Instantiate Timer Object

        ScheduledClass st = new ScheduledClass(this.job,this.shm,time,this.stopp); // Instantiate SheduledTask class
        time.schedule(st, 0, this.periodicwait); // Create Repetitively task for every 1 secs
    }

}
