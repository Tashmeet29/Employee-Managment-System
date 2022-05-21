package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.apply_training

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
import com.android.employeemanagmentsystem.databinding.FragmentApplyTrainingAdminBinding
import com.android.employeemanagmentsystem.utils.*
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

private const val TAG = "ApplyTrainingFragment"

class ApplyTrainingFragment : Fragment(R.layout.fragment_apply_training_admin) {

    private lateinit var binding: FragmentApplyTrainingAdminBinding
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


        binding = FragmentApplyTrainingAdminBinding.bind(view)

        handleClickOfApplyTrainingButton()

        authRepository = AuthRepository()
        employeeDao = AppDatabase.invoke(requireContext()).getEmployeeDao()
        trainingRepo = TrainingRepository()
        trainingApi = TrainingApi()

        getTrainingTypes()
    }

    public fun getTrainingTypes() {
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
                val training_start_date = binding.tvStartDate.text.toString()
                val training_end_date = binding.tvEndDate.text.toString()



                when {

                    training_name.isBlank() -> requireContext().toast("Please Enter Training Name")
                    organization_name.isBlank() -> requireContext().toast("Please Enter organization name")
                    training_start_date.isBlank() -> requireContext().toast("Please Select Start Date")
                    training_end_date.isBlank() -> requireContext().toast("Please Select End Name")
                    !isPdfSelected -> requireContext().toast("Please Select PDF to Upload")
                    getDurationInDays(training_start_date, training_end_date).toInt() == 0 ->  requireContext().toast("Start date and end Date should be different")
                    getDurationInDays(training_start_date, training_end_date).toInt() < 0 ->  requireContext().toast("Start date must be smaller than end date")


                    else -> {

                        GlobalScope.launch {

                            try {

                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = true
                                }

                                //getting employee details from room database
                                val employee = authRepository.getEmployee(employeeDao)

                                val trainingResponse = trainingRepo.applyTraining(
                                    sevarth_id = employee.sevarth_id,
                                    name = training_name,
                                    duration = getDurationInDays(
                                        training_start_date,
                                        training_end_date
                                    ),
                                    start_date = training_start_date,
                                    end_date = training_end_date,
                                    org_name = organization_name,
                                    organized_by = organized_by,
                                    org_id = employee.org_id.toString(),
                                    department_id = employee.dept_id.toString(),
                                    trainingApi = trainingApi,
                                    training_status_id = (employee.role_id.toInt() == ROLE_HOD) then TRAINING_APPLIED_TO_PRINCIPLE.toString()
                                        ?: "-1",
                                    applyPdf = convertBytesToMultipart(),
                                    training_type = selectedType
                                )


                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = false
                                    requireContext().toast(trainingResponse.status)
                                }

                                delay((1 * 1000).toLong())

                                withContext(Dispatchers.Main) {
                                    findNavController().popBackStack(R.id.nav_apply_training, true)
                                    findNavController().navigate(R.id.nav_training_applications)
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
                val calendar: Calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                var listener = DatePickerDialog.OnDateSetListener { datePicker, year, month, date ->
                    tvStartDate.text = "$date-${month + 1}-$year"
                }

                DatePickerDialog(
                    requireContext(),
                    listener,
                    year,
                    month,
                    day
                ).show()

            }

            tvEndDate.setOnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                var listener = DatePickerDialog.OnDateSetListener { datePicker, year, month, date ->

                    tvEndDate.text = "$date-${month + 1}-$year"
                }

                DatePickerDialog(
                    requireContext(),
                    listener,
                    year,
                    month,
                    day
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

    private fun getDurationInDays(startDate: String, endDate: String): String{


        val date2: Date
        val date1: Date

        var newStartDate = startDate.replace("-", "/")
        var newEndDate = endDate.replace("-", "/")

        Log.e(TAG, "getDurationInDays: $newStartDate")

        val dates = SimpleDateFormat("dd/MM/yyyy")
        date1 = dates.parse(newStartDate)
        date2 = dates.parse(newEndDate)
        val difference: Long = kotlin.math.abs(date1.time - date2.time)
        val differenceIsNeg: Boolean = (date2.time - date1.time) < 0
        var differenceDates = difference / (24 * 60 * 60 * 1000)


        Log.e(TAG, "getDurationInDays: $difference", )
        Log.e(TAG, "getDurationInDays: $differenceIsNeg", )
        Log.e(TAG, "getDurationInDays: $differenceDates", )

        if(differenceIsNeg){
            differenceDates *= -1
        }

        Log.e(TAG, "getDurationInDays: $differenceDates", )

        return differenceDates.toString()
    }
}