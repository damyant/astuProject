package com.university

class TempLogin {
    /*saves rollno & password for new student users(before student registration)*/
    String rollNo
    String password
    static mapping = {
//        year defaultValue: 2014
    }
    static constraints = {
        rollNo(unique:true)

    }
}
