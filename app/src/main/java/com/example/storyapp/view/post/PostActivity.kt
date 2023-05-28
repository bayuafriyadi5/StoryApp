package com.example.storyapp.view.post

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.example.storyapp.R
import com.example.storyapp.Utils.*
import com.example.storyapp.databinding.ActivityPostBinding
import com.example.storyapp.view.login.LoginActivity
import com.example.storyapp.view.main.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
@ExperimentalPagingApi
class PostActivity : AppCompatActivity() {

    private val binding: ActivityPostBinding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }

    private val postViewModel: PostViewModel by viewModels ()
    private var location: Location? = null
    private var getFile: File? = null
    private var token: String = "Token Tidak Ada"
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        when {
            it[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> getLocation()
            else -> binding.switchLocation.isChecked = false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        binding.cameraButton.setOnClickListener { startCameraX() }
        binding.galleryButton.setOnClickListener { startGallery() }

        binding.switchLocation.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) getLocation()
            else location = null
        }

        postViewModel.checkIfTokenAvailable().observe(this) { token ->
            this.token = token ?: "Token Tidak Ada"
        }
        binding.uploadButton.setOnClickListener {
            uploadImage()
        }
    }

    override fun onResume() {
        super.onResume()
        checkIfSessionValid()
    }

    private fun checkIfSessionValid() {
        postViewModel.checkIfTokenAvailable().observe(this) {
            if (it == "null") {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }


    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }


    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }


    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File

            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            myFile?.let { file ->
                rotateFile(file, isBackCamera)
                getFile = file
                binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }



    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri


            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this)
                getFile = myFile
                binding.previewImageView.setImageURI(uri)
            }
        }
    }

    private fun getLocation() {
        if (
            ContextCompat.checkSelfPermission(
                this@PostActivity, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                if (it != null) location = it
                else {
                    Toast.makeText(this, "Please activate your location",Toast.LENGTH_SHORT).show()
                    binding.switchLocation.isChecked = false
                }
            }
        } else requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private fun uploadImage() {
        if (binding.etDesc.text.isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.description_cannot_empty), Toast.LENGTH_SHORT).show()
        } else {
            lifecycleScope.launchWhenStarted{
                launch {
                    if (getFile != null) {
                        val file = reduceFileImage(getFile as File)
                        binding.loading.visibility = View.VISIBLE
                        val description = binding.etDesc.text.toString()
                        var lat: RequestBody? = null
                        var lon: RequestBody? = null

                        if (location != null) {
                            lat = location?.latitude.toString()
                                .toRequestBody("text/plain".toMediaType())
                            lon = location?.longitude.toString()
                                .toRequestBody("text/plain".toMediaType())
                        }
                        postViewModel.postStory(token, file, description, lat, lon)
                            .observe(this@PostActivity) { result ->
                                result.onSuccess {
                                    showToast(this@PostActivity, getString(R.string.image_posted))
                                    showLoading(false)
                                    val intent = Intent(this@PostActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                result.onFailure {
                                    showToast(this@PostActivity, getString(R.string.upload_failed))
                                    showLoading(false)
                                }
                            }
                    }else{
                       showToast(this@PostActivity, getString(R.string.select_image))
                    }
                }
            }

        }
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loading.setVisible(isLoading)
    }

    fun View.setVisible(visible: Boolean) {
        this.visibility = if (visible) View.VISIBLE else View.GONE
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}