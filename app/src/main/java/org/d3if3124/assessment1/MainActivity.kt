package org.d3if3124.assessment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.d3if3124.assessment1.navigation.SetupNavGraph
import org.d3if3124.assessment1.ui.theme.Assessment1Theme
import org.d3if3124.assessment1.util.SettingsDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dataStore = SettingsDataStore(LocalContext.current)
            val showDarkMode by dataStore.darkModeFlow.collectAsState(false)
            Assessment1Theme(
                darkTheme = showDarkMode
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupNavGraph(showDarkMode = showDarkMode, dataStore = dataStore)
                }
            }
        }
    }
}

