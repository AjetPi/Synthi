package org.elsysbg.synthi.ui.view

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.elsysbg.synthi.data.model.Category

@Composable
fun SynthiBottomBar(
    bottomNavigationState: Boolean,
    isSelected: (String) -> Boolean,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = bottomNavigationState,
        enter = slideInHorizontally() + fadeIn(),
        exit = slideOutHorizontally() + fadeOut()
    ) {
        BottomNavigation(modifier = modifier.fillMaxWidth()) {
            Category.values().forEach { category ->
                BottomNavigationItem(
                    selected = isSelected(category.name),
                    onClick = { onCategoryClick(category.name) },
                    icon = {
                        Icon(imageVector = category.icon, contentDescription = category.name)
                    },
                    label = { Text(text = stringResource(id = category.id)) },
                    alwaysShowLabel = false
                )
            }
        }
    }
}