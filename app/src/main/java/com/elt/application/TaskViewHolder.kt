package com.elt.application

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.task_row_item.view.*

class TaskViewHolder(taskView: View) : RecyclerView.ViewHolder(taskView) {
    val nameLabel: TextView
    val descriptionLabel: TextView
    val typeIconImageView: ImageView

    init {
        nameLabel = taskView.name
        descriptionLabel = taskView.description
        typeIconImageView = taskView.task_type
    }
}