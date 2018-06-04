/*
 * Copyright (C) 2015 wangtianya.com, Inc. All Rights Reserved.
 */
package com.wangtianya.aspeed.fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wangtianya.yaa.core.activity.backhandle.YaaBackHandleFragment;
import com.wangtianya.yaa.core.util.YaaLog;
import com.wangtianya.yaa.core.util.ScreenUtil;
import com.wangtianya.yaa.net.bandwidth.BroadWidthListener;
import com.wangtianya.yaa.net.bandwidth.BroadwidthTest;
import com.wangtianya.yaa.net.bandwidth.BroadwidthTestI;
import com.wangtianya.yaa.net.provider.isp.ISPModel;
import com.wangtianya.yaa.net.provider.isp.TaobaoISPRequest;
import com.wangtianya.aspeed.R;
import com.wangtianya.aspeed.core.ASContext;
import com.wangtianya.aspeed.util.AnimationManager;
import com.wangtianya.aspeed.widget.matetrialdialog.MaterialDialog;
import com.wangtianya.aspeed.widget.topbar.TopBarView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

public class BroadWidthFragment extends YaaBackHandleFragment {
    private static String[] URLS = {
            "http://dlsw.baidu.com/sw-search-sp/soft/a8/27390/androidstudio.1401270841.exe",
            "http://42.236.2.151/dlied1.qq.com/lol/full/LOL_V3.1.5.0_FULL.7z.001?mkey=55544e314ea9a1c8&f=178a&p=.001",
            "http://down.360safe.com/setup.exe"
    };

    YaaLog log = YaaLog.getLogger();

    private TextView btnCesu = null;
    private TextView tvCurrentSpeed = null;
    private TextView tvBeforeTest = null;
    private TextView tvResult = null;
    private TextView tvIsp = null;
    private ImageView ivPoint = null;

    private float pointStart = 90;
    private float pointBase = 120;
    private float pointLast = pointStart;

    private int mHowLong = 10;

    private BroadwidthTestI mBroadwidthTest;
    private MaterialDialog mNumberPickerDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_broad_width, null);
        btnCesu = (TextView) view.findViewById(R.id.tv_cesu_btn);
        tvCurrentSpeed = (TextView) view.findViewById(R.id.tv_current_speed);
        tvResult = (TextView) view.findViewById(R.id.tv_result);
        tvBeforeTest = (TextView) view.findViewById(R.id.tv_before_test);
        tvIsp = (TextView) view.findViewById(R.id.tv_isp);

        ivPoint = (ImageView) view.findViewById(R.id.iv_point);
        ivPoint.startAnimation(AnimationManager.getRotate(pointStart, pointStart));

        btnCesu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("开始测速".equals(btnCesu.getText().toString()) || "重新开始".equals(btnCesu.getText().toString())) {
                    startTest();
                    return;
                }
                if ("立即结束".equals(btnCesu.getText().toString())) {
                    stopTest();
                    return;
                }
            }
        });

        if (ASContext.Caches.topbar != null) {
            ASContext.Caches.topbar.showRightButton(TopBarView.RightButtonType.Setting, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View rlNumberPiker = getActivity().getLayoutInflater().inflate(R.layout.bw_number_picker, null);

                    NumberPicker numberPicker = (NumberPicker) rlNumberPiker.findViewById(R.id.npSeconds);
                    numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                            mHowLong = newVal;
                        }
                    });
                    numberPicker.setMaxValue(10000);
                    numberPicker.setMinValue(10);
                    numberPicker.setValue(mHowLong);

                    mNumberPickerDialog = new MaterialDialog(getActivity());
                    mNumberPickerDialog.setTitle("时长 (单位/秒)");
                    mNumberPickerDialog.setTitleColor(Color.parseColor("#666666"));
                    mNumberPickerDialog.setTitleSize(ScreenUtil.dip2px(6, ASContext.getContext()));
                    mNumberPickerDialog.setContentView(rlNumberPiker);

                    mNumberPickerDialog.setNegativeButton("完成", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mNumberPickerDialog.dismiss();
                            mNumberPickerDialog = null;
                        }
                    });
                    mNumberPickerDialog.setNegativeButtonColor(getResources().getColor(R.color.main_color));
                    mNumberPickerDialog.show();
                }
            });
        }

        refreshISP();
        return view;
    }

    @Override
    public void onDestroy() {
        if (mBroadwidthTest != null) {
            mBroadwidthTest.stop();
            mBroadwidthTest = null;
        }
        if (mNumberPickerDialog != null) {
            mNumberPickerDialog.dismiss();
        }
        super.onDestroy();
    }



    public void startTest() {
        mBroadwidthTest = BroadwidthTest.start(URLS, mHowLong, new BroadWidthListener() {
            @Override
            public void onStart() {
                tvBeforeTest.setText("请等待结果。");
                tvBeforeTest.startAnimation(AnimationManager.getFlashAnimotion());

                ivPoint.startAnimation(AnimationManager.getRotate(pointStart, pointBase));
                pointLast = pointBase;

                btnCesu.setText("立即结束");
                tvResult.setText("");
                btnCesu.setBackgroundResource(R.color.main_color_darkgray);
            }

            @Override
            public void onProgress(long kbSecondDown, long kbSecondUp, int progress, int remainTime) {
                String currentStr = kbSecondDown + " kb/秒";
                log.w(kbSecondUp + "kb/s 上传");
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = remainTime;
                msg.arg2 = Integer.valueOf(kbSecondDown + "");
                msg.obj = currentStr;
                handler.sendMessage(msg);
            }

            @Override
            public void onFinish(long max, long average, long useTime) {
                Message msg = new Message();
                msg.what = 2;
                int broadwidth = (int) ((max + average) / 2 / 1024.0 * 8);
                msg.obj = getResultStr(max + " kb/秒", average + "kb／秒", broadwidth);
                handler.sendMessage(msg);
            }
        });
    }

    public void stopTest() {
        btnCesu.setText("重新开始");
        tvBeforeTest.setText("网络畅通, 点击重新开始测速");
        tvCurrentSpeed.setText("...");
        ivPoint.startAnimation(AnimationManager.getRotate(pointLast, pointStart));
        btnCesu.setBackgroundResource(R.color.main_color_pink);
        tvBeforeTest.clearAnimation();
        mBroadwidthTest.stop();
        BroadwidthTest.stopAll();
    }

    private void refreshISP() {
        Response.Listener listener = new Response.Listener<ISPModel>() {
            @Override
            public void onResponse(ISPModel response) {
            }
        };
        Request request = new TaobaoISPRequest("220.181.38.109", new Response.Listener<ISPModel>() {
            @Override
            public void onResponse(ISPModel model) {
                String strWillshow = "";
                if (!TextUtils.isEmpty(model.city)) {
                    strWillshow = model.city;
                }
                if (!TextUtils.isEmpty(model.isp)) {
                    strWillshow += " " + model.isp;
                }
                tvIsp.setText(strWillshow);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvIsp.setText("未知");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshISP();
                    }
                }, 5000);
                // 重测
            }
        });
        addRequest(request);
    }

    private Handler handler = new MyHandler();

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tvCurrentSpeed.setText(msg.obj.toString());
                    float pointToDegrees = pointBase + calcPointDegrees(msg.arg2);
                    ivPoint.startAnimation(AnimationManager.getRotate(pointLast, pointToDegrees));
                    pointLast = pointToDegrees;
                    tvBeforeTest.setText("请等待结果, " + msg.arg1 + "秒。");
                    break;
                case 2:
                    tvResult.setText(msg.obj.toString());
                    btnCesu.setText("重新开始");
                    tvBeforeTest.setText("");
                    tvCurrentSpeed.setText("...");
                    ivPoint.startAnimation(AnimationManager.getRotate(pointLast, pointStart));
                    pointLast = pointStart;
                    btnCesu.setBackgroundResource(R.color.main_color_pink);
                    break;
            }
        }
    }

    String getResultStr(String maxStr, String averangeStr, int boradwidth) {
        String rateStr = getBoradWidthPingjia(boradwidth);
        String result =
                "        本次测速中, 您的平均下载速度为 " + averangeStr + " , 最大下载速度为 " + maxStr + " , 相当于 " + boradwidth + " 兆带宽。 "
                        + rateStr + "! 觉得软件好用, 请不吝分享, 谢谢!";
        return result;
    }


    private float calcPointDegrees(float speed) {
        int speedBase = 0;
        if (speed >= 0 && speed <= 500) {
            return speed / 100f * 30;
        }

        // 500k － 1m
        speedBase = 500 / 100 * 30;
        if (speed > 500 && speed <= 1024) {
            return (speed - 500) / 524f * 30 + speedBase;
        }

        // 1m - 2m
        speedBase += 30;
        if (speed > 1024 && speed <= 2048) {
            return (speed - 1024) / 1024 * 30 + speedBase;
        }

        // 2m - 5m
        speedBase += 30;
        if (speed > 2048 && speed <= 5120) {
            return (speed - 2048) / 3072 * 30 + speedBase;
        }

        // 5m - 10m
        speedBase += 30;
        if (speed > 5120 && speed <= 10240) {
            return (speed - 5120) / 5120 * 30 + speedBase;
        }

        // 10m - 20m
        speedBase += 30;
        if (speed > 10240 && speed <= 20480) {
            return (speed - 10240) / 10240 * 30 + speedBase;
        }

        return 0;
    }

    private String getBoradWidthPingjia(int bw) {
        if (bw < 2) {
            return "龟速，还能再慢点!";
        }
        if (bw < 4) {
            return "这样的速度在全国网友中, 基本上算是垫底了";
        } else if (bw < 10) {
            return "这网速还一般啦，能满足日常需要了";
        } else if (bw < 20) {
            return "在全国网友中，排名是靠前的";
        } else if (bw >= 20) {
            return "好快的网, 这样的网速足以笑傲一切！";
        }
        return "";
    }
}
