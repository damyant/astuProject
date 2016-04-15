/**
 * Created by chandan on 6/10/14.
 */


$(document).ready(function(){

    $('#allBookList').bind('change',function () {

        $("#quantity option").removeAttr("selected");
      var index= $("#allBookList option:selected").index();
//
        $('#quantity > option').eq(index).attr('selected','selected')

    });

    $("#test").click(function(){
        alert("hi")
    })


})



function getSubjects(){
    var studentRollNo=$('#issuingPersonId').val()
    var catalogType=$("#catalogType").val()
    var catalogDepartment=$("#catalogDepartment").val()
    if($("#catalogType").val()!='' && $("#catalogDepartment").val()!=''){
        $.ajax({
            type: "post",
            url: url('admin', 'getBooksName', ''),
            data: {studentRollNo:studentRollNo,catalogType:catalogType,catalogDepartment:catalogDepartment},
            success: function (data) {
                if(data.bookList.length>0) {

                    $('#allBookList option').remove()
                    $('#quantity').empty()
                    for (var i = 0; i < data.bookList.length; i++) {
                        if(data.bookList[i].availableCatalog<1){
                            $("#allBookList").append('<option disabled value="' + data.bookList[i].id + '">' + data.bookList[i].title +'</option>')
                        }
                        else{
                            $("#allBookList").append('<option value="' + data.bookList[i].id + '">' + data.bookList[i].title +'</option>')
                        }
                        $("#quantity").append('<option>' + data.bookList[i].availableCatalog +'</option>')
                    }

                    $('#selectedBookList option').remove()
                    for (var i = 0; i < data.bookIssuedList.length; i++) {
                            $("#selectedBookList").append('<option value="' + data.bookIssuedList[i].id + '">' + data.bookIssuedList[i].title +'</option>')
                    }
                    $('#issuedButton').attr('disabled',false)
                }else{
                    $('#allBookList option').remove()
                    $('#selectedBookList option').remove()
                    $('#issuedButton').attr('disabled',true)
                }
            }
        })

    }
}

function addToList() {
    var selectedValues = [];
    var nonSelected = [];
    $('#issuedButton').attr('disabled',false)
    var inList2;
    $('#allBookList :selected').each(function (l, list1Selected) {
        selectedValues[l] = $(list1Selected).val();
        inList2 = false;

        $('#selectedBookList option').each(function (m, list2Selected) {
            nonSelected[m] = $(list2Selected).val();
            if (selectedValues[l] == nonSelected[m]) {
                inList2 = true;
            }
        });

        if (inList2 != true) {
            $('#selectedBookList').append("<option value='" + selectedValues[l] + "'>" + $(list1Selected).text() + "</option>");

            var text1 = $(list1Selected).val()

            $('#allBookList option:selected').each(function (n, allsubSelected) {
                var text3 = $(allsubSelected).val()
                $('#allBookList option').filter(function () {
                    return $(this).val() == text3;
                }).attr('selected', false);
            });
        }

    });
    validateLength();
}

function removeFromList() {
    $('#selectedBookList option:selected').each(function () {
        $(this).remove();
        $('#issuedButton').attr('disabled',false)
        $('#selectedBookList option:not(selected)').each(function (k, semSelected) {
            var text2 = $(semSelected).val()

            $('#allBookList option:selected').each(function (n, allsubSelected) {
                var text3 = $(allsubSelected).val()
                $('#allBookList option').filter(function () {
                    return $(this).val() == text3;
                }).attr('selected', false);
            });
        });
    });
    validateLength();
}
function validateLength() {
    var validate;
    var length = document.getElementById('selectedBookList').options.length;
    if (length == 0) {
        $('#error-select-').html("<label style='margin-left: 4px; color: #cd0a0a; '>Please choose course for semesters</label>");
        validate = false;
    } else {
        $('#error-select-').html("");
        validate = true;
    }

    return validate;
}

function saveData(){
    validateLibrary()
    console.log('here')
    var result=$('#bookIssueForm').valid()
    var count=0;
    if (result) {
        console.log('here')
        var bookList = [];
        $('#selectedBookList option').each(function () {
            bookList.push($(this).val() || '');
            ++count;
        });
        var issueBooks=parseInt($("#issuedBooks").val())
        issueBooks=issueBooks+count
        if( issueBooks>parseInt($("#maxBooks").val())){
         alert("Books allocation limit exceeded")
        }
        else{
            console.log('this is the list '+ bookList)
                $.ajax({
            type: "post",
            url: url('admin', 'saveBookIssue', ''),
            data: $("#bookIssueForm").serialize() + "&bookList=" + bookList,
            success: function (data) {
                if (data.flag == "true") {
                    alertPopup("Successfully Completed")
//                    location.reload();
                }
                else if (data.flag == "blank") {
                    alertPopup("Please Assign Book before submit")
                }
                else if (data.flag == "allReturn") {
                    alertPopup("Book Returned Successfully")
                }
                else {
                    $('#allBookList option').remove()
                }
            }
            })
          }
    }

}

function showIssuedBooks(){
    $.ajax({
        type: "post",
        url: url('libraryReports', 'getIssuedBooks', ''),
        data:"id="+$("#textBoxId").val(),
        success: function (data) {
            if(data.length>0) {
                var count=1;
                $("#bookList tr").remove()
                $('#errorMsg').text(" ")
                $("#bookList").append("<tr><th>S.No</th><th>Book Name</th><th>Category</th><th>ISBN</th><th>Issuing Date</th></tr>")
                for(var i=0;i<data.length;i++){
                    $("#bookList").append('<tr><td>'+count+'</td><td>'+data[i][0]+'</td><td>'+data[i][1]+'</td><td>'+data[i][2]+'</td><td>'+data[i][3]+'</td></tr>')
                ++count
                }

            }else{
                $("#bookList tr").remove()
                $('#errorMsg').text("No Record Found")
            }
        }
    })

}

function showCatalogList(){

    $.ajax({
        type: "post",
        url: url('libraryReports', 'getCatalogList', ''),
        data:$("#catalogForm").serialize(),
        success: function (data) {
            if(data.length>0) {
                var count=1;
                $("#bookList tr").remove()
                $('#errorMsg').text(" ")
                $("#bookList").append("<tr><th>S.No</th><th>Book Name</th><th>Publisher</th><th>ISBN</th><th>Author</th></tr>")
                for(var i=0;i<data.length;i++){
                    $("#bookList").append('<tr><td>'+count+'</td><td>'+data[i][0]+'</td><td>'+data[i][1]+'</td><td>'+data[i][2]+'</td><td>'+data[i][3]+'</td></tr>')
                    ++count
                }

            }else{
                $("#bookList tr").remove()
                $('#errorMsg').text("No Record Found")
            }
        }
    })

}

function getOverDueBooks(){
    $.ajax({
        type: "post",
        url: url('libraryReports', 'getOverDueBooks', ''),
        data:"id="+$("#textBoxId").val(),
        success: function (data) {
            if(data.length>0) {
                var count=1;
                $("#bookList tr").remove()
                $('#errorMsg').text(" ")
                $("#bookList").append("<tr><th>S.No</th><th>Book Name</th><th>Category</th><th>ISBN</th><th>Issuing Date</th></tr>")
                for(var i=0;i<data.length;i++){
                    $("#bookList").append('<tr><td>'+count+'</td><td>'+data[i][0]+'</td><td>'+data[i][1]+'</td><td>'+data[i][2]+'</td><td>'+data[i][3]+'</td></tr>')
                    ++count
                }

            }else{
                $("#bookList tr").remove()
                $('#errorMsg').text("No Record Found")
            }
        }
    })



}
function enableRoll(){


    $("#rollnum").prop( "disabled", false );
    if($("#rollnum").length > 0 && $("#rollnum").val() != ''){
        $("#catalogType").removeAttr( 'disabled' );
    }

}



function enableType(){

  if($("#rollnum").length > 0 && $("#rollnum").val() != ''){
        $("#catalogType").removeAttr( 'disabled' );
    }

}


function getTotalBooks(){
    $.ajax({
        type: "post",
        url: url('admin', 'getTotalBooks', ''),
        data:"type="+$("#type").val(),
        success: function (data) {
            if(data.value){
                $("#maxBooks").val(data.value)
                $('#issuingPersonId').attr('disabled',false)
            }
            else{
                $("#maxBooks").val('')
                $('#issuingPersonId').attr('disabled',true)
            }
        }
    })
}

function getIssuedBooks(){
    var issuingPersonId=$("#issuingPersonId").val()
    var type=$("#type").val()
    if(issuingPersonId!='' && type!=''){
        $.ajax({
            type: "post",
            url: url('admin', 'getIssuedBooks', ''),
            data:{issuingPersonId:issuingPersonId,type:type},
            success: function (data) {
                if(data.status){
                    $("#catalogDepartment").attr('disabled',false)
                    $("#catalogType").attr('disabled',false)
                    $("#issuedBooks").val(data.value)
                    $("#catalogDepartment").val(data.dept)
                }
                else{
                    $("#catalogType").attr('disabled',true)
                    $("#catalogDepartment").attr('disabled',true)
                    alertPopupWithoutTime("Please Enter Correct RollNumber or EmployeeId.")
                }
            }
        })
    }
}


function enableCategory(){

    if($("#catalogType").length > 0 && $("#catalogType").val() != ''){
 $("#catalogCategory").removeAttr('disabled');
    }
}


function enableElements(){

    if($("#catalogCategory").length > 0 && $("#catalogCategory").val() != ''){

        $("#allBookList").removeAttr('disabled');
        $("#quantity").removeAttr('disabled');
        $("#addButton").prop( "disabled", false );
        $("#removeButton").prop( "disabled", false );
        $("#selectedBookList").removeAttr('disabled');
        $("#button1").prop( "disabled", false );

    }
}
function searchCatalog(){
    var searchText=$('#search').val()
    var searchType=$('input[name=searchBy]:checked').val()
    if(searchType!='' && searchText!='') {
        if (searchType == 'byIsbn') {
            $.ajax({
                type: "post",
                url: url('libraryReports', 'searchCatalogList', ''),
                data: {catalogIsbn: searchText},
                success: function (data) {
                    if (data.length > 0) {
                        var count = 1;
                        $("#editCatalog tbody").empty()
                        $('#errorMsg').text(" ")
                        for (var i = 0; i < data.length; i++) {
                            $("#editCatalog tbody").append('<tr><td>' + data[i][0] + '</td><td>' + data[i][1] + '</td><td>' + data[i][2] + '</td><td>' + data[i][3] + '</td><td>' + data[i][4] + '</td><td>' + data[i][5] + '</td><td>' + data[i][6] + '</td><td>' + data[i][7] + '</td><td><a href="#" onclick="catalogEdit(' + data[i][8] + ')">Edit</a> </td><td><a href="#" onclick="catalogDelete(' + data[i][8] + ')">Delete</a> </td></tr>')
                        }

                    } else {
                        $("#bookList tr").remove()
                        $('#errorMsg').text("No Record Found")
                    }
                }
            })
        }
        else if (searchType == 'byTitle') {
            $.ajax({
                type: "post",
                url: url('libraryReports', 'getCatalogList', ''),
                data: {catalogTitle: searchText},
                success: function (data) {
                    if (data.length > 0) {
                        var count = 1;
                        $("#editCatalog tbody").empty()
                        $('#errorMsg').text(" ")
                        for (var i = 0; i < data.length; i++) {
                            $("#editCatalog tbody").append('<tr><td>' + data[i][0] + '</td><td>' + data[i][1] + '</td><td>' + data[i][2] + '</td><td>' + data[i][3] + '</td><td>' + data[i][4] + '</td><td>' + data[i][5] + '</td><td>' + data[i][6] + '</td><td>' + data[i][7] + '</td><td><a href="#" onclick="catalogEdit(' + data[i][8] + ')">Edit</a> </td><td><a href="#" onclick="catalogDelete(' + data[i][8] + ')">Delete</a> </td></tr>')
                        }

                    } else {
                        $("#bookList tr").remove()
                        $('#errorMsg').text("No Record Found")
                    }
                }
            })
        }
    }
}
function catalogEdit(catId) {
    window.open('/UniversityProject/admin/addCatalog?catalogInstId='+catId, '_self', false)
}
function catalogDelete(catId) {
    window.open('/UniversityProject/admin/delCatalog?catalogInstId='+catId, '_self', false)
}