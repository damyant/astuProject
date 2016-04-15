package universityproject


import examinationproject.FeeSession
import examinationproject.FeeType
import examinationproject.MiscellaneousFee
import examinationproject.ProgramDetail

import examinationproject.ProgramSession
import examinationproject.ProgramType
import grails.transaction.Transactional

import java.text.SimpleDateFormat

@Transactional
class ProgramFeeService {

    def serviceMethod() {

    }

/**
 * Service to update fee type
 * @param programFeeInstance
 * @return
 */
    def saveProgramFeeType(params) {
        def status=false
        def newSessionStatus=false
        def feeTypeList=params.feeTypeList.split(',')
        def admissionFeeIns,sessionObj
        def programDetailIns = ProgramDetail.findById(Integer.parseInt(params.programDetailId))
//        def session = FeeSession.count()
//        if (session > 0) {
//            if (FeeSession.findByProgramDetailIdAndSessionOfFee(programDetailIns,params.programSessionId)) {
////                println("me in iffff")
//                sessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(programDetailIns,params.programSessionId)
//            } else {
////                println("**********"+programDetailIns)
//                sessionObj = new FeeSession(sessionOfFee: params.programSessionId, programDetailId:programDetailIns).save(flush: true, failOnError: true)
//                newSessionStatus=true
//            }
//        } else {
////            println("me in last els")
//            sessionObj = new FeeSession(sessionOfFee: params.programSessionId, programDetailId:programDetailIns).save(flush: true, failOnError: true)
//            newSessionStatus=true
//        }


        def i=0;
        try{

        feeTypeList.each{
            def misFeeIns  =MiscellaneousFee.findByFeeTypeAndProgramDetail(FeeType.findById(it),programDetailIns)
            if(!misFeeIns){
                misFeeIns=new MiscellaneousFee()
            }
            misFeeIns.feeType=FeeType.findById(Long.parseLong(it.toString()))
            misFeeIns.amount=Integer.parseInt(params.feeTypeAmount[i])
            misFeeIns.programDetail=programDetailIns
            ++i;
           if(misFeeIns.save(failOnError: true)){
               status = true
           }
        }
        }catch(Exception e){
            println("Error:::"+e)
        }
        return status
    }


/**
 * Service to delete a particular fee type
 * @param programFeeInstance
 */
    boolean deleteFeeType(params) {
        boolean isDeleted = false
        def programFeeInstance = FeeSession.findById(Integer.parseInt(params.id))
        if (programFeeInstance.delete(flush: true)) {
            isDeleted = true
        } else {
            isDeleted = false
        }

    }

    def getProgramSessions(params){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // Just the year
        String year = sdf.format(Calendar.getInstance().getTime());
        def startYear = year
        def endYear
        def currentSession
        def nextSession
        def programSessions= []
        try{
//            Set<ProgramDetail> programDetail = ProgramDetail.findAllById(Integer.parseInt(params.program))
          //  endYear = (Integer.parseInt(year) + programDetail[0].noOfAcademicYears).toString()
            endYear = Integer.parseInt(year)+1
            currentSession = (startYear + "-" + endYear)
            nextSession    = endYear+ "-" + ++endYear
            programSessions.add(new ProgramSession(sessionOfProgram: currentSession))
            programSessions.add(new ProgramSession(sessionOfProgram: nextSession))
//
//            println("ProgramSession"+programSessions)
            }catch(Exception ex){
                println("..........Problem in creating program session for programs"+ex)
         }

            return programSessions
    }

}
