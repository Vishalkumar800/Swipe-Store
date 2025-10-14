package com.rach.swipestore.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.rach.swipestore.ProductionWorker

@Composable
fun PaymentScreen() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Payment Screen")
    }

}


@Composable
fun SettingsScreen() {

    val context = LocalContext.current
    val owner = LocalLifecycleOwner.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Settings Screen")

        Button(onClick = {

            val workManager = WorkManager.getInstance(context)
            workManager.cancelAllWorkByTag("TestWorkerTag")
            val workerRequest =
                OneTimeWorkRequestBuilder<ProductionWorker>().addTag("TestWorkerTag").build()
            workManager.enqueue(workerRequest)

            workManager.getWorkInfoByIdLiveData(workerRequest.id)
                .observe(owner) { workInfo ->
                    when (workInfo?.state) {
                        WorkInfo.State.ENQUEUED -> Log.d("tomy", "Work enqueued")
                        WorkInfo.State.RUNNING -> Log.d("tomy", "Work running")
                        WorkInfo.State.SUCCEEDED -> Log.d("tomy", "Work succeeded")
                        WorkInfo.State.FAILED -> Log.d(
                            "tomy",
                            "Work failed: ${workInfo.outputData}"
                        )

                        else -> Log.d("tomy", "Work state: ${workInfo?.state}")
                    }
                }
        }) {
            Text("Hip")
        }

    }
}