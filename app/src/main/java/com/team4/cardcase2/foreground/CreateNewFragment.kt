package com.team4.cardcase2.foreground

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.team4.cardcase2.R
import com.team4.cardcase2.entity.*
import com.team4.cardcase2.interfaces.HttpRequest
import java.io.InputStream
import java.net.URI


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [CreateNewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateNewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var headImage: ImageView
    private lateinit var selectedImage: Uri
    private val REQUEST_IMAGE_PICK = 1
    private val REQUEST_PERMISSION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                Toast.makeText(requireContext(), "拒绝读取外部存储的权限", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            selectedImage = data.data!!
            headImage.setImageURI(selectedImage)
        }
    }

    private fun getBytesFromUri(uri: Uri): ByteArray? {
        val inputStream: InputStream? = requireContext().contentResolver.openInputStream(uri)
        return inputStream?.use { it.readBytes() }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_create_new, container, false)
        val saveButton: Button = root.findViewById(R.id.saveButton)
        val inputFirstName: EditText = root.findViewById(R.id.inputFirstName)
        val inputSecondName: EditText = root.findViewById(R.id.inputSecondName)
        val showName: TextView = root.findViewById(R.id.showName)
        val inputCompany: EditText = root.findViewById(R.id.inputCompany)
        val showCompany: TextView = root.findViewById(R.id.showCompany)
        val showPhone: TextView = root.findViewById(R.id.showPhone)
        val showEmail: TextView = root.findViewById(R.id.showEmail)
        val inputPhone: EditText = root.findViewById(R.id.inputPhone)
        val inputEmail: EditText = root.findViewById(R.id.inputEmail)
        val backButton: TextView = root.findViewById(R.id.backButton)
        val inputTitle: TextView = root.findViewById(R.id.inputTitle)
        headImage = root.findViewById(R.id.imageView)

        headImage.setOnClickListener {
            // 检查是否已经授予READ_EXTERNAL_STORAGE权限
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // 请求权限
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION
                )
            } else {
                // 如果权限已授予，打开相册
                openGallery()
            }
        }

        saveButton.setOnClickListener{
            val base64: String = ""

            val name = Elements("name", showName.text.toString(), Position(50, 10),
                Style("Arial", 20, "#000000", true, false, false))
            val title = Elements("title", inputTitle.text.toString(), Position(50, 30),
                Style("Arial", 20, "#333333", false, false, false))
            val company = Elements("company", inputCompany.text.toString(), Position(50, 50),
                Style("Arial", 20, "#333333", false, false, false))
            val email = Elements("email", showEmail.text.toString(), Position(50, 70),
                Style("Arial", 20, "#000000", false, false, false))
            val phone = Elements("phone", showPhone.text.toString(), Position(50, 90),
                Style("Arial", 20, "#000000", false, false, false))
            val socialMedia = Elements("socialMedia", "GYJ", Position(50, 110),
                Style("Arial", 20, "#000000", false, false, false))
            val elements : MutableList<Elements> = mutableListOf(name, title, company, email, phone, socialMedia)
            val userId = 1
            val avatar = ""
            val background = ""
            val design = Design("", "", "", "")
            val serverCard = ServerCard(0, false, userId, elements, avatar, background, design)

            val authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6IjE5NzIzNTY5NThAcXEuY29tIiwiZXhwIjoxNzE4NTIwMjQyfQ.mXJxuhtmxTx3_yvKv1MOgnGEccK7h_f21ugT-bvuViI"
            val jsonBody = serverCard.toJson()
            println(jsonBody)
            val url = "https://578q07p228.vicp.fun/api/create-card"

            val httpRequest = HttpRequest()
            httpRequest.sendPostRequest(url, authToken, jsonBody) { response, exception ->
                if (exception != null) {
//                    outText.text=exception.message.toString()
                    println("Request failed: ${exception.message}")
                } else {
                    println("Response: $response")
//                    outText.text = response.toString()
                }
            }
            val navController = findNavController()
            navController.navigate(R.id.createCardFragment2)
        }

        backButton.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.createCardFragment)
        }

        inputFirstName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Update showName with the current text in inputFirstName and inputSecondName
                val firstName = inputFirstName.text.toString()
                val secondName = inputSecondName.text.toString()
                showName.text = "$firstName $secondName"
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })

        inputSecondName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Update showName with the current text in inputFirstName and inputSecondName
                val firstName = inputFirstName.text.toString()
                val secondName = inputSecondName.text.toString()
                showName.text = "$firstName $secondName"
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })

        inputCompany.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Update showName with the current text in inputFirstName and inputSecondName
                val company = inputCompany.text.toString()
                showCompany.text = "$company"
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })

        inputPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Update showName with the current text in inputFirstName and inputSecondName
                val phone = inputPhone.text.toString()
                showPhone.text = "$phone"
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })

        inputEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Update showName with the current text in inputFirstName and inputSecondName
                val email = inputEmail.text.toString()
                showEmail.text = "$email"
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })
        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateNewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateNewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}