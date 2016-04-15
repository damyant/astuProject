package postexamination

import com.university.User
import examinationproject.Subject

class Examination {
    /*Saves examination details(like subject, examType, examSubType,marks etc)*/
    Subject subject
    String examTypes
    String examSubType
    String academicSession
    String totalMarks
    User user
    static constraints = {
        user(nullable: true)
        examSubType(nullable: true)
    }
    static mapping = {
        subject column: "subject"
        examTypes column: 'examType'
        examSubType column: "examSubType"
        academicSession column: "academicSession"
        totalMarks column: "totalMarks"
    }
}
