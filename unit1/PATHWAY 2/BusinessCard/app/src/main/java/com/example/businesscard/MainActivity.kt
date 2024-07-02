package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BusinessCardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val name = "Phạm Anh Tuấn"
    val job = "Android Developer"
    val phone = "089817228"
    val email = "21020397@vnu.edu.vn"
    val address = "Hà Nội, Việt Nam"
    Surface(
        modifier = modifier.fillMaxSize(),
        color = colorResource(id = R.color.green_0)
    ) {
        BusinessCard(
            name = name,
            job = job,
            phone = phone,
            email = email,
            address = address
        )
    }
}

@Composable
fun BusinessCard(
    name: String = "",
    job: String = "",
    phone: String = "",
    email: String = "",
    address: String = ""
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, start = 64.dp, end = 64.dp, bottom = 32.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val image = painterResource(R.drawable.android_logo)
            Image(
                painter = image, contentDescription = "Android Logo",
                modifier = Modifier.size(120.dp),
            )
            Text(
                text = name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 30.sp
            )
            Text(text = job)
        }

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            val phoneNumberIcon = Icons.Filled.Phone
            Icon(
                imageVector = phoneNumberIcon,
                contentDescription = "Phone Number",
                modifier = Modifier.padding(end = 16.dp),
                tint = colorResource(id = R.color.green_1)
            )
            Text(text = phone)
        }

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            val emailIcon = Icons.Filled.Email
            Icon(
                imageVector = emailIcon,
                contentDescription = "Email",
                modifier = Modifier.padding(end = 16.dp),
                tint = colorResource(id = R.color.green_1)
            )
            Text(text = email)
        }

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            val addressIcon = Icons.Filled.Home
            Icon(
                imageVector = addressIcon,
                contentDescription = "Address",
                modifier = Modifier.padding(end = 16.dp),
                tint = colorResource(id = R.color.green_1)
            )
            Text(text = address)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BusinessCardTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding ->
            MainScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}