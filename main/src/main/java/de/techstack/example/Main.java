package de.techstack.example;

import com.sun.xml.internal.ws.fault.ServerSOAPFaultException;
import com.thomas_bayer.blz.BLZService;
import com.thomas_bayer.blz.BLZServicePortType;
import com.thomas_bayer.blz.DetailsType;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by basti on 23.11.15.
 */
public class Main {

    public static void main(String[] pArgs) {
        Checker lcChecker = new Checker();

        lcChecker.start();
    }
}
