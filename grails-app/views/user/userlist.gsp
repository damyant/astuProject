<%@ page import="com.university.User" %>

<!DOCTYPE html>
<html>
<head>
    %{--Page for showing list of users and provide option to edit user details and reset password --}%
    <title></title>
    <meta name="layout" content="main">
    <g:javascript src='admin.js'/>
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'shCore.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.dataTables.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.dataTables.min.css')}" type='text/css'/>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'shCore.css')}" type='text/css'/>
    <style type="text/css" class="init"></style>
</head>
<body>
<div id="main">
    <fieldset class="form">
        <div id="list-user" class="content scaffold-list" role="main">
            <h3><g:message code="default.list.label" args="[entityName]"/></h3>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table class="university-table-1-7 inner" id="userListTable">
                <thead>
                <tr>
                    <th>${message(code: 'user.username.label', default: 'Username')}</th>
                    <th>${message(code: 'user.email.label', default: 'Email')}</th>
                    <th>${message(code: 'user.accountExpired.label', default: 'Account Expired')}</th>
                    <th>${message(code: 'user.accountLocked.label', default: 'Account Locked')}</th>
                    <th>${message(code: 'user.enabled.label', default: 'Enabled')}</th>
                    <th>${message(code: 'user.passwordExpired.label', default: 'Password Expired')}</th>
                    <th>${message(code: 'user.enabledited.label', default: 'Edit')}</th>
                    <th>${message(code: 'user.reset.label', default: 'Reset Password')}</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${userInstanceList}" status="i" var="userInstance">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td>${fieldValue(bean: userInstance, field: "username")}</td>
                        <td>${fieldValue(bean: userInstance, field: "email")}</td>
                        <td><g:formatBoolean boolean="${userInstance.accountExpired}"/></td>
                        <td><g:formatBoolean boolean="${userInstance.accountLocked}"/></td>
                        <td><g:formatBoolean boolean="${userInstance.enabled}"/></td>
                        <td><g:formatBoolean boolean="${userInstance.passwordExpired}"/></td>
                        <td><g:link class="university-text-decoration-none" action="editUser" target="_blank"
                                    id="${userInstance.id}"><button>Edit</button></g:link></td>
                        <td><g:link class="university-text-decoration-none" controller="user" target="_blank"
                                    action="resetPassword"
                                    params="[id: userInstance.id]"><button>Reset Password</button></g:link></td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
    </fieldset>
</div>
<script type="text/javascript" language="javascript" class="init">
    $(document).ready(function () {
        $('#userListTable').dataTable({
            "columnDefs": [
                {
                    "targets": [ 2, 3, 4, 5, 6, 7 ],
                    "searchable": false
                }
            ]
        });
    });

</script>

</body>
</html>
