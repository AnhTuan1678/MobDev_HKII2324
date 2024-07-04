package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    class Art(
        val name: String? = null,
        val artist: String? = null,
        val year: Int? = null,
        val image: Painter
    )

    val arts = ArrayList<Art>()
    arts.add(
        Art(
            "The Starry Night",
            "Vincent van Gogh",
            1889,
            painterResource(R.drawable.the_starry_night)
        )
    )
    arts.add(
        Art(
            "Monalisa",
            "Leonardo da Vinci",
            1503,
            painterResource(R.drawable.mona_lisa)
        )
    )
    arts.add(
        Art(
            image = painterResource(R.drawable.nh_chp)
        )
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var i by remember { mutableStateOf(0) }
        var art = arts[if (i % arts.size < 0) i % arts.size + arts.size else i % arts.size]

        val shape = RoundedCornerShape(8.dp)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .padding(15.dp)
        ) {
            ImageBox(image = art.image)
        }

        TitleBox(name = art.name ?: "", artist = art.artist ?: "", year = art.year ?: 0)

        Row(
            modifier = Modifier
                .padding(30.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { i-- },
                enabled = i > 0,
                modifier = Modifier.size(width = 125.dp, height = 50.dp)
            ) {
                Text("Previous")
            }
            Button(
                onClick = { i++ },
                enabled = i < arts.size - 1,
                modifier = Modifier.size(width = 125.dp, height = 50.dp)
            ) {
                Text("Next")
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
    }
}

@Composable
fun ImageBox(image: Painter) {
    val shape = RoundedCornerShape(8.dp)
    Box(
        modifier = Modifier
            .padding(10.dp)
            .shadow(8.dp, shape, clip = false) // Tạo bóng đổ ra ngoài
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .aspectRatio(image.intrinsicSize.width / image.intrinsicSize.height)
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize() // Lấp đầy toàn bộ không gian của Box
                .padding(8.dp),
        )
    }
}

@Composable
fun TitleBox(name: String, artist: String, year: Int) {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 32.sp, fontWeight = FontWeight.W200)) {
            append("${name ?: "<Không có tên>"}\n")
        }
        withStyle(style = SpanStyle(fontWeight = FontWeight.W500)) {
            append("${artist ?: "<Không có tác giả>"}")
        }
        withStyle(style = SpanStyle(fontWeight = FontWeight.W300)) {
            append(" (${year ?: "<Không có năm>"})")
        }
    }

    Box(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(15.dp),
            maxLines = 3
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        MainScreen()
    }
}