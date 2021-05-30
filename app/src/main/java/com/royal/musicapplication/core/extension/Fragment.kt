package com.royal.musicapplication.core.extension

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.royal.musicapplication.R
import com.royal.musicapplication.core.base.BaseActivity
import com.royal.musicapplication.core.base.BaseFragment

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

val BaseFragment<*>.viewContainer: View get() = (activity as BaseActivity).findViewById(R.id.fragmentContainer)

val BaseFragment<*>.appContext: Context get() = activity?.applicationContext!!