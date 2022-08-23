package com.devrimcatak.egitim.ui.courses

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devrimcatak.egitim.R
import com.devrimcatak.egitim.databinding.ItemCoursesBinding
import com.devrimcatak.egitim.model.courses.Data
import com.devrimcatak.egitim.model.enums.CourseStatusType


/**
 * Created by @Devrim Çatak on 31.05.2022.
 */
class CoursesAdapter (private val context: Context, private val courseList: List<Data>, private val listener: ItemClickListener):
    RecyclerView.Adapter<CoursesAdapter.MViewHolder>() {

    class MViewHolder(private val binding: ItemCoursesBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, listener: ItemClickListener, course: Data){
            binding.onItemClickListener = listener
            binding.course = course
            binding.executePendingBindings()
            try {
                binding.animationView.setAnimationFromUrl(course.banner)
            } catch (e : Exception) {
                Log.d("Lottie","Lottie",e);
            }

            //binding.btnStart.setOnClickListener { listener.onItemClick(course) }

            if (course.status == CourseStatusType.Live.id){
                binding.btnStart.text = context.getString(R.string.courses_start)
                binding.btnStart.isEnabled = true
                binding.btnStart.setBackgroundResource(R.drawable.rounded_orange)
            } else {
                binding.btnStart.text = context.getString(R.string.courses_soon)
                binding.btnStart.isEnabled = false
                binding.btnStart.setBackgroundResource(R.drawable.rounded_orange_enabled)
            }
        }

        companion object{
            fun from(parent: ViewGroup) :MViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCoursesBinding.inflate(layoutInflater,parent,false)
                return MViewHolder(binding)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        return MViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int){
        holder.bind(context, listener, courseList[position])
    }

    override fun getItemCount() : Int{
        return courseList.size
    }

}