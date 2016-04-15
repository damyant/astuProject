
<%@ page import=" com.university.Role; com.university.UserRole; com.university.User" %>
%{--Page for editing user details--}%
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:javascript src='tabulator.js'/>
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
    <g:if test="${userInstance.department}">
        <script type="text/javascript">
            var gender = "${userInstance.department.id}"

            $('#edit').ready(function () {
                $('#userDepartment').val(gender)
            });
        </script>
    </g:if>
</head>

<body>
<div id="main">
    <div id="edit-user" class="content scaffold-edit" role="main">
        <h3><g:message code="default.edit.label" args="[entityName]"/></h3>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${userInstance}">
            <ul class="errors" role="alert">
                <g:eachError bean="${userInstance}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                            error="${error}"/></li>
                </g:eachError>
            </ul>
        </g:hasErrors>
    <g:form name="edit" action='updateUser'>
        <g:hiddenField name="version" value="${userInstance?.version}"/>
            <g:hiddenField name="id" value="${userInstance?.id}"/>
            <fieldset class="form">

                <%@ page import="com.university.Role; com.university.UserRole; com.university.User" %>



                <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
                    <div class="university-size-1-4"><label for="username">
                        <g:message code="user.username.label" default="Username"/>
                        <span class="required-indicator">*</span>
                    </label></div>

                    <div class="university-size-1-3"><g:textField class="university-size-2-3" name="username"
                                                                  required="" value="${userInstance?.username}"/></div>
                </div>

                <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', '')} required">
                    <div class="university-size-1-4"><label for="password">
                        <g:message code="user.password.label" default="Password"/>
                        <span class="required-indicator">*</span>
                    </label></div>

                    <div class="university-size-1-3"><g:passwordField name="password" class="university-size-2-3"
                                                                      required=""
                                                                      value="${userInstance?.password}"/></div>
                </div>

                <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">

                    <div class="university-size-1-4"><label for="email">
                        <g:message code="user.email.label" default="Email"/></label>
                        <span class="required-indicator">*</span></div>

                    <div class="university-size-1-3"><g:textField name="email" class="university-size-2-3" required=""
                                                                  value="${userInstance?.email}"/></div>

                </div>


                %{--<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">--}%

                    %{--<div><label for="role">--}%
                        %{--<g:message code="user.role.label" default="Role"/></label>--}%
                        %{--<span class="required-indicator">*</span></div>--}%
                %{--<div>--}%
                    %{--<g:if test="${UserRole.findByUser(userInstance) != null}">--}%

                        %{--<g:select from="${Role.list()}" optionKey="authority" optionValue="authority"--}%
                                  %{--value="${role.authority}" name="userRole" class="university-size-2-3"/>--}%

                    %{--</g:if>--}%
                    %{--<g:else>--}%
                        %{--<g:select from="${Role.list()}" optionKey="authority" optionValue="authority" value=""--}%
                                  %{--name="userRole" class="university-size-2-3" noSelection="['': '-Choose role-']"/>--}%

                    %{--</g:else>--}%

                    %{--</div>--}%

                    <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountExpired', 'error')} ">
                        <div><label for="accountExpired">
                            <g:message code="user.accountExpired.label" default="Account Expired"/>

                        </label></div>

                        <div>
                            <g:checkBox name="accountExpired" value="${userInstance?.accountExpired}"/>
                        </div>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountLocked', 'error')} ">
                        <div><label for="accountLocked">
                            <g:message code="user.accountLocked.label" default="Account Locked"/>
                        </label></div>

                        <div>
                            <g:checkBox name="accountLocked" value="${userInstance?.accountLocked}"/>
                        </div>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">
                        <div><label for="enabled">
                            <g:message code="user.enabled.label" default="Enabled"/>

                        </label></div>

                        <div>
                            <g:checkBox name="enabled" value="${userInstance?.enabled}"/>
                        </div>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'passwordExpired', 'error')} ">
                        <div class="university-size-1-4"><label for="passwordExpired">
                            <g:message code="user.passwordExpired.label" default="Password Expired"/>

                        </label></div>

                        <div><g:checkBox name="passwordExpired"
                                         value="${userInstance?.passwordExpired}"/></div>
                    </div>
            </fieldset>
            <fieldset class='editRoleFieldSet'>
                <legend>
                    Please Select Roles
                </legend>
                <table style='border:0px'>
                    <tbody>
                    <g:each in="${roles}" status="i" var='roleInstance'>

                        <tr>
                            <td class='name university-size-1-4' >
                                <label> ${fieldValue(bean: roleInstance, field: "authority")} </label>
                            </td>
                            <td class='value university-size-3-4'>
                                <g:set var="userRoleIds" value="${userRoles.id}"/>

                                <g:if test="${userRoleIds.contains(roleInstance.id)}">

                                    <g:checkBox name="myCheckbox" value="${roleInstance.id}" checked="true"/>

                                </g:if>
                                <g:else>

                                    <g:checkBox name="myCheckbox" value="${roleInstance.id}" checked="false"/>

                        </g:else>
                            </td>

                        </tr>

                    </g:each>
                    </tbody>
                </table>
                %{--<g:if test="${studyCentre!=null}">--}%

               <g:if test="${userInstance.department}">
                    %{--<div id="userDepartmentDiv">--}%
                        %{--User Department :--}%

                    %{--</div>--}%

                   <div class="fieldcontain required" id="studyCentreDiv">
                        <div class="university-size-1-4"> <label for="email">
                            User Department : </label>
                            <span class="required-indicator">*</span>
                        </div>
                       <div class="university-size-1-3">
                       <select name="userDepartment" id="userDepartment"  class="university-size-2-3">
                           <option value>Select User Department</option>
                           <g:each in="${departmentList}" var="department">
                               <option value="${department.id}">${department.name} </option>
                           </g:each>
                       </select>
                        </div>
                   </div>



                </g:if>
            </fieldset>
            <fieldset class="buttons">
               <input type="SUBMIT" class="university-button" value="Update" id="updateUser"/>
                <g:link controller="user" action="index"><input type="button" name="create"
                                                                class="save university-button"
                                                                value="${message(code: 'default.button.cancel', default: 'Cancel')}"/></g:link>
            </fieldset>
        </g:form>
    </div>
<div id="coursePopup">
    <g:form>
        <div class="dialogTab1" id="dialogTab" style="height: 650px; display: none;">

        </div>
    </g:form>
</div>
</div>
</body>
</html>
