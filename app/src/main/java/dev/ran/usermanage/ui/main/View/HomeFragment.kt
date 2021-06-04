package dev.ran.usermanage.ui.main.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.ran.usermanage.R
import dev.ran.usermanage.data.Connection.AdapterClick
import dev.ran.usermanage.data.Repository.Session
import dev.ran.usermanage.ui.main.Adapter.UserAdapter
import dev.ran.usermanage.ui.main.ViewModel.UserViewModel

class HomeFragment : Fragment(), AdapterClick {

    private lateinit var homeviewmodel: UserViewModel
    private var userAdapter = UserAdapter(this)

    private lateinit var homeloading: ProgressBar
    private lateinit var userrecyclerView: RecyclerView
    private lateinit var tvconnection: TextView
    private lateinit var spinsetting: Spinner
    private var iduser : Int = 0
    private val bdata = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewH = inflater.inflate(R.layout.fragment_home, container, false)

        homeloading = viewH.findViewById(R.id.pbHome)
        userrecyclerView = viewH.findViewById(R.id.rvUserlist)
        tvconnection = viewH.findViewById(R.id.noserver)
        spinsetting = viewH.findViewById(R.id.spinSetting)

        val sessionCheck = Session(requireContext())
        spinsetting.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val setting = spinsetting.selectedItem.toString()
                if (setting == "Logout") {
                    sessionCheck.setUser("")
                    findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                }
            }
        }

        setupUi()
        setupViewModel()
        setUpObserver()

        return viewH

    }

    private fun setupViewModel() {
        homeviewmodel = ViewModelProvider(this).get(UserViewModel::class.java)
        homeviewmodel.refreshUser()
    }

    private fun setUpObserver() {
        homeviewmodel.livedUser.observe(viewLifecycleOwner, { dataUser ->
            if (dataUser != null) {
                userrecyclerView.visibility = View.VISIBLE
                userrecyclerView.alpha = 0f
                userrecyclerView.animate().setDuration(1500).alpha(1f).withEndAction {
                    homeloading.visibility = View.GONE
                    tvconnection.visibility = View.GONE
                    userAdapter.setUser(dataUser)
                }
            } else {
                tvconnection.visibility = View.VISIBLE
                tvconnection.alpha = 0f
                tvconnection.animate().setDuration(1500).alpha(1f).withEndAction {
                    homeloading.visibility = View.GONE
                    userrecyclerView.visibility = View.GONE
                }
            }
        })
        homeviewmodel.loaderrorUser.observe(viewLifecycleOwner, { loadError ->
            loadError?.let {
                tvconnection.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    homeloading.visibility = View.GONE
                    userrecyclerView.visibility = View.GONE
                }
            }
        })
    }

    private fun setupUi() {
        userrecyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        userAdapter = UserAdapter(this)
        userrecyclerView.adapter = userAdapter
    }

    override fun onclick(position: Int) {
        iduser = position + 1
        bdata.putInt("userid", iduser)

        findNavController().navigate(R.id.action_homeFragment_to_viewUserFragment, bdata)
    }
}