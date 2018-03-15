package com.macormap.kotlintestui

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.macormap.kotlintestui.Data.Obj_post








/**
 * Created by carlo on 15/03/2018.
 */
class MainAdapter  : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private var listPostAdapter: List<Obj_post>? = null

    private var onClick: OnItemClicked? = null

    interface OnItemClicked {
        fun onItemClick(view : View, position: Int)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj_post = listPostAdapter!!.get(position)

        holder.picAuthor.setImageResource(obj_post.author_idImg)
        holder.picImgPost.setImageResource(obj_post.post_idImg)

        holder.nameAuthor.setText(obj_post.author_name)
        holder.mainCommentAuthor.setText(obj_post.mainComment)
        holder.txt_num_watch.setText(String.format("%,d", obj_post.num_watch))
        holder.txt_num_subcomment.setText(String.format("%,d", obj_post.num_subcomment))
        holder.txt_num_love.setText(String.format("%,d", obj_post.num_love))

        holder.cardView.setOnClickListener(View.OnClickListener {
            onClick?.onItemClick(it, position)
            Log.d("APP","clicked on " +position.toString())
         }
        )

    }

    fun setOnClick(onClick: OnItemClicked) {
        this.onClick = onClick
    }

    override fun getItemCount(): Int {
        return if (listPostAdapter == null) 0 else listPostAdapter!!.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cardView: CardView
        var picAuthor: ImageView
        var picImgPost: ImageView
        var nameAuthor: TextView
        var mainCommentAuthor: TextView
        var txt_num_watch: TextView
        var txt_num_subcomment: TextView
        var txt_num_love: TextView

        init {
            cardView = itemView.findViewById<View>(R.id.idCardrow) as CardView
            picAuthor = itemView.findViewById<View>(R.id.idPicAuthor) as ImageView
            picImgPost = itemView.findViewById<View>(R.id.idImgPost) as ImageView
            nameAuthor = itemView.findViewById<View>(R.id.idNameAuthor) as TextView
            mainCommentAuthor = itemView.findViewById<View>(R.id.idCommentAuthor) as TextView
            txt_num_watch = itemView.findViewById<View>(R.id.idNumWatch) as TextView
            txt_num_subcomment = itemView.findViewById<View>(R.id.idNumSubComment) as TextView
            txt_num_love = itemView.findViewById<View>(R.id.idNumLove) as TextView
        }
    }

    fun updateListData(listPost: List<Obj_post>) {
        listPostAdapter = listPost
        notifyDataSetChanged()
    }


}