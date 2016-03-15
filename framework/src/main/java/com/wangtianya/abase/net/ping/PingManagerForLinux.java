
package com.wangtianya.abase.net.ping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.wangtianya.abase.net.ping.model.PingListener;
import com.wangtianya.abase.net.ping.model.PingResult;
import com.wangtianya.abase.net.ping.model.PingResultRow;
import com.wangtianya.abase.net.utils.NetworkUtil;

import android.os.AsyncTask;
import android.util.Log;

public class PingManagerForLinux implements PingCommand {

    Map<String, AsyncTask> taskList = new HashMap<String, AsyncTask>();

    public static final Executor THREAD_POOL_EXECUTOR
            = Executors.newCachedThreadPool();


    @Override
    public void ping(String host, int times, PingListener listener) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("-c", String.valueOf(times));
        param.put("-W", "1");
        ping(host, param, listener);
    }

    @Override
    public void ping(String host, Map<String, String> param, PingListener listener) {
        cancel(host);
        taskList.put(host, new AndroidPingTask(host, param, listener).executeOnExecutor(THREAD_POOL_EXECUTOR));
    }

    @Override
    public void cancel(String host) {
        if (taskList.get(host) != null) {
            taskList.get(host).cancel(true);
            taskList.remove(host);
        }
    }

    @Override
    public void cancelAll() {
        for (AsyncTask at : taskList.values()) {
            at.cancel(true);
            taskList.remove(at);
        }
    }

    class AndroidPingTask extends AsyncTask<String, PingResultRow, PingResult> {

        String host;
        Map<String, String> param;
        PingListener listener;

        Process p = null;

        public AndroidPingTask(String host, Map<String, String> param, PingListener listener) {
            this.host = host;
            this.param = param;
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected PingResult doInBackground(String... params) {
            String ip = null;

            for (String temtIp : host.split(",")) {
                if (NetworkUtil.isReachable(temtIp, 1000)){
                    ip = temtIp;
                }
            }

            if (ip == null) {
                return null;
            }

            PingResult pr = new PingResult(ip);

            try {
                p = Runtime.getRuntime().exec(PingManagerForLinuxEM.getPingStr(ip, param));
            } catch (IOException e) {
                e.printStackTrace();
                pr.errMsg = "";
                return pr;
            }

            BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = new String();

            //读出所有信息并显示
            try {
                while ((str = buf.readLine()) != null) {
                    if (isCancelled()) {
                        break;
                    }
                    if (!PingManagerForLinuxEM.isCanReachable(str)) {
                        return pr;
                    }

                    if ( PingManagerForLinuxEM.isStartStr(str)) {
                        listener.onStart();
                    }

                    if(PingManagerForLinuxEM.isTimeoutStr(str)) {
                        pr.rows.add(null);
                        listener.onProgress(null);
                    }

                    if (PingManagerForLinuxEM.isRowStr(str)) {
                        PingResultRow prr = PingManagerForLinuxEM.getRow(str);
                        pr.rows.add(prr);
                        this.publishProgress(prr);
                    }
                    if (PingManagerForLinuxEM.isStatisticsStr(str)) {
                        PingManagerForLinuxEM.getResult(str, pr);
                    }
                }
                return pr;
            } catch (Exception e) {
                listener.onFinish(pr);
            }
            return pr;
        }

        @Override
        protected void onProgressUpdate(PingResultRow... values) {
            if (isCancelled()) {
                return;
            }
            try {
                listener.onProgress(values[0]);
            } catch (Exception e){
                Log.e(this.getClass().getSimpleName(), e.getMessage());
            }
        }
        @Override
        protected void onPostExecute(PingResult pr) {
            listener.onFinish(pr);
        }
        @Override
        protected void onCancelled() {
            if (p != null) {
                p.destroy();
            }
        }
    }
}
