package examinationproject

import postexamination.SubjectMarksDetail

class SubjectSession {
    /*Maps between subject and session*/
    Subject subjectId
    String sessionOfSubject
    Boolean isOptional
    Boolean isProjectSubject=false
    static hasMany = [subjectMarksDetail:SubjectMarksDetail]
    static constraints = {
        sessionOfSubject(nullable: true)
    }


    static mapping = {
        isOptional defaultValue: false
        isProjectSubject defaultValue: false
        sessionOfSubject column: "SessionOfProgram"
        subjectId column: "SubjectId"
        subjectMarksDetail cascade:"all,delete-orphan"
        isOptional column: "isOptional"
    }
}