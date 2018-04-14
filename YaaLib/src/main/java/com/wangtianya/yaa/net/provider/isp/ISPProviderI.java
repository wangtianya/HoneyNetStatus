package com.wangtianya.yaa.net.provider.isp;

/**
 * Created by wangtianya on 2018/4/15.
 */

public interface ISPProviderI {

    ISPModel getIspModel(String host);

    void getIspModelAsync(String host, ISPAsyncListener listener);

}

