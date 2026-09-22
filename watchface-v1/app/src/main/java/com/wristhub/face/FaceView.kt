package com.wristhub.face

import android.content.Context
import android.graphics.*
import android.os.BatteryManager
import android.hardware.*
import android.view.View
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.*

class FaceView(context: Context) : View(context), SensorEventListener {
    private val p = Paint(Paint.ANTI_ALIAS_FLAG)
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var steps = 0
    private var heart = 0
    private val baseStep by lazy {
        val s = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        s?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
        val h = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)
        h?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
        0f
    }
    private var stepStart = -1f

    init {
        isFocusable = true
        setBackgroundColor(Color.BLACK)
        baseStep
    }

    override fun onDetachedFromWindow() {
        sensorManager.unregisterListener(this)
        super.onDetachedFromWindow()
    }

    override fun onSensorChanged(e: SensorEvent) {
        when (e.sensor.type) {
            Sensor.TYPE_STEP_COUNTER -> {
                if (stepStart < 0) stepStart = e.values[0]
                steps = max(0, (e.values[0] - stepStart).toInt())
                invalidate()
            }
            Sensor.TYPE_HEART_RATE -> {
                heart = e.values[0].roundToInt()
                invalidate()
            }
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun txt(canvas: Canvas, text: String, x: Float, y: Float, size: Float, color: Int, align: Paint.Align = Paint.Align.CENTER, bold: Boolean = false) {
        p.style = Paint.Style.FILL
        p.color = color
        p.textSize = size
        p.textAlign = align
        p.typeface = if (bold) Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD) else Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        canvas.drawText(text, x, y - (p.ascent()+p.descent())/2, p)
    }

    private fun ring(canvas: Canvas, cx: Float, cy: Float, r: Float, progress: Float, color: Int) {
        p.style = Paint.Style.STROKE; p.strokeCap = Paint.Cap.ROUND; p.strokeWidth = r*0.12f
        p.color = Color.rgb(35,35,35)
        canvas.drawCircle(cx, cy, r, p)
        p.color = color
        val rect = RectF(cx-r, cy-r, cx+r, cy+r)
        canvas.drawArc(rect, -90f, 360f*progress.coerceIn(0f,1f), false, p)
        p.style = Paint.Style.FILL
    }

    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        val w = width.toFloat(); val h = height.toFloat(); val s = min(w,h); val cx=w/2; val cy=h/2

        val bg = RadialGradient(cx, cy*0.7f, s*0.65f, intArrayOf(Color.rgb(18,18,20), Color.BLACK), null, Shader.TileMode.CLAMP)
        p.shader = bg; c.drawCircle(cx,cy,s/2,p); p.shader=null

        p.style=Paint.Style.STROKE; p.strokeWidth=s*0.006f; p.color=Color.rgb(45,45,48)
        c.drawCircle(cx,cy,s*0.475f,p)

        for(i in 0 until 60){
            val a=Math.toRadians((i*6-90).toDouble())
            val major=i%5==0
            val r1=s*(if(major) .425f else .44f); val r2=s*.46f
            p.strokeWidth=s*(if(major) .010f else .004f)
            p.color= if(major) Color.rgb(220,188,123) else Color.rgb(115,115,120)
            c.drawLine(cx+cos(a).toFloat()*r1,cy+sin(a).toFloat()*r1,cx+cos(a).toFloat()*r2,cy+sin(a).toFloat()*r2,p)
        }

        val now=Date()
        val time=SimpleDateFormat("HH:mm", Locale("tr","TR")).format(now)
        val date=SimpleDateFormat("EEE d MMM", Locale("tr","TR")).format(now).uppercase(Locale("tr","TR"))
        txt(c,date,cx,cy-s*.13f,s*.055f,Color.rgb(220,188,123))
        txt(c,time,cx,cy,s*.19f,Color.WHITE,bold=true)

        val bm=context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val battery=bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY).coerceIn(0,100)
        ring(c,cx-s*.25f,cy-s*.245f,s*.095f,battery/100f,Color.rgb(78,220,145))
        txt(c,"▮",cx-s*.25f,cy-s*.27f,s*.055f,Color.rgb(78,220,145))
        txt(c,"$battery%",cx-s*.25f,cy-s*.22f,s*.060f,Color.WHITE)

        val hr = if(heart>0) heart.toString() else "—"
        ring(c,cx+s*.25f,cy-s*.245f,s*.095f,(heart.coerceIn(40,180)-40)/140f,Color.rgb(255,95,95))
        txt(c,"♥",cx+s*.25f,cy-s*.27f,s*.060f,Color.rgb(255,95,95))
        txt(c,hr,cx+s*.25f,cy-s*.22f,s*.060f,Color.WHITE)
        txt(c,"bpm",cx+s*.25f,cy-s*.17f,s*.034f,Color.LTGRAY)

        ring(c,cx,cy+s*.255f,s*.10f,(steps%10000)/10000f,Color.rgb(60,190,220))
        txt(c,"⌁",cx,cy+s*.225f,s*.055f,Color.rgb(60,190,220))
        txt(c,String.format(Locale.US,"%,d",steps),cx,cy+s*.28f,s*.060f,Color.WHITE)
        txt(c,"adım",cx,cy+s*.33f,s*.034f,Color.LTGRAY)

        txt(c,"İstanbul",cx,cy-s*.35f,s*.035f,Color.GRAY)
        txt(c,"—°",cx,cy-s*.30f,s*.050f,Color.WHITE)

        postInvalidateDelayed(1000)
    }
}
