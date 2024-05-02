package com.example.newest

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movie.R
import com.example.newest.bottom_nav.BottomNavItem
import com.example.newest.pages.HomePage
import com.example.newest.pages.SearchPage
import com.example.newest.ui.theme.MovieTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.primary)
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val listBottom =
        listOf(BottomNavItem.Home, BottomNavItem.Search, BottomNavItem.WatchList)

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.primary),
        modifier = Modifier.height(height = 80.dp),
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                listBottom.forEach { item ->
                    BottomNavigationItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = item.icon),
                                contentDescription = null,
                                modifier = Modifier.size(width = 32.dp, height = 32.dp),
                                tint = if (currentRoute == item.route) colorResource(id = R.color.blue) else colorResource(
                                    id = R.color.secondary
                                )
                            )
                        },
                        label = {
                            Text(
                                item.label,
                                fontSize = 16.sp,
                                color = colorResource(id = R.color.secondary),
                            )
                        },
                        selectedContentColor = Color.Gray,
                        unselectedContentColor = Color.Gray
                    )
                }
            }
        }
    )
}


@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            HomePage()
        }
        composable(BottomNavItem.Search.route) {
            SearchPage()
        }
        composable(BottomNavItem.WatchList.route) { }
    }
}

@Composable
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun MainScreen() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) },
        backgroundColor = colorResource(id = R.color.primary)
    ) {
        NavigationHost(navController = navController)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun GreetingPreview() {
    MovieTheme {
        MainScreen()
    }
}