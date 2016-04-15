package examinationproject

import IST.Department
import IST.Employee
import IST.EmployeeDesignation
import IST.EmployeeQualification
import IST.ProgramBranch
import com.university.User
import com.university.UserRole
import com.university.Role
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import postexamination.StudentEvaluatedMarks

import javax.activation.MimetypesFileTypeMap
import java.text.DateFormat
import java.text.SimpleDateFormat

class ReportController {
    def reportService
    def pdfRenderingService
    def springSecurityService



    def index ={
    }

   @Secured(["ROLE_ADMIN", "ROLE_HOD"])
    def reportIndex = {
       /* For showing report index*/
        def programList=ProgramDetail.list()
        def semester= Semester.list().semesterNo.unique()
        def registrationYear = Student.createCriteria().list {
            projections {
                distinct("registrationYear")
            }
            order("registrationYear", "desc")
        }
        def academicSession=SemesterFeeDetails.createCriteria().list {
            projections {
                distinct("academicSession")
            }
            order("academicSession", "desc")
        }
//        def courseInstructor= UserRole.findAllByRole(Role.findByAuthority('ROLE_INSTRUCTOR')).user.username.unique()
        def courseInstructor= StudentEvaluatedMarks.findAll().user.username.unique()

        def progTypeList=ProgramType.findAll()
        def depList=Department.findAll()
        def branchList = ProgramBranch.findAll()
        def empDesignation = EmployeeDesignation.findAll().designation.unique()
        def empQualification = EmployeeQualification.findAll().degree.unique()

        [programList:programList,registrationYear:registrationYear,academicSession:academicSession,semester:semester,courseInstructor:courseInstructor,progTypeList:progTypeList,depList:depList,empDesignation:empDesignation,empQualification:empQualification,branchList:branchList]
    }

    def getSessionList(){
//        println("in getSession List")
        def sessionList = Student.createCriteria().list {
            projections {
                distinct("registrationYear")
            }
        }
        render sessionList as JSON
    }

    def generateReport={
        /* For generating reports*/
        if(params.value=='studentApproved' && (params.approvedProgram && params.approvedAdmissionYear)){
            /*Report for list of approved students*/
            def studentName = [],studentRoll = [], studentDOB = [], studentMobNo = []
            def progInst=ProgramDetail.findById(Long.parseLong(params.approvedProgram))
            def studentList=Student.findAllByRegistrationYearAndProgramAndStatus(Integer.parseInt(params.approvedAdmissionYear),progInst,Status.findById(4))
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy")
            studentList.each {
                studentName << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
                studentRoll << it.rollNo
                studentDOB << df.format(it.dob)
                studentMobNo << it.mobileNo
            }
            def args = [template: "generate", model: [admissionYear:params.approvedAdmissionYear,programName:progInst.courseName,approvedStudentList: studentList, studentMobNo: studentMobNo, studentDOB: studentDOB, studentRoll: studentRoll, studentName: studentName,approved:"approved"],filename:progInst.courseName+'_Approved'+".pdf"]
            pdfRenderingService.render(args + [controller: this ], response)

        }

        else if(params.value=='studentUnapproved' && (params.approvedProgram && params.approvedAdmissionYear)){
            /*Report for list of unapproved students*/
            def studentName = [],studentRoll = [], studentDOB = [], studentMobNo = []
            def progInst=ProgramDetail.findById(Long.parseLong(params.approvedProgram))
            def studentList=Student.findAllByRegistrationYearAndProgramAndStatus(Integer.parseInt(params.approvedAdmissionYear),progInst,Status.findById(1))
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy")
            studentList.each {
                studentName << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
                studentRoll << it.rollNo
                studentDOB << df.format(it.dob)
                studentMobNo << it.mobileNo
            }
            def args = [template: "generate", model: [admissionYear:params.approvedAdmissionYear,programName:progInst.courseName,approvedStudentList: studentList, studentMobNo: studentMobNo, studentDOB: studentDOB, studentRoll: studentRoll, studentName: studentName,unapproved:"unapproved"],filename:progInst.courseName+'_Unapproved'+".pdf"]
            pdfRenderingService.render(args + [controller: this ], response)
        }

        else if(params.value=='projectThesis'&& params.academicSession ){
            /*project thesis title report*/
               def finalMap=[:]
               def finalList = []
            if(ProjectDetails.findByAcademicSession(params.academicSession)){
                def studentList = ProjectDetails.findAllByAcademicSessionAndStatus(params.academicSession,Status.findById(4)).student

                studentList.each {
                    def tempList = []
                    tempList << it.rollNo
                    tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
                    tempList << it.program.courseName
                    def proDetails = ProjectDetails.findByStudentAndAcademicSessionAndStatus(it,params.academicSession,Status.findById(4))
                    tempList << proDetails.projectTitle
                    tempList << proDetails.subject.subjectName
                    def guide = GuideDetails.findById(Long.parseLong(proDetails.guideId)).employee
                    tempList << guide.firstName+ " "+ (guide.middleName ? guide.middleName : "")+ " "+ guide.lastName
                    def dom = ProjectDomain.findById(Long.parseLong(proDetails.domain)).domainName
                    tempList << dom
                    tempList << proDetails.nameOfInstitution
                    finalList << tempList
                }
            }

            def headList=[]
            headList << "Roll No"
            headList << "Name"
            headList << "Programme"
            headList << "Project Title"
            headList << "Subject"
            headList << "Supervisor"
            headList << "Domain"
            headList << "Institution"

            finalMap.headList=headList
            finalMap.finalList=finalList

            render finalMap as JSON
        }

        else if(params.value=='studentAttendance' && (params.programId && params.academicSession1 && params.programTerm)){
            /*Student's attendence report*/
            def progInst = ProgramDetail.findById(Long.parseLong(params.programId))
            def progSession = ProgramSession.findByProgramDetailId(progInst)
            def subjectName = []
            def programSession = ProgramSession.findByProgramDetailId(progInst)
            def semesterInst = Semester.findById(Long.parseLong(params.programTerm))
            def studentSubjectList = StudentSubject.findAllByProgramSessionAndAcademicSessionAndSemester(programSession, params.academicSession1, semesterInst, [sort: "subjectSession"])
            def subjectList = studentSubjectList.subjectSession.subjectId.unique()
            def studentList = studentSubjectList.student.unique()
            def progType = progInst.courseName
            def progBranch = progInst.programBranch.name

            def finalList = []
            def finalMap =[:]
            studentList.each {
                def tempList = []
                tempList << it.rollNo
                tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
                tempList << ""
                tempList << ""
                tempList << ""
                tempList << ""
                tempList << ""
                finalList << tempList
            }

            def headList = []
            headList<< "Roll No"
            headList<< "Name"
            headList<< "Percentage of Attendence"
            headList<< "No of class held"
            headList<< "No of class attended"
            headList<< "Date of validation"
            headList<< "Remarks"

            finalMap.headList=headList
            finalMap.finalList=finalList

            render finalMap as JSON
//            def args = [template: "generate", model: [studentAttendence:params.value,program:progType,branch:progBranch,session:params.academicSession1,finalList:finalList,],filename:progInst.courseName+'_Project'+".pdf"]
//            pdfRenderingService.render(args + [controller: this ], response)
        }

        else if(params.value=='subjectStudent' && params.academicSession &&params.subjectCode){
            /*Subjectwise Student Report*/
            def subjectInst = Subject.findBySubjectCode(params.subjectCode)
            def acaSession = params.academicSession
            def studentList = StudentSubject.findAllByAcademicSessionAndSubjectSession(acaSession,SubjectSession.findBySubjectId(subjectInst))
            def finalList = []
            def finalMap =[:]
            studentList.each {
                def tempList = []
                tempList << it.student.rollNo
                tempList << it.student.firstName + " " + (it.student.middleName ? it.student.middleName : "") + " " + it.student.lastName
                tempList << it.student.program.courseName
                tempList << it.semester.semesterNo

                finalList << tempList
            }

            def headList = []
            headList<< "Roll No"
            headList<< "Name"
            headList<< "Program"
            headList<< "Semester"


            finalMap.headList=headList
            finalMap.finalList=finalList

            render finalMap as JSON
//            def args = [template: "generate", model: [studentAttendence:params.value,program:progType,branch:progBranch,session:params.academicSession1,finalList:finalList,],filename:progInst.courseName+'_Project'+".pdf"]
//            pdfRenderingService.render(args + [controller: this ], response)
        }

        else if(params.value=='approvedSemesterRegistrationInExcel' && (params.programId && params.academicSession1 && params.programTerm && params.resultFormat == "pdf")){
            /*Approved Students with Subjects in pdf format report*/
            def subjectName = []
            def programInst = ProgramDetail.findById(Long.parseLong(params.programId))
            def programSession = ProgramSession.findByProgramDetailId(programInst)
            def semesterInst = Semester.findById(Long.parseLong(params.programTerm))
            def studentSubjectList=StudentSubject.findAllByProgramSessionAndAcademicSessionAndSemester(programSession, params.academicSession1, semesterInst, [sort: "subjectSession"])
            def subjectList = studentSubjectList.subjectSession.subjectId.unique()
            def studentList = studentSubjectList.student.unique()


            subjectList.each{
                subjectName<<it.subjectCode
            }
            def finalList=[]
            studentList.each {
                def tempList=[]
                tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
                tempList << it.rollNo
                tempList <<semesterInst.semesterNo
                def stuId= it.id
                subjectList.each{
                    def stuSub=StudentSubject.findByStudentAndSubjectSession(Student.findById(stuId),SubjectSession.findAllBySubjectId(Subject.findById(it.id)))
                    if(stuSub){
                        tempList<< "Y"

                    }
                    else{
                        tempList<< "N"
                    }
                }
                finalList<< tempList
            }



            def args = [template: "generate", model: [admissionYear:params.academicSession1,programName:programInst.courseName,students:finalList, subjectName:subjectName],filename:programInst.courseName+'_ApprovedWithSubjects'+".pdf"]
            pdfRenderingService.render(args + [controller: this ], response)

        }

        else if(params.value=='approvedSemesterRegistrationInExcel' && (params.programId && params.academicSession1 && params.programTerm && params.resultFormat == "excel")){
            /*Approved Students with Subjects in excel format*/
            def webRootDir = servletContext.getRealPath("/")
            def userDir = new File(webRootDir,'/Report')
            userDir.mkdirs()
            def progInst=ProgramDetail.findById(Long.parseLong(params.programId))
            def excelPath = servletContext.getRealPath("/")+'/Report'+System.getProperty('file.separator')+'SemesterRegistration_'+params.academicSession1+'_'+progInst.courseName+'.xls'
//           println('this is the real path '+excelPath)
            def status = reportService.getReportDataCourse(params, excelPath)

            if(status){
                File myFile = new File(servletContext.getRealPath("/")+'/Report'+System.getProperty('file.separator')+'SemesterRegistration_'+params.academicSession1+'_'+progInst.courseName+'.xls')
                response.setHeader "Content-disposition", "attachment; filename="+'"'+'SemesterRegistration_'+params.academicSession1+'_'+progInst.courseName+".xls"+'"'
                response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
                response.outputStream << myFile .bytes
                response.outputStream.flush()
                myFile.delete()
            }
            else{
                flash.message = "${message(code: 'record.not.found.message')}"
                redirect(action: 'reportIndex')
            }
        }
        else if(params.value=='foreignStudent' && (params.approvedAdmissionYear)){
            /*This module is for foreign student report*/
            def studentList=Student.findAllByNationalityAndRegistrationYear("Non-Indian",Integer.parseInt(params.approvedAdmissionYear))
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy")
            def finalList=[]
            def finalMap=[:]
            studentList.each {
                def tempList=[]
                tempList << it.rollNo
                tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
                tempList << it.program.courseName
                tempList << df.format(it.admissionDate)
                if(it.nationalityName==null){
                    tempList << "NA"
                }
                else{
                    tempList << it.nationalityName
                }

                finalList<< tempList
            }
            def headList=[]
            headList << "Roll No"
            headList << "Name"
            headList << "Course"
            headList << "DOA"
            headList << "Country"
            finalMap.headList= headList
            finalMap.finalList= finalList
            render finalMap as JSON
        }

        else if((params.value=='approvedRegistrationByFA') && params.programId && params.academicSession1 && params.programTerm){
            /*This module is for Approved Semester Registration By Faculty Advisor report*/
            def studentName = [],studentRoll = [],studentSemester = [], studentAcademicSession = [], studentFacultyAdvisor = []
            def progInst=ProgramDetail.findById(Long.parseLong(params.programId))
            def sem = Semester.findById(Long.parseLong(params.programTerm))
            def studSub = StudentSubject.findAllByAcademicSessionAndSemester(params.academicSession1,sem)
            def facultyAdv = FacultyAdvisor.findByProgramDetailAndAcademicSessionAndSemester(progInst,params.academicSession1,sem)
            if(facultyAdv) {
                def usr = facultyAdv.user.employeeCode

                def emp = Employee.findByEmployeeCode(usr)
                def studentList = studSub.student.unique()
                studentList.each {
                    studentName << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
                    studentRoll << it.rollNo
                    studentSemester << sem.semesterNo
                    studentAcademicSession << params.academicSession1
                    studentFacultyAdvisor << emp.firstName + " " + (emp.middleName ? emp.middleName : "") + " " + emp.lastName
                }
            }
            else{
            }
            def args = [template: "generate", model: [admissionYear:params.academicSession1,studentName:studentName,studentAcademicSession:studentAcademicSession,studentRoll:studentRoll,studentSemester:studentSemester,studentFacultyAdvisor:studentFacultyAdvisor],filename:'ApprovedSemesterRegistrationByFacultyAdvisor'+".pdf"]
            pdfRenderingService.render(args + [controller: this ], response)
        }

        else if ((params.value=='categoryGraph')&& params.approvedAdmissionYear) {
            /*For graph(Student Intake Graphical form)*/
            def progList = ProgramDetail.findAll()

            def currentYear=params.approvedAdmissionYear
            def graphMap=[:]
            def listProgram=[]
            def studentList=[]
            progList.each{
                listProgram << it.programType.type+" "+it.programBranch.name
                studentList << Student.countByProgramAndRegistrationYearAndStatus(it, currentYear, Status.findAllById(4))
            }
            graphMap.put(0,listProgram)
            graphMap.put(1,studentList)
                  redirect(controller: "report",action: "reportIndex",params: [graphMap:graphMap as JSON])
//              render map as JSON
            }

        else if((params.value=='studentsByGender') && params.approvedProgram && params.approvedAdmissionYear && params.gender )  {
            /*List of student by Gender report*/
//            def webRootDir = servletContext.getRealPath("/")
//            def userDir = new File(webRootDir,'/Report')
//            userDir.mkdirs()
            def progInst=ProgramDetail.findById(Long.parseLong(params.approvedProgram))
            def year = params.approvedAdmissionYear
//            def excelPath = servletContext.getRealPath("/")+'/Report'+System.getProperty('file.separator')+'List of Students by Gender'+progInst.courseName+'_'+year+'.xls'
//           println('this is the real path '+excelPath)
            def finalMap = reportService.getReportStudentListByGender(progInst,year,params.gender)
                render finalMap as JSON
//            if(status){
//                File myFile = new File(servletContext.getRealPath("/")+'/Report'+System.getProperty('file.separator')+'List of Students by Gender'+progInst.courseName+'_'+year+'.xls')
//                response.setHeader "Content-disposition", "attachment; filename="+'List of Students by Gender'+progInst.courseName+'_'+year+'.xls'
//                response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
//                response.outputStream << myFile.bytes
//                response.outputStream.flush()
//                myFile.delete()
//            }
//            else{
//                flash.message = "${message(code: 'record.not.found.message')}"
//                redirect(action: 'reportIndex')
//            }

        }

        else if((params.value=='studentsCategory') && params.approvedProgram && params.approvedAdmissionYear && params.category )  {
            /*List of student by Category report*/
//            def webRootDir = servletContext.getRealPath("/")
//            def userDir = new File(webRootDir,'/Report')
//            userDir.mkdirs()
            def progInst=ProgramDetail.findById(Long.parseLong(params.approvedProgram))
            def year = params.approvedAdmissionYear
//            def excelPath = servletContext.getRealPath("/")+'/Report'+System.getProperty('file.separator')+'List of Students with category'+progInst.courseName+'_'+year+'.xls'
//           println('this is the real path '+excelPath)
            def finalMap = reportService.getReportStudentCategory(progInst,year,params.category)
            render finalMap as JSON

//            if(status){
//                File myFile = new File(servletContext.getRealPath("/")+'/Report'+System.getProperty('file.separator')+'List of Students with category'+progInst.courseName+'_'+year+'.xls')
//                response.setHeader "Content-disposition", "attachment; filename="+'List of Students with category'+progInst.courseName+'_'+year+'.xls'
//                response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
//                response.outputStream << myFile.bytes
//                response.outputStream.flush()
//                myFile.delete()
//            }
//            else{
//                flash.message = "${message(code: 'record.not.found.message')}"
//                redirect(action: 'reportIndex')
//            }



        }

        else if((params.value=='studentsByCategory') && params.approvedProgram && params.approvedAdmissionYear && params.genders && params.category)  {
            /*Excel Report for List of student by Category & Gender*/
            def webRootDir = servletContext.getRealPath("/")
            def userDir = new File(webRootDir,'/Report')
            userDir.mkdirs()
            def progInst=ProgramDetail.findById(Long.parseLong(params.approvedProgram))
            def year = params.approvedAdmissionYear
            def excelPath = servletContext.getRealPath("/")+'/Report'+System.getProperty('file.separator')+'List of Students by Category'+progInst.courseName+'_'+year+'.xls'
//           println('this is the real path '+excelPath)
            def status = reportService.getReportStudentListByCategory(progInst,year,params.genders,params.category,excelPath)

            if(status){
                File myFile = new File(servletContext.getRealPath("/")+'/Report'+System.getProperty('file.separator')+'List of Students by Category'+progInst.courseName+'_'+year+'.xls')
                response.setHeader "Content-disposition", "attachment; filename="+'"'+'List of Students by Category'+progInst.courseName+'_'+year+'.xls'+'"'
                response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
                response.outputStream << myFile.bytes
                response.outputStream.flush()
                myFile.delete()
            }
            else{
                flash.message = "${message(code: 'record.not.found.message')}"
                redirect(action: 'reportIndex')
            }



        }

        else if(params.value=='studentInfoByCourse' && params.approvedProgram){
            /*Report for Course Wise Student Information*/
//            def webRootDir = servletContext.getRealPath("/")
//            def userDir = new File(webRootDir,'/Report')
//            userDir.mkdirs()
//            def excelPath = servletContext.getRealPath("/")+'/Report'+System.getProperty('file.separator')+'Student_Information_'+'.xls'
            def finalMap = reportService.getReportStudentInfoByCourse(params.approvedProgram)
            render finalMap as JSON
//            if(status){
//                File myFile = new File(servletContext.getRealPath("/")+'/Report'+System.getProperty('file.separator')+'Student_Information_'+'.xls')
//                response.setHeader "Content-disposition", "attachment; filename="+'Student_Information_'+".xls"
//                response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
//                response.outputStream << myFile .bytes
//                response.outputStream.flush()
//                myFile.delete()
//            }
//            else{
//                flash.message = "${message(code: 'record.not.found.message')}"
//                redirect(action: 'reportIndex')
//            }


        }

        else if(params.value=='minorityStudent' && (params.approvedAdmissionYear)){
            /*Report for Minority Students' information*/
//            def webRootDir = servletContext.getRealPath("/")
//            def userDir = new File(webRootDir,'/Report')
//            userDir.mkdirs()
//            def excelPath = servletContext.getRealPath("/")+'/Report'+System.getProperty('file.separator')+'Minority Students\' record'+'.xls'
            def finalMap = reportService.getReportMinorityStudent(params.value,params.approvedAdmissionYear)
            render finalMap as JSON
        }

        else if(params.value=='registrationDetails' && (params.programId && params.academicSession1 && params.programTerm)) {
            /*Report for Students' Registration details*/
            def finalMap = reportService.getReportStudentRegistration(params)

            render finalMap as JSON


        }

        else if(params.value=='regDetailsWithBack' && (params.programId && params.academicSession1 && params.programTerm)) {
            /*Report for Students' previous semester registration details along with back details*/
            def finalMap = reportService.getReportStudentRegistrationPreviousSem(params)
            render finalMap as JSON

        }

//        else if(params.value=='grade' && (params.academicSession && params.courseInstructor && params.resultFormat == "excel")) {
//            def webRootDir = servletContext.getRealPath("/")
//            def userDir = new File(webRootDir,'/Report')
//            userDir.mkdirs()
//
//            def excelPath = servletContext.getRealPath("/")+'/Report'+System.getProperty('file.separator')+'Grade_Details'+'.xls'
////           println('this is the real path '+excelPath)
//            def status = reportService.getReportGradeDetails(params, excelPath)
//
//            if(status){
//                File myFile = new File(servletContext.getRealPath("/")+'/Report'+System.getProperty('file.separator')+'Grade_Details'+'.xls')
//                response.setHeader "Content-disposition", "attachment; filename="+'Grade_Details'+'.xls'+".xls"
//                response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
//                response.outputStream << myFile .bytes
//                response.outputStream.flush()
//                myFile.delete()
//            }
//            else{
//                flash.message = "${message(code: 'record.not.found.message')}"
//                redirect(action: 'reportIndex')
//            }
//
//
//        }

        else if(params.value=='grade' && (params.academicSession && params.courseInstructor )) {
            /*Report for Grade details from Course instructor*/
            def studentList= StudentEvaluatedMarks.findAllByAcademicSessionAndUser(params.academicSession,User.findAllByUsername(params.courseInstructor)).student.unique()

            def finalMap=[:]
            def finalList = []
            studentList.each {
                def tempList = []
                tempList << it.rollNo
                tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
                tempList << it.program.courseName

                def stuMarks= StudentEvaluatedMarks.findByAcademicSessionAndUserAndStudent(params.academicSession,User.findAllByUsername(params.courseInstructor),it)

                tempList << stuMarks.subjectId.subjectName
                tempList << stuMarks.marksSecuredAfterRule
                tempList << stuMarks.grade

                finalList << tempList
            }

            def headList=[]
            headList << "Roll No"
            headList << "Name"
            headList << "Programme"
            headList << "Subject"
            headList << "Marks"
            headList << "Grade"

            finalMap.headList=headList
            finalMap.finalList=finalList

            render finalMap as JSON

//            def args = [template: "generate", model: [admissionYear:params.academicSession,gradeStudentList:finalList, instructor: params.courseInstructor],filename:params.courseInstructor+'Grades'+".pdf"]
//            pdfRenderingService.render(args + [controller: this ], response)


        }

        else if(params.value=='sessions' && params.fromSession && params.toSession){

//           println("getting the printable report of students of all course of sessions "+ params.fromSession+" and "+ params.toSession)
            def startSession=Integer.parseInt(params.fromSession)
            def endSession = Integer.parseInt(params.toSession)
            def totalList = reportService.getReportDataSessions(startSession, endSession)
//           println("back in controller with this "+ totalList)
            def sessionList=[]
            for(int i= startSession; i<=endSession;i++){
                def sessionVal= i+1
                sessionVal= i+'-'+sessionVal
                sessionList.add(sessionVal)
            }
            def args = [template: "generate", model: [totalListBySessions :totalList, sessionVal: sessionList, ],filename:params.session+'_All_Course'+".pdf"]
            pdfRenderingService.render(args + [controller: this], response)
            //   redirect( action: reportIndex)


        }

        else if(params.value=='course' && params.course){
            def webRootDir = servletContext.getRealPath("/")
            def userDir = new File(webRootDir,'/Report')
            userDir.mkdirs()
            def excelPath = servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_'+params.courseSession+'.xls'
//           println('this is the real path '+excelPath)
            def status = reportService.getReportDataCourse(params, excelPath)

            if(status){
                File myFile = new File(servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_'+params.courseSession+'.xls')
                response.setHeader "Content-disposition", "attachment; filename="+'"'+'Student_List_'+params.courseSession+".xls"+'"'
                response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
                response.outputStream << myFile .bytes
                response.outputStream.flush()
                myFile.delete()
            }
            else{
                flash.message = "${message(code: 'record.not.found.message')}"
                redirect(action: 'reportIndex')
            }


        }

        else if(params.value=='StudyCentreStudentDetails'){
            def webRootDir = servletContext.getRealPath("/")
            def userDir = new File(webRootDir,'/Report')
            userDir.mkdirs()
            def excelPath = servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_'+params.studyCentreStudentSession+'.xls'
            def status = reportService.getReportOfStudentDetails(params, excelPath)
            if(status){
                File myFile = new File(servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_'+params.studyCentreStudentSession+'.xls')
                response.setHeader "Content-disposition", "attachment; filename="+'"'+'Student_List_'+params.studyCentreStudentSession+".xls"+'"'
                response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
                response.outputStream << myFile .bytes
                response.outputStream.flush()
                myFile.delete()
            }
            else{
                flash.message = "${message(code: 'record.not.found.message')}"
                redirect(action: 'reportIndex')
            }
        }

        else if(params.value=='examinationCentre' && params.examCity){
            if(params.inExcel){
                def webRootDir = servletContext.getRealPath("/")
                def userDir = new File(webRootDir,'/Report')
                userDir.mkdirs()
                def excelPath = servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_'+params.examinationCentreSession+'.xls'
//               println('this is the real path '+excelPath)
                def status = reportService.getReportDataExaminationCentre(params, excelPath)

                if(status){
                    File myFile = new File(servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_'+params.examinationCentreSession+'.xls')
                    response.setHeader "Content-disposition", "attachment; filename="+'"'+'Student_List_'+params.studyCentreSession+".xls"+'"'
                    response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
                    response.outputStream << myFile .bytes
                    response.outputStream.flush()
                    myFile.delete()
                }
                else{
                    flash.message = "${message(code: 'record.not.found.message')}"
                    redirect(action: 'reportIndex')
                }
            }

            else{
                def totalList = reportService.getReportDataExaminationCentre(params, null)
                def sessionVal= Integer.parseInt(params.examinationCentreSession)+1
                def examinationCentre = City.findById(Long.parseLong(params.examCity))
                sessionVal= params.examinationCentreSession+'-'+sessionVal
                def args = [template: "generate", model: [totalListByExaminationCentre :totalList, examinationCentreSession:sessionVal, examinationCentre: examinationCentre.cityName],filename:'Student_List_'+params.examinationCentreSession+".pdf"]
                pdfRenderingService.render(args + [controller: this], response)
                // redirect( action: reportIndex)

            }
        }

        else if(params.value=='category' && params.categorySession){
            def totalList = reportService.getReportDataCategory(params)
            def sessionVal= Integer.parseInt(params.categorySession)+1
            sessionVal= params.categorySession+'-'+sessionVal
            def args = [template: "generate", model: [totalListByCategory :totalList, categorySession:sessionVal],filename:params.categorySession+'_All_Course_'+params.value+".pdf"]
            pdfRenderingService.render(args + [controller: this], response)
        }

        else if(params.value=='categoryGender' && params.categoryGenderSession){
//           println("this function is called")
            def totalList = reportService.getReportDataCategoryGender(params)
            def sessionVal= Integer.parseInt(params.categoryGenderSession)+1
            sessionVal= params.categoryGenderSession+'-'+sessionVal
            def args = [template: "generate", model: [totalListByCategoryGender :totalList, categoryGenderSession:sessionVal],filename:params.session+'_All_Course_'+params.value+".pdf"]
            pdfRenderingService.render(args + [controller: this], response)
            //redirect(action: 'reportIndex')
        }

        else if(params.value=='admissionUnapproved' && params.admissionUnapprovedSession || params.value=='admissionApproved' && params.admissionApprovedSession){
//           println("this function is called")
            def sessionVal
            def studyCentre=null
            if(params.admissionUnapprovedStudyCentre=='All' && params.admissionApprovedStudyCentre=='All') {
//               println('true')
                studyCentre = 'All'
            }
            def totalList = reportService.getReportDataAdmissionApprovedUnapproved(params)
            if(params.value=='admissionUnapproved'){
                sessionVal= Integer.parseInt(params.admissionUnapprovedSession)+1
                sessionVal= params.admissionUnapprovedSession+'-'+sessionVal
            }
            else if(params.value=='admissionApproved'){
                sessionVal= Integer.parseInt(params.admissionApprovedSession)+1
                sessionVal= params.admissionApprovedSession+'-'+sessionVal
            }
//           println('this is the value of total list '+ totalList)
            def args = [template: "generate", model: [totalListByAdmissionApprovedUnapproved :totalList, admissionApprovedUnapprovedSession:sessionVal, value:params.value, studyCentre:studyCentre],filename:sessionVal+'_All_Course_'+params.value+".pdf"]
            pdfRenderingService.render(args + [controller: this], response)
            // redirect(action: 'reportIndex')
        }

        else if(params.value=='admissionSelfRegistration' && params.admissionSelfRegistrationSession){
//           println("this function is called "+ params)
            def totalList = reportService.getReportDataAdmissionSelfRegistration(params)
            def sessionVal= Integer.parseInt(params.admissionSelfRegistrationSession)+1
            sessionVal= params.admissionSelfRegistrationSession+'-'+sessionVal
//           println('this is the list '+ totalList)
            def args = [template: "generate", model: [totalListBySelfRegistration :totalList, admissionSelfRegistrationSession:sessionVal],filename:params.admissionSelfRegistrationSession+'_All_Course_'+params.value+".pdf"]
            pdfRenderingService.render(args + [controller: this], response)
            // redirect(action: 'reportIndex')
        }

        else if( params.value=='dailyFeePaid' && params.feeFromDate && params.feeToDate){
//           println("this function is called")
            def totalList = reportService.getReportDataStudyCentreFeePaid(params)
            def feeType = FeeType.list()
//           totalList.size()
            def args = [template: "generate", model: [totalListByStudyCentreFeePaid :totalList, feeType: feeType, fromDate:params.feeFromDate, toDate:params.feeToDate],filename:'StudentList_'+params.value+".pdf"]
            pdfRenderingService.render(args + [controller: this], response)

        }

        else if( params.value=='paymentModeReport' && params.paymentModeFromDate && params.paymentModeToDate){
//           println("this function is called")
            def totalList = reportService.getReportDataPaymentMode(params)
            def paymentMode= PaymentMode.findById(Integer.parseInt(params.paymentMode))
            def args = [template: "generate", model: [totalListByPaymentMode :totalList, fromDate:params.paymentModeFromDate, toDate:params.paymentModeToDate, paymentMode:paymentMode.paymentModeName],filename:'StudentList_'+params.value+".pdf"]
            pdfRenderingService.render(args + [controller: this], response)

        }

        else if(params.value=='sessionsComparative' && params.fromSessionComparative && params.toSessionComparative){
//           println("this function is called")
            def startSession=Integer.parseInt(params.fromSessionComparative)
            def endSession = Integer.parseInt(params.toSessionComparative)
            def totalList = reportService.getReportDataComparative(startSession, endSession)
            def sessionList=[]
            for(int i= startSession; i<=endSession;i++){
                def sessionVal= i+1
                sessionVal= i+'-'+sessionVal
                sessionList.add(sessionVal)
            }
            def args = [template: "generate", model: [totalListBySessionComprative :totalList, sessionVal:sessionList],filename:params.session+'_All_Course_'+params.value+".pdf"]
            pdfRenderingService.render(args + [controller: this], response)
            redirect(action: 'reportIndex')
        }

        else if(params.value=='categoryStudentList' && params.categoryStudentListSession){
            def webRootDir = servletContext.getRealPath("/")
            def userDir = new File(webRootDir,'/Report')
            userDir.mkdirs()
            def excelPath = servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_'+params.studentCategory+"_"+params.studyCentreSession+'.xls'
//           println('this is the real path '+excelPath)
            def status = reportService.getReportDataStudentCategory(params, excelPath)

            if(status){
                File myFile = new File(servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'Student_List_'+params.studentCategory+"_"+params.studyCentreSession+'.xls')
                response.setHeader "Content-disposition", "attachment; filename="+'"'+'Student_List_'+params.studentCategory+"_"+params.studyCentreSession+".xls"+'"'
                response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
                response.outputStream << myFile .bytes
                response.outputStream.flush()
                myFile.delete()
            }
            else{
                flash.message = "${message(code: 'record.not.found.message')}"
                redirect(action: 'reportIndex')
            }

        }

        else if(params.value=='courseUnapproved' && params.courseUnapprovedSession || params.value=='courseApproved' && params.courseApprovedSession){
//           println("this function is called")
            def program= null
            def totalList = reportService.getReportDataCourseApprovedUnapproved(params)
            def sessionVal= Integer.parseInt(params.courseApprovedSession)+1
            sessionVal= params.courseApprovedSession+'-'+sessionVal
            def type
            if(params.value=='courseUnapproved') {
                type = 'Unapproved'
                if(params.courseUnapproved!='All')
                    program = ProgramDetail.findById(Long.parseLong(params.courseUnapproved))
            }
            else {
                type='Approved'
                if(params.courseUnapproved!='All')
                    program = ProgramDetail.findById(Long.parseLong(params.courseApproved)).courseName
            }
            def args = [template: "generate", model: [totalListApprovedUnapprovedRollNo :totalList, approvedUnapprovedSessionVal:sessionVal, type:type, program:program],filename:params.session+'_All_Course_'+params.value+".pdf"]
            pdfRenderingService.render(args + [controller: this], response)
            // redirect(action: 'reportIndex')
        }
//have to complete*****************************************************************
        else if(params.value=='examinationCentreCumulative' && params.examinationCentreCumulativeSchedule){
//           println("this cumulative function is called")
            def totalList = reportService.getReportDataExaminationCentreCumulative(params)
            def sessionVal= Integer.parseInt(params.courseApprovedSession)+1
            sessionVal= params.courseApprovedSession+'-'+sessionVal
            def args = [template: "generate", model: [totalListApprovedUnapprovedRollNo :totalList, approvedUnapprovedSessionVal:sessionVal],filename:params.session+'_All_Course_'+params.value+".pdf"]
            pdfRenderingService.render(args + [controller: this], response)
            //  redirect(action: 'reportIndex')

        }

        else if(params.studentIntake=='studentIntake'){
            /*Report for Student Intake AICTE*/
            def webRootDir = servletContext.getRealPath("/")
            def userDir = new File(webRootDir,'/Report')
            userDir.mkdirs()
//         def progInst=ProgramDetail.findById(Long.parseLong(params.programId))
            def excelPath = servletContext.getRealPath("/")+'/Report'+System.getProperty('file.separator')+'Student_Intake_'+'.xls'
//           println('this is the real path '+excelPath)
            def status = reportService.getReportStudentIntake(excelPath)

            if(status){
                File myFile = new File(servletContext.getRealPath("/")+'/Report'+System.getProperty('file.separator')+'Student_Intake_'+'.xls')
                response.setHeader "Content-disposition", "attachment; filename="+'"'+'Student_Intake_'+".xls"+'"'
                response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
                response.outputStream << myFile .bytes
                response.outputStream.flush()
                myFile.delete()
            }
            else{
                flash.message = "${message(code: 'record.not.found.message')}"
                redirect(action: 'reportIndex')
            }


        }

        else if(params.value=='studentInfo'&& params.multiProgram && params.multiDepartment && params.multiSem && params.mGender){
            /*Report for Student Information*/
            println("+++sanjay++"+params)
            def finalMap = reportService.getReportStudentInfo(params)
            render finalMap as JSON

        }


        else if((params.value=='empDetails' && params.employeeName==''&& params.programDepartment && params.gender)||(params.value=='empDetails' && params.employeeName && params.programDepartment)){
            /*Report for Employee Details*/
            def finalMap = reportService.getReportEmployeeInfo(params)
            render finalMap as JSON
        }

        else if(params.value=='passoutStudents' && params.programType && params.programBranch && params.gender){
            /*Report for passout Student*/
//            def webRootDir = servletContext.getRealPath("/")
//            def userDir = new File(webRootDir,'/Report')
//            userDir.mkdirs()
//            def excelPath = servletContext.getRealPath("/")+'/Report'+System.getProperty('file.separator')+'PassOut_Students_'+'.xls'
            def finalMap = reportService.getReportPassoutStudents(params)
            render finalMap as JSON
        }

        else{
            redirect(action: 'reportIndex')
        }
    }

}
