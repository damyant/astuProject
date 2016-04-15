package postexamination

import com.university.User
import examinationproject.Status
import examinationproject.Student
import examinationproject.StudentSubject
import examinationproject.Subject
import examinationproject.SubjectSession
import grails.transaction.Transactional

@Transactional
class MarksEnteringService {
    def springSecurityService
    def grailsApplication

    def serviceMethod() {

    }

//    def getGroupDetail(params) {
//        def programSessionObj = ProgramSession.findByProgramDetailId(ProgramDetail.get(params.program))
//        def programGroupIns = ProgramGroup.findAllByProgramSessionAndSemester(programSessionObj, Semester.get(params.semester))
//        return programGroupIns
//
//    }

//    def getCourseDetail(params) {
//        def subjectList
//        def programDetail = ProgramDetail.findById(params.program)
//        def programSession = ProgramSession.get(Integer.parseInt(params.session))
//        def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.semester))
//
//        if (params.groupType == "0") {
//            subjectList = CourseSubject.findAllByCourseDetailAndSemesterAndProgramSession(programDetail, semester, programSession).subjectSessionId.subjectId
//        } else {
//            def programGroupIns = ProgramGroup.findByProgramSessionAndSemester(programSession, semester)
//            subjectList = ProgramGroupDetail.findAllByProgramGroupId(programGroupIns).subjectSessionId.subjectId
//
//        }
//        return subjectList
//    }

    def saveMarks(params) {
        /* saving marks for particular student and examiantion*/
        def result = [:]
        def currentUser = springSecurityService.getCurrentUser()
        try {
            def statusInst=Status.findById(1)
            def studentMarksIns
            def subjInst = Subject.findBySubjectCode(params.subjectId)
            def stuInst = Student.findById(Long.parseLong(params.student))
            def examInst = Examination.findById(Long.parseLong(params.examination))
            studentMarksIns = StudentMarks.findBySubjectIdAndAcademicSessionAndStudentAndExaminationAndStatus(subjInst, params.academicSession, stuInst, examInst.id.toString(),statusInst)
             if (studentMarksIns) {
                if(params.marksValue=='A'||params.marksValue=='a'){
                    studentMarksIns.totalMarks = 'Absent'
                }
                else{
                    studentMarksIns.totalMarks = params.marksValue
                }
                studentMarksIns.user = User.findById(currentUser.id)
                 if (studentMarksIns.save(flush: true, failOnError: true)) {
                     result.status = true
                 }
            } else {
                studentMarksIns = new StudentMarks()
                if(params.marksValue=='A'||params.marksValue=='a'){
                    studentMarksIns.totalMarks = 'Absent'
                }
                else{
                    studentMarksIns.totalMarks = params.marksValue
                }
                studentMarksIns.examination = examInst.id
                studentMarksIns.academicSession = params.academicSession
                studentMarksIns.student = stuInst
                studentMarksIns.status=statusInst
                studentMarksIns.subjectId = subjInst
                studentMarksIns.user = User.findById(currentUser.id)
                if (studentMarksIns.save(flush: true, failOnError: true)) {
                    result.status = true
                }
            }
        }
        catch (Exception e) {
            println("Exception in saving marks" + e)
            result.status = false
        }
        return result
    }

    def getRollNumbers(params) {
        /*getting list of students enrolled in particular examination*/
        def returnMap=[:]
        def stuList1 = []
        def subjInst = Subject.findBySubjectCode(params.subjectId)
        def subjSessionInst = SubjectSession.findBySubjectId(subjInst)
        def examInst = Examination.findById(Long.parseLong(params.examination))
        def MarksEnteredList = StudentMarks.findAllByExamination(examInst.id)
        def studentMarksEnteredList = MarksEnteredList.student.unique()
        def statusInst=Status.findById(4)
        def unapproveStatusInst=Status.findById(1)
        def studentList = []
        if (params.entryType == 'add') {
            if (studentMarksEnteredList.size() > 0) {
                studentList = StudentSubject.findAllByStudentNotInListAndSubjectSessionAndStatusAndAcademicSession(studentMarksEnteredList, subjSessionInst,statusInst , params.academicSession).student.unique()
            } else {
                studentList = StudentSubject.findAllBySubjectSessionAndStatusAndAcademicSession(subjSessionInst, statusInst, params.academicSession).student.unique()
            }
        } else if (params.entryType == 'edit') {
            studentList = StudentMarks.findAllByExaminationAndIsRejected(examInst.id,false).student
        }
        else if (params.entryType == 'rejected') {
            studentList =StudentMarks.findAllByExaminationAndStatusAndIsRejected(examInst.id,unapproveStatusInst,true).student
        }
        if (studentList.size() > 0) {
            returnMap.studentList = studentList.sort { it.rollNo }
            returnMap.MarksEnteredList = MarksEnteredList.reverse()
            returnMap.studentRollNo = MarksEnteredList.reverse().student.rollNo

        }
        return returnMap
    }
}
