package examinationproject

import IST.ProgramBranch

class ProjectDomain {
    /*Saves project domain and maps between programBranch and projectDomain*/
    ProgramBranch programBranch
    String domainName
    static constraints = {
    }
    static mapping = {
        programBranch column: "programBranch"
        domainName column: "domainName"
    }
}
