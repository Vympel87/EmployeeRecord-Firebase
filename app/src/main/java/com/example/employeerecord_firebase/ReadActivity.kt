package com.example.employeerecord_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeerecord_firebase.databinding.ActivityReadBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadBinding
    private val dbReference = Firebase.firestore.collection("employee")
    private val employeeList = mutableListOf<EmployeeData>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.employee_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        retrieveEmployee()
    }

    private fun retrieveEmployee() {
        dbReference.get()
            .addOnSuccessListener { querySnapshot ->
                employeeList.clear()
                val snapshots = mutableListOf<DocumentSnapshot>()
                for (document in querySnapshot.documents) {
                    val employee = document.toObject<EmployeeData>()
                    employee?.let {
                        employeeList.add(it)
                        snapshots.add(document)
                    }
                }
                adapter = EmployeeAdapter(employeeList, snapshots)
                recyclerView.adapter = adapter
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@ReadActivity, e.message, Toast.LENGTH_SHORT).show()
            }
    }
}


