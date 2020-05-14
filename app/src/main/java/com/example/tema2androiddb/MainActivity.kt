package com.example.tema2androiddb

import Fragment.User_fragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToFragment()
    }

    fun goToFragment()
    {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragment = User_fragment()
        fragmentTransaction.add(R.id.recycle_fragment, fragment)
        fragmentTransaction.commit()
    }
}
