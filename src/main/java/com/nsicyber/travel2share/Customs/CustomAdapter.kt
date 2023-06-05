package com.nsicyber.travel2share.Customs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.nsicyber.travel2share.R
import com.nsicyber.travel2share.models.NoteModel

class CustomAdapter(context: Context, private val data: List<NoteModel>) : ArrayAdapter<NoteModel>(context, 0, data) {

    private var onItemLongClickListener: ((NoteModel) -> Unit)? = null

    fun setOnItemLongClickListener(listener: (NoteModel) -> Unit) {
        onItemLongClickListener = listener
    }
    private var onItemClickListener: ((NoteModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (NoteModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            holder = ViewHolder()
            holder.titleText = view.findViewById(R.id.titleText)
            holder.locationText = view.findViewById(R.id.locationText)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val item = data[position]
        holder.titleText?.text = item.title
        holder.locationText?.text = item.location

        view?.setOnLongClickListener {
            onItemLongClickListener?.invoke(item)
            true
        }
        view?.setOnClickListener {
            onItemClickListener?.invoke(item)
            true
        }

        return view!!
    }

    private class ViewHolder {
        var titleText: TextView? = null
        var locationText: TextView? = null
    }
}
