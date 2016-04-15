package examinationproject

class ProgramSession {
    /*Saves session of program(maps between program detail and session of program)*/
    ProgramDetail programDetailId
    String sessionOfProgram

    static hasMany = [semester: Semester]
    static constraints = {
        sessionOfProgram(nullable: true)
    }


    static mapping = {
        sessionOfProgram column: "SessionOfProgram"
        programDetailId column: "ProgramDetailId"
        semester cascade:"all,delete-orphan"
    }
}
