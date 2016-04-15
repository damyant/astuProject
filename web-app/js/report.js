
$(function() {
    $(".dialog").dialog({
        autoOpen: false,
        draggable: false,
        position: ['center',150],
//        maxWidth:600,
//        maxHeight: 500,
        width: 600,
        resizable: false,
        height: 210,
        modal: true,
        title:'Enter Details',
        close: function(ev, ui) {
            $.unblockUI();
            location.reload();
//            getStudentsList()
        }

    });
    $("#feeToDate").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd/mm/yy",
        maxDate: 0
    });
    $("#feeFromDate").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd/mm/yy",
        maxDate: 0
    });
    $('#studyCentreFeeFromDate').datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd/mm/yy",
        maxDate: 0
    });
    $('#studyCentreFeeToDate').datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd/mm/yy",
        maxDate: 0
    });
    $('#dailyAdmissionFromDate').datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd/mm/yy",
        maxDate: 0
    });
    $('#dailyAdmissionToDate').datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd/mm/yy",
        maxDate: 0
    });
    $('#paymentModeFromDate').datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd/mm/yy",
        maxDate: 0
    });
    $('#paymentModeToDate').datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "dd/mm/yy",
        maxDate: 0
    });


  setSessions()
  setTerm()


    $('#approvedStudentList').on('click', function(){
//        alert("clicked")
        openPopUp(1)
    })
    $('#unapprovedStudentList').on('click', function(){
//        alert("clicked")
        openPopUp(2)
    })
    $('#approvedSemesterRegistrationInExcel').on('click', function(){
//        alert("clicked")
        openPopUp(3)
    })
    $('#approvedRegistrationByFA').on('click', function(){
//        alert("clicked")
        openPopUp(4)
    })
    $('#studentsByCategory').on('click', function(){
//        alert("clicked")
        openPopUp(5)
    })
    $('#categoryGraph').on('click', function(){
//        alert("clicked")
        openPopUp(6)
    })
    $('#studentsByGender').on('click', function(){
//        alert("clicked")
        openPopUp(7)
    })
    $('#studentsCategory').on('click', function(){
//        alert("clicked")
        openPopUp(8)
    })
    $('#studentInfoByCourse').on('click', function(){
//        alert("clicked")
        openPopUp(9)
    })
    $('#registrationDetails').on('click', function(){
//        alert("clicked")
        openPopUp(10)
    })
    $('#projectThesis').on('click', function(){
//        alert("clicked")
        openPopUp(11)
    })

    $('#studentAttendance').on('click', function(){
//        alert("clicked")
        openPopUp(12)
    })

    $('#regDetailsWithBack').on('click', function(){
//        alert("clicked")
        openPopUp(13)
    })
    $('#studyCentreStudentDetails').on('click', function(){
//        alert("clicked")
        openPopUp(25)
    })

    $('#grade').on('click', function(){
//        alert("clicked")
        openPopUp(14)
    })

    $('#categoryGenderStudentList').on('click', function(){
//        alert("clicked")
        openPopUp(15)
    })
    $('#sessionStudentList').on('click', function(){
//        alert("clicked")
        openPopUp(16)
    })

    $('#studentInfo').on('click', function(){
//        alert("clicked")
        openPopUp(17)
    })
    $('#passoutStudents').on('click', function(){
//        alert("clicked")
        openPopUp(18)
    })
    $('#empDetails').on('click', function(){
//        alert("clicked")
        openPopUp(19)
    })
    $('#foreignStudent').on('click', function(){
//        alert("clicked")
        openPopUp(20)
    })

    $('#minorityStudent').on('click', function(){
//        alert("clicked")
        openPopUp(21)
    })
    $('#subjectStudent').on('click', function(){
//        alert("clicked")
        openPopUp(22)
    })
    $('#dailyAdmissionReport').on('click', function(){
//        alert("clicked")
        openPopUp(23)
    })
    $('#byPaymentMode').on('click', function(){
//        alert("clicked")
        openPopUp(24)
    })

});





function openPopUp(value){
//    alert(value)
    if(value==1){

        $('#flagValue').val('studentApproved')
        $('#inExcel').val('')
        $('tr').hide()
        $("#studentApproved1").show()
        $("#studentApproved2").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
   else if(value==2){
//        alert("condition is true")
        $('#flagValue').val('studentUnapproved')
        $('#inExcel').val('')
        $('tr').hide()
        $("#studentApproved1").show()
        $("#studentApproved2").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==3){
        $('#flagValue').val('approvedSemesterRegistrationInExcel')
//        alert("condition is true")
        $('tr').hide()
        $("#program1").show()
        $("#session1").show()
        $("#semester1").show()
        $("#format").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==4){

        $('#flagValue').val('approvedRegistrationByFA')

        $('tr').hide()

        $("#program1").show()
        $("#session1").show()
        $("#semester1").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==5){

        $('#flagValue').val('studentsByCategory')
        $('tr').hide()
        $("#studentApproved1").show()
        $("#studentApproved2").show()
        $("#gender").show()
        $("#category").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==6){
        $('tr').hide()
        $('#flagValue').val('categoryGraph')
        $("#studentApproved2").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }
    else if(value==7){
        $('tr').hide()
        $('#flagValue').val('studentsByGender')
        $("#studentApproved1").show()
        $("#studentApproved2").show()
        $("#radioGender").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }
    else if(value==8){
        $('tr').hide()
        $('#flagValue').val('studentsCategory')
        $("#studentApproved1").show()
        $("#studentApproved2").show()
        $("#radioCategory").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }
    else if(value==9){
        $('tr').hide()
        $('#flagValue').val('studentInfoByCourse')
        $("#studentApproved1").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }
    else if(value==10){
        $('tr').hide()
        $('#flagValue').val('registrationDetails')
        $("#program1").show()
        $("#session1").show()
        $("#semester1").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==11){
        $('tr').hide()
        $('#flagValue').val('projectThesis')
        $('#academicSessionBox').show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')

    }

    else if(value==12){
        $('tr').hide()
        $('#flagValue').val('studentAttendance')
        $("#program1").show()
        $("#session1").show()
        $("#semester1").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')

    }
    else if(value==13){

        $('tr').hide()
        $('#flagValue').val('regDetailsWithBack')
        $("#program1").show()
        $("#session1").show()
        $("#semester1").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')

    }

    else if(value==14){

        $('#flagValue').val('grade')
        $('tr').hide()
        $("#academicSessionBox").show()
        $("#courseInstruct").show()

        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }
    else if(value==15){
//                alert("condition is true" + value)
        $('tr').hide()
        $('#flagValue').val('categoryStudentList')
        $("#categoryStudentList").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==16){
//        alert("condition is true")
        $('#inExcel').val('true')
        $('tr').hide()
        $('#flagValue').val('session')
        $("#bySessionStudentList").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }

    else if(value==17){
        $('tr').hide()
        $('#flagValue').val('studentInfo')
        $("#multiProgType").show()
        $("#multiDepartmentRow").show()
        $("#multiSemRow").show()
        $("#multiGender").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==18){
        $('tr').hide()
        $('#flagValue').val('passoutStudents')
        $("#multiProgType").show()
        $("#multiDepartmentRow").show()
        $("#multiGender").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==19){
        $('tr').hide()
        $('#flagValue').val('empDetails')
        $("#empName").show()
        $("#or").show()
        $("#progGender").show()
        $("#hrow").show()
        $("#department").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }

    else if(value==20){
        $('tr').hide()

        $('#flagValue').val('foreignStudent')
        $("#studentApproved2").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==21){
        $('tr').hide()
        $('#flagValue').val('minorityStudent')
        $("#studentApproved2").show()
        $("#submitButton").show()
        $('#sessionDialog').dialog('open')
    }
    else if(value==22){
        $('tr').hide()
        $('#flagValue').val('subjectStudent')
        $('#academicSessionBox').show()
        $('#courseCode').show()
        //$('#courseName').show()
        //$('#oDetails').show()
        //$('#aSession').show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }
    else if(value==23){
        $('tr').hide()
        $('#flagValue').val('dailyAdmissionReport')
        $("#byDailyAdmissionReport").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }
    else if(value==24){
//        alert('24')
        $('tr').hide()
        $('#flagValue').val('paymentModeReport')
        $("#byPaymentModeReport").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }
    else if(value==25){
        $('tr').hide()
        $('#flagValue').val('StudyCentreStudentDetails')
        $('#inExcel').val('true')
        $("#byStudyCentreStudentDetails").show()
        $("#submitButton").show()
//        alert("condition is true")
        $('#sessionDialog').dialog('open')
    }

}



function setSessions(){
    $.ajax({
        type: "post",
        url: url('report', 'getSessionList', ''),
        async: false,
        data: '',
        success: function (data) {
            $(".allSession").empty().append('')
            for (var i = 0; i < data.length; i++) {
                $(".allSession").append('<option value="' + data[i] + '">' + data[i]+'-'+ (data[i]+1) + '</option>')
            }
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("response in error")
        }

    });
}

function setTerm(){
    $.ajax({
        type: "post",
        url: url('admitCard', 'getSemesterListOnly', ''),
        data: {},
        success: function (data) {
            $("#semesterList").empty().append('data <option value="null">Select Term</option>')
            for (var i = 1; i <= data.totalSem; i++) {
                $("#semesterList").append('<option value="' + i + '">' + i + '</option>')
            }
        }
    })
}

function disGender(){
    var name = $('#employeeName').val()
    if(name.length > 0) {
        $("input:radio[class^=egender]").each(function (i) {

            this.checked = false;
        });
    }
}

function disName(){

    document.getElementById('employeeName').value = "";

}