package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.application_details

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.TrainingRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentTrainingDetailsBinding
import com.android.employeemanagmentsystem.utils.*
import kotlinx.coroutines.*
import java.io.File
import android.content.Intent
import android.graphics.*


import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.android.employeemanagmentsystem.data.models.responses.*
import kotlinx.android.synthetic.main.fragment_training_details.*
import java.io.FileOutputStream
import java.io.IOException


private const val TAG = "ApplicationDetails"

class TrainingApplicationDetails : Fragment(R.layout.fragment_training_details) {

    private lateinit var binding: FragmentTrainingDetailsBinding
    private lateinit var training: Training

    private lateinit var trainingRepository: TrainingRepository
    private lateinit var trainingApi: TrainingApi
    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao

    private lateinit var employee: Employee
    private lateinit var employeeDetails: EmployeeDetails
    private var file_name_path = ""

    lateinit var paint: Paint

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTrainingDetailsBinding.bind(view)

        training = arguments?.get("training") as Training
        trainingApi = TrainingApi.invoke()
        trainingRepository = TrainingRepository()
        authRepository = AuthRepository()
        employeeDao = AppDatabase.invoke(requireContext()).getEmployeeDao()




        setTrainingType(training.training_type)
        setValuesOnFields()


    }
    private fun createTabularPdf() {

        GlobalScope.launch {

            withContext(Dispatchers.IO){

                withContext(Dispatchers.Main){
                    binding.progressBar.isVisible = true
                }

                val myPdfDocument = PdfDocument()
                paint = Paint().apply {
                    textSize = 55F
                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                    color = ContextCompat.getColor(requireContext(), R.color.black)
                }

                setPage(myPdfDocument, 1);

                storePdfOffline(myPdfDocument)

                withContext(Dispatchers.Main){
                    binding.progressBar.isVisible = false
                }
            }


        }

    }


    private fun setPage(myPdfDocument: PdfDocument, pageNumber: Int) {

        var myPageInfo: PdfDocument.PageInfo = PdfDocument.PageInfo.Builder(1120, 792, pageNumber).create()
        var myPage = myPdfDocument.startPage(myPageInfo)
        var canvas = myPage.canvas

        paint.apply {
            textSize = 70f
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = 2F
        }


        canvas.drawText("Government Polytechnic Amravati", 800F, 100F, paint)

        paint.apply {
            textSize = 50f
            style = Paint.Style.STROKE
            strokeWidth = 2F
        }



        canvas.drawText("Sevarth-Id", 230f, 230f, paint)
        canvas.drawText("Training Name", 230f, 330f, paint)
        canvas.drawText("Duration ", 230f, 430f, paint)
        canvas.drawText("Start Date ", 230f, 530f, paint)
        canvas.drawText("End Date ", 230f, 630f, paint)
        canvas.drawText("Status", 230f, 730f, paint)
        canvas.drawText("Organized By", 230f, 830f, paint)



        canvas.drawText(employee.sevarth_id, 560f, 230f, paint)
        canvas.drawText(training.name, 560f, 330f, paint)
        canvas.drawText(getDurationInWeeks(training.duration), 560f, 430f, paint)
        canvas.drawText(training.start_date, 560f, 530f, paint)
        canvas.drawText(training.end_date, 560f, 630f, paint)
        canvas.drawText("Taining status ID", 560f, 730f, paint)
        canvas.drawText(training.organized_by, 560f, 830f, paint)



        myPdfDocument.finishPage(myPage)
    }


    private suspend fun storePdfOffline(myPdfDocument: PdfDocument) {

        withContext(Dispatchers.IO){
            employee = authRepository.getEmployee(employeeDao)
            val name = employee.sevarth_id + ".pdf"
            val file =
                File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/${name}")

            try {
                myPdfDocument.writeTo(FileOutputStream(file))
                withContext(Dispatchers.Main) {
                    requireContext().toast("Document Created Successfully")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    requireContext().toast(e.toString())
                    e.printStackTrace()
                }
            } finally {
                myPdfDocument.close()
            }
        }


    }

    private fun hasPermissions(context: Context, permissions: List<String>): Boolean {
        for (i in permissions.indices) {
            val permission = permissions[i]
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true;
    }


    fun viewPdfFile() {
        val file: File = File(requireContext().getExternalFilesDir(null)?.getAbsolutePath() + file_name_path)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.fromFile(file), "application/pdf")
        startActivity(intent)
    }


    @SuppressLint("SetTextI18n")
    private fun setValuesOnFields() {

        GlobalScope.launch {
            employee = authRepository.getEmployee(employeeDao)

            val isHod = employee.role_id.toInt() == ROLE_HOD

            binding.apply {

                withContext(Dispatchers.Main) {


                    if (isHod && training.training_status_id == TRAINING_APPLIED_TO_HOD) binding.LinearButtonLayout.isVisible =
                        true
                    else if ((!isHod && training.training_status_id == TRAINING_APPLIED_TO_PRINCIPLE) || (!isHod && training.training_status_id == TRAINING_APPROVED_BY_HOD)) binding.LinearButtonLayout.isVisible =
                        true

                    btnApply.setOnClickListener {

                        GlobalScope.launch {
                            if (isHod) {
                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = true
                                }

                                val response = trainingRepository.updateTrainingStatus(
                                    training.id,
                                    TRAINING_APPROVED_BY_HOD.toString(),
                                    trainingApi
                                )
                                withContext(Dispatchers.Main) {
                                    requireContext().toast(response.status)
                                    binding.progressBar.isVisible = false
                                }

                                delay(1000)

                                withContext(Dispatchers.Main) {
                                    findNavController().popBackStack()
                                }
                            } else {

                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = true
                                }

                                val response = trainingRepository.updateTrainingStatus(
                                    training.id,
                                    TRAINING_APPROVED_BY_PRINCIPAL.toString(),
                                    trainingApi
                                )
                                withContext(Dispatchers.Main) {

                                    binding.progressBar.isVisible = false

                                    requireContext().toast(response.status)
                                }

                                delay(1000)

                                withContext(Dispatchers.Main) {
                                    findNavController().popBackStack()
                                }
                            }

                        }


                    }

                    btnGeneratePdf.setOnClickListener {
                        createTabularPdf()
                    }

                    btnDecline.setOnClickListener {

                        GlobalScope.launch {
                            if (isHod) {

                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = true
                                }

                                val response = trainingRepository.updateTrainingStatus(
                                    training.id,
                                    TRAINING_DECLINE_BY_HOD.toString(),
                                    trainingApi
                                )

                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = false
                                    requireContext().toast(response.status)
                                }

                                delay(1000)

                                withContext(Dispatchers.Main) {
                                    findNavController().popBackStack()
                                }
                            } else {

                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = true
                                }
                                val response = trainingRepository.updateTrainingStatus(
                                    training.id,
                                    TRAINING_DECLINED_BY_PRINCIPLE.toString(),
                                    trainingApi
                                )
                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = false
                                    requireContext().toast(response.status)
                                }

                                delay(1000)

                                withContext(Dispatchers.Main) {
                                    findNavController().popBackStack()
                                }

                            }

                        }


                    }
                }

                withContext(Dispatchers.Main) {
                    tvTrainingName.text = training.name
                    tvOrganizationName.text =
                        if (training.org_name.isBlank()) training.org_name else training.organized_by

                    tvApplyLetter.text = training.apply_letter

                    tvDuration.text =
                        getDurationInWeeks(training.duration) + "  (" + training.start_date + " to " + training.end_date + ")"
                    etTrainingStatus.text = training.training_status_id.getTrainingStatusById()
                }

            }
        }


    }

    private fun setTrainingType(statusId: String) {
        GlobalScope.launch {
            val training: TrainingTypes = trainingRepository.getTrainingTypes(trainingApi, statusId)
            withContext(Dispatchers.Main) {
                binding.etTrainingType.text = training.name

            }
        }
    }

    private fun getDurationInWeeks(days: String): String {
        val day: Int = days.toInt()

        return if (day < 7) "$days days "
        else {
            val week = (day % 365) / 7
            "$week weeks"
        }
    }
}