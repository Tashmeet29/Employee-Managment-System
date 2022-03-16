package com.android.employeemanagmentsystem.ui.training.training_completion

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.TrainingRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.ActivityTrainingCompletionBinding
import com.android.employeemanagmentsystem.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.time.LocalDateTime

private const val TAG = "TrainingCompletion"

class TrainingCompletion : AppCompatActivity() {

    private lateinit var binding: ActivityTrainingCompletionBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao
    private lateinit var trainingRepo: TrainingRepository
    private lateinit var trainingApi: TrainingApi
    private val PICK_PDF_REQUEST = 112
    private var isPdfSelected = false
    private lateinit var byteArray: ByteArray

    companion object {
        val INTENT_TRAINING_ID = "124"
    }

    private lateinit var trainingId: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTrainingCompletionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        trainingId = intent.getStringExtra(INTENT_TRAINING_ID) ?: "-1"

        authRepository = AuthRepository()
        employeeDao = AppDatabase.invoke(this).getEmployeeDao()
        trainingRepo = TrainingRepository()
        trainingApi = TrainingApi()

        binding.apply {
            tvPdf.setOnClickListener {
                val intent = Intent()
                intent.type = "application/pdf"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST)
            }

            btnSubmit.setOnClickListener {
                GlobalScope.launch {

                    try {

                        val trainingResponse = trainingRepo.uploadTrainingCertificate(
                            trainingId,
                            convertBytesToMultipart(),
                            trainingApi
                        )


                        withContext(Dispatchers.Main) { toast(trainingResponse.status) }

                    } catch (e: Exception) {
                        handleException(e)
                    }

                }

            }

        }
    }

    //handling the image chooser activity result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {

            GlobalScope.launch {
                val filePath = data.data!!



                try {

                    withContext(Dispatchers.Main) {
                        val fileName: String? =
                            filePath.getOriginalFileName(this@TrainingCompletion)
                        binding.tvPdf.text = "$fileName.pdf"
                    }

                    isPdfSelected = true
                    byteArray = convertUriToBytes(filePath)
                } catch (e: java.lang.Exception) {


                    withContext(Dispatchers.Main) { toast(e.toString()) }
                }

            }

        }
    }

    suspend fun convertUriToBytes(uri: Uri): ByteArray {

        return withContext(Dispatchers.Default) {

            try {

                val inputStream: InputStream? =
                    this@TrainingCompletion.contentResolver.openInputStream(uri)
                val byteBuff = ByteArrayOutputStream()
                val buff = ByteArray(1024)
                var len = 0

                while (inputStream!!.read(buff).also { len = it } != -1) {

                    byteBuff.write(buff, 0, len)
                }

                val imageBytesArray = byteBuff.toByteArray()
                if (imageBytesArray.isNotEmpty()) {
                    return@withContext imageBytesArray
                } else throw Exception("can not convert this pdf")


            } catch (exception: Exception) {
                Log.e(TAG, "convertBytesToMultipart: 107" + exception.toString())
                throw exception
            }

        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertBytesToMultipart(): MultipartBody.Part {

        val localDateTime = LocalDateTime.now()
        val fileName = "${localDateTime.hour + localDateTime.minute + localDateTime.second}.pdf"

        val filePart =
            MultipartBody.Part.createFormData(
                "training_certificate",
                fileName,
                byteArray.toPdfRequestBody()
            )

        return filePart


    }


}