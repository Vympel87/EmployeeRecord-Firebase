package com.example.employeerecord_firebase

import java.io.Serializable

data class EmployeeData(
    var firstName: String = "",
    var lastName: String = "",
    var age: Int = -1,
    var email: String = "",
    var phoneNumber: Long = -1,
    var address: String = "",
) : Serializable
