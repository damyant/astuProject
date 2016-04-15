package examinationproject

class StudentAdmitCard {
    Student student
    String examinationSession
    Semester semester
    Date date

    static constraints = {
        student(nullable: false)
        examinationSession(nullable:false)
        semester(nullable: false)
        date(nullable: false)
    }

    static mapping = {
        student column: 'student'
        examinationSession column: 'examinationSession'
        semester column: 'semester'
        date column: 'date'
    }
}
