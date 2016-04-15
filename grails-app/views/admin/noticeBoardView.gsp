<%--
  Created by Damyant Software Pvt Ltd.
  User: damyant
  Date: 9/18/14
  Time: 6:20 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--page for showing the list of notice & download the particular notice--}%
<html>
<head>
    <meta name="layout" content="main"/>
    %{--<script src="${resource(dir: 'js', file: 'monthpicker.js')}"></script>--}%

    <script src="${resource(dir: 'js', file: 'admin.js')}"></script>
    <script>
        $(function() {
            $('.monthYearPicker').datepicker({
                changeMonth: true,
                changeYear: true,
                showButtonPanel: true,
                dateFormat: 'yy-mm'
            }).focus(function() {
                        var thisCalendar = $(this);
                        $('.ui-datepicker-calendar').detach();
                        $('.ui-datepicker-close').click(function() {
                            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                            thisCalendar.datepicker('setDate', new Date(year, month, 1));
                        });
                    });
        });
    </script>
    <style>
    .ui-datepicker-calendar {
        display: none;
    }
    </style>

    <title></title>

</head>

<body><div id="main">
    <fieldset class="form">
        <h3>Notice Board</h3>

        <g:if test="${flash.message}">
            <div class="message" role="status">
                <div class="university-status-message">${flash.message} </div>
            </div>

        </g:if>

        <table>

            <thead>
            <tr>
                <g:sortableColumn property="noticeHeader" title="Date" class="university-size-1-4"/>
                <g:sortableColumn property="noticeHeader" title="Description" class="university-size-1-2"/>
                <g:sortableColumn property="fileName" title="Link" class="university-size-1-4"/>
            </tr>
            </thead>
            <tbody>

                <g:each in="${noticeList}" status="i" var="noticeInst">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                        <td>${noticeInst.noticeDate.format('dd-MM-yyyy')}</td>
                        <td>${noticeInst.noticeHeader}</td>
                        <td><g:link controller="admin" action="download" params="[fileName: noticeInst.fileName]"
                                    target="_blank"><img style="width:30px; "
                                                         src="${resource(dir: 'images', file: 'download.png')}"
                                                         class="window"></g:link></td>

                    </tr>
                </g:each>

            </tbody>
        </table>

        <div style="margin-top: 10px;"><label>Previous Notices :</label>
            <g:form controller="admin" action="loadArchiveNotice" id="loadArchiveNoticeForm">
                %{--<input id="Html5Month" type="text" name="month"  />--}%
                <input name="month" class="monthYearPicker"  />

                <input type="submit" value="Load">
            </g:form>
        </div>

    </fieldset>
</div>
</body>
</html>