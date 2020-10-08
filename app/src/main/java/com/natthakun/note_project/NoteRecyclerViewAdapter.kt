package com.natthakun.note_project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.natthakun.note_project.data.Note
import com.natthakun.note_project.databinding.ListItemBinding

class NoteRecyclerViewAdapter(private val clickListener:(Note)->Unit)
    :RecyclerView.Adapter<ViewHolder>()
{

    private val noteList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //TODO("Not yet implemented")
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ListItemBinding =
            DataBindingUtil.inflate(layoutInflater,R.layout.list_item,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(noteList[position],clickListener)
    }

    fun setList(subscribers: List<Note>) {
        noteList.clear()
        noteList.addAll(subscribers)

    }

}

class ViewHolder(val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(note: Note, clickListener:(Note)->Unit){
        binding.titleTextView.text= note.title
        binding.descripTextView.text = note.des
        binding.listItemLayout.setOnClickListener{
            clickListener(note)
        }
    }
}