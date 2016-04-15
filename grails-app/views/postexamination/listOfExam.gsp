<html>
<head>
    %{--Shows list of examinations and provide options to delete and edit it--}%
    <meta name="layout" content="main"/>
    <title></title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'shCore.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.dataTables.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.dataTables.min.css')}" type='text/css'/>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'shCore.css')}" type='text/css'/>
    <style type="text/css" class="init"></style>
    %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'studyCenter.js')}"></script>--}%
</head>

<body>
<div id="main">
    <fieldset class="form">
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <h3>List of Examination</h3>
        <table id="courseTab" class="university-size-full-1-1 university-table-text-left inner">

            <thead>
            <tr>
                <th style="width: 25%;">Course Name</th>
                <th style="width: 15%;">Academic Session</th>
                <th style="width: 15%;">Examination Type</th>
                <th style="width: 15%;">Examination Sub Type</th>
                <th style="width: 10%;">Marks</th>
                <th style="width: 10%;">&nbsp;</th>
                <th style="width: 10%;">&nbsp;</th>
            </tr>
            </thead>
            <tbody>
                <g:each var="exam" in="${examList}">
                    <tr>
                        <td>${exam.subject.subjectName} [${exam.subject.subjectCode}]</td>
                        <td>${exam.academicSession}</td>
                        <td>${postexamination.ExamType.findById(Long.parseLong(exam.examTypes)).examTypeName}</td>
                        <td><g:if test="${exam.examSubType}"> ${postexamination.ExamSubType.findById(Long.parseLong(exam.examSubType)).examSubTypeName}</g:if><g:else>NA</g:else></td>
                        <td>${exam.totalMarks}</td>
                        <td>
                            %{--<input type="submit" value="Update" class="university-button"/>--}%

                            <g:link controller="postExamination" action="createCourseExam"
                                    params="[examId: exam.id]"
                                    class="university-text-decoration-none"><button
                                    class="university-button">Update</button></g:link>
                            </td>
                        <td>
                            <g:link controller="postExamination" action="deleteExamination"
                                    params="[examId: exam.id]" onclick="return confirm('Are you sure? On deletion all related student marks will be deleted.');"
                                    class="university-text-decoration-none"><button
                                    class="university-button">Delete</button></g:link>
                            </td>
                    </tr>
                </g:each>
            </tbody>
        </table>

        %{--<div class="paginateButtons">--}%
            %{--<g:paginate controller="postExamination" action="listOfExam" total="${examListTotal}"/>--}%
        %{--</div>--}%
    </fieldset>
</div>
<script type="text/javascript" language="javascript" class="init">

    $(document).ready(function() {
        $('#courseTab').dataTable( {
            "columnDefs": [
                {
                    "targets": [5,6 ],
                    "searchable": false
                }
            ]
        } );
    } );

</script>
</body>

</html>