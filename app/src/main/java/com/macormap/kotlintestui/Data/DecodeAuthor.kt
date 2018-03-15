package com.macormap.kotlintestui.Data

import android.app.Activity

/**
 * Created by carlo on 15/03/2018.
 */
class DecodeAuthor {

    companion object {
        fun nameAuthor(cod: Int): String {
            var res = ""
            when (cod) {
                1 -> res = "Zmrzlina"

                2 -> res = "Waalewiener"

                3 -> res = "William"

                4 -> res = "Peter Robinson"

                else -> res = "Unknow"
            }
            return res
        }

        fun getIdResourceImgAuthor(activity: Activity, codAuthor: Int): Int {
            var res = 0
            when (codAuthor) {
                1 -> res = activity.resources.getIdentifier("pic1", "drawable", activity.packageName)

                2 -> res = activity.resources.getIdentifier("pic2", "drawable", activity.packageName)

                3 -> res = activity.resources.getIdentifier("pic3", "drawable", activity.packageName)

                4 -> res = activity.resources.getIdentifier("pic4", "drawable", activity.packageName)
            }
            return res
        }



        fun getIdImgPost(activity: Activity, codPost: Int): Int {
            var res = 0
            when (codPost) {
                1 -> res = activity.resources.getIdentifier("food_1", "drawable", activity.packageName)

                2 -> res = activity.resources.getIdentifier("food_2", "drawable", activity.packageName)

                3 -> res = activity.resources.getIdentifier("food_3", "drawable", activity.packageName)

                4 -> res = activity.resources.getIdentifier("food_4", "drawable", activity.packageName)

                5 -> res = activity.resources.getIdentifier("food_5", "drawable", activity.packageName)
            }
            return res
        }

    }

}