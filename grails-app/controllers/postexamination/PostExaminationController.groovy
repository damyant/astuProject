package postexamination

import com.university.Role

import com.university.UserRole
import examinationproject.*
import universityproject.*
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.web.context.ServletContextHolder

//import grails.plugin.jxl.Cell
import javax.activation.MimetypesFileTypeMap
import java.text.DateFormat
import java.text.SimpleDateFormat


class PostExaminationController {
    def pdfRenderingService
    def adminInfoService
    def marksEnteringService
    def postExaminationService
    def springSecurityService

    def createMarksFoil = {
        def programList = ProgramSession.list()
        [programList: programList]
    }
    @Secured(["ROLE_EXAM_ADMIN"])
    def markMismatchReport = {


    }
    @Secured(["ROLE_EXAM_ADMIN"])
    def marksUpdation = {
        def programList = ProgramDetail.list(sort: 'courseCode')
        def marksTypeList = MarksType.list()
        [programList: programList, marksTypeList: marksTypeList]
    }


    def getCourseData = {

        def subjectIns = []
        try {
            subjectIns = marksEnteringService.getCourseDetail(params)
        }
        catch (Exception e) {
            println("<<<<< Problem in getting subjects" + e)
        }
        render subjectIns as JSON
    }

    def getSemesterOfProgram = {
        def semesterIns = []
        try {
            semesterIns = Semester.findAllByProgramSession(ProgramSession.get(Integer.parseInt(params.program)))
        }
        catch (Exception e) {
            println("<<<<<<<<< Problem in getting semester of Program" + e)
        }
        render semesterIns as JSON
    }

    def semesterForMarksMisMatch = {
        def semesterIns = []
        try {
            semesterIns = Semester.findAllByProgramSession(ProgramSession.get(Integer.parseInt(params.session)))
        }
        catch (Exception e) {
            println("<<<<<<<<< Problem in getting semester of Program" + e)
        }
        render semesterIns as JSON

    }

    def getGroup = {

        def groupIns = []
        try {
            groupIns = marksEnteringService.getGroupDetail(params)
        }
        catch (Exception e) {
            println("<<<<< Problem in getting groups on semester" + e)
        }
        render groupIns as JSON
    }

    def generateMarksFoilSheet = {

        try {
            if (params.btn == "pdf") {
                def course = ProgramDetail.findById(Integer.parseInt(params.programId)).courseName
                def programSession = ProgramSession.get(Integer.parseInt(params.programSessionId))
                def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.semester))
                def subjectName = Subject.get(Integer.parseInt(params.courseCode)).subjectName
                def studentList = postExaminationService.dataForMarksFoilSheetPdf(params, semester)
                if (studentList) {
                    def args = [template: "generateMarksFoil", model: [program: course, semester: semester.semesterNo, subjectName: subjectName, stuList: studentList], filename: "MarksFoilSheet.pdf"]
                    pdfRenderingService.render(args + [controller: this], response)
                } else {
                    flash.message = "No Roll Number Found"
                    redirect(controller: 'postExamination', action: 'createMarksFoil')
                }

            } else {
                def webRootDir = servletContext.getRealPath("/")
                def userDir = new File(webRootDir, '/Report')
                userDir.mkdirs()
                def excelPath = servletContext.getRealPath("/") + 'Report' + System.getProperty('file.separator') + 'MarksFoilSheet.xls'
                def status = postExaminationService.getMarksFoilData(params, excelPath)

                if (status) {
                    File myFile = new File(servletContext.getRealPath("/") + 'Report' + System.getProperty('file.separator') + 'MarksFoilSheet.xls')
                    //response.setHeader "Content-disposition", "attachment; filename="+'Student_List_'+params.session+".xls"
                    response.setHeader "Content-disposition", "attachment; filename=" + 'MarksFoilSheet' + ".xls"
                    response.contentType = new MimetypesFileTypeMap().getContentType(myFile)
                    response.outputStream << myFile.bytes
                    response.outputStream.flush()
                    myFile.delete()
                } else {
                    flash.message = "No Roll Number Found"
                    redirect(controller: 'postExamination', action: 'createMarksFoil')
                }

            }
        }
        catch (Exception e) {
            println("<<<<<<<< Problem in generating marks foil sheet" + e)
        }

    }

    @Secured(["ROLE_ADMIN", "ROLE_INSTRUCTOR"])
    def marksEntering = {
        /*for entering marks for particular subject in particular session*/
//        def criteria = Student.createCriteria()
//        def admissionYear = criteria.listDistinct() {
//            projections {
//                distinct("registrationYear")
//            }
//            order("registrationYear", "desc")
//        }
//        def academicSession = SemesterFeeDetails.createCriteria().list {
//            projections {
//                distinct("academicSession")
//            }
//            order("academicSession", "desc")
//        }
//        def c = Examination.createCriteria()
//        def examList = c.list(params) {
//            order("academicSession", "desc")
//        }
//        def programList = ProgramDetail.list(sort: 'courseCode')
//        def marksTypeList = MarksType.list()
//        [ admissionYear: admissionYear]
    }


    def getRollNoList = {
        /*getting list of students enrolled in particular examination*/
        def finalList = []
        finalList = marksEnteringService.getRollNumbers(params)
        render finalList as JSON
    }

    @Secured(["ROLE_EXAM_ADMIN"])
    def resultProcessing = {
        def programList = ProgramDetail.list(sort: 'courseCode')
        [programList: programList]
    }

    def finalResult = {
        def programList = ProgramDetail.list(sort: 'courseCode')
        [programList: programList]
    }

    def bulkMarksSheet = {
        def academicSession = StudentEvaluatedMarks.findAll().academicSession.unique()
        [academicSession:academicSession]
    }

    def singleMarksSheet = {
    }

    def loadSemesterFromRollNo = {
        def program = Student.findByRollNo(params.rollNo).program
        def progSession = ProgramSession.findByProgramDetailId(program)
        def semList = Semester.findAllByProgramSession(progSession)
        render semList as JSON
    }

    def loadSemesterFromProgram = {
        def program = ProgramDetail.findById(Long.parseLong(params.prog))
        def progSession = ProgramSession.findByProgramDetailId(program)
        def semList = Semester.findAllByProgramSession(progSession)
        render semList as JSON

    }
    def saveMarks = {
        /* saving marks for particular student and examiantion*/
        def result = marksEnteringService.saveMarks(params)
        if (result.status) {
            render result as JSON
        }

    }

    def xmlCreateData() {
        def result = marksEnteringService.createXMLFile(params)

    }
    def xmlParseData = {
        XmlParser parser = new XmlParser()
        def xmlData = parser.parse(new FileInputStream("web-app/subjectPassMarks/subjectMarksRule.xml"))
        return xmlData

    }

    def loadMarksType() {
        def returnMap = [:]
        def marksTypeObj = []
        def subMarksInst = SubjectMarksDetail.findBySubjectSession(SubjectSession.findBySubjectId(Subject.findById(params.subjectID)))
        if (subMarksInst.theory) {
            marksTypeObj << MarksType.findById(1)
        }
        if (subMarksInst.practical) {
            marksTypeObj << MarksType.findById(2)
        }
        if (subMarksInst.project) {
            marksTypeObj << MarksType.findById(3)
        }
        returnMap.marksTypeList = marksTypeObj
        render returnMap as JSON
    }

    def getSemesterForMarksUpdate = {
        def returnMap = [:]
        def semesterList = []
        def session = ProgramSession.findById(params.session).sessionOfProgram
        def programSession = ProgramSession.findByProgramDetailIdAndSessionOfProgram(ProgramDetail.findById(Integer.parseInt(params.program)), session)
        semesterList = Semester.findAllByProgramSession(programSession)

        returnMap.semesterList = semesterList
        render returnMap as JSON
    }


    def getStudentSession = {
        def criteria = Student.createCriteria()
        def stuSessionList = criteria.list() {
            projections {
                distinct("registrationYear")
            }
        }

        render stuSessionList as JSON
    }


    def marksMissMatchUpdate = {
        def finalList = postExaminationService.getRollNoForMisMatchUpdate(params)
        render finalList as JSON
    }

    def loadTabulatorMarks = {
        def finalList = postExaminationService.getTabulatorMarks(params)
        render finalList as JSON
    }


    def generateFinalResult = {
        def progSessionInst = ProgramSession.findById(Long.parseLong(params.sessionId))
        def semesterInst = Semester.findById(Long.parseLong(params.semesterId))
//        def studentList = Student.findAllByProgramSessionAndSemester(progSessionInst, semesterInst.semesterNo)

        def result = postExaminationService.finalResult(params)
        if (result.status) {
            def args = [template: "finalMeritList", model: [studentMeritList: result.studentMeritList, totalStudentsAppeared: result.totalStudentsAppeared, courseName: progSessionInst.programDetailId.courseName, semester: semesterInst.semesterNo],
                        filename: progSessionInst.programDetailId.courseName + ".pdf"]
            pdfRenderingService.render(args + [controller: this], response)
        } else {
            flash.message = result.msg
            redirect(controller: 'postExamination', action: 'generateFinalResult')
        }

    }


    def getProgramSemester = {
        def programInst = ProgramDetail.findById(Long.parseLong(params.program))
        def semesterList = Semester.findAllByProgramSession(ProgramSession.findByProgramDetailId(programInst))
        render semesterList as JSON
    }
    def getCourses = {
        def programInst = ProgramDetail.findById(Long.parseLong(params.program))
        def SemInst = Semester.findById(Long.parseLong(params.semester))
        def subjectList = CourseSubject.findAllByCourseDetailAndSemester(programInst, SemInst).subjectSessionId.subjectId
        render subjectList as JSON
    }

    def createCourseExam = {
        /*Action for creating and updating examination for particular subject*/
        if (springSecurityService.currentUser) {
            def examTypeList = ExamType.list()
            def academicSession = SemesterFeeDetails.createCriteria().list {
                projections {
                    distinct("academicSession")
                }
                order("academicSession", "desc")
            }
            if (params.examId) {
                def examInst = Examination.findById(Long.parseLong(params.examId))
                [examInst: examInst, examTypeList: examTypeList, academicSession: academicSession]
            } else {
                [examTypeList: examTypeList, academicSession: academicSession]
            }

        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def loadCourseDetailsFromCode() {
        /*This action load subject(course) details using subject(course) code*/
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def courseInst = Subject.findBySubjectCode(params.courseCode)
            def academicSessionList = StudentSubject.findAllBySubjectSession(SubjectSession.findBySubjectId(courseInst)).academicSession.unique()
            returnMap.courseInst = courseInst
            returnMap.academicSessionList = academicSessionList.reverse()
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def saveCourseExam() {
        /*Save details of examination (Subject,ExamType,ExamSubType and marks etc)*/
        if (springSecurityService.currentUser) {
            def result = postExaminationService.saveCourseExamination(params)
            if (result == 0) {
                flash.message = "Unable to Update/Save the Examination."
                redirect(controller: "postExamination", action: "createCourseExam")
            } else if (result == 1) {
                flash.message = "The Examination Already Exist."
                redirect(controller: "postExamination", action: "createCourseExam")
            } else if (result == 2) {
                flash.message = "The Examination Saved Successfully."
                redirect(controller: "postExamination", action: "createCourseExam")
            } else if (result == 3) {
                flash.message = "The Examination Updated Successfully."
                redirect(controller: "postExamination", action: "listOfExam")
            }

        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def listOfExam() {
        /*Action for showing list of examination created*/
        if (springSecurityService.currentUser) {
            def examList = []
            def c = Examination.createCriteria()
            def examLists = c.list() {
                order("academicSession", "desc")
            }
            examLists.each {
                if (ExamType.findById(Long.parseLong(it.examTypes))) {
                    if (it.examSubType != null) {
                        if (ExamSubType.findById(Long.parseLong(it.examSubType))) {
                            examList << it
                        } else {
                            it.delete(flush: true)
                        }
                    } else {
                        examList << it
                    }
                } else {
                    it.delete(flush: true)
                }
            }
            [examList: examList, examListTotal: Examination.count()]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def deleteExamination = {
        /*Action for deleting examination for particular subject and exam Type*/
        if (springSecurityService.currentUser) {
            def result = postExaminationService.deleteCourseExamination(params)
            if (result) {
                flash.message = "The Examination Deleted Successfully."

            } else {
                flash.message = "Unable to Deleted The Examination Successfully."
            }
            redirect(controller: "postExamination", action: "listOfExam")
        } else {
            redirect(controller: "login", action: "auth")

        }
    }
    def getProgramSessionSemester = {
        if (springSecurityService.currentUser) {
            def programInst = ProgramDetail.findById(Long.parseLong(params.program))
            def programSession = ProgramSession.findByProgramDetailId(programInst)
            def semList = StudentSubject.findAllByProgramSessionAndAcademicSession(programSession, params.academicSession, [sort: "semester"]).semester.unique()
            render semList as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }


    def getProgramSessionSemesterCourse() {
        if (springSecurityService.currentUser) {
            def programInst = ProgramDetail.findById(Long.parseLong(params.program))
            def programSession = ProgramSession.findByProgramDetailId(programInst)
            def semesterInst = Semester.findById(Long.parseLong(params.semester))
            def subjectList = StudentSubject.findAllByProgramSessionAndAcademicSessionAndSemester(programSession, params.academicSession, semesterInst, [sort: "subjectSession"]).subjectSession.subjectId.unique()
            render subjectList as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def getExamTypeBySession() {
        if (springSecurityService.currentUser) {
            def courseInst = Subject.findById(Long.parseLong(params.course))
            def examList = Examination.findAllByAcademicSessionAndSubject(params.academicSession, courseInst).examTypes.unique()
            render examList as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def getExamSubTypeBySession() {
        if (springSecurityService.currentUser) {
            def courseInst = Subject.findById(Long.parseLong(params.course))
            def examTypeInst = ExamType.findById(Long.parseLong(params.examType))
            def examSubTypeList = Examination.findAllByAcademicSessionAndSubjectAndExamTypes(params.academicSession, courseInst, examTypeInst).examSubType.unique()
            render examSubTypeList as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def getExaminationBySession() {
        /*  action for getting examination by session*/
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def courseInst = Subject.findBySubjectCode(params.course)
            def examList = Examination.findAllByAcademicSessionAndSubject(params.academicSession, courseInst)
            returnMap.idList = examList.id
            def examListName = []
            examList.each {
                if (it.examSubType != null) {
                    examListName << ExamSubType.findById(it.examSubType).examSubTypeName
                } else {
                    examListName << ExamType.findById(it.examTypes).examTypeName
                }
            }
            if (examList.examSubType)
                returnMap.SubTypeName = examListName
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def getTotalMarks() {
        /*for getting total marks of particualr examination*/
        if (springSecurityService.currentUser) {
            def examList = Examination.findById(Long.parseLong(params.exam))
            render examList as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    @Secured(["ROLE_ADMIN", "ROLE_INSTRUCTOR"])
    def marksEvaluation() {
        /*Action for interface to evaluate marks */
        if (springSecurityService.currentUser) {
            def programList = ProgramDetail.list(sort: 'courseName')
            def academicSession = SemesterFeeDetails.createCriteria().list {
                projections {
                    distinct("academicSession")
                }
                order("academicSession", "desc")
            }
            [programList: programList, academicSession: academicSession]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    @Secured(["ROLE_ADMIN", "ROLE_INSTRUCTOR"])
    def gradeConversion() {
        /*Action for grade conversion interface*/
        if (springSecurityService.currentUser) {
            def programList = ProgramDetail.list(sort: 'courseName')
            def academicSession = SemesterFeeDetails.createCriteria().list {
                projections {
                    distinct("academicSession")
                }
                order("academicSession", "desc")
            }
            [programList: programList, academicSession: academicSession]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    @Secured(["ROLE_ADMIN", "ROLE_INSTRUCTOR"])
    def marksApproval() {
        if (springSecurityService.currentUser) {

        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def loadExamSubType() {
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def subInst = Subject.findBySubjectCode(params.subjectCode)
            def examTypeInst = ExamType.findById(Long.parseLong(params.examType))
            def examSubTypeList = ExamSubType.findAllByExamTypeId(examTypeInst.id.toString())
            if (examTypeInst.requiredTotalMarks != null) {
                returnMap.maxMarks = examTypeInst.requiredTotalMarks
            }
            if (examSubTypeList) {
                returnMap.examSubTypeList = examSubTypeList
                returnMap.isExist = true
            } else {
                returnMap.isExist = false
                if (examTypeInst.requiredTotalMarks != null && examTypeInst.requiredTotalMarks != '') {
                    def examTotalMarks = 0
                    def examList = Examination.findAllByExamTypesAndSubjectAndAcademicSession(examTypeInst.id.toString(), subInst, params.academicSession)
                    examList.each {
                        examTotalMarks += Integer.parseInt(it.totalMarks)
                    }
                    returnMap.requiredTotalMarks = examTypeInst.requiredTotalMarks
                    returnMap.examTotalMarks = examTotalMarks.toString()
                    returnMap.examTotalRemainingMarks = (Integer.parseInt(examTypeInst.requiredTotalMarks) - examTotalMarks).toString()
                }
            }
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def xmlParseRuleInfo = {
        XmlParser parser = new XmlParser()
        def docDirectory = ServletContextHolder.servletContext.getRealPath(System.getProperty("file.separator"))
        def dir = docDirectory + System.getProperty("file.separator") + "ruleEngine" + System.getProperty("file.separator") + "Rule.xml"
        def xmlData = parser.parse(new FileInputStream(dir))
        return xmlData
    }

    def performMarksEvaluation() {
        /*Action for evaluating marks according to rule */
        if (springSecurityService.currentUser) {
            def subjectInst = Subject.findBySubjectCode(params.subjectCode)
            def examinationList = Examination.findAllByAcademicSessionAndSubject(params.academicSession, subjectInst)
            def examTypeList = examinationList.examTypes.unique()
            def status = true
            examTypeList.each {
                def reqMarks = 0
                def examTypeInst = ExamType.findById(Long.parseLong(it))
                if ((examTypeInst.requiredTotalMarks != null && examTypeInst.requiredTotalMarks != '') && status) {
                    reqMarks = Integer.parseInt(examTypeInst.requiredTotalMarks)
                    def reqMarksExaminationList = Examination.findAllByAcademicSessionAndSubjectAndExamTypes(params.academicSession, subjectInst, it)
                    def totalMarks = 0
                    reqMarksExaminationList.each {
                        totalMarks += Integer.parseInt(it.totalMarks)
                    }
                    if (reqMarks != totalMarks) {
                        status = false
                    }
                }
            }
            if (status) {
                def result = postExaminationService.marksEvaluationDetails(params)
                if (result) {
                    flash.message = "Marks Evaluated for Roll No " + result
                } else {
                    flash.message = "Marks Evaluation Failed."
                }
            } else {
                flash.message = "Examination Total does not match to Required Total Marks."
            }

            redirect(controller: "postExamination", action: "marksEvaluation")
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def definedSymbols() {
        /*action for showing Symbol's list*/
        def symbolList = RuleSymbols.list(sort: 'symbol')
        def idList = [], symbolTextList = [], symbolViewList = [], symbolEditableList = []
        symbolList.each {
            def examtypeInst, ruleInst
            if (it.examType != null)
                examtypeInst = ExamType.findById(Long.parseLong(it.examType))
            if (it.ruleNames != null)
                ruleInst = RuleNames.findById(Long.parseLong(it.ruleNames))
            if (examtypeInst || ruleInst) {
                idList << it.id
                symbolViewList << it.symbol
                symbolEditableList << it.isEditable
                if (it.examType != null && it.ruleNames == null && it.isEditable == true) {
                    symbolTextList << examtypeInst.examTypeName + "_" + it.text
                } else if (it.examType != null && it.ruleNames == null && it.isEditable == true) {
                    symbolTextList << examtypeInst.examTypeName + "_" + it.text
                } else if (it.examType == null && it.ruleNames != null && it.isEditable == true) {
                    symbolTextList << ruleInst.rule + "_" + it.text
                } else if (it.examType == null && it.ruleNames == null && it.isEditable == true) {
                    symbolTextList << it.text
                }
            } else {
                if (it.isEditable)
                    it.delete(flush: true)
                else {
                    idList << it.id
                    symbolViewList << it.symbol
                    symbolEditableList << it.isEditable
                    symbolTextList << it.text
                }
            }
        }
        [idList: idList, symbolTextList: symbolTextList, symbolViewList: symbolViewList, symbolEditableList: symbolEditableList]
    }
    def checkExistenceOfExamSubType = {
//        examType: examType,examSubType:examSubType,subjectCode:subjectCode,academicSession:academicSession
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def examTypeInst = ExamType.findById(Long.parseLong(params.examType))
            def examSubTypeInst = ExamSubType.findById(Long.parseLong(params.examSubType))
            def subInst = Subject.findBySubjectCode(params.subjectCode)

            def examInst = Examination.findByExamSubTypeAndSubjectAndAcademicSession(examSubTypeInst.id.toString(), subInst, params.academicSession)
            if (examInst) {
                returnMap.exist = true
            } else {
                returnMap.exist = false
            }
            if (examTypeInst.requiredTotalMarks != null && examTypeInst.requiredTotalMarks != '') {
                def examTotalMarks = 0
                def examList = Examination.findAllByExamTypesAndSubjectAndAcademicSession(examTypeInst.id.toString(), subInst, params.academicSession)
                examList.each {
                    examTotalMarks += Integer.parseInt(it.totalMarks)
                }
                returnMap.requiredTotalMarks = examTypeInst.requiredTotalMarks
                returnMap.examTotalMarks = examTotalMarks.toString()
                returnMap.examTotalRemainingMarks = (Integer.parseInt(examTypeInst.requiredTotalMarks) - examTotalMarks).toString()
            }
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def performGradeConvert = {
        /*Action for creating grades from evaluated marks(performing grade conversion)*/
        if (springSecurityService.currentUser) {
            def status = false
            def resultStudentMarks = [], studentNameList = [], studentRollNoList = []
            def subInst = Subject.findBySubjectCode(params.subjectCode)
            def statusInst = Status.findById(1)
            def xmlNodes = xmlParseRuleInfo()
            def result = adminInfoService.generateGradeConversionRuleDetails(params, xmlNodes)
            def toList = result.toList, fromList = result.fromList, gradeList = result.gradeList
            def studentEvalMarksList = StudentEvaluatedMarks.findAllByAcademicSessionAndSubjectIdAndStatusAndGradeIsNull(params.academicSession, subInst, statusInst)
            studentEvalMarksList.each {
                def stuEvalMarksInst = it
                def studentInst = it.student
                def studentMarks = (Double.parseDouble(it.marksSecuredAfterRule)).intValue()
                for (def i = 0; i < toList.size(); i++) {
                    def maxValue = 0, minValue = 0
                    if (Double.parseDouble(toList[i]) > Double.parseDouble(fromList[i])) {
                        maxValue = Double.parseDouble(toList[i]).intValue()
                        minValue = Double.parseDouble(fromList[i])
                    } else {
                        maxValue = Double.parseDouble(fromList[i]).intValue()
                        minValue = Double.parseDouble(toList[i])
                    }
                    if (studentMarks <= maxValue && studentMarks >= minValue) {
                        stuEvalMarksInst.grade = gradeList[i]
                        if (stuEvalMarksInst.save(flush: true)) {
                            status = true
                            resultStudentMarks << stuEvalMarksInst
                            if (studentInst.middleName == null) {
                                studentNameList << studentInst.firstName + " " + studentInst.lastName
                            } else {
                                studentNameList << studentInst.firstName + "" + studentInst.middleName + " " + studentInst.lastName
                            }
                            studentRollNoList << studentInst.rollNo
                        }
                    }
                }
            }
            if (status) {
                def args = [template: "finalMeritList", model: [status: statusInst.status, studentNameList: studentNameList, courseName: subInst.subjectName, academicSession: params.academicSession, studentRollNoList: studentRollNoList, marks: resultStudentMarks.marksSecuredAfterRule, gradeList: resultStudentMarks.grade],
                            filename: subInst.subjectName + " " + params.academicSession + ".pdf"]
                pdfRenderingService.render(args + [controller: this], response)
            } else {
                flash.message = "Grade conversion was not completed successfully."
                redirect(controller: 'postExamination', action: 'gradeConversion')
            }
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def gradeConversionToApprove = {
        /* Action for approving grades(interface)*/
        if (springSecurityService.currentUser) {
            def programList = ProgramDetail.list(sort: 'courseName')
            def academicSession = SemesterFeeDetails.createCriteria().list {
                projections {
                    distinct("academicSession")
                }
                order("academicSession", "desc")
            }
            [programList: programList, academicSession: academicSession]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def performApproveGradeConvert() {
        /*Action for showing(downloading) evaluated marks and grades(aproved) of students enrolled in particular examination*/
        def status = false
        def resultMap = [:], studentNameList = [], studentRollNoList = [], studentMarksList = [], studentGradeList = []
        def subInst = Subject.findBySubjectCode(params.subjectCode)
        def statusInst = Status.findById(1)
        def statusApproveInst = Status.findById(4)
        def studentEvalMarksList = StudentEvaluatedMarks.findAllByAcademicSessionAndSubjectIdAndStatus(params.academicSession, subInst, statusInst)
        studentEvalMarksList.each {
            def studentInst = it.student
            it.status = statusApproveInst
            if (it.save(flush: true)) {
                status = true
                if (studentInst.middleName == null) {
                    studentNameList << studentInst.firstName + " " + studentInst.lastName
                } else {
                    studentNameList << studentInst.firstName + "" + studentInst.middleName + " " + studentInst.lastName
                }
                studentRollNoList << studentInst.rollNo
                studentMarksList << it.marksSecuredAfterRule
                studentGradeList << it.grade
            }

        }
        if (status) {
            def args = [template: "finalMeritList", model: [status: statusApproveInst.status, studentNameList: studentNameList, courseName: subInst.subjectName, academicSession: params.academicSession, studentRollNoList: studentRollNoList, marks: studentMarksList, gradeList: studentGradeList],
                        filename: subInst.subjectName + " " + params.academicSession + ".pdf"]
            pdfRenderingService.render(args + [controller: this], response)
        } else {
            flash.message = "Approval was not completed successfully."
            redirect(controller: 'postExamination', action: 'gradeConversionToApprove')
        }
    }

    def viewEvaluatedMarks() {
        /*for showing evaluated marks*/
        def programList = ProgramDetail.list(sort: 'courseName')
        def academicSession = SemesterFeeDetails.createCriteria().list {
            projections {
                distinct("academicSession")
            }
            order("academicSession", "desc")
        }
        [programList: programList, academicSession: academicSession]
    }

    def showUnapprovedMarks() {
        /*for showing student's marks for particular examination which is not approved by admin or HOD*/
    }

    def showApprovedMarks() {
        /*Action for showing student's approved marks for particular examination*/
    }

    def loadStudentMarksDetails() {
        /*load marks (not approved or approved)*/
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def status
            def showApprove = false
            if (params.loadFor == 'unapprove') {
                status = Status.findById(1)
                def currentUser = springSecurityService.currentUser
                if (UserRole.findByUserAndRole(currentUser, Role.findByAuthority('ROLE_ADMIN')) || UserRole.findByUserAndRole(currentUser, Role.findByAuthority('ROLE_HOD'))) {
                    showApprove = true
                }
            } else {
                status = Status.findById(4)
            }
            def examInst = Examination.findById(Long.parseLong(params.examination))
            def studentRollNoList = [], studentNameList = [], studentMarks = [], studentMarksID = []
            def studentMarksList = StudentMarks.findAllByAcademicSessionAndSubjectIdAndStatusAndExamination(params.academicSession, Subject.findBySubjectCode(params.subjectCode), status, examInst.id)
            studentMarksList.each {
                studentRollNoList << it.student.rollNo
                if (it.student.middleName == null) {
                    studentNameList << it.student.firstName + " " + it.student.lastName
                } else {
                    studentNameList << it.student.firstName + " " + it.student.middleName + " " + it.student.lastName
                }
                studentMarks << it.totalMarks
                studentMarksID << it.id
            }
            returnMap.studentRollNoList = studentRollNoList
            returnMap.studentNameList = studentNameList
            returnMap.studentMarks = studentMarks
            returnMap.studentMarksID = studentMarksID
            returnMap.showApprove = showApprove
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def approveAllMarks() {
        /*Action for approving marks*/
        if (springSecurityService.currentUser) {
            def result = false, rollNoList = []
            def status = Status.findById(1)
            def statusApprove = Status.findById(4)
            def examInst = Examination.findById(Long.parseLong(params.examination))
            def studentMarksList = StudentMarks.findAllByAcademicSessionAndSubjectIdAndStatusAndExamination(params.academicSession, Subject.findBySubjectCode(params.subjectCode), status, examInst.id)
            studentMarksList.each {
                it.status = statusApprove
                rollNoList << it.student.rollNo
            }
            render rollNoList as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def rejectAllMarks() {
        /*Action for rejecting marks*/
        if (springSecurityService.currentUser) {
            def result = false, rollNoList = []
            def status = Status.findById(1)
            def statusApprove = Status.findById(4)
            def examInst = Examination.findById(Long.parseLong(params.examination))
            def studentMarksList = StudentMarks.findAllByAcademicSessionAndSubjectIdAndStatusAndExamination(params.academicSession, Subject.findBySubjectCode(params.subjectCode), status, examInst.id)
            studentMarksList.each {
                it.isRejected = true
                rollNoList << it.student.rollNo
            }
            render rollNoList as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def approveIndividualStudent() {
        if (springSecurityService.currentUser) {
            def status = []
            def statusApprove = Status.findById(4)
            def studentMarksList = StudentMarks.findById(Long.parseLong(params.marksId))
            if (studentMarksList) {
                studentMarksList.status = statusApprove
                studentMarksList.save(flush: true)
                status << 'Approved Successfully!'
            } else {
                status << 'Unable to Approved Successfully!'
            }
            render status as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def rejectIndividualStudent() {
        if (springSecurityService.currentUser) {
            def status = []
            def statusApprove = Status.findById(4)
            def studentMarksList = StudentMarks.findById(Long.parseLong(params.marksId))
            if (studentMarksList) {
                studentMarksList.isRejected = true
                studentMarksList.save(flush: true)
                status << 'Rejected Successfully!'
            } else {
                status << 'Unable to Rejecte Successfully!'
            }
            render status as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def totalCalculationRule() {
        /*Action for creating rule for Exam Type*/
        if (springSecurityService.currentUser) {
            def examTypeList = ExamType.list()
            [examTypeList: examTypeList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def createMainRules() {
    /*Action for creating main rule for marks evaluation*/
    }

    def getExamList() {
        if (springSecurityService.currentUser) {
            def examTypeList = ExamType.list()
            render examTypeList as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def rejectGradeConversion() {
        if (springSecurityService.currentUser) {
            def resultMap=[:]
            def subjectInst = Subject.findBySubjectCode(params.subjectCode)
            def studenEvalList = StudentEvaluatedMarks.findAllByAcademicSessionAndSubjectIdAndStatusNotEqual(params.academicSession, subjectInst, Status.findById(4))
            studenEvalList.each {
                it.delete(flush: true)
            }

            def studenEvalListCheck = StudentEvaluatedMarks.findAllByAcademicSessionAndSubjectIdAndStatusNotEqual(params.academicSession, subjectInst, Status.findById(4))
            if (studenEvalListCheck.size() == 0) {
                resultMap.status=true
//                flash.message = "All Grades and Evaluated Marks are deleted. Please Re-Evaluate and do the Greade Conversion"
            } else {
                resultMap.status=false
//                flash.message = "deletion of Grade was not successfull"
            }
            render resultMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    def downloadSingleMarksheet = {
        def student = Student.findByRollNo(params.rollNo)
        def marksList = []
        def subjects=[]
        def evalMarks=[]
        def grades = []
        def sem = Semester.findById(Long.parseLong(params.semester))
        def subjectList = StudentSubject.findAllByStudentAndSemester(student, sem).subjectSession.subjectId
//        println("###########"+subjectList)
        subjectList.each{
            def marks = StudentEvaluatedMarks.findAllByStudentAndSubjectId(student,it)
            if(!marks){

            }else{
                marksList << marks
            }

        }
        marksList.each{
            subjects  << it.subjectId.subjectName
            evalMarks << it.marksSecuredAfterRule
            grades << it.grade

        }
        if(marksList){
            def args = [template: "singleMarksheetTemplet", model: [student: student,subjects:subjects,grades:grades,evalMarks:evalMarks,semester:sem.semesterNo,marksList:marksList], filename: params.rollNo + ".pdf"]
            pdfRenderingService.render(args + [controller: this], response)
        }
       else{
            flash.message="Marks not Evaluated yet"
            redirect(action:"singleMarksSheet")

        }

    }

    def bulkMsheetGenerate = {
//        println("#########"+params)
        def progName = ProgramDetail.findById(Long.parseLong(params.programId)).courseName
        def stuList = Student.findAllByProgram(ProgramDetail.findById(Long.parseLong(params.programId)))
        def sem = Semester.findById(Long.parseLong(params.semester))
        def totalMarkList=[]
        def finalStudentList=[]
        def marksLength=[]
        stuList.each{
            def student = it
            def marksList=[]
            def subjectList = StudentSubject.findAllByStudentAndSemesterAndAcademicSession(student, sem, params.session).subjectSession.subjectId
            subjectList.each{
                def marks = StudentEvaluatedMarks.findAllByStudentAndSubjectIdAndAcademicSession(student,it,params.session )
                if(!marks){

                }else{
                    finalStudentList << student
                    marksList << marks
                    println("#####SUBJECT#####"+marks.subjectId.subjectName)
                    println("#####MARKS#####"+ marks.marksSecuredAfterRule)
                    println("#####GRADE#####"+ marks.grade)
                }

            }
            totalMarkList << marksList
            marksLength << marksList.size()
        }
        if(finalStudentList){
            def args = [template: "bulkMarksheetTemplet", model: [progName: progName,marksLength:marksLength,studentList: finalStudentList,semester:sem.semesterNo,totalMarkList:totalMarkList], filename: progName + ".pdf"]
            pdfRenderingService.render(args + [controller: this], response)

        }
        else{
            flash.message="Marks not Evaluated yet"
            redirect(action:"singleMarksSheet")

        }


    }
}

