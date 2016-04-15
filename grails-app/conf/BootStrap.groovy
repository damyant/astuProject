import certificate.Certificate
import certificate.CertificateProgram
import certificate.CertificateRole
import com.university.Role
import com.university.User
import com.university.UserRole
import examinationproject.ProgramType
import postexamination.RuleSymbols


class BootStrap {

    def init = { servletContext ->
        def prgWoPhd=ProgramType.list()
        def adminRole =Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)
        def libraryRole =Role.findByAuthority('ROLE_LIBRARY') ?: new Role(authority: 'ROLE_LIBRARY').save(failOnError: true)
        def hodRole = Role.findByAuthority('ROLE_HOD')?:new Role(authority: 'ROLE_HOD').save(failOnError: true)
        def studentUserRole = Role.findByAuthority('ROLE_STUDENT')?:new Role(authority: 'ROLE_STUDENT').save(failOnError: true)
        def facultyAdvisorRole =Role.findByAuthority('ROLE_FACULTYADVISOR') ?: new Role(authority: 'ROLE_FACULTYADVISOR').save(failOnError: true)
        def instructorRole =Role.findByAuthority('ROLE_INSTRUCTOR') ?: new Role(authority: 'ROLE_INSTRUCTOR').save(failOnError: true)
        def departmentLibrary =Role.findByAuthority('ROLE_DEPARTMENT_LIBRARY') ?: new Role(authority: 'ROLE_DEPARTMENT_LIBRARY').save(failOnError: true)
        def employee =Role.findByAuthority('ROLE_EMPLOYEE') ?: new Role(authority: 'ROLE_EMPLOYEE').save(failOnError: true)
        def lecture =RuleSymbols.findBySymbol('L') ?: new RuleSymbols(symbol: 'L',text:'Lecture',isEditable:false).save(failOnError: true)
        def tutorial =RuleSymbols.findBySymbol('T') ?: new RuleSymbols(symbol: 'T',text:'Tutorial',isEditable:false).save(failOnError: true)
        def practical =RuleSymbols.findBySymbol('P') ?: new RuleSymbols(symbol: 'P',text:'Practical',isEditable:false).save(failOnError: true)
        def credit =RuleSymbols.findBySymbol('C') ?: new RuleSymbols(symbol: 'C',text:'Credit',isEditable:false).save(failOnError: true)
        def guestFacultyRole = Role.findByAuthority('ROLE_GUEST_FACULTY')?:new Role(authority: 'ROLE_GUEST_FACULTY').save(failOnError: true)
        def certificateAdminRole = Role.findByAuthority('ROLE_CERTIFICATE_ADMIN')?:new Role(authority: 'ROLE_CERTIFICATE_ADMIN').save(failOnError: true)
        def incomeTaxCertificate = Certificate.findByNameOfCertificate('For Parent\'s income tax purpose')?:new Certificate(nameOfCertificate: 'For Parent\'s income tax purpose',format: 'Annexer_A').save(failOnError: true)
        def studentCertificate = Certificate.findByNameOfCertificate('Studentship Certificate')?:new Certificate(nameOfCertificate: 'Studentship Certificate',format: 'Annexer_B').save(failOnError: true)
        def bonafiedCertificate = Certificate.findByNameOfCertificate('Bonafied Certificate')?:new Certificate(nameOfCertificate: 'Bonafied Certificate',format: 'Annexer_C').save(failOnError: true)
        def bankLoanDetailCertificate = Certificate.findByNameOfCertificate('Certificate stating details fee for applying Bank Loan')?:new Certificate(nameOfCertificate: 'Certificate stating details fee for applying Bank Loan',format: 'Annexer_F').save(failOnError: true)
        def semesterBankLoanCertificate = Certificate.findByNameOfCertificate('Certificate asking for semester fee for Bank Loan')?:new Certificate(nameOfCertificate: 'Certificate asking for semester fee for Bank Loan',format: 'Annexer_G').save(failOnError: true)
        def trainingCertificate = Certificate.findByNameOfCertificate('Letter to different Organisation for Summer Training')?:new Certificate(nameOfCertificate: 'Letter to different Organisation for Summer Training',format: 'Annexer_H').save(failOnError: true)
        def staffExperienceCertificate = Certificate.findByNameOfCertificate('Experience Certificate')?:new Certificate(nameOfCertificate: 'Experience Certificate',format: 'Annexer_D').save(failOnError: true)
        def guestExperienceCertificate = Certificate.findByNameOfCertificate('Experience Certificate (Guest)')?:new Certificate(nameOfCertificate: 'Experience Certificate (Guest)',format: 'Annexer_E').save(failOnError: true)
       if(!CertificateRole.findByCertificateAndRole(incomeTaxCertificate,studentUserRole)){
           new CertificateRole(certificate: incomeTaxCertificate, role:studentUserRole ).save(failOnError: true)
       }
        if(!CertificateRole.findByCertificateAndRole(studentCertificate,studentUserRole)){
           new CertificateRole(certificate: studentCertificate, role:studentUserRole ).save(failOnError: true)
       }
        if(!CertificateRole.findByCertificateAndRole(bonafiedCertificate,studentUserRole)){
           new CertificateRole(certificate: bonafiedCertificate, role:studentUserRole ).save(failOnError: true)
       }
        if(!CertificateRole.findByCertificateAndRole(bankLoanDetailCertificate,studentUserRole)){
           new CertificateRole(certificate: bankLoanDetailCertificate, role:studentUserRole ).save(failOnError: true)
       }
        if(!CertificateRole.findByCertificateAndRole(semesterBankLoanCertificate,studentUserRole)){
           new CertificateRole(certificate: semesterBankLoanCertificate, role:studentUserRole ).save(failOnError: true)
       }
        if(!CertificateRole.findByCertificateAndRole(trainingCertificate,studentUserRole)){
           new CertificateRole(certificate: trainingCertificate, role:studentUserRole ).save(failOnError: true)
       }
        if(!CertificateRole.findByCertificateAndRole(staffExperienceCertificate,employee)){
           new CertificateRole(certificate: staffExperienceCertificate, role:employee ).save(failOnError: true)
       }
        if(!CertificateRole.findByCertificateAndRole(guestExperienceCertificate,guestFacultyRole)){
           new CertificateRole(certificate: guestExperienceCertificate, role:guestFacultyRole ).save(failOnError: true)
       }

        prgWoPhd.each {
           if(it.code!='05'){
               CertificateProgram.findByCertificateAndProgramType(incomeTaxCertificate,it)?:new CertificateProgram(certificate: incomeTaxCertificate,programType: it).save(failOnError: true)
               CertificateProgram.findByCertificateAndProgramType(semesterBankLoanCertificate,it)?:new CertificateProgram(certificate: semesterBankLoanCertificate,programType: it).save(failOnError: true)
               CertificateProgram.findByCertificateAndProgramType(bankLoanDetailCertificate,it)?:new CertificateProgram(certificate: bankLoanDetailCertificate,programType: it).save(failOnError: true)
           }
            CertificateProgram.findByCertificateAndProgramType(studentCertificate,it)?:new CertificateProgram(certificate: studentCertificate,programType: it).save(failOnError: true)
            CertificateProgram.findByCertificateAndProgramType(bonafiedCertificate,it)?:new CertificateProgram(certificate: bonafiedCertificate,programType: it).save(failOnError: true)
            if(it.code=='01')
            CertificateProgram.findByCertificateAndProgramType(trainingCertificate,it)?:new CertificateProgram(certificate: bonafiedCertificate,programType: it,semesterNo: 1).save(failOnError: true)
        }
        def adminUser = User.findByUsername('admin') ?: new User(
                username: 'admin',
                password: 'admin',
                email: 'admin@damyant.com',
                studyCentreId: 1,
                enabled: true).save(failOnError: true)

        if (!adminUser.authorities.contains(adminRole)) {
            UserRole.create adminUser, adminRole
        }
        def libraryUser = User.findByUsername('libraryAdmin') ?: new User(
                username: 'libraryAdmin',
                password: 'admin',
                email: 'library@damyant.com',
                studyCentreId: 0,
                enabled: true).save(failOnError: true)

        if (!libraryUser.authorities.contains(libraryRole)) {
            UserRole.create libraryUser, libraryRole
        }
        def employeeUser = User.findByUsername('employee') ?: new User(
                username: 'employee',
                password: 'employee',
                email: 'employee@damyant.com',
                studyCentreId: 0,
                enabled: true).save(failOnError: true)



    }
    def destroy = {
    }
}
