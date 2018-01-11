package StartingMain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import com.MyHook.MyShutDownHook;
import com.MyHook.Stopper;
import com.SenderThreadsClasses.senderThread;
import com.oneTimeThreads.cons.consumer;
import com.oneTimeThreads.prod.producer;
import com.oneTimeThreads.mySharedMem.sharedMemOfPool;
import com.periodicThreads.periodicThread;
import com.sharedmemforoutput.SharedMemOut;

public class Main {

    public static void main(String[] args) {
        try {
            String fileName=args[1];
            String fileName2=args[0];
            StopMain stopm=new StopMain();
            Stopper stopp=new Stopper();
            final BlockingQueue<String> myqueue = new LinkedBlockingQueue<>();
            List consumerthreads=new LinkedList();
            List periodthreads=new LinkedList();
            String line;
            Integer numofThreads = 0;
            Integer mainperiod=0;
            sharedMemOfPool shar = new sharedMemOfPool();
            SharedMemOut sharout=new SharedMemOut();
            senderThread send=new senderThread(3*1000,0,sharout,stopp);
            Thread sendt=new Thread(send,"sender");
            sendt.start();
            producer myprod = new producer(shar, 1, stopp,myqueue);
            Thread prodt=new Thread(myprod,"producer");
            prodt.start();

            MyShutDownHook myhook = new MyShutDownHook(consumerthreads,periodthreads,Thread.currentThread(),stopm,stopp,sendt,prodt);
            Runtime.getRuntime().addShutdownHook(myhook);
            try {

                FileReader fileReader = new FileReader(fileName);

                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while ((line = bufferedReader.readLine()) != null) {

                    String array1[] = line.split(",");
                    numofThreads = Integer.parseInt(array1[0]);
                    mainperiod=Integer.parseInt(array1[1]);
                }
                Integer i = 0;



                while (i < numofThreads) {
                    consumer cons=new consumer(shar, i, stopp,sharout);
                    i++;
                    Thread consumert=new Thread(cons,"consumer"+i);
                    consumert.start();
                    consumerthreads.add(consumert);

                }
                Integer j = 0;

                while(stopm.isStopmain()) {
                    String line2;

                    FileReader fileReader2 = new FileReader(fileName2);
                    BufferedReader bufferedReader2 = new BufferedReader(fileReader2);

                    boolean filend=true;
                    while(filend) {
                        Random newrandgen=new Random();
                        int randomint=newrandgen.nextInt(4);
                        for (int k = 0; k < randomint; k++) {
                            if (!stopp.isChecker()) {
                                return;
                            }
                            if ((line2 = bufferedReader2.readLine()) != null) {
                                String array[] = line2.split(",");
                                if (array[2].equals("false")) {
                                    myprod.addtomyQueue(array[1]);
                                } else {
                                    periodicThread perd = new periodicThread(array[1], Integer.parseInt(array[3]) * 1000, j, sharout, stopp);
                                    Thread pdt = new Thread(perd, "periodic" + j);
                                    pdt.start();
                                    periodthreads.add(pdt);

                                    j++;

                                }
                            } else {
                                filend=false;
                                break;
                            }
                            Thread.sleep(mainperiod * 3000);
                        }
                    }
                    bufferedReader2.close();

                }
                bufferedReader.close();
                return;
            } catch (FileNotFoundException ex) {
                System.out.println(
                        "Unable to open file '" +
                                fileName + "'");
                System.out.println(ex);
            } catch (IOException ex) {
                System.out.println(
                        "Error reading file '"
                                + fileName + "'");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
