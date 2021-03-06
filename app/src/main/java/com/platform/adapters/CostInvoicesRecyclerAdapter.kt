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
import com.platform.adapters.CostInvoicesRecyclerAdapter.MyViewHolder
import com.platform.pojo.costInvoices.CostInvoices
import java.sql.Date
import java.text.SimpleDateFormat

/**
 * Adapter dla recycler viewera dla umów
 * @author Rafał Pasternak
 **/
class CostInvoicesRecyclerAdapter(
    var context: Context,
    var costInvoices: CostInvoices = CostInvoices(),//orginalne dane

    private val listener:OnItemClickListener
) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.sell_invoice_list_item, parent, false)
        return MyViewHolder(view)
    }
    /**
     * Metoda przypisująca dane do widoku
     * @author Rafał Pasternak
     **/
    override fun onBindViewHolder(holder: CostInvoicesRecyclerAdapter.MyViewHolder, position: Int) {
        if(costInvoices.results[position].foreignNumber!=null)
            holder.number.text = costInvoices.results[position].foreignNumber
        if(costInvoices.results[position].issueDate!=null)
            holder.date.text = convertLongToTime(costInvoices.results[position].issueDate)
        if(costInvoices.results[position].grossTotal!=null)
            holder.grossAmount.text = costInvoices.results[position].grossTotal.toString()
        if(costInvoices.results[position]?.contractor?.shortName!=null)
            holder.contractorName.text = costInvoices.results[position].contractor.shortName
        if(costInvoices.results[position].currency.name!=null)
            holder.currency.text = costInvoices.results[position].currency.name
        val color: Int = Color.rgb(230,230,230)
        if(position%2==1)
            holder.background.setBackgroundColor(color)
    }

    override fun getItemCount(): Int {
        return costInvoices.results.size
    }


    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),View.OnClickListener{
        var date: TextView
        var grossAmount: TextView
        var contractorName: TextView
        var currency: TextView
        var number: TextView
        var background: ConstraintLayout
        init {
            date = itemView.findViewById(R.id.SL_Date_TV)
            number = itemView.findViewById(R.id.SIIL_Number_TV)
            grossAmount=itemView.findViewById(R.id.SIIL_GrossAmount_TV)
            contractorName=itemView.findViewById(R.id.SIIL_Ammount_TV)
            currency=itemView.findViewById(R.id.SL_Currency_TV)
            background = itemView.findViewById(R.id.SL_Layout_TV)
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