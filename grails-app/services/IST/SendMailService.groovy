package IST

import com.university.Role
import com.university.UserRole
import examinationproject.FacultyAdvisor
import examinationproject.Hod
import examinationproject.Status
import examinationproject.Student
import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional
class SendMailService {
    def mailService
    def dataSource
    def serviceMethod() {

    }

    def mailSend (){
        def status = Status.findById(4)
//        Sql sql = new Sql(dataSource)
//        def studentList = sql.rows("select * from student where status ="+status.id+" group by program ")
        def studentList = Student.findAll("from Student where status= "+status.id+" group by program_id")
         studentList.each{
           def role = Role.findByAuthority('ROLE_HOD')
           def users = UserRole.findAllByRole(role).user
           def employeeCode = []
           users.each{
               employeeCode.add(it.employeeCode)
           }
           def hod = Employee.findByDepartmentAndEmployeeCodeInList(it.programBranch.department,employeeCode)
           if(hod?.firstEmail){
               mailService.sendMail {
                   to ""+hod.firstEmail
                   subject "Student Approval"
                   body "There Are Some Students Pending For Your Approval. Please Click On Below Link To Approve \n" +
                           "http://localhost:9098/UniversityProject" +
                           "\n"+
                           "Regards IST Guahati"

               }
           }
       }


    }

    def mailSendToFA(){
       def studentList = Student.findAll("from Student where semesterRegistration= "+1+" group by program_id")
        studentList.each{
            def role = Role.findByAuthority('ROLE_FACULTYADVISOR')
            def users = UserRole.findAllByRole(role).user
            def employeeCode = []
            users.each{
                employeeCode.add(it.employeeCode)
            }
            def fa = Employee.findByDepartmentAndEmployeeCodeInList(it.programBranch.department,employeeCode)
//            println('this is the hod of department '+ fa.email)
            if(fa?.firstEmail){
                mailService.sendMail {
                    to ""+fa.firstEmail
                    subject "Student Approval"
                    body "There Are Some Students Pending For Your Approval. Please Click On Below Link To Approve \n" +
                            "http://localhost:9098/UniversityProject" +
                            "\n"+
                            "Regards IST Guahati"

                }
            }
        }
    }
}
