package certificate

class AnnexerH {
    /*Save format for Letter to different Organisation for Summer Training*/
    String refNo
    String fromName
    String fromPosition
    String dateShown
    Date date
    String header
    String paragraph1
    String paragraph2
    String paragraph3
    String paragraph4
    String paragraph5
    String designation
    String institute
    String address
    String signatureNameEdit
    CertificateRequestDetails certificateDetails

    static constraints = {
        paragraph2(nullable: true)
        paragraph3(nullable: true)
        paragraph4(nullable: true)
        paragraph5(nullable: true)
        address(nullable: true)
        institute(nullable: true)
    }
    static mapping = {
        paragraph1 sqlType:"longtext"
        paragraph2 sqlType:"longtext"
        paragraph3 sqlType:"longtext"
        paragraph4 sqlType:"longtext"
        paragraph5 sqlType:"longtext"
        designation column: 'designation'
        institute column: 'institute'
        address column: 'address'
        refNo column: 'refNo'
        fromName column: "fromName"
        fromPosition column: "fromPosition"
        dateShown column: "dateShown"
        date column: "date"
        header column: "header"
        paragraph1 column: "paragraph1"
        paragraph2 column: "paragraph2"
        paragraph3 column: "paragraph3"
        paragraph4 column: "paragraph4"
        paragraph5 column: "paragraph5"
        signatureName column: "signatureName"
        certificateDetails column: "certificateDetails"
    }
}
