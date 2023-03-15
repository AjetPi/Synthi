package org.elsysbg.synthi.component.activity

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import org.elsysbg.synthi.ui.view.SynthiApp
import org.elsysbg.synthi.ui.theme.SynthiTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        requestPermission()
        setContent {
            SynthiTheme {
                SynthiApp(viewModel = viewModel)
            }
        }
    }

    private fun requestPermission() {
        val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            val requestPermissionLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                    if (isGranted) viewModel.updateLibrary()
                }
            requestPermissionLauncher.launch(permission)
        }
        else {
            viewModel.updateLibrary()
        }
    }
}