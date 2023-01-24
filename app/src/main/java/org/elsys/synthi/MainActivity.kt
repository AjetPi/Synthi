package org.elsys.synthi

import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import org.elsys.synthi.data.Audio
import org.elsys.synthi.ui.SynthiApp
import org.elsys.synthi.ui.theme.SynthiTheme

class MainActivity : ComponentActivity() {
    private val library: MutableList<Audio> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkReadExternalStoragePermission()
        readExternalStorage()

        setContent {
            SynthiTheme {
                SynthiApp(library = library)
            }
        }
    }

    private fun checkReadExternalStoragePermission() {
        val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {}
            requestPermissionLauncher.launch(permission)
        }
    }

    private fun readExternalStorage() {
        val collection: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TRACK,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION
        )

        val query = contentResolver.query(
            collection,
            projection,
            null,
            null,
            null
        )
        query?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val trackColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TRACK)
            val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val albumColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val durationColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val track = it.getInt(trackColumn)
                val title = it.getString(titleColumn)
                val album = it.getString(albumColumn)
                val artist = it.getString(artistColumn)
                val duration = it.getInt(durationColumn)
                val uri: Uri = ContentUris.withAppendedId(collection, id)

                library.add(
                    Audio(
                        uri = uri,
                        title = title,
                        artist = artist,
                        album = album,
                        track = track,
                        duration = duration
                    )
                )
            }
        }
    }
}