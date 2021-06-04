package dev.ran.usermanage.ui.main.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ran.usermanage.R
import dev.ran.usermanage.data.Connection.AdapterClick
import dev.ran.usermanage.data.Model.UserModel
import kotlinx.android.synthetic.main.userlayout.view.*

class UserAdapter (var adapterclick : AdapterClick): RecyclerView.Adapter<UserAdapter.DataHolder>() {

    private var userlist = emptyList<UserModel>()

    class DataHolder(v: View, var itemClick: AdapterClick) : RecyclerView.ViewHolder(v), View.OnClickListener {
        val vuser = v.tvUsername
        val vname = v.tvfullname
        fun bindDataUser(data: UserModel) {
            vuser.text = data.username
            vname.text = data.name
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemClick.onclick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.userlayout, parent, false
        ), adapterclick
    )

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.bindDataUser(userlist.get(position))
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    fun setUser(userSet: List<UserModel>){
        this.userlist = userSet
        notifyDataSetChanged()
    }
}