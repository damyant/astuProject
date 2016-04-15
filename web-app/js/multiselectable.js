var checkCourseCodeFlag = false, updateModeFlag = false
var groupListIndex = 0, k = 0;
var groups = ["A", "B", "C", "D", "E", "F", "G", "H", "I"];
var selectedSubjectValues = [];
var numberOfGroups = [];
//var selectedValues = [];
var flagg = true;
$(document).ready(function () {


});

function semesterList(jsonObject) {
    var courseType = $('#programType').val()
    $.ajax({
        type: "post",
        url: url('course', 'getCourseOnProgramCode', ''),
        data: $("#createCourse").serialize(),
        success: function (data) {
            console.log('this is the returnList ' + data)
            subjectList = data

            // showSemesterAndSubjects()
            if (subjectList.length > 0) {
                showSemesterAndSubjects(jsonObject)
                $("#worningMsg").hide();
            } else {
                $('#multiSelectTab tbody tr').remove()
                $("#worningMsg").html("Sorry We Are Unable To Find Courses Associated The Current Program And Session");
                $("#worningMsg").show();
            }
        }
    })

}


function viewSemesterList() {
    $('#multiSelectTab tr').remove()
    for (var j = 1; j <= $('#noOfTerms').html(); j++) {
        $('#multiSelectTab').append('<tr id="tr' + j + '"><td style="width: 40%"><label>Semester - ' + j + ' Course</label></td>' +
            '<td style="width: 60%"><select class="select-to university-size-2-3" readonly="readonly" style="width: 75%;border: 1px solid;margin: 0px" name="semester' + j + '" id="semester' + j + '"  multiple="true" /></td></tr>')

    }

}


function makeJson(list) {

//    subjectList = jQuery.parseJSON(list.replace(/&quot;/g, '"'))


}


function addToList(j) {
    var selectedValues = [];
    var nonSelected = [];
    var inList2;
    $('#allsubjectList' + j + ' :selected').each(function (l, list1Selected) {
        selectedValues[l] = $(list1Selected).val();
        inList2 = false;

        $('#semester' + j + ' option').each(function (m, list2Selected) {
            //alert("in addToList function")
            nonSelected[m] = $(list2Selected).val();
//            alert("list1"+selectedValues[l])
//            alert("list2"+nonSelected[m])
            if (selectedValues[l] == nonSelected[m]) {
                inList2 = true;
            }
        });

        if (inList2 != true) {
            $('#semester' + j).append("<option value='" + selectedValues[l] + "'>" + $(list1Selected).text() + "</option>");

            var text1 = $(list1Selected).val()
//            alert(text1);
//            $('#semester'+j+' option').filter(function() {
//                //may want to use $.trim in here
//                return $(this).val() == text1;
//            }).attr('selected', true);
            $('#allsubjectList' + j + ' option:selected').each(function (n, allsubSelected) {
                var text3 = $(allsubSelected).val()
                $('#allsubjectList' + j + ' option').filter(function () {
                    return $(this).val() == text3;
                }).attr('selected', false);
            });
        }

    });
    validateLength(j);
}

function removeFromList(j) {
    $('#semester' + j + ' option:selected').each(function () {
        $(this).remove();
        $('#semester' + j + ' option:not(selected)').each(function (k, semSelected) {
            var text2 = $(semSelected).val()
//        $('#semester'+j+' option').filter(function() {
//            return $(this).val() == text2;
//        }).attr('selected', true);
            $('#allsubjectList' + j + ' option:selected').each(function (n, allsubSelected) {
                var text3 = $(allsubSelected).val()
                $('#allsubjectList' + j + ' option').filter(function () {
                    return $(this).val() == text3;
                }).attr('selected', false);
            });
        });
    });
    validateLength(j);
}
function validateLength(j) {
    var validate;
    var length = document.getElementById('semester' + j).options.length;
    if (length == 0) {
        $('#error-select-' + j).html("<label style='margin-left: 4px; color: #cd0a0a; '>Please choose course for semesters</label>");
        validate = false;
    } else {
        $('#error-select-' + j).html("");
        validate = true;
    }

    return validate;
}

function updateInfo(obj) {
    updateModeFlag = true
    var courseDetailJson = jQuery.parseJSON(obj.replace(/&quot;/g, '"'))
    $('#courseName').val(courseDetailJson['course'].courseName)
    $('#modeName option[value=' + courseDetailJson['course'].courseMode.id + ']').attr("selected", "selected");
    $("#modeName").prop('disabled', false);
    $('#courseTypeName option[value=' + courseDetailJson['course'].programType.id + ']').attr("selected", "selected");
    $('#courseTypeName').prop("disabled", false)
    $("#session").prop('disabled', false);
    $('#courseTypeBranch option[value=' + courseDetailJson['course'].programBranch.id + ']').attr("selected", "selected");
    $('#programType').prop("disabled", false)
    if(courseDetailJson['course'].programType.id==3){
        $('#isPartTimeTr').css("display", "");
        if(courseDetailJson['course'].isPartTime){
            $("input[name='partTimeStatus'][value='partTime']").attr('checked', 'checked');
        }
        else{
            $("input[name='partTimeStatus'][value='']").attr('checked', 'checked');
        }

    }
    $('#noOfTerms').val(courseDetailJson['course'].noOfTerms)
    $('#finalTerm').val(courseDetailJson['course'].finalTerm)
    $('#noOfTerms').prop("disabled", false)
    $('#courseCode').val(courseDetailJson['course'].courseCode)
    $('#courseCode').prop("disabled", false)
    $('#courseCode').prop("readonly")

    $("#worningMsg").hide()
    console.log('Here is ' + courseDetailJson['course'].noOfTerms)
    $('#noOfAcademicYears').val(courseDetailJson['course'].noOfAcademicYears)
    $('#totalMarks').val(courseDetailJson['course'].totalMarks)
    $('#marksPerPaper').val(courseDetailJson['course'].marksPerPaper)
    $('#totalCreditPoints').val(courseDetailJson['course'].totalCreditPoints)
    $('#noOfPapers').val(courseDetailJson['course'].noOfPapers)
    $('#courseId').val(courseDetailJson['course'].id)
    $('#session option[value=' + courseDetailJson['sessionOfCourse'] + ']').attr("selected", "selected");

    if ($("#session option:selected").text() == courseDetailJson['sessionOfCourse'].toString()) {
        $('#session option[value=' + courseDetailJson['sessionOfCourse'] + ']').attr("selected", "selected");
    }
    else {
        $('#session').prepend('<option value="' + courseDetailJson['sessionOfCourse'] + '">' + courseDetailJson['sessionOfCourse'] + '</option>');
        $('#session option[value=' + courseDetailJson['sessionOfCourse'] + ']').attr("selected", "selected");
    }

    semesterList(courseDetailJson)

}
function enableNoOfSem(t) {
    if ($(t).val() != '0') {
        $('#noOfTerms').prop('readonly', false);
    }
    else {
        $('#noOfTerms').val('');
        $('#noOfTerms').prop('readonly', true);
    }
    if($(t).val() == '3'){
        $('#isPartTimeTr').css("display", "");
    }
}

function viewCourseInfo(obj) {

    var courseDetailJson = jQuery.parseJSON(obj.replace(/&quot;/g, '"'))
    $('#courseName').html(courseDetailJson['course'].courseName)
    //$('#modeName option[value='+courseDetailJson['course'].courseMode.id+']').attr("selected", "selected");
    $('#modeName').html(courseDetailJson['courseMode'])
    $('#courseTypeName').html(courseDetailJson['programType'])
    $('#courseCategory').html(courseDetailJson['programBranch'])
    $('#noOfTerms').html(courseDetailJson['course'].noOfTerms)
    $('#courseCode').html(courseDetailJson['course'].courseCode)
    $('#noOfAcademicYears').html(courseDetailJson['course'].noOfAcademicYears)
    $('#totalMarks').html(courseDetailJson['course'].totalMarks)
    $('#marksPerPaper').html(courseDetailJson['course'].marksPerPaper)
    $('#totalCreditPoints').html(courseDetailJson['course'].totalCreditPoints)
    $('#noOfPapers').html(courseDetailJson['course'].noOfPapers)
    $('#courseId').html(courseDetailJson['course'].id)
    viewSemesterList()

    appendSubjectsInUpdateMode(courseDetailJson)


}

function fireMultiValidate() {
    var validate = true;
    var startTerm=1
    if($('#courseTypeName').val()=='4'){
        startTerm=3
    }
    for (var i = startTerm; i <= ((parseInt(startTerm) - 1) + parseInt($('#noOfTerms').val())); i++) {
//        alert($("input[name=partTimeStatus]:checked").val())
        if($('#courseTypeName').val()=='3'){
            if(($("input[name=partTimeStatus]:checked").val()=='partTime') && (i==(startTerm + 2)|| i==(startTerm + 3))){

            }
            else{
                if (!validateLength(i)) {
                    validate = false;
                }
            }
        }
        else{
            if (!validateLength(i)) {
                validate = false;
            }
        }


    }

    return validate;
}


function ConvertFormToJSON(form) {
    var array = jQuery(form).serializeArray();
    var json = {};
    var finalList = new Array();
    var i = 0;

    jQuery.each(array, function () {
        json[this.name] = this.value || '';
        i++;
    });
    json['uploadSyllabus'] = $('#uploadSyllabus').val() || '';
    var semesterList = {};

    var counter = 0;
    var groupSelectionTypeList = [], noOfSubjects = [];
    var startTerm=1
    if($('#courseTypeName').val()=='4'){
        startTerm=3
    }
    for (var j = startTerm; j <= ((parseInt(startTerm) - 1) + parseInt($('#noOfTerms').val())); j++) {
        var totalList = [];
        var subList = [], subGroupList = []

        $('#semester' + j + ' option').each(function () {

            subList.push($(this).val() || '');

        })
        totalList.push(subList);


        if ($("#groupListBox" + j).length > 0) {
            $('#groupListBox' + j + ' option').each(function () {
                subGroupList.push($(this).val() || '');


            });


            groupSelectionTypeList.push($('input[name=groupSelection' + j + ']:radio:checked').val())
            noOfSubjects.push($("#noOfSubjects" + j).val())
            totalList.push(subGroupList);


        }
        else {
            groupSelectionTypeList.push(" ")
            noOfSubjects.push(" ")
        }
        semesterList["semester" + j] = totalList;

    }

    json["groupSelectionType"] = groupSelectionTypeList;
    json["noOfSubjects"] = noOfSubjects;
    finalList.push(semesterList);
    console.log("hello java" + semesterList)
    json["semesterList"] = finalList;
//       console.log("hello java"+json);

    return json
}

function clearField() {

    for (var i = 1; i <= $('#noOfTerms').val(); i++) {
        $('#semester' + i).empty();
    }
    $('#createCourse').each(function () {
        this.reset();
    });
//    $("html, body").animate({ scrollTop: 0 }, "slow");
}
function save() {
    var status = $("#createCourse").valid();
    if (!fireMultiValidate()) {
        return;
    }
    if (status && $("#errorMsg").text().length == 0) {
        var formObj = $("#createCourse");
        var data = ConvertFormToJSON(formObj);

        $.ajax({
            type: "post",
            url: url('course', 'saveProgram', ''),
            data: JSON.stringify(data),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function (data) {
                if (data.response1 == 'update') {
                    document.getElementById("statusMessage").style.display = "block";
                    document.getElementById("worningMsg").style.display = "none";
                    $('#worningMsg').text('')
                }
                else if (data.response1 == 'Created') {
                    $('#worningMsg').text('')
                    document.getElementById("worningMsg").style.display = "none";
                    document.getElementById("statusMessage").style.display = "block";
                    setTimeout(function () {
                        window.location.reload();
                    }, 1500);

                    $('#creationMessage').text('Program Successfully Created')
                    clearField()
                }
                else if (data.response1 == 'Not Created') {
                    $('#worningMsg').text('Same course in multiple Semester or Program May already Exist.')
                    document.getElementById("worningMsg").style.display = "block";
                }
                $("html, body").animate({ scrollTop: 0 }, "slow");
            }
        });
    }
}
function syllabusUpload() {
    if ($('#courseName').val() != "") {

    }
    else {
        alert("Enter The Programme Name First.");
    }
}


function checkCourseCode() {

    var data = $('#courseCode').val();
    $.ajax({
        type: "post",
        url: url('course', 'checkCourseCode', ''),
        data: {courseCode: data},
        success: function (data) {
            if (data.courseCode == "true") {
                $('#errorMsg').text("Programme Code is already registered")
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
function checkFileType(e) {

    var file_list = e.target.files;

    for (var i = 0, file; file = file_list[i]; i++) {
        var sFileName = file.name;
        var sFileExtension = sFileName.split('.')[sFileName.split('.').length - 1].toLowerCase();
        var iFileSize = file.size;
        var iConvert = (file.size / 10485760).toFixed(2);

        if (!(sFileExtension === "pdf" || sFileExtension === "doc" || sFileExtension === "docx") || iFileSize > 10485760) {
            txt = "File type : " + sFileExtension + "\n\n";
            txt += "Size: " + iConvert + " MB \n\n";
            txt += "Please make sure your file is in pdf or doc format and less than 10 MB.\n\n";
        }
        else {
            return false;
        }
    }
}


function showSemesterAndSubjects(courseDetailJson) {
    var courseType = $('#courseTypeName').val()
    var startTerm=1
    var semVal = 0
    if(courseType=='4'){
        startTerm=3
        semVal = 1
    }
    else if(courseType=='3' || courseType=='6'){
        semVal = 4
    }
    var clVal = 0

    $('#multiSelectTab tbody tr').remove()
    for (var j = startTerm; j <= ((parseInt(startTerm) - 1) + parseInt($('#noOfTerms').val())); j++) {
        $('#multiSelectTab tbody').append('' +
//            '<tr><td colspan="3" style="text-align: left"><label>All Course <span class="university-obligatory">*</span></label></td></tr>' +
            '<tr id="tr' + j + '">' +
            '<td style="width:40% "> ' +
            '<label>All Course <span class="university-obligatory">*</span></label>' +
            '<input type="search" style="margin-left:23px;margin-top:10px;"  onchange="selectSearch('+j+')" class="university-size-1-5" id="search'+j+'" placeholder="Search by Course Code"/>' +
            '<select style="width: 80%;height: 30px;" name="allsubjectList' + j + '" id="allsubjectList' + j + '"/>' +
            '</td>' +
            ' <td style="width:10% "> ' +
            '<button type="button" class="multiSelect-buttons-button" onclick="addToList(' + j + ')" name="add' + j + '"  id="add' + j + '">Add</button>' +
            '<button type="button" class="multiSelect-buttons-button" onclick="removeFromList(' + j + ')" name="remove' + j + '"  id="remove' + j + '">Remove</button> ' +
            '</td>' +
            '<td style="width:40%;">' +
            '<select class="select-to" style="width: 50%"  name="semester' + j + '" id="semester' + j + '"  multiple="true"  />' +
            '<div id="error-select-' + j + '"></div>' +
            '</td></tr>')


        if ($('#modeName option:selected').text().toLowerCase() == "annual") {
            $("<div>Term-" + j + "</div>").insertBefore($('#semester' + j))
        }
        else if (($('#modeName option:selected').text().toLowerCase() == "semester")) {
            $("<div>Semester-" + j + "</div>").insertBefore($('#semester' + j))
        }

        for (var i = 0; i < subjectList.length; i++) {
            var sem = (subjectList[i].subjectCode).substring(2, 3);
            if ((j + 1) % 2 == 0 && i == 0) {

                    semVal += 1

            }


//            if (sem == semVal) {
                $("#allsubjectList" + j).append('<option value="' + subjectList[i].id + '" title="' + subjectList[i].subjectName + ' [' + subjectList[i].subjectCode + ']">' + subjectList[i].subjectName + ' [' + subjectList[i].subjectCode + ']</option>')
//            }
        }
        $("#createCourse").append('<div id="groupDialog' + j + '" class="dialog" hidden="hidden">' +
            '<g:form method="post" name="groupsOfSubject" id="groupsOfSubject" enctype="multipart/form-data">' +
            '<input id="addGroup' + j + '" onclick="addGroups(' + j + ')" type="reset" value="Add Groups"  class="university-button">' +
            '<input id="removeGroupOnPopUp' + j + '" onclick="removeSubjectGroup(' + j + ')" type="reset" value="Remove Groups" disabled class="university-button">' +
            '<input type="button" id="saveGroups' + j + '" value="Save Groups"  disabled class="university-button" onclick="saveSubjectGroup(' + j + ',' + clVal + ')" />' +
            '<table  name="subjectGroup" id="subjectGroup' + j + '"><tbody></tbody></table></g:form></div>')
    }

    if (updateModeFlag) {
        appendSubjectsInUpdateMode(courseDetailJson)
    }
}
function selectSearch(tableRowIndex){
    var searchText=$('#search'+tableRowIndex).val().toUpperCase()
//    alert(searchText.length)
    if(searchText.length>3){
        $('#allsubjectList'+tableRowIndex+' option:contains(' + searchText + ')').each(function(){
//            alert($(this).text())
            $('#allsubjectList'+tableRowIndex).val('').scrollTop()
            var scrollIndex=$('#allsubjectList'+tableRowIndex).find("option[value="+$(this).val()+"]").index()
//            var isSelected = $('#allsubjectList'+tableRowIndex).find("option[value="+$(this).val()+"]").is(":selected");
            $('#allsubjectList'+tableRowIndex).find("option[value="+$(this).val()+"]").prop("selected", true); // scroll to the option
//            $('#allsubjectList'+tableRowIndex).find("option[value="+$(this).val()+"]").prop("selected", isSelected);
            $('#allsubjectList'+tableRowIndex).val($(this).val())
            $('#search'+tableRowIndex).val('')
//            $('#search'+tableRowIndex).scroll(scrollIndex)
//        }

        });
    }

}
jQuery.expr[':'].Contains = function(a,i,m){
    return jQuery(a).text().toUpperCase().indexOf(m[3].toUpperCase())>=0;
};
function showGroup(j) {

    initializeDialog(j)
//    if(numberOfGroups[j]){
//        k=numberOfGroups[j];
//        groupListIndex=numberOfGroups[j];
//    }else{

    groupListIndex = 0;
    //}

    $('#groupDialog' + j).dialog('open');
}

function addGroups(id) {
    var rowCount = $('#subjectGroup' + id + ' tbody tr').length;

    $("#removeGroupOnPopUp" + id).prop('disabled', false)
    $("#saveGroups" + id).prop('disabled', false)
    groupListIndex = rowCount


    $('#subjectGroup' + id + ' tbody').append('<tr id="groupTr' + id + groupListIndex + '"><td style="width:30% "></div> <label>All Course <span class="university-obligatory">*</span></label>' +
        '<select style="width: 60%" name="listOfAllsubject' + id + groupListIndex + '" id="listOfAllsubject' + id + groupListIndex + '"  multiple="true"  /></td>' +
        ' <td style="width:10% "> <button type="button" class="multiSelect-buttons-button" onclick="addToGroupList(' + id + groupListIndex + ')" name="add' + id + groupListIndex + '"  id="add' + id + groupListIndex + '">Add</button>' +
        '  <button type="button" class="multiSelect-buttons-button" onclick="removeGroupFromList(' + id + groupListIndex + ')" name="remove' + id + groupListIndex + '"  id="remove' + id + groupListIndex + '">Remove</button> </td>' +
        '<td style="width:30%;"><select class="select-to" style="width: 50%"  name="group' + id + groupListIndex + '" id="group' + id + groupListIndex + '"  multiple="true"  />' +
        '<div style="color:#FF0000;" id="error-select' + id + groupListIndex + '" ></div></div></td></tr>')


    $("<div>Group <label id='groupName'>" + groups[rowCount] + "</label></div>").insertBefore($('#group' + id + '' + groupListIndex))

    if (rowCount == 8) {
        $('#addGroup' + id).prop('disabled', true)
    }

    for (var i = 0; i < subjectList.length; i++) {

        $("#listOfAllsubject" + id + '' + groupListIndex).append('<option value="' + subjectList[i].id + '">' + subjectList[i].subjectName + '</option>')
    }

}

function removeSubjectGroup(t) {
    var closeVal = "notClose";
    $('#subjectGroup' + t + ' tbody tr:last').remove()
    groupListIndex--;
    var rowCount = $('#subjectGroup' + t + ' tr').length;
    if (rowCount <= 8) {
        $('#addGroup' + t).prop('disabled', false)
    }
    if (rowCount == 0) {
        $("#removeGroupOnPopUp" + t).prop('disabled', true)
    }

}

function addToGroupList(j) {

    var nonSelected = [];
    var inList2;
    $('#listOfAllsubject' + j + ' :selected').each(function (l, list1Selected) {
        selectedSubjectValues[l] = $(list1Selected).val();
        //numberOfGroups[l] = selectedSubjectValues
        inList2 = false;

        $('#group' + j + ' option').each(function (m, list2Selected) {
            nonSelected[m] = $(list2Selected).val();
            if (selectedSubjectValues[l] == nonSelected[m]) {
                inList2 = true;
            }
        });

        if (inList2 != true) {
            $('#group' + j).append("<option value='" + selectedSubjectValues[l] + "'>" + $(list1Selected).text() + "</option>");

            var text1 = $(list1Selected).val()
            $('#listOfAllsubject' + j + ' option:selected').each(function (n, allsubSelected) {
                var text3 = $(allsubSelected).val()
                $('#listOfAllsubject' + j + ' option').filter(function () {
                    return $(this).val() == text3;
                }).attr('selected', false);
            });
        }

    });
    validateGroupLength(j);
}

function saveSubjectGroup(j, closeVal) {
    var isVaid = validateGroupSubjects(j)
    var rowCount = 0

    if (!isVaid) {
        return false
    }
    var subList = [];
    var subGroupMap;
    rowCount = $('#subjectGroup' + j + ' tr').length;
    var count = $('#group' + j + groupListIndex + ' option').size();
    // numberOfGroups[j]=rowCount;
    if ($("#groupListBox" + j + " option").length > 0) {
        //save $('#multiSelectTab tbody #tr'+j).find("td:last").remove()
        $('#multiSelectTab tbody #tr' + j + ' td:gt(2)').remove();
    }
    var groupSubjectJson = ConvertGroupFormToJSON(j);
    subList = groupSubjectJson["groupList"];
    var subMap = subList[0];


    if (rowCount != 0) {
        if (!document.getElementById("groupListBox" + j)) {
            $('#multiSelectTab tbody #tr' + j + ':last').append('<td style="width:30%;"><select multiple="multiple" id="groupListBox' + j + '" style="height: 100px; width: 150px;">' +
                '</select> </td>')

        }
        if (!document.getElementById('groupOption' + j)) {

            $('#multiSelectTab tbody #tr' + j + ':last').append('<td><div name="groupSelection' + j + '" id="groupOption' + j + '" ><label><span>Select From One Group</span><input type="radio" name="groupSelection' + j + '" value="single" onclick="hideTextBox(' + j + ')" class="radioInput"/></label></div><br/>' +
                '<label><span>Select Multiple Subject In Group</span><input type="radio" name="groupSelection' + j + '" id="groupSelection' + j + '" value="multipleSubject" class="radioInput" onclick="openTextBox(' + j + ')"/></label><input type="text" onkeypress="return isNumber(event)" maxlength="1" hidden="hidden" id="noOfSubjects' + j + '" ><div style="color:#FF0000;" id="radioError' + j + '"></div><br/>' +
                '<label><span>Select From Multiple Group </span><input type="radio" name="groupSelection' + j + '" id="groupSelection' + j + '" value="multiple" class="radioInput" onclick="hideTextBox(' + j + ')"/></label><div style="color:#FF0000;" id="radioRuleError' + j + '"></div></div></td>')

        }
        $("#groupListBox" + j).empty()
        for (var i = 0; i < rowCount; i++) {
            subGroupMap = subMap["group" + j + '' + i]
            if (subGroupMap) {
                $("#groupListBox" + j).append('<option value="Group' + groups[i] + '" style="font-family: bold;">Group <label >' + groups[i] + '</option>');
                for (var key in subGroupMap) {
                    //alert("appening the options"+subGroupMap[key])
                    $("#groupListBox" + j).append('<option value="' + key + '"><label>' + subGroupMap[key] + '</label></option>');
                }

            }
            subGroupMap = null;
        }

    }
    if (closeVal == "") {
        $('#groupDialog' + j).dialog('close');
    }
}

function validateGroupLength(j) {
    var validate;
    var length = document.getElementById('group' + j).options.length;
    if (length == 0) {
        $('#error-select-' + j).html("<label style='margin-left: 4px; color: #cd0a0a; '>Please choose course for semesters</label>");
        validate = false;
    } else {
        $('#error-select-' + j).html("");
        validate = true;
    }

    return validate;
}
function removeGroupFromList(j) {
    $('#group' + j + ' option:selected').each(function () {
        $(this).remove();
        $('#group' + j + ' option:not(selected)').each(function (k, semSelected) {
            var text2 = $(semSelected).val()

            $('#listOfAllsubject' + j + ' option:selected').each(function (n, allsubSelected) {
                var text3 = $(allsubSelected).val()
                $('#listOfAllsubject' + j + ' option').filter(function () {
                    return $(this).val() == text3;
                }).attr('selected', false);
            });
        });
    });
    validateGroupLength(j);
}

function ConvertGroupFormToJSON(id) {
    var json = {};
    var finalList = new Array();
    var i = 0;
    var groupMap = {};
    var rowCount = $('#subjectGroup' + id + ' tr').length;

    for (var j = 0; j < rowCount; j++) {
        var subMap = {};
        $('#group' + id + '' + j + ' option').each(function () {
                var key = $(this).val()
                subMap[key] = $('#group' + id + '' + j + ' option[value=' + $(this).val() + ']').text()
                groupMap["group" + id + '' + j] = subMap;
            }
        )

    }
    finalList.push(groupMap);

    json["groupList"] = finalList;


    return json
}

function initializeDialog(j) {
    $(".dialog").dialog({
        autoOpen: false,
        draggable: false,
        position: ['center', 0],
        width: 850,
        resizable: false,
        height: 650,
        modal: true,
        title: 'Subject Groups',
        close: function (ev, ui) {
            $.unblockUI();
        },
        open: function () {

            showSubjectGroupInDialog(j)
        }
    });
}

function addToTestList(id) {
    ConvertGroupFormToJSON(id)
}

function openTextBox(data) {
    $("#noOfSubjects" + data).show()
    $('#radioRuleError' + data).hide()
}

function hideTextBox(data) {
    $("#noOfSubjects" + data).hide()
    $('#radioRuleError' + data).hide()
    $('#radioError' + data).hide()
}

function appendSubjectsInUpdateMode(courseDetailJson) {
    for (var i = 1; i <= courseDetailJson['course'].noOfTerms; i++) {
        if (courseDetailJson['semesterList'][i] != undefined) {
            for (var j = 0; j < courseDetailJson['semesterList'][i].length; j++) {
                var groupFlag = false
                $('#semester' + i).append('<option value="' + courseDetailJson['semesterList'][i][j].id + '" title="' + courseDetailJson['semesterList'][i][j].subjectName + ' [' + courseDetailJson['semesterList'][i][j].subjectCode + ']">' + courseDetailJson['semesterList'][i][j].subjectName + ' [' + courseDetailJson['semesterList'][i][j].subjectCode + ']</option> ')
            }
        }
    }
}


function appendSubjectGroupInUpdate(i) {


    if (document.getElementById("groupListBox" + i)) {
    }
    else {
        $('#multiSelectTab tbody #tr' + i + ':last').append('<td style="width:30%;"><select multiple="multiple" id="groupListBox' + i + '" style="height: 100px; width: 150px;">' +
            '</select> </td><td><div id="groupOption"><label><span>Select From One Group</span>' +
            '<input type="radio" name="groupSelection' + i + '" id="groupSelection' + i + '"  value="single"  onclick="hideTextBox(' + i + ')" class="radioInput"/></label></div><br/>' +
            '<label><span>Select Multiple Subject In Group</span><input type="radio" id="groupSelection' + i + '"  name="groupSelection' + i + '" value="multipleSubject" class="radioInput" onclick="openTextBox(' + i + ')" id="multiSubjects' + i + '" /></label><div style="color:#FF0000;" id="radioError' + i + '"></div>' +
            '<input type="text" hidden="hidden" name="noOfSubjects" id="noOfSubjects' + i + '" ><br/>' +
            '<label><span>Select From Multiple Group</span><input type="radio" id="groupSelection' + i + '"  name="groupSelection' + i + '" value="multiple" class="radioInput" onclick="hideTextBox(' + i + ')"/></label></div><div style="color:#FF0000;" id="radioRuleError' + i + '"></div></td>')

    }
}

function showSubjectGroupInDialog(i) {
    var groupRowCounter = 0;

    if (document.getElementById("groupListBox" + i)) {

        $('#groupListBox' + i + ' option').each(function () {

            if ($(this).val().indexOf("Group") > -1) {
                ++groupRowCounter
            }

        })

    }
    $('#subjectGroup' + i + ' tbody tr:not(first)').remove();

    var tempList = ["GroupA", "GroupB", "GroupC", "GroupD", "GroupE", "GroupF", "GroupG", "GroupH", "GroupI"]


    for (var j = 0; j < groupRowCounter; j++) {
        groupFlag = false;
        $("#removeGroupOnPopUp" + i).prop('disabled', false)
        $("#saveGroups" + i).prop('disabled', false)
        if ((document.getElementById("subjectGroup" + i))) {

            $("#subjectGroup" + i + ' tbody').append('<tr id="groupTr' + i + j + '"><td style="width:30% "></div> <label>All Course <span class="university-obligatory">*</span></label>' +
                '<select style="width: 60%" name="listOfAllsubject' + i + j + '" id="listOfAllsubject' + i + j + '"  multiple="true"  /></td>' +
                ' <td style="width:10% "> <button type="button" class="multiSelect-buttons-button" onclick="addToGroupList(' + i + j + ')" name="add' + i + j + '"  id="add' + i + j + '">Add</button>' +
                '  <button type="button" class="multiSelect-buttons-button" onclick="removeGroupFromList(' + i + j + ')" name="remove' + i + j + '"  id="remove' + i + j + '">Remove</button> </td>' +
                '<td style="width:30%;"><div id="groupName' + i + j + '"></div><select class="select-to" style="width: 50%"  name="group' + i + j + '" id="group' + i + j + '"  multiple="true"  />' +
                //'<td style="width:10% "> <button type="button" class="multiSelect-buttons-button" onclick="removeSubjectGroup(' +id+',' + id +l+ ')" name="removeGroup' + id +l+ '"  id="removeGroup' + id +l+ '">Remove Group</button>' +
                '<div id="error-select"></div></div></td></tr>')


            $('#groupListBox' + i + ' option').each(function () {

                if ($(this).val().indexOf("Group") > -1) {


                    if ($("#groupName" + i + j).text().length == 0 && tempList[j] == $(this).val()) {

                        $("#groupName" + i + j).html($(this).val())
                        groupFlag = true

                    }
                    else {
                        groupFlag = false
                    }

                }
                else if (groupFlag) {
                    $("#group" + i + j).append('<option value="' + $(this).val() + '">' + $(this).text() + '</option>')
                }
            })
            for (var k = 0; k < subjectList.length; k++) {

                $("#listOfAllsubject" + i + '' + j).append('<option value="' + subjectList[k].id + '">' + subjectList[k].subjectName + '</option>')
            }
        }
    }
    var rowCount = $('#subjectGroup' + i + ' tr').length;
    if (rowCount > 0) {
        groupListIndex = groupListIndex + rowCount;
    }
}

function validateGroupSubjects(j) {
    var rowCount = 0
    rowCount = $('#subjectGroup' + j + ' tr').length;
    var isValid = false
    if (rowCount != 0) {
        for (var i = 0; i < rowCount; i++) {
            var count = $('#group' + j + i + ' option').size();
            if (count == 0) {
                $('#error-select' + j + i).text('Please Add Atleast One Subject To The Group');
                isValid = false
            } else {
                isValid = true
            }

        }
    } else {
        isValid = true
    }
    return isValid

}


function validateGroupSelection() {
    var isValidRadio = false;
    var isText = false

    var row = $('#multiSelectTab tbody tr').length

    for (var i = 1; i <= row; i++) {
        if (document.getElementById("groupListBox" + i)) {

            if ($('[name="groupSelection' + i + '"]').is(':checked')) {

                var multiSubject = $('input[name=groupSelection' + i + ']:radio:checked').val()
                if (multiSubject == "multipleSubject") {
                    var noOfSubject = $('#noOfSubjects' + i).val()
                    if (noOfSubject == "") {
                        $('#radioError' + i).text('Please Enter Number Of Subject ');
                        $('#radioError' + i).show()
                        isValidRadio = false;
                    } else {
                        $('#radioError' + i).hide()
                        isValidRadio = true;

                    }


                } else {
                    isValidRadio = true
                }

                if (!isValidRadio)
                    return isValidRadio

            } else {
                $('#radioRuleError' + i).text('Please Select Criteria ');
                $('#radioRuleError' + i).show()
                isValidRadio = false;
                return isValidRadio
            }
        }
    }
    return isValidRadio
}