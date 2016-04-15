package symphonie

import certificate.AnnexerA
import certificate.AnnexerB
import certificate.AnnexerC
import certificate.AnnexerD
import certificate.AnnexerE
import certificate.AnnexerF
import certificate.AnnexerG
import certificate.AnnexerH
import certificate.Certificate
import certificate.CertificateProgram
import certificate.CertificateRequestDetails
import certificate.CertificateRequests
import certificate.CertificateRole
import certificate.CertificatesTemplate
import certificate.GeneratedCertificateDetails
import com.university.Role
import examinationproject.ProgramType
import examinationproject.Status
import grails.transaction.Transactional

import java.text.SimpleDateFormat

@Transactional
class CertificateInfoService {

    def saveAnnexerServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerDInst=AnnexerD.findByCertificateDetails(certificateDetailInst)
            if(annexerDInst==null){
                annexerDInst=new AnnexerD()
            }
            annexerDInst.certificateDetails=certificateDetailInst
            annexerDInst.date=df.parse(params.editDate)
            annexerDInst.dateShown=params.editDate
            annexerDInst.fromName=params.editFromName
            annexerDInst.fromPosition=params.editFromPosition
            annexerDInst.header=params.certificateMsgHeaderEdit
            annexerDInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='')
            annexerDInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!='')
            annexerDInst.paragraph3=params.paragraphEdit3
            annexerDInst.signatureName=params.signatureNameEdit
            annexerDInst.refNo=params.refNo
            if(annexerDInst.save(flush: true,failOnError: true)){
                certificateReqInst.status=1
                if(certificateReqInst.save(flush: true)){
                    result=true
                }
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveAnnexerWithoutStatusServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerDInst=AnnexerD.findByCertificateDetails(certificateDetailInst)
            if(annexerDInst==null){
                annexerDInst=new AnnexerD()
            }
            annexerDInst.certificateDetails=certificateDetailInst
            annexerDInst.date=df.parse(params.editDate)
            annexerDInst.dateShown=params.editDate
            annexerDInst.fromName=params.editFromName
            annexerDInst.fromPosition=params.editFromPosition
            annexerDInst.header=params.certificateMsgHeaderEdit
            annexerDInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='')
                annexerDInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!='')
                annexerDInst.paragraph3=params.paragraphEdit3
            annexerDInst.signatureName=params.signatureNameEdit
            annexerDInst.refNo=params.refNo
            if(annexerDInst.save(flush: true,failOnError: true)){
                result=true
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveAnnexerEServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerEInst=AnnexerE.findByCertificateDetails(certificateDetailInst)
            if(annexerEInst==null){
                annexerEInst=new AnnexerE()
            }
            annexerEInst.certificateDetails=certificateDetailInst
            annexerEInst.date=df.parse(params.editDate)
            annexerEInst.dateShown=params.editDate
            annexerEInst.fromName=params.editFromName
            annexerEInst.fromPosition=params.editFromPosition
            annexerEInst.header=params.certificateMsgHeaderEdit
            annexerEInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='')
                annexerEInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!='' && params.paragraphEdit3!=null)
                annexerEInst.paragraph3=params.paragraphEdit3
            annexerEInst.signatureName=params.signatureNameEdit
            annexerEInst.refNo=params.refNo
            if(annexerEInst.save(flush: true,failOnError: true)){
                certificateReqInst.status=1
                if(certificateReqInst.save(flush: true)){
                    result=true
                }
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveAnnexerEWithoutStatusServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerEInst=AnnexerE.findByCertificateDetails(certificateDetailInst)
            if(annexerEInst==null){
                annexerEInst=new AnnexerE()
            }
            annexerEInst.certificateDetails=certificateDetailInst
            annexerEInst.date=df.parse(params.editDate)
            annexerEInst.dateShown=params.editDate
            annexerEInst.fromName=params.editFromName
            annexerEInst.fromPosition=params.editFromPosition
            annexerEInst.header=params.certificateMsgHeaderEdit
            annexerEInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='')
                annexerEInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!=''||params.paragraphEdit3!=null)
                annexerEInst.paragraph3=params.paragraphEdit3
            annexerEInst.signatureName=params.signatureNameEdit
            annexerEInst.refNo=params.refNo
            if(annexerEInst.save(flush: true,failOnError: true)){
                result=true
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveAnnexerBServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerBInst=AnnexerB.findByCertificateDetails(certificateDetailInst)
            if(annexerBInst==null){
                annexerBInst=new AnnexerB()
            }
            annexerBInst.certificateDetails=certificateDetailInst
            annexerBInst.date=df.parse(params.editDate)
            annexerBInst.dateShown=params.editDate
            annexerBInst.fromName=params.editFromName
            annexerBInst.fromPosition=params.editFromPosition
            annexerBInst.header=params.certificateMsgHeaderEdit
            annexerBInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='')
                annexerBInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!='' && params.paragraphEdit3!=null)
                annexerBInst.paragraph3=params.paragraphEdit3
            annexerBInst.signatureName=params.signatureNameEdit
            annexerBInst.refNo=params.refNo
            if(annexerBInst.save(flush: true,failOnError: true)){
                certificateReqInst.status=1
                if(certificateReqInst.save(flush: true)){
                    result=true
                }
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveOthersServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerBInst=GeneratedCertificateDetails.findByCertificateDetails(certificateDetailInst)
            if(annexerBInst==null){
                annexerBInst=new GeneratedCertificateDetails()
            }
            annexerBInst.certificateDetails=certificateDetailInst
            annexerBInst.date=df.parse(params.editDate)
            annexerBInst.dateShown=params.editDate
            annexerBInst.fromName=params.editFromName
            annexerBInst.fromPosition=params.editFromPosition
            annexerBInst.header=params.certificateMsgHeaderEdit
            annexerBInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='')
                annexerBInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!='' && params.paragraphEdit3!=null)
                annexerBInst.paragraph3=params.paragraphEdit3
            if(params.paragraphEdit4!='' && params.paragraphEdit4!=null)
                annexerBInst.paragraph4=params.paragraphEdit4
            if(params.paragraphEdit5!='' && params.paragraphEdit5!=null)
                annexerBInst.paragraph5=params.paragraphEdit5

            annexerBInst.signatureName=params.signatureNameEdit
            annexerBInst.refNo=params.refNo
            if(annexerBInst.save(flush: true,failOnError: true)){
                certificateReqInst.status=1
                if(certificateReqInst.save(flush: true)){
                    result=true
                }
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveAnnexerBWithoutStatusServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerBInst=AnnexerB.findByCertificateDetails(certificateDetailInst)
            if(annexerBInst==null){
                annexerBInst=new AnnexerB()
            }
            annexerBInst.certificateDetails=certificateDetailInst
            annexerBInst.date=df.parse(params.editDate)
            annexerBInst.dateShown=params.editDate
            annexerBInst.fromName=params.editFromName
            annexerBInst.fromPosition=params.editFromPosition
            annexerBInst.header=params.certificateMsgHeaderEdit
            annexerBInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='' && params.paragraphEdit2!=null)
                annexerBInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!='' && params.paragraphEdit3!=null)
                annexerBInst.paragraph3=params.paragraphEdit3
            annexerBInst.signatureName=params.signatureNameEdit
            annexerBInst.refNo=params.refNo
            if(annexerBInst.save(flush: true,failOnError: true)){
                result=true
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveOthersWithoutStatusServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerBInst=GeneratedCertificateDetails.findByCertificateDetails(certificateDetailInst)
            if(annexerBInst==null){
                annexerBInst=new GeneratedCertificateDetails()
            }
            annexerBInst.certificateDetails=certificateDetailInst
            annexerBInst.date=df.parse(params.editDate)
            annexerBInst.dateShown=params.editDate
            annexerBInst.fromName=params.editFromName
            annexerBInst.fromPosition=params.editFromPosition
            annexerBInst.header=params.certificateMsgHeaderEdit
            annexerBInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='' && params.paragraphEdit2!=null)
                annexerBInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!='' && params.paragraphEdit3!=null)
                annexerBInst.paragraph3=params.paragraphEdit3
             if(params.paragraphEdit4!='' && params.paragraphEdit4!=null)
                annexerBInst.paragraph4=params.paragraphEdit4
             if(params.paragraphEdit5!='' && params.paragraphEdit5!=null)
                annexerBInst.paragraph5=params.paragraphEdit5

            annexerBInst.signatureName=params.signatureNameEdit
            annexerBInst.refNo=params.refNo
            if(annexerBInst.save(flush: true,failOnError: true)){
                result=true
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveAnnexerCServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerCInst=AnnexerC.findByCertificateDetails(certificateDetailInst)
            if(annexerCInst==null){
                annexerCInst=new AnnexerC()
            }
            annexerCInst.certificateDetails=certificateDetailInst
            annexerCInst.date=df.parse(params.editDate)
            annexerCInst.dateShown=params.editDate
            annexerCInst.fromName=params.editFromName
            annexerCInst.fromPosition=params.editFromPosition
            annexerCInst.header=params.certificateMsgHeaderEdit
            annexerCInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='')
                annexerCInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!='' && params.paragraphEdit3!=null)
                annexerCInst.paragraph3=params.paragraphEdit3
            annexerCInst.signatureName=params.signatureNameEdit
            annexerCInst.refNo=params.refNo
            if(annexerCInst.save(flush: true,failOnError: true)){
                certificateReqInst.status=1
                if(certificateReqInst.save(flush: true)){
                    result=true
                }
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveAnnexerCWithoutStatusServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerCInst=AnnexerC.findByCertificateDetails(certificateDetailInst)
            if(annexerCInst==null){
                annexerCInst=new AnnexerC()
            }
            annexerCInst.certificateDetails=certificateDetailInst
            annexerCInst.date=df.parse(params.editDate)
            annexerCInst.dateShown=params.editDate
            annexerCInst.fromName=params.editFromName
            annexerCInst.fromPosition=params.editFromPosition
            annexerCInst.header=params.certificateMsgHeaderEdit
            annexerCInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='' && params.paragraphEdit2!=null)
                annexerCInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!='' && params.paragraphEdit3!=null)
                annexerCInst.paragraph3=params.paragraphEdit3
            annexerCInst.signatureName=params.signatureNameEdit
            annexerCInst.refNo=params.refNo
            if(annexerCInst.save(flush: true,failOnError: true)){
                result=true
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveAnnexerAServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerAInst=AnnexerA.findByCertificateDetails(certificateDetailInst)
            if(annexerAInst==null){
                annexerAInst=new AnnexerA()
            }
            annexerAInst.certificateDetails=certificateDetailInst
            annexerAInst.date=df.parse(params.editDate)
            annexerAInst.dateShown=params.editDate
            annexerAInst.fromName=params.editFromName
            annexerAInst.fromPosition=params.editFromPosition
            annexerAInst.header=params.certificateMsgHeaderEdit
            annexerAInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='')
                annexerAInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!='' && params.paragraphEdit3!=null)
                annexerAInst.paragraph3=params.paragraphEdit3
            annexerAInst.signatureName=params.signatureNameEdit
            annexerAInst.refNo=params.refNo
            annexerAInst.prevYearAdmissionFee=params.prevYearAdmissionFee
            annexerAInst.prevYearExamFee=params.prevYearExamFee
            annexerAInst.prevYearOtherFee=params.prevYearOtherFee
            annexerAInst.prevYearTuitionFee=params.prevYearTuitionFee
            annexerAInst.thisYearExamFee=params.thisYearExamFee
            annexerAInst.thisYearLabFee=params.thisYearLabFee
            annexerAInst.thisYearTuitionFee=params.thisYearTuitionFee
            annexerAInst.totalFee=params.totalFee
            if(annexerAInst.save(flush: true,failOnError: true)){
                certificateReqInst.status=1
                if(certificateReqInst.save(flush: true)){
                    result=true
                }
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveAnnexerAWithoutStatusServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerAInst=AnnexerA.findByCertificateDetails(certificateDetailInst)
            if(annexerAInst==null){
                annexerAInst=new AnnexerA()
            }
            annexerAInst.certificateDetails=certificateDetailInst
            annexerAInst.date=df.parse(params.editDate)
            annexerAInst.dateShown=params.editDate
            annexerAInst.fromName=params.editFromName
            annexerAInst.fromPosition=params.editFromPosition
            annexerAInst.header=params.certificateMsgHeaderEdit
            annexerAInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='' && params.paragraphEdit2!=null)
                annexerAInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!='' && params.paragraphEdit3!=null)
                annexerAInst.paragraph3=params.paragraphEdit3
            annexerAInst.signatureName=params.signatureNameEdit
            annexerAInst.prevYearOtherFee=params.prevYearOtherFee
            annexerAInst.prevYearAdmissionFee=params.prevYearAdmissionFee
            annexerAInst.prevYearTuitionFee=params.prevYearTuitionFee
            annexerAInst.prevYearExamFee=params.prevYearExamFee
            annexerAInst.thisYearTuitionFee=params.thisYearTuitionFee
            annexerAInst.thisYearExamFee=params.thisYearExamFee
            annexerAInst.thisYearLabFee=params.thisYearLabFee
            annexerAInst.totalFee=params.totalFee
            annexerAInst.refNo=params.refNo
            if(annexerAInst.save(flush: true,failOnError: true)){
                result=true
            }
            else{
                certificateDetailInst.delete(flush: true)
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveAnnexerFServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerFInst=AnnexerF.findByCertificateDetails(certificateDetailInst)
            if(annexerFInst==null){
                annexerFInst=new AnnexerF()
            }
            annexerFInst.certificateDetails=certificateDetailInst
            annexerFInst.date=df.parse(params.editDate)
            annexerFInst.dateShown=params.editDate
            annexerFInst.fromName=params.editFromName
            annexerFInst.fromPosition=params.editFromPosition
            annexerFInst.header=params.certificateMsgHeaderEdit
            annexerFInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='')
                annexerFInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!='' && params.paragraphEdit3!=null)
                annexerFInst.paragraph3=params.paragraphEdit3
            annexerFInst.signatureName=params.signatureNameEdit
            annexerFInst.refNo=params.refNo
            annexerFInst.firstSemFee=params.firstSemFeeEdit
            annexerFInst.firstYearFee=params.firstYearFeeEdit
            annexerFInst.secondSemFee=params.secondSemFeeEdit
            annexerFInst.secondYearFee=params.secondYearFeeEdit
            annexerFInst.courseFeeRemarks=params.courseFeeRemarksEdit
            annexerFInst.thirdSemFee=params.thirdSemFeeEdit
            annexerFInst.thirdYearFee=params.thirdYearFeeEdit
            annexerFInst.fourthSemFee=params.fourthSemFeeEdit
            annexerFInst.fourthYearFee=params.fourthYearFeeEdit
            annexerFInst.fifthSemFee=params.fifthSemFeeEdit
            annexerFInst.sixthSemFee=params.sixthSemFeeEdit
            annexerFInst.seventhSemFee=params.seventhSemFeeEdit
            annexerFInst.eighthSemFee=params.eighthSemFeeEdit
            annexerFInst.hostelFeeRemarks=params.hostelRemarksEdit
            annexerFInst.hostelFee=params.hostelAdmissionFeeEdit
            annexerFInst.hostelFeeTotal=params.hostelTotalFeeEdit
            annexerFInst.otherExpensesFee=params.otherExpensesEdit
            annexerFInst.otherExpensesFeeTotal=params.otherExpensesTotalEdit
            annexerFInst.otherFeeRemarks=params.otherExpensesRemarksEdit
            annexerFInst.laptopAmount=params.laptopExpensesEdit


            if(annexerFInst.save(flush: true,failOnError: true)){
                certificateReqInst.status=1
                if(certificateReqInst.save(flush: true)){
                    result=true
                }
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveAnnexerFWithoutStatusServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerFInst=AnnexerF.findByCertificateDetails(certificateDetailInst)
            if(annexerFInst==null){
                annexerFInst=new AnnexerF()
            }
            annexerFInst.certificateDetails=certificateDetailInst
            annexerFInst.date=df.parse(params.editDate)
            annexerFInst.dateShown=params.editDate
            annexerFInst.fromName=params.editFromName
            annexerFInst.fromPosition=params.editFromPosition
            annexerFInst.header=params.certificateMsgHeaderEdit
            annexerFInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='' && params.paragraphEdit2!=null)
                annexerFInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!='' && params.paragraphEdit3!=null)
                annexerFInst.paragraph3=params.paragraphEdit3
            annexerFInst.signatureName=params.signatureNameEdit
            annexerFInst.firstSemFee=params.firstSemFeeEdit
            annexerFInst.firstYearFee=params.firstYearFeeEdit
            annexerFInst.secondSemFee=params.secondSemFeeEdit
            annexerFInst.secondYearFee=params.secondYearFeeEdit
            annexerFInst.courseFeeRemarks=params.courseFeeRemarksEdit
            annexerFInst.thirdSemFee=params.thirdSemFeeEdit
            annexerFInst.thirdYearFee=params.thirdYearFeeEdit
            annexerFInst.fourthSemFee=params.fourthSemFeeEdit
            annexerFInst.fourthYearFee=params.fourthYearFeeEdit
            annexerFInst.fifthSemFee=params.fifthSemFeeEdit
            annexerFInst.sixthSemFee=params.sixthSemFeeEdit
            annexerFInst.seventhSemFee=params.seventhSemFeeEdit
            annexerFInst.eighthSemFee=params.eighthSemFeeEdit
            annexerFInst.hostelFeeRemarks=params.hostelRemarksEdit
            annexerFInst.hostelFee=params.hostelAdmissionFeeEdit
            annexerFInst.hostelFeeTotal=params.hostelTotalFeeEdit
            annexerFInst.otherExpensesFee=params.otherExpensesEdit
            annexerFInst.otherExpensesFeeTotal=params.otherExpensesTotalEdit
            annexerFInst.otherFeeRemarks=params.otherExpensesRemarksEdit
            annexerFInst.laptopAmount=params.laptopExpensesEdit
            annexerFInst.refNo=params.refNo
            if(annexerFInst.save(flush: true,failOnError: true)){
                result=true
            }
            else{
                certificateDetailInst.delete(flush: true)
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveAnnexerGServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerGInst=AnnexerG.findByCertificateDetails(certificateDetailInst)
            if(annexerGInst==null){
                annexerGInst=new AnnexerG()
            }
            annexerGInst.certificateDetails=certificateDetailInst
            annexerGInst.date=df.parse(params.editDate)
            annexerGInst.dateShown=params.editDate
            annexerGInst.fromName=params.editFromName
            annexerGInst.fromPosition=params.editFromPosition
            annexerGInst.header=params.certificateMsgHeaderEdit
            annexerGInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='')
                annexerGInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!='' && params.paragraphEdit3!=null)
                annexerGInst.paragraph3=params.paragraphEdit3
            if(params.paragraphEdit4!='' && params.paragraphEdit4!=null)
                annexerGInst.paragraph4=params.paragraphEdit4
            annexerGInst.signatureName=params.signatureNameEdit
            annexerGInst.refNo=params.refNo
            if(annexerGInst.save(flush: true,failOnError: true)){
                certificateReqInst.status=1
                if(certificateReqInst.save(flush: true)){
                    result=true
                }
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveAnnexerGWithoutStatusServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerGInst=AnnexerG.findByCertificateDetails(certificateDetailInst)
            if(annexerGInst==null){
                annexerGInst=new AnnexerG()
            }
            annexerGInst.certificateDetails=certificateDetailInst
            annexerGInst.date=df.parse(params.editDate)
            annexerGInst.dateShown=params.editDate
            annexerGInst.fromName=params.editFromName
            annexerGInst.fromPosition=params.editFromPosition
            annexerGInst.header=params.certificateMsgHeaderEdit
            annexerGInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='' && params.paragraphEdit2!=null)
                annexerGInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!='' && params.paragraphEdit3!=null)
                annexerGInst.paragraph3=params.paragraphEdit3
            if(params.paragraphEdit4!='' && params.paragraphEdit4!=null)
                annexerGInst.paragraph4=params.paragraphEdit4
            annexerGInst.signatureName=params.signatureNameEdit
            annexerGInst.refNo=params.refNo
            if(annexerGInst.save(flush: true,failOnError: true)){
                result=true
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveAnnexerHServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerHInst=AnnexerH.findByCertificateDetails(certificateDetailInst)
            if(annexerHInst==null){
                annexerHInst=new AnnexerH()
            }
            annexerHInst.certificateDetails=certificateDetailInst
            annexerHInst.date=df.parse(params.editDate)
            annexerHInst.dateShown=params.editDate
            annexerHInst.fromName=params.editFromName
            annexerHInst.fromPosition=params.editFromPosition
            annexerHInst.header=params.certificateMsgHeaderEdit
            annexerHInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='')
                annexerHInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit3!='' && params.paragraphEdit3!=null)
                annexerHInst.paragraph3=params.paragraphEdit3
            if(params.paragraphEdit4!='' && params.paragraphEdit4!=null)
                annexerHInst.paragraph4=params.paragraphEdit4
            if(params.paragraphEdit5!='' && params.paragraphEdit5!=null)
                annexerHInst.paragraph5=params.paragraphEdit5
            annexerHInst.signatureNameEdit=params.signatureNameEdit
            annexerHInst.designation=params.toDesignationEdit
            annexerHInst.institute=params.toInstituteEdit
            annexerHInst.address=params.toAddressEdit
            annexerHInst.refNo=params.refNo
            if(annexerHInst.save(flush: true,failOnError: true)){
                certificateReqInst.status=1
                if(certificateReqInst.save(flush: true)){
                    result=true
                }
            }
        }
        resultMap.status=result
        return resultMap
    }
    def saveAnnexerHWithoutStatusServiceMethod(params){
        def resultMap=[:]
        def result=false
        def df = new SimpleDateFormat('dd-MMM-yyyy')
        def certificateReqInst=CertificateRequests.findById(Long.parseLong(params.certificateReqId))
        def certificateDetailInst=CertificateRequestDetails.findByCertificateRequests(certificateReqInst)
        if(certificateDetailInst==null){
            certificateDetailInst=new CertificateRequestDetails()
        }
        certificateDetailInst.certificateRequests=certificateReqInst
        certificateDetailInst.generateDate=df.parse(params.editDate)
        certificateDetailInst.referenceNo=params.certificateNo
        certificateDetailInst.status=Status.findById(1)
        certificateDetailInst.year=params.year
        if(certificateDetailInst.save(flush: true,failOnError: true)){
            def annexerHInst=AnnexerH.findByCertificateDetails(certificateDetailInst)
            if(annexerHInst==null){
                annexerHInst=new AnnexerH()
            }
            annexerHInst.certificateDetails=certificateDetailInst
            annexerHInst.date=df.parse(params.editDate)
            annexerHInst.dateShown=params.editDate
            annexerHInst.fromName=params.editFromName
            annexerHInst.fromPosition=params.editFromPosition
            annexerHInst.header=params.certificateMsgHeaderEdit
            annexerHInst.paragraph1=params.paragraphEdit1
            if(params.paragraphEdit2!='' && params.paragraphEdit2!=null)
                annexerHInst.paragraph2=params.paragraphEdit2
            if(params.paragraphEdit4!='' && params.paragraphEdit4!=null)
                annexerHInst.paragraph4=params.paragraphEdit4
            if(params.paragraphEdit5!='' && params.paragraphEdit5!=null)
                annexerHInst.paragraph5=params.paragraphEdit5
            annexerHInst.signatureNameEdit=params.signatureNameEdit
            annexerHInst.designation=params.toDesignationEdit
            annexerHInst.institute=params.toInstituteEdit
            annexerHInst.address=params.toAddressEdit
            annexerHInst.refNo=params.refNo
            if(annexerHInst.save(flush: true,failOnError: true)){
                result=true
            }
        }
        resultMap.status=result
        return resultMap
    }
    /*save new certificate */
    def saveNewCertificateTemplate(params,instituteLogo){
        /*Method for saving details of certificate template like logo ,header ,content and signature etc*/
        def status=false
        def certificateInst
        def certificate=Certificate.findById(Long.parseLong(params.certificateName))
        if(params.updateCertificate){
            certificateInst=CertificatesTemplate.findById(Long.parseLong(params.updateCertificate))
        }
        else{
            certificateInst=CertificatesTemplate.findByCertificate(certificate)
            if(!certificateInst){
                certificateInst=new CertificatesTemplate()
            }
        }

        certificateInst.certificate=certificate
        certificateInst.instituteNameHeader1=params.instituteNameHeader1
        certificateInst.instituteNameHeader2=params.instituteNameHeader2
        certificateInst.instituteAddressHeader=params.instituteAddress
        if (instituteLogo.bytes)
        certificateInst.logoImage=instituteLogo.bytes
        certificateInst.refNo=params.ref
        certificateInst.fromName=params.fromName
        certificateInst.fromPosition=params.fromDesignation
        certificateInst.header=params.certificateHeader
        certificateInst.paragraph1=params.paragraph1
        certificateInst.paragraph2=params.paragraph2
        certificateInst.paragraph3=params.paragraph3
        certificateInst.paragraph4=params.paragraph4
        certificateInst.paragraph5=params.paragraph5
        certificateInst.signatureName=params.signature
        if(certificateInst.save(flush: true)){
            status=true
        }
        return status
    }
    /*saves new Certificates*/
    def saveNewCertificate(params){
        /*Saves certificate details like name, users(role) who can request for certificate, program and semesters*/
        def status=true
        def certificateInst
        if(params.updateId){
            certificateInst=Certificate.findById(Long.parseLong(params.updateId))
            def allRoleList=CertificateRole.findAllByCertificate(certificateInst)
            def allProgramList=CertificateProgram.findAllByCertificate(certificateInst)
            allRoleList.each{
                it.delete(flush: true)
            }
            allProgramList.each{
                it.delete(flush: true)
            }
        }
        else {
            certificateInst = new Certificate()
        }
        certificateInst.nameOfCertificate=params.certificateName
        if(certificateInst.save(flush: true)){
            def requestedRole=[]
            if(params.requestedRole.getClass()==String){
                requestedRole<<params.requestedRole
            }
            else{
                params.requestedRole.each{
                    requestedRole<<it
                }
            }
            requestedRole.each {
                def certificateRoleInst = new CertificateRole()
                certificateRoleInst.role=Role.findById(Long.parseLong(it))
                certificateRoleInst.certificate=certificateInst
                if(certificateRoleInst.save(flush: true)){

                }
                else {
                    status=false
                }
            }
            if(status){
                def programType=[],semesterList=[]
                if(params.programType.getClass()==String){
                    programType<<ProgramType.findById(Long.parseLong(params.programType))
                }
                else{
                    params.programType.each{
                        programType<<ProgramType.findById(Long.parseLong(it))
                    }
                }
                if(params.semester.getClass()==String){
                    semesterList<<params.semester
                }
                else{
                    params.semester.each{
                        semesterList<<it
                    }
                }
                programType.each {
                    def progTypeInst=it
                    if(semesterList.size()>0) {
                        semesterList.each {
                            def semNo = it
                            def certificateProgramInst = new CertificateProgram()
                            certificateProgramInst.programType = progTypeInst
                            certificateProgramInst.certificate=certificateInst
                            certificateProgramInst.semesterNo = Integer.parseInt(semNo)
                            if (certificateProgramInst.save(flush: true)) {

                            } else {
                                status = false
                            }
                        }
                    }
                    else{
                        def certificateProgramInst = new CertificateProgram()
                        certificateProgramInst.programType = progTypeInst
                        certificateProgramInst.certificate=certificateInst
                        if (certificateProgramInst.save(flush: true)) {

                        } else {
                            status = false
                        }
                    }
                }
            }
        }
        return status
    }
}
