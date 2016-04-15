package examinationproject

import org.apache.commons.lang.builder.HashCodeBuilder

class CourseSubject implements Serializable  {
    /*Mapping among program,semester and subject*/
    ProgramDetail courseDetail
    SubjectSession subjectSessionId
    Semester semester
    ProgramSession programSession
    Date examDate
    String examTime
    Boolean isOptional=false

    boolean equals(other) {
        if (!(other instanceof CourseSubject)) {
            return false
        }
        other.courseDetail?.id == courseDetail?.id &&
        other.subjectSessionId?.id == subjectSessionId?.id
//        other.semester?.id==semester?.id
//        other.programSession?.id==programSession?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (courseDetail) builder.append(courseDetail.id)
        if (subjectSessionId) builder.append(subjectSessionId.id)
//        if (semester) builder.append(semester.id)
//        if(programSession) builder.append(programSession.id)
        builder.toHashCode()
    }


    static CourseSubject get(long courseId, long subjectSessionId) {
        find 'from CourseSubject where courseDetail.id=:courseId and subjectSessionId.id=:subjectSessionId  ',
                [courseId: courseId, subjectSessionId: subjectSessionId]
    }

//    static CourseSubject create(ProgramDetail courseDetail,   SubjectSession subjectSessionId,boolean flush = false) {
//
//        new CourseSubject(courseDetail: courseDetail, subjectSessionId: subjectSessionId).save(failOnError: true)
//        println('saved')
//    }

    static CourseSubject create(ProgramDetail courseDetail,   SubjectSession subjectSessionId, Semester semester,ProgramSession programSession,boolean flush = false) {
         new CourseSubject(courseDetail: courseDetail, subjectSessionId: subjectSessionId,semester:semester,programSession:programSession).save(failOnError: true)

    }


//    static CourseSubject saveDate(ProgramDetail courseDetail,   SubjectSession subjectSessionId,  Date examDate,String examTime,boolean flush = false) {
//
//        new CourseSubject(courseDetail: courseDetail, subjectSessionId: subjectSessionId,examDate:examDate,examTime:examTime).save()
//    }

    static boolean remove(ProgramDetail courseDetail,SubjectSession subjectSessionId, boolean flush = false) {
        CourseSubject instance = CourseSubject.findByCourseDetailAndSubjectSessionId(courseDetail, subjectSessionId)
        if (!instance) {
            return false
        }

        instance.delete(flush: flush)
        true
    }

    static void removeAll(ProgramDetail courseDetail) {
        executeUpdate 'DELETE FROM CourseSubject WHERE courseDetail=:courseDetail' , [courseDetail: courseDetail]
    }
    static void removeAll(ProgramDetail courseDetail,ProgramSession programSession) {
        executeUpdate 'DELETE FROM CourseSubject WHERE courseDetail=:courseDetail and  programSession=:programSession', [courseDetail: courseDetail,programSession:programSession]
    }

    static void removeAll(  SubjectSession subjectSessionId) {
        executeUpdate 'DELETE FROM CourseSubject WHERE subjectSessionId=:subjectSessionId', [subjectSessionId: subjectSessionId]
    }

//    static void removeAll(Semester semester) {
//        executeUpdate 'DELETE FROM CourseSubject WHERE semester=:semester', [semester: semester]
//    }

//    static void removeAll(ProgramSession programSession) {
//        executeUpdate 'DELETE FROM CourseSubject WHERE programSession=:programSession', [programSession: programSession]
//    }
    static mapping = {
        id composite: ['courseDetail', 'subjectSessionId']
        version false
        isOptional defaultValue: false

    }



static constraints = {
    examDate (nullable: true)
    examTime (nullable: true)
    }


}





