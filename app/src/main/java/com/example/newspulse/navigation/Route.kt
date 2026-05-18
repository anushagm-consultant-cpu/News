package com.example.newspulse.navigation

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
}
