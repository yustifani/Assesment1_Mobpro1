package org.d3if3124.assessment1.navigation

import org.d3if3124.assessment1.screen.KEY_ID_ORDER

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_ORDER}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}