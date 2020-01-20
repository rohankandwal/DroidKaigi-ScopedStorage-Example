package com.ailoitte.scopedstorageexample.ui.external_no_permission

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ailoitte.scopedstorageexample.R
import com.ailoitte.scopedstorageexample.utility.createFile
import com.ailoitte.scopedstorageexample.utility.readFile
import kotlinx.android.synthetic.main.fragment_external_no_permission.*
import java.io.File

class ExternalStorageNoPermissionFragment : Fragment() {

    private lateinit var externalStorageNoPermissionViewModel: ExternalStorageNoPermissionViewModel
    private val fileName: String = "external_no_permission.text"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        externalStorageNoPermissionViewModel =
            ViewModelProviders.of(this).get(ExternalStorageNoPermissionViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_external_no_permission, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btCreateFile.setOnClickListener {
            activity?.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.let {
                val file = File(it, fileName)
                file.createFile("This is a internal file located in " + it.path)
                btReadFile.isEnabled = true
            }
        }

        btReadFile.setOnClickListener {
            activity?.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.let {
                val file = File(it, fileName)
                Log.d("ReadFile", file.readFile())
                Toast.makeText(activity, file.readFile(), Toast.LENGTH_LONG).show()
            }
        }
    }
}