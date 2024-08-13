package com.example.testapp.ui.main.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testapp.databinding.AddNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddNoteSheetFragment(private val onNoteAdded: (String) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var views : AddNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        views = AddNoteBinding.inflate(inflater, container, false)
        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews(){
        views.apply {
            addNoteBtn.setOnClickListener {
                val typedNote = noteText.editText?.text.toString()
                if(typedNote.isNotBlank()){
                    onNoteAdded(typedNote)
                    dismiss()
                }
            }
        }
    }
}