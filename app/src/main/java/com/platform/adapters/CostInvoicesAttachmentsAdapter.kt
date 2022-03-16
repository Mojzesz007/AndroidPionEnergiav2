package com.platform.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.platform.R
import com.platform.adapters.CostInvoicesAttachmentsAdapter.MyViewHolder
import com.platform.pojo.costInvoice.attachments.Attachments
import java.sql.Date
import java.text.SimpleDateFormat

/**
 * Adapter dla recycler viewera dla umów
 * @author Rafał Pasternak
 **/
class CostInvoicesAttachmentsAdapter(
    var context: Context,
    var attachments: Attachments = Attachments(),//orginalne dane

    private val listener:OnItemClickListener
) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cost_invoice_attachments_item, parent, false)
        return MyViewHolder(view)
    }
    /**
     * Metoda przypisująca dane do widoku
     * @author Rafał Pasternak
     **/
    override fun onBindViewHolder(holder: CostInvoicesAttachmentsAdapter.MyViewHolder, position: Int) {
        if(attachments.attachments[position].updatedAt!=null)
            holder.date.text = attachments.attachments[position].updatedAt.toString()
        if(attachments.attachments[position].size!=null){
            //holder.size.text=(attachments.attachments[position].size/1 000 000).toString()
            var size:Double= attachments.attachments[position].size.toDouble()
            size /= 1000000
            val rounded = String.format("%.2f", size)
            holder.size.text=rounded+" Mb"
        }

        if(attachments.attachments[position].filename!=null)
            holder.title.text=attachments.attachments[position].filename.toString()
    }

    override fun getItemCount(): Int {
        return attachments.attachments.size
    }


    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),View.OnClickListener{
        var date: TextView
        var title: TextView
        var size: TextView
        var delateIcone : ImageView
        init {
            date = itemView.findViewById(R.id.CL_Date_TV)
            title = itemView.findViewById(R.id.CL_Title_TV)
            size=itemView.findViewById(R.id.CL_Size_TV)
            delateIcone=itemView.findViewById(R.id.CL_Delate_IV)
            itemView.setOnClickListener(this)
            delateIcone.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            var type=0;
            if (v != null) {
                if(v.id==delateIcone.id){
                    type=1;
                }else
                    type=0
            }
            val position=adapterPosition
            if(position!=RecyclerView.NO_POSITION){
                listener.onItemClick(position,type)}
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
        fun onItemClick(position: Int,type: Int)

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