package com.example.taxdocumentapp

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.currentCoroutineContext

public class ImageGallery {
    companion object Dave {


        public final fun listOfImages(context: Context): ArrayList<String> {
            var uri: Uri
            var cursor: Cursor?
            var indexData: Int
            var indexFolderName: Int
            var listOfImages: ArrayList<String>? = null
            var absolutePath: String

            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            var projection: Array<String> = arrayOf(
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
            )

            var orderBy: String = MediaStore.Video.Media.DATE_TAKEN
            cursor = context.contentResolver.query(
                uri, projection,
                null, null, orderBy + "DESC"
            )

            indexData = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

            //get folder name

            while (cursor.moveToNext()) {
                absolutePath = cursor.getString(indexData)
                listOfImages?.add(absolutePath)
            }


            return listOfImages!!
        }
    }
}