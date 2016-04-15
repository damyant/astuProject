package examinationproject

import IST.Department

class Hod {
    String name
    String email
    Department department

    static constraints = {
        name(nullable:false)
        email (nullable: false,unique: true)
    }
}
