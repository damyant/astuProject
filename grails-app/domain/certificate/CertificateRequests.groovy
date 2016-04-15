package certificate

import IST.Employee
//import certificate.CertificateName
import examinationproject.Student

class CertificateRequests {
    /*Save details of certificate request(i.e which certificate has been requested and which user has requested,remarks etc)*/

    Student student
    Employee employee
    Certificate certificate
    String certificateRemarks
    String ipAddress
    Date releaseDate
    Date date
    int hodStatus=0
    int status // 0 for unapproved 1 for approved.............

    static constraints = {
        certificateRemarks(nullable: true)
        student(nullable: true)
        releaseDate(nullable: true)
        employee(nullable: true)
        ipAddress(nullable: true)
    }
}
