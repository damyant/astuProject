package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import grails.transaction.Transactional
import groovy.transform.Synchronized
import java.text.DateFormat
import java.text.SimpleDateFormat
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

//@Transactional(readOnly = true)
class FeeDetailsController {
//    private final challanLock = new Object()
//    def feeDetailService
//    def studentRegistrationService
//    def pdfRenderingService
//    def springSecurityService
////    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
//
//    @Secured("ROLE_ADMIN")
//    def createFeeDetails() {
//        def paymentModeList = PaymentMode.list(sort: 'paymentModeName')
//        respond new FeeDetails(params)
//    }
//
//    @Transactional
//    def saveFeeDetails(FeeDetails feeDetailsInstance) {
//
//
//        if (feeDetailsInstance == null) {
//            return
//        }
//
//        feeDetailsInstance = feeDetailService.saveFeeDetails(params)
//
//        if (feeDetailsInstance.hasErrors()) {
//            respond feeDetailsInstance.errors, view: 'createFeeDetails'
//            return
//        }
//
//        if (feeDetailsInstance) {
//            flash.message = "Fee Details Entered Successfully."
//            redirect(action: "createFeeDetails")
//        }
//    }
//
//    def editFeeDetails(FeeDetails feeDetailsInstance) {
//        respond feeDetailsInstance
//    }
//
//    @Transactional
//    def updateFeeDetails(FeeDetails feeDetailsInstance) {
//
//
//        if (feeDetailsInstance.hasErrors()) {
//            respond feeDetailsInstance.errors, view: 'editFeeDetails'
//            return
//        }
//
//        if (feeDetailsInstance.save(flush: true)) {
//            redirect(action: "createFeeDetails")
//        }
//    }
////    @Secured("ROLE_ADMIN")
////    def bulkFeeEntry = {
////
////        def filterType = []
////        def programList = ProgramDetail.list(sort: 'courseCode')
////        def studyCentre = StudyCenter.list(sort: 'name');
////        filterType.add("By Program")
////        filterType.add("By Study Centre")
//////         filterType.add("By Admission Date")
////
////        [filterType: filterType, programList: programList, studyCentre: studyCentre]
////    }
//
//    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
//    def getStudentList() {
//        def responseMap = [:]
//        def stuList = feeDetailService.provisionalStudentList(params)
//        responseMap.status = "referenceNo"
//        responseMap.label = params.pageType
//        responseMap.stuList = stuList
//        render responseMap as JSON
//
//    }
//
//    def checkStudentByRollNo = {
//        def student = Student.findByRollNo(params.rollNo)
//        def response = [id: student.id, feeStatus: true]
//        render response as JSON
//    }
//
//    def saveBulkFeeDetails = {
//        def feeDetailsInstance = feeDetailService.saveFeeDetails(params)
//
//        if (feeDetailsInstance.hasErrors()) {
//            render "<h5>Fee Details for this student cannot be saved</h5>"
//        }
//
//        if (feeDetailsInstance) {
//            render "<h5>Fee Details saved</h5>"
//        }
//
//    }
//    @Secured(["ROLE_ADMIN", "ROLE_STUDY_CENTRE", "ROLE_ACCOUNT"])
//    def payAdmissionFee = {
//        def bankName = Bank.list(sort: 'bankName')
//        def paymentMode = PaymentMode.list(sort: 'paymentModeName')
//        [bankName: bankName, paymentMode: paymentMode]
//    }
//
//    @Secured(["ROLE_ADMIN", "ROLE_STUDY_CENTRE"])
//    def generateChallanSCAdmissionFee = {
//        def programList = ProgramDetail.list(sort: 'courseCode')
//        def programCategory = ProgramType.list(sort: 'type')
//        def miscFeeType = FeeType.list(sort: 'type')
//        [programList: programList, programCategory: programCategory, miscFeeType: miscFeeType]
//    }
//
//    def loadProgram = {
////        println("params"+params)
//        def programType = ProgramType.findById(Long.parseLong(params.type))
//        def programList = ProgramDetail.findAllByProgramType(programType)
//        def response = [programList: programList]
////        println(response.programList[0].courseName)
//        render response as JSON
//    }
//    @Secured(["ROLE_ADMIN", "ROLE_STUDY_CENTRE"])
//    def challanForMiscellaneousFee = {
//        def programList = ProgramDetail.list(sort: 'courseCode')
//        def programCategory = ProgramType.list(sort: 'type')
//        def miscFeeType = FeeType.list(sort: 'type')
//        [programList: programList, programCategory: programCategory, miscFeeType: miscFeeType]
//    }
//
//    @Secured(["ROLE_ADMIN", "ROLE_STUDY_CENTRE", "ROLE_ACCOUNT"])
//    def payMiscellaneousFee = {
//        def bankName = Bank.list(sort: 'bankName')
//        def paymentMode = PaymentMode.list(sort: 'paymentModeName')
//        [bankName: bankName, paymentMode: paymentMode]
//    }
//    def populateStudents = {
//        def resultMap = [:]
//        resultMap = feeDetailService.StudentList(params)
//        render resultMap as JSON
//    }
//    def populateStudentsForStudyCenter = {
//        def resultMap = [:]
//        if (params.feeType == '3') {
////            println("Admission")
//            resultMap = feeDetailService.StudentListForAdmission(params)
//        } else if (params.feeType == '2') {
//            resultMap = feeDetailService.StudentListForCertificate(params)
//        } else {
//            resultMap = feeDetailService.StudentListForMFee(params)
//        }
//        render resultMap as JSON
//    }
//    def populateStudentsForMFee = {
//        def resultMap = [:]
//        resultMap = feeDetailService.StudentListForMFee(params)
//        render resultMap as JSON
//    }
//
//    def populateStudentsForAllProgram = {
////        println("in method");
//        def resultMap = [:]
//        resultMap = feeDetailService.AllProgramStudentList()
////        println("in method"+resultMap);
//        render resultMap as JSON
//    }
//
//    def getBankBranch = {
//
//        def bankIns = Bank.findById(Long.parseLong(params.bankId))
//        def branchName = bankIns.branch
//        render branchName as JSON
//
//    }
//    def saveFeeData = {
//
//        feeDetailService.saveFeeDetails(params)
//        def resultMap = [:]
//        resultMap = feeDetailService.StudentList(params)
//        render resultMap as JSON
//
//    }
//    def getFeeAmount = {
////        println("################################################ ====>"+params);
//        def resultMap = [:]
//        def student = Student.findById(params.studentId)
//        def programFee = AdmissionFee.findByFeeSession(FeeSession.findByProgramDetailId(student.programDetail[0]));
////        println(student.programDetail)
//        def programFeeAmount
////        println("type --->"+params.feeType)
//        def feeType = params.feeType
//        if (feeType == 1) {
//            programFeeAmount = programFee.feeAmountAtSC
////            println("1")
//        } else if (feeType == 2) {
//            programFeeAmount = programFee.examinationFee
////            println("2")
//        } else {
//            programFeeAmount = programFee.certificateFee
////                println("3")
//        }
//        resultMap.programFeeAmount = programFeeAmount;
////        println(programFee.feeAmountAtSC)
////        println(resultMap)
//        render resultMap as JSON
//    }
//
//    @Synchronized("challanLock")
//    def challanForStudyCenterStu() {
////        println("***************" + params)
//
//        DateFormat dfp = new SimpleDateFormat("MM/dd/yyyy");
//        List<Student> studList = []
//        List<AdmissionFee> addmissionFee = []
//        studList.clear()
//        addmissionFee.clear()
//        def stuList = []
//        stuList.clear()
//        def totalFee = 0;
//        def lateFee = 0
//        def feeDetails
//        def term
//        def challanNo
//        def studyCentre
//        def feeForStudent
//        def today = dfp.parse(dfp.format(new Date()))
//        def feeTypeName = FeeType.findById(params.feeCategory).type
//
//        if (params.rollNoSearch) {
//            lateFee = 0
//            def stuIns = Student.findByRollNo(params.rollNoSearch)
//            studyCentre = stuIns.studyCentre
//            studList.add(stuIns)
//            Set<ProgramDetail> programDetails = ProgramDetail.findAllById(stuIns.programDetail[0].id)
//            int year = stuIns.registrationYear
//            def sessionVal = year + 1
//            sessionVal = year + '-' + sessionVal
//            def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(stuIns.programDetail[0].id), sessionVal)
//            challanNo = studentRegistrationService.getChallanNumber()
//            if (params.feeCategory == '3') {
//                stuIns.challanNo = challanNo
//                stuIns.save(failOnError: true)
//
//                def lateFeeDate = dfp.parse(dfp.format(stuIns.programDetail.lateFeeDate[0]))
//                feeForStudent = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, Integer.parseInt(params.semesterListHidden)).feeAmountAtSC
//                if (lateFeeDate != null) {
//                    if (today.compareTo(lateFeeDate) > 0) {
//                        lateFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, Integer.parseInt(params.semesterListHidden)).lateFeeAmount
//                    }
//                }
//                feeForStudent = feeForStudent + lateFee
//                totalFee = totalFee + feeForStudent
//                def studInFeeDetails = FeeDetails.findByStudentAndFeeTypeAndSemesterValue(stuIns, FeeType.findById(3), Integer.parseInt(params.semesterListHidden))
//                if (studInFeeDetails) {
//                    feeDetails = studInFeeDetails
//                } else {
//                    feeDetails = new FeeDetails()
//                }
//            } else {
//                feeForStudent = MiscellaneousFee.findByFeeSessionAndFeeType(feeSessionObj, FeeType.findById(params.feeCategory)).amount
//                totalFee = totalFee + feeForStudent
//                feeDetails = new FeeDetails()
//            }
//
//
//            term = params.semesterListHidden
//            feeDetails.feeType = FeeType.findById(params.feeCategory)
//            feeDetails.paidAmount = feeForStudent
//            feeDetails.challanNo = challanNo
//            feeDetails.isApproved = Status.findById(1)
//            feeDetails.challanDate = new Date()
//            feeDetails.student = stuIns
//            feeDetails.semesterValue = Integer.parseInt(params.semesterListHidden)
//            feeDetails.save(failOnError: true, flush: true)
//            addmissionFee.add(feeForStudent)
//        } else {
//            stuList = params.studentListId.split(",")
//            def semesterList = params.semesterListHidden.split(",")
//            term = Integer.parseInt(semesterList[0])
//            challanNo = studentRegistrationService.getChallanNumber()
//            for (def i = 0; i < stuList.size(); i++) {
//                lateFee = 0
//                def stuIns = Student.findById(Long.parseLong(stuList[i]))
//                if (i == 0) {
//                    studyCentre = stuIns.studyCentre
//                }
//                def FeeDetails
//                studList.add(stuIns)
//                int year = stuIns.registrationYear
//                def sessionVal = year + 1
//                sessionVal = year + '-' + sessionVal
//
//                def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(stuIns.programDetail[0].id), sessionVal)
//
//                if (params.feeCategory == '3') {
//                    stuIns.challanNo = challanNo
//                    stuIns.save(failOnError: true)
//                    def lateFeeDate = stuIns.programDetail.lateFeeDate[0]
//
//
//                    Set<ProgramDetail> programDetails = ProgramDetail.findAllById(stuIns.programDetail[0].id)
//
//                    feeForStudent = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, term).feeAmountAtSC
//                    if (lateFeeDate != null) {
//                        if (today.compareTo(lateFeeDate) > 0) {
//                            lateFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, term).lateFeeAmount
//                        }
//                    }
//                    feeForStudent = feeForStudent + lateFee
//                    totalFee = totalFee + feeForStudent
//                    def studInFeeDetails = examinationproject.FeeDetails.findByStudentAndFeeTypeAndSemesterValue(stuIns, FeeType.findById(3), semesterList[i])
//
//                    if (studInFeeDetails) {
//                        feeDetails = studInFeeDetails
//                    } else {
//                        feeDetails = new FeeDetails()
//                    }
//                } else {
//                    feeForStudent = MiscellaneousFee.findByFeeSessionAndFeeType(feeSessionObj, FeeType.findById(params.feeCategory)).amount
//                    totalFee = totalFee + feeForStudent
//                    feeDetails = new FeeDetails()
//                }
//                feeDetails.feeType = FeeType.findById(params.feeCategory)
//                feeDetails.paidAmount = feeForStudent
//                feeDetails.challanNo = challanNo
//                feeDetails.challanDate = new Date()
//                feeDetails.student = stuIns
//                feeDetails.isApproved = Status.findById(1)
//                feeDetails.semesterValue = Integer.parseInt(semesterList[i])
//                feeDetails.save(failOnError: true, flush: true)
//                addmissionFee.add(feeForStudent)
//            }
//        }
//
////        def termMesg = feeTypeName + ' for ' + termText + ' Term'
//        def args = [template: "printChallan", model: [studList: studList, challanNo: challanNo, feeTypeName: feeTypeName, term: term, studyCentre: studyCentre, addmissionFee: addmissionFee, totalFee: totalFee, lateFee: lateFee], filename: challanNo + ".pdf"]
//        pdfRenderingService.render(args + [controller: this], response)
//    }
//
//
//    def printChallan = {
//    }
//
//    def populateStudentsByChallan = {
//        def resultMap = [:]
//        resultMap = feeDetailService.StudentListByChallan(params)
////        println(resultMap)
//        render resultMap as JSON
//    }
//
//
////    def payChallanForStudyCenterStu = {
////        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
////        DateFormat dfp = new SimpleDateFormat("MM/dd/yyyy");
////        def courseNameList = [], courseFee = []
////        def feeDetailsInstance = FeeDetails.findAllByChallanNo(params.searchChallanNo)
////        def stuList = []
////        feeDetailsInstance.each {
////            stuList << it.student
////        }
////        def currentUser = springSecurityService.currentUser
////        def totalFee = 0;
////        def lateFee = 0
////        def studyCentre
////        studyCentre = stuList[0].studyCentre
////        stuList.each {
//////            println(params.semester[0])
////            lateFee = 0
////            def lateFeeDate = dfp.parse(dfp.format(it.programDetail.lateFeeDate[0]))
////            def today = dfp.parse(dfp.format(feeDetailsInstance[0].challanDate))
////
////            int year = it.registrationYear
////            def sessionVal = year + 1
////            sessionVal = year + '-' + sessionVal
////
////            def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(it.programDetail[0].id), sessionVal)
////
////            if (lateFeeDate && (Integer.parseInt(params.semester[0]) == 1)) {
////                if (today.compareTo(lateFeeDate) > 0) {
////                    lateFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, feeDetailsInstance[0].semesterValue).lateFeeAmount
////                }
////            }
////            courseNameList << it.programDetail[0].courseName
////            if (it.studyCentre.centerCode[0] == "11111") {
////                courseFee << AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, feeDetailsInstance[0].semesterValue).feeAmountAtIDOL + lateFee
////            } else {
////                courseFee << AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, feeDetailsInstance[0].semesterValue).feeAmountAtSC + lateFee
////            }
////        }
////        for (def k = 0; k < courseFee.size(); k++) {
////            totalFee = totalFee + courseFee[k]
////        }
////        def paymentModeName = PaymentMode.findById(params.paymentMode)
////        def bank = Bank.findById(params.bankName)
////        def branchInst
////        if (params.bankCheckBox) {
////            branchInst = new Branch()
////            branchInst.bank = bank
////            branchInst.branchLocation = params.branchLocation
////            branchInst.save(flush: true)
////        } else {
////            branchInst = Branch.findById(params.branchLocation)
////        }
////        def termList = []
////        def prevFeeStatus = true
////        feeDetailsInstance.each {
//////            println(it.semesterValue)
////            if (it.semesterValue > 1) {
////                def feeDInst = FeeDetails.findByStudentAndFeeTypeAndSemesterValueAndIsApproved(it.student, FeeType.findById(3), (it.semesterValue - 1), Status.findById(4))
////                if (!feeDInst) {
////                    prevFeeStatus = false
////                }
////            }
////        }
////        if (prevFeeStatus) {
////            feeDetailsInstance.each {
////                termList << it.semesterValue
////                it.paymentModeId = paymentModeName
////                it.paymentReferenceNumber = params.paymentReferenceNumber
////                it.bankId = Bank.findById(params.bankName)
////                it.branchId = branchInst
////                it.paymentDate = df.parse(params.paymentDate)
////                if (PaymentMode.findById(params.paymentMode) != PaymentMode.findById(1)) {
////                    it.isApproved = Status.findById(3)
////                    if (it.save(flush: true, failOnError: true)) {
////                        if (it.semesterValue == 1) {
////                            it.student.status = Status.findById(3)
////                        }
////                    }
////                } else {
////                    it.isApproved = Status.findById(4)
////                    if (it.save(flush: true, failOnError: true)) {
////                        if (it.student.migratingStudyCentre != 0) {
////                            it.student.studyCentre = StudyCenter.findById(it.student.migratingStudyCentre)
////                            it.student.migratingStudyCentre = 0
////                        }
////                        if (it.semesterValue == 1) {
////                            it.student.status = Status.findById(4)
////                        }
////                    }
////                }
////            }
////            def challanNo = params.searchChallanNo
////            def paymentDate = params.paymentDate
////            def paymentReferenceNumber = params.paymentReferenceNumber
////            def args = [template: "printPayChallan", model: [bank: bank, termList: termList, lateFee: lateFee, studyCentre: studyCentre, branch: branchInst, paymentReferenceNumber: paymentReferenceNumber, paymentModeName: paymentModeName, paymentDate: paymentDate, stuList: stuList, courseFee: courseFee, totalFee: totalFee, courseNameList: courseNameList, challanNo: challanNo,], filename: challanNo + ".pdf"]
////            pdfRenderingService.render(args + [controller: this], response)
////        } else {
////            flash.message = "Please Pay Previous Term Fee First."
////            redirect(action: 'payAdmissionFee')
////        }
////
////
////    }
//    def payMiscFeeChallan = {
//        def courseNameList = [], courseFee = [], feeType = [], termValue = []
//        def stuList = []
//        def studyCentre
//        def miscFeeChallanList = FeeDetails.findAllByChallanNo(params.searchChallanNo)
//        miscFeeChallanList.each {
//            stuList << it.student
//            courseNameList << it.student.program.courseName
////            int year = it.student.registrationYear
//            feeType << it.feeType.type
//            termValue << it.semesterValue.semesterNo
////            def sessionVal = year + 1
////            sessionVal = year + '-' + sessionVal
////            def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(it.student.programDetail[0].id), sessionVal)
//            courseFee << MiscellaneousFee.findByProgramDetailAndFeeType(it.student.program, it.feeType).amount
//        }
//        def totalFee = 0;
//        for (def k = 0; k < courseFee.size(); k++) {
//            totalFee = totalFee + courseFee[k]
//        }
//        def paymentModeName = PaymentMode.findById(params.paymentMode)
//        def bank = Bank.findById(params.bankName)
//        def branch
//        if (params.bankCheckBox) {
//            branch = new Branch()
//            branch.bank = bank
//            branch.branchLocation = params.branchLocation
//            branch.save(flush: true)
//        } else {
//            branch = Branch.findById(params.branchLocation)
//        }
//        def feeDetailsInstance = FeeDetails.findAllByChallanNo(params.searchChallanNo)
//        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//        def status = false
//        feeDetailsInstance.each {
//            it.paymentModeId = PaymentMode.findById(params.paymentMode)
//            it.bankId = bank
//            it.branchId = branch
//            it.paymentReferenceNumber = params.paymentReferenceNumber
//            it.paymentDate = df.parse(params.paymentDate)
////            if (PaymentMode.findById(params.paymentMode) != PaymentMode.findById(1)) {
////                it.isApproved = Status.findById(3)
////                if (it.save(flush: true, failOnError: true)) {
////                    status = true
////                }
////            } else {
//                it.isApproved = Status.findById(3)
//                if (it.save(flush: true, failOnError: true)) {
//                    status = true
//                }
////            }
//        }
//        def challanNo = params.searchChallanNo
//        def paymentDate = params.paymentDate
//        def paymentReferenceNumber = params.paymentReferenceNumber
//        def args = [template: "printPayMiscFeeChallan", model: [bank: bank, feeType: feeType[0], branch: branch, termValue: termValue, paymentReferenceNumber: paymentReferenceNumber, paymentModeName: paymentModeName, paymentDate: paymentDate, stuList: stuList, courseFee: courseFee, totalFee: totalFee, courseNameList: courseNameList, challanNo: challanNo,], filename: challanNo + ".pdf"]
//        pdfRenderingService.render(args + [controller: this], response)
//    }
//    def gerStudentId = {
//        def resultMap = [:]
//        def studentId = Student.findByRollNo(params.rollNo).id
//        resultMap.studentId = studentId
//        render resultMap as JSON
//    }
//    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT", "ROLE_HELP_DESK", "ROLE_STUDY_CENTRE"])
//    def feeStatusForRollNumber = {
//    }
//    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT", "ROLE_HELP_DESK", "ROLE_STUDY_CENTRE"])
//    def checkRollNoFeeStatus = {
//        def resultMap = [:]
//        resultMap = feeDetailService.rollNumberFeeStatus(params)
//        render resultMap as JSON
//    }
//    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT", "ROLE_HELP_DESK"])
//
//    def generateProgrammeFee = {
//        if (params.amount) {
//            def feeDetailIns = FeeDetails.findById(Integer.parseInt(params.misFeeObject))
//            def program = feeDetailIns.student.program.courseName
//            [misFeeObject: feeDetailIns, amount: params.amount, program: program]
//        }
//    }
//
//    def searchDataByRollNumber = {
//        def status = feeDetailService.searchByRollNumber(params)
//        render status as JSON
//    }
//
//    def searchHomeAssignmentByRollNumber = {
//
//    }
//
//    def checkRollNoPreviousData = {
//        def returnMap = [:]
//        def studObj = Student.findByRollNo(params.rollNumberInput)
//        def feeType = FeeType.findById(Long.parseLong(params.postFeeType))
//        def semInst = Semester.findById(Long.parseLong(params.semester))
//        def currFeeObj = FeeDetails.findByStudentAndSemesterValueAndFeeTypeAndIsApproved(studObj, semInst, feeType, Status.findById(4))
//        if (currFeeObj) {
//            returnMap.feePaid = true
//        } else {
//            int semValue = semInst.semesterNo - 1
//            if(semValue>0) {
//                def prevSemInst = Semester.findBySemesterNoAndProgramSession(semValue, semInst.programSession)
//                def misFeeObj = FeeDetails.findByStudentAndSemesterValueAndFeeType(studObj, prevSemInst, FeeType.findById(Long.parseLong(params.postFeeType)))
//                if (misFeeObj) {
//                } else {
//                    returnMap.feePaid = false
//                }
//            }
//        }
//        render returnMap as JSON
//    }
//
//    @Synchronized("challanLock")
//    def savePostExamFee() {
//        def studentInst = Student.findByRollNo(params.rollNumberInput)
//        if (studentInst) {
//            def misFeeObj = new FeeDetails()
//            misFeeObj.semesterValue = Semester.findById(Long.parseLong(params.semester))
//            misFeeObj.challanNo = studentRegistrationService.getChallanNumber()
//            misFeeObj.feeType = FeeType.findById(Long.parseLong(params.postFeeType))
//            misFeeObj.student = studentInst
//            misFeeObj.isApproved = Status.findById(1)
//            def progmIns = studentInst.program
//            def misFee = MiscellaneousFee.findByFeeTypeAndProgramDetail(misFeeObj.feeType, progmIns)
//            def amount = misFee.amount
//            misFeeObj.paidAmount = amount
//            misFeeObj.challanDate = new Date()
//            misFeeObj.save(failOnError: true)
//            redirect(action: 'generateProgrammeFee', params: [misFeeObject: misFeeObj.id, amount: amount])
//        } else {
//            flash.message = "Unable to find any student the Roll Number."
//            redirect(action: 'generateProgrammeFee')
//        }
//    }
//
//    @Secured(["ROLE_ADMIN", "ROLE_STUDY_CENTRE", "ROLE_ACCOUNT"])
//    def challanNumberStatus = {
//
//    }
//
//    def challanDetails = {
//        def result = feeDetailService.getChallanDetails(params)
//        render result as JSON
//    }
//    def challanDetailsRemove = {
//        def result = feeDetailService.getChallanDetailsForRemove(params)
//        render result as JSON
//    }
//    def getTermForFeeType = {
//        def returnMap = [:]
//        def feeTypeInst = FeeType.findById(Long.parseLong(params.feeType))
//        def stuInst = Student.findByRollNo(params.rollNo)
//        if (feeTypeInst.id == 1) {
//            returnMap.term = stuInst.program.noOfTerms
//        } else {
//            if (stuInst.program.programType.id == ProgramType.findById(1).id)
//                returnMap.term = stuInst.program.noOfAcademicYears
//            else {
//                returnMap.term = stuInst.program.noOfTerms
//            }
//        }
//        render returnMap as JSON
//    }
//    def editChallanData = {
//        def feeDetailList = FeeDetails.findAllByChallanNo(params.challanNoText)
//        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//        if (feeDetailList) {
//            feeDetailList.each {
//                it.bankId = Bank.findById(Integer.parseInt(params.bankName))
//                it.paymentModeId = PaymentMode.findById(Integer.parseInt(params.paymentMode))
//                it.paymentReferenceNumber = params.paymentReferenceNumber
//                it.bankId = Bank.findById(Integer.parseInt(params.bankName))
//                it.branchId = Branch.findById(Integer.parseInt(params.branchLocation))
//                it.paymentDate = df.parse(params.paymentDate)
//            }
//            flash.message = "Updated Succesfully."
//        } else {
//            flash.message = "Unable to updated Succesfully."
//        }
//
//        redirect(action: 'challanNumberStatus')
//    }
//
//    def searchCustomChallan() {
//
//    }
//
//
//    def deleteStudentFromChallan = {
//        def resultMap = [:]
//        def feeDetailInst = FeeDetails.findById(Long.parseLong(params.studentId))
//        def status
//        if (feeDetailInst) {
//            feeDetailInst.delete(flush: true, failOnError: true)
//            if (FeeDetails.exists(feeDetailInst.id)) {
//                flash.message = "Unable to Delete Successfully."
//            } else {
//                flash.message = "Delete Successfully."
//            }
//
//        }
//        redirect(action: 'challanNumberStatus')
//    }
//
//    @Secured(["ROLE_ADMIN"])
//    def printAChallan() {
//
//    }
//
//    @Secured(["ROLE_ADMIN"])
//    def printTheChallan() {
//        def challanInst = FeeDetails.findAllByChallanNo(params.challanNo)
//        if (challanInst) {
//            if (challanInst[0].isApproved == Status.findById(1)) {
//                redirect(action: 'printUnapproveChallan', params: [challanNo: params.challanNo])
//            } else {
//                redirect(action: 'printApproveChallan', params: [challanNo: params.challanNo])
//            }
//        } else {
//            flash.message = "Please Enter a Correct Challan Number"
//            redirect(action: 'printAChallan')
//        }
//    }
//    @Secured(["ROLE_ADMIN"])
//    def printUnapproveChallan = {
//        println("=================" + params)
//        def feeDetailsInstance = FeeDetails.findAllByChallanNo(params.challanNo)
//        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//        DateFormat dfp = new SimpleDateFormat("MM/dd/yy");
//        def courseNameList = [], courseFee = []
//        def totalFee = 0;
//        def lateFee = 0
//        def studyCentre
//        def stuList = []
//        def termList = []
//        feeDetailsInstance.each {
//            termList << it.semesterValue
//            stuList << it.student
//            courseFee << it.paidAmount
//            courseNameList << it.student.programDetail[0].courseName
//            totalFee = totalFee + it.paidAmount
//        }
//        stuList.each {
//            lateFee = 0
//            def lateFeeDate = dfp.parse(dfp.format(it.programDetail.lateFeeDate[0]))
//            def today = dfp.parse(dfp.format(feeDetailsInstance[0].challanDate))
//            int year = it.registrationYear
//            def sessionVal = year + 1
//            sessionVal = year + '-' + sessionVal
//            def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(it.programDetail[0].id), sessionVal)
//            if (lateFeeDate && (termList[0] == 1)) {
//                if (today.compareTo(lateFeeDate) > 0) {
//                    lateFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, feeDetailsInstance[0].semesterValue).lateFeeAmount
//                }
//            }
//        }
//        studyCentre = stuList[0].studyCentre
//        def challanNo = params.challanNo
//        def args = [template: "printChallan", model: [studList: stuList, challanNo: challanNo, feeTypeName: feeDetailsInstance[0].feeType.type, term: termList[0], studyCentre: studyCentre, addmissionFee: courseFee, totalFee: totalFee, lateFee: lateFee], filename: challanNo + ".pdf"]
//        pdfRenderingService.render(args + [controller: this], response)
//
//    }
//    def printApproveChallan = {
//        def feeDetailsInstance = FeeDetails.findAllByChallanNo(params.challanNo)
//        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//        DateFormat dfp = new SimpleDateFormat("MM/dd/yy");
//        def courseNameList = [], courseFee = []
//        def totalFee = 0;
//        def lateFee = 0
//        def studyCentre
//        def stuList = []
//        def termList = []
//        def feeType = []
//        feeDetailsInstance.each {
//            termList << it.semesterValue
//            stuList << it.student
//            courseFee << it.paidAmount
//            courseNameList << it.student.programDetail[0].courseName
//            totalFee = totalFee + it.paidAmount
//            feeType << it.feeType.type
//        }
//        studyCentre = stuList[0].studyCentre
//        def paymentModeName = feeDetailsInstance[0].paymentModeId
//        def bank = feeDetailsInstance[0].bankId
//        def branchInst = feeDetailsInstance[0].branchId
//        def challanNo = params.challanNo
//        def paymentDate = df.format(feeDetailsInstance[0].paymentDate)
//        def paymentReferenceNumber = feeDetailsInstance[0].paymentReferenceNumber
//        if (feeDetailsInstance[0].feeType.id == 3) {
//            stuList.each {
//                lateFee = 0
//                def lateFeeDate = dfp.parse(dfp.format(it.programDetail.lateFeeDate[0]))
//                def today = dfp.parse(dfp.format(feeDetailsInstance[0].challanDate))
//                int year = it.registrationYear
//                def sessionVal = year + 1
//                sessionVal = year + '-' + sessionVal
//                def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(it.programDetail[0].id), sessionVal)
//                if (lateFeeDate && (termList[0] == 1)) {
//                    if (today.compareTo(lateFeeDate) > 0) {
//                        lateFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, feeDetailsInstance[0].semesterValue).lateFeeAmount
//                    }
//                }
//            }
//            def args = [template: "printPayChallan", model: [bank: bank, termList: termList, lateFee: lateFee, studyCentre: studyCentre, branch: branchInst, paymentReferenceNumber: paymentReferenceNumber, paymentModeName: paymentModeName, paymentDate: paymentDate, stuList: stuList, courseFee: courseFee, totalFee: totalFee, courseNameList: courseNameList, challanNo: challanNo,], filename: challanNo + ".pdf"]
//            pdfRenderingService.render(args + [controller: this], response)
//        } else {
//            def args = [template: "printPayMiscFeeChallan", model: [bank: bank, studyCentre: studyCentre, feeType: feeType[0], branch: branchInst, termValue: termList, paymentReferenceNumber: paymentReferenceNumber, paymentModeName: paymentModeName, paymentDate: paymentDate, stuList: stuList, courseFee: courseFee, totalFee: totalFee, courseNameList: courseNameList, challanNo: challanNo,], filename: challanNo + ".pdf"]
//            pdfRenderingService.render(args + [controller: this], response)
//        }
//
//    }
}
