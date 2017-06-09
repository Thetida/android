package com.XmlParser;

import com.sharedmemforoutput.SharedMemOut;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

/**
 * Created by giannis on 11/7/15.
 */
public class MyXmlParser {
   SharedMemOut myshm;
    public MyXmlParser(SharedMemOut myshm){
        this.myshm=myshm;
    }

    public void parseXML(String output){
        try {
            Map newverlist=getPortList(output,"verbose");

            Map newdebugginglist=getPortList(output,"debugging");

            Map newaddresslist=getPortList(output,"address");

            Map newstatuslist=getPortList(output,"status");

            Map newtracelist=getPortList(output,"trace");

            Map newportlist=getPortList(output,"port");

            Map newextraportlist=getPortList(output,"extraports");

            Map newhostnames=getPortList(output,"hostname");

            Map newosclass=getPortList(output,"osclass");

            Map newosmatch=getPortList(output,"osmatch");

            Map newuptimelist=getPortList(output,"uptime");

            Map newtcpseqlist=getPortList(output,"tcpsequence");

            Map newipidlist=getPortList(output,"ipidsequence");

            Map newtcptslist=getPortList(output,"tcptssequence");

            ScanInfo newscan=new ScanInfo(newverlist,newdebugginglist,newaddresslist,newstatuslist,newtracelist,newportlist
            ,newextraportlist,newhostnames,newosclass,newosmatch,newuptimelist,newtcpseqlist,newipidlist,newtcptslist);
            this.myshm.put(newscan);


        }catch (Exception e){
            System.out.println(e);
        }
    }


    public Map<String, Map<String,Map<String,String>>> getPortList(String xml,String element){
        try{
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            StringBuilder xmlStringBuilder = new StringBuilder();
            xmlStringBuilder.append(xml);
            ByteArrayInputStream input =  new ByteArrayInputStream(
                    xmlStringBuilder.toString().getBytes("UTF-8"));
            Document doc = dBuilder.parse(input);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName(element);
            Map<String, Map<String,Map<String,String>>> portlist=new LinkedHashMap<>();


            for (int temp = 0; temp < nList.getLength(); temp++) {
                Map<String,Map<String,String>> externalmap=new LinkedHashMap<>();
                Node nNode = nList.item(temp);
                NamedNodeMap lista1 = nNode.getAttributes();
                Map<String,String> valuemap=new LinkedHashMap<>();
                for (int k = 0; k < lista1.getLength(); k++) {
                    valuemap.put(lista1.item(k).getNodeName(), lista1.item(k).getNodeValue());
                }
                externalmap.put("values",valuemap);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    NodeList lista = eElement.getChildNodes();

                    for (int templista = 0; templista < lista.getLength(); templista++) {
                        Node nNodelista = lista.item(templista);
                        Element elLista = (Element) nNodelista;
                        String tag = elLista.getTagName();
                        NamedNodeMap nListaextra = elLista.getAttributes();
                        Map<String, String> internalmap = new LinkedHashMap();
                        for (int j = 0; j < nListaextra.getLength(); j++) {
                            internalmap.put(nListaextra.item(j).getNodeName(), nListaextra.item(j).getNodeValue());
                        }
                        externalmap.put(tag, internalmap);

                    }

                }
                portlist.put(element+temp,externalmap);


            }
            return portlist;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
