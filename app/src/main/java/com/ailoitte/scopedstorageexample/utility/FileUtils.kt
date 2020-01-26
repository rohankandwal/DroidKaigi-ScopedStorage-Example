package com.ailoitte.scopedstorageexample.utility

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import java.io.*


/**
 * @author Rohan Kandwal on 2020-01-12.
 */

/**
 * Extension Function to create a new file
 */
fun File.createFile(data: String) {
//    data.replace("external_shared_storage_permission_required.text", "")
    Log.d("FileUtils", this.listFiles()?.toString() ?: "Unknown")
    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(this.path)
        val buffer = data.toByteArray()
        fos.write(buffer, 0, buffer.size)
        fos.close()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        Log.e("FileUtils", "FNFException    =" + e.message)
    } catch (e: IOException) {
        e.printStackTrace()
        Log.e("FileUtils", "IOException=" + e.message)
    } finally {
        fos?.close()
    }
}

/**
 * Extension function to read data from a file
 */

fun File.readFile(): String {
    //Read text from file
    val text = StringBuilder()
    try {
        val br = BufferedReader(FileReader(this))
        var line: String?
        while (br.readLine().also { line = it } != null) {
            text.append(line)
            text.append('\n')
        }
        br.close()
    } catch (e: IOException) { //You'll need to add proper error handling here
        return (e.message ?: "IOException occurred")
    }
    return text.toString()
}

fun File.obtainDurablePermission(resolver: ContentResolver, document: Uri): Boolean {
    var weHaveDurablePermission = false
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        val perms =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        try {
            resolver.takePersistableUriPermission(document, perms)
            for (perm in resolver.persistedUriPermissions) {
                if (perm.uri == document) {
                    weHaveDurablePermission = true
                }
            }
        } catch (e: SecurityException) { // OK, we were not offered any persistable permissions
        }
    }
    return weHaveDurablePermission
}

@Throws(IOException::class)
fun Uri.readFileContent(contentResolver: ContentResolver): String? {
    contentResolver.openInputStream(this)?.let {
        val reader = BufferedReader(
            InputStreamReader(
                it
            )
        )
        val stringBuilder = StringBuilder()
        var currentline: String = ""
        while (reader.readLine()?.also {
                currentline = it
            } != null
        ) {
            stringBuilder.append(currentline + "\n")
        }
        it.close()
        return stringBuilder.toString()
    }
    return null
}
