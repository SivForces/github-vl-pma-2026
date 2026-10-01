package com.example.myapp02_dicethrowcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp02_dicethrowcompose.ui.theme.MyApp02_DiceThrowCOMPOSETheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                DiceApp()
            }
        }
    }
}

@Composable
fun DiceApp() {
    val diceSymbols = listOf("⚀", "⚁", "⚂", "⚃", "⚄", "⚅")
    var diceValue by remember { mutableStateOf(1) }
    var isRolling by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current

    val rotation = remember { Animatable(0f) }
    val scale = remember { Animatable(1f) }

    val bigScale = remember { Animatable(1f) }
    val bigAlpha = remember { Animatable(0f) }

    val backgroundColor = Color(0xFFF5F3FF)
    val primaryColor = Color(0xFF352060)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Hod kostku",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
            Text(
                text = diceSymbols[diceValue - 1],
                fontSize = 180.sp,
                color = primaryColor,
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .graphicsLayer {
                        rotationZ = rotation.value
                        scaleX = scale.value
                        scaleY = scale.value
                    }
            )
            Button(
                enabled = !isRolling,
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = White
                ),
                onClick = {
                    isRolling = true
                    scope.launch {
                        launch {
                            rotation.animateTo(
                                rotation.value + 720f,
                                tween(2400, easing = FastOutSlowInEasing)
                            )
                        }
                        var pause = 60L
                        repeat(12) {
                            diceValue = (1..6).random()
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            delay(pause)
                            pause += 25
                        }
                        diceValue = (1..6).random()

                        launch {
                            scale.snapTo(0.6f)
                            scale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioHighBouncy))
                        }
                        launch {
                            bigScale.snapTo(0.5f)
                            bigAlpha.snapTo(0.85f)
                            launch { bigScale.animateTo(5f, tween(1000, easing = FastOutSlowInEasing)) }
                            bigAlpha.animateTo(0f, tween(1000))
                        }
                        isRolling = false
                    }
                }
            ) {
                Text(text = "Hodit", fontSize = 26.sp)
            }
        }


        Text(
            text = diceValue.toString(),
            fontSize = 300.sp,
            fontWeight = FontWeight.Black,
            color = primaryColor,
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    scaleX = bigScale.value
                    scaleY = bigScale.value
                    alpha = bigAlpha.value
                }
        )
    }
}
