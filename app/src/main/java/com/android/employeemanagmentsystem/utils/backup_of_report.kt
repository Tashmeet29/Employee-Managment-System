package com.android.employeemanagmentsystem.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import com.android.employeemanagmentsystem.R
import java.io.File
import java.io.FileOutputStream

//private fun generatePdf() {
//
//
//    val name = status[status_id] + ".pdf"
//    val file =
//        File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/${name}")
//
//    val fileOutputStream = FileOutputStream(file)
//    val pdfDocument = PdfDocument()
//
//    val title = Paint()
//
//    title.apply {
//        textSize = 15F
//        typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
//        color = ContextCompat.getColor(requireContext(), R.color.black)
//    }
//
//
//    addDataToPdf(pdfDocument, title)
//
//
//
//    try {
//        pdfDocument.writeTo(fileOutputStream)
//        requireContext().toast("Report Generated Successfully")
//        Log.e(com.android.employeemanagmentsystem.ui.admin_dashboard.ui.training_applications.TAG, "generatePdf: pfd generated" + file.absolutePath)
//    } catch (e: Exception) {
//        requireContext().toast(e.toString())
//    } finally {
//        pdfDocument.close()
//    }
//}
//
//
//private fun addDataToPdf(pdfDocument: PdfDocument, title: Paint) {
//
//    for (i in 0..trainings.size step 3) {
//
//        var x = 50f
//        var y = 100f
//        var offset = 20f
//
//        val myPageInfo: PdfDocument.PageInfo =
//            PdfDocument.PageInfo.Builder(1120, 1000, 1).create()
//        val myPage = pdfDocument.startPage(myPageInfo)
//        val canvas: Canvas = myPage.canvas
//
//        var training = trainings[i]
//        canvas.drawText("Training Name :-  " + training.name, x, y, title)
//        y += offset
//        canvas.drawText("Training Duration :-  " + training.myDuration, x, y, title)
//        y += offset
//        canvas.drawText(
//            "Date :-  " + training.start_date + " - " + training.end_date,
//            x,
//            y,
//            title
//        )
//        y += offset
//        canvas.drawText("Sevarth-ID :-  " + training.sevarth_id, x, y, title)
//        y += offset
//        canvas.drawText("Organized By :-  " + training.org_name, x, y, title)
//        y += offset
//        canvas.drawText("Status :-  " + training.myStatus, x, y, title)
//        y += 50F
//
//        if (i + 1 >= trainings.size) {
//            pdfDocument.finishPage(myPage)
//            continue
//        }
//
//        training = trainings[i + 1]
//        canvas.drawText("Training Name :-  " + training.name, x, y, title)
//        y += offset
//        canvas.drawText("Training Duration :-  " + training.myDuration, x, y, title)
//        y += offset
//        canvas.drawText(
//            "Date :-  " + training.start_date + " - " + training.end_date,
//            x,
//            y,
//            title
//        )
//        y += offset
//        canvas.drawText("Sevarth-ID :-  " + training.sevarth_id, x, y, title)
//        y += offset
//        canvas.drawText("Organized By :-  " + training.org_name, x, y, title)
//        y += offset
//        canvas.drawText("Status :-  " + training.myStatus, x, y, title)
//        y += 50F
//
//        training = trainings[i + 2]
//        canvas.drawText("Training Name :-  " + training.name, x, y, title)
//        y += offset
//        canvas.drawText("Training Duration :-  " + training.myDuration, x, y, title)
//        y += offset
//        canvas.drawText(
//            "Date :-  " + training.start_date + " - " + training.end_date,
//            x,
//            y,
//            title
//        )
//        y += offset
//        canvas.drawText("Sevarth-ID :-  " + training.sevarth_id, x, y, title)
//        y += offset
//        canvas.drawText("Organized By :-  " + training.org_name, x, y, title)
//        y += offset
//        canvas.drawText("Status :-  " + training.myStatus, x, y, title)
//        y += 50F
//
//        pdfDocument.finishPage(myPage)
//
//    }
//}
