package examinationproject

import certificate.Certificate
import certificate.CertificateRequests
import certificate.CertificateRole
import IST.Employee
import IST.ProgramBranch
import certificate.AnnexerHStudents
import com.university.Role
import com.university.User
import com.university.UserRole
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import com.university.TempLogin
import postexamination.StudentEvaluatedMarks
import postexamination.StudentMarks

import java.text.DateFormat
import java.text.SimpleDateFormat

class StudentController {
    private final myLock = new Object()
    def studentRegistrationService
    def pdfRenderingService
    def springSecurityService

    def registration = {
        /*Action for student registration page(entering details of student)*/
        if (springSecurityService.currentUser) {
            def LogincurrentUser = springSecurityService.getCurrentUser()
            def currentUser
            def programCode
            def branchCode
            def programList
            def progDetCode
            def currentProgram
            def currentBranch
            def programBranchList
            def currentProgDetail
            if (params.studentID) {
                currentUser = Student.findById(Integer.parseInt(params.studentID))
                programCode = currentUser.rollNo.substring(2, 4)
                branchCode = currentUser.rollNo.substring(4, 6)
                progDetCode = currentUser.rollNo.substring(2, 6)
            } else {
                currentUser = springSecurityService.getCurrentUser().getUsername()
                programCode = (currentUser.substring(2, 4))
                branchCode = (currentUser.substring(4, 6))
                progDetCode = (currentUser.substring(2, 6))

            }
            currentProgram = ProgramType.findByCode(programCode)
            currentBranch = ProgramBranch.findByCode(branchCode)
            currentProgDetail = ProgramDetail.findAllByCourseCode(progDetCode)
            programList = ProgramDetail.list(sort: 'courseName')
            programBranchList = ProgramBranch.list()
            if (params.studentID) {
                def studInstance = Student.findById(Integer.parseInt(params.studentID))

                [programBranchList: programBranchList, studInstance: studInstance, programList: programList, view: params.view]
            } else if (Student.findByRollNo(currentUser)) {
                def studUser = Student.findByRollNo(currentUser)
                if (studUser.status == Status.findById(4)) {
                    flash.message = "You are already Registered"
                    [status: 'Approved']

                } else if (studUser.status == Status.findById(1)) {
//                    redirect(controller: "home", action: "index")
                    flash.message = "Your Approval is pending"
                    [status: 'Unapproved']

                } else if (studUser.status == Status.findById(5)) {
                    [programList:programList,currentUser: currentUser, studInstance: studUser, currentProgram: currentProgram, currentBranch: currentBranch, currentProgDetail: currentProgDetail, programBranchList: programBranchList]
                }
            } else {
                [programList:programList,currentUser: currentUser, currentProgram: currentProgram, currentBranch: currentBranch, registered: params.registered, studentID: params.studentID, currentProgDetail: currentProgDetail, programBranchList: programBranchList]
            }
        } else {
            redirect(controller: "login", action: "auth")
        }


    }
    def viewResult = {

    }
    def submitRegistration = {
        /*Action for saving student registration details*/

        if (springSecurityService.currentUser) {
            def signature = request.getFile('signature')
            def photographe = request.getFile("photograph")
            def studentRegistration = studentRegistrationService.saveNewStudentRegistration(params, signature, photographe)

            if (studentRegistration) {
                if (params.studentId) {
                    if (springSecurityService.isLoggedIn()) {
                        flash.message = "${message(code: 'register.updated.message')}"

                        redirect(controller: 'home', action: "index")

                    } else {
                        flash.message = "${message(code: 'register.updated.message')}"
                        redirect(action: "registration", params: [fee: fee, studentID: studentRegistration.id, registered: "registered"])
                    }
                } else {
                    if (springSecurityService.isLoggedIn()) {
                        flash.message = "${message(code: 'register.created.message')} & Your Enrollment is pending For Approval"
                        redirect(action: "registration", params: [studentID: studentRegistration.id, registered: "reg"])
                    }
                }
            } else {
                flash.message = "${message(code: 'register.notCreated.message')}"
                redirect(controller: 'home', action: "index")
            }
        } else {
            redirect(controller: "login", action: "auth")
        }

    }


    def applicationPrintPreview = {
        def student = Student.findById(params.studentID)
        def lateFee = 0
        def payableFee = 0
        try {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(cal.YEAR);
            def sessionVal = year + 1
            sessionVal = year + '-' + sessionVal
//            def programIns = ProgramDetail.findById(Integer.parseInt(params.program))
            def lateFeeDate = student.programDetail.lateFeeDate[0]
            def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(student.programDetail[0].id), sessionVal)
            def today = new Date()
            if (lateFeeDate != null) {
                if (today.compareTo(lateFeeDate) > 0) {
//                    lateFee = AdmissionFee.findByProgramDetail(student.programDetail).lateFeeAmount
                    lateFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, 1).lateFeeAmount
                }
            }
//            def feeAmount = AdmissionFee.findByProgramDetail(student.programDetail);
            def feeAmount = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, 1);
            payableFee = feeAmount.feeAmountAtIDOL + lateFee
        }
        catch (NullPointerException e) {
            payableFee = 0
        }
        def feeDetails = FeeDetails.findByChallanNo(student.challanNo)
        render template: 'applicationPrintPreview', model: [studentInstance: student, feeDetails: feeDetails, payableFee: payableFee]
    }

    def showStatus() {
        try {
            def response = [:]
//        println("showing data.."+params.data)
            def refNumber = Student.findByReferenceNumber(params.data)
//        println(refNumber)
            def statusName
            if (refNumber != null) {
                def status = refNumber.statusId
                def rollNo = refNumber.rollNo
                def statusIn = Status.findById(status)
//            println(statusIn)
                statusName = statusIn.status
                response.response1 = statusName
                response.response2 = rollNo
                render response as JSON
            } else {
                render response as JSON
            }
        } catch (Exception e) {
//            println("***problem in showing Status of Application***")
        }
    }

    def applicationPreview() {

    }

    @Secured(["ROLE_FACULTYADVISOR", "ROLE_ADMIN", "ROLE_HOD"])
    def studentListView = {
        if (springSecurityService.currentUser) {
            def programList = ProgramDetail.list()
            def currentUser = springSecurityService.getCurrentUser()
            def roleList = UserRole.findAllByUser(currentUser).role
            def isFacultyAdvisor = false
            roleList.each {
                if (it == Role.findByAuthority('ROLE_HOD')) {
                    isFacultyAdvisor = true
                }
                if (it == Role.findByAuthority("ROLE_FACULTYADVISOR")) {
                    isFacultyAdvisor = true
                }
            }
            [programList: programList,isFacultyAdvisor:isFacultyAdvisor,]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def show = {
        def id = Integer.parseInt(params.id)
        def something = Student.get(id)
        byte[] image = something.studentImage
        response.setContentType(params.mime)
        response.outputStream << image
    }

    def downloadAdmitCard = {

    }






    def updateStudent = {
        if (springSecurityService.currentUser) {
            try {
                def student = Student.findByRollNo(params.rollNo)
                if (student) {
                    redirect(action: "registration", params: [studentID: student.id, update: 'update'])
                } else {
                    flash.message = "No Record Found"
                    redirect(action: "studentListView")
                }
            } catch (NullPointerException ex) {
                println("Problem in searching student by roll number" + ex.printStackTrace())
            }
        } else {
            redirect(controller: "login", action: "auth")
        }

    }
    @Secured(["ROLE_FACULTYADVISOR", "ROLE_ADMIN", "ROLE_HOD"])
    def viewStudent = {
        /*Action for showing student details*/
        if (springSecurityService.currentUser) {
            def student = Student.findById(Long.parseLong(params.studentId))
            redirect(controller: 'student', action: "viewStudentDetails", params: [studentId: student.id.toString()])

        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def showStudentDetails = {
        if (springSecurityService.currentUser) {
            def student = Student.findByRollNo(params.studentRoll)
            redirect(controller: 'student', action: "viewStudentDetails", params: [studentId: student.id.toString()])
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def showStudentSemesterDetails = {
        if (springSecurityService.currentUser) {
            def student = Student.findByRollNo(params.studentRoll)
            redirect(controller: 'student', action: "semesterDetails", params: [studentID: student.id.toString(),view:'view'])
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    @Secured(["ROLE_HELP_DESK", "ROLE_ADMIN", "ROLE_ACCOUNT"])
    def customChallanSave = {
        def resultMap = studentRegistrationService.saveCChallan(params)
        if (resultMap.status) {
            def infoMap = [:]
            infoMap.challanNo = resultMap.challanNo
            infoMap.name = params.challanName
            infoMap.feeAmount = params.amount
            infoMap.feeType = params.typeOfFee
            render infoMap as JSON
        } else {
            flash.message = "${message(code: 'register.notCreated.message')}"
            redirect(controller: 'admin', action: "generateCustomChallan")
        }
    }
    @Secured(["ROLE_HELP_DESK", "ROLE_ADMIN", "ROLE_ACCOUNT"])
    def generateIdentityCard = {
        def programList = ProgramDetail.list(sort: 'courseCode')
        def sessionList = Student.createCriteria().list {
            projections {
                distinct("registrationYear")
            }
        }
        [programList: programList, sessionList: sessionList]
    }
    def getStudentsForIdentityCard = {
        def prgramInst = ProgramDetail.findById(Long.parseLong(params.programList))
        def status = Status.findById(4)
        def stuObj = Student.createCriteria()
        def studentList = stuObj.list {
            programDetail {
                eq('id', prgramInst.id)
            }
            and {
                eq('status', status)
                eq('registrationYear', Integer.parseInt(params.admissionYear))
                eq('identityCardGenerated', false)
            }
        }
        if (studentList) {
            render studentList as JSON
        } else {
            def resultMap = [:]
            resultMap.status = false
            render resultMap as JSON
        }
    }
    def printIdentityCard = {
        def year = Integer.parseInt(params.admissionYear)
        def fileName = year + "-" + (year + 1)
        def studentName = [], studentProgram = [], studentRoll = [], studentDOB = [], studentAddress = [], studentPin = [], studentMobNo = [], stuList = []
        def studentList = params.studentList.split(",")
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy")
        studentList.each {
            def studentInst = Student.findById(Integer.parseInt(it.toString()))
            stuList << studentInst
            studentName << studentInst.firstName + " " + (studentInst.middleName ? studentInst.middleName : "") + " " + studentInst.lastName
            studentProgram << studentInst.programDetail[0].courseName
            studentRoll << studentInst.rollNo
            studentDOB << df.format(studentInst.dob)
            studentAddress << studentInst.studentAddress + ", " + studentInst.addressTown + ", " + studentInst.addressDistrict + ", " + studentInst.addressState
            studentPin << studentInst.addressPinCode
            studentMobNo << studentInst.mobileNo
        }
        def args = [template: "printIdentityCard", model: [studentInstance: stuList, studentMobNo: studentMobNo, studentPin: studentPin, studentAddress: studentAddress, studentDOB: studentDOB, studentRoll: studentRoll, studentProgram: studentProgram, studentName: studentName], filename: fileName + ".pdf"]
        pdfRenderingService.render(args + [controller: this], response)
    }
    @Secured(["ROLE_FACULTYADVISOR", "ROLE_ADMIN", "ROLE_HOD","ROLE_STUDENT"])
    def viewStudentDetails = {
        /*for showing student details*/
        if (springSecurityService.currentUser) {
            def student, showType=false
            if(params.studentId){
                student = Student.findById(params.studentId)
            }
            else {
                def user =springSecurityService.currentUser
                def role=UserRole.findAllByUser(user).role
                role.each{
                    if(it.authority=="ROLE_STUDENT"){
                        student = Student.findByRollNo(user.username)
                        showType=true
                    }
                }
            }
            if(student){
                def feeDetails = SemesterFeeDetails.findAllByStudent(student ,[sort: "semester", order: "desc"])
                def studentMarksList = StudentEvaluatedMarks.findAllByStudentAndGradeIsNotNull(student,[sort: "academicSession", order: "desc"])
                [studInstance: student,feeDetails:feeDetails,studentMarksList:studentMarksList,showType:showType]
            }
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    @Secured(["ROLE_ADMIN"])
    def deleteStudents = {
        /*For deleting the student*/
        if (springSecurityService.currentUser) {
            def result = studentRegistrationService.deleteStudentWithDetails(params)
            if (result) {
                flash.message = "Successfully Deleted the Students"
            } else {
                flash.message = "Unable to Delete the Students. This may be a Approved Student."
            }
            redirect(controller: 'student', action: "studentListView")
        } else {
            redirect(controller: "login", action: "auth")
        }

    }


    def studentRegistration = {
        if (springSecurityService.currentUser) {
            def studentInst = Student.findById(Long.parseLong(params.studentId))
            def semesterList = CourseSubject.findAllByProgramSession(studentInst.programSession).semester.unique()

            [semesterList: semesterList, programSessionId: studentInst.programSession.id]
        } else {
            redirect(controller: "login", action: "auth")
        }


    }
    def getSemesterWiseSubjects = {
        if (springSecurityService.currentUser) {
            def progSessionInst = ProgramSession.findById(Long.parseLong(params.progragramSessionId))
            def semInst = Semester.findById(Long.parseLong(params.semesterID))
            def subjectList = CourseSubject.findAllByProgramSessionAndSemester(progSessionInst, semInst)
            def subList = []
            def backSubList = []
            def totalSubjects = [:]
            int i = 0
            def semesterList = CourseSubject.findAllByProgramSession(progSessionInst).semester.unique()

            for (val in semesterList) {
                if (val.semesterNo < semInst.semesterNo) {
                    def backSubs = StudentMarks.findAllByRoleIdAndSemesterNoAndIsPassed(Role.findById(9), val.id, false).subjectId.unique()
                    backSubs.each {
                        backSubList << it
                    }
                }

            }

            subjectList.each {
                subList << it.subjectSessionId.subjectId
            }
            def StuIns = Student.findById(Long.parseLong(params.studentID))
            def semesterIns = Semester.findById(Long.parseLong(params.semesterID))
            def studentSubjectList = StudentSubject.findAllBySemesterAndStudent(semesterIns, StuIns)
            def unapprovedList = []
            def approvedList = []
            def unapprovedListBack = []
            def approvedListBack = []

            studentSubjectList.each {
                for (int k = 0; k < subList.size(); k++) {
                    if (it.subjectSession.subjectId == subList[k] && (it.status == Status.findById(1) || it.status == Status.findById(5))) {
                        unapprovedList << subList[k]
                    }
                    if (it.subjectSession.subjectId == subList[k] && (it.status == Status.findById(4))) {
                        approvedList << subList[k]
                    }
                }
                for (int j = 0; j < backSubList.size(); j++) {
                    if (it.subjectSession == backSubList[j] && (it.status == Status.findById(1) || it.status == Status.findById(5))) {
                        unapprovedListBack << backSubList[j]
                    }
                    if (it.subjectSession == backSubList[j] && (it.status == Status.findById(4))) {
                        approvedListBack << backSubList[j]
                    }
                }
            }
            totalSubjects.subList = subList
            totalSubjects.backSubList = backSubList
            totalSubjects.unapprovedList = unapprovedList
            totalSubjects.approvedList = approvedList
            totalSubjects.unapprovedListBack = unapprovedListBack
            totalSubjects.approvedListBack = approvedListBack
            render totalSubjects as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }


    }

    @Secured(["ROLE_ADMIN"])
    def tempRollNo() {
        /*action for entering roll no's of new students*/
        if (springSecurityService.currentUser) {
        } else {
            redirect(controller: "login", action: "auth")
        }
    }


    def changePassword() {
        if (springSecurityService.currentUser) {
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    def savePassword() {
        if (springSecurityService.getCurrentUser()) {
            def currentUser = springSecurityService.getCurrentUser().getUsername()
            def stuInst = User.findByUsername(currentUser)
            stuInst.password = params.pwd
            stuInst.upDifferent = true
            stuInst.save(flush: true)
            flash.message = "Changed Password Successfully"
            redirect(controller: "home", action: "index")
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    def submitStudentRegistration = {
        if (springSecurityService.currentUser) {
            def result = studentRegistrationService.saveStudentSubjects(params)

            flash.message = "Registration Done !!"
            redirect(controller: "student", action: "studentRegistration", params: [studentId: params.studentID])

        } else {
            redirect(controller: "login", action: "auth")
        }


    }
    def addUser = {
        /*action for saving roll no's of new students & validate roll no(whether same roll no already exist or not)*/
        /*creating username and password for students*/
        if (springSecurityService.currentUser) {
            def atmp = TempLogin.findByRollNo(params.rollNo)
            if (atmp) {
                redirect(controller: "student", action: "tempRollNo")
                flash.message = "${message(code: 'student.RollNo.Already.Exist')}"

            } else {
                Calendar now = Calendar.getInstance();   // Gets the current date and time
                int year = now.get(Calendar.YEAR);
                def tmp = new TempLogin()
                tmp.rollNo = params.rollNo
                tmp.password = params.rollNo

                def studentRole = Role.findByAuthority('ROLE_STUDENT') ?: new Role(authority: 'ROLE_STUDENT').save(failOnError: true)
//            /*create new user*/

                def studentUser = User.findByUsername("${params.rollNo}") ?: new User(
                        username: params.rollNo,
                        password: params.rollNo,
                        email: "abc@gmail.com",
                        upDifferent: true,
                        enabled: true,
                ).save(flush: true, failOnError: true)
                if (!studentUser.authorities.contains(studentRole)) {
                    UserRole.create studentUser, studentRole
                }

                tmp.save(flush: true, failOnError: true)
                flash.message = "Student Entered"
                redirect(controller: "student", action: "tempRollNo")
            }
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def studentSemesterRegistration = {
        /*Interface for semester registration*/
        if (springSecurityService.currentUser) {
            def regDateCreated = true, validRegDate = true, prevApproved = true, withoutSubject=false
            def studInstance = Student.findByRollNo(springSecurityService.currentUser.username)
            if (studInstance != null) {
                def nextSemester = 0
                if (studInstance.programType.id == 4 && studInstance.semester == 0) {
                    nextSemester = studInstance.semester + 3

                } else {
                    nextSemester = studInstance.semester + 1

                }

                if (studInstance.semester != 0) {
                    def programSession = ProgramSession.findByProgramDetailId(studInstance.program)
                    def semesterId = Semester.findByProgramSessionAndSemesterNo(programSession, studInstance.semester)
                    def studentSubjectApproved = StudentSubject.findAllByStudentAndSemester(studInstance, semesterId).status.unique()
                    if(studentSubjectApproved){
                        if (studentSubjectApproved[0].id != 4) {
                            prevApproved = false
                        }
                    }else {
                        if(studInstance.semesterRegistration!=3){
                            withoutSubject=true
                        }

                    }

                }
println("========================="+studInstance.semester)
                if(!withoutSubject){
                    if (prevApproved) {
                        def programSession = ProgramSession.findByProgramDetailId(studInstance.program)
                        def semesterId = Semester.findByProgramSessionAndSemesterNo(programSession, nextSemester)
                        def regPeriod = EnrollmentPeriod.findByProgramDetailAndSemesterAndStudentAdmissionYear(studInstance.program, semesterId, studInstance.registrationYear)
                        try {
                            DateFormat df = new SimpleDateFormat("MM/dd/yyyy")
                            def today = df.parse(df.format(new Date()))
                            if (regPeriod) {
                                if (regPeriod.startDate != null && regPeriod.endDate != null) {
                                    def start = df.parse(df.format(regPeriod.startDate))
                                    def end = df.parse(df.format(regPeriod.endDate))
                                    if (start.compareTo(today) <= 0 && end.compareTo(today) >= 0) {
                                    } else {
                                        validRegDate = false
                                    }
                                }
                            } else {
                                regDateCreated = false
                            }
                        }
                        catch (NullPointerException e) {
                            [status: 'Admission Date']
                            flash.message = "Please Assign Admission Date"
                        }
                        if (regDateCreated && validRegDate) {
                            if (!studInstance) {
                                [status: 'Not Registered']
                            } else if (studInstance.status.id == 4) {
                                def totalList = []
                                def totalList1 = []
                                def returnMap = [:]
                                def resultMap = [:]
                                def optionalList = []
                                def optionalSubjectList = SubjectSession.findAllByIsOptional(true)

                                def courseSubList = []
                                optionalSubjectList.each {
                                    courseSubList << CourseSubject.findBySubjectSessionIdAndCourseDetailAndProgramSessionAndSemester(it, studInstance.program, programSession, semesterId)
                                }
                                def semester = Semester.findBySemesterNoAndProgramSession(nextSemester, programSession)
                                def courseAllList = CourseSubject.findAllByCourseDetailAndSemesterAndProgramSession(programSession.programDetailId, semester, programSession).subjectSessionId
                                def courseList = [], optSubSessionList = []
                                courseAllList.each {
                                    if (it.isOptional) {
                                        optSubSessionList << it
                                    } else {
                                        courseList << it
                                    }
                                }
                                if (courseList.size() > 0) {
                                    courseList.each {
                                        returnMap["id"] = it.id
                                        returnMap["subjectName"] = it.subjectId.subjectName
                                        returnMap["subjectCredit"] = it.subjectId.creditPoints
                                        returnMap["contactHours"] = it.subjectId.contactHours
                                        returnMap["lecture"] = it.subjectId.lecture
                                        returnMap["tutorial"] = it.subjectId.tutorial
                                        returnMap["practical"] = it.subjectId.practical
                                        returnMap["subjectCode"] = it.subjectId.subjectCode
                                        totalList.add(returnMap)
                                        returnMap = [:]
                                    }
                                }
                                def allSemesterForStudent = [],projectSemesterCheck=[]
                                def startSem = programSession.programDetailId.admissionTerm
                                if (startSem != nextSemester) {
                                    if (nextSemester % 2 == 0) {
                                        for (def i = startSem; i < nextSemester; i++) {
                                            if (i % 2 == 0) {
                                                allSemesterForStudent << Semester.findBySemesterNoAndProgramSession(i, programSession)
                                            }
                                            else{
                                                projectSemesterCheck<<Semester.findBySemesterNoAndProgramSession(i, programSession)
                                            }
                                        }
                                    } else {
                                        for (def i = startSem; i < nextSemester; i++) {
                                            if (i % 2 != 0) {
                                                allSemesterForStudent << Semester.findBySemesterNoAndProgramSession(i, programSession)
                                            }
                                            else{
                                                projectSemesterCheck<<Semester.findBySemesterNoAndProgramSession(i, programSession)
                                            }
                                        }
                                    }
                                }
                                def projectSubjectSessionList=SubjectSession.findAllByIsProjectSubject(true)
                                def programProjectSubjectList=CourseSubject.findAllByCourseDetailAndSemesterInListAndSubjectSessionIdInList(programSession.programDetailId, projectSemesterCheck,projectSubjectSessionList).subjectSessionId

                                def allCourseList = CourseSubject.findAllByCourseDetailAndSemesterInList(programSession.programDetailId, allSemesterForStudent).subjectSessionId
                                programProjectSubjectList.each{
                                    allCourseList<<it
                                }
                                allCourseList.each {

                                    returnMap["id"] = it.id
                                    returnMap["subjectName"] = it.subjectId.subjectName
                                    returnMap["subjectCredit"] = it.subjectId.creditPoints
                                    returnMap["contactHours"] = it.subjectId.contactHours
                                    returnMap["lecture"] = it.subjectId.lecture
                                    returnMap["tutorial"] = it.subjectId.tutorial
                                    returnMap["practical"] = it.subjectId.practical
                                    returnMap["subjectCode"] = it.subjectId.subjectCode
                                    totalList1.add(returnMap)
                                    returnMap = [:]
                                }
                                if (optSubSessionList.size() > 0) {
                                    optSubSessionList.each {
                                        returnMap["id"] = it.id
                                        returnMap["subjectName"] = it.subjectId.subjectName
                                        returnMap["subjectCredit"] = it.subjectId.creditPoints
                                        returnMap["contactHours"] = it.subjectId.contactHours
                                        returnMap["lecture"] = it.subjectId.lecture
                                        returnMap["tutorial"] = it.subjectId.tutorial
                                        returnMap["practical"] = it.subjectId.practical
                                        returnMap["subjectCode"] = it.subjectId.subjectCode
                                        optionalList.add(returnMap)
                                        returnMap = [:]
                                    }
                                }
                                def studentSubject = StudentSubject.findAllByStudentAndSemester(studInstance, semester).subjectSession.subjectId.id
                                [nextSemester:nextSemester,optionalList: optionalList, studInstance: studInstance, totalList: courseList, totalList1: allCourseList, studentSubject: studentSubject, session: regPeriod.admissionSession]
                            } else {
                                [status: 'Not Approved']
                            }
                        } else {
                            def msg
                            if (studInstance) {
                                def sem = studInstance.semester
                                def status = studInstance.semesterRegistration
                                [status: status, sem: sem]
                            } else {
                                [status: 'Reg Expired']
                            }

                        }
                    } else {
                        if(studInstance.semester != 0){
                            [status: 'Prev Not Approved']
                        }
                        else{
                            [status: 'Reg Expired']
                        }

                    }
                }
                else{

                    if(studInstance.semesterRegistration!=3 && studInstance.semesterRegistration!=0){
                        [status: 'Prev Not Approved']
                    }
                    else {
                        [status: 'Reg Expired']

                    }
                }


            } else {
                [status: 'Not Registered']
            }
        } else {
            redirect(controller: "login", action: "auth")
        }

    }


    def getCourseForStudent = {
        if (springSecurityService.currentUser) {
            def totalList = []
            def totalList1 = []
            def optionalList = []
            def returnMap = [:]
            def resultMap = [:]
            def optionalMap = [:]
            def programSession = ProgramSession.findById(Long.parseLong(params.programId))
            def optionalSubjectList = SubjectSession.findAllByIsOptional(true)
            def courseSubList = []
            optionalSubjectList.each {
                courseSubList << CourseSubject.findBySubjectSessionId(it).id
            }


            def semester = Semester.findBySemesterNoAndProgramSession(Integer.parseInt(params.studentSemester), programSession)
            def courseList = CourseSubject.findAllByCourseDetailAndSemester(programSession.programDetailId, semester).subjectSessionId
            courseList.each {
                returnMap["id"] = it.subjectId.id
                returnMap["subjectName"] = it.subjectId.subjectName
                returnMap["subjectCredit"] = it.subjectId.creditPoints
                totalList.add(returnMap)
                returnMap = [:]
            }
            def stuObj = CourseSubject.createCriteria()
            def optSubSessionList = []
            if (courseSubList) {
                optSubSessionList = stuObj.list {
                    eq('programSession', programSession)
                    eq('semester', semester)
                    and {
                        'in'('id', courseSubList)
                    }
                }
            }
            def allSemesterForStudent = []
            def startSem = programSession.programDetailId.admissionTerm
            if (startSem != Integer.parseInt(params.studentSemester)) {
                if (Integer.parseInt(params.studentSemester) % 2 == 0) {
                    for (def i = startSem; i < Integer.parseInt(params.studentSemester); i++) {
                        if (i % 2 == 0) {
                            allSemesterForStudent << Semester.findBySemesterNoAndProgramSession(i, programSession)
                        }
                    }
                } else {
                    for (def i = startSem; i < Integer.parseInt(params.studentSemester); i++) {
                        if (i % 2 != 0) {
                            allSemesterForStudent << Semester.findBySemesterNoAndProgramSession(i, programSession)
                        }
                    }
                }
            }
            def allCourseList = CourseSubject.findAllByCourseDetailAndSemesterInList(programSession.programDetailId, allSemesterForStudent).subjectSessionId
            allCourseList.each {
                returnMap["id"] = it.subjectId.id
                returnMap["subjectName"] = it.subjectId.subjectName
                returnMap["subjectCredit"] = it.subjectId.creditPoints
                totalList1.add(returnMap)
                returnMap = [:]
            }
            if (optSubSessionList.size() > 0) {
                optSubSessionList.each {
                    returnMap["id"] = it.subjectId.id
                    returnMap["subjectName"] = it.subjectId.subjectName
                    returnMap["subjectCredit"] = it.subjectId.creditPoints
                    optionalList.add(returnMap)
                    returnMap = [:]
                }
            }
            resultMap.totalList = totalList
            resultMap.totalList1 = totalList1
            resultMap.optionalList = optionalList
            render resultMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def submitSemesterRegistration = {
        /*Action for saving semester registration details*/
        if (springSecurityService.currentUser) {
            def studentIns = Student.findById(Integer.parseInt(params.studentId))
            def programSession = ProgramSession.findById(studentIns.program.id)
            def semester = Semester.findBySemesterNoAndProgramSession(Integer.parseInt(params.studentSemester), programSession)
            def provStatus = Status.findById(4)
            if (!SemesterFeeDetails.findByProgramDetailAndAcademicSessionAndSemesterAndStudentAndStatus(studentIns.program, params.session, semester, studentIns, provStatus)) {
                def result = studentRegistrationService.saveSemesterRegistration(params)

                if (result) {
                    flash.message = 'Semester Registration Done Successfully'
                } else {
                    flash.message = 'Unable to Complete Semester Registration'
                }
            } else {
                flash.message = 'Already Approved Semester Registration'
            }
            redirect(controller: "student", action: "semesterDetails")
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    def semesterDetails = {
        /*action for showing semester registration details*/
        if (springSecurityService.currentUser) {
            def subjectList = [], finalSubjectList = [], feeList = [], finalFeeList = []
            def studInstance, noButton=false
            if(params.studentID){
                studInstance = Student.findById(Long.parseLong(params.studentID))
                noButton=true
            }
            else{
                studInstance = Student.findByRollNo(springSecurityService.currentUser.username)
            }

            if (studInstance) {
                def studentSemester = StudentSubject.findAllByStudent(studInstance).semester.unique()
                if (studentSemester) {
                    def programSession = ProgramSession.findByProgramDetailId(studInstance.program)
                    def semester = Semester.findBySemesterNoAndProgramSession(studInstance.semester, programSession)
                    studentSemester.each {
                        subjectList = StudentSubject.findAllByStudentAndSemester(studInstance, it)
                        finalSubjectList.add(subjectList)
                        subjectList = []
                        feeList = SemesterFeeDetails.findAllByStudentAndSemester(studInstance, it)
                        finalFeeList.add(feeList)
                        feeList = []
                    }
                    def currentAcademicSession=StudentSubject.findAllBySemester(semester)[0].academicSession
                    [noButton:noButton,studInstance: studInstance, finalSubjectList: finalSubjectList, finalFeeList: finalFeeList,session:currentAcademicSession]
                } else {
                    if (studInstance.semesterRejectionReason) {
                        [status: 'rejected', reason: studInstance.semesterRejectionReason]
                    } else {
                        [status: 'No SemRegistration']
                    }
                }
            } else {
                [status: 'Not Enrolled']
            }
        } else {
            redirect(controller: "login", action: "auth")
        }

    }


    def requestCertificate = {
        /*Action for interface for requesting certificate*/
        /*Shows list of certificate in drop down menu according to user role*/
        if (springSecurityService.currentUser) {
            def  userRoleList=UserRole.findAllByUser(springSecurityService.currentUser)
            def certificateList = []
            userRoleList.each {
                def thisRoleList=CertificateRole.findAllByRole(it.role)
                thisRoleList.each {
                    certificateList<<it.certificate
                }
            }
            [certificateList: certificateList.unique()]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def submitCertificateRequest = {
        /*Action for saving or submitting details of certificate request*/
        if (springSecurityService.currentUser) {
            def df=new SimpleDateFormat('EEE, dd MMM yyyy HH:mm:ss z')
            def sf=new SimpleDateFormat('dd-MM-yyyy')
            String ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }
            def studInstance, employeeInst, type=0 //0-student, 1=Employee
            def  userRoleList=UserRole.findAllByUser(springSecurityService.currentUser)
            userRoleList.each {
                if(it.role.authority=='ROLE_STUDENT'){
                    studInstance = Student.findByRollNo(springSecurityService.currentUser.username)
                }
                if(it.role.authority=='ROLE_EMPLOYEE'){

                    employeeInst=Employee.findByEmployeeCode(springSecurityService.currentUser.employeeCode)
                    type=1
                }
            }
            def certificate = Certificate.findById(Long.parseLong(params.certificate))
            def certificateReq = null
                certificateReq = new CertificateRequests()
                if(type==0){
                    certificateReq.student = studInstance
                }
                else if(type==1){
                    certificateReq.employee=employeeInst
                }
                certificateReq.certificate = certificate
                certificateReq.date=df.parse(df.format(new Date()))
            if(params.dateOfRelease)
                certificateReq.releaseDate=sf.parse(params.dateOfRelease)
            if(params.certificateRemarks)
                certificateReq.certificateRemarks = params.certificateRemarks
                certificateReq.status = 0
                if (certificateReq.save(failOnError: true, flush: true)) {
                    if(params.rollNoList){
                        def rollList=params.rollNoList.split(",")
                        if(rollList.getClass()==String){
                            def stuInst=Student.findByRollNo(params.rollNoList)
                            def anxHStu= new AnnexerHStudents()
                            anxHStu.student=stuInst
                            anxHStu.certificateRequests=certificateReq
                            anxHStu.save(flush: true)
                        }
                        else{
                            rollList.each{
                                def stuInst=Student.findByRollNo(it)
                                def anxHStu= new AnnexerHStudents()
                                anxHStu.student=stuInst
                                anxHStu.certificateRequests=certificateReq
                                anxHStu.save(flush: true)
                            }
                        }
                    }

                    flash.message = 'Request Submitted Successfully'
                    redirect(action: 'requestCertificate')
                } else {
                    flash.message = 'Request Submission Failed'
                    redirect(action: 'requestCertificate')
                }
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    def loadProgramDetail() {
        if (springSecurityService.currentUser) {
            def programTypeInst = ProgramType.findById(Long.parseLong(params.programType))
            def branchInst = ProgramBranch.findById(Long.parseLong(params.branch))
            def programInst = ProgramDetail.findByProgramTypeAndProgramBranch(programTypeInst, branchInst)
            render programInst as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }

    }
    def submitProjectEnrollment(){
        /*This action saves or submit project details for particular student*/
        if (springSecurityService.currentUser) {
            def result = studentRegistrationService.saveProjectEnrollment(params)
            if (result) {
                flash.message = 'Saved Successfully'
            } else {
                flash.message = 'Unable to Save'
            }
            redirect(controller: "admin", action: "enrollProjectForm")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def generateRollNo(){

            def year = Student.findAll().registrationYear.unique()
            def programs = ProgramDetail.findAll()
             println("++++++++++++"+programs)
            [year: year,programs:programs]

    }
    def generateRoll(){
        def result = studentRegistrationService.createRollNo(params)
        if (result){
            redirect(controller: "student", action: "generateRollNo")
             flash.message = "Roll Number Generated ${result}"
        }
        else{
            flash.message = "Roll Number can not be generated"
        }


    }
}