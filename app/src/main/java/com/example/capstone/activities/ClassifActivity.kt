package com.example.capstone.activities


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
import com.example.capstone.classif.Classifier

class ClassifActivity : AppCompatActivity() {

    private val mInputSize = 224
    private val mModelPath = "model_mnet.tflite"
    private val mLabelPath = "label_mnet.txt"
    private lateinit var classifier: Classifier

    lateinit var camera: Button
    lateinit var gallery: Button
    lateinit var imageView: ImageView
    lateinit var res: TextView
    lateinit var conf: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classif)
        initClassifier()

        camera = findViewById<Button>(R.id.button)
        gallery = findViewById<Button>(R.id.button2)

        res = findViewById<TextView>(R.id.result)
        conf = findViewById<TextView>(R.id.confident)
        imageView = findViewById<ImageView>(R.id.imageView)

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
        // Your code for handling the result goes here
        if(requestCode == 1){
            var uri = data?.data;
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,uri)
            imageView.setImageBitmap(bitmap)
            val result = classifier.recognizeImage(bitmap)

            res.setText(result.get(0).title)
            conf.setText(result.get(0).confidence.toString())

//            image = MediaStore.Images.Media.getBitmap(this.contentResolver,uri)
//
//            val scaledImage = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
//            classifyImage(scaledImage)
        }
        if (requestCode == 3) {
            val image = data?.extras?.get("data") as Bitmap
            val dimension = image.width.coerceAtMost(image.height)
            val thumbnail = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
            imageView.setImageBitmap(thumbnail)

            val bitmap = Bitmap.createScaledBitmap(thumbnail, 224, 224, false)
            val result = classifier.recognizeImage(bitmap)

            res.setText(result.get(0).title)
            conf.setText(result.get(0).confidence.toString())
        }
    }

}