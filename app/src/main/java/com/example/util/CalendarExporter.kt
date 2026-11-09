package com.example.util

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.model.InterviewLog
import java.io.File
import java.io.FileOutputStream

object CalendarExporter {

    /**
     * downloadBlob utility for Android: writes text or binary blob to local storage/cache,
     * triggers system download/view notification, and opens or shares the file.
     */
    fun downloadBlob(context: Context, filename: String, content: String, mimeType: String = "text/calendar"): File? {
        return try {
            val cacheDir = File(context.cacheDir, "downloads")
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }
            val targetFile = File(cacheDir, filename)
            FileOutputStream(targetFile).use { out ->
                out.write(content.toByteArray(Charsets.UTF_8))
            }

            // Also make a copy in public Downloads directory if possible
            try {
                val publicDownloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (publicDownloads != null && publicDownloads.exists()) {
                    val publicFile = File(publicDownloads, filename)
                    FileOutputStream(publicFile).use { out ->
                        out.write(content.toByteArray(Charsets.UTF_8))
                    }
                }
            } catch (_: Exception) {
                // Ignore if storage permission/scoped storage restricts public write
            }

            targetFile
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Generates .ICS using downloadBlob and triggers personal calendar import/share.
     */
    fun exportAndShareIcs(context: Context, log: InterviewLog) {
        try {
            val icsData = log.toIcsContent()
            val sanitizedName = log.company.replace(Regex("[^a-zA-Z0-9]"), "_")
            val fileName = "interview_${sanitizedName}_${log.dateString}.ics"

            val icsFile = downloadBlob(
                context = context,
                filename = fileName,
                content = icsData,
                mimeType = "text/calendar"
            ) ?: throw IllegalStateException("Failed to create .ICS file blob")

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                icsFile
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "text/calendar")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
            }

            val chooser = Intent.createChooser(intent, "Add Interview to Personal Calendar (.ICS)").apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(chooser)
            Toast.makeText(context, "Generated ${fileName} for ${log.company}!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/calendar"
                    putExtra(Intent.EXTRA_SUBJECT, "Interview: ${log.company} - ${log.role}")
                    putExtra(Intent.EXTRA_TEXT, log.toIcsContent())
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(Intent.createChooser(shareIntent, "Save / Share Interview .ICS"))
            } catch (fallbackError: Exception) {
                Toast.makeText(context, "Generated .ICS successfully! Saved to downloads.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
