package com.example.newest.pages

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.newest.data.instance.RetrofitInstance
import com.example.newest.data.services.NewsApi
import com.example.newest.domain.models.Article


@Composable
fun SearchPage() {

    val textFieldVal = remember { mutableStateOf("") }
    val result = remember { mutableStateOf<List<Article>?>(null) }
    val news = remember { mutableStateOf<List<Article>?>(null) }

    LaunchedEffect(textFieldVal.value) {
        result.value = fetch(textFieldVal.value)
    }

    LaunchedEffect(Unit) {
        news.value = fetchNewsByCategory("science")
    }

    Column(modifier = Modifier.padding(top = 20.dp)) {
        TextField(
            value = textFieldVal.value,
            onValueChange = { textFieldVal.value = it },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color.Black),
            label = { Text(text = "Search") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        result.value?.let { newsList ->
            if (newsList.isNotEmpty()) {
                GetItems(items = newsList)
            } else {
                GetItems(items = news.value?.toList() ?: emptyList())
            }
        }
    }
}


suspend fun fetch(q: String): List<Article> {
    val retrofit = RetrofitInstance.getRetrofit()
    val newsAPI = retrofit.create(NewsApi::class.java)
    val response = newsAPI.search(q)

    if (response.isSuccessful) {
        response.body()?.let {
            return it.articles
        }
    }

    return emptyList()

}


@Composable
fun GetItems(items: List<Article>) {

    LazyColumn {
        items(items) { news ->
            news.urlToImage?.let {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 15.dp)
                ) {
                    AndroidView(
                        factory = { context ->
                            ImageView(context).apply {
                                scaleType = ImageView.ScaleType.FIT_XY
                                Glide.with(context)
                                    .load(news.urlToImage)
                                    .into(this)
                                    .onLoadFailed(resources.getDrawable(R.drawable.no_photo))
                            }
                        },
                        modifier = Modifier
                            .size(width = 200.dp, height = 150.dp)
                            .padding(end = 10.dp)
                    )

                    Column {
                        news.title?.let { title ->
                            Text(
                                text = title,
                                color = colorResource(id = R.color.white),
                                maxLines = 2,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        news.description?.let { description ->
                            Text(
                                text = description,
                                color = colorResource(id = R.color.secondary),
                                maxLines = 3,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
//                        Text(text = "Source : ")
//                        Text(text = "${news.source}")
                    }
                }
            }

        }
    }
}

