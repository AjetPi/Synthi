package org.elsysbg.synthi.ui

import androidx.compose.animation.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import org.elsysbg.synthi.R

@Composable
fun SynthiTopBar(
    canNavigate: Boolean,
    onBackClick: () -> Unit,
    search: String?,
    onSearchClick: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = true,
        enter = slideInHorizontally() + fadeIn(),
        exit = slideOutHorizontally() + fadeOut()
    ) {
        TopAppBar(
            title = {
                if (search == null) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.h6
                    )
                } else {
                    OutlinedTextField(
                        value = search,
                        onValueChange = { onSearchClick(it) },
                        placeholder = {
                            Text(
                                modifier = Modifier.alpha(ContentAlpha.medium),
                                text = stringResource(id = R.string.search)
                            )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = { onSearchClick(null) })
                    )
                }
            },
            modifier = modifier,
            navigationIcon = {
                if (canNavigate) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            },
            actions = {
                IconButton(onClick = { onSearchClick(if (search == null) "" else null) }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search)
                    )
                }
            }
        )
    }
}