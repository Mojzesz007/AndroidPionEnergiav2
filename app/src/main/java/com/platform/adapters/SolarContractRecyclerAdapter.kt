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
import com.platform.pojo.solarContracts.SolarContracts
import java.sql.Date
import java.text.SimpleDateFormat

class SolarContractRecyclerAdapter(
    var context: Context,
    var solarContracts: SolarContracts = SolarContracts(),//orginalne dane

    private val listener:OnItemClickListener
) : RecyclerView.Adapter<SolarContractRecyclerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.solar_contracts_list_item, parent, false)
        return MyViewHolder(view)
    }
    /**
     * Metoda przypisująca dane do widoku
     * @author Rafał Pasternak
     **/
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(solarContracts.results[position].number!=null)
            holder.number.text = solarContracts.results[position].number

        if(solarContracts.results[position].recipient.shortName!=null)
            holder.recipientName.text = solarContracts.results[position].recipient.shortName

        if(solarContracts.results[position].date!=null)
            holder.date.text = convertLongToTime(solarContracts.results[position].date)

        val color: Int = Color.rgb(230,230,230)
        if(position%2==1)
            holder.background.setBackgroundColor(color)
    }

    override fun getItemCount(): Int {
        return solarContracts.results.size
    }


    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var date: TextView
        var number: TextView
        var recipientName: TextView
        var background: ConstraintLayout
        init {
            date = itemView.findViewById(R.id.SCIL_Date_TV)
            number = itemView.findViewById(R.id.SCIL_Name_TV)
            recipientName = itemView.findViewById(R.id.SCIL_RecipientName_TV)

            background = itemView.findViewById(R.id.SCIL_Layout_TV)
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