package com.nsicyber.travel2share.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.nsicyber.travel2share.Customs.NoteManager
import com.nsicyber.travel2share.R
import com.nsicyber.travel2share.models.NoteModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EnterNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EnterNoteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var editTitle: EditText
    lateinit var editLocation: EditText
    lateinit var editNote: EditText
    lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_enter_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

        with(view) {
            editTitle = findViewById(R.id.editTitle)
            editLocation = findViewById(R.id.editLocation)
            saveButton = findViewById(R.id.saveButton)
            editNote = findViewById(R.id.editNote)
        }

        saveButton.setOnClickListener {
            if (checkIsNull()) {
                saveData(
                    NoteModel(
                        title = editTitle.text.toString(),
                        location = editLocation.text.toString(),
                        note = editNote.text.toString()
                    )
                )
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Fill", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun saveData(data: NoteModel) {
        val noteManager = NoteManager()
        noteManager.createNote(data)

    }

    fun checkIsNull(): Boolean {
        return (editLocation.text.toString().length > 0
                && editNote.text.toString().length > 0 &&
                editTitle.text.toString().length > 0)
    }


}