package com.university

import IST.Department
import IST.Employee
import examinationproject.ProgramDetail
import examinationproject.ProgramSession
import examinationproject.Semester
//import examinationproject.StudyCenter
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import javax.validation.constraints.Null

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def userService
    def springSecurityService

    @Secured(["ROLE_ADMIN"])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond User.list(params), model: [userInstanceCount: User.count()]
    }

    def show(User userInstance) {
        respond userInstance
    }

    def resetPassword() {
        /*for reset the password*/

    }


    @Transactional
    @Secured(["ROLE_ADMIN"])
    def updatePwd() {
        /*Saves new(changed) password*/
        def userInstance = User.findById(Long.parseLong(params.identification))
        userInstance.password = params.newPwd
        userInstance.save(flush: true)

        flash.message = "${message(code: 'password.reset.msg', args: [userInstance.username, userInstance.email])}"
        redirect(action: "userlist")
    }
//
    @Secured(["ROLE_ADMIN"])
    def createUser() {
        /*Action for creating new user*/
        def userInstance = new User()
        userInstance.properties = params
        def departmentList = Department.list(sort: 'name')
        def programList = ProgramSession.list()
        def examAdminCount=UserRole.findAllByRole(Role.findById(11)).size()
        def roleList = userService.getRoleList()
        [userInstance: userInstance, roles: roleList,examAdminCount:examAdminCount, programList: programList,departmentList:departmentList]
    }

    @Secured(["ROLE_ADMIN"])
    def userlist() {
        /*Action for showing userList*/
//        params.max = Math.min(max ?: 10, 100)
        respond User.list()

    }

    def getProgramList = {

        def programListMap = []
        def programList = ProgramSession.list()
        programList.each {
            def returnMap = [:]
            returnMap.id = it.id
            returnMap.programName = it.programDetailId.courseName
            returnMap.noOfSemester = it.programDetailId.noOfTerms
            returnMap.semesterList = Semester.findAllByProgramSession(it)
            programListMap.add(returnMap)
        }

        render programListMap as JSON
    }

    @Secured(["ROLE_ADMIN"])
    def save = {
        /*Action for saving user details*/
        def result=userService.saveUserDetails(params)
       if(result.status){
           flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), result.userInstance.id])}"
           redirect(action: "index")
        } else {
            render(view: "createUser", model: [userInstance: result.userInstance])
        }
    }
    @Secured(["ROLE_ADMIN"])


    def editUser = {
        /*for editing user details*/
        def result=userService.editUserDetails(params)
        def departmentList = Department.list()
        if (!result.status) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(default: 'User'), params.id])}"
            redirect(action: "list")
        } else {
            return [tab1HProgList: result.tab1HProgList, tab1HSemList: result.tab1HSemList, tab2HProgList: result.tab2HProgList, tab2HSemList: result.tab2HSemList, tab1OptionText: result.tab1OptionText, tab2OptionText: result.tab2OptionText, tab1OptionValue: result.tab1OptionValue, tab2OptionValue: result.tab2OptionValue, userInstance: result.userInstance, roles: result.roles, userRoles: result.userRoles, compare: result.compare, studyCentreList: result.studyCentreList, studyCentre: result.studyCentre,departmentList:departmentList]
        }
    }


    @Secured(["ROLE_ADMIN"])
    def updateUser = {

        def result=userService.updateUserDetails(params)
        if(result.userInstance){
            if(result.status){
                flash.message = "${message(code: 'default.updated.message', args: [message(default: 'User'), result.userInstance.id])}"

                redirect(action: "index", id: result.userInstance.id)
            } else {

                log.debug "-----------" + result.userInstance.errors
                render(view: "editUser", model: [userInstance: result.userInstance])
            }
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(default: 'User'), params.id])}"
            redirect(action: "index")
        }
    }

    @Transactional
    @Secured(["ROLE_ADMIN"])
    def delete(User userInstance) {

        if (userInstance == null) {
            notFound()
            return
        }

        userInstance.delete flush: true

        request.withFormat {
            form {

                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }


    def assignCourse(){
//        println('these are the parameters '+params)

        def programList = ProgramDetail.list(sort: 'courseCode')
        [programList: programList]
    }

    def saveCourseForTabulator() {
        def sizeP = ProgramDetail.list().size()


    }

    def getEmployeeInfo ={
        def returnMap = [:]
        def user = User.findByEmployeeCode(params.employeeCode)
        if(user){
            returnMap.exist = 'User Already Exist !'
        }
        else{
            def employeeInfo = Employee.findByEmployeeCode(params.employeeCode)
            returnMap.employeeInfo = employeeInfo
        }
        render returnMap as JSON
    }
    def changePassword(){

    }

    @Transactional
    def resetUserPassword(){
        /* for reset user's own password*/
    }
    @Transactional
    def savePassword() {
        /*for saving new password*/
        if (springSecurityService.getCurrentUser()) {
           def currentUserId = springSecurityService.getCurrentUser().getId()
            def userInstance = User.findById(currentUserId)

            userInstance.password = params.pwd
           if(userInstance.save(flush:true)){
                flash.message = "Changed Password Successfully"
            }
            else{
                flash.message = "Unable to Changed Password!!!"
            }
            redirect(controller:"home",action: "index")
        }
        else{
            redirect(controller:"login",action: "auth")
        }
    }
    def saveResetPassword() {
        if (springSecurityService.getCurrentUser()) {
           def currentUserId = springSecurityService.getCurrentUser().getId()
            def password = springSecurityService.encodePassword(params.oldPwd)
            def userInstance = User.findById(currentUserId)
            if(password==userInstance.password){
                userInstance.password = params.pwd
                if(userInstance.save(flush: true)){
                    flash.message = "Changed Password Successfully"
                }
                else{
                    flash.message = "Unable to Changed Password Successfully"
                }
                redirect(controller:"home",action: "index")
            }
            else{
                flash.message = "Old Password is Incorrect"
                redirect(controller:"user",action: "resetUserPassword")
            }



        }
        else{
            redirect(controller:"login",action: "auth")
        }
    }

}


