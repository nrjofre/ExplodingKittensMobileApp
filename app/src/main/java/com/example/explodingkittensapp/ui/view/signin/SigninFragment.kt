package com.example.explodingkittensapp.ui.view.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.explodingkittensapp.APImodels.Bodies.APIUser
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.databinding.FragmentSigninBinding
import com.example.explodingkittensapp.ui.viewmodel.UserViewModel


class SigninFragment : Fragment() {

    private val viewModel: UserViewModel by activityViewModels()
    private lateinit var binding: FragmentSigninBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_signin, container, false)

        val signinbtn : Button = view.findViewById(R.id.signinbtn)
        val signinusername : EditText = view.findViewById(R.id.signinusername)
        val signinpassword : EditText = view.findViewById(R.id.signinpassword)
        val signinpassword2 : EditText = view.findViewById(R.id.signinrepeatpassword)
        val signinmail : EditText = view.findViewById(R.id.signinmail)


        signinbtn.setOnClickListener {
            val semail = signinmail.text.toString()
            val susername = signinusername.text.toString()
            val spassword = signinpassword.text.toString()
            val spassword2 = signinpassword2.text.toString()
            val stotal_matches = 0
            val swinrate = 100.0
            val sfriends = mutableListOf<String>()
            val scards = mutableListOf<String>()
            var swins = 0
            var sloses = 0
            var sdefuses = 0
            var sshuffles = 0
            var sattacks = 0
            var sskips = 0

            if(semail.isEmpty()){
                signinmail.error = "Email required"
                signinmail.requestFocus()
                return@setOnClickListener
            }
            if(susername.isEmpty()){
                signinusername.error = "Username required"
                signinusername.requestFocus()
                return@setOnClickListener
            }
            if(spassword.isEmpty()){
                signinpassword.error = "Password required"
                signinpassword.requestFocus()
                return@setOnClickListener
            }
            if(spassword2.isEmpty()){
                signinpassword2.error = "Confirm Password required"
                signinpassword2.requestFocus()
                return@setOnClickListener
            }
            if(spassword2 != spassword){
                signinpassword2.error = "Passwords do not Match"
                signinpassword2.requestFocus()
                return@setOnClickListener
            }
            val newAPIUser = APIUser(semail,susername,spassword,stotal_matches,swinrate,sfriends,scards,swins,sloses,sdefuses,sshuffles,sattacks,sskips)
            viewModel.addUserAPI(newAPIUser)
            Toast.makeText(activity, "User Created", Toast.LENGTH_LONG).show()
            Navigation.findNavController(view).navigate(R.id.action_signinFragment_to_loginFragment)

        }
        return view
    }
}