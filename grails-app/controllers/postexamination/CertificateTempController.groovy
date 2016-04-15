package postexamination

import certificate.Certificate
import certificate.CertificateProgram
import certificate.CertificateRequests
import IST.EmployeeDesignation
import certificate.AnnexerA
import certificate.AnnexerB
import certificate.AnnexerC
import certificate.AnnexerD
import certificate.AnnexerE
import certificate.AnnexerF
import certificate.AnnexerG
import certificate.AnnexerH
import certificate.AnnexerHStudents
import certificate.CertificateRequestDetails
import certificate.CertificateRole
import certificate.CertificatesTemplate
import certificate.GeneratedCertificateDetails
import com.university.Role
import com.university.UserRole
import examinationproject.ProgramSession
import examinationproject.ProgramType
import examinationproject.Semester
import examinationproject.SemesterFeeDetails
import examinationproject.Student
import examinationproject.StudentSubject
import grails.converters.JSON

import java.text.SimpleDateFormat

class CertificateTempController {
    def springSecurityService
    def certificateInfoService

    def loadCorrectMethod() {
        /*Action for generating certificate according to inputs*/
        def certificateInst=Certificate.findById(Long.parseLong(params.certificate))
        if (certificateInst.format == 'Annexer_A') {
            redirect(action: "annexerA", id: params.id)
        } else if (certificateInst.format == 'Annexer_B') {
            redirect(action: "annexerB", id: params.id)
        } else if (certificateInst.format == 'Annexer_C') {
            redirect(action: "annexerC", id: params.id)
        } else if (certificateInst.format == 'Annexer_F') {
            redirect(action: "annexerF", id: params.id)
        } else if (certificateInst.format == 'Annexer_G') {
            redirect(action: "annexerG", id: params.id)
        } else if (certificateInst.format == 'Annexer_H') {
            redirect(action: "annexerH", id: params.id)
        } else if (certificateInst.format == 'Annexer_D') {
            redirect(action: "annexerD", id: params.id)
        } else if (certificateInst.format == 'Annexer_E') {
            redirect(action: "annexerE", id: params.id)
        }
        else{
            redirect(action: "otherCertificate", id: params.id)
        }
    }

    def annexerA() {
        /*creating certificate template for Parent's income tax purpose*/
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def showEdit = true
        def certificateReqInst = CertificateRequests.findById(Long.parseLong(params.id))
        if (certificateReqInst.status == 1) {
            showEdit = false
        }
        def semesterNo = certificateReqInst.student.semester
        def semester, prevSemester
        if (semesterNo == 1) {
            semester = 'First'
        } else if (semesterNo == 2) {
            semester = 'Second'
            prevSemester = 'First'
        } else if (semesterNo == 3) {
            semester = 'Third'
            prevSemester = 'Second'
        } else if (semesterNo == 4) {
            semester = 'Fourth'
            prevSemester = 'Third'
        } else if (semesterNo == 5) {
            semester = 'Fifth'
            prevSemester = 'Fourth'
        } else if (semesterNo == 6) {
            semester = 'Sixth'
            prevSemester = 'Fifth'
        } else if (semesterNo == 7) {
            semester = 'Seventh'
            prevSemester = 'Sixth'
        } else if (semesterNo == 8) {
            semester = 'Eighth'
            prevSemester = 'Seventh'
        } else if (semesterNo == 9) {
            semester = 'Ninth'
            prevSemester = 'Eighth'
        } else if (semesterNo == 10) {
            semester = 'Tenth'
            prevSemester = 'Ninth'
        } else if (semesterNo == 11) {
            semester = 'Eleventh'
            prevSemester = 'Tenth'
        } else if (semesterNo == 12) {
            semester = 'Twelfth'
            prevSemester = 'Eleventh'
        }
        if (CertificateRequestDetails.findByCertificateRequests(certificateReqInst)) {
            def certificateDetailsInst = CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
            def annexerAInst = AnnexerA.findByCertificateDetails(certificateDetailsInst)
            def refNo = annexerAInst.refNo
            def dateShown = annexerAInst.dateShown
            def fromName = annexerAInst.fromName
            def fromPosition = annexerAInst.fromPosition
            def header = annexerAInst.header
            def paragraph1 = annexerAInst.paragraph1
            def paragraph2 = annexerAInst.paragraph2
            def paragraph3 = annexerAInst.paragraph3
            def signatureName = annexerAInst.signatureName
            def prevYearTuitionFee = annexerAInst.prevYearTuitionFee
            def prevYearAdmissionFee = annexerAInst.prevYearAdmissionFee
            def prevYearExamFee = annexerAInst.prevYearExamFee
            def prevYearOtherFee = annexerAInst.prevYearOtherFee
            def thisYearTuitionFee = annexerAInst.thisYearTuitionFee
            def thisYearLabFee = annexerAInst.thisYearLabFee
            def thisYearExamFee = annexerAInst.thisYearExamFee
            def totalFee = annexerAInst.totalFee
            [prevSemester: prevSemester, semester: semester, semesterNo: semesterNo, totalFee: totalFee, thisYearExamFee: thisYearExamFee, thisYearLabFee: thisYearLabFee, thisYearTuitionFee: thisYearTuitionFee, prevYearOtherFee: prevYearOtherFee, prevYearExamFee: prevYearExamFee, prevYearAdmissionFee: prevYearAdmissionFee, prevYearTuitionFee: prevYearTuitionFee, certificateReqId: certificateReqInst.id, showEdit: showEdit, refNo: refNo, dateShown: dateShown, fromName: fromName, fromPosition: fromPosition, header: header, paragraph1: paragraph1, paragraph2: paragraph2, paragraph3: paragraph3, signatureName: signatureName, fromDB: 'yes']
        } else {
            def applicantName, department, program, male = false, rollNo, gurdianName
            if (certificateReqInst.student.middleName != null) {
                applicantName = certificateReqInst.student.firstName + " " + certificateReqInst.student.middleName + " " + certificateReqInst.student.lastName

            } else {
                applicantName = certificateReqInst.student.firstName + " " + certificateReqInst.student.lastName
            }

            if (certificateReqInst.student.gender == 'Male') {
                male = true
            }
            department = certificateReqInst.student.programBranch.name
            gurdianName = certificateReqInst.student.guardianName

            rollNo = certificateReqInst.student.rollNo
            program = certificateReqInst.student.program.courseName.replace('(L)', '')
            int year = Calendar.getInstance().get(Calendar.YEAR);
            def refNo = 0
            def refrence = CertificateRequestDetails.createCriteria().get {
                projections {
                    max "referenceNo"
                }
                and {
                    eq('year', year.toString())
                }
            }
            if (refrence != null) {
                refNo = Integer.parseInt(refrence)
            }
            refNo++
            def progSessionInst = ProgramSession.findByProgramDetailId(certificateReqInst.student.program)
            def semesterInst = Semester.findByProgramSessionAndSemesterNo(progSessionInst, semesterNo)
            def prevSemesterInst = Semester.findByProgramSessionAndSemesterNo(progSessionInst, (semesterNo - 1))
            def feeDetailsList = SemesterFeeDetails.findAllByStudentAndSemester(certificateReqInst.student, semesterInst)
            def prevSemFeeAmount = 0, thisSemFeeAmount = 0
            feeDetailsList.each {
                thisSemFeeAmount += Integer.parseInt(it.guReceiptAmount)
            }
            def prevFeeDetailsList = SemesterFeeDetails.findAllByStudentAndSemester(certificateReqInst.student, prevSemesterInst)
            prevFeeDetailsList.each {
                prevSemFeeAmount += Integer.parseInt(it.guReceiptAmount)
            }
            def totalAmount = thisSemFeeAmount + prevSemFeeAmount
            [totalAmount: totalAmount, prevSemFeeAmount: prevSemFeeAmount, thisSemFeeAmount: thisSemFeeAmount, semesterNo: semesterNo, prevSemester: prevSemester, gurdianName: gurdianName, rollNo: rollNo, program: program, semester: semester, showEdit: showEdit, certificateReqId: certificateReqInst.id, year: year, applicantName: applicantName, male: male, department: department, refNo: refNo]
        }
    }

    def annexerB() {
        /*creating certificate template for Studentship Certificate*/
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def showEdit = true
        def certificateReqInst = CertificateRequests.findById(Long.parseLong(params.id))
        if (certificateReqInst.status == 1) {
            showEdit = false
        }
        if (CertificateRequestDetails.findByCertificateRequests(certificateReqInst)) {
            def certificateDetailsInst = CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
            def annexerBInst = AnnexerB.findByCertificateDetails(certificateDetailsInst)
            def refNo = annexerBInst.refNo
            def dateShown = annexerBInst.dateShown
            def fromName = annexerBInst.fromName
            def fromPosition = annexerBInst.fromPosition
            def header = annexerBInst.header
            def paragraph1 = annexerBInst.paragraph1
            def paragraph2 = annexerBInst.paragraph2
            def paragraph3 = annexerBInst.paragraph3
            def signatureName = annexerBInst.signatureName
            [certificateReqId: certificateReqInst.id, showEdit: showEdit, refNo: refNo, dateShown: dateShown, fromName: fromName, fromPosition: fromPosition, header: header, paragraph1: paragraph1, paragraph2: paragraph2, paragraph3: paragraph3, signatureName: signatureName, fromDB: 'yes']
        } else {
            def applicantName, semester, department, program, male = false, rollNo
            if (certificateReqInst.student.middleName != null) {
                applicantName = certificateReqInst.student.firstName + " " + certificateReqInst.student.middleName + " " + certificateReqInst.student.lastName

            } else {
                applicantName = certificateReqInst.student.firstName + " " + certificateReqInst.student.lastName
            }

            if (certificateReqInst.student.gender == 'Male') {
                male = true
            }
            department = certificateReqInst.student.programBranch.name
            def semesterNo = certificateReqInst.student.semester
            if (semesterNo == 1) {
                semester = 'First'
            } else if (semesterNo == 2) {
                semester = 'Second'
            } else if (semesterNo == 3) {
                semester = 'Third'
            } else if (semesterNo == 4) {
                semester = 'Fourth'
            } else if (semesterNo == 5) {
                semester = 'Fifth'
            } else if (semesterNo == 6) {
                semester = 'Sixth'
            } else if (semesterNo == 7) {
                semester = 'Seventh'
            } else if (semesterNo == 8) {
                semester = 'Eighth'
            } else if (semesterNo == 9) {
                semester = 'Ninth'
            } else if (semesterNo == 10) {
                semester = 'Tenth'
            } else if (semesterNo == 11) {
                semester = 'Eleventh'
            } else if (semesterNo == 12) {
                semester = 'Twelfth'
            }
            rollNo = certificateReqInst.student.rollNo
            program = certificateReqInst.student.program.courseName.replace('(L)', '')
            int year = Calendar.getInstance().get(Calendar.YEAR);
            def refNo = 0
            def refrence = CertificateRequestDetails.createCriteria().get {
                projections {
                    max "referenceNo"
                }
                and {
                    eq('year', year.toString())
                }
            }
            if (refrence != null) {
                refNo = Integer.parseInt(refrence)
            }
            refNo++
            [rollNo: rollNo, program: program, semester: semester, showEdit: showEdit, certificateReqId: certificateReqInst.id, year: year, applicantName: applicantName, male: male, department: department, refNo: refNo]
        }
    }

    def annexerC() {
        /*creating certificate template for Bonafied Certificate*/
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def showEdit = true
        def certificateReqInst = CertificateRequests.findById(Long.parseLong(params.id))
        if (certificateReqInst.status == 1) {
            showEdit = false
        }
        if (CertificateRequestDetails.findByCertificateRequests(certificateReqInst)) {
            def certificateDetailsInst = CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
            def annexerCInst = AnnexerC.findByCertificateDetails(certificateDetailsInst)
            def refNo = annexerCInst.refNo
            def dateShown = annexerCInst.dateShown
            def fromName = annexerCInst.fromName
            def fromPosition = annexerCInst.fromPosition
            def header = annexerCInst.header
            def paragraph1 = annexerCInst.paragraph1
            def paragraph2 = annexerCInst.paragraph2
            def paragraph3 = annexerCInst.paragraph3
            def signatureName = annexerCInst.signatureName
            [certificateReqId: certificateReqInst.id, showEdit: showEdit, refNo: refNo, dateShown: dateShown, fromName: fromName, fromPosition: fromPosition, header: header, paragraph1: paragraph1, paragraph2: paragraph2, paragraph3: paragraph3, signatureName: signatureName, fromDB: 'yes']
        } else {
            def applicantName, semester, department, program, male = false, rollNo
            if (certificateReqInst.student.middleName != null) {
                applicantName = certificateReqInst.student.firstName + " " + certificateReqInst.student.middleName + " " + certificateReqInst.student.lastName

            } else {
                applicantName = certificateReqInst.student.firstName + " " + certificateReqInst.student.lastName
            }

            if (certificateReqInst.student.gender == 'Male') {
                male = true
            }
            department = certificateReqInst.student.programBranch.name
            def semesterNo = certificateReqInst.student.semester
            if (semesterNo == 1) {
                semester = 'First'
            } else if (semesterNo == 2) {
                semester = 'Second'
            } else if (semesterNo == 3) {
                semester = 'Third'
            } else if (semesterNo == 4) {
                semester = 'Fourth'
            } else if (semesterNo == 5) {
                semester = 'Fifth'
            } else if (semesterNo == 6) {
                semester = 'Sixth'
            } else if (semesterNo == 7) {
                semester = 'Seventh'
            } else if (semesterNo == 8) {
                semester = 'Eighth'
            } else if (semesterNo == 9) {
                semester = 'Ninth'
            } else if (semesterNo == 10) {
                semester = 'Tenth'
            } else if (semesterNo == 11) {
                semester = 'Eleventh'
            } else if (semesterNo == 12) {
                semester = 'Twelfth'
            }
            rollNo = certificateReqInst.student.rollNo
            program = certificateReqInst.student.program.courseName.replace('(L)', '')
            int year = Calendar.getInstance().get(Calendar.YEAR);
            def refNo = 0
            def refrence = CertificateRequestDetails.createCriteria().get {
                projections {
                    max "referenceNo"
                }
                and {
                    eq('year', year.toString())
                }
            }
            if (refrence != null) {
                refNo = Integer.parseInt(refrence)
            }
            refNo++
            [rollNo: rollNo, program: program, semester: semester, showEdit: showEdit, certificateReqId: certificateReqInst.id, year: year, applicantName: applicantName, male: male, department: department, refNo: refNo]
        }
    }

    def annexerD() {
        /*creating certificate template for Experience Certificate*/
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def showEdit = true
        def certificateReqInst = CertificateRequests.findById(Long.parseLong(params.id))
        if (certificateReqInst.status == 1) {
            showEdit = false
        }
        if (CertificateRequestDetails.findByCertificateRequests(certificateReqInst)) {
            def certificateDetailsInst = CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
            def annexerDInst = AnnexerD.findByCertificateDetails(certificateDetailsInst)
            def refNo = annexerDInst.refNo
            def dateShown = annexerDInst.dateShown
            def fromName = annexerDInst.fromName
            def fromPosition = annexerDInst.fromPosition
            def header = annexerDInst.header
            def paragraph1 = annexerDInst.paragraph1
            def paragraph2 = annexerDInst.paragraph2
            def paragraph3 = annexerDInst.paragraph3
            def signatureName = annexerDInst.signatureName
            [certificateReqId: certificateReqInst.id, showEdit: showEdit, refNo: refNo, dateShown: dateShown, fromName: fromName, fromPosition: fromPosition, header: header, paragraph1: paragraph1, paragraph2: paragraph2, paragraph3: paragraph3, signatureName: signatureName, fromDB: 'yes']
        } else {
            def applicantName, position, department, joiningDate, male = false, releaseDate
            if (certificateReqInst.employee.middleName != null) {
                if (certificateReqInst.employee.salutation == null || certificateReqInst.employee.salutation == '') {
                    applicantName = certificateReqInst.employee.firstName + " " + certificateReqInst.employee.middleName + " " + certificateReqInst.employee.lastName
                } else {
                    applicantName = certificateReqInst.employee.salutation + " " + certificateReqInst.employee.firstName + " " + certificateReqInst.employee.middleName + " " + certificateReqInst.employee.lastName
                }
            } else {
                if (certificateReqInst.employee.salutation == null || certificateReqInst.employee.salutation == '') {
                    applicantName = certificateReqInst.employee.firstName + " " + certificateReqInst.employee.lastName
                } else {
                    applicantName = certificateReqInst.employee.salutation + " " + certificateReqInst.employee.firstName + " " + certificateReqInst.employee.lastName
                }
            }
            def designationList = EmployeeDesignation.findAllByEmployee(certificateReqInst.employee)
            Date maxDate = designationList[0].joiningDate
            Date joinDate = designationList[0].joiningDate
            designationList.each {
                if (it.joiningDate.compareTo(maxDate) >= 0) {
                    maxDate = it.joiningDate
                    position = it.designation
                }
                if (it.joiningDate.compareTo(maxDate) <= 0) {
                    joinDate = it.joiningDate
                }
            }
            if (certificateReqInst.employee.gender == 'Male') {
                male = true
            }
            department = certificateReqInst.employee.department.name
            joiningDate = df.format(joinDate)
            releaseDate = df.format(certificateReqInst.releaseDate)
            int year = Calendar.getInstance().get(Calendar.YEAR);
            def refNo = 0
            def refrence = CertificateRequestDetails.createCriteria().get {
                projections {
                    max "referenceNo"
                }
                and {
                    eq('year', year.toString())
                }
            }
            if (refrence != null) {
                refNo = Integer.parseInt(refrence)
            }
            refNo++
            [showEdit: showEdit, certificateReqId: certificateReqInst.id, year: year, applicantName: applicantName, position: position, male: male, department: department, joiningDate: joiningDate, releaseDate: releaseDate, refNo: refNo]
        }
    }

    def annexerE() {
        /*creating certificate template for Experience Certificate(Guest)*/
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def showEdit = true
        def certificateReqInst = CertificateRequests.findById(Long.parseLong(params.id))
        if (certificateReqInst.status == 1) {
            showEdit = false
        }
        if (CertificateRequestDetails.findByCertificateRequests(certificateReqInst)) {
            def certificateDetailsInst = CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
            def annexerEInst = AnnexerE.findByCertificateDetails(certificateDetailsInst)
            def refNo = annexerEInst.refNo
            def dateShown = annexerEInst.dateShown
            def fromName = annexerEInst.fromName
            def fromPosition = annexerEInst.fromPosition
            def header = annexerEInst.header
            def paragraph1 = annexerEInst.paragraph1
            def paragraph2 = annexerEInst.paragraph2
            def paragraph3 = annexerEInst.paragraph3
            def signatureName = annexerEInst.signatureName
            [showEdit: showEdit, certificateReqId: certificateReqInst.id, refNo: refNo, dateShown: dateShown, fromName: fromName, fromPosition: fromPosition, header: header, paragraph1: paragraph1, paragraph2: paragraph2, paragraph3: paragraph3, signatureName: signatureName, fromDB: 'yes']
        } else {
            def applicantName, position, department, joiningDate, male = false, parentName
            if (certificateReqInst.employee.middleName != null) {
                if (certificateReqInst.employee.salutation == null || certificateReqInst.employee.salutation == '') {
                    applicantName = certificateReqInst.employee.firstName + " " + certificateReqInst.employee.middleName + " " + certificateReqInst.employee.lastName
                } else {
                    applicantName = certificateReqInst.employee.salutation + " " + certificateReqInst.employee.firstName + " " + certificateReqInst.employee.middleName + " " + certificateReqInst.employee.lastName
                }
            } else {
                if (certificateReqInst.employee.salutation == null || certificateReqInst.employee.salutation == '') {
                    applicantName = certificateReqInst.employee.firstName + " " + certificateReqInst.employee.lastName
                } else {
                    applicantName = certificateReqInst.employee.salutation + " " + certificateReqInst.employee.firstName + " " + certificateReqInst.employee.lastName
                }
            }
            def designationList = EmployeeDesignation.findAllByEmployee(certificateReqInst.employee)
            Date maxDate = designationList[0].joiningDate
            Date joinDate = designationList[0].joiningDate
            designationList.each {
                if (it.joiningDate.compareTo(maxDate) >= 0) {
                    maxDate = it.joiningDate
                    position = it.designation
                }
                if (it.joiningDate.compareTo(maxDate) <= 0) {
                    joinDate = it.joiningDate
                }
            }
            if (certificateReqInst.employee.gender == 'Male') {
                male = true
            }
            department = certificateReqInst.employee.department.name
            joiningDate = df.format(joinDate)
            parentName = certificateReqInst.employee.fatherName
            int year = Calendar.getInstance().get(Calendar.YEAR);
            def refNo = 0
            def refrence = CertificateRequestDetails.createCriteria().get {
                projections {
                    max "referenceNo"
                }
                and {
                    eq('year', year.toString())
                }
            }
            if (refrence != null) {
                refNo = Integer.parseInt(refrence)
            }
            refNo++
            [showEdit: showEdit, certificateReqId: certificateReqInst.id, year: year, applicantName: applicantName, position: position, male: male, department: department, joiningDate: joiningDate, releaseDate: parentName, refNo: refNo]
        }
    }

    def annexerF() {
        /*creating certificate template for Certificate stating details fee for applying Bank Loan*/
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def showEdit = true
        def certificateReqInst = CertificateRequests.findById(Long.parseLong(params.id))
        if (certificateReqInst.status == 1) {
            showEdit = false
        }
        if (CertificateRequestDetails.findByCertificateRequests(certificateReqInst)) {
            def certificateDetailsInst = CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
            def annexerFInst = AnnexerF.findByCertificateDetails(certificateDetailsInst)
            def refNo = annexerFInst.refNo
            def dateShown = annexerFInst.dateShown
            def fromName = annexerFInst.fromName
            def fromPosition = annexerFInst.fromPosition
            def header = annexerFInst.header
            def paragraph1 = annexerFInst.paragraph1
            def paragraph2 = annexerFInst.paragraph2
            def paragraph3 = annexerFInst.paragraph3
            def signatureName = annexerFInst.signatureName
            def firstSemFeeEdit = annexerFInst.firstSemFee
            def secondSemFeeEdit = annexerFInst.secondSemFee
            def firstYearFeeEdit = annexerFInst.firstYearFee
            def courseFeeRemarksEdit = annexerFInst.courseFeeRemarks
            def thirdSemFeeEdit = annexerFInst.thirdSemFee
            def fourthSemFeeEdit = annexerFInst.fourthSemFee
            def secondYearFeeEdit = annexerFInst.secondYearFee
            def fifthSemFeeEdit = annexerFInst.fifthSemFee
            def sixthSemFeeEdit = annexerFInst.sixthSemFee
            def thirdYearFeeEdit = annexerFInst.thirdYearFee
            def seventhSemFeeEdit = annexerFInst.seventhSemFee
            def eighthSemFeeEdit = annexerFInst.eighthSemFee
            def fourthYearFeeEdit = annexerFInst.fourthYearFee
            def hostelAdmissionFeeEdit = annexerFInst.hostelFee
            def hostelTotalFeeEdit = annexerFInst.hostelFeeTotal
            def hostelRemarksEdit = annexerFInst.hostelFeeRemarks
            def otherExpensesEdit = annexerFInst.otherExpensesFee
            def otherExpensesTotalEdit = annexerFInst.otherExpensesFeeTotal
            def otherExpensesRemarksEdit = annexerFInst.otherFeeRemarks
            def laptopExpensesEdit = annexerFInst.laptopAmount
            [firstSemFeeEdit: firstSemFeeEdit, secondSemFeeEdit: secondSemFeeEdit, firstYearFeeEdit: firstYearFeeEdit, courseFeeRemarksEdit: courseFeeRemarksEdit, thirdSemFeeEdit: thirdSemFeeEdit, fourthSemFeeEdit: fourthSemFeeEdit, secondYearFeeEdit: secondYearFeeEdit, fifthSemFeeEdit: fifthSemFeeEdit, sixthSemFeeEdit: sixthSemFeeEdit, thirdYearFeeEdit: thirdYearFeeEdit, seventhSemFeeEdit: seventhSemFeeEdit, eighthSemFeeEdit: eighthSemFeeEdit, fourthYearFeeEdit: fourthYearFeeEdit, hostelAdmissionFeeEdit: hostelAdmissionFeeEdit, hostelTotalFeeEdit: hostelTotalFeeEdit, hostelRemarksEdit: hostelRemarksEdit, otherExpensesEdit: otherExpensesEdit, otherExpensesTotalEdit: otherExpensesTotalEdit, otherExpensesRemarksEdit: otherExpensesRemarksEdit, laptopExpensesEdit: laptopExpensesEdit, certificateReqId: certificateReqInst.id, showEdit: showEdit, refNo: refNo, dateShown: dateShown, fromName: fromName, fromPosition: fromPosition, header: header, paragraph1: paragraph1, paragraph2: paragraph2, paragraph3: paragraph3, signatureName: signatureName, fromDB: 'yes']
        } else {
            def applicantName, semester, department, program, male = false, rollNo
            if (certificateReqInst.student.middleName != null) {
                applicantName = certificateReqInst.student.firstName + " " + certificateReqInst.student.middleName + " " + certificateReqInst.student.lastName

            } else {
                applicantName = certificateReqInst.student.firstName + " " + certificateReqInst.student.lastName
            }

            if (certificateReqInst.student.gender == 'Male') {
                male = true
            }
            department = certificateReqInst.student.programBranch.name
            def semesterNo = certificateReqInst.student.semester
            if (semesterNo == 1) {
                semester = 'First'
            } else if (semesterNo == 2) {
                semester = 'Second'
            } else if (semesterNo == 3) {
                semester = 'Third'
            } else if (semesterNo == 4) {
                semester = 'Fourth'
            } else if (semesterNo == 5) {
                semester = 'Fifth'
            } else if (semesterNo == 6) {
                semester = 'Sixth'
            } else if (semesterNo == 7) {
                semester = 'Seventh'
            } else if (semesterNo == 8) {
                semester = 'Eighth'
            } else if (semesterNo == 9) {
                semester = 'Ninth'
            } else if (semesterNo == 10) {
                semester = 'Tenth'
            } else if (semesterNo == 11) {
                semester = 'Eleventh'
            } else if (semesterNo == 12) {
                semester = 'Twelfth'
            }
            rollNo = certificateReqInst.student.rollNo
            program = certificateReqInst.student.program.courseName.replace('(L)', '')
            int year = Calendar.getInstance().get(Calendar.YEAR);
            def refNo = 0
            def programDuration = certificateReqInst.student.registrationYear + "-" + (certificateReqInst.student.registrationYear + certificateReqInst.student.program.noOfAcademicYears)
            def refrence = CertificateRequestDetails.createCriteria().get {
                projections {
                    max "referenceNo"
                }
                and {
                    eq('year', year.toString())
                }
            }
            if (refrence != null) {
                refNo = Integer.parseInt(refrence)
            }
            refNo++
            [programDuration: programDuration, rollNo: rollNo, program: program, semester: semester, showEdit: showEdit, certificateReqId: certificateReqInst.id, year: year, applicantName: applicantName, male: male, department: department, refNo: refNo]
        }

    }

    def annexerG() {
        /*creating certificate template for Certificate asking for semester fee for Bank Loan*/
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def showEdit = true
        def certificateReqInst = CertificateRequests.findById(Long.parseLong(params.id))
        if (certificateReqInst.status == 1) {
            showEdit = false
        }
        if (CertificateRequestDetails.findByCertificateRequests(certificateReqInst)) {
            def certificateDetailsInst = CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
            def annexerGInst = AnnexerG.findByCertificateDetails(certificateDetailsInst)
            def refNo = annexerGInst.refNo
            def dateShown = annexerGInst.dateShown
            def fromName = annexerGInst.fromName
            def fromPosition = annexerGInst.fromPosition
            def header = annexerGInst.header
            def paragraph1 = annexerGInst.paragraph1
            def paragraph2 = annexerGInst.paragraph2
            def paragraph3 = annexerGInst.paragraph3
            def paragraph4 = annexerGInst.paragraph4
            def signatureName = annexerGInst.signatureName
            [certificateReqId: certificateReqInst.id, showEdit: showEdit, refNo: refNo, dateShown: dateShown, fromName: fromName, fromPosition: fromPosition, header: header, paragraph1: paragraph1, paragraph2: paragraph2, paragraph3: paragraph3,paragraph4:paragraph4, signatureName: signatureName, fromDB: 'yes']
        } else {
            def applicantName, semester, department, program, male = false, rollNo
            if (certificateReqInst.student.middleName != null) {
                applicantName = certificateReqInst.student.firstName + " " + certificateReqInst.student.middleName + " " + certificateReqInst.student.lastName

            } else {
                applicantName = certificateReqInst.student.firstName + " " + certificateReqInst.student.lastName
            }

            if (certificateReqInst.student.gender == 'Male') {
                male = true
            }
            department = certificateReqInst.student.programBranch.name
            def progSession = ProgramSession.findByProgramDetailId(certificateReqInst.student.program)
            def semesterNo = certificateReqInst.student.semester
            def semesterInst = Semester.findByProgramSessionAndSemesterNo(progSession, semesterNo)
            def registrationDate = df.format(StudentSubject.findAllByStudentAndSemester(certificateReqInst.student, semesterInst)[0].registrationDate)
            if (semesterNo == 1) {
                semester = 'First'
            } else if (semesterNo == 2) {
                semester = 'Second'
            } else if (semesterNo == 3) {
                semester = 'Third'
            } else if (semesterNo == 4) {
                semester = 'Fourth'
            } else if (semesterNo == 5) {
                semester = 'Fifth'
            } else if (semesterNo == 6) {
                semester = 'Sixth'
            } else if (semesterNo == 7) {
                semester = 'Seventh'
            } else if (semesterNo == 8) {
                semester = 'Eighth'
            } else if (semesterNo == 9) {
                semester = 'Ninth'
            } else if (semesterNo == 10) {
                semester = 'Tenth'
            } else if (semesterNo == 11) {
                semester = 'Eleventh'
            } else if (semesterNo == 12) {
                semester = 'Twelfth'
            }
            rollNo = certificateReqInst.student.rollNo
            program = certificateReqInst.student.program.courseName.replace('(L)', '')
            int year = Calendar.getInstance().get(Calendar.YEAR);
            def refNo = 0
            def refrence = CertificateRequestDetails.createCriteria().get {
                projections {
                    max "referenceNo"
                }
                and {
                    eq('year', year.toString())
                }
            }
            if (refrence != null) {
                refNo = Integer.parseInt(refrence)
            }
            refNo++
            [registrationDate: registrationDate, rollNo: rollNo, program: program, semester: semester, showEdit: showEdit, certificateReqId: certificateReqInst.id, year: year, applicantName: applicantName, male: male, department: department, refNo: refNo]
        }

    }

    def annexerH() {
        /*creating certificate template for Letter to different Organisation for Summer Training*/
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def showEdit = true
        def certificateReqInst = CertificateRequests.findById(Long.parseLong(params.id))
        if (certificateReqInst.status == 1) {
            showEdit = false
        }
        if (CertificateRequestDetails.findByCertificateRequests(certificateReqInst)) {
            def certificateDetailsInst = CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
            def annexerHInst = AnnexerH.findByCertificateDetails(certificateDetailsInst)
            def studentList=AnnexerHStudents.findAllByCertificateRequests(certificateReqInst).student.unique()
            def refNo = annexerHInst.refNo
            def dateShown = annexerHInst.dateShown
            def fromName = annexerHInst.fromName
            def fromPosition = annexerHInst.fromPosition
            def header = annexerHInst.header
            def paragraph1 = annexerHInst.paragraph1
            def paragraph2 = annexerHInst.paragraph2
              def paragraph3 = annexerHInst.paragraph3
              def paragraph4 = annexerHInst.paragraph4
              def paragraph5 = annexerHInst.paragraph5
            def signatureName = annexerHInst.signatureNameEdit
            def designation=annexerHInst.designation
            def institute=annexerHInst.institute
            def address=annexerHInst.address
            [studentList:studentList,studentSize:studentList.size(),paragraph5:paragraph5,paragraph4:paragraph4,address:address,institute:institute,designation:designation,certificateReqId: certificateReqInst.id, showEdit: showEdit, refNo: refNo, dateShown: dateShown, fromName: fromName, fromPosition: fromPosition, header: header, paragraph1: paragraph1, paragraph2: paragraph2, paragraph3: paragraph3, signatureName: signatureName, fromDB: 'yes']
        } else {
            def studentList=AnnexerHStudents.findAllByCertificateRequests(certificateReqInst).student.unique()
            def applicantName, semester, department, program, male = false, rollNo
            if (certificateReqInst.student.middleName != null) {
                applicantName = certificateReqInst.student.firstName + " " + certificateReqInst.student.middleName + " " + certificateReqInst.student.lastName

            } else {
                applicantName = certificateReqInst.student.firstName + " " + certificateReqInst.student.lastName
            }

            if (certificateReqInst.student.gender == 'Male') {
                male = true
            }
            department = certificateReqInst.student.programBranch.name
            def semesterNo = certificateReqInst.student.semester
            if (semesterNo == 1) {
                semester = 'First'
            } else if (semesterNo == 2) {
                semester = 'Second'
            } else if (semesterNo == 3) {
                semester = 'Third'
            } else if (semesterNo == 4) {
                semester = 'Fourth'
            } else if (semesterNo == 5) {
                semester = 'Fifth'
            } else if (semesterNo == 6) {
                semester = 'Sixth'
            } else if (semesterNo == 7) {
                semester = 'Seventh'
            } else if (semesterNo == 8) {
                semester = 'Eighth'
            } else if (semesterNo == 9) {
                semester = 'Ninth'
            } else if (semesterNo == 10) {
                semester = 'Tenth'
            } else if (semesterNo == 11) {
                semester = 'Eleventh'
            } else if (semesterNo == 12) {
                semester = 'Twelfth'
            }
            rollNo = certificateReqInst.student.rollNo
            program = certificateReqInst.student.program.courseName.replace('(L)', '')
            int year = Calendar.getInstance().get(Calendar.YEAR);
            def refNo = 0
            def refrence = CertificateRequestDetails.createCriteria().get {
                projections {
                    max "referenceNo"
                }
                and {
                    eq('year', year.toString())
                }
            }
            if (refrence != null) {
                refNo = Integer.parseInt(refrence)
            }
            refNo++
            [studentList:studentList,studentSize:studentList.size(),rollNo: rollNo, program: program, semester: semester, showEdit: showEdit, certificateReqId: certificateReqInst.id, year: year, applicantName: applicantName, male: male, department: department, refNo: refNo]
        }
    }
    def otherCertificate() {
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def showEdit = true
        def certificateReqInst = CertificateRequests.findById(Long.parseLong(params.id))
        if (certificateReqInst.status == 1) {
            showEdit = false
        }

        def certificateTemplateInst=CertificatesTemplate.findByCertificate(certificateReqInst.certificate)
        if (CertificateRequestDetails.findByCertificateRequests(certificateReqInst)) {
            def certificateDetailsInst = CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
            def cerDetailsInst = GeneratedCertificateDetails.findByCertificateDetails(certificateDetailsInst)
            def refNo = cerDetailsInst.refNo
            def dateShown = cerDetailsInst.dateShown
            def fromName = cerDetailsInst.fromName
            def fromPosition = cerDetailsInst.fromPosition
            def header = cerDetailsInst.header
            def paragraph1 = cerDetailsInst.paragraph1
            def paragraph2 = cerDetailsInst.paragraph2
            def paragraph3 = cerDetailsInst.paragraph3
            def paragraph4 = cerDetailsInst.paragraph4
            def paragraph5 = cerDetailsInst.paragraph5
            def signatureName = cerDetailsInst.signatureName
            [certificateTemplateInst:certificateTemplateInst,certificateReqId: certificateReqInst.id, showEdit: showEdit, refNo: refNo, dateShown: dateShown, fromName: fromName, fromPosition: fromPosition, header: header, paragraph1: paragraph1, paragraph2: paragraph2, paragraph3: paragraph3,paragraph4: paragraph4, paragraph5: paragraph5, signatureName: signatureName, fromDB: 'yes']
        } else {
            def applicantName, semester, department, program, male = false, rollNo
            int year = Calendar.getInstance().get(Calendar.YEAR);
            def refNo = 0
            def refrence = CertificateRequestDetails.createCriteria().get {
                projections {
                    max "referenceNo"
                }
                and {
                    eq('year', year.toString())
                }
            }
            if (refrence != null) {
                refNo = Integer.parseInt(refrence)
            }
            refNo++
            if(certificateReqInst.student!=null){
                if (certificateReqInst.student.middleName != null) {
                    applicantName = certificateReqInst.student.firstName + " " + certificateReqInst.student.middleName + " " + certificateReqInst.student.lastName

                } else {
                    applicantName = certificateReqInst.student.firstName + " " + certificateReqInst.student.lastName
                }

                if (certificateReqInst.student.gender == 'Male') {
                    male = true
                }
                department = certificateReqInst.student.programBranch.name
                def semesterNo = certificateReqInst.student.semester
                if (semesterNo == 1) {
                    semester = 'First'
                } else if (semesterNo == 2) {
                    semester = 'Second'
                } else if (semesterNo == 3) {
                    semester = 'Third'
                } else if (semesterNo == 4) {
                    semester = 'Fourth'
                } else if (semesterNo == 5) {
                    semester = 'Fifth'
                } else if (semesterNo == 6) {
                    semester = 'Sixth'
                } else if (semesterNo == 7) {
                    semester = 'Seventh'
                } else if (semesterNo == 8) {
                    semester = 'Eighth'
                } else if (semesterNo == 9) {
                    semester = 'Ninth'
                } else if (semesterNo == 10) {
                    semester = 'Tenth'
                } else if (semesterNo == 11) {
                    semester = 'Eleventh'
                } else if (semesterNo == 12) {
                    semester = 'Twelfth'
                }
                rollNo = certificateReqInst.student.rollNo
                program = certificateReqInst.student.program.courseName.replace('(L)', '')
                [certificateTemplateInst:certificateTemplateInst,rollNo: rollNo, program: program, semester: semester, showEdit: showEdit, certificateReqId: certificateReqInst.id, year: year, applicantName: applicantName, male: male, department: department, refNo: refNo]
            }
            else{
                if (certificateReqInst.employee.middleName != null) {
                    if (certificateReqInst.employee.salutation == null || certificateReqInst.employee.salutation == '') {
                        applicantName = certificateReqInst.employee.firstName + " " + certificateReqInst.employee.middleName + " " + certificateReqInst.employee.lastName
                    } else {
                        applicantName = certificateReqInst.employee.salutation + " " + certificateReqInst.employee.firstName + " " + certificateReqInst.employee.middleName + " " + certificateReqInst.employee.lastName
                    }
                } else {
                    if (certificateReqInst.employee.salutation == null || certificateReqInst.employee.salutation == '') {
                        applicantName = certificateReqInst.employee.firstName + " " + certificateReqInst.employee.lastName
                    } else {
                        applicantName = certificateReqInst.employee.salutation + " " + certificateReqInst.employee.firstName + " " + certificateReqInst.employee.lastName
                    }
                }
                if (certificateReqInst.employee.gender == 'Male') {
                    male = true
                }
                department = certificateReqInst.employee.department.name
                [certificateTemplateInst:certificateTemplateInst, showEdit: showEdit, certificateReqId: certificateReqInst.id, year: year, applicantName: applicantName, male: male, department: department, refNo: refNo]
            }
        }
    }
    def saveAnnexerD() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveAnnexerServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveAnnexerDWithoutStatus() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveAnnexerWithoutStatusServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveAnnexerE() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveAnnexerEServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveAnnexerEWithoutStatus() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveAnnexerEWithoutStatusServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveAnnexerB() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveAnnexerBServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def saveOthers() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveOthersServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveAnnexerBWithoutStatus() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveAnnexerBWithoutStatusServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
 def saveOthersWithoutStatus() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveOthersWithoutStatusServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveAnnexerC() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveAnnexerCServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveAnnexerCWithoutStatus() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveAnnexerCWithoutStatusServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveAnnexerA() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveAnnexerAServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveAnnexerAWithoutStatus() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveAnnexerAWithoutStatusServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveAnnexerF() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveAnnexerFServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveAnnexerFWithoutStatus() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveAnnexerFWithoutStatusServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def saveAnnexerG() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveAnnexerGServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveAnnexerGWithoutStatus() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveAnnexerGWithoutStatusServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def saveAnnexerH() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveAnnexerHServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveAnnexerHWithoutStatus() {
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveAnnexerHWithoutStatusServiceMethod(params)
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def checkRollNo(){
        if (springSecurityService.currentUser) {
            def result=[:]
            def studentInst=Student.findByRollNo(params.rollNo)
            if(studentInst!=null){
                result.status=true
            }
            else{
                result.status=false
            }
            render result as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def createCertificate(){
        /*Action for create certificate interface
          provides option for certificate name,User's role(which can apply for certificate),program and semesters */
        if (springSecurityService.currentUser) {

            def roleList=Role.list()
            def programList=ProgramType.list()
            if(params.id){
                def certificateInast=Certificate.findById(Long.parseLong(params.id))
                def certificateRoleList=CertificateRole.findAllByCertificate(certificateInast)
                def certificateProgramList=CertificateProgram.findAllByCertificate(certificateInast)
                [certificateInast:certificateInast,assignedRoleoleList:certificateRoleList.role.id,semesterList:certificateProgramList.semesterNo,programType:certificateProgramList.programType.id,roleList:roleList,programList:programList]
            }
            else{
                [roleList:roleList,programList:programList]
            }

        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def createCertificateTemplate(){
        /*Action for creating template for certificate*/
        if (springSecurityService.currentUser) {

            def certificateList=Certificate.findAllByFormatIsNull()
            if(params.id){
                def certificateInast=Certificate.findById(Long.parseLong(params.id))
                def certificateTemplateInst=CertificatesTemplate.findByCertificate(certificateInast)
                [certificateList: certificateList,certificateTemplateInst:certificateTemplateInst,certificateInast:certificateInast]
            }
            else {
                [certificateList: certificateList]
            }
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def saveNewCertificate(){
        /*Saves certificate details like name, users(role) who can request for certificate, program and semesters*/
        if (springSecurityService.currentUser) {
            def result = certificateInfoService.saveNewCertificate(params)
            if(result){
                flash.message="Saved Successfully."
            }
            else{
                flash.message="Unable to Save Successfully"
            }
            redirect(controller: "certificateTemp", action: "createCertificate")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def saveNewCertificateTemplate(){
        /*Action for saving details of certificate template like logo ,header ,content and signature etc*/
        if (springSecurityService.currentUser) {
            def instituteLogo = request.getFile('instituteLogo')
            def result = certificateInfoService.saveNewCertificateTemplate(params,instituteLogo)
            if(result){
                flash.message="Saved Successfully."
            }
            else{
                flash.message="Unable to Save Successfully"
            }
            redirect(controller: "certificateTemp", action: "createCertificateTemplate")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def listOfCertificate(){
        /*Action for showing list of certificates, their related user roles,format availability ,template availability etc*/
        if (springSecurityService.currentUser) {
            def certificateList=[],roleList=[],templateList=[],tempAvailable=[],certificateTemplateId=[]
            def certificate=Certificate.list()
            certificate.each {
                certificateList<<it
                if(it.format!=null){
                    tempAvailable<<'N'
                }
                else{
                    tempAvailable<<'Y'
                }
                roleList<<CertificateRole.findAllByCertificate(it)
                def cerTempInst=CertificatesTemplate.findByCertificate(it)
                println("=================================="+cerTempInst)
                if(cerTempInst){
                    certificateTemplateId<<cerTempInst.id
                    templateList<<it.id
                }
                else{
                    templateList<<'NA'
                    certificateTemplateId<<'NA'
                }
            }
            [certificateList:certificateList,roleList:roleList,certificateTemplateId:certificateTemplateId,templateList:templateList,tempAvailable:tempAvailable]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def show = {
        def id = Integer.parseInt(params.id)
        def something = CertificatesTemplate.get(id)
        byte[] image = something.logoImage
        response.setContentType(params.mime)
        response.outputStream << image
    }
    /*Shows list of Certificates*/
    def listOfCertificatesAvailable={
        if (springSecurityService.currentUser) {
            def certificateList=Certificate.list(sort: 'nameOfCertificate')
            [certificateList:certificateList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    /*Deletes Certificate template*/
    def deleteCertificate(){
        if (springSecurityService.currentUser) {
            def certificateInst=CertificatesTemplate.findById(Long.parseLong(params.id))
            certificateInst.delete(flush: true)
            if(CertificatesTemplate.exists(certificateInst.id)){
                flash.message="Certificate Template Deleted Successfully"
            }
            else{
                flash.message="Unable to Delete Certificate Template Successfully"
            }
            redirect(controller: "certificateTemp", action: "listOfCertificate")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
}
