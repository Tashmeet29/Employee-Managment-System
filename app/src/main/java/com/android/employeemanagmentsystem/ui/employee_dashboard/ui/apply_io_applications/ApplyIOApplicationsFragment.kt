package com.android.employeemanagmentsystem.ui.employee_dashboard.ui.apply_io_applications

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.network.apis.IOApplicationApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.IOApplicationRepository
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentIoApplyApplicationsBinding
import com.android.employeemanagmentsystem.ui.employee_dashboard.ui.apply_training.TAG
import com.android.employeemanagmentsystem.utils.handleException
import com.android.employeemanagmentsystem.utils.then
import com.android.employeemanagmentsystem.utils.toPdfRequestBody
import com.android.employeemanagmentsystem.utils.toast
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.time.LocalDateTime

private const val TAG = "ApplyIOApplicationsFrag"
class ApplyIOApplicationsFragment: Fragment(R.layout.fragment_io_apply_applications) {

    private lateinit var binding: FragmentIoApplyApplicationsBinding
    private lateinit var ioApplicationRepository: IOApplicationRepository
    private lateinit var ioApplicationApi: IOApplicationApi
    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao

    private val PICK_PDF_REQUEST = 112
    private var isPdfSelected = false
    private lateinit var byteArray: ByteArray

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentIoApplyApplicationsBinding.bind(view)

        applyApplications()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun applyApplications() {


        binding.apply {

            rbInward.isChecked = true

            btnSubmit.setOnClickListener {
                val title = etIoTitle.text.toString()
                val desc = etDesc.text.toString()
                val date = tvDate.text.toString()
                val pdf = tvPdf.text.toString()



                when {
                    title.isEmpty()-> requireContext().toast("Enter Title")
                    desc.isEmpty()-> requireContext().toast("Enter Description")
                    date.isEmpty()-> requireContext().toast("Enter Date")
                    pdf.isEmpty()-> requireContext().toast("Select PDF please")
                    !isPdfSelected -> requireContext().toast("Please Select PDF to Upload")

                    else -> {
s
                        GlobalScope.launch {

                            try {

                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = true
                                }

                                //getting employee details from room database
                                val employee = authRepository.getEmployee(employeeDao)

                                /*
                                *  sevarth_id: String,
        title: String,
        desc: String,
        date: String,
        end_date: String,
        org_id: String,
        department_id: String,
        applyPdf: MultipartBody.Part,
        iOApplicationApi: IOApplicationApi*/

                                val trainingResponse = ioApplicationRepository.applyIOApplication(
                                    sevarth_id = employee.sevarth_id,
                                    title = title,
                                    desc = desc,
                                    date = date,

                                    applyPdf = convertBytesToMultipart(),

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

            rbInward.setOnClickListener {
                rbInward.isChecked = true
                rbOutward.isChecked = false

            }
            rbOutward.setOnClickListener {
                rbInward.isChecked = false
                rbOutward.isChecked = true

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertBytesToMultipart(): MultipartBody.Part {

        val localDateTime = LocalDateTime.now()
        val fileName = "${localDateTime.hour + localDateTime.minute + localDateTime.second}.pdf"

        val filePart =
            MultipartBody.Part.createFormData(
                "training_application",
                fileName,
                byteArray.toPdfRequestBody()
            )

        return filePart


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

}