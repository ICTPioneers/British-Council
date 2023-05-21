package com.example.british_council.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.british_council.R
import com.example.british_council.databinding.ListItemTextBinding
import com.example.british_council.helper.App.Companion.context
import com.example.british_council.model.Text

class TextAdapter(val paymentList: ArrayList<Text>) : RecyclerView.Adapter<TextAdapter.AdapterViewHolder>(){
    private var currentPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val itemBinding = ListItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdapterViewHolder(itemBinding, paymentList)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val paymentBean: Text = paymentList[position]
        Log.e("qqqq-amin-2", "onBindViewHolder: $position" )
        holder.bind(paymentBean,position, this.currentPosition?:0)
    }

    override fun getItemCount(): Int = paymentList.size

    class AdapterViewHolder(private val itemBinding: ListItemTextBinding,
                            private val list: ArrayList<Text>) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Text,position: Int, currentPos: Int) {
            itemBinding.textViewInListview.text = item.text

            Log.e("qqqq-amin-3", "bind: $currentPos" )
            if (currentPos == position){
                itemBinding.textViewInListview.setTextColor(context.resources.getColor(R.color.green_main))
            }else{
                itemBinding.textViewInListview.setTextColor(context.resources.getColor(R.color.black))
            }
        }
    }


    fun getCurrentPos(audioSec: Int){
        try {
            for (i in 0 until paymentList.size) {
//            App.toast("index $x")
                if (audioSec in paymentList[i].start_time!!..paymentList[i].end_time!!) {
                    val lasPos = this.currentPosition?:0
                    this.currentPosition = i
                    this.notifyItemChanged(lasPos, paymentList[lasPos])
                    this.notifyItemChanged(audioSec, paymentList[i])

                    Log.e("qqqq-amin", "getCurrentPos: $currentPosition - $lasPos" )
                    return
                }
            }
        }catch (e : java.lang.Exception){
            Log.e("qqqq-error", "getCurrentPos: ${e.message}",e )
        }
    }

}
