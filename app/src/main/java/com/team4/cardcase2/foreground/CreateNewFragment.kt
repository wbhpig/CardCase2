package com.team4.cardcase2.foreground

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.team4.cardcase2.R

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_create_new, container, false)
        val inputFirstName: EditText = root.findViewById(R.id.inputFirstName)
        val inputSecondName: EditText = root.findViewById(R.id.inputSecondName)
        val showName: TextView = root.findViewById(R.id.showName)
        val inputCompany: EditText = root.findViewById(R.id.inputCompany)
        val showCompany: TextView = root.findViewById(R.id.showCompany)
        val showPhone: TextView = root.findViewById(R.id.showPhone)
        val showEmail: TextView = root.findViewById(R.id.showEmail)
        val inputPhone: EditText = root.findViewById(R.id.inputPhone)
        val inputEmail: EditText = root.findViewById(R.id.inputEmail)

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
                val company = inputFirstName.text.toString()
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