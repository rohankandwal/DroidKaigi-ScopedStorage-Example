package com.ailoitte.scopedstorageexample.ui.external_no_permission_q_document_tree

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import com.ailoitte.scopedstorageexample.R
import com.ailoitte.scopedstorageexample.utility.obtainDurablePermission
import com.ailoitte.scopedstorageexample.utility.readFileContent
import kotlinx.android.synthetic.main.fragment_q_document.*
import java.io.File


/**
 * @author Rohan Kandwal on 2020-01-26.
 */
class QDocumentTreeFragment : Fragment() {

    private val REQUEST_TREE: Int = 101
    private val OPEN_REQUEST_CODE: Int = 102

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_q_document, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btCreateFile.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 21) {
                startActivityForResult(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE), REQUEST_TREE)
            } else {
                Toast.makeText(activity, "Not supported < 21", Toast.LENGTH_LONG).show()
            }
        }

        btReadFile.setOnClickListener {
            openFile()
        }
    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        startActivityForResult(intent, OPEN_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_TREE) {
                data?.data?.let { useTheTree(it) }
            } else if (requestCode == OPEN_REQUEST_CODE) {
                activity?.let { activity ->
                    Toast.makeText(
                        activity,
                        data?.data?.readFileContent(activity.contentResolver),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun useTheTree(root: Uri) {
        activity?.let { activity ->
            root.path?.let {
                // Creating new folder inside the location
                // New folder
                val folderPath = it + File.pathSeparator + System.currentTimeMillis()
                val folder = File(folderPath)
                if (folder.obtainDurablePermission(activity.contentResolver, root)) {
                    var pickedDir = DocumentFile.fromTreeUri(activity, root)

                    // Creating file path structure
                    for (i in 0..5) {
                        val newDir =
                            pickedDir?.createDirectory(System.currentTimeMillis().toString())
                        // First creating a folder & then a file in that folder
                        if (newDir?.exists() == true) {
                            val newFile = newDir.createFile(
                                "text/plain",
                                System.currentTimeMillis().toString()
                            )
                            // Writing data to the file
                            newFile?.let {
                                activity.contentResolver?.openOutputStream(it.uri)
                                    ?.apply {
                                        write("Temp file (Level $i) for showing how ACTION_OPEN_DOCUMENT_TREE works".toByteArray())
                                        close()
                                    }
                            }
                            // Changing the picked directory to create a nested structure
                            pickedDir = newDir
                        } else {
                            showErrorToast()
                        }
                    }
                    // Enabling the read file permission
                    btReadFile.isEnabled = true
                } else {
                    showErrorToast()
                }
            }
        }
    }

    private fun showErrorToast() {
        Toast.makeText(activity, "Unable to create the file", Toast.LENGTH_LONG)
            .show()
    }

}