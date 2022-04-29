package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.completed_trainings

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.TrainingTypes
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.TrainingRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentAddCompletedTrainingBinding
import com.android.employeemanagmentsystem.ui.employee_dashboard.ui.apply_training.TrainingTypesAdapter
import com.android.employeemanagmentsystem.utils.*
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.time.LocalDate
import java.time.LocalDateTime

private const val TAG = "CompletedTrainingsFragm"
class CompletedTrainingsFragment: Fragment(R.layout.fragment_add_completed_training) {

    private lateinit var binding: FragmentAddCompletedTrainingBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao
    private lateinit var trainingRepo: TrainingRepository
    private lateinit var trainingApi: TrainingApi
    private val PICK_PDF_REQUEST = 112
    private var isPdfSelected = false
    private lateinit var byteArray: ByteArray
    private var selectedType = "-1"


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddCompletedTrainingBinding.bind(view)


        handleClickOfApplyTrainingButton()

        authRepository = AuthRepository()
        employeeDao = AppDatabase.invoke(requireContext()).getEmployeeDao()
        trainingRepo = TrainingRepository()
        trainingApi = TrainingApi()

        getTrainingTypes()

    }

    fun getTrainingTypes() {
        GlobalScope.launch {
            val types: List<TrainingTypes> = trainingRepo.getTrainingTypes(trainingApi)

            val adapter = TrainingTypesAdapter(requireContext(), types)

            withContext(Dispatchers.Main) {
                binding.spinnerTrainingTypes.adapter = adapter

                binding.spinnerTrainingTypes.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            selectedType = types[p2].id
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {

                        }

                    }

            }
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleClickOfApplyTrainingButton() {

        binding.apply {


            btnSubmit.setOnClickListener {

                val training_name = etTrainingName.text.toString()
                val organization_name = etOrganizationName.text.toString()
                val organized_by = etOrganizedBy.text.toString()
                val training_duration = etDuration.text.toString()
                val training_start_date = binding.tvStartDate.text.toString()
                val training_end_date = binding.tvEndDate.text.toString()


                when {

                    training_name.isBlank() -> requireContext().toast("Please Enter Training Name")
                    organization_name.isBlank() -> requireContext().toast("Please Enter organization name")
                    training_duration.isBlank() -> requireContext().toast("Please Enter Training Duration")
                    training_start_date.isBlank() -> requireContext().toast("Please Select Start Date")
                    training_end_date.isBlank() -> requireContext().toast("Please Select End Name")
                    !isPdfSelected -> requireContext().toast("Please Select PDF to Upload")

                    else -> {

                        GlobalScope.launch {

                            try {

                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = true
                                }

                                //getting employee details from room database
                                val employee = authRepository.getEmployee(employeeDao)

                                val trainingResponse = trainingRepo.add_completed_training(
                                    sevarth_id = employee.sevarth_id,
                                    name = training_name,
                                    duration = training_duration,
                                    start_date = training_start_date,
                                    end_date = training_end_date,
                                    org_name = organization_name,
                                    organized_by = organized_by,
                                    org_id = employee.org_id.toString(),
                                    department_id = employee.dept_id.toString(),
                                    trainingApi = trainingApi,
                                    training_status_id = TRAINING_COMPLETED.toString(),
                                    applyPdf = convertBytesToMultipart(),
                                    training_type = selectedType
                                )


                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = false
                                    requireContext().toast(trainingResponse.status)
                                }

                                delay((1 * 1000).toLong())

                                withContext(Dispatchers.Main) {
                                    findNavController().navigate(R.id.nav_applied_trainings)
                                }

                            } catch (e: Exception) {

                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = false
                                }

                                requireContext().handleException(e)

                            }

                        }


                    }
                }

            }


            tvPdf.setOnClickListener {
                val intent = Intent()
                intent.type = "application/pdf"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST)
            }

            tvStartDate.setOnClickListener {
                var localDate = LocalDate.now()

                var listener = DatePickerDialog.OnDateSetListener { datePicker, year, month, date ->
                    tvStartDate.text = "$date-${month + 1}-$year"
                }

                DatePickerDialog(
                    requireContext(),
                    listener,
                    localDate.dayOfMonth,
                    localDate.monthValue,
                    localDate.year
                ).show()

            }

            tvEndDate.setOnClickListener {
                var localDate = LocalDate.now()

                var listener = DatePickerDialog.OnDateSetListener { datePicker, year, month, date ->

                    tvEndDate.text = "$date-${month + 1}-$year"
                }

                DatePickerDialog(
                    requireContext(),
                    listener,
                    localDate.dayOfMonth,
                    localDate.monthValue,
                    localDate.year
                ).show()

            }
        }

    }

    //handling the image chooser activity result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PDF_REQUEST && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {

            GlobalScope.launch {
                val filePath = data.data!!



                try {

                    withContext(Dispatchers.Main) {
                        val fileName: String? = filePath.getOriginalFileName(requireContext())
                        binding.tvPdf.text = "$fileName.pdf"
                    }

                    isPdfSelected = true
                    byteArray = convertUriToBytes(filePath)
                } catch (e: java.lang.Exception) {


                    withContext(Dispatchers.Main) { requireContext().toast(e.toString()) }
                }

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
        val fileName = "${localDateTime.hour}${localDateTime.minute}${localDateTime.second}.pdf"

        val filePart =
            MultipartBody.Part.createFormData(
                "training_application",
                fileName,
                byteArray.toPdfRequestBody()
            )

        return filePart


    }
}