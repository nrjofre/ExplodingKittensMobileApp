package com.example.explodingkittensapp.activities

interface AdapterView {
    fun addItem(item: Any)
    val onClickListener: OnClickListener
}