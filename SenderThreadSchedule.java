package com.SenderThreadsClasses;

import com.MyHook.MyShutDownHook;
import com.MyHook.Stopper;
import com.XmlParser.MyXmlParser;
import com.sharedmemforoutput.SharedMemOut;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by giannis on 11/16/15.
 */
public class SenderThreadSchedule extends TimerTask {
    SharedMemOut shm;
    Timer time;
    Stopper stopp;
    public SenderThreadSchedule(SharedMemOut shm, Timer time, Stopper stopp){
        this.time=time;
        this.stopp=stopp;
        this.shm=shm;
    }
    /**
     *Calls get method from @see com.sharedmemforoutput.SharedMemOut#get()
     */
    public void run(){
        try {
            if(!this.stopp.isChecker())
            {
                this.time.cancel();
                this.time.purge();

            }
            else {
                this.shm.get();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
