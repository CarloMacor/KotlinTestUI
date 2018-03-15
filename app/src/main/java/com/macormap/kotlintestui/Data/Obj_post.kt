package com.macormap.kotlintestui.Data

import android.app.Activity
import java.util.ArrayList

/**
 * Created by carlo on 14/03/2018.
 */
data class Obj_post(
        var author_code    : Int    = 0,
        var post_idImg     : Int    = 0,
        var author_name    : String = "",
        var author_idImg   : Int    = 0,
        var mainComment    : String = "",
        var listComments   : MutableList<SubComment> = ArrayList(),
        var num_watch      : Int    = 0,
        var num_subcomment : Int    = 0,
        var num_love       : Int    = 0
) {

    fun setAuthor_code(activity: Activity, author_code: Int) {
        this.author_code = author_code
        author_name  = DecodeAuthor.nameAuthor(author_code)
        author_idImg = DecodeAuthor.getIdResourceImgAuthor(activity, author_code)
    }


    fun addItemListComments(comment: SubComment) {
        listComments.add(comment)
    }

}