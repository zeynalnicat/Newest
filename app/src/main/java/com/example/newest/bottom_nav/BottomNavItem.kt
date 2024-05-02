package com.example.newest.bottom_nav

import com.example.movie.R

sealed class BottomNavItem(val route: String, val icon: Int, val label: String) {
    object Home : BottomNavItem("home", R.drawable.icon_home, "Home")
    object Search : BottomNavItem("search", R.drawable.icon_search, "Search")
    object WatchList : BottomNavItem("watchlist", R.drawable.icon_wishlist, "Watch List")
}