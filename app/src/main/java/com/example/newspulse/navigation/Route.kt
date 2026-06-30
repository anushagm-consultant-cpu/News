package com.example.newspulse.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    object Onboarding: Route

    @Serializable
    object Home: Route

    @Serializable
    object Login:Route{

}
    @Serializable
    object CreateAccount: Route
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
    object FontSizeScreen: Route

    @Serializable
    data class MyDetailedArticleScreen(
        val image: String?,
        val title: String,
        val description: String?,
        val content: String?,
        val fromSaved: Boolean = false
    ): Route
}
