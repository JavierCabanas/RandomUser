package me.javicabanas.randomuser.androidcommons.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun ViewGroup.inflate(layout: Int, root: ViewGroup = this, attachtToRoot: Boolean = false): View =
    LayoutInflater.from(this.context).inflate(layout, root, attachtToRoot)
