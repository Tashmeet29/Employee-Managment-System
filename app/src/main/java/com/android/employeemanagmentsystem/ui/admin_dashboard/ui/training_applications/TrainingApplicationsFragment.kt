package com.android.employeemanagmentsystem.ui.admin_dashboard.ui.training_applications

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.employeemanagmentsystem.R
import com.android.employeemanagmentsystem.data.models.responses.Training
import com.android.employeemanagmentsystem.data.network.apis.TrainingApi
import com.android.employeemanagmentsystem.data.repository.AuthRepository
import com.android.employeemanagmentsystem.data.repository.TrainingRepository
import com.android.employeemanagmentsystem.data.room.AppDatabase
import com.android.employeemanagmentsystem.databinding.FragmentTrainingApplicationsBinding
import com.android.employeemanagmentsystem.utils.TRAINING_ALL_STATUS
import com.android.employeemanagmentsystem.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


private const val TAG = "TrainingApplicationsFra"

class TrainingApplicationsFragment : Fragment(R.layout.fragment_training_applications),
    AppliedTrainingsAdapter.TrainingClickListener {

    private lateinit var binding: FragmentTrainingApplicationsBinding
    private var status_id = TRAINING_ALL_STATUS
    lateinit var trainings: List<Training>

    lateinit var status: List<String>
    lateinit var paint: Paint


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTrainingApplicationsBinding.bind(view)

        binding.apply {
            btnGeneratePdf.setOnClickListener {
                createTabularPdf()
            }
        }

        setSpinner()

        getTrainings()
    }

    private fun setSpinner() {
        binding.apply {

            status = listOf(
                "All Status",
                "Applied to HOD",
                "Applied to Principle",
                "Approved by HOD",
                "Decline by HOD",
                "Approved by Principle",
                "Decline by Principle",
                "Completed"
            )

            val ad: ArrayAdapter<*> = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                status
            )


            ad.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
            )


            spinnerStatus.adapter = ad

            spinnerStatus.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        status_id = p2
                        Log.e(TAG, "onItemSelected: " + status_id)
                        getTrainings()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }

                }
        }
    }

    private fun getTrainings() {
        val authRepository = AuthRepository()
        val trainingRepository = TrainingRepository()
        val trainingApi = TrainingApi()

        val empDao = AppDatabase.invoke(requireContext()).getEmployeeDao()

        binding.progressBar.isVisible = true

        GlobalScope.launch {
            val employee = authRepository.getEmployee(empDao)

            trainings = trainingRepository.getAppliedTrainingsByAdmin(
                employee.role_id.toInt(),
                employee.sevarth_id,
                status_id.toString(),
                trainingApi
            )

            withContext(Dispatchers.Main) {

                binding.progressBar.isVisible = false

                binding.tvNotAvailable.isVisible = trainings.isEmpty()

                binding.recyclerView.apply {
                    adapter = AppliedTrainingsAdapter(trainings, this@TrainingApplicationsFragment)
                    layoutManager = LinearLayoutManager(requireContext())
                }


            }

        }


    }

    override fun onTrainingItemClicked(training: Training) {
        TrainingApplicationsFragmentDirections.actionNavTrainingApplicationsToApplicationDetails(
            training
        ).apply {
            findNavController().navigate(this)
        }
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

                //setting data in pdf page
                var pageNumber = 1
                var i = 0
                val rows = 24
                while (i < trainings.size){

                    if(i+rows > trainings.size) {
                        setPage(myPdfDocument, pageNumber, i, trainings.size-1);
                        pageNumber += 1
                        i += rows
                        continue
                    }

                    setPage(myPdfDocument, pageNumber, i, i+rows);
                    pageNumber += 1
                    i += rows;

                }

                storePdfOffline(myPdfDocument)

                withContext(Dispatchers.Main){
                    binding.progressBar.isVisible = false
                }
            }


        }

    }


    private fun setPage(myPdfDocument: PdfDocument, pageNumber: Int, low: Int, high: Int) {

        var myPageInfo: PdfDocument.PageInfo = PdfDocument.PageInfo.Builder(2000, 3010, pageNumber).create()
        var myPage = myPdfDocument.startPage(myPageInfo)
        var canvas = myPage.canvas

        canvas.drawText("Government Polytechnic Amravati", 800F, 100F, paint)

        paint.apply {
            textSize = 35f
            style = Paint.Style.STROKE
            strokeWidth = 2F
        }

        canvas.drawRect(20f, 180f, 1980f, 260f, paint)

        canvas.drawText("Sevarth-Id", 23f, 230f, paint)
        canvas.drawText("Training Name", 260f, 230f, paint)
        canvas.drawText("Duration ", 650f, 230f, paint)
        canvas.drawText("Start Date ", 820f, 230f, paint)
        canvas.drawText("End Date ", 1050f, 230f, paint)
        canvas.drawText("Status", 1350f, 230f, paint)
        canvas.drawText("Organized By", 1600f, 230f, paint)

        var x = 20f
        var y = 280f
        var offset_y = 100f

        for (i in low .. high){

            y += offset_y
            x = 50f


            canvas.drawText(trainings[i].sevarth_id, x, y, paint)
            x += 230f
            canvas.drawText(trainings[i].name, x, y, paint)
            x += 400f
            canvas.drawText(trainings[i].duration, x, y, paint)
            x += 150f
            canvas.drawText(trainings[i].start_date, x, y, paint)
            x += 230f
            canvas.drawText(trainings[i].end_date, x, y, paint)
            x += 200f
            canvas.drawText(trainings[i].myStatus, x, y, paint)
            x += 350f
            canvas.drawText(trainings[i].organized_by, x, y, paint)


        }


        myPdfDocument.finishPage(myPage)
    }

    private suspend fun storePdfOffline(myPdfDocument: PdfDocument) {

        withContext(Dispatchers.IO){
            val name = status[status_id] + ".pdf"
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
}

