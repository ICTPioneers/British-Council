package com.example.british_council.helper

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.example.british_council.database.DatabaseHelper
import java.io.*

class App : Application() {

     override fun onCreate() {
        super.onCreate()
        context = this
        database = DatabaseHelper.getInstance(context)!!
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var database: DatabaseHelper


        fun toast(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }


        fun getByte(uri: Uri): ByteArray {
            val baos = ByteArrayOutputStream()
            val fis: FileInputStream
            try {
                fis = FileInputStream(File(uri.path))
                val buf = ByteArray(1024)
                var n: Int
                while (-1 != fis.read(buf).also { n = it }) baos.write(buf, 0, n)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return baos.toByteArray()
        }

        fun saveFile(byteArray: ByteArray): String {
            val outStream: FileOutputStream
            try {
                val path = File(Environment.getExternalStorageDirectory(), "/BritishCouncil/sound")
                path.mkdirs()
                val fileName = "image_${System.currentTimeMillis()}.jpg"
                val file = File(path, fileName)
                Log.e("qqqq", "saveFile: ${file.path}")
                outStream = FileOutputStream(file)
                outStream.write(byteArray)
                outStream.close()
                return file.path
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return ""
        }
    }

}