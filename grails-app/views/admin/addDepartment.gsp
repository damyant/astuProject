<%--
  Created by IntelliJ IDEA.
  User: chandan's
  Date: 27-05-2015
  Time: 17:44
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for adding & updating Department Name--}%
<html>
<head>
    <title>Add Courses</title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>

    <script type="text/javascript">
        $(window).bind("load", function () {
            if(${updateMode==true}){
                fillCourseInfoUpdate("${courseSession?.sessionOfSubject}")
                $('#updateModes').val('true')
            }
        })
        $(document).ready(function () {
            var courseType="${courseType}";
            if(${updateMode==true}) {
                $("input[name='courseType'][value='"+courseType+"']").attr('checked', 'checked');
            }
        })
    </script>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Add Department</h3>
        <g:form controller="admin" action="saveDepartment" id="saveDepartment" name="saveDepartment" >
            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>
            <table class="inner" style="margin: auto; width: 100%">
                <tr>
                    <td class="university-size-1-3"><p>Department Name <span class="university-obligatory">*</span>
                    </p></td>
                    <td class="university-size-2-3"><g:textField name="name" id="name" maxlength="150" value=""
                                                                 class="university-size-1-2"/>

                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Save" class="university-button"></td>
                </tr>
            </table>

        </g:form>

    </fieldset>
</div>
</body>
</html>