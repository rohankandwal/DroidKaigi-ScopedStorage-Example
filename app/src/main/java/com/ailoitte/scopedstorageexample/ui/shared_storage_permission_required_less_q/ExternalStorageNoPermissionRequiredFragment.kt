package com.ailoitte.scopedstorageexample.ui.shared_storage_permission_required_less_q

import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ailoitte.scopedstorageexample.R
import com.ailoitte.scopedstorageexample.utility.createFile
import com.ailoitte.scopedstorageexample.utility.readFile
import kotlinx.android.synthetic.main.fragment_shared_storage_permission_required_less_q.*
import java.io.File


class ExternalStorageNoPermissionRequiredFragment : Fragment() {

    private lateinit var sharedStoragePermissionRequiredViewModel: SharedStoragePermissionRequiredViewModel
    private val fileName: String = "external_shared_storage_no_permission_required.text"
    private val TAG: String = this.javaClass.simpleName

    private val path: String by lazy {
        getPathUrl()
    }

    fun getPathUrl(): String {
        activity?.let {
            var path = System.getenv("SECONDARY_STORAGE")
            // For Marshmallow, use getExternalCacheDirs() instead of System.getenv("SECONDARY_STORAGE")
            // For Marshmallow, use getExternalCacheDirs() instead of System.getenv("SECONDARY_STORAGE")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val externalCacheDirs: Array<File> =
                    it.getExternalFilesDirs(Environment.DIRECTORY_DOCUMENTS)
                for (file in externalCacheDirs) {
                    if (Environment.isExternalStorageRemovable(file)) {
                        // Path is in format /storage.../Android....
                        // Get everything before /Android
//                        path = file.path.split("/data").toTypedArray()[0]
                        path = file.path
                        break
                    }
                }
            }
            // Android avd emulator doesn't support this variable name so using other one
            if (path.isNullOrBlank())
                path = Environment.getExternalStorageDirectory().absolutePath
            return path!!
        }
        return Environment.getExternalStorageDirectory().absolutePath
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedStoragePermissionRequiredViewModel =
            ViewModelProviders.of(this).get(SharedStoragePermissionRequiredViewModel::class.java)
        return inflater.inflate(
            R.layout.fragment_shared_storage_permission_required_less_q,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btCreateFile.setOnClickListener {
            createFile()
        }

        btReadFile.setOnClickListener {
            val file = File(path, fileName)
            Toast.makeText(activity, file.readFile(), Toast.LENGTH_LONG).show()
        }
    }

    private fun createFile() {
        val file = File(path, fileName)
        file.createFile("This is a internal file located in " + path)
        btReadFile.isEnabled = true
    }
}