package dev.ran.usermanage.ui.main.View

import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dev.ran.usermanage.R
import dev.ran.usermanage.data.Repository.Session
import dev.ran.usermanage.ui.main.ViewModel.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var logviewmodel: UserViewModel
    private lateinit var logUserviewmodel: UserLocalViewModel

    private lateinit var tvregistervl: TextView

    private lateinit var spin_Country: Spinner
    private lateinit var chck_Remember: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewL = inflater.inflate(R.layout.fragment_login, container, false)

        viewL.btnLoginl.setOnClickListener(this)
        viewL.tvForgotpasswordl.setOnClickListener(this)
        viewL.tvRegisterl.setOnClickListener(this)

        tvregistervl = viewL.findViewById(R.id.tvRegisterl)

        spin_Country = viewL.findViewById(R.id.spnCountryl)
        chck_Remember = viewL.findViewById(R.id.chckRemember)

        setupUi()
        setupViewModel()
        setUpObserver()

        return viewL
    }

    private fun setupViewModel() {
        logviewmodel = ViewModelProvider(this).get(UserViewModel::class.java)
        logviewmodel.refreshCountry()
        logUserviewmodel = ViewModelProvider(this).get(UserLocalViewModel::class.java)
    }

    private fun setUpObserver() {
        logviewmodel.livedCountry.observe(viewLifecycleOwner, { listcounry ->
            if (listcounry != null) {
                val adapterCountry =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listcounry)
                adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spin_Country.setAdapter(adapterCountry)
            }
        })
    }

    private fun setupUi() {
        tvregistervl.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    override fun onClick(v: View?) {
        when (v) {
            btnLoginl -> {
                val user = etUsernamel.text.toString()
                val pass = etPasswordl.text.toString()

                if (inputCheckLogin(user, pass)) {
                    checkData(user, pass)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please provide required fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            tvForgotpasswordl -> {
                Toast.makeText(requireContext(), "Forgot Password", Toast.LENGTH_SHORT).show()
            }
            tvRegisterl -> {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    private fun inputCheckLogin(user: String, pass: String): Boolean {
        val datalog: Boolean
        if (TextUtils.isEmpty(user)) {
            datalog = false
        } else {
            datalog = !TextUtils.isEmpty(pass)
        }
        return datalog
    }

    private fun checkData(user: String, pass: String) {
        val sessionCheck = Session(requireContext())
        logUserviewmodel.validateUser(user, pass)?.observe(this, Observer {
            if (it == null) {
                Toast.makeText(
                    requireContext(),
                    "The username and password you entered did not match our records.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                pbLogin.visibility = View.VISIBLE
                pbLogin.alpha = 0f
                pbLogin.animate().setDuration(1500).alpha(1f).withEndAction {
                    if (chck_Remember.isChecked) {
                        sessionCheck.setUser(user)
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
            }
        })
    }
}