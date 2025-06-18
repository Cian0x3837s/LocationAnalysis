package com.db.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.db.myapplication.model.UserSession
import com.db.myapplication.model.Venue
import com.db.myapplication.ui.theme.MyApplicationTheme
import com.db.myapplication.utils.Utils
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val context = LocalContext.current

                        Button(
                            onClick = {
                                trackCalculator(context)
                            }
                        ) {
                            Text("Load Data")
                        }

                    }
                }
            }
        }
    }
}

fun trackCalculator(context: Context) {
    val sessionList = Utils.readJsonFromAssets(context, "sessions.json")
    val venueList = Utils.readJsonFromAssets(context, "venues.json")

    val sessions = Json.decodeFromString<List<UserSession>>(sessionList)
    val venues = Json.decodeFromString<List<Venue>>(venueList)


    val  res = Utils.userDwellDurationCalculator(sessions,venues,2.0)


    res.users.forEach {  data ->
        Log.d("Result","current user -- ${data.userId}--- visited  ${data.venueTimes.size} venues")
        data.venueTimes.forEach { (venueId,dwell) ->
            Log.d("Result", "stay at  $venueId --- for $dwell")
        }
    }

}