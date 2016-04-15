package examinationproject

class AdmissionFee {
    int feeAmount
    int lateFeeAmount
    int term

    static belongsTo = [feeSession: FeeSession]
    static constraints = {
        feeAmount(nullable:true)
        lateFeeAmount(nullable:true)
        term(nullable:true)
    }
    static mapping = {
        feeAmount column:"FeeAmount"
        lateFeeAmount column: "LateFeeAmount"
        term column: "Term"
    }
}
