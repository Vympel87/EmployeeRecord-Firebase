package com.example.employeerecord_firebase

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore

class EmployeeAdapter(private var employees: List<EmployeeData>, private val snapshots: List<DocumentSnapshot>) :
    RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstNameTextView: TextView = itemView.findViewById(R.id.first_name)
        val lasttNameTextView: TextView = itemView.findViewById(R.id.last_name)
        val ageTextView: TextView = itemView.findViewById(R.id.age)
        val emailTextView: TextView = itemView.findViewById(R.id.email)
        val phoneNumberTextView: TextView = itemView.findViewById(R.id.phone_number)
        val addressTextView: TextView = itemView.findViewById(R.id.address)
        val updateButton: Button = itemView.findViewById(R.id.update_btn)
        val deleteButton: Button = itemView.findViewById(R.id.delete_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_item, parent, false)
        return EmployeeViewHolder(view)
    }

    override fun getItemCount(): Int = employees.size

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employees[position]
        val documentSnapshot = snapshots[position]
        holder.firstNameTextView.text = employee.firstName
        holder.lasttNameTextView.text = employee.lastName
        holder.ageTextView.text = employee.age.toString()
        holder.emailTextView.text = employee.email
        holder.phoneNumberTextView.text = employee.phoneNumber.toString()
        holder.addressTextView.text = employee.address
        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("employee_data", employee)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener {
            val context = holder.itemView.context
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle("Konfirmasi")
            alertDialogBuilder.setMessage("Hapus data ini?")
            alertDialogBuilder.setPositiveButton("Ya") { _, _ ->
                employees = employees.filterIndexed { index, _ -> index != position }
                notifyItemRemoved(position)
                deleteData(documentSnapshot, context)
                Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
            alertDialogBuilder.setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    private fun deleteData(documentSnapshot: DocumentSnapshot, context: Context) {
        val db = Firebase.firestore
        val collectionReference = db.collection("employee")

        documentSnapshot.reference
            .delete()
            .addOnSuccessListener {
                Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Data gagal dihapus", Toast.LENGTH_SHORT).show()
            }
    }
}
