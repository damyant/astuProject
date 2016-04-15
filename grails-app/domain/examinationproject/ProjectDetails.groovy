package examinationproject

import IST.Department
import IST.Employee

class ProjectDetails {
    /*Saving project details and mapping among project,subject and student*/
    Subject subject
    Student student
    Department department
    String academicSession
    String projectTitle
    String domain
    String nameOfInstitution
    String guideId
    Status status
    static constraints = {
    }


    static mapping = {
        subject column: "subject"
        department column: "department"
        student column: "student"
        academicSession column: "academicSession"
        projectTitle column: "projectTitle"
        domain column: "domain"
        nameOfInstitution column: "nameOfInstitution"
        guideId column: "guideId"
        status column: "status"
    }
}
