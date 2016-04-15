package postexamination

class RuleNames {
    /*Saves rule's detail like rule name and it's requirement*/
    String rule
    Boolean ruleRequired
    String sequenceNo
    String maxInAllStudentsSymbol
    String ruleTotalMarksSecuredSymbol
    static constraints = {
        sequenceNo(nullable: true)
        maxInAllStudentsSymbol(nullable: true)
        ruleTotalMarksSecuredSymbol(nullable: true)
    }
    static mapping = {
        rule column: 'rule'
        ruleRequired column: 'ruleRequired'
        sequenceNo column: 'sequenceNo'
        maxInAllStudentsSymbol column: 'maxInAllStudentsSymbol'
        ruleTotalMarksSecuredSymbol column: 'ruleTotalMarksSecuredSymbol'
    }
}
