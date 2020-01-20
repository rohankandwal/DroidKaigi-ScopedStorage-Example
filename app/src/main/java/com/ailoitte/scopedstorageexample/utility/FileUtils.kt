package com.ailoitte.scopedstorageexample.utility

import android.util.Log
import java.io.*


/**
 * @author Rohan Kandwal on 2020-01-12.
 */

/**
 * Extension Function to create a new file
 */
fun File.createFile(data: String) {
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
