package postexamination

import com.university.User
import examinationproject.Semester
import examinationproject.Status
import examinationproject.Student
import examinationproject.Subject

class StudentEvaluatedMarks {
    /*Saves evaluated marks,status of marks,grades of students for particular examination*/
    Subject subjectId
    String academicSession
    User user
    String marksSecuredAfterRule
    String grade
    Status status
    String isProcessed=false
    static belongsTo = [student:Student]
    static constraints = {
        isProcessed(nullable: true)
        marksSecuredAfterRule(nullable: true)
        grade(nullable: true)
        status(nullable: true)
        user(nullable: true)
    }
    static mapping ={
        subjectId column: 'SubjectId'
        academicSession column: 'academicSession'
        marksSecuredAfterRule column: 'marksSecuredAfterRule'
        isProcessed column: 'isProcessed'
        grade column: 'grade'
        status column: 'status'
        student column: "StudentId"
    }
}
