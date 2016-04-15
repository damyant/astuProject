package postexamination

import postexamination.ExamSchedule
import examinationproject.AdmitCard
import examinationproject.ProgramDetail
import examinationproject.ProgramSession
import examinationproject.ProgramType
import examinationproject.Semester
import examinationproject.Status
import examinationproject.Student
import examinationproject.StudentAdmitCard
import examinationproject.StudentSubject
import examinationproject.SubjectSession
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import java.text.DateFormat
import java.text.SimpleDateFormat

class ExamController {
    def springSecurityService
    def pdfRenderingService
    def examService

    def index() {}
    def assignExamDate(){
        def programList = ProgramDetail.list()
        Calendar now = Calendar.getInstance()
        int year = now.get(Calendar.YEAR)
        def yearList = [year,year+1]
        [programList: programList, yearList:yearList]

    }
    def getSemesterList = {
        try {
            if (params.data == 'allProgram') {
                def course = ProgramDetail.executeQuery('select max(noOfTerms) from ProgramDetail')
                def sessions = ProgramSession.executeQuery("select distinct  programSession.sessionOfProgram from ProgramSession programSession");
                def resultMap = [:]
                resultMap.totalSem = course[0]
                resultMap.session = sessions
                render resultMap as JSON
            } else {
                def course = ProgramDetail.findById(Integer.parseInt(params.data))
                def resultMap = [:]
                if (course != null) {
                    def programSession = ProgramSession.findAllByProgramDetailId(course)
                    resultMap.totalSem = course.noOfTerms
                    resultMap.session = programSession

                    render resultMap as JSON
                } else {
                    render null
                }
            }
        } catch (Exception e) {
            println("Error in getting Semester Number" + e)

        }

    }

    def getSubjectList = {
        if (springSecurityService.currentUser) {

            def subMap = [:]
            subMap = examService.subjectList(params)
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
            def status = examService.saveExamDate(params)
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
    def admitCard(){
        Calendar now = Calendar.getInstance()
        int year = now.get(Calendar.YEAR)
        def yearList = [year,year+1]
        [ yearList:yearList]
    }

    def loadTermAndVenueFromRollNo = {
        def returnMap = [:]
        def stuInst = Student.findByRollNo(params.data)
//        def examVenue = ProgramExamVenue.findAllByCourseDetailAndExamCenter(stuInst.programDetail, stuInst.city)
//        def catgId = stuInst.programDetail.programType.id
        def term
//        if (catgId == '1') {
//            term = stuInst.programDetail.noOfAcademicYears
//        } else {
//            term = stuInst.programDetail.noOfTerms
//        }
        term = stuInst.program.noOfTerms
        returnMap.term = term
//        returnMap.examVenue = examVenue.examVenue
        render returnMap as JSON
    }
    @Secured(["ROLE_ADMIN"])
    def generateSingleAdmitCard = {
        println("````````````````````````````````````````````````````````````````")
        def stuList = [], mode = [], examType = [], courseName = [],pictureList=[],semesterList=[]
        def status
//        , feeNotPaid=false
        def month = "", year = ''
        def admitInst = null
        def user = springSecurityService.currentUser
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder examDate = new StringBuilder()
        StringBuilder examTime = new StringBuilder()
        def webRootDir = servletContext.getRealPath("/")
        def byte[] logo = new File(webRootDir + "/images/Guwahati.jpg").bytes
        def student = Student.findByRollNo(params.rollNoForFeeStatus)

            pictureList<<''

            def progDet = ProgramDetail.findById(student.program.id)
//            def programSessionIns = ProgramSession.findById(stuList[0].programSession.id)
            def programSessionIns = ProgramSession.findByProgramDetailId(progDet)

            def semInst=Semester.findBySemesterNoAndProgramSession(student.semester, programSessionIns)
            def currSubjects = StudentSubject.findAllByStudentAndSemester(student,semInst).subjectSession
//            def subjectSessionList = CourseSubject.findAllBySemesterAndProgramSession(semInst, programSessionIns)*.subjectSessionId
            def subjectSessionList = ExamSchedule.findAllBySemesterAndProgramSessionAndYear(semInst, programSessionIns,params.examYear)*.subjectSession

            def showSubjects = []
               currSubjects.each{
                   def stusub=it
                   subjectSessionList.each {
                       if(stusub.id==it.id){
                           showSubjects << it
                       }

                   }

               }


            println("===================================="+subjectSessionList)
            def dateList = ['Exam Date'], timeList = ['Exam Time'], courseList= ['Subject Code']
            if (showSubjects) {
                showSubjects.each {
//                    dateList << CourseSubject.findBySubjectSessionIdAndProgramSession(it, programSessionIns).examDate
                    dateList << ExamSchedule.findBySubjectSessionAndProgramSession(it, programSessionIns).examDate
//                    timeList << CourseSubject.findBySubjectSessionIdAndProgramSession(it, programSessionIns).examTime
                    timeList << ExamSchedule.findBySubjectSessionAndProgramSession(it, programSessionIns).examTime
                    courseList << ExamSchedule.findBySubjectSessionAndProgramSession(it, programSessionIns).subjectSession.subjectId.subjectCode
                }
            }
//            def programGroupList = ProgramGroup.findAllBySemesterAndProgramSession(semInst, programSessionIns)
//            println("================programGroupList==========="+programGroupList)
            println("================dateList==========="+dateList)
//            if (programGroupList) {
//                programGroupList.each {
//                    def subGroupList = ProgramGroupDetail.findAllByProgramGroupId(it)
//                    subGroupList.each { itr ->
//                        if(!(dateList.contains(itr.examDate))){
//                            dateList << itr.examDate
//                            timeList << itr.examTime
//                        }
//                        else{
//                            if(!(timeList[dateList.indexOf(itr.examDate)]==itr.examTime)){
//                                dateList << itr.examDate
//                                timeList << itr.examTime
//                            }
//                        }
//                    }
//                }
//            }
            println("================dateList==========="+dateList)
            def count = 1
            SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
            dateList.each {
                if (it != 'Exam Date' && it !=null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dateParser.parse(it.toString()));
                    if (year == '') {
                        year = cal.get(Calendar.YEAR);
                    }
                    java.util.Date d = new java.util.Date(cal.getTimeInMillis());
                    if (month == "") {
                        month = new SimpleDateFormat("MMMM").format(d)
                    }
                }
            }

            def examSession = month + "-" + year
//            def studentAdmitInst = StudentAdmitCard.findByExaminationSessionAndSemesterAndExaminationVenueAndStudent(examSession, semInst, student.examinationVenue, student)
            def studentAdmitInst = StudentAdmitCard.findByExaminationSessionAndSemesterAndStudent(examSession, semInst,student)
            if (!studentAdmitInst) {
                studentAdmitInst = new StudentAdmitCard()
                studentAdmitInst.semester = semInst
                studentAdmitInst.examinationSession = examSession

//                studentAdmitInst.examinationVenue = student.examinationVenue
                studentAdmitInst.student = student
                studentAdmitInst.date = new Date()
                studentAdmitInst.save(flush: true)
            }

            def total=dateList.size()
//            def progCourseName = student.program[0].courseName
            def progCourseName = student.program.courseName
            def subSt="(Lt)"
//           println("@@@@@@@@@@@@"+student.programDetail[0].courseMode.id)
           /* if ((progCourseName.contains(subSt)) && student.program[0].courseMode.id== Long.parseLong("1")){
                semesterList << Integer.parseInt(params.semesterList) + 2
            }
            else if ((progCourseName.contains(subSt)) && student.program[0].courseMode== Long.parseLong("2")){
                semesterList << Integer.parseInt(params.semesterList) + 1
            }*/
//            else {
                semesterList<<student.semester
//            }



//ABC            def session = stuList[0].programSession.sessionOfProgram.split("-")
// ABC           def session = student.programSession.sessionOfProgram.split("-")
//ABC            def fileName = stuList[0].programDetail[0].courseName + " " + month + " " + session[0]
            def fileName = student.program.courseName + " " + month
//        + session[0]

            if (dateList[0] == '' || timeList[0] == null || dateList[0] == null) {
                flash.message = "Examination Date Not Assigned Yet"
                redirect(controller: 'exam', action: 'SingleAdmitCardGenerate')
            } else {
//                status = admitCardService.updateStudentRecord(stuList, params.examinationVenue)
                /*def args = [template: "printAdmitCard", model: [semesterList:semesterList,pictureList:pictureList,studentInstance: stuList, dateCount: total, examTime: timeList, courseName: courseName, examType: examType, examination: params.examType, examDate: dateList, year: year, guLogo: logo, admitInst: admitInst], filename: fileName + ".pdf"]
                pdfRenderingService.render(args + [controller: this], response)
*/
                def args = [template: "printSingle", model: [semesterList:semesterList,pictureList:pictureList,studentInstance: student, dateCount: total, examTime: timeList,courseList:courseList, courseName: courseName, examType: examType, examination: params.examType, examDate: dateList, year: year, guLogo: logo, admitInst: admitInst], filename: fileName + ".pdf"]
                pdfRenderingService.render(args + [controller: this], response)
            }
        }

    def bulkAdmit(){
        def programList = ProgramDetail.list()
        Calendar now = Calendar.getInstance()
        int year = now.get(Calendar.YEAR)
        def yearList = [year,year+1]
        [programList: programList, yearList:yearList]
    }

    def generateBulkAdmit={
        def studentList = examService.getStudents(params)
        println("STUDENTS"+studentList)
        if (studentList) {
            def stuList = [], examType = [], courseName = [],semesterList = [],examDateList=[],examTimeList=[],examCourseList=[],totalDateSize=[]
//                def status
            def month = "", year = ''
            def admitInst = null
//                def user = springSecurityService.currentUser
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
            StringBuilder examDate = new StringBuilder()
            StringBuilder examTime = new StringBuilder()
            def webRootDir = servletContext.getRealPath("/")
            def byte[] logo = new File(webRootDir + "/images/Guwahati.jpg").bytes

            def progDet = ProgramDetail.findById(Long.parseLong(params.programList))
            def programSessionIns = ProgramSession.findByProgramDetailId(progDet)

            def semInst = Semester.findBySemesterNoAndProgramSession(Integer.parseInt(params.programTerm), programSessionIns)
            def subjectSessionList = ExamSchedule.findAllBySemesterAndProgramSessionAndYear(semInst, programSessionIns,params.examYear)*.subjectSession
            studentList.each {

                def student = it

//            pictureList<<''

                def currSubjects = StudentSubject.findAllByStudentAndSemester(student, semInst).subjectSession
//                def subjectSessionList = ExamSchedule.findAllBySemesterAndProgramSession(semInst, programSessionIns)*.subjectSession

                def showSubjects = []
                currSubjects.each {
                    def stusub = it
                    subjectSessionList.each {
                        if (stusub.id == it.id) {
                            showSubjects << it
                        }

                    }

                }


                println("====================================" + subjectSessionList)
                def dateList = ['Exam Date'], timeList = ['Exam Time'], courseList = ['Subject Code']
                if (showSubjects) {
                    showSubjects.each {
                        dateList << ExamSchedule.findBySubjectSessionAndProgramSession(it, programSessionIns).examDate
                        timeList << ExamSchedule.findBySubjectSessionAndProgramSession(it, programSessionIns).examTime
                        courseList << ExamSchedule.findBySubjectSessionAndProgramSession(it, programSessionIns).subjectSession.subjectId.subjectCode
                    }
                }
                totalDateSize << dateList.size()
                examDateList << dateList
                examTimeList << timeList
                examCourseList << courseList
                println("================dateList===========" + dateList)
                def count = 1
                SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
                dateList.each {
                    if (it != 'Exam Date' && it != null) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(dateParser.parse(it.toString()));
                        if (year == '') {
                            year = cal.get(Calendar.YEAR);
                        }
                        java.util.Date d = new java.util.Date(cal.getTimeInMillis());
                        if (month == "") {
                            month = new SimpleDateFormat("MMMM").format(d)
                        }
                    }
                }

                def examSession = month + "-" + year
                def studentAdmitInst = StudentAdmitCard.findByExaminationSessionAndSemesterAndStudent(examSession, semInst, student)
                if (!studentAdmitInst) {
                    studentAdmitInst = new StudentAdmitCard()
                    studentAdmitInst.semester = semInst
                    studentAdmitInst.examinationSession = examSession
                    studentAdmitInst.student = student
                    studentAdmitInst.date = new Date()
                    studentAdmitInst.save(flush: true)
                }
                    stuList << student
//                def total = dateList.size()
//                def progCourseName = student.program.courseName
//                def subSt = "(Lt)"

                semesterList << student.semester

//                def fileName = student.rollNo + " " + month
//
//                if (dateList[0] == '' || timeList[0] == null || dateList[0] == null) {
//                    flash.message = "Examination Date Not Assigned Yet"
//                    redirect(controller: 'exam', action: 'SingleAdmitCardGenerate')
//                } else {
//                    def args = [template: "printBulk", model: [semesterList: semesterList, pictureList: pictureList, studentInstance: stuList, dateCount: total, examTime: timeList, courseName: courseName, examType: examType, examination: params.examType, examDate: dateList, year: year, guLogo: logo, admitInst: admitInst], filename: fileName + ".pdf"]
//                    pdfRenderingService.render(args + [controller: this], response)
//                }
            }
            if (stuList[0]) {
                def total = examDateList.size()
                def finalListSize = totalDateSize.size()
//                def session = stuList[0].programSession.sessionOfProgram.split("-")
//                def fileName = stuList[0].program.courseName + " " + month
                   def fileName = progDet.courseName
                DateFormat yr = new SimpleDateFormat("yyyy");
                if (examDateList[0] == '' || examTimeList[0] == null || examDateList[0] == null || examDateList.size() <= 1) {
                    flash.message = "Examination Date Not Assigned Yet"
                    redirect(controller: 'exam', action: 'bulkAdmit')
                } else {
                        def args = [template: "printBulk", model: [semesterList:semesterList,studentInstance: stuList,examCourseList:examCourseList, examTime: examTimeList, dateCount: totalDateSize, courseName: courseName, examType: examType, examination: params.examType, examDate: examDateList, year: year, guLogo: logo, admitInst: admitInst], filename: fileName + ".pdf"]
                        pdfRenderingService.render(args + [controller: this], response)

                }
            }
        }
        else {
            flash.message="No Roll Number Found"
        }
    }

}
