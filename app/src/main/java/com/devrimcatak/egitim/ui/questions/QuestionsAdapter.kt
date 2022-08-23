package com.devrimcatak.egitim.ui.questions

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Switch
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devrimcatak.egitim.R
import com.devrimcatak.egitim.databinding.ItemLessonsBinding
import com.devrimcatak.egitim.databinding.ItemQuestionsBinding
import com.devrimcatak.egitim.model.enums.QuestionAnswersType
import com.devrimcatak.egitim.model.questions.Answer


/**
 * Created by @Devrim Çatak on 4.06.2022.
 */
class QuestionsAdapter (private val context: Context, private val lessonList: List<Answer>, private val listener: ItemClickListener):
    RecyclerView.Adapter<QuestionsAdapter.MViewHolder>() {

    class MViewHolder(private val binding: ItemQuestionsBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, listener: ItemClickListener, answer: Answer){
            binding.onItemClickListener = listener
            binding.answers = answer
            binding.executePendingBindings()

            if (answer.answersType != null) {
                when (answer.answersType) {
                    QuestionAnswersType.Review -> {
                        binding.llAnswer.background = context.getDrawable(R.drawable.rounded_white_10)
                        binding.textAnswer.setTextColor(ContextCompat.getColor(context,R.color.black))
                    }
                    QuestionAnswersType.Wrong -> {
                        binding.llAnswer.background = context.getDrawable(R.drawable.rounded_red_10)
                        binding.textAnswer.setTextColor(ContextCompat.getColor(context,R.color.white))
                    }
                    QuestionAnswersType.True -> {
                        binding.llAnswer.background = context.getDrawable(R.drawable.rounded_green_10)
                        binding.textAnswer.setTextColor(ContextCompat.getColor(context,R.color.white))
                    }
                }
            }


        }

        companion object{
            fun from(parent: ViewGroup) :MViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemQuestionsBinding.inflate(layoutInflater,parent,false)
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