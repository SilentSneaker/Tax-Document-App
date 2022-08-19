package com.example.taxdocumentapp

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

public class ImageGallery {
    companion object Dave {


        public final fun ListOfImages(context: Context): ArrayList<String> {
            var uri: Uri
            var cursor: Cursor
            var indexData: Int
            var indexFolderName: Int
            var listOfImage: ArrayList<String> = ArrayList()
            var absolutePath: String
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            var projection: Array<String> = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            var orderBy = MediaStore.Video.Media.DATE_TAKEN
            cursor = context.contentResolver.query(uri, projection,null,null,orderBy)!!

            indexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

            while (cursor.moveToNext())
            {
                absolutePath = cursor.getString(indexData)
                listOfImage.add(absolutePath)
            }
            return listOfImage
        }
    }
}