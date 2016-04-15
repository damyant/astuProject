/**
 * Created by chandan on 3/12/14.
 */
function validate() {

//alert("------------")
    $("#performApproveGradeConvert,#performGradeConvert,#performMarksEvaluation,#saveNewRule,#saveCourseExam,#saveSubExamType,#saveExamType,#certificate,#assignAdmissionPeriod,#crateNewCategory,#addStudent,#saveAssignFaculty,#studentRegister,#updatePwds,#changePwd,#equipmentCreation,#employe1,#employe,#studyMaterialPage,#addUser,#bankForm,#addCoursesFrmId,#marksTypeForm,#tempEnrollment,#districtForm,#addNewFeeType,#uploadInternalMarks,#rollNoGenerationDate,#saveExaminationCentre,#createStudyCenter,#individualDownloadAdmitCard,#studentRegister,#createCourse,#generateFeeVoucher,#generateExamFeeVoucher, #createFeeDetail").validate({


        rules: {
            programId:"required",
            academicSession:"required",
            programTerm:"required",
            courseName:"required",
            courseCode:"required",
            ruleName:"required",
            rule:"required",
            academicSession:"required",
            examType:"required",
            examSubType:"required",
            assignedTotalMarks:{
                required:true
            },
            exmType:"required",
            examSubName:"required",
            certificate:"required",
            admissionSession:"required",
            program:"required",
            semester:"required",
            admissionYear:"required",
            startAdmission_D:{
                required:true,
                minlength:10
            },
            endAdmission_D:{
                required:true,
                minlength:10
            },

            //Add Course
            pwd:{
                required:true,
                password:true
            },
            cpwd:{
                required:true,
                password:true,
                equalTo: "#pwd"
            },
            newPwd:{
                required:true,
                password:true
            },
            reNewPwd:{
                required:true,
                password:true,
                equalTo: "#newPwd"
            },
            categoryName:"required",
            rollNo: "required",
            admissionSession: "required",
            bankName:"required",
            program:"required",
            semester:"required",
            facultyAdvisor:"required",
            marksTypeName:"required",
            programTypeId:"required",
            stayInHostel:"required",
            guReceiptNo:"required",
            guReceiptAmount:"required",
            hostelRoomNo:"required",
            hostelName:"required",
            subjectSelected:"required",
            guReceiptDate:"required",
            imageValidate:"required",
            //groupSelection:"required",
            subjectName: {required: true
//                lettersnumberswithbasicpunc: true
            },

            subjectCode:{required:true},
            aliasCode:{required:true},
            creditPoints:{required:true},
            //theoryMarks:{required:true},
            // homeAssignmentMarks:{required:true},
            totalMarks:{required:true},

            registrationNo1:{
                minlength:5
            },
            registrationNo2:{
                minlength:5
            },
            //studyMaterialPageFields
            studyMaterialText: {
                required: true,
                number: true
            },
//              tempEnroll
            examCentre:"required",
            address:"required",
            examVenue:"required",
//            Study Center
            name: {
                required: true,
                textonly: true
            },
            uploadSyllabus: {
                extension: "doc|pdf|docx"
            },
            programList: "required",
            internalMarks: {
                required:true,
                accept:'xls|xlsx|cvs'
            },
            district: "required",
            city: "required",
            regNoCheck: "required",
            districtName: "required",
            parentsName:"required",
            studentAddress:"required",
            addressTown:"required",
            addressPO:"required",
            addressDistrict:"required",

            addressState:"required",
            addressPinCode: "required",
            semesterList: "required",
            examDistrict:"required",
            examCity:"required",
            examinationCentre:"required",
//            equipment
             equipmentName:"required",
             equipmentId:"required",
             labInventoryId:"required",
             equipmentType:"required",
             manufacturer:"required",
             dateOfPurchase:"required",
             issuedDate:"required",
             description:"required",
             warranty:"required",
             issuedTo:"required",
             vendorName:"required",
             quantity:"required",
//      employee

            maritalStatus:"required",

            firstEmail:{
                required: true,
                email: true
            },
            secondEmail:{

                email: true
            },


            firstMobileNo:"required",
            secondMobileNo:"required",
            employeeCode:{required:true,
                minlength:5
            },
            panNumber:"required",
//                 bankName:"required",
            accountNo:"required",
            currentAddress:"required",
            permanentAddress:"required",
            currentAddressCity:"required",
            currentAddressDistrict:"required",
            currentStateAddress:"required",
            currentPincode:"required",
            permanentAddressCity:"required",
//                 permanentAddressDistrict:"required",
//                 permanentStateAddress:"required",
//                 permanentPincode:"required",

            department:"required",
            emergencyContactNo:"required",
            salutation:"required",
//          studentRegister
//            photograph:"required",
            imageValidate:"required",
            fatherName:"required",
            motherName:"required",
            guardianName:"required",
            bloodGroup:"required",
            email:"required",
            School10th:"required",
            Subject10th:"required",
            Div10th:"required",
            Percntge10th:"required",
            Year10th:"required",
            mobileNo:"required",
            guardianMobileNo1:"required",
            guardianMobileNo2:"required",
            permanent_Address:"required",
            mailing_Address:"required",
//            designation:"required",
//            joiningDate:"required",
//            degree:"required",
//            university:"required",
//            subject:"required",
//            year:"required",
            permanentAddressDistrict:"required",
            permanentStateAddress:"required",
            mailingAddressDistrict:"required",
            mailingStateAddress:"required",
            permanentPincode:"required",
            permanentMobileNo:"required",
            mailingPincode:"required",
            mailingMobileNo:"required",


            centerCode: {
                required: true,
                alphanumeric: true
            },
            nameOfHeadIns: {
                required: true,
                textonly: true
            },
            emailIdOfHeadIns: {
                required: true,
                email: true
            },
            nameOfCoordinator: {
                required: true

            },
            emailIdOfCoordinator: {
                required: true,
                email: true
            },
            bankName:{
                required: true
            },
            websiteUrl: {
                required: true,
                url: true
            },
            phoneNoOfCoordinator: {
                required: true,
                minlength: 10,
                number: true
            },
            phoneNoOfHeadIns: {
                required: true,
                minlength: 10,
                number: true
            },

            programDetail: "required",

            nameOfApplicant: {
                required: true
            },
            programId: {
                required: true
            },
            category: {
                required: true
            },
            nationality: {
                required: true
            },
            gender: {
                required: true
            },
            state: {
                required: true
            },
            contactNo: {
                required: true,
                number: true
            },
            contactCentre: {
                required: true
            },
            location: {
                required: true
            },
            firstName: {
                required: true,
                textonly: true
            },
            lastName: {
                required: true,
                textonly: true
            },
            feeReferenceNumber:{

            },
//
            declaration: {
                required: true
            },
            courseName: {
                required: true
            },
            admissionFeeAmount:{
                required: true,
                min: 1
            },
            courseMode: {
                required: true
            },
            courseType: {
                required: true
            },
            noOfTerms: {
                required: true,
                number: true
            },
            courseCode: {
                required: true,
                number: true
            },
            noOfAcademicYears: {
                required: true,
                number: true
            },

            noOfPapers:  {
                required: true,
                number: true,
                min:1
            },
            marksPerPaper:  {
                required: true,
                number: true,
                min:1
            },
            totalCreditPoints:  {
                required: true,
                number: true,
                min:1
            },

            d_o_b: {
                required: true,
                universityDateFormat: true,
                minlength:10
            },

            startD: {
                required: true
            },
            endD: {
                required: true
            },
            mobileNo: {
                required: true,
                number: true,
                minlength: 10
            },


            studyCentre: "required",

            examiNationCentre: "required",

//              Student Enroll End

//            Exam Center Create
            examinationCentreName: {
                required: true,
                textonly: true
            },


//            CreateNewFeeType
            feeAmountAtIDOL: {
                required: true,
                number: true
            },
            feeAmountAtSC: {
                required: true,
                number: true
            },
            lateFeeAmount: {
                required: true,
                number: true,
                min: 0
            },
            examinationFee: {
                required: true,
                number: true
            },
            certificateFee: {
                required: true,
                number: true
            },
//            fee voucher
            rollNo: {
                required: true,
                number: true,
                minlength: 8
            },
            applicationNo: {
                required: true,
                number: true,
                minlength: 5,
                maxlength: 10
            },
            feeType: {
                required: true
            },
            paymentMode: {
                required: true
            },
            feeReferenceNumber:{
                required: true,
                number: true,
                minlength:6,
                maxlength:8
            },
            draftNumber: {
                required: true,
                number: true
            },
            paymentDate: {
                required: true,
                minlength:10
            },
            draftDate: {
                required: true
            },
            issuingBank: {
                required: true
            },
            issuingBranch: {
                required: true
            },
//              Download Admit Card
            rollNumber:{
                required:true,
                minlength: 8
            },
            branchName:{
                required:true
            },
            dob:{
                required:true
            },
            categoryName:{
                required:true
            },

            //Exam Centre
//            examinationCentreName: {required: true, textonly: true},
            examinationCentreCode: {required: true,
                number: true},
            examinationCentreCapacity: {required: true,
                number: true},
            examinationCentreIncharge: {required: true,
                textonly: true},
            examinationCentreContactNo: {required: true,
                number: true},
            examinationCentreAddress: {required: true},
            examCentreName: {required: true,
                textonly: true},
            type:{
                required: true,
                textonly: true
            }

        },
        messages: {
            programId:"Please select program",
            academicSession:"Please select Academic session",
            programTerm:"Please select Session",
            courseName:"Please select course",
            ruleName:"Please enter Rule name",

            rule:"Please enter the Rule Expression",
            academicSession:"Please select Academic session",
            examType:"Please select Exam type",
            examSubType:"Please select Sub Exam type",
            assignedTotalMarks:"Please select total marks",
            exmType:"Please select this field",
            examSubName:"Please Enter Exam Type Name ",
            certificate:"Please select certificate",
            admissionSession:"Please select session",
            program:"Please select  program",
            semester:"Please select semester",
            admissionYear:"Please select admission year",
            startAdmission_D:{
                required:"Please select Date",
                minlength:"Select Correct Date"
            },
            endAdmission_D:{
                required:"Please select Date",
                minlength:"Select Correct Date"
            },

            categoryName:"Please  enter Category",
            pwd:{
                required:"Please Enter Password"
            },
            cpwd:{
                required:"please Enter Confirm Password",
                equalTo: "Password Not Matching"
            },
            newPwd:{
                required:"Please Enter Password"
            },
            reNewPwd:{
                required:"please Enter Confirm Password",
                equalTo: "Password Not Matching"
            },
            programTypeId:"Please Select Programme Type",
            imageValidate:"Please Upload Image",
            stayInHostel:"",
            guReceiptNo:"",
            guReceiptAmount:"",
            hostelRoomNo:"",
            hostelName:"",
            subjectSelected:"Please Select at least one Course",
            guReceiptDate:"",
            regNoCheck:"Please Enter Reg. No. or Click A/F",
            subjectName: {required: "Please Enter Course Name",
                lettersnumberswithbasicpunc: "Letters or numbers or punctuation only please"
            },

            subjectCode:{required: "Please Enter Course Code"},
            aliasCode:{required:"Please Enter Alias Code"},
            creditPoints:{required:"Please Enter Credit Points"},
            theoryMarks:{required:"Please Enter Examination Marks"},
            homeAssignmentMarks:{required:"Please Enter Home-assignment Marks"},
            totalMarks:{required:"Please Enter Total Marks"},
            categoryName:{required:'Please Enter Category Name'},

            registrationNo1:{
                minlength:"Please Enter 5 Character"
            },
            registrationNo2:{
                minlength:"Please Enter 7 Character"
            },
////            studentRegister

            imageValidate:{required: "Please upload image"},
            fatherName:{required: "Please Enter Father Name"},
            motherName:{required: "Please Enter Mother Name"},
            guardianName:{required: "Please Enter Guardian Name"},
            email:{required: "Please Enter Email id"},
            bloodGroup:{required: "Please Enter Blood Group"},
            School10th:{required: "Please Enter School"},
            School12th:{required: "Please Enter School"},
            Subject10th:{required: "Please Enter Subject"},
            Subject12th:{required: "Please Enter Subject"},
            Div10th:{required: "Please Enter Division"},
            Div12th:{required: "Please Enter Division"},
            Percntge10th:{required: "Please Enter Percentage"},
            Percntge12th:{required: "Please Enter Percentage"},
            Year10th:{required: "Please Enter Year"},
            Year12th:{required: "Please Enter Year"},
            guardianMobileNo1:{required: "Please Enter Mobile No"},
            guardianMobileNo2:{required: "Please Enter Mobile No"},
            permanent_Address:{required: "Please Enter Address"},
            mailing_Address:{required: "Please Enter Address"},
            permanentAddressDistrict:{required: ""},
            permanentStateAddress:{required: ""},
            mailingAddressDistrict:{required: ""},
            mailingStateAddress:{required: ""},
            permanentPincode:{required: ""},
            permanentMobileNo:{required: ""},
            mailingPincode:{required: ""},
            mailingMobileNo:{required: ""},


//   Equipment
            equipmentName:{required: "Please Enter Equipment Name"},
            equipmentId:{required: "Please Enter Equipment Id"},
            labInventoryId:{required: "Please Enter Lab Inventory Id"},
            equipmentType:{required: "Please Enter Equipment Type"},
            manufacturer:{required: "Please Enter Manufacturer"},
            dateOfPurchase:{required: "Please Enter Date"},
            issuedDate:{required: "Please Enter Date"},
            description:{required: "Please Enter Description"},
            warranty:{required: "Please Enter Warranty"},
            issuedTo:{required: "Please Enter Value"},
            vendorName:{required: "Please Enter Vendor Name"},
            quantity:{required: "Please Enter Quantity"},
//          employe
//            firstName:{required: "Please Enter First Name"},
//            lastName:{required: "Please Enter Second Name"},
//            fatherName:{required: "Please Enter Father Name"},
//            motherName:{required: "Please Enter Mother Name"},
            spouseName:{required: "Please Enter Spouse Name"},
            maritalStatus:{required: "Please Select Married Status"},

            firstEmail:{required: "Please Enter Email"},
            secondEmail:{required: "Please Enter Email"},
//            dob:{required: "Please Enter Dat"},
//            gender:{required: "Please Enter Birth Date"},

            firstMobileNo:{required: "Please Enter Contact No."},
            secondMobileNo:{required: "Please Enter Contact No."},
            employeeCode:{required: "Please Enter Employee Code",
                minlength:"Minimum 5 Character Long "
            },
            panNumber:{required: "Please Enter Pan No."},
//            bankName:{required: "Please Enter Bank Name"},
            accountNo:{required: "Please Enter Account No."},
            currentAddress:{required: "Please Enter Address"},
            permanentAddress:{required: "Please Enter Address"},
            currentAddressCity:{required: ""},
            currentAddressDistrict:{required: ""},
            currentStateAddress:{required: ""},
            currentPincode:{required: ""},
            permanentAddressCity:{required: ""},
//            permanentAddressDistrict:{required: "Please Enter District"},
//            permanentStateAddress:{required: "Please Enter State"},
//            permanentPincode:{required: "Please Enter Pin"},

            department:{required: "Please Select Department"},
            emergencyContactNo:{required: "Please Enter Contact No"},
            salutation:{required: "Please Select Salutation"},

            // groupSelection:"Please Select AtLeast One Group",
            studyMaterialText:{
                required:'Please Enter Roll Number',
                number: "Only accepts Numbers"
            },
            feeReferenceNumber:{
                required: "Please Enter Reference Number",
                number: "Only accepts Numbers",
                minlength:"Minimum 6 Characters Allowed",
                maxlength:"Maximum 8 Characters Allowed"
            },
            type:{
                required: "Please Enter Fee Type Name",
                textonly: "Only accepts Characters"
            },
            bankName:{
                required: "Please Enter Bank Name"
            },
            branchName:"Please Enter Branch Name",
            rollNumber:{
                required:"Please Enter Roll Number",
                minlength:"Please Enter 8 digit Roll Number"
            },
            uploadSyllabus: {
                extension: "Please enter a value with a valid extension"
            },
            dob:{
                required:"Please Enter Date Of Birth",
                date: "Please Enter Date Of Birth in Correct Date Format"
            },
            examCentreName: {required: "Please Enter Centre Name",
                textonly: "Please Enter Alphabets Only"},
            applicationNo: {
                required: "Please Enter Application Number",
                number: "Allow Numbers Only",
                minlength:"Minimum 6 Characters Allowed",
                maxlength:"Maximum 10 Characters Allowed"
            },
            examCentre: "Please Select Examination Centre",
            programList: "Please Select Programme Name",
            semesterList: "Please Select Term",
            internalMarks:{ required:"Please Upload Internal Marks Sheet",
                accept:'Accepts only Excel and CVS file'},
            examVenue: "Please Select Examination Venue",
            profileImage: "File must be JPG, GIF or PNG, less than 1MB",
            name: "Please enter study center name",
            address: "Please enter study center address",
            district: "Please select district",
            marksTypeName:"Please Enter Marks Type",
            city: "Please select city",
            nameOfHeadIns: "Please enter name of the Principal",
            districtName: "Please Enter District Name",
            phoneNoOfHeadIns: "Please enter Contact No of Principal",
            emailIdOfHeadIns:{
                required: "Please enter email of Principal",
                email: "Please Enter Valid Email"
            },
            nameOfCoordinator: "Please enter Name of Coordinator",
            phoneNoOfCoordinator: "Please enter Phone No of Coordinator",
            emailIdOfCoordinator:{
                required: "Please enter Email of Coordinator",
                email: "Please Enter Valid Email"
            },
//            asstCoordinator: "Please enter Name of Asst. Coordinator",
//            asstMobile: "Please enter Phone No of Asst. Coordinator",
//            asstEmail:{
//                required:"Please enter Email of Asst. Coordinator",
//                email: "Please Enter Valid Email"
//            },
            websiteUrl:{
                required: "Please Enter Website URL",
                url: "Please Enter Valid website Url‎"
            },
            admissionFeeAmount:{
                required: "Please Select Programme",
                min: "Programme is unavailable at this Study Centre"
            },
            nameOfApplicant: "Please enter Name of an Applicant",
            date_of_birth: "Please Enter Date of birth",
            centerCode: "Please Enter Center Code",
            d_o_b: {required: "Please Enter Date of birth",

                minlength:"Please Enter Correct Date"
            },
            programId: "Please select Programme",
            parentsName: "Please Enter Parent's Name",
            studentAddress: "Please Enter Address",
            addressTown: "Please Enter Town Name",
            addressPO: "Please Enter Post Office Address",
            addressDistrict: "Please Enter District Name",
            addressState: "Please Enter State Name",
            addressPinCode: "Please Enter Pincode",
            programDetail: "Please enter Programme",
            category: "Please select one of these categories",
            nationality: "please select Nationality",
            gender: "Please select your gender",
            state: "Please select your State",
            contactNo: "Please enter your Contact Number",
            mobileNo: "Please enter your Contact Number",
            contactCentre: "Please enter your contact Centre",
            location: "Please Select your location",
//            photograph: "Please upload your PhotoGraph",
            declaration: "Please declare before you proceed",
            courseName: "Please Enter your Name",
            studyCentre: "Please Enter your Study Center Name",
            examinationCentre: "Please Select Examination Venue",
            examDistrict:"Please Select Examination Center District",
            examCity:"Please Select Examination Center City",
            firstName: "Please enter First Name",
            lastName:"Please enter Last Name",
            addressTown: "Please Enter your town",
            addressPO: "Please enter your Post Office",
            addressDistrict: "Please enter your District",
            addressState: "Please enter your State",
            addressPinCode: "Please enter your PinCode",
            courseMode: "Please Enter course mode",
            courseType: "Please Enter your Course Type",
            noOfTerms: "Please Enter Number of terms",
            courseCode: "Please Enter your Course Code ",
            noOfAcademicYears: "Please enter your Academic years",
            totalMarks: {required: "Please Enter Total Marks",
                min: "Enter Value Greater then Zero"
            },
            noOfPapers: {required: "Please Enter Number of papers",
                min: "Enter Value Greater then Zero"
            },
            marksPerPaper:{required: "Please Enter Passing Marks",
                min: "Enter Value Greater then Zero"
            },
            totalCreditPoints:{required: "Please Enter total Credit Points",
                min: "Enter Value Greater then Zero"
            },
            examinationCentreName: "Please Enter examination Centre Name",
            rollNo:{ required:"Please Enter Roll Number",
                minlength:"Please Enter 8 digit Roll Number"
            },
            startD:{
                required:"Please Enter Start Date",
                dateISO:"Please Enter Valid Date"
            },
            endD:{
                required:"Please Enter End Date",
                dateISO:"Please Enter Valid Date"
            },
            feeType: "Please Select Fee type",
            feeAmountAtIDOL: "Please Enter Fee amount ",
            feeAmountAtSC: "Please Enter Fee amount at Study Centre",
            lateFeeAmount: "Please Enter Late Fee amount",
            examinationFee: "Please Enter Examination Fee amount",
            certificateFee: "Please Enter Certificate Fee amount",
            paymentMode: "Please Enter Payment Mode",
            draftNumber: "Please Enter Draft Number",
            paymentDate: {required: "Please Enter Payment Date",
                minlength: "Please Enter Correct Date"
            },
            draftDate: "Please Enter Draft Date",
            issuingBank: "Please Enter Issuing Bank Name",
            issuingBranch: "Please Enter Issuing Branch Name",
            examinationCentreIncharge: "Please Enter Examination Centre Name",
            examinationCentreContactNo: "Please Enter Examination Centre Contact Number",
            examinationCentreAddress: "Please Enter Examination Centre Address",
            bankName: "Please Enter Bank Name"
        },
        errorPlacement: function (error, element) {
            if (element.is("input:radio")) {
                element.parents(".radio_options").after(error);
            } else if (element.is("input:checkbox")) {
                element.parents("#declaration-label").after(error);
            }else if (element.is("#subjectSelected")) {
                $("#semTegTable").after(error);
            } else {
                element.after(error);
            }
        }


    });
    $.validator.addMethod("universityDateFormat",function (value, element) {
            return value.match(/^\d\d?\/\d\d?\/\d\d\d\d$/);
        },
        "Please enter a date in the format dd/mm/yy"
    );

    jQuery.validator.addMethod("textonly", function (value, element) {
            valid = false;
            check = /[^-\.a-zA-Z\s\u00C0-\u00D6\u00D8-\u00F6\u00F8-\u02AE]/.test(value);
            if (check == false)
                valid = true;
            return this.optional(element) || valid;
        },

//        $.validator.addMethod('filesize', function(value, element, param) {
//            // param = size (en bytes)
//            // element = element to validate (<input>)
//            // value = value of the element (file name)
//            return this.optional(element) || (element.files[0].size <= 50)
//        }),



        jQuery.format("Please only enter letters, spaces, periods, or hyphens.")
    );
    jQuery.validator.addMethod("lettersnumberswithbasicpunc", function(value, element) {
        return this.optional(element) || /^[a-z0-9-.,\\:()'\"\s]+$/i.test(value);
    }, "Letters or punctuation only please");

    $.validator.addMethod('minStrict', function (value, element, param) {
            return value > param;
        },
        $.format("Please larger value.")
    );
    $.validator.addMethod("alphanumeric", function (value, element) {
        return this.optional(element) || /^[a-z0-9\-]+$/i.test(value);
    }, "Username must contain only letters, numbers, or dashes.");


    jQuery.validator.addMethod("extension", function(value, element, param) {
        param = typeof param === "string" ? param.replace(/,/g, '|') : "png|jpe?g|gif";
        return this.optional(element) || value.match(new RegExp(".(" + param + ")$", "i"));
    }, jQuery.format("Please enter a value with a valid extension."));
    $.validator.addMethod("password",function(value,element)
    {
        return this.optional(element) || /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[A-Za-z0-9!@#$%^&*()_]{8,16}$/i.test(value);
    },"Passwords must contain 1 number, 1 lowercase, 1 uppercase character (length 8-16)");
}


function isNumber(evt) {

    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if ((charCode > 47 && charCode < 58) || charCode == 9 ||charCode == 11 ||charCode == 8) {
        return true;
    }
    return false;
}
function isMarks(evt) {

    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if ((charCode > 47 && charCode < 58) || charCode == 9 ||charCode == 11 ||charCode == 8 ||charCode == 65 ||charCode == 97) {
        return true;
    }
    return false;
}
function isCapitalAlphabets(evt) {

    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode == 08 || ( charCode > 64 && charCode < 91 ) ||  charCode == 127 )
        return true;
    else
        return false;
}
function isRuleFormula(evt) {

    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if ((charCode > 47 && charCode < 58) ||(charCode > 64 && charCode < 74) ||(charCode > 39 && charCode < 44) || charCode == 45 ||charCode == 76 ||charCode == 84||charCode == 80 ||charCode == 47 ||charCode == 127 ||charCode == 8) {
        return true;
    }
    return false;
}
function isNumberWithDash(evt) {

    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if ((charCode > 47 && charCode < 58) || charCode == 9 ||charCode == 11 ||charCode == 8 || charCode == 45) {
        return true;
    }
    return false;
}
function isNumberWithSpaceComma(evt) {

    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if ((charCode > 47 && charCode < 58) || charCode == 9 ||charCode == 11 ||charCode == 8 || charCode == 45|| charCode == 32 || charCode == 44) {
        return true;
    }
    return false;
}

function onlyAlphabets(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if ( charCode == 11 ||charCode == 9 ||charCode == 8 || charCode == 32 || charCode == 46 || charCode == 45 || (charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123))
    {
        return true;
    }
    return false;
}


function isAlphaNumeric(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if ((charCode == 32 ||charCode == 9 ||charCode == 11||charCode == 8 || (charCode > 47 && charCode < 58) || (charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123))) {
        return true;
    }
    return false;
}

function isTime(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if ((charCode == 9 ||charCode == 58 || charCode == 11 ||charCode == 8 || charCode == 32 || ( charCode > 47 && charCode < 58) || charCode == 65 || charCode == 97 || charCode == 77 || charCode == 109 || charCode == 80 || charCode == 112)) {
        return true;
    }
    return false;
}

function onlyAlphabetsWithSplChar(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode == 9 ||charCode == 11 ||charCode == 95||charCode == 45 ||charCode == 8 || charCode == 32 || charCode == 40 || charCode == 41 || ( charCode > 64 && charCode < 91 ) || ( charCode > 96 && charCode < 123 ) ||  charCode == 45 || charCode == 46 || charCode == 47 )
        return true;
    else
        return false;
}function alphanumericWithSplChar(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if ((charCode > 47 && charCode < 58)||charCode == 9 ||charCode == 11 ||charCode == 95||charCode == 45 ||charCode == 8 || charCode == 32 || charCode == 40 || charCode == 41 || ( charCode > 64 && charCode < 91 ) || ( charCode > 96 && charCode < 123 ) ||  charCode == 45 || charCode == 46 || charCode == 47 )
        return true;
    else
        return false;
}

function admissionFeeValidation(index){

    var paymentMode = "",bankName="" ,branch="", paymentDate=""
    var bool = true
    if($('#paymentMode' + index).val()==""){
        paymentMode = "PaymentMode"
        bool = false

    }
    if($('#bankName' + index).val()==""){
        bankName="Bank Name"
        bool = false
    }
    if($('#branch' + index).val()== ""){
        branch ="Branch"
        bool = false
    }
    if($('#paymentDate' + index).val()== ""){
        paymentdate ="Payment Date"
        bool = false
    }
    if(!bool){
        alert("Please Fill "+paymentMode+","+paymentDate+","+bankName+","+branch)
        return false
    }else{

        return true
    }

}

function checkValidation() {
//    alert("hi")
    $("#paychallanForStudyCenter,#assignLateFeeDate,#assignAdmissionPeriod, #saveCatalogCatagory, #saveCatalogType").validate({
        rules: {
            paymentMode:"required",
            catalogName:"required",
            catalogCatagoryName:"required",
            paymentDate:"required",
            programCategory:"required",
            rollNoSearch:"required",
            serialNoTo:"required",
            program:"required",
            startAdmission_D:{
                required:true,
                minlength:10
            },
            endAdmission_D:{
                required:true,
                minlength:10
            },
            programs:"required",
            lateFeeDate:{
                required: true,
                minlength:10
            },
            paymentReferenceNumber: {required: true
            },
            bankName:"required",
            branchLocation:"required"
        },
        messages: {
            programCategory:"Please Select Programme Category",
            catalogName:"Please Enter Catalog Name ",
            catalogCatagoryName:"Please Enter Catalog Category Name ",
            program:"Please Select Programme",
            paymentMode:"Please  Select Payment Mode",
            rollNoSearch:"Please Enter valid Roll No",
            serialNoTo:"Please Enter valid Serial No",
            paymentDate:"Select Payment Date",
            programs:"Please Select A Programme",
            lateFeeDate:{
                required: "Please Enter Late Fee Date",
                minlength:"Please Enter Correct Date"
            },
            startAdmission_D:{
                required: "Please Enter Start Date",
                minlength:"Please Enter Correct Date"
            },
            endAdmission_D:{
                required: "Please Enter End Date",
                minlength:"Please Enter Correct Date"
            },
            paymentReferenceNumber: {required: "Please Enter Ref. Number",
                number: "Accepts Number Only"
            },
            bankName:"Please Select Bank Name",
            branchLocation:"Please Select Branch Name"
        },
        errorPlacement: function (error, element) {
            if (element.is("input:checkbox[name=programs]")) {
                element.parents("#courseListDiv").after(error);
            } else {
                element.after(error);
            }
        }
    })

}
function validationPostExam(){
    $("#marksUpdate").validate({
        rules: {
            //marksUpdate
            rollNoList:'required',
            updatedMarks:'required'
        },
        messages: {
            rollNoList:'Please Select Roll Number',
            updatedMarks:'Please Enter Correct Marks'
        }
    })
}
function validateProgramFee() {
//    alert("hi")
    $("#createNewFee,#createAdmissionFee, #individualStudentUpdate,#printIdentityCard, #customChallanSave, #signatureImage, #generateSingleAdmitCard").validate({
        rules: {
//          printIdentityCard
            programList:'required',
            admissionYear:'required',
//          printIdentityCard
//          generateSingleAdmitCard
            rollNoForFeeStatus:{
                required: true,
                number: true,
                minlength: 8
            },
            examinationVenue:"required",
//          generateSingleAdmitCard
//          #signatureImage
            examVenue:"required",
            signature:"required",
//          #signatureImage
            programDetailId:"required",
            programSessionId:"required",
            feeAmountAtIDOL:"required",
            feeAmountAtSC:"required",
            semesterList:"required",
            lateFeeAmount:"required",
            rollNo: {
                required: true,
                number: true,
                minlength: 8
            },
            challanName:'required',
            typeOfFee:'required',
            amount:'required'
        },
        messages: {
            programList:'Please Select Program',
            admissionYear:'Please Select Admission Session',
            rollNoForFeeStatus:{ required:"Please Enter a Roll Number",
                minlength:"Please Enter 8 digit Roll Number"
            } ,
            examinationVenue:"Please Select Examination Venue",
            examVenue:"Please Select Examination Venue",
            signature:"Please Upload Signature Image",
            programDetailId:"Please Select Programme Detail",
            programSessionId:"Please Select Programme Session",
            feeAmountAtIDOL:"Please  Enter Admission Fee",
            semesterList:"Please  Select Term",
            feeAmountAtSC:"Please  Enter Admission Fee at Study Centre",
            lateFeeAmount:"Please Enter Late Fee Amount",
            rollNo:{ required:"Please Enter a Roll Number",
                minlength:"Please Enter Min 8 digit Roll Number"
            } ,
            challanName:'Please Enter Name',
            typeOfFee:'Please Enter Payment Type',
            amount:'Please Enter Amount'

        }
    })
//    alert($("input[name='feeTypeAmount']").length)
    $("input[name='feeTypeAmount']").each(function(){

        $(this).rules("add", {
            required: true,
            messages: {
                required: "Please Enter The Fee Amount"
            }
        } );
    });
}
function disableKeyInput(t){
    $(t).keydown(false);
}
//function disableThisButton(t){
//    $(t).attr("disabled", true)
//}

function validateLibrary() {

    $("#saveCatalog, #bookIssueForm, #ntc1").validate({
        rules: {
            catalogDepartment:"required",
            catalogType:"required",
            catalogCategory:"required",
            catalogIsbn:"required",
            catalogTitle:"required",
            catalogAuthor:"required",
            catalogPublisher:"required",
            catalogYear:"required",
            catalogQuantity:"required",

//            ----------------
            type:"required",
            id:"required",
//            selectedBookList:"required",
//            --------------
            noticeHeader:'required',
            //fle:'required',
            roles:"required"
        },
        messages: {
            catalogDepartment:"Please select Department",
            catalogType:"Please Select Category Type",
            catalogCategory:"Please Select Catalog Category Type",
            catalogIsbn:"Please Enter Catalog ISBN",
            catalogTitle:"Please Enter Catalog Title",
            catalogAuthor:"Please Enter Catalog Author",
            catalogPublisher:"Please Enter Catalog Publisher",
            catalogYear:"Please Enter Catalog Year",
            catalogQuantity:"Please Enter Catalog Quantity",
            type:"Please Select Type",
            id:"Please Select RollNumber/ EmpId",
//            selectedBookList:"Please Add Book",
            noticeHeader:"Please Enter Notice Header",
            //fle:"Please Upload Folder",
            roles:"Please Select Role"
        }
    })
}
