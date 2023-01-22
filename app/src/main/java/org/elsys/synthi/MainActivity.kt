package org.elsys.synthi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.elsys.synthi.ui.SynthiApp
import org.elsys.synthi.ui.theme.SynthiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SynthiTheme {
                SynthiApp()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SynthiAppPreview() {
    SynthiTheme(darkTheme = true) {
        SynthiApp()
    }
}