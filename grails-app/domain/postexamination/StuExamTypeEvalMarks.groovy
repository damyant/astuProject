package postexamination

import examinationproject.Student
import examinationproject.Subject

class StuExamTypeEvalMarks {
    /*Saves evaluated marks of particular exam type and academic session*/
    Subject subjectId
    Student student
    String academicSession
    String examTypeName
    String evaluatedMarks
    static constraints = {
    }
}
