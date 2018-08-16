package com.qjuzi.yaa.net.ping2.inteface;

import java.util.ArrayList;
import java.util.List;

public class PingResult {

    public String host;

    public int packetsTransmitted = 0;

    public int packetsReceived = 0;

    public String consoleMsg;

    public List<PingRow> rows = new ArrayList<>();


    public PingResult(String host) {
        this.host = host;
    }

}
