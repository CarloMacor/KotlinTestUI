package com.macormap.kotlintestui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.macormap.kotlintestui.Data.DecodeAuthor
import com.macormap.kotlintestui.Data.Obj_post
import com.macormap.kotlintestui.Data.SubComment
import java.util.ArrayList

/**
 * Created by carlo on 15/03/2018.
 */
class OverFragment : Fragment(), MainAdapter.OnItemClicked {

    private var mainAdapter: MainAdapter? = null

    private var mListener: OnOverFragmentListener? = null

    private var recyclerView: RecyclerView? = null
    private var mRecyclerlayoutManager: RecyclerView.LayoutManager? = null
    private var mHorizontalHelper: OrientationHelper? = null


    private var listPost: MutableList<Obj_post>? = null

    private var listSubComment: MutableList<SubComment>? = null

    private var txtNum1: TextView? = null
    private var txtNum2: TextView? = null

    private var bottom_but_1: ImageButton? = null
    private var bottom_but_2: ImageButton? = null
    private var bottom_but_3: ImageButton? = null

    private var bottomArrow_1: ImageView? = null
    private var bottomArrow_2: ImageView? = null
    private var bottomArrow_3: ImageView? = null


    interface OnOverFragmentListener {
        fun onPostSelectFragmentInteraction(view: View, index: Int)
        fun setbackImages(ind: Int, codRes: Int, traspPar: Float)
    }


    fun getPostSelected(index: Int): Obj_post {
        return listPost!!.get(index)
    }

    companion object {
        fun newInstance(): OverFragment {
            return OverFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_over, container, false)
        setupTheView(view)
        return view
    }


    override fun onItemClick(view : View, position: Int) {
        Log.d("APP","clicked on Fragment " +position.toString())
        onPostClicked(view, position)
    }


    private fun setupTheView(view: View) {

        recyclerView = view.findViewById<View>(R.id.idMainRecycle) as RecyclerView
        recyclerView!!.setHasFixedSize(true)
        mRecyclerlayoutManager = LinearLayoutManager(activity.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerView!!.setLayoutManager(mRecyclerlayoutManager)

        mHorizontalHelper = OrientationHelper.createHorizontalHelper(mRecyclerlayoutManager)

        mainAdapter = MainAdapter()
        recyclerView!!.setAdapter(mainAdapter)

        createDataMainRecycler()


        val txtnumImg = view.findViewById(R.id.idtxtTotNumImg) as TextView
        txtnumImg.text = Integer.toString(listPost!!.size)


        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)


        recyclerView!!.addOnScrollListener(mScrollListener)

        mainAdapter!!.setOnClick(this); // Bind the listener
//        recyclerView!!.addOnItemTouchListener(RecyclerItemClickListener())

/*

        recyclerView!!.addOnItemTouchListener( RecyclerItemClickListener(activity,
                object : RecyclerItemClickListener.OnItemClickListener() {
                    fun onItemClick(view: View, position: Int) {
                        onPostClicked(view, position)
                    }
                }))

*/

        txtNum1 = view.findViewById<View>(R.id.idtxtIndex1) as TextView
        txtNum2 = view.findViewById<View>(R.id.idtxtIndex2) as TextView
        txtNum2?.setAlpha(0.0f)

        setBottomButton(view)

        // first time set the second background image to null and trasparent
        mListener!!.setbackImages(2, 0, 0.0f)

    }


    fun onPostClicked(view: View, index: Int) {
            mListener!!.onPostSelectFragmentInteraction(view, index)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnOverFragmentListener) {
            mListener = context as OnOverFragmentListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    private fun setBottomButton(view: View) {
        bottom_but_1 = view.findViewById<View>(R.id.idBottomBut1) as ImageButton
        bottom_but_2 = view.findViewById<View>(R.id.idBottomBut2) as ImageButton
        bottom_but_3 = view.findViewById<View>(R.id.idBottomBut3) as ImageButton

        bottom_but_1!!.setOnClickListener({ v -> setBottomArrow(1) })
        bottom_but_2!!.setOnClickListener({ v -> setBottomArrow(2) })
        bottom_but_3!!.setOnClickListener({ v -> setBottomArrow(3) })

        bottomArrow_1 = view.findViewById<View>(R.id.idBottomArrow1) as ImageView
        bottomArrow_2 = view.findViewById<View>(R.id.idBottomArrow2) as ImageView
        bottomArrow_3 = view.findViewById<View>(R.id.idBottomArrow3) as ImageView

        setBottomArrow(1)
    }


    private fun setBottomArrow(ind: Int) {
        //        Toast.makeText(this, "Clicked on Button Num : "+Integer.toString(ind),  Toast.LENGTH_SHORT).show();

        bottom_but_1!!.setColorFilter(Color.WHITE)
        bottom_but_2!!.setColorFilter(Color.WHITE)
        bottom_but_3!!.setColorFilter(Color.WHITE)

        bottomArrow_1!!.setVisibility(View.INVISIBLE)
        bottomArrow_2!!.setVisibility(View.INVISIBLE)
        bottomArrow_3!!.setVisibility(View.INVISIBLE)
        when (ind) {
            1 -> {
                bottomArrow_1!!.setVisibility(View.VISIBLE)
                bottom_but_1!!.setColorFilter(Color.RED)
            }
            2 -> {
                bottomArrow_2!!.setVisibility(View.VISIBLE)
                bottom_but_2!!.setColorFilter(Color.RED)
            }
            3 -> {
                bottomArrow_3!!.setVisibility(View.VISIBLE)
                bottom_but_3!!.setColorFilter(Color.RED)
            }
        }
    }


    // used just to inform that the recycler is scrolled
    private val mScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            val offer = recyclerView!!.computeHorizontalScrollOffset()
            setScaleCardbyScrolled(offer, dx)
        }
    }


    private fun setScaleCardbyScrolled(offval: Int, locdx: Int) {
        val minScale = 1.0f
        val maxScale = 1.30f
        val offer = recyclerView!!.computeHorizontalScrollOffset()

        val childCount = mRecyclerlayoutManager!!.getChildCount()
        if (childCount == 0) {
            return
        }

        val center: Int
        if (mRecyclerlayoutManager!!.getClipToPadding()) {
            center = mHorizontalHelper!!.getStartAfterPadding() + mHorizontalHelper!!.getTotalSpace() / 2
        } else {
            center = mHorizontalHelper!!.getEnd() / 2
        }

        var nearest_1 = -1
        var nearest_2 = -1
        var paramtxt_1 = 0.0f
        var paramtxt_2 = 0.0f


        for (i in 0 until childCount) {
            val child = mRecyclerlayoutManager!!.getChildAt(i)

            val intVisibleItem = recyclerView!!.getChildAdapterPosition(child)

            val childCenter = mHorizontalHelper!!.getDecoratedStart(child) + mHorizontalHelper!!.getDecoratedMeasurement(child) / 2
            val absDistance = Math.abs(childCenter - center)

            val cardView = child.findViewById(R.id.idCardrow) as CardView



            if (absDistance < center) {
                val param = (center - absDistance) * 1.0f / (center * 1.0f)
                val locscale = 1 + param * (maxScale - minScale)
                cardView.setScaleX(locscale)
                cardView.setScaleY(locscale)
                if (nearest_1 < 0) {
                    nearest_1 = intVisibleItem
                    paramtxt_1 = param
                } else {
                    if (nearest_2 < 0) {
                        nearest_2 = intVisibleItem
                        paramtxt_2 = param
                    }
                }
            } else {
                cardView.setScaleX(1.0f)
                cardView.setScaleY(1.0f)
            }


            if (nearest_1 == 0) {
                if (offval == 0) {
                    paramtxt_1 = 1.0f
                    paramtxt_2 = 0.0f
                }
            }

            if (nearest_1 == listPost!!.size - 1) {
                paramtxt_1 = 1.0f
                paramtxt_2 = 0.0f
            }

            if (nearest_1 < 0) {
                txtNum1!!.setAlpha(0.0f)
            } else {
                txtNum1!!.setText(Integer.toString(nearest_1 + 1))
                txtNum1!!.setAlpha(paramtxt_1)
                val codImg = DecodeAuthor.getIdImgPost(activity, nearest_1 + 1)
                mListener!!.setbackImages(1, codImg, paramtxt_1)
            }
            if (nearest_2 < 0) {
                txtNum2!!.setAlpha(0.0f)
            } else {
                txtNum2!!.setText(Integer.toString(nearest_2 + 1))
                txtNum2!!.setAlpha(paramtxt_2)
                val codImg = DecodeAuthor.getIdImgPost(activity, nearest_2 + 1)
                mListener!!.setbackImages(2, codImg, paramtxt_2)
            }
        }
    }


    // Hard Code data
    private fun createDataMainRecycler() {
        val activity = activity
        var subComment: SubComment

        listSubComment = ArrayList()

        // 1
        subComment = SubComment()
        subComment.setAuthor_code(activity, 1)
        subComment.subCommenttxt = "Bruges is small and beautiful city, you can enjoy walking here."
        subComment.comment_date  = "AUG 17,2014"
        listSubComment!!.add(subComment)
        // 2
        subComment = SubComment()
        subComment.setAuthor_code(activity, 2)
        subComment.subCommenttxt = "Just a word of caution, please don't think you can possibly stary on"
        subComment.comment_date = "SEP 14,2014"
        listSubComment!!.add(subComment)
        // 3
        subComment = SubComment()
        subComment.setAuthor_code(activity, 3)
        subComment.subCommenttxt ="the canal tours could be considered a 'tourist trap' however, I did enjoy. "
        subComment.comment_date = "NOV 11,2016"
        listSubComment!!.add(subComment)
        // 4
        subComment = SubComment()
        subComment.setAuthor_code(activity, 4)
        subComment.subCommenttxt = "Yes I wanna comment , How cook that one ? "
        subComment.comment_date = "GEN 1,2018"
        listSubComment!!.add(subComment)
        // 5
        subComment = SubComment()
        subComment.setAuthor_code(activity, 1)
        subComment.subCommenttxt = "Nothing to comment for the moment "
        subComment.comment_date  = "FEB 6,2018"
        listSubComment!!.add(subComment)



        listPost = ArrayList()
        var obj_post: Obj_post
        obj_post = Obj_post()
        obj_post.setAuthor_code(activity, 1)
        obj_post.mainComment    = "Woow so beautiful plate"
        obj_post.num_watch      = 2345
        obj_post.num_subcomment = 34
        obj_post.num_love       = 185
        obj_post.post_idImg     = DecodeAuthor.getIdImgPost(activity, 1)
        obj_post.addItemListComments(listSubComment!!.get(0))
        obj_post.addItemListComments(listSubComment!!.get(1))
        obj_post.addItemListComments(listSubComment!!.get(3))
        obj_post.addItemListComments(listSubComment!!.get(2))
        obj_post.addItemListComments(listSubComment!!.get(4))
        listPost!!.add(obj_post)


        obj_post = Obj_post()
        obj_post.setAuthor_code(activity, 2)
        obj_post.mainComment    = "Yes today Lasagne and Fettuccine"
        obj_post.num_watch      = 835
        obj_post.num_subcomment = 12
        obj_post.num_love       = 85
        obj_post.post_idImg     = DecodeAuthor.getIdImgPost(activity, 2)
        obj_post.addItemListComments(listSubComment!!.get(4))
        obj_post.addItemListComments(listSubComment!!.get(2))
        obj_post.addItemListComments(listSubComment!!.get(0))
        listPost!!.add(obj_post)

        obj_post = Obj_post()
        obj_post.setAuthor_code(activity, 3)
        obj_post.mainComment    = "Pizza remain the best"
        obj_post.num_watch      = 7832
        obj_post.num_subcomment = 92
        obj_post.num_love       = 1085
        obj_post.post_idImg     = DecodeAuthor.getIdImgPost(activity, 3)
        obj_post.addItemListComments(listSubComment!!.get(1))
        obj_post.addItemListComments(listSubComment!!.get(3))
        obj_post.addItemListComments(listSubComment!!.get(2))
        obj_post.addItemListComments(listSubComment!!.get(4))
        obj_post.addItemListComments(listSubComment!!.get(2))
        obj_post.addItemListComments(listSubComment!!.get(0))
        listPost!!.add(obj_post)

        obj_post = Obj_post()
        obj_post.setAuthor_code(activity, 4)
        obj_post.mainComment    = "Today I cook this plate"
        obj_post.num_watch      = 1000
        obj_post.num_subcomment = 100
        obj_post.num_love       = 10
        obj_post.post_idImg     = DecodeAuthor.getIdImgPost(activity, 4)
        obj_post.addItemListComments(listSubComment!!.get(3))
        obj_post.addItemListComments(listSubComment!!.get(2))
        obj_post.addItemListComments(listSubComment!!.get(4))
        obj_post.addItemListComments(listSubComment!!.get(2))
        obj_post.addItemListComments(listSubComment!!.get(0))
        obj_post.addItemListComments(listSubComment!!.get(3))
        obj_post.addItemListComments(listSubComment!!.get(2))
        obj_post.addItemListComments(listSubComment!!.get(4))
        obj_post.addItemListComments(listSubComment!!.get(2))
        obj_post.addItemListComments(listSubComment!!.get(0))
        obj_post.addItemListComments(listSubComment!!.get(3))
        obj_post.addItemListComments(listSubComment!!.get(2))
        obj_post.addItemListComments(listSubComment!!.get(4))
        obj_post.addItemListComments(listSubComment!!.get(2))
        obj_post.addItemListComments(listSubComment!!.get(0))
        listPost!!.add(obj_post)

        obj_post = Obj_post()
        obj_post.setAuthor_code(activity, 2)
        obj_post.mainComment    = "Swite plate"
        obj_post.num_watch      = 7712
        obj_post.num_subcomment = 3456
        obj_post.num_love       = 982
        obj_post.post_idImg     = DecodeAuthor.getIdImgPost(activity, 5)
        obj_post.addItemListComments(listSubComment!!.get(0))
        listPost!!.add(obj_post)

        mainAdapter!!.updateListData(listPost!!)
    }


}