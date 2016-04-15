package postexamination

import com.university.Role
import com.university.User
import examinationproject.CourseSubject
import examinationproject.ProgramDetail
import examinationproject.ProgramSession
import examinationproject.Semester
import examinationproject.Status
import examinationproject.Student
import examinationproject.StudentSubject
import examinationproject.Subject
import examinationproject.SubjectSession
import grails.converters.JSON
import grails.transaction.Transactional
import jxl.Cell
import jxl.Sheet
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.write.WritableWorkbook
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.w3c.dom.Document
import org.w3c.dom.Element
import postExamination.EvaluateString

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

@Transactional
class PostExaminationService {
    //def writeExcelService
    def marksFoilExcelService
    def springSecurityService

    def serviceMethod() {

    }

    def dataForMarksFoilSheetPdf(params, semester) {
        def stuList = []

        try {
            def regYear = (ProgramSession.findById(params.programSessionId).sessionOfProgram).substring(0, 4)

            def studentObj = Student.createCriteria()
            stuList = studentObj.list {
                programDetail {
                    eq('id', Long.parseLong(params.programId))
                }
                and
                        {
                            eq('semester', semester.semesterNo)
                            eq('status', Status.findById(4))
                            eq("admitCardGenerated", true)
                            eq('registrationYear', Integer.parseInt(regYear))

                        }
            }.rollNo

        }
        catch (Exception e) {
            println(" Problem in service for generating marks foil sheet for PDF" + e)
        }
        return stuList

    }

    def getMarksFoilData(params, excelPath) {

        def stuList = []
        def status = false
        if (params.btn == "excel") {
            File file = new File('' + excelPath);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);

            def course = ProgramDetail.findById(Integer.parseInt(params.programId)).courseName
            def programSession = ProgramSession.get(Integer.parseInt(params.programSessionId))
            def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.semester))
            def subjectName = Subject.get(Integer.parseInt(params.courseCode)).subjectName
            def regYear = (ProgramSession.findById(params.programSessionId).sessionOfProgram).substring(0, 4)
            def currentYear = new Date().format("yyyy")
            def studentObj = Student.createCriteria()
            stuList = studentObj.list {
                programDetail {
                    eq('id', Long.parseLong(params.programId))
                }
                and
                        {
                            eq('semester', semester.semesterNo)
                            eq('status', Status.findById(4))
                            eq("admitCardGenerated", true)
                            eq('registrationYear', Integer.parseInt(regYear))

                        }
            }.rollNo

            int count = 0
            if (stuList) {
                status = marksFoilExcelService.excelReport(params, stuList, course, count, workbook, currentYear, semester.semesterNo)
                workbook.write();
                workbook.close();
            }


            return status
        }
    }

    def getRollNoForMisMatchUpdate(params) {
        def finalList = [], counter = 1
        def semesterIns = Semester.findById(params.semester)
        def stuList = studentList(params, semesterIns)
        if (stuList) {
            for (def i = 0; i < stuList.size(); i++) {
                def stuMarks1 = StudentMarks.findBySubjectIdAndStudentAndRoleId(Subject.findById(params.subjectId), stuList[i], Role.get(9), MarksType.findById(params.marksType))
                def stuMarks2 = StudentMarks.findBySubjectIdAndStudentAndRoleId(Subject.findById(params.subjectId), stuList[i], Role.get(10), MarksType.findById(params.marksType))
                def firstMarks, secondMarks
                if (params.marksType == '1') {
                    firstMarks = stuMarks1?.theoryMarks
                    secondMarks = stuMarks2?.theoryMarks
                } else if (params.marksType == '2') {
                    firstMarks = stuMarks1?.practicalMarks
                    secondMarks = stuMarks2?.practicalMarks
                } else if (params.marksType == '3') {
                    firstMarks = stuMarks1?.homeAssignmentMarks
                    secondMarks = stuMarks2?.homeAssignmentMarks
                } else if (params.marksType == '4') {
                    firstMarks = stuMarks1?.project
                    secondMarks = stuMarks2?.project
                }
                if (firstMarks != secondMarks) {
                    finalList << stuList[i]
                }
            }
        }
        return finalList
    }


    def getDetailForMisMatch(params, courseList, semesterIns, groupSubList) {
        def returnMap = [:]
        def finalList = [], headerList = [], marksType = [], counter = 1
        def stuList = studentList(params, semesterIns)
        if (stuList && courseList) {

            for (def i = 0; i < stuList.size(); i++) {
                def resultList = [], checkFlag = false
                resultList << counter
                resultList << stuList[i].rollNo
                for (def j = 0; j < courseList.size(); j++) {
                    def missMatchBlank = false, missMatch = false
                    def marksTypeObj = []
                    def subMarksInst = SubjectMarksDetail.findAllBySubjectSession(courseList[j])
                    if (subMarksInst.theory) {
                        marksTypeObj << MarksType.findById(1)
                    }
                    if (subMarksInst.home) {
                        marksTypeObj << MarksType.findById(2)
                    }
                    if (subMarksInst.practical) {
                        marksTypeObj << MarksType.findById(3)
                    }
                    if (subMarksInst.project) {
                        marksTypeObj << MarksType.findById(4)
                    }
                    if (i == 0) {
                        headerList << courseList[j].subjectId.subjectName
                    }
                    marksTypeObj.each {
                        def stuMarks1 = StudentMarks.findBySubjectIdAndStudentAndRoleId(courseList[j].subjectId, stuList[i], Role.get(9))
                        def stuMarks2 = StudentMarks.findBySubjectIdAndStudentAndRoleId(courseList[j].subjectId, stuList[i], Role.get(10))
                        if (!missMatch) {
                            if (stuMarks1 == null && stuMarks2 == null) {
                                missMatchBlank = true
                            } else if (stuMarks1?.theoryMarks != stuMarks2?.theoryMarks && it.id == 1) {
                                missMatch = true
                                missMatchBlank = false
                            } else if (stuMarks1?.practicalMarks != stuMarks2?.practicalMarks && it.id == 2) {
                                missMatch = true
                                missMatchBlank = false
                            } else if (stuMarks1?.homeAssignmentMarks != stuMarks2?.homeAssignmentMarks && it.id == 3) {
                                missMatch = true
                                missMatchBlank = false
                            } else if (stuMarks1?.project != stuMarks2?.project && it.id == 4) {
                                missMatch = true
                                missMatchBlank = false
                            } else if (stuMarks1 != null && stuMarks2 == null) {
                                missMatch = true
                                missMatchBlank = false
                            } else if (stuMarks1 == null && stuMarks2 != null) {
                                missMatch = true
                                missMatchBlank = false
                            }
                        }
                    }
                    if (!missMatchBlank && !missMatch) {
                        resultList << ""
                    } else if (missMatchBlank) {
                        resultList << "X"
                    } else if (missMatch) {
                        resultList << "?"
                    }
                }
                if (groupSubList.size() > 0) {
                    for (def j = 0; j < groupSubList.size(); j++) {
                        for (def k = 0; k < groupSubList[j].size(); k++) {
                            def missMatchBlank = false, missMatch = false
                            def marksTypeObj = []
                            def subMarksInst = SubjectMarksDetail.findAllBySubjectSession(groupSubList[j])
                            if (subMarksInst.theory) {
                                marksTypeObj << MarksType.findById(1)
                            }
                            if (subMarksInst.home) {
                                marksTypeObj << MarksType.findById(2)
                            }
                            if (subMarksInst.practical) {
                                marksTypeObj << MarksType.findById(3)
                            }
                            if (subMarksInst.project) {
                                marksTypeObj << MarksType.findById(4)
                            }
                            if (j == 0) {
                                headerList << groupSubList[j].subjectId.subjectName
                            }
                            marksTypeObj.each {
                                def stuMarks1 = StudentMarks.findBySubjectIdAndStudentAndRoleId(groupSubList[j].subjectId, stuList[i], Role.get(9))
                                def stuMarks2 = StudentMarks.findBySubjectIdAndStudentAndRoleId(groupSubList[j].subjectId, stuList[i], Role.get(10))

                                if (!missMatch) {
                                    if (stuMarks1 == null && stuMarks2 == null) {
                                        missMatchBlank = true
                                    } else if (stuMarks1?.theoryMarks != stuMarks2?.theoryMarks && it.id == 1) {
                                        missMatch = true
                                        missMatchBlank = false
                                    } else if (stuMarks1?.practicalMarks != stuMarks2?.practicalMarks && it.id == 2) {
                                        missMatch = true
                                        missMatchBlank = false
                                    } else if (stuMarks1?.homeAssignmentMarks != stuMarks2?.homeAssignmentMarks && it.id == 3) {
                                        missMatch = true
                                        missMatchBlank = false
                                    } else if (stuMarks1?.project != stuMarks2?.project && it.id == 4) {
                                        missMatch = true
                                        missMatchBlank = false
                                    } else if (stuMarks1 != null && stuMarks2 == null) {
                                        missMatch = true
                                        missMatchBlank = false
                                    } else if (stuMarks1 == null && stuMarks2 != null) {
                                        missMatch = true
                                        missMatchBlank = false
                                    }
                                }
                            }
                            if (!missMatchBlank && !missMatch) {
                                resultList << ""
                            } else if (missMatchBlank) {
                                resultList << "X"
                            } else if (missMatch) {
                                resultList << "?"
                            }
                        }

                    }


                }
                if (checkFlag) {
                    resultList << "R"
                } else {
                    resultList << "F"
                }
                finalList << resultList
                ++counter;
            }

        }
        returnMap.finalList = finalList
        returnMap.headerList = headerList
        returnMap.marksType = marksType

        return returnMap


    }


    def studentList(params, semester) {
        def stuList = []
        try {
            def regYear = (ProgramSession.findById(params.session).sessionOfProgram).substring(0, 4)

            def studentObj = Student.createCriteria()
            stuList = studentObj.list {
                programDetail {
                    eq('id', Long.parseLong(params.programId))
                }
                and
                        {
                            eq('semester', semester.semesterNo)
                            eq('status', Status.findById(4))
                            eq("admitCardGenerated", true)
                            eq('registrationYear', Integer.parseInt(regYear))

                        }
            }

        }
        catch (Exception e) {
            println(" Problem in service for getting student list for mis-match report" + e)
        }
        return stuList

    }

    def getTabulatorMarks(params) {
        def marks = [:]
        def tab1MarksInst = [], tab2MarksInst = []
        def tab1Obj = StudentMarks.createCriteria()
        tab1MarksInst = StudentMarks.findBySubjectIdAndStudent(Subject.findBySubjectCode(params.subjectId), Student.findById(Long.parseLong(params.studentId)))
        return tab1MarksInst
    }

    def saveMisMatchMarks(params) {
        def marks = [:]
        def tab1MarksInst = [], tab2MarksInst = []
        def tab1Obj = StudentMarks.createCriteria()
        def tab2Obj = StudentMarks.createCriteria()
        tab1MarksInst = tab1Obj.list {
            student {
                eq('id', Long.parseLong(params.studentId))
            }
            and {
                eq('semesterNo', Semester.findById(Long.parseLong(params.semester)).semesterNo)
                eq("subjectId", Subject.get(Integer.parseInt(params.subjectId)))
                eq('roleId', Role.get(9))
            }
        }
        tab2MarksInst = tab2Obj.list {
            student {
                eq('id', Long.parseLong(params.studentId))
            }
            and {
                eq('semesterNo', Semester.findById(Long.parseLong(params.semester)).semesterNo)
                eq("subjectId", Subject.get(Integer.parseInt(params.subjectId)))
                eq('roleId', Role.get(10))
            }
        }
        tab1MarksInst.each {
            def prevMarks
            if (params.marksType == '1') {
                prevMarks = it.theoryMarks
                it.theoryMarks = params.updatedMarks
            } else if (params.marksType == '2') {
                prevMarks = it.practicalMarks
                it.practicalMarks = params.updatedMarks
            } else if (params.marksType == '3') {
                prevMarks = it.homeAssignmentMarks
                it.homeAssignmentMarks = params.updatedMarks
            } else if (params.marksType == '4') {
                prevMarks = it.project
                it.project = params.updatedMarks
            }
            it.totalMarks = (Integer.parseInt(it.totalMarks) - Integer.parseInt(prevMarks)) + Integer.parseInt(params.updatedMarks)
        }
        tab2MarksInst.each {
            def prevMarks
            if (params.marksType == '1') {
                prevMarks = it.theoryMarks
                it.theoryMarks = params.updatedMarks
            } else if (params.marksType == '2') {
                prevMarks = it.practicalMarks
                it.practicalMarks = params.updatedMarks
            } else if (params.marksType == '3') {
                prevMarks = it.homeAssignmentMarks
                it.homeAssignmentMarks = params.updatedMarks
            } else if (params.marksType == '4') {
                prevMarks = it.project
                it.project = params.updatedMarks
            }
            it.totalMarks = (Integer.parseInt(it.totalMarks) - Integer.parseInt(prevMarks)) + Integer.parseInt(params.updatedMarks)

        }
        marks.status = true
        return marks
    }

    def generateProgramResults(params, xmlNodes) {
        def returnMap = [:]

        def passInAll = [], partiallyPass = [], finalStuList = []
//        def misMatchStatus = StudentMarks.findAllByCorrectMarkIsNull()
//        if (misMatchStatus.size() == 0) {
        def progSessionInst = ProgramSession.findById(Long.parseLong(params.sessionId))
        def semesterInst = Semester.findById(Long.parseLong(params.semesterId))
        def studentList = Student.findAllByProgramSessionAndSemesterAndAdmitCardGeneratedAndStatus(progSessionInst, semesterInst.semesterNo, true, Status.get(4))
        def subjectList = CourseSubject.findAllByProgramSessionAndSemester(progSessionInst, semesterInst).subjectSessionId.subjectId
        def subjectGroupList = ProgramGroup.findAllByProgramSessionAndSemester(progSessionInst, semesterInst)
        if (subjectGroupList) {
            subjectGroupList.each {
                def groupSubjectList = ProgramGroupDetail.findAllByProgramGroupId(it).subjectSessionId.subjectId
                groupSubjectList.each { itr ->
                    subjectList << itr
                }
            }

        }

        def subjectMarksList = minMarksForSubjectsFromXML(subjectList, xmlNodes)
        def totalMarksList = [], totalStudentList = []
        def tempMarksVariable = 0
        for (def i = 0; i < studentList.size(); i++) {
            def status = true
            def pass = true
            def checkFlag = false
            tempMarksVariable = 0

            if (subjectList.size() > 0) {

                for (def j = 0; j < subjectList.size(); j++) {

                    if (!pass || !status) {
                        break
                    } else {
                        if (pass) {
                            def tab1Marks = StudentMarks.findByStudentAndSubjectIdAndSemesterNoAndRoleId(studentList[i], subjectList[j], Integer.parseInt(semesterInst.id.toString()), Role.get(9))

                            if (tab1Marks) {
                                tempMarksVariable += Integer.parseInt(tab1Marks.totalMarks)

                                if (tab1Marks != null) {
                                    subjectMarksList.each {

                                        it.eachWithIndex { itr, index ->

                                            if (Integer.parseInt(tab1Marks.theoryMarks) < Integer.parseInt(itr) && index == 0) {
                                                pass = false
                                                partiallyPass << studentList[i]
                                            } else {
                                                checkFlag = true
                                            }

                                            if (Integer.parseInt(tab1Marks.homeAssignmentMarks) < Integer.parseInt(itr) && index == 1) {
                                                pass = false
                                                partiallyPass << studentList[i]
                                            } else {
                                                checkFlag = true
                                            }
                                            if (Integer.parseInt(tab1Marks.practicalMarks) < Integer.parseInt(itr) && index == 2) {
                                                pass = false
                                                partiallyPass << studentList[i]
                                            } else {
                                                checkFlag = true
                                            }
                                        }
                                    }
                                } else {
                                    status = false
                                }
                            }
                        }
                    }

                }
                if (pass && checkFlag) {
                    passInAll << studentList[i]
                    finalStuList << " "
                }
            }

            totalMarksList << tempMarksVariable
            totalStudentList << studentList[i]
        }

        finalStuList = totalMarksAndResultUpdation(totalStudentList, totalMarksList, partiallyPass, passInAll, finalStuList)

        returnMap.studentPartialPassList = partiallyPass.unique()
        returnMap.studentPassList = finalStuList
        returnMap.status = true

        return returnMap
    }

    def minMarksForSubjectsFromXML(subjectList, xmlNodes) {
        def finalMarksList = []
        subjectList.each {
            def marksList = []
            xmlNodes.subject.each { itr ->

                if (it.subjectCode.contains(itr?.@code)) {
                    if (marksList.size() > 0) {
                        marksList = []
                    }
                    marksList << itr?.theory?.@minMarks[0]
                    marksList << itr?.home?.@minMarks[0]
                    marksList << itr?.practical?.@minMarks[0]

                }

            }
            finalMarksList << marksList
        }
        return finalMarksList

    }


    def totalMarksAndResultUpdation(totalStudentList, totalMarksList, partiallyPass, passInAll, finalStuList) {

        totalStudentList.eachWithIndex { it, index ->
            if (it.totalMarks) {
                def getBackJsonObj = grails.converters.JSON.parse(it.totalMarks)
                getBackJsonObj.putAt(it.semester, totalMarksList[index])
                it.totalMarks = getBackJsonObj.toString()
            } else {
                def tempMap = [:]
                tempMap[it.semester] = totalMarksList[index]
                def newJsonObj = tempMap as JSON
                it.totalMarks = newJsonObj.toString()
            }
            it.save(failOnError: true)
        }

        def tempMap = [:]
        passInAll.each {
            def getBackJsonObj = grails.converters.JSON.parse(it.totalMarks)
            tempMap[it.id] = getBackJsonObj[it.semester.toString()]
            if (it.result) {
                def getBackJsonObj1 = grails.converters.JSON.parse(it.result)
                getBackJsonObj1.putAt(it.semester, 'Pass')
                it.result = getBackJsonObj1.toString()
            } else {
                def tempMap1 = [:]
                tempMap1[it.semester] = 'Pass'
                def newJsonObj = tempMap1 as JSON
                it.result = newJsonObj.toString()
            }
            it.save(failOnError: true)
        }

        partiallyPass.unique().each {
            if (it.result) {
                def getBackJsonObj1 = grails.converters.JSON.parse(it.result)
                getBackJsonObj1.putAt(it.semester, 'Fail')
                it.result = getBackJsonObj1.toString()
            } else {
                def tempMap1 = [:]
                tempMap1[it.semester] = 'Fail'
                def newJsonObj = tempMap1 as JSON
                it.result = newJsonObj.toString()
            }
            it.save(failOnError: true)

        }


        Map sorted = tempMap.sort { a, b -> a.value <=> b.value }

        def keyList = sorted.keySet()
        for (int i = 0; i < passInAll.size(); i++) {
            for (int j = 0; j < keyList.size(); j++) {
                if (passInAll[i].id == keyList[j]) {
                    finalStuList.remove(j)
                    finalStuList.add(j, passInAll[i])
                }

            }
        }

        return finalStuList

    }

    def finalResult(params) {
        def returnMap = [:]
        def tempMap = [:]
        def newStuList = [], finalStuList = []
        def progSessionInst = ProgramSession.findById(Long.parseLong(params.sessionId))
        def semesterInst = Semester.findById(Long.parseLong(params.semesterId))

        def studentList = Student.findAllByProgramSessionAndSemesterAndAdmitCardGeneratedAndStatus(progSessionInst, semesterInst.semesterNo, true, Status.get(4))


        studentList.each {
            def getBackJsonObj = grails.converters.JSON.parse(it.result)
            def getBackJsonObj1 = grails.converters.JSON.parse(it.totalMarks)
            def result = getBackJsonObj[it.semester.toString()]

            if (result.toString().equalsIgnoreCase("Pass")) {
                newStuList << it
                finalStuList << ""
                tempMap[it.id] = getBackJsonObj1[it.semester.toString()]
            }
        }
        Map sorted = tempMap.sort { a, b -> a.value <=> b.value }


        def keyList = sorted.keySet()
        for (int i = 0; i < newStuList.size(); i++) {
            for (int j = 0; j < keyList.size(); j++) {
                if (newStuList[i].id == keyList[j]) {
                    finalStuList.remove(j)
                    finalStuList.add(j, newStuList[i])
                }

            }
        }

        returnMap.studentMeritList = finalStuList
        returnMap.totalStudentsAppeared = studentList.size()
        returnMap.status = true

        return returnMap

    }

    def studentMeritRegisterData(params, excelPath, studentList, xmlNodes, subjectList, semesterIns, programSession) {

        def subMarksObj = SubjectMarksDetail

        def marksList = [], finalMarksTypeList = [], subjectMarksList = [], finalList = [], passingSubjectNoList = []
        studentList.eachWithIndex { it, index ->
            def stuMarksList = []
            def counter = 0
            subjectList.each { itr ->


                subMarksObj = SubjectMarksDetail.findBySubjectSession(SubjectSession.findBySubjectId(itr))
                if (index == 0) {
                    def marksTypeList = []
                    if (subMarksObj.theory) {
                        marksTypeList << "Exam"
                    }
                    if (subMarksObj.home) {
                        marksTypeList << "HA"
                    }
                    if (subMarksObj.practical) {
                        marksTypeList << "Practical"

                    }
                    if (subMarksObj.project) {
                        marksTypeList << "Project"
                    }
                    marksTypeList << "Total"
                    ++counter
                    finalMarksTypeList << marksTypeList
                }

                def stuMarksObj = StudentMarks.findBySubjectIdAndStudentAndSemesterNo(itr, it, Integer.parseInt(semesterIns.id.toString()))

                if (subMarksObj.theory) {
                    stuMarksList << stuMarksObj.theoryMarks
                }
                if (subMarksObj.home) {
                    stuMarksList << stuMarksObj.homeAssignmentMarks
                }
                if (subMarksObj.practical) {
                    stuMarksList << stuMarksObj.practicalMarks
                }
                if (subMarksObj.project) {
                    stuMarksList << stuMarksObj.project
                }
//                    stuMarksList<<stuMarksObj.totalMarks

                marksList = []
                def subMinPassingMarks = 0
//                    def stuMarksObject = StudentMarks.findByStudentAndSubjectIdAndSemesterNoAndRoleId(it, itr, Integer.parseInt(semesterIns.id.toString()), Role.get(9))

                xmlNodes.subject.each { itr1 ->


                    if (itr.subjectCode.contains(itr1?.@code)) {

                        subMinPassingMarks = Integer.parseInt(itr1?.@minMarks)

                        //                            else{
//                                marksList<<"no"
//                            }
//                            if(Integer.parseInt(itr1?.home?.@minMarks[0])>0){
//                                marksList<<stuMarksObj.homeAssignmentMarks
//                            }
//                            else{
//                                marksList<<"no"
//                            }
//                            if(Integer.parseInt(itr1?.practical?.@minMarks[0])>0){
//                                marksList<<stuMarksObject.practicalMarks
//                            }
//                            else{
//                                marksList<<"no"
//                            }
//
//                        }
//                        if(Integer.parseInt(stuMarksObject.totalMarks)<Integer.parseInt(itr1?.@minMarks)){
//                            passingSubjectNoList<<"0"
//
//                        }else{
//                            passingSubjectNoList<<counter
                    }

                }
                if (stuMarksObj.totalMarks && Integer.parseInt(stuMarksObj.totalMarks) > subMinPassingMarks) {
                    stuMarksList << stuMarksObj.totalMarks
                    passingSubjectNoList << counter

                } else {
                    stuMarksList << "NC"
                    passingSubjectNoList << "No"
                }

                subjectMarksList << marksList

            }
//                println("======="+studentSubjectMarksList)
            finalList << stuMarksList
//                finalList
        }



        def status = ""
        File file = new File('' + excelPath);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);


        if (studentList) {
            int count = 0
            def subjectCount = subjectList.size()
            def course = ProgramDetail.findById(Integer.parseInt(params.programId)).courseName
            status = marksFoilExcelService.excelReport1(studentList, finalList, finalMarksTypeList, subjectCount, course, count, workbook, semesterIns.semesterNo)
            workbook.write();
            workbook.close();
        }


        return status

    }

    def homeAssignmentExcelUpload(fileToBeUploaded, params) {
        String fileName = fileToBeUploaded.originalFilename
        def docDirectory = ServletContextHolder.servletContext.getRealPath(System.getProperty("file.separator"))
        def dir = new File(docDirectory + System.getProperty("file.separator") + "documentFile" + System.getProperty("file.separator"))
        dir.mkdirs()
        def subjectList = []
        String path = ServletContextHolder.servletContext.getRealPath(System.getProperty("file.separator") + "documentFile" + System.getProperty("file.separator"))
        fileToBeUploaded.transferTo(new File(path, fileName))
        File inputWorkbook = new File(path + "/" + fileName)
        def programDetail = ProgramDetail.findById(params.programId)
        def programSession = ProgramSession.get(Integer.parseInt(params.SessionList))
        def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.programTerm))
//        def groupType=ProgramGroup.findAllBySemester(semester)
        def subjectGenList = CourseSubject.findAllByCourseDetailAndSemesterAndProgramSession(programDetail, semester, programSession).subjectSessionId.subjectId
        if (subjectGenList) {
            subjectGenList.each {
                subjectList << it.id
            }
        }

        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            Sheet sheet = w.getSheet(0);
            def headRow = 5
            Cell col = sheet.getCell(j, headRow);
            def semg
            for (int j = 0; j < sheet.getColumns(); j++) {
                def rollNo
                for (int i = headRow + 1; i < sheet.getRows(); i++) {
                    if (col.getContents().toLowerCase() == 'roll no.') {
                        Cell cols = sheet.getCell(j, i);
                        rollNo = cols.getContents()
                    }
                    subjectList.each {
                        def subjectInst = Subject.findById(it)
                        if (subjectInst.aliasCode.matches(".*(A|B|C|D).*")) {
                            if (programGroupIns.groupSelectionType.toLowerCase() == 'single') {
                                if (col.getContents().toLowerCase() == 'semg') {
                                    Cell cols = sheet.getCell(j, i);
                                    semg = cols.getContents()
                                }
                                if (semg != null || semg != '') {
                                    if (col.getContents().concat(semg) == subjectInst.aliasCode) {
                                        Cell cols = sheet.getCell(j, i);
                                        updateStudentMarksExcel(rollNo, semester, subjectInst, cols.getContents())
                                    }
                                }
                            } else if ((programGroupIns.groupSelectionType.toLowerCase() == 'multiplesubject') || (programGroupIns.groupSelectionType.toLowerCase() == 'multiplesubject')) {
                                Cell prev = sheet.getCell(j - 1, i);
                                def optg = prev.getContents()
                                if (optg != null || optg != '') {
                                    if (col.getContents().concat(optg) == subjectInst.aliasCode.substring(0, subjectInst.aliasCode.length() - 1)) {
                                        Cell cols = sheet.getCell(j, i);
                                        updateStudentMarksExcel(rollNo, semester, subjectInst, cols.getContents())
                                    }
                                }
                            }
                        } else {
                            if (col.getContents() == subjectInst.aliasCode) {
                                Cell cols = sheet.getCell(j, i);
                                updateStudentMarksExcel(rollNo, semester, subjectInst, cols.getContents())

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (inputWorkbook.exists()) {
            inputWorkbook.delete()
        }
    }

    def saveCourseExamination(params) {
        /*Save details of examination (Subject,ExamType,ExamSubType and marks etc)*/
        def status = 0
        def examinationInst
        def currentUser = springSecurityService.getCurrentUser()
        def subInst = Subject.findBySubjectCode(params.subjectCode)
        def examTypeInst = ExamType.findById(Long.parseLong(params.examType))
        if (params.examSubType != '') {
            def exmSubInst = ExamSubType.findById(Long.parseLong(params.examSubType))
            examinationInst = Examination.findByAcademicSessionAndExamTypesAndSubjectAndExamSubType(params.academicSession, examTypeInst.id.toString(), subInst, exmSubInst.id.toString())
        } else {
            examinationInst = Examination.findByAcademicSessionAndExamTypesAndSubject(params.academicSession, examTypeInst.id.toString(), subInst)
        }
        if (examinationInst) {
            if (Long.parseLong(params.updateExamInst) == examinationInst.id) {
                examinationInst.totalMarks = params.assignedTotalMarks
                examinationInst.user = User.findById(currentUser.id)
                if (examinationInst.save(flush: true, failOnError: true)) {
                    status = 3
                }
            } else {
                status = 1
            }

        } else {
            def newExamInst = new Examination()
            newExamInst.academicSession = params.academicSession
            if (params.examSubType != '')
                newExamInst.examSubType = ExamSubType.findById(Long.parseLong(params.examSubType)).id.toString()
            newExamInst.subject = subInst
            newExamInst.user = User.findById(currentUser.id)
            newExamInst.examTypes = examTypeInst.id.toString()
            newExamInst.totalMarks = params.assignedTotalMarks
            if (newExamInst.save(flush: true, failOnError: true)) {
                status = 2
            }
        }
        return status
    }

    def deleteCourseExamination(params) {
        /*method for deleting exam for particular subject(course)*/
        def examInst = Examination.findById(Long.parseLong(params.examId))
        def studentMarksList = StudentMarks.findAllByExamination(examInst)
        studentMarksList.each {
            it.delete()
        }
        examInst.delete()
        if (Examination.exists(examInst.id)) {
            return false
        } else {
            return true
        }
    }

    def marksEvaluationDetails(params) {
        /*method for evaluating marks(according to rule) of particular exam */
        def marksAfterRuleApply = 0
        def rollNoList = [], ruleResultsMap = [:], returnStatus = false, status = true
        def ruleApplySymbolMarksMap = [:]
        def subjectInst = Subject.findBySubjectCode(params.subjectCode)
        def subjectSession = SubjectSession.findBySubjectId(subjectInst)
        def ruleSymbolList = RuleSymbols.list().symbol.toArray()
        def statusInst = Status.findById(4)
        def statusUnapproveInst = Status.findById(1)
        def StudentList = StudentSubject.findAllByAcademicSessionAndStatusAndSubjectSession(params.academicSession, statusInst, subjectSession).student.unique()
        def examTypeList = ExamType.list()
        def ruleList = RuleNames.list()
        ruleList.each {
            def maxInRule = null
            def ruleNameInst = it
            StudentList.each {
                def stuInst = it
                if (status) {
                    examTypeList.each {
                        def examTypeName = it
                        def ruleInst = Rule.findByExamType(examTypeName.id)
                        if (ruleInst) {
                            def ruleExpression = ruleInst.expression
                            for (def j = 0; j < ruleSymbolList.length; j++) {
                                if (ruleExpression.contains(ruleSymbolList[j])) {
                                    def ruleTypeFor = RuleSymbols.findBySymbol(ruleSymbolList[j])
                                    def examTypeInsta = Examination.findByExamTypesAndAcademicSessionAndSubject(ruleTypeFor.examType, params.academicSession, subjectInst)
                                    def subTypeList = ExamSubType.findAllByExamTypeId(ruleTypeFor.examType)
                                    def totalMarks = 0, totalMarksSecured = 0
                                    if (subTypeList) {
                                        subTypeList.each {
                                            def examInst = Examination.findByExamSubTypeAndAcademicSessionAndSubject(it.id.toString(), params.academicSession, subjectInst)
                                            totalMarks += Integer.parseInt(examInst.totalMarks)
                                            def marksInst = StudentMarks.findByExaminationAndAcademicSessionAndStudentAndSubjectIdAndStatus(examInst.id, params.academicSession, stuInst, subjectInst, statusInst)
                                            if (marksInst.totalMarks != 'Absent')
                                                totalMarksSecured += Integer.parseInt(marksInst.totalMarks)
                                        }
                                    } else {
                                        def examInst = Examination.findByExamTypesAndAcademicSessionAndSubjectAndExamSubTypeIsNull(ruleTypeFor.examType, params.academicSession, subjectInst)
                                        totalMarks = examInst.totalMarks
                                        def marksInst = StudentMarks.findByExaminationAndAcademicSessionAndStudentAndSubjectIdAndStatus(examInst.id, params.academicSession, stuInst, subjectInst, statusInst)
                                        if (marksInst.totalMarks != 'Absent')
                                            totalMarksSecured = Integer.parseInt(marksInst.totalMarks)
                                    }
                                    if (ruleTypeFor.text == 'marks secured') {
                                        ruleExpression = ruleExpression.replace(ruleSymbolList[j], totalMarksSecured.toString())
                                    } else if (ruleTypeFor.text == 'total marks') {
                                        ruleExpression = ruleExpression.replace(ruleSymbolList[j], totalMarks.toString())
                                    } else if (ruleTypeFor.text == 'marks secured after applying Rule') {
                                        totalMarksSecured = ruleResultsMap.get(ruleSymbolList[j])
                                        ruleExpression = ruleExpression.replace(ruleSymbolList[j], totalMarksSecured.toString())
                                    }

                                }
                            }
                            def evaluatedMarks = EvaluateString.evaluate(ruleExpression.toString())
                            def stuExamTypeEvalInst = new StuExamTypeEvalMarks()
                            stuExamTypeEvalInst.academicSession = params.academicSession
                            stuExamTypeEvalInst.examTypeName = examTypeName.examTypeName
                            stuExamTypeEvalInst.subjectId = subjectInst
                            stuExamTypeEvalInst.student = stuInst
                            stuExamTypeEvalInst.evaluatedMarks = evaluatedMarks
                            stuExamTypeEvalInst.save(flush: true)
                            ruleResultsMap.put(ruleInst.variableOfResult, evaluatedMarks)
                            marksAfterRuleApply = evaluatedMarks
                        }
                    }
                }
                def ruleInst = Rule.findByRuleNames(ruleNameInst.id.toString())
                def ruleExpression = ruleInst.expression
                if (ruleNameInst.ruleRequired) {
                    for (def j = 0; j < ruleSymbolList.length; j++) {
                        if (ruleExpression.contains(ruleSymbolList[j])) {
                            def ruleTypeFor = RuleSymbols.findBySymbol(ruleSymbolList[j])
                            if (ruleTypeFor.text == 'marks secured after applying Rule') {
                                def marksInst = StuRuleEvalMarks.findByRuleNameAndStudentAndSubjectIdAndAcademicSession(ruleTypeFor.ruleNames, stuInst, subjectInst, params.academicSession)
                                def totalMarksSecured
                                if (marksInst != null)
                                    totalMarksSecured = marksInst.evaluatedMarks
                                else {
                                    totalMarksSecured = ruleResultsMap.get(ruleSymbolList[j])
                                }
                                ruleExpression = ruleExpression.replace(ruleSymbolList[j], totalMarksSecured.toString())
                            } else if (ruleTypeFor.text == 'max marks among All, after applying Rule') {
                                if (maxInRule == null) {
                                    def stuEvlObj = StuRuleEvalMarks.createCriteria()
                                    def stuEvlMaxMarks = stuEvlObj.get {
                                        projections {
                                            max "evaluatedMarks"
                                        }
                                        and {
                                            eq('subjectId', subjectInst)
                                            eq('academicSession', params.academicSession)
                                        }
                                    }
                                    maxInRule = stuEvlMaxMarks
                                }
                                ruleExpression = ruleExpression.replace(ruleSymbolList[j], maxInRule.toString())
                            } else if (ruleTypeFor.text == 'marks secured') {
                                def examTypeFor = RuleSymbols.findBySymbol(ruleSymbolList[j])
                                def examSubTypeList = ExamSubType.findAllByExamTypeId(examTypeFor.examType)
                                def totalMarksSecured = 0
                                if (examSubTypeList) {
                                    def examInst = Examination.findByExamSubTypeAndAcademicSessionAndSubject(it.id.toString(), params.academicSession, subjectInst)
                                    def marksInst = StudentMarks.findByExaminationAndAcademicSessionAndStudentAndSubjectId(examInst.id, params.academicSession, stuInst, subjectInst)
                                    if (marksInst.totalMarks != 'Absent')
                                        totalMarksSecured += Integer.parseInt(marksInst.totalMarks)
                                } else {
                                    def examInst = Examination.findByExamTypesAndAcademicSessionAndSubjectAndExamSubTypeIsNull(examTypeFor.examType, params.academicSession, subjectInst)
                                    def marksInst = StudentMarks.findByExaminationAndAcademicSessionAndStudentAndSubjectId(examInst.id, params.academicSession, stuInst, subjectInst)
                                    if (marksInst.totalMarks != 'Absent')
                                        totalMarksSecured = Integer.parseInt(marksInst.totalMarks)
                                }
                                ruleExpression = ruleExpression.replace(ruleSymbolList[j], totalMarksSecured.toString())
                            } else if (ruleTypeFor.text == 'total marks') {
                                def examTypeFor = RuleSymbols.findBySymbol(ruleSymbolList[j])
                                def examSubTypeList = ExamSubType.findAllByExamTypeId(examTypeFor.examType)
                                def totalMarks = 0
                                if (examSubTypeList) {
                                    def examInst = Examination.findByExamSubTypeAndAcademicSessionAndSubject(it.id.toString(), params.academicSession, subjectInst)
                                    totalMarks += examInst.totalMarks
                                } else {
                                    def examInst = Examination.findByExamTypesAndAcademicSessionAndSubjectAndExamSubTypeIsNull(examTypeFor.examType, params.academicSession, subjectInst)
                                    totalMarks += examInst.totalMarks
                                }
                                ruleExpression = ruleExpression.replace(ruleSymbolList[j], totalMarks.toString())
                            } else if (ruleTypeFor.text == 'lecture') {
                                def ltpcValue = subjectInst.lecture
                                ruleExpression = ruleExpression.replace(ruleSymbolList[j], ltpcValue.toString())
                            } else if (ruleTypeFor.text == 'tutorial') {
                                def ltpcValue = subjectInst.tutorial
                                ruleExpression = ruleExpression.replace(ruleSymbolList[j], ltpcValue.toString())
                            } else if (ruleTypeFor.text == 'credit') {
                                def ltpcValue = subjectInst.creditPoints
                                ruleExpression = ruleExpression.replace(ruleSymbolList[j], ltpcValue.toString())
                            } else if (ruleTypeFor.text == 'practical') {
                                def ltpcValue = subjectInst.practical
                                ruleExpression = ruleExpression.replace(ruleSymbolList[j], ltpcValue.toString())
                            }
                        }
                    }
                }
                def evaluatedMarks = EvaluateString.evaluate(ruleExpression.toString())
                def stuExamTypeEvalInst = new StuRuleEvalMarks()
                stuExamTypeEvalInst.academicSession = params.academicSession
                stuExamTypeEvalInst.ruleName = ruleNameInst.id
                stuExamTypeEvalInst.student = stuInst
                stuExamTypeEvalInst.subjectId = subjectInst
                stuExamTypeEvalInst.evaluatedMarks = evaluatedMarks
                stuExamTypeEvalInst.save(flush: true)
                ruleResultsMap.put(ruleInst.variableOfResult, evaluatedMarks)
                marksAfterRuleApply = evaluatedMarks
                def studentEvalMarksInst = StudentEvaluatedMarks.findBySubjectIdAndStudentAndAcademicSession(subjectInst, stuInst, params.academicSession)
                if (studentEvalMarksInst) {
                    studentEvalMarksInst.marksSecuredAfterRule = String.format("%.2f", marksAfterRuleApply)
                    studentEvalMarksInst.user = springSecurityService.currentUser
                    studentEvalMarksInst.status = statusUnapproveInst
                    returnStatus = true
                    rollNoList << stuInst.rollNo
                } else {
                    studentEvalMarksInst = new StudentEvaluatedMarks()
                    studentEvalMarksInst.marksSecuredAfterRule = String.format("%.2f", marksAfterRuleApply)
                    studentEvalMarksInst.user = springSecurityService.currentUser
                    studentEvalMarksInst.status = statusUnapproveInst
                    studentEvalMarksInst.student = stuInst
                    studentEvalMarksInst.academicSession = params.academicSession
                    studentEvalMarksInst.subjectId = subjectInst
                    if (studentEvalMarksInst.save(flush: true)) {
                        rollNoList << stuInst.rollNo
                        returnStatus = true
                    }
                }
            }
        }
        if (returnStatus) {
            return rollNoList.sort()
        } else {
            return null
        }
    }

    def replaceSymbolWithActualMarks(stuInst, params, returnFormulaList, ruleSymbolList) {
        for (def j = 0; j < ruleSymbolList.length; j++) {
            if (returnFormulaList.toString().contains(ruleSymbolList[j])) {
                def ruleTypeFor = RuleSymbols.findBySymbol(ruleSymbolList[j])
                if (ruleTypeFor) {
                    if (ruleTypeFor.examType != null && ruleTypeFor.examSubType != null && ruleTypeFor.ruleNames == null) {
//                        'examSubType'
                        if (ruleTypeFor.text == 'maxInAllStudents') {

                        } else if (ruleTypeFor.text == 'marksSecured') {

                        } else if (ruleTypeFor.text == 'totalMarks') {

                        } else if (ruleTypeFor.text == 'examSubTypeTotalAfterRule') {

                        }
                    } else if (ruleTypeFor.examType != null && ruleTypeFor.examSubType == null && ruleTypeFor.ruleNames == null) {
//                        'examType'
                        if (ruleTypeFor.text == 'maxInAllStudents') {

                        } else if (ruleTypeFor.text == 'marksSecured') {
                            def examTypeInst = ruleTypeFor.examType
                            def examinationList = Examination.findAllByExamTypesAndAcademicSession(examTypeInst, params.academicSession)
                            def totalMarks = 0
                            examinationList.each {
                                totalMarks += StudentMarks.findByExaminationAndStudent(it, stuInst)
                            }
                        } else if (ruleTypeFor.text == 'totalMarks') {

                        } else if (ruleTypeFor.text == 'examTypeTotalAfterRule') {

                        }
                    } else if (ruleTypeFor.examType == null && ruleTypeFor.examSubType == null && ruleTypeFor.ruleNames != null) {
//                       'Rule'
                        if (ruleTypeFor.text == 'TotalAfterRuleApply') {

                        } else if (ruleTypeFor.text == 'maxInAllStudents') {

                        }
                    } else if (ruleTypeFor.examType == null && ruleTypeFor.examSubType == null && ruleTypeFor.ruleNames == null) {
//                        'LTPC'
                        if (ruleTypeFor.text == 'lecture') {

                        } else if (ruleTypeFor.text == 'tutorial') {

                        } else if (ruleTypeFor.text == 'credit') {

                        } else if (ruleTypeFor.text == 'practical') {

                        }
                    }
                }
            }
        }
    }
    def getRuleXmlNodeNameSymbolAndFormula(ruleName) {
        def returnMap = [:], returnList = [], returnSymbolList = [], returnFormulaList = []
        def status = false
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
}