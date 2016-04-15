package certificate

import examinationproject.Status

class CertificateRequestDetails {
    /*This domain saves other details after Certificate Request has been generated*/
    CertificateRequests certificateRequests
    Date generateDate
    Status status
    String referenceNo
    String year

    static constraints = {
    }
}
