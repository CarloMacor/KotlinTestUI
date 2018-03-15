package com.macormap.kotlintestui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.macormap.kotlintestui.Data.Obj_post

class MainActivity : AppCompatActivity(),  DetailFragment.OnDetailFragmentListener
        ,OverFragment.OnOverFragmentListener {


    private var overFragment: OverFragment? = null

    private var imgBack1: ImageView? = null
    private var imgBack2: ImageView? = null

    private var viewfragDetail: View? = null
    private var postSelected: Obj_post? = null

    private var xfragSxMargin: Float = 0.toFloat()
    private var animationFragOn: AnimationSet? = null
    private var animationFragOff: AnimationSet? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarSetup()
        overFragment = OverFragment.newInstance()
        val fm = supportFragmentManager

        fm.beginTransaction()
                .add(R.id.idFragmentContainer, overFragment)
                .commit()

        getBackRefImage()
    }


    // setup of the toolBar
    private fun toolbarSetup() {
        val toolbar = findViewById<View>(R.id.idToolBar) as Toolbar
        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_search)
        toolbar.setNavigationOnClickListener { Toast.makeText(applicationContext, "Toolbar left Button clicked", Toast.LENGTH_SHORT).show() }
    }


    // get reference of the two background images
    private fun getBackRefImage() {
        imgBack1 = findViewById<View>(R.id.idMainBack_1) as ImageView
        imgBack2 = findViewById<View>(R.id.idMainBack_2) as ImageView
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // not usefull just prepared and have an icon on right of Toolbar
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // not usefull just prepared
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }


    // Implementation of  OnDetailFragmentListener [START]
    override fun onCloseFragmentInteraction() {
        val linearSubComment = findViewById<View>(R.id.idSubComment) as LinearLayout
        val animSub = AnimationUtils.loadAnimation(this, R.anim.exit_trasl_animation)
        animSub.fillAfter = true
        linearSubComment.startAnimation(animSub)

        val viewfrag = findViewById<View>(R.id.idViewFragDetail)
        viewfrag.pivotX = xfragSxMargin
        viewfrag.pivotY = 0f
        viewfrag.startAnimation(animationFragOff)

        Log.d("APP22", "close Frag")
    }

    override fun onGetPostSelected(): Obj_post {
        return postSelected!!
    }
    // Implementation of  OnDetailFragmentListener [END]

    // Implementation of  OnOverFragmentListener [START]
    override fun onPostSelectFragmentInteraction(view: View, index: Int) {
        // here remeamber the Obj with data of the post selected
        // gived to the other fragment using  onGetPostSelected()
        postSelected = overFragment!!.getPostSelected(index)

        val detailFragment = DetailFragment.newInstance()

        createAnimationFrag(view)

        val fm = supportFragmentManager
        fm.beginTransaction()
                .replace(R.id.idFragmentContainer, detailFragment)
                //                    .add(R.id.idFragmentContainer, detailFragment)
                //                    .addToBackStack(null)
                .commit()
        fm.executePendingTransactions()


        val linearSubComment = findViewById<View>(R.id.idSubComment) as LinearLayout
        val animSub = AnimationUtils.loadAnimation(this, R.anim.enter_trasl_animation)
        animSub.fillAfter = true
        linearSubComment.startAnimation(animSub)

        viewfragDetail = findViewById<View>(R.id.idViewFragDetail)
        viewfragDetail!!.setPivotX(xfragSxMargin)
        viewfragDetail!!.setPivotY(0f)
        viewfragDetail!!.startAnimation(animationFragOn)
        //       viewfragDetail.setAlpha(0.6f);
    }


    private fun createAnimationFrag(view: View) {

        // get the recyler parent of the card selected to know the y position of parent
        val rec = view.parent as RecyclerView
        val xrec = rec.x
        val yrec = rec.y

        // x , y of card not scaled
        val x1 = view.x
        val y1 = view.y

        // to get dimension of schede
        val xdim = view.width.toFloat()
        val ydim = view.height.toFloat()

        Log.d("APP22", "dimx scheda " + java.lang.Float.toString(xdim))
        Log.d("APP22", "dimy scheda " + java.lang.Float.toString(ydim))

        // need the scale to know exactly the vertex point to align the fragment
        val xscal = view.scaleX
        val yscal = view.scaleY

        val dimxScaled = xdim * xscal
        val dimyScaled = ydim * yscal
        Log.d("APP22", "dimxScaled scheda " + java.lang.Float.toString(dimxScaled))
        Log.d("APP22", "dimyScaled scheda " + java.lang.Float.toString(dimyScaled))


        xfragSxMargin = xdim / 11f

        var dimXback = imgBack1!!.getWidth().toFloat()
        val dimYback = imgBack1!!.getHeight().toFloat()


        dimXback = dimXback * 10f / 11f

        val rapXImages = xdim * xscal / (dimXback * 1.0f)

        val scaleYToApply = ydim * yscal / (dimYback / 2.0f)

        // the coordinate of upper left coner of the image of selected card
        // the y of recycler more the y of the card but ( update for current scale of card )
        val y1v = yrec + y1 + ydim * (1 - yscal) / 2f

        val x1v = xrec + x1 + xdim * (1 - xscal) / 2f - xfragSxMargin  //  - xfragSxMargin


        val traslOnFrag = TranslateAnimation(
                Animation.ABSOLUTE, x1v,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, y1v,
                Animation.ABSOLUTE, 0f)
        traslOnFrag.duration = 300


        val scalerOn = ScaleAnimation(
                rapXImages, 1.0f,
                scaleYToApply, 1.0f,
                Animation.ABSOLUTE, x1v + xfragSxMargin / rapXImages,
                Animation.ABSOLUTE, y1v)
        scalerOn.duration = 300

        animationFragOn = AnimationSet(true)
        animationFragOn!!.addAnimation(traslOnFrag)
        animationFragOn!!.addAnimation(scalerOn)


        val traslOFFFrag = TranslateAnimation(
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, x1v,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, y1v)
        traslOFFFrag.duration = 300

        val scalerOFF = ScaleAnimation(
                1.0f, rapXImages,
                1.0f, scaleYToApply,
                Animation.ABSOLUTE, x1v + xfragSxMargin / rapXImages,
                Animation.ABSOLUTE, y1v)
        scalerOFF.duration = 300


        animationFragOff = AnimationSet(true)
        animationFragOff!!.addAnimation(traslOFFFrag)
        animationFragOff!!.addAnimation(scalerOFF)
        animationFragOff!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                viewfragDetail!!.setVisibility(View.INVISIBLE)
                val fm = supportFragmentManager
                fm.beginTransaction()
                        .replace(R.id.idFragmentContainer, overFragment)
                        .commit()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })


    }


    override fun setbackImages(ind: Int, codRes: Int, traspPar: Float) {
        when (ind) {
            1 -> {
                imgBack1!!.setImageResource(codRes)
                imgBack1!!.setAlpha(traspPar)
            }
            2 -> {
                imgBack2!!.setImageResource(codRes)
                imgBack2!!.setAlpha(traspPar)
            }
        }
    }
    // Implementation of  OnOverFragmentListener [END]


}
