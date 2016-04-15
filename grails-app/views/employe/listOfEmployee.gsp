<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 22/9/14
  Time: 11:49 AM
--%>


<html>
<head>
    %{--Shows list of employees(view,edit and delete)--}%
    <meta name="layout" content="main"/>
    <title>View Course</title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admin.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.dataTables.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.dataTables.min.css')}" type='text/css'/>
    <style type="text/css" class="init"></style>
    <script type="text/javascript">

        function confirmBeforeDelete(empId){
            /*Ajax function for confirm and then delete the particular employee*/
           var status= confirm("Are you sure want to delete")
            if(status){
                $.ajax({
                    type: "post",
                    url: url('employe', 'deleteEmployee', ''),
                    data: '&empId=' + empId,
                    success: function (data) {
                      if (data.status == true) {
                            location.reload(true);
//                          $(".message").innerHTML="Deleted Successfully"
                        }
                        else {
//                            $("#successMessage").html("")
                        }
                    }
                });

            }
        }
    </script>
    %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'studyCenter.js')}"></script>--}%
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h1>List of Employees</h1>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:if test="${params.type == 'update'}">
            <table id="courseTab" class="university-size-full-1-1 university-table-1-4 university-table-text-left ">
        </g:if>
        <g:else>
            <table id="courseTab" class="university-size-full-1-1 university-table-1-3 university-table-text-left " style="border: 1px solid #f2f5f7">
        </g:else>
        <thead>
        <tr>
            <th>Employee Name</th>
            <th>Employee Code</th>
            <th>Contact No.</th>
            <th>&nbsp;</th>
            <th>&nbsp;</th>
            <th>&nbsp;</th>
        </tr>
        </thead>
        <tbody>

            <g:each var="employee"  in="${employeeList}">
                <tr>
                    <g:if test="${employee?.middleName}">
                        <td><label>${employee.firstName} </label><label>${employee?.middleName} </label><label>${employee.lastName}</label></td>
                    </g:if>
                    <g:else>
                        <td><label>${employee.firstName} </label><label>${employee.lastName}</label></td>
                    </g:else>
                    <td>${employee.employeeCode}</td>
                    <td>${employee.firstMobileNo}</td>
                    <td><g:link controller="employe" action="viewEmployeeDetail" class="university-text-decoration-none" params="[id:employee.id]"><button class="university-button">View</button></g:link></td>
                    <td> <g:link  controller="employe" action="editEmployee" class="university-text-decoration-none" params="[id:employee.id]"><button class="university-button">Update</button></g:link></td>
                    <td><button class="university-button" onclick="confirmBeforeDelete('${employee.id}')"> Delete</button></td>
                </tr>
            </g:each>
         </tbody>
    </table>
    </fieldset>
</div>


<script type="text/javascript" language="javascript" class="init">
    $(document).ready(function () {
        $('#courseTab').dataTable({
            "columnDefs": [
                {
                    "targets": [ 3,4,5],
                    "searchable": false
                }
            ]
        });
    });

</script>
</body>
</html>