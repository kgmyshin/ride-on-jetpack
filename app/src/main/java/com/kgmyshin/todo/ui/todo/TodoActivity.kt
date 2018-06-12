package com.kgmyshin.todo.ui.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.kgmyshin.todo.R

class TodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        setSupportActionBar(findViewById(R.id.toolbar))

        val navController = findNavController(
                R.id.nav_host_fragment
        )
        // TODO: navigation-ui-ktxが androidxの方に拡張関数を生やしたら対応する
        setupActionBarWithNavController(
                this,
                navController
        )
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
}