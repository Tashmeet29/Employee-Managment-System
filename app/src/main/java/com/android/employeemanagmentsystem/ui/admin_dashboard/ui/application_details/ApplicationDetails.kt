package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.application_details

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Employee
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.data.models.responses.TrainingTypes
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.TrainingRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.data.room.EmployeeDao
import com.android.employeemanagmentsystem.databinding.FragmentApplicationDetailsBinding
import com.android.employeemanagmentsystem.utils.*
import kotlinx.coroutines.*
import java.io.File
import android.content.Intent
import android.graphics.*


import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.net.Uri
import java.io.FileOutputStream
import java.io.IOException


private const val TAG = "ApplicationDetails"

class ApplicationDetails : Fragment(R.layout.fragment_application_details) {

    private lateinit var binding: FragmentApplicationDetailsBinding
    private lateinit var training: Training

    private lateinit var trainingRepository: TrainingRepository
    private lateinit var trainingApi: TrainingApi
    private lateinit var authRepository: AuthRepository
    private lateinit var employeeDao: EmployeeDao

    private lateinit var employee: Employee
    private var file_name_path = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentApplicationDetailsBinding.bind(view)

        training = arguments?.get("training") as Training
        trainingApi = TrainingApi.invoke()
        trainingRepository = TrainingRepository()
        authRepository = AuthRepository()
        employeeDao = AppDatabase.invoke(requireContext()).getEmployeeDao()




        setTrainingType(training.training_type)
        setValuesOnFields()

        generatePdf()
    }

    private fun generatePdf() {

        binding.apply {
            btnGeneratePdf.setOnClickListener {
                val builder = VmPolicy.Builder()
                StrictMode.setVmPolicy(builder.build())

                val permissions = listOf<String>(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)

                if (!hasPermissions(requireContext(), permissions)) {
//                    ActivityCompat.requestPermissions(
//                        requireActivity(),
//                        permissions.toArray() as Array<out String>,
//                        1
//                    )
                    requireContext().toast("missing permission")
                }

                val file: File = File(
                    requireContext().getExternalFilesDir(null)?.getAbsolutePath(),
                    "pdfsdcard_location"
                );
                if (!file.exists()) {
                    file.mkdir();
                    createpdf()
                }
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

    fun createpdf() {
        val bounds = Rect()
        val pageWidth = 300
        val pageheight = 470
        val pathHeight = 2
        val fileName = "mypdf"
        file_name_path = "/pdfsdcard_location/$fileName.pdf"
        val myPdfDocument = PdfDocument()
        val paint = Paint()
        val paint2 = Paint()
        val path = Path()
        val myPageInfo = PageInfo.Builder(pageWidth, pageheight, 1).create()
        val documentPage = myPdfDocument.startPage(myPageInfo)
        val canvas: Canvas = documentPage.canvas
        var y = 25 // x = 10,
        var x = 10
        paint.getTextBounds(
            "tv_title.getText().toString()",
            0,
            10,
            bounds
        )
        x = canvas.getWidth() / 2 - bounds.width() / 2
        canvas.drawText("tv_title.getText().toString()", x.toFloat(), y.toFloat(), paint)
        paint.getTextBounds(
            "tv_sub_title.getText().toString()",
            0,
            "tv_sub_title.getText().toString()".toString().length,
            bounds
        )
        x = canvas.getWidth() / 2 - bounds.width() / 2
        y += (paint.descent() - paint.ascent()).toInt()
        canvas.drawText("tv_sub_title.getText().toString()", x.toFloat(), y.toFloat(), paint)
        y += (paint.descent() - paint.ascent()).toInt()
        canvas.drawText("", x.toFloat(), y.toFloat(), paint)

//horizontal line
        path.lineTo(pageWidth.toFloat(), pathHeight.toFloat())
        paint2.setColor(Color.GRAY)
        paint2.setStyle(Paint.Style.STROKE)
        path.moveTo(x.toFloat(), y.toFloat())
        canvas.drawLine(0F, y.toFloat(), pageWidth.toFloat(), y.toFloat(), paint2)

////blank space
//        y += (paint.descent() - paint.ascent()).toInt()
//        canvas.drawText("", x, y, paint)
//        y += (paint.descent() - paint.ascent()).toInt()
//        x = 10
//        canvas.drawText(tv_location.getText().toString(), x, y, paint)
//        y += paint.descent() - paint.ascent()
//        x = 10
//        canvas.drawText(tv_city.getText().toString(), x, y, paint)
//
////blank space
//        y += paint.descent() - paint.ascent()
//        canvas.drawText("", x, y, paint)

//horizontal line
        path.lineTo(pageWidth.toFloat(), pathHeight.toFloat())
        paint2.setColor(Color.GRAY)
        paint2.setStyle(Paint.Style.STROKE)
        path.moveTo(x.toFloat(), y.toFloat())
        canvas.drawLine(0F, y.toFloat(), pageWidth.toFloat(), y.toFloat(), paint2)

//blank space
//        y += paint.descent() - paint.ascent()
//        canvas.drawText("", x, y, paint)
//        val res: Resources = resources
//        val bitmap = BitmapFactory.decodeResource(res, R.drawable.logo)
//        val b = Bitmap.createScaledBitmap(bitmap, 100, 50, false)
//        canvas.drawBitmap(b, x, y, paint)
//        y += 25
        canvas.drawText(getString(R.string.app_name), 120F, y.toFloat(), paint)
        myPdfDocument.finishPage(documentPage)
        val file: File = File(requireContext().getExternalFilesDir(null)?.getAbsolutePath() + file_name_path)
        try {
            myPdfDocument.writeTo(FileOutputStream(file))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        myPdfDocument.close()
        viewPdfFile()
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

            val isHod = employee.role_id.toInt() == 2

            binding.apply {

                withContext(Dispatchers.Main) {


                    if (isHod && training.training_status_id == APPLIED_TO_HOD) binding.LinearButtonLayout.isVisible =
                        true
                    else if ((!isHod && training.training_status_id == APPLIED_TO_PRINCIPLE) || (!isHod && training.training_status_id == APPROVED_BY_HOD)) binding.LinearButtonLayout.isVisible =
                        true

                    btnApply.setOnClickListener {

                        GlobalScope.launch {
                            if (isHod) {
                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = true
                                }

                                val response = trainingRepository.updateTrainingStatus(
                                    training.id,
                                    APPROVED_BY_HOD.toString(),
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
                                    APPROVED_BY_PRINCIPAL.toString(),
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

                    btnDecline.setOnClickListener {

                        GlobalScope.launch {
                            if (isHod) {

                                withContext(Dispatchers.Main) {
                                    binding.progressBar.isVisible = true
                                }

                                val response = trainingRepository.updateTrainingStatus(
                                    training.id,
                                    DECLINE_BY_HOD.toString(),
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
                                    DECLINED_BY_PRINCIPLE.toString(),
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