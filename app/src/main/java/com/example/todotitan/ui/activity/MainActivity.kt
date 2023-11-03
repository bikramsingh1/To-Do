package com.example.todotitan.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.todotitan.R
import com.example.todotitan.ui.adapters.AdapterTaskList
import com.example.todotitan.databinding.ActivityMainBinding
import com.example.todotitan.ui.dialogs.AddNewTaskBottomSheet
import com.example.todotitan.ui.viewModel.TaskListVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var taskListAdapter: AdapterTaskList

    private val viewModel: TaskListVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
        setObserver()
        setOnClickListener()
    }


    private fun setAdapter() {
        taskListAdapter = AdapterTaskList()
        binding.taskListRV.adapter = taskListAdapter

        // handle adapter click
        taskListAdapter.adapterTaskDeleteCallback = { taskId ->
            viewModel.deleteTask(taskId)
        }

        taskListAdapter.adapterTaskMarkCompleteCallback = { taskId, isComplete ->
            viewModel.markTaskAsComplete(taskId, isComplete)
        }
    }

    private fun setObserver() {
        lifecycleScope.launch {
            viewModel.taskListEvent.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    if(it.size > 0){
                        taskListAdapter.setData(it)
                        binding.noDataFoundGroup.visibility = View.GONE
                        binding.taskListRV.visibility = View.VISIBLE
                    }else{
                        binding.noDataFoundGroup.visibility = View.VISIBLE
                        binding.taskListRV.visibility = View.GONE

                    }
                }
        }
    }

    private fun setOnClickListener() {
        binding.addTaskBT.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addTaskBT -> {
                val dialog = AddNewTaskBottomSheet()
                dialog.show(supportFragmentManager, "")
            }
        }
    }

}