package com.rach.swipestore

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.rach.swipestore.presentation.theme.SwipeStoreTheme
import com.rach.swipestore.presentation.ui.MyAppControl
import com.rach.swipestore.presentation.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwipeStoreTheme {

                val context = LocalContext.current
                val viewModel : MainViewModel = hiltViewModel()
                val networkState = viewModel.isConnected.collectAsStateWithLifecycle()

                LaunchedEffect(networkState.value) {
                    if (networkState.value){
                        delay(500)
                        secheduleWorkManager(context)
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    MyAppControl(viewModel = viewModel)
                }

            }
        }
    }
}

private fun secheduleWorkManager(context: Context){
    val workManager = WorkManager.getInstance(context)
    workManager.cancelAllWorkByTag("ProdWorkerTag")

    val workerRequest = OneTimeWorkRequestBuilder<ProductionWorker>()
        .addTag("ProdWorkerTag")
        .build()

    workManager.enqueue(workerRequest)
}


