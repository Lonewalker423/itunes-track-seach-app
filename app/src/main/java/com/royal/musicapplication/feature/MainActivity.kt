package com.royal.musicapplication.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.royal.musicapplication.R
import com.royal.musicapplication.core.base.BaseActivity
import com.royal.musicapplication.core.base.BaseFragment

class MainActivity : BaseActivity() {
    override fun layoutRes() = R.layout.activity_layout

    override fun fragment() = MusicListFragment()
}