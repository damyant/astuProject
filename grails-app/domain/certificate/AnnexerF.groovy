package certificate

class AnnexerF {
    /*Save format for Certificate stating details fee for applying Bank Loan*/
    String refNo
    String fromName
    String fromPosition
    String dateShown
    Date date
    String header
    String paragraph1
    String paragraph2
    String paragraph3
    String signatureName
    String firstSemFee
    String firstYearFee
    String secondSemFee
    String secondYearFee
    String thirdSemFee
    String thirdYearFee
    String fourthSemFee
    String fourthYearFee
    String fifthSemFee
    String sixthSemFee
    String seventhSemFee
    String eighthSemFee
    String hostelFee
    String hostelFeeTotal
    String otherExpensesFee
    String otherExpensesFeeTotal
    String laptopAmount
    String courseFeeRemarks
    String hostelFeeRemarks
    String otherFeeRemarks

    CertificateRequestDetails certificateDetails

    static constraints = {
        paragraph2(nullable: true)
        paragraph3(nullable: true)
        firstSemFee(nullable: true)
        firstYearFee(nullable: true)
        secondSemFee(nullable: true)
        secondYearFee(nullable: true)
        thirdSemFee(nullable: true)
        thirdYearFee(nullable: true)
        fourthSemFee(nullable: true)
        fourthYearFee(nullable: true)
        fifthSemFee(nullable: true)
        sixthSemFee(nullable: true)
        seventhSemFee(nullable: true)
        eighthSemFee(nullable: true)
        hostelFee(nullable: true)
        hostelFeeTotal(nullable: true)
        otherExpensesFee(nullable: true)
        otherExpensesFeeTotal(nullable: true)
        laptopAmount(nullable: true)
        courseFeeRemarks(nullable: true)
        hostelFeeRemarks(nullable: true)
        otherFeeRemarks(nullable: true)
    }
    static mapping = {
        courseFeeRemarks sqlType:"longtext"
        hostelFeeRemarks sqlType:"longtext"
        otherFeeRemarks sqlType:"longtext"
        paragraph1 sqlType:"longtext"
        paragraph2 sqlType:"longtext"
        paragraph3 sqlType:"longtext"
        refNo column: 'refNo'
        fromName column: "fromName"
        fromPosition column: "fromPosition"
        dateShown column: "dateShown"
        date column: "date"
        header column: "header"
        paragraph1 column: "paragraph1"
        paragraph2 column: "paragraph2"
        paragraph3 column: "paragraph3"
        signatureName column: "signatureName"
    }
}
