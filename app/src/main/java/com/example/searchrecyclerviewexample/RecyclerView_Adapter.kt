package com.example.searchrecyclerviewexample

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_row.view.*
import java.util.*
import kotlin.collections.ArrayList


class RecyclerView_Adapter(private var itemList: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    //此處加上Filterable的extensions就可以自行定義filter（getFilter()）

    var searchList = ArrayList<String>()

    lateinit var mcontext: Context

    class ListHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        searchList = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemListView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row, parent, false)
        val source = ListHolder(itemListView)
        mcontext = parent.context
        return source
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.search_TextView.setTextColor(Color.BLACK)
        holder.itemView.search_TextView.text = searchList[position]

        holder.itemView.setOnClickListener {
            val intent = Intent(mcontext, DetailsActivity::class.java)
            intent.putExtra("passhot", searchList[position])
            mcontext.startActivity(intent)
            Log.d("Selected:", searchList[position])
        }
    }


    override fun getFilter(): Filter {  //使用getFilter()時有兩個必須有的method: performFiltering 及 publishResults
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    searchList = itemList
                } else {
                    val resultList = ArrayList<String>()
                    for (row in itemList) {
                        if (row.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    searchList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = searchList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }

        }
    }

}