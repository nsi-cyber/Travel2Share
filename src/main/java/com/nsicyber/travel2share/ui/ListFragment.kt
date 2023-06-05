package com.nsicyber.travel2share.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nsicyber.travel2share.Customs.CustomAdapter
import com.nsicyber.travel2share.Customs.NoteManager
import com.nsicyber.travel2share.R
import com.nsicyber.travel2share.Customs.handleClick
import com.nsicyber.travel2share.models.NoteModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var listView: ListView
    lateinit var floatingActionButton: FloatingActionButton
    var adapter: CustomAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            floatingActionButton = findViewById(R.id.floatingActionButton)
            listView = findViewById(R.id.listView)
        }
        getData()

        floatingActionButton.setOnClickListener {
            handleClick(this, R.id.enterNoteFragment, null)
        }

    }

    fun configure(data: List<NoteModel>) {
        adapter = CustomAdapter(requireContext(), data)
        adapter?.let {
            it.setOnItemLongClickListener { item ->
                showDialog(item)

            }
            it.setOnItemClickListener { model ->
                changePage(model)
            }

            listView.adapter = adapter
        }

    }

    fun getData() {
        val noteManager = NoteManager()

        noteManager.getAllNotes { notes ->
            configure(notes)

        }
    }

    fun changePage(data: NoteModel) {
        handleClick(this, R.id.detailFragment, data)
    }


    fun showDialog(note: NoteModel) {
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder.setTitle("Not Silme")
        dialogBuilder.setMessage(note.title + " başlıklı notu silmek istediğinize emin misiniz?")
        dialogBuilder.setPositiveButton("Evet") { dialogInterface: DialogInterface, _: Int ->
            deleteNoteFromDatabase(note)
            dialogInterface.dismiss()
        }
        dialogBuilder.setNegativeButton("Hayır") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    fun deleteNoteFromDatabase(note: NoteModel) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val notesRef = FirebaseDatabase.getInstance().reference
            .child("users")
            .child(currentUser?.uid ?: "")
            .child("notes")

        notesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (noteSnapshot in dataSnapshot.children) {
                    val title = noteSnapshot.child("title").getValue(String::class.java)
                    val location = noteSnapshot.child("location").getValue(String::class.java)
                    val noteText = noteSnapshot.child("note").getValue(String::class.java)

                    if (title == note.title && location == note.location && noteText == note.note) {
                        noteSnapshot.ref.removeValue()
                            .addOnSuccessListener {
                                Toast.makeText(
                                    requireContext(),
                                    "Silme işlemi başarılı",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                getData()
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    requireContext(),
                                    "Silme işlemi başarısız",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // İşlem iptal edildi veya hata oluştu
            }
        })
    }

            override fun onResume() {
                super.onResume()
                getData()
            }
        }

