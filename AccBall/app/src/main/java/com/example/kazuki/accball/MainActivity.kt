package com.example.kazuki.accball

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener, SurfaceHolder.Callback {

    private var surfaceWidth:Int = 0
    private var surfaceHeight:Int = 0

    private val radius = 50.0f //radius of ball
    private val coef = 1000.0f //ボールの移動量

    private var ballX: Float = 0f
    private var ballY: Float = 0f
    private var vx: Float = 0f
    private var vy: Float = 0f
    private var time: Long = 0L //前回時間の保持



    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        if(event == null) return
        if(event.sensor.type == Sensor.TYPE_ACCELEROMETER){
            if(time == 0L) {
                time = System.currentTimeMillis()
            }
            val ax = -event.values[0]
            val ay = event.values[1]

            var t = (System.currentTimeMillis() - time).toFloat()
            time = System.currentTimeMillis()
            t /= 1000.0f

            val dx = vx*t + ax*t*t / 2.0f
            val dy = vy*t + ax*t*t / 2.0f
            ballX += dx * coef
            ballY += dy * coef
            vx += ax * t
            vy += ay * t

            if(ballX - radius < 0 && vx < 0){
              vx = -vx / 1.5f
              ballX = radius
            } else if (ballX + radius > surfaceWidth && vx > 0){
              vx = -vx /1.5f
              ballX = surfaceWidth - radius
            }
            if(ballY - radius < 0 && vy < 0){
              vy = -vy / 1.5f
              ballY = radius
            } else if (ballY + radius > surfaceHeight && vy > 0){
              vy = -vy / 1.5f
              ballY = surfaceHeight - radius
            }

            drawCanvas()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        val holder = surfaceView.holder
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        surfaceWidth = width
        surfaceHeight = height
        ballX = (width / 2).toFloat()
        ballY = (height / 2).toFloat()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.unregisterListener(this)
    }

    private fun drawCanvas(){
      val canvas = surfaceView.holder.lockCanvas()
      canvas.drawColor(Color.YELLOW)
      canvas.drawCircle(ballX, ballY, radius, Paint().apply{
        color = Color.MAGENTA
        })
        surfaceView.holder.unlockCanvasAndPost(canvas)
    }

}
