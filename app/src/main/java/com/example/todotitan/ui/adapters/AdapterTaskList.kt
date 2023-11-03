package com.example.todotitan.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todotitan.databinding.AdapterTaskListBinding
import com.example.todotitan.ui.models.TaskModel

class AdapterTaskList : RecyclerView.Adapter<TaskViewHolder>() {

    var adapterTaskDeleteCallback: ((taskId: Int) -> Unit)? = null
    var adapterTaskMarkCompleteCallback: ((taskId: Int,isComplete: Boolean) -> Unit)? = null
    private var oldList: ArrayList<TaskModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = AdapterTaskListBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        setUpdatedView(holder.binding as AdapterTaskListBinding, oldList[position])
    }

    @SuppressLint("SetTextI18n")
    private fun setUpdatedView(
        binding: AdapterTaskListBinding,
        data: TaskModel
    ) {
        binding.taskTitleCB.isChecked = data.isCompleted
        binding.taskTitleCB.text = data.taskDescription
        binding.taskTitleCB.setOnCheckedChangeListener{ view,check->
            if(view.isPressed){
               adapterTaskMarkCompleteCallback?.invoke(data.id,check)
            }
        }

        binding.deleteIV.setOnClickListener {
            adapterTaskDeleteCallback?.invoke(data.id)
        }

    }

    fun setData(newList: ArrayList<TaskModel>) {
        val diffResult = DiffUtil.calculateDiff(
            TaskListDiffUtil(oldList, newList),
            true
        )
        oldList = ArrayList(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}
class TaskViewHolder(var binding: ViewDataBinding?) : RecyclerView.ViewHolder(binding?.root!!)


class TaskListDiffUtil constructor(
    private val oldList: List<TaskModel>,
    private val newList: List<TaskModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.size == newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].isCompleted == newList[newItemPosition].isCompleted
    }
}