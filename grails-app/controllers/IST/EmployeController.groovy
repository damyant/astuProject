package IST

import examinationproject.ProgramSession
import grails.converters.JSON

import java.text.DateFormat
import java.text.SimpleDateFormat

class EmployeController {
    def springSecurityService
    def employeeInformationService

    def index() {}

    def createEmployee() {
        /*This action is for creating new employee*/
        if (springSecurityService.currentUser) {
        def departList = Department.list()
        def marital = ["Married", "Single", "Divorced", "Widow"]
        def qual = ["Ph.D", "Masters", "Bachelor", "Undergraduate", "Others"]
        def salutation = ["Blank", "Dr.", "Prof.", "Md", "Mr.", "Mrs.", "Miss", "Ms"]
        if (params.id) {
            def employeeObj = Employee.get(params.id)
            def empDesObj = EmployeeDesignation.findAll("from EmployeeDesignation as b where b.employee= " + employeeObj.id + " order by b.joiningDate desc")
            [employeeObj: employeeObj, departList: departList, empDesObj: empDesObj, marital: marital, qual: qual, salutation: salutation]
        } else {
            [departList: departList, marital: marital, qual: qual, salutation: salutation]
        }
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    def editEmployee() {
        /*action for editing already existing employee*/
        if (springSecurityService.currentUser) {
        def departList = Department.list()
        def marital = ["Married", "Single", "Divorced", "Widow"]
        def qual = ["Ph.D", "Masters", "Bachelor", "Undergraduate", "Others"]
        def salutation = ["Blank", "Dr.", "Prof.", "Md", "Mr.", "Mrs.", "Miss", "Ms"]
        if (params.id) {
            def employeeObj = Employee.get(params.id)
            def empDesObj = EmployeeDesignation.findAll("from EmployeeDesignation as b where b.employee= " + employeeObj.id + " order by b.joiningDate desc")
            def empQualObj = EmployeeQualification.findAllByEmployee(employeeObj)
            [employeeObj: employeeObj, departList: departList, empQualObj: empQualObj, empDesObj: empDesObj, marital: marital, qual: qual, salutation: salutation]
        } else {
            [departList: departList, salutation: salutation]
        }
        } else {
            redirect(controller: "login", action: "auth")
        }


    }

    def saveEmployee() {
        /*action for saving a new or already existing employee*/
        if (springSecurityService.currentUser) {
        def status = ""
        def photo = request.getFile("photograph")
        employeeInformationService.saveEmployeeInformationData(params, photo)
        if (params.employeeId) {
            redirect(action: "listOfEmployee")
            flash.message = "Updated Information Successfully"
        } else {
            redirect(action: "createEmployee")
            flash.message = "Saved Information Successfully"
        }
    } else {
        redirect(controller: "login", action: "auth")
    }


}

    def listOfEmployee() {
        /*action for showing list of all employees*/
        if (springSecurityService.currentUser) {
        [employeeList: Employee.list()]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def viewEmployeeDetail() {
        /*Action for showing all details of a particular employee*/
        if (springSecurityService.currentUser) {
        def employeeObj = Employee.get(params.id)
        def empDesObj = EmployeeDesignation.findAll("from EmployeeDesignation as b where b.employee= " + employeeObj.id + " order by b.joiningDate desc")
        def empQualObj = EmployeeQualification.findAllByEmployee(employeeObj)
        [employeeObj: employeeObj, empDesObj: empDesObj, empQualObj: empQualObj]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def deleteEmployee() {
        /*This action is for deleting a particular employee*/
        if (springSecurityService.currentUser) {
        def returnMap = [:]
        def empObj = Employee.get(params.empId)
        def empDesObj = EmployeeDesignation.findAllByEmployee(empObj)
        def empQualObj = EmployeeQualification.findAllByEmployee(empObj)
        empDesObj.each {
            it.delete()
        }

        empQualObj.each {
            it.delete()
        }
        empObj.delete(failOnError: true, flush: true)
        if (Employee.exists(params.empId)) {
            returnMap.status = false
        } else {
            returnMap.status = true
        }
        render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def show = {
        /*Action for showing the employee image*/
        if (springSecurityService.currentUser) {
        def id = Integer.parseInt(params.id)
        def something = Employee.get(id)
        byte[] image = something.employeeImage
        response.setContentType(params.mime)
        response.outputStream << image
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def validateCode() {
        /*Action for validating employee code(whether an employe with same employe code is exisitng or not)*/
        if (springSecurityService.currentUser) {
        def returnMap = [:]
        def temp = 0
        def emp = Employee.findByEmployeeCode(params.code)
        if (emp) {
            temp = 1
        }
        returnMap.temp = temp
        render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def paginateOfEmployeee() {

    }
}