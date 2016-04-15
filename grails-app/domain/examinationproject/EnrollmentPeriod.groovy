package examinationproject

class EnrollmentPeriod {
    /*Saves details of enrollment period for semester registration*/
    ProgramDetail programDetail
    Semester semester
    Date startDate
    Date endDate
    String admissionSession
    String studentAdmissionYear
    static mapping = {
        programDetail column: "programDetail"
        studentAdmissionYear column: "studentAdmissionYear"
        semester column: "semester"
        startDate column: "startDate"
        endDate column: "endDate"
        admissionSession column: "admissionSession"
    }
    static constraints = {
        programDetail(nullable: false)
        studentAdmissionYear(nullable: false)
        semester(nullable: false)
        startDate(nullable: false)
        endDate(nullable: false)
        admissionSession(nullable: false)
    }
}
