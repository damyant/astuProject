<%--
  Created by Damyant Software Pvt Ltd.
  User: chandan
  Date: 22/9/14
  Time: 11:49 AM
--%>


<html>
<head>
    %{--This page is for showing list of equipment(to search equipments)And also provides options for viewing editing and deleting equipments--}%
    <meta name="layout" content="main"/>
    <title>Equipment List</title>
    <script type="text/javascript">
        function confirmBeforeDelete(equipmentId) {
//
            var status = confirm("Are you sure want to delete")
            if (status) {
                $.ajax({
                    type: "post",
                    url: url('equipment', 'deleteEquipment', ''),
                    data: '&equipmentId=' + equipmentId,
                    success: function (data) {
                        if (data.status == true) {
                            location.reload(true);
                        }
                        else {
//                            $("#successMessage").html("")
                        }
                    }
                });

            }
        }
    </script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admin.js')}"></script>
</head>

<body>
<div id="main">
    <div style="width: 100%; text-align: center;">
        <g:form id="catalogSearch" name="catalogSearch">
            <input type="text" id="search" name="searchText" class="university-size-1-4"
                   placeholder="   Search Equipment "
                   style="border-radius: 1px;height: 20px;line-height: 20px;">
            By <label style="margin: 1px 10px;"><input name="searchBy" type="radio" value="byName">Name</label>
            <label style="margin: 1px 10px;"><input name="searchBy" type="radio" value="byId">ID</label>
            <label style="margin: 1px 10px;"><input name="searchBy" type="radio" value="byManufacturer">Manufacturer
            </label>
            <label style="margin: 1px 10px;"><input name="searchBy" type="radio" value="byType">Type</label>
            <input type="button" class="university-button" value="Search" onclick="searchEquipment()">
        </g:form>
    </div>
    <fieldset class="form">
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:if test="${params.type == 'update'}">
            <table id="courseTab" class="university-size-full-1-1 university-table-1-4 university-table-text-left ">
        </g:if>
        <g:else>
            <table id="courseTab" class="university-size-full-1-1 university-table-1-3 university-table-text-left " style="border: 1px solid #f2f5f7">
        </g:else>
        <table id="equipmentTable">
            <thead>
            <tr>
                <th>Equipment Name</th>
                <th>Equipment Id</th>
                <th>Manufacturer</th>
                <th>Equipment Type</th>
                <th>&nbsp;</th>
                <sec:ifAnyGranted roles="ROLE_ADMIN">
                    <th>&nbsp;</th>
                <th>&nbsp;</th>
                </sec:ifAnyGranted>
            </tr>
            </thead>
            <tbody>

            <g:each var="equipment" in="${equipmentObjList}">
                <tr>
                    <td>${equipment.equipmentName}</td>
                    <td>${equipment.equipmentId}</td>
                    <td>${equipment.manufacturer}</td>
                    <td>${equipment.equipmentType}</td>
                    <td><g:link controller="equipment" action="viewEquipmentDetail"
                                    class="university-text-decoration-none"
                                   params="[id: equipment.id]"><button class="university-button">View</button></g:link>
                    </td>
                <sec:ifAnyGranted roles="ROLE_ADMIN">

                    <td><g:link controller="equipment" action="createEquipment"
                                       class="university-text-decoration-none"
                                  params="[id: equipment.id]"><button class="university-button">Update</button></g:link>
                    </td>
                    <td><button class="university-button"
                                onclick="confirmBeforeDelete('${equipment.id}')">Delete</button></td>
                </sec:ifAnyGranted>

                </tr>
            </g:each>
            </tbody>
        </table>
        %{--<div class="paginateButtons">--}%
        %{--<g:if test="${params.type == 'update'}">--}%
        %{--<g:paginate controller="course" action="listOfCourses"  total="${courseInstanceTotal}" params="['type':'update']"/>--}%
        %{--</g:if>--}%
        %{--<g:else>--}%
        %{--<g:paginate controller="course" action="listOfCourses"  total="${courseInstanceTotal}" />--}%
        %{--</g:else>--}%
        %{--</div>--}%
    </fieldset>
</div>

</body>

</html>