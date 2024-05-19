package com.jesusdmedinac.shorturl.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cmp_shorturl.composeapp.generated.resources.Res
import cmp_shorturl.composeapp.generated.resources.ic_link
import com.jesusdmedinac.shorturl.presentation.screenmodel.ShortURLScreenModel
import com.jesusdmedinac.shorturl.presentation.screenmodel.ShortenURL
import com.jesusdmedinac.shorturl.utils.OpenBrowserBy
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

data object ShortURLScreen : Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: ShortURLScreenModel = getScreenModel()

        LaunchedEffect(Unit) {
            screenModel.loadShortenURLs()
        }

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
                ShortenedURLLazyColumn(
                    screenModel = screenModel,
                    modifier = Modifier.fillMaxWidth()
                        .weight(1f)
                )
                URLShortenerTextField(
                    urlToShorten = urlToShorten,
                    onUrlToShortenChange = onUrlToShortenChange,
                    onShortenClick = { url ->
                        screenModel.shortenURL(url = url)
                        onUrlToShortenChange(TextFieldValue(""))
                    },
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
            }
        }
    }

    @Composable
    private fun ShortenedURLLazyColumn(
        screenModel: ShortURLScreenModel,
        modifier: Modifier = Modifier
    ) {
        val state by screenModel.container.stateFlow.collectAsState()
        LazyColumn(
            modifier = modifier
        ) {
            items(state.shortenURLs) { shortenURL ->
                ShortenedURLListItem(shortenURL)
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun ShortenedURLListItem(shortenURL: ShortenURL) {
        ListItem(
            leadingContent = {
                Icon(
                    painterResource(Res.drawable.ic_link),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            },
            headlineContent = {
                Text(shortenURL.long)
            },
            supportingContent = {
                Text(shortenURL.short)
            },
            trailingContent = {
                var expanded by remember { mutableStateOf(false) }
                IconButton(onClick = {
                    expanded = true
                }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    },
                ) {
                    OpenDropdownMenuItem(
                        shortenURL = shortenURL,
                        onClick = {
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Share") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Share,
                                contentDescription = null
                            )
                        },
                        onClick = {}
                    )
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = null
                            )
                        },
                        onClick = {}
                    )
                }
            }
        )
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun OpenDropdownMenuItem(
        shortenURL: ShortenURL,
        onClick: () -> Unit = {}
    ) {
        var shouldOpenLink by remember { mutableStateOf(false) }
        if (shouldOpenLink) {
            OpenBrowserBy(shortenURL.short)
        }
        DropdownMenuItem(
            text = { Text("Open") },
            leadingIcon = {
                Icon(
                    painterResource(Res.drawable.ic_link),
                    contentDescription = null
                )
            },
            onClick = {
                shouldOpenLink = true
                onClick()
            }
        )
    }

    @Composable
    private fun URLShortenerTextField(
        urlToShorten: TextFieldValue,
        onUrlToShortenChange: (TextFieldValue) -> Unit,
        onShortenClick: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier
        ) {
            OutlinedTextField(
                value = urlToShorten,
                onValueChange = onUrlToShortenChange,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = {
                onShortenClick(urlToShorten.text)
            }) {
                Text(text = "Shorten")
            }
        }
    }
}