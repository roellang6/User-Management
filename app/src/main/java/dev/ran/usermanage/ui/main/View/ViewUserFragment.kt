package dev.ran.usermanage.ui.main.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import dev.ran.usermanage.R
import dev.ran.usermanage.ui.main.Adapter.UserviewAdapter
import dev.ran.usermanage.ui.main.ViewModel.UserViewModel
import kotlinx.android.synthetic.main.fragment_view_user.view.*

class ViewUserFragment : Fragment() {

    private lateinit var viewUsermodel: UserViewModel
    private var userAdapterdetail = UserviewAdapter()

    private lateinit var viewloading: ProgressBar
    private lateinit var userdetail: RecyclerView
    private lateinit var dowservertv: TextView

    private var iduser: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewU = inflater.inflate(R.layout.fragment_view_user, container, false)

        iduser = arguments?.getInt("userid")!!

        viewU.backicon.setOnClickListener {
            findNavController().navigate(R.id.action_viewUserFragment_to_homeFragment)
            //findNavController().navigate(0)
        }

        viewloading = viewU.findViewById(R.id.pbView)
        userdetail = viewU.findViewById(R.id.rvuserdetail)
        dowservertv = viewU.findViewById(R.id.noserverw)

        setupUi()
        setupViewModel()
        setUpObserver()

        return viewU
    }

    private fun setupViewModel() {
        viewUsermodel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewUsermodel.refreshUserFilter(iduser)
    }

    private fun setUpObserver() {
        viewUsermodel.livedUserfilter.observe(viewLifecycleOwner, { userdata ->
            if (userdata.isEmpty()) {
                dowservertv.visibility = View.VISIBLE
                dowservertv.alpha = 0f
                dowservertv.animate().setDuration(1500).alpha(1f).withEndAction {
                    userdetail.visibility = View.GONE
                    viewloading.visibility = View.GONE
                }
            } else {
                userdetail.visibility = View.VISIBLE
                userdetail.alpha = 0f
                userdetail.animate().setDuration(1500).alpha(1f).withEndAction {
                    dowservertv.visibility = View.GONE
                    viewloading.visibility = View.GONE
                }
                userAdapterdetail.viewUser(userdata)
            }
        })
        viewUsermodel.loaderrorfilter.observe(viewLifecycleOwner, Observer {
            dowservertv.visibility = if (it) View.VISIBLE else View.GONE
            if (it) {
                viewloading.visibility = View.GONE
                userdetail.visibility = View.GONE
            }
        })
    }
    private fun setupUi(){
        userdetail.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
        userAdapterdetail = UserviewAdapter()
        userdetail.adapter = userAdapterdetail
    }
}