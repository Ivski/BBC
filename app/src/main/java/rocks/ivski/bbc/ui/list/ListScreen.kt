package rocks.ivski.bbc.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import org.koin.androidx.compose.getViewModel
import rocks.ivski.bbc.data.dto.Character

@ExperimentalCoilApi
@Composable
fun ListScreen() {
    val viewModel = getViewModel<ListViewModel>()
    val screenState by viewModel.state.collectAsState()

    when (screenState) {
        is ListViewModel.ListState.GenericError -> TODO()
        is ListViewModel.ListState.Loading -> {
            LoadingSpinner()
            viewModel.handleIntent(ListViewModel.ListIntent.LoadList)
        }
        is ListViewModel.ListState.NetworkError -> TODO()
        is ListViewModel.ListState.Ready -> DisplayList(items = (screenState as ListViewModel.ListState.Ready).characters)
        is ListViewModel.ListState.CharacterSelected -> TODO()
    }
}

@ExperimentalCoilApi
@Composable
private fun DisplayList(items: List<Character>) {
    LazyColumn {
        items(
            items, itemContent = {
                ListItem(item = it)
            }
        )
    }
}

@ExperimentalCoilApi
@Composable
private fun ListItem(item: Character) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            if (item.imageUrl != null) {
                Image(
                    painter = rememberImagePainter(item.imageUrl),
                    contentDescription = "image",
                    modifier = Modifier.size(72.dp)
                )
            }
            Text(
                text = item.name,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(8.dp, 0.dp, 0.dp, 0.dp)
            )
        }
    }
}

@Composable
private fun LoadingSpinner() {
    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        CircularProgressIndicator(modifier = Modifier.size(128.dp).align(Center))
    }
}