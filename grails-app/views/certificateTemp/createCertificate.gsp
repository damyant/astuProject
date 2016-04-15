<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 28/4/15
  Time: 4:08 PM
--%>

<%@ page import="java.text.SimpleDateFormat" contentType="text/html;charset=UTF-8" %>
%{--Page for creating certificate --}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <link rel='stylesheet' href="${resource(dir: 'bootstrap-3.3.4-dist/css', file: 'bootstrap.min.css')}"
          type='text/css'/>
    <g:javascript src="admin.js"></g:javascript>
    <script type="text/javascript">
        $(document).ready(function () {
            var roleList = ${assignedRoleoleList}
            var programType = ${programType}
            var semesterList = ${semesterList}
            for (var i = 0; i < roleList.length; i++) {
                $("input[name='requestedRole'][value=" + roleList[i] + "]").attr('checked', 'checked');
            }
            for (var i = 0; i < programType.length; i++) {
                $("input[name='programType'][value=" + programType[i] + "]").attr('checked', 'checked');
            }
            for (var i = 0; i < semesterList.length; i++) {
                $("input[name='semester'][value=" + semesterList[i] + "]").attr('checked', 'checked');
            }
        })
    </script>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <g:uploadForm controller="certificateTemp" action="saveNewCertificate" name="saveNewCertificate"
                      id="saveNewCertificate">
            <div class="container-fluid col-sm-12">
                <div class="page-header text-center"><h2>Create Certificate</h2></div>
                <g:if test="${flash.message}">
                    <div class="message"><div class="university-status-message">${flash.message}</div></div>
                </g:if>
                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Certificate Name</div>
                    <div class="col-sm-8"><input type="text" name="certificateName" required value="${certificateInast?.nameOfCertificate}"
                                                 id="certificateName" class="col-sm-5"></div>
                    <g:if test="${certificateInast}"><input type="hidden"  name="updateId" value="${certificateInast?.id}"></g:if>
                </div>

                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Requested By</div>
                    <div class="col-sm-8">
                        <div class="col-sm-12">
                            <g:each in="${roleList}" var="listInst" status="inde">
                                <div  class="col-sm-6"><label><input type="checkbox" name="requestedRole" value="${listInst.id}"/>${listInst.authority} </label></div>

                            </g:each>
                        </div>
                    </div>
                </div>
                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Program</div>
                    <div class="col-sm-8">
                        <div class="col-sm-12">
                            <g:each in="${programList}" var="listInst" status="inde">
                                <div  class="col-sm-6"><label><input type="checkbox" name="programType" value="${listInst.id}"/>${listInst.type} </label></div>

                            </g:each>
                        </div>
                    </div>
                </div>
                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Semester</div>
                    <div class="col-sm-8">
                        <div class="col-sm-12">
                            <g:each in="${1..8}" var="listInst" status="inde">
                                <div  class="col-sm-6"><label><input type="checkbox" name="semester" value="${listInst}"/>Semester - ${listInst} </label></div>

                            </g:each>
                        </div>
                    </div>
                </div>

                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4"></div>
                    <div class="col-sm-8"><input type="submit" value="Submit" class="col-sm-5 btn-info"></div>
                </div>

            </div>
        </g:uploadForm>
    </fieldset>
</div>
<script type="text/javascript">
    $(document).ready(function () {

    });

</script>

</body>
</html>