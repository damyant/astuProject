package postexamination

class RuleSymbols {
    /*For saving rule symbols & their details(for which exam type rule symbol is created)*/
    String symbol
    String examType
    String ruleNames
    String text
    Boolean isEditable

    static constraints = {
        ruleNames(nullable: true)
        examType(nullable: true)
        symbol(unique: true)
    }
    static mapping ={
        ruleNames column: 'ruleNames'
        symbol column: 'Symbol'
        examType column: 'examType'
        text column: "text"
    }
}
