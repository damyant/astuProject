package certificate

class CertificatesTemplate {
    /*Saves details of templates for different kind of certificates*/
    Certificate certificate
    String refNo
    String fromName
    String fromPosition
    String header
    String paragraph1
    String paragraph2
    String paragraph3
    String paragraph4
    String paragraph5
    String signatureName
    String instituteNameHeader1
    String instituteNameHeader2
    String instituteAddressHeader
    byte[] logoImage

    static mapping = {
        paragraph1 sqlType:"longtext"
        paragraph2 sqlType:"longtext"
        paragraph3 sqlType:"longtext"
        paragraph4 sqlType:"longtext"
        paragraph5 sqlType:"longtext"
        certificate column: 'certificate'
        refNo column: 'refNo'
        fromName column: 'fromName'
        fromPosition column: 'fromPosition'
        header column: 'header'
        paragraph1 column: 'paragraph1'
        paragraph2 column: 'paragraph2'
        paragraph3 column: 'paragraph3'
        paragraph4 column: 'paragraph4'
        paragraph5 column: 'paragraph5'
        signatureName column: 'signatureName'
        instituteNameHeader1 column: 'instituteNameHeader1'
        instituteNameHeader2 column: 'instituteNameHeader2'
        instituteAddressHeader column: 'instituteAddressHeader'
        logoImage column: 'logoImage', sqlType: "blob"
    }
    static constraints = {
        paragraph2(nullable: true)
        paragraph3(nullable: true)
        paragraph4(nullable: true)
        paragraph5(nullable: true)
    }
}
