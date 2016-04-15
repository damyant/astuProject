package examinationproject

import postexamination.SubjectMarksDetail

class Subject {
    /*Saves details of subject*/

    String subjectCode
    String subjectName
    Integer lecture
    Integer tutorial
    Integer practical
    Boolean isOptional=false
    Integer creditPoints
    Integer contactHours
    Boolean isProjectSubject=false


//    static hasMany = [subjectMarksDetail:SubjectMarksDetail]

    static mapping = {
//        id column: "SubjectId"
        isOptional defaultValue: false
        isProjectSubject defaultValue: false
        subjectName column: "SubjectName"
        subjectCode column: "SubjectCode"
        lecture column: "Lecture"
        tutorial column: "Tutorial"
        practical column: "Practical"
        creditPoints column: 'CreditPoints'
        contactHours column: "ContactHours"
        isOptional column: "isOptional"
//        totalMarks column: 'TotalMarks'



    }

    static constraints = {
        subjectName(nullable: false)
        subjectCode (nullable:false, unique: true)
        creditPoints (nullable: false)

    }
}
