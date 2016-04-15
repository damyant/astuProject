package examinationproject

import IST.ProgramBranch
import postexamination.StudentMarks


class Student {
    /*Saves details of student*/

    String firstName
    String middleName
    String lastName
    Date dob
    String category
    boolean isExtraCurricularActivity
    boolean isWardOfGuEmployee
    boolean isFringeVillage
    boolean isNEStudent=false
    boolean isPH
    String gender
    String nationality
    String state
    String bloodGroup
    BigInteger mobileNo
    BigInteger guardianMobileNo1
    BigInteger guardianMobileNo2
    String permanent_Address
    String mailing_Address
    String permanentAddressDistrict
    String permanentStateAddress
    String nationalityName='India'
    String mailingAddressDistrict
    String mailingStateAddress
    String permanentPincode
    String permanentMobileNo
    String mailingPincode
    String mailingMobileNo
    String school10th
    String subject10th
    String div10th
    String percntge10th
    String year10th
    String school12th
    String subject12th
    String div12th
    String year12th
    String percntge12th
    String diplomaSchool
    String diplomaSubject
    String diplomaYear
    String diplomaDiv
    String diplomaPercntge
    String degreeSchool
    String degreeSubject
    String degreeYear
    String degreeDiv
    String degreePercntge
    String otherSchool
    String otherSubject
    String otherYear
    String otherDiv
    String otherPercntge
    String gateSubject
    String gateYear
    String gateDiv
    String gatePercntge
    String expDetails
    String ceeScore
    String fatherName
    String motherName
    String guardianName
    String rollNo
    String email
    int semester
    Status status
    int registrationYear
    String totalMarks
    String referenceNumber
    byte[] studentImage
    ProgramDetail program
    ProgramType programType
    ProgramBranch programBranch
    Boolean admitCardGenerated
    Boolean identityCardGenerated=false
    Boolean stayInHostel=false
    String hostelName
//    Boolean isPartTime=false
    String hostelRoomNo
    Date admissionDate = new Date()
    String result
    int semesterRegistration = 0 //0 for not registration not submitted and 1 for registration submitted and 2 for submitted & rejected and 3 for submitted and accepted
    String semesterRejectionReason
    String reasonForRejection
    static hasMany = [studentMarks:StudentMarks]
    void setAField( String s ){
        firstName = s?.toUpperCase()
        middleName = s?.toUpperCase()
        lastName = s?.toUpperCase()
    }
    static constraints = {
        fatherName(nullable:false)
        stayInHostel(nullable:false)
//        isPartTime(nullable:true)
        hostelName(nullable:true)
        hostelRoomNo(nullable:true)
        motherName(nullable:false)
        guardianName(nullable:false)
        middleName(nullable: true)
        email(nullable: true)
        dob(nullable: true)
        category(nullable: true)
        nationalityName(nullable: true)
        isExtraCurricularActivity(nullable:true)
        isNEStudent(nullable:true)
        isWardOfGuEmployee(nullable:true)
        guardianMobileNo2(nullable:true)
        isFringeVillage(nullable:true)
        isPH(nullable:true)
        totalMarks(nullable: true)
        gender(nullable: true)
        nationality(nullable: true)
        state(nullable: true)
        mobileNo(nullable: true)
        rollNo(nullable:true,unique: true)
        status(nullable:true)
        admitCardGenerated(nullable: true)
        result(nullable: true)
        school12th(nullable: true)
        subject12th(nullable: true)
        div12th(nullable: true)
        year12th(nullable: true)
        percntge12th(nullable: true)
        diplomaSchool(nullable: true)
        diplomaSubject(nullable: true)
        diplomaYear(nullable: true)
        diplomaDiv(nullable: true)
        diplomaPercntge(nullable: true)
        degreeSchool(nullable: true)
        degreeSubject(nullable: true)
        degreeYear(nullable: true)
        degreeDiv(nullable: true)
        degreePercntge(nullable: true)
        otherSchool(nullable: true)
        otherSubject(nullable: true)
        otherYear(nullable: true)
        otherDiv(nullable: true)
        otherPercntge(nullable: true)
        gateSubject(nullable: true)
        gateYear(nullable: true)
        gateDiv(nullable: true)
        gatePercntge(nullable: true)
        expDetails(nullable: true)
        ceeScore(nullable: true)
        semesterRejectionReason(nullable: true)
        reasonForRejection(nullable: true)
    }

    static mapping ={
        stayInHostel defaultValue: false
//        nationalityName defaultValue: 'India'
        isNEStudent defaultValue: false
//        isPartTime defaultValue: false
        hostelName defaultValue: null
        hostelRoomNo defaultValue: null
        studentImage column: "studentImage", sqlType: "blob"
        program cascade: 'none'
        stayInHostel column: "isStayInHostel"
        isNEStudent column: "isNEStudent"
        nationalityName column: "nationalityName"
        hostelRoomNo column: "hostelRoomNo"
        hostelName column: "hostelName"
        firstName column: "firstName"
        email column: "email"
        lastName column: "lastName"
        totalMarks column: "totalMarks"
        middleName column: "middleName"
        dob column: "Dob",index: 'Dob_Index'
        rollNo column: "RollNo",index: 'RollNo_Index'
        category column: "Category"
        gender column: "Gender"
        nationality column: "Nationality"
        state column: "State"
        mobileNo column : "MobileNo"
        registrationYear column: "RegistrationYear"
        status column: 'StatusId'
        admitCardGenerated column: 'AdmitCardGenerated'
        identityCardGenerated column: 'IdentityCardGenerated'
        result column: "Result"
    }

}
