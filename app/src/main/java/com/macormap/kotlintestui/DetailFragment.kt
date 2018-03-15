package com.macormap.kotlintestui


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.macormap.kotlintestui.Data.Obj_post

/**
 * Created by carlo on 15/03/2018.
 */
class DetailFragment : Fragment() {

    private var mListener: OnDetailFragmentListener? = null
    private var postSelected: Obj_post? = null

    interface OnDetailFragmentListener {
        fun onCloseFragmentInteraction()
        fun onGetPostSelected(): Obj_post
    }

    companion object {
        fun newInstance(): DetailFragment {
            return DetailFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postSelected = mListener!!.onGetPostSelected()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_detail, container, false)

        val imagePost = view.findViewById<View>(R.id.idImgPost) as ImageView
        imagePost.setImageResource(postSelected!!.post_idImg)

        val imageAuthor = view.findViewById<View>(R.id.idPicAuthor) as ImageView
        imageAuthor.setImageResource(postSelected!!.author_idImg)

        val txtNameAuthor = view.findViewById<View>(R.id.idNameAuthor) as TextView
        txtNameAuthor.setText(postSelected!!.author_name)

        val txtCommentAuthor = view.findViewById<View>(R.id.idCommentAuthor) as TextView
        txtCommentAuthor.setText(postSelected!!.mainComment)

        val txtNumWatch = view.findViewById<View>(R.id.idNumWatch) as TextView
        txtNumWatch.setText(String.format("%,d", postSelected!!.num_watch))

        val txtNumSubComment = view.findViewById<View>(R.id.idNumSubComment) as TextView
        txtNumSubComment.setText(String.format("%,d", postSelected!!.num_subcomment))

        val txtNumLove = view.findViewById<View>(R.id.idNumLove) as TextView
        txtNumLove.setText(String.format("%,d", postSelected!!.num_love))


        val recyclerView = view.findViewById<View>(R.id.idCommentRecycler) as RecyclerView
        recyclerView.setHasFixedSize(true)
        val mRecyclerlayoutManager: RecyclerView.LayoutManager
        mRecyclerlayoutManager = LinearLayoutManager(activity.applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = mRecyclerlayoutManager

        val commentAdapter = CommentAdapter()
        recyclerView.adapter = commentAdapter

        commentAdapter.setListData(postSelected!!.listComments!!)

        val butClose = view.findViewById<View>(R.id.idclosefrag) as ImageButton
        butClose.setOnClickListener { v -> onButtonClose() }

        return view
    }


    fun onButtonClose() {
            mListener!!.onCloseFragmentInteraction()
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnDetailFragmentListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


}