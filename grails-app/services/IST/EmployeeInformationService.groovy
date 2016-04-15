package IST

import grails.transaction.Transactional

import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class EmployeeInformationService {

    def serviceMethod() {

    }

    def saveEmployeeInformationData(params, photo) {
        /*Method for saving employee's data*/
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy")

        if (params.employeeId) {
            def employeeObj = Employee.get(params.employeeId)
            employeeObj.firstName = params.firstName.toUpperCase()
            employeeObj.middleName = params.middleName.toUpperCase()
            employeeObj.lastName = params.lastName.toUpperCase()
            employeeObj.fatherName = params.fatherName.toUpperCase()
            employeeObj.motherName = params.motherName.toUpperCase()
            employeeObj.spouseName = params.spouseName.toUpperCase()
            employeeObj.firstEmail = params.firstEmail
            employeeObj.secondEmail = params.secondEmail

            employeeObj.maritalStatus = params.maritalStatus
            employeeObj.emergencyContactNo = Long.parseLong(params.emergencyContactNo)
            employeeObj.salutation = params.salutation
            if (params.qualification == "Others") {
                employeeObj.qualification = params.other
            } else {
                employeeObj.qualification = params.qualification
            }
            employeeObj.employeeCode = params.employeeCode
            employeeObj.currentAddress = params.currentAddress
            employeeObj.currentAddressCity = params.currentAddressCity
            employeeObj.currentAddressDistrict = params.currentAddressDistrict
            employeeObj.currentStateAddress = params.currentStateAddress
            employeeObj.currentPincode = params.currentPincode

            employeeObj.permanentAddress = params.permanentAddress
            employeeObj.permanentAddressCity = params.permanentAddressCity
            employeeObj.permanentAddressDistrict = params.permanentAddressDistrict
            employeeObj.permanentStateAddress = params.permanentStateAddress
            employeeObj.permanentPincode = params.permanentPincode

            employeeObj.gender = params.gender
            employeeObj.firstMobileNo = Long.parseLong(params.firstMobileNo)
            employeeObj.secondMobileNo = Long.parseLong(params.secondMobileNo)
            employeeObj.dob = df.parse(params.dob)
            employeeObj.panNumber = params.panNumber
            employeeObj.bankName = params.bankName
            employeeObj.accountNo = params.accountNo

            def dep = Department.findById(Long.parseLong(params.department))
            employeeObj.department = dep
            if (photo.bytes) {
                employeeObj.employeeImage = photo.bytes
            }
            employeeObj.save(failOnError: true)
            def empDesObj = EmployeeDesignation.findAllByEmployee(employeeObj)
            empDesObj.each {
                it.delete()
            }
            def empQualObj = EmployeeQualification.findAllByEmployee(employeeObj)
            empQualObj.each {
                it.delete()
            }
            def len = null
            def lent = null

            /* For employeeDesignation*/
            if (params.designation.getClass() != String) {
                len = params.designation.length
                for (def i = 0; i < len; i++) {
                    def empDes = new EmployeeDesignation()
                    empDes.employee = employeeObj
                    empDes.designation = params.designation[i]
                    empDes.joiningDate = df.parse(params.joiningDate[i])
                    empDes.save(failOnError: true)
                }
            } else {
                def empDes = new EmployeeDesignation()
                empDes.employee = employeeObj
                empDes.designation = params.designation
                empDes.joiningDate = df.parse(params.joiningDate)
                empDes.save(failOnError: true)
            }
       /*for employee Qualification*/
            if (params.degree.getClass() != String) {
                lent = params.degree.length
                for (def i = 0; i < lent; i++) {

                    def empQual = new EmployeeQualification()
                    empQual.employee = employeeObj
                    empQual.degree = params.degree[i]
                    empQual.university = params.university[i]
                    empQual.subject = params.subject[i]
                    empQual.year = params.year[i]
                    empQual.save(failOnError: true)
                }
            } else {
                def empQual = new EmployeeQualification()
                empQual.employee = employeeObj
                empQual.degree = params.degree
                empQual.university = params.university
                empQual.subject = params.subject
                empQual.year = params.year
                empQual.save(failOnError: true)
            }


        } else {
            def len = null
            def lent = null
            if (params.designation.getClass() != String) {
                len = params.designation.length
            }
            if (params.degree.getClass() != String) {
                lent = params.degree.length
            }
            def employeeObj = new Employee(params)
            employeeObj.firstName = params.firstName.toUpperCase()
            employeeObj.middleName = params.middleName.toUpperCase()
            employeeObj.lastName = params.lastName.toUpperCase()
            employeeObj.fatherName = params.fatherName.toUpperCase()
            employeeObj.motherName = params.motherName.toUpperCase()
            employeeObj.spouseName = params.spouseName.toUpperCase()
            if (params.qualification == "Others") {
                employeeObj.qualification = params.other
            } else {
                employeeObj.qualification = params.qualification
            }
            def dep = Department.findById(Long.parseLong(params.department))
            employeeObj.department = dep
            if (photo.bytes) {

                employeeObj.employeeImage = photo.bytes
            }

            employeeObj.save(failOnError: true)

            /*employeeDesignation*/
            if (params.designation.getClass() != String) {
                for (def i = 0; i < len; i++) {
                    def empDes = new EmployeeDesignation()
                    empDes.employee = employeeObj
                    if (params.designation[i]) {
                        empDes.designation = params.designation[i]
                        if (params.joiningDate[i]) {
                            empDes.joiningDate = df.parse(params.joiningDate[i])
                            empDes.save(failOnError: true)
                        }
                    }
                }
            } else {
                def empDes = new EmployeeDesignation()
                empDes.employee = employeeObj
                empDes.designation = params.designation
                empDes.joiningDate = df.parse(params.joiningDate)
                empDes.save(failOnError: true)
            }
            /*employee qualification*/
            if (params.degree.getClass() != String) {
                for (def i = 0; i < lent; i++) {

                    def empQual = new EmployeeQualification()
                    empQual.employee = employeeObj
                    if (params.degree[i]) {
                        empQual.degree = params.degree[i]
                        if (params.university[i]) {
                            empQual.university = params.university[i]
                            if (params.subject[i]) {
                                empQual.subject = params.subject[i]
                                if (params.year[i]) {
                                    empQual.year = params.year[i]
                                    empQual.save(failOnError: true)
                                }
                            }
                        }
                    }
                }
            } else {
                def empQual = new EmployeeQualification()
                empQual.employee = employeeObj
                empQual.degree = params.degree
                empQual.university = params.university
                empQual.subject = params.subject
                empQual.year = params.year
                empQual.save(failOnError: true)
            }

        }
    }
}
