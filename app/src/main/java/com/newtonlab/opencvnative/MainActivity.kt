package com.newtonlab.opencvnative

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import com.newtonlab.opencvnative.databinding.ActivityMainBinding
import java.lang.Float.max

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private lateinit var binding: ActivityMainBinding

    var srcBitmap: Bitmap? = null
    var dstBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        srcBitmap = BitmapFactory.decodeResource(this.resources, R.drawable.rallycar)

        dstBitmap = srcBitmap!!.copy(srcBitmap!!.config, true)

        binding.imageView.setImageBitmap(dstBitmap)

        binding.sldSigma.setOnSeekBarChangeListener(this)
    }

    fun btnFlip_click(view: View)
    {
        myFlip(srcBitmap!!, srcBitmap!!)
        doBlur()
    }

    fun doBlur()
    {
        val sigma = max(0.1F, binding.sldSigma.progress / 10F)
        this.myBlur(srcBitmap!!, dstBitmap!!, sigma)
    }

    /**
     * A native method that is implemented by the 'opencvnative' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun myFlip(bitmapIn: Bitmap, bitmapOut: Bitmap)
    external fun myBlur(bitmapIn: Bitmap, bitmapOut: Bitmap, sigma: Float)
    companion object {
        // Used to load the 'opencvnative' library on application startup.
        init {
            System.loadLibrary("opencvnative")
        }
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        doBlur()

    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }
}