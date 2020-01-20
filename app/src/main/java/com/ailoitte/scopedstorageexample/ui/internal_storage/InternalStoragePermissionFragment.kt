package com.ailoitte.scopedstorageexample.ui.internal_storage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ailoitte.scopedstorageexample.R
import com.ailoitte.scopedstorageexample.utility.createFile
import com.ailoitte.scopedstorageexample.utility.readFile
import kotlinx.android.synthetic.main.fragment_internal_storage.*
import java.io.File

class InternalStoragePermissionFragment : Fragment() {

    private val fileName:String = "internal.txt"

    private lateinit var internalStoragePermissionViewModel: InternalStoragePermissionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        internalStoragePermissionViewModel =
            ViewModelProviders.of(this).get(InternalStoragePermissionViewModel::class.java)
        return inflater.inflate(R.layout.fragment_internal_storage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btCreateFile.setOnClickListener {
            activity?.filesDir?.let {
                val file = File(it, fileName)
                file.createFile("This is a internal file located in " + it.path)
                btReadFile.isEnabled = true
            }
        }

        btReadFile.setOnClickListener {
            activity?.filesDir?.let {
                val file = File(it, fileName)
                Log.d("ReadFile", file.readFile())
                Toast.makeText(activity, file.readFile(), Toast.LENGTH_LONG).show()
            }
        }
    }
}