package com.example.dingdong

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dingdong.ui.theme.DingDongTheme
import java.util.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding

class MainActivity : ComponentActivity() {
    private lateinit var textToSpeech: TextToSpeech
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.getDefault()
            }
        }
        
        enableEdgeToEdge()
        setContent {
            DingDongTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF64B5F6)
                ) {
                    SayThingsApp(textToSpeech)
                }
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}

@Composable
fun SayThingsApp(textToSpeech: TextToSpeech) {
    var text by remember { mutableStateOf("") }
    var outputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Say Things",
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 32.dp)
            )

            TextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text("Type Here") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )
        }

        // Centered button
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { 
                    outputText = text
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                },
                modifier = Modifier
                    .size(160.dp),  // Increased size
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Red
                    )
            ) {
                Surface(
                    modifier = Modifier
                        .size(120.dp),  // Increased size
                    shape = CircleShape,
                    color = Color.Red
                ) { }
            }
        }

        if (outputText.isNotEmpty()) {
            Text(
                text = outputText,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}