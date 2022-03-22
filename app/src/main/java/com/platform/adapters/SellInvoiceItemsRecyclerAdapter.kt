package com.platform.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.platform.R
import com.platform.adapters.SellInvoiceItemsRecyclerAdapter.MyViewHolder
import com.platform.pojo.sellInvoices.sellInvoice.SellInvoice

/**
 * Adapter dla recycler viewera dla umów
 * @author Rafał Pasternak
 **/
class SellInvoiceItemsRecyclerAdapter(
    var context: Context,
    var sellInvoice: SellInvoice = SellInvoice(),//orginalne dane

    private val listener:OnItemClickListener
) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.sell_invoice_items_item, parent, false)
        return MyViewHolder(view)
    }
    /**
     * Metoda przypisująca dane do widoku
     * @author Rafał Pasternak
     **/
    override fun onBindViewHolder(holder: SellInvoiceItemsRecyclerAdapter.MyViewHolder, position: Int) {

        if(sellInvoice?.items[position]?.name!=null)
            holder?.name?.text=sellInvoice?.items[position]?.name

        if(sellInvoice?.items[position]?.grossPrice!=null)
            holder?.grossTotal?.text= sellInvoice?.items[position]?.grossPrice.toString()

        if(sellInvoice?.items[position]?.netPrice!=null)
            holder?.netTotal?.text=sellInvoice?.items[position]?.netPrice.toString()

        if(sellInvoice?.items[position]?.unit?.name!=null)
            holder?.unit?.text=sellInvoice?.items[position]?.unit?.name

        if(sellInvoice?.items[position]?.vatValue!=null)
            holder?.vatRate?.text= sellInvoice?.items[position]?.vat?.value?.toInt()?.toString()+"%"

    }

    override fun getItemCount(): Int {
        return sellInvoice.items.size
    }


    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),View.OnClickListener{
        var name: TextView
        var grossTotal: TextView
        var netTotal: TextView
        var unit: TextView
        var vatRate: TextView

        init {
            name = itemView.findViewById(R.id.SIIL_Number_TV)
            grossTotal=itemView.findViewById(R.id.SIIL_GrossAmount_TV)
            netTotal=itemView.findViewById(R.id.SIIL_NetTotal_TV)
            unit=itemView.findViewById(R.id.SIIL_Unit_TV)
            vatRate=itemView.findViewById(R.id.SIIL_VatRate_TV)
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

}