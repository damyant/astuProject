package examinationproject

import certificate.CertificateRole
import certificate.CertificateRequests
import IST.Employee
import IST.ProgramBranch
import com.university.Role
import com.university.User
import com.university.UserRole
import IST.Department
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import java.text.DateFormat
import java.text.SimpleDateFormat

class HodController {
    def springSecurityService
    def pdfRenderingService

    def addHod() {
        def depList = []
        depList = Department.list(sort: 'name')
        [depList: depList]

    }

    def saveHod() {
        if (springSecurityService.currentUser) {
            if (params.password == params.cnfpassword) {
                def head = new Hod()
                head.name = params.name
                head.email = params.email
                def department = Department.findById(Long.parseLong(params.department))
                head.department = department
                head.save(flush: true, failOnError: true)
                def hodRole = Role.findByAuthority('ROLE_HOD') ?: new Role(authority: 'ROLE_HOD').save(failOnError: true)
                /*create new user*/

                def hodUser = User.findByUsername("${params.username}") ?: new User(
                        username: params.username,
                        password: params.password,
                        email: params.email,
                        department: department,
                        enabled: true,
                ).save(flush: true, failOnError: true)

                /*Assign a user role*/

                if (!hodUser.authorities.contains(hodRole)) {
                    UserRole.create hodUser, hodRole
                }

                if (head.save(flush: true, failOnError: true)) {
                    flash.message = "Saved Successfully"
                } else {
                    flash.message = "Not Saved"
                }

            } else {
                flash.message = "Password and Confirm Password are not same"
            }
            redirect(controller: "hod", action: "addHod")
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    def viewHod() {
        if (springSecurityService.currentUser) {
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def addFacultyAdvisor() {
        if (springSecurityService.currentUser) {
            def depList = []
            depList = Department.list(sort: 'name')
            [depList: depList]

        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    def saveFacultyAdvisor() {
        if (springSecurityService.currentUser) {
            if (params.password == params.cnfpassword) {
                def faculty = new FacultyAdvisor()
                faculty.name = params.name
                faculty.email = params.email
                faculty.department = Department.findById(Long.parseLong(params.department))
                def facultyAdvisorRole = Role.findByAuthority('ROLE_FACULTYADVISOR') ?: new Role(authority: 'ROLE_FACULTYADVISOR').save(failOnError: true)
                /*create new user*/

                def facultyAdvisorUser = User.findByUsername("${params.username}") ?: new User(
                        username: params.username,
                        password: params.password,
                        email: params.email,
                        enabled: true,
                        department: Department.findById(Long.parseLong(params.department))
                ).save(flush: true, failOnError: true)

                /*Assign a user role*/

                if (!facultyAdvisorUser.authorities.contains(facultyAdvisorRole)) {
                    UserRole.create facultyAdvisorUser, facultyAdvisorRole
                }

                if (faculty.save(flush: true, failOnError: true)) {
                    flash.message = "Saved Successfully"
                } else {
                    flash.message = "Not Saved"
                }

            } else {
                flash.message = "Password and Confirm Password are not same"
            }
            redirect(controller: "hod", action: "addFacultyAdvisor")

        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    def stuList() {
        /*Action for showing list of students pending for approval & view student details*/
        if (springSecurityService.currentUser) {
            def currentUser = springSecurityService.getCurrentUser()
            if (springSecurityService.getCurrentUser()) {
                def studentList = []
                def studentListTotal = 0
                def roleList = UserRole.findAllByUser(currentUser).role
                def isAdmin = false
                roleList.each {
                    if (it == Role.findByAuthority('ROLE_ADMIN')) {
                        isAdmin = true
                    }
                }
                if (isAdmin) {
                    def status = Status.findById(1)
                    studentList = Student.findAllByStatus(status, [sort: "rollNo", order: "asc"])
                } else {
                    def currentUserEmployeeCode = springSecurityService.getCurrentUser().employeeCode
                    def currentUserDepartment = Employee.findByEmployeeCode(currentUserEmployeeCode).department
                    def status = Status.findById(1)
                    studentList = Student.findAllByProgramBranchInListAndStatus(ProgramBranch.findAllByDepartment(currentUserDepartment), status, [sort: "rollNo", order: "asc"])
                }
                [studentList: studentList]
            }
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    @Secured(["ROLE_ADMIN", "ROLE_FACULTYADVISOR", "ROLE_HOD"])
    def studentListForSemesterRegistration = {
        if (springSecurityService.currentUser) {
            def status = true
//            params.max = Math.min(params.max ? params.int('max') : 20, 100)
            def studentList = []
            def studentListTotal = 0
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
                studentList = Student.findAllBySemesterRegistration(1, [sort: "rollNo", order: "asc"])
            } else {
                def currentUserEmployeeCode = springSecurityService.getCurrentUser().employeeCode
                def currentUserDepartment = Employee.findByEmployeeCode(currentUserEmployeeCode).department

                if (isFacultyAdvisor) {
                    def programList = FacultyAdvisor.findAllByUser(springSecurityService.getCurrentUser()).programDetail.unique()
                    def facultyAdvisorInst = FacultyAdvisor.findAllByUser(springSecurityService.getCurrentUser())
                    if (programList) {
                        def stuIdLIst = []
                        def statusInst = Status.findById(1)
                        facultyAdvisorInst.each {
                            def academic = it.academicSession
                            def semInst = it.semester
                            def progInstInst = it.programDetail
                            def studentsList = StudentSubject.findAllByAcademicSessionAndSemesterAndStatus(academic, semInst, statusInst).student.unique()
                          studentsList.each {
                              if(it.program.id==progInstInst.id)
                                stuIdLIst << it.id
                            }

                        }
                        def a = Student.createCriteria()
                        def stusList = []
                        if (stuIdLIst) {

                            stusList = a.list() {
                                eq('semesterRegistration', 1)
                                not {
                                    'in'('id', stuIdLIst)
                                }
                                order('rollNo', 'asc')
                            }
                        } else {
                            stusList = a.list() {
                                eq('semesterRegistration', 1)
                                order('rollNo', 'asc')
                            }
                        }
                        programList.each {
                            def progInstInst = it.id
                            stusList.each {
                                if(it.program.id==progInstInst)
                                    stuIdLIst << it.id
                            }
                        }
                        if (stuIdLIst) {
                            def c = Student.createCriteria()
                            def d = Student.createCriteria()
                            studentList = c.list() {
                                eq('semesterRegistration', 1)
                                and {
                                    'in'('id', stuIdLIst)
                                }
                                order('rollNo', 'asc')
                            }

                        }
                    } else {
                        status = false
                    }
                } else {
                    studentList = Student.findAllByProgramBranchInListAndSemesterRegistration(ProgramBranch.findAllByDepartment(currentUserDepartment), 1, [sort: "rollNo", order: "asc"])
                }
            }
            if (status) {
                [studentList: studentList]
            } else {
                flash.message = "You are not Assigned to Any Program."
                redirect(controller: "home", action: "index")
            }

        } else {
            redirect(controller: "login", action: "auth")
        }
    }


    def approveStudent() {
        /*Action for approving Student registration */
        if (springSecurityService.currentUser) {
            def stud = Student.findById(Long.parseLong(params.studentID))
            def status = Status.findById(4)
            stud.status = status
            if (stud.save(flush: true, failOnError: true)) {
                redirect(controller: "hod", action: "stuList")
                flash.message = "Student Approved"
            } else {
                redirect(controller: "hod", action: "stuList")
                flash.message = "Student NotApproved"
            }
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    def rejectStudent() {
        /*Action for rejecting student registration*/
        if (springSecurityService.currentUser) {
            def stud = Student.findById(Long.parseLong(params.studentId))
            def status = Status.findById(5)
            stud.status = status
            stud.reasonForRejection = params.reasonForRejection
            if (stud.save(flush: true, failOnError: true)) {

                redirect(controller: "hod", action: "stuList")
                flash.message = "Student Rejected"
            } else {
                redirect(controller: "hod", action: "stuList")
                flash.message = "Not Rejected"
            }
        } else {
            redirect(controller: "login", action: "auth")
        }


    }

    def semesterApproval() {
        if (springSecurityService.getCurrentUser()) {
            def currentUserEmail = springSecurityService.getCurrentUser().getEmail()
            def studentList = []
            def depart = FacultyAdvisor.findByEmail(currentUserEmail).getDepartment()
            def status = Status.findById(1)
            studentList = Student.findAllByProgramBranchInListAndStatus(ProgramBranch.findAllByDepartment(depart), status)
            [studentList: studentList]
        } else {
            redirect(controller: "login", action: "auth")
        }


    }
    def unapproveSemesterRegistration = {
        if (springSecurityService.currentUser) {
            def currentUser
            if (springSecurityService.getCurrentUser()) {
                currentUser = springSecurityService.getCurrentUser().getDepartment()
            }
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    def viewSemesterStudent = {
        /*Action for showing semester registration details*/
        if (springSecurityService.currentUser) {
//        println("=================="+params)
            def studInstance = Student.findById(Integer.parseInt(params.studentID))
            def totalList = []
            def totalList1 = []
            def returnMap = [:]
            def resultMap = [:]
            def optionalList = []
            def programSession = ProgramSession.findById(studInstance.programId)
            def optionalSubjectList = SubjectSession.findAllByIsOptional(true)
            def courseSubList = []
            optionalSubjectList.each {
                courseSubList << CourseSubject.findBySubjectSessionId(it)
            }
            def semester = Semester.findBySemesterNoAndProgramSession(studInstance.semester, programSession)
            def regPeriod = EnrollmentPeriod.findByProgramDetailAndSemester(studInstance.program, semester)
            def provStatus, noReject = false
            if (params.approved) {
                noReject = true
                provStatus = Status.findById(4)
            } else {
                provStatus = Status.findById(1)
            }

            def semFeeInst = SemesterFeeDetails.findAllByProgramDetailAndSemesterAndStudentAndStatus(studInstance.program, semester, studInstance, provStatus)
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
            def allSemesterForStudent = []
            def nextSemester = studInstance.semester
            def startSem = programSession.programDetailId.admissionTerm
            if (startSem != nextSemester) {
                if (nextSemester % 2 == 0) {
                    for (def i = startSem; i < nextSemester; i++) {
                        if (i % 2 == 0) {
                            allSemesterForStudent << Semester.findBySemesterNoAndProgramSession(i, programSession)
                        }
                    }
                } else {
                    for (def i = startSem; i < nextSemester; i++) {
                        if (i % 2 != 0) {
                            allSemesterForStudent << Semester.findBySemesterNoAndProgramSession(i, programSession)
                        }
                    }
                }
            }
            def allCourseList = CourseSubject.findAllByCourseDetailAndSemesterInList(programSession.programDetailId, allSemesterForStudent).subjectSessionId
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
            def view = false
            if (params.view) {
                view = true
            }
            def stuSubList = []
            if (params.semester) {
                stuSubList = StudentSubject.findAllByStudentAndStatusAndSemester(studInstance, provStatus, semester)
            } else {
                stuSubList = StudentSubject.findAllByStudentAndStatus(studInstance, provStatus)
            }
            def academicSession = SemesterFeeDetails.findAllBySemesterAndStudent(semester,studInstance).academicSession
            def studentSubject = stuSubList.unique().subjectSession.id
            [viewStatus: view, noReject: noReject, semFeeInst: semFeeInst, optionalList: optionalList, studInstance: studInstance, totalList: courseList, totalList1: allCourseList, studentSubject: studentSubject, session: academicSession[0], view: params.view]

        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def approveSemesterStudent = {
        /*Action for approving or update semester registration of particular student*/
        if (springSecurityService.currentUser) {
            def studentCourseList = [], regDate
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy")
            def studentIns = Student.findById(Integer.parseInt(params.studentId))
            studentIns.semester = Integer.parseInt(params.studentSemester)
            studentIns.semesterRegistration = 3
            studentIns.save(flush: true, failOnError: true)
            def programSession = ProgramSession.findById(studentIns.program.id)
            def semester = Semester.findBySemesterNoAndProgramSession(Integer.parseInt(params.studentSemester), programSession)
            def regPeriod = EnrollmentPeriod.findByProgramDetailAndSemesterAndStudentAdmissionYear(studentIns.program, semester, studentIns.registrationYear)
            def feeDetailsInst
            if (params.approvedUpdate) {
                feeDetailsInst = SemesterFeeDetails.findAllByProgramDetailAndStudentAndSemesterAndStatus(studentIns.program, studentIns, semester, Status.findById(4))
            } else {
                feeDetailsInst = SemesterFeeDetails.findAllByProgramDetailAndStudentAndSemesterAndStatus(studentIns.program, studentIns, semester, Status.findById(1))
            }
            def studentSubject
            studentSubject = StudentSubject.findAllByStudentAndProgramSessionAndSemester(studentIns, programSession, semester)
            studentSubject.each {
                if (regDate == null) {
                    regDate = it.registrationDate
                }
                it.delete()
            }
            feeDetailsInst.each {
                it.status = Status.findById(4)
            }

            if (params.studentSubjects) {

                if (params.studentSubjects.getClass() != String) {
                    for (int i = 0; i < params.studentSubjects.length; i++) {
                        def subjectSessionId = SubjectSession.findById(Long.parseLong(params.studentSubjects[i]))
                        def courseList = CourseSubject.findByProgramSessionAndSemesterAndSubjectSessionId(programSession, semester, subjectSessionId)
                        studentCourseList.add(courseList)
                    }
                } else {
                    def subjectSessionId = SubjectSession.findById(Long.parseLong(params.studentSubjects))
                    def courseList = CourseSubject.findByProgramSessionAndSemesterAndSubjectSessionId(programSession, semester, subjectSessionId)
                    studentCourseList.add(courseList)
                }

                for (int i = 0; i < studentCourseList.size(); i++) {
                    studentSubject = new StudentSubject();
                    studentSubject.student = studentIns;
                    studentSubject.programSession = programSession;
                    studentSubject.semester = semester
                    studentSubject.status = Status.findById(4)
                    studentSubject.registrationApprovalDate = df.parse(df.format(new Date()))
                    studentSubject.registrationDate = regDate
                    studentSubject.academicSession = regPeriod.admissionSession
                    studentSubject.subjectSession = studentCourseList[i].subjectSessionId
                    studentSubject.save(flush: true, failOnError: true)
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

                for (int i = 0; i < studentCourseList.size(); i++) {
                    studentSubject = new StudentSubject();
                    studentSubject.student = studentIns;
                    studentSubject.programSession = programSession;
                    studentSubject.semester = semester
                    studentSubject.status = Status.findById(4)
                    studentSubject.registrationApprovalDate = df.parse(df.format(new Date()))
                    studentSubject.registrationDate = regDate
                    studentSubject.academicSession = regPeriod.admissionSession
                    studentSubject.subjectSession = studentCourseList[i].subjectSessionId
                    studentSubject.save(flush: true, failOnError: true)
                }
            }

            flash.message = 'Student Approved/Updated For Semester Successfully'
            redirect(action: 'studentListForSemesterRegistration')
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    def rejectSemesterStudent = {
        /*Action for rejecting semester registration of particular student*/
        if (springSecurityService.currentUser) {
            def studentInstance = Student.findById(Integer.parseInt(params.studentId))
            def pr
            studentInstance.semesterRejectionReason = params.reasonForRejection
            studentInstance.semesterRegistration = 0
            if(studentInstance.program.programType.id==4){
                if(studentInstance.semester==3){
                    studentInstance.semester = 0
                }
                else{
                    if(studentInstance.semester!=0)
                    studentInstance.semester = (studentInstance.semester - 1)
                }
            }
            else{
                if(studentInstance.semester!=0)
                studentInstance.semester = (studentInstance.semester - 1)
            }
            if (studentInstance.save(flush: true, failOnError: true)) {
                def studentSubjectInst = StudentSubject.findAllByStudentAndStatus(studentInstance, Status.findById(1))
                studentSubjectInst.each {
                    it.delete(flush: true)
                }
                def semFeeInst = SemesterFeeDetails.findAllByStudentAndStatus(studentInstance, Status.findById(1))
                semFeeInst.each {
                    it.delete(flush: true)
                }
                flash.message = 'Rejected Successfully'
            } else {
                flash.message = 'Unable to Reject Successfully'
            }
            redirect(action: 'studentListForSemesterRegistration')
        } else {
            redirect(controller: "login", action: "auth")
        }


    }

    def studentListApprovedStudents() {
        /*Interface for showing list of students whose registrations have been approved*/
        def currentUser = springSecurityService.getCurrentUser()
        if (springSecurityService.getCurrentUser()) {
            def studentList = []
            def studentListTotal = 0
            def roleList = UserRole.findAllByUser(currentUser).role
            def isAdmin = false
            roleList.each {
                if (it == Role.findByAuthority('ROLE_ADMIN')) {
                    isAdmin = true
                }
            }
            if (isAdmin) {
                def status = Status.findById(4)
                studentList = Student.findAllByStatus(status, [sort: "rollNo", order: "asc"])
//                studentListTotal = Student.findAllByStatus(status).size()
            } else {
                def currentUserEmployeeCode = springSecurityService.getCurrentUser().employeeCode
                def currentUserDepartment = Employee.findByEmployeeCode(currentUserEmployeeCode).department
                def status = Status.findById(4)
                studentList = Student.findAllByProgramBranchInListAndStatus(ProgramBranch.findAllByDepartment(currentUserDepartment), status, [sort: "rollNo", order: "asc"])
//                studentListTotal = Student.findAllByProgramBranchInListAndStatus(ProgramBranch.findAllByDepartment(currentUserDepartment), status).size()
            }
//            [studentList: studentList]
            [studentList: studentList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }


    def studentListApprovedRegistrations() {
        /*Interface for showing list of students whose semester registrations have been approved*/
        def status = true
        if (springSecurityService.currentUser) {
//            params.max = Math.min(params.max ? params.int('max') : 20, 100)
            def studentList = []
            def studentListTotal = 0
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
                studentList = Student.findAllBySemesterRegistration(3, [sort: "rollNo", order: "asc"])
            } else {
                def programList = FacultyAdvisor.findAllByUser(springSecurityService.getCurrentUser()).programDetail.unique()
                def facultyAdvisorInst = FacultyAdvisor.findAllByUser(springSecurityService.getCurrentUser())
                if (programList) {
                    def stuIdLIst = []
                    def statusInst = Status.findById(4)
                    facultyAdvisorInst.each {
                        def academic = it.academicSession
                        def semInst = it.semester
                        def progInstInst = it.programDetail
                        def studentsList = StudentSubject.findAllByAcademicSessionAndSemesterAndStatus(academic, semInst, statusInst).student.unique()
                        studentsList.each {
                            if(it.program.id==progInstInst.id)
                                stuIdLIst << it.id
                        }

                    }
                    def a = Student.createCriteria()
                    def stusList = []
                    if (stuIdLIst) {

                        stusList = a.list() {
                            eq('semesterRegistration', 3)
                            not {
                                'in'('id', stuIdLIst)
                            }
                            order('rollNo', 'asc')
                        }
                    } else {
                        stusList = a.list() {
                            eq('semesterRegistration', 3)
                            order('rollNo', 'asc')
                        }
                    }
                    programList.each {
                        def progInstInst = it.id
                        stusList.each {
                            if(it.program.id==progInstInst)
                                stuIdLIst << it.id
                        }
                    }
                    if (stuIdLIst) {
                        def c = Student.createCriteria()
                        def d = Student.createCriteria()
                        studentList = c.list() {
                            eq('semesterRegistration', 3)
                            and {
                                'in'('id', stuIdLIst)
                            }
                            order('rollNo', 'asc')
                        }

                    }
                } else {
                    studentList = Student.findAllByProgramBranchInListAndSemesterRegistration(ProgramBranch.findAllByDepartment(currentUserDepartment), 3, [sort: "rollNo", order: "asc"])
                }
            }
            if (status) {
                [studentList: studentList]
            } else {
                flash.message = "You are not Assigned to Any Program."
                redirect(controller: "home", action: "index")
            }
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def studentListCertificateRequest = {
        if (springSecurityService.currentUser) {
            def currentUserEmployeeCode = springSecurityService.getCurrentUser().employeeCode
            def currentUserDepartment = Employee.findByEmployeeCode(currentUserEmployeeCode).department
            def programBranch = ProgramBranch.findByDepartment(currentUserDepartment)
            def studentList = Student.findAllByProgramBranch(programBranch)
            def requestedCertificateList = CertificateRequests.findAllByStudentInListAndStatus(studentList, 0)
            [requestedCertificateList: requestedCertificateList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }


    def approveCertificateRequest = {
        if (springSecurityService.currentUser) {
            def student = Student.findById(Integer.parseInt(params.studentID))
            def certificate = CertificateRole.findById(Integer.parseInt(params.certificateId))
            def studCertificate = CertificateRequests.findByStudentAndCertificate(student, certificate)
            studCertificate.status = 1
            studCertificate.save(flush: true, failOnError: true)
            def args = [template: "generate", model: [student: student, certificate: certificate], filename: "" + student.firstName + "_" + certificate.nameOfCertificate + ".pdf"]
            pdfRenderingService.render(args + [controller: this], response)
        } else {
            redirect(controller: "login", action: "auth")
        }

//        redirect(action: 'studentListCertificateRequest')
    }

    def searchApprovedStudent(){
//        def resultMap=[:],firstName,middleName,lastName,rollNo,studentId
//        def stud= Student.findByRollNoAndStatus(params.studentRollNo,Status.findById(4))
//
//        resultMap.firstName=stud.firstName
//        resultMap.middleName=stud.middleName
//        resultMap.lastName=stud.lastName
//        resultMap.rollNo=stud.rollNo
//        resultMap.studentId=stud.id
//
//        render resultMap as JSON
        if (springSecurityService.currentUser) {
            def currentUser = springSecurityService.getCurrentUser()
            def resultMap=[:],firstName,middleName,lastName,rollNo,studentId,stud,msg
            if (springSecurityService.getCurrentUser()) {
                def roleList = UserRole.findAllByUser(currentUser).role
                def isAdmin = false
                roleList.each {
                    if (it == Role.findByAuthority('ROLE_ADMIN')) {
                        isAdmin = true
                    }
                }
                if (isAdmin) {
                    stud= Student.findByRollNoAndStatus(params.studentRollNo,Status.findById(4))
                    if(!stud){
                        resultMap.msg="This roll no does not exist"
                        resultMap.firstName=""
                        resultMap.middleName=""
                        resultMap.lastName=""
                        resultMap.rollNo=""
                        render resultMap as JSON

                    }
                } else {
                    def currentUserEmployeeCode = springSecurityService.getCurrentUser().employeeCode
                    def currentUserDepartment = Employee.findByEmployeeCode(currentUserEmployeeCode).department

                    stud = Student.findByProgramBranchInListAndRollNoAndStatus(ProgramBranch.findAllByDepartment(currentUserDepartment),params.studentRollNo, Status.findById(4))
                    if(!stud){
                        resultMap.msg="This roll no does not exist"
                        resultMap.firstName=""
                        resultMap.middleName=""
                        resultMap.lastName=""
                        resultMap.rollNo=""
                        render resultMap as JSON

                    }

                }
                resultMap.firstName=stud.firstName
                resultMap.middleName=stud.middleName
                resultMap.lastName=stud.lastName
                resultMap.rollNo=stud.rollNo

                resultMap.studentId=stud.id

                render resultMap as JSON
            }
        }
    }

    def searchApprovedRegistration(){
//        def resultMap=[:],firstName,middleName,lastName,rollNo,studentId,sem
//        def stud= Student.findByRollNoAndSemesterRegistration(params.studentRollNo,3)
//
//        resultMap.firstName=stud.firstName
//        resultMap.middleName=stud.middleName
//        resultMap.lastName=stud.lastName
//        resultMap.rollNo=stud.rollNo
//        resultMap.sem=stud.semester
//        resultMap.studentId=stud.id
//
//        render resultMap as JSON

        def status = true
        if (springSecurityService.currentUser) {
            def resultMap=[:],firstName,middleName,lastName,rollNo,studentId,sem
            def stud

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
                stud = Student.findByRollNoAndSemesterRegistration(params.studentRollNo,3)

            } else {
                def programList = FacultyAdvisor.findAllByUser(springSecurityService.getCurrentUser()).programDetail.unique()
                def facultyAdvisorInst = FacultyAdvisor.findAllByUser(springSecurityService.getCurrentUser())
                if (programList) {
                    def stuIdLIst = []
                    def statusInst = Status.findById(4)
                    facultyAdvisorInst.each {
                        def academic = it.academicSession
                        def semInst = it.semester
                        def progInstInst = it.programDetail
                        def studentsList = StudentSubject.findAllByAcademicSessionAndSemesterAndStatus(academic, semInst, statusInst).student.unique()
                        studentsList.each {
                            if(it.program.id==progInstInst.id)
                                stuIdLIst << it.id
                        }

                    }
                    def a = Student.createCriteria()
                    def stusList = []
                    if (stuIdLIst) {

                        stusList = a.list(params) {
                            eq('semesterRegistration', 3)
                            not {
                                'in'('id', stuIdLIst)
                            }
                            order('rollNo', 'asc')
                        }
                    } else {
                        stusList = a.list(params) {
                            eq('semesterRegistration', 3)
                            order('rollNo', 'asc')
                        }
                    }
                    programList.each {
                        def progInstInst = it.id
                        stusList.each {
                            if(it.program.id==progInstInst)
                                stuIdLIst << it.id
                        }
                    }
                    if (stuIdLIst) {
                        def c = Student.createCriteria()
                        def d = Student.createCriteria()
                        stud = c.list(params) {
                            eq('rollNo',params.studentRollNo)
                            eq('semesterRegistration', 3)
                            and {
                                'in'('id', stuIdLIst)
                            }

                        }

                    }
                } else {
                    stud = Student.findAllByProgramBranchInListAndRollNoAndSemesterRegistration(ProgramBranch.findAllByDepartment(currentUserDepartment),params.studentRollNo ,3)

                }
            }
            if (status) {
                resultMap.firstName=stud.firstName
                resultMap.middleName=stud.middleName
                resultMap.lastName=stud.lastName
                resultMap.rollNo=stud.rollNo
                resultMap.sem=stud.semester
                resultMap.studentId=stud.id

                render resultMap as JSON
            }
            else {
                flash.message = "You are not Assigned to Any Program."
                redirect(controller: "home", action: "index")
            }
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    def searchStudentToApprove(){
//        def resultMap=[:],firstName,middleName,lastName,rollNo,studentId
//        def stud= Student.findByRollNoAndStatus(params.studentRollNo,Status.findById(1))
//
//        resultMap.firstName=stud.firstName
//        resultMap.middleName=stud.middleName
//        resultMap.lastName=stud.lastName
//        resultMap.rollNo=stud.rollNo
//        resultMap.studentId=stud.id
//
//        render resultMap as JSON

        if (springSecurityService.currentUser) {
            def currentUser = springSecurityService.getCurrentUser()
            def resultMap=[:],firstName,middleName,lastName,rollNo,studentId,stud,msg
            if (springSecurityService.getCurrentUser()) {
                def roleList = UserRole.findAllByUser(currentUser).role
                def isAdmin = false
                roleList.each {
                    if (it == Role.findByAuthority('ROLE_ADMIN')) {
                        isAdmin = true
                    }
                }
                if (isAdmin) {
                    stud= Student.findByRollNoAndStatus(params.studentRollNo,Status.findById(1))
                    if(!stud){
                        resultMap.msg="This roll no does not exist"
                        resultMap.firstName=""
                        resultMap.middleName=""
                        resultMap.lastName=""
                        resultMap.rollNo=""
                        render resultMap as JSON

                    }
                } else {
                    def currentUserEmployeeCode = springSecurityService.getCurrentUser().employeeCode
                    def currentUserDepartment = Employee.findByEmployeeCode(currentUserEmployeeCode).department

                    stud = Student.findByProgramBranchInListAndRollNoAndStatus(ProgramBranch.findAllByDepartment(currentUserDepartment),params.studentRollNo, Status.findById(1))
                    if(!stud){
                        resultMap.msg="This roll no does not exist"
                        resultMap.firstName=""
                        resultMap.middleName=""
                        resultMap.lastName=""
                        resultMap.rollNo=""
                        render resultMap as JSON

                    }

                }
                resultMap.firstName=stud.firstName
                resultMap.middleName=stud.middleName
                resultMap.lastName=stud.lastName
                resultMap.rollNo=stud.rollNo

                resultMap.studentId=stud.id

                render resultMap as JSON
            }
        }

    }

    def searchRegistrationToApprove(){
//        def resultMap=[:],firstName,middleName,lastName,rollNo,studentId
//        def stud= Student.findByRollNoAndSemesterRegistration(params.studentRollNo,1)
//
//        resultMap.firstName=stud.firstName
//        resultMap.middleName=stud.middleName
//        resultMap.lastName=stud.lastName
//        resultMap.rollNo=stud.rollNo
//
//        resultMap.studentId=stud.id
//
//        render resultMap as JSON

        if (springSecurityService.currentUser) {
            def resultMap=[:],firstName,middleName,lastName,rollNo,studentId
            def status = true

            def stud
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
                stud = Student.findByRollNoAndSemesterRegistration(params.studentRollNo,1)

            } else {
                def currentUserEmployeeCode = springSecurityService.getCurrentUser().employeeCode
                def currentUserDepartment = Employee.findByEmployeeCode(currentUserEmployeeCode).department

                if (isFacultyAdvisor) {
                    def programList = FacultyAdvisor.findAllByUser(springSecurityService.getCurrentUser()).programDetail.unique()
                    def facultyAdvisorInst = FacultyAdvisor.findAllByUser(springSecurityService.getCurrentUser())
                    if (programList) {
                        def stuIdLIst = []
                        def statusInst = Status.findById(1)
                        facultyAdvisorInst.each {
                            def academic = it.academicSession
                            def semInst = it.semester
                            def progInstInst = it.programDetail
                            def studentsList = StudentSubject.findAllByAcademicSessionAndSemesterAndStatus(academic, semInst, statusInst).student.unique()
                            studentsList.each {
                                if(it.program.id==progInstInst.id)
                                    stuIdLIst << it.id
                            }

                        }
                        def a = Student.createCriteria()
                        def stusList = []
                        if (stuIdLIst) {

                            stusList = a.list(params) {
                                eq('semesterRegistration', 1)
                                not {
                                    'in'('id', stuIdLIst)
                                }
                                order('rollNo', 'asc')
                            }
                        } else {
                            stusList = a.list(params) {
                                eq('semesterRegistration', 1)
                                order('rollNo', 'asc')
                            }
                        }
                        programList.each {
                            def progInstInst = it.id
                            stusList.each {
                                if(it.program.id==progInstInst)
                                    stuIdLIst << it.id
                            }
                        }
                        if (stuIdLIst) {
                            def c = Student.createCriteria()

                            stud = c.list(params) {
                                eq('rollNo', params.studentRollNo)
                                eq('semesterRegistration', 1)
                                and {
                                    'in'('id', stuIdLIst)
                                }

                            }

                        }
                    } else {
                        status = false
                    }
                } else {
                    stud = Student.findAllByProgramBranchInListAndRollNoAndSemesterRegistration(ProgramBranch.findAllByDepartment(currentUserDepartment),params.studentRollNo,1)

                }
            }
            if (status) {
                resultMap.firstName=stud.firstName
                resultMap.middleName=stud.middleName
                resultMap.lastName=stud.lastName
                resultMap.rollNo=stud.rollNo

                resultMap.studentId=stud.id

                render resultMap as JSON
            } else {
                flash.message = "You are not Assigned to Any Program."
                redirect(controller: "home", action: "index")
            }

        } else {
            redirect(controller: "login", action: "auth")
        }
    }


}
