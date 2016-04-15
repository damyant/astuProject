/**
 * Created by Digvijay on 3/6/14.
 */
var marksTypeId = 0;
function loadSessionForMissMatch(t) {
    var program = $(t).val();
    if (program) {
        $.ajax({
            type: "post",
            url: url('programFee', 'programSessionForMarksMissMatch', ''),
            data: {program: program},
            success: function (data) {

                $("#session").empty().append('');

                $("#session").append('<option value="">Select Session</option>');
                for (var i = 0; i < data.length; i++) {
                    $("#session").append('<option value="' + data[i].id + '">' + data[i].sessionOfProgram + '</option>')
                }
                $("#session").attr('disabled', false)
            }
        });

    }
    else {
        $("#session").empty().append('');
        $("#session").append('<option value="">Select Session</option>');
        $("#semesterList").empty().append('data <option value="">Select Semester</option>')
        $('#courseCode').empty().append('<option value="">Select Course</option>')
    }
}

function loadSemesterFromRollNo(t) {
    var rollNo = $(t).val()
    if (rollNo.length > 8) {
        $.ajax({
            type: "post",
            url: url('postExamination', 'loadSemesterFromRollNo', ''),
            data: {rollNo: rollNo},
            success: function (data) {
                if (data) {
                    $('#semester').empty().append('<option value="">Select Semester</option>')
                    for (var i = 0; i < data.length; i++) {
                        $('#semester').append('<option value="' + data[i].id + '">' + data[i].semesterNo + '</option>')
                    }
                }
            }
        })
    }
}

function loadSemesterFromProgram(t){
    var prog = $(t).val()
        $.ajax({
            type: "post",
            url: url('postExamination', 'loadSemesterFromProgram', ''),
            data: {prog: prog},
            success: function (data) {
                if (data) {
                    $('#semester').empty().append('<option value="">Select Semester</option>')
                    for (var i = 0; i < data.length; i++) {
                        $('#semester').append('<option value="' + data[i].id + '">' + data[i].semesterNo + '</option>')
                    }
                }
            }
        })
}

function loadSession(t) {
    var program = $(t).val();
    if (program) {
        $.ajax({
            type: "post",
            url: url('programFee', 'getProgramSession', ''),
            data: {program: program},
            success: function (data) {
                if ($('#SessionList').length > 0) {
                    $("#SessionList").empty().append('<option value="">Select Session</option>');
                    for (var i = 0; i < data.length; i++) {
                        $("#SessionList").append('<option value="' + data[i].id + '">' + data[i].sessionOfProgram + '</option>')
                    }
                    $("#SessionList").attr('disabled', false)
                }
                if ($('#session').length > 0) {
                    $("#session").empty().append('<option value="">Select Session</option>');
                    for (var i = 0; i < data.length; i++) {
                        $("#session").append('<option value="' + data[i].id + '">' + data[i].sessionOfProgram + '</option>')
                    }
                    $("#session").attr('disabled', false)
                }
            }
        });

    }
    else {
        $("#session").empty().append('');
        $("#session").append('<option value="">Select Session</option>');
        $("#semesterList").empty().append('data <option value="">Select Semester</option>')
        $('#courseCode').empty().append('<option value="">Select Course</option>')
    }
}

//function loadSemester(){
//    var data = $('#programId').val();
//    if(data){
//        $.ajax({
//            type: "post",
//            url: url('admitCard', 'getSemesterList', ''),
//            data: {data: data},
//            success: function (data) {
//                $("#semesterList").empty().append('data <option value="">Select Semester</option>')
//                $("#SessionList").empty().append('data <option value="">Select Session</option>')
//                $('#courseCode').empty().append('<option value="">Select Course</option>')
//                for (var i = 1; i <= data.totalSem; i++) {
//                    $("#semesterList").append('<option value="' + i + '">' + i + '</option>')
//                }
//                for (var i = 0; i < data.session.length; i++) {
//                    $("#SessionList").append('<option value="' + data.session[i].id + '">' + data.session[i].sessionOfProgram + '</option>')
//                }
//            }
//        })
//    }
//    else{
//        $("#semesterList").empty().append('data <option value="">Select Semester</option>')
//        $('#courseCode').empty().append('<option value="">Select Course</option>')
//    }
//}

function loadProgramCourse() {
    var program = $('#programId').val();
    var semester = $('#semesterList').val();
    $.ajax({
        type: "post",
        url: url('postExamination', 'getCourses', ''),
        data: {program: program, semester: semester},

        success: function (data) {
            $("#courseCode").empty().append('<option value="">Select Course</option>')
            if (data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    $("#courseCode").append('<option value="' + data[i].id + '">' + data[i].subjectName + '</option>')
                }
            }
        }
    });

    $("#courseCode").attr('disabled', false)
}
function loadMarksType(t) {
    var program = $('#programId').val();
    var semester = $('#semesterList').val();
    var subjectID = $(t).val();
    $.ajax({
        type: "post",
        url: url('postExamination', 'loadMarksType', ''),
        data: {subjectID: subjectID},
        success: function (data) {
            $('#marksType').empty().append('<option value="">Select Marks Type</option>')
            for (var i = 0; i < data.marksTypeList.length; i++) {
                $('#marksType').append('<option value="' + data.marksTypeList[i].id + '">' + data.marksTypeList[i].marksTypeName + '</option>')
            }
        }
    })

}

function enableMarksType() {
    $("#marksType").attr('disabled', false)
    $("#pdfButton").attr('disabled', false)
    $("#excelButton").attr('disabled', false)
    $("#cancelButton").attr('disabled', false)

}

function enableButton() {
    if ($("#marksType").val() == 0) {
        $("#setButton").attr('disabled', true)
    } else {
        $("#setButton").attr('disabled', false)
    }

}
function enableSubmitButton() {
    if ($("#academicSession").val() == '') {
        $("#evaluatedMarksShowDetails").attr('disabled', true)
    } else {
        $("#evaluatedMarksShowDetails").attr('disabled', false)
    }
}


function loadCourse() {
    var program = $('#programId').val();
    var session = $('#SessionList').val();
    var semester = $('#semesterList').val();
    var groupType = $("#groupList").val();
    $.ajax({
        type: "post",
        url: url('postExamination', 'getCourseData', ''),
        data: {program: program, session: session, semester: semester, groupType: groupType},

        success: function (data) {

            $('#courseCode').empty().append('<option value="">Select Course</option>')
            for (i = 0; i < data.length; i++) {
                $('#courseCode').append('<option value="' + data[i].id + '">' + data[i].subjectName + '</option>')

            }
        }
    });
    $("#courseCode").attr('disabled', false)
}


function loadSemester() {

    var program = $('#programId').val();
    var session = $('#SessionList').val();
//    var semester = $('#semesterList').val();
//    var groupType= $("#groupList").val();
    $.ajax({
        type: "post",
        url: url('postExamination', 'getSemesterOfProgram', ''),
        data: {program: program, session: session},

        success: function (data) {

            $('#semesterList').empty().append('<option value="">Select Semester</option>')
            for (i = 0; i < data.length; i++) {
                $('#semesterList').append('<option value="' + data[i].id + '">' + data[i].semesterNo + '</option>')

            }
            $("#semesterList").attr('disabled', false)
        }
    });

    $("#semesterList").attr('disabled', false)

}


function loadSemesterForMissMatch() {

    var program = $('#programId').val();
    var session = $('#session').val();
//    var semester = $('#semesterList').val();
//    var groupType= $("#groupList").val();

    $.ajax({
        type: "post",
        url: url('postExamination', 'semesterForMarksMisMatch', ''),
        data: {program: program, session: session},

        success: function (data) {

            $('#semesterList').empty().append('<option value="">Select Semester</option>')
            for (i = 0; i < data.length; i++) {
                $('#semesterList').append('<option value="' + data[i].id + '">' + data[i].semesterNo + '</option>')

            }
            $("#semesterList").attr('disabled', false)
        }
    });

    $("#semesterList").attr('disabled', false)

}


function loadSemester() {

    var program = $('#programId').val();
    var session = $('#SessionList').val();
//    var semester = $('#semesterList').val();
//    var groupType= $("#groupList").val();
    $.ajax({
        type: "post",
        url: url('postExamination', 'getSemesterOfProgram', ''),
        data: {program: program, session: session},

        success: function (data) {

            $('#semesterList').empty().append('<option value="">Select Semester</option>')
            for (i = 0; i < data.length; i++) {
                $('#semesterList').append('<option value="' + data[i].id + '">' + data[i].semesterNo + '</option>')

            }
            $("#semesterList").attr('disabled', false)
        }
    });

    $("#semesterList").attr('disabled', false)

}


function loadMismatchStudents() {
//    alert("hello kuldeep")

}

$(document).ready(function () {
    $('#pdfButton').on('click', function () {
        $("#btn").val("pdf")
        $("#marksFoilId").submit()
    })

    $('#excelButton').on('click', function () {
        $("#btn").val("excel")
        $("#marksFoilId").submit()
    })

    $("#SessionList").on('change', function () {
        $("#semesterList").removeAttr('disabled')
    })

    $("#missMatchedButton").on('click', function () {

        $("#marksMissMatchForm").submit()
    })

})
function populateStudentListForMarksUpdate() {
    var program = $('#programId').val();
    var session = $('#SessionList').val();
    var semester = $('#semesterList').val();
    var groupType = $("#groupList").val();
    var subjectId = $("#courseCode").val()
    var marksType = $("#marksType").val()
    $.ajax({
        type: "post",
        url: url('postExamination', 'marksMissMatchUpdate', ''),
        data: {programId: program, session: session, semester: semester, groupType: groupType, subjectId: subjectId, marksType: marksType},
        success: function (data) {
            $('#rollNoList').empty()
            if (data.length > 0) {
                $('#rollNoList').append('<option value="">Select Roll Number</option>')
                for (var i = 0; i < data.length; i++) {
                    $('#rollNoList').append('<option value="' + data[i].id + '">' + data[i].rollNo + '</option>')
                }
//                $('#rollNoList').prop('disabled', false)
            }
            else {
                $("<div></div>").html("<div style='text-align: justify;font-size: 12px;'><p>No Roll numbers found.</p></div>").dialog({
                    title: "Sorry",
                    resizable: false,
                    modal: true,
                    buttons: {
                        "Ok": function () {
                            $(this).dialog("close");
                        }
                    }
                });
            }

        }
    });

}
function loadTabulatorMarks() {
    var studentId = $('#rollNoList').val()
    var subjectId = $("#subjectCode").val()
    $.ajax({
        type: "post",
        url: url('postExamination', 'loadTabulatorMarks', ''),
        data: {studentId: studentId,subjectId: subjectId},
        success: function (data) {
            if (data.totalMarks) {
                $('#marksValue').val(data.totalMarks)
            }
        }
    })
}
function populateStudentListForMarks() {
    var academicSession = $('#academicSession').val();
    var subjectId = $("#subjectCode").val()
    var examination = $("#examination").val()
    var entryType = $('input[name="entryType"]:checked').val()
    $.ajax({
        type: "post",
        url: url('postExamination', 'getRollNoList', ''),
        data: {academicSession: academicSession, subjectId: subjectId,examination:examination,entryType:entryType},
        success: function (data) {
            $('#rollNoList').empty()
            if (data.studentList) {

                for (var i = 0; i < data.studentList.length; i++) {
                    $('#rollNoList').append('<option value="' + data.studentList[i].id + '">' + data.studentList[i].rollNo + '</option>')
                }
                document.getElementById("dataTable").style.visibility = "visible";
                document.getElementById("buttonDiv").style.visibility = "visible";
                $("#rollNoList option:first").attr('selected', 'selected');
                $('#marksValue').focus();
                $('#alreadyEnteredMarks').empty()
                for (var j = 0; j < data.MarksEnteredList.length; j++) {
                    $('#alreadyEnteredMarks').append('<option value="' + data.studentRollNo[j]+ '">' + data.studentRollNo[j]+ '-->'+data.MarksEnteredList[j].totalMarks+'</option>')
                }
            }
            else {
                sorryPopup("No Roll numbers found.")
            }

        }
    });
}

function updateMisMatchMarks() {
    validationPostExam()
    var result = $('#marksUpdate').valid()
    if (result) {
        var studentId = $('#rollNoList').val()
        var program = $('#programId').val();
        var session = $('#SessionList').val();
        var semester = $('#semesterList').val();
        var groupType = $("#groupList").val();
        var subjectId = $("#courseCode").val()
        var marksType = $("#marksType").val()
        var updatedMarks = $("#updatedMarks").val()
        $.ajax({
            type: "post",
            url: url('postExamination', 'updateMisMatchMarks', ''),
            data: {updatedMarks: updatedMarks, studentId: studentId, programId: program, session: session, semester: semester, groupType: groupType, subjectId: subjectId, marksType: marksType},
            success: function (data) {
                if (data.status) {
                    successPopup("Marks Updated Succesfully")

                    $('#tab1Marks').val('')
                    $('#tab2Marks').val('')
                    $('#updatedMarks').val('')
                }
            }
        })
        populateStudentListForMarksUpdate()
    }
}

function setSessions() {
    $.ajax({
        type: "post",
        url: url('report', 'getSessionList', ''),
        async: false,
        data: '',
        success: function (data) {
            $(".allSession").empty().append('')
            for (var i = 0; i < data.length; i++) {
                $(".allSession").append('<option value="' + data[i] + '">' + data[i] + '-' + (data[i] + 1) + '</option>')
            }
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("response in error")
        }

    });
}


function updateMarksType(id) {
    marksTypeId = id
    alert(marksTypeId)
    window.location.href = '/UniversityProject/marksType/createMarksType?marksTypeId=' + marksTypeId;
}
function deleteMarksType(id) {
    marksTypeId = id
//    alert(bankId)
    window.location.href = '/UniversityProject/marksType/deleteMarksType?marksTypeId=' + marksTypeId;
}


function saveMarks() {
    if ($("#rollNoList").val()) {
        if($("#marksValue").val()!=''){
            $.ajax({
                type: "post",
                url: url('postExamination', 'saveMarks', ''),
                data: {academicSession:$('#academicSession').val(),
                    subjectId: $("#subjectCode").val(), examination: $("#examination").val(), student: $("#rollNoList").val(), marksValue: $("#marksValue").val()},
                success: function (data) {

                    $("#marksValue").val('')
                    populateStudentListForMarks()

                }
            });
        }
        else{
            alertPopupWithoutTime("Can Not Save Blank Value. Please Enter Number or 'A' for Absent.")
        }
    }
    else {
        alertPopup(" There is no roll numbers to enter marks")
    }


}
function enableExamSession(){
    $( "#session" ).prop( "disabled", false );
}

function enableSession() {

    $.ajax({
        type: "post",
        url: url('postExamination', 'getStudentSession', ''),
        success: function (data) {
            $('#studentSession').empty().append('<option value="">Select Student Session</option>')
            if (data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    $('#studentSession').append('<option value="' + data[i] + '">' + data[i] + '</option>')
                }
                $("#studentSession").attr('disabled', false)

            }

        }
    });


}

function disableAllSelectBox() {

    $("select").attr("disabled", true);
    $("input[type='radio']").attr("disabled", true);
    $("#SubjectName").attr("disabled", true);
    $("#subjectCode").attr("disabled", true);
    $("#showButton").attr("disabled", false)
    $("#resetButton").attr("disabled", false)
    $("#setButton").attr("disabled", true)
    $("#rollNoList").attr("disabled", false);

}

function enableAllSelectBox() {
    $("select").attr("disabled", false);
    $("input[type='radio']").attr("disabled", false);
    $("#SubjectName").attr("disabled", false);
    $("#subjectCode").attr("disabled", false);
    $("#showButton").attr("disabled", true)
    $("#resetButton").attr("disabled", true)
    $("#setButton").attr("disabled", false)

//    $("#rollNoList").attr("disabled",true)

    document.getElementById("dataTable").style.visibility = "hidden";
    document.getElementById("buttonDiv").style.visibility = "hidden";
}

function enableButtonOfMissMatch() {
    $("input[type=button]").removeAttr("disabled");

}


function matchMarks() {
    if ($("#marksValue").length != 0) {
        if ($("#marksValue").val().length > 1) {
            $.ajax({
                type: "post",
                url: url('postExamination', 'checkMarks', ''),
                data: {program: $('#programId').val(), session: $('#SessionList').val(), semester: $('#semesterList').val(), groupType: $("#groupList").val(),
                    subjectId: $("#courseCode").val(), marksType: $("#marksType").val(), rollNoId: $("#rollNoList").val(), marksValue: $("#marksValue").val()},
                success: function (data) {

                    if (data.status == false) {
                        alertPopup("Current entered marks did not match,the marks entered by " + data.tabulator)

                    }
                }
            });
        }
    }
    else {
        alert(" There is no roll numbers to enter marks")
    }

}
function getTabulatorSession(t) {
    var program = $(t).val()
    $.ajax({
        type: "post",
        url: url('postExamination', 'getTabulatorSession', ''),
        data: {program: program},
        success: function (data) {
            if (data.session) {
                $('#SessionList').prop('disabled', false)
                $("#SessionList").empty().append('data <option value="">Select Session</option>')
                for (var j = 0; j < data.session.length; j++) {
                    $("#SessionList").append('<option value="' + data.session[j].id + '">' + data.session[j].sessionOfProgram + '</option>')
                }
            }

        }
    })
}
function getSemesterForMarksUpdate(t) {
    var program = $('#programId').val()
    var session = $(t).val()
    $.ajax({
        type: "post",
        url: url('postExamination', 'getSemesterForMarksUpdate', ''),
        data: {program: program, session: session},
        success: function (data) {
            if (data.semesterList) {
                $('#semesterList').prop('disabled', false)
                $('#semesterList').empty().append("<option value=''>Select Semester</option>")

                for (var i = 0; i < data.semesterList.length; i++) {
                    $('#semesterList').append("<option value='" + data.semesterList[i].id + "'>" + data.semesterList[i].semesterNo + "</option>")
                }
            }
        }
    });
}
function generateResults() {
    var program = $('#programId').val()
    var sessionId = $('#SessionList').val()
    var semesterId = $('#semesterList').val()
    $.ajax({
        type: "post",
        url: url('postExamination', 'generateResults', ''),
        data: {program: program, session: session},
        success: function (data) {
            if (data.status) {

            }
            else {
                sorryPopup(data.msg)
            }

        }
    })
}
function getTabulatorSemester(t) {
    var program = $('#programId').val()
    var session = $(t).val()
    $.ajax({
        type: "post",
        url: url('postExamination', 'getTabulatorSemester', ''),
        data: {program: program, session: session},
        success: function (data) {
            if (data.tabSemesterList) {
                $('#semesterList').prop('disabled', false)
                $('#semesterList').empty().append("<option value=''>Select Semester</option>")

                for (var i = 0; i < data.tabSemesterList.length; i++) {
                    for (var j = 0; j < data.tabSemesterList[i].length; j++) {
                        $('#semesterList').append("<option value='" + data.tabSemesterList[i][j].id + "'>" + data.tabSemesterList[i][j].semesterNo + "</option>")
                    }
                }
//
            }

        }
    })
}
function alertPopup(data) {
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
    }, 3000);
}
function alertPopupWithoutTime(data) {
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
}
function alertPopupWithoutTimeRefress(data) {
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
    location.reload()
}

function sorryPopup(data) {
    var dialog = $("<div></div>").html("<div style='text-align: justify;font-size: 12px;'><p>" + data + ".</p></div>").dialog({
        title: "Sorry",
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
    }, 3000);
}

function successPopup(data) {
    var dialog = $("<div></div>").html("<div style='text-align: justify;font-size: 12px;'><p>" + data + ".</p></div>").dialog({
        title: "Success",
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
    }, 3000);
}
function loadVenueProgram(t) {
    var venue = $(t).val()
    $.ajax({
        type: "post",
        url: url('postExamination', 'loadVenueProgram', ''),
        data: {venue: venue},
        success: function (data) {
            if (data) {
                $('#programId').empty().append('<option value="">Select Program</option>')
                for (var i = 0; i < data.length; i++) {
                    $('#programId').append('<option value="' + data[i].id + '">' + data[i].courseName + '</option>')
                }
            }
        }
    })
}
function enableLoadAbsentee() {
    $('#loadAbsentee').prop('disabled', false)
}
function loadAbsenteeRollNo() {
    var examVenue = $('#examVenue').val();
    var session = $('#SessionList').val();
    var semester = $('#semesterList').val();
//    var groupType= $("#groupList").val();
//    var subjectId=$("#courseCode").val()
    $.ajax({
        type: "post",
        url: url('postExamination', 'loadAbsenteeRollNo', ''),
        data: {examVenue: examVenue, session: session, semester: semester},
        success: function (data) {
            if (data) {
                $('#canselect_code').empty()
                for (var i = 0; i < data.length; i++) {
                    $('#canselect_code').append('<option value="' + data[i].id + '">' + data[i].rollNo + '</option>')
                }
                $('#canselect_code').prop('disabled', false)
                $('#add').prop('disabled', false)
                $('#remove').prop('disabled', false)
                $('#isselect_code').prop('disabled', false)
                $('#absenteeSave').prop('disabled', false)
                $('#absenteeCancel').prop('disabled', false)
            }
        }
    })
}
function valueInBox() {
    $('#canselect_code').find('option:selected').remove().appendTo('#isselect_code');

//    for (var i = 0; i < $('#canselect_code').find('option:selected').length; i++) {
//        if ($('#studentAddedList').val() == '') {
//            $('#studentAddedList').val($('#canselect_code').find('option:selected').val())
//        }
//        else{
//            $('#studentAddedList').val($('#studentAddedList').val()+","+$('#canselect_code').find('option:selected').val())
//        }
//    }
}
function valueOutBox() {
    $('#isselect_code').find('option:selected').remove().appendTo('#canselect_code');
}
function checkFileExtension() {
    var FileUploadPath = $("#homeAssignment").val()

    var Extension = FileUploadPath.substring(FileUploadPath.lastIndexOf('.') + 1).toLowerCase();
    if (Extension == "xls") {
//        alertPopup(Extension)
    }
    else {
        sorryPopup("Please Upload a Excel File with xls Extension")
    }

}
function getProgramSemester(t) {
    var program = $(t).val();
    $.ajax({
        type: "post",
        url: url('postExamination', 'getProgramSemester', ''),
        data: {program: program},
        success: function (data) {
            if (data) {
                $('#semesterList').empty().append('<option value="">Select Semester </option>')
                $('#semesterList').prop('disabled', false)
                for (var i = 0; i < data.length; i++) {
                    $('#semesterList').append('<option value="' + data[i].id + '">' + data[i].semesterNo + '</option>')
                }
            }
            else {
                $('#semesterList').prop('disabled', true)
            }
        }
    })
}
function loadSessionSemester(t) {

    var academicSession = $(t).val();
    var program = $('#programId').val();
    $.ajax({
        type: "post",
        url: url('postExamination', 'getProgramSessionSemester', ''),
        data: {program: program, academicSession: academicSession},
        success: function (data) {
            if (data) {

                $('#semesterList').empty().append('<option value="">Select Semester </option>')
                $('#semesterList').prop('disabled', false)
                for (var i = 0; i < data.length; i++) {
                    $('#semesterList').append('<option value="' + data[i].id + '">' + data[i].semesterNo + '</option>')
                }
            }
            else {
                $('#semesterList').prop('disabled', true)
            }
        }
    })
}


function loadProgramCourseByAcademic(t){
    var semester = $(t).val();
    var academicSession = $('#academicSession').val();
    var program = $('#programId').val();
    $.ajax({
        type: "post",
        url: url('postExamination', 'getProgramSessionSemesterCourse', ''),
        data: {semester:semester,program: program, academicSession: academicSession},
        success: function (data) {
            if (data) {
                $('#courseName').empty().append('<option value="">Select Course </option>')
                $('#courseName').prop('disabled', false)
                for (var i = 0; i < data.length; i++) {
                    $('#courseName').append('<option value="' + data[i].id + '">' + data[i].subjectName + '</option>')
                }
            }
            else {
                $('#courseName').prop('disabled', true)
            }
        }
    })
}
function loadExaminationBySession(t){
    var course = $('#subjectCode').val();
    var academicSession = $(t).val();
    $.ajax({
        type: "post",
        url: url('postExamination', 'getExaminationBySession', ''),
        data: {course:course,academicSession: academicSession},
        success: function (data) {
            if (data) {
                $('#examination').empty().append('<option value="">Select Examination</option>')
                $('#examination').prop('disabled', false)
                for (var i = 0; i < data.idList.length; i++) {
                    $('#examination').append('<option value="' + data.idList[i]+ '">' + data.SubTypeName[i] + '</option>')
                }
            }
            else {
                $('#examination').prop('disabled', true)
            }
        }
    })
}
function loadExamTypeBySession(t){
    var course = $(t).val();
    var semester = $('#semesterList').val();
    var academicSession = $('#academicSession').val();
    var program = $('#programId').val();
    $.ajax({
        type: "post",
        url: url('postExamination', 'getExamTypeBySession', ''),
        data: {course:course,semester:semester,program: program, academicSession: academicSession},
        success: function (data) {
            if (data) {
                $('#examType').empty().append('<option value="">Select Examination Type</option>')
                $('#examType').prop('disabled', false)
                for (var i = 0; i < data.length; i++) {
                    $('#examType').append('<option value="' + data[i].id + '">' + data[i].examTypeName + '</option>')
                }
            }
            else {
                $('#examType').prop('disabled', true)
            }
        }
    })
}
function loadExamSubTypeBySession(t){
    var examType = $(t).val();
    var course = $('#courseName').val();
    var semester = $('#semesterList').val();
    var academicSession = $('#academicSession').val();
    var program = $('#programId').val();
    $.ajax({
        type: "post",
        url: url('postExamination', 'getExamSubTypeBySession', ''),
        data: {examType:examType,course:course,semester:semester,program: program, academicSession: academicSession},
        success: function (data) {
            if (data) {
                $('#examSubType').empty().append('<option value="">Select Examination Type</option>')
                $('#examSubType').prop('disabled', false)
//                $('#showButton').prop('disabled', false)
                $('#setButton').prop('disabled', false)
//                $('#resetButton').prop('disabled', false)
                for (var i = 0; i < data.length; i++) {
                    $('#examSubType').append('<option value="' + data[i] + '">' + data[i] + '</option>')
                }
            }
            else {
                $('#examSubType').prop('disabled', true)
//                $('#showButton').prop('disabled', true)
                $('#setButton').prop('disabled', true)
//                $('#resetButton').prop('disabled', true)
            }
        }
    })
}
function loadTotalMarks(t){
    var exam = $(t).val();
    $.ajax({
        type: "post",
        url: url('postExamination', 'getTotalMarks', ''),
        data: {exam:exam},
        success: function (data) {
            if (data) {
                $('#setButton').prop('disabled', false)
                $('#totalMarks').val(data.totalMarks)
//
            }
            else {
                $('#setButton').prop('disabled', true)
                $('#totalMarks').val('')
            }
        }
    })
}
function checkMarks(t){
    var mark = $(t).val();
    var totalMarks=$('#totalMarks').val()

    if(mark=='A' ||mark=='a' ){
        $('#Save_Marks').prop('disabled', false)
        $('#marksError').text('')
    }
    else if(parseInt(mark)>=0 && parseInt(mark)<999){
        $('#Save_Marks').prop('disabled', false)
        if(parseInt(mark)>parseInt(totalMarks)){
            $('#Save_Marks').prop('disabled', true)
            $('#marksError').text('Maximum Marks is : '+totalMarks+'.')
        }
        else{
            $('#Save_Marks').prop('disabled', false)
            $('#marksError').text('')
        }
    }
    else{
        alertPopupWithoutTime("Only Numbers and 'A' for Absent is allowed")
        $('#Save_Marks').prop('disabled', true)
        $('#marksError').text('')
    }

}
function enableEvaluatedMarksShowDetails(t){
    var mark = $(t).val();
    if(mark!=''){
        $('#evaluatedMarksShowDetails').prop('disabled', false)
    }
    else{
        $('#evaluatedMarksShowDetails').prop('disabled', true)
    }

}
function AddSubType(idNo){
    $('#subType'+idNo).css("display", "");
}
function addMoreSubType(fieldNo){
    var latestFieldNo=parseInt(fieldNo)+1
    var status=true
    $('.moreSubType'+fieldNo).each(function(index){
        if($(this).val()!=''){
            status=false
        }
    })
    if(status){
        alertPopupWithoutTime("Please Enter Existing Sub Type First.")
    }
    else{
        $('#addSubType'+fieldNo).append("<tr><td><input type='text' name='moreSubType"+fieldNo+"'  class='moreSubType"+fieldNo+"' placeholder='Enter Sub Type Name' class='university-size-2-3'/></td><td></td></tr>")
    }
}
function addMoreSubExamType(){
    var status=true
    $('.forTotalOf').each(function(index){
        if($(this).val()==''){
            status=false
        }
    })
    $('.ruleVariable').each(function(index){
        if($(this).val()==''){
            status=false
        }
    })
    $('.ruleExpression').each(function(index){
        if($(this).val()==''){
            status=false
        }
    })
    var index1=1
    while($('#forTotalOf'+index1).length!=0){
        index1++
    }
    if(status==true){
        $('#totalCalculation tbody').append('<tr><td>Rule For Total</td><td><select name="forTotalOf" id="forTotalOf'+index1+'" class="university-size-1-1 forTotalOf"> <option value=""> Select Exam Type</option> </select></td>' +
        '<td><input type="text" name="ruleVariable" placeholder="Enter Variable" maxlength="1" onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)" class="university-size-1-3 ruleVariable"></td>' +
        '<td><input type="text" name="ruleExpression" placeholder="Enter Rule Expression" class="university-size-1-1 ruleExpression"></tr>')
        $.ajax({
            type: "post",
            url: url('postExamination', 'getExamList', ''),
            data: {},
            success: function (data) {
                if (data) {
                    //$('input[name="forTotalOf"][value=""]').append("<option value=''>Select Exam Type</option>")
                    for (var i=0;i<data.length;i++){
                        $('#forTotalOf'+index1).append("<option value='"+data[i].id+"'>"+data[i].examTypeName+"</option>")
                    }
//
                }
            }
        })
    }
    else{
        alertPopup("Please Enter Previous Fields.")
    }

}
function addMoreMainRule(){
    var status=true
    $('.ruleName').each(function(index){
        if($(this).val()==''){
            status=false
        }
    })
    $('.ruleVariable').each(function(index){
        if($(this).val()==''){
            status=false
        }
    })
    $('.ruleExpression').each(function(index){
        if($(this).val()==''){
            status=false
        }
    })
    $('.ruleMaxVariable').each(function(index){
        if($(this).val()==''){
            status=false
        }
    })
    if(status==true){
        $('#totalCalculation tbody').append('<tr><td><input type="text" name="ruleName" placeholder="Enter Rule Name" maxlength="100" class="university-size-1-1 ruleName"/></td>'+
        '<td><input type="text" name="ruleVariable" placeholder="Enter Variable"  maxlength="1"  onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)" class="university-size-1-3 ruleVariable"/></td>'+
        '<td><input type="text" name="ruleMaxVariable" placeholder="Enter Variable"  maxlength="1"  onkeypress="return isCapitalAlphabets(event)" onchange="checkForUniqueSymbol(this)" class="rsuniveity-size-1-3 ruleMaxVariable"/></td>'+
        '<td><input type="text" name="ruleExpression" placeholder="Enter Rule Expression"   class="university-size-1-1 ruleExpression"/></td><td></td></tr>')
    }
    else{
        alertPopup("Please Enter Previous Fields.")
    }

}
