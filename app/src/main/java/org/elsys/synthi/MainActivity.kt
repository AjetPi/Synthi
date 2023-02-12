package org.elsys.synthi

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.elsys.synthi.ui.SynthiApp
import org.elsys.synthi.ui.SynthiViewModel
import org.elsys.synthi.ui.theme.SynthiTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SynthiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermission()

        setContent {
            SynthiTheme {
                SynthiApp()
            }
        }
    }

    private fun requestPermission() {
        val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) viewModel.setLibrary()
            }
            requestPermissionLauncher.launch(permission)
        }
    }
}