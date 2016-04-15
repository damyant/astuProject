package examinationproject

import IST.ProgramBranch

class ProgramDetail {
    /*save details of program(like name,type,code,branch etc) */

    String courseName
    String courseCode
    ProgramType programType
    CourseMode courseMode
    ProgramBranch programBranch
    int noOfTerms
    int noOfAcademicYears
    int noOfPapers
    int totalMarks
    int marksPerPaper
    int totalCreditPoints
    Date lateFeeDate
    int admissionTerm
    int finalTerm=12
    Date startAdmission_D
    int admissionYear=0
    Date endAdmission_D
    Boolean isPartTime=false
    static hasMany = [student: Student]
    static belongsTo = [Student]
    static mapping = {
        isPartTime defaultValue: false
        admissionTerm defaultValue: 1
        finalTerm defaultValue: 12
        id column: "CourseId"
        courseName column: "CourseName"
        admissionTerm column: "admissionTerm"
        finalTerm column: "finalTerm"
        admissionYear column: "AdmissionYear"
        courseCode column: "CourseCode"
        programType column: "ProgramType"
        courseMode column: "CourseMode"
        noOfTerms column: "NoOfTerms"
        noOfAcademicYears column: "NoOfAcademicYears"
        noOfPapers column: "NoOfPapers"
        totalMarks column: "TotalMarks"
        marksPerPaper column: "MarksPerPaper"
        totalCreditPoints column: "TotalCreditPoints"
        lateFeeDate column: "LateFeeDate"
        endAdmission_D column: "endAdmission_D"
        startAdmission_D column: "startAdmission_D"


    }

    static constraints = {
        courseName(nullable: false)
        isPartTime(nullable: false)
        admissionTerm(nullable: false)
        finalTerm(nullable: false)
        courseCode(nullable:false)
        programType(nullable:true)
        courseMode(nullable: false)
        noOfTerms(nullable: false)
        noOfAcademicYears(nullable: false)
        noOfPapers(nullable: false)
        totalMarks(nullable: false)
        marksPerPaper(nullable: false)
        totalCreditPoints(nullable:false)
        lateFeeDate(nullable: true)
        endAdmission_D(nullable: true)
        startAdmission_D(nullable: true)
        admissionYear(nullable: true)
        programBranch(nullable: true)
    }
}
