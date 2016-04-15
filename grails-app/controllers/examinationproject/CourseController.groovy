package examinationproject

import IST.Employee
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import postexamination.MarksType
import postexamination.SubjectMarksDetail
import universityproject.CourseDetailService

import java.io.File;

@Secured("ROLE_ADMIN")
class CourseController {
    def courseDetailService
    def programFeeService
    def marksEnteringService


    def createNewCourse() {
        /*For creating new program(program detail) or updating existing program*/
        boolean updateFlag = false
        def courseDetail = [:]
        def subObj = Subject.findAll()
        def subjectList = Subject.list()
        def programSessions = programFeeService.getProgramSessions(params)

        if (params.courseSessionId) {
            courseDetail = courseDetailService.getFullDetailOfCourse(params)
            updateFlag = true
        }

        [courseDetail: courseDetail as JSON, subjList: subObj as JSON, updateFlag: updateFlag, programSessions: programSessions,subjectList:subjectList]
    }

    def getCourseByCategory() {
        def subObj = Subject.findAllByProgramTypeId(ProgramType.findById(Long.parseLong(params.courseType)))
        render subObj as JSON
    }

    def viewCourses() {
        /*action for showing all details of Program*/
        def courseDetails = courseDetailService.getFullDetailOfCourse(params)
        [courseDetail: courseDetails as JSON]

    }

    def saveProgram() {
        /*saving and updating details of program*/
        def response = [:]
        def data = request.JSON
        def result=true
        def status
//        try{
        status = courseDetailService.saveCourseInfo(data)
//        }
//        catch (Exception e){
//            result=false
//        }
        if(status){
            response.response1 = status
        }
        else{
            response.response1 = 'Not Created'
        }

//        println(response)
        render response as JSON
      // redirect( controller: "course", action: "listOfCourses", params: [message:status])

    }

    def listOfCourses() {
        /*for showing list of programs with view option*/

        def c = ProgramSession.createCriteria()
        def programList = c.list() {
        }
        [courseSessionList: programList, courseInstanceTotal: ProgramSession.count()]

    }

    def updateCourses() {
        /*for showing list of programs with update and delete option*/
        redirect(action: "listOfCourses", params: ['type': "update"])
    }

    def deleteCourse() {
        /*To delete the program details*/
        def result = courseDetailService.deleteCourse(params)
        if (result) {
            flash.message = "Deleted Successfully."
        } else {
            flash.message = "Unable to Delete "
        }
        redirect(action: "listOfCourses", params: ['type': "update"])
    }

    def uploadSyllabus() {
//        println("params>>>>>>>>>>>>"+params.syllabusFile)
        try {
            if (data.uploadSyllabus) {
//                println("############>>" + data.uploadSyllabus);
                String ext = "";
                def fileToBeUploaded = request.getfile('uploadSyllabus')
//                println("############>>" + fileToBeUploaded);
                String fileName = fileToBeUploaded.originalFilename
                int i = fileName.lastIndexOf('.');
                if (i > 0) {
                    ext = fileName.substring(i + 1);
                }
                def servletContext = ServletContextHolder.servletContext
                def storagePath = servletContext.getRealPath('syllabus')
                def dir = new File(storagePath + System.getProperty("file.separator") + data.courseName + System.getProperty("file.separator"))
//                println("==============>>" + dir);
                if ((dir.exists())) {
                    File[] listOfFiles = dir.listFiles();
                    for (File file : listOfFiles) {
                        file.delete();
                    }
                } else {
                    dir.mkdirs()
                }

                fileToBeUploaded.transferTo(new File(dir, fileName))
//                println("File Saved");
                def fullPath = new File(storagePath + System.getProperty("file.separator") + data.courseName + System.getProperty("file.separator") + fileName)
                def newFullPath = new File(storagePath + System.getProperty("file.separator") + data.courseName + System.getProperty("file.separator") + data.courseName + '.' + ext)
                fullPath.renameTo(newFullPath)
            }
        }
        catch (Exception e) {
            println("There is some problem parsing Document file" + e);
        }

    }

    def checkCourseCode() {
        def status = [:]
        def courseCodeIns = ProgramDetail.findAllByCourseCode(params.courseCode)
        if (courseCodeIns) {
            status.courseCode = 'true'
        } else {
            status.courseCode = 'false'
        }
        render status as JSON

    }

    //ADDED BY DIGVIJAY ON 20 May 2014
   // Modified By Ajay
    def saveCourses() {
        /*Action for saving subject(course) details*/
      def isSaved =courseDetailService.saveCourseDetail(params)

        if(isSaved=="update"){
            flash.message = "Course Successfully Updated."
        }else{
            flash.message = "Course Successfully Created"
        }
        if(params.updateModes=='true'){
            redirect(controller: 'admin', action: 'listOfCourses')
        }
        else{
            redirect(controller: 'admin', action: 'addCourses')
        }

    }

    //ADDED BY RAJ
    def checkSubjectCode = {
        /*for validating subject code*/
        def status = [:]
        def courseCodeIns = Subject.findAllBySubjectCode(params.subjectCode)
        if (courseCodeIns) {
            status.subjectCode = 'true'
        } else {
            status.subjectCode = 'false'
        }
        render status as JSON
    }

    //ADDED BY RAJ
    def checkAliasCode = {
        def status = [:]
        def courseCodeIns = Subject.findAllByAliasCode(params.aliasCode)
        if (courseCodeIns) {
            status.aliasCode = 'true'
        } else {

            status.aliasCode = 'false'
        }
        render status as JSON
    }

    def getCourseOnProgramCode(){

        def subjectList=[]
       subjectList= courseDetailService.getCourseOnProgramCode(params)
      render subjectList as JSON

    }

    def getCourse(){
        def subjectList=[]
        subjectList= courseDetailService.getCourse()
        render subjectList as JSON


    }

    def saveProgramme(){
        def isSaved =courseDetailService.saveProgDetails(params)
        if(isSaved=="create"){
            flash.message = "Program Successfully Created."
        }else{
            flash.message = "Program Not Created"
        }

        redirect(controller: 'course', action: 'createNewCourse')
         }

  }
