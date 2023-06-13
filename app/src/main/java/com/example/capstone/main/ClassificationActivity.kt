package com.example.capstone.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.capstone.R
import com.example.capstone.classification.Classifier

class ClassificationActivity : AppCompatActivity() {

    private val mInputSize = 224
    private val mModelPath = "model_mnet.tflite"
    private val mLabelPath = "label_mnet.txt"
    private lateinit var classifier: Classifier

    lateinit var camera: Button
    lateinit var gallery: Button
    lateinit var imageView: ImageView
    lateinit var res: TextView
    lateinit var conf: TextView
    lateinit var calories : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classification)
        initClassifier()

        camera = findViewById(R.id.btn_camera)
        gallery = findViewById(R.id.btn_gallery)
        calories = findViewById(R.id.calories)
        res = findViewById(R.id.result)
        conf = findViewById(R.id.confident)
        imageView = findViewById(R.id.imageView)

        camera.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 3)
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
        gallery.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 1)
        }
    }

    private fun initClassifier() {
        classifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val uri = data?.data
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            imageView.setImageBitmap(bitmap)
            val result = classifier.recognizeImage(bitmap)

            res.text = result[0].title
            conf.text = result[0].confidence.toString()
            calories.text = result[0].calories.toString()
        }

        if (requestCode == 3) {
            val image = data?.extras?.get("data") as Bitmap
            val dimension = image.width.coerceAtMost(image.height)
            val thumbnail = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
            imageView.setImageBitmap(thumbnail)

            val bitmap = Bitmap.createScaledBitmap(thumbnail, 224, 224, false)
            val result = classifier.recognizeImage(bitmap)

            res.text = result[0].title
            conf.text = result[0].confidence.toString()
            calories.text = result[0].calories.toString()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}