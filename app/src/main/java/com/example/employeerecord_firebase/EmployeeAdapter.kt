package com.example.employeerecord_firebase

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmployeeAdapter(private var employees: List<EmployeeData>) :
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
        holder.firstNameTextView.text = employee.firstName
        holder.lasttNameTextView.text = employee.lastName
        holder.ageTextView.text = employee.age.toString()
        holder.emailTextView.text = employee.email
        holder.phoneNumberTextView.text = employee.phoneNumber.toString()
        holder.addressTextView.text = employee.address

    }
}
