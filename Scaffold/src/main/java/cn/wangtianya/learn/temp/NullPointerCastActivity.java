/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.wangtianya.learn.temp;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.Html;

public class NullPointerCastActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        test2();
    }


    private void test1() {
        Context nullContext = getApplication();
        AudioManager am = (AudioManager) null;
    }


    private void test2() {
        String str = "<span style='background-color:#006633;color:#FFFFFF'>8号线</span>";
        Html.fromHtml(str, null, new TagHandler());
    }

}
