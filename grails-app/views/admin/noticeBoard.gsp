<%--
  Created by Damyant Software Pvt Ltd.
  User: damyant
  Date: 9/18/14
  Time: 1:24 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--Page for uploading new notice (& assign user role to notice to ensure which user can see the notice)--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
<g:if test="${noticeIns}">

    <script type="text/javascript">

    $(document).ready(function () {
        if(${noticeIns.isArchive})  {
            $('#Archive').click()
        }
        else{
            $('#Active').click()
        }


        var roles = ${noticeRollIns.id}
        var roleSize = ${noticeRollIns.id.size()}
        for (var i=0;i<roleSize;i++ ){
            var curRole = roles[i]

            $(".checkInput[name= 'roles'][value=" +curRole + "]").attr('checked', 'checked');
        }

    })
    </script>
</g:if>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Notice Upload</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:uploadForm controller="admin" action="noticeBoardSave" id="ntc1"  name="ntc1" >

            <table class="university-size-full-1-1 inner spinner">
                <tr>
                    <g:if test="${noticeIns}">
                        <input type="hidden" value="${noticeIns?.id}" name="noticeUpdate"/>
                    </g:if>
                    <td class="university-size-1-3">Title<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3"><input type="text" name="noticeHeader" class="university-size-1-1" value="${noticeIns?.noticeHeader}"></td>
                </tr>
                <tr>
                    <g:if test="${noticeIns}">
                        <td class="university-size-1-3">Upload Pdf File</td>
                    </g:if>
                    <g:else>
                    <td class="university-size-1-3">Upload Pdf File<span class="university-obligatory">*</span></td>
                    </g:else>
                        <td class="university-size-1-3">
                        <input type='file' id="fle" name="fle" >
                    </td>

                </tr>
                <g:if test="${noticeIns}">
                    <tr>
                        <td class="university-size-1-3">Notice Status<span class="university-obligatory">*</span></td>
                        <td class="university-size-1-3">
                            <label><input type='radio' id="Active" value="Active" name="noticeStatus" /> Active</label>
                            <label> <input type='radio' id="Archive" value="Archive"  name="noticeStatus" />Archive</label>
                        </td>

                    </tr>
                </g:if>
                <tr>
                    <td class="university-size-1-3">View To<span class="university-obligatory">*</span></td>
                    <td colspan="2">
                        <table class="university-size-full-1-1 inner">
                        <g:each in="${roleList}" var="role" status="index">
                            <g:if test="${index%2==0}">
                                <tr>
                            </g:if>
                            <td><label><input type="checkbox" class="checkInput" id="role${role.id}" name="roles" value="${role.id}">${role.authority}</label></td>
                            <g:if test="${index%2!=0}">
                                </tr>
                            </g:if>

                        </g:each>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="submit" class="university-size-1-2 university-button" onclick="validateLibrary()" value="Submit"/>
                    </td>
                    <td></td>
                </tr>
            </table>
        </g:uploadForm>
    </fieldset>
</div>


</body>
</html>