package com.example.todotitan.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.todotitan.R
import com.example.todotitan.databinding.AddTaskBottomSheetBinding
import com.example.todotitan.ui.viewModel.TaskListVM
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddNewTaskBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var binding: AddTaskBottomSheetBinding
    private val viewModel : TaskListVM by activityViewModels()


    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddTaskBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
    }


    private fun setOnClickListener() {
        binding.addBT.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addBT -> {
                if(binding.descriptionET.text.toString().trim().isEmpty()){
                    Toast.makeText(requireActivity(),
                        getString(R.string.please_enter_the_task),Toast.LENGTH_SHORT).show()
                }else{
                    viewModel.addTask(binding.descriptionET.text.toString().trim())
                    binding.descriptionET.setText("")
                }
            }
        }
    }
}