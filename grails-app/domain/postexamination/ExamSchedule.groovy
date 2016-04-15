package postexamination

import examinationproject.CourseSubject
import examinationproject.ProgramDetail
import examinationproject.ProgramSession
import examinationproject.Semester
import examinationproject.SubjectSession

class ExamSchedule {
    ProgramDetail programDetail
    SubjectSession subjectSession
    Semester semester
    ProgramSession programSession
    Date examDate
    String examTime
    String year


    static constraints = {
        examDate (nullable: true)
        examTime (nullable: true)
    }

    static mapping = {
        programDetail column: "programDetail"
        subjectSession column: "subjectSession"
        semester column: "semester"
        programSession column: "programSession"
        examDate column: "examDate"
        examTime column: "examTime"
        year column: "year"
    }

}
