package dev.ran.usermanage.ui.main.View

import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dev.ran.usermanage.R
import dev.ran.usermanage.data.Model.UserTableModel
import dev.ran.usermanage.ui.main.ViewModel.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment : Fragment(), View.OnClickListener {

    private lateinit var regviewmodel: UserViewModel
    private lateinit var regUserviewmodel: UserLocalViewModel

    private lateinit var tvloginvl: TextView
    private lateinit var SpnrSecretqstn: Spinner
    private lateinit var SpnrCountry: Spinner

    private val paths = arrayOf(
        "What is your Pet Name?",
        "What is your Favorite color?",
        "What is your Father's  Name?",
        "What is your Favorite Movie?",
        "What is your Maiden Name?",
        "What is your Mother Name?"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewR = inflater.inflate(R.layout.fragment_register, container, false)

        viewR.tvLogin.setOnClickListener(this)
        viewR.btnRegister.setOnClickListener(this)

        tvloginvl = viewR.findViewById(R.id.tvLogin)
        SpnrSecretqstn = viewR.findViewById(R.id.spnSecretquestion)
        SpnrCountry = viewR.findViewById(R.id.spnCountry)

        setupUi()
        setupViewModel()
        setUpObserver()

        return viewR
    }

    private fun setupViewModel(){
        regviewmodel = ViewModelProvider(this).get(UserViewModel::class.java)
        regviewmodel.refreshCountry()
        regUserviewmodel = ViewModelProvider(this).get(UserLocalViewModel::class.java)
    }
    private fun setUpObserver(){
        regviewmodel.livedCountry.observe(viewLifecycleOwner, { listcounry ->
            if (listcounry != null) {
                val adapterCountry = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    listcounry
                )
                adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                SpnrCountry.setAdapter(adapterCountry)
            }
        })
    }

    private fun setupUi() {
        val adapterSpinner = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            paths
        )
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SpnrSecretqstn.setAdapter(adapterSpinner)

        tvloginvl.paintFlags = Paint.UNDERLINE_TEXT_FLAG

    }

    override fun onClick(v: View?) {
        when (v) {
            tvLogin -> {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
            btnRegister -> {
                val fullname = etfullname.text.toString()
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                val country = SpnrCountry.selectedItem.toString()
                val secretquestion = SpnrSecretqstn.selectedItem.toString()
                val secretanswer = etSecretAnswer.text.toString()
                if (inputCheck(fullname, username, password, secretanswer)) {
                    if (password.equals(etconfpassword.text.toString())) {
                        insertUser( fullname, username, password, country, secretquestion, secretanswer)
                    }else{
                        etconfpassword.setError("Confirm password not match")
                    }
                } else {
                    Toast.makeText(requireContext(), "Please provide required fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun insertUser(name: String, user: String, pass: String, country: String, secretquestion: String, secretanswer: String){
        val userData = UserTableModel(0, name, user, pass, country, secretquestion, secretanswer)
        pbRegister.visibility = View.VISIBLE
        pbRegister.alpha = 0f
        pbRegister.animate().setDuration(1500).alpha(1f).withEndAction {
            regUserviewmodel.addUser(userData)
            Toast.makeText(requireContext(), "Successfully Register!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
    private fun inputCheck(fullname: String, username: String, password: String, secretanswer: String) : Boolean{
        val datareg : Boolean
        if(TextUtils.isEmpty(fullname) && TextUtils.isEmpty(username) ){
            datareg = false
        }else{
            datareg = !(TextUtils.isEmpty(password) && TextUtils.isEmpty(secretanswer))
        }
        return datareg
    }
}