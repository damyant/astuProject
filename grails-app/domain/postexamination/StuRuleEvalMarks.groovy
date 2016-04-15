package postexamination

import examinationproject.Student
import examinationproject.Subject

class StuRuleEvalMarks {
    /*Saves evaluated marks (rule and academic session)*/
    Subject subjectId
    Student student
    String academicSession
    String ruleName
    String evaluatedMarks
    static constraints = {
    }
}
