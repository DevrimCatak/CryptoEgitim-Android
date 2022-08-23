package com.devrimcatak.egitim.ui.lessons

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devrimcatak.egitim.R
import com.devrimcatak.egitim.databinding.ItemLessonsBinding
import com.devrimcatak.egitim.model.enums.LessonsCompletedType
import com.devrimcatak.egitim.model.lessons.Data


/**
 * Created by @Devrim Çatak on 4.06.2022.
 */
class LessonsAdapter (private val context: Context, private val lessonList: List<Data>, private val listener: ItemClickListener):
    RecyclerView.Adapter<LessonsAdapter.MViewHolder>() {

    class MViewHolder(private val binding: ItemLessonsBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, listener: ItemClickListener, lesson: Data){
            binding.onItemClickListener = listener
            binding.lesson = lesson
            binding.executePendingBindings()

            //binding.btnStart.setOnClickListener { listener.onItemClick(course) }

            when (lesson.completedType) {
                LessonsCompletedType.Complete -> {
                    binding.btnStart.text = context.getString(R.string.lessons_start_repeat)
                    binding.btnStart.isEnabled = true
                    binding.btnStart.setBackgroundResource(R.drawable.rounded_green)
                }
                LessonsCompletedType.OpenLessons -> {
                    binding.btnStart.text = context.getString(R.string.lessons_start)
                    binding.btnStart.isEnabled = true
                    binding.btnStart.setBackgroundResource(R.drawable.rounded_orange)
                }
                LessonsCompletedType.NotComplete -> {
                    binding.btnStart.text = context.getString(R.string.lessons_before_completed)
                    binding.btnStart.isEnabled = false
                    binding.btnStart.setBackgroundResource(R.drawable.rounded_orange_enabled)
                }
            }
        }

        companion object{
            fun from(parent: ViewGroup) :MViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLessonsBinding.inflate(layoutInflater,parent,false)
                return MViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        return MViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int){
        holder.bind(context, listener, lessonList[position])
    }

    override fun getItemCount() : Int{
        return lessonList.size
    }

}