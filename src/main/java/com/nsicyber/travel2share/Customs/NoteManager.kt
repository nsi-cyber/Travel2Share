package com.nsicyber.travel2share.Customs

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nsicyber.travel2share.models.NoteModel


// Notları yönetmek için bir sınıf
class NoteManager {

    private val database = FirebaseDatabase.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val notesRef = database.reference.child("users").child(currentUser?.uid ?: "").child("notes")

    // Yeni bir not oluşturma
    fun createNote(note: NoteModel) {
        val noteId = notesRef.push()

        noteId.key?.let {
            notesRef.child(it).setValue(note)
                .addOnSuccessListener {
                    // Not oluşturma başarılı
                }
                .addOnFailureListener {
                    // Not oluşturma başarısız
                }
        }
    }

    fun getAllNotes(callback: (List<NoteModel>) -> Unit) {
        notesRef.get()
            .addOnSuccessListener { snapshot ->
                val noteList = mutableListOf<NoteModel>()
                for (childSnapshot in snapshot.children) {
                    val note = childSnapshot.getValue(NoteModel::class.java)
                    note?.let {
                        noteList.add(it)
                    }
                }
                callback(noteList)
            }
            .addOnFailureListener {
                // Notları alma başarısız
                callback(emptyList())
            }
    }
}