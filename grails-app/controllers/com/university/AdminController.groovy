package com.university

import IST.*
import certificate.CertificateRequests
import examinationproject.*

//import examinationproject.RollNoGenerationFixture
//import examinationproject.StudyCenter
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import postexamination.*

import javax.activation.MimetypesFileTypeMap
import java.text.DateFormat
import java.text.SimpleDateFormat

class AdminController {

    def adminInfoService
    def pdfRenderingService
    def studentRegistrationService
    def springSecurityService
    def feeDetailService
    def attendanceService
    def programFeeService


    def getStudentList() {
        if (springSecurityService.currentUser) {
            def responseMap = [:]
            def stuList = adminInfoService.provisionalStudentList(params)
            responseMap.status = "referenceNo"
            responseMap.label = params.pageType
            responseMap.stuList = stuList
            render responseMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    @Secured(["ROLE_ADMIN", "ROLE_INSTRUCTOR"])
    def generateRollNo() {
        if (springSecurityService.currentUser) {
            String rollNumber = null
            def stuObj
            def stuList = []
            def responseMap = [:]
            def status
            def refNo = [], rollNum = []
            if (params.pageType == "Approve RollNo") {
                status = studentRegistrationService.approvedStudents(params)
            } else {
                def studentIdList = params.studentList.split(",")
                rollNumber = studentRegistrationService.getUpdatedStudentRollNumber(params)

                if (rollNumber) {
                    studentIdList.each { i ->
                        def stuObjs = Student.findById(i)
                        def stmt = "# Ref No : " + stuObjs.referenceNumber.concat("  Roll No " + stuObjs.rollNo + "     ")
                        refNo << stmt
                    }
                }
            }
            if (stuObj) {
                stuList = adminInfoService.provisionalStudentList(params)
            }
            responseMap.status = 'rollNo'
            responseMap.studentName = refNo
            responseMap.stuList = stuList
            render responseMap as JSON
            render stuList as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    @Secured(["ROLE_ADMIN", "ROLE_HELP_DESK", "ROLE_ACCOUNT"])
    def feeVoucher = {
        if (springSecurityService.currentUser) {
            def feeType = []
            feeType = FeeType.list(sort: 'type')
            [feeType: feeType]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }


    def examFeeVoucher = {
        if (springSecurityService.currentUser) {
            def feeType = FeeType.list(sort: 'type')

            [feeType: feeType]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def getStudentByRollNo = {
        /*Action for getting student by roll no*/
        if (springSecurityService.currentUser) {
            def studentMap = [:]
            def student = Student.findByRollNo(params.rollNo)
            if (student) {
                studentMap.student = student
                render studentMap as JSON
            } else {
                studentMap.noStudent = 'No Student Found'
                render studentMap as JSON
            }
        } else {
            redirect(controller: "login", action: "auth")
        }
    }


    @Secured(["ROLE_ADMIN", "ROLE_HELP_DESK", "ROLE_ACCOUNT"])
    def generateFeeVoucher = {
        if (springSecurityService.currentUser) {
            def response = [:]
            def student = Student.findByRollNo(params.rollNo)
            def program = student.program
            def feeType = FeeType.findById(params.feeType)
            def lateFee = 0
            def programFeeAmount = 0
            def semInst = Semester.findById(Long.parseLong(params.term))
            def feeDetailInst = FeeDetails.findByStudentAndSemesterValueAndFeeTypeAndIsApproved(student, semInst, feeType, Status.findById(1))

            if (feeDetailInst) {
                response.student = feeDetailInst.student
                response.lateFee = lateFee
                response.term = feeDetailInst.semesterValue
                response.challanNo = feeDetailInst.challanNo
                response.courseName = student.program.courseName
                response.programFeeAmount = feeDetailInst.paidAmount
                response.feeType = feeType
                response.status = true
            } else {
                response.status = false
            }
            render response as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def assignExaminationDate = {
        if (springSecurityService.currentUser) {
            def programList = ProgramDetail.list(sort: 'courseCode')
            [programList: programList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def assignExaminationVenue = {
        if (springSecurityService.currentUser) {
            def programList = ProgramDetail.list(sort: 'courseCode')
            def obj = City.createCriteria()
            def examCenterList = obj.list {
                eq('isExamCentre', 1)
                order('cityName', 'asc')
            }
            [programList: programList, examinationCenterList: examCenterList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def getSubjectList = {
        if (springSecurityService.currentUser) {
            def subMap = [:]
            subMap = adminInfoService.subjectList(params)
            if (subMap.allSubjects.size() < 1) {
                subMap.noSubjects = true
                render subMap as JSON
            } else {
                render subMap as JSON
            }
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveExamDate = {
        if (springSecurityService.currentUser) {
            def checkStatus = [:]
//        println(params)
            def status = adminInfoService.saveExamDate(params)
            if (status.size() > 1) {
                checkStatus.saveFlag = true
            } else {
                checkStatus.saveFlag = false
            }
            render checkStatus as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveExamVenue = {
        if (springSecurityService.currentUser) {
            def status = adminInfoService.saveExamVenue(params)
            if (status) {
                render status
            } else {
                render "<h5>Error In Assigning Examination Venue</h5>"
            }
        } else {
            redirect(controller: "login", action: "auth")
        }

    }
    def generateStudentList = {
        /*action for generating student list for selected program*/
        if (springSecurityService.currentUser) {
            def studList = adminInfoService.updateStudentList(params)
            render studList as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def downloadAttendanceSheet = {
        if (springSecurityService.currentUser) {
            if (params.programSession) {
                def webRootDir = servletContext.getRealPath("/")
                def userDir = new File(webRootDir, '/Attendance')
                userDir.mkdirs()
                def excelPath = servletContext.getRealPath("/") + 'Attendance' + System.getProperty('file.separator') + 'Output' + '.xls'
                try {
                    def status = attendanceService.getStudentList(params, excelPath)
                    if (status) {
                        File myFile = new File(servletContext.getRealPath("/") + 'Attendance' + System.getProperty('file.separator') + 'Output' + '.xls')
                        response.setHeader "Content-disposition", "attachment; filename=" + 'Output' + ".xls"
                        response.contentType = new MimetypesFileTypeMap().getContentType(myFile)
                        response.outputStream << myFile.bytes
                        response.outputStream.flush()
                        myFile.delete()
                        redirect(action: 'downloadAttendanceSheet')
                    } else {
                        flash.message = "${message(code: 'student.not.found.message')}"
                        redirect(action: 'downloadAttendanceSheet')
                    }
                }
                catch (Exception e) {
//                println("This is the exception "+ e)
                    flash.message = "${message(code: 'student.exception.found.message')}"
                    redirect(action: 'downloadAttendanceSheet')
                }
            } else {
//            println("there is no parameters")
            }
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

//    def uploadInternalMarks = {
//        if (springSecurityService.currentUser) {
//        def studyCentreList = StudyCenter.list(sort: 'name')
//        def programList = ProgramDetail.list(sort: 'courseCode')
//        [programList: programList, studyCentreList: studyCentreList]
//        } else {
//            redirect(controller: "login", action: "auth")
//        }
//    }
    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def approvePayInSlip = {
        if (springSecurityService.currentUser) {
            def bankList = Bank.list(sort: 'bankName');
            def feeTypeList = FeeType.list(sort: 'type');
            [bankList: bankList, feeTypeList: feeTypeList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def getBranchList = {
        if (springSecurityService.currentUser) {
            def list = Bank.findAllById(Integer.parseInt(params.bank));
            render list.branch[0] as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

//    def studyCentreFeeApproval = {
//        if (springSecurityService.currentUser) {
//        def studyCenterList = StudyCenter.list(sort: 'name');
//        def programList = ProgramDetail.list(sort: 'courseCode')
//        [studyCenterList: studyCenterList, programList: programList]
//        } else {
//            redirect(controller: "login", action: "auth")
//        }
//    }
    def getChallanDetailsforStudent = {
        if (springSecurityService.currentUser) {
            def resultMap = [:]
            resultMap = feeDetailService.studentDetailByChallanNumber(params)
            render resultMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def saveApprovePayInSlip = {
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            Boolean result = adminInfoService.savePayInSlip(params);
            returnMap.flag = result
//        println(result);

            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def searchListStudentByChallanNo() {
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def courseNameList = [], courseFee = [], stuList = [], semester = []
            def feeDetailsInstance = FeeDetails.findAllByChallanNoAndIsApprovedAndFeeType(params.challanNo, Status.findById(1), FeeType.findById(3))
            feeDetailsInstance.each {
                if (it.student.rollNo != null) {
                    stuList << Student.findById(it.student.id)
                    courseNameList << it.student.programDetail.courseName
                    courseFee << it.paidAmount
                    semester << it.semesterValue
                }
            }
            returnMap.stuList = stuList
            returnMap.semester = semester
            returnMap.courseNameList = courseNameList
            returnMap.courseFee = courseFee
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }


    def getCourseCodeLength() {
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def progmInst = ProgramDetail.findById(Long.parseLong(params.program))
            if (progmInst.courseCode.length() == 2) {
                returnMap.status = true
            } else {
                returnMap.status = false
            }
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def searchByChallanNo() {
        if (springSecurityService.currentUser) {
            def returnMap = [:]
//        println("???????/" + params)
            returnMap = feeDetailService.studentDetailByChallanNumber(params)
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def approveFeeForStudents = {
        if (springSecurityService.currentUser) {
            Boolean status = false
            def feeDetailList = FeeDetails.findAllByChallanNo(params.payInSlipNo)
            feeDetailList.each {
                it.isApproved = Status.findById(4)
                if (it.save(flush: true)) {
                    status = true
                }
            }
            if (status) {
                flash.message = "Approved Successfully"

            } else {
                flash.message = "Unable to Approved Successfully"
            }
            redirect(action: "approvePayInSlip")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    //ADDED BY DIGVIJAY ON 19 May 2014
    @Secured(["ROLE_ADMIN"])
    def addCourses = {
        /*for adding new subject(course)*/
        if (springSecurityService.currentUser) {
            def updateMode = false
            def result
            def courseList, courseSession, courseType, projectThesis
            def programTypeList = ProgramType.list()
            if (params.id) {
                courseSession = SubjectSession.findById(Long.parseLong(params.id))
                courseList = courseSession.subjectId
                if (courseSession.isOptional) {
                    courseType = 'optional'
                } else {
                    courseType = 'core'
                }
                if (courseSession.isProjectSubject) {
                    projectThesis = 'required'
                } else {
                    projectThesis = 'notRequired'
                }
                updateMode = true
//            def xmlNodes = xmlParseInfo()
//            result = adminInfoService.generateCourseDetails(params, xmlNodes)
            }
            def marksTypeList = MarksType.list()
            def subjectSessions = programFeeService.getProgramSessions(params)
            def subjectMarksList = SubjectMarksDetail.findAllBySubjectSession(courseSession)
            def marksMap = [:]
            if (params.id) {
                [projectThesis: projectThesis, programTypeList: programTypeList, courseList: courseList, subjectInfo: result, courseSession: courseSession, updateMode: updateMode,
                 marksTypeList: marksTypeList, marksMap: marksMap, subjectSessions: subjectSessions, courseType: courseType]
            } else {
                [programTypeList: programTypeList, courseList: courseList, courseSession: courseSession, updateMode: updateMode,
                 marksTypeList  : marksTypeList, marksMap: marksMap, subjectSessions: subjectSessions, courseType: courseType]
            }
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    @Secured(["ROLE_ADMIN"])
    def listOfCourses = {
        if (springSecurityService.currentUser) {
//        params.max = Math.min(params.max ? params.int('max') : 20, 100)
            def subSessionList = [], subjectNameList = []
            def c = Subject.createCriteria()
            def subjectList = c.list() {
                order('subjectCode', 'asc')
            }
            def subSession = SubjectSession.list()
            subjectList.each {
                SubjectSession.findAllBySubjectId(it).each {
                    subSessionList << it
                    subjectNameList << it.subjectId
                }
            }
            [subjectList: subjectList, subSessionList: subSessionList, subSession: subSession]
        } else {
            redirect(controller: "login", action: "auth")
        }

    }
    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def assignRollNoGenerationDate = {

    }
    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def saveRollNoGenerationDate = {
        if (springSecurityService.currentUser) {
            def status = adminInfoService.saveRollNoGenDate(params)
            if (status) {
                flash.message = "Roll Number Generation Date Assigned Successfully"
            } else {
                flash.message = "Unable to Assign Date Successfully"
            }
            redirect(action: "assignRollNoGenerationDate")
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    def generateRollIsAllow = {
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            Boolean status = false

//        def genRollNoIns = RollNoGenerationFixture.findById(1)
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date min = genRollNoIns.startD
//            Date max = genRollNoIns.endD
//            Date cdate = new Date()
//            if (cdate.compareTo(min) >= 0 && cdate.compareTo(max) <= 0) {
//                status = true
//            }
//        }
//        catch (NullPointerException e) {
//            flash.message = "Please Assign Roll No Generation Date."
//            redirect(action: "viewProvisionalStudents")
//        }
            returnMap.status = status
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def assignLateFeeDate = {
        if (springSecurityService.currentUser) {
            def programList = []
            def programs = ProgramDetail.list(sort: 'courseCode')
            programs.each {

                if (it.lateFeeDate == null) {
                    programList.add(it)
                }
            }

            def programCategory = ProgramType.list(sort: 'type')
            [programList: programList, programCategory: programCategory]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def removeLateFeeDate = {
        if (springSecurityService.currentUser) {
            def programList = []
            def programs = ProgramDetail.list(sort: 'courseCode')
            programs.each {

                if (it.lateFeeDate == null) {
                    programList.add(it)
                }
            }

            def programCategory = ProgramType.list(sort: 'type')
            [programList: programList, programCategory: programCategory]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def deleteLateFeeDate = {
//        println(params)
        if (springSecurityService.currentUser) {
            def status = adminInfoService.removeDateLateFee(params)
            if (status) {
                flash.message = "Late Fee Date Removed Successfully"
            } else {
                flash.message = "Unable Remove Late Fee Date"
            }
            redirect(action: "removeLateFeeDate")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def loadProgram = {
//        println("params" + params)
        if (springSecurityService.currentUser) {
            def programList = [], dateList = []
            def programType = ProgramType.findById(Long.parseLong(params.type))
            def programs = ProgramDetail.findAllByProgramType(programType)
            programs.each {
                if (it.lateFeeDate) {
                    dateList << it.lateFeeDate.getDateString()
                } else {
                    dateList << ""
                }
            }

            def response = [programList: programs, dateList: dateList]
            render response as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def saveLateFeeDate = {
        if (springSecurityService.currentUser) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy")
            def date = df.parse(params.lateFeeDate)
            def programList = []
            programList.addAll(params.programs)
            programList.each {
//            println("############################3 "+it )
                def program = ProgramDetail.findById(Integer.parseInt(it))
                program.lateFeeDate = date
                if (program.save(flush: true, failOnError: true)) {
                    flash.message = "Succesfully Assigned Late Fee Date."
                }
            }
            redirect(action: "assignLateFeeDate")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def studyMaterial = {

    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def getStudentForStudyMaterial() {
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            returnMap = adminInfoService.studentForStudyMaterial(params)
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def saveStudyMaterial() {
//        println("inn"+params)
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def resultMap = adminInfoService.saveStudentForStudyMaterial(params)
//        println("********"+resultMap)
            if (resultMap) {
                returnMap.status = "true"
            } else {
                returnMap.status = "false"
            }
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def assignAdmissionPeriod() {
        /*for assigning time period for semester registration(for particular program and semester)*/
        if (springSecurityService.currentUser) {
            def admissionSession = []
            def programList = ProgramDetail.list()
            Calendar cal = Calendar.getInstance();
            int year = cal.get(cal.YEAR);
            def sessionVal = year - 1
            admissionSession << sessionVal + '-' + year
            admissionSession << sessionVal + 1 + '-' + (year + 1)
            admissionSession << sessionVal + 2 + '-' + (year + 2)
            def sessionList = Student.createCriteria().list {
                projections {
                    distinct("registrationYear")
                }
            }
            [programList: programList, admissionSession: admissionSession, sessionList: sessionList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def saveAdmissionFeePeriod() {
        /*Action for saving details of semester registration(program,session,semester,StartDate,EndDate etc) */
        if (springSecurityService.currentUser) {
            def status = adminInfoService.saveAdmissionPeriod(params)
            if (status) {
                flash.message = "Admission Period Saved Successfully"
            } else {
                flash.message = "Unable Save Successfully. "
            }
            redirect(action: "assignAdmissionPeriod")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def getAdmissionDate = {
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            if (params.programCode) {
                def programInst = ProgramDetail.findById(Long.parseLong(params.programCode))
                def semesterInst = Semester.findById(Long.parseLong(params.semester))
                def enrollInst = EnrollmentPeriod.findByProgramDetailAndSemesterAndAdmissionSessionAndStudentAdmissionYear(programInst, semesterInst, params.admissionSession, params.admissionYear)
                if (enrollInst) {
                    returnMap.startDate = sdf.format(enrollInst.startDate)
                    returnMap.endDate = sdf.format(enrollInst.endDate)
                    returnMap.status = true
                } else {
                    returnMap.startDate = ""
                    returnMap.endDate = ""
                    returnMap.status = false
                }
                render returnMap as JSON
            }
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    @Secured(["ROLE_FACULTYADVISOR", "ROLE_ADMIN", "ROLE_HOD"])
    def individualStudentUpdate = {
//        def grailsApplication = Holders.getGrailsApplication()
//        def rootImageFolder =  grailsApplication.config.my.global.variable;
//        grailsApplication.config.my.global.variable=grailsApplication.config.my.global.variable+1
//        [rootImageFolder:rootImageFolder]
    }
    def loadPayInSlipDetail = {
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def status = false
            def payMode = PaymentMode.findById(Integer.parseInt(params.pMode))
            def studentInst
            if (payMode == PaymentMode.findById(1)) {
                studentInst = FeeDetails.findByChallanNo(params.challanNo).student
            }
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy")
            def bank = []
            def branch = Branch.findAllByBranchLocation("Gauhati University")
            branch.each {
                bank << it.bank
            }
//            println(bank)

            returnMap.refNo = studentInst.challanNo
            returnMap.payDate = df.format(new Date())
            returnMap.bank = bank
            returnMap.branch = Branch.findByBranchLocation('Gauhati University').id
            returnMap.branchName = Branch.findByBranchLocation('Gauhati University').branchLocation
//        }
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    @Secured("ROLE_ADMIN")
    def deleteCourse = {
        if (springSecurityService.currentUser) {
            try {
                def status = adminInfoService.deleteTheCourse(params)

                if (status) {
                    flash.message = "Course Removed Successfully"

                }
            }
            catch (Exception e) {
                flash.message = "Unable To Remove Course"
            }
            redirect(controller: "admin", action: "listOfCourses")
        } else {
            redirect(controller: "login", action: "auth")
        }

    }
    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def loadSubject = {
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def subSessionList = [], subjectNameList = []
//        def programType = ProgramType.findById(Long.parseLong(params.type))
//        def subjectList = Subject.findAllByProgramTypeId(programType)
            def subjectList = Subject.findAll()
            subjectList.each {
                subSessionList << SubjectSession.findAllBySubjectId(it)
                subjectNameList << SubjectSession.findAllBySubjectId(it).subjectId
            }

            returnMap.subSession = subSessionList
            returnMap.subject = subjectNameList
            def response = [subjectList: subSessionList]
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    @Secured(["ROLE_FACULTYADVISOR", "ROLE_ADMIN", "ROLE_HOD"])
    def searchStudentName = {
        /*action for searching student by name in selected registration year*/
        if (springSecurityService.currentUser) {
            def sessionList = Student.createCriteria().list {
                projections {
                    distinct("registrationYear")
                }
            }
//        println(sessionList)
            [sessionList: sessionList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    @Secured(["ROLE_FACULTYADVISOR", "ROLE_ADMIN", "ROLE_HOD"])
    def searchStudentList = {
        /*Action for searching student by name*/
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def studyOfFName = []
            def courseOfFName = []
            def searchList = []
            def year = Integer.parseInt(params.session)
            def searchobj = Student.createCriteria()
            def arr = params.student.split(' ')
            if (arr.size() == 2) {

                searchList = searchobj.list {
                    or {
                        like('firstName', '%' + arr[0] + '%')
                        and {
                            like('middleName', '%' + arr[0] + '%')
                        }
                        and {
                            like('lastName', '%' + arr[0] + '%')
                        }
                    }
                    or {
                        like('firstName', '%' + arr[1] + '%')
                        and {
                            like('middleName', '%' + arr[1] + '%')
                        }
                        and {
                            like('lastName', '%' + arr[1] + '%')
                        }
                    }
                    and {
                        eq('registrationYear', year)
                    }
                    order('rollNo')
                }.unique()
            } else if (arr.size() > 2) {
                searchList = searchobj.list {
                    or {
                        like('firstName', '%' + arr[0] + '%')
                        and {
                            like('middleName', '%' + arr[0] + '%')
                        }
                        and {
                            like('lastName', '%' + arr[0] + '%')
                        }
                    }
                    or {
                        like('firstName', '%' + arr[1] + '%')
                        and {
                            like('middleName', '%' + arr[1] + '%')
                        }
                        and {
                            like('lastName', '%' + arr[1] + '%')
                        }
                    }
                    or {
                        like('firstName', '%' + arr[2] + '%')
                        and {
                            like('middleName', '%' + arr[2] + '%')
                        }
                        and {
                            like('lastName', '%' + arr[2] + '%')
                        }
                    }
                    and {
                        eq('registrationYear', year)
                    }
                    order('rollNo')
                }.unique()
            } else {
                searchList = searchobj.list {
                    or {
                        like('firstName', '%' + params.student + '%')
                        and {
                            like('middleName', '%' + params.student + '%')
                        }
                        and {
                            like('lastName', '%' + params.student + '%')
                        }
                    }
                    and {
                        eq('registrationYear', year)
                    }
                    order('rollNo')
                }.unique()
            }
            searchList.each {
                courseOfFName << it.program.courseName
            }

            returnMap.studentListByFName = searchList
            returnMap.courseOfFName = courseOfFName

            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    @Secured(["ROLE_HELP_DESK", "ROLE_ADMIN", "ROLE_ACCOUNT"])
    def generateCustomChallan = {

    }
    def loadTermFromRollNo = {
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def stuInst = Student.findByRollNo(params.data)

            def catgId = stuInst.programDetail.programType.id
            def term
            if (catgId == '1') {
                term = stuInst.programDetail.noOfAcademicYears
            } else {
                term = stuInst.programDetail.noOfTerms
            }
            returnMap.term = term
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def getStudentRollNo() {
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            returnMap.roll = Student.findByRollNo(params.data).rollNo

            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
//    def dbdump(){
//        def initialSize = 4096
//        def outStream = new ByteArrayOutputStream(initialSize)
//        def errStream = new ByteArrayOutputStream(initialSize)
//        def proc = "ls".execute()
//        proc.consumeProcessOutput(outStream, errStream)
//        proc.waitFor()
//        println 'out:\n' + outStream
//        println 'err:\n' + errStream
//    }
    def noticeBoard = {
        /*Action for creating or uploading new notice */
        if (springSecurityService.currentUser) {
            def noticeBoardIns = null
            def noticeRollIns = null
            def roleList = Role.list(sort: 'authority')
            if (params.noticeInstId) {
                noticeBoardIns = NoticeBoard.findById(Long.parseLong(params.noticeInstId))
                noticeRollIns = NoticeBoardRole.findAllByNoticeboard(noticeBoardIns).role.unique()

                [noticeIns: noticeBoardIns, roleList: roleList, noticeRollIns:noticeRollIns]
            } else {
                [roleList: roleList]
            }
        } else {
            redirect(controller: "login", action: "auth")
        }

    }
    def noticeBoardSave = {
        /*for saving notice details*/
        if (springSecurityService.currentUser) {
            def result
            def f = request.getFile('fle')
            if (f.originalFilename) {
                def noticeInst
                if (params.noticeUpdate) {
                    noticeInst = NoticeBoard.findById(Long.parseLong(params.noticeUpdate))
                    if (noticeInst) {
                        boolean fileSuccessfullyDeleted = new File(noticeInst.fileName).delete()
                    }
                }
                def servletContext = ServletContextHolder.servletContext
                def storagePath = servletContext.getRealPath("/") + 'Noticeboard'
                def storagePathDirectory = new File(storagePath)
                if (!storagePathDirectory.exists()) {
                    storagePathDirectory.mkdirs()
                }
                f.transferTo(new File(storagePath + System.getProperty('file.separator') + f.originalFilename))
                File newFile = new File(storagePath + System.getProperty('file.separator') + f.originalFilename)
                if (newFile.exists()) {
                    result = adminInfoService.uploadNoticeBoard(newFile.absolutePath, params)
                    if (result) {
                        flash.message = "Saved Successfully"
                    } else {
                        flash.message = "Unable to Save Successfully"
                    }
                }
            } else {
                if (params.noticeUpdate) {
                    result = adminInfoService.uploadNoticeBoardwithoutFile(params)

                    if (result) {
                        flash.message = "Saved Successfully"
                    } else {
                        flash.message = "Unable to Save Successfully"
                    }
                }
            }
            redirect(controller: "admin", action: "noticeBoard")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }


    def noticeBoardView = {
        /*To show the list of uploaded notice*/
        if (springSecurityService.currentUser) {
            def userInst = springSecurityService.currentUser
            def userRoleInst = UserRole.findByUser(userInst).role
            def noticeList = []
            if (params.archive) {
                if (params.archiveNoticeList) {
                    for (def i = 0; i < params.archiveNoticeList.size(); i++) {
                        noticeList << NoticeBoard.findById(Long.parseLong(params.archiveNoticeList.getAt(i)))
                    }
                }
            } else {
                def noticeListAll = NoticeBoard.findAllByIsArchive(false)
                def today = new Date()
                for (def j = 0; j < noticeListAll.size(); j++) {
                    long diffDays = (today.getTime() - noticeListAll[j].noticeDate.getTime()) / (24 * 60 * 60 * 1000);
                    if (diffDays > 60) {
                        noticeListAll[j].isArchive = true
                    }
                }


                if (userRoleInst == Role.findById(1)) {
                    noticeList = NoticeBoard.findAllByIsArchive(false)
                } else {
                    def searchobj1 = NoticeBoard.createCriteria()
                    def noticeBoardRoleList = NoticeBoardRole.findAllByRole(userRoleInst).noticeboard.unique()
                    noticeList = searchobj1.list {
                        eq('isArchive', false)
                        and {
                            'in'('id', noticeBoardRoleList.id)
                        }

                    }
                }
            }
            def filePath = servletContext.getRealPath("/") + 'Noticeboard' + System.getProperty('file.separator')

            [noticeList: noticeList, filePath: filePath]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }


    def download = {
        /*Action for download the notice*/
        if (springSecurityService.currentUser) {
            def file = new File(params.fileName)
            def fileInputStream = new FileInputStream(file)
            def outputStream = response.getOutputStream()

            byte[] buffer = new byte[4096];
            int len;
            while ((len = fileInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }

            outputStream.flush()
            outputStream.close()
            fileInputStream.close()
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def noticeBoardDel = {
        /*action provide interface to delete and edit notice*/
        if (springSecurityService.currentUser) {
            def noticeList = NoticeBoard.list()
            def filePath = servletContext.getRealPath("/") + 'Noticeboard' + System.getProperty('file.separator')
            [noticeList: noticeList, filePath: filePath]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }


    def delNotice = {
        /*action for deleting the notice*/
        if (springSecurityService.currentUser) {
            def noticeInst = NoticeBoard.findById(Long.parseLong(params.noticeInstId))
            def noticeRoleList = NoticeBoardRole.findAllByNoticeboard(noticeInst)
            def status = true
            noticeRoleList.each {
                it.delete(flush: true)
                if (NoticeBoardRole.exists(it.id)) {
                    status = false
                }
            }
            if (status) {
                noticeInst.delete(flush: true)
            }
            if (!NoticeBoard.exists(noticeInst.id)) {
                boolean fileSuccessfullyDeleted = new File(noticeInst.fileName).delete()
                if (fileSuccessfullyDeleted) {
                    flash.message = "Delete Successfully"
                }
            }
            redirect(controller: "admin", action: "noticeBoardDel")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def loadArchiveNotice = {
        /*Action for loading earlier(archived) notice*/
        if (springSecurityService.currentUser) {
            def noticeList
            def userInst = springSecurityService.currentUser
            def userRoleInst = UserRole.findByUser(userInst).role
            if (userRoleInst == Role.findById(1)) {
                noticeList = NoticeBoard.list()
            } else {
                def noticeBoardRoleList = NoticeBoardRole.findAllByRole(userRoleInst).noticeboard.unique()
                def searchobj1 = NoticeBoard.createCriteria()
                noticeList = searchobj1.list {
                    'in'('id', noticeBoardRoleList.id)
                }
            }

            def archiveNoticeList = []
            DateFormat df = new SimpleDateFormat("yyyy-MM")

            def month = params.month
            noticeList.each {
                def dbDate = df.format(it.noticeDate)

                if (month == dbDate) {

                    archiveNoticeList << it.id

                }
            }
            if (archiveNoticeList.size() == 0) {

                flash.message = "No result to display"
            }
            redirect(controller: "admin", action: "noticeBoardView", params: [archiveNoticeList: archiveNoticeList, archive: 'archive'])
        } else {
            redirect(controller: "admin", action: "noticeBoardView")
        }
    }


    @Secured(["ROLE_LIBRARY", 'ROLE_DEPARTMENT_LIBRARY'])
    def addCatalog = {
        /*action for adding a new catalog*/
        def department = null
        def employeeCode = null
        def roles = springSecurityService.getPrincipal().getAuthorities()
        for (def role in roles) {
            if (role.getAuthority() == "ROLE_DEPARTMENT_LIBRARY") {
                employeeCode = springSecurityService.currentUser.employeeCode
                department = Employee.findByEmployeeCode(employeeCode).department
            }
        }
        def catIns = null
        def catalogTypeList = CatalogType.list()
        def catalogDepartmentList = Department.list()
        if (params.catalogInstId) {
            catIns = Catalog.findById(Long.parseLong(params.catalogInstId))
            [catalogIns: catIns, catalogTypeList: catalogTypeList, catalogDepartmentList: catalogDepartmentList, department: department]

        } else {
            [catalogTypeList: catalogTypeList, catalogDepartmentList: catalogDepartmentList, department: department]
        }

    }
    @Secured(["ROLE_LIBRARY", "ROLE_DEPARTMENT_LIBRARY"])
    def saveCatalog = {
        /*action for saving catalog details*/
        def catalogInst
        if (params.catalogUpdate) {
            catalogInst = Catalog.findById(Long.parseLong(params.catalogUpdate))
            catalogInst.department = Department.get(Long.parseLong(params.catalogDepartment))
            catalogInst.isbn = params.catalogIsbn
            catalogInst.title = params.catalogTitle
            catalogInst.author = params.catalogAuthor
            catalogInst.publisher = params.catalogPublisher
            catalogInst.year = Integer.parseInt(params.catalogYear)
            catalogInst.quantity = Integer.parseInt(params.catalogQuantity)
            catalogInst.availableCatalog = Integer.parseInt(params.catalogQuantity)
            if (catalogInst.isbn == params.catalogIsbn) {
                flash.message = "Updated Successfully"
            } else {
                flash.message = "Not Updated"
            }
        } else {
            catalogInst = new Catalog()
            catalogInst.type = CatalogType.findById(Long.parseLong(params.catalogType))
            catalogInst.department = Department.get(Long.parseLong(params.catalogDepartment))
            catalogInst.isbn = params.catalogIsbn
            catalogInst.title = params.catalogTitle
            catalogInst.author = params.catalogAuthor
            catalogInst.publisher = params.catalogPublisher
            catalogInst.year = Integer.parseInt(params.catalogYear)
            catalogInst.quantity = Integer.parseInt(params.catalogQuantity)
            catalogInst.availableCatalog = Integer.parseInt(params.catalogQuantity)
            if (catalogInst.save(failOnError: true, flush: true)) {
                flash.message = "Saved Successfully"
            } else {
                flash.message = "Not Saved "
            }
        }
        redirect(controller: "admin", action: "addCatalog")

    }
    @Secured(["ROLE_LIBRARY", "ROLE_DEPARTMENT_LIBRARY"])
    def editCatalog = {
        /*action for editing and deleting catalog*/
        def department = null
        def catalogList = null
        def roles = springSecurityService.getPrincipal().getAuthorities()
        for (def role in roles) {
            if (role.getAuthority() == "ROLE_DEPARTMENT_LIBRARY") {
                department = springSecurityService.currentUser.department
            }
        }
        if (department) {
            catalogList = Catalog.findAllByDepartment(department)
        } else {
            catalogList = Catalog.list()
        }
        [catalogList: catalogList]

    }
    @Secured(["ROLE_LIBRARY", "ROLE_DEPARTMENT_LIBRARY"])
    def viewCatalog = {
        /*action for showing list of catalogs*/
        def department = null
        def catalogList = null
        def roles = springSecurityService.getPrincipal().getAuthorities()
        for (def role in roles) {
            if (role.getAuthority() == "ROLE_DEPARTMENT_LIBRARY") {
                department = springSecurityService.currentUser.department
            }
        }
        if (department) {
            catalogList = Catalog.findAllByDepartment(department)
        } else {
            catalogList = Catalog.list()
        }
        [catalogList: catalogList]

    }
    @Secured(["ROLE_LIBRARY", "ROLE_DEPARTMENT_LIBRARY"])
    def delCatalog = {
        /*action for deleting catalog*/
        def catalogInst = Catalog.findById(Long.parseLong(params.catalogInstId))
        catalogInst.delete()
        if (!Catalog.exists(catalogInst.id)) {
            boolean fileSuccessfullyDeleted = new File(catalogInst.fileName).delete()
            if (fileSuccessfullyDeleted) {
                flash.message = "Delete Successfully"
            }
        }
        redirect(controller: "admin", action: "editCatalog")

    }
    @Secured(["ROLE_LIBRARY"])
    def catalogType = {
        /*action for adding catalog type*/
        if (params.view) {
            def catagoryTypeList = CatalogType.list()
            [catagoryTypeList: catagoryTypeList]
        } else if (params.catId) {
            def catagoryTypeInst = CatalogType.findById(Long.parseLong(params.catId))
            [catagoryTypeInst: catagoryTypeInst]
        }
    }
    @Secured(["ROLE_LIBRARY"])
    def saveCatalogType = {
        /*action for saving new or edited catalog type*/
        def catalogTypeInst
        if (params.catgoryTypeId) {
            catalogTypeInst = CatalogType.findById(Long.parseLong(params.catgoryTypeId))
            catalogTypeInst.catalogTypeName = params.catalogName
            if (catalogTypeInst.catalogTypeName == params.catalogName) {
                flash.message = "Updated Successfully"
            } else {
                flash.message = "Not Updated "
            }
        } else {
            catalogTypeInst = new CatalogType()
            catalogTypeInst.catalogTypeName = params.catalogName
            if (catalogTypeInst.save(failOnError: true, flush: true)) {
                flash.message = "Saved Successfully"
            } else {
                flash.message = "Not Saved "
            }
        }
        redirect(controller: "admin", action: "catalogType")
    }
    @Secured(["ROLE_LIBRARY"])
    def delCatalogType = {
        /*action for deleting catalog type*/
        def catalogTypeInst = CatalogType.findById(Long.parseLong(params.catId))
        catalogTypeInst.delete(flush: true)
        if (CatalogType.exists(catalogTypeInst.id)) {
            flash.message = "Unable Delete "
        } else {
            flash.message = "deleted Successfully"
        }
        redirect(controller: "admin", action: "catalogType", params: [view: "view"])
    }

    @Secured(["ROLE_LIBRARY"])
    def catalogCatagory = {
        if (params.view) {
            def catalogCatagoryList = CatalogCatagory.list()
            [catalogCatagoryList: catalogCatagoryList]
        } else if (params.catId) {
            def catalogCatagoryInst = CatalogCatagory.findById(Long.parseLong(params.catId))
            [catalogCatagoryInst: catalogCatagoryInst]
        }
    }
    @Secured(["ROLE_LIBRARY"])
    def saveCatalogCatagory = {
        def catalogCatagoryInst
        if (params.catalogCatagoryId) {
            catalogCatagoryInst = CatalogCatagory.findById(Long.parseLong(params.catalogCatagoryId))
            catalogCatagoryInst.catalogCatagoryName = params.catalogCatagoryName
            if (catalogCatagoryInst.catalogCatagoryName == params.catalogCatagoryName) {
                flash.message = "Updated Successfully"
            } else {
                flash.message = "Not Updated "
            }
        } else {
            catalogCatagoryInst = new CatalogCatagory()
            catalogCatagoryInst.catalogCatagoryName = params.catalogCatagoryName
            if (catalogCatagoryInst.save(failOnError: true, flush: true)) {
                flash.message = "Saved Successfully"
            } else {
                flash.message = "Not Saved "
            }
        }
        redirect(controller: "admin", action: "catalogCatagory")
    }


    @Secured(["ROLE_LIBRARY", "ROLE_DEPARTMENT_LIBRARY"])
    def bookIssue = {
        /*action for book issue & return interface*/
        def department = null
        def employeeCode = null
        def roles = springSecurityService.getPrincipal().getAuthorities()
        for (def role in roles) {
            if (role.getAuthority() == "ROLE_DEPARTMENT_LIBRARY") {
                employeeCode = springSecurityService.currentUser.employeeCode
                department = Employee.findByEmployeeCode(employeeCode).department
            }
        }
        def catalogTypeList = CatalogType.list()
        def catalogDepartmentList = Department.list()
//        def catalogCatagoryList = CatalogCatagory.list()

        [catalogTypeList: catalogTypeList, department: department, catalogDepartmentList: catalogDepartmentList]

    }

    def getBooksName = {
        def returnMap = [:]
        def departmentIns = Department.findById(Integer.parseInt(params.catalogDepartment))
        def catalogTypeIns = CatalogType.get(params.catalogType)
        def bookList = Catalog.findAllByDepartmentAndType(departmentIns, catalogTypeIns)
        bookList.each {
            def issuedCount = BookIssue.countByCatalog(it)
            it.availableCatalog = it.quantity - issuedCount
            it.save(flush: true)
        }
        def bookIssuedList = BookIssue.findAllByIssuingPersonId(params.studentRollNo).catalog
        returnMap.bookIssuedList = bookIssuedList
        returnMap.bookList = bookList
        render returnMap as JSON

    }

    def saveBookIssue = {
        def returnMap = [:]
        def isPerform=false
        def userInst = springSecurityService.currentUser
        def bookList = params.bookList.split(",")
        def issuedBooks = BookIssue.findAllByIssuingPersonId(params.id)
        def alreadyIssuedCount=issuedBooks.size()
        def newIssuedCount=bookList.size()
        issuedBooks.each {
            it.delete(flush: true)
            returnMap.deleted = true
        }

        def department = Department.findById(Integer.parseInt(params.catalogDepartment))
        def catalogTypeObj = CatalogType.get(params.catalogType)
        bookList.each {
            if(it!=""){
                def obj = new BookIssue()
                def catalogObj = Catalog.get(it.toString())
                catalogObj.availableCatalog = catalogObj.availableCatalog - 1
                obj.catalog = catalogObj
                obj.department = department
                obj.catalogType = catalogTypeObj
                obj.issuingPersonId = params.id
                obj.issueTo = params.type
                obj.user = userInst
                obj.save(failOnError: true)
                catalogObj.save(failOnError: true)
                returnMap.flag = "true"
                isPerform=true
            }
        }
        if(alreadyIssuedCount==0 && !isPerform){
            returnMap.flag = "blank"
        }
        else if (alreadyIssuedCount>0 && !isPerform){
            returnMap.flag = "allReturn"
        }
        render returnMap as JSON
    }
    def delCatalogCatagory = {
        def catalogCatagoryInst = CatalogCatagory.findById(Long.parseLong(params.catId))
        catalogCatagoryInst.delete(flush: true)
        if (CatalogCatagory.exists(catalogCatagoryInst.id)) {
            flash.message = "Unable Delete "
        } else {
            flash.message = "deleted Successfully"
        }
        redirect(controller: "admin", action: "catalogCatagory", params: "view")

    }

    def getTotalBooks = {
        def returnMap = [:]
        if (params.type == "student") {
            returnMap.value = grailsApplication.config.maxBooksForStudents
        } else {
            returnMap.value = grailsApplication.config.maxBooksForFaculty
        }
        render returnMap as JSON

    }


    def getIssuedBooks = {
        def returnMap = [:]
        returnMap.value = BookIssue.countByIssuingPersonId(params.issuingPersonId)
        if (params.type == 'student') {
            def userId = User.findByUsername(params.issuingPersonId)
            if (userId) {
                returnMap.dept = userId.department.id
                returnMap.status = true
            } else {
                returnMap.status = false
            }

        } else if (params.type == 'faculty') {
            def userId = User.findByEmployeeCode(params.issuingPersonId)
            if (userId) {
                returnMap.dept = userId.department.id
                returnMap.status = true
            } else {
                returnMap.status = false
            }
        }
        render returnMap as JSON

    }
    def searchEquipmentList = {
        def equipmentList = []
        def eqpObj = Equipment.createCriteria()
        if (params.searchType == 'byName') {
            equipmentList = eqpObj.listDistinct() {
                ilike('equipmentName', "%" + params.searchText + "")
                or {
                    ilike('equipmentName', "" + params.searchText + '%')
                }
                or {
                    ilike('equipmentName', '%' + params.searchText + '%')
                }
                or {
                    eq('equipmentName', params.searchText, [ignoreCase: true])
                }
            }
        } else if (params.searchType == 'byId') {
            equipmentList = eqpObj.list() {
                eq('equipmentId', Integer.parseInt(params.searchText))
            }
        } else if (params.searchType == 'byManufacturer') {
            equipmentList = eqpObj.list() {
                eq('manufacturer', params.searchText)
            }
        } else if (params.searchType == 'byType') {
            equipmentList = eqpObj.list() {
                eq('equipmentType', params.searchText)
            }
        }
        render equipmentList as JSON
    }

    def approveStudentRegistration() {
        def programList = ProgramDetail.list(sort: 'courseCode')
        [programList: programList]
    }

    def getStudStatus() {
        def progSessionInst = ProgramSession.findById(Long.parseLong(params.session))
        def stuSubObj = StudentSubject.createCriteria()
        def entryList = stuSubObj.list {
            eq('programSession', progSessionInst)
            eq('status', Status.findById(1))
            eq('semester', Semester.findById(Long.parseLong(params.semester)))
        }
        def StudentList = entryList.student.unique()
        render StudentList as JSON
    }
    def updateSemRegStatus = {
        def studentIDS = (params.selectedStudents).split(',')
        def progSession = ProgramSession.findById(Long.parseLong(params.programSession))
        def semester = Semester.findById(Long.parseLong(params.programSemester))
        def status = Status.findById(1)
        def approv = Status.findById(4)
        def unapprove = Status.findById(5)
        if (params.approvalStatus == 'Approved') {
            for (def i = 0; i < studentIDS.length; i++) {
                def stuInst = Student.findById(Long.parseLong(studentIDS[i]))
                def subjectList = StudentSubject.findAllByProgramSessionAndSemesterAndStudentAndStatus(progSession, semester, stuInst, status)
                subjectList.each {
                    it.status = approv
                }
            }
            flash.message = 'Approved Selected Students'
        } else {
            for (def i = 0; i < studentIDS.length; i++) {
                def stuInst = Student.findById(Long.parseLong(studentIDS[i]))
                def subjectList = StudentSubject.findAllByProgramSessionAndSemesterAndStudentAndStatus(progSession, semester, stuInst, status)
                subjectList.each {
                    it.status = unapprove
                }
            }
            flash.message = 'Unapproved Selected Students'
        }

        redirect(controller: "admin", action: "approveStudentRegistration")
    }
    def loadProgramSessions = {
        def sessionMap = [:]
        def idList = [], nameList = []
        def progSessionList = ProgramSession.findAllByProgramDetailId(ProgramDetail.findById(Long.parseLong(params.programId)))
        progSessionList.each {
            idList << it.id
            nameList << it.sessionOfProgram
        }
        sessionMap.id = idList
        sessionMap.session = nameList
        render sessionMap as JSON
    }
    def loadProgramSemester = {
        def resultMap = [:]
        def semId = [], semNo = []
        def semesterList = CourseSubject.findAllByProgramSession(ProgramSession.findById(Long.parseLong(params.progSession))).semester.unique()
        semesterList.each {
            semId << it.id
            semNo << it.semesterNo
        }
        resultMap.semId = semId
        resultMap.semNo = semNo
        render resultMap as JSON
    }
    def viewStudentSubjectDetails = {
        def progSessionInst = ProgramSession.findById(Long.parseLong(params.session))
        def stuInst = Student.findById(Long.parseLong(params.studId))
        def semInst = Semester.findById(Long.parseLong(params.semester))
        def stuSubList = StudentSubject.findAllByProgramSessionAndSemesterAndStudent(progSessionInst, semInst, stuInst).subjectSession.subjectId
        render stuSubList as JSON
    }

    def bookIssuedToStudent = {
        def username = springSecurityService.currentUser.username
        def issuedBookList = BookIssue.findAllByIssuingPersonId(username)
        [issuedBookList: issuedBookList]
    }
    def bookIssuedToEmployee = {
        def username = springSecurityService.currentUser.employeeCode
        def issuedBookList = BookIssue.findAllByIssuingPersonId(username)
        [issuedBookList: issuedBookList]
    }


    def mailbox = {

    }

    def loadSemesterForProgram = {
        /*Action for getting semesters for particular program*/
        def programInst = ProgramDetail.findById(Long.parseLong(params.data))
        def progSession = ProgramSession.findByProgramDetailId(programInst)
        def semList = Semester.findAllByProgramSession(progSession)
        render semList as JSON
    }
    @Secured(["ROLE_ADMIN", "ROLE_HOD"])
    def assignFacultyAdvisor = {
        /*Interface for assigning faculty advisor*/
        if (springSecurityService.currentUser) {
            def admissionSession = []
            def programList = ProgramDetail.list(sort: 'courseName')
            Calendar cal = Calendar.getInstance();
            int year = cal.get(cal.YEAR);
            def sessionVal = year - 1
            admissionSession << sessionVal + '-' + year
            admissionSession << sessionVal + 1 + '-' + (year + 1)
            admissionSession << sessionVal + 2 + '-' + (year + 2)
            [programList: programList, admissionSession: admissionSession]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def loadFacultyAdvisor = {
        /*Action for loading employee and semester for assigning faculty advisor*/
        if (springSecurityService.currentUser) {
            def returnList = [:], deptUserList = [], employeeNameList = [], userIdList = []
            def programInst = ProgramDetail.findById(Long.parseLong(params.program))
            def programSession = ProgramSession.findByProgramDetailId(programInst)
            def semList = Semester.findAllByProgramSession(programSession)
            def userInstList = UserRole.findAllByRole(Role.findByAuthority('ROLE_FACULTYADVISOR')).user.unique()
            userInstList.each {
                if (it.department.id == programInst.programBranch.department.id) {
                    deptUserList << it
                }
            }
            deptUserList.each {
                def employeeInst = Employee.findByEmployeeCode(it.employeeCode)
                def employeeName
                if (employeeInst.middleName) {
                    employeeName = employeeInst.firstName + " " + employeeInst.middleName + " " + employeeInst.lastName
                } else {
                    employeeName = employeeInst.firstName + " " + employeeInst.lastName
                }
                employeeNameList << employeeName
                userIdList << it.id

            }
            returnList.employeeNameList = employeeNameList
            returnList.userIdList = userIdList
            returnList.semList = semList
            render returnList as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def saveAssignFaculty = {
        /*Action for saving details of assigned faculty advisor*/
        if (springSecurityService.currentUser) {
            def result = adminInfoService.saveAssignFaculty(params)
            if (result) {
                flash.message = "Faculty Advisor Successfully Assigned"
            } else {
                flash.message = "Faculty Advisor Already Assigned or Some Error Occurred."
            }
            redirect(controller: "admin", action: "assignFacultyAdvisor")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def xmlParseInfo = {
        XmlParser parser = new XmlParser()
        def docDirectory = ServletContextHolder.servletContext.getRealPath(System.getProperty("file.separator"))
        def dir = docDirectory + System.getProperty("file.separator") + "subjectPassMarks" + System.getProperty("file.separator") + "subjectMarksRule.xml"

        def xmlData = parser.parse(new FileInputStream(dir))
        return xmlData
    }
    def xmlParseRuleInfo = {
        XmlParser parser = new XmlParser()
        def docDirectory = ServletContextHolder.servletContext.getRealPath(System.getProperty("file.separator"))
        def dir = docDirectory + System.getProperty("file.separator") + "ruleEngine" + System.getProperty("file.separator") + "Rule.xml"

        def xmlData = parser.parse(new FileInputStream(dir))

        return xmlData
    }

    def viewListOfAssignDate() {
        /*for showing list of semester registration date*/
        if (springSecurityService.currentUser) {
//            params.max = Math.min(params.max ? params.int('max') : 10, 100)
            def c = EnrollmentPeriod.createCriteria()
            def enrollPeriodList = c.list() {
            }
            [enrollPeriodList: enrollPeriodList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def viewListOfAssignFA() {
        /*for showing list of assigned faculty advisor*/
        if (springSecurityService.currentUser) {
//            params.max = Math.min(params.max ? params.int('max') : 10, 100)
            def c = FacultyAdvisor.createCriteria()
            def facultyAdvisorList = c.list() {
            }
            [facultyAdvisorList: facultyAdvisorList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def deleteAssignedDate() {
        /*Action for deleting particular semester registration period*/
        if (springSecurityService.currentUser) {
            if (params.id) {
                def assignedDateInst = EnrollmentPeriod.findById(Long.parseLong(params.id))
                def assignedDateId = assignedDateInst.id
                assignedDateInst.delete(flush: true)
                if (EnrollmentPeriod.exists(assignedDateId)) {
                    flash.message = 'Unable To Delete'
                } else {
                    flash.message = 'Deleted Succesfully'
                }
                redirect(controller: "admin", action: "viewListOfAssignDate")
            }
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def deleteAssignedFA() {
        /*for deleting particular faculty advisor*/
        if (springSecurityService.currentUser) {
            if (params.id) {
                def assignedFAInst = FacultyAdvisor.findById(Long.parseLong(params.id))
                def assignedFAId = assignedFAInst.id
                assignedFAInst.delete(flush: true)
                if (FacultyAdvisor.exists(assignedFAId)) {
                    flash.message = 'Unable To Delete'
                } else {
                    flash.message = 'Deleted Succesfully'
                }
                redirect(controller: "admin", action: "viewListOfAssignFA")
            }
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    @Secured(["ROLE_ADMIN"])
    def deleteCurrentSemRegistration = {
        /*Action for Interface which provide option for deleting semester registration of particular student*/
        if (springSecurityService.currentUser) {
            if (params.id) {
                def assignedFAInst = FacultyAdvisor.findById(Long.parseLong(params.id))
                def assignedFAId = assignedFAInst.id
                assignedFAInst.delete(flush: true)
                if (FacultyAdvisor.exists(assignedFAId)) {
                    flash.message = 'Unable To Delete'
                } else {
                    flash.message = 'Deleted Succesfully'
                }
                redirect(controller: "admin", action: "viewListOfAssignFA")
            }
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def loadCurrentSemDetails = {
        /*Load Student's current semester details by Roll No*/
        if (springSecurityService.currentUser) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy")
            def resultMap = [:], subjectName = [], semesterNo = [], status = []
            def feeAcademicSession = [], feeSem = [], feeDate = [], feeAmount = [], feeRNo = [], feeStatus = []
            def student = Student.findByRollNo(params.rollNo)
            def semester = Semester.findBySemesterNoAndProgramSession(student.semester, ProgramSession.findByProgramDetailId(student.program))
            def feeDtails = SemesterFeeDetails.findAllByStudentAndSemester(student, semester)
            def studentSubjectList = StudentSubject.findAllByStudentAndSemester(student, semester)
            feeDtails.each {
                feeAcademicSession << it.academicSession
                feeSem << it.semester.semesterNo
                feeDate << df.format(it.guReceiptDate)
                feeAmount << it.guReceiptAmount
                feeRNo << it.guReceiptNo
                feeStatus << it.status.status

            }
            studentSubjectList.each {
                subjectName << it.subjectSession.subjectId.subjectName
                semesterNo << it.semester.semesterNo
                status << it.status.status
            }
            resultMap.feeAcademicSession = feeAcademicSession
            resultMap.feeSem = feeSem
            resultMap.feeDate = feeDate
            resultMap.feeAmount = feeAmount
            resultMap.feeRNo = feeRNo
            resultMap.feeStatus = feeStatus
            resultMap.subjectName = subjectName
            resultMap.semesterNo = semesterNo
            resultMap.status = status
            resultMap.studentSubjectList = studentSubjectList
//            println("---------------------"+resultMap)
            render resultMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def deleteCurrentSemRegDetails() {
        /*Action for deleting semester registration of selected student*/
        def status = true
        def student = Student.findByRollNo(params.rollNo)
        def semester = Semester.findBySemesterNoAndProgramSession(student.semester, ProgramSession.findByProgramDetailId(student.program))
        def feeDtails = SemesterFeeDetails.findAllByStudentAndSemester(student, semester)
        def studentSubjectList = StudentSubject.findAllByStudentAndSemester(student, semester)
        feeDtails.each {
            def instId = it.id
            if (status) {
                it.delete(flush: true)
                if (SemesterFeeDetails.exists(instId)) {
                    status = false
                }
            }

        }
        studentSubjectList.each {
            def instId = it.id
            if (status) {
                it.delete(flush: true)
                if (StudentSubject.exists(instId)) {
                    status = false
                }
            }
        }
        if (status) {
            student.semesterRegistration = 0
            if (student.program.programType.id == 4) {
                if (student.semester == 3) {
                    student.semester = 0
                } else {
                    student.semester = (student.semester - 1)
                }
            } else {
                student.semester = (student.semester - 1)
            }
            flash.message = "Delete Succesfully"
        } else {
            flash.message = "Unable to Delete Succesfully"
        }
        redirect(controller: "admin", action: "deleteCurrentSemRegistration")
    }

    @Secured(["ROLE_ADMIN"])
    def createGradeConversionRule() {
        /*for creating rule grade conversion*/
        if (springSecurityService.currentUser) {

        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    @Secured(["ROLE_ADMIN"])
    def saveGradeConversionRule() {
        /*action for saving details of grade conversion rule*/
        if (springSecurityService.currentUser) {
            def toNo, fromNo, grade, result
            adminInfoService.deleteGradeConversionRuleInXmlFile()
            if (params.toNo.getClass() == String) {
                toNo = params.toNo
                fromNo = params.fromNo
                grade = params.grade
                result = adminInfoService.createXMLFile(toNo, fromNo, grade)
            } else {
//                for (def i=0;i<params.toNo.length;i++){
                toNo = params.toNo
                fromNo = params.fromNo
                grade = params.grade
                result = adminInfoService.createXMLFile(toNo, fromNo, grade)
//                }
            }
            redirect(controller: "admin", action: "createGradeConversionRule")
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    @Secured(["ROLE_ADMIN"])
    def viewGradeConversionRule() {
        /*Action for showing details of grade conversion rule*/
        if (springSecurityService.currentUser) {
            def xmlNodes = xmlParseRuleInfo()
            def result = adminInfoService.generateGradeConversionRuleDetails(params, xmlNodes)
            [toList: result.toList, fromList: result.fromList, gradeList: result.gradeList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def deleteConversionRule() {
        if (springSecurityService.currentUser) {
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def createPermanentRule() {
        if (springSecurityService.currentUser) {

        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def savePermanentRule() {
        if (springSecurityService.currentUser) {
            def result = adminInfoService.createPermanentRuleFile(params)
            if (result) {
                flash.message = "Permanent Rule Saved Successfully."
            }
            redirect(controller: "admin", action: "createPermanentRule")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def viewRuleDetails() {
        if (springSecurityService.currentUser) {
//            def xmlNodes = xmlParseRuleInfo()
            def symbolViewList = [], symbolTextList = []
            def ruleSymbolList = RuleSymbols.list()
            ruleSymbolList.each {
                symbolViewList << it.symbol
                if (it.examType != null && it.ruleNames == null) {
                    symbolTextList << ExamType.findById(Long.parseLong(it.examType)).examTypeName + "_" + it.text

                } else if (it.examType != null && it.ruleNames == null) {
                    symbolTextList << ExamType.findById(Long.parseLong(it.examType)).examTypeName + "_" + it.text
                } else if (it.examType == null && it.ruleNames != null) {
                    symbolTextList << RuleNames.findById(Long.parseLong(it.ruleNames)).rule + "_" + it.text
                } else if (it.examType == null && it.ruleNames == null) {
                    symbolTextList << it.text
                }
            }
            def ruleInst=Rule.findById(Long.parseLong(params.id))
            [symbolTextList: symbolTextList, symbolViewList: symbolViewList, ruleInst:ruleInst]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def createRelativeRule() {
        if (springSecurityService.currentUser) {

        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def viewRelativeRule() {
        if (springSecurityService.currentUser) {
            def xmlNodes = xmlParseRuleInfo()
            def result = adminInfoService.generateRelativeRuleDetails(params, xmlNodes)
            def relativeMarksFormula = covertFormula(result.relativeMarks)
            [isRequired: result.isRequired.toUpperCase(), relativeMarksFormula: relativeMarksFormula]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveRelativeRule() {
        if (springSecurityService.currentUser) {
            def result = adminInfoService.createRelativeRuleFile(params)
            if (result) {
                flash.message = "Relative Rule Saved Successfully."
            }
            redirect(controller: "admin", action: "createRelativeRule")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def covertFormula(String str) {
        if (springSecurityService.currentUser) {

            if (str.contains('A')) {

                str = str.replace('A', 'class test total')
            }
            if (str.contains('B')) {
                str = str.replace('B', 'end semester theory marks secured')
            }
            if (str.contains('D')) {
                str = str.replace('D', 'end semester practical marks secured')
            }
            if (str.contains('E')) {
                str = str.replace('E', 'maximum practical marks')
            }
            if (str.contains('F')) {
                str = str.replace('F', 'theory marks')
            }
            if (str.contains('G')) {
                str = str.replace('G', 'practical marks')
            }
            if (str.contains('H')) {
                str = str.replace('H', 'maximum marks secured among all students')
            }
            if (str.contains('I')) {
                str = str.replace('I', 'total marks secured from permanent rule')
            }
            return str.toUpperCase()
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def createExamType() {
        if (springSecurityService.currentUser) {
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def createExamSubType() {
        if (springSecurityService.currentUser) {
            def examTypeList = ExamType.list()
            [examTypeList: examTypeList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveSubExamType() {
        if (springSecurityService.currentUser) {
            def result = adminInfoService.saveSubExamType(params)
            if (result) {
                flash.message = "Saved Successfully."
            } else {
                flash.message = "Unable to Saved Successfully.Symbol may already Exist."
            }
            redirect(controller: "admin", action: "createExamSubType")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveExamType() {
        if (springSecurityService.currentUser) {
            def result = adminInfoService.saveExamType(params)
            if (result) {
                flash.message = "Saved Successfully."
            } else {
                flash.message = "Unable to Saved Successfully.Symbol may already Exist."
            }
            redirect(controller: "admin", action: "createExamType")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def viewExamType() {
        /*for showing exam Type and provide button to delete exam type */
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def c = ExamType.createCriteria()
            def examTypeList = c.list() {
                order('examTypeName', 'asc')
            }

            [examTypeList: examTypeList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def viewExamSubType() {
        /*for showing exam sub Type and provide button to delete exam sub type */
        if (springSecurityService.currentUser) {
            def returnMap = [:],examTypeList=[]
            def c = ExamSubType.createCriteria()
            def examTypeLists = c.list() {
                order('examTypeId', 'asc')
            }
            examTypeLists.each{
                if(ExamType.findById(Long.parseLong(it.examTypeId))){
                    examTypeList<<it
                }
                else{
                    it.delete(flush: true)
                }
            }

            [examSubTypeList: examTypeList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def loadHomePageMessage() {
        if (springSecurityService.currentUser) {
            def currentUser = User.findById(springSecurityService.getCurrentUser().id)
            def mesgList = LoginAlerts.findAllByUserAndViewStatus(currentUser, 0)
            def msges = null
            mesgList.each {
                it.viewStatus = 1

            }
//            println(msges)
            render mesgList as JSON

        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def createNewRule() {
        def calculationFor = []
        def calculationForID = []
        def typeTotalSymbol = []
        def typeList = []
        def examTypeList = ExamType.findAllByExamTypeTotalSymbolIsNotNull()
        def examSubTypeList = ExamSubType.findAllByExamSubTypeTotalSymbolIsNotNull()
        examTypeList.each {
            typeTotalSymbol << it.examTypeTotalSymbol
            calculationFor << it.examTypeName
            calculationForID << it.id
            typeList << it.examTypeName.replace(" ", '').toLowerCase()
        }
        examSubTypeList.each {
            typeTotalSymbol << it.examSubTypeTotalSymbol
            calculationFor << it.examSubTypeName
            calculationForID << it.id
            typeList << it.examSubTypeName.replace(" ", '').toLowerCase()
        }
        calculationFor << "Total Secured From This Rule"
        calculationForID << "0"
        typeList << "totalMarksSecured".toLowerCase()
        [typeTotalSymbol: typeTotalSymbol, calculationForID: calculationForID, calculationFor: calculationFor, typeList: typeList]
    }

    def checkForUniqueSymbol() {
        def resultMap = [:]
        def result = false
        def symbolInst = RuleSymbols.findBySymbol(params.symbol)
        if (symbolInst) {
            result = true
        }
        resultMap.result = result
        render resultMap as JSON
    }

    def saveNewRule() {
        if (springSecurityService.currentUser) {
            def result = adminInfoService.createNewRuleFile(params)
            if (result) {
                def status = adminInfoService.saveCreateNewRule(params)
                if (status) {
                    flash.message = "Rule Saved Successfully."
                } else {
                    flash.message = "Unable to Save Rule Successfully"
                }
            } else {
                flash.message = "Unable to Save Rule Successfully"
            }
            redirect(controller: "admin", action: "createNewRule")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def editRuleRequirement = {
        /*action for editing rule name and requirment*/
        if (springSecurityService.currentUser) {
            def ruleNameList = RuleNames.list()
            if (params.id) {
                def ruleNameInst = RuleNames.findById(Long.parseLong(params.id))
                [ruleNameList: ruleNameList, ruleNameInst: ruleNameInst]
            } else {
                [ruleNameList: ruleNameList]
            }

        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def saveEditRuleRequirement = {
        /*save edited details of main rule(name and requirement status)*/
        if (springSecurityService.currentUser) {
            def ruleNameInst = RuleNames.findById(Long.parseLong(params.ruleId))
            if (ruleNameInst) {
                if (params.ruleRequired == 'yes') {
                    ruleNameInst.ruleRequired = true
                } else {
                    ruleNameInst.ruleRequired = false
                }
                if (ruleNameInst.save(flush: true, failOnError: true)) {
                    flash.message = 'Saved Succesfully.'
                }
            }
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def ruleListView() {
        /*for showing list of rules and provide options for viewing details of rule, editing and deleting rules*/
        if (springSecurityService.currentUser) {
            def ruleList = Rule.list()
            def ruleNameList=[], idList=[], statusList=[],ruleID=[]
            ruleList.each {
                def canDelete=false
                if(it.examType!=null){
                    def examTypeInst=ExamType.findById(Long.parseLong(it.examType))
                    if(examTypeInst!=null){
                        ruleNameList<<examTypeInst.examTypeName
                        statusList<<"NA"
                        idList<<examTypeInst.id
                    }
                    else{
                        canDelete=true
                    }
                }
                else{
                    def ruleInst=RuleNames.findById(Long.parseLong(it.ruleNames))
                    if(ruleInst!=null){
                    ruleNameList<<ruleInst.rule
                    statusList<<ruleInst.ruleRequired
                    idList<<ruleInst.id
                    }
                    else{
                        canDelete=true
                    }
                }
                if(canDelete){
                    it.delete(flush: true)
                }
                else{
                    ruleID<<it.id
                }
            }
            [ruleNameList: ruleNameList,idList:idList,statusList:statusList,ruleID:ruleID]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def deleteRuleDetails() {
        /*for deleting rule and their details*/
        if (springSecurityService.currentUser) {
            def result = adminInfoService.deleteRuleFromFile(params)
            if (result) {
                flash.message="Rule Deleted Successfully."
            } else {
                flash.message="Unable Delete the Rule."
            }
            redirect(controller: "admin", action: "ruleListView")
        } else {

            redirect(controller: "login", action: "auth")
        }
    }

    def showEvaluatedStudentDetails() {
        /*Action for showing grades of student in particular examination*/
        def resultMap = [:], studentNameList = [], studentRollNoList = [], studentMarksList = [], studentGradeList = []
        def subInst = Subject.findBySubjectCode(params.course)
        def statusInst = Status.findById(1)
        def studentEvalMarksList = StudentEvaluatedMarks.findAllByAcademicSessionAndSubjectIdAndStatus(params.academicSession, subInst, statusInst)
        studentEvalMarksList.each {
            def studentInst = it.student
            if (studentInst.middleName == null) {
                studentNameList << studentInst.firstName + " " + studentInst.lastName
            } else {
                studentNameList << studentInst.firstName + " " + studentInst.middleName + " " + studentInst.lastName
            }

            studentRollNoList << studentInst.rollNo
            studentMarksList << it.marksSecuredAfterRule
            studentGradeList << it.grade
        }
        resultMap.studentNameList = studentNameList
        resultMap.studentGradeList = studentGradeList
        resultMap.studentRollNoList = studentRollNoList
        resultMap.studentMarksList = studentMarksList
        render resultMap as JSON
    }

    def showEvaluatedMarksGradeDetails() {
        def resultMap = [:], studentNameList = [], studentRollNoList = [], studentMarksList = [], studentGradeList = []
        def subInst = Subject.findBySubjectCode(params.course)
        def studentEvalMarksList = StudentEvaluatedMarks.findAllByAcademicSessionAndSubjectId(params.academicSession, subInst)
        studentEvalMarksList.each {
            def studentInst = it.student
            if (studentInst.middleName == null) {
                studentNameList << studentInst.firstName + " " + studentInst.lastName
            } else {
                studentNameList << studentInst.firstName + " " + studentInst.middleName + " " + studentInst.lastName
            }

            studentRollNoList << studentInst.rollNo
            studentMarksList << it.marksSecuredAfterRule
            if (it.grade != null) {
                studentGradeList << it.grade
            } else {
                studentGradeList << 'N/A'
            }

        }
        resultMap.studentNameList = studentNameList
        resultMap.studentGradeList = studentGradeList
        resultMap.studentRollNoList = studentRollNoList
        resultMap.studentMarksList = studentMarksList
        render resultMap as JSON
    }

    def searchCourseByCode() {
        def resultMap = [:]
        def code, name, points, session
        def sub = Subject.findBySubjectCode(params.course)
        def subSess = SubjectSession.findBySubjectId(sub)
        resultMap.code = sub.subjectCode
        resultMap.name = sub.subjectName
        resultMap.points = sub.creditPoints
        resultMap.session = subSess.sessionOfSubject
        resultMap.sessionId = subSess.id
        render resultMap as JSON
    }

    def listOfUnApprovedCertificates() {
        /*Provide interface for showing list of requests for certificate */
        if (springSecurityService.currentUser) {
            def df = new SimpleDateFormat('dd-MMM-yyyy hh:mm a')
            def certificateList = CertificateRequests.findAllByStatus(0)
            def nameList = [], type = [], rollOrEid = [], remarks = [], ipAddress = [], timeList = [], certificateTypeIdList = [], certificateTypeList = [], requestId = [], semester = [], deptList = []
            certificateList.each {
                if (it.employee != null) {
                    type << 'employee'
                    semester << 'NA'
                    rollOrEid << it.employee.employeeCode
                    def userId = User.findByEmployeeCode(it.employee.employeeCode)
                    deptList << userId.department.name
                    if (it.employee.middleName == null) {
                        nameList << it.employee.firstName + " " + it.employee.lastName
                    } else {
                        nameList << it.employee.firstName + " " + it.employee.middleName + " " + it.employee.lastName
                    }
                } else if (it.student != null) {
                    type << 'student'
                    def userId = User.findByUsername(it.student.rollNo)
                    deptList << userId.department.name
                    semester << it.student.semester
                    rollOrEid << it.student.rollNo
                    if (it.student.middleName == null) {
                        nameList << it.student.firstName + " " + it.student.lastName
                    } else {
                        nameList << it.student.firstName + " " + it.student.middleName + " " + it.student.lastName
                    }
                }
                if (it.ipAddress != null) {
                    ipAddress << it.ipAddress
                } else {
                    ipAddress << 'NA'
                }
                timeList << df.format(it.date)
                certificateTypeList << it.certificate.nameOfCertificate
                certificateTypeIdList << it.certificate.id
                requestId << it.id
                remarks << it.certificateRemarks
            }
            [certificateTypeIdList: certificateTypeIdList, remarks: remarks, deptList: deptList, nameList: nameList, type: type, semester: semester, rollOrEid: rollOrEid, ipAddress: ipAddress, timeList: timeList, certificateTypeList: certificateTypeList, requestId: requestId]
        } else {

            redirect(controller: "login", action: "auth")
        }

    }

    def listOfApprovedCertificates() {
        /*Action for showing list of certificates which are approved by admin or certificate admin*/
        if (springSecurityService.currentUser) {
            def df = new SimpleDateFormat('dd-MMM-yyyy hh:mm a')
            def certificateList = CertificateRequests.findAllByStatus(1)
            def nameList = [], type = [], rollOrEid = [], remarks = [], ipAddress = [], timeList = [], certificateTypeIdList = [], certificateTypeList = [], requestId = [], semester = [], deptList = []
            certificateList.each {
                if (it.employee != null) {
                    type << 'employee'
                    semester << 'NA'
                    rollOrEid << it.employee.employeeCode
                    def userId = User.findByEmployeeCode(it.employee.employeeCode)
                    deptList << userId.department.name
                    if (it.employee.middleName == null) {
                        nameList << it.employee.firstName + " " + it.employee.lastName
                    } else {
                        nameList << it.employee.firstName + " " + it.employee.middleName + " " + it.employee.lastName
                    }
                } else if (it.student != null) {
                    type << 'student'
                    def userId = User.findByUsername(it.student.rollNo)
                    deptList << userId.department.name
                    semester << it.student.semester
                    rollOrEid << it.student.rollNo
                    if (it.student.middleName == null) {
                        nameList << it.student.firstName + " " + it.student.lastName
                    } else {
                        nameList << it.student.firstName + " " + it.student.middleName + " " + it.student.lastName
                    }
                }
                if (it.ipAddress != null) {
                    ipAddress << it.ipAddress
                } else {
                    ipAddress << 'NA'
                }
                timeList << df.format(it.date)
                certificateTypeList << it.certificate.nameOfCertificate
                certificateTypeIdList << it.certificate.id
                requestId << it.id
                remarks << it.certificateRemarks
            }
            [certificateTypeIdList: certificateTypeIdList, remarks: remarks, deptList: deptList, nameList: nameList, type: type, semester: semester, rollOrEid: rollOrEid, ipAddress: ipAddress, timeList: timeList, certificateTypeList: certificateTypeList, requestId: requestId]
        } else {

            redirect(controller: "login", action: "auth")
        }

    }

    def createNewExamType() {
        /*for creating new ExamType and ExamSubType*/

    }

    def saveNewExamType() {
        /*for Saving new exam type*/
        if (springSecurityService.currentUser) {
            def result = adminInfoService.saveNewExamTypeAndSubTyoe(params)
            if (result) {
                flash.message = "Saved Successfully."
            } else {
                flash.message = "Unable to Save Successfully."
            }
            redirect(controller: "admin", action: "createNewExamType")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveNewTotalRule() {
        /*Action for saving rule  and rule details*/
        if (springSecurityService.currentUser) {
            def result = adminInfoService.saveNewTotalRule(params)
            if (result) {
                flash.message = "Saved Successfully."
            } else {
                flash.message = "Unable to Save Successfully."
            }
            redirect(controller: "postExamination", action: "totalCalculationRule")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveMainRule() {
        /*for saving details of main rule for marks evaluation(name, variable , expression etc)*/
        if (springSecurityService.currentUser) {
            def result = adminInfoService.saveNewMainRule(params)
            if (result) {
                flash.message = "Saved Successfully."
            } else {
                flash.message = "Unable to Save Successfully."
            }
            redirect(controller: "postExamination", action: "createMainRules")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def addProgramBranch() {
        /*For adding new Program Branch*/
        if (springSecurityService.currentUser) {
            def deptList = Department.list()
            [deptList: deptList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveProgramBranch() {
        /*For saving new Program Branch*/
        if (springSecurityService.currentUser) {
            def branchInst = ProgramBranch.findByCode(params.branchCode)
            if (!branchInst) {
                branchInst = new ProgramBranch()
                branchInst.name = params.programBranchName
                branchInst.department = Department.findById(Long.parseLong(params.department))
                branchInst.code = params.branchCode
                if (branchInst.save(flush: true, failOnError: true)) {
                    flash.message = "Saved Successfully."
                } else {
                    flash.message = "Unable to save Successfully."
                }
            } else {
                flash.message = "Branch Code Already Exist."
            }
            redirect(controller: "admin", action: "addProgramBranch")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def listOfProgramBranch() {
        /*For showing list of program Branch*/
        if (springSecurityService.currentUser) {
            def programBranchList = ProgramBranch.list(sort: 'code')
            [programBranchList: programBranchList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def deleteProgramBranch() {
        /*For deleting program branch*/
        if (springSecurityService.currentUser) {
            def progBranchInst = ProgramBranch.findById(Long.parseLong(params.id))
            progBranchInst.delete(flush: true)
            if (!ProgramBranch.exists(progBranchInst.id)) {
                flash.message = "Deleted Successfully"
            } else {
                flash.message = "Unable Delete Successfully."
            }
            redirect(controller: "admin", action: "listOfProgramBranch")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def addDepartment() {
        /*For adding new Department*/
        if (springSecurityService.currentUser) {
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveDepartment() {
        /*For saving Department*/
        if (springSecurityService.currentUser) {
            def branchInst = new Department()
            branchInst.name = params.name
            if (branchInst.save(flush: true, failOnError: true)) {
                flash.message = "Saved Successfully."
            } else {
                flash.message = "Unable to save Successfully."
            }
            redirect(controller: "admin", action: "addDepartment")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def listOfDepartment() {
        /*Action for showing list*/
        if (springSecurityService.currentUser) {
            def programBranchList = Department.list()
            [programBranchList: programBranchList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def deleteDepartment() {
        /*For deleting Department*/
        if (springSecurityService.currentUser) {
            def progBranchInst = Department.findById(Long.parseLong(params.id))
            progBranchInst.delete(flush: true)
            if (!Department.exists(progBranchInst.id)) {
                flash.message = "Deleted Successfully"
            } else {
                flash.message = "Unable Delete Successfully."
            }
            redirect(controller: "admin", action: "listOfDepartment")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def deleteExamType() {
        /*Action for deleting exam type*/
        if (springSecurityService.currentUser) {
            def progBranchInst = ExamType.findById(Long.parseLong(params.id))
            def subTypeList=ExamSubType.findAllByExamTypeId(progBranchInst.id.toString())
            if(subTypeList){
                subTypeList.each {
                    it.delete(flush: true)
                }
            }
            def symbolList = RuleSymbols.findAllByExamType(progBranchInst.id.toString())
            symbolList.each {
                it.delete(flush: true)
            }
            progBranchInst.delete(flush: true)
            if (!ExamType.exists(progBranchInst.id)) {
                flash.message = "Deleted Successfully"
            } else {
                flash.message = "Unable Delete Successfully."
            }
            redirect(controller: "admin", action: "viewExamType")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def deleteExamSubType() {
        /*Action for deleting exam sub type*/
        if (springSecurityService.currentUser) {
            def progBranchInst = ExamSubType.findById(Long.parseLong(params.id))
            progBranchInst.delete(flush: true)
            if (!ExamSubType.exists(progBranchInst.id)) {
                flash.message = "Deleted Successfully"
            } else {
                flash.message = "Unable Delete Successfully."
            }
            redirect(controller: "admin", action: "viewExamSubType")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def enrollProjectForm() {
        /*Interface for project enrollment */
        if (springSecurityService.currentUser) {
            def userInst = springSecurityService.currentUser
            def roleList = UserRole.findAllByUser(userInst)
            def status = false
            roleList.each {
                if (it.role.authority == 'ROLE_STUDENT') {
                    status = true
                }
            }
            if (status) {
                def projectSubjectList = SubjectSession.findAllByIsProjectSubject(true).subjectId
                def student = Student.findByRollNo(userInst.username)
                def semester = Semester.findByProgramSessionAndSemesterNo(ProgramSession.findByProgramDetailId(student.program), student.semester)

                def studentSubList = StudentSubject.findAllByStudentAndSemester(student, semester)
                if(studentSubList.size()>0){
                    def studentSubjectInst=StudentSubject.findAllByStudentAndSemester(student, semester).subjectSession.subjectId
                    def academicSession = StudentSubject.findAllByStudentAndSemester(student, semester)[0].academicSession
                    def domainList=ProjectDomain.findAllByProgramBranch(student.programBranch)
                    def subList = []
                    studentSubjectInst.each {
                        def subId = it.subjectCode
                        projectSubjectList.each {
                            if (it.subjectCode == subId) {
                                subList << it
                            }
                        }
                    }
                    def guideList=GuideDetails.findAllByProgramBranch(student.programBranch)
                    def nameList=[],idList=[]
                    guideList.each {
                        if (it.employee.middleName == null) {
                            nameList << it.employee.firstName + " " + it.employee.lastName+" ["+it.academicSession+"]"
                        } else {
                            nameList << it.employee.firstName + " " + it.employee.middleName + " " + it.employee.lastName+" ["+it.academicSession+"]"
                        }
                        idList<<it.id
                    }
                    if (subList.size() > 0) {
                        [domainList:domainList,idList:idList,academicSession: academicSession, student: student, subList: subList, status: true, authourity: true,nameList:nameList]
                    } else {
                        [status: false, authourity: true]
                    }
                }
                else {
                    [status: false, authourity: true]
                }

            } else {
                [authourity: false]
            }

        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def approveEnrolledProject() {
        /*for List of projects which is not approved(unapproved project)*/
        if (springSecurityService.currentUser) {
            def unApproveStatus = Status.findById(1)
            def studentList = [], projectDetailsList = []
            def studentListTotal = 0, status = true
            def currentUser = springSecurityService.getCurrentUser()
            def roleList = UserRole.findAllByUser(currentUser).role
            def isAdmin = false, isFacultyAdvisor = false
            roleList.each {
                if (it == Role.findByAuthority('ROLE_ADMIN')) {
                    isAdmin = true
                }
                if (it == Role.findByAuthority("ROLE_FACULTYADVISOR")) {
                    isFacultyAdvisor = true
                }
            }
            if (isAdmin) {
                projectDetailsList = ProjectDetails.findAllByStatus(unApproveStatus)
            } else {
                def currentUserEmployeeCode = springSecurityService.getCurrentUser().employeeCode
                def currentUserDepartment = Employee.findByEmployeeCode(currentUserEmployeeCode).department
                if (isFacultyAdvisor) {
                    def programList = FacultyAdvisor.findAllByUser(springSecurityService.getCurrentUser()).programDetail.unique()
                    def facultyAdvisorInst = FacultyAdvisor.findAllByUser(springSecurityService.getCurrentUser())
                    if (programList) {
                        projectDetailsList = ProjectDetails.findAllByStatusAndDepartment(unApproveStatus,currentUserDepartment)
                    } else {
                        status = false
                    }
                } else {
                }
            }
            if (status) {
                def nameList=[]
                projectDetailsList.each {
                    def guideInst=GuideDetails.findById(Long.parseLong(it.guideId))
                    if(guideInst.employee.middleName==null)
                        nameList<<guideInst.employee.firstName+" "+guideInst.employee.lastName
                    else{
                        nameList<<guideInst.employee.firstName+" "+guideInst.employee.middleName+" "+guideInst.employee.lastName
                    }
                }
                [projectDetailsList: projectDetailsList,nameList:nameList]
            } else {
                flash.message = "You are not Assigned to Any Program."
                redirect(controller: "home", action: "index")
            }

        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def addGuide() {
        /*Action for assigning project guide*/
        if (springSecurityService.currentUser) {
            def branchList = ProgramBranch.list(sort: 'name')
            [branchList: branchList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def approveProjectDetails() {
        if (springSecurityService.currentUser) {
            def projectDetailsInst=ProjectDetails.findById(Long.parseLong(params.id))
            projectDetailsInst.status=Status.findById(4)
            if(projectDetailsInst.save()){
                flash.message="Approved Succesfully"
            }
            redirect(controller: "admin", action: "approveEnrolledProject")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def loadAllEmployeeByDept() {
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def branch = ProgramBranch.findById(Long.parseLong(params.branchId))
            def employeeList = Employee.findAllByDepartment(branch.department)
            def nameList = [], employeeIdList = []
            employeeList.each {
                if (it.middleName == null)
                    nameList << it.firstName + " " + it.lastName
                else
                    nameList << it.firstName + " " + it.middleName + " " + it.lastName
                employeeIdList << it.id
            }
            returnMap.nameList = nameList
            returnMap.employeeIdList = employeeIdList
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def listOfGuide() {
        /*for showing list of guide*/
        if (springSecurityService.currentUser) {
            def guideList = GuideDetails.list(sort: 'programBranch')
            [guideList: guideList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def deleteGuide() {
        /*for deleting guide*/
        if (springSecurityService.currentUser) {
            def guideInst = GuideDetails.findById(Long.parseLong(params.id))
            guideInst.delete(flush: true)
            if (!GuideDetails.exists(guideInst.id)) {
                flash.message = "Deleted Successfully."
            }
            redirect(controller: "admin", action: "listOfGuide")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveGuide() {
        /*for saving project guide details*/
        if (springSecurityService.currentUser) {
            def guideInst=new GuideDetails(params)
            if (guideInst.save(flush: true,failOnError: true)) {
                flash.message = "Saved Successfully."
            }
            redirect(controller: "admin", action: "addGuide")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def loadGuideList(){
        if (springSecurityService.currentUser) {
            def branch = ProgramBranch.findById(Long.parseLong(params.branchId))
            def guideList=GuideDetails.findAllByProgramBranch(branch)
            def nameList = [], guideIdList = [],returnMap=[:]
            guideList.each {
                if (it.employee.middleName == null)
                    nameList << it.employee.firstName + " " + it.employee.lastName
                else
                    nameList << it.employee.firstName + " " + it.employee.middleName + " " + it.employee.lastName
                guideIdList << it.id
            }
            returnMap.nameList = nameList
            returnMap.guideIdList = guideIdList
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def loadGuideDetails(){
        if (springSecurityService.currentUser) {
            def returnMap=[:]
            def guideInst=GuideDetails.findById(params.guideId)
            def designation=EmployeeDesignation.findByEmployee(guideInst.employee).designation
            def email=guideInst.employee.firstEmail
            def phno=guideInst.employee.firstMobileNo
            returnMap.designation = designation
            returnMap.email = email
            returnMap.phno = phno
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def approvedProjects(){
        /*for showing list of approved projects*/
        if (springSecurityService.currentUser) {
            def projectDetailsList=ProjectDetails.findAllByStatus(Status.findById(4))
            def nameList=[],stuNameList=[]
            projectDetailsList.each {
                def guideInst=GuideDetails.findById(Long.parseLong(it.guideId))
                if (it.student.middleName == null)
                    stuNameList << it.student.firstName + " " + it.student.lastName
                else
                    stuNameList << it.student.firstName + " " + it.student.middleName + " " + it.student.lastName
                if (guideInst.employee.middleName == null)
                    nameList << guideInst.employee.firstName + " " + guideInst.employee.lastName
                else
                    nameList << guideInst.employee.firstName + " " + guideInst.employee.middleName + " " + guideInst.employee.lastName
            }
            [projectDetailsList:projectDetailsList,nameList:nameList,stuNameList:stuNameList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def addProjectDomain(){
        /*for adding project domain*/
        if (springSecurityService.currentUser) {
            def branchList=ProgramBranch.list()
            [branchList:branchList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def saveProjectDomain(){
        /*for saving project details domain*/
        if (springSecurityService.currentUser) {
            def branchInst=ProgramBranch.findById(Long.parseLong(params.programBranch))
            def projectDomainInst=new ProjectDomain()
            projectDomainInst.domainName=params.domainName
            projectDomainInst.programBranch=branchInst
            if(projectDomainInst.save(flush: true)){
                flash.message="Saved Successfully."
            }
            else{
                flash.message="Unable to Save Successfully"
            }
            redirect(controller: "admin", action: "addProjectDomain")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def deleteProjectDomain(){
        /*for deleting project domain*/
        if (springSecurityService.currentUser) {
            def guideInst = ProjectDomain.findById(Long.parseLong(params.id))
            guideInst.delete(flush: true)
            if (!ProjectDomain.exists(guideInst.id)) {
                flash.message = "Deleted Successfully."
            }
            redirect(controller: "admin", action: "listOfProjectDomains")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def listOfProjectDomains(){
        /*for showing list of project domain*/
        if (springSecurityService.currentUser) {
            def domainList=ProjectDomain.list()
            [domainList:domainList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
}



