package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.training_completion

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.data.models.responses.TrainingTypes
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.TrainingRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentTrainingCompletionBinding
import com.android.employeemanagmentsystem.ui.employee_dashboard.ui.applied_trainings.AppliedTrainingFragment
import com.android.employeemanagmentsystem.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.time.LocalDateTime

private const val TAG = "FragmentTrainingComplet"
class TrainingCompletionFragment: Fragment(R.layout.fragment_training_completion) {

    private lateinit var binding: FragmentTrainingCompletionBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao
    private lateinit var trainingRepo: TrainingRepository
    private lateinit var trainingApi: TrainingApi
    private val PICK_PDF_REQUEST = 112
    private var isPdfSelected = false
    private lateinit var byteArray: ByteArray

    private lateinit var training: Training

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTrainingCompletionBinding.bind(view)

        authRepository = AuthRepository()
        employeeDao = AppDatabase.invoke(requireContext()).getEmployeeDao()
        trainingRepo = TrainingRepository()
        trainingApi = TrainingApi()

        training = arguments?.get("training") as Training

        setTrainingType(training.training_type)

        binding.apply {

            linearTrainingCompletion.isVisible = training.training_status_id == APPROVED_BY_PRINCIPAL
            btnSubmit.isVisible = training.training_status_id == APPROVED_BY_PRINCIPAL

            tvCompletionCertificate.setOnClickListener {
                val intent = Intent()
                intent.type = "application/pdf"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST)
            }

            btnSubmit.setOnClickListener {
                GlobalScope.launch {

                    try {

                        val trainingResponse = trainingRepo.uploadTrainingCertificate(
                            training.id,
                            convertBytesToMultipart(),
                            trainingApi
                        )


                        withContext(Dispatchers.Main) {
                            requireContext().toast(trainingResponse.status)
                            findNavController().popBackStack()
                        }

                    } catch (e: Exception) {
                        requireContext().handleException(e)
                    }

                }

            }

            etTrainingName.text = training.name
            tvOrganizationName.text = if(training.org_name.isBlank()) training.org_name else training.organized_by

            tvApplyLetter.text = training.apply_letter

            etDuration.text = training.duration.getDurationInWeeks() +  "  (" + training.start_date + " to " + training.end_date + ")"
            etTrainingStatus.text = training.training_status_id.getTrainingStatusById()
        }


        Log.e(TAG, "onViewCreated: " + training.name )
    }

    private fun setTrainingType(statusId: String){
        GlobalScope.launch {
            val training: TrainingTypes = trainingRepo.getTrainingTypes(trainingApi, statusId)
            withContext(Dispatchers.Main){
                binding.etTrainingType.text = training.name

            }
        }
    }

    suspend fun convertUriToBytes(uri: Uri): ByteArray {

        return withContext(Dispatchers.Default) {

            try {

                val inputStream: InputStream? =
                    requireContext().contentResolver.openInputStream(uri)
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

    //handling the image chooser activity result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PDF_REQUEST && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {

            GlobalScope.launch {
                val filePath = data.data!!



                try {

                    withContext(Dispatchers.Main){
                        val fileName: String? = filePath.getOriginalFileName(requireContext())
                        binding.tvCompletionCertificate.text = "$fileName.pdf"
                    }

                    isPdfSelected = true
                    byteArray = convertUriToBytes(filePath)
                } catch (e: java.lang.Exception) {



                    withContext(Dispatchers.Main) { requireContext().toast(e.toString()) }
                }

            }

        }
    }



}