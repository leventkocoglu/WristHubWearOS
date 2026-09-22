package com.wristhub.face

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.content.pm.PackageManager
import android.view.WindowManager

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(FaceView(this))
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            val perms = mutableListOf(Manifest.permission.ACTIVITY_RECOGNITION, Manifest.permission.BODY_SENSORS)
            val missing = perms.filter { checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED }
            if (missing.isNotEmpty()) requestPermissions(missing.toTypedArray(), 100)
        }
    }
}
