package universityproject

import IST.ProgramBranch
import com.university.Role
import com.university.TempLogin
import com.university.UserRole
import examinationproject.*
import grails.transaction.Transactional
import groovy.transform.Synchronized

import java.security.SecureRandom
import java.text.DateFormat
import java.text.SimpleDateFormat
import com.university.User
import grails.plugin.mail.*

import static com.university.Role.*

@Transactional
class StudentRegistrationService {
    private final myLock = new Object()
    private final challanLock = new Object()
    def springSecurityService
    def mailService
    def springSecurityUtils

    @Synchronized("myLock")
    Student saveNewStudentRegistration(params, signature, photographe) {
        /*method for saving student registration details*/
        Boolean studentRegistrationInsSaved = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year
        String year = sdf.format(Calendar.getInstance().getTime());
        def startYear = year
        def endYear
        def programSession
        def studentRegistration
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy")
        if (params.studentId) {
            studentRegistration = Student.findById(Long.parseLong(params.studentId))
            studentRegistration.firstName = params.firstName.toUpperCase()
            studentRegistration.lastName = params.lastName.toUpperCase()
            studentRegistration.middleName = params.middleName.toUpperCase()
            studentRegistration.fatherName = params.fatherName.toUpperCase()
            studentRegistration.motherName = params.motherName.toUpperCase()
            studentRegistration.guardianName = params.guardianName.toUpperCase()
            studentRegistration.gender = params.gender
            studentRegistration.dob = df.parse(params.dob)
            if (params.categoryCA) {
                studentRegistration.isExtraCurricularActivity = true
            }
//            if (params.isPartTime) {
//                if(params.isPartTime=='partTime')
//                studentRegistration.isPartTime=true
//            }
            if (params.categoryGU) {
                studentRegistration.isWardOfGuEmployee = true
            }
            if (params.categoryNe) {
                studentRegistration.isNEStudent = true
            }
            if (params.categoryFV) {
                studentRegistration.isFringeVillage = true
            }
            if (params.categoryDA) {
                studentRegistration.isPH = true
            }
            if (params.Country) {
                if (params.Country != '')
                    studentRegistration.nationalityName = params.Country
                else{
                    studentRegistration.nationalityName = null
                }
            }
            else{
                studentRegistration.nationalityName = null
            }

            studentRegistration.category = params.category
            studentRegistration.program = ProgramDetail.findById(Long.parseLong(params.programId))
/*---------------------------------------------------------*/
            def progType = ProgramType.findById(Long.parseLong(params.programType))
            def progBranch = ProgramBranch.findById(Long.parseLong(params.programBranch))
            studentRegistration.programType = progType
            studentRegistration.programBranch = progBranch
/*---------------------------------------------------------*/
            studentRegistration.email = params.email
            studentRegistration.bloodGroup = params.bloodGroup
            studentRegistration.guardianMobileNo1 = Long.parseLong(params.guardianMobileNo1)
            studentRegistration.guardianMobileNo2 = Long.parseLong(params.guardianMobileNo2)
            studentRegistration.permanent_Address = params.permanent_Address
            studentRegistration.mailing_Address = params.mailing_Address
            studentRegistration.permanentAddressDistrict = params.permanentAddressDistrict
            studentRegistration.permanentStateAddress = params.permanentStateAddress
            studentRegistration.mailingAddressDistrict = params.mailingAddressDistrict
            studentRegistration.mailingStateAddress = params.mailingStateAddress
            studentRegistration.permanentPincode = params.permanentPincode
            studentRegistration.permanentMobileNo = params.permanentMobileNo
            studentRegistration.mailingPincode = params.mailingPincode
            studentRegistration.mailingMobileNo = params.mailingMobileNo
            studentRegistration.school10th = params.School10th
            studentRegistration.subject10th = params.Subject10th
            studentRegistration.year10th = params.Year10th
            studentRegistration.year12th = params.Year12th
            studentRegistration.div10th = params.Div10th
            studentRegistration.percntge10th = params.Percntge10th
            studentRegistration.school12th = params.School12th
            studentRegistration.subject12th = params.Subject12th
            studentRegistration.div12th = params.Div12th
            studentRegistration.percntge12th = params.Percntge12th
            studentRegistration.diplomaSchool = params.diplomaSchool
            studentRegistration.diplomaSubject = params.diplomaSubject
            studentRegistration.diplomaYear = params.diplomaYear
            studentRegistration.diplomaPercntge = params.diplomaPercntge
            studentRegistration.degreeSchool = params.degreeSchool
            studentRegistration.degreeSubject = params.degreeSubject
            studentRegistration.degreeYear = params.degreeYear
            studentRegistration.degreeDiv = params.degreeDiv
            studentRegistration.degreePercntge = params.degreePercntge
            studentRegistration.otherSchool = params.otherSchool
            studentRegistration.otherSubject = params.otherSubject
            studentRegistration.otherYear = params.otherYear
            studentRegistration.otherDiv = params.otherDiv
            studentRegistration.otherPercntge = params.otherPercntge
            studentRegistration.gateSubject = params.gateSubject
            studentRegistration.gateYear = params.gateYear
            studentRegistration.gateDiv = params.gateDiv
            studentRegistration.gatePercntge = params.gatePercntge
            studentRegistration.expDetails = params.expDetails
            studentRegistration.ceeScore = params.ceeScore
            if (photographe.bytes)
                studentRegistration.studentImage = photographe.bytes
            studentRegistration.mobileNo = Long.parseLong(params.mobileNo)
            studentRegistration.nationality = params.nationality
            studentRegistration.state = params.state
            if (studentRegistration.status.id == 5) {
                studentRegistration.status = Status.findById(1)
                studentRegistration.reasonForRejection = null
            }
            if (params.rollNo) {
                def studentToBeUpdate
                try {
                    studentToBeUpdate = Student.findByRollNo(params.rollNo)
                } catch (Exception e) {
                    println("hello" + e.printStackTrace())
                }
            }
            if (springSecurityService.isLoggedIn()) {
                def userDetails = springSecurityService.principal.getAuthorities()
                boolean isAdmin = false
                for (def role in userDetails) {
                    if (role.getAuthority() == "ROLE_ADMIN") //do something }
                        if (role.getAuthority() == "ROLE_ADMIN") {
                            isAdmin = true
                        }
                }
            }
            if (studentRegistration.save(flush: true, failOnError: true)) {
                return studentRegistration
            } else {
                return null
            }
        } else {
            /*for new student registration*/
            studentRegistration = new Student(params)
            studentRegistration.firstName = params.firstName.toUpperCase()
            studentRegistration.lastName = params.lastName.toUpperCase()
            studentRegistration.middleName = params.middleName.toUpperCase()
            studentRegistration.fatherName = params.fatherName.toUpperCase()
            studentRegistration.motherName = params.motherName.toUpperCase()
            studentRegistration.guardianName = params.guardianName.toUpperCase()
            studentRegistration.school10th = params.School10th
            studentRegistration.subject10th = params.Subject10th
            studentRegistration.year10th = params.Year10th
            studentRegistration.year12th = params.Year12th
            studentRegistration.div10th = params.Div10th
            studentRegistration.percntge10th = params.Percntge10th
            studentRegistration.school12th = params.School12th
            studentRegistration.subject12th = params.Subject12th
            studentRegistration.div12th = params.Div12th
            studentRegistration.percntge12th = params.Percntge12th
            studentRegistration.rollNo = springSecurityService.getCurrentUser().getUsername()
//            if (params.isPartTime) {
//                if(params.isPartTime=='partTime')
//                    studentRegistration.isPartTime=true
//            }
            if (params.categoryNe) {
                studentRegistration.isNEStudent = true
            }
            if (params.categoryCA) {
                studentRegistration.isExtraCurricularActivity = true
            }
            if (params.categoryGU) {
                studentRegistration.isWardOfGuEmployee = true
            }
            if (params.categoryFV) {
                studentRegistration.isFringeVillage = true
            }
            if (params.categoryDA) {
                studentRegistration.isPH = true
            }
            if (params.Country) {
                if (params.Country != '')
                    studentRegistration.nationalityName = params.Country
                else{
                    studentRegistration.nationalityName = null
                }
            }
            else{
                studentRegistration.nationalityName = null
            }
            studentRegistration.registrationYear = Integer.parseInt("20" + params.rollNo.substring(0, 2))
            if (springSecurityService.isLoggedIn()) {
                studentRegistration.referenceNumber = 0
                studentRegistration.status = Status.findById(1)
                def progDetail = ProgramDetail.findById(Long.parseLong(params.programId))
                studentRegistration.program = progDetail
                def progType = ProgramType.findById(Long.parseLong(params.programType))
                def progBranch = ProgramBranch.findById(Long.parseLong(params.programBranch))
                studentRegistration.programType = progType
                studentRegistration.programBranch = progBranch
                def userInst = User.findByUsername(springSecurityService.getCurrentUser().getUsername())
                userInst.department = progBranch.department
                userInst.email = params.email
            }
            studentRegistration.dob = df.parse(params.dob)
            if (photographe.bytes)
                studentRegistration.studentImage = photographe.bytes

            studentRegistration.semester = 0
            studentRegistration.admitCardGenerated = false
            if (studentRegistration.save(flush: true, failOnError: true)) {
                return studentRegistration
            } else {
                return null
            }
        }

    }

    def getStudentReferenceNumber() {
        /* Assign a string that contains the set of characters you allow. */
        String symbols = "123456789987654321";
        Random random = new SecureRandom();
        char[] buf;
        buf = new char[6];
        def bufLength = buf.length
        for (int idx = 0; idx < bufLength; idx++)
            buf[idx] = symbols.charAt(random.nextInt(symbols.length()));
        if (Student.count() > 0) {
            if (!Student.findByReferenceNumber(new String(buf))) {
                return new String(buf);
            } else {
                getStudentReferenceNumber()
            }
        } else {
            return new String(buf)
        }
    }

    def approvedStudents(params) {
        def studentIdList = params.studentList.split(",")
        studentIdList.each { i ->
            def stuObj = Student.findById(Long.parseLong(i.toString()))
            stuObj.status = Status.findById(Long.parseLong("2"))
            stuObj.save(failOnError: true)
        }
    }

    def seedStudent(params) {
        def students
        Set<ProgramDetail> programDetails = ProgramDetail.findAllById(23)

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year
        int year = Integer.parseInt(sdf.format(Calendar.getInstance().getTime()))
        for (int i = 250; i < 750; i++) {
//            println("Student Number is " + i)
            students = new Student()
            students.firstName = "StudentAtIDOL" + i
            students.lastName = "Test"
            students.gender = "Male"
            students.category = "GEN"
            students.programSession = ProgramSession.get(23)
            students.referenceNumber = getStudentReferenceNumber()
            //students.challanNo = getChallanNumber()
            students.dob = new Date()
            students.admissionDate = new Date()
            students.programDetail = programDetails
            students.status = Status.findById(1)
//            students.studyCentre = StudyCenter.findAllById(1)
            students.admitCardGenerated = false
            students.semester = 1
            students.city = City.findAllById(8)
            students.programDetail = programDetails
            students.registrationYear = year
            try {
                students.save(flush: true, failOnError: true)
            } catch (Exception e) {
                println("????????" + e.printStackTrace())
            }
        }
    }

    static int a = 0

    @Synchronized("challanLock")
//    def getChallanNumber() {
//        int serialNo = 1
//        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd"); // Just the year
//        def date = (sdf.format(Calendar.getInstance().getTime()))
//        def challan = date.replaceAll("/", "")
//        def challanSr
//        def length
//        def challanNo
//        def returnChallanNo
//        def studentByChallanNo
//        if (Student.count() > 0) {
//            def stdObj = Student.createCriteria()
//            def mscObj = FeeDetails.createCriteria()
//            def feeDetailByChallanNo = mscObj.list {
//                maxResults(1)
//                order("id", "desc")
//            }
//            def custObj = CustomChallan.createCriteria()
//            def custByChallanNo = custObj.list {
//                maxResults(1)
//                order("id", "desc")
//            }
//
//            def envChallanInst = ExistingChallan.findBySession(2014)
//            if (feeDetailByChallanNo) {
//                if (custByChallanNo) {
//                    if (envChallanInst) {
//                        if ((Integer.parseInt(envChallanInst.challan) > Integer.parseInt(feeDetailByChallanNo[0].challanNo)) && (Integer.parseInt(feeDetailByChallanNo[0].challanNo) > Integer.parseInt(custByChallanNo[0].challanNo))) {
//                            studentByChallanNo = envChallanInst.challan
//                        } else if ((Integer.parseInt(envChallanInst.challan) < Integer.parseInt(feeDetailByChallanNo[0].challanNo)) && (Integer.parseInt(feeDetailByChallanNo[0].challanNo) < Integer.parseInt(custByChallanNo[0].challanNo))) {
//                            studentByChallanNo = custByChallanNo[0].challanNo
//                        } else {
//                            studentByChallanNo = feeDetailByChallanNo[0].challanNo
//                        }
//                    } else {
//                        if (Integer.parseInt(feeDetailByChallanNo[0].challanNo) > Integer.parseInt(custByChallanNo[0].challanNo)) {
//                            studentByChallanNo = feeDetailByChallanNo[0].challanNo
//                        } else {
//                            studentByChallanNo = custByChallanNo[0].challanNo
//                        }
//                    }
//                } else {
//                    studentByChallanNo = feeDetailByChallanNo[0].challanNo
//                }
//
//            }
//
//            def lastChallanDate
//            if (studentByChallanNo) {
//                if (studentByChallanNo != null) {
//                    lastChallanDate = studentByChallanNo.substring(0, 6)
//
//                    if (lastChallanDate.equalsIgnoreCase(challan)) {
//                        serialNo = Integer.parseInt(studentByChallanNo.substring(6, 10))
//                        serialNo = serialNo + 1
//
//                    } else {
//                        serialNo = 1
//                    }
//                } else {
//                    serialNo = 1
//                }
//
//            } else {
//                serialNo = 1
//            }
//            length = serialNo.toString().length()
//            switch (length) {
//                case 1:
//                    challanSr = "000" + serialNo.toString()
//                    break;
//                case 2:
//                    challanSr = "00" + serialNo.toString()
//                    break;
//                case 3:
//                    challanSr = "0" + serialNo.toString()
//                    break;
//                default:
//                    challanSr = serialNo.toString()
//            }
//            challanNo = challan + challanSr
//
//        } else {
//            challanSr = "000" + serialNo.toString()
//            challanNo = challan + challanSr
//        }
//        def existingChallan = ExistingChallan.findByChallan(challanNo)
//
//        if (existingChallan) {
//            challanNo = Integer.parseInt(challanNo) + 1
//        }
//
//        def existingChallanInst
//        if (ExistingChallan.findBySession(2014)) {
//            existingChallanInst = ExistingChallan.findBySession(2014)
//            def existingDate = existingChallanInst.challan.substring(0, 6)
//            def existingChallanSr = existingChallanInst.challan.substring(6, 10)
//            if ((Integer.parseInt(existingDate) == Integer.parseInt(challan)) && (Integer.parseInt(challanSr) < Integer.parseInt(existingChallanSr))) {
//                challanNo = Integer.parseInt(existingChallanInst.challan) + 1
//            }
//            existingChallanInst.challan = challanNo
//            returnChallanNo = challanNo
//        } else {
//            existingChallanInst = new ExistingChallan()
//            existingChallanInst.challan = challanNo
//            existingChallanInst.session = 2014
//            if (existingChallanInst.save(flush: true, failOnError: true)) {
//                returnChallanNo = challanNo
//            }
//        }
//        return returnChallanNo
//    }

//    def saveCChallan(params) {
//        def resultMap = [:]
//        def status = false
//        def challanInst = new CustomChallan(params)
//        def challanNo = getChallanNumber()
//        challanInst.challanNo = challanNo
//        challanInst.challanDate = new Date()
//        if (challanInst.save(flush: true, failOnError: true)) {
//            status = true
//        }
//        resultMap.status = status
//        resultMap.challanNo = challanNo
//        return resultMap
//    }

    def getAdmissionFeeAmount(studentRegistration) {
        def infoMap = [:]
        def student = Student.findByRollNo(studentRegistration.rollNo)
//            println("program"+student.programDetail)
        def program = student.programDetail
        def feeTypeId
        def feeType = null
        def args
        def lateFee = 0
        def programFeeAmount = 0

        Calendar cal = Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        def sessionVal = year + 1
        sessionVal = year + '-' + sessionVal

        def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(student.programDetail[0].id), sessionVal)
        def programFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, 1)
        try {
            def lateFeeDate = student.programDetail.lateFeeDate[0]
            def today = new Date()
            if (lateFeeDate != null) {
                if (today.compareTo(lateFeeDate) > 0) {

                    lateFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, 1).lateFeeAmount

                }
            }
            feeType = null
            programFeeAmount = programFee.feeAmountAtIDOL + lateFee
        } catch (Exception e) {
            println("this exception occurred here " + e)
            flash.message = "Late Fee Date is not assigned! "
            redirect(controller: student, action: enrollmentAtIdol)

        }
    }

    def deleteStudentWithDetails(params) {
        def studentInst = Student.findById(Long.parseLong(params.studentId))
        def status = true
        if ((studentInst.status.id != 3) && (studentInst.status.id != 4)) {
            def feeDetailInst = FeeDetails.findAllByStudent(studentInst)
            if (feeDetailInst) {
                feeDetailInst.each {
                    it.delete(flush: true, failOnError: true)
                    if (FeeDetails.exists(it.id)) {
                        status = false
                    }
                }
            }
            def studyCenterId = studentInst.studyCentre[0].id
            def cityId = studentInst.city[0].id
            def programId = studentInst.programDetail[0].id
            if (status) {
                City.findById(cityId).removeFromStudent(studentInst)
//                StudyCenter.findById(studyCenterId).removeFromStudent(studentInst)
                ProgramDetail.findById(programId).removeFromStudent(studentInst)
            }
            if (status) {
                studentInst.delete(flush: true, failOnError: true)
                if (Student.exists(studentInst.id)) {
                    status = false
                }
            }
        } else {
            status = false
        }
        return status
    }

    def saveStudentSubjects(params) {
        def result
        def studentInst = Student.findById(Long.parseLong(params.studentID))
        def progSession = studentInst.programSession
        def semInst = Semester.findById(Long.parseLong(params.semester))
        def status = Status.findById(1)
        def existingStudSubjectList = StudentSubject.findAllByProgramSessionAndStudentAndSemesterAndStatusNotEqual(progSession, studentInst, semInst, Status.findById(4))
        if (existingStudSubjectList) {
            existingStudSubjectList.each {
                it.delete()
            }
        }
        if ((params.subjects).getClass() == String) {
            def subjectInst = Subject.findById(Long.parseLong(params.subjects))
            def subjectSession = SubjectSession.findBySubjectId(subjectInst)
            def stuSubInst = new StudentSubject()
            stuSubInst.programSession = progSession
            stuSubInst.student = studentInst
            stuSubInst.semester = semInst
            stuSubInst.subjectSession = subjectSession
            stuSubInst.status = status
            if (stuSubInst.save(flush: true, failOnError: true)) {
                result = true
            } else {
                result = false
            }
        } else {
            params.subjects.each {


                def subjectInst = Subject.findById(Long.parseLong(it))
                def subjectSession = SubjectSession.findBySubjectId(subjectInst)
                def stuSubInst = new StudentSubject()
                stuSubInst.programSession = progSession
                stuSubInst.student = studentInst
                stuSubInst.semester = semInst
                stuSubInst.subjectSession = subjectSession
                stuSubInst.status = status
                if (stuSubInst.save(flush: true, failOnError: true)) {
                    result = true
                } else {
                    result = false
                }
            }

        }
        return result


    }

    def saveSemesterRegistration(params) {
        /*Service method for saving semester registration details for particular student*/
        def studentCourseList = []
        def saveStatus = true
        def studentIns = Student.findById(Integer.parseInt(params.studentId))
        def programSession = ProgramSession.findById(studentIns.program.id)
        def semester = Semester.findBySemesterNoAndProgramSession(Integer.parseInt(params.studentSemester), programSession)
        def regPeriod = EnrollmentPeriod.findByProgramDetailAndSemesterAndStudentAdmissionYear(studentIns.program, semester, studentIns.registrationYear)
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy")
        studentIns.semester = Integer.parseInt(params.studentSemester)
        studentIns.semesterRegistration = 1
        studentIns.semesterRejectionReason = null
        if (params.permanentAddressDistrict) {
            studentIns.guardianName = params.guardianName
            studentIns.email = params.email
            if (params.stayInHostel == 'Yes') {
                studentIns.stayInHostel = true
                studentIns.hostelName = params.hostelName
                studentIns.hostelRoomNo = params.hostelRoomNo
            }
            studentIns.permanent_Address = params.permanent_Address
            studentIns.mailing_Address = params.mailing_Address
            studentIns.permanentAddressDistrict = params.permanentAddressDistrict
            studentIns.mailingAddressDistrict = params.mailingAddressDistrict
            studentIns.permanentStateAddress = params.permanentStateAddress
            studentIns.mailingStateAddress = params.mailingStateAddress
            studentIns.permanentPincode = params.permanentPincode
            studentIns.permanentMobileNo = "+91" + params.permanentMobileNo
            studentIns.mailingPincode = params.mailingPincode
            studentIns.mailingMobileNo = "+91" + params.mailingMobileNo

        }
        if (studentIns.save(flush: true, failOnError: true)) {

        } else {
            saveStatus = false
        }
        def provStatus = Status.findById(1)
        def semFeeDetailsInst = SemesterFeeDetails.findAllByProgramDetailAndAcademicSessionAndSemesterAndStudentAndStatus(studentIns.program, params.session, semester, studentIns, provStatus)
        if (semFeeDetailsInst) {

        } else {
            if (params.guReceiptNo.getClass() == String) {
                semFeeDetailsInst = new SemesterFeeDetails()
                semFeeDetailsInst.student = studentIns
                semFeeDetailsInst.semester = semester
                semFeeDetailsInst.programDetail = studentIns.program
                semFeeDetailsInst.academicSession = params.session
                semFeeDetailsInst.guReceiptAmount = params.guReceiptAmount
                semFeeDetailsInst.guReceiptDate = df.parse(params.guReceiptDate.toString())
                semFeeDetailsInst.guReceiptNo = params.guReceiptNo
                semFeeDetailsInst.status = provStatus
                if (semFeeDetailsInst.save(flush: true, failOnError: true)) {

                } else {
                    saveStatus = false
                }
            } else {
                for (def i = 0; i < params.guReceiptNo.length; i++) {

                    if (params.guReceiptNo[i] != '') {
                        semFeeDetailsInst = new SemesterFeeDetails()
                        semFeeDetailsInst.student = studentIns
                        semFeeDetailsInst.semester = semester
                        semFeeDetailsInst.programDetail = studentIns.program
                        semFeeDetailsInst.academicSession = params.session
                        semFeeDetailsInst.guReceiptAmount = params.guReceiptAmount[i]
                        semFeeDetailsInst.guReceiptDate = df.parse(params.guReceiptDate[i].toString())
                        semFeeDetailsInst.guReceiptNo = params.guReceiptNo[i]
                        semFeeDetailsInst.status = provStatus
                        if (semFeeDetailsInst.save(flush: true, failOnError: true)) {
                        } else {
                            saveStatus = false
                        }
                    }

                }
            }
        }

        if (params.studentSubjects) {
            if (params.studentSubjects.getClass() != String) {
                for (int i = 0; i < params.studentSubjects.length; i++) {
                    def subjectSessionId = SubjectSession.findById(Long.parseLong(params.studentSubjects[i]))
                    def courseList = CourseSubject.findByProgramSessionAndSemesterAndSubjectSessionId(programSession, semester, subjectSessionId)
                    if (courseList == null) {
                        courseList = CourseSubject.findByProgramSessionAndSubjectSessionId(programSession, subjectSessionId)
                    }
                    studentCourseList.add(courseList)
                }
            } else {
                def subjectSessionId = SubjectSession.findById(Long.parseLong(params.studentSubjects))
                def courseList = CourseSubject.findByProgramSessionAndSemesterAndSubjectSessionId(programSession, semester, subjectSessionId)
                studentCourseList.add(courseList)
            }
        }
        if (studentCourseList.size() > 0) {
            for (int i = 0; i < studentCourseList.size(); i++) {
                def studentSubject = new StudentSubject();
                studentSubject.student = studentIns;
                studentSubject.programSession = programSession;
                studentSubject.semester = semester
                studentSubject.status = Status.findById(1)
                studentSubject.registrationDate = df.parse(df.format(new Date()))
                studentSubject.academicSession = regPeriod.admissionSession
                studentSubject.subjectSession = studentCourseList[i].subjectSessionId
                if (studentSubject.save(flush: true, failOnError: true)) {

                } else {
                    saveStatus = false
                }
            }
        }

        studentCourseList = []
        if (params.studentExtraSubjects) {
            def semester1 = []
            def startSem = programSession.programDetailId.admissionTerm
            if (startSem != Integer.parseInt(params.studentSemester)) {
                if (Integer.parseInt(params.studentSemester) % 2 == 0) {
                    for (def i = startSem; i < Integer.parseInt(params.studentSemester); i++) {
                        if (i % 2 == 0) {
                            semester1 << Semester.findBySemesterNoAndProgramSession(i, programSession)
                        }
                    }
                } else {
                    for (def i = startSem; i < Integer.parseInt(params.studentSemester); i++) {
                        if (i % 2 != 0) {
                            semester1 << Semester.findBySemesterNoAndProgramSession(i, programSession)
                        }
                    }
                }
            }
            if (params.studentExtraSubjects.getClass() != String) {
//                def semester1 = Semester.findAllBySemesterNoLessThanAndProgramSession(Integer.parseInt(params.studentSemester), programSession)
                for (int i = 0; i < params.studentExtraSubjects.length; i++) {
                    def subjectSessionId = SubjectSession.findById(Long.parseLong(params.studentExtraSubjects[i]))
                    def courseList = CourseSubject.findAllByProgramSessionAndSemesterInListAndSubjectSessionId(programSession, semester1, subjectSessionId)
                    studentCourseList.add(courseList[0])
                }
            } else {
//                def semester1 = Semester.findAllBySemesterNoLessThanAndProgramSession(Integer.parseInt(params.studentSemester), programSession)
                def subjectSessionId = SubjectSession.findById(Long.parseLong(params.studentExtraSubjects))
                def courseList = CourseSubject.findAllByProgramSessionAndSemesterInListAndSubjectSessionId(programSession, semester1, subjectSessionId)
                studentCourseList.add(courseList[0])
            }
        }
        for (int i = 0; i < studentCourseList.size(); i++) {
            def studentSubject = new StudentSubject();
            studentSubject.student = studentIns;
            studentSubject.programSession = programSession;
            studentSubject.semester = semester
            studentSubject.status = Status.findById(1)
            studentSubject.registrationDate = df.parse(df.format(new Date()))
            studentSubject.academicSession = regPeriod.admissionSession
            studentSubject.subjectSession = studentCourseList[i].subjectSessionId
            if (studentSubject.save(flush: true, failOnError: true)) {

            } else {
                saveStatus = false
            }
        }
        return saveStatus
    }
    def saveProjectEnrollment(params){
        /*This method saves or submit project details for particular student*/
        def status=true
        def stuInst=Student.findByRollNo(params.rollNo)
        def branchInst=ProgramBranch.findById(Long.parseLong(params.branchId))
        def statusInstUn=Status.findById(1)
        def subjectCodeList=[],projectTitleList=[],domainList=[],nameOfInstitutionList=[],guideNameList=[],designationList=[],phnoGuideList=[],emailGuideList=[]
        if(params.projectTitle.getClass()==String){
            projectTitleList<<params.projectTitle
            subjectCodeList<<params.subjectCode
            domainList<<params.domain
            nameOfInstitutionList<<params.nameOfInstitution
            guideNameList<<params.guideName
        }
        else{
            params.projectTitle.eachWithIndex{ entry, i ->
                projectTitleList<<entry
                domainList<<params.domain[i]
                nameOfInstitutionList<<params.nameOfInstitution[i]
                guideNameList<<params.guideName[i]
                designationList<<params.designation[i]
                phnoGuideList<<params.phnoGuide[i]
                emailGuideList<<params.emailGuide[i]
                subjectCodeList<<params.subjectCode[i]
            }
        }
        projectTitleList.eachWithIndex {itr, i ->
            def subInst=Subject.findBySubjectCode(subjectCodeList[i])
            def projectDetailsInst=new ProjectDetails()
            projectDetailsInst.academicSession=params.session
            projectDetailsInst.student=stuInst
            projectDetailsInst.department=branchInst.department
            projectDetailsInst.projectTitle=itr
            projectDetailsInst.domain=params.domain
            projectDetailsInst.guideId=guideNameList[i]
            projectDetailsInst.subject=subInst
            projectDetailsInst.nameOfInstitution=nameOfInstitutionList[i]
            projectDetailsInst.status=statusInstUn
            if(projectDetailsInst.save(flush: true)){}
            else{
                status=false
            }
        }
        return status
    }

    def createRollNo(params){
//        def status = true
        boolean flag = false
        println("#################"+params)
        def firstTwoDigit = params.year.substring(2,4)
        println("#################"+firstTwoDigit)
        def secondFourDigit = ProgramDetail.findById(Long.parseLong(params.programs)).courseCode
        println("#################"+secondFourDigit)
        def serialNo = "001"
        def rollNo = firstTwoDigit+secondFourDigit+serialNo

        while(flag.equals(false)){

        println("+++++++++++"+rollNo)
        def atmp = TempLogin.findByRollNo(rollNo)
        if (atmp) {
            def temproll = Long.parseLong(rollNo) + 1;
//            temproll++;
            println("QQQQQQQ"+temproll)
            rollNo = temproll.toString();


        }
            else{
              flag = true
        }

        }

        def tmp = new TempLogin()
        println("WWWWWWWWWWWWWWWWWW"+rollNo)
        tmp.rollNo = rollNo
        tmp.password =rollNo

        def studentRole = Role.findByAuthority('ROLE_STUDENT') ?: new Role(authority: 'ROLE_STUDENT').save(failOnError: true)
//            /*create new user*/

        def studentUser = User.findByUsername(rollNo) ?: new User(
                username: rollNo,
                password: rollNo,
                email: "abc@gmail.com",
                upDifferent: true,
                enabled: true,
        ).save(flush: true, failOnError: true)
        if (!studentUser.authorities.contains(studentRole)) {
            UserRole.create studentUser, studentRole
        }

      tmp.save(flush: true, failOnError: true)
           if(tmp){
           return rollNo
       }
        else{

       }
    }
}



