package com.example.newest.pages

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.newest.data.instance.RetrofitInstance
import com.example.newest.data.services.NewsApi
import com.example.newest.domain.models.Article


@Composable
fun HomePage() {

    val news = remember {
        mutableStateOf(listOf<Article>())
    }

    val trNews = remember {
        mutableStateOf(listOf<Article>())
    }



    LaunchedEffect(Unit) {
        news.value = fetchNews("us")
        trNews.value = fetchNewsByCategory()
    }

    Column(
        modifier = Modifier.padding(15.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            Image(
                painter = painterResource(id = R.drawable.newest_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(width = 48.dp, height = 48.dp)
            )

            Text(
                text = "Newest",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
        }


        Spacer(modifier = Modifier.height(24.dp))

        LazyRow {
            items(news.value) { item ->

                item.urlToImage?.let { img ->
                    Box(
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .size(width = 300.dp, height = 250.dp)
                    ) {

                        AndroidView(
                            factory = { context ->
                                ImageView(context).apply {
                                    scaleType = ImageView.ScaleType.FIT_XY
                                    Glide.with(context)
                                        .load(img)
                                        .into(this)
                                }
                            },
                            modifier = Modifier.size(width = 300.dp, height = 250.dp)
                        )



                        item.title?.let { title ->
                            Text(
                                text = title,
                                color = colorResource(id = R.color.white),
                                maxLines = 2,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .background(Color(color = 0x70000000))
                                    .padding(10.dp)
                            )
                        }
                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn {
            items(trNews.value) { item ->

                item.urlToImage?.let { img ->
                    Row(
                        modifier = Modifier.padding(bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AndroidView(
                            factory = { context ->
                                ImageView(context).apply {
                                    scaleType = ImageView.ScaleType.FIT_XY
                                    Glide.with(context)
                                        .load(img)
                                        .into(this)
                                }
                            },
                            modifier = Modifier
                                .size(width = 240.dp, height = 200.dp)
                                .padding(end = 10.dp)
                        )


                        Column {
                            item.title?.let { title ->
                                Text(
                                    text = title,
                                    color = colorResource(id = R.color.white),
                                    maxLines = 2,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            item.description?.let {
                                Text(
                                    text = it, color = colorResource(id = R.color.secondary),
                                    maxLines = 3,
                                    fontSize = 12.sp,
                                )
                            }

                        }


                    }
                }
            }
        }


    }
}

suspend fun fetchNews(country: String, size: Int = 20 , page:Int = 1): List<Article> {
    val retrofit = RetrofitInstance.getRetrofit()
    val newsApi = retrofit.create(NewsApi::class.java)
    val news = newsApi.getTop(country = country, size = size, page = 1)

    if (news.isSuccessful) {
        news.body()?.let {
            return it.articles
        }
    }
    return emptyList()
}


suspend fun fetchNewsByCategory(category:String = "general"): List<Article> {
    val retrofit = RetrofitInstance.getRetrofit()
    val newsApi = retrofit.create(NewsApi::class.java)
    val news = newsApi.getByCategory(category)

    if (news.isSuccessful) {
        news.body()?.let {
            return it.articles
        }
    }
    return emptyList()
}

