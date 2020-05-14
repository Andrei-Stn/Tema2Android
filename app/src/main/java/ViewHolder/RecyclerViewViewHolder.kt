package ViewHolder

import Model.User
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tema2androiddb.R

class UserViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_user,parent,false)) {
    private var firstName: TextView? = null
    private var lastName: TextView? = null

    init{
        firstName=itemView.findViewById(R.id.tv_user_first_name)
        lastName=itemView.findViewById(R.id.tv_user_last_name)
    }

    fun bind(user: User) {
        firstName?.text=user.firstName
        lastName?.text=user.lastName
    }
}