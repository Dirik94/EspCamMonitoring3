package com.example.espcammonitoring

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.GridLayout
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GalleryActivity : AppCompatActivity() {

    private var imageRecycler:RecyclerView? = null
    private var progressBar:ProgressBar? = null
    private var allPictures:ArrayList<Image>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        imageRecycler = findViewById(R.id.image_recycler)
        progressBar = findViewById(R.id.recycler_progress)

        imageRecycler?.layoutManager = GridLayoutManager(this, 3)
        imageRecycler?.setHasFixedSize(true)

        //Storage Permissions
        if(ContextCompat.checkSelfPermission(this@GalleryActivity,android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@GalleryActivity, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),101)
        }

        allPictures=ArrayList()

        if(allPictures!!.isEmpty()){
            progressBar?.visibility = View.VISIBLE
            allPictures = getAllImages()
            imageRecycler?.adapter = ImageAdapter(this, allPictures!!)
            progressBar?.visibility = View.GONE
        }
    }

    private fun getAllImages(): ArrayList<Image>? {
        val images = ArrayList<Image>()

        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)

        var cursor = this@GalleryActivity.contentResolver.query(allImageUri,projection,null,null,null)

        try {
            cursor!!.moveToFirst()
            do{
                val image = Image(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)),cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)))
//                image.imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
//                image.imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                images.add(image)
            }while (cursor.moveToNext())
            cursor.close()
        }catch (e:java.lang.Exception){
            e.printStackTrace()
        }
        return images
    }
}