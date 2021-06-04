package dev.ran.usermanage.ui.main.Adapter

import android.content.Context
import android.view.*
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import dev.ran.usermanage.R
import dev.ran.usermanage.data.Model.*
import kotlinx.android.synthetic.main.viewuserlayout.view.*

class UserviewAdapter : RecyclerView.Adapter<UserviewAdapter.ViewDataHolder>() {

    private var userdlit = emptyList<UserModel>()
    private lateinit var context : Context

    class ViewDataHolder(vd: View) : RecyclerView.ViewHolder(vd) {
        val vduser = vd.tvUsername
        val vdname = vd.tvfullname
        val vdemail = vd.tvemailv
        val vdaddress = vd.tvaddressv
        val vdphonenum = vd.tvphonenumv
        val vdwebsite = vd.tvwebsitev
        val vdcompany = vd.tvcompanyv

        fun bindUserdetail(userd: UserModel) {
            vduser.text = userd.username

            vdname.text = userd.name
            vdemail.text = userd.email

            vdphonenum.text = userd.phone
            vdwebsite.text = userd.website

            val datauseraddress: Address = userd.address
            val addressstr: String = datauseraddress.suite + " " + datauseraddress.street + " " + datauseraddress.city + "\n zipcode: " + datauseraddress.zipcode
            vdaddress.text = addressstr

            val datausercompany: Company = userd.company
            val companydetail: String = "Name : "+datausercompany.name + "\nPhrase : " + datausercompany.catchPhrase + "\nBS : " + datausercompany.bs
            vdcompany.text = companydetail

       //     val mGoogleMap: GoogleMap = (getChildFragmentManager().findFragmentById(R.id.map) as SupportMapFragment).getMap()

            val datausergeo : Geo = datauseraddress.geo



           /* lateinit var googleMap : GoogleMap

            var mapFragment : FragmentContainerView= vdmap


            mapFragment = googleMap*/

         /*   lateinit var googleMap : GoogleMap
           *//* val mapFragment = vdmap as SupportMapFragment
            mapFragment.getMapAsync(context)


            vdmap = googleMap*//*
            //  userselectmap.getlanglong()
            // Add a marker in Sydney and move the camera
            val sydney = LatLng(datausergeo.lat.toDouble(), datausergeo.lng.toDouble())
            googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewDataHolder(
        LayoutInflater.from(
            parent.context
        ).inflate(R.layout.viewuserlayout, parent, false)
    )

    override fun onBindViewHolder(holder: ViewDataHolder, position: Int) {
        holder.bindUserdetail(userdlit.get(position))
    }

    override fun getItemCount(): Int {
        return userdlit.size
    }

    fun viewUser(datauser: List<UserModel>){
        this.userdlit = datauser
        notifyDataSetChanged()
    }
}