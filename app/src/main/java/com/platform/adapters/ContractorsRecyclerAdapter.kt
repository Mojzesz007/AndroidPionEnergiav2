package com.platform.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.platform.R
import com.platform.adapters.ContractorsRecyclerAdapter.MyViewHolder
import com.platform.pojo.contractors.Contractors

class ContractorsRecyclerAdapter(
    var context: Context,
    var contractors: Contractors = Contractors(),//orginalne dane

    private val listener:OnItemClickListener
) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.contractor_list_item, parent, false)
        return MyViewHolder(view)
    }
    /**
     * Metoda przypisująca dane do widoku
     * @author Rafał Pasternak
     **/

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text=contractors.results[position].name
        if(contractors.results[position].isCompany==false)
        holder.icon.setImageResource(R.drawable.outline_person_black_36)

    }

    override fun getItemCount(): Int {
        return contractors.results.size
    }


    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),View.OnClickListener{
        var name: TextView
        var icon: ImageView

        init {
            name = itemView.findViewById(R.id.CIL_Name_TV)
            icon = itemView.findViewById(R.id.CIL_Icon_IV)
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