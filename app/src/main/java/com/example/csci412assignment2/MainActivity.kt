package com.example.csci412assignment2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.csci412assignment2.ui.theme.CSCI412Assignment2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CSCI412Assignment2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Nathaniel Schuster", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Student ID: 1354608", style = MaterialTheme.typography.bodyLarge)

        Button(onClick = {
            val intent = Intent(context, SecondActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Start Activity Explicitly")
        }

        Button(onClick = {
            val intent = Intent("com.example.SECOND_ACTIVITY")
            context.startActivity(intent)
        }) {
            Text("Start Activity Implicitly")
        }

        Button(onClick = {
            val intent = Intent(context, ThirdActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("View Image Activity")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CSCI412Assignment2Theme {
        MainScreen()
    }
}