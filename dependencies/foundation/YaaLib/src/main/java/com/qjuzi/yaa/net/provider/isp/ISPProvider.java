package com.qjuzi.yaa.net.provider.isp;

import com.qjuzi.yaa.net.provider.isp.impl.taobao.TaobaoISPImpl;

public class ISPProvider implements ISPProviderI {

    private static ISPProviderI impl = new TaobaoISPImpl();

    public static synchronized ISPProviderI getInstance() {
        if (impl == null) {
            impl = new TaobaoISPImpl();
        }
        return impl;
    }

    @Override
    public ISPModel getIspModel(String host) {
        return impl.getIspModel(host);
    }

    @Override
    public void getIspModelAsync(String host, ISPAsyncListener listener) {
        impl.getIspModelAsync(host, listener);
    }

}
