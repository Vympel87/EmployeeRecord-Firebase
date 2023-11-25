package com.example.employeerecord_firebase

data class EmployeeData(
    var firstName: String = "",
    var lastName: String = "",
    var age: Int = -1,
    var email: String = "",
    var phoneNumber: Long = -1,
    var address: String = "",
)
