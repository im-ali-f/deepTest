package com.example.deeptest

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.deeptest.ui.theme.DeepTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DeepTestTheme {
                //call specified url
                var ctx = LocalContext.current


                //deeplink
                var navState = rememberNavController()
                NavHost(navController = navState, startDestination = "home") {

                    composable(route="home"){
                        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Button(onClick = {
                                calURL(ctx)
                            }) {
                                Text(text = "Call URL")
                            }
                        }
                    }
                    composable(
                        route = "deepLinkDoNotNavigateDirectly",
                        deepLinks = listOf(
                            navDeepLink {
                                uriPattern =
                                    "https://ozonecowork.ir/openapp2?Authority={Authority}&Status={Status}"
                                action = Intent.ACTION_VIEW
                            }
                        ),
                        arguments = listOf(
                            navArgument("Authority") {
                                type = NavType.StringType
                                defaultValue = ""
                            },
                            navArgument("Status") {
                                type = NavType.StringType
                                defaultValue = "NOK"
                            }
                        )
                    ) { entry ->
                        Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center){
                            Text(text = "authority : " + entry.arguments?.get("Authority").toString())
                            Text(text = "status : " + entry.arguments?.get("Status").toString())
                        }
                        

                    }
                }

            }
        }
    }
}

fun calURL(ctx:Context){
    val urlIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://ozonecowork.ir/openapp2?Authority=deepLinkWorked&Status=OK") // static data ! change it
    )
    ctx.startActivity(urlIntent)
}