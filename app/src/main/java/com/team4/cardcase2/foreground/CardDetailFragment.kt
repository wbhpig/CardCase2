package com.team4.cardcase2.foreground

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.team4.cardcase2.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CardDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardDetailFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_card_detail, container, false)

        val showName: TextView = root.findViewById(R.id.showName)
        val showName2: TextView = root.findViewById(R.id.showName2)
        val showCompany: TextView = root.findViewById(R.id.showCompany)
        val showPhone: TextView = root.findViewById(R.id.showPhone)
        val showPhone2: TextView = root.findViewById(R.id.showPhone2)
        val showEmail: TextView = root.findViewById(R.id.showEmail)
        val showEmail2: TextView = root.findViewById(R.id.showEmail2)
        val showAddress2: TextView = root.findViewById(R.id.showAddress2)

        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CardDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CardDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}