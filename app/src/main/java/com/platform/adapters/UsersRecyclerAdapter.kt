package com.platform.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.platform.R
import com.platform.pojo.employees.Employees

class UsersRecyclerAdapter (

    var context: Context,
    var users: Employees = Employees(),

    private val listener:OnItemClickListener
) : RecyclerView.Adapter<UsersRecyclerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.user_list_item, parent, false)
        return MyViewHolder(view)
    }
    /**
     * Metoda przypisująca dane do widoku
     * @author Rafał Pasternak
     **/

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text=users.results[position].name+" "+users.results[position].surname

    }

    override fun getItemCount(): Int {
        return users.results.size
    }


    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var name: TextView


        init {
            name = itemView.findViewById(R.id.UIL_Name_TV)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position=adapterPosition
            if(position!= RecyclerView.NO_POSITION){
                listener.onItemClick(position)}
        }
    }
    /**
     * Interfejs implementujący możliwość kliknięcia na element
     * @author Rafał Pasternak
     **/
    interface OnItemClickListener{
        /**
         *Metoda intefejsu pozwalająca zidentyfikować kliknięty element
         * @param position pozycja kliknietego elementu
         **/
        fun onItemClick(position: Int)
    }
}