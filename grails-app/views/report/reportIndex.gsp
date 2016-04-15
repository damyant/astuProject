<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 29/4/14
  Time: 3:09 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" import=" examinationproject.ProgramDetail;" %>
%{--For Showing List of Reports & Dialog box for filters --}%
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <script type="text/javascript">
        var uri = window.location.toString();
        if (uri.indexOf("?") > 0) {
            var clean_uri = uri.substring(0, uri.indexOf("?"));
            window.history.replaceState({}, document.title, clean_uri);
        }
    </script>

    <script type="text/javascript" src="${resource(dir: 'js', file: 'report.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery', file: 'jquery.form.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'tablesorter-master/js', file: 'jquery.tablesorter.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'tablesorter-master/js', file: 'jquery.tablesorter.widgets.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'tablesorter-master/js/widgets', file: 'widget-columnSelector.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'tablesorter-master/js/widgets', file: 'widget-print.js')}"></script>
    <script type="text/javascript" src="${resource(dir: '/bootstrap-3.3.4-dist/js', file: 'bootstrap.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'MultipleSelect', file: 'jquery.multiselect.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'MultipleSelect', file: 'jquery.multiselect.filter.min.js')}"></script>
    <link rel="stylesheet" href="${resource(dir: '/bootstrap-3.3.4-dist/css', file: 'bootstrap.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'MultipleSelect', file: 'jquery.multiselect.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'MultipleSelect', file: 'jquery.multiselect.filter.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'tablesorter-master/css', file: 'theme.blue.css')}" type="text/css">

    <g:javascript src='postExamination.js'/>
    <g:javascript src='studyCenter.js'/>
    <g:javascript src='highcharts.js'/>
    <g:javascript src='drawGraph.js'/>
    <g:javascript src='basic.js'/>
    <g:javascript src='admin.js'/>
    <g:javascript src='jspdf.min.js'/>

<style>
.options th.narrow {
    width: 150px;
}
.button {
    position: relative;
    padding: 1px 6px;
    display: inline-block;
    cursor: pointer;
    border: #000 1px solid;
    border-radius: 5px;
}
.columnSelector, .hidden {
    display: none;
}
#colSelect1:checked + label {
    color: #307ac5;
}
#colSelect1:checked ~ #columnSelector {
    display: block;
}
.columnSelector {
    width: 200px;
    position: absolute;
    top: 30px;
    padding: 10px;
    background: #fff;
    border: #99bfe6 1px solid;
    border-radius: 5px;
}
.columnSelector label {
    display: block;
    text-align: left;
}
.columnSelector label:nth-child(1) {
    border-bottom: #99bfe6 solid 1px;
    margin-bottom: 5px;
}
.columnSelector input {
    margin-right: 5px;
}
.columnSelector .disabled {
    color: #ddd;
}
</style>


</head>
<body>
<div id="main">

<g:if test="${flash.message}">
    <div class="message"><div class="university-status-message">${flash.message}</div></div>
</g:if>
<fieldset class="form">
%{--<div style="width: 30%">--}%
<div id="list5">
    <ol>
        <li style="">Student List By Programme & Admission Year
            <ol>
                <a href="#"><li id="approvedStudentList">Approved</li></a>
                <a href="#"><li id="unapprovedStudentList">UnApproved</li></a>
            </ol>
        </li>

        <li>Semester Registration
            <ol>
                <a href="#"><li id="approvedSemesterRegistrationInExcel">Approved Students with Subjects</li></a>
            </ol>
        </li>
        <li id="approvedRegistrationByFA"><a href="#">Approved Semester Registration By Faculty Advisor</a></li>
        <li>Category
            <ol>
                <li id="studentIntake"><g:link controller="report" action="generateReport"
                                               params="[studentIntake: 'studentIntake']">Student Intake AICTE</g:link></li>
                <li id="categoryGraph"><a href="#">Student Intake Graphical form</a></li>
                <li id="studentsByCategory"><a href="#">List of student by Category & Gender</a></li>
                <li id="studentsByGender"><a href="#">List of student by Gender</a></li>
                <li id="studentsCategory"><a href="#">List of student by Category</a></li>
            </ol>
        </li>
        <li>Student Information
            <ol>
                %{--<li id="studentInfo"><g:link controller="report" action="generateReport" params="[studentInfo:'studentInfo']" >Student Information</g:link></li>--}%
                <li id="studentInfo"><a href="#">Student Information</a></li>
                <li id="studentInfoByCourse"><a href="#">Course Wise Student Information</a></li>
                <li id="passoutStudents"><a href="#">Student Information Of Pass out students</a></li>
            </ol>

        </li>
        <li id="empDetails"><a href="#">Employee Details</a></li>
        <li id="subjectStudent"><a href="#">Subjectwise Student</a></li>

        <li id="registrationDetails"><a href="#">Students' Registration details</a></li>
        <li id="regDetailsWithBack"><a href="#">
                Students' previous semester registration details along with back details</a></li>
        <li id="foreignStudent"><a href="#">Foreign Student data</a></li>
        <li id="minorityStudent"><a href="#">Minority Students' information</a></li>
        <li id="projectThesis"><a href="#">Project Thesis Title</a></li>
        <li id="studentAttendance"><a href="#">Student's Attendence</a></li>
        <li id="grade"><a href="#">Grade details from Course instructor</a></li>
    </ol>
</div>

%{--</div>--}%

<div id="sessionDialog" class="dialog">
<g:uploadForm controller="report" action="generateReport" id="reports" name="reports" method="post"
              enctype="multipart/form-data">

<table class="inner" style="margin: auto;" id="fee-table">
<tr id="empName">
    <td style="width: 30%">
        <label for="employeeName">Employee's First Name:</label>
    </td>
    <td style="width: 70%">
        <input type="text" id="employeeName" name="employeeName" class="university-size-1-1" onchange="disGender()">
    </td>
</tr>
<tr id="or"><td colspan="2" style="text-align: center;font-weight: bold">OR</td></tr>

    <tr id="progGender" >
        <td style="width: 30%">
            <label for="gender">Gender</label>
        </td>
        <td style="width: 70%">
            <input type="radio" class="egender" name="gender" value="male" checked onclick="disName()">Male
            <input type="radio" class="egender" name="gender" value="female" onclick="disName()">Female
            <input type="radio" class="egender" name="gender" value="Both" onclick="disName()">Both
        </td>

    </tr>
    <tr id="hrow"><td colspan="2"><hr/></td></tr>

<tr id="progType">
    <td style="width: 30%">
        <label for="programType">Programme Type:</label>
    </td>
    <td style="width: 70%">
        <select name="programType" id="programType" class="university-size-1-1">
            <option value="null">Select Program Type</option>
            <g:each in="${progTypeList}" var="progT">
                <option value="${progT.id}">${progT.type}</option>
            </g:each>
            <option value="All">All</option>
        </select>

     </td>
</tr>

    <tr id="multiProgType">
        <td style="width: 30%">
            <label for="programType">Programme Type:</label>
        </td>
        <td style="width: 70%">
            <select multiple="multiple" name="multiProgram" id="multiProgram" class="university-size-1-1 select2">
                %{--<option value="null">Select Program Type</option>--}%
                <g:each in="${progTypeList}" var="progT">
                    <option value="${progT.id}">${progT.type}</option>
                </g:each>
                %{--<option value="All">All</option>--}%
            </select>

        </td>
    </tr>

    <tr id="multiDepartmentRow">
        <td style="width: 30%">
            <label for="department">Department:</label>
        </td>
        <td style="width: 70%">
            <select multiple="multiple" name="multiDepartment" id="multiDepartment" class="university-size-1-1 select2">
                %{--<option value="null">Select Department</option>--}%
                <g:each in="${depList}" var="dep">
                    <option value="${dep.id}">${dep.name}</option>
                </g:each>
                %{--<option value="All">All</option>--}%
            </select>

        </td>
    </tr>

    <tr id="multiSemRow">
        <td style="width: 30%">
            <label for="programSem">Semester:</label>
        </td>
        <td style="width: 70%">
            <select multiple="multiple" name="multiSem" id="multiSem" class="university-size-1-1 select2">
                %{--<option value="null">Select Semester</option>--}%
                <g:each in="${semester}" var="sem">
                    <option value="${sem}">${sem}</option>
                </g:each>
                %{--<option value="All">All</option>--}%
            </select>

        </td>
    </tr>

    <tr id="multiGender" >
        <td style="width: 30%">
            <label for="gender">Gender</label>
        </td>
        <td style="width: 70%">
            <input type="radio" name="mGender" value="male" checked>Male
            <input type="radio" name="mGender" value="female">Female
            <input type="radio" name="mGender" value="Both">Both
        </td>

    </tr>

<tr id="department">
    <td style="width: 30%">
        <label for="department">Department:</label>
    </td>
    <td style="width: 70%">
        <select name="programDepartment" id="programDepartment" class="university-size-1-1">
            <option value="null">Select Department</option>
            <g:each in="${depList}" var="dep">
                <option value="${dep.id}">${dep.name}</option>
            </g:each>
            <option value="All">All</option>
        </select>

    </td>
</tr>

<tr id="branch">
    <td style="width: 30%">
        <label for="department">Branch:</label>
    </td>
    <td style="width: 70%">
        <select name="programBranch" id="programBranch" class="university-size-1-1">
            <option value="null">Select Branch</option>
            <g:each in="${branchList}" var="branch">
                <option value="${branch.id}">${branch.name}</option>
            </g:each>
            <option value="All">All</option>
        </select>

    </td>
</tr>

<tr id="progSem">
    <td style="width: 30%">
        <label for="programSem">Semester:</label>
    </td>
    <td style="width: 70%">
        <select name="programSem" id="programSem" class="university-size-1-1">
            <option value="null">Select Semester</option>
            <g:each in="${semester}" var="sem">
                <option value="${sem}">${sem}</option>
            </g:each>
            <option value="All">All</option>
        </select>

    </td>
</tr>

<tr id="studentApproved1">
    <td style="width: 30%">
        <label for="approvedProgram">Programme:</label>
    </td>
    <td style="width: 70%">
        <g:select name="approvedProgram" class="university-size-1-1" id="approvedProgram"
                  from="${programList}" optionKey="id" optionValue="courseName"
                  noSelection="['null': ' Select Program']"/>
    </td>
</tr>
<tr id="studentApproved2" style="margin-top: 10px;">
    <td style="width: 30%">
        <label for="approvedAdmissionYear">Enrollment Year:</label>
    </td>
    <td style="width: 70%">
        <select name="approvedAdmissionYear" id="approvedAdmissionYear" class="university-size-1-1">
            <option value="null">Select Enrollment Year</option>
            <g:each in="${registrationYear}" var="year">
                <option value="${year}">${year}-${year+1}</option>
            </g:each>
        </select>
    </td>
</tr>
<tr id="courseInstruct" style="margin-top: 10px;">
    <td style="width: 30%">
        <label for="courseInstructor">Course Instructor:</label>
    </td>
    <td style="width: 70%">
        <select name="courseInstructor" id="courseInstructor" class="university-size-1-2">
            <option value="null"> Course Instructor</option>
            <g:each in="${courseInstructor}" var="ins">
                <option value="${ins}">${ins}</option>
            </g:each>
        </select>
    </td>
</tr>
<tr id="academicSessionBox">
    <td style="width: 30%">
        <label for="academicSession">Select Academic Session:</label>
    </td>
    <td style="width: 70%">
        <select name="academicSession" id="academicSession" class="university-size-1-2">
            <option value="null">Select Session</option>
            <g:each in="${academicSession}" var="year">
                <option value="${year}">${year}</option>
            </g:each>
        </select>
    </td>

</tr>
<tr id="program1">
    <td style="width: 30%">Program</td>
    <td style="width: 70%">
        <g:select name="programId" id="programId" optionKey="id"
                  class="university-size-1-1"
                  optionValue="courseName" from="${programList}" noSelection="['null': ' Select Program']"/>
    </td>
</tr>
<tr id="session1">
    <td style="width: 30%">Academic Session
    </td>
    <td style="width: 70%">
        <select name="academicSession1" id="academicSession1"
                class="university-size-1-1" onchange="loadSessionSemester(this)">
            <option value="null">Select Academic Session</option>
            <g:each in="${academicSession}" var="year">
                <option value="${year}">${year}</option>
            </g:each>
        </select>
    </td>
</tr>
<tr id="semester1">
    <td style="width: 30%">Semester</td>
    <td style="width: 70%">
        <g:select name="programTerm" id="semesterList" optionKey=""
                  class="university-size-1-1" disabled=""
                  value="" optionValue="" from="" noSelection="['null': ' Select Semester']"/>

    </td>
</tr>
<tr id="format">
    <td style="width: 30%">
        <label for="resultFormat">Select Format</label>
    </td>
    <td style="width: 70%">
        <select name="resultFormat" id="resultFormat" class="university-size-1-1">
            <option value="null">Select Format</option>
            <option value="pdf">PDF</option>
            <option value="excel">Excel</option>
        </select>
    </td>

</tr>
<tr id="gender">
    <td style="width: 30%">
        <label for="gender">Gender</label>
    </td>
    <td style="width: 70%">
        <input type="checkbox" name="genders" value="male" checked>Male
        <input type="checkbox" name="genders" value="female">Female
    </td>

</tr>
<tr id="radioGender">
    <td style="width: 30%">
        <label for="gender">Gender</label>
    </td>
    <td style="width: 70%">
        <input type="radio" name="gender" value="male" checked>Male
        <input type="radio" name="gender" value="female">Female
    </td>

</tr>
<tr id="radioCategory">
    <td style="width: 30%">
        <label for="category">Category</label>
    </td>
    <td style="width: 70%">
        <input type="radio" name="category" value="General" checked>General
        <input type="radio" name="category" value="OBC">OBC
        <input type="radio" name="category" value="S.T">ST
        <input type="radio" name="category" value="SC">SC
        <input type="radio" name="category" value="Minority">Minority
        <input type="radio" name="category" value="PH">PH

    </td>

</tr>
<tr id="category">
    <td style="width: 30%">
        <label for="category">Category</label>
    </td>
    <td style="width: 70%">
        <input type="checkbox" name="category" value="General" checked>General
        <input type="checkbox" name="category" value="OBC">OBC
        <input type="checkbox" name="category" value="S.T">ST
        <input type="checkbox" name="category" value="SC">SC
        <input type="checkbox" name="category" value="Minority">Minority
        <input type="checkbox" name="category" value="PH">PH

    </td>

</tr>
<tr id="foreign"> <td style="width: 100%">
    <label>List of Foreign Students</label>
</td></tr>
<tr id="minority"><td style="width: 100%">
    <label>List of Minority Students</label>
</td></tr>

<tr id="courseCode">
        <td style="width: 30%"><label>Course Code </label></td>

        <td style="width: 45%">
            <input type="text"  name="subjectCode" id="subjectCode" class="university-size-1-2"/>
        </td>
        %{--<td style="width: 25%">--}%
            %{--<input type="button" value="Load Details" class="university-button"--}%
                   %{--onclick="loadSubjectDetails()"/><label id="loadError"></label>--}%
        %{--</td>--}%
</tr>

    %{--<tr id="courseName">--}%
        %{--<td style="width: 30%"><label>Course Name</label></td>--}%
        %{--<td style="width: 70%">--}%
            %{--<input type="text" class="university-size-1-2" name="subjectName" readonly--}%
                   %{--id="SubjectName"/>--}%
            %{--<input type="hidden" id="theory" value=""/>--}%
            %{--<input type="hidden" id="practical" value=""/>--}%
        %{--</td>--}%
    %{--</tr>--}%
    %{--<tr id="oDetails">--}%
        %{--<td style="width: 30%"><label>Other Details</label>--}%
        %{--</td>--}%
        %{--<td style="width: 70%">--}%

            %{--<table class="inner university-size-1-1" id="ltpc">--}%

            %{--</table>--}%
        %{--</td>--}%
    %{--</tr>--}%

<tr>
    <td><br/></td>
</tr>
<tr id="submitButton">
    <td>
        <g:hiddenField name="value" id="flagValue" value=""/>
    </td>
    <td>
        <g:hiddenField name="inExcel" id="inExcel" value=""/>
    </td>
    <td>
        <input type="submit" name="create" class="save university-button"
               value='Submit' onclick="return validatePopUp()"/>
    </td>
</tr>

</table>
</g:uploadForm>
</div>

</fieldset>

</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    %{--Div for Graph--}%
    <div class="modal-dialog modal-lg" style="margin-top: 50px;width: 90%">
        <div class="modal-content" id="printable" style="background-color: rgba(195, 188, 187, 0.9);">
            <div class="modal-header" style="text-align: center;">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" ><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Data</h4>
            </div>

            <div class="modal-body" style="height: 450px;margin-left: 2%;">
                <div class="text-center">
                    <div id="graph" class="col-md-10"></div>

                </div>
            </div>
        </div>

        <div style="height: 50px" class="nonPrintable"><button class="btn btn-primary"
                                                               onclick="downloadAsPdf('#printable')">Print</button>
        </div>
    </div>
</div>

<div class="modal fade" id="bstStrategyModal" tabindex="-1" role="dialog" style="overflow-y: scroll;" aria-labelledby="myModalLabel" aria-hidden="true">
    %{--Div for showing Reports--}%
    <div class="modal-dialog modal-lg" style="margin-top: 80px;width: 1200px">

        <div class="modal-content">
            <div class="modal-header" style="text-align: center;">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="reLoad()"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myBestModalLabel"></h4>
                <div id="btnDiv"></div>
            </div>
            <div class="modal-body" id="modalBody" style="display: none">

                <div id="graphDiv" class="table-responsive" style="overflow: scroll;height: 600px">
                    <div class="print button">Print</div>

                    <div class="columnSelectorWrapper button">
                        <input id="colSelect1" type="checkbox" class="hidden">
                        <label class="columnSelectorButton" for="colSelect1">Column</label>
                        <div id="columnSelector" class="columnSelector">
                            <!-- this div is where the column selector is added -->
                        </div>
                    </div>
                    <table class="tablesorter" id="reportTab" name="reportTab">
                       <thead><tr id="headRow">

                       </tr></thead>
                        <tbody id="bodyRow">

                        </tbody>
                    </table>
                </div>

            </div>
            <div class="modal-body" id="noResult" style="display: none">
                No Result to Display
            </div>
        </div>
    </div>
</div>
<script>
    /*function for showing graph*/
    $(document).ready(function () {

        if ("${params.graphMap}") {
            $('#myModal').modal('show')
            drawBarChart("${params.graphMap}");

        }

    });
</script>
<script>
    function validatePopUp() {
        /*function for validating params(whether user has selected form elements or not)*/
        var val = $('#flagValue').val()
        var check1 = null, check2 = null, check3 = null, check4 = null;
        if (val == 'studentApproved') {
            check1 = $('#approvedProgram').val()
            check2 = $('#approvedAdmissionYear').val()
            if (check1 == 'null'|| check2 == 'null') {
                alert("please select values")
                return false;
            }
//             return true;
        }
        else if (val == 'studentUnapproved') {
            check1 = $('#approvedProgram').val()
            check2 = $('#approvedAdmissionYear').val()
            if (check1 == 'null' || check2 == 'null') {
                alert("please select values")
                return false;
            }
//             return true;
        }
        else if(val == 'approvedRegistrationByFA'){
            check1 = $('#programId').val()
            check2 = $('#academicSession1').val()
            check3 = $('#semesterList').val()

            if (check1 == 'null' || check2 == 'null' || check3 == '') {
                alert("please select values")
                return false;
            }

        }
        else if (val == 'empDetails') {
              check1 = $('#employeeName').val()

              check2 = $('#programDepartment').val()


            if (check2 == 'null') {
                alert("Please select Department")

                return false;
            }
//             return true;
        }
        else if (val == 'approvedSemesterRegistrationInExcel') {
            check1 = $('#programId').val()
            check2 = $('#academicSession1').val()
            check3 = $('#semesterList').val()

            check4 = $('#resultFormat').val()
            if (check1 == 'null' || check2 == 'null' || check3 == '' || check4 == 'null') {
                alert("please select values")
                return false;
            }
//            return true;
        }
        else if (val == 'studentsByCategory') {
            check1 = $('#approvedProgram').val()
            check2 = $('#approvedAdmissionYear').val()

            if (check1 == 'null' || check2 == 'null') {
                alert("please select values")
                return false;
            }
//            return true;
        }
        else if(val == 'studentInfoByCourse'){
            check1 = $('#approvedProgram').val()

            if(check1 == 'null'){
                alert("Please select values")
                return false;
            }
        }
        else if (val == 'studentsByGender') {
            check1 = $('#approvedProgram').val()
            check2 = $('#approvedAdmissionYear').val()

            if (check1 == 'null' || check2 == 'null') {
                alert("please select values")
                return false;
            }
//            return true;
        }
        else if(val == 'foreignStudent'){
            check1 = $('#approvedAdmissionYear').val()
            if (check1 == 'null'){
                alert("please select values")
                return false;
            }
        }
        else if(val == 'minorityStudent'){
            check1 = $('#approvedAdmissionYear').val()
            if (check1 == 'null'){
                alert("please select values")
                return false;
            }
        }
        else if (val == 'studentsCategory') {
            check1 = $('#approvedProgram').val()
            check2 = $('#approvedAdmissionYear').val()

            if (check1 == 'null' || check2 == 'null') {
                alert("please select values")
                return false;
            }
//            return true;
        }
        else if (val == 'categoryGraph') {
            check1 = $('#approvedAdmissionYear').val()
            if (check1 == 'null') {
                alert("please select values")
                return false;
            }
//            return true;
        }

        else if (val == 'subjectStudent') {
            check1 = $('#academicSession').val()
            check2 = $('#subjectCode').val()
            if (check1 == 'null'|| check2 == '') {
                alert("please select values")
                return false;
            }
//            return true;
        }
        else if (val == 'registrationDetails') {

            check1 = $('#programId').val()
            check2 = $('#academicSession1').val()
            check3 = $('#semesterList').val()
            if (check1 == 'null' || check2 == 'null' || check3 == '') {
                alert("please select value")
                return false;
            }
        }
        else if (val == 'regDetailsWithBack') {

            check1 = $('#programId').val()
            check2 = $('#academicSession1').val()
            check3 = $('#semesterList').val()

            if (check1 == 'null' || check2 == 'null' || check3 == '') {
                alert("please select value")
                return false;
            }
        }
        else if (val == 'projectThesis') {
            check1 = $('#academicSession').val()
//            check2 = $('#academicSession1').val()
//            check3 = $('#semesterList').val()
           if (check1 == 'null' ) {
               alert("please select values")
              return false;
            }
        }
        else if (val == 'courseUnapproved') {
            check1 = $('#courseUnapproved').val()
            if (check1 == 'null') {
                alert("please select values")
                return false;
            }
//            return true;
        }
        else if (val == 'grade') {
            check1 = $('#academicSession').val()
            check2 = $('#courseInstructor').val()
//            check3 = $('#resultFormat').val()

            if (check1 == 'null' || check2 == 'null') {
                alert("please select values")
                return false;
            }
//            return true;
        }
        else if (val == 'studentInfo') {
            check1 = $('#multiProgram').val()
            check2 = $('#multiDepartment').val()
            check3 = $('#multiSem').val()

            if (check1 === null || check2 === null || check3 === null) {

                alert("Please select values")
                return false;
            }
        }

        else if (val == 'passoutStudents') {
            check1 = $('#multiProgram').val()
            check2 = $('#multiDepartment').val()
            if (check1 === null || check2 == null) {
                alert("please select values")
                return false;
            }
        }
        else if (val == 'category') {
            check1 = $('#categorySession').val()
            if (check1 == 0) {
                alert("please select values")
                return false;
            }
//            return true;
        }
        else if (val == 'categoryGender') {
            check1 = $('#categoryGenderSession').val()
            if (check1 == 0) {
                alert("please select values")
                return false;
            }
//            return true;
        }
        else if (val == 'admissionUnapproved') {
            $('#admissionApprovedStudyCentre').val('')
            check1 = $('#admissionUnapprovedStudyCentre').val()
            if (check1 == 'null') {
                alert("please select values")
                return false;
            }
//            return true;
        }
        else if (val == "admissionApproved") {
            $('#admissionUnapprovedStudyCentre').val('')
            check1 = $('#admissionApprovedStudyCentre').val()
            if (check1 == 'null') {
                alert("please select values")
                return false;
            }
        }
        else if (val == "admissionSelfRegistration") {
            check1 = $('#admissionSelfRegistrationSession').val()
            if (check1 == 0) {
                alert("please select values")
                return false;
            }
        }
        else if (val == "dailyAdmissionReport") {
            check1 = $('#dailyAdmissionFromDate').val()
            check2 = $('#dailyAdmissionToDate').val()
            if (!check1 || !check2) {
                alert("please select values")
                return false;
            }
        }
        else if (val == "studyCentreFeePaid") {
            check1 = $('#studyCentreFeeFromDate').val()
            check2 = $('#studyCentreFeeToDate').val()
            check3 = $('#feePaidStudyCentre').val()
            if (!check1 || !check2) {
                alert("please select values")
                return false;
            }
        }
        else if (val == "dailyFeePaid") {
            check1 = $('#feeFromDate').val()
            check2 = $('#feeToDate').val()
            if (!check1 && !check2) {
                alert("please select values")
                return false;
            }
        }
        else if (val == "sessionProgramWiseFeePaid" || val == "sessionProgramWiseFeeNotPaid") {
            check1 = $('#semesterList').val()
            if (check1 == 'null') {
                alert("please select values")
                return false;
            }
        }
        else if (val == "paymentModeReport") {
            check1 = $('#paymentModeFromDate').val()
            check2 = $('#paymentModeToDate').val()
            check3 = $('#paymentMode').val()
            if (!check1 || !check2 || check3 == 'null') {
                alert("please select values")
                return false;
            }
        }
        else if (val == "categoryStudentList") {
            check1 = $('#studentCategory').val()
            if (check1 == 'null') {
                alert("please select values")
                return false;
            }
        }
       if(val == "studentApproved"|| val == "studentUnapproved"|| val == "approvedSemesterRegistrationInExcel"|| val == "approvedRegistrationByFA" || val =="studentsByCategory"|| val == "categoryGraph"){

       }
        else{

           submitForm();

       }
    }

    function submitForm() {
        $(document).ready(function () {

            var options = {
                beforeSend: function () {
                    $.blockUI();
                },

                uploadProgress: function (event, position, total, percentComplete) {

                },
                success: function (data) {
                    $.unblockUI();
                    $("#bstStrategyModal").modal('show');
//                    $("#cart").on('hide', function () {
//                        window.location.reload();
//                    });
                    var tabId = $("#reportTab")
                    if(data.finalList.length>0) {
                        $('#modalBody').show()
                        $('#headRow').show()
                        $('.ui-dialog').hide()
                        $('.ui-widget-overlay').hide()
                        if (data.headList.length > 0) {
                            var tabThBody = ''
                            for (var i = 0; i < data.headList.length; i++) {

                                if (i == 0) {
                                    $('#headRow').append('<th data-priority="critical">' + data.headList[i] + '</th>')
                                }
                                else {
                                    $('#headRow').append('<th>' + data.headList[i] + '</th>')
                                }

                            }

                        }
                        if (data.finalList.length > 0) {
                            for (var j = 0; j < data.finalList.length; j++) {

                                var td = ''
                                for (var k = 0; k < data.finalList[j].length; k++) {
                                    td = td + '<td>' + data.finalList[j][k] + '</td>';
                                }
                                $('#bodyRow').append('<tr>' + td + '</tr>')
                            }
                        }
                        colSelector();
                    }

                    else{
                        $('.ui-dialog').hide()
                        $('.ui-widget-overlay').hide()
                        $("#noResult").show()
                    }

                },
                complete: function (response) {
                    $.unblockUI();
                },
                error: function () {
                    $.unblockUI();
                }

            };
//            if(params.value)
                $("#reports").ajaxForm(options);
        });

    }



    function colSelector(){
        /*function for table selector plugin*/
        $(function() {

            $(".tablesorter").tablesorter({
                theme: 'blue',
                widgets: ["zebra", "filter", "print", "columnSelector"],
                widgetOptions : {
                    columnSelector_container : $('#columnSelector'),
                    columnSelector_name : 'data-name',

                    print_title      : '',          // this option > caption > table id > "table"
                    print_dataAttrib : 'data-name', // header attrib containing modified header name
                    print_rows       : 'f',         // (a)ll, (v)isible or (f)iltered
                    print_columns    : 's',         // (a)ll, (v)isible or (s)elected (columnSelector widget)
                    print_extraCSS   : '',          // add any extra css definitions for the popup window here
                    print_styleSheet : '', // add the url of your print stylesheet
                    // callback executed when processing completes - default setting is null
                    print_callback   : function(config, $table, printStyle){
                        // do something to the $table (jQuery object of table wrapped in a div)
                        // or add to the printStyle string, then...
                        // print the table using the following code
                        $.tablesorter.printTable.printOutput( config, $table.html(), printStyle );
                    }
                }
            });

            $('.print').click(function(){
                $('.tablesorter').trigger('printTable');
            });

        });
    }
//function getTHId(i){
//    if(i==0)
//        return 'zero'
//    else if(i==1)
//        return 'one'
//    else if(i==2)
//        return 'two'
//    else if(i==3)
//        return 'three'
//    else if(i==4)
//        return 'four'
//    else if(i==5)
//        return 'five'
//
//}
function reLoad(){
    location.reload(true);
}
</script>
<script>
    $(function(){
        $(".select2").multiselect();
    });
//    $('.select2').SumoSelect({ selectAll: true });
//    $('.select1').SumoSelect({ okCancelInMulti: true, selectAll: true });
</script>
</body>
</html>
