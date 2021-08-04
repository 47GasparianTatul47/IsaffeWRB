package com.example.phoneAuth

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.architecture.MyViewModel
import com.example.isaffewrb.R
import com.example.isaffewrb.databinding.FragmentPhoneNumberBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PhoneNumberFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhoneNumberFragment : Fragment() {

    private lateinit var myViewModel: MyViewModel
    private lateinit var binding: FragmentPhoneNumberBinding


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPhoneNumberBinding.inflate(layoutInflater)

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PhoneNumberFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PhoneNumberFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.countryCodePicker.registerCarrierNumberEditText(binding.editTextPhone)

        val getCountryCode: String = binding.countryCodePicker.fullNumberWithPlus.replace(" ", "")

        binding.continueBtn.setOnClickListener {

            myViewModel.setUserNumber(binding.editTextPhone.text.toString(), getCountryCode)

            myViewModel.phoneVerification(context as Activity)

            myViewModel.liveData.observe(viewLifecycleOwner, {

                val bundle = bundleOf("verificationID" to it)

                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_phoneNumberFragment_to_SMSFragment, bundle)
            })
        }

    }


}