package universityproject

import IST.Department
import IST.Employee
import com.university.Role

import com.university.User
import com.university.UserRole
import examinationproject.ProgramDetail
import examinationproject.Semester
//import examinationproject.StudyCenter
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsHttpSession
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.web.context.request.RequestContextHolder

/*import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils*/

@Transactional
class UserService {


    static transactional = true
    def springSecurityService
    def grailsApplication

    def serviceMethod() {

    }

    def iUserLoggedInAction(params) {
        def c = User.createCriteria()
        def userInstance = c.list {
            eq('username', params.username)
            eq('password', springSecurityService.encodePassword(params.password))
            eq('enabled', true)
            eq('accountExpired', false)
            eq('accountLocked', false)
            eq('passwordExpired', false)
        }[0]
        //def userInstance=User.findByUsernameAndPassword(params.username,springSecurityService.encodePassword(params.password))
        def role = Role.findByAuthority('ROLE_USER')
        def userRole = UserRole.findByUserAndRoleNotEqual(userInstance, role)
        if (userInstance && userRole) {
            return userInstance
        } else {
            return false
        }
    }

    def iUserLoggedIn() {
        try {
            GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
            GrailsHttpSession session = request.session
            if (session.user) {
                return session.user
            } else {
                return false
            }
        }
        catch (IllegalStateException ise) {
            log.warn("No WebRequest available!")
            return false
        }
    }

    def iUserLogOut() {
        try {
            GrailsWebRequest request = RequestContextHolder.currentRequestAttributes()
            GrailsHttpSession session = request.session
            session.user = ""

        }
        catch (IllegalStateException ise) {
            log.warn("No WebRequest available!")
        }
    }

    def updateUserRole(user, role) {
        //UserRole.create userInstance, role
        def uRole = UserRole.findAllByUser(user)
        uRole.each { ur ->
            ur.delete(flush: true)
        }
        UserRole.create user, role
    }

    def newPwd(Integer len = 10) {
        def AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    /*
    * Return role list below the current role.
    * */

    def getRoleList() {
        /* def role=springSecurityService.getPrincipal().authorities*.authority[0]*/
        def rolesGrated = []
        def rolesList = Role.list()
//        println(rolesList)
        /* rolesList.each {r ->
             if(SpringSecurityUtils.ifAnyGranted(r.authority) && r.authority!=role){
                 rolesGrated.add(r)
             }
         }*/

//        println("Role List from User Service" + rolesList)
        return rolesList
    }

    /*
    * Return user list below the current user role.
    * */

    def getUsersList() {
        def roleList = getRoleList()
        def userList = []
        User.list().each { u ->
            def flag = true
            roleList.each { r ->
                if (r.authority == u.getAuthorities().authority[0]) flag = false
            }
            if (!flag) userList.add(u)
        }
        return userList
    }
    def saveUserDetails(params){
        /*method for saving user details*/
        def resultMap=[:]
        def status=true
        def userInstance = new User(params)
        if(params.employeeCode){
            def employeeInst=Employee.findByEmployeeCode(params.employeeCode)
            userInstance.department= employeeInst.department
        }
        def checked = params.list('myCheckbox')
        def roleList = Role.getAll()
        if (userInstance.save(flush: true)) {
            for (int i = 0; i < checked.size(); i++) {
                def role = Role.findById(checked[i])
                UserRole.create userInstance, role

            }
            resultMap.userInstance=userInstance
            resultMap.status=true
        }
        else{
            resultMap.status=false
        }
        return resultMap
    }

   def updateUserDetails(params){
       def resultMap=[:]
       def UserInstance = User.get(Long.parseLong(params.id))
       if (UserInstance) {
           if (params.version) {

               def version = params.version.toLong()
               if (UserInstance.version > version) {

                   UserInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(default: 'User')] as Object[], "Another user has updated this User while you were editing")
                   render(view: "editUser", model: [UserInstance: UserInstance])
                   return
               }
           }

           UserInstance.properties = params
           if(params.userDepartment){
               UserInstance.department= Department.findById(Integer.parseInt(params.userDepartment))
           }

           def secUserSecRoleInstance = UserRole.findAllByUser(UserInstance)
           secUserSecRoleInstance.each {
               it.delete()
           }
           def checked = params.list('myCheckbox')
           if (UserInstance.save(flush: true)) {
               def count = 0
               for (int i = 0; i < checked.size(); i++) {
                   def role = Role.findById(checked[i])
                   UserRole.create UserInstance, role

               }
               resultMap.status=true
           }
           else{
               resultMap.status=false
           }
           UserInstance.department.id
           resultMap.userInstance=UserInstance
       }
       return resultMap
   }
    def editUserDetails(params){
        /*Method for editing user details*/
        def resultMap=[:]
        def currentUser = springSecurityService.getCurrentUser().getUsername()
//        def studyCentreList = StudyCenter.list(sort: 'name')
        def boolean compare = false
        def userInstance = User.get(params.id)
        if (currentUser == userInstance.username) {
            compare = true
        }
        def userRoles = UserRole.findAllByUser(userInstance)*.role
        if (!userInstance) {
            resultMap.status=false
        }
        else{
            def roles = Role.getAll()
            def tab1ProgramList, tab2ProgramList, tab1OptionValue = [], tab1OptionText = []
            def tab1HProgList = '', tab2HProgList = '', tab1HSemList = '', tab2HSemList = '', tab2OptionValue = [], tab2OptionText = []

            resultMap.status=true
            resultMap.userInstance=userInstance
            resultMap.roles=roles
            resultMap.userRoles=userRoles
            resultMap.compare=compare
        }
        return resultMap
    }
}