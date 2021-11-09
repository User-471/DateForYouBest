package com.dateforyou.best.ui.splash.choose.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dateforyou.best.R
import com.dateforyou.best.models.Card
import kotlinx.android.synthetic.main.item_card.view.*
import java.io.File

class KolodaAdapter(val context: Context, val data: List<Card>) : BaseAdapter() {

//    val dataList = mutableListOf<Card>()

//    init {
//        if (data != null) {
//            dataList.addAll(data)
//        }
//    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Card {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<Card>) {
        data.clear()
        data.addAll(data)
        notifyDataSetInvalidated()
    }

//    fun toWrong(position: Int) {
//        data!![position + 1].type = 1
//        notifyDataSetChanged()
////        setData(dataList)
//    }
//
//    fun toRight(position: Int) {
//        data!![position + 1].type = 2
//        notifyDataSetChanged()
////        setData(dataList)
//    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val holder: DataViewHolder
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
            holder = DataViewHolder(view)
            view?.tag = holder
        } else {
            holder = view.tag as DataViewHolder
        }

        holder.bindData(context, getItem(position), position, data.size)

        return view
    }

    /**
     * Static view items holder
     */
    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var wrong = view.cl_wrong
        var right = view.cl_right
        var image = view.iv_photo
        var name = view.tv_name

        internal fun bindData(context: Context, data: Card, position: Int, size: Int) {

//            image.setImageDrawable(data)
            name.text = data.name

            Glide.with(context)
                .load(data.image)
                .centerCrop()
                .into(image)
        }

    }
}