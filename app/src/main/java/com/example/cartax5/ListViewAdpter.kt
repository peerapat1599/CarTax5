package com.example.cartax5

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListViewAdpter(var context: Context?, var carId: ArrayList<HomeList>) : BaseAdapter() {
    private class ViewHolder(row: View?) {
        var txtCarId: TextView
        var txtDateExpire: TextView
        var txtprice: TextView

        init {
            this.txtCarId = row?.findViewById(R.id.txtCarId) as TextView
            this.txtDateExpire = row?.findViewById(R.id.txtDateExpire) as TextView
            this.txtprice = row?.findViewById(R.id.txtPrice) as TextView
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: ViewHolder
        if (convertView == null) {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.list_main, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var car: HomeList = getItem(position) as HomeList
        viewHolder.txtCarId.text = car.carId
        viewHolder.txtDateExpire.text = car.dateExpire
        viewHolder.txtprice.text = car.price

        return view as View

    }

    override fun getItem(position: Int): Any {
        return carId.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return carId.count();
    }

    fun updateReceiptsList(newlist:ArrayList<HomeList>){
        carId = newlist
        notifyDataSetChanged()
    }



}