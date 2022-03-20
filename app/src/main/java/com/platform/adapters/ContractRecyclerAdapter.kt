package com.platform.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.platform.R
import com.platform.adapters.ContractRecyclerAdapter.MyViewHolder
import com.platform.pojo.contracts.Contracts
import java.sql.Date
import java.text.SimpleDateFormat

/**
 * Adapter dla recycler viewera dla umów
 * @author Rafał Pasternak
 **/
class ContractRecyclerAdapter(
    var context: Context,
    var contracts: Contracts = Contracts(),//orginalne dane

    private val listener:OnItemClickListener
    ) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.contracts_list_item, parent, false)
        return MyViewHolder(view)
    }
    /**
     * Metoda przypisująca dane do widoku
     * @author Rafał Pasternak
     **/
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(contracts.results[position].supplierNumber!=null)
            holder.number.text = contracts.results[position].supplierNumber
        if(contracts.results[position].endDate!=null)
            holder.date.text = convertLongToTime(contracts.results[position].endDate)
        if(contracts.results[position].title!=null)
            holder.title.text = contracts.results[position].title
        if(contracts.results[position].recipient.shortName!=null)
        holder.recipientName.text = contracts.results[position].recipient.shortName
        val color: Int = Color.rgb(248,156,20)
        if(contracts.results[position].color!=null)
            holder.background.setBackgroundColor(color)
    }

    override fun getItemCount(): Int {
        return contracts.results.size
    }


    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),View.OnClickListener{
        var date: TextView
        var title: TextView
        var number: TextView
        var recipientName: TextView
        var background: ConstraintLayout
        init {
            date = itemView.findViewById(R.id.IL_Date_TV)
            title = itemView.findViewById(R.id.IL_Title_TV)
            number = itemView.findViewById(R.id.CIL_Name_TV)
            recipientName = itemView.findViewById(R.id.IL_RecipientName_TV)
            background = itemView.findViewById(R.id.IL_Layout_TV)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position=adapterPosition
            if(position!=RecyclerView.NO_POSITION){
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
    /**
     *Metoda konwertująca datę w formacie Long do formatu czytelnego dla użytkownika
     * @param time data w formacie long
     * @return data w formacie ludzkim
     **/
    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.format(date)
    }
}