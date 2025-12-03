package com.example.csci412assignment2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.csci412assignment2.ui.theme.CSCI412Assignment2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Custom permission name
        val customPermission = "${'$'}packageName.MSE412"
        var pendingIntent: Intent? = null

        // request the custom dangerous permission at runtime
        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                pendingIntent?.let { startActivity(it) }
            }
            pendingIntent = null
        }

        setContent {
            CSCI412Assignment2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        onStartSecond = {
                            val intent = Intent(this, SecondActivity::class.java)
                            if (ContextCompat.checkSelfPermission(this, customPermission) == PackageManager.PERMISSION_GRANTED) {
                                startActivity(intent)
                            } else {
                                pendingIntent = intent
                                permissionLauncher.launch(customPermission)
                            }
                        },
                        onStartImplicit = {
                            val intent = Intent("com.example.SECOND_ACTIVITY")
                            if (ContextCompat.checkSelfPermission(this, customPermission) == PackageManager.PERMISSION_GRANTED) {
                                startActivity(intent)
                            } else {
                                pendingIntent = intent
                                permissionLauncher.launch(customPermission)
                            }
                        },
                        onViewImage = {
                            val intent = Intent(this, ThirdActivity::class.java)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    onStartSecond: () -> Unit,
    onStartImplicit: () -> Unit,
    onViewImage: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Nathaniel Schuster", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Student ID: 1354608", style = MaterialTheme.typography.bodyLarge)

        Button(onClick = onStartSecond) {
            Text("Start Activity Explicitly")
        }

        Button(onClick = onStartImplicit) {
            Text("Start Activity Implicitly")
        }

        Button(onClick = onViewImage) {
            Text("View Image Activity")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CSCI412Assignment2Theme {
        MainScreen(onStartSecond = {}, onStartImplicit = {}, onViewImage = {})
    }
}