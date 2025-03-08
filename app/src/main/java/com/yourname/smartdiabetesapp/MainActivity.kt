package com.yourname.smartdiabetesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier.fillMaxSize()) {
                SplashScreen()
                DashboardScreen()
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Smart Diabetes App", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Blue)
        Spacer(modifier = Modifier.height(10.dp))
        Text("Created by: Alqamah Raihan,M.Sc(microbiology)", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.DarkGray)
    }
}

@Composable
fun DashboardScreen() {
    var bloodSugarLevel by remember { mutableStateOf(0) }
    var insulinDose by remember { mutableStateOf("Enter blood sugar level to calculate insulin dose") }
    var userInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Smart Diabetes App",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E88E5)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Enter Blood Sugar Level (mg/dL)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            val enteredValue = userInput.toIntOrNull()
            if (enteredValue != null) {
                bloodSugarLevel = enteredValue
                insulinDose = suggestInsulinDose(enteredValue)
            }
        }) {
            Text("Calculate Insulin Dose")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Blood Sugar Level", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("$bloodSugarLevel mg/dL", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.Red)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEB3B)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(insulinDose, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3E2723))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { /* Navigate to AboutScreen */ }) {
            Text("About")
        }
    }
}

fun suggestInsulinDose(glucose: Int): String {
    return when {
        glucose < 70 -> "Low Sugar! Eat something sweet 🍬"
        glucose in 70..140 -> "Normal Range ✅ No insulin needed."
        glucose in 141..180 -> "Suggested: 18 Units of Insulin 💉"
        else -> {
            val extraUnits = max(0, (glucose - 180) / 20 * 2)
            "High Sugar! Consider ${20 + extraUnits} Units of Insulin 💉"
        }
    }
}
