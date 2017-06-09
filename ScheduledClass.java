package com.periodicThreads;

import com.MyHook.MyShutDownHook;
import com.MyHook.Stopper;
import com.XmlParser.MyXmlParser;
import com.sharedmemforoutput.SharedMemOut;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by giannis on 11/4/15.
 */
public class ScheduledClass extends TimerTask{
    String job;
    MyXmlParser xmlp;
    Stopper stopp;
    Timer time;

    public ScheduledClass(String job, SharedMemOut shm, Timer time, Stopper stopp){
        this.xmlp=new MyXmlParser(shm);
        this.job=job;
        this.time=time;
        this.stopp=stopp;
    }
    /**
     *Runs repeatitevely each job for periodic threads
     */
     public void run(){
          try {
              if (!this.stopp.isChecker()) {
                  this.time.cancel();
                  this.time.purge();
              } else {
                  String line;
                  Process p;
                  if (this.job.contains("-oX - ")) {
                      p = Runtime.getRuntime().exec("nmap " + this.job);
                  } else {
                      p = Runtime.getRuntime().exec("nmap -oX - " + this.job);
                  }
                  BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                  String outputstring = "";
                  while ((line = input.readLine()) != null) {
                      outputstring += line;
                  }
                  if (outputstring.contains("</nmaprun>")) {
                      this.xmlp.parseXML(outputstring);
                  }
                  input.close();

              }
          }
        catch (Exception e){
            e.printStackTrace();
        }
     }
}
