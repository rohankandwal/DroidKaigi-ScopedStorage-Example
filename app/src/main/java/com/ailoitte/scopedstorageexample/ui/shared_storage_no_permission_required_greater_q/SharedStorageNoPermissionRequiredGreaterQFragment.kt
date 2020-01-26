package com.ailoitte.scopedstorageexample.ui.shared_storage_no_permission_required_greater_q

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ailoitte.scopedstorageexample.R
import com.ailoitte.scopedstorageexample.utility.readFileContent
import kotlinx.android.synthetic.main.fragment_shared_storage_no_permission_required_greater_q.*
import java.io.*


class SharedStorageNoPermissionRequiredGreaterQFragment : Fragment() {

    private val CREATE_REQUEST_CODE = 40
    private val OPEN_REQUEST_CODE = 41
    private val SAVE_REQUEST_CODE = 42


    private val fileName: String = "external_shared_storage_permission_required.text"

    // Use only for > Q Devices
    val path = Environment.getExternalStorageDirectory().path

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_shared_storage_no_permission_required_greater_q,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btCreateFile.setOnClickListener {
            newFile()
            /*activity?.getExternalFilesDir(null)?.let {
                val file = File(path, fileName)
                file.createFile("This is a internal file located in " + it.path)
            }*/
        }

        btReadFile.setOnClickListener {
            openFile()
            /*activity?.getExternalFilesDir(null)?.let {
                val file = File(path, fileName)
                Log.d("ReadFile", file.readFile())
            }*/
        }
    }
    private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        startActivityForResult(intent, OPEN_REQUEST_CODE)
    }

    private fun newFile() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TITLE, fileName)
        startActivityForResult(intent, CREATE_REQUEST_CODE)
    }

    private fun writeFileContent(uri: Uri) {
        try {
            val pfd: ParcelFileDescriptor? = activity?.contentResolver?.openFileDescriptor(uri, "w")
            pfd?.let {
                val fileOutputStream = FileOutputStream(
                    it.fileDescriptor
                )
                fileOutputStream.write("This is a internal file located in ${uri.path}".toByteArray())
                fileOutputStream.close()
                it.close()
                Toast.makeText(activity, "File write successful", Toast.LENGTH_LONG).show()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CREATE_REQUEST_CODE) {
                if (data != null && data.data != null) {
                    Toast.makeText(activity, "File created", Toast.LENGTH_LONG).show()
                    writeFileContent(data.data!!)
                    btReadFile.isEnabled = true
                }
            } else if (requestCode == OPEN_REQUEST_CODE) {
                if (data != null && data.data != null) {
                    try {
                        val content = data.data!!.readFileContent(activity!!.contentResolver)
                        Toast.makeText(activity, content, Toast.LENGTH_LONG).show()
                    } catch (e: IOException) {
                        // Handle error here
                    }
                }
            }
        }
    }
}