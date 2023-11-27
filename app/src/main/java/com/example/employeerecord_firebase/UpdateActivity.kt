package com.example.employeerecord_firebase

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.employeerecord_firebase.databinding.ActivityUpdateBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private val db = Firebase.firestore
    private val collectionReference = db.collection("employee")
    private lateinit var currentEmployeeId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentEmployeeId = intent.getStringExtra("employee_id") ?: ""

        displayOldData()

        binding.btnUpdate.setOnClickListener {
            updateData()
        }
    }

    private fun displayOldData() {
        collectionReference.document(currentEmployeeId).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val currentEmployee = documentSnapshot.toObject(EmployeeData::class.java)
                    currentEmployee?.let {
                        binding.updateFirstname.setText(it.firstName)
                        binding.updateLasttname.setText(it.lastName)
                        binding.updateAge.setText(it.age.toString())
                        binding.updateEmail.setText(it.email)
                        binding.updatePhonenumber.setText(it.phoneNumber.toString())
                        binding.updateAddress.setText(it.address)
                    }
                }
            }
            .addOnFailureListener {

            }
    }

    private fun updateData() {
        val updatedData = getUpdatedData()

        collectionReference.document(currentEmployeeId)
            .update(updatedData)
            .addOnSuccessListener {
                Toast.makeText(this, "Data successfully updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Data failed to update", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getUpdatedData(): Map<String, Any> {
        val firstName = binding.updateFirstname.text.toString()
        val lastName = binding.updateLasttname.text.toString()
        val age = binding.updateAge.text.toString().toInt()
        val email = binding.updateEmail.text.toString()
        val phoneNumber = binding.updatePhonenumber.text.toString().toLong()
        val address = binding.updateAddress.text.toString()

        val updatedData = HashMap<String, Any>()
        updatedData["firstName"] = firstName
        updatedData["lastName"] = lastName
        updatedData["age"] = age
        updatedData["email"] = email
        updatedData["phoneNumber"] = phoneNumber
        updatedData["address"] = address

        return updatedData
    }
}
