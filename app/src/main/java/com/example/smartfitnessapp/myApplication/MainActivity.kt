package com.example.smartfitnessapp.myApplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.smartfitnessapp.navigation.MainNavigation
import com.example.smartfitnessapp.ui.theme.SmartFitnessAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartFitnessAppTheme {
                MainNavigation()
            }
        }
    }
}