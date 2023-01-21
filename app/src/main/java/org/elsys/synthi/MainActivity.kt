package org.elsys.synthi

import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.elsys.synthi.ui.theme.SynthiTheme

data class Audio(
    val uri: Uri,
    val track: Int,
    val title: String,
    val album: String,
    val artist: String
)
val audioList = mutableListOf<Audio>()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Log.i("Permission: ", "Granted")
                } else {
                    Log.i("Permission: ", "Denied")
                }
            }
        val permission = "android.permission.READ_EXTERNAL_STORAGE"
        val permissionGranted = checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        if (!permissionGranted) {
            requestPermissionLauncher.launch(permission)
        }

        val collection: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TRACK,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST
        )
        val sortOrder = "${MediaStore.Audio.Media.TRACK} ASC"

        val query = contentResolver.query(
            collection,
            projection,
            null,
            null,
            sortOrder
        )
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val trackColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TRACK)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val track = cursor.getInt(trackColumn)
                val title = cursor.getString(titleColumn)
                val album = cursor.getString(albumColumn)
                val artist = cursor.getString(artistColumn)
                val uri: Uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)

                audioList += Audio(uri, track, title, album, artist)
            }
        }

        setContent {
            SynthiTheme {
                LazyColumn {
                    items(audioList) { audio ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            elevation = 4.dp
                        ) {
                            Text(text = "${audio.artist} - ${audio.title}", modifier = Modifier.padding(4.dp))
                        }
                    }
                }
            }
        }
    }
}