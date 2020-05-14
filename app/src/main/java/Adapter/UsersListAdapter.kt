package Adapter

import Model.User
import ViewHolder.UserViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tema2androiddb.R
import org.w3c.dom.Text

public class UsersListAdapter(private val userList: ArrayList<User>) :RecyclerView.Adapter<UserViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserViewHolder(inflater, parent)
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user: User = userList[position]
        holder.bind(user)
    }

   /* inner class UserViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(
        inflater.inflate(R.layout.fragment_user_fragment, parent, false)){

        var firstName: TextView ?= null
        var lastName: TextView ?= null

        init {
            firstName = itemView.findViewById(R.id.tv_user_first_name)
            lastName = itemView.findViewById(R.id.tv_user_last_name)
        }

        fun bind(user: User){
            firstName?.text = user.firstName
            lastName?.text = user.lastName
        }
    }*/
}