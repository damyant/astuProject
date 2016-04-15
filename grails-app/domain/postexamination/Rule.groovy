package postexamination

class Rule {
    /*saves details of rules like for which exam type, expression, variable of result*/
    String expression
    String examType
    String ruleNames
    String variableOfMaxInAll
    String variableOfResult

    static constraints = {
        ruleNames(nullable: true)
        examType(nullable: true)
        variableOfMaxInAll(nullable: true)
        variableOfResult(nullable: true)
        expression(unique: true)
    }
    static mapping ={
        ruleNames column: 'ruleNames'
        expression column: 'expression'
        examType column: 'examType'
        variableOfResult column: 'variableOfResult'
        variableOfMaxInAll column: 'variableOfMaxInAll'
    }
}
