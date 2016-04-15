
<html>
%{--page for showing list of programs(program details) and provide button for showing details of particular program--}%
%{--Also provides button for updating/editing and deleting the program--}%
<head>
    <meta name="layout" content="main"/>
    <title>View Course</title>
    %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'studyCenter.js')}"></script>--}%
    <script type="text/javascript" src="${resource(dir: 'js', file: 'shCore.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.dataTables.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.dataTables.min.css')}" type='text/css'/>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'shCore.css')}" type='text/css'/>
    <style type="text/css" class="init"></style>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:if test="${params.type == 'update'}">


            <table id="courseTab" class="university-size-full-1-1 university-table-text-left table table-hover table-bordered results ">
        </g:if>
        <g:else>


            <table id="courseTab" class="university-size-full-1-1 university-table-text-left table table-hover table-bordered results" style="border: 1px solid #f2f5f7">
        </g:else>
        <thead>
        <tr>
            <th style="width: 30%;">Programme Name</th>
            %{--<th style="width: 15%;">Programme Session</th>--}%
            <th style="width: 15%;">Programme Type</th>
            <th style="width: 20%;">Programme Mode</th>
            <th style="width: 20%;">&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <g:if test="${params.type == 'update'}">
            <g:each var="course"  in="${courseSessionList}">
                <tr>
                    <td style="line-height: 18px;">${course.programDetailId.courseName}</td>
                    %{--<td>${course.sessionOfProgram}</td>--}%
                    <td>${course.programDetailId.programType.type}</td>
                    <td>${course.programDetailId.courseMode.modeName}</td>

                    <td><div class="university-float-right">
                        %{--<input type="submit" value="Update" class="university-button"/>--}%

                         <g:link  controller="course" action="createNewCourse" params="[courseSessionId:course.id,semester:course.programDetailId.noOfTerms]" class="university-text-decoration-none"><button class="university-button"> Update</button></g:link>
                        <g:link  controller="course" action="deleteCourse" params="[courseSessionId:course.id,semester:course.programDetailId.noOfTerms]" class="university-text-decoration-none"><button class="university-button"> Delete</button></g:link>

                        %{--<input type="button" value="Delete"  class="university-button"/>--}%
                    </div></td>
                </tr>
            </g:each>
        </g:if>
        <g:else>
            <g:each var="course"  in="${courseSessionList}">
                <tr>
                    <td  style="line-height: 18px;">${course.programDetailId.courseName}</td>
                    %{--<td>${course.sessionOfProgram}</td>--}%
                    <td>${course.programDetailId.programType.type}</td>
                    <td>${course.programDetailId.courseMode.modeName}</td>
                    <td><div class="university-float-right">
                       <g:link  controller="course" action="viewCourses" params="[courseSessionId:course.id,semester:course.programDetailId.noOfTerms,type:'view']" class="university-text-decoration-none"><button class="university-button"> View</button></g:link>
                    </div></td>
                </tr>
            </g:each>
        </g:else>
        </tbody>
    </table>
        %{--<div class="paginateButtons">--}%
            %{--<g:paginate controller="course" action="listOfCourses" total="${courseInstanceTotal}"/>--}%
        %{--</div>--}%
    </fieldset>
</div>
<script type="text/javascript" language="javascript" class="init">

    $(document).ready(function() {
        $('#courseTab').dataTable( {
            "columnDefs": [
                {
                    "targets": [ 2,3 ],
                    "searchable": false
                }
            ]
        } );
    } );

</script>

</body>


</html>