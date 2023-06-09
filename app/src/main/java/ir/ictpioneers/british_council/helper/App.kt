package ir.ictpioneers.british_council.helper

import android.annotation.SuppressLint
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import ir.ictpioneers.british_council.R
import ir.ictpioneers.british_council.database.DatabaseHelper
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
                val fileName = "image_${System.currentTimeMillis()}.mp3"
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


        fun showDialogAboutUs(_context: Context) {

            val metrics = _context.resources.displayMetrics
            val width = metrics.widthPixels


            val dialog = Dialog(_context)
            dialog!!.window!!.setLayout(
                ((6.7 * width) / 8).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(_context.resources.getDrawable(R.drawable.item_border_dialog))
            dialog.setContentView(R.layout.dialog_about_us)
            dialog.show()

            var close: TextView = dialog.findViewById(R.id.txt_close)
            close.setOnClickListener { dialog.dismiss() }
        }



        fun showDialogPrivacy(_context: Context, resId : Int) {
            val metrics = _context.resources.displayMetrics
            val width = metrics.widthPixels
            val dialog = Dialog(_context)
            dialog!!.window!!.setLayout(((6.7 * width) / 8).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)


            dialog?.setCancelable(false)
            dialog?.window!!.setBackgroundDrawable(_context.resources.getDrawable(R.drawable.item_border_dialog))
            dialog?.setContentView(R.layout.dialog_privacy)
            dialog?.show()

            var close: TextView = dialog?.findViewById(R.id.txt_close_privace)!!
            close.setOnClickListener { dialog?.dismiss() }
        }


        fun showDialogSource(_context: Context) {
            val metrics = _context.resources.displayMetrics
            val width = metrics.widthPixels


            val dialog = Dialog(_context)
            dialog!!.window!!.setLayout(
                ((6.7 * width) / 8).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(_context.resources.getDrawable(R.drawable.item_border_dialog))
            dialog.setContentView(R.layout.dialog_source)
            dialog.show()

            var close: TextView = dialog.findViewById(R.id.txt_close_source)
            close.setOnClickListener { dialog.dismiss() }
        }





    }
}