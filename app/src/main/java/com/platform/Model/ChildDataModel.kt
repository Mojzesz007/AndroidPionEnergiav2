package com.platform.Model

import android.util.Log

class ChildDataModel(id: Int, country: String?, image: Int) {
    var id: Long = 0
    var title: String? = null
    var image = 0
    fun setId(id: Int) {
        this.id = id.toLong()
    }

    override fun toString(): String {
        Log.d("response ", "ID: $id Title: $title")
        return super.toString()
    }

    init {
        setId(id)
        title = country
        this.image = image
    }
}