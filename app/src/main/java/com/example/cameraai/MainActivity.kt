package com.example.cameraai

import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Color.RED
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import com.example.cameraai.ui.theme.CameraAiTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!hasRequiredPermissions()){
            ActivityCompat.requestPermissions(this, CAMERAX_PERMISSION,0)
        }
        setContent {
            CameraAiTheme {
                val scaffoldState= rememberBottomSheetScaffoldState()
                val controller = remember {
                    LifecycleCameraController(applicationContext).apply {
                        setEnabledUseCases(
                            CameraController.IMAGE_CAPTURE or
                                    CameraController.VIDEO_CAPTURE
                        )
                    }
                }
                Scaffold(
                    floatingActionButton = {
                                           ExtendedFloatingActionButton(onClick = { /*TODO*/ },
                                               ){
                                               Text(text = "Press me")
                                           }
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    padding ->
                        CameraPreview(controller =controller,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(0.dp)
                            )
                    Text(text = "kalyan ram",modifier=Modifier.offset(20.dp,20.dp))
                    Text(text = "mannesh teja",modifier=Modifier.offset(20.dp,500.dp))

                }
            }
        }
    }
    private fun hasRequiredPermissions():Boolean{
        return CAMERAX_PERMISSION.all{
            ActivityCompat.checkSelfPermission(applicationContext, it)==PackageManager.PERMISSION_GRANTED
        }
    }
    companion object{
        private val CAMERAX_PERMISSION= arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO
        );
    }
}


@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
    )
}