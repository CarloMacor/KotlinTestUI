package com.macormap.kotlintestui.Data

import android.app.Activity

/**
 * Created by carlo on 14/03/2018.
 */
data class SubComment(
        var author_code   : Int     = 0,
        var author_name   : String  = "",
        var author_idImg  : Int     = 0,
        var subCommenttxt : String  = "",
        var comment_date  : String  = ""
) {

    fun setAuthor_code(activity: Activity, author_code: Int) {
        this.author_code = author_code
        author_name  = DecodeAuthor.nameAuthor(author_code)
        author_idImg = DecodeAuthor.getIdResourceImgAuthor(activity, author_code)
    } 
    
}