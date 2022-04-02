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
import com.android.employeemanagmentsystem.utils.ALL_STATUS
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
    private var status_id = ALL_STATUS
    lateinit var trainings: List<Training>

    lateinit var status: List<String>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTrainingApplicationsBinding.bind(view)

        generatePdf()

        setSpinner()

        getTrainings()
    }

    private fun generatePdf() {
        binding.btnGeneratePdf.setOnClickListener {

            val name = status[status_id] + ".pdf"
            val file =
                File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/${name}")

            val fileOutputStream = FileOutputStream(file)
            val pdfDocument = PdfDocument()

            val title = Paint()

            title.apply {
                textSize = 15F
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                color = ContextCompat.getColor(requireContext(), R.color.black)
            }


            addDataToPdf(pdfDocument, title)



            try {
                pdfDocument.writeTo(fileOutputStream)
                requireContext().toast("Report Generated Successfully")
                Log.e(TAG, "generatePdf: pfd generated" + file.absolutePath)
            } catch (e: Exception) {
                requireContext().toast(e.toString())
            } finally {
                pdfDocument.close()
            }
        }

    }

    private fun addDataToPdf(pdfDocument: PdfDocument, title: Paint) {

        for (i in 0..trainings.size step 3) {

            var x = 50f
            var y = 100f
            var offset = 20f

            val myPageInfo: PdfDocument.PageInfo =
                PdfDocument.PageInfo.Builder(1120, 1000, 1).create()
            val myPage = pdfDocument.startPage(myPageInfo)
            val canvas: Canvas = myPage.canvas

            var training = trainings[i]
            canvas.drawText("Training Name :-  " + training.name, x, y, title)
            y += offset
            canvas.drawText("Training Duration :-  " + training.myDuration, x, y, title)
            y += offset
            canvas.drawText(
                "Date :-  " + training.start_date + " - " + training.end_date,
                x,
                y,
                title
            )
            y += offset
            canvas.drawText("Sevarth-ID :-  " + training.sevarth_id, x, y, title)
            y += offset
            canvas.drawText("Organized By :-  " + training.org_name, x, y, title)
            y += offset
            canvas.drawText("Status :-  " + training.myStatus, x, y, title)
            y += 50F

            if (i + 1 >= trainings.size) {
                pdfDocument.finishPage(myPage)
                continue
            }

            training = trainings[i + 1]
            canvas.drawText("Training Name :-  " + training.name, x, y, title)
            y += offset
            canvas.drawText("Training Duration :-  " + training.myDuration, x, y, title)
            y += offset
            canvas.drawText(
                "Date :-  " + training.start_date + " - " + training.end_date,
                x,
                y,
                title
            )
            y += offset
            canvas.drawText("Sevarth-ID :-  " + training.sevarth_id, x, y, title)
            y += offset
            canvas.drawText("Organized By :-  " + training.org_name, x, y, title)
            y += offset
            canvas.drawText("Status :-  " + training.myStatus, x, y, title)
            y += 50F

            training = trainings[i + 2]
            canvas.drawText("Training Name :-  " + training.name, x, y, title)
            y += offset
            canvas.drawText("Training Duration :-  " + training.myDuration, x, y, title)
            y += offset
            canvas.drawText(
                "Date :-  " + training.start_date + " - " + training.end_date,
                x,
                y,
                title
            )
            y += offset
            canvas.drawText("Sevarth-ID :-  " + training.sevarth_id, x, y, title)
            y += offset
            canvas.drawText("Organized By :-  " + training.org_name, x, y, title)
            y += offset
            canvas.drawText("Status :-  " + training.myStatus, x, y, title)
            y += 50F

            pdfDocument.finishPage(myPage)

        }
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

}


/*

        val columnsWidth: FloatArray = FloatArray(2)
        columnsWidth[0] = 200f
        columnsWidth[1] = 200f

        val table = Table(columnsWidth)
        table.addCell("Cell 1")
        table.addCell("Cell 2")

        table.addCell(Cell(2, 1).add(Paragraph("paragraph")))
        table.addCell("332")*/