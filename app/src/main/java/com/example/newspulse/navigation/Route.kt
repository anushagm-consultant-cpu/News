package com.example.newspulse.navigation

import android.R
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    object Home: Route

    @Serializable
    object Explore: Route

    @Serializable
    object Save: Route

    @Serializable
    object Profile: Route
    @Serializable
    object MyreadingHistroy: Route
    @Serializable
    object MyInterestAndPreference: Route
    @Serializable
    object MyrNotificationScreen: Route
    @Serializable
    object MyAppThemeScreen: Route
    @Serializable
    object MyHelpScreen: Route
    @Serializable
    data class  MyDetailedArticleScreen(
        val image: Int,
        val title: String ,
        val description: String,
        val content: String
    ):Route
}
