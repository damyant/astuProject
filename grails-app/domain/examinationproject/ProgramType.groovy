package examinationproject

class ProgramType {
    /*for saving program type and program type code*/

    String type
    String code

    static constraints = {
        type{nullable:true}
        code{nullable:true}
    }
}
