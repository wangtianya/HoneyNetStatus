/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package cn.wangtianya.learn.other.temp.utag

import android.app.Fragment
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL


class UniverseTagFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val linearLayout = LinearLayout(activity)

        linearLayout.orientation = VERTICAL
        linearLayout.gravity = Gravity.CENTER
        linearLayout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)



        val universeTag1 = UniverseTag(activity)
        set1(universeTag1)
        linearLayout.addView(universeTag1, ViewGroup.LayoutParams(-2, -2))
        linearLayout.addView(View(activity), LinearLayout.LayoutParams(1, 50))

        val universeTag2 = UniverseTag(activity)
        set2(universeTag2)
        linearLayout.addView(universeTag2, LinearLayout.LayoutParams(-2, -2))
        linearLayout.addView(View(activity), LinearLayout.LayoutParams(1, 50))


        val universeTag3 = UniverseTag(activity)
        set3(universeTag3)
        linearLayout.addView(universeTag3, LinearLayout.LayoutParams(-2, -2))
        linearLayout.addView(View(activity), LinearLayout.LayoutParams(1, 50))


        val universeTag4 = UniverseTag(activity)
        set4(universeTag4)
        linearLayout.addView(universeTag4, LinearLayout.LayoutParams(-2, -2))
        linearLayout.addView(View(activity), LinearLayout.LayoutParams(1, 50))


        val universeTag5 = UniverseTag(activity)
        set5(universeTag5)
        linearLayout.addView(universeTag5, LinearLayout.LayoutParams(-2, -2))
        linearLayout.addView(View(activity), LinearLayout.LayoutParams(1, 50))

        val universeTag6 = UniverseTag(activity)
        set6(universeTag6)
        linearLayout.addView(universeTag6, LinearLayout.LayoutParams(-2, -2))
        linearLayout.addView(View(activity), LinearLayout.LayoutParams(1, 50))

        val universeTag7 = UniverseTag(activity)
        set7(universeTag7)
        linearLayout.addView(universeTag7, LinearLayout.LayoutParams(-2, -2))
        linearLayout.addView(View(activity), LinearLayout.LayoutParams(1, 50))


        return linearLayout
    }


    fun set1(universeTag: UniverseTag) {
        universeTag.setParam(null,
                "#3385ff",
                "#3385ff",
                null,
                null,
                "<font color=\"#ffffff\">P</font>",
                "无车位")
    }

    fun set2(universeTag: UniverseTag) {
        universeTag.setParam(null,
                "#3385ff",
                null,
                "#3385ff",
                null,
                "<font color=\"#ffffff\">P</font>",
                null)
    }

    fun set3(universeTag: UniverseTag) {
        universeTag.setParam(null,
                "#3385ff",
                null,
                "#3385ff",
                null,
                "<font color=\"#ffffff\">香菜</font>",
                null)
    }

    fun set4(universeTag: UniverseTag) {
        universeTag.setParam("#999999",
                null,
                "#999999",
                null,
                null,
                "<font color=\"#999999\">1</font>",
                null)
    }

    fun set5(universeTag: UniverseTag) {
        universeTag.setParam(null,
                null,
                null,
                null,
                "https://static.qjuzi.com/web/img/icon-clue-db.png",
                "<font color=\"#333333\">太阳饭店</font>",
                null)
    }

    fun set6(universeTag: UniverseTag) {
        universeTag.setParam("#3385ff",
                null,
                "#3385ff",
                null,
                "https://static.qjuzi.com/web/img/icon-clue-db.png",
                null,
                null)
    }

    fun set7(universeTag: UniverseTag) {
        universeTag.setParam(null,
                null,
                null,
                null,
                "https://static.qjuzi.com/web/img/icon-clue-db.png",
                null,
                null)
    }


}
