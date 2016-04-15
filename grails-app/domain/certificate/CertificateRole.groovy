package certificate

import com.university.Role

class CertificateRole {
    /*This domain identifies which role(user) can apply which certificate(maps between Certificate and Role)*/
    Certificate certificate
    Role role
    static constraints = {
        role(nullable: false)
    }
    static mapping = {
        role column: 'role'
        certificate column: 'certificate'
    }
}
