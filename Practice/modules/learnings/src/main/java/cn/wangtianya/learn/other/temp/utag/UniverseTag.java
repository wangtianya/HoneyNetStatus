/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package cn.wangtianya.learn.other.temp.utag;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.wangtianya.learn.R;

public class UniverseTag extends FrameLayout {

    private View leftBg;
    private View leftStroke;
    private View rightBg;
    private View rightStroke;

    private ImageView leftIcon;
    private TextView leftTextView;
    private TextView rightTextView;

    private String leftStrokeColor;
    private String leftBgColor;
    private String rightStrokeColor;
    private String rightBgColor;
    private String lefIconUrl;
    private String leftText;
    private String rightText;

    public UniverseTag(@NonNull Context context) {
        super(context, null);
        init();
    }

    public UniverseTag(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UniverseTag(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_tag_universe, this);

        leftIcon = findViewById(R.id.leftIcon);
        leftTextView = findViewById(R.id.leftTv);
        rightTextView = findViewById(R.id.rightTv);

        leftBg = findViewById(R.id.leftBg);
        leftStroke = findViewById(R.id.leftStroke);
        rightBg = findViewById(R.id.rightBg);
        rightStroke = findViewById(R.id.rightStroke);
    }

    private void setLeftBackgroundColor(String color) {
        DrawableCompat.setTint(leftBg.getBackground(), TextUtils.isEmpty(color) ? 0 : Color.parseColor(color));
    }

    private void setLeftStrokeColor(String color) {
        LayerDrawable layerDrawable = (LayerDrawable) leftStroke.getBackground();
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.leftStrokeItem);
        gradientDrawable.setStroke(1, TextUtils.isEmpty(color) ? 0 : Color.parseColor(color));

    }

    private void setRightroundColor(String color) {
        DrawableCompat.setTint(rightBg.getBackground(), TextUtils.isEmpty(color) ? 0 : Color.parseColor(color));
    }

    private void setRightStrokeColor(String color) {
        LayerDrawable layerDrawable = (LayerDrawable) rightStroke.getBackground();
        GradientDrawable gradientDrawable =
                (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.rightStrokeItem);
        gradientDrawable.setStroke(1, TextUtils.isEmpty(color) ? 0 : Color.parseColor(color));
    }

    public void setParam(String leftStrokeColor,
                         String leftBgColor,
                         String rightStrokeColor,
                         String rightBgColor,
                         String lefIconUrl,
                         String leftText,
                         String rightText) {
        this.leftStrokeColor = leftStrokeColor;
        this.leftBgColor = leftBgColor;
        this.rightStrokeColor = rightStrokeColor;
        this.rightBgColor = rightBgColor;
        this.lefIconUrl = lefIconUrl;
        this.leftText = leftText;
        this.rightText = rightText;

        if (TextUtils.isEmpty(rightStrokeColor) && TextUtils.isEmpty(rightBgColor)) {
            rightStrokeColor = leftStrokeColor;
            rightBgColor = leftBgColor;
        }

        setLeftStrokeColor(leftStrokeColor);
        setLeftBackgroundColor(leftBgColor);
        setRightStrokeColor(rightStrokeColor);
        setRightroundColor(rightBgColor);

        if (!TextUtils.isEmpty(lefIconUrl)) {
            Glide.with(getContext()).load(lefIconUrl).into(leftIcon);
        }

        leftTextView.setText(TextUtils.isEmpty(leftText) ? "" : Html.fromHtml(leftText));
        rightTextView.setText(TextUtils.isEmpty(rightText) ? "" : Html.fromHtml(rightText));

        leftIcon.setVisibility(TextUtils.isEmpty(lefIconUrl) ? GONE : VISIBLE);
        leftTextView.setVisibility(TextUtils.isEmpty(leftText) ? GONE : VISIBLE);
        rightTextView.setVisibility(TextUtils.isEmpty(rightText) ? GONE : VISIBLE);


        if (allStyleNull()) {
            if (TextUtils.isEmpty(lefIconUrl)) { // 1、纯文本(左右均可)
                leftTextView.setPadding(0, 0, 0, 0);
                leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                rightTextView.setPadding(0, 0, 0, 0);
                rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
            } else if (TextUtils.isEmpty(leftText) && TextUtils.isEmpty(rightText)) { // 2、纯icon
                setImageMargin(getDP(16), getDP(16), 0, 0);
            } else {
                setImageMargin(-2, -2, 0, 0); // icon + 文字
            }
            return;
        }


        // 3、文字(左) + 边框/背景
        if (!allStyleNull() && TextUtils.isEmpty(rightText) && TextUtils.isEmpty(lefIconUrl)) {
            leftTextView.setPadding(getDP(4), 0, getDP(2), 0);
            return;
        }

        // 4、ICon + 边框
        if (!TextUtils.isEmpty(leftStrokeColor) && !TextUtils.isEmpty(rightStrokeColor)
                && !TextUtils.isEmpty(lefIconUrl)
                && TextUtils.isEmpty(leftText)
                && TextUtils.isEmpty(rightText)) {
            setImageMargin(-2, -2, getDP(4), getDP(2));
            return;
        }

        // 5、icon + 右边文字 + 边框
        if (!allStyleNull() && TextUtils.isEmpty(leftText) && !TextUtils.isEmpty(rightText)) {
            if (TextUtils.isEmpty(leftStrokeColor) && TextUtils.isEmpty(leftBgColor)
                    && (!TextUtils.isEmpty(rightStrokeColor)
                    || !TextUtils.isEmpty(rightBgColor))) { // 5.1左边icon全尺寸 : 充电桩
                setImageMargin(getDP(16), getDP(16), 0, 0);
            } else {
                setImageMargin(-2, -2, 0, 0); // 5.2 左边正常尺寸 被包裹
            }
            rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        }

    }

    private void setImageMargin(int width, int height, int leftMargin, int rightMargin) {
        MarginLayoutParams layoutParams = leftIcon.getLayoutParams() == null ? new MarginLayoutParams(width, height) :
                (MarginLayoutParams) leftIcon.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        layoutParams.leftMargin = leftMargin;
        layoutParams.rightMargin = rightMargin;
        leftIcon.setLayoutParams(layoutParams);
    }

    private int getDP(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private boolean viewTreeFlag;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!viewTreeFlag) {
            reset();
            viewTreeFlag = true;
            if (INVISIBLE == getVisibility()) {
                return;
            }
        }
    }

    public void reset() {
        if (leftTextView.getLineCount() > 1) {
            setVisibility(INVISIBLE);
            return;
        }

        if (rightTextView.getLineCount() > 1) {
            setVisibility(INVISIBLE);
            return;
        }

        setVisibility(VISIBLE);
    }

    private boolean allStyleNull() {
        return TextUtils.isEmpty(leftStrokeColor) && TextUtils.isEmpty(leftBgColor)
                && TextUtils.isEmpty(rightBgColor) && TextUtils.isEmpty(rightBgColor);
    }

}
