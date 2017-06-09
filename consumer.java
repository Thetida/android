package com.oneTimeThreads.cons;

import com.MyHook.MyShutDownHook;
import com.MyHook.Stopper;
import com.XmlParser.MyXmlParser;
import com.oneTimeThreads.mySharedMem.sharedMemOfPool;
import com.sharedmemforoutput.SharedMemOut;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class consumer implements Runnable{
    MyXmlParser mxlp;
    sharedMemOfPool shared;
    Integer id;
    Stopper stopp;
    public consumer(sharedMemOfPool shared, Integer id, Stopper stopp, SharedMemOut shm) {
        this.mxlp=new MyXmlParser(shm);
        this.shared=shared;
        this.id=id;
        this.stopp=stopp;

    }

    /**
     *gets from list shared with @see com.oneTimeThreads.prod.producer
     * and calls an execute of nmap in com.oneTimeThreads.cons.consumer#runexec()
     */
    public void run() {

      while(this.stopp.isChecker()){

          this.runexec(this.shared.get());
      }
     return;
    }
    public void runexec(String args){

         try {
            String line;
             String output="";
             Process p;
             if(args.contains("-oX - ")) {
                 p = Runtime.getRuntime().exec("nmap " + args);
             }else{
                 p = Runtime.getRuntime().exec("nmap -oX - " + args);
             }

            BufferedReader input=new BufferedReader(new InputStreamReader(p.getInputStream()));
            while((line=input.readLine())!=null){
                output+=line;
            }
             if (output.contains("</nmpaprun>")) {
                 this.mxlp.parseXML(output);
             }
            input.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}