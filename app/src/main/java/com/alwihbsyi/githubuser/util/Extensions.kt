package com.alwihbsyi.githubuser.util

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

inline fun <FRAGMENT : Fragment> FRAGMENT.putArgs(argsBuilder: Bundle.() -> Unit): FRAGMENT = this.apply { arguments = Bundle().apply(argsBuilder) }
