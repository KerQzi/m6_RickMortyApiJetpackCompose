package kg.geeks.rickmortyapicompose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.paging.*

@Composable
fun LoadStateView(
    loadState: CombinedLoadStates,
    onRetry: () -> Unit
) {
    when (val refreshState = loadState.source.refresh) {
        is LoadState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is LoadState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Ошибка: ${refreshState.error.localizedMessage}")
                    Button(onClick = onRetry) {
                        Text("Повторить")
                    }
                }
            }
        }

        else -> {
            val appendError = loadState.append as? LoadState.Error
            if (appendError != null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Ошибка подгрузки: ${appendError.error.localizedMessage}")
                    Button(onClick = onRetry) {
                        Text("Повторить")
                    }
                }
            }
        }
    }
}
