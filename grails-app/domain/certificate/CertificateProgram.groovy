package certificate

//import certificate.CertificateName
import examinationproject.ProgramType

class CertificateProgram {
    /*Save Certificate program details(Certificate for specified program and semester)*/
    Certificate certificate
    ProgramType programType
    int semesterNo

    static constraints = {
        programType(nullable: false)
        semesterNo(nullable: true)
    }
    static mapping = {
        certificate column: 'certificate'
        programType column: 'programType'
        semesterNo column: 'semesterNo'
    }
}
