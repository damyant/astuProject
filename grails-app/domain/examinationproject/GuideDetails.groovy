package examinationproject

import IST.Employee
import IST.ProgramBranch

class GuideDetails {
    /*Save(assign) details of project guide*/
    ProgramBranch programBranch
    String academicSession
    Employee employee
    static constraints = {
    }
    static mapping = {
        programBranch column: "programBranch"
        employee column: "employee"
        academicSession column: "academicSession"
    }
}
