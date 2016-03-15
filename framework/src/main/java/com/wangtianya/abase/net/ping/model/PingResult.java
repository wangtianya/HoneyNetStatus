
package com.wangtianya.abase.net.ping.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianya on 15/4/21.
 */
public class PingResult {

    public boolean isSuccess = false;

    public String host = "";

    public int packetsTransmitted = 0;

    public int packetsReceived = 0;

    public String errMsg = "";

    public List<PingResultRow> rows = new ArrayList<PingResultRow>();

    public PingResult(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "isSuccess: " + isSuccess + ", host" + host + ", packetsTransmitted" + packetsTransmitted + ", packetsReceived" + packetsReceived;
    }
}

