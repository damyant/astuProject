var studentIdList = [];
var subjectIdList = [];
var groupSubjectList = [];
var selected = new Array();
var feeTypeList = [];
$(document).ready(function () {
    $(document).ajaxStart(function () {

        $.blockUI({ css: {
            border: 'none',
            padding: '15px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: 5,
            color: '#fff'
        } });
    }).ajaxStop($.unblockUI);


    $(document).on('click', '#assignRollNo', function () {
//        alert("hi")
        if ($("input[name=rollno_checkbox]:checked").length != 0) {
//            $.blockUI({ message: '<h1><img src="busy.gif" /> Please Wait...</h1>' });
            $("input[name=rollno_checkbox]:checked").each(function (i) {

                if ($(this).attr("checked", true)) {
                    studentIdList[i] = $(this).attr("id");
                    $("#studentId").val(studentIdList);
                }

            })
            $.ajax({
                type: "post",
                url: url('admin', 'generateRollIsAllow', ''),
                success: function (data) {
                    if (data.status) {
                        generateRollNo(this.value)
                    }
                    else {
                        $("<div></div>").html("<div style='text-align: justify;font-size: 12px;'><p>Roll No Generation Date has Expired/Date Are Not Set Yet.</p></div>").dialog({
                            title: "Sorry",
                            resizable: false,
                            modal: true,
                            buttons: {
                                "Ok": function () {
                                    $(this).dialog("close");
                                }
                            }
                        });
                        $.unblockUI();
                        $('#generateRollNo').reset();
                        return false;
                    }
                }
            })
//            document.forms["generateRollNo"].submit();
        }
        else {
            alert("Select the student first.");
            return false;
        }
    });


//$("#feeType").append('<option value="0">' + "Admission Fee" + '</option>')


//    $('#semester').live('change',(function(){
//        alert("hi")
//        $('#postFeeType').prop('selectedIndex',0);
//    });
//
//    $('#postFeeType').live('change',(function(){
//        alert("hi")
////        $('#postFeeType').prop('selectedIndex',0);
//    });

//    $("#feeType").append('<option value="0">' + "Admission Fee" + '</option>')


});


function a(id) {
    window.open('/UniversityProject/student/applicationPrintPreview/?studentID=' + id);
}

function saveExamDate() {
    var course = $('#programList').val();
    $.ajax({
        type: "post",
        url: url('exam', 'saveExamDate', ''),
        data: $('#assignDate').serialize() + '&subjectIdList=' + subjectIdList + '&groupSubjectList=' + groupSubjectList,
        success: function (data) {
            if (data.saveFlag == true) {
                $('#assignDate')[0].reset();
                $('#assignDate').find(':input').each(function () {
                    switch (this.type) {
                        case 'text':
                            $(this).val('');
                            break;
                    }
                });
                $("#successMessage").html("Examination Date is saved")
                $("html, body").animate({ scrollTop: 0 }, "slow");
            }
            else {
                $("#successMessage").html("")
            }
        }
    });

}
function generateDuplicateChallan() {
//    alert("dfsfs")
    var rollNo = $("#rollNumberInput").val()
    var feeType = $("#postFeeType").val()
    var term = $("#semester").val()
//            alert(feeType)
    if (rollNo.length == "") {
        $("#rollNo").after('<label class="error">Please Enter Roll Number</label>')
        return false
    }
    if (feeType == "") {
        $("#type").after('<label class="error">Please Select Fee Type</label>')
        return false
    }
    $.ajax({
        type: "post",
        url: url('admin', 'generateFeeVoucher', ''),
        data: {rollNo: rollNo, feeType: feeType, term: term},
        success: function (data) {
            if (!data.status) {
                $('#statusError').html("Please Enter Correct Details.")
            } else {
                $('#statusError').html("")
                $("#rollNumberInput").val('')
                $("#postFeeType").val('')
                $("#semester").empty()
//            document.getElementById("generateFeeVoucher").reset();
                $('#studentName').text('' + data.student.firstName + ' ' + (data.student.middleName ? data.student.middleName : '') + ' ' + data.student.lastName)
                $('#studentRollNo').text('' + data.student.rollNo)
                $('#challanNo').text('' + data.challanNo)
                $('#term').text('' + data.term.semesterNo)
                $('#feeType').text('' + data.feeType.type + ' for ' + data.courseName)

                $('#amount').text('' + data.programFeeAmount)
                $('#feeInWord').text('(' + inFullWords(data.programFeeAmount) + " only)")
                if (data.lateFee > 0)
                    $('#lateFee').text('(with late fee ' + data.lateFee + ')')
                $('#challanDiv').dialog('open')
            }
        }
    })

//
//        window.open('/UniversityProject/admin/generateFeeVoucher/?rollNo=' + rollNo + '&feeType=' + feeType+'&term='+term);
}
var a = ['', 'one ', 'two ', 'three ', 'four ', 'five ', 'six ', 'seven ', 'eight ', 'nine ', 'ten ', 'eleven ', 'twelve ', 'thirteen ', 'fourteen ', 'fifteen ', 'sixteen ', 'seventeen ', 'eighteen ', 'nineteen '];
var b = ['', '', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];

function inFullWords(num) {
    if ((num = num.toString()).length > 9) return 'overflow';
    n = ('000000000' + num).substr(-9).match(/^(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
    if (!n) return;
    var str = '';
    str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'crore ' : '';
    str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'lakh ' : '';
    str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'thousand ' : '';
    str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'hundred ' : '';
    str += (n[5] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + '' : '';

    return str
}
function getStudents() {
    var program = $('#programId').val()
//    alert("*******"+program)
    if (program == 'null') {
        $('#studentList thead tr').remove()
        $('#studentList tbody tr').remove()
        return false;
    }
    else {
        $.ajax({
            type: "post",
            url: url('admin', 'getStudentList', ''),
            data: {studyCenterId: $('#studyCenter').val(), programId: $('#programId').val(), pageType: $('#pageType').val()},
            success: function (data) {
                appendTable(data)
            }
        });
    }
}

function checkRollNo() {
    $('#msgDiv').html('')
    var rollNo = $("#StudentRollNo").val()
    $.ajax({
        type: "post",
        url: url('admin', 'getStudentByRollNo', ''),
        data: {rollNo: rollNo},
        success: function (data) {
            if (data.student) {
                $('#rollNo').prop('disabled', false)
                $('#view').prop('disabled', false)
                $('#viewSem').prop('disabled', false)
                $('#msgDiv').html('')

            }
            else if (data.noStudent) {
                $('#rollNo').prop('disabled', true)
                $('#view').prop('disabled', true)
                $('#viewSem').prop('disabled', true)
                $('#msgDiv').html('' + data.noStudent)
            }

        }

    });
}

function updateStudentByRollNo() {
//    alert("dfdfdf")
    validateProgramFee()
    var rollNo = $("#StudentRollNo").val()
//    alert($('#individualStudentUpdate').valid())
    if ($('#individualStudentUpdate').valid()) {
        if (rollNo.length >= 8) {

            window.location.href = '/Symphonie/student/updateStudent?rollNo=' + rollNo;
        }
        else {
            alert("Please Correct roll number!")
        }
    }

}
function updateStudentListByRollNo(roll) {
    var rollNo = roll
    if ($('#individualStudentUpdate').valid()) {
        if (rollNo.length >= 8) {

            window.location.href = '/Symphonie/student/updateStudent?rollNo=' + rollNo;
        }
        else {
            alert("Please Correct roll number!")
        }
    }

}
function viewStudentByRollNo() {
    validateProgramFee()
    var rollNo = $("#StudentRollNo").val()
//    alert(rollNo)
    if ($('#individualStudentUpdate').valid()) {
        if (rollNo.length >= 8) {
            window.location.href = '/Symphonie/student/showStudentDetails?studentRoll=' + rollNo;
        }
        else {
            alert("Please Correct roll number!")
        }
    }
}
function viewStudentSemesterByRollNo() {
    validateProgramFee()
    var rollNo = $("#StudentRollNo").val()
//    alert(rollNo)
    if ($('#individualStudentUpdate').valid()) {
        if (rollNo.length >= 8) {
            window.location.href = '/Symphonie/student/showStudentSemesterDetails?studentRoll=' + rollNo;
        }
        else {
            alert("Please Correct roll number!")
        }
    }
}
function viewStudentListByRollNo(roll) {
    var rollNo = roll
    if ($('#individualStudentUpdate').valid()) {
        if (rollNo.length >= 8) {
            window.location.href = '/Symphonie/student/showStudentDetails?studentRoll=' + rollNo;
        }
        else {
            alert("Please Correct roll number!")
        }
    }
}
function enableProgram(t) {
    var op = $(t).val();
    $('#programId').val('');
    $('#studentList thead tr').remove()
    $('#studentList tbody tr').remove()
    if (op != 'null') {
        $('#programId').prop('disabled', false);
    } else {
        $('#programId').val('');
        $('#programId').prop('disabled', true);
        $('#studentList thead tr').remove()
        $('#studentList tbody tr').remove()


    }
}
function toggleChecked(status) {
    $(".checkbox").each(function () {
//        $('input:checkbox:not(:disabled)').attr("checked", status)
        $('input:checkbox:not(:disabled)').prop("checked", status);
    })
}

function generateRollNo(value) {
    $.ajax({
        type: "post",
        url: url('admin', 'generateRollNo', ''),
        data: {studyCenterId: $('#studyCenter').val(), programId: $('#programId').val(), studentList: $("#studentId").val(), pageType: value},
        success: function (data) {
            if (data.studentName) {
                $("<div></div>").html("<div style='text-align: justify;font-size: 12px;'>" +
                "<div>" + data.studentName + "</div></div>").dialog({
                    title: "Success",
                    resizable: false,
                    modal: true,
                    buttons: {
                        "Ok": function () {
                            $(this).dialog("close");
                        }
                    }
                });
                getStudents()
            }
//            appendTable(data)
        }
    });

}


function appendTable(data) {

    document.getElementById("studentList").style.visibility = "hidden";
    document.getElementById("paginationDiv").style.visibility = "hidden"
    $('#studentList thead tr').remove()
    $('#studentList tbody tr').remove()
    $('#studentListButton tbody tr').remove()
    if (data.stuList.length > 0) {
        $('#msg').html("")
        document.getElementById("studentList").style.visibility = "visible";
        document.getElementById("paginationDiv").style.visibility = "visible";
        $('#studentList thead').append('<tr><th><input type="checkbox" name="chkbox" onchange="toggleChecked(this.checked)"/> <label for="chkbox">Select All</label> </th><th>' + "Student Name" + '</th><th>' + "Reference Number" + '</th><th></th></tr>')
        for (var i = 0; i < data.stuList.length; i++) {
            $('#studentList tbody').append('<tr><td><input type="checkbox" name="rollno_checkbox"  class="checkbox" id="' + data.stuList[i].id + '"/></td><td>' + data.stuList[i].firstName + ' ' + (data.stuList[i].middleName ? data.stuList[i].middleName : '') + ' ' + data.stuList[i].lastName + '</td><td>' + data.stuList[i].referenceNumber + '</td><td><input type="button" class="university-button" id="view" value="View" onclick="viewStudent(' + data.stuList[i].id + ')"/></td></tr>')
        }
        $table_rows = $('#studentList tbody tr');
        var table_row_limit = 10;
        var page_table = function (page) {
            var offset = (page - 1) * table_row_limit,
                limit = page * table_row_limit;
            $table_rows.hide();
            $table_rows.slice(offset, limit).show();
        }
        var pageNo = 0
        if ($table_rows.length % table_row_limit) {
            pageNo = parseInt(parseInt($table_rows.length) / table_row_limit) + 1
        }
        else {
            pageNo = parseInt($table_rows.length / table_row_limit)
        }
        $('.pagination').jqPagination({
            max_page: pageNo,
            paged: page_table
        });
        page_table(1);
        $('#studentListButton tbody').empty().append('<tr><td colspan="3"><input type="button" value="Assign Roll No" id="assignRollNo"></td></tr>')


    }
    else {
        document.getElementById("studentList").style.visibility = "hidden";
        $('#msg').html("<div class='university-status-message'>No Students Found</div>")
    }
}

function getSemesterAndSubjectList() {
    var session = $('#SessionList').val()
    var sessionType = $("#sessionType").val()
    if (session && sessionType != '0') {
        $.ajax({
            type: "post",
            url: url('exam', 'getSubjectList', ''),
            data: {sessionId: $('#SessionList').val(), sessionTypeId: $("#sessionType").val()},
            success: function (data) {

                if (data.noSubjects == true) {
                    $("#subjectList tr").remove()
                    $("#msgDiv").html("There is no Course associated with the program")

                }
                else {
                    $("#msgDiv").html("")
                    appendSubjects(data)
                }
            }
        });
    }
    else {
        $("#subjectList").empty();
    }
}
function appendSubjects(obj) {

    var count = 1;
    var counter = 0;
    $("#subjectList").empty();
   //alert("===="+obj.allSubjects.length);
    for (var i = 0; i < obj.allSubjects.length; i++) {


        //console.log("====" + $('#subjectList tr').length)
        if ($('#subjectList tr').length < 1) {
            if (obj.semesterNoList[i][0].semesterNo)
                $("#subjectList").append('<tr><th>' + "Term" + obj.semesterNoList[i][0].semesterNo + " Courses" + '</th><th>Examination Date</th><th>Examination Time</th></tr>')
        }
        else {
            if (obj.semesterNoList[i][0].semesterNo != obj.semesterNoList[i - 1][0].semesterNo) {
                console.log("innnn")
                if (obj.semesterNoList[i][0].semesterNo)
                    $("#subjectList").append('<tr><th>' + "Term" + obj.semesterNoList[i][0].semesterNo + " Courses" + '</th><th>Examination Date</th><th>Examination Time</th></tr>')

            }
        }

        //if (obj.groupNameList[i]) {
        //    $("#subjectList").append('<tr id="subjectRow"><td>' + obj.groupNameList[i] + '</td></tr>')
        //}
        for (var j = 0; j < obj.allSubjects[i].length; j++) {
            debugger;
            //if (obj.groupNameList[i]) {
            //    groupSubjectList[counter] = obj.allSubjects[i][j].id + "-" + obj.semesterNoList[i][0].semesterNo + "-" + obj.groupNameList[i]
            //    subjectIdList[counter] = obj.allSubjects[i][j].id + "-" + obj.semesterNoList[i][0].semesterNo + "-" + "no"
            //}
            //else {
            //    debugger;
                subjectIdList[counter] = obj.allSubjects[i][j].id + "-" + obj.semesterNoList[i][0].semesterNo
                groupSubjectList[counter] = obj.allSubjects[i][j].id + "-" + obj.semesterNoList[i][0].semesterNo + "-" + "no"
            //}
//=======
//        $("#subjectList").append('<tr><th>' + "Term" + obj.semesterNoList[i][0].semesterNo + " Courses" + '</th><th>Examination Date</th><th>Examination Time</th></tr>')
//        for (var j = 0; j < obj.allSubjects[i].length; j++) {
//            subjectIdList[counter] = obj.allSubjects[i][j].id + "-" + obj.semesterNoList[i][0].semesterNo
//>>>>>>> origin/damyant
            var datesInNewFormat = "", examTime = ""

            //if (obj.dateList[counter] != undefined && obj.dateList[counter].toString() != 'noo')
            //
            //{
            //    var d = $.datepicker.parseDate("mm/dd/yy", obj.dateList[counter].toString());
            //    datesInNewFormat = $.datepicker.formatDate("dd/mm/yy", d);
            //}

            //if (obj.allSubjects[i][j].toString() != null) {
            //    examTime = obj.examTimeList[i][j]
            //
            //}
            debugger;
            $("#subjectList").append('<tr id="subjectRows' + counter + '"><td class="university-size-1-3">' + obj.subNameList[i][j] + '</td><td class="university-size-1-3">' +
            '<input type="text"  name="examinationDate" maxlength="10" onkeypress="disableKeyInput(this)" id="examDate' + counter + '"  onchange="clearError(this)" class="datePickers university-size-1-2 "  value=' + datesInNewFormat + '></input><label id="dateError' + counter + '" class="error3">&nbsp;</label></td>' +
            '<td class="university-size-1-3"><input type="text" maxlength="8" id="examTime' + counter + '" onkeypress="disableKeyInput(this)"  onchange="clearError(this)"  name="examinationTime" style="width: 70px;" class="timePicker_6" value="' + examTime + '" /><label id="timeError' + counter + '" class="error4">&nbsp;</label></td>' +
            '</tr>')

            debugger;
            ++counter;
        }

        count++;
    }
    $("#subjectList").append('<tr><td colspan="2"><input type="button" id="submitExamDate" class="university-button" value="Submit" onclick="validateFields(' + counter + ')"/></td></tr>')
    $(".datePickers").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd/mm/yy",
        minDate: 0
    });
    $('.timePicker_6').timepicker({
        showPeriod: true,
        showLeadingZero: true
    });
}
function validateFields(counter) {
//    alert(counter)
    var date = null;
    var time = null;
    var bool = true;
    for (var i = 0; i < counter; i++) {
        date = $('#examDate' + i).val();
        time = $('#examTime' + i).val()
        if ((date == "null" || date.length == 0)) {
//            $('#dateError' + i).text("Please Select Examination Date")
            bool = true
        }
        else if (date.length != 10) {
            $('#dateError' + i).text("Please Enter Proper Date")
            bool = false
        }
        if ((time == "null" || time == "")) {
//            $('#timeError' + i).text("Please Select Examination Time")
            bool = true
        }
        else if (time.length != 8) {
            $('#timeError' + i).text("Please Enter Proper Date")
            bool = false
        }
    }
    if (bool) {
        saveExamDate();
    }
    return bool;
}

function checkTimeFormat(count) {

    re = /^\d{1,2}:\d{2}([ap]m)?$/;

    var val = $('#examTime' + count).val();

    if (val != '' && !val.match(re)) {
//        alert("Invalid time format: " + val);
        form.timeVal.focus();
        return false;
    }
    return true;

}

function saveExamVenue() {
    var venueList = []
    if ($("#addExamCentre").html() == "") {
        alert("Please assign at least one venue");
        return false
    }


    $('#addExamCentre option').each(function () {
        venueList.push($(this).val() || '');

    });

    $.ajax({
        type: "post",
        url: url('admin', 'saveExamVenue', ''),
        data: $("#assignExamVenue").serialize() + "&venueList=" + venueList,
        success: function (data) {
            $('#assignExamVenue')[0].reset();
            $('#courseForExamVenue').html('');
            $('#CentreForExamVenue').html('');
            $('#examCenterList').empty();
            $('#addExamCentre').empty();
            $("#examinationCentre").prop("disabled", false)
            $('#successMessage').html('Successfully Assigned Examination Venue');
        }
    });
}

//function generateStudentsList() {
//
//    $.ajax({
//        type: "post",
//        url: url('admin', 'generateStudentList', ''),
//        data: {studyCenterId: $('#studyCenter').val(), programId: $('#programId').val(), session: $('#session').val()},
//        success: function (data) {
//            $('#studentList thead tr').remove()
//            $('#studentList tbody tr').remove()
//            if (data.studList.length > 0) {
//                $('#msg').html("");
//                document.getElementById("studentList").style.visibility = "visible";
//                document.getElementById("paginationDiv").style.visibility = "visible";
////                $('#paginationDiv').after(data.studList[0].firstName)
//                $('#studentList thead').append('<tr><th class="university-size-1-11">Sr. No.</th><th class="university-size-1-11">' + "Roll Number" + '</th><th class="university-size-1-11">' + "Student Name" + '</th><th class="university-size-1-11">' + "Date of Birth" + '</th><th class="university-size-1-11">' + "Gender" + '</th><th class="university-size-1-11">' + "Mobile No" + '</th><th class="university-size-1-11">Fee Status</th><th class="university-size-1-11">Admission Status</th><th class="university-size-1-4">&nbsp;</th></tr>')
//                for (var i = 0; i < data.studList.length; i++) {
//                    if (data.studList[i].middleName != null) {
//                        if (data.studList[i].rollNo != null) {
//
//                            $('#studentList tbody').append('<tr><td>'+ (parseInt(i)+1) +'</td><td>' + data.studList[i].rollNo + '</td><td>' + data.studList[i].firstName + ' ' + data.studList[i].middleName + ' ' + data.studList[i].lastName + '</td><td>' + $.datepicker.formatDate('MM dd, yy', new Date(data.studList[i].dob)) + '</td><td>' + data.studList[i].gender + '</td><td>' + data.studList[i].mobileNo + '</td><td style="text-align: center;">' + data.feeStatus[i] + '</td><td style="text-align: center;">' + data.status[i] + '</td><td style="text-align: center;"><input type="button" class="university-button" id="view" value="View" onclick="viewStudent(' + data.studList[i].id + ')"/><input type="button" class="university-button" id="updateStudentButton"  value="Update" onclick="updateStudent(' + data.studList[i].id + ')"/><input type="button" class="university-button" id="deleteStudentButton" title="Only Unapproved students" value="Delete" onclick="deleteStudent(' + data.studList[i].id + ')"/></td></tr>')
//                        }
//                        else {
//                            $('#studentList tbody').append('<tr><td>'+ (parseInt(i)+1) +'</td><td>' + "Not Generated Yet" + '</td><td>' + data.studList[i].firstName + ' ' + data.studList[i].middleName + ' ' + data.studList[i].lastName + '</td><td>' + $.datepicker.formatDate('MM dd, yy', new Date(data.studList[i].dob)) + '</td><td>' + data.studList[i].gender + '</td><td>' + data.studList[i].mobileNo + '</td><td style="text-align: center;">' + data.feeStatus[i] + '</td><td style="text-align: center;">' + data.status[i] + '</td><td style="text-align: center;"><input type="button" class="university-button" id="view" value="View" onclick="viewStudent(' + data.studList[i].id + ')"/><input type="button" class="university-button" id="updateStudentButton" value="Update" onclick="updateStudent(' + data.studList[i].id + ')"/><input type="button" class="university-button" id="deleteStudentButton" title="Only Unapproved students" value="Delete" onclick="deleteStudent(' + data.studList[i].id + ')"/></td></tr>')
//                        }
//                    }
//                    else {
//                        if (data.studList[i].rollNo != null) {
//                            $('#studentList tbody').append('<tr><td>'+ (parseInt(i)+1) +'</td><td>' + data.studList[i].rollNo + '</td><td>' + data.studList[i].firstName + ' ' + data.studList[i].lastName + '</td><td>' + $.datepicker.formatDate('MM dd, yy', new Date(data.studList[i].dob)) + '</td><td>' + data.studList[i].gender + '</td><td>' + data.studList[i].mobileNo + '</td><td style="text-align: center;">' + data.feeStatus[i] + '</td><td style="text-align: center;">' + data.status[i] + '</td><td style="text-align: center;"><input type="button" class="university-button" id="view" value="View" onclick="viewStudent(' + data.studList[i].id + ')"/><input type="button" class="university-button" id="updateStudentButton"  value="Update" onclick="updateStudent(' + data.studList[i].id + ')"/><input type="button" class="university-button" id="deleteStudentButton" title="Only Unapproved students" value="Delete" onclick="deleteStudent(' + data.studList[i].id + ')"/></td></tr>')
//                        }
//                        else {
//                            $('#studentList tbody').append('<tr><td>'+ (parseInt(i)+1) +'</td><td>' + "Not Generated Yet" + '</td><td>' + data.studList[i].firstName + ' ' + data.studList[i].lastName + '</td><td>' + $.datepicker.formatDate('MM dd, yy', new Date(data.studList[i].dob)) + '</td><td>' + data.studList[i].gender + '</td><td>' + data.studList[i].mobileNo + '</td><td style="text-align: center;">' + data.feeStatus[i] + '</td><td style="text-align: center;">' + data.status[i] + '</td><td style="text-align: center;"><input type="button" class="university-button" id="view" value="View" onclick="viewStudent(' + data.studList[i].id + ')"/><input type="button" class="university-button" id="updateStudentButton"  value="Update" onclick="updateStudent(' + data.studList[i].id + ')"/><input type="button" class="university-button" id="deleteStudentButton" title="Only Unapproved students" value="Delete" onclick="deleteStudent(' + data.studList[i].id + ')"/></td></tr>')
//                        }
//                    }
//                }
//                $('#studentList tbody tr:not(:first)').hide();
//                $table_rows = $('#studentList tbody tr');
//
//                var table_row_limit = 20;
//
//                var page_table = function (page) {
//
//                    // calculate the offset and limit values
//                    var offset = (page - 1) * table_row_limit,
//                        limit = page * table_row_limit;
//
//                    // hide all table rows
//                    $table_rows.hide();
//
//                    // show only the n rows
//                    $table_rows.slice(offset, limit).show();
//
//                }
//                var pageNo = 0
//                if ($table_rows.length % table_row_limit) {
//                    pageNo = parseInt(parseInt($table_rows.length) / table_row_limit) + 1
//                }
//                else {
//                    pageNo = parseInt($table_rows.length / table_row_limit)
//                }
////                alert(5%5)
//                $('.pagination').jqPagination({
//                    max_page: pageNo,
//                    paged: page_table
//                });
//                page_table(1);
//
//
//            }
//            else {
//                document.getElementById("paginationDiv").style.visibility = "hidden";
//                document.getElementById("studentList").style.visibility = "hidden";
//                $('#msg').html("<div class='university-status-message'>No Students Found</div>")
//            }
//
//        }
//    });
//}

function generateStudentsList() {
    var isFacultyAdvisor = $('#isFacultyAdvisor').val()

    $.ajax({
        type: "post",
        url: url('admin', 'generateStudentList', ''),
        data: { programId: $('#programId').val()},
        success: function (data) {
            $('#studentList thead tr').remove()
            $('#studentList tbody tr').remove()
            if (data.studList.length > 0) {
                $('#msg').html("");
                document.getElementById("studentList").style.visibility = "visible";
                document.getElementById("paginationDiv").style.visibility = "visible";
                if (isFacultyAdvisor == 'true') {
                    $('#studentList thead').append('<tr><th style="width: 5%;">Sr. No.</th><th style="8%">' + "Roll Number" + '</th><th style="13%">' + "Student Name" + '</th><th  style="width: 15%;">' + "Date of Birth" + '</th><th style="8%">' + "Gender" + '</th><th style="5%">' + "Mobile No" + '</th><th style="width: 10%">Semester Registration</th><th style="width: 25%">Student Details</th></tr>')
                    for (var i = 0; i < data.studList.length; i++) {
                        if (data.studList[i].middleName != null) {

                            $('#studentList tbody').append('<tr><td>' + (parseInt(i) + 1) + '</td><td>' + data.studList[i].rollNo + '</td><td style=" line-height: 18px;">' + data.studList[i].firstName + ' ' + data.studList[i].middleName + ' ' + data.studList[i].lastName + '</td><td  style=" line-height: 18px;">' + $.datepicker.formatDate('MM dd, yy', new Date(data.studList[i].dob)) + '</td><td>' + data.studList[i].gender + '</td><td>' + data.studList[i].mobileNo + '</td><td><input type="button" class="university-button" id="view" value="view" onclick="viewStudentSemester(' + data.studList[i].id + ')"/></td><td><input type="button" class="university-button" id="view" value="View" onclick="viewStudent(' + data.studList[i].id + ')"/></td></tr>')

                        }
                        else {
                            $('#studentList tbody').append('<tr><td>' + (parseInt(i) + 1) + '</td><td>' + data.studList[i].rollNo + '</td><td style=" line-height: 18px;">' + data.studList[i].firstName + ' ' + data.studList[i].lastName + '</td><td  style=" line-height: 18px;">' + $.datepicker.formatDate('MM dd, yy', new Date(data.studList[i].dob)) + '</td><td>' + data.studList[i].gender + '</td><td>' + data.studList[i].mobileNo + '</td><td><input type="button" class="university-button" id="view" value="view" onclick="viewStudentSemester(' + data.studList[i].id + ')"/></td><td><input type="button" class="university-button" id="view" value="View" onclick="viewStudent(' + data.studList[i].id + ')"/></td></tr>')

                        }
                    }
                }
                else {
                    $('#studentList thead').append('<tr><th style="width: 5%;">Sr. No.</th><th style="8%">' + "Roll Number" + '</th><th style="13%">' + "Student Name" + '</th><th  style="width: 15%;">' + "Date of Birth" + '</th><th style="8%">' + "Gender" + '</th><th style="5%">' + "Mobile No" + '</th><th style="width: 10%">Semester Registration</th><th style="width: 25%">Student Details</th></tr>')
                    for (var i = 0; i < data.studList.length; i++) {
                        if (data.studList[i].middleName != null) {

                            $('#studentList tbody').append('<tr><td>' + (parseInt(i) + 1) + '</td><td>' + data.studList[i].rollNo + '</td><td style=" line-height: 18px;">' + data.studList[i].firstName + ' ' + data.studList[i].middleName + ' ' + data.studList[i].lastName + '</td><td  style=" line-height: 18px;">' + $.datepicker.formatDate('MM dd, yy', new Date(data.studList[i].dob)) + '</td><td>' + data.studList[i].gender + '</td><td>' + data.studList[i].mobileNo + '</td><td><input type="button" class="university-button" id="view" value="view" onclick="viewStudentSemester(' + data.studList[i].id + ')"/></td><td><input type="button" class="university-button" id="view" value="View" onclick="viewStudent(' + data.studList[i].id + ')"/><input type="button" class="university-button" id="updateStudentButton"  value="Update" onclick="updateStudent(' + data.studList[i].id + ')"/></td></tr>')

                        }
                        else {
                            $('#studentList tbody').append('<tr><td>' + (parseInt(i) + 1) + '</td><td>' + data.studList[i].rollNo + '</td><td style=" line-height: 18px;">' + data.studList[i].firstName + ' ' + data.studList[i].lastName + '</td><td  style=" line-height: 18px;">' + $.datepicker.formatDate('MM dd, yy', new Date(data.studList[i].dob)) + '</td><td>' + data.studList[i].gender + '</td><td>' + data.studList[i].mobileNo + '</td><td><input type="button" class="university-button" id="view" value="view" onclick="viewStudentSemester(' + data.studList[i].id + ')"/></td><td><input type="button" class="university-button" id="view" value="View" onclick="viewStudent(' + data.studList[i].id + ')"/><input type="button" class="university-button" id="updateStudentButton"  value="Update" onclick="updateStudent(' + data.studList[i].id + ')"/></td></tr>')

                        }
                    }
                }
//                $('#paginationDiv').after(data.studList[0].firstName)

                $('#jqPagination').empty().append('<a href="#" class="first" data-action="first">&laquo;</a><a href="#" class="previous" data-action="previous">&lsaquo;</a><input type="text" readonly="readonly"/><a href="#" class="next" data-action="next">&rsaquo;</a><a href="#" class="last" data-action="last">&rsaquo;</a>')
                $('#studentList tbody tr:not(:first)').hide();
                $table_rows = $('#studentList tbody tr');

                var table_row_limit = 20;

                var page_table = function (page) {
                    // calculate the offset and limit values
                    var offset = (page - 1) * table_row_limit,
                        limit = page * table_row_limit;

                    // hide all table rows
                    $table_rows.hide();

                    // show only the n rows
                    $table_rows.slice(offset, limit).show();

                }
                var pageNo = 0
                if ($table_rows.length % table_row_limit) {
                    pageNo = parseInt(parseInt($table_rows.length) / table_row_limit) + 1
                }
                else {
                    pageNo = parseInt($table_rows.length / table_row_limit)
                }
                $('.pagination').jqPagination({
                    max_page: pageNo,
                    paged: page_table
                });
                page_table(1);

            }
            else {
                document.getElementById("paginationDiv").style.visibility = "hidden";
                document.getElementById("studentList").style.visibility = "hidden";
                $('#msg').html("<div class='university-status-message'>No Students Found</div>")
            }

        }
    });
}


function viewStudentDetails(studentId) {
    var data = studentId
//    alert(data)
}
function viewStudent(studentId) {
    var data = studentId
    window.location.href = '/Symphonie/student/viewStudent?studentId=' + data;
}
function viewStudentSemester(studentId) {
    var data = studentId
    window.location.href = '/Symphonie/student/semesterDetails?studentID=' + data + '&view=view';
}
function deleteStudent(studentId) {
    var data = studentId
    window.location.href = '/Symphonie/student/deleteStudents?studentId=' + data;
}
function updateStudent(studentId) {
    var data = studentId
    window.location.href = '/Symphonie/student/registration?studentID=' + data + '&&update=update';
}
function clearError(t) {
    $(t).next("label").text("");

}
function updateFeeType(feeTypeId) {
//    alert(feeTypeId)
    var data = feeTypeId
    window.location.href = '/UniversityProject/programFee/addFeeType?feeTypeId=' + data;

}
function deleteFeeType(feeTypeId) {
    var result = confirm("Are you sure you want to delete this item?", "Confirm Delete");

    if (result == true) {
        var data = feeTypeId;
//        alert(result)
        $.ajax({
            type: "post",
            url: url('programFee', 'deleteFeesType', ''),
            data: {data: data},
            success: function (data) {
                //document.location.reload();
                window.location.href = '/UniversityProject/programFee/viewExistingFeeType';
            }
        });
    }
}
function loadBranch(t) {
    var bank = $(t).val();
    alert(bank)
    if (bank) {
        $.ajax({
            type: "post",
            url: url('admin', 'getBranchList', ''),
            data: {bank: bank},
            success: function (data) {
                //document.location.reload();
                $("#branchLocation").empty().append('');
                $("#branchLocation").append('<option value="">Select Branch</option>');
                for (var i = 0; i < data.length; i++) {
                    $("#branchLocation").append('<option value="' + data[i].id + '">' + data[i].branchLocation + '</option>')
                }
            }
        });
    }
}


function loadSession(t) {
    var program = $(t).val();
    if (program) {
        $.ajax({
            type: "post",
            url: url('programFee', 'getProgramSession', ''),
            data: {program: program},
            success: function (data) {
                //document.location.reload();
                $("#session").empty().append('<option value="">Choose Session</option>');
                for (var i = 0; i < data.length - 1; i++) {
                    $("#session").append('<option value="' + data[i].sessionOfProgram + '">' + data[i].sessionOfProgram + '</option>')
                }
            }
        });
    }
}

function approvePayInSlip() {
    $.ajax({
        type: "post",
        url: url('admin', 'saveApprovePayInSlip', ''),
        data: {rollNo: $('#rollNo').val(), bankId: 10, paymentModeId: 5, branchId: 21,
            paymentDate: $('#datePick').val(), paymentReferenceNumber: $('#payInSlipNo').val(), feeTypeId: 1},
        success: function (data) {
            if (data.flag) {
                $('#rollNo').val('');
                $('#payInSlipNo').val('');
                $('#datePick').val('');
                $('#approvePayInSlip')[0].reset();
                $('#statusMessage').html("Approved Succesfully")
            }
        }

    })
}

function submitProgramFee() {

    validateProgramFee()
    var status = $("#createNewFee").valid();
//    alert(status)
    if (status) {
        var programId = $("#programDetail").val()
//        alert(programId)

        $.ajax({
            type: "post",
            url: url('programFee', 'saveProgramFee', ''),
            data: $("#createNewFee").serialize() + "&feeTypeList=" + feeTypeList + "&programDetail=" + programId,

            success: function (data) {
                if (data.status) {
                    $('#createNewFee')[0].reset();
                    document.getElementById("statusMessage").style.visibility = "visible";
                    $('#statusMessage').html("Saved Successfully")
                }

            }

        })
    }

}

function updateProgramFee() {

    var programId = $("#programId").val()
    $.ajax({
        type: "post",
        url: url('programFee', 'saveProgramFee', ''),
        data: $("#updateFee").serialize() + "&feeTypeList=" + feeTypeList + "&programDetail=" + programId,

        success: function (data) {
            if (data.status) {
//                $('#updateFee').refresh()
//                document.location.reload()
//                $('#updateFee')[0].reset();
                document.getElementById("statusMessage").style.visibility = "visible";
                $('#statusMessage').html("Updated Successfully")
            }
        }

    })
    document.location.reload()
}


function generateChallanForRange() {
    var selectedStudentId = []
    var selectedSemester = []
    $('#rollNoError').html("")
    var from = $("#serialNoFrom").val()
    var to = $("#serialNoTo").val()
    if (from != undefined) {
        if (from.length == 0) {
            $('#rollNoError').html("Please Enter Range/Roll Number From Above List.")
            return false
        }
        var selectedRange = 0;
        if (parseInt(to) >= parseInt(from)) {
            selectedRange = (to - from)
        } else {
            $('#rollNoError').html("Please enter range correctly")
            return false
        }

        var rangeCount = parseInt(from) + selectedRange;
        var srNoCount = $('input[name="studentCheckbox"]').length;
        if (rangeCount > srNoCount) {
            $('#rollNoError').html("Please enter range correctly")
            return false
        }

        for (i = from - 1; i < rangeCount; i++)
            $('#studyCenterFeeEntryTable').find('#rowID' + i).find('input[type="checkbox"]').prop('checked', true)

        for (i = to; i < totalRows; i++)
            $('#studyCenterFeeEntryTable').find('#rowID' + i).find('input[type="checkbox"]').prop('checked', false)
        for (i = from - 2; i >= 0; i--)
            $('#studyCenterFeeEntryTable').find('#rowID' + i).find('input[type="checkbox"]').prop('checked', false)
//        selectedStudentId.clean()
        $('input[name="studentCheckbox"]:checked').each(function () {
            if (document.getElementById('allProgram').checked == true) {
                selectedSemester.push('1');
            }
            else {
                selectedSemester.push($('#semesterList').val());
            }
            selectedStudentId.push($(this).attr('id'));
        });
        $("#studentListId").val(selectedStudentId)
        $("#semesterListHidden").val(selectedSemester)

        if (selectedStudentId != null) {
            checkValidation()
            if ($("#challanForStudyCenter").valid()) {
                $("#challanForStudyCenter").submit()
            }
        }
    }
    else {

    }
}


function showStudents() {


    $.ajax({
        type: "post",
        url: url('admin', 'searchByChallanNo', ''),
        data: 'challanNo=' + $('#searchChallanNo').val(),

        success: function (data) {
//            alert(data[0].programDetail.id)
            $("#scStudnetList tbody").empty().append('')
            $("#scStudnetList tbody").append('<tr><th>Student name</th><th>Roll Number</th><th>Course Name</th><th>Amount</th></tr>')
            for (var i = 0; i < data.stuList.length; i++) {
                $("#scStudnetList tbody").append('<tr><td>' + data.stuList[i].firstName + ' &nbsp;' + data.stuList[i].lastName + '</td><td><input type="text" name="rollNo' + i + '" value="' + data.stuList[i].rollNo + '"</td><td>' + data.courseNameList[i] + '</td><td>' + data.courseFee[i] + '</td></tr>')
            }
        }

    })

}
function clearTable() {
    document.getElementById("scStudnetList").style.visibility = "hidden";
    document.getElementById("studentPayList").style.visibility = "hidden";//
    document.getElementById("paySubmit").style.visibility = "hidden";
    document.getElementById("payClear").style.visibility = "hidden";
}
function loadProgramTerm() {
    var data
    if ($('#programDetail').length > 0) {
        data = $('#programDetail').val();
    }
    else if ($('#programList').length > 0) {
        data = $('#programList').val();
    }
    if (data) {
        $.ajax({
            type: "post",
            url: url('programFee', 'getTermInList', ''),
            data: {data: data},
            success: function (data) {
                $("#semesterList").empty().append('<option value="">Select Terms</option>')

                for (var i = 1; i <= data.programlist; i++) {
                    $("#semesterList").append('<option value="' + i + '">' + i + '</option>')
                }
            }
        })
    }
    else {
        $("#semesterList").empty().append('data <option value="">Select Term</option>')
    }
}
function loadEditAdmissionFeeDetails(t) {
    var program = $('#programDetail').val();
    var cValue = $(t).val();
    var result = checkTerm(cValue)
    var session = $('#session').val();
    var term = $('#semesterList').val();
    if (result) {
        if (program != '' && session != '' && term != '0') {
            $.ajax({
                type: "post",
                url: url('programFee', 'getAdmissionFeeDetails', ''),
                data: {program: program, session: session, term: term},
                success: function (data) {
                    if (data) {
                        $('#feeAmountAtIDOL').val(data.idolFee);
                        $('#feeAmountAtSC').val(data.stFee);
                        $('#lateFeeAmount').val(data.lateFee);
                    }
                }
            })
        }
    }
    else {
        alert("This Term is Invalid!")
    }
}
function loadAdmissionFeeDetails() {
    $('#admissionFeeAmount').val(10000)
//    var program = $('#programDetail').val();
//    var session = $('#session').val();
//    var term = $('#semesterList').val();
//    if (program != '' && session != '' && term != '0') {
//        $.ajax({
//            type: "post",
//            url: url('programFee', 'getAdmissionFeeDetails', ''),
//            data: {program: program, session: session, term: term},
//            success: function (data) {
//                if (data) {
//                    $('#feeAmountAtIDOL').val(data.idolFee);
//                    $('#feeAmountAtSC').val(data.stFee);
//                    $('#lateFeeAmount').val(data.lateFee);
//                }
//            }
//        })
//    }
}
function checkTerm(pId) {
    var term = $('#semesterList').val();
    $.ajax({
        type: "post",
        url: url('programFee', 'isValidTerm', ''),
        data: {pId: pId, term: term},
        success: function (data) {
            return data.status
        }
    })
}
function showListOfStudents() {
    document.getElementById("paginationDiv").style.visibility = "hidden";
    $.ajax({
        type: "post",
        url: url('admin', 'searchListStudentByChallanNo', ''),
        data: 'challanNo=' + $('#searchChallanNo').val(),

        success: function (data) {
            $('#msgDiv').html("")
//            alert(data.stuList.length)
            if (data.stuList.length > 0) {
                document.getElementById("studentPayList").style.visibility = "visible";
                document.getElementById("scStudnetList").style.visibility = "visible";
//
                document.getElementById("paySubmit").style.visibility = "visible";
                document.getElementById("payClear").style.visibility = "visible";
                $("#scStudnetList thead").empty().append('')
                $("#scStudnetList thead").append('<tr><th>Student name</th><th>Roll Number</th><th>Term</th><th>Course Name</th><th>Amount</th></tr>')
                $("#scStudnetList tbody").empty().append('')
                for (var i = 0; i < data.stuList.length; i++) {
                    $("#scStudnetList tbody").append('<tr><td>' + data.stuList[i].firstName + ' &nbsp;' + data.stuList[i].lastName + '</td><td><input type="text" readonly name="rollNo' + i + '" value="' + data.stuList[i].rollNo + '"/></td><td><input type="text" readonly name="semester" value="' + data.semester[i] + '"/></td><td>' + data.courseNameList[i] + '</td><td>' + data.courseFee[i] + '</td></tr>')
                }
                document.getElementById("paginationDiv").style.visibility = "visible";
                $table_rows = $('#scStudnetList tbody tr');
                var table_row_limit = 10;
                var page_table = function (page) {
                    var offset = (page - 1) * table_row_limit,
                        limit = page * table_row_limit;
                    $table_rows.hide();
                    $table_rows.slice(offset, limit).show();
                }
                var pageNo = 0
                if ($table_rows.length % table_row_limit) {
                    pageNo = parseInt(parseInt($table_rows.length) / table_row_limit) + 1
                }
                else {
                    pageNo = parseInt($table_rows.length / table_row_limit)
                }
                $('.pagination').jqPagination({
                    max_page: pageNo,
                    paged: page_table
                });
                page_table(1);
            }
            else {
                document.getElementById("paginationDiv").style.visibility = "hidden";
                $('#msgDiv').html("Challan is already paid or Invalid Challan.")
            }
        }
    })
}

function showMiscFeeListOfStudents() {

    document.getElementById("paginationDiv").style.visibility = "hidden";
    $.ajax({
        type: "post",
        url: url('admin', 'searchMiscFeeListByChallanNo', ''),
        data: 'challanNo=' + $('#searchChallanNo').val(),

        success: function (data) {
            if (data.stuList.length > 0) {
                $('#errorMessage').text('')
                document.getElementById("studentPayList").style.visibility = "visible";
                document.getElementById("paySubmit").style.visibility = "visible";
                $("#scStudnetList thead").empty().append('')
                $("#scStudnetList tbody").empty().append('')
                $("#scStudnetList thead").append('<tr><th>Student name</th><th>Roll Number</th><th>Fee Type</th><th>Programme Name</th><th>Semester</th><th>Amount</th></tr>')
                for (var i = 0; i < data.stuList.length; i++) {
                    $("#scStudnetList tbody").append('<tr><td>' + data.stuList[i].firstName + ' &nbsp;' + data.stuList[i].lastName + '</td><td><input type="text" readonly="true" name="rollNo' + i + '" value="' + data.stuList[i].rollNo + '"</td><td>' + data.feeType[i] + '</td><td>' + data.courseNameList[i] + '</td><td>' + data.semList[i] + '</td><td>' + data.courseFee[i] + '</td></tr>')
                }
//                document.getElementById("paginationDiv").style.visibility = "visible";
                document.getElementById("scStudnetList").style.visibility = "visible";
//                $table_rows = $('#scStudnetList tbody tr');
//
//                var table_row_limit = 10;
//
//                var page_table = function (page) {
//
//                    // calculate the offset and limit values
//                    var offset = (page - 1) * table_row_limit,
//                        limit = page * table_row_limit;
//
//                    // hide all table rows
//                    $table_rows.hide();
//
//                    // show only the n rows
//                    $table_rows.slice(offset, limit).show();
//
//                }
//                var pageNo = 0
//                if ($table_rows.length % table_row_limit) {
//                    pageNo = parseInt(parseInt($table_rows.length) / table_row_limit) + 1
//                }
//                else {
//                    pageNo = parseInt($table_rows.length / table_row_limit)
//                }
////                alert(5%5)
//                $('.pagination').jqPagination({
//                    max_page: pageNo,
//                    paged: page_table
//                });
//                page_table(1);
            }
            else {
                $('#errorMessage').text("Challan is already paid or Invalid Challan.")
            }
        }

    })
}

function checkChallan(challan) {
    if (challan.length != 10) {
        return false
    }
}

function populateChallanDetail() {
    $('#statusMessage').html('');
    var challanNo = $("#payInSlipNo").val();
    if (challanNo.length == 10) {

        $.ajax({
            type: "post",
            url: url('admin', 'getChallanDetailsforStudent', ''),
            data: {challanNo: challanNo},

            success: function (data) {
                if (data.stuList.length > 0) {
//                console.log("error")
                    $("#allStudentList tbody").empty().append('<tr><th>Student name</th><th>Semester</th><th>Roll Number</th><th>Course Name</th><th>Fee Type</th><th>Ref. Number</th><th>Bank</th><th>Branch</th><th>Amount</th></tr>')
                    for (var i = 0; i < data.stuList.length; i++) {
                        if (!data.stuList[i].middleName) {
                            $("#allStudentList tbody").append('<tr><td><input type="text" name="studentListId" hidden="hidden" value="' + data.stuList[i].id + '"/> ' + data.stuList[i].firstName + '&nbsp;' + data.stuList[i].lastName + '</td><td>' + data.studyCentreList[i] + '</td><td>' + data.stuList[i].rollNo + '</td><td>' + data.courseNameList[i] + '</td><td>' + data.feeType[i] + '</td><td>' + data.paymentReferenceNumber[i] + '</td><td>' + data.bank[i] + '</td><td>' + data.branch[i] + '</td><td>' + data.courseFee[i] + '</td></tr>')
                        }
                        else {
                            $("#allStudentList tbody").append('<tr><td><input type="text" name="studentListId" hidden="hidden" value="' + data.stuList[i].id + '"/> ' + data.stuList[i].firstName + '&nbsp;' + data.stuList[i].middleName + '&nbsp;' + data.stuList[i].lastName + '</td><td>' + data.studyCentreList[i] + '</td><td>' + data.stuList[i].rollNo + '</td><td>' + data.courseNameList[i] + '</td><td>' + data.feeType[i] + '</td><td>' + data.paymentReferenceNumber[i] + '</td><td>' + data.bank[i] + '</td><td>' + data.branch[i] + '</td><td>' + data.courseFee[i] + '</td></tr>')
                        }
                    }
                    $("#allStudentList tbody").append('<tr><td colspan="9" style="text-align: center;"><input type="button" value="Approve" class="university-button university-size-1-4" onclick="submitStudents()" style="margin-top: 20px;"/> </td></tr>')
                    $("#error").hide()
                } else {
                    if (!data.rollStatus) {
                        $("#allStudentList tbody").empty().append('<tr><td class="university-status-message">PLease Generate Roll Number Before Approving Pay-In-Slip</td></tr>')
                    }
                    else {
                        $("#allStudentList tbody").empty().append('<tr><td class="university-status-message">Already Approved or Wrong Challan Number</td></tr>')
                    }
                    $("#error").hide()
//            }else{
////                alert(data.rollStatus)
//                if(!data.rollStatus){
//                    $("#allStudentList tbody").empty().append('<tr><td class="university-status-message">PLease Generate Roll Number Before Approving Pay-In-Slip</td></tr>')
//
                }
            }
        });
    } else {
        alert("Please enter 10 digit valid challan number")
        return false
    }
}
//Added By DIgvijay on 22 May 2014
function populateCourseDetail() {
    var courseId = $("#courseId").val();
//    alert("courseId--->"+courseId)

    $.ajax({
        type: "post",
        url: url('admin', 'updateCourses', ''),
        data: {courseId: courseId},

        success: function (data) {
//            alert("Inside ajax call.....")
            if (data.programDetail) {
                $("#allCourseList tbody").empty().append('<tr><th>Course Id</th><th>Course Code</th><th>Course Name</th></tr>')
                for (var i = 0; i < data.programDetail.length; i++) {
                    $("#allCourseList tbody").append('<tr><td><input type="text" name="courseListId" hidden="hidden" value="' + data.programDetail[i].courseId + '"/> ' + data.programDetail[i].courseCode + ' &nbsp;' + data.programDetail[i].courseName + '</td></tr>')
                }
                $("#allCourseList tbody").append('<tr><td><input type="button" value="Approve" onclick="submitStudents()"/> </td></tr>')
                $("#error").hide()
            } else {
                $("#error").show()
            }
        }
    });
}

function submitStudents() {
    $("#approvePayInSlip").submit()
}


$(document).ready(function () {
    $("#rollNoGenerationDate").ready(function () {
        $("#startD").datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: "dd/mm/yy",
            minDate: 0
        });
        $("#endD").datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: "dd/mm/yy",
            minDate: 0
        });
    })
});


function studentForStudyMaterial() {
    var result = $('#studyMaterialPage').valid()
    $.ajax({
        type: "post",
        url: url('admin', 'getStudentForStudyMaterial', ''),
        data: $("#studyMaterialPage").serialize(),
        success: function (data) {
            if (data.studentList) {
                $("#msgDiv").html(" ")
                $("#studentRecordDiv").empty().append("<table id='studentRecord' class='inner'><tbody></tbody></table>")
                $("#studentRecord tbody").empty().append('<tr><th>Student name</th><th>Roll Number</th><th>Course Name</th></tr>')
                for (var i = 0; i < data.studentList.length; i++) {
                    $("#studentRecord tbody").append('<tr style="border:  1px solid gainsboro"><td><input type="text" name="studentListId" hidden="hidden" value="' + data.studentList[i].id + '"/> ' + data.studentList[i].firstName + ' &nbsp;' + data.studentList[i].lastName + '</td><td>' + data.studentList[i].rollNo + '</td><td>' + data.courseDetail[0].courseName + '</td></tr>')
                    $("#studentRecord tbody").append('<tr><td> </td><td> </td><td> </td></tr>')
                    $("#studentRecord tbody").append('<tr><th colspan="3">Current Semester Courses</th></tr>')

                    for (var j = 0; j < data.subjectsList[i].length; j++) {

                        $("#studentRecord tbody").append('<tr><td><input type="checkbox" name="subjectCheckBox" id="' + data.subjectsList[i][j].id + '" value="' + data.subjectsList[i][j].id + '" /></td><td>' + data.subjectsList[i][j].subjectName + '</td></tr>')
                    }
                }

                $("#studentRecord tbody").append('<tr><td><input type="button" value="Assign Study Material" onclick="assignStudyMaterial()"/> </td></tr>')

                for (var k = 0; k < data.assignedStudyMaterail.length; k++) {
                    $("#" + data.assignedStudyMaterail[k].id).attr('checked', true)
                }
                $("#error").hide()
            } else {
                $("#error").show()
                $("#studentRecord tbody").empty()
            }
        }
    });


}


function assignStudyMaterial() {
    var subjectList = []
    if ($("input[name=subjectCheckBox]:checked").length != 0) {

        $.ajax({
            type: "post",
            url: url('admin', 'saveStudyMaterial', ''),
            data: $("#studyMaterialPage").serialize(),
            success: function (data) {
                if (data.status == 'true') {
                    $("#studentRecord tbody tr").remove()
                    $("#studyMaterialText").val('')
                    $("#msgDiv").html("Study Material has been assigned to student")
                }
                else {
                    alert("There is some problem in assigning Study Material")

                }

            }
        })

    }
    else {
        alert("Select the Subject first.");
        return false;
    }

}
function loadPayInSlipDetails(t) {
    var pMode = $(t).val()
    var challanNo = $('#searchChallanNo').val()
    if (pMode == '1') {
        $.ajax({
            type: "post",
            url: url('admin', 'loadPayInSlipDetail', ''),
            data: {pMode: pMode, challanNo: challanNo},
            success: function (data) {
                if (data.payDate) {
                    $('#datepicker').val(data.payDate)
                    $('#paymentReferenceNumber').val(data.refNo)
                    $('#bankNameForGU').empty().append('<option value="">Select Bank</option>')
                    for (var i = 0; i < data.bank.length; i++) {
                        $('#bankNameForGU').append('<option value="' + data.bank[i].id + '">' + data.bank[i].bankName + '</option>')
                    }
                    $('#branchLocationForGU').empty().append('<option value="' + data.branch + '">' + data.branchName + '</option>')
                    $('#datepicker').prop('readonly', false)
                    $('#paymentReferenceNumber').prop('readonly', true)
                    $('#bankName').prop('disabled', true)
                    $('#branchLocation').prop('hidden', true)
                    $('#bankName').prop('hidden', true)
                    $('#branchLocation').prop('disabled', true)
                    $('#bankNameForGU').prop('disabled', false)
                    $('#branchLocationForGU').prop('hidden', false)
                    $('#bankNameForGU').prop('hidden', false)
                    $('#branchLocationForGU').prop('disabled', false)
                }
                else {
                    $(t).val('')
                    $('#datepicker').prop('readonly', false)
                    $('#paymentReferenceNumber').prop('readonly', false)
                    $('#bankName').prop('readonly', false)
                    $('#branchLocation').prop('readonly', false)
                }
            }
        })
    }
    else {
        $('#datepicker').prop('readonly', false)
        $('#paymentReferenceNumber').prop('readonly', false)
        $('#bankName').prop('readonly', false)
        $('#branchLocation').prop('readonly', false)
        $('#datepicker').val('')
        $('#paymentReferenceNumber').val('')
        $('#bankName').val('')
        $('#branchLocation').val('')
        $('#bankName').prop('disabled', false)
        $('#branchLocation').prop('hidden', false)
        $('#bankName').prop('hidden', false)
        $('#branchLocation').prop('disabled', false)
        $('#bankNameForGU').prop('disabled', true)
        $('#branchLocationForGU').prop('hidden', true)
        $('#bankNameForGU').prop('hidden', true)
        $('#branchLocationForGU').prop('disabled', true)

    }
}

function checkFeeStatusForRollNo() {
    var rollNo = $('#rollNoForFeeStatus').val()
    if (rollNo.length == 8) {
        $('#errorLabel').html("")
        $.ajax({
            type: "post",
            url: url('feeDetails', 'checkRollNoFeeStatus', ''),
            data: {rollNo: rollNo},
            success: function (data) {
                if (!data.error) {
                    if (data.miscFeeList.length > 0) {
                        $('#showStatusForRollNo').empty().append('<table class="university-size-full-1-1 inner" id="statusTable"></table>')
                        $('#statusTable').append('<tr><th>Challan No</th><th>Fee Type</th><th>Term</th><th>Fee Paid Date</th><th>Status</th></tr>')
                        for (var i = 0; i < data.miscFeeList.length; i++) {
                            $('#statusTable').append('<tr><td>' + data.miscFeeList[i].challanNo + '</td><td>' + data.miscFeetype[i] + '</td><td>' + data.termValue[i] + '</td><td>' + data.mPayDate[i] + '</td><td>' + data.miscFeeStatus[i] + '</td></tr>')

                        }

                    }
                }
                else {
                    $('#showStatusForRollNo').empty()
                    $('#errorLabel').html(data.error)
                }

            }
        })
    }
    else {
        $('#errorLabel').html("Please Enter Correct Roll Number")
    }
}

function editUser(userId) {
    window.open('/UniversityProject/user/editUser/' + userId, '_self', false)
}
function resetPassword(userId) {
    window.open('/UniversityProject/user/resetPassword/' + userId, '_self', false)
}
function showDistrictsCityList(t) {
    var data = $(t).val()
    if (data) {
        $.ajax({
            type: "post",
            url: url('studyCenter', 'getCityList', ''),
            data: {data: data},
            success: function (data) {
                var count = 1
//                alert(data.length)
                $('#cityListTable tbody').empty()
                for (var i = 0; i < data.length; i++) {
                    $('#cityListTable tbody').append('<tr><td>' + count++ + '</td><td>' + data[i].cityName + '</td><td><input type="button" class="university-button" onclick="deleteCity(' + data[i].id + ')" value="Delete"/> <input type="button" class="university-button" onclick="editCity(' + data[i].id + ')" value="Edit"/> </td></tr>')
                }
            }
        })
    }
}
function showDistrictsExamCentreList(t) {
    var data = $(t).val()
    if (data) {
        $.ajax({
            type: "post",
            url: url('examinationCenter', 'getExamCenterName', ''),
            data: {data: data},
            success: function (data) {
                if (data.length != 0) {
                    var count = 1
//                alert(data.length)
                    $('#cityListTable tbody').empty()
                    for (var i = 0; i < data.length; i++) {
                        $('#cityListTable tbody').append('<tr><td>' + count++ + '</td><td>' + data[i].cityName + '</td><td><input type="button" class="university-button" onclick="deleteExamCentre(' + data[i].id + ')" value="Delete"/> <input type="button" class="university-button" onclick="editExamCentre(' + data[i].id + ')" value="Edit"/> </td></tr>')
                    }
                }
                else {
                    $('#cityListTable tbody').empty().append('<tr><td></td><td>No Examination Centre In This District</td><td></td></tr>')
                }
            }
        })
    }
}

function editCity(data) {
    window.open('/UniversityProject/examinationCenter/createNewCity/' + data, '_self', false)
}
function deleteCity(data) {
//    alert(data)
    $('#deleteCityId').val(data)
//    alert($('#deleteCityId').val())
    $('#deleteCityInst').submit()
}

function editExamCentre(data) {
    window.open('/UniversityProject/examinationCenter/createExamCentre/' + data, '_self', false)
}
function deleteExamCentre(data) {
//    alert(data)
    $('#deleteCityId').val(data)
//    alert($('#deleteCityId').val())
    $('#deleteCityInst').submit()
}
function searchStudentList() {
    var session = $('#session').val()
    var student = $('#searchStudent').val()
    if (session && student) {
        $.ajax({
            type: "post",
            url: url('admin', 'searchStudentList', ''),
            data: {student: student, session: session},
            success: function (data) {
                $('#studentListTable tbody').empty().append('')
                if (data.studentListByFName) {
                    document.getElementById("studentListTable").style.visibility = "visible";
                    for (var i = 0; i < data.studentListByFName.length; i++) {
                        if (data.studentListByFName[i].middleName != null) {
                            $('#studentListTable tbody').append('<tr style="line-height: 18px;"><td  style="line-height: 18px;">' + data.studentListByFName[i].firstName + ' ' + data.studentListByFName[i].middleName + ' ' + data.studentListByFName[i].lastName + '</td><td>' + data.studentListByFName[i].rollNo + '</td><td>' + data.courseOfFName[i] + '</td><td><input type="button" class="university-button" id="view" value="View" onclick="viewStudent(' + data.studentListByFName[i].id + ')"/></td><td><input type="button" class="university-button" id="view" value="View" onclick="viewStudentSemester(' + data.studentListByFName[i].id + ')"/></td></tr>')
                        }
                        else {
                            $('#studentListTable tbody').append('<tr style="line-height: 18px;"><td  style="line-height: 18px;">' + data.studentListByFName[i].firstName + ' ' + data.studentListByFName[i].lastName + '</td><td>' + data.studentListByFName[i].rollNo + '</td><td>' + data.courseOfFName[i] + '</td><td><input type="button" class="university-button" id="view" value="View" onclick="viewStudent(' + data.studentListByFName[i].id + ')"/></td><td><input type="button" class="university-button" id="view" value="View" onclick="viewStudentSemester(' + data.studentListByFName[i].id + ')"/></td></tr>')
                        }
                    }
                    document.getElementById("paginationDiv").style.visibility = "visible";
                    $table_rows = $('#studentListTable tbody tr');

                    var table_row_limit = 10;

                    var page_table = function (page) {
                        var offset = (page - 1) * table_row_limit,
                            limit = page * table_row_limit;
                        $table_rows.hide();
                        $table_rows.slice(offset, limit).show();

                    }
                    var pageNo = 0
                    if ($table_rows.length % table_row_limit) {
                        pageNo = parseInt(parseInt($table_rows.length) / table_row_limit) + 1
                    }
                    else {
                        pageNo = parseInt($table_rows.length / table_row_limit)
                    }
                    $('.pagination').jqPagination({
                        max_page: pageNo,
                        paged: page_table
                    });
                    page_table(1);
                }
            }
        });
    }
}


function fillFeeInfoUpdate(sessionOfFee) {

    $('#session option[value=' + sessionOfFee + ']').attr("selected", "selected");
    if ($("#session option:selected").text() == sessionOfFee) {
        $('#session option[value=' + sessionOfFee + ']').attr("selected", "selected");
    }
    else {
        $('#session').prepend('<option value="' + sessionOfFee + '">' + sessionOfFee + '</option>');
        $('#session option[value=' + sessionOfFee + ']').attr("selected", "selected");
    }

}


function searchByRollNumber() {
    if ($('#rollNumberInput').val().length != 0) {
        $.ajax({
            type: "post",
            url: url('feeDetails', 'searchDataByRollNumber', ''),
            data: 'rollNumber=' + $('#rollNumberInput').val(),
            success: function (data) {
                if (data.status == true && !(data.errMsg)) {
                    $("#semester").empty().append('<option value="">Select Semester</option>')
                    for (var i = 0; i < data.semesterList.length; i++) {
                        $("#semester").append('<option value="' + data.semesterList[i].id + '">' + data.semesterList[i].semesterNo + '</option>')
                    }
                    appendDetail(data)
                    $("#errorMsgForRollNo").html("")
                }
                else if (data.errMsg) {
                    alert(data.errMsg)
                }
                else {
                    //                alert("false")
                    $("#errorMsgForRollNo").html("No record Found")
                }
            }
        })
    }
}

function appendDetail(data) {
    $("#semester").attr('disabled', false)
    $("#postFeeType").attr('disabled', false)
//
//    $("#course").val(data['courseName'])
//    $("#semester option").remove();
//    $("#postFeeType option").remove();

    $("#postFeeType").empty().append('<option value="' + 0 + '">Select Fee Type</option>')

    for (var j = 0; j < data['feeType'].length; j++) {
        $("#postFeeType").append('<option value="' + data['feeType'][j].id + '">' + data['feeType'][j].type + '</option>')
    }
}

function checkPreviousRecord() {
    var val = $('#postFeeType').val()
    if (val > 0) {
        $.ajax({
            type: "post",
            url: url('feeDetails', 'checkRollNoPreviousData', ''),
            data: $("#postExamFee").serialize(),
            success: function (data) {
                alert(data.feePaid)
                if (data.feePaid == true) {
                    alert("Fees of current Semester/ Year is already paid")
                    $("#savePostFee").attr('disabled', true)
                }
                else if (data.feePaid == false) {
                    alert("Please pay previous Semester/ Year fees first")
                    $("#savePostFee").attr('disabled', true)

                }
                else {
                    $("#savePostFee").attr('disabled', false)
                }
            }
        })
    }
}

function loadTermByFeeType(t) {
    var feeType = $(t).val()
    var rollNo = $('#rollNumberInput').val()
    $.ajax({
        type: "post",
        url: url('feeDetails', 'getTermForFeeType', ''),
        data: {feeType: feeType, rollNo: rollNo},
        success: function (data) {
            if (data) {
                $("#semester").attr('disabled', false)
                $('#semester').empty().append("<option value=''>Select Term</option> ")
                for (var i = 1; i <= data.term; i++) {
                    $('#semester').append("<option value='" + i + "'>" + i + "</option> ")
                }

            }
            else {
                $("#semester").attr('disabled', true)
            }
        }
    })
}

function clearSelectBox() {

    $('#semester').val('')

}

function saveCustomChallan() {
    validateProgramFee()
    var result = $('#customChallanSave').valid()
    if (result) {
        $.ajax({
            type: "post",
            url: url('student', 'customChallanSave', ''),
            data: $("#customChallanSave").serialize(),
            success: function (data) {
                document.getElementById("customChallanSave").reset();
                $('#cName').text('' + data.name)
                $('#challanNo').text('' + data.challanNo)
                $('#feeType').text('' + data.feeType)
                $('#feeAmount').text('' + data.feeAmount)
                $('#feeInWord').text('(' + inFullWords(data.feeAmount) + " only)")
                $('#challanDiv').dialog('open')
            }
        })
    }
}


function subjectDialog() {
    $('#groupDialog').dialog('open');
}


function checkSubjectCode() {

    var data = $('#subjectCode').val();
    $.ajax({
        type: "post",
        url: url('course', 'checkSubjectCode', ''),
        data: {subjectCode: data},
        success: function (data) {
            if (data.subjectCode == "true") {
                $('#errorMsg').text("Subject Code is already registered")
                $('#errorMsg').attr('display', true)
                return false
            }
            else {
                $('#errorMsg').text("")
                return true
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

function checkAliasCode() {

    var data = $('#aliasCode').val();
    $.ajax({
        type: "post",
        url: url('course', 'checkAliasCode', ''),
        data: {aliasCode: data},
        success: function (data) {
            if (data.aliasCode == "true") {
                $('#errorMsg1').text("AliasCode Code is already registered")
                $('#errorMsg1').attr('display', true)
                return false
            }
            else {
                $('#errorMsg1').text("")
                return true
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

function fillCourseInfoUpdate(sessionOfCourse) {

    $('#sessionOfCourse option[value=' + sessionOfCourse + ']').attr("selected", "selected");

    if ($("#sessionOfCourse option:selected").text() == sessionOfCourse) {
        $('#sessionOfCourse option[value=' + sessionOfCourse + ']').attr("selected", "selected");
    }
    else {
        $('#sessionOfCourse').prepend('<option value="' + sessionOfCourse + '">' + sessionOfCourse + '</option>');
        $('#sessionOfCourse option[value=' + sessionOfCourse + ']').attr("selected", "selected");
        $("#sessionOfCourse option[value='0']").remove();
    }

}

function loadAdmissionFeeDetailsView(t) {
    var session = $(t).val()
    var program = $('#programDetail').val()
    if (program) {
        $.ajax({
            type: "post",
            url: url('programFee', 'loadAdmissionFee', ''),
            data: {session: session, program: program},
            success: function (data) {
                if (data.admissionFeeList.length > 0) {
                    $('#admissionFeeTable thead').empty().append('<tr><th>Term</th><th>Fee Amount</th><th>Late Fee</th><th></th></tr>')
                    $('#admissionFeeTable tbody').empty()
                    for (var i = 0; i < data.admissionFeeList.length; i++) {
                        $('#admissionFeeTable tbody').append('<tr><td>' + data.admissionFeeList[i].term + '</td><td>' + data.admissionFeeList[i].feeAmount + '</td><td>' + data.admissionFeeList[i].lateFeeAmount + '</td><td style="text-align: center;"><input type="button" class="ui-button" value="Edit" onclick="editAdmissionFee(' + data.admissionFeeList[i].id + ')" </td></tr>')
                    }
                }
            }
        })
    }

}
function editAdmissionFee(admissioFeeId) {
    window.open('/UniversityProject/programFee/createAdmissionFee/' + admissioFeeId, '_self', false)
}
function clearAllFields(t) {
    var program = $(t).val()
    if (program) {
        $('#session').val('')
        $("#session").attr('disabled', false)
        $('#admissionFeeTable thead').empty()
        $('#admissionFeeTable tbody').empty()
    }
    else {
        $('#session').val('')
        $("#session").attr('disabled', true)
        $('#admissionFeeTable thead').empty()
        $('#admissionFeeTable tbody').empty()
    }

}
function generateSingleAdmitCard() {
    var roll = $('#rollNoForFeeStatus').val()
    var term = $('#semesterList').val()
    var examFee = $('#feeExempt').is(':checked')
    $.ajax({
        type: "post",
        url: url('admitCard', 'generateSingleAdmitCard', ''),
        data: {roll: roll, term: term, examFee: examFee},
        success: function (data) {

        }
    })
}

//<<<<<<< HEAD
//=======
function showCustomChallanByDate() {
    var challanDate = $('#customChallanDate').val()
    $.ajax({
        type: "post",
        url: url('feeDetails', 'loadCustomChallanByDate', ''),
        data: {challanDate: challanDate},
        success: function (data) {
            if (data.challanList) {
                $('#showCustomChallanList thead').empty().append('<tr><th>Challan No</th><th>Amount</th><th>Challan Name</th><th>Type of Fee</th></tr>')
                $('#showCustomChallanList tbody').empty()
                for (var i = 0; i < data.listSize; i++) {
                    $('#showCustomChallanList tbody').append('<tr><td>' + data.challanList[i].challanNo + '</td><td>' + data.challanList[i].amount + '</td><td>' + data.challanList[i].challanName + '</td><td>' + data.challanList[i].typeOfFee + '</td></tr>')
                }
                document.getElementById("paginationDiv").style.visibility = "visible";
                $table_rows = $('#showCustomChallanList tbody tr');

                var table_row_limit = 10;

                var page_table = function (page) {

                    // calculate the offset and limit values
                    var offset = (page - 1) * table_row_limit,
                        limit = page * table_row_limit;

                    // hide all table rows
                    $table_rows.hide();

                    // show only the n rows
                    $table_rows.slice(offset, limit).show();

                }
                var pageNo = 0
                if ($table_rows.length % table_row_limit) {
                    pageNo = parseInt(parseInt($table_rows.length) / table_row_limit) + 1
                }
                else {
                    pageNo = parseInt($table_rows.length / table_row_limit)
                }
//                alert(5%5)
                $('.pagination').jqPagination({
                    max_page: pageNo,
                    paged: page_table
                });
                page_table(1);
            }
            else {
                $('#errorMessage').html(data.status)
                $('#showCustomChallanList thead').empty()
                $('#showCustomChallanList tbody').empty()
                document.getElementById("paginationDiv").style.visibility = "hidden";
            }

        }
    })
}
function checkSelfEnrollmentStatus() {
//    alert("fgfgfgfgfg")
    $.ajax({
        type: "post",
        url: url('home', 'selfEnrollStatus', ''),
        data: {data: 'data'},
        success: function (data) {
            if (data) {
                $('#showCounts thead').empty().append('<tr><td colspan="2"><h3>SELF ENROLLMENT STUDENT COUNT</h3></td></tr>')
                $('#showCounts tbody').empty().append('<tr><td class="university-size-1-2" style="text-align: center; font-size: 13px; font-weight: bold;">Total Number of Student: ' + data.selfEnrollmentCount + '</td><td class="university-size-1-2" style="text-align: center;font-size: 13px; font-weight: bold;">Total Number of Student without Roll No: ' + data.rollNoNotCount + '</td></tr>')
                $('#showStudyCenterCount thead').empty().append('<tr><th class="university-size-2-3">Program Name</th><th class="university-size-1-3">Total Student</th></tr>')
                $('#showStudyCenterCount tbody').empty()
                for (var i = 0; i < data.programListSize; i++) {
                    $('#showStudyCenterCount tbody').append('<tr><td>' + data.programName[i] + '</td><td>' + data.programCount[i] + '</td></tr>')
                }
            }
        }
    })
}
function checkFeeStatus() {
    $.ajax({
        type: "post",
        url: url('home', 'feeChallanStatus', ''),
        data: {data: 'data'},
        success: function (data) {
            if (data) {
                $('#showCounts thead').empty().append('<tr><td colspan="3"><h3>FEE CHALLAN STATUS COUNT</h3></td></tr>')
                $('#showCounts tbody').empty()
                $('#showStudyCenterCount thead').empty().append('<tr><th class="university-size-1-2">Study Centre Name</th><th class="university-size-1-4">Approved </th><th class="university-size-1-4">Unapproved</th></tr>')
                $('#showStudyCenterCount tbody').empty()
                for (var i = 0; i < data.studyCentreCount; i++) {
                    $('#showStudyCenterCount tbody').append('<tr><td>' + data.studyCentreName[i] + '</td><td>' + data.studyCentreFeeAppCount[i] + '</td><td>' + data.studyCentreFeeUnAppCount[i] + '</td></tr>')
                }
            }
        }
    })
}
function checkStudyCentreStatus() {
    $.ajax({
        type: "post",
        url: url('home', 'studyCentreStudentStatus', ''),
        data: {data: 'data'},
        success: function (data) {
            if (data) {
                $('#showCounts thead').empty().append('<tr><td colspan="3"><h3>STUDY CENTRE WISE STUDENT COUNT</h3></td></tr>')
                $('#showCounts tbody').empty()
                $('#showStudyCenterCount thead').empty().append('<tr><th class="university-size-1-2">Study Centre Name</th><th class="university-size-1-4">Approved </th><th class="university-size-1-4">Unapproved</th></tr>')
                $('#showStudyCenterCount tbody').empty()
                for (var i = 0; i < data.studyCentreCount; i++) {
                    $('#showStudyCenterCount tbody').append('<tr><td>' + data.studyCentreName[i] + '</td><td>' + data.studyCentreAppCount[i] + '</td><td>' + data.studyCentreUnAppCount[i] + '</td></tr>')
                }
            }
        }
    })
}
//>>>>>>> origin/damyant
function loadAllArchiveNotice() {
    $('#loadArchiveNoticeForm').submit()
}
function searchEquipment() {
    var searchText = $('#search').val()
    var searchType = $('input[name=searchBy]:checked').val()
//    alert(searchType)
    if (searchType != '' && searchText != '') {
        $.ajax({
            type: "post",
            url: url('admin', 'searchEquipmentList', ''),
            data: {searchText: searchText, searchType: searchType},
            success: function (data) {
                if (data.length > 0) {
                    var count = 1;
//                    alert(data[0].id)
                    $("#equipmentTable tbody").empty()
                    $('#errorMsg').text(" ")
                    for (var i = 0; i < data.length; i++) {
                        $("#equipmentTable tbody").append('<tr><td>' + data[i].equipmentName + '</td><td>' + data[i].equipmentId + '</td><td>' + data[i].manufacturer + '</td><td>' + data[i].equipmentType + '</td><td><a href="#" onclick="eqipmentView(' + data[i].id + ')"><input type="button" class="university-button" style="text-decoration: none;" value="View"/></a> </td><td><a href="#" onclick="eqipmentUpdate(' + data[i].id + ')"><input type="button" class="university-button" style="text-decoration: none" value="Update"/></a> </td><td><a href="#" onclick="confirmBeforeDelete(' + data[i].id + ')"><input type="button" class="university-button" style="text-decoration: none" value="Delete"/></a></td></tr>')
                    }

                } else {
                    $("#bookList tr").remove()
                    $('#errorMsg').text("No Record Found")
                }
            }
        })
    }
}
http://localhost:9098/UniversityProject/equipment/viewEquipmentDetail/1
    function eqipmentView(catId) {
        window.open('/UniversityProject/equipment/viewEquipmentDetail/' + catId, '_self', false)
    }
function eqipmentUpdate(catId) {
    window.open('/UniversityProject/equipment/createEquipment/' + catId, '_self', false)
}

function unapproveStudent(t) {
    var session = $('#programSession').val()
    var programId = $('#programId').val()
    var semester = $(t).val()

    $.ajax({
        type: "post",
        url: url('admin', 'getStudStatus', ''),
        data: {programId: programId, session: session, semester: semester},
        success: function (data) {
            if (data.length > 0) {
                $('#studentSubLIst thead').empty().append('<tr><td style="text-align: center" colspan="5"><h4>Unapproved Students</h4></td></tr>')
                $('#studentSubLIst thead').append('<tr><th style="width: 5%;"></th><th>Serial NO</th><th>Roll No</th> <th> Name </th><th>View Details</th></tr>')
                $('#studentSubLIst tbody').empty()
                for (var i = 0; i < data.length; i++) {
                    $('#studentSubLIst tbody').append('<tr><td><label><input type="checkbox" id="checkbox' + (i + 1) + '" value="' + data[i].id + '"></label></td><td>' + (i + 1) + '</td><td>' + data[i].rollNo + '</td><td>' + data[i].firstName + ' ' + data[i].lastName + '</td><td><input type="button" value="view Subjects" class="university-button" onclick="viewSubjectDetails(' + data[i].id + ')"/></td></tr>')
                }
                $table_rows = $('#studentSubLIst tbody tr');
//                alert($table_rows.length)
                var table_row_limit = 10;

                var page_table = function (page) {

                    // calculate the offset and limit values
                    var offset = (page - 1) * table_row_limit,
                        limit = page * table_row_limit;

                    // hide all table rows
                    $table_rows.hide();

                    // show only the n rows
                    $table_rows.slice(offset, limit).show();

                }
                var pageNo = 0
                if ($table_rows.length % table_row_limit) {
                    pageNo = parseInt(parseInt($table_rows.length) / table_row_limit) + 1
                }
                else {
                    pageNo = parseInt($table_rows.length / table_row_limit)
                }
                $('.pagination').jqPagination({
                    max_page: pageNo,
                    paged: page_table
                });
                page_table(1);
                document.getElementById("paginationDiv").style.visibility = "visible";
                $('#studentListPrint').prop('hidden', false)
                $('#checkCheckBoxes').prop('disabled', false)
                document.getElementById("applicationStatus").style.visibility = "visible"
            }
            else {
                $('#studentSubLIst thead').empty().append('<tr><td class="university-status-message" style="text-align: center;"> No Unapproved Stdent Available</td></tr>')

                $('#studentSubLIst tbody').empty()
                document.getElementById("paginationDiv").style.visibility = "hidden";
                $('#studentListPrint').prop('hidden', true)
                $('#studentListPrint').prop('disabled', true)
                document.getElementById("applicationStatus").style.visibility = "hidden"
            }
        }
    })
    ;


}
function loadProgramSession(t) {
    var progId = $(t).val()

    $.ajax({
        type: "post",
        url: url('admin', 'loadProgramSessions', ''),
        data: {programId: progId},
        success: function (data) {
            $('#programSession').empty().append('<option value="">Select Program Session</option>')
            for (var i = 0; i < data.id.length; i++) {
                $('#programSession').append('<option value="' + data.id[i] + '">' + data.session[i] + '</option>')
            }

        }
    })
}
function loadProgramSemester(t) {
    var progSession = $(t).val()

    $.ajax({
        type: "post",
        url: url('admin', 'loadProgramSemester', ''),
        data: {progSession: progSession},
        success: function (data) {
            $('#programSemester').empty().append('<option value="">Select Semester</option>')
            for (var i = 0; i < data.semId.length; i++) {
                $('#programSemester').append('<option value="' + data.semId[i] + '">' + data.semNo[i] + '</option>')
            }

        }
    })
}

function getSubjectsSemesterWise(t) {
    var sem = $(t).val()

    if (sem) {

        $('#submit').prop('disabled', false)

        $.ajax({
            type: "post",
            url: url('student', 'getSemesterWiseSubjects', ''),
            data: {semesterID: sem, progragramSessionId: $('#progragramSessionId').val(), studentID: $('#studentID').val()},
            success: function (data) {

                if (parseInt(data.subList.length) > 0) {
                    $('.subjectBox').css('visibility', 'visible')

                    $('#subjectLst').empty().append('<table class="inner">')

                    for (var i = 0; i < data.subList.length; i++) {
                        $('#subjectLst').append('<tr><td><label style="width: 100%;margin-left: -12px;"><input type="checkbox" name="subjects" id="subject' + data.subList[i].id + '"  value="' + data.subList[i].id + '"/>' + data.subList[i].subjectName + '</label></td></tr>')

                    }
                    if (data.approvedList) {
                        for (var i = 0; i < data.approvedList.length; i++) {
                            $('#subject' + data.approvedList[i].id).attr("checked", "true")
                            $('#subject' + data.approvedList[i].id).attr("readonly", "readonly")
                            $('#submit').attr("disabled", "true")

                        }
                    }
                    if (data.unapprovedList) {
                        for (var i = 0; i < data.unapprovedList.length; i++) {
                            $('#subject' + data.unapprovedList[i].id).prop("checked", "true")
                        }
                    }

                    $('#subjectLst').append('</table>')

                }

                if (parseInt(data.backSubList.length) > 0) {

                    $('.backSubjectBox').css('visibility', 'visible')
                    $('#backSubjectLst').empty().append('<table class="inner">')

                    $('#backSubjectLst').append('<tr><th  align="center">Back Papers</th></tr>')

                    for (var i = 0; i < data.backSubList.length; i++) {

                        $('#backSubjectLst').append('<tr><td><label style="width: 100%;margin-left: -12px;"><input type="checkbox" name="backSubjects" id="backSubjects' + data.backSubList[i].id + '" value="' + data.backSubList[i].id + '"/>' + data.backSubList[i].subjectName + '</label></td></tr>')
                    }
                    if (data.approvedListBack) {
                        for (var i = 0; i < data.approvedListBack.length; i++) {
                            $('#backSubjects' + data.approvedListBack[i].id).prop("checked", "true")
                            $('#backSubjects' + data.approvedListBack[i].id).prop("readonly", "true")
                            $('#submit').attr("disabled", "true")
                        }
                    }
                    if (data.unapprovedListBack) {

                        for (var i = 0; i < data.unapprovedListBack.length; i++) {
                            $('#backSubjects' + data.unapprovedListBack[i].id).prop("checked", "true")
                        }
                    }


                    $('#backSubjectLst').append('</table>')

                }
            }
        })
    }
    else {
        $('.subjectBox').empty()
        $('#submit').prop('disabled', true)
        $('.subjectBox').css('visibility', 'hidden')
    }

}
function viewSubjectDetails(studId) {
    var session = $('#programSession').val()
    var programId = $('#programId').val()
    var semester = $('#programSemester').val()
    $.ajax({
        type: "post",
        url: url('admin', 'viewStudentSubjectDetails', ''),
        data: {session: session, semester: semester, studId: studId},
        success: function (data) {
            if (data) {
                var view = ''
                for (var i = 0; i < data.length; i++) {
                    view = view + "<p>" + (i + 1) + ".  " + data[i].subjectName + "</p>"
                }

                $("<div></div>").html("<div style='text-align: justify;font-size: 12px;'>" + view + "</div>").dialog({
                    title: "Selected Subjects",
                    resizable: true,
                    width: 450,
                    height: 200,
                    modal: true,
                    buttons: {
                        "Ok": function () {
                            $(this).dialog("close");
                        }
                    }
                });
            }
        }
    })
}
$(document).ready(function () {
    $(document).on('change', "input[name='studentTo']", function () {
        if ($("#studentFrom").val().length == 0) {
            alert("Please enter range correctly")
            return false
        } else {
            selectRows();
        }

    });
    $(document).on('change', "input[name='studentFrom']", function () {

        if ($("#studentTo").val().length == 0 && $("#studentTo").val() == "") {
            return false
        } else {
            selectRows();
        }

    });
})
function selectRows() {
    selected = []
    $('input[type=checkbox]:checked').each(function () {
        $(this).prop('checked', false)
    });
    var from = $("#studentFrom").val()

    var to = $("#studentTo").val()

    if (from.length == 0) {
        alert("Please Enter from Sr No.")
    }
    var selectedRange = 0;
    if (to >= from) {
        selectedRange = (to - from)
    } else {
        alert("Please enter range correctly")
        return false
    }

    var rangeCount = parseInt(from) + selectedRange;
    for (i = from - 1; i < rangeCount; i++)
        $('#checkbox' + (i + 1) + '').prop('checked', true)

    for (i = to; i < totalRows; i++)
        $('#checkbox' + (i + 1) + '').prop('checked', false)
    for (i = from - 2; i >= 0; i--)
        $('#checkbox' + (i + 1) + '').prop('checked', false)
    $('input[type=checkbox]:checked').each(function () {
        selected.push($(this).val());
    });
    $(selectedStudents).val(selected)
}
function checkCheckBoxes() {
    selected = []
    if ($("#studentFrom").val().length == 0) {
        $('input[type=checkbox]:checked').each(function () {
            selected.push($(this).val());
        });
        $(selectedStudents).val(selected)
    }
    $('#approveStudent').submit()
}
function enableDuplicateButton() {
    $('#submit').prop('disabled', false)
}

function validEmpCode() {
    var code = $('#employeeCode').val()

    $.ajax({
        type: "post",
        url: url('employe', 'validateCode', ''),
        data: {code: code},
        success: function (data) {

            if (data.temp == 1) {
                alertPopup("Employee Code Already Exist")
                $('#employeeCode').val('')
            }
            else {

            }
        }


    })
}

function validEquipCode() {
    var code = $('#equipmentId').val()

    $.ajax({
        type: "post",
        url: url('equipment', 'validateCode', ''),
        data: {code: code},
        success: function (data) {

            if (data.temp == 1) {
                alertPopup("Equipment Code Already Exist")
                $('#equipmentId').val('')
            }
            else {

            }
        }


    })
}


function confirmMatchPassword() {
    var theValue = $('#cpwd').val()
    var myValue = $('#pwd').val()
    if (theValue.length > 0) {
        if (theValue != myValue) {
            message("New password & Confirm password not Matched !");
            $('#pwd').focus();
            $('#saveChangePassword').prop('disabled', true)
        }
        else {
            $('#saveChangePassword').prop('disabled', false)
        }
    }
}
function confirmMatchPassword1() {
    var theValue = $('#pwd').val()
    var myValue = $('#cpwd').val()
    if (theValue.length > 0) {
        if (theValue != myValue) {
            message("New password & Confirm password not Matched !");
            $('#cpwd').focus();
            $('#saveChangePassword').attr('disabled', true)
        }
        else {
            $('#saveChangePassword').attr('disabled', false)
        }
    }
}
function message(data) {
    var dialog = $("<div></div>").html("<div style='text-align: justify;font-size: 12px;'><p>" + data + ".</p></div>").dialog({
        title: "Alert!",
        resizable: false,
        modal: true,
        buttons: {
            "Ok": function () {
                $(this).dialog("close");
            }
        }
    });
    setTimeout(function () {
        dialog.dialog('destroy');
    }, 9000);
}
function checkFormat(t) {
    var tex = $(t).val()
    if (tex.match(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[A-Za-z0-9!@#$%^&*()_]{8,16}$/i)) {
        $('#submitButton').attr('disabled', false)
    }
    else {
        message("Passwords must contain 1 number, 1 lowercase, 1 uppercase character (length 8-16)");
        $('#submitButton').attr('disabled', true)
    }

}
function loadSemesterForProgram(t) {
    var data = $(t).val();
    if (data) {
        $.ajax({
            type: "post",
            url: url('admin', 'loadSemesterForProgram', ''),
            data: {data: data},
            success: function (data) {
                $("#semesterList").empty().append('data <option value="">Select Semester</option>')

                for (var i = 0; i < data.length; i++) {
                    $("#semesterList").append('<option value="' + data[i].id + '">' + data[i].semesterNo + '</option>')
                }
            }
        })
    }
}
function checkValidation() {
    var designation = $("input[name='designation']:visible:first").val()
    var joiningDate = $("input[name='joiningDate']:visible:first").val()
    var degree = $("input[name='degree']:eq(2)").val()
    var university = $("input[name='university']:eq(2)").val()
    var subject = $("input[name='subject']:eq(2)").val()
    var year = $("input[name='year']:eq(2)").val()
    validate()
    $('#employe1').valid()
    if (designation != '' && joiningDate != '' && degree != '' && university != '' && subject != '' && year != '') {
        if ($('#employe1').valid()) {
            $('#employe1').submit()
        }
    }
    else {
        if (designation == '') {
            $("input[name='designation']:visible:first").addClass('error')
        }
        if (joiningDate == '') {
            $("input[name='joiningDate']:visible:first").addClass('error')
        }
        if (degree == '') {
            $("input[name='degree']:eq(2)").addClass('error')
        }
        if (university == '') {
            $("input[name='university']:eq(2)").addClass('error')
        }
        if (subject == '') {
            $("input[name='subject']:eq(2)").addClass('error')
        }
        if (year == '') {
            $("input[name='year']:eq(2)").addClass('error')
        }
    }

}
function enableEdit() {
    $('#guardianName').attr('readonly', false)
    $('#email').attr('readonly', false)
    $('#permanentAddressDistrict').attr('disabled', false)
    $(".viewAddress").css("display", "none");
    $(".editAddress").css("display", "");
}
function enableHostel() {
    if ($('#stayInHostelYes').is(":checked")) {
        $('#hostelName').attr('disabled', false)
        $('#hostelRoomNo').attr('disabled', false)
    }
    if ($('#stayInHostelNo').is(":checked")) {
        $('#hostelName').attr('disabled', true)
        $('#hostelRoomNo').attr('disabled', true)
    }
}
function loadFacultyAdvisorAndSemester(t) {
    var program = $(t).val()
    if (program) {
        $.ajax({
            type: "post",
            url: url('admin', 'loadFacultyAdvisor', ''),
            data: {program: program},
            success: function (data) {
                $("#semesterTable").empty().append('')
                $("#facultyTable").empty().append('')

                for (var i = 0; i < data.semList.length; i = i + 2) {

                    if ((i + 1 <= (data.semList.length - 1))) {
                        $("#semesterTable").append('<tr><td><label><input type="checkbox" name="semester" value="' + data.semList[i].id + '"/> Semester - ' + data.semList[i].semesterNo + ' </label></td><td><label><input type="checkbox" name="semester" value="' + data.semList[i + 1].id + '"/> Semester - ' + data.semList[i + 1].semesterNo + ' </label></td></tr>')
                    }
                    else {
                        $("#semesterTable").append('<tr><td><label><input type="checkbox" name="semester" value="' + data.semList[i].id + '"/> Semester - ' + data.semList[i].semesterNo + ' </label></td><td></td></tr>')
                    }

                }
                for (var j = 0; j < data.userIdList.length; j = j + 2) {
                    if (j + 1 <= (data.userIdList.length - 1)) {
                        $("#facultyTable").append('<tr><td><label><input type="checkbox" name="facultyAdvisor" value="' + data.userIdList[j] + '"/> ' + data.employeeNameList[j] + ' </label></td><td><label><input type="checkbox" name="facultyAdvisor" value="' + data.userIdList[j + 1] + '"/> ' + data.employeeNameList[j + 1] + ' </label></td></tr>')
                    }
                    else {
                        $("#facultyTable").append('<tr><td><label><input type="checkbox" name="facultyAdvisor" value="' + data.userIdList[j] + '"/> ' + data.employeeNameList[j] + ' </label></td><td></td></tr>')
                    }
                }
            }
        })
    }
}
function viewCurrentSemDetailsByRollNo() {
    validateProgramFee()
    var rollNo = $("#StudentRollNo").val()
//    alert($('#individualStudentUpdate').valid())
    if ($('#individualStudentUpdate').valid()) {
        if (rollNo.length >= 8) {
            $.ajax({
                type: "post",
                url: url('admin', 'loadCurrentSemDetails', ''),
                data: {rollNo: rollNo},
                success: function (data) {
                    if (data.feeRNo.length > 0) {
                        document.getElementById("feeDetailsTable").style.visibility = "visible";
                        document.getElementById("submitButton").style.visibility = "visible";
                        $('#feeDetailsTable tbody').empty()
                        for (var i = 0; i < data.feeRNo.length; i++) {
                            $('#feeDetailsTable tbody').append('<tr><td>' + data.feeAcademicSession[i] + '</td><td>' + data.feeSem[i] + '</td><td>' + data.feeStatus[i] + '</td><td>' + data.feeRNo[i] + '</td><td>' + data.feeAmount[i] + '</td><td>' + data.feeDate[i] + '</td></tr>')
                        }
                    }
                    else {
                        document.getElementById("feeDetailsTable").style.visibility = "hidden";
                        document.getElementById("submitButton").style.visibility = "hidden";
                    }
                    if (data.subjectName.length > 0) {
                        document.getElementById("subjectDetailsTable").style.visibility = "visible";
                        $('#subjectDetailsTable tbody').empty()
                        for (var i = 0; i < data.subjectName.length; i++) {
                            $('#subjectDetailsTable tbody').append('<tr><td>' + data.subjectName[i] + '</td><td>' + data.semesterNo[i] + '</td><td>' + data.status[i] + '</td></tr>')
                        }
                    }
                    else {
                        document.getElementById("subjectDetailsTable").style.visibility = "hidden";
                    }


                }
            })

        }
        else {
            alert("Please Correct roll number!")
        }
    }

}
function enableFeeEdit() {
    $("input[name='guReceiptNo']").each(function () {
        $(this).attr('readonly', false)
    });
    $("input[name='guReceiptAmount']").each(function () {
        $(this).attr('readonly', false)
    });
    $("input[name='guReceiptDate']").each(function () {
        $(this).attr('readonly', false)
    });
}
function loadSubjectDetails() {
    var courseCode = $('#subjectCode').val()
    $('#loadError').text('')
    $('#SubjectName').val('')
    $('#theory').val('')
    $('#practical').val('')
    $('#ltpc').empty()
    if (courseCode.length > 4) {
        $.ajax({
            type: "post",
            url: url('postExamination', 'loadCourseDetailsFromCode', ''),
            data: {courseCode: courseCode},
            success: function (data) {
                if (data.courseInst) {
                    $('#SubjectName').val(data.courseInst.subjectName)
                    $('#theory').val(data.courseInst.lecture + data.courseInst.tutorial)
                    $('#practical').val(data.courseInst.practical)
                    $('#ltpc').empty().append('<tr><td>L : ' + data.courseInst.lecture + ' </td><td>T : ' + data.courseInst.tutorial + ' </td><td>P : ' + data.courseInst.practical + ' </td><td>C : ' + data.courseInst.creditPoints + ' </td></tr>')
                    if ($('#academicSession').length > 0) {
                        $('#academicSession').prop('disabled', false)
                        $('#academicSession').empty().append('<option>Select Academic Session</option>')
                        for (var i = 0; i < data.academicSessionList.length; i++) {
                            $('#academicSession').append('<option value="' + data.academicSessionList[i] + '">' + data.academicSessionList[i] + '</option>')
                        }
                    }
                }
                else {
                    $('#loadError').text('Enter Correct Course Code')
                }
            }
        })
    }
    else {
        alertPopup("Enter Correct Course Code")
    }
}
function loadExamSubType(t) {
    var examType = $(t).val()
    var academicSession = $('#academicSession').val()
    var subjectCode = $('#subjectCode').val().toUpperCase();
    $.ajax({
        type: "post",
        url: url('postExamination', 'loadExamSubType', ''),
        data: {examType: examType,subjectCode:subjectCode,academicSession:academicSession},
        success: function (data) {
            if(data.isExist){
                $('#examSubTypeTr').css('display', '')
                $('#examSubType').empty().append('<option value="">Select Exam Sub Type</option>')
                for (var i = 0; i < data.examSubTypeList.length; i++) {
                    $('#examSubType').append('<option value="' + data.examSubTypeList[i].id + '">' + data.examSubTypeList[i].examSubTypeName + '</option>')
                }
            }
            else{
                var msg = ''
                $('#examSubTypeTr').css('display', 'none')
                if (data.requiredTotalMarks) {
                    msg += "Required Total Marks is " + data.requiredTotalMarks + " and remaining required marks is " + data.examTotalRemainingMarks
                    $('#examTypeReqMarksHidden').val(data.examTotalRemainingMarks)
                    $('#assignedTotalMarks').val(data.examTotalRemainingMarks)
                    if (parseInt(data.examTotalRemainingMarks) <= 0) {
                        $('#assignedTotalMarkSubmit').prop('disabled', true)
                        $('#assignedTotalMarks').prop('disabled', true)
                    }
                    else {
                        $('#assignedTotalMarkSubmit').prop('disabled', false)
                        $('#assignedTotalMarks').prop('disabled', false)
                    }
                }

                alertPopupWithoutTime(msg)
            }


        }
    })
}
function checkExamSubType(t) {
    var examSubType = $(t).val()
    var subjectCode = $('#subjectCode').val().toUpperCase();
    var academicSession = $('#academicSession').val()
    var examType = $('#examType').val()
    $('#examTypeReqMarks').val('')
    $('#examTypeReqMarksHidden').val('0')
    $.ajax({
        type: "post",
        url: url('postExamination', 'checkExistenceOfExamSubType', ''),
        data: {examType: examType, examSubType: examSubType, subjectCode: subjectCode, academicSession: academicSession},
        success: function (data) {
            var msg = ''
            if (data.exist) {
                msg = 'This Examination Already Exist. '
                $('#assignedTotalMarkSubmit').prop('disabled', true)
            }
            else {
                $('#assignedTotalMarkSubmit').prop('disabled', false)
            }
            if (data.requiredTotalMarks) {
                msg += "Required Total Marks is " + data.requiredTotalMarks + " and remaining required marks is " + data.examTotalRemainingMarks
                $('#examTypeReqMarksHidden').val(data.examTotalRemainingMarks)
                $('#examTypeReqMarks').val('Remaining Max Marks is ' + data.examTotalRemainingMarks)
                if (parseInt(data.examTotalRemainingMarks) <= 0) {
                    $('#assignedTotalMarkSubmit').prop('disabled', true)
                    $('#assignedTotalMarks').prop('disabled', true)
                }
                else {
                    $('#assignedTotalMarkSubmit').prop('disabled', false)
                    $('#assignedTotalMarks').prop('disabled', false)
                }
            }

            alertPopupWithoutTime(msg)
        }
    })
}
function checkRemainingMarks(t) {
    var enteredMarks = $(t).val()
    var remainingMarks = $('#examTypeReqMarksHidden').val()
    if (remainingMarks != '0') {
        if (parseInt(enteredMarks) > parseInt(remainingMarks)) {
            $('#assignedTotalMarkSubmit').prop('disabled', true)
            alertPopupWithoutTime("Enter Marks Less than or equal to " + remainingMarks)
        }
        else {
            $('#assignedTotalMarkSubmit').prop('disabled', false)
        }
    }
}
function AddGrade(count) {
    var prevCount = count
    count = parseInt(count) + 1
    var fromNoStatus = true
    var toNoStatus = true
    var gradeStatus = true
    $("select[name='fromNo']").each(function () {
        if ($(this).val() == '' || $(this).val() == undefined) {
            fromNoStatus = false
        }
    })
    $("select[name='toNo']").each(function () {
        if ($(this).val() == '' || $(this).val() == undefined) {
            toNoStatus = false
        }
    })
    $("select[name='grade']").each(function () {
        if ($(this).val() == '' || $(this).val() == undefined) {
            gradeStatus = false
        }
    })
    if (fromNoStatus == false || toNoStatus == false || gradeStatus == false) {
        alertPopup("Please Select all unfilled values First")
    }
    else {
        $('#gradeTable').append('<tr><td class="university-size-1-4" style="text-align: right;"><select name="fromNo" id="fromNo' + count + '"  class="university-size-1-1"><option value="">Select From Number</option></select></td><td class="university-size-1-4" style="text-align: center;"><select name="toNo"  id="toNo' + count + '"  class="university-size-1-1"><option value="">Select to Number</option></select></td><td  style="text-align: left;"><select name="grade" id="grade' + count + '"  class="university-size-1-1"><option value="">Select Grade</option></select></td><td><label class="ui-icon-image" id="addButton' + count + '" onclick="AddGrade(' + count + ')"><img id="im" style="width:20px;" alt="Add" src="../images/Button-Add-icon.png" class="window"/></label></td></tr>')
        $("#addButton" + prevCount).css('display', 'none')
        for (var i = 100; i >= 0; i--) {
            $("#fromNo" + count).append('<option var="' + i + '">' + i + '</option>')
            $("#toNo" + count).append('<option var="' + i + '">' + i + '</option>')
        }
        var str = 'ABCDEFG'
        for (var i = 0; i < str.length; i++) {
            $("#grade" + count).append('<option var="' + str[i] + '">' + str[i] + '</option>')
        }
    }
}
function AddSubExam() {
    $('#examSubName').append('<tr><td><input type="text" class="university-size-1-2" name="examSubName"/></td><td></td></tr>')
}
function loadHomePageMessage() {
//    alert("hjjjj")
    $.ajax({
        type: "post",
        url: url('admin', 'loadHomePageMessage', ''),
        data: {},
        success: function (data) {
            var msg = ''
//            alert(data)
            if (data.length > 0) {
//                msg='<div id="homeMsg"></div>'
                $('#dialog').append('<ol>')
                for (var i = 0; i < data.length; i++) {
                    $('#dialog').append('<li>' + data[i].msg + '</li>')
                }
                $('#dialog').append('</ol>')
//                $( "#dialog" ).dialog( "open" );
                alertPopupWithoutTime($("#dialog").html())
            }

        }
    })
}
function loadMoreRow() {
    for (var i = 1; i < parseInt($('#noOfRule').val()); i++) {
        $("#ruleDetailTable tr:first").clone().appendTo("#ruleDetailTable");
    }

}
function checkUniqueInSame(t) {
    var symbol = $(t).val()
    if (symbol == $('#requiredTotalMarks').val() || symbol == $('#marksSecuredSymbol').val() || symbol == $('#totalMarksSymbol').val()) {
        $('#symbolError').text("")
        $('#symbolError').text("The symbol already Used. Please Enter Unique Symbol")
        $(t).addClass('error')
        $('#submitButton').prop('disabled', true)
    }
    else {
        $('#symbolError').text("")
        $(t).removeClass('error')
        $('#submitButton').prop('disabled', false)
    }

}
function checkForUniqueSymbol(t) {
    var symbol = $(t).val()
    $.ajax({
        type: "post",
        url: url('admin', 'checkForUniqueSymbol', ''),
        data: {symbol: symbol},
        success: function (data) {
//            alert(data.result)
            if (data.result == true) {
                $('#symbolError').text("")
                $('#symbolError').text("The symbol already Used. Please Enter Unique Symbol")
                $(t).addClass('error')
                $('#submitButton').prop('disabled', true)
            } else {
                $('#symbolError').text("")
                $(t).removeClass('error')
                $('#submitButton').prop('disabled', false)
            }

        }
    })
}
function loadSymbol(t) {
    var ruleFor = $(t).val()
    if (ruleFor != 'totalmarkssecured') {
        var ib = $(t).children('option:selected').index()
        $(t).closest('td').next().find('select').children('option').eq(ib).prop('selected', true);
//        alert($(t).closest('td').next().find('select').val())
//        var b=$(t).closest('td').next().find('select').val()
//        $(t).closest('td').next().find("input[name='text']").val(b)
    }
    else {
        var marksSecuredSymbol = $('#marksSecuredSymbol').val()
        if (marksSecuredSymbol != '') {
            $('#marksSecuredSymbol').removeClass('error')
            $('#submitButton').prop('disabled', false)
            $(t).closest('td').next().find('select').append("<option value='" + marksSecuredSymbol + "' selected>" + marksSecuredSymbol + "</option>")
//            var b=$(t).closest('td').next().find('select').val()
//            $(t).closest('td').next().find("input[name='text']").val(b)
        }
        else {
            $('#marksSecuredSymbol').addClass('error')
            alert('Please Enter a Unique Symbol for Marks Secured From this Rule')
            $('#submitButton').prop('disabled', true)
        }

    }
}
function showEvaluatedMarksGrade() {
    var course = $('#subjectCode').val()
    var academicSession = $('#academicSession').val()
    $.ajax({
        type: "post",
        url: url('admin', 'showEvaluatedStudentDetails', ''),
        data: {course: course,  academicSession: academicSession},
        success: function (data) {
            if (data.studentRollNoList.length > 0) {
                document.getElementById("paginationDiv").style.visibility = "visible";
                document.getElementById("submitButton").style.visibility = "visible";
                document.getElementById("rejectButton").style.visibility = "visible";

                $('#studentEvaluatedMarksTable thead').empty().append('<tr><th>Sr. No.</th><th>Name</th><th>Roll No</th><th>Marks</th><th>Grade</th></tr>')
                for (var i = 0; i < data.studentNameList.length; i++) {
                    $('#studentEvaluatedMarksTable tbody').append('<tr><td>' + ( i + 1 ) + '</td><td>' + data.studentNameList[i] + '</td><td>' + data.studentRollNoList[i] + '</td><td>' + data.studentMarksList[i] + '</td><td>' + data.studentGradeList[i] + '</td></tr>')
                }
                $('#jqPagination').empty().append('<a href="#" class="first" data-action="first">&laquo;</a><a href="#" class="previous" data-action="previous">&lsaquo;</a><input type="text" readonly="readonly"/><a href="#" class="next" data-action="next">&rsaquo;</a><a href="#" class="last" data-action="last">&rsaquo;</a>')
                $('#studentEvaluatedMarksTable tbody tr:not(:first)').hide();
                $table_rows = $('#studentEvaluatedMarksTable tbody tr');

                var table_row_limit = 10;

                var page_table = function (page) {
                    // calculate the offset and limit values
                    var offset = (page - 1) * table_row_limit,
                        limit = page * table_row_limit;

                    // hide all table rows
                    $table_rows.hide();

                    // show only the n rows
                    $table_rows.slice(offset, limit).show();

                }
                var pageNo = 0
                if ($table_rows.length % table_row_limit) {
                    pageNo = parseInt(parseInt($table_rows.length) / table_row_limit) + 1
                }
                else {
                    pageNo = parseInt($table_rows.length / table_row_limit)
                }
                $('.pagination').jqPagination({
                    max_page: pageNo,
                    paged: page_table
                });
                page_table(1);
            }

        }
    })
}
function showEvaluatedMarksGradeDetails() {
    var course = $('#subjectCode').val()
    var academicSession = $('#academicSession').val()
    $.ajax({
        type: "post",
        url: url('admin', 'showEvaluatedMarksGradeDetails', ''),
        data: {course: course, academicSession: academicSession},
        success: function (data) {
            if (data.studentRollNoList.length > 0) {
                document.getElementById("paginationDiv").style.visibility = "visible";
//                document.getElementById("submitButton").style.visibility = "visible";
                $('#studentEvaluatedMarksTable thead').empty().append('<tr><th>Sr. No.</th><th>Name</th><th>Roll No</th><th>Marks</th><th>Grade</th></tr>')
                for (var i = 0; i < data.studentNameList.length; i++) {
                    $('#studentEvaluatedMarksTable tbody').append('<tr><td>' + ( i + 1 ) + '</td><td>' + data.studentNameList[i] + '</td><td>' + data.studentRollNoList[i] + '</td><td>' + data.studentMarksList[i] + '</td><td>' + data.studentGradeList[i] + '</td></tr>')
                }
                $('#jqPagination').empty().append('<a href="#" class="first" data-action="first">&laquo;</a><a href="#" class="previous" data-action="previous">&lsaquo;</a><input type="text" readonly="readonly"/><a href="#" class="next" data-action="next">&rsaquo;</a><a href="#" class="last" data-action="last">&rsaquo;</a>')
                $('#studentEvaluatedMarksTable tbody tr:not(:first)').hide();
                $table_rows = $('#studentEvaluatedMarksTable tbody tr');

                var table_row_limit = 10;

                var page_table = function (page) {
                    // calculate the offset and limit values
                    var offset = (page - 1) * table_row_limit,
                        limit = page * table_row_limit;

                    // hide all table rows
                    $table_rows.hide();

                    // show only the n rows
                    $table_rows.slice(offset, limit).show();

                }
                var pageNo = 0
                if ($table_rows.length % table_row_limit) {
                    pageNo = parseInt(parseInt($table_rows.length) / table_row_limit) + 1
                }
                else {
                    pageNo = parseInt($table_rows.length / table_row_limit)
                }
                $('.pagination').jqPagination({
                    max_page: pageNo,
                    paged: page_table
                });
                page_table(1);
            }

        }
    })
}

function searchCourseByCode() {

    var course = $('#searchCourse').val()
    $.ajax({
        type: "post",
        url: url('admin', 'searchCourseByCode', ''),
        data: {course: course},
        success: function (data) {

            if (data.code.length > 0) {


                $('#courseTable tbody').empty().append('<tr><td>' + data.code + '</td><td>' + data.name + '</td><td>' + data.points + '</td><td>' + data.session + '</td><td><a href="#" onclick="subjectEdit(' + data.sessionId + ')">Edit</a></td></tr>')

                $('#pagin').hide();

            }
        }
    })
}
function subjectEdit(sessId) {

    window.location.href = '/Symphonie/admin/addCourses/' + sessId;
}


function searchApprovedStudent() {

    var studentRollNo = $('#searchStudent').val()
    $.ajax({
        type: "post",
        url: url('hod', 'searchApprovedStudent', ''),
        data: {studentRollNo: studentRollNo},
        success: function (data) {

            if (data.rollNo.length > 0) {
                if (data.middleName != null) {

                    $('#courseTable tbody').empty().append('<tr><td>' + data.firstName + ' ' + data.middleName + ' ' + data.lastName + '</td><td>' + data.rollNo + '</td><td><button onclick="studentView(' + data.studentId + ')">View</button></td></tr>')

                    $('#pagin').hide();

                }
                else {

                    $('#courseTable tbody').empty().append('<tr><td>' + data.firstName + ' ' + data.lastName + '</td><td>' + data.rollNo + '</td><td><button onclick="studentView(' + data.studentId + ')">View</button></td></tr>')

                    $('#pagin').hide();
                }


            }
        }
    })
}

function studentView(stuId) {

    window.location.href = '/Symphonie/student/registration?studentID=' + stuId + '&view=view'
}

function searchApprovedRegistration() {

    var studentRollNo = $('#searchStudent').val()
    $.ajax({
        type: "post",
        url: url('hod', 'searchApprovedRegistration', ''),
        data: {studentRollNo: studentRollNo},
        success: function (data) {

            if (data.rollNo.length > 0) {
                if (data.middleName != null) {

                    $('#courseTable tbody').empty().append('<tr><td>' + data.firstName + ' ' + data.middleName + ' ' + data.lastName + '</td><td>' + data.rollNo + '</td><td>' + data.sem + '</td><td><button onclick="viewRegistrationDetails(' + data.studentId + ')">View</button></td><td><button onclick="editRegistrationDetails(' + data.studentId + ',' + data.sem + ')">Edit</button></td></tr>')

                    $('#pagin').hide();

                }
                else {

                    $('#courseTable tbody').empty().append('<tr><td>' + data.firstName + ' ' + data.lastName + '</td><td>' + data.rollNo + '</td><td>' + data.sem + '</td><td><button onclick="viewRegistrationDetails(' + data.studentId + ')">View</button></td><td><button onclick="editRegistrationDetails(' + data.studentId + ',' + data.sem + ')">Edit</button></td></tr>')

                    $('#pagin').hide();
                }


            }
        }
    })
}

function viewRegistrationDetails(stuId) {

    window.location.href = '/Symphonie/student/semesterDetails?studentID=' + stuId + '&view=view'
}

function editRegistrationDetails(stuId, sem) {

    window.location.href = '/Symphonie/hod/viewSemesterStudent?studentID=' + stuId + '&approved=approved&semester=' + sem + ''
}

function searchStudentToApprove() {

    var studentRollNo = $('#searchStudent').val()
    $.ajax({
        type: "post",
        url: url('hod', 'searchStudentToApprove', ''),
        data: {studentRollNo: studentRollNo},
        success: function (data) {
            debugger;
            if (data.rollNo.length > 0) {
                if (data.middleName != null) {

                    $('#courseTable tbody').empty().append('<tr><td>' + data.firstName + ' ' + data.middleName + ' ' + data.lastName + '</td><td>' + data.rollNo + '</td><td><button onclick="viewStudentToApprove(' + data.studentId + ')">View</button></td></tr>')

                    $('#pagin').hide();

                }
                else {

                    $('#courseTable tbody').empty().append('<tr><td>' + data.firstName + ' ' + data.lastName + '</td><td>' + data.rollNo + '</td><td><button onclick="viewStudentToApprove(' + data.studentId + ')">View</button></td></tr>')

                    $('#pagin').hide();
                }

                $('#errorMsg').html("")
            }
            else {
                $('#errorMsg').html("Please enter Appropriate Roll No")

            }
        }

    })
}
function viewStudentToApprove(stuId) {

    window.location.href = '/Symphonie/student/registration?studentID=' + stuId + ''
}
function searchRegistrationToApprove() {

    var studentRollNo = $('#searchStudent').val()
    $.ajax({
        type: "post",
        url: url('hod', 'searchRegistrationToApprove', ''),
        data: {studentRollNo: studentRollNo},
        success: function (data) {

            if (data.rollNo.length > 0) {
                if (data.middleName != null) {

                    $('#courseTable tbody').empty().append('<tr><td>' + data.firstName + ' ' + data.middleName + ' ' + data.lastName + '</td><td>' + data.rollNo + '</td><td><button onclick="viewRegistrationToApprove(' + data.studentId + ')">View</button></td></tr>')

                    $('#pagin').hide();

                }
                else {

                    $('#courseTable tbody').empty().append('<tr><td>' + data.firstName + ' ' + data.lastName + '</td><td>' + data.rollNo + '</td><td><button onclick="viewRegistrationToApprove(' + data.studentId + ')">View</button></td></tr>')

                    $('#pagin').hide();
                }


            }
        }
    })
}

function viewRegistrationToApprove(stuId) {
    window.location.href = '/Symphonie/hod/viewSemesterStudent?studentID=' + stuId + ''
}
function loadUnapprovedStudentMarksDetails() {
    var subjectCode = $('#subjectCode').val()
    var academicSession = $('#academicSession').val()
    var examination = $('#examination').val()
    $('#StudentMarksDetails tbody').empty()
    if (subjectCode != '' && academicSession != '' && examination != '') {
        $.ajax({
            type: "post",
            url: url('postExamination', 'loadStudentMarksDetails', ''),
            data: {subjectCode: subjectCode, academicSession: academicSession, examination: examination, loadFor: 'unapprove'},
            success: function (data) {
                if (data.studentRollNoList.length > 0) {

                    if (data.showApprove == true) {
                        $('#submitAll').css("display", "");
                        $('#StudentMarksDetails thead').empty().append('<tr><th>Name</th><th>Rol Number</th><th>Marks</th><th></th></tr>')
                        for (var i = 0; i < data.studentRollNoList.length; i++) {
                            $('#StudentMarksDetails tbody').append('<tr><td class="university-size-1-4">' + data.studentNameList[i] + '</td><td class="university-size-1-4">' + data.studentRollNoList[i] + '</td><td class="university-size-1-4">' + data.studentMarks[i] + '</td><td class="university-size-1-4"><input type="button" value="Approve" onclick="approveIndividualStudentsMarks(' + data.studentMarksID[i] + ')"> <input type="button" value="Reject" onclick="rejectIndividualStudentsMarks(' + data.studentMarksID[i] + ')"></td></tr>')
                        }
                    }
                    else {
                        $('#StudentMarksDetails thead').empty().append('<tr><th class="university-size-1-3">Name</th><th class="university-size-1-3">Rol Number</th><th class="university-size-1-3">Marks</th></tr>')
                        for (var i = 0; i < data.studentRollNoList.length; i++) {
                            $('#StudentMarksDetails tbody').append('<tr><td >' + data.studentNameList[i] + '</td><td>' + data.studentRollNoList[i] + '</td><td>' + data.studentMarks[i] + '</td></tr>')
                        }
                    }

                    $('#StudentMarksDetails').dataTable({
                        "paging": false,
                        "ordering": false,
                        "info": false
                    });
                } else {
                    $('#submitAll').css("display", "none");
                }

            }
        })
    }

}
function approveIndividualStudentsMarks(marksId) {
    $.ajax({
        type: "post",
        url: url('postExamination', 'approveIndividualStudent', ''),
        data: {marksId: marksId},
        success: function (data) {
            if (data[0] == 'Approved Successfully!') {
                alertPopup("Approved Successfully!")
                loadUnapprovedStudentMarksDetails()
            }
        }
    })
}
function rejectIndividualStudentsMarks(marksId) {
    $.ajax({
        type: "post",
        url: url('postExamination', 'rejectIndividualStudent', ''),
        data: {marksId: marksId},
        success: function (data) {
            if (data[0] == 'Rejected Successfully!') {
                alertPopup("Rejected Successfully!")
                loadUnapprovedStudentMarksDetails()
            }
            else{
                alertPopup(data[0])
            }
        }
    })
}
function loadApprovedStudentMarksDetails() {
    var subjectCode = $('#subjectCode').val()
    var academicSession = $('#academicSession').val()
    var examination = $('#examination').val()
    $('#StudentMarksDetails tbody').empty()
    if (subjectCode != '' && academicSession != '' && examination != '') {
        $.ajax({
            type: "post",
            url: url('postExamination', 'loadStudentMarksDetails', ''),
            data: {subjectCode: subjectCode, academicSession: academicSession, examination: examination, loadFor: 'approve'},
            success: function (data) {
                if (data.studentRollNoList.length > 0) {
                    $('#StudentMarksDetails thead').empty().append('<tr><th class="university-size-1-3">Name</th><th class="university-size-1-3">Rol Number</th><th class="university-size-1-3">Marks</th></tr>')
                    for (var i = 0; i < data.studentRollNoList.length; i++) {
                        $('#StudentMarksDetails tbody').append('<tr><td >' + data.studentNameList[i] + '</td><td>' + data.studentRollNoList[i] + '</td><td>' + data.studentMarks[i] + '</td></tr>')
                    }
                    $('#StudentMarksDetails').dataTable({
                        "paging": false,
                        "ordering": false,
                        "info": false
                    });
                }

            }
        })
    }
}
function setButtonEnable(t) {
    if ($(t).val() != '')
        $('#setButton').attr('disabled', false)
}
function approveAllStudents() {
    var subjectCode = $('#subjectCode').val()
    var academicSession = $('#academicSession').val()
    var examination = $('#examination').val()
    if (subjectCode != '' && academicSession != '' && examination != '') {
        $.ajax({
            type: "post",
            url: url('postExamination', 'approveAllMarks', ''),
            data: {subjectCode: subjectCode, academicSession: academicSession, examination: examination},
            success: function (data) {
                if (data.length > 0) {
                    alert("All Approved Students are :" + data)

                    $("select").attr("disabled", false);
                    $("#subjectCode").attr("disabled", false);
                    $("#showButton").attr("disabled", true)
                    $("#resetButton").attr("disabled", true)
                    $("#setButton").attr("disabled", false)
                    $('#ltpc').empty()
                    $('#StudentMarksDetails thead').empty()
                    $('#StudentMarksDetails tbody').empty()
                    $('#submitAll').css("display", "none");

                    location.reload();
                }
            }
        })
    }
}function rejectAllStudents() {
    var subjectCode = $('#subjectCode').val()
    var academicSession = $('#academicSession').val()
    var examination = $('#examination').val()
    if (subjectCode != '' && academicSession != '' && examination != '') {
        $.ajax({
            type: "post",
            url: url('postExamination', 'rejectAllMarks', ''),
            data: {subjectCode: subjectCode, academicSession: academicSession, examination: examination},
            success: function (data) {
                if (data.length > 0) {
                    alert("Rejected Students are :" + data)

                    $("select").attr("disabled", false);
                    $("#subjectCode").attr("disabled", false);
                    $("#showButton").attr("disabled", true)
                    $("#resetButton").attr("disabled", true)
                    $("#setButton").attr("disabled", false)
                    $('#ltpc').empty()
                    $('#StudentMarksDetails thead').empty()
                    $('#StudentMarksDetails tbody').empty()
                    $('#submitAll').css("display", "none");

                    location.reload();
                }
            }
        })
    }
}
function printCertificate() {
    var certificateType = $('#certificateType').val()
    var showEdit = $('#showEdit').val()
    var year = $('#year').val()
    var certificateNo = $('#certificateNo').val()
    if(showEdit=='true'){
        if (certificateType == 'd') {
            var refNo = $('#editRefNo').val()
            var certificateReqId = $('#certificateReqId').val()
            var editDate = $('#editDate').val()
            var editFromName = $('#editFromName').val()
            var editFromPosition = $('#editFromPosition').val()
            var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
            var paragraphEdit1 = $('#paragraphEdit1').val()
            var paragraphEdit2 = $('#paragraphEdit2').val()
            var paragraphEdit3 = $('#paragraphEdit3').val()
            var signatureNameEdit = $('#signatureNameEdit').val()
            $.ajax({
                type: "post",
                url: url('certificateTemp', 'saveAnnexerD', ''),
                data: {year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit3: paragraphEdit3, paragraphEdit2: paragraphEdit2, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
                success: function (data) {
                    if (data.status) {
                        var divContents = $("#templateRegion").html();
                        var printWindow = window.open('', '', 'height=842,width=595');
                        printWindow.document.write('<html><head><title></title>');
                        printWindow.document.write('<link rel="stylesheet" href="/Symphonie/static/bootstrap-3.3.4-dist/css/bootstrap.min.css" type="text/css"/>');
                        printWindow.document.write('<style type="text/css" media="print">@page{size: auto;}body{background-color:#FFFFFF;border: solid 1px black ;margin: 5mm;} </style>');
                        printWindow.document.write('</head><body >');
                        printWindow.document.write(divContents);
                        printWindow.document.write('</body></html>');
                        printWindow.document.close();
                        printWindow.print();

                    }
                }
            })
        }
        if (certificateType == 'h') {
            var refNo = $('#editRefNo').val()
            var certificateReqId = $('#certificateReqId').val()
            var editDate = $('#editDate').val()
            var editFromName = $('#editFromName').val()
            var editFromPosition = $('#editFromPosition').val()
            var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
            var paragraphEdit1 = $('#paragraphEdit1').val()
            var paragraphEdit2 = $('#paragraphEdit2').val()
            var paragraphEdit3 = $('#paragraphEdit3').val()
            var paragraphEdit4 = $('#paragraphEdit4').val()
            var paragraphEdit5 = $('#paragraphEdit5').val()
            var toDesignationEdit = $('#toDesignationEdit').val()
            var toInstituteEdit = $('#toInstituteEdit').val()
            var toAddressEdit = $('#toAddressEdit').val()
            var signatureNameEdit = $('#signatureNameEdit').val()
            $.ajax({
                type: "post",
                url: url('certificateTemp', 'saveAnnexerH', ''),
                data: {toAddressEdit:toAddressEdit,toInstituteEdit:toInstituteEdit,toDesignationEdit:toDesignationEdit,year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit3: paragraphEdit3, paragraphEdit2: paragraphEdit2, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
                success: function (data) {
                    if (data.status) {
                        var divContents = $("#templateRegion").html();
                        var printWindow = window.open('', '', 'height=842,width=595');
                        printWindow.document.write('<html><head><title></title>');
                        printWindow.document.write('<link rel="stylesheet" href="/Symphonie/static/bootstrap-3.3.4-dist/css/bootstrap.min.css" type="text/css"/>');
                        printWindow.document.write('<style type="text/css" media="print">@page{size: auto;}body{background-color:#FFFFFF;border: solid 1px black ;margin: 5mm;} </style>');
                        printWindow.document.write('</head><body >');
                        printWindow.document.write(divContents);
                        printWindow.document.write('</body></html>');
                        printWindow.document.close();
                        printWindow.print();

                    }
                }
            })
        }
        else if (certificateType == 'e') {
            var refNo = $('#editRefNo').val()
            var certificateReqId = $('#certificateReqId').val()
            var editDate = $('#editDate').val()
            var editFromName = $('#editFromName').val()
            var editFromPosition = $('#editFromPosition').val()
            var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
            var paragraphEdit1 = $('#paragraphEdit1').val()
            var paragraphEdit2 = $('#paragraphEdit2').val()
            var paragraphEdit3 = $('#paragraphEdit3').val()
            var signatureNameEdit = $('#signatureNameEdit').val()
            $.ajax({
                type: "post",
                url: url('certificateTemp', 'saveAnnexerE', ''),
                data: {year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit3: paragraphEdit3, paragraphEdit2: paragraphEdit2, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
                success: function (data) {
                    if (data.status) {
                        var divContents = $("#templateRegion").html();
                        var printWindow = window.open('', '', 'height=842,width=595');
                        printWindow.document.write('<html><head><title></title>');
                        printWindow.document.write('<link rel="stylesheet" href="/Symphonie/static/bootstrap-3.3.4-dist/css/bootstrap.min.css" type="text/css"/>');
                        printWindow.document.write('<style type="text/css" media="print">@page{size: auto;margin: 0mm;}body{background-color:#FFFFFF;border: solid 1px black ;margin: 0px;} </style>');
                        printWindow.document.write('</head><body >');
                        printWindow.document.write(divContents);
                        printWindow.document.write('</body></html>');
                        printWindow.document.close();
                        printWindow.print();
                    }
                }
            })
        }
        else if (certificateType == 'g') {
            var refNo = $('#editRefNo').val()
            var certificateReqId = $('#certificateReqId').val()
            var editDate = $('#editDate').val()
            var editFromName = $('#editFromName').val()
            var editFromPosition = $('#editFromPosition').val()
            var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
            var paragraphEdit1 = $('#paragraphEdit1').val()
            var paragraphEdit2 = $('#paragraphEdit2').val()
            var paragraphEdit3 = $('#paragraphEdit3').val()
            var signatureNameEdit = $('#signatureNameEdit').val()
            $.ajax({
                type: "post",
                url: url('certificateTemp', 'saveAnnexerG', ''),
                data: {year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit3: paragraphEdit3, paragraphEdit2: paragraphEdit2, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
                success: function (data) {
                    if (data.status) {
                        var divContents = $("#templateRegion").html();
                        var printWindow = window.open('', '', 'height=842,width=595');
                        printWindow.document.write('<html><head><title></title>');
                        printWindow.document.write('<link rel="stylesheet" href="/Symphonie/static/bootstrap-3.3.4-dist/css/bootstrap.min.css" type="text/css"/>');
                        printWindow.document.write('<style type="text/css" media="print">@page{size: auto;margin: 0mm;}body{background-color:#FFFFFF;border: solid 1px black ;margin: 0px;} </style>');
                        printWindow.document.write('</head><body >');
                        printWindow.document.write(divContents);
                        printWindow.document.write('</body></html>');
                        printWindow.document.close();
                        printWindow.print();
                    }
                }
            })
        }
        else if (certificateType == 'b') {
            var refNo = $('#editRefNo').val()
            var certificateReqId = $('#certificateReqId').val()
            var editDate = $('#editDate').val()
            var editFromName = $('#editFromName').val()
            var editFromPosition = $('#editFromPosition').val()
            var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
            var paragraphEdit1 = $('#paragraphEdit1').val()
            var paragraphEdit2 = $('#paragraphEdit2').val()
            var paragraphEdit3 = $('#paragraphEdit3').val()
            var signatureNameEdit = $('#signatureNameEdit').val()
            $.ajax({
                type: "post",
                url: url('certificateTemp', 'saveAnnexerB', ''),
                data: {year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit3: paragraphEdit3, paragraphEdit2: paragraphEdit2, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
                success: function (data) {
                    if (data.status) {
                        var divContents = $("#templateRegion").html();
                        var printWindow = window.open('', '', 'height=842,width=595');
                        printWindow.document.write('<html><head><title></title>');
                        printWindow.document.write('<link rel="stylesheet" href="/Symphonie/static/bootstrap-3.3.4-dist/css/bootstrap.min.css" type="text/css"/>');
                        printWindow.document.write('<style type="text/css" media="print">@page{size: auto;margin: 0mm;}body{background-color:#FFFFFF;border: solid 1px black ;margin: 0px;} </style>');
                        printWindow.document.write('</head><body >');
                        printWindow.document.write(divContents);
                        printWindow.document.write('</body></html>');
                        printWindow.document.close();
                        printWindow.print();
                    }
                }
            })
        }
        else if (certificateType == '#') {
            var refNo = $('#editRefNo').val()
            var certificateReqId = $('#certificateReqId').val()
            var editDate = $('#editDate').val()
            var editFromName = $('#editFromName').val()
            var editFromPosition = $('#editFromPosition').val()
            var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
            var paragraphEdit1 = $('#paragraphEdit1').val()
            var paragraphEdit2 = $('#paragraphEdit2').val()
            var paragraphEdit3 = $('#paragraphEdit3').val()
            var paragraphEdit4 = $('#paragraphEdit4').val()
            var paragraphEdit5 = $('#paragraphEdit5').val()
            var signatureNameEdit = $('#signatureNameEdit').val()
            $.ajax({
                type: "post",
                url: url('certificateTemp', 'saveOthers', ''),
                data: {year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit3: paragraphEdit3, paragraphEdit2: paragraphEdit2,paragraphEdit4: paragraphEdit4,paragraphEdit5: paragraphEdit5, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
                success: function (data) {
                    if (data.status) {
                        var divContents = $("#templateRegion").html();
                        var printWindow = window.open('', '', 'height=842,width=595');
                        printWindow.document.write('<html><head><title></title>');
                        printWindow.document.write('<link rel="stylesheet" href="/Symphonie/static/bootstrap-3.3.4-dist/css/bootstrap.min.css" type="text/css"/>');
                        printWindow.document.write('<style type="text/css" media="print">@page{size: auto;margin: 0mm;}body{background-color:#FFFFFF;border: solid 1px black ;margin: 0px;} </style>');
                        printWindow.document.write('</head><body >');
                        printWindow.document.write(divContents);
                        printWindow.document.write('</body></html>');
                        printWindow.document.close();
                        printWindow.print();
                    }
                }
            })
        }
        else if (certificateType == 'c') {
            var refNo = $('#editRefNo').val()
            var certificateReqId = $('#certificateReqId').val()
            var editDate = $('#editDate').val()
            var editFromName = $('#editFromName').val()
            var editFromPosition = $('#editFromPosition').val()
            var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
            var paragraphEdit1 = $('#paragraphEdit1').val()
            var paragraphEdit2 = $('#paragraphEdit2').val()
            var paragraphEdit3 = $('#paragraphEdit3').val()
            var signatureNameEdit = $('#signatureNameEdit').val()
            $.ajax({
                type: "post",
                url: url('certificateTemp', 'saveAnnexerC', ''),
                data: {year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit3: paragraphEdit3, paragraphEdit2: paragraphEdit2, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
                success: function (data) {
                    if (data.status) {
                        var divContents = $("#templateRegion").html();
                        var printWindow = window.open('', '', 'height=842,width=595');
                        printWindow.document.write('<html><head><title></title>');
                        printWindow.document.write('<link rel="stylesheet" href="/Symphonie/static/bootstrap-3.3.4-dist/css/bootstrap.min.css" type="text/css"/>');
                        printWindow.document.write('<style type="text/css" media="print">@page{size: auto;margin: 0mm;}body{background-color:#FFFFFF;border: solid 1px black ;margin: 0px;} </style>');
                        printWindow.document.write('</head><body >');
                        printWindow.document.write(divContents);
                        printWindow.document.write('</body></html>');
                        printWindow.document.close();
                        printWindow.print();
                    }
                }
            })
        } else if (certificateType == 'a') {
            var refNo = $('#editRefNo').val()
            var certificateReqId = $('#certificateReqId').val()
            var editDate = $('#editDate').val()
            var editFromName = $('#editFromName').val()
            var editFromPosition = $('#editFromPosition').val()
            var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
            var paragraphEdit1 = $('#paragraphEdit1').val()
            var paragraphEdit2 = $('#paragraphEdit2').val()
            var paragraphEdit3 = $('#paragraphEdit3').val()
            var signatureNameEdit = $('#signatureNameEdit').val()
            var signatureNameEdit = $('#signatureNameEdit').val()
            var prevYearTuitionFee = $('#prevYearTutionFeeEdit').val()
            var prevYearAdmissionFee = $('#prevYearAdmissionFeeEdit').val()
            var prevYearExamFee = $('#prevYearExamFeeEdit').val()
            var prevYearOtherFee = $('#prevYearOtherFeeEdit').val()
            var thisYearTuitionFee = $('#thisYearTutionFeeEdit').val()
            var thisYearLabFee = $('#thisYearExamFeeEdit').val()
            var thisYearExamFee = $('#thisYearLabFeeEdit').val()
            var totalFee = $('#totalAmount').text()
            $.ajax({
                type: "post",
                url: url('certificateTemp', 'saveAnnexerA', ''),
                data: {totalFee:totalFee,thisYearExamFee:thisYearExamFee,thisYearLabFee:thisYearLabFee,thisYearTuitionFee:thisYearTuitionFee,prevYearOtherFee:prevYearOtherFee,prevYearExamFee:prevYearExamFee,prevYearAdmissionFee:prevYearAdmissionFee,prevYearTuitionFee:prevYearTuitionFee,year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit3: paragraphEdit3, paragraphEdit2: paragraphEdit2, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
                success: function (data) {
                    if (data.status) {
                        var divContents = $("#templateRegion").html();
                        var printWindow = window.open('', '', 'height=842,width=595');
                        printWindow.document.write('<html><head><title></title>');
                        printWindow.document.write('<link rel="stylesheet" href="/Symphonie/static/bootstrap-3.3.4-dist/css/bootstrap.min.css" type="text/css"/>');
                        printWindow.document.write('<style type="text/css" media="print">@page{size: auto;margin: 0mm;}body{background-color:#FFFFFF;border: solid 1px black ;margin: 0px;} </style>');
                        printWindow.document.write('</head><body >');
                        printWindow.document.write(divContents);
                        printWindow.document.write('</body></html>');
                        printWindow.document.close();
                        printWindow.print();
                    }
                }
            })
        }
        else if (certificateType == 'f') {
            var refNo = $('#editRefNo').val()
            var certificateReqId = $('#certificateReqId').val()
            var editDate = $('#editDate').val()
            var editFromName = $('#editFromName').val()
            var editFromPosition = $('#editFromPosition').val()
            var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
            var paragraphEdit1 = $('#paragraphEdit1').val()
            var paragraphEdit2 = $('#paragraphEdit2').val()
            var paragraphEdit3 = $('#paragraphEdit3').val()
            var signatureNameEdit = $('#signatureNameEdit').val()
            var firstSemFeeEdit = $('#firstSemFeeEdit').val()
            var secondSemFeeEdit = $('#secondSemFeeEdit').val()
            var firstYearFeeEdit = $('#firstYearFeeEdit').val()
            var courseFeeRemarksEdit = $('#courseFeeRemarksEdit').val()
            var thirdSemFeeEdit = $('#thirdSemFeeEdit').val()
            var fourthSemFeeEdit = $('#fourthSemFeeEdit').val()
            var secondYearFeeEdit = $('#secondYearFeeEdit').val()
            var fifthSemFeeEdit = $('#fifthSemFeeEdit').val()
            var sixthSemFeeEdit = $('#sixthSemFeeEdit').val()
            var thirdYearFeeEdit = $('#thirdYearFeeEdit').val()
            var seventhSemFeeEdit = $('#seventhSemFeeEdit').val()
            var eighthSemFeeEdit = $('#eighthSemFeeEdit').val()
            var fourthYearFeeEdit = $('#fourthYearFeeEdit').val()
            var hostelAdmissionFeeEdit = $('#hostelAdmissionFeeEdit').val()
            var hostelTotalFeeEdit = $('#hostelTotalFeeEdit').val()
            var hostelRemarksEdit = $('#hostelRemarksEdit').val()
            var otherExpensesEdit = $('#otherExpensesEdit').val()
            var otherExpensesTotalEdit = $('#otherExpensesTotalEdit').val()
            var otherExpensesRemarksEdit = $('#otherExpensesRemarksEdit').val()
            var laptopExpensesEdit = $('#laptopExpensesEdit').val()
            $.ajax({
                type: "post",
                url: url('certificateTemp', 'saveAnnexerA', ''),
                data: {laptopExpensesEdit:laptopExpensesEdit,otherExpensesRemarksEdit:otherExpensesRemarksEdit,otherExpensesTotalEdit:otherExpensesTotalEdit,otherExpensesEdit:otherExpensesEdit,hostelRemarksEdit:hostelRemarksEdit,hostelTotalFeeEdit:hostelTotalFeeEdit,hostelAdmissionFeeEdit:hostelAdmissionFeeEdit,fourthYearFeeEdit:fourthYearFeeEdit,eighthSemFeeEdit:eighthSemFeeEdit,seventhSemFeeEdit:seventhSemFeeEdit,thirdYearFeeEdit:thirdYearFeeEdit,sixthSemFeeEdit:sixthSemFeeEdit,fifthSemFeeEdit:fifthSemFeeEdit,secondYearFeeEdit:secondYearFeeEdit,fourthSemFeeEdit:fourthSemFeeEdit,thirdSemFeeEdit:thirdSemFeeEdit,courseFeeRemarksEdit:courseFeeRemarksEdit,firstYearFeeEdit:firstYearFeeEdit,secondSemFeeEdit:secondSemFeeEdit,firstSemFeeEdit:firstSemFeeEdit,year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit3: paragraphEdit3, paragraphEdit2: paragraphEdit2, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
                success: function (data) {
                    if (data.status) {
                        var divContents = $("#templateRegion").html();
                        var printWindow = window.open('', '', 'height=842,width=595');
                        printWindow.document.write('<html><head><title></title>');
                        printWindow.document.write('<link rel="stylesheet" href="/Symphonie/static/bootstrap-3.3.4-dist/css/bootstrap.min.css" type="text/css"/>');
                        printWindow.document.write('<style type="text/css" media="print">@page{size: auto;margin: 0mm;}body{background-color:#FFFFFF;border: solid 1px black ;margin: 0px;} </style>');
                        printWindow.document.write('</head><body >');
                        printWindow.document.write(divContents);
                        printWindow.document.write('</body></html>');
                        printWindow.document.close();
                        printWindow.print();
                    }
                }
            })
        }
    }
    else{
        var divContents = $("#templateRegion").html();
        var printWindow = window.open('', '', 'height=842,width=595');
        printWindow.document.write('<html><head><title></title>');
        printWindow.document.write('<link rel="stylesheet" href="/Symphonie/static/bootstrap-3.3.4-dist/css/bootstrap.min.css" type="text/css"/>');
        printWindow.document.write('<style type="text/css" media="print">@page{size: auto;margin: 0mm;}body{background-color:#FFFFFF;border: solid 1px black ;margin: 0px;} </style>');
        printWindow.document.write('</head><body >');
        printWindow.document.write(divContents);
        printWindow.document.write('</body></html>');
        printWindow.document.close();
        printWindow.print();
    }

}

function editCertificate() {
    $(".editCertificate").css("display", "");
    $(".view").css("display", "none");
    $('#btnEditDone').attr('disabled', false)
    $('#btnPrint').attr('disabled', true)
    $('#btnEdit').attr('disabled', true)
    $('#sendForApproval').attr('disabled', true)
}

function editDone() {
    var certificateType = $('#certificateType').val()
    var year = $('#year').val()
    var certificateNo = $('#certificateNo').val()
    if (certificateType == 'a') {
        var refNo = $('#editRefNo').val()
        var certificateReqId = $('#certificateReqId').val()
        var editDate = $('#editDate').val()
        var editFromName = $('#editFromName').val()
        var editFromPosition = $('#editFromPosition').val()
        var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
        var paragraphEdit1 = $('#paragraphEdit1').val()
        var paragraphEdit2 = $('#paragraphEdit2').val()
        var paragraphEdit3 = $('#paragraphEdit3').val()
        var signatureNameEdit = $('#signatureNameEdit').val()
        var prevYearTuitionFee = $('#prevYearTutionFeeEdit').val()
        var prevYearAdmissionFee = $('#prevYearAdmissionFeeEdit').val()
        var prevYearExamFee = $('#prevYearExamFeeEdit').val()
        var prevYearOtherFee = $('#prevYearOtherFeeEdit').val()
        var thisYearTuitionFee = $('#thisYearTutionFeeEdit').val()
        var thisYearLabFee = $('#thisYearExamFeeEdit').val()
        var thisYearExamFee = $('#thisYearLabFeeEdit').val()
        var totalFee = $('#totalAmount').text()
        $.ajax({
            type: "post",
            url: url('certificateTemp', 'saveAnnexerAWithoutStatus', ''),
            data: {totalFee:totalFee,thisYearExamFee:thisYearExamFee,thisYearLabFee:thisYearLabFee,thisYearTuitionFee:thisYearTuitionFee,prevYearOtherFee:prevYearOtherFee,prevYearExamFee:prevYearExamFee,prevYearAdmissionFee:prevYearAdmissionFee,prevYearTuitionFee:prevYearTuitionFee,year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit3: paragraphEdit3, paragraphEdit2: paragraphEdit2, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
            success: function (data) {
                if (data.status) {
                    $(".view").css("display", "");
                    $(".editCertificate").css("display", "none");
                    $('#btnEditDone').attr('disabled', true)
                    $('#btnPrint').attr('disabled', false)
                    $('#btnEdit').attr('disabled', false)
                    $('#sendForApproval').attr('disabled', false)
                    $('#viewFromName').text($('#editFromName').val())
                    $('#viewRefNo').text($('#editRefNo').val())
                    $('#viewFromPosition').text($('#editFromPosition').val())
                    $('#viewDate').text($('#editDate').val())
                    $('#certificateMsgHeaderView').text($('#certificateMsgHeaderEdit').val())
                    $('#paragraphView1').text($('#paragraphEdit1').val())
                    $('#paragraphView2').text($('#paragraphEdit2').val())
                    $('#paragraphView3').text($('#paragraphEdit3').val())
                    $('#signatureNameView').text($('#signatureNameEdit').val())
                    $('#prevYearTutionFeeView').text($('#prevYearTutionFeeEdit').val())
                    $('#prevYearAdmissionFeeView').text($('#prevYearAdmissionFeeEdit').val())
                    $('#prevYearExamFeeView').text($('#prevYearExamFeeEdit').val())
                    $('#prevYearOtherFeeView').text($('#prevYearOtherFeeEdit').val())
                    $('#thisYearTutionFeeView').text($('#thisYearTutionFeeEdit').val())
                    $('#thisYearExamFeeView').text($('#thisYearExamFeeEdit').val())
                    $('#thisYearLabFeeView').text($('#thisYearLabFeeEdit').val())
                }
            }
        })
    }
    else if (certificateType == 'f') {
        var refNo = $('#editRefNo').val()
        var certificateReqId = $('#certificateReqId').val()
        var editDate = $('#editDate').val()
        var editFromName = $('#editFromName').val()
        var editFromPosition = $('#editFromPosition').val()
        var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
        var paragraphEdit1 = $('#paragraphEdit1').val()
        var paragraphEdit2 = $('#paragraphEdit2').val()
        var paragraphEdit3 = $('#paragraphEdit3').val()
        var signatureNameEdit = $('#signatureNameEdit').val()
        var firstSemFeeEdit = $('#firstSemFeeEdit').val()
        var secondSemFeeEdit = $('#secondSemFeeEdit').val()
        var firstYearFeeEdit = $('#firstYearFeeEdit').val()
        var courseFeeRemarksEdit = $('#courseFeeRemarksEdit').val()
        var thirdSemFeeEdit = $('#thirdSemFeeEdit').val()
        var fourthSemFeeEdit = $('#fourthSemFeeEdit').val()
        var secondYearFeeEdit = $('#secondYearFeeEdit').val()
        var fifthSemFeeEdit = $('#fifthSemFeeEdit').val()
        var sixthSemFeeEdit = $('#sixthSemFeeEdit').val()
        var thirdYearFeeEdit = $('#thirdYearFeeEdit').val()
        var seventhSemFeeEdit = $('#seventhSemFeeEdit').val()
        var eighthSemFeeEdit = $('#eighthSemFeeEdit').val()
        var fourthYearFeeEdit = $('#fourthYearFeeEdit').val()
        var hostelAdmissionFeeEdit = $('#hostelAdmissionFeeEdit').val()
        var hostelTotalFeeEdit = $('#hostelTotalFeeEdit').val()
        var hostelRemarksEdit = $('#hostelRemarksEdit').val()
        var otherExpensesEdit = $('#otherExpensesEdit').val()
        var otherExpensesTotalEdit = $('#otherExpensesTotalEdit').val()
        var otherExpensesRemarksEdit = $('#otherExpensesRemarksEdit').val()
        var laptopExpensesEdit = $('#laptopExpensesEdit').val()
        $.ajax({
            type: "post",
            url: url('certificateTemp', 'saveAnnexerFWithoutStatus', ''),
            data: {laptopExpensesEdit:laptopExpensesEdit,otherExpensesRemarksEdit:otherExpensesRemarksEdit,otherExpensesTotalEdit:otherExpensesTotalEdit,otherExpensesEdit:otherExpensesEdit,hostelRemarksEdit:hostelRemarksEdit,hostelTotalFeeEdit:hostelTotalFeeEdit,hostelAdmissionFeeEdit:hostelAdmissionFeeEdit,fourthYearFeeEdit:fourthYearFeeEdit,eighthSemFeeEdit:eighthSemFeeEdit,seventhSemFeeEdit:seventhSemFeeEdit,thirdYearFeeEdit:thirdYearFeeEdit,sixthSemFeeEdit:sixthSemFeeEdit,fifthSemFeeEdit:fifthSemFeeEdit,secondYearFeeEdit:secondYearFeeEdit,fourthSemFeeEdit:fourthSemFeeEdit,thirdSemFeeEdit:thirdSemFeeEdit,courseFeeRemarksEdit:courseFeeRemarksEdit,firstYearFeeEdit:firstYearFeeEdit,secondSemFeeEdit:secondSemFeeEdit,firstSemFeeEdit:firstSemFeeEdit,year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit3: paragraphEdit3, paragraphEdit2: paragraphEdit2, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
            success: function (data) {
                if (data.status) {
                    $(".view").css("display", "");
                    $(".editCertificate").css("display", "none");
                    $('#btnEditDone').attr('disabled', true)
                    $('#btnPrint').attr('disabled', false)
                    $('#btnEdit').attr('disabled', false)
                    $('#sendForApproval').attr('disabled', false)
                    $('#viewFromName').text($('#editFromName').val())
                    $('#viewRefNo').text($('#editRefNo').val())
                    $('#viewFromPosition').text($('#editFromPosition').val())
                    $('#viewDate').text($('#editDate').val())
                    $('#certificateMsgHeaderView').text($('#certificateMsgHeaderEdit').val())
                    $('#paragraphView1').text($('#paragraphEdit1').val())
                    $('#paragraphView2').text($('#paragraphEdit2').val())
                    $('#paragraphView3').text($('#paragraphEdit3').val())
                    $('#signatureNameView').text($('#signatureNameEdit').val())
                    $('#firstSemFeeView').text($('#firstSemFeeEdit').val())
                    $('#secondSemFeeView').text($('#secondSemFeeEdit').val())
                    $('#firstYearFeeView').text($('#firstYearFeeEdit').val())
                    $('#courseFeeRemarksView').text($('#courseFeeRemarksEdit').val())
                    $('#thirdSemFeeView').text($('#thirdSemFeeEdit').val())
                    $('#fourthSemFeeView').text($('#fourthSemFeeEdit').val())
                    $('#secondYearFeeView').text($('#secondYearFeeEdit').val())
                    $('#fifthSemFeeView').text($('#fifthSemFeeEdit').val())
                    $('#sixthSemFeeView').text($('#sixthSemFeeEdit').val())
                    $('#thirdYearFeeView').text($('#thirdYearFeeEdit').val())
                    $('#seventhSemFeeView').text($('#seventhSemFeeEdit').val())
                    $('#eighthSemFeeView').text($('#eighthSemFeeEdit').val())
                    $('#fourthYearFeeView').text($('#fourthYearFeeEdit').val())
                    $('#hostelAdmissionFeeView').text($('#hostelAdmissionFeeEdit').val())
                    $('#hostelTotalFeeView').text($('#hostelTotalFeeEdit').val())
                    $('#hostelRemarksView').text($('#hostelRemarksEdit').val())
                    $('#otherExpensesView').text($('#otherExpensesEdit').val())
                    $('#otherExpensesTotalView').text($('#otherExpensesTotalEdit').val())
                    $('#otherExpensesRemarksView').text($('#otherExpensesRemarksEdit').val())
                    $('#laptopExpensesView').text($('#laptopExpensesEdit').val())
                }
            }
        })
    }
    else if (certificateType == 'd') {
        var refNo = $('#editRefNo').val()
        var certificateReqId = $('#certificateReqId').val()
        var editDate = $('#editDate').val()
        var editFromName = $('#editFromName').val()
        var editFromPosition = $('#editFromPosition').val()
        var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
        var paragraphEdit1 = $('#paragraphEdit1').val()
        var paragraphEdit2 = $('#paragraphEdit2').val()
        var paragraphEdit3 = $('#paragraphEdit3').val()
        var signatureNameEdit = $('#signatureNameEdit').val()
        $.ajax({
            type: "post",
            url: url('certificateTemp', 'saveAnnexerDWithoutStatus', ''),
            data: {year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit3: paragraphEdit3, paragraphEdit2: paragraphEdit2, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
            success: function (data) {
                if (data.status) {
                    $(".view").css("display", "");
                    $(".editCertificate").css("display", "none");
                    $('#btnEditDone').attr('disabled', true)
                    $('#btnPrint').attr('disabled', false)
                    $('#btnEdit').attr('disabled', false)
                    $('#sendForApproval').attr('disabled', false)
                    $('#viewFromName').text($('#editFromName').val())
                    $('#viewRefNo').text($('#editRefNo').val())
                    $('#viewFromPosition').text($('#editFromPosition').val())
                    $('#viewDate').text($('#editDate').val())
                    $('#certificateMsgHeaderView').text($('#certificateMsgHeaderEdit').val())
                    $('#paragraphView1').text($('#paragraphEdit1').val())
                    $('#paragraphView2').text($('#paragraphEdit2').val())
                    $('#paragraphView3').text($('#paragraphEdit3').val())
                    $('#signatureNameView').text($('#signatureNameEdit').val())
                }
            }
        })
    }
    else if (certificateType == 'e') {
        var refNo = $('#editRefNo').val()
        var certificateReqId = $('#certificateReqId').val()
        var editDate = $('#editDate').val()
        var editFromName = $('#editFromName').val()
        var editFromPosition = $('#editFromPosition').val()
        var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
        var paragraphEdit1 = $('#paragraphEdit1').val()
        var paragraphEdit2 = $('#paragraphEdit2').val()
        var paragraphEdit3 = $('#paragraphEdit3').val()
        var signatureNameEdit = $('#signatureNameEdit').val()
        $.ajax({
            type: "post",
            url: url('certificateTemp', 'saveAnnexerEWithoutStatus', ''),
            data: {year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit3: paragraphEdit3, paragraphEdit2: paragraphEdit2, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
            success: function (data) {
                if (data.status) {
                    $(".view").css("display", "");
                    $(".editCertificate").css("display", "none");
                    $('#btnEditDone').attr('disabled', true)
                    $('#btnPrint').attr('disabled', false)
                    $('#btnEdit').attr('disabled', false)
                    $('#sendForApproval').attr('disabled', false)
                    $('#viewFromName').text($('#editFromName').val())
                    $('#viewRefNo').text($('#editRefNo').val())
                    $('#viewFromPosition').text($('#editFromPosition').val())
                    $('#viewDate').text($('#editDate').val())
                    $('#certificateMsgHeaderView').text($('#certificateMsgHeaderEdit').val())
                    $('#paragraphView1').text($('#paragraphEdit1').val())
                    $('#paragraphView2').text($('#paragraphEdit2').val())
                    $('#signatureNameView').text($('#signatureNameEdit').val())
                }
            }
        })
    }
    else if (certificateType == 'g') {
        var refNo = $('#editRefNo').val()
        var certificateReqId = $('#certificateReqId').val()
        var editDate = $('#editDate').val()
        var editFromName = $('#editFromName').val()
        var editFromPosition = $('#editFromPosition').val()
        var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
        var paragraphEdit1 = $('#paragraphEdit1').val()
        var paragraphEdit2 = $('#paragraphEdit2').val()
        var paragraphEdit3 = $('#paragraphEdit3').val()
        var paragraphEdit4 = $('#paragraphEdit4').val()
        var signatureNameEdit = $('#signatureNameEdit').val()
        $.ajax({
            type: "post",
            url: url('certificateTemp', 'saveAnnexerGWithoutStatus', ''),
            data: {year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit3: paragraphEdit3, paragraphEdit2: paragraphEdit2,paragraphEdit4:paragraphEdit4, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
            success: function (data) {
                if (data.status) {
                    $(".view").css("display", "");
                    $(".editCertificate").css("display", "none");
                    $('#btnEditDone').attr('disabled', true)
                    $('#btnPrint').attr('disabled', false)
                    $('#btnEdit').attr('disabled', false)
                    $('#sendForApproval').attr('disabled', false)
                    $('#viewFromName').text($('#editFromName').val())
                    $('#viewRefNo').text($('#editRefNo').val())
                    $('#viewFromPosition').text($('#editFromPosition').val())
                    $('#viewDate').text($('#editDate').val())
                    $('#certificateMsgHeaderView').text($('#certificateMsgHeaderEdit').val())
                    $('#paragraphView1').text($('#paragraphEdit1').val())
                    $('#paragraphView2').text($('#paragraphEdit2').val())
                    $('#paragraphView3').text($('#paragraphEdit3').val())
                    $('#paragraphView4').text($('#paragraphEdit4').val())
                    $('#signatureNameView').text($('#signatureNameEdit').val())
                }
            }
        })
    }
    else if (certificateType == 'b') {
        var refNo = $('#editRefNo').val()
        var certificateReqId = $('#certificateReqId').val()
        var editDate = $('#editDate').val()
        var editFromName = $('#editFromName').val()
        var editFromPosition = $('#editFromPosition').val()
        var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
        var paragraphEdit1 = $('#paragraphEdit1').val()
        var paragraphEdit2 = $('#paragraphEdit2').val()
        var paragraphEdit3 = $('#paragraphEdit3').val()
        var signatureNameEdit = $('#signatureNameEdit').val()
        $.ajax({
            type: "post",
            url: url('certificateTemp', 'saveAnnexerBWithoutStatus', ''),
            data: {year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit3: paragraphEdit3, paragraphEdit2: paragraphEdit2, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
            success: function (data) {
                if (data.status) {
                    $(".view").css("display", "");
                    $(".editCertificate").css("display", "none");
                    $('#btnEditDone').attr('disabled', true)
                    $('#btnPrint').attr('disabled', false)
                    $('#btnEdit').attr('disabled', false)
                    $('#sendForApproval').attr('disabled', false)
                    $('#viewFromName').text($('#editFromName').val())
                    $('#viewRefNo').text($('#editRefNo').val())
                    $('#viewFromPosition').text($('#editFromPosition').val())
                    $('#viewDate').text($('#editDate').val())
                    $('#certificateMsgHeaderView').text($('#certificateMsgHeaderEdit').val())
                    $('#paragraphView1').text($('#paragraphEdit1').val())
                    $('#paragraphView2').text($('#paragraphEdit2').val())
                    $('#signatureNameView').text($('#signatureNameEdit').val())
                }
            }
        })
    }
    else if (certificateType == '#') {
        alert("0000000000000000000")
        var refNo = $('#editRefNo').val()
        var certificateReqId = $('#certificateReqId').val()
        var editDate = $('#editDate').val()
        var editFromName = $('#editFromName').val()
        var editFromPosition = $('#editFromPosition').val()
        var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
        var paragraphEdit1 = $('#paragraphEdit1').val()
        var paragraphEdit2 = $('#paragraphEdit2').val()
        var paragraphEdit3 = $('#paragraphEdit3').val()
        var paragraphEdit4 = $('#paragraphEdit4').val()
        var paragraphEdit5 = $('#paragraphEdit5').val()
        var signatureNameEdit = $('#signatureNameEdit').val()
        $.ajax({
            type: "post",
            url: url('certificateTemp', 'saveOthersWithoutStatus', ''),
            data: {year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit3: paragraphEdit3,paragraphEdit4: paragraphEdit4,paragraphEdit5: paragraphEdit5, paragraphEdit2: paragraphEdit2, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
            success: function (data) {
                if (data.status) {
                    $(".view").css("display", "");
                    $(".editCertificate").css("display", "none");
                    $('#btnEditDone').attr('disabled', true)
                    $('#btnPrint').attr('disabled', false)
                    $('#btnEdit').attr('disabled', false)
                    $('#sendForApproval').attr('disabled', false)
                    $('#viewFromName').text($('#editFromName').val())
                    $('#viewRefNo').text($('#editRefNo').val())
                    $('#viewFromPosition').text($('#editFromPosition').val())
                    $('#viewDate').text($('#editDate').val())
                    $('#certificateMsgHeaderView').text($('#certificateMsgHeaderEdit').val())
                    $('#paragraphView1').text($('#paragraphEdit1').val())
                    $('#paragraphView2').text($('#paragraphEdit2').val())
                    $('#paragraphView3').text($('#paragraphEdit3').val())
                    $('#paragraphView4').text($('#paragraphEdit4').val())
                    $('#paragraphView5').text($('#paragraphEdit5').val())
                    $('#signatureNameView').text($('#signatureNameEdit').val())
                }
            }
        })
    }
    else if (certificateType == 'h') {
        var refNo = $('#editRefNo').val()
        var certificateReqId = $('#certificateReqId').val()
        var editDate = $('#editDate').val()
        var editFromName = $('#editFromName').val()
        var editFromPosition = $('#editFromPosition').val()
        var certificateMsgHeaderEdit = $('#certificateMsgHeaderEdit').val()
        var paragraphEdit1 = $('#paragraphEdit1').val()
        var paragraphEdit2 = $('#paragraphEdit2').val()
        var paragraphEdit4 = $('#paragraphEdit4').val()
        var paragraphEdit5 = $('#paragraphEdit5').val()
        var signatureNameEdit = $('#signatureNameEdit').val()

        var toDesignationEdit = $('#toDesignationEdit').val()
        var toInstituteEdit = $('#toInstituteEdit').val()
        var toAddressEdit = $('#toAddressEdit').val()
        $.ajax({
            type: "post",
            url: url('certificateTemp', 'saveAnnexerHWithoutStatus', ''),
            data: {toInstituteEdit:toInstituteEdit,toAddressEdit:toAddressEdit,toDesignationEdit:toDesignationEdit,paragraphEdit5:paragraphEdit5,paragraphEdit4:paragraphEdit4,year: year, certificateNo: certificateNo, signatureNameEdit: signatureNameEdit, paragraphEdit2: paragraphEdit2, paragraphEdit1: paragraphEdit1, certificateMsgHeaderEdit: certificateMsgHeaderEdit, editFromPosition: editFromPosition, editFromName: editFromName, editDate: editDate, certificateReqId: certificateReqId, refNo: refNo},
            success: function (data) {
                if (data.status) {
                    $(".view").css("display", "");
                    $(".editCertificate").css("display", "none");
                    $('#btnEditDone').attr('disabled', true)
                    $('#btnPrint').attr('disabled', false)
                    $('#btnEdit').attr('disabled', false)
                    $('#sendForApproval').attr('disabled', false)
                    $('#viewFromName').text($('#editFromName').val())
                    $('#viewRefNo').text($('#editRefNo').val())
                    $('#viewFromPosition').text($('#editFromPosition').val())
                    $('#viewDate').text($('#editDate').val())
                    $('#certificateMsgHeaderView').text($('#certificateMsgHeaderEdit').val())
                    $('#paragraphView1').text($('#paragraphEdit1').val())
                    $('#paragraphView2').text($('#paragraphEdit2').val())
                    $('#paragraphView4').text($('#paragraphEdit4').val())
                    $('#paragraphView5').text($('#paragraphEdit5').val())
                    $('#signatureNameView').text($('#signatureNameEdit').val())
                }
            }
        })
    }

}
function loadRequiredFields(t) {
    var certificateId = $(t).val()
    if (certificateId == '7') {
        $('.experiance').css("display", "");
    }
    else if (certificateId == '6') {
        $('.internship').css("display", "");
    }
}
function calculateTotalFee(){
    var total=0
    $("input.feeAmount").each(function(){
        if($(this).val()!=''){
            total+=parseInt($(this).val())
        }
    });
    $('#totalAmount').text(total)
}
function addRollNO(){
    var rollNo=$('#rollNo').val()
    $.ajax({
        type: "post",
        url: url('certificateTemp', 'checkRollNo', ''),
        data: {rollNo: rollNo},
        success: function (data) {
            if(data.status){
                if($('#rollNoList').val()==''||$('#rollNoList').val()==null){
                    $('#rollNoList').val(rollNo)
                    $('#rollNo').val('')
                }
                else{
                    var list=$('#rollNoList').val()+","+rollNo
                    $('#rollNoList').val(list)
                }
            }
            else{
                alertPopup("Please Enter Correct Roll No.")
            }

        }
    })
}
function sendForApproval(){

}
function loadAllEmployee(t){
    var branchId=$(t).val()
    $.ajax({
        type: "post",
        url: url('admin', 'loadAllEmployeeByDept', ''),
        data: {branchId: branchId},
        success: function (data) {
            if(data.employeeIdList.length>0){
                $('#employee').empty().append('<option value="">Select Employee</option>')
                for(var i=0; i<data.employeeIdList.length;i++){
                    $('#employee').append('<option value="'+data.employeeIdList[i]+'">'+data.nameList[i]+'</option>')
                }

            }
            else{
                alertPopup("No Employee Found")
            }

        }
    })
}
function loadGuideList(t){
    var branch=$(t).val()
    $.ajax({
        type: "post",
        url: url('admin', 'loadGuideList', ''),
        data: {branch: branch},
        success: function (data) {
            if(data.employeeIdList.length>0){
                $('.guideName').empty().append('<option value="">Select Guide</option>')
                for(var i=0; i<data.guideIdList.length;i++){
                    $('.guideName').append('<option value="'+data.guideIdList[i]+'">'+data.nameList[i]+'</option>')
                }
            }
            else{
                alertPopup("No Guide Assigned Yet")
            }

        }
    })
}
function loadGuideDetails(t, index){
    var guideId=$(t).val()
    $.ajax({
        type: "post",
        url: url('admin', 'loadGuideDetails', ''),
        data: {guideId: guideId},
        success: function (data) {
            if(data.designation.length>0){
                $('#designation'+index).val(data.designation)
                $('#phnoGuide'+index).val(data.phno)
                $('#emailGuide'+index).val(data.email)
            }

        }
    })
}
function rejectGrades(){
    var subjectCode=$('#subjectCode').val()
    var academicSession=$('#academicSession').val()
    $.ajax({
        type: "post",
        url: url('postExamination', 'rejectGradeConversion', ''),
        data: {subjectCode: subjectCode,academicSession:academicSession},
        success: function (data) {
            if(data.status){
                alertPopupWithoutTime("All Grades and Evaluated Marks are deleted. Please Re-Evaluate and do the Greade Conversion")
            }
            else{
                alertPopup("deletion of Grade was not successfull")
            }

        }

    })
    setTimeout(5000)
    showEvaluatedMarksGrade()
}