package com.example.petme.ui.signup

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.petme.R
import com.example.petme.common.Resource
import com.example.petme.data.model.User
import com.example.petme.databinding.FragmentSignUp2Binding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding:FragmentSignUp2Binding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding= FragmentSignUp2Binding.inflate(inflater)

        initObservers()

        binding.button.setOnClickListener {
          if (checkAllFields()){
              viewModel.signUpWithEmailAndPassword(
                  User( email = binding.editTextTextEmailAddress3.text.toString(),
                      nickname = binding.editTextTextPersonName2.text.toString(),
                      phoneNumber = binding.editTextPhone2.text.toString()),
               binding.editTextTextPassword4.text.toString()
         )

            }
        }

        binding.Signing.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment2)

        }


        // Inflate the layout for this fragment
        return binding.root
    }





    private fun initObservers() {

        with(viewModel) {

            checkCurrentUser.observe(viewLifecycleOwner) {
                if (it!!) findNavController().navigate(R.id.action_signUpFragment_to_addpet)
            }

            result.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        Log.v("Success","Success")
                       binding.statusLoadingWheel.visibility =View.GONE
                        findNavController().navigate(R.id.action_signUpFragment_to_signInFragment2)
                        Log.v("Success","Success")
                        Toast.makeText(context, "SignUP Success", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        binding.statusLoadingWheel.visibility =View.GONE
                        Toast.makeText(context, "SignUP error", Toast.LENGTH_SHORT).show()
                        Log.v("error","SignUP error")

                    }
                    is Resource.Loading-> {
                        binding.statusLoadingWheel.visibility = View.VISIBLE


                    }
                    else -> {}
                }
            }
        }
    }



    private fun checkAllFields():Boolean {
        with(binding){
            if (editTextTextEmailAddress3.text.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(
                    editTextTextEmailAddress3.text
                ).matches()) {
                editTextTextEmailAddress3.error = "please enter a valid email"
                return false
            }

            if (editTextTextPersonName2.text.isNullOrEmpty()) {
                editTextTextPersonName2.error = "please enter a valid name"
                return false
            }

            if (editTextTextPassword4.text.isNullOrEmpty()|| editTextTextPassword4.length() < 6) {
               editTextTextPassword4.error = "Password must be minimum 6 characters"
                return false
            }

            if (editTextTextPassword5.text.isNullOrEmpty() ) {
                editTextTextPassword5.error = "Password is required"
                return false
            } else if (editTextTextPassword5.length() < 6) {
                editTextTextPassword5.error = "Password must be minimum 6 characters"
                return false
            } else if (editTextTextPassword5.text.toString()!= editTextTextPassword4.text.toString()) {
                editTextTextPassword5.error = "Password must be the same"
                return false
            }
            if (editTextPhone2.text.isNullOrEmpty() || editTextPhone2.length()<11) {
                editTextPhone2.error = "phone must be 11 number"
                return false
            }



            // after all validation return true.
            return true
        }


    }




}