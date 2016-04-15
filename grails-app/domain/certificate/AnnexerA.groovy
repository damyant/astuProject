package certificate

class AnnexerA {
    /*save certificate format for Parent's income tax purpose*/
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
    String prevYearTuitionFee
    String prevYearAdmissionFee
    String prevYearExamFee
    String prevYearOtherFee
    String thisYearTuitionFee
    String thisYearLabFee
    String thisYearExamFee
    String totalFee
    CertificateRequestDetails certificateDetails

    static constraints = {
        paragraph2(nullable: true)
        paragraph3(nullable: true)
        prevYearTuitionFee(nullable: true)
        prevYearAdmissionFee(nullable: true)
        prevYearExamFee(nullable: true)
        prevYearOtherFee(nullable: true)
        thisYearTuitionFee(nullable: true)
        thisYearLabFee(nullable: true)
        thisYearExamFee(nullable: true)
        totalFee(nullable: true)
    }
    static mapping = {
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
        prevYearTuitionFee column: "prevYearTuitionFee"
        prevYearAdmissionFee column: "prevYearAdmissionFee"
        prevYearExamFee column: "prevYearExamFee"
        prevYearOtherFee column: "prevYearOtherFee"
        thisYearTuitionFee column: "thisYearTuitionFee"
        thisYearLabFee column: "thisYearLabFee"
        thisYearExamFee column: "thisYearExamFee"
        totalFee column: "totalFee"
    }
}
