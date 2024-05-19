package com.jesusdmedinac.shorturl.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cmp_shorturl.composeapp.generated.resources.Res
import cmp_shorturl.composeapp.generated.resources.ic_link
import com.jesusdmedinac.shorturl.presentation.screenmodel.ShortURLScreenModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

data object ShortURLScreen : Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: ShortURLScreenModel = getScreenModel()
        val state by screenModel.container.stateFlow.collectAsState()

        var urlToShorten by remember {
            mutableStateOf(TextFieldValue(""))
        }
        val onUrlToShortenChange: (TextFieldValue) -> Unit = {
            urlToShorten = it
        }
        Scaffold {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                        .weight(1f)
                ) {
                    items(state.shortenURLs) { shortenURL ->
                        ListItem(
                            leadingContent = {
                                Icon(
                                    painterResource(Res.drawable.ic_link),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            },
                            headlineContent = {
                                Text(shortenURL.url)
                            },
                            supportingContent = {
                                Text(shortenURL.shortURL)
                            }
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    OutlinedTextField(
                        value = urlToShorten,
                        onValueChange = onUrlToShortenChange,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = {
                        screenModel.shortenURL(url = urlToShorten.text)
                    }) {
                        Text(text = "Shorten")
                    }
                }
            }
        }
    }
}