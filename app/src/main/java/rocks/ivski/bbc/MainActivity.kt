package rocks.ivski.bbc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.inject
import rocks.ivski.bbc.navigation.Navigator
import rocks.ivski.bbc.ui.list.ListScreen
import rocks.ivski.bbc.ui.theme.BBCTheme

class MainActivity : ComponentActivity() {

    @ExperimentalCoilApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navigator: Navigator by inject()
            BBCTheme {
                Scaffold {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        NavigationComponent(navController = navController, navigator)
                    }
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun NavigationComponent(navController: NavHostController, navigator: Navigator) {
    LaunchedEffect("navigation") {
        navigator.sharedFlow.onEach {
            navController.navigate(it.label)
        }.launchIn(this)
    }
    NavHost(navController = navController, startDestination = Navigator.NavTarget.List.label) {

        composable(Navigator.NavTarget.List.label) {
            ListScreen()
        }
        composable(Navigator.NavTarget.Detail.label) {

        }
    }
}