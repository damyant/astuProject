<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 28/4/15
  Time: 4:08 PM
--%>

<%@ page import="java.text.SimpleDateFormat" contentType="text/html;charset=UTF-8" %>
%{--page for creating template for certificate--}%
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <link rel='stylesheet' href="${resource(dir: 'bootstrap-3.3.4-dist/css', file: 'bootstrap.min.css')}"
          type='text/css'/>
    <g:javascript src="admin.js"></g:javascript>
</head>

<body>
<div id="main">
    <fieldset class="form">
    %{--<div contenteditable="true" style="height: 100%;border: dashed 1px black;"></div>--}%
        <g:uploadForm controller="certificateTemp" action="saveNewCertificateTemplate" name="saveNewCertificateTemplate"
                      id="saveNewCertificateTemplate">
            <div class="container-fluid col-sm-12">
                <div class="page-header text-center"><h2>Create Certificate</h2></div>
                <g:if test="${flash.message}">
                    <div class="message"><div class="university-status-message">${flash.message}</div></div>
                </g:if>
                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Certificate Name</div>

                    <div class="col-sm-8"><g:select name="certificateName" from="${certificateList}" optionKey="id"
                                                    value="${certificateInast?.id}"
                                                    class="col-sm-5" optionValue="nameOfCertificate"
                                                    noSelection="['': ' Select Certificate']"/></div>
                    <g:if test="${certificateTemplateInst?.id}">
                        <input type="hidden" name="updateCertificate" value="${certificateTemplateInst?.id}">
                    </g:if>
                </div>


                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Institute Name Header1</div>

                    <div class="col-sm-8"><input type="text" name="instituteNameHeader1" required
                                                 value="${certificateTemplateInst?.instituteNameHeader1}"
                                                 id="instituteNameHeader1" class="col-sm-5"></div>
                </div>

                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Institute Name Header2</div>

                    <div class="col-sm-8"><input type="text" name="instituteNameHeader2" required
                                                 value="${certificateTemplateInst?.instituteNameHeader2}"
                                                 id="instituteNameHeader2" class="col-sm-5"></div>
                </div>

                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Institute Address</div>

                    <div class="col-sm-8"><input type="text" name="instituteAddress" required
                                                 value="${certificateTemplateInst?.instituteAddressHeader}"
                                                 id="instituteAddress" class="col-sm-5"></div>
                </div>

                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Institute Logo</div>
                    <g:if test="${certificateTemplateInst}">
                        <div class="col-sm-8"><input type="file" name="instituteLogo"
                                                     class="col-sm-5"></div>
                    </g:if>
                    <g:else>
                        <div class="col-sm-8"><input type="file" name="instituteLogo" required
                                                      class="col-sm-5"></div>
                    </g:else>
                </div>

                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Ref</div>

                    <div class="col-sm-8"><input type="text" name="ref" required
                                                 value="${certificateTemplateInst?.refNo}"
                                                 id="ref" class="col-sm-5"></div>
                </div>

                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">From Name</div>

                    <div class="col-sm-8"><input type="text" name="fromName" required
                                                 value="${certificateTemplateInst?.fromName}"
                                                 id="fromName" class="col-sm-5"></div>
                </div>

                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">From Designation</div>

                    <div class="col-sm-8"><input type="text" name="fromDesignation" required
                                                 value="${certificateTemplateInst?.fromPosition}"
                                                 id="fromDesignation" class="col-sm-5"></div>
                </div>

                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Certificate Header</div>

                    <div class="col-sm-8"><input type="text" name="certificateHeader" required
                                                 value="${certificateTemplateInst?.header}"
                                                 id="certificateHeader" class="col-sm-5"></div>
                </div>

                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Certificate Paragraph1</div>

                    <div class="col-sm-8"><g:textArea name="paragraph1" required="required" rows="5" maxlength="500"
                                                    value="${certificateTemplateInst?.paragraph1}"
                                                    id="paragraph1" class="col-sm-8"></g:textArea></div>
                </div>

                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Certificate Paragraph2</div>

                    <div class="col-sm-8"><g:textArea name="paragraph2" rows="5" maxlength="500"
                                                    value="${certificateTemplateInst?.paragraph2}"
                                                    id="paragraph2" class="col-sm-8"></g:textArea></div>
                </div>

                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Certificate Paragraph3</div>

                    <div class="col-sm-8"><g:textArea name="paragraph3" rows="5" maxlength="500"
                                                    value="${certificateTemplateInst?.paragraph3}"
                                                    id="paragraph3" class="col-sm-8"></g:textArea></div>
                </div>

                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Certificate Paragraph4</div>

                    <div class="col-sm-8"><g:textArea name="paragraph4" rows="5" maxlength="500"
                                                    value="${certificateTemplateInst?.paragraph4}"
                                                    id="paragraph4" class="col-sm-8"></g:textArea></div>
                </div>

                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Certificate Paragraph5</div>

                    <div class="col-sm-8"><g:textArea name="paragraph5" rows="5" maxlength="500"
                                                    value="${certificateTemplateInst?.paragraph5}"
                                                    id="paragraph5" class="col-sm-8"></g:textArea></div>
                </div>

                <div class="col-sm-12 form-group well well-sm">
                    <div class="col-sm-4">Signature</div>

                    <div class="col-sm-8"><input type="text" name="signature" required
                                                 value="${certificateTemplateInst?.signatureName}"
                                                 id="signature" class="col-sm-5"></div>
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