package org.elsysbg.synthi.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.elsysbg.synthi.data.model.Category

@Composable
fun SynthiAudioItem(
    category: Category,
    title: String,
    subtitle: String,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable { onItemClick() }
    ) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp)
            )
            Column {
                Text(
                    text = title,
                    modifier = Modifier.padding(bottom = 2.dp),
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}