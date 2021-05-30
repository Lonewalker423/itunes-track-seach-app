package com.royal.musicapplication.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.royal.musicapplication.R
import com.royal.musicapplication.core.extension.inTransaction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    protected abstract fun layoutRes(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
        setSupportActionBar(findViewById(R.id.toolbar))
        addFragment(savedInstanceState)
    }


    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(R.id.fragmentContainer) as BaseFragment<*>).onBackPressed()
        super.onBackPressed()
    }

    private fun addFragment(savedInstanceState: Bundle?) =
        savedInstanceState ?: supportFragmentManager.inTransaction {
            add(
                R.id.fragmentContainer,
                fragment()
            )
        }

    abstract fun fragment(): BaseFragment<*>
}
