package com.igem2026.doctorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.igem2026.doctorapp.ui.DoctorAppRoot
import com.igem2026.doctorapp.ui.theme.DoctorAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DoctorAppTheme {
                DoctorAppRoot()
            }
        }
    }
}