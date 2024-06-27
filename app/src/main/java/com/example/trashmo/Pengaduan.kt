package com.example.trashmo

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import android.Manifest
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.testapp.reduceFileImage
import com.example.testapp.uriToFile
import com.example.trashmo.CameraActivity.Companion.CAMERAX_RESULT
import com.example.trashmo.databinding.ActivityPengaduanBinding
import com.example.trashmo.model.AduanModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage

class Pengaduan : AppCompatActivity() {

    private lateinit var binding: ActivityPengaduanBinding
    private var currentImageUri: Uri? = null
    private lateinit var storageRef: StorageReference
    private lateinit var databaseRef: DatabaseReference
    private lateinit var authRef: FirebaseAuth

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }
    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPengaduanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        authRef = Firebase.auth
        storageRef = Firebase.storage.reference
        databaseRef = Firebase.database.reference

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.imageView3.setOnClickListener {
            startCameraX()
        }

        binding.button.setOnClickListener {
            upload()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.imageView3.setImageURI(it)
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun upload() {
        val userId = authRef.currentUser?.uid
        val judul = binding.etIsijenisaduan.text.toString()
        val deskripsi = binding.etIsideskripsi.text.toString()
        val lokasi = binding.etIsiloksi.text.toString()

        currentImageUri?.let {
            val database = databaseRef.child("aduan").child("$userId")
            val imgFile = uriToFile(it, this).reduceFileImage()
            val uploadImage = storageRef.child("aduan/${imgFile.name}").putFile(imgFile.toUri())

            uploadImage.addOnSuccessListener {
                val aduanId = database.push().key!!
                storageRef.child("aduan/${imgFile.name}")
                    .downloadUrl.addOnSuccessListener {  imageUrl ->
                        val aduan = AduanModel(
                            aduanId,
                            judul,
                            deskripsi,
                            lokasi,
                            imageUrl.toString()
                        )

                        database.child(aduanId).setValue(aduan)
                        Toast.makeText(this, "Aduan success",Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Aduan gagal",Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}