package com.livingcode.test.celotest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.livingcode.test.celotest.ui.userlist.UserListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            navigateTo(UserListFragment.newInstance())
        }
    }

    fun navigateTo(destination: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, destination)
            .addToBackStack(null)
            .commit()
    }
}
