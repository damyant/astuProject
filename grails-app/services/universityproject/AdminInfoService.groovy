package universityproject

import com.university.NoticeBoard
import com.university.NoticeBoardRole
import com.university.Role
import com.university.User
import examinationproject.CourseSubject
import examinationproject.EnrollmentPeriod
import examinationproject.FacultyAdvisor
import examinationproject.PaymentMode
import examinationproject.ProgramDetail
import examinationproject.ProgramSession
import examinationproject.Semester
import examinationproject.Status
import examinationproject.Student
import examinationproject.Subject
import examinationproject.SubjectSession
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.w3c.dom.Document
import org.w3c.dom.Element
import postexamination.ExamSubType
import postexamination.ExamType
import postexamination.Rule
import postexamination.RuleNames
import postexamination.RuleSymbols

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class AdminInfoService {
    def studentRegistrationService
    def springSecurityService

    def serviceMethod() {
    }
    def provisionalStudentList(params) {
        def obj = Student.createCriteria()
        def stuList = obj.list {
            programDetail {
                eq('id', Long.parseLong(params.programId))
            }
            and {
                isNull('rollNo')
            }
        }
        return stuList
    }
    def subjectList(params) {
        def subjectMap = [:]
        def subList = [], totalDateList = [], totalTimeList = [], gList = [], subNameList = [],
            examDateList = [], newSemesterList = [], semesterNumberList = [], examTimeList = [], sList = [], dateList = [], groupNameList = []
        def semCounter = 0
        def programSessionIns = ProgramSession.findById(Long.parseLong(params.sessionId))
        def semesterList = Semester.findAllByProgramSession(programSessionIns)
        def count = 0
        if (params.sessionTypeId == '1') {
            semesterList.each {
                if (it.semesterNo % 2 == 0) {
                    newSemesterList << it
                }
            }
        } else {
            semesterList.each {
                if (it.semesterNo % 2 != 0) {
                    newSemesterList << it
                }
            }
        }
        def groupSubjectDateList = [], groupObj = []
        newSemesterList.each {
            def obj = CourseSubject.findAllByProgramSessionAndSemester(programSessionIns, it)
            if (obj) {
                dateList << obj
            }
            groupObj = ProgramGroup.findAllByProgramSessionAndSemester(programSessionIns, it)
            if (groupObj) {
                groupObj.each {
                    groupSubjectDateList << ProgramGroupDetail.findAllByProgramGroupId(it)
                }
            }
        }
        dateList.each {
            subList << it.subjectSessionId
            subNameList << it.subjectSessionId.subjectId.subjectName
            semesterNumberList << it.semester
            examDateList << it.examDate
            examTimeList << it.examTime
            groupNameList << ""
        }
        groupSubjectDateList.each {
            sList << groupObj[semCounter].semester
            subList << it.subjectSessionId
            subNameList << it.subjectSessionId.subjectId.subjectName
            semesterNumberList << sList
            examDateList << it.examDate
            examTimeList << it.examTime
            groupNameList << groupObj[semCounter].groupName
            ++semCounter;
        }
        def cond = dateList.examDate.size() + groupSubjectDateList.examDate.size()
        for (def i = 0; i < cond; i++) {
            for (def j = 0; j < examDateList[i].size(); j++) {
                if (examDateList[i][j] != null) {
                    totalDateList << examDateList[i][j].getDateString()
                    ++count
                } else {
                    totalDateList << 'noo'
                }
            }
        }
        subjectMap.allSubjects = subList
        subjectMap.semesterNoList = semesterNumberList
        subjectMap.dateList = totalDateList
        subjectMap.examTimeList = examTimeList
        subjectMap.groupNameList = groupNameList
        subjectMap.subNameList = subNameList
        return subjectMap
    }

    def saveExamDate(params) {
        SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
        def subjectList = params.subjectIdList.split(",")
        def groupSubjectList = [], programGroupDetail = [], courseSubjectObj = []
        if (params.groupSubjectList) {
            groupSubjectList = params.groupSubjectList.split(",")
        }
        def count = 0
        def sessionObj = ProgramSession.findById(Long.parseLong(params.SessionList))
        subjectList.each {
            def subj = it.toString().split('-')[0]
            def sem = it.toString().split('-')[1]
            def groupName = groupSubjectList[count].toString().split('-')[2]
            if (groupName != "no") {
                def progGroup = ProgramGroup.findByProgramSessionAndSemesterAndGroupName(sessionObj, Semester.findBySemesterNoAndProgramSession(sem, sessionObj), groupName)

                programGroupDetail = ProgramGroupDetail.findByProgramGroupIdAndSubjectSessionId(progGroup, SubjectSession.findById(Long.parseLong(subj)))
            } else {
                courseSubjectObj = CourseSubject.findBySubjectSessionIdAndProgramSessionAndSemester(SubjectSession.findById(Long.parseLong(subj)), sessionObj, Semester.findBySemesterNoAndProgramSession(sem, sessionObj))

            }


            def dateList = []
            def timeList = []
            dateList.addAll(params.examinationDate)
            timeList.addAll(params.examinationTime)

            if (dateList[count]) {

                if (groupName != "no") {
                    programGroupDetail.examDate = f1.parse(dateList[count])
                    programGroupDetail.examTime = timeList[count]
                    programGroupDetail.save(failOnError: true, flush: true)

                } else {
                    courseSubjectObj.examDate = f1.parse(dateList[count])
                    courseSubjectObj.examTime = timeList[count]
                    courseSubjectObj.save(failOnError: true, flush: true)
                }

            } else {
                courseSubjectObj.examDate = null
                courseSubjectObj.save(failOnError: true, flush: true)
            }
            ++count
        }
    }
    def updateStudentList(params) {
        /*method for getting student list*/
        def subjectMap = [:], status = []
        def obj = Student.createCriteria()
        def studList = obj.list {
            eq('program', ProgramDetail.findById(Long.parseLong(params.programId)))
            order("rollNo", "asc")
        }
        subjectMap.studList = studList
        return subjectMap
    }
    def saveAdmissionPeriod(params) {
        /*Method for saving details of semester registration(program,session,semester,StartDate,EndDate etc)*/
        Boolean status = false;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy")
        def startAdmission_D = df.parse(params.startAdmission_D)
        def endAdmission_D = df.parse(params.endAdmission_D)
        def programInst = ProgramDetail.findById(Long.parseLong(params.program))
        def semesterInst = Semester.findById(Long.parseLong(params.semester))
        def enrollInst = EnrollmentPeriod.findByProgramDetailAndSemesterAndStudentAdmissionYear(programInst, semesterInst, params.admissionYear)
        if (enrollInst) {
            enrollInst.startDate = startAdmission_D
            enrollInst.endDate = endAdmission_D
            enrollInst.studentAdmissionYear = params.admissionYear
            enrollInst.admissionSession = params.admissionSession
        } else {
            enrollInst = new EnrollmentPeriod()
            enrollInst.admissionSession = params.admissionSession
            enrollInst.semester = semesterInst
            enrollInst.studentAdmissionYear = params.admissionYear
            enrollInst.programDetail = programInst
            enrollInst.startDate = startAdmission_D
            enrollInst.endDate = endAdmission_D
        }
        if (enrollInst.save(flush: true, failOnError: true)) {
            status = true
        }
        return status
    }
    def removeDateLateFee(params) {
        Boolean status = false;
        def programIns = ProgramDetail.findById(Integer.parseInt(params.programs))
        programIns.lateFeeDate = null
        if (programIns.save(flush: true, failOnError: true)) {
            status = true
        }
        return status
    }
    def deleteTheCourse(params) {

        Boolean status = false;
        Boolean dStatus = false;
        def SubjectIns = Subject.findById(Integer.parseInt(params.courseId))
        def subjMapping = CourseSubject.findAllBySubject(SubjectIns)
        if (subjMapping.size() > 0) {
            subjMapping.each {
                it.delete()
                if (it.exists()) {
                    dStatus = true
                }
            }
            if (!dStatus) {
                SubjectIns.delete(failOnError: true)
                if (!SubjectIns.exists()) {
                    status = true
                }
            }
        } else {
            SubjectIns.delete()
            if (!SubjectIns.exists()) {
                status = true
            }
        }
        return status
    }

    def uploadNoticeBoard(newFile, params) {
        /*method for saving notice details*/
        def finalStatus = false
        if (params.noticeUpdate) {
            def noticeBoardUpdateInst = NoticeBoard.findById(Long.parseLong(params.noticeUpdate))
            noticeBoardUpdateInst.noticeHeader = params.noticeHeader
            if (params.noticeStatus == 'Archive') {
                noticeBoardUpdateInst.isArchive = true
            }
            noticeBoardUpdateInst.fileName = newFile
            def roleList = []
            if (params.roles.getClass() == String) {
                roleList << Role.findById(Long.parseLong(params.roles))
            } else {
                params.roles.each {
                    roleList << Role.findById(Long.parseLong(it))
                }
            }
            def roleInstList = NoticeBoardRole.findAllByNoticeboard(noticeBoardUpdateInst)
            roleInstList.each {
                it.delete(flush: true)
            }
            def status = true
            roleList.each {
                def noticeBoardRoleInst = new NoticeBoardRole()
                noticeBoardRoleInst.noticeboard = noticeBoardUpdateInst
                noticeBoardRoleInst.role = it
                if (noticeBoardRoleInst.save(failOnError: true, flush: true)) {

                } else {
                    status = false
                }
            }
            if (status) {
                finalStatus = true
            }

        } else {
            def noticeBoardInst = new NoticeBoard()
            noticeBoardInst.fileName = newFile
            noticeBoardInst.noticeHeader = params.noticeHeader
            noticeBoardInst.noticeDate = new Date()
            noticeBoardInst.isArchive = false
            def roleList = []
            if (params.roles.getClass() == String) {
                roleList << Role.findById(Long.parseLong(params.roles))
            } else {
                params.roles.each {
                    roleList << Role.findById(Long.parseLong(it))
                }
            }

            if (noticeBoardInst.save(failOnError: true, flush: true)) {
                def status = true
                roleList.each {
                    def noticeBoardRoleInst = new NoticeBoardRole()
                    noticeBoardRoleInst.noticeboard = noticeBoardInst
                    noticeBoardRoleInst.role = it
                    if (noticeBoardRoleInst.save(failOnError: true, flush: true)) {

                    } else {
                        status = false
                    }
                }
                if (status) {
                    finalStatus = true
                }

            }
        }
        return finalStatus
    }

    def uploadNoticeBoardwithoutFile(params) {
        def finalStatus = false
        if (params.noticeUpdate) {
            def noticeBoardUpdateInst = NoticeBoard.findById(Long.parseLong(params.noticeUpdate))
            noticeBoardUpdateInst.noticeHeader = params.noticeHeader
            if (params.noticeStatus == 'Archive') {
                noticeBoardUpdateInst.isArchive = true
            }
            def roleList = []
            if (params.roles.getClass() == String) {
                roleList << Role.findById(Long.parseLong(params.roles))
            } else {
                params.roles.each {
                    roleList << Role.findById(Long.parseLong(it))
                }
            }
            def roleInstList = NoticeBoardRole.findAllByNoticeboard(noticeBoardUpdateInst)
            roleInstList.each {
                it.delete(flush: true)
            }
            def status = true
            roleList.each {
                def noticeBoardRoleInst = new NoticeBoardRole()
                noticeBoardRoleInst.noticeboard = noticeBoardUpdateInst
                noticeBoardRoleInst.role = it
                if (noticeBoardRoleInst.save(failOnError: true, flush: true)) {
                } else {
                    status = false
                }
            }
            if (status) {
                finalStatus = true
            }
        }
        return finalStatus
    }

    def saveAssignFaculty(params) {
        /*Method for saving details of assigned faculty advisor*/
        def result = false
        def fAList = FacultyAdvisor.list()
        def semesterList = [], facultyList = []
        def programInst = ProgramDetail.findById(Long.parseLong(params.program))
        if (params.semester.getClass() == String) {
            semesterList = Semester.findAllById(Long.parseLong(params.semester))
        } else {
            params.semester.each {
                def sem = it
                semesterList << Semester.findById(Long.parseLong(sem))
            }
        }
        if (params.facultyAdvisor.getClass() == String) {
            facultyList = User.findAllById(Long.parseLong(params.facultyAdvisor))
        } else {
            params.facultyAdvisor.each {
                def userIn = it
                facultyList << User.findById(Long.parseLong(userIn))
            }
        }
        semesterList.each {
            def currentSem = it
            facultyList.each {
                def currentFaculty = it
                def facultyAdvisorInst = FacultyAdvisor.findByProgramDetailAndUserAndSemesterAndAcademicSession(programInst, currentFaculty, currentSem, params.admissionSession)
                if (!facultyAdvisorInst) {
                    def fAInst = new FacultyAdvisor()
                    fAInst.programDetail = programInst
                    fAInst.academicSession = params.admissionSession
                    fAInst.semester = currentSem
                    fAInst.user = currentFaculty
                    if (fAInst.save(flush: true, failOnError: true)) {
                        result = true
                    }
                }
            }
        }
        return result
    }

    def generateGradeConversionRuleDetails(params, xmlNodes) {
        /*Method for creating grades from evaluated marks(performin grade conversion) according to grade rule*/
        def finalMarksList = [:]
        def fromList = []
        def toList = []
        def gradeList = []
        xmlNodes.ruleConversion.grade.each { itr ->
            fromList << itr.attribute('from')
            toList << itr.attribute('to')
            gradeList << itr.attribute('grade')
        }
        finalMarksList.fromList = fromList
        finalMarksList.toList = toList
        finalMarksList.gradeList = gradeList
        return finalMarksList
    }
    def deleteGradeConversionRuleInXmlFile() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        def docDirectory = ServletContextHolder.servletContext.getRealPath(System.getProperty("file.separator"))
        def dir = docDirectory + System.getProperty("file.separator") + "ruleEngine" + System.getProperty("file.separator") + "Rule.xml"
        Document document = documentBuilder.parse(dir);
        org.w3c.dom.Node employee = document.getElementsByTagName("rule").item(0);
        org.w3c.dom.NodeList nodes = employee.getChildNodes();
        def updateStatus = false
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeName() == "ruleConversion" && updateStatus == false) {
                employee.removeChild(nodes.item(i).getChildNodes())
            }
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(dir));
        transformer.transform(domSource, streamResult);
    }

    def createXMLFile(toNo, fromNo, grade) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        def docDirectory = ServletContextHolder.servletContext.getRealPath(System.getProperty("file.separator"))
        def dir = docDirectory + System.getProperty("file.separator") + "ruleEngine" + System.getProperty("file.separator") + "Rule.xml"
        Document document = documentBuilder.parse(dir);
        org.w3c.dom.Node employee = document.getElementsByTagName("rule").item(0);
        org.w3c.dom.NodeList nodes = employee.getChildNodes();
        Element ruleConversion = document.createElement("ruleConversion");
        employee.appendChild(ruleConversion)
        Element ruleConversionExisting = document.getElementsByTagName("ruleConversion").item(0);
        org.w3c.dom.NodeList ruleConversionNodes = ruleConversionExisting.getChildNodes();
        if (fromNo.getClass() == String) {
            Element gradeNode = document.createElement("grade");
            gradeNode.setAttribute('from', fromNo)
            gradeNode.setAttribute('to', toNo)
            gradeNode.setAttribute('grade', grade)
            ruleConversionExisting.appendChild(gradeNode)
        } else {
            for (def i = 0; i < fromNo.length; i++) {
                Element gradeNode = document.createElement("grade");
                gradeNode.setAttribute('from', fromNo[i])
                gradeNode.setAttribute('to', toNo[i])
                gradeNode.setAttribute('grade', grade[i])
                ruleConversionExisting.appendChild(gradeNode)
            }
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(dir));
        transformer.transform(domSource, streamResult);
    }

    def createNewRuleFile(params) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        def docDirectory = ServletContextHolder.servletContext.getRealPath(System.getProperty("file.separator"))

        def dir = docDirectory + System.getProperty("file.separator") + "ruleEngine" + System.getProperty("file.separator") + "Rule.xml"
        Document document = documentBuilder.parse(dir);
        org.w3c.dom.Node rule = document.getElementsByTagName("rule").item(0);
        org.w3c.dom.NodeList nodes = rule.getChildNodes();
        Element ruleConversion = document.getElementsByTagName("ruleBase").item(0);
        org.w3c.dom.NodeList ruleConversionNodes = ruleConversion.getChildNodes();
        def ruleName = (params.ruleName).replace(" ", '').toLowerCase()
        Element ruleBaseNameNode = document.createElement(ruleName);
        if (params.ruleFor.getClass() == String) {
            Element ctNode = document.createElement(params.ruleFor);
            ctNode.setAttribute('formula', params.rule)
            ctNode.setAttribute('symbol', params.ruleType)
            ruleBaseNameNode.appendChild(ctNode)
        } else {
            for (def i = 0; i < params.ruleType.length; i++) {
                Element ctNode = document.createElement(params.ruleFor[i]);
                ctNode.setAttribute('formula', params.rule[i])
                ctNode.setAttribute('symbol', params.ruleType[i])
                def ctUpdateStatus = false
                org.w3c.dom.NodeList ruleForNode = ctNode.getChildNodes();
                for (int j = 0; j < ruleForNode.getLength(); j++) {
                    if (ruleForNode.item(j).getNodeName() == params.ruleFor[i]) {
                        ctUpdateStatus = true
                        ruleForNode.item(j).setNodeValue(params.rule[i])
                        ruleForNode.item(j).setAttribute('formula', params.rule[i])
                        ruleForNode.item(j).setAttribute('symbol', params.ruleType[i])
                    }
                }
                if (!ctUpdateStatus) {
                    ruleBaseNameNode.appendChild(ctNode)
                }
            }
        }

        def elementUpdateStatus = false
        for (int i = 0; i < ruleConversionNodes.getLength(); i++) {
            if (ruleConversionNodes.item(i).getNodeName() == ruleName) {
                ruleConversion.removeChild(ruleConversionNodes.item(i).getChildNodes())
            }
        }
        if (!elementUpdateStatus) {
            ruleConversion.appendChild(ruleBaseNameNode)
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);

        StreamResult streamResult = new StreamResult(new File(dir));
        transformer.transform(domSource, streamResult);
        return true
    }

    def generatePermanentRuleDetails(params, xmlNodes) {
        def finalMarksList = [:]
        def classTestWeightage, classTestTotal, theoryMarks, practicalMarks, totalMarksSecured
        xmlNodes.permanentRule.classTest.each { itr ->
            classTestWeightage = itr.attribute("weightage")
            classTestTotal = itr.attribute("cttotal")

        }
        xmlNodes.permanentRule.theoryMarks.each { itr ->
            theoryMarks = itr.attribute("formula")
        }
        xmlNodes.permanentRule.practicalMarks.each { itr ->
            practicalMarks = itr.attribute("formula")
        }
        xmlNodes.permanentRule.totalMarksSecured.each { itr ->
            totalMarksSecured = itr.attribute("formula")
        }
        finalMarksList.classTestWeightage = classTestWeightage
        finalMarksList.classTestTotal = classTestTotal
        finalMarksList.theoryMarks = theoryMarks
        finalMarksList.practicalMarks = practicalMarks
        finalMarksList.totalMarksSecured = totalMarksSecured
        return finalMarksList
    }

    def createRelativeRuleFile(params) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        def docDirectory = ServletContextHolder.servletContext.getRealPath(System.getProperty("file.separator"))

        def dir = docDirectory + System.getProperty("file.separator") + "ruleEngine" + System.getProperty("file.separator") + "Rule.xml"
        Document document = documentBuilder.parse(dir);
        org.w3c.dom.Node employee = document.getElementsByTagName("rule").item(0);
        org.w3c.dom.NodeList nodes = employee.getChildNodes();
        Element ruleConversion = document.getElementsByTagName("relativeRule").item(0);
        ruleConversion.setAttribute("isRequired", params.relativeIsRequired)
        org.w3c.dom.NodeList ruleConversionNodes = ruleConversion.getChildNodes();
        Element ctNode = document.createElement("relativeMarks");
        ctNode.setAttribute('formula', params.relativeMarks)


        def relativeMarksStatus = false
        for (int i = 0; i < ruleConversionNodes.getLength(); i++) {
            if (ruleConversionNodes.item(i).getNodeName() == "relativeMarks") {
                relativeMarksStatus = true
                ruleConversionNodes.item(i).setAttribute('formula', params.relativeMarks)
            }

        }
        if (!relativeMarksStatus) {
            ruleConversion.appendChild(ctNode)
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);

        StreamResult streamResult = new StreamResult(new File(dir));
        transformer.transform(domSource, streamResult);
        return true
    }

    def generateRelativeRuleDetails(params, xmlNodes) {
        def finalMarksList = [:]
        def relativeMarks, isRequired
        xmlNodes.relativeRule.each { itr ->
            isRequired = itr.attribute("isRequired")
        }
        xmlNodes.relativeRule.relativeMarks.each { itr ->
            relativeMarks = itr.attribute("formula")
        }

        finalMarksList.isRequired = isRequired
        finalMarksList.relativeMarks = relativeMarks
        return finalMarksList
    }

    def saveSubExamType(params) {
        def status = true
        def examTypeInst = ExamType.findById(Long.parseLong(params.exmType))
        if (params.examSubName.getClass() == String) {
            def examSubTypeInst = new ExamSubType()
            examSubTypeInst.examType = examTypeInst
            examSubTypeInst.examSubTypeName = params.examSubName
            if (params.marksSecuredSymbol) {
                examSubTypeInst.marksSecuredSymbol = params.marksSecuredSymbol
            }
            if (params.totalMarksSymbol) {
                examSubTypeInst.totalMarksSymbol = params.totalMarksSymbol
            }
            if (params.maxInAllStudentsSymbol) {
                examSubTypeInst.maxInAllStudentsSymbol = params.maxInAllStudentsSymbol
            }
            if (params.examSubTypeTotalSymbol) {
                examSubTypeInst.examSubTypeTotalSymbol = params.examSubTypeTotalSymbol
            }
            if (examSubTypeInst.save(flush: true)) {
                if (params.marksSecuredSymbol) {
                    def symbolInst = RuleSymbols.findBySymbol(params.marksSecuredSymbol)
                    if (symbolInst) {
                        status = false
                        examSubTypeInst.delete(flush: true)
                    } else {
                        symbolInst = new RuleSymbols()
                        symbolInst.examType = examTypeInst
                        symbolInst.examSubType = examSubTypeInst
                        symbolInst.symbol = params.marksSecuredSymbol
                        symbolInst.text = 'marksSecured'
                        symbolInst.isEditable = true
                        if (symbolInst.save(flush: true)) {

                        } else {
                            status = false
                        }
                    }
                }
                if (params.totalMarksSymbol && status) {
                    def symbolInst = RuleSymbols.findBySymbol(params.totalMarksSymbol)
                    if (symbolInst) {
                        status = false
                        if (params.marksSecuredSymbol) {
                            def symbolInst1 = RuleSymbols.findBySymbol(params.marksSecuredSymbol)
                            symbolInst1.delete(flush: true)
                        }

                        examSubTypeInst.delete(flush: true)
                    } else {
                        symbolInst = new RuleSymbols()
                        symbolInst.examType = examTypeInst
                        symbolInst.examSubType = examSubTypeInst
                        symbolInst.symbol = params.totalMarksSymbol
                        symbolInst.text = 'totalMarks'
                        symbolInst.isEditable = true
                        if (symbolInst.save(flush: true)) {

                        } else {
                            status = false
                        }
                    }
                }
                if (params.maxInAllStudentsSymbol && status) {
                    def symbolInst = RuleSymbols.findBySymbol(params.maxInAllStudentsSymbol)
                    if (symbolInst) {
                        status = false
                        if (params.marksSecuredSymbol) {
                            def symbolInst1 = RuleSymbols.findBySymbol(params.marksSecuredSymbol)
                            symbolInst1.delete(flush: true)
                        }
                        if (params.totalMarksSymbol) {
                            def symbolInst1 = RuleSymbols.findBySymbol(params.totalMarksSymbol)
                            symbolInst1.delete(flush: true)
                        }
                        examSubTypeInst.delete(flush: true)
                    } else {
                        symbolInst = new RuleSymbols()
                        symbolInst.examType = examTypeInst
                        symbolInst.examSubType = examSubTypeInst
                        symbolInst.symbol = params.maxInAllStudentsSymbol
                        symbolInst.text = 'maxInAllStudents'
                        symbolInst.isEditable = true
                        if (symbolInst.save(flush: true)) {

                        } else {
                            status = false
                        }
                    }
                }
                if (params.examSubTypeTotalSymbol && status) {
                    def symbolInst = RuleSymbols.findBySymbol(params.examSubTypeTotalSymbol)
                    if (symbolInst) {
                        status = false
                        if (params.marksSecuredSymbol) {
                            def symbolInst1 = RuleSymbols.findBySymbol(params.marksSecuredSymbol)
                            symbolInst1.delete(flush: true)
                        }
                        if (params.totalMarksSymbol) {
                            def symbolInst1 = RuleSymbols.findBySymbol(params.totalMarksSymbol)
                            symbolInst1.delete(flush: true)
                        }
                        if (params.maxInAllStudentsSymbol) {
                            def symbolInst1 = RuleSymbols.findBySymbol(params.maxInAllStudentsSymbol)
                            symbolInst1.delete(flush: true)
                        }
                        examSubTypeInst.delete(flush: true)
                    } else {
                        symbolInst = new RuleSymbols()
                        symbolInst.examType = examTypeInst
                        symbolInst.examSubType = examSubTypeInst
                        symbolInst.symbol = params.examSubTypeTotalSymbol
                        symbolInst.text = 'examSubTypeTotalAfterRule'
                        symbolInst.isEditable = true
                        if (symbolInst.save(flush: true)) {

                        } else {
                            status = false
                        }
                    }
                }
            } else {
                status = false
            }
        }
//        else{
//            params.examSubName.each{
//                if(it!=''){
//                    def examSubTypeInst=new ExamSubType()
//                    examSubTypeInst.examType=examTypeInst
//                    examSubTypeInst.examSubTypeName=it
//                    if(examSubTypeInst.save(flush: true)){
//                    }
//                    else{
//                        status=false
//                    }
//                }
//            }
//        }
        return status
    }

    def saveExamType(params) {
        def status = true
        if (params.examSubName.getClass() == String) {
            def examSubTypeInst = new ExamType()
            examSubTypeInst.examTypeName = params.examSubName
            examSubTypeInst.requiredTotalMarks = params.requiredTotalMarks
            if (params.marksSecuredSymbol) {
                examSubTypeInst.marksSecuredSymbol = params.marksSecuredSymbol
            }
            if (params.totalMarksSymbol) {
                examSubTypeInst.totalMarksSymbol = params.totalMarksSymbol
            }
            if (params.maxInAllStudentsSymbol) {
                examSubTypeInst.maxInAllStudentsSymbol = params.maxInAllStudentsSymbol
            }
            if (params.examSubTypeTotalSymbol) {
                examSubTypeInst.examTypeTotalSymbol = params.examSubTypeTotalSymbol
            }

            if (examSubTypeInst.save(flush: true)) {
                if (params.marksSecuredSymbol) {
                    def symbolInst = RuleSymbols.findBySymbol(params.marksSecuredSymbol)
                    if (symbolInst) {
                        status = false
                        examSubTypeInst.delete(flush: true)
                    } else {
                        symbolInst = new RuleSymbols()
                        symbolInst.examType = examSubTypeInst
                        symbolInst.symbol = params.marksSecuredSymbol
                        symbolInst.text = 'marksSecured'
                        symbolInst.isEditable = true
                        if (symbolInst.save(flush: true)) {

                        } else {
                            status = false
                        }
                    }

                }
                if (params.totalMarksSymbol && status) {
                    def symbolInst = RuleSymbols.findBySymbol(params.totalMarksSymbol)
                    if (symbolInst) {
                        status = false
                        if (params.marksSecuredSymbol) {
                            def symbolInst1 = RuleSymbols.findBySymbol(params.marksSecuredSymbol)
                            symbolInst1.delete(flush: true)
                        }

                        examSubTypeInst.delete(flush: true)
                    } else {
                        symbolInst = new RuleSymbols()
                        symbolInst.examType = examSubTypeInst
                        symbolInst.symbol = params.totalMarksSymbol
                        symbolInst.text = 'totalMarks'
                        symbolInst.isEditable = true
                        if (symbolInst.save(flush: true)) {

                        } else {
                            status = false
                        }
                    }
                }
                if (params.maxInAllStudentsSymbol && status) {
                    def symbolInst = RuleSymbols.findBySymbol(params.maxInAllStudentsSymbol)
                    if (symbolInst) {
                        status = false
                        if (params.marksSecuredSymbol) {
                            def symbolInst1 = RuleSymbols.findBySymbol(params.marksSecuredSymbol)
                            symbolInst1.delete(flush: true)
                        }
                        if (params.totalMarksSymbol) {
                            def symbolInst1 = RuleSymbols.findBySymbol(params.totalMarksSymbol)
                            symbolInst1.delete(flush: true)
                        }
                        examSubTypeInst.delete(flush: true)
                    } else {
                        symbolInst = new RuleSymbols()
                        symbolInst.examType = examSubTypeInst
                        symbolInst.symbol = params.maxInAllStudentsSymbol
                        symbolInst.text = 'maxInAllStudents'
                        symbolInst.isEditable = true
                        if (symbolInst.save(flush: true)) {

                        } else {
                            status = false
                        }
                    }
                }
                if (params.examSubTypeTotalSymbol && status) {
                    def symbolInst = RuleSymbols.findBySymbol(params.examSubTypeTotalSymbol)
                    if (symbolInst) {
                        status = false
                        if (params.marksSecuredSymbol) {
                            def symbolInst1 = RuleSymbols.findBySymbol(params.marksSecuredSymbol)
                            symbolInst1.delete(flush: true)
                        }
                        if (params.totalMarksSymbol) {
                            def symbolInst1 = RuleSymbols.findBySymbol(params.totalMarksSymbol)
                            symbolInst1.delete(flush: true)
                        }
                        if (params.maxInAllStudentsSymbol) {
                            def symbolInst1 = RuleSymbols.findBySymbol(params.maxInAllStudentsSymbol)
                            symbolInst1.delete(flush: true)
                        }
                        examSubTypeInst.delete(flush: true)
                    } else {
                        symbolInst = new RuleSymbols()
                        symbolInst.examType = examSubTypeInst
                        symbolInst.symbol = params.examSubTypeTotalSymbol
                        symbolInst.text = 'examTypeTotalAfterRule'
                        symbolInst.isEditable = true
                        if (symbolInst.save(flush: true)) {

                        } else {
                            status = false
                        }
                    }
                }
            } else {
                status = false
            }
        }
        return status
    }

    def saveCreateNewRule(params) {
        def status = false
        def ruleNameInst = RuleNames.findByRule(params.ruleName)
        if (ruleNameInst) {
            ruleNameInst.ruleTotalMarksSecuredSymbol = params.marksSecuredSymbol
            ruleNameInst.maxInAllStudentsSymbol = params.maxInAllStudentsSymbol
            ruleNameInst.ruleRequired = true
            if (ruleNameInst.save(flush: true, failOnError: true)) {
                def existingSymbolList = RuleSymbols.findAllById(ruleNameInst.id)
                existingSymbolList.each {
                    it.delete(flush: true)
                }
                status = true
            }
        } else {

            ruleNameInst = new RuleNames()
            ruleNameInst.rule = params.ruleName
            ruleNameInst.ruleTotalMarksSecuredSymbol = params.marksSecuredSymbol
            ruleNameInst.maxInAllStudentsSymbol = params.maxInAllStudentsSymbol
            ruleNameInst.ruleRequired = true
            if (ruleNameInst.save(flush: true, failOnError: true)) {
                status = true
            }
        }
        if (params.marksSecuredSymbol) {
            def symbolInst = new RuleSymbols()
            symbolInst.ruleNames = ruleNameInst
            symbolInst.symbol = params.marksSecuredSymbol
            symbolInst.text = 'TotalAfterRuleApply'
            symbolInst.isEditable = true
            symbolInst.save(flush: true)
        }
        if (params.maxInAllStudentsSymbol) {
            def symbolInst = new RuleSymbols()
            symbolInst.ruleNames = ruleNameInst
            symbolInst.symbol = params.maxInAllStudentsSymbol
            symbolInst.text = 'maxInAllStudents'
            symbolInst.isEditable = true
            symbolInst.save(flush: true)
        }
        return status
    }
    def deleteRuleFromFile(params) {
        /*for deleting rule and their details*/
        def status=false
        def ruleInst = Rule.findById(Long.parseLong(params.id))
        if(ruleInst.examType!=null){
            def ruleSymbolList=RuleSymbols.findAllByExamType(ruleInst.examType)
            ruleSymbolList.each {
                it.delete(flush: true)
            }

        }
        else{
            def ruleSymbolList=RuleSymbols.findAllByRuleNames(ruleInst.ruleNames)
            ruleSymbolList.each {
                it.delete(flush: true)
            }
            def ruleNameInst=RuleNames.findById(Long.parseLong(ruleInst.ruleNames))
            ruleNameInst.delete(flush: true)
        }
        ruleInst.delete(flush: true)
        if(!Rule.exists(ruleInst.id)){
            status=true
        }
        return status
    }
    def getAllSymbolAndDefination() {
        def returnMap = [:]
        def symbolList = RuleSymbols.list()
        def symbolTextList = [], symbolViewList = [], ruleSymbolTypeList = []
        symbolList.each {
            symbolViewList << it.symbol
            if (it.examType != null && it.examSubType != null && it.ruleNames == null) {
                symbolTextList << it.examType.examTypeName + "_" + it.examSubType.examSubTypeName + "_" + it.text
                ruleSymbolTypeList << 'examSubType'
            } else if (it.examType != null && it.examSubType == null && it.ruleNames == null) {
                symbolTextList << it.examType.examTypeName + "_" + it.text
                ruleSymbolTypeList << 'examType'
            } else if (it.examType == null && it.examSubType == null && it.ruleNames != null) {
                symbolTextList << it.ruleNames.rule + "_" + it.text
                ruleSymbolTypeList << 'Rule'
            } else if (it.examType == null && it.examSubType == null && it.ruleNames == null) {
                symbolTextList << it.text
                ruleSymbolTypeList << 'LTPC'
            }
        }
        returnMap.symbolList = symbolViewList
        returnMap.ruleSymbolTypeList = ruleSymbolTypeList
        returnMap.definationList = symbolTextList
        return returnMap
    }
    def getRuleXmlNodeNameSymbolAndFormula(params) {
        def returnMap = [:], returnList = [], returnSymbolList = [], returnFormulaList = []
        def status = false
        def ruleNameInst = RuleNames.findById(Long.parseLong(params.id))
        def ruleName = (ruleNameInst.rule).replace(" ", '').toLowerCase()
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        def docDirectory = ServletContextHolder.servletContext.getRealPath(System.getProperty("file.separator"))

        def dir = docDirectory + System.getProperty("file.separator") + "ruleEngine" + System.getProperty("file.separator") + "Rule.xml"
        Document document = documentBuilder.parse(dir);

        org.w3c.dom.Node rule = document.getElementsByTagName("rule").item(0);
        org.w3c.dom.NodeList nodes = rule.getChildNodes();
//        println(rule)

        Element ruleConversion = document.getElementsByTagName("ruleBase").item(0);
        org.w3c.dom.NodeList ruleConversionNodes = ruleConversion.getChildNodes();
        for (int i = 0; i < ruleConversionNodes.getLength(); i++) {
            if (ruleConversionNodes.item(i).getNodeName() == ruleName) {
                status = true
                org.w3c.dom.NodeList ruleNameChild = ruleConversionNodes.item(i).getChildNodes()
                for (int j = 0; j < ruleNameChild.getLength(); j++) {
                    if (ruleNameChild.item(j).getNodeName() != '#text') {
                        returnList << ruleNameChild.item(j).getNodeName()
//                        ruleNameChild.item(j).s
                        returnFormulaList << ruleNameChild.item(j).getAttribute('formula')
                        returnSymbolList << ruleNameChild.item(j).getAttribute('symbol')
                    }
                }
            }
        }
        returnMap.returnFormulaList = returnFormulaList
        returnMap.returnSymbolList = returnSymbolList
        returnMap.status = status
        returnMap.returnList = returnList
        return returnMap
    }

    def getFormulaAndSymbols(params, xmlNodes, List ruleNodeList) {

    }

    def saveNewExamTypeAndSubTyoe(params) {
        /*method for saving exam type and exam sub-type*/
        def status = true
        if (params.examTypeName1 != '') {
            def examTypeInst = new ExamType()
            examTypeInst.examTypeName = params.examTypeName1
            examTypeInst.totalMarksSymbol = params.totalMarksVariable1
            examTypeInst.marksSecuredSymbol = params.securedMarksVariable1
            examTypeInst.requiredTotalMarks = params.examTotalMarks1
            if (examTypeInst.save(flush: true, failOnError: true)) {
                def ruleSymbolInst=new RuleSymbols()
                /*saving rule symbol*/
                ruleSymbolInst.examType=examTypeInst.id
                ruleSymbolInst.isEditable=true
                ruleSymbolInst.symbol=params.totalMarksVariable1
                ruleSymbolInst.text="total marks"
                ruleSymbolInst.save(flush: true)
                def ruleSymbolInst1=new RuleSymbols()
                /*saving rule symbol*/
                ruleSymbolInst1.examType=examTypeInst.id
                ruleSymbolInst1.isEditable=true
                ruleSymbolInst1.symbol=params.securedMarksVariable1
                ruleSymbolInst1.text="marks secured"
                ruleSymbolInst1.save(flush: true)
                def examSubTypeList = []
                /*saving exam subType*/
                if(params.moreSubType1!='') {
                    if (params.moreSubType1.getClass() == String) {
                        examSubTypeList << params.moreSubType1
                    } else {
                        params.moreSubType1.each {
                            examSubTypeList << it
                        }
                    }
                    examSubTypeList.each {
                        def examSubTypeInst = new ExamSubType()
                        /*saving exam subType*/
                        examSubTypeInst.examSubTypeName = it
                        examSubTypeInst.examTypeId = examTypeInst.id
                        if (examSubTypeInst.save(flush: true, failOnError: true)) {

                        } else {
                            status = false
                        }
                    }
                }
            }
        }
        if (params.examTypeName2 != '') {
            def examTypeInst = new ExamType()
            /*saving exam type*/
            examTypeInst.examTypeName = params.examTypeName2
            examTypeInst.totalMarksSymbol = params.totalMarksVariable2
            examTypeInst.marksSecuredSymbol = params.securedMarksVariable2
            examTypeInst.requiredTotalMarks = params.examTotalMarks2
            if (examTypeInst.save(flush: true, failOnError: true)) {
                def ruleSymbolInst=new RuleSymbols()
                /*saving rule symbol*/
                ruleSymbolInst.examType=examTypeInst.id
                ruleSymbolInst.isEditable=true
                ruleSymbolInst.symbol=params.totalMarksVariable2
                ruleSymbolInst.text="total marks"
                ruleSymbolInst.save(flush: true)
                def ruleSymbolInst1=new RuleSymbols()
                /*saving rule symbol*/
                ruleSymbolInst1.examType=examTypeInst.id
                ruleSymbolInst1.isEditable=true
                ruleSymbolInst1.symbol=params.securedMarksVariable2
                ruleSymbolInst1.text="marks secured"
                ruleSymbolInst1.save(flush: true)
                def examSubTypeList = []
                if(params.moreSubType2!='') {
                    /*saving exam subType*/
                    if (params.moreSubType2.getClass() == String) {
                        examSubTypeList << params.moreSubType2
                    } else {
                        params.moreSubType2.each {
                            examSubTypeList << it
                        }
                    }
                    examSubTypeList.each {
                        def examSubTypeInst = new ExamSubType()
                        examSubTypeInst.examSubTypeName = it
                        examSubTypeInst.examTypeId = examTypeInst.id
                        if (examSubTypeInst.save(flush: true, failOnError: true)) {

                        } else {
                            status = false
                        }
                    }
                }
            }
        }
        if (params.examTypeName3 != '') {
            def examTypeInst = new ExamType()
            /*for saving exam type*/
            examTypeInst.examTypeName = params.examTypeName3
            examTypeInst.totalMarksSymbol = params.totalMarksVariable3
            examTypeInst.marksSecuredSymbol = params.securedMarksVariable3
            examTypeInst.requiredTotalMarks = params.examTotalMarks3
            if (examTypeInst.save(flush: true, failOnError: true)) {
                def ruleSymbolInst=new RuleSymbols()
                /*saving rule symbol*/
                ruleSymbolInst.examType=examTypeInst.id
                ruleSymbolInst.isEditable=true
                ruleSymbolInst.symbol=params.totalMarksVariable3
                ruleSymbolInst.text="total marks"
                ruleSymbolInst.save(flush: true)
                def ruleSymbolInst1=new RuleSymbols()
                /*saving rule symbol*/
                ruleSymbolInst1.examType=examTypeInst.id
                ruleSymbolInst1.isEditable=true
                ruleSymbolInst1.symbol=params.securedMarksVariable3
                ruleSymbolInst1.text="marks secured"
                ruleSymbolInst1.save(flush: true)
                /*for exam subType*/
                def examSubTypeList = []
                if(params.moreSubType3!='') {
                    if (params.moreSubType3.getClass() == String) {
                        examSubTypeList << params.moreSubType3
                    } else {
                        params.moreSubType3.each {
                            examSubTypeList << it
                        }
                    }
                    examSubTypeList.each {
                        def examSubTypeInst = new ExamSubType()
                        examSubTypeInst.examSubTypeName = it
                        examSubTypeInst.examTypeId = examTypeInst.id
                        if (examSubTypeInst.save(flush: true, failOnError: true)) {

                        } else {
                            status = false
                        }
                    }
                }
            }
        }
        return status
    }
    def saveNewTotalRule(params){
        /*method for saving rule details for exam type*/
        def result= true
        def forTotalOfList=[],ruleVariableList=[],ruleExpressionList=[]
        if(params.forTotalOf.getClass()==String){
            forTotalOfList<<params.forTotalOf
            ruleVariableList<<params.ruleVariable
            ruleExpressionList<<params.ruleExpression
        }
        else{
            params.forTotalOf.eachWithIndex{itr,i->
                forTotalOfList<<itr
                ruleVariableList<<params.ruleVariable[i]
                ruleExpressionList<<params.ruleExpression[i]

            }
        }
        ruleExpressionList.eachWithIndex{entry,i ->
            def ruleSymbolInst=new RuleSymbols()
            ruleSymbolInst.examType=forTotalOfList[i]
            ruleSymbolInst.isEditable=true
            ruleSymbolInst.symbol=ruleVariableList[i]
            ruleSymbolInst.text="marks secured after applying Rule"
            if(ruleSymbolInst.save(flush: true)){
                def ruleInst=new Rule()
                ruleInst.examType=forTotalOfList[i]
                ruleInst.expression=entry
                ruleInst.variableOfResult=ruleVariableList[i]
                if(ruleInst.save(flush: true,failOnError: true)){

                }
                else{
                    result=false
                    ruleSymbolInst.delete(flush: true)
                }
            }
        }
        return result
    }
    def saveNewMainRule(params){
        /*for saving details of main rule for marks evaluation(name, variable , expression etc)*/
        def result= true
        def ruleNameList=[],ruleVariableList=[],ruleMaxVariableList=[],ruleExpressionList=[]
        if(params.ruleName.getClass()==String){
            ruleMaxVariableList<<params.ruleMaxVariable
            ruleVariableList<<params.ruleVariable
            ruleExpressionList<<params.ruleExpression
            ruleNameList<<params.ruleName
        }
        else{
            params.ruleName.eachWithIndex{itr,i->
                ruleMaxVariableList<<params.ruleMaxVariable[i]
                ruleNameList<<itr
                ruleVariableList<<params.ruleVariable[i]
                ruleExpressionList<<params.ruleExpression[i]

            }
        }
        ruleExpressionList.eachWithIndex{entry,i ->
            def status=true
            def ruleNameInst=new RuleNames()
            ruleNameInst.rule=ruleNameList[i]
            ruleNameInst.ruleRequired=true
            if(ruleNameInst.save(flush: true)){
                def ruleSymbolInst=new RuleSymbols()
                ruleSymbolInst.ruleNames=ruleNameInst.id
                ruleSymbolInst.isEditable=true
                ruleSymbolInst.symbol=ruleVariableList[i]
                ruleSymbolInst.text="marks secured after applying Rule"
                if(ruleSymbolInst.save(flush: true)){}
                else{
                    status=false
                }
                def ruleSymbolIns=new RuleSymbols()
                ruleSymbolIns.ruleNames=ruleNameInst.id
                ruleSymbolIns.isEditable=true
                ruleSymbolIns.symbol=ruleMaxVariableList[i]
                ruleSymbolIns.text="max marks among All, after applying Rule"
                if(ruleSymbolIns.save(flush: true)){}
                else{
                    status=false
                }
                if(status) {
                    def ruleInst = new Rule()
                    ruleInst.ruleNames = ruleNameInst.id
                    ruleInst.expression = entry
                    ruleInst.variableOfResult=ruleVariableList[i]
                    ruleInst.variableOfMaxInAll=ruleMaxVariableList[i]
                    if (ruleInst.save(flush: true, failOnError: true)) {

                    } else {
                        result = false
                        ruleSymbolInst.delete(flush: true)
                        ruleSymbolIns.delete(flush: true)
                        ruleNameInst.delete(flush: true)
                    }
                }
                else{
                    if(RuleSymbols.exists(ruleSymbolInst.id))
                        ruleSymbolInst.delete(flush: true)
                    if(RuleSymbols.exists(ruleSymbolIns.id))
                        ruleSymbolIns.delete(flush: true)
                    ruleNameInst.delete(flush: true)
                }
            }

        }
        return result
    }
}
