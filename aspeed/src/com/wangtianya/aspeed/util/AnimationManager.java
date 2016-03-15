/*
 * Copyright (C) 2015 wangtianya.com, Inc. All Rights Reserved.
 */
package com.wangtianya.aspeed.util;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class AnimationManager {

    public static Animation getFlashAnimotion() {
        //闪烁
        AlphaAnimation animation = new AlphaAnimation(0.5f, 1.0f);
        animation.setDuration(500);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }

    public static Animation getRotate(float fromDegrees, float toDegrees) {
        RotateAnimation
                animation = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation
                .RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(500);
        return animation;
    }


}
