package postexamination

import examinationproject.CourseSubject
import examinationproject.ProgramDetail
import examinationproject.ProgramSession
import examinationproject.ProgramType
import examinationproject.Semester
import examinationproject.Status
import examinationproject.Student
import examinationproject.SubjectSession
import grails.transaction.Transactional

import java.text.SimpleDateFormat

@Transactional
class ExamService {
    def springSecurityService
    def serviceMethod() {

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

        newSemesterList.each {
            def obj = CourseSubject.findAllByProgramSessionAndSemester(programSessionIns, it)

            if (obj) {
                dateList << obj
            }

        }
        dateList.each {
            subList << it.subjectSessionId
            subNameList << it.subjectSessionId.subjectId.subjectName
            semesterNumberList << it.semester

        }
        println("DATE-LIST"+dateList)
        subjectMap.allSubjects = subList
        subjectMap.semesterNoList = semesterNumberList
        subjectMap.dateList = totalDateList
        subjectMap.subNameList = subNameList
        return subjectMap
    }

    def saveExamDate(params) {
        SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
        def year = 0, month = null
        def subjectList = params.subjectIdList.split(",")
        def courseSubjectObj = []
        def count = 0
        def sessionObj = ProgramSession.findById(Long.parseLong(params.SessionList))
        subjectList.each {
            def subj = it.toString().split('-')[0]
            def sem = it.toString().split('-')[1]

                courseSubjectObj = CourseSubject.findBySubjectSessionIdAndProgramSessionAndSemester(SubjectSession.findById(Long.parseLong(subj)), sessionObj, Semester.findBySemesterNoAndProgramSession(sem, sessionObj))

             def examSch= new ExamSchedule()

            def dateList = []
            def timeList = []
            dateList.addAll(params.examinationDate)
            timeList.addAll(params.examinationTime)

            if (dateList[count]) {

                    examSch.programDetail = ProgramDetail.findById(Long.parseLong(params.programList))
                println("SubjectSess"+SubjectSession.findById(Long.parseLong(subj)))
                    examSch.subjectSession = SubjectSession.findById(Long.parseLong(subj))
                println("Semester"+Semester.findBySemesterNoAndProgramSession(sem, sessionObj))
                    examSch.semester = Semester.findBySemesterNoAndProgramSession(sem, sessionObj)
                println("ProgSess"+ProgramSession.findById(Long.parseLong(params.SessionList)))
                    examSch.programSession = ProgramSession.findById(Long.parseLong(params.SessionList))
                    examSch.year = params.examYear
                    examSch.examDate = f1.parse(dateList[count])
                    examSch.examTime = timeList[count]
                    examSch.save(failOnError: true, flush: true)
                    println("SAVED")


            } else {
                examSch.examDate = null
                examSch.save(failOnError: true, flush: true)

            }
            ++count
        }
    }

    def getStudents(params) {

        def progSessionInst = ProgramSession.findById(Integer.parseInt(params.programSession))
        def prog = ProgramDetail.findById(Long.parseLong(params.programList))
        def sem = Integer.parseInt(params.programTerm)
        def statusInst = Status.findById(4)
        def studentList= Student.findAllByProgramAndSemesterAndStatus(prog,sem,statusInst)
        return studentList
    }
}
