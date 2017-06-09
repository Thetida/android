package com.SenderThreadsClasses;

import com.MyHook.MyShutDownHook;
import com.MyHook.Stopper;
import com.periodicThreads.ScheduledClass;
import com.sharedmemforoutput.SharedMemOut;

import java.util.Timer;

/**
 * Created by giannis on 11/16/15.
 */
public class senderThread implements Runnable{
    Integer periodicwait;
    Integer id;
    SharedMemOut shm;
    Stopper stopp;

    /**
     * We create a thread
     */
    public senderThread(Integer periodicwait, Integer id, SharedMemOut shm, Stopper stopp){
        this.id=id;
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

        SenderThreadSchedule st = new SenderThreadSchedule(this.shm,time,this.stopp); // Instantiate SheduledTask class
        time.schedule(st, 0, this.periodicwait);
    }

}
