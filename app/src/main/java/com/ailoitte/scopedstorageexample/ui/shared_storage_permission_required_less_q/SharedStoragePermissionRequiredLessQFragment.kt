package com.ailoitte.scopedstorageexample.ui.shared_storage_permission_required_less_q

import android.Manifest
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ailoitte.scopedstorageexample.R
import com.ailoitte.scopedstorageexample.utility.createFile
import com.ailoitte.scopedstorageexample.utility.readFile
import kotlinx.android.synthetic.main.fragment_shared_storage_permission_required_less_q.*
import java.io.File


class SharedStoragePermissionRequiredLessQFragment : Fragment() {

    private lateinit var sharedStoragePermissionRequiredViewModel: SharedStoragePermissionRequiredViewModel
    private val fileName: String = "external_shared_storage_permission_required.text"
    private val TAG: String = this.javaClass.simpleName

    // Use only for < Q Devices
    val path = Environment.getExternalStorageDirectory().path

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
            activity?.getExternalFilesDir(null)?.let {
                val file = File(path, fileName)
                Toast.makeText(activity, file.readFile(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createFile() {
        if (isStoragePermissionGranted()) {
            activity?.getExternalFilesDir(null)?.let {
                val file = File(path, fileName)
                file.createFile("This is a internal file located in " + it.path)
                btReadFile.isEnabled = true
            }
        } else {
            Toast.makeText(activity, "Please give storage permissions", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun isStoragePermissionGranted(): Boolean {
        activity?.let {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(it, WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    Log.v(TAG, "Permission is granted")
                    true
                } else {
                    Log.v(TAG, "Permission is revoked")
                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        1
                    )
                    false
                }
            } else { //permission is automatically granted on sdk<23 upon installation
                Log.v(TAG, "Permission is granted")
                true
            }
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0])
            createFile()
        }
    }
}