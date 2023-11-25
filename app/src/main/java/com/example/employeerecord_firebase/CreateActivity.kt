package com.example.employeerecord_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.employeerecord_firebase.databinding.ActivityCreateBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateBinding
    private val dbReference = Firebase.firestore.collection("employee")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val firstName = binding.createFirstname.text.toString()
            val lastName = binding.createLasttname.text.toString()
            val age = binding.createAge.text.toString().toInt()
            val email = binding.createEmail.text.toString()
            val phoneNumber = binding.createPhonenumber.text.toString().toLong()
            val address = binding.createAddress.text.toString()
            val employee = EmployeeData(firstName, lastName, age, email, phoneNumber, address)
            saveEmployee(employee)
        }
    }

    private fun saveEmployee(employee: EmployeeData) {
        if (isFieldsEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        } else {
            dbReference.add(employee)
                .addOnSuccessListener {
                    clearInputFields()
                    Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Data Failed to save", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun isFieldsEmpty(): Boolean {
        return binding.createFirstname.text.isNullOrEmpty() ||
                binding.createLasttname.text.isNullOrEmpty() ||
                binding.createAge.text.isNullOrEmpty() ||
                binding.createEmail.text.isNullOrEmpty() ||
                binding.createPhonenumber.text.isNullOrEmpty() ||
                binding.createAddress.text.isNullOrEmpty()
    }

    private fun clearInputFields() {
        binding.createFirstname.text?.clear()
        binding.createLasttname.text?.clear()
        binding.createAge.text?.clear()
        binding.createEmail.text?.clear()
        binding.createPhonenumber.text?.clear()
        binding.createAddress.text?.clear()
    }


}