package com.macormap.kotlintestui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.macormap.kotlintestui.Data.SubComment

/**
 * Created by carlo on 15/03/2018.
 */
class CommentAdapter : RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {

    private var listcommentAdapter: List<SubComment>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.other_comment, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val subComment = listcommentAdapter!!.get(position)
        holder.picAuthor.setImageResource(subComment.author_idImg)
        holder.nameAuthor.setText(subComment.author_name)
        holder.subComment.setText(subComment.subCommenttxt)
        holder.subDateComment.setText(subComment.comment_date)
    }

    override fun getItemCount(): Int {
        return if (listcommentAdapter == null) 0 else listcommentAdapter!!.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var picAuthor: ImageView
        var nameAuthor: TextView
        var subComment: TextView
        var subDateComment: TextView

        init {
            picAuthor = itemView.findViewById<View>(R.id.idSubPicAuthor) as ImageView
            nameAuthor = itemView.findViewById<View>(R.id.idSubNameAuthor) as TextView
            subComment = itemView.findViewById<View>(R.id.idSubComment) as TextView
            subDateComment = itemView.findViewById<View>(R.id.idDateSubComment) as TextView
        }
    }


    fun setListData(listComment: List<SubComment>) {
        listcommentAdapter = listComment
        notifyDataSetChanged()
    }


}