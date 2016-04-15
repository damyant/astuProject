package IST

class ProgramBranch {
    /*Saves Program Branch Details & Mapping between Branch and Department*/
     String name
     String code
     Department department

    static constraints = {
        name(nullable:true)
        code(nullable:true)
        department(nullable:true)
    }
}
