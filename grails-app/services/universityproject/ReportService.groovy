package universityproject

import IST.Department
import IST.Employee
import IST.EmployeeDesignation
import IST.EmployeeQualification
import IST.ProgramBranch
import com.university.User
import examinationproject.*
import grails.transaction.Transactional
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.write.WritableWorkbook
import postexamination.StudentEvaluatedMarks

import java.text.DateFormat
import java.text.SimpleDateFormat


@Transactional
class ReportService {
    def writeExcelForFeeService
    def writeExcelService
    def springSecurityService

    def getReportDataSession(params, excelPath) {
        def session = params.session
        def finalStudentMap = [:]
        def totalForSession = 0
        def studyCenterId = 0
        def currentUser = springSecurityService.getCurrentUser()
//         println("-------------------------------"+currentUser)
        def programList = ProgramDetail.list(sort: 'courseCode')
        if (currentUser.studyCentreId && params.inExcel) {
            def status
            File file = new File('' + excelPath);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
            int sheetNo = 0
//            studyCenterId = currentUser.studyCentreId
//            def studyCenter = StudyCenter.findById(studyCenterId)
            programList.each {
                def pId = it.id
                def stuObj = Student.createCriteria()
                def count = stuObj.list {
                    programDetail {
                        eq('id', pId)
                    }
                    studyCentre {
                        eq('id', studyCenter.id)
                    }
                    and {
                        eq('registrationYear', Integer.parseInt(params.session))
                    }
                    and {
                        eq('status', Status.findById(4))
                    }
                }
                if (count) {
                    status = writeExcelService.excelReport(params, count, it, sheetNo, workbook, studyCenter.name, session)
                    sheetNo = sheetNo + 1
                }
            }
            if (sheetNo > 0) {
                workbook.write();
                workbook.close();
            }
            return status
        } else if (currentUser.studyCentreId) {
            studyCenterId = currentUser.studyCentreId
//            def studyCenter = StudyCenter.findById(studyCenterId)
            programList.each {
                def pId = it.id
                def stuObj = Student.createCriteria()
                def count = stuObj.list {
                    programDetail {
                        eq('id', pId)
                    }
                    studyCentre {
                        eq('id', studyCenter.id)
                    }
                    and {
                        eq('registrationYear', Integer.parseInt(params.session))
                    }
                    and {
                        eq('status', Status.findById(4))
                    }
                    projections {
                        rowCount()
                    }
                }

                totalForSession += count
                finalStudentMap.put(it.courseName, count.getAt(0))
            }
            finalStudentMap.put("TOTAL STUDENTS", totalForSession)
            return finalStudentMap
        } else {
            programList.each {
                def pId = it.id
                def stuObj = Student.createCriteria()
                def count = stuObj.list {
                    programDetail {
                        eq('id', pId)
                    }
                    and {
                        eq('registrationYear', Integer.parseInt(params.session))
                    }
                    and {
                        eq('status', Status.findById(4))
                    }
                    projections {
                        rowCount()
                    }
                }

                totalForSession += count
                finalStudentMap.put(it.courseName, count.getAt(0))
            }
            finalStudentMap.put("TOTAL STUDENTS", totalForSession)
            return finalStudentMap
        }

    }

    def getReportDataSessions(startSession, endSession) {
        def categoryList = []
        categoryList
        def totalByCategory = []
        int j = 0
        for (int i = startSession; i <= endSession; i++) {
            categoryList.add(j, i)
            totalByCategory.add(j, 0)
            j++
        }
        def finalStudentMap = [:]
        def programList = ProgramDetail.list(sort: 'courseCode')
        programList.each {
            def sizeList = []
            def pId = it.id
            def stuObj = Student.createCriteria()
            def count = stuObj.list {
                programDetail {
                    eq('id', pId)
                }
                and {
                    between('registrationYear', startSession, endSession)
                }
                and {
                    eq('status', Status.findById(4))
                }
                projections {
                    groupProperty('registrationYear')
                    rowCount()
                }

            }
            for (int i = 0; i < categoryList.size(); i++) {
                if (count.size()) {
                    boolean flag = false
                    for (j = 0; j < count.size(); j++) {

                        if (count[j]?.getAt(0) == categoryList[i]) {
                            sizeList.add(i, count[j]?.getAt(1))
                            totalByCategory.set(i, totalByCategory[i] + count[j]?.getAt(1))
                            flag = true;
                            break;
                        }
                    }
                    if (flag == false) {
                        sizeList.add(i, 0)
//                        totalByCategory.add(i, totalByCategory.get(i)+0)
                    }
                } else {
                    sizeList.add(i, 0)
//                    totalByCategory.add(i, totalByCategory.get(i)+0)
                }
            }
            finalStudentMap.put(it.courseName, sizeList)
        }
        finalStudentMap.put('TOTAL STUDENTS', totalByCategory)
        return finalStudentMap
    }

    Boolean getReportDataCourse(params, excelPath) {
        def progInst = ProgramDetail.findById(Long.parseLong(params.programId))
        def progSession = ProgramSession.findByProgramDetailId(progInst)

        File file = new File(excelPath);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        int sheetNo = 0
        def status

        def subjectName = []
        def programInst = ProgramDetail.findById(Long.parseLong(params.programId))
        def programSession = ProgramSession.findByProgramDetailId(programInst)
        def semesterInst = Semester.findById(Long.parseLong(params.programTerm))
        def studentSubjectList = StudentSubject.findAllByProgramSessionAndAcademicSessionAndSemester(programSession, params.academicSession1, semesterInst, [sort: "subjectSession"])
        def subjectList = studentSubjectList.subjectSession.subjectId.unique()
        def studentList = studentSubjectList.student.unique()


        subjectList.each {
            subjectName << it.subjectName + " " + it.subjectCode
        }
        def finalList = []
        studentList.each {
            def tempList = []
            tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
            tempList << it.rollNo
            tempList << semesterInst.semesterNo
            def stuId = it
            subjectList.each {
                def stuSub = StudentSubject.findByStudentAndSubjectSession(stuId, SubjectSession.findAllBySubjectId(it))
                if (stuSub) {
                    tempList << "Y"

                } else {
                    tempList << "N"
                }
            }
            finalList << tempList
        }

        status = writeExcelService.excelReport(params, progInst.courseName, semesterInst.semesterNo, finalList, subjectList, progInst, sheetNo, workbook)
        sheetNo = sheetNo + 1


        workbook.write();
        workbook.close();
        return status

    }

    Map getReportStudentRegistration(params) {
        /*Report for Students' Registration details*/
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy")
        def subjectName = []
        def finalMap = [:]
        def programInst = ProgramDetail.findById(Long.parseLong(params.programId))
        def programSession = ProgramSession.findByProgramDetailId(programInst)
        def semesterInst = Semester.findById(Long.parseLong(params.programTerm))
        def studentSubjectList=StudentSubject.findAllByProgramSessionAndAcademicSessionAndSemester(programSession, params.academicSession1, semesterInst, [sort: "subjectSession"])
        def subjectList = studentSubjectList.subjectSession.subjectId.unique()
        def studentList = studentSubjectList.student.unique()


        subjectList.each{
            subjectName<<it.subjectName
        }
        def finalList=[]
        studentList.each {
            def tempList=[]
            tempList << it.rollNo
            tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName

            tempList << df.format(it.admissionDate)
            tempList <<semesterInst.semesterNo
            def stuId= it
            subjectList.each{
                def stuSub=StudentSubject.findByStudentAndSubjectSessionAndSemester(stuId,SubjectSession.findAllBySubjectId(it),semesterInst)
                if(stuSub){
                    tempList<< it.subjectName

                }
                else{
                    tempList<< " "
                }
            }
            def studentSem = SemesterFeeDetails.findAllByStudentAndProgramDetailAndSemester(it,it.program,semesterInst)
            tempList << studentSem.guReceiptNo
            tempList << studentSem.guReceiptAmount
//            tempList << studentSem.guReceiptDate
            def dList=[]
            studentSem.guReceiptDate.each{
                dList= df.format(it)
            }
            tempList << dList

            finalList << tempList

        }

        def headList=[]
        headList << "Roll No"
        headList << "Name"
        headList << "Admission Date"
        headList <<"Semester"
        subjectName.each{
            headList << it
        }

        headList << "Receipt No"
        headList << "Amount"
        headList << "Receipt Date"

        finalMap.headList = headList
        finalMap.finalList = finalList

        return finalMap

    }

//    Map getReportStudentRegistrationPreviousSem(params) {
//        DateFormat df = new SimpleDateFormat("dd-MM-yyyy")
//        def subjectName = []
//        def finalMap = [:]
//        int max=0
//        def programInst = ProgramDetail.findById(Long.parseLong(params.programId))
//        def programSession = ProgramSession.findByProgramDetailId(programInst)
//        def semesterInst = Semester.findById(Long.parseLong(params.programTerm))
//        def studentSubjectList=StudentSubject.findAllByProgramSessionAndAcademicSessionAndSemester(programSession, params.academicSession1, semesterInst, [sort: "subjectSession"])
//        def subjectList = studentSubjectList.subjectSession.subjectId.unique()
//        def studentList = studentSubjectList.student.unique()
//
//
//        subjectList.each{
//            subjectName<<it.subjectName
//        }
//        def finalList=[]
//        studentList.each {
//            def tempList=[]
//            tempList << it.rollNo
//            tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
//
//            tempList << df.format(it.admissionDate)
//            tempList <<semesterInst.semesterNo
//            def stuId= it.id
//            subjectList.each{
//                def stuSub=StudentSubject.findByStudentAndSubjectSession(Student.findById(stuId),SubjectSession.findAllBySubjectId(Subject.findById(it.id)))
//                if(stuSub){
//                    tempList<< it.subjectName
//
//                }
//                else{
//                    tempList<< " "
//                }
//            }
//            def prog= ProgramSession.findByProgramDetailId(it.program)
//            def tempSem = Semester.findByProgramSessionAndSemesterNo(prog,it.semester-1)
//            def tSem = Semester.findByProgramSessionAndSemesterNo(prog,it.semester)
//
//            def studentSubjects = StudentSubject.findAllByProgramSessionAndStudentAndSemester(prog,it,tempSem, [sort: "subjectSession"]).subjectSession.subjectId.unique()
//            def currentSubjects = StudentSubject.findAllByProgramSessionAndStudentAndSemester(prog,it,tSem, [sort: "subjectSession"]).subjectSession.subjectId.unique()
//
//            def studentSem = SemesterFeeDetails.findAllByStudentAndProgramDetailAndSemester(it,it.program,semesterInst)
//            tempList << studentSem.guReceiptNo
//            tempList << studentSem.guReceiptAmount
//            currentSubjects.retainAll(studentSubjects)
//            int temp = currentSubjects.size
//            if(temp > max){
//                max = temp
//            }
//            def dList=[]
//            studentSem.guReceiptDate.each{
//                dList << df.format(it)
//            }
//            tempList << dList
//            currentSubjects.each{
//                tempList << it
//            }
//
//            finalList << tempList
//
//        }
//
//        def headList=[]
//        headList << "Roll No"
//        headList << "Name"
//        headList << "Admission Date"
//        headList <<"Semester"
//        subjectName.each{
//            headList << it
//        }
//
//        headList << "Receipt No"
//        headList << "Amount"
//        headList << "Receipt Date"
//        for(int counter=1;counter <= max;counter++){
//            headList << "Back Paper"+counter
//        }
//
//
//        finalMap.headList = headList
//        finalMap.finalList = finalList
//
//        return finalMap
//
//
////        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy")
////
////        def studentList= Student.findAllByProgram(ProgramDetail.findById(params.approvedProgram))
////        def finalMap=[:]
////        def finalList = []
////        studentList.each {
////            def tempList = []
////            tempList << it.rollNo
////            tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
////            tempList << it.program.courseName
////
////            tempList << it.semester
////
////            def prog= ProgramSession.findByProgramDetailId(it.program)
////            def tempSem = Semester.findByProgramSessionAndSemesterNo(prog,it.semester-1)
////            def tSem = Semester.findByProgramSessionAndSemesterNo(prog,it.semester)
////
////            def studentSubjects = StudentSubject.findAllByProgramSessionAndStudentAndSemester(prog,it,tempSem, [sort: "subjectSession"]).subjectSession.subjectId.unique()
////            def currentSubjects = StudentSubject.findAllByProgramSessionAndStudentAndSemester(prog,it,tSem, [sort: "subjectSession"]).subjectSession.subjectId.unique()
////            studentSubjects.each{
////                tempList << it.subjectName
////            }
////
////            def studentSem = SemesterFeeDetails.findAllByStudentAndProgramDetailAndSemester(it,it.program,tempSem)
////            tempList << studentSem.guReceiptNo
////            tempList << studentSem.guReceiptAmount
////            tempList << studentSem.guReceiptDate
////            currentSubjects.retainAll(studentSubjects)
////            currentSubjects.each{
////                tempList << it
////            }
////
////            finalList << tempList
////        }
////        def headList=[]
////        headList <<"RollNo"
////        headList <<"Name"
////        headList <<"Program"
////        headList <<"Semester"
////        headList <<"Subject1"
////        headList <<"ReceiptNo"
////        headList <<"Amount"
////        headList <<"Date"
////        headList <<"Back Papers"
////
////        finalMap.headList =headList
////        finalMap.finalList = finalList
////        return finalMap
//
//    }

    Map getReportStudentRegistrationPreviousSem(params) {
        /*Report for Students' previous semester registration details along with back details*/
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy")
        def subjectName = []
        def finalMap = [:]
        def programInst = ProgramDetail.findById(Long.parseLong(params.programId))
        def programSession = ProgramSession.findByProgramDetailId(programInst)
        def semesterInst = Semester.findById(Long.parseLong(params.programTerm))

        if (semesterInst.semesterNo > 1) {


        def prevSemesterInst = Semester.findByProgramSessionAndSemesterNo(programSession, (semesterInst.semesterNo - 1))

        def studentSubjectList = StudentSubject.findAllByProgramSessionAndAcademicSessionAndSemester(programSession, params.academicSession1, semesterInst, [sort: "subjectSession"])

        def studentSubjectPrevSemList = StudentSubject.findAllByProgramSessionAndAcademicSessionAndSemester(programSession, params.academicSession1, prevSemesterInst, [sort: "subjectSession"])
        def subjectList = studentSubjectList.subjectSession.subjectId.unique()
        def subjectPrevSemList = studentSubjectPrevSemList.subjectSession.subjectId.unique()
        def studentList = studentSubjectPrevSemList.student.unique()
        def actualSubjectList = CourseSubject.findAllByProgramSessionAndSemester(programSession, semesterInst).subjectSessionId.subjectId
        def actualPrevSemSubjectList = CourseSubject.findAllByProgramSessionAndSemester(programSession, prevSemesterInst).subjectSessionId.subjectId
        def backHeaderList = studentSubjectList.subjectSession.subjectId.unique()
        def backPrevSemHeaderList = studentSubjectPrevSemList.subjectSession.subjectId.unique()
        backHeaderList.removeAll(actualSubjectList)
        backPrevSemHeaderList.removeAll(actualPrevSemSubjectList)
        subjectPrevSemList.each {
            subjectName << it.subjectName

        }
        def finalList = []

        studentList.each {
            def tempList = []
            def currentSubjects = []
            def previousSubjects = []

            def prog = ProgramSession.findByProgramDetailId(it.program)
            def tempSem = Semester.findByProgramSessionAndSemesterNo(prog, it.semester - 1)
            def tSem = Semester.findByProgramSessionAndSemesterNo(prog, it.semester)

//            def studentSubjects = StudentSubject.findAllByProgramSessionAndStudentAndSemester(prog,it,tempSem, [sort: "subjectSession"]).subjectSession.subjectId.unique()

            currentSubjects = StudentSubject.findAllByProgramSessionAndStudentAndSemester(prog, it, tSem, [sort: "subjectSession"]).subjectSession.subjectId.unique()
            previousSubjects = StudentSubject.findAllByProgramSessionAndStudentAndSemester(prog, it, tempSem, [sort: "subjectSession"]).subjectSession.subjectId.unique()


            tempList << it.rollNo
            tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName

            tempList << df.format(it.admissionDate)
            tempList << semesterInst.semesterNo - 1
            def stuId = it.id
            subjectPrevSemList.each {
                def stuSub = StudentSubject.findByStudentAndSubjectSessionAndSemester(Student.findById(stuId), SubjectSession.findBySubjectId(Subject.findById(it.id)), prevSemesterInst)
                if (stuSub) {
                    tempList << it.subjectName

                } else {
                    tempList << " "
                }
            }

            def allCurrentSub = StudentSubject.findAllByProgramSessionAndStudentAndSemester(prog, it, tSem, [sort: "subjectSession"]).subjectSession.subjectId.unique()
            def studentSem = SemesterFeeDetails.findAllByStudentAndProgramDetailAndSemester(it, it.program, prevSemesterInst)
            tempList << studentSem.guReceiptNo
            tempList << studentSem.guReceiptAmount
            currentSubjects.removeAll(actualSubjectList)
            println(allCurrentSub)
            def dList = []
            studentSem.guReceiptDate.each {
                dList = df.format(it)
            }
            tempList << dList
            backHeaderList.each {
                def stuSub = StudentSubject.findByStudentAndSubjectSessionAndSemester(Student.findById(stuId), SubjectSession.findAllBySubjectId(Subject.findById(it.id)), semesterInst)
                if (stuSub) {
                    tempList << it.subjectName
                } else {
                    tempList << " "
                }
            }
            backPrevSemHeaderList.each {
                def stuSub = StudentSubject.findByStudentAndSubjectSessionAndSemester(Student.findById(stuId), SubjectSession.findAllBySubjectId(Subject.findById(it.id)), prevSemesterInst)
                if (stuSub) {
                    tempList << it.subjectName
                } else {
                    tempList << " "
                }
            }
            finalList << tempList
        }

        def headList = []
        headList << "Roll No"
        headList << "Name"
        headList << "Admission Date"
        headList << "Semester"
        subjectPrevSemList.each {
            headList << it.subjectName
        }

        headList << "Receipt No"
        headList << "Amount"
        headList << "Receipt Date"
        backHeaderList.each {
            headList << it.subjectName + " (Current Back Paper)"
        }
        backPrevSemHeaderList.each {
            headList << it.subjectName + " (Previous Back Paper)"
        }
        finalMap.headList = headList
        finalMap.finalList = finalList

        return finalMap
    }
        else{

            def headList=[]
            def finalList=[]
            finalMap.headList = headList
            finalMap.finalList = finalList

            return finalMap
        }



    }


    Boolean getReportGradeDetails(params, excelPath) {
        File file = new File(excelPath);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        int sheetNo = 0
        def status
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy")

        def studentList= StudentEvaluatedMarks.findAllByAcademicSessionAndUser(params.academicSession,User.findAllByUsername(params.courseInstructor)).student.unique()

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

        status = writeExcelService.excelReportForGradeDetails(params,finalList, sheetNo, workbook)
        sheetNo = sheetNo + 1


        workbook.write();
        workbook.close();
        return status

    }

    Boolean getReportStudentIntake(excelPath) {
        File file = new File(excelPath);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        int sheetNo = 0
        def status
        def progList = ProgramDetail.findAll()
        Date dt = new Date()
        def currentYear=Student.createCriteria().get {
            projections {
                max "registrationYear"
            }
        }
//       def currentYear= dt.calendarDate.year
       def nextYear = currentYear+1

      for(def i=0;i<3;i++)
      {
        def finalList = []
        progList.each {
            def tempList = []
            if (it.programType.type == 'B TECH' || it.programType.type == 'BS' || it.programType.type == 'B TECH (L)') {
                tempList << 'UG'
            } else {
                tempList << 'PG'
            }
            tempList << it.courseName
            tempList << " "

            tempList << Student.countByProgramAndGenderAndCategoryAndRegistrationYearAndStatus(it, "Male", "General", currentYear, Status.findAllById(4))
            tempList << Student.countByProgramAndGenderAndCategoryAndRegistrationYearAndStatus(it, "Female", "General", currentYear, Status.findAllById(4))
            def obcMale = Student.countByProgramAndGenderAndCategoryAndRegistrationYearAndStatus(it, "Male", "OBC", currentYear, Status.findAllById(4))
            def mobcMale = Student.countByProgramAndGenderAndCategoryAndRegistrationYearAndStatus(it, "Male", "MOBC", currentYear, Status.findAllById(4))
            tempList << obcMale + mobcMale
            def obcFemale = Student.countByProgramAndGenderAndCategoryAndRegistrationYearAndStatus(it, "Female", "OBC", currentYear, Status.findAllById(4))
            def mobcFemale = Student.countByProgramAndGenderAndCategoryAndRegistrationYearAndStatus(it, "Female", "MOBC", currentYear, Status.findAllById(4))
            tempList << obcFemale + mobcFemale
            tempList << Student.countByProgramAndGenderAndCategoryAndRegistrationYearAndStatus(it, "Male", "SC", currentYear, Status.findAllById(4))
            tempList << Student.countByProgramAndGenderAndCategoryAndRegistrationYearAndStatus(it, "Female", "SC", currentYear, Status.findAllById(4))
            tempList << Student.countByProgramAndGenderAndCategoryAndRegistrationYearAndStatus(it, "Male", "S.T", currentYear, Status.findAllById(4))
            tempList << Student.countByProgramAndGenderAndCategoryAndRegistrationYearAndStatus(it, "Female", "S.T", currentYear, Status.findAllById(4))
            tempList << Student.countByProgramAndGenderAndIsPHAndRegistrationYearAndStatus(it, "Male", true, currentYear, Status.findAllById(4))
            tempList << Student.countByProgramAndGenderAndIsPHAndRegistrationYearAndStatus(it, "Female", true, currentYear, Status.findAllById(4))
            tempList << Student.countByProgramAndGenderAndCategoryAndRegistrationYearAndStatus(it, "Male", "Minority", currentYear, Status.findAllById(4))
            tempList << Student.countByProgramAndGenderAndCategoryAndRegistrationYearAndStatus(it, "Female", "Minority", currentYear, Status.findAllById(4))
            tempList << " "
            tempList << " "
            tempList << Student.countByProgramAndRegistrationYearAndStatus(it, currentYear, Status.findAllById(4))
            finalList << tempList
        }




        status = writeExcelService.excelReportForIntake(currentYear,nextYear,finalList, sheetNo, workbook)
        sheetNo = sheetNo + 1
        currentYear = currentYear-1
        nextYear = nextYear-1
      }
        workbook.write();
        workbook.close();
        return status

    }

//    Map getReportStudentInfo(params) {
//
//        def status
//        def studentList=[]
//        def stuList=[]
//
//        def progT,progD,progB
//          if(params.programType!="All"){
//            progT= ProgramType.findById(Integer.parseInt(params.programType))
//          }
//          else{
//            progT = "All"
//          }
//
//          if(params.programDepartment!="All"){
//            progD = Department.findById(Integer.parseInt(params.programDepartment))
//           }
//          else{
//            progD = "All"
//           }
//            if (progD !="All"){
//                progB= ProgramBranch.findAllByDepartment(progD)
//                progB.each{
//                   println("#########"+ it.name)
//
//                }
//            }
//
//
//
//
//
//       if(progT=='All'&& progD=='All' && params.programSem=='All' && params.gender=='Both'){
//         studentList=Student.findAllBySemesterGreaterThan(0)
//        }
//        else if(progT !='All'&& progD!='All' && params.programSem!='All' && params.gender!='Both'){
//           progB.each{
//               stuList=Student.findAllByProgramTypeAndProgramBranchAndSemesterAndGender(progT,ProgramBranch.findById(it.id),Integer.parseInt(params.programSem),params.gender)
//               studentList =studentList+ stuList
//           }
//
//        }
//        else if(progT=='All'&& progD!='All' && params.programSem!='All' && params.gender!='Both'){
//           progB.each{
//              stuList=Student.findAllByProgramBranchAndSemesterAndGender(ProgramBranch.findById(it.id),Integer.parseInt(params.programSem),params.gender)
//               studentList =studentList+ stuList
//           }
//
//        }
//        else if(progT!='All'&& progD=='All' && params.programSem!='All' && params.gender!='Both'){
//            studentList=Student.findAllByProgramTypeAndSemesterAndGender(progT,Integer.parseInt(params.programSem),params.gender)
//        }
//        else if(progT!='All'&& progD!='All' && params.programSem=='All' && params.gender!='Both') {
//           progB.each {
//               stuList = Student.findAllByProgramTypeAndProgramBranchAndGenderAndSemesterGreaterThan(progT, ProgramBranch.findById(it.id), params.gender, 0)
//               studentList =studentList+ stuList
//           }
//       }
//        else if(progT!='All'&& progD!='All' && params.programSem!='All' && params.gender=='Both') {
//           progB.each {
//               stuList = Student.findAllByProgramTypeAndProgramBranchAndSemester(progT,ProgramBranch.findById(it.id), Integer.parseInt(params.programSem))
//               studentList =studentList+ stuList
//           }
//       }
//        else if(progT=='All'&& progD=='All' && params.programSem!='All' && params.gender!='Both'){
//            studentList=Student.findAllBySemesterAndGender(Integer.parseInt(params.programSem),params.gender)
//                    }
//        else if(progT=='All'&& progD!='All' && params.programSem=='All' && params.gender!='Both') {
//           progB.each {
//               stuList = Student.findAllByProgramBranchAndGenderAndSemesterGreaterThan(ProgramBranch.findById(it.id), params.gender, 0)
//               studentList =studentList+ stuList
//           }
//       }
//        else if(progT=='All'&& progD!='All' && params.programSem!='All' && params.gender=='Both') {
//           progB.each {
//               stuList = Student.findAllByProgramBranchAndSemester(ProgramBranch.findById(it.id), Integer.parseInt(params.programSem))
//               studentList =studentList+ stuList
//           }
//       }
//        else if(progT!='All'&& progD=='All' && params.programSem=='All' && params.gender!='Both'){
//            studentList=Student.findAllByProgramTypeAndGenderAndSemesterGreaterThan(progT,params.gender,0)
//        }
//        else if(progT!='All'&& progD=='All' && params.programSem!='All' && params.gender=='Both'){
//            studentList=Student.findAllByProgramTypeAndSemester(progT,Integer.parseInt(params.programSem))
//        }
//        else if(progT!='All'&& progD!='All' && params.programSem=='All' && params.gender=='Both') {
//           progB.each {
//               stuList = Student.findAllByProgramTypeAndProgramBranchAndSemesterGreaterThan(progT,ProgramBranch.findById(it.id), 0)
//               studentList =studentList+ stuList
//           }
//       }
//        else if(progT=='All'&& progD=='All' && params.programSem=='All' && params.gender!='Both'){
//            studentList=Student.findAllByGenderAndSemesterGreaterThan(params.gender,0)
//        }
//        else if(progT=='All'&& progD=='All' && params.programSem!='All' && params.gender=='Both'){
//            studentList=Student.findAllBySemester(Integer.parseInt(params.programSem))
//        }
//        else if(progT=='All'&& progD!='All' && params.programSem=='All' && params.gender=='Both'){
//           progB.each {
//               stuList = Student.findAllByProgramBranchAndSemesterGreaterThan(ProgramBranch.findById(it.id), 0)
//               studentList =studentList+ stuList
//           }
//        }
//        else if(progT!='All'&& progD=='All' && params.programSem=='All' && params.gender=='Both'){
//            studentList=Student.findAllByProgramTypeAndSemesterGreaterThan(progT,0)
//        }
//
//        def finalList=[]
//        studentList.each{
//
//                def tempList=[]
//                 tempList << it.rollNo
//                 tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
//                 tempList << it.programType.type
//                 tempList << it.programBranch.name
//                 tempList << it.semester
//                 tempList << it.gender
//
//                 finalList << tempList
//            }
//        def headList=[]
//            headList << "Roll No"
//            headList << "Name"
//            headList << "Program"
//            headList << "Branch"
//            headList << "Semester"
//            headList << "Gender"
//
//            def myMap=[:]
//            myMap.headList= headList
//            myMap.finalList= finalList
//
//        return myMap
//
//    }

    Map getReportStudentInfo(params) {
        /*Report for Student Information*/

        def prog=[]
        def dep =[]
        def sems =[]
        if(params.multiProgram.getClass() == String){
            prog << params.multiProgram
        }
        else{
            prog = params.multiProgram
        }
        if(params.multiDepartment.getClass() == String){
            dep << params.multiDepartment
        }
        else{
            dep = params.multiDepartment
        }
        if(params.multiSem.getClass() == String){
            sems << params.multiSem
        }
        else{
            sems = params.multiSem
        }

        def studentList=[]
        def stuList=[]

        for(int i=0; i< prog.size() ;i++){
            for( int j=0; j < dep.size();j++){
                def pBranch = ProgramBranch.findAllByDepartment(Department.findById(Long.parseLong(dep[j])))
                pBranch.each{

                    for( int k=0;k < sems.size();k++){
                        if(params.mGender == "Both"){
                            stuList=Student.findAllByProgramTypeAndProgramBranchAndSemester(ProgramType.findById(Long.parseLong(prog[i])),ProgramBranch.findById(it.id),Integer.parseInt(sems[k]))

                        }
                        else{
                            stuList=Student.findAllByProgramTypeAndProgramBranchAndSemesterAndGender(ProgramType.findById(Long.parseLong(prog[i])),ProgramBranch.findById(it.id),Integer.parseInt(sems[k]),params.gender)

                        }
                        stuList.each{
                            studentList << it
                        }
                    }

                }
            }
        }


        def finalList=[]
        studentList.each{

            def tempList=[]
            tempList << it.rollNo
            tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
            tempList << it.programType.type
            tempList << it.programBranch.name
            tempList << it.semester
            tempList << it.gender

            finalList << tempList
        }
        def headList=[]
        headList << "Roll No"
        headList << "Name"
        headList << "Program"
        headList << "Branch"
        headList << "Semester"
        headList << "Gender"

        def myMap=[:]
        myMap.headList= headList
        myMap.finalList= finalList

        return myMap

    }

    Map getReportStudentInfoByCourse(approvedProgram) {
        /*Report for Course Wise Student Information*/

            def stuList=[]
            def finalMap=[:]
            def finalList=[]
            def branch = ProgramDetail.findById(Long.parseLong(approvedProgram)).programBranch.name
            def course = ProgramDetail.findById(Long.parseLong(approvedProgram)).courseName


            stuList= Student.findAllByProgramAndSemesterGreaterThan(ProgramDetail.findById(Long.parseLong(approvedProgram)),0)
            stuList.each{
                def tempList=[]
                tempList << it.rollNo
                tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
                tempList << branch
                finalList << tempList
            }

            def headList=[]
            headList << "Roll No"
            headList << "Name"
            headList << "Branch"

        finalMap.headList= headList
        finalMap.finalList = finalList
        return finalMap
//            status = writeExcelService.excelReportForStudentInfo(course,finalList, sheetNo, workbook)
//            sheetNo = sheetNo + 1
//
//
//        workbook.write();
//        workbook.close();
//        return status

    }

    Map getReportEmployeeInfo(params) {
        /*Report for Employee Details*/
        def empList=[]

        def finalList=[]


          if(params.employeeName!=''&& params.programDepartment=="All" ){
            empList = Employee.findAllByFirstNameLike("%"+params.employeeName+"%")

          }
          else if(params.employeeName!=''&& params.programDepartment!="All"){
              empList = Employee.findAllByFirstNameLikeAndDepartment("%"+params.employeeName+"%",Department.findById(Long.parseLong( params.programDepartment)))

          }
          else if(params.gender=="Both"&& params.programDepartment=="All" && params.employeeName==''){
            empList = Employee.findAll()

          }
          else if(params.gender!="Both"&& params.programDepartment=="All" && params.employeeName==''){
              empList = Employee.findAllByGender(params.gender)

          }
          else if(params.gender=="Both"&& params.programDepartment!="All" && params.employeeName==''){
              empList = Employee.findAllByDepartment(Department.findById(Long.parseLong(params.programDepartment)))

          }
          else if(params.gender!="Both"&& params.programDepartment!="All" && params.employeeName==''){
             empList = Employee.findAllByGenderAndDepartment(params.gender,Department.findById(Long.parseLong(params.programDepartment)))

          }
//        if (params.employeeName=='' && params.programDepartment=="All" && params.gender=="Both"){
//        empList= Employee.findAll()
//        }
//        else if(params.employeeName!='' && params.programDepartment!="All" && params.gender!="Both"){
//        empList= Employee.findAllByFirstNameLikeAndGenderAndDepartment(params.employeeName,params.gender,Department.findById(Long.parseLong( params.programDepartment)))
//        }
//        else if(params.employeeName=='' && params.programDepartment!="All" && params.gender!="Both"){
//        empList = Employee.findAllByGenderAndDepartment(params.gender,Department.findById(Long.parseLong( params.programDepartment)))
//        }
//        else if(params.employeeName!='' && params.programDepartment=="All" && params.gender!="Both"){
//        empList= Employee.findAllByFirstNameLikeAndGender(params.employeeName,params.gender)
//        }
//        else if(params.employeeName!='' && params.programDepartment!="All" && params.gender=="Both"){
//        empList= Employee.findAllByFirstNameLikeAndDepartment(params.employeeName,Department.findById(Long.parseLong( params.programDepartment)))
//        }
//        else if(params.employeeName=='' && params.programDepartment=="All" && params.gender!="Both"){
//        empList= Employee.findAllByGender(params.gender)
//        }
//        else if(params.employeeName=='' && params.programDepartment!="All" && params.gender=="Both"){
//        empList= Employee.findAllByDepartment(Department.findById(Long.parseLong(params.programDepartment)))
//        }
//        else if(params.employeeName!='' && params.programDepartment=="All" && params.gender=="Both"){
//        empList= Employee.findAllByFirstNameLike(params.employeeName)
//        }


        empList.each{
            def tempList=[]
            tempList << it.employeeCode
            tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
            tempList << it.gender
            tempList << it.fatherName
            tempList << it.motherName
            tempList << it.firstEmail
            tempList << it.firstMobileNo
            tempList << it.department.name
            tempList << it.currentAddress+""+it.currentAddressCity+" "+it.currentAddressDistrict+" "+it.currentStateAddress

            finalList << tempList
        }
        def headList=[]
        headList << "Code"
        headList << "Name"
        headList << "Gender"
        headList << "FatherName"
        headList << "Mother Name"
        headList << "E-mail"
        headList << "Mobile No"
        headList << "Department"
        headList << "Address"

        def myMap=[:]
        myMap.headList= headList
        myMap.finalList= finalList

        return myMap
//        status = writeExcelService.excelReportForEmployeeInfo(finalList, sheetNo, workbook)
//        sheetNo = sheetNo + 1
//
//
//        workbook.write();
//        workbook.close();
//        return status

    }

    Map getReportPassoutStudents(params) {
        /*Report for passout Student*/
        def stuList=[]
        def passedStudents=[]
        def finalList=[]

        def prog=[]
        def dep =[]

        if(params.multiProgram.getClass() == String){
            prog << params.multiProgram
        }
        else{
            prog = params.multiProgram
        }
        if(params.multiDepartment.getClass() == String){
            dep << params.multiDepartment
        }
        else{
            dep = params.multiDepartment
        }


        def studentList=[]

        for(int i=0; i< prog.size() ;i++){
            for( int j=0; j < dep.size();j++){
                def pBranch = ProgramBranch.findAllByDepartment(Department.findById(Long.parseLong(dep[j])))
                pBranch.each{
                       if(params.mGender == "Both"){
                            stuList=Student.findAllByProgramTypeAndProgramBranch(ProgramType.findById(Long.parseLong(prog[i])),ProgramBranch.findById(it.id))

                        }
                        else{
                            stuList=Student.findAllByProgramTypeAndProgramBranchAndGender(ProgramType.findById(Long.parseLong(prog[i])),ProgramBranch.findById(it.id),params.gender)
                        }
                        stuList.each{
                            studentList << it
                        }
                }
            }
        }

        def maxProgramSession = SemesterFeeDetails.findAll().academicSession.max()
        int max = Integer.parseInt(maxProgramSession.substring(0,4))
        studentList.each{

            int noTerm = it.program.noOfTerms
            int maxTerm = it.program.finalTerm
            def progS= ProgramSession.findByProgramDetailId(it.program)
            def stuId = it
            def tSem = Semester.findByProgramSessionAndSemesterNo(progS,it.semester)

              def studentProgramSession=SemesterFeeDetails.findAllByStudentAndSemester(it,tSem).academicSession.unique()
                if(studentProgramSession){
                    studentProgramSession.each{

                        int currSession = Integer.parseInt(it.substring(0,4))

                        if(stuId.semester >= noTerm && stuId.semester <= maxTerm && currSession < max){
                            passedStudents << stuId
                        }
                    }
                }
        }

        passedStudents.each{
            def tempList = []
            tempList << it.rollNo
            tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
            tempList << it.program.courseName
            tempList << it.registrationYear
            finalList << tempList
        }
        def headList=[]
        headList << "Roll No"
        headList << "Name"
        headList << "Program"
        headList << "Registratiion Year"

        def myMap=[:]
        myMap.headList= headList
        myMap.finalList= finalList
        return myMap


    }

    Map getReportMinorityStudent(minority,admissionYear) {
        /*Report for Minority Students' information*/

        def stuList=[]
        stuList = Student.findAllByCategoryAndRegistrationYear("Minority",Integer.parseInt(admissionYear))

        def finalList=[]
        def finalMap=[:]
        stuList.each{
            def tempList=[]
            tempList << it.rollNo
            tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
            tempList << it.program.courseName
            tempList << it.semester
            tempList << it.gender
            finalList << tempList
        }
        def headList=[]
        headList << "RollNo"
        headList << "Name"
        headList << "Program"
        headList << "Semester"
        headList << "Gender"

        finalMap.headList=headList
        finalMap.finalList=finalList

//        status = writeExcelService.excelReportMinorityStudent(finalList, sheetNo, workbook)
//        sheetNo = sheetNo + 1
//
//        workbook.write();
//        workbook.close();
        return finalMap

    }

//    Boolean getReportStudentListByGender(progInst,year,gender, excelPath) {
//        File file = new File(excelPath);
//        WorkbookSettings wbSettings = new WorkbookSettings();
//        wbSettings.setLocale(new Locale("en", "EN"));
//        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
//        int sheetNo = 0
//        def status
//
//        def stuList=[]
//                stuList = Student.findAllByProgramAndRegistrationYearAndGender(progInst,Integer.parseInt(year),gender)
//
//                def finalList=[]
//                stuList.each{
//                    def tempList=[]
//                    tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
//                    tempList << it.rollNo
//                    tempList << it.semester
//                    tempList << it.gender
//                    tempList << it.fatherName
//                    tempList << it.motherName
//                    finalList << tempList
//                }
//
//                status = writeExcelService.excelReportForStudentGender(progInst,year,gender,finalList, sheetNo, workbook)
//                sheetNo = sheetNo + 1
//
//        workbook.write();
//        workbook.close();
//        return status
//
//    }

    Map getReportStudentListByGender(progInst,year,gender) {
        /*List of student by Gender report*/
        def stuList=[]
        stuList = Student.findAllByProgramAndRegistrationYearAndGenderAndSemesterGreaterThan(progInst,Integer.parseInt(year),gender,0)

        def finalList=[]
        stuList.each{
            def tempList=[]
            tempList << it.rollNo
            tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
            tempList << it.semester
            tempList << it.gender
            tempList << it.fatherName
            tempList << it.motherName
            finalList << tempList
        }
        def headList=[]
        headList << "RollNo"
        headList << "Name"
        headList << "Semester"
        headList << "Gender"
        headList << "FatherName"
        headList << "MotherName"

        def myMap=[:]
        myMap.headList= headList
        myMap.finalList= finalList

        return myMap

//        status = writeExcelService.excelReportForStudentGender(progInst,year,gender,finalList, sheetNo, workbook)
//        sheetNo = sheetNo + 1
//
//        workbook.write();
//        workbook.close();
//        return status

    }

    Map getReportStudentCategory(progInst,year,category){
        /*List of student by Category report*/

        def stuList=[]
        if(category=="OBC"){

                    stuList = Student.findAllByProgramAndRegistrationYearAndCategoryAndSemesterGreaterThan(progInst,Integer.parseInt(year),"OBC",0)
                    def studList = Student.findAllByProgramAndRegistrationYearAndCategoryAndSemesterGreaterThan(progInst,Integer.parseInt(year),"MOBC",0)
                    stuList = stuList + studList
                }
        else{
            stuList = Student.findAllByProgramAndRegistrationYearAndCategoryAndSemesterGreaterThan(progInst,Integer.parseInt(year),category,0)
        }

        def finalMap=[:]
        def finalList=[]
        stuList.each{
            def tempList=[]
            tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
            tempList << it.rollNo
            tempList << it.category
            tempList << it.semester
            tempList << it.fatherName
            tempList << it.motherName
            finalList << tempList
        }

        def headList=[]
        headList  << "Name"
        headList  << "Roll No"
        headList  << "Category"
        headList  << "Semester"
        headList  << "Father"
        headList  << "Mother"

        finalMap.headList = headList
        finalMap.finalList = finalList

        return finalMap

//        status = writeExcelService.excelReportCategoryStudent(progInst,year,category,finalList, sheetNo, workbook)
//        sheetNo = sheetNo + 1
//
//        workbook.write();
//        workbook.close();
//        return status


    }

    Boolean getReportStudentListByCategory(progInst,year,gender,category, excelPath) {
        /*Report for List of student by Category & Gender*/
        File file = new File(excelPath);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        int sheetNo = 0
        def status
        def catGender
        def stuList=[],categoryList=[],genderList=[]
        if (category.getClass() == String) {
            categoryList << category
        }
        else{
            category.each{
                categoryList<<it
            }

        }
        if (gender.getClass() == String) {
            genderList << gender
        }
        else{
            gender.each{
            genderList<<it
            }
        }

        for(def i=0;i<categoryList.size();i++){
            genderList.each(){

                if(categoryList[i]=="OBC"){
                    catGender = categoryList[i]+" "+ it
                    stuList = Student.findAllByProgramAndRegistrationYearAndCategoryAndGenderAndSemesterGreaterThan(progInst,Integer.parseInt(year),"OBC",it,0)
                    def studList = Student.findAllByProgramAndRegistrationYearAndCategoryAndGenderAndSemesterGreaterThan(progInst,Integer.parseInt(year),"MOBC",it,0)
                    stuList = stuList + studList
                }
                else if(categoryList[i]=="PH"){
                    catGender = "PH"+" "+it
                    stuList = Student.findAllByProgramAndRegistrationYearAndIsPHAndGenderAndSemesterGreaterThan(progInst,Integer.parseInt(year),true,it,0)

                }
                else{
                    catGender = categoryList[i] +" "+ it
                    stuList = Student.findAllByProgramAndRegistrationYearAndCategoryAndGenderAndSemesterGreaterThan(progInst,Integer.parseInt(year),categoryList[i],it,0)
                }
//
                def finalList=[]
                stuList.each{
                    def tempList=[]
                    tempList << it.firstName + " " + (it.middleName ? it.middleName : "") + " " + it.lastName
                    tempList << it.rollNo
                    tempList << it.semester
                    tempList << it.fatherName
                    tempList << it.motherName
                    finalList << tempList
                }

                status = writeExcelService.excelReportForStudentCategory(catGender,year,it,categoryList[i],finalList, sheetNo, workbook)
                sheetNo = sheetNo + 1

            }


        }

        workbook.write();
        workbook.close();
        return status

    }

    Boolean getReportOfStudentDetails(params, excelPath) {
        def status
        def programList = ProgramDetail.list(sort: 'courseCode')
        if (params.inExcel) {
            def session = params.studyCentreStudentSession
            File file = new File(excelPath);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
            if (params.studyCentreStudentInfo != 'All') {
//            def studyCentreName = StudyCenter.findById(Long.parseLong(params.studyCentreStudentInfo))
                int sheetNo = 0
                programList.each {
                    def pId = it.id
                    def stuObj = Student.createCriteria()
                    def count = stuObj.list {
                        programDetail {
                            eq('id', pId)
                        }
                        studyCentre {
                            eq('id', Long.parseLong(params.studyCentreStudentInfo))
                        }
                        and {
                            eq('registrationYear', Integer.parseInt(params.studyCentreStudentSession))
                        }
//                    and {
//                        eq('status', Status.findById(4))
//                    }
                    }
//                /println(pId+ "--"+params.studyCentre +"------------------"+count)
                    status = writeExcelService.excelReport(params, count, it, sheetNo, workbook, studyCentreName.name, session)
                    sheetNo = sheetNo + 1
                }
                workbook.write();
                workbook.close();
                return status
            } else {
                def studyCentreName = "All"
                int sheetNo = 0
                programList.each {
                    def pId = it.id
                    def stuObj = Student.createCriteria()
                    def count = stuObj.list {
                        programDetail {
                            eq('id', pId)
                        }
//                        studyCentre {
//                            eq('id', Long.parseLong(params.studyCentreStudentInfo))
//                        }
                        and {
                            eq('registrationYear', Integer.parseInt(params.studyCentreStudentSession))
                        }
//                    and {
//                        eq('status', Status.findById(4))
//                    }
                    }
//                /println(pId+ "--"+params.studyCentre +"------------------"+count)
                    status = writeExcelService.excelReport(params, count, it, sheetNo, workbook, studyCentreName, session)
                    sheetNo = sheetNo + 1
                }
                workbook.write();
                workbook.close();
                return status
            }
        }
    }

    def getReportDataStudyCentre(params, excelPath) {
        def status
        def finalStudentMap = [:]
        def totalForSession = 0
        def programList = ProgramDetail.list(sort: 'courseCode')
        if (params.inExcel) {
            def session = params.studyCentreSession
            File file = new File(excelPath);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
//            def studyCentreName = StudyCenter.findById(Long.parseLong(params.studyCentre))
            int sheetNo = 0
            programList.each {
                def pId = it.id
                def stuObj = Student.createCriteria()
                def count = stuObj.list {
                    programDetail {
                        eq('id', pId)
                    }
                    studyCentre {
                        eq('id', Long.parseLong(params.studyCentre))
                    }
                    and {
                        eq('registrationYear', Integer.parseInt(params.studyCentreSession))
                    }
                    and {
                        eq('status', Status.findById(4))
                    }
                }
//                /println(pId+ "--"+params.studyCentre +"------------------"+count)
                status = writeExcelService.excelReport(params, count, it, sheetNo, workbook, studyCentreName.name, session)
                sheetNo = sheetNo + 1
            }
            workbook.write();
            workbook.close();
            return status
        } else {
            programList.each {
                def pId = it.id
                def stuObj = Student.createCriteria()
                def count = stuObj.list {
                    programDetail {
                        eq('id', pId)
                    }
                    studyCentre {
                        eq('id', Long.parseLong(params.studyCentre))
                    }
                    and {
                        eq('registrationYear', Integer.parseInt(params.studyCentreSession))
                    }
                    and {
                        eq('status', Status.findById(4))
                    }
                    projections {
                        rowCount()
                    }
                }

                totalForSession += count
//            println('final student map is '+ finalStudentMap)
                finalStudentMap.put(it.courseName, count.getAt(0))
            }
            finalStudentMap.put("TOTAL STUDENTS", totalForSession)
            return finalStudentMap
        }

    }

    def getReportDataExaminationCentre(params, excelPath) {
        def status
        def finalStudentMap = [:]
        def totalForSession = 0
        def programList = ProgramDetail.list(sort: 'courseCode')
        if (params.inExcel) {
            def session = params.examinationCentreSession
            File file = new File(excelPath);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
            int sheetNo = 0
            programList.each {
                def pId = it.id
                def stuObj = Student.createCriteria()
                def count = stuObj.list {
                    programDetail {
                        eq('id', pId)
                    }
                    city {
                        eq('id', Long.parseLong(params.examCity))
                    }
                    and {
                        eq('registrationYear', Integer.parseInt(params.examinationCentreSession))
                    }
                    and {
                        eq('status', Status.findById(4))
                    }
                }
//                println("--------------"+count)
                status = writeExcelService.excelReport(params, count, it, sheetNo, workbook, null, session)
                sheetNo = sheetNo + 1
            }
            workbook.write();
            workbook.close();
            return status
        } else {
//            println("in else")
            programList.each {
                def pId = it.id
                def stuObj = Student.createCriteria()
                def count = stuObj.list {
                    programDetail {
                        eq('id', pId)
                    }
                    city {
                        eq('id', Long.parseLong(params.examCity))
                    }
                    and {
                        eq('registrationYear', Integer.parseInt(params.examinationCentreSession))
                    }
                    and {
                        eq('status', Status.findById(4))
                    }
                    projections {
                        rowCount()
                    }
                }

                totalForSession += count
                //            println('final student map is '+ finalStudentMap)
                finalStudentMap.put(it.courseName, count.getAt(0))
            }
            finalStudentMap.put("TOTAL STUDENTS", totalForSession)
            return finalStudentMap
        }
    }

    def getReportDataCategory(params) {
        def categoryList = ['General', 'MOBC', 'OBC', 'S.T', 'SC', 'MINORITY COMMUNITY']
        def finalStudentMap = [:]
        def totalByCategory = [0, 0, 0, 0, 0, 0]
        def programList = ProgramDetail.list(sort: 'courseCode')
        programList.each {
            def sizeList = []
            def pId = it.id
            def stuObj = Student.createCriteria()
            def count = stuObj.list {
                programDetail {
                    eq('id', pId)
                }
                and {
                    eq('registrationYear', 2014)
                }
                and {
                    eq('status', Status.findById(4))
                }
                projections {
                    groupProperty("category")
                    rowCount()
                }
            }

            for (int i = 0; i < categoryList.size(); i++) {
                if (count.size()) {
                    boolean flag = false
                    for (int j = 0; j < count.size(); j++) {

                        if (count[j]?.getAt(0) == categoryList[i]) {
                            sizeList.add(i, count[j]?.getAt(1))
                            totalByCategory.set(i, totalByCategory[i] + count[j]?.getAt(1))
                            flag = true;
                            break;
                        }
                    }
                    if (flag == false) {
                        sizeList.add(i, 0)
//                        totalByCategory.add(i, totalByCategory.get(i)+0)
                    }
                } else {
                    sizeList.add(i, 0)
//                    totalByCategory.add(i, totalByCategory.get(i)+0)
                }
            }
            finalStudentMap.put(it.courseName, sizeList)
        }
        finalStudentMap.put('TOTAL STUDENTS', totalByCategory)
        return finalStudentMap
    }

    def getReportDataCategoryGender(params) {
        def categoryList = ['General', 'MOBC', 'OBC', 'S.T', 'SC', 'MINORITY COMMUNITY']
        def genderList = ['Male', 'Female']
        def finalStudentMap = [:]
        def totalByCategoryGender = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        def programList = ProgramDetail.list(sort: 'courseCode')

        programList.each {
            def sizeList = []
            def pId = it.id
            def stuObj = Student.createCriteria()
            def count = stuObj.list {
                programDetail {
                    eq('id', pId)
                }
                and {
                    eq('registrationYear', 2014)
                }
                and {
                    eq('status', Status.findById(4))
                }
                projections {
                    groupProperty("category")
                    groupProperty("gender")
                    rowCount()
                }
            }
            int l = 0
            for (int i = 0; i < categoryList.size(); i++) {

                for (int k = 0; k < genderList.size(); k++) {
                    if (count.size()) {
                        boolean flag = false
                        for (int j = 0; j < count.size(); j++) {

                            if (count[j]?.getAt(0) == categoryList[i] && count[j].getAt(1) == genderList[k]) {
                                sizeList.add(l, count[j]?.getAt(2))
//                                        println("match value of l is "+l+" valu of i is "+i+ 'value of k is '+k)
                                totalByCategoryGender.set(l, totalByCategoryGender[l] + count[j]?.getAt(2))
                                l = l + 1
                                flag = true;
                                break;
                            }
                        }
                        if (flag == false) {
                            sizeList.add(l, 0)
//                                    println("flag value of l is "+l+" valu of i is "+i+ 'value of k is '+k)
                            l = l + 1
//                            totalByCategory.set(l, totalByCategory.get(l)+0)
                        }
                    } else {
                        sizeList.add(l, 0)
//                              println("else value of l is "+l+" valu of i is "+i+ 'value of k is '+k)
                        l = l + 1
//                            totalByCategory.set(l, totalByCategory.get(l)+0)
                    }
//                     println("value of l is "+ l)
                }
            }
            finalStudentMap.put(it.courseName, sizeList)
        }
        finalStudentMap.put('TOTAL STUDENTS', totalByCategoryGender)
        return finalStudentMap
    }

    def getReportDataAdmissionApprovedUnapproved(params) {
        def stuObj = Student.createCriteria()
        def studentList
        if (params.value == 'admissionUnapproved') {
            if (params.admissionUnapprovedStudyCentre == 'All') {
//                println('me isme hu')
                studentList = stuObj.list {
                    and {
                        eq('registrationYear', Integer.parseInt(params.admissionUnapprovedSession))
                    }
                    and {
                        ne('status', Status.findById(4))
                    }

                    order('programDetail', 'asc')
                }
                return studentList
            } else {
                studentList = stuObj.list {
                    studyCentre {
                        eq('id', Long.parseLong(params.admissionUnapprovedStudyCentre))
                    }
                    and {
                        eq('registrationYear', Integer.parseInt(params.admissionUnapprovedSession))
                    }
                    and {
                        ne('status', Status.findById(4))
                    }

                    order('programDetail', 'asc')
                }
                return studentList
            }

        } else if (params.value == 'admissionApproved') {
            if (params.admissionApprovedStudyCentre == 'All') {
                studentList = stuObj.list {
                    and {
                        eq('registrationYear', Integer.parseInt(params.admissionApprovedSession))
                    }
                    and {
                        eq('status', Status.findById(4))
                    }

                    order('programDetail', 'asc')
                }
                return studentList
            } else {
                studentList = stuObj.list {
                    studyCentre {
                        eq('id', Long.parseLong(params.admissionApprovedStudyCentre))
                    }
                    and {
                        eq('registrationYear', Integer.parseInt(params.admissionApprovedSession))
                    }
                    and {
                        eq('status', Status.findById(4))
                    }

                    order('programDetail', 'asc')
                }
                return studentList
            }

        }


    }

    def getReportDataAdmissionSelfRegistration(params) {
        def stuObj = Student.createCriteria()
        def studentList = stuObj.list {
            ne('referenceNumber', '0')
            and {
                eq('registrationYear', Integer.parseInt(params.admissionSelfRegistrationSession))
            }
//            and{
//                ne('referenceNumber', 0)
//            }

            and {
                eq('status', Status.findById(4))
            }
//            and{
//              isNotNull('referenceNumber')
//            }

        }
        return studentList
    }

    def getReportDataStudyCentreFeePaid(params) {
        def finalMap = []
        def studentList
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        def fromDate
        def toDate
        def stuObj = Student.createCriteria()
        if (params.value == 'dailyFeePaid') {
            studentList = Student.list()

            fromDate = df.parse(params.feeFromDate)
            toDate = df.parse(params.feeToDate)
        } else {
            studentList = stuObj.list {
                studyCentre {
                    eq('id', Long.parseLong(params.feePaidStudyCentre))
                }
            }
            fromDate = df.parse(params.studyCentreFeeFromDate)
            toDate = df.parse(params.studyCentreFeeToDate)
        }
        toDate.setHours(24)
//        println(fromDate)
//        println(" and to date is "+ toDate)
        def feeType = FeeType.list(sort: 'type');
        int finalTotal = 0
        int i = 1
        feeType.each {
            def feeTypeIns = it
            def feeDetails = FeeDetails.createCriteria()
            def musFeeList = feeDetails.list {
                'in'('student', studentList)
                and {
                    between('paymentDate', fromDate, toDate)
                    eq('feeType', feeTypeIns)
                }
                and {
                    eq('isApproved', Status.findById(4))
                }

            }
            int totalForList = 0
//                /println("this is the count "+  musFeeList)
            if (musFeeList) {
                finalMap.add(musFeeList)
                musFeeList.each {
                    totalForList = totalForList + it.paidAmount
                }
            }
            finalTotal = finalTotal + totalForList
            if (totalForList > 0)
                finalMap.add(totalForList)
            i = i + 1
        }
//        finalMap.add(finalTotal)
        return finalMap
    }

    def getReportDataPaymentMode(params) {
//        println('these are the parameterssssssssssss '+params)
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        def fromDate = df.parse(params.paymentModeFromDate)
        def toDate = df.parse(params.paymentModeToDate)
        toDate.setHours(24)
        def feeDetails = FeeDetails.createCriteria()
        def feeList = feeDetails.list {
            eq('paymentModeId', PaymentMode.findById(Integer.parseInt(params.paymentMode)))
            and {
                between('paymentDate', fromDate, toDate)

            }
            and {
                eq('isApproved', Status.findById(4))
            }

        }
//        println(",.,.,,.,.,.,.,.,.,..,.,.,. "+feeList)
        return feeList
    }

    def getReportDataComparative(startSession, endSession) {
//        println('calling it')
        def categoryList = []
        categoryList
        def totalByCategory = []
        int j = 0
        for (int i = startSession; i <= endSession; i++) {
            categoryList.add(j, i)
            totalByCategory.add(j, 0)
            j++
        }

        def finalStudentMap = [:]
//        def programList = StudyCenter.list()
        programList.each {
            def sizeList = []
            def pId = it.id
            def stuObj = Student.createCriteria()
            def count = stuObj.list {
                studyCentre {
                    eq('id', pId)
                }
                and {
                    between('registrationYear', startSession, endSession)
                }
                and {
                    eq('status', Status.findById(4))
                }
                projections {
                    groupProperty('registrationYear')
                    rowCount()
                }

            }
            for (int i = 0; i < categoryList.size(); i++) {
                if (count.size()) {
                    boolean flag = false
                    for (j = 0; j < count.size(); j++) {

                        if (count[j]?.getAt(0) == categoryList[i]) {
                            sizeList.add(i, count[j]?.getAt(1))
                            totalByCategory.set(i, totalByCategory[i] + count[j]?.getAt(1))
                            flag = true;
                            break;
                        }
                    }
                    if (flag == false) {
                        sizeList.add(i, 0)
//                        totalByCategory.add(i, totalByCategory.get(i)+0)
                    }
                } else {
                    sizeList.add(i, 0)
//                    totalByCategory.add(i, totalByCategory.get(i)+0)
                }
            }
            finalStudentMap.put(it.name, sizeList)
        }
        finalStudentMap.put('TOTAL STUDENTS', totalByCategory)
        return finalStudentMap
    }

    def getReportDataStudentCategory(params, excelPath) {
//        println(params.categoryStudentListSession+"--------------------------"+params.studentCategory)
        def session = params.categoryStudentListSession
        File file = new File(excelPath);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        int sheetNo = 0
        def status
        def programList = ProgramDetail.list(sort: 'courseCode')
        programList.each {
            def pId = it.id
            def stuObj = Student.createCriteria()
            def count = stuObj.list {
                programDetail {
                    eq('id', pId)
                }
                and {
                    eq('registrationYear', Integer.parseInt(params.categoryStudentListSession))
                }
                and {
                    eq('category', '' + params.studentCategory)
                }
                and {
                    eq('status', Status.findById(4))
                }
            }
            status = writeExcelService.excelReport(params, count, it, sheetNo, workbook, null, session)
            sheetNo = sheetNo + 1
        }
        workbook.write();
        workbook.close();
        return status
    }

    def getReportDataCourseApprovedUnapproved(params) {
        def currentUser = springSecurityService.getCurrentUser()
        def studyCenterId
        if (currentUser) {
            studyCenterId = currentUser.studyCentreId
//            def studyCenter = StudyCenter.findById(studyCenterId)
            def stuObj = Student.createCriteria()
            if (params.value == 'courseUnapproved') {
                if (params.courseUnapproved == 'All') {
                    def studentList = stuObj.list {
                        studyCentre {
                            eq('id', studyCenter.id)
                        }
                        and {
                            eq('registrationYear', Integer.parseInt(params.courseUnapprovedSession))
                        }
                        and {
                            ne('status', Status.findById(4))
                        }
                    }

                    return studentList
                } else {
                    def studentList = stuObj.list {
                        programDetail {
                            eq('id', Long.parseLong(params.courseUnapproved))
                        }
                        studyCentre {
                            eq('id', studyCenter.id)
                        }
                        and {
                            eq('registrationYear', Integer.parseInt(params.courseUnapprovedSession))
                        }
                        and {
                            ne('status', Status.findById(4))
                        }
                    }

                    return studentList
                }

            } else if (params.value == 'courseApproved') {
                if (params.courseApproved == 'All') {
                    def studentList = stuObj.list {
                        studyCentre {
                            eq('id', studyCenter.id)
                        }
                        and {
                            eq('registrationYear', Integer.parseInt(params.courseApprovedSession))
                        }
                        and {
                            eq('status', Status.findById(4))
                        }
                    }

                    return studentList
                } else {
                    def studentList = stuObj.list {
                        programDetail {
                            eq('id', Long.parseLong(params.courseApproved))
                        }
                        studyCentre {
                            eq('id', studyCenter.id)
                        }
                        and {
                            eq('registrationYear', Integer.parseInt(params.courseApprovedSession))
                        }
                        and {
                            eq('status', Status.findById(4))
                        }
                    }

                    return studentList
                }

            }
        }
    }    //Added By Digvijay...
    def getReportDataDailyFeePaid(params) {
        def finalMap = [:]
        def feeFromDate
        def feeToDate
//        println("Report Service --> getReportDataDailyFeePaid--> Parameters Values :: "+ params)
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        feeFromDate = df.parse(params.feeFromDate)
        feeToDate = df.parse(params.feeToDate)
        feeToDate.setHours(24)
        def feeType = FeeType.list(sort: 'type')
        int i = 0
        int finalTotal = 0
        feeType.each {
            def feeTypeIns = it
            def feeObj = FeeDetails.createCriteria()
            def feeList = feeObj.list {
                eq('feeType', feeTypeIns)
                and {
                    between('paymentDate', feeFromDate, feeToDate)
                }
            }
            int totalForList = 0
            if (feeList) {
                feeList.each {
                    totalForList = totalForList + it.paidAmount
                }
            }
            finalTotal = finalTotal + totalForList
            finalMap.put("a" + i, feeList)
            i = i + 1
            finalMap.put(it.type + ' Total', totalForList)
//                println("List Value --> "+ feeList)
            return feeList
        }

    }


    def getReportDataSessionProgramWiseFee(params, excelPath) {
        def session = params.categoryStudentListSession
        File file = new File(excelPath);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        int sheetNo = 0
        def status
        def stuList
        def studyCentreName
//        if (params.sessionProgramFeePaidStudyCentre != 'All')
//            studyCentreName = StudyCenter.findById(Long.parseLong(params.sessionProgramFeePaidStudyCentre)).name
//        else
        studyCentreName = null
        def programList = ProgramDetail.list(sort: 'courseCode')
        programList.each {
            if (it.noOfTerms >= Integer.parseInt(params.programTerm)) {
                def stuObj = Student.createCriteria()
                def programIns = it
                if (params.sessionProgramFeePaidStudyCentre == 'All') {
                    stuList = stuObj.list {
                        programDetail {
                            eq('id', programIns.id)
                        }
                        and {
                            eq('registrationYear', Integer.parseInt(params.sessionProgramFeePaidSession))
                        }
                    }
                } else {
                    stuList = stuObj.list {
                        studyCentre {
                            eq('id', Long.parseLong(params.sessionProgramFeePaidStudyCentre))
                        }
                        programDetail {
                            eq('id', programIns.id)
                        }
                        and {
                            eq('registrationYear', Integer.parseInt(params.sessionProgramFeePaidSession))
                        }
                    }
                }
                if (stuList) {
                    if (params.value == 'sessionProgramWiseFeePaid') {
                        def count = FeeDetails.findAllByStudentInListAndFeeTypeAndSemesterValueAndIsApproved(stuList, FeeType.findById(3), Integer.parseInt(params.programTerm), Status.findById(4))
                        if (count) {
                            status = writeExcelForFeeService.excelReport(params, count, it, sheetNo, workbook, studyCentreName, session)
                            sheetNo = sheetNo + 1
                        }
                    } else if (params.value == 'sessionProgramWiseFeeNotPaid') {
//                                println("student list is "+ stuList)
                        def count = FeeDetails.findAllByStudentInListAndFeeTypeAndSemesterValueAndIsApprovedNotEqual(stuList, FeeType.findById(3), Integer.parseInt(params.programTerm), Status.findById(4))
//                                println("count is "+ count)
                        if (count) {
                            status = writeExcelForFeeService.excelReport(params, count, it, sheetNo, workbook, studyCentreName, session)
                            sheetNo = sheetNo + 1
                        }
                    }

                }
            }
        }
        if (sheetNo > 0) {
            workbook.write();
            workbook.close();
        }
        return status
    }

    def getReportDataDailyAdmissionReport(params) {
        def stuObj = Student.createCriteria()
        def stuList
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        def fromDate = df.parse(params.dailyAdmissionFromDate)
        def toDate = df.parse(params.dailyAdmissionToDate)
        toDate.setHours(24)
        if (params.dailyAdmissionStudyCentre == 'All') {
            stuList = stuObj.list {
                and {
                    between('admissionDate', fromDate, toDate)
                }
//                and{
//                    eq('admissionDate', fromDate)
//                    eq('admissionDate', toDate)
//                }
                and {
                    ne('rollNo', IS_NULL)
                }
            }
        } else {
//            println('in else' +params.dailyAdmissionStudyCentre)
            stuList = stuObj.list {
                studyCentre {
                    eq('id', Long.parseLong(params.dailyAdmissionStudyCentre))
                }
                and {
                    between('admissionDate', fromDate, toDate)
                }
                and {
                    ne('rollNo', IS_NULL)
                }
            }
        }
//        println("this is the list of students "+ stuList)
        return stuList
    }

    def approvedStudentDetails(params) {
        def studentList = Student.findAllByRegistrationYearAndProgramAndStatus(Integer.parseInt(params.approvedAdmissionYear), ProgramDetail.findById(Long.parseLong(params.approvedProgram)), Status.findById(4))
    }
}

