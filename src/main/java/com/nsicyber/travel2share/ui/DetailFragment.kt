package com.nsicyber.travel2share.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.nsicyber.travel2share.R
import com.nsicyber.travel2share.models.NoteModel
import com.nsicyber.travel2share.Customs.parse


class DetailFragment : Fragment() {

    var model: NoteModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            model = parse(it.getSerializable("data"))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
            )


        with(view){
            findViewById<TextView>(R.id.editTitle).text=model!!.title
            findViewById<TextView>(R.id.editLocation).text=model!!.location
            findViewById<TextView>(R.id.editNote).text=model!!.note
        }

    }

}