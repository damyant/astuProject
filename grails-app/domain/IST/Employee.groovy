package IST

class Employee {
    /*Save Employee Details*/
    String firstName
    String middleName
    String lastName
    String fatherName
    String motherName
    String spouseName
    String maritalStatus
    String qualification
    String firstEmail
    String secondEmail
    Date dob
    String gender
    byte[] employeeImage
    BigInteger firstMobileNo
    BigInteger secondMobileNo
    String employeeCode
    String panNumber
    String bankName
    String accountNo
    String currentAddress
    String permanentAddress
    String currentAddressCity
    String currentAddressDistrict
    String currentStateAddress
    String currentPincode
    String permanentAddressCity
    String permanentAddressDistrict
    String permanentStateAddress
    String permanentPincode

    Department department
    BigInteger emergencyContactNo
    String salutation
    static constraints = {
        firstName(nullable:true)
        middleName(nullable:true)
        lastName(nullable:true)
        fatherName(nullable:true)
        motherName(nullable:true)
        spouseName(nullable:true)
        maritalStatus(nullable:true)
        qualification(nullable:true)
        dob(nullable:true)
        gender(nullable:true)
        firstMobileNo(nullable:true)
        secondMobileNo(nullable:true)
        employeeCode(nullable:true,unique:true)
        panNumber(nullable:true)
        currentAddress(nullable:true)
        permanentAddress(nullable:true)
        currentAddressCity(nullable:true)
        currentAddressDistrict(nullable:true)
        currentStateAddress(nullable:true)
        currentStateAddress(nullable:true)
        currentPincode(nullable:true)
        permanentAddressCity(nullable:true)
        permanentAddressDistrict(nullable:true)
        permanentStateAddress(nullable:true)
        permanentPincode(nullable:true)
        bankName(nullable:true)
        accountNo(nullable:true)
        emergencyContactNo(nullable:true)
        salutation(nullable:true)
        firstEmail(nullable:true)
        secondEmail(nullable:true)

    }


    static mapping = {
        employeeImage column: "employeeImage", sqlType: "longblob"
        firstName column: 'FirstName'
        middleName column: 'MiddleName'
        lastName column: 'LastName'
        dob column: 'DOB'
        gender column: 'Gender'
        firstMobileNo column: 'MobileNo1'
        secondMobileNo column: 'MobileNo2'
        employeeCode column: 'EmployeeCode'
        panNumber column: 'PanNumber'
        currentAddress column: 'CurrentAddress'
        permanentAddress column: 'PermanentAddress'
    }
}
