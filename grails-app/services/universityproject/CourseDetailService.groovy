package universityproject

import IST.ProgramBranch
import examinationproject.CourseMode
import examinationproject.ProgramDetail
import examinationproject.CourseSubject

//import examinationproject.ProgramGroup
//import examinationproject.ProgramGroupDetail
import examinationproject.ProgramType

import examinationproject.ProgramSession

import examinationproject.Semester
import examinationproject.Subject
import examinationproject.SubjectSession
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.w3c.dom.Document
import org.w3c.dom.Element
import postexamination.SubjectMarksDetail

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

@Transactional
class CourseDetailService {
    def marksEnteringService

    def serviceMethod() {

    }

    def saveCourseInfo(params) {
        /*saving and updating details of program*/

        def status = ""
        def term = 1
        def updateStatus = ""
        def existingCourseObj
        if (params.courseId) {
            existingCourseObj = ProgramDetail.findById(Integer.parseInt(params.courseId))
            updateStatus = "update"

        }
        def courseTypeInst = ProgramType.findById(Long.parseLong(params.programType))
        def branchInst = ProgramBranch.findById(params.programBranch)
        def session = ProgramSession.count()
        def sessionObj
        def partTimeStatus=false
        if(params.programType=='3'){
            if(params.partTimeStatus=='partTime'){
                partTimeStatus=true
            }
        }
        if (existingCourseObj) {
            if (params.programType == '4') {
                existingCourseObj.admissionTerm = 3
                term = 3
            } else {
                existingCourseObj.admissionTerm = 1
                term = 1
            }

            existingCourseObj.isPartTime=partTimeStatus
            if(partTimeStatus){
                existingCourseObj.courseName = courseTypeInst.type + " " + branchInst.name +" [Part Time] "
            }
            else{
                if(params.programType=='3'){
                    existingCourseObj.courseName = courseTypeInst.type + " " + branchInst.name +" [Full Time] "
                }
                else{
                    existingCourseObj.courseName = courseTypeInst.type + " " + branchInst.name
                }

            }
            existingCourseObj.courseCode = courseTypeInst.code + branchInst.code
            existingCourseObj.courseMode = CourseMode.findById(params.courseMode)
            existingCourseObj.programType = courseTypeInst
            existingCourseObj.programBranch = branchInst
            existingCourseObj.noOfTerms = Integer.parseInt(params.noOfTerms)
            existingCourseObj.noOfAcademicYears = Integer.parseInt(params.noOfAcademicYears)
            existingCourseObj.noOfPapers = Integer.parseInt(params.noOfPapers)
            existingCourseObj.totalMarks = Integer.parseInt(params.totalMarks)
            existingCourseObj.programBranch = ProgramBranch.findById(Long.parseLong(params.programBranch))
            existingCourseObj.totalCreditPoints = Integer.parseInt(params.totalCreditPoints)
            existingCourseObj.save(failOnError: true, flush: true)

            if (session > 0) {
                if (ProgramSession.findByProgramDetailIdAndSessionOfProgram(existingCourseObj, params.session)) {
                    sessionObj = ProgramSession.findByProgramDetailIdAndSessionOfProgram(existingCourseObj, params.session)
                } else {
                    sessionObj = new ProgramSession(sessionOfProgram: params.session, programDetailId: existingCourseObj).save(flush: true, failOnError: true)
                }
            } else {
                sessionObj = new ProgramSession(sessionOfProgram: params.session, programDetailId: existingCourseObj).save(flush: true, failOnError: true)
            }
            def semesterList = Semester.findAllByProgramSession(sessionObj)


            if (sessionObj) {

            }
            CourseSubject.removeAll(existingCourseObj, sessionObj)
//            semesterList.each{
//                it.delete()
//            }
            if (semesterList) {
                def maxTerm=Integer.parseInt(params.finalTerm)+((term - 1))
                if(semesterList.size()!=maxTerm){
                    def SemNoList=semesterList.semesterNo
                    for(def i=term;i<=maxTerm;i++){
                        def samAvailable=false
                        SemNoList.each {
                            if(it==i){
                                samAvailable=true
                            }
                        }
                        if(!samAvailable){
                            def semObj = new Semester()
                            semObj.semesterNo = i
                            semObj.programSession = sessionObj
                            semObj.save(failOnError: true)
                        }
                    }

                }
                status = UpdateCourseInformationExistingSam(params, sessionObj, existingCourseObj, term, semesterList)
            } else {
                status = saveAndUpdateCourseInformation(params, sessionObj, existingCourseObj, term)

            }
            if (status) {
                status = updateStatus
            }
            return status
        } else {
            if(ProgramDetail.findByProgramTypeAndCourseNameAndProgramBranchAndIsPartTime(courseTypeInst,courseTypeInst.type + " " + branchInst.name,branchInst,partTimeStatus)){
                return false
            }
            else{
                def courseObj = new ProgramDetail(params)
                if (params.programType == '4') {
                    courseObj.admissionTerm = 3
                    term = 3
                } else {
                    courseObj.admissionTerm = 1
                    term = 1
                }
                courseObj.isPartTime=partTimeStatus
                if(partTimeStatus){
                    courseObj.courseName = courseTypeInst.type + " " + branchInst.name +" [Part Time] "
                }
                else{
                    courseObj.courseName = courseTypeInst.type + " " + branchInst.name
                }

                courseObj.courseCode = courseTypeInst.code + branchInst.code
                courseObj.save(failOnError: true, flush: true)
                if (session > 0) {
                    if (ProgramSession.findBySessionOfProgramAndProgramDetailId(params.session, courseObj)) {
                        sessionObj = ProgramSession.findBySessionOfProgramAndProgramDetailId(params.session, courseObj)
                    } else {
                        sessionObj = new ProgramSession(sessionOfProgram: params.session, programDetailId: courseObj).save(flush: true, failOnError: true)
                    }
                } else {
                    sessionObj = new ProgramSession(sessionOfProgram: params.session, programDetailId: courseObj).save(flush: true, failOnError: true)
                }
                status = saveAndUpdateCourseInformation(params, sessionObj, courseObj, term)
                return status
            }

        }


    }

    def getAllCourses() {
        def courseObj = ProgramDetail.list(sort: 'courseCode')
    }

    def getFullDetailOfCourse(params) {
        /*Method for getting details of a program*/
        def courseDetail = [:], subMap = [:]
        def subList = []

        def programSession = ProgramSession.findById(Long.parseLong(params.courseSessionId))
        courseDetail.course = programSession.programDetailId
        courseDetail.programType = programSession.programDetailId.programType.type
        courseDetail.programBranch = programSession.programDetailId.programBranch.name
        courseDetail.courseMode = programSession.programDetailId.courseMode.modeName
        courseDetail.sessionOfCourse = programSession.sessionOfProgram

        programSession.semester.sort().each {
            def totalList = [], courseList = []
            courseList = CourseSubject.findAllByCourseDetailAndSemesterAndProgramSession(programSession.programDetailId, it, programSession).subjectSessionId
            courseList.each {
                def returnMap = [:]
                returnMap["id"] = it.id
                returnMap["subjectName"] = it.subjectId.subjectName
                returnMap["subjectCode"] = it.subjectId.subjectCode
                totalList << returnMap;
            }

            if (totalList.size())
                subMap[it.semesterNo] = totalList
            subList = subMap
        }
        courseDetail.semesterList = subList
        return courseDetail

    }

    def deleteCourse(params) {
        /*To delete the program details*/
        def status = false

        def existingProgramSession = ProgramSession.findById(Long.parseLong(params.courseSessionId))

        if (existingProgramSession) {
            def semesterList = Semester.findAllByProgramSession(existingProgramSession)

            CourseSubject.removeAll(existingProgramSession.programDetailId, existingProgramSession)
            semesterList.each {

                it.delete(failOnError: true, flush: true)
            }
            existingProgramSession.delete(flush: true)
            def courseSubject = ProgramSession.findByProgramDetailId(existingProgramSession.programDetailId)


            if (courseSubject == null) {
                def progDetails =ProgramDetail.findById(existingProgramSession.programDetailId.id)
                progDetails.delete(failOnError: true, flush: true)
                if (!ProgramDetail.exists(progDetails.id)) {
                    status = true
                }
            }
        }
        return status

    }

    def saveProgDetails(params) {
        println("================"+params)
        def isSaved = ""
        def subList = params.programSubject
        def prg = new ProgramDetail(params)
        if(params.programType=='4'){
            prg.admissionTerm=3
        }
        else{
            prg.admissionTerm=1
        }

        if (prg.save(flush: true, failOnError: true)) {
            subList.each {
                def currentSub = Subject.findById(it)

                CourseSubject.create prg, SubjectSession.findBySubjectId(currentSub)

            }
            isSaved = "create"

        } else {
            isSaved = "NotCreate"
        }
    }

    def saveCourseDetail(params) {
        def subjectIns
        def isSaved = ""

        try {
            if (params.subjectId) {
                subjectIns = Subject.get(params.subjectId)
                subjectIns.subjectCode = params.subjectCode
                subjectIns.subjectName = params.subjectName
                if (params.courseType == 'optional') {
                    subjectIns.isOptional = true
                } else {
                    subjectIns.isOptional = false
                }
                if (params.projectThesis == 'required') {
                    subjectIns.isProjectSubject = true
                } else {
                    subjectIns.isProjectSubject = false
                }
                subjectIns.lecture = Integer.parseInt(params.lecture)
                subjectIns.tutorial = Integer.parseInt(params.tutorial)
                subjectIns.practical = Integer.parseInt(params.practical)
                subjectIns.creditPoints = Integer.parseInt(params.creditPoints)
                subjectIns.contactHours = Integer.parseInt(params.contactHours)
                subjectIns.save(flush: true)
                isSaved = "update"
            } else {
                subjectIns = new Subject(params)
                if (params.courseType == 'optional') {
                    subjectIns.isOptional = true
                } else {
                    subjectIns.isOptional = false
                }
                subjectIns.save(failOnError: true, flush: true)
                isSaved = "create"
            }

            def session = SubjectSession.count()
            def sessionObj, optionalStatus,projectThesis
            if (session > 0) {
                if (params.courseType == 'optional') {
                    optionalStatus = true
                } else {
                    optionalStatus = false
                }
                if (params.projectThesis == 'required') {
                    projectThesis = true
                } else {
                    projectThesis = false
                }
                if (SubjectSession.findAllBySubjectIdAndSessionOfSubject(subjectIns, params.session)) {
                    sessionObj = SubjectSession.findBySubjectIdAndSessionOfSubject(subjectIns, params.session)
                    sessionObj.isOptional = optionalStatus
                    sessionObj.isProjectSubject=projectThesis
                } else {
                    sessionObj = new SubjectSession(sessionOfSubject: params.session, subjectId: subjectIns, isOptional: optionalStatus,isProjectSubject:projectThesis).save(failOnError: true)
                }
            } else {
                if (params.courseType == 'optional') {
                    optionalStatus = true
                } else {
                    optionalStatus = false
                }
                if (params.projectThesis == 'required') {
                    projectThesis = true
                } else {
                    projectThesis = false
                }
                sessionObj = new SubjectSession(isProjectSubject:projectThesis , sessionOfSubject: params.session, subjectId: subjectIns, isOptional: optionalStatus).save(failOnError: true)
            }

            if (sessionObj.subjectMarksDetail) {
                sessionObj.subjectMarksDetail.toList().each {
                    sessionObj.removeFromSubjectMarksDetail(it)
                    it.delete()
                }
            }
//            def subjectMarksDetailIns = new SubjectMarksDetail()
//            if (params.theoryTotal) {
//                if (params.theoryTotal != '0') {
//                    subjectMarksDetailIns.theory = true
//                } else {
//                    subjectMarksDetailIns.theory = false
//                }
//            } else {
//                subjectMarksDetailIns.theory = false
//            }
//            if (params.homeTotal) {
//                if (params.home != '0') {
//                    subjectMarksDetailIns.home = true
//                } else {
//                    subjectMarksDetailIns.home = false
//                }
//            } else {
//                subjectMarksDetailIns.home = false
//            }
//            if (params.practicalTotal) {
//                if (params.practical != '0') {
//                    subjectMarksDetailIns.practical = true
//                } else {
//                    subjectMarksDetailIns.practical = false
//                }
//            } else {
//                subjectMarksDetailIns.practical = false
//            }
//            if (params.projectTotal) {
//                if (params.project != '0') {
//                    subjectMarksDetailIns.project = true
//                } else {
//                    subjectMarksDetailIns.project = false
//                }
//            } else {
//                subjectMarksDetailIns.project = false
//            }
//            subjectMarksDetailIns.subjectSession = sessionObj
//            subjectMarksDetailIns.save(failOnError: true)
//                }
//                ++i
//            }


        }

        catch (Exception e) {
            println(" There is some problem in saving Course=" + e)
            isSaved = false
        }

        return isSaved
    }

    def getCourseOnProgramCode(params) {
        def resultList = [], courseNameList = [], returnList = [], courseCodeList = []
        def counter = 0
        def subList = Subject.createCriteria()
        def finalSubjectList = subList.list {

        }
        finalSubjectList.sort{it.subjectCode}
        finalSubjectList.each {
            resultList << SubjectSession.findBySubjectId(it)
            courseNameList << SubjectSession.findBySubjectId(it).subjectId.subjectName
            courseCodeList << SubjectSession.findBySubjectId(it).subjectId.subjectCode
        }

        resultList.each {
            def returnMap = [:]
            returnMap["id"] = it.id
            returnMap["subjectCode"] = courseCodeList[counter]
            returnMap["subjectName"] = courseNameList[counter]
            ++counter;
            returnList << returnMap
        }
        return returnList
    }

    def getCourse() {
        def resultList = [], courseNameList = [], returnList = [], courseCodeList = []
        def counter = 0

        def subList = Subject.findAll()
        subList.each {
            resultList << SubjectSession.findBySubjectId(it)
            courseNameList << SubjectSession.findBySubjectId(it).subjectId.subjectName
            courseCodeList << SubjectSession.findBySubjectId(it).subjectId.subjectCode
        }

        resultList.each {
            def returnMap = [:]
            returnMap["id"] = it.id
            returnMap["subjectCode"] = courseCodeList[counter]
            returnMap["subjectName"] = courseNameList[counter]
            ++counter;
            returnList << returnMap
        }

        return returnList

    }

//    def saveAndUpdateCourseInformation(params,courseObj){
//        println("parms=="+params)
////        println(sessionObj)
//        println(courseObj)
//        def status
//        def indexVal=0;
//        for (def i = 1; i <= Integer.parseInt(params.noOfTerms); i++) {
//            params.semesterList.each {
//                i
//                def subjectList = it."semester${i}".sort()
//
//                for(def j=0;j<subjectList.size();j++){
//                    def groupIns
//                    for(def k=0;k<subjectList[j].size();k++){
//                            println(Integer.parseInt(subjectList[j][k].toString()) +' ,.,.,.,.,.,.,.. '+courseObj.courseName)
//                            println(SubjectSession.findById(Integer.parseInt(subjectList[j][k].toString())))
//                            CourseSubject.create courseObj, SubjectSession.findById(Integer.parseInt(subjectList[j][k].toString()))
//                    }
//                }
//
//                status = 'Created'
//            }
//            ++indexVal;
//        }
//
//        return status
//
//    }
    def saveAndUpdateCourseInformation(params, sessionObj, courseObj, term) {
        def semObj, status
        def indexVal = 0;
        for (def i = term; i <= Integer.parseInt(params.finalTerm)+(term - 1); i++) {
            semObj = new Semester()
            semObj.semesterNo = i
            semObj.programSession = sessionObj
            semObj.save(failOnError: true)
            params.semesterList.each {
                i
                if(it."semester${i}"){
                    def subjectList = it."semester${i}".sort()

                    for (def j = 0; j < subjectList.size(); j++) {
                        def groupIns
                        for (def k = 0; k < subjectList[j].size(); k++) {

                            CourseSubject.create courseObj, SubjectSession.findById(Integer.parseInt(subjectList[j][k].toString())), semObj, sessionObj
                        }
                    }
                }

                status = 'Created'
            }

            ++indexVal;
        }

        return status

    }

    def UpdateCourseInformationExistingSam(params, sessionObj, courseObj, term, List semesterList) {
        def status
        def indexVal = 0;
        semesterList.each {
            def semObj = it
            def i = it.semesterNo
            params.semesterList.each {
                i
                if(it."semester${i}"){
                    def subjectList = it."semester${i}".sort()

                    for (def j = 0; j < subjectList.size(); j++) {
                        def groupIns
                        for (def k = 0; k < subjectList[j].size(); k++) {

                            CourseSubject.create courseObj, SubjectSession.findById(Integer.parseInt(subjectList[j][k].toString())), semObj, sessionObj
                        }
                    }
                }

                status = 'Created'
            }

            ++indexVal;
        }

        return status

    }

    def createXMLFile(params) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        def docDirectory = ServletContextHolder.servletContext.getRealPath(System.getProperty("file.separator"))
        def dir = docDirectory + System.getProperty("file.separator") + "subjectPassMarks" + System.getProperty("file.separator") + "subjectMarksRule.xml"
        Document document = documentBuilder.parse(dir);
        org.w3c.dom.Node employee = document.getElementsByTagName("rule").item(0);
        org.w3c.dom.NodeList nodes = employee.getChildNodes();
        Element subject = document.createElement("subject");
        subject.setAttribute('code', params.subjectCode)
        subject.setAttribute('credit', params.creditPoints)
        subject.setAttribute('subMinMarks', params.subjPassMarks)
        if (params.theoryTotal != '' || params.theoryTotal != '0') {
            Element theory = document.createElement("theory");
            theory.setAttribute('total', params.theoryTotal)
            theory.setAttribute('minMarks', params.theoryPass)
            subject.appendChild(theory)
        }

        if (params.practicalTotal != '' || params.practicalTotal != '0') {
            Element practical = document.createElement("practical");
            practical.setAttribute('total', params.practicalTotal)
            practical.setAttribute('minMarks', params.practicalPass)
            subject.appendChild(practical)
        }
        if (params.projectTotal != '' || params.projectTotal != '0') {
            Element project = document.createElement("project");
            project.setAttribute('total', params.projectTotal)
            project.setAttribute('minMarks', params.projectPass)
            subject.appendChild(project)
        }


        def updateStatus = false
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeName() != "#text" && updateStatus == false) {
                if ((params.subjectCode).equals(nodes.item(i).getAttribute("code"))) {
                    updateStatus = true
                    nodes.item(i).setAttribute('code', params.subjectCode)
                    nodes.item(i).setAttribute('credit', params.creditPoints)
                    nodes.item(i).setAttribute('subMinMarks', params.subjPassMarks)
                    for (int j = 0; j < nodes.item(i).getChildNodes().getLength(); j++) {
                        if ('theory'.equals(nodes.item(i).item(j).getNodeName())) {
                            nodes.item(i).item(j).setAttribute('total', params.theoryTotal)
                            nodes.item(i).item(j).setAttribute('minMarks', params.theoryPass)
                        } else if ('practical'.equals(nodes.item(i).item(j).getNodeName())) {
                            nodes.item(i).item(j).setAttribute('total', params.practicalTotal)
                            nodes.item(i).item(j).setAttribute('minMarks', params.practicalPass)
                        } else if ('project'.equals(nodes.item(i).item(j).getNodeName())) {
                            nodes.item(i).item(j).setAttribute('total', params.projectTotal)
                            nodes.item(i).item(j).setAttribute('minMarks', params.projectPass)
                        }
                    }
                }
            }

        }
        if (!updateStatus) {
            employee.appendChild(subject)
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);

        StreamResult streamResult = new StreamResult(new File(dir));
        transformer.transform(domSource, streamResult);
    }
}

