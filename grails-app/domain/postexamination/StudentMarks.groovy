package postexamination

import com.university.User
import examinationproject.Status
import examinationproject.Student
import examinationproject.Subject
class StudentMarks {
    /*This domain saves students marks and marks status(approve/unapproved) for particular examination(subject,examType,examSubType) and academic session*/
    Subject subjectId
    String academicSession
    String examination
    String totalMarks
    Status status
    User user
    Boolean isRejected=false


    static belongsTo = [student:Student]
    static constraints = {
        user(nullable: true)
        totalMarks(nullable: true)
    }
    static mapping ={
        isRejected defaultValue: false
        user column: 'user'
        subjectId column: 'SubjectId'
        academicSession column: 'academicSession'
        student column: "StudentId"
        examination column: "examination"
        totalMarks column: "totalMarks"
        status column: "status"
    }
}
