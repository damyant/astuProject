package certificate

class Certificate {
    /*Save Name and format of Certificates*/
    String nameOfCertificate
    String format
    static constraints = {
        nameOfCertificate(nullable: false)
        format(nullable: true)
    }
    static mapping = {
        nameOfCertificate column: 'nameOfCertificate'
    }
}
