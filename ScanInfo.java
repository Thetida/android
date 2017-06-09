package com.XmlParser;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by giannis on 11/7/15.
 */
public class ScanInfo {
    Map newverlist;
    Map newdebugginglist;
    Map newaddresslist;
    Map newstatuslist;
    Map newtracelist;
    Map newportlist;
    Map newextraportlist;
    Map newhostnames;
    Map newosclass;
    Map newosmatch;
    Map newuptimelist;
    Map newtcpseqlist;
    Map newipidlist;
    Map newtcptslist;

    /**
     *Scaninfo constructor-Gets the Mapings of xmlfiles
     */
    public ScanInfo(Map newverlist , Map newdebugginglist,Map newaddresslist,Map newstatuslist,Map newtracelist,
                    Map newportlist, Map newextraportlist,Map newhostnames,Map newosclass,Map newosmatch,
                    Map newuptimelist,Map newtcpseqlist,Map newipidlist,Map newtcptslist)
    {
        this.newverlist=newverlist;
        this.newdebugginglist=newdebugginglist;
        this.newaddresslist=newaddresslist;
        this.newstatuslist=newstatuslist;
        this.newtracelist=newtracelist;
        this.newportlist=newportlist;
        this.newextraportlist=newextraportlist;
        this.newhostnames=newhostnames;
        this.newosclass=newosclass;
        this.newosmatch=newosmatch;
        this.newuptimelist=newuptimelist;
        this.newtcpseqlist=newtcpseqlist;
        this.newipidlist=newipidlist;
        this.newtcptslist=newtcptslist;

    }
    /**
     *Calls printMap to Print xmls infos one by one
     */
    public void printMyInfo(){
        printMap("verbose",this.newverlist);
        printMap("debug",this.newdebugginglist);
        printMap("address",this.newaddresslist);
        printMap("status",this.newstatuslist);
        printMap("trace",this.newtracelist);
        printMap("port",this.newportlist);
        printMap("extraport",this.newextraportlist);
        printMap("hostnames",this.newhostnames);
        printMap("osclass",this.newosclass);
        printMap("osmatch",this.newosmatch);
        printMap("uptime",this.newuptimelist);
        printMap("tcpsequence",this.newtcpseqlist);
        printMap("ipidsequence",this.newipidlist);
        printMap("tcptssequence",this.newtcptslist);

    }

    /**
     *Print each Map individually
     */
    public void printMap(String name,Map curmap){
        if(!curmap.isEmpty()) {
            System.out.println(name + ":");
        }

        Iterator<Map.Entry<String,Map<String,Map<String,String>>>> iter0 = curmap.entrySet().iterator();
        while(iter0.hasNext()){
            String key = iter0.next().getKey();
            if(curmap.size()>1) {
                System.out.println("\t" + key + ": ");
            }
            Map<String,Map<String,String>> newmap=(Map)curmap.get(key);
            Iterator<Map.Entry<String,Map<String,String>>> iter1=newmap.entrySet().iterator();
            while(iter1.hasNext()){

                String key1 = iter1.next().getKey();
                if(newmap.size()>1) {
                    System.out.println("\t\t" + key1 + ": ");
                }
                Map<String,String> newmap1=(Map)newmap.get(key1);
                Iterator<Map.Entry<String,String>> iter2=newmap1.entrySet().iterator();
                while(iter2.hasNext()){
                    String key2 = iter2.next().getKey();
                    String value=(String) newmap1.get(key2);
                    System.out.println("\t\t\t"+key2+":"+value);

                }
            }
        }
    }
}
