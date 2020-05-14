package Fragment

import Model.User
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.example.tema2androiddb.R
import com.google.gson.JsonObject
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user_fragment.*
import org.json.JSONArray
import org.json.JSONObject


class User_fragment : Fragment() {

    private var users_recyclerView: RecyclerView? = null;
    private val realm by lazy { Realm.getDefaultInstance() }
    private val userList = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Realm.init(this.context!!)
        val config = RealmConfiguration.Builder().name("Users.realm").build()
        Realm.setDefaultConfiguration(config)

        btn_add_user.setOnClickListener {
            addUser(et_first_name.text.toString(), et_last_name.text.toString())
            refreshUsers()
            rv_users_list.adapter?.notifyDataSetChanged()
            Toast.makeText(activity, "User added", Toast.LENGTH_SHORT).show()
        }

        btn_remove_user.setOnClickListener {
            removeUser(et_first_name.text.toString(), et_last_name.text.toString())
            refreshUsers()
            rv_users_list.adapter?.notifyDataSetChanged()
            Toast.makeText(activity, "User deleted", Toast.LENGTH_SHORT).show()
        }

        btn_sync.setOnClickListener {
            getUploadedList()
            refreshUsers()
            rv_users_list.adapter?.notifyDataSetChanged()
            Toast.makeText(activity, "List added", Toast.LENGTH_SHORT).show()
        }


        rv_users_list.apply {
            refreshUsers()
            this.layoutManager = LinearLayoutManager(this.context)
            this.adapter = Adapter.UsersListAdapter(this@User_fragment.userList)
        }
    }

    fun addUser(firstName: String, lastName: String) {
        realm.executeTransaction {
            val lastId = realm.where(User::class.java).max("id")
            if (lastId == null) {
                val user = realm.createObject(User::class.java, 0)
                user.firstName = firstName
                user.lastName = lastName
            } else {
                val user = realm.createObject(User::class.java, (lastId.toLong() + 1))
                user.firstName = firstName
                user.lastName = lastName
            }
        }
    }


    fun removeUser(firstName: String, lastName: String) {
        realm.executeTransaction {
            val user = realm.where(User::class.java)
                .equalTo("firstName", firstName)
                .equalTo("lastName", lastName)
                .findAll()

            for (user in user) {
                user.deleteFromRealm()
            }
        }
    }

    fun getAllUsers(): ArrayList<User> {
        val userList = realm.where(User::class.java)
            .findAll()

        val list = ArrayList<User>()

        for (user in userList) {
            list.add(user)
        }
        return list
    }

    fun refreshUsers() {
        userList.clear()
        userList.addAll(getAllUsers())
    }

    fun getUploadedList() {
        val queue = Volley.newRequestQueue(context)
        val url = "https://jsonplaceholder.typicode.com/users"

        val getCommentsRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener { response ->
                handlerUsersResponse(response)
            },
            Response.ErrorListener { error ->
                Toast.makeText(context, "Error! get comments failed ", Toast.LENGTH_SHORT).show()

            }
        )
        queue.add(getCommentsRequest)
    }

    fun handlerUsersResponse(response: String) {
        val userJsonArray = JSONArray(response)

        for (index in 0 until userJsonArray.length()) {
            val userJson: JSONObject? = userJsonArray[index] as JSONObject

            userJson?.let {
                val firstNameJ = userJson.getString("name")
                val lastNameJ = userJson.getString("username")
                val idJ = userJson.getString("id")

                realm.executeTransaction {
                    val lastId = realm.where(User::class.java).max("id")
                    if (lastId == null) {
                        val user = realm.createObject(User::class.java, 0)
                        user.firstName = firstNameJ
                        user.lastName = lastNameJ
                    } else {
                        val user = realm.createObject(User::class.java,lastId.toLong() + 1)
                        user.firstName = firstNameJ
                        user.lastName = lastNameJ
                    }
                }
            }
            refreshUsers()
        }
    }
}
