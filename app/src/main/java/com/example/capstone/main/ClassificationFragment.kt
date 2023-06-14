package com.example.capstone.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

import com.example.capstone.classification.Classifier
import com.example.capstone.databinding.FragmentClassificationBinding



class ClassificationFragment : Fragment() {


    private var _binding: FragmentClassificationBinding? = null
    private val binding get() = _binding!!

    private val mInputSize = 224
    private val mModelPath = "model_mnet.tflite"
    private val mLabelPath = "label_mnet.txt"
    private lateinit var classifier: Classifier

    private lateinit var cameraButton: Button
    private lateinit var galleryButton: Button
    private lateinit var imageView: ImageView
    private lateinit var resultTextView: TextView
    private lateinit var confidenceTextView: TextView
    private lateinit var caloriesTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClassificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClassifier()

        cameraButton = binding.btnCamera
        galleryButton = binding.btnGallery
        caloriesTextView = binding.calories
        resultTextView = binding.result
        confidenceTextView = binding.confident
        imageView = binding.imageView

        cameraButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 3)
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }

        galleryButton.setOnClickListener {
            val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(galleryIntent, 1)
        }
    }

    private fun initClassifier() {
        classifier = Classifier(requireContext().assets, mModelPath, mLabelPath, mInputSize)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            val uri = data?.data
            val bitmap =
                MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
            imageView.setImageBitmap(bitmap)
            val result = classifier.recognizeImage(bitmap)

            resultTextView.text = result[0].title
            confidenceTextView.text = result[0].confidence.toString()
            caloriesTextView.text = result[0].calories.toString()
        }

        if (requestCode == 3) {
            val image = data?.extras?.get("data") as Bitmap
            val dimension = image.width.coerceAtMost(image.height)
            val thumbnail = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
            imageView.setImageBitmap(thumbnail)

            val bitmap = Bitmap.createScaledBitmap(thumbnail, 224, 224, false)
            val result = classifier.recognizeImage(bitmap)

            resultTextView.text = result[0].title
            confidenceTextView.text = result[0].confidence.toString()
            caloriesTextView.text = result[0].calories.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
