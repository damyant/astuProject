package IST

import grails.converters.JSON

import java.text.SimpleDateFormat


class LibraryReportsController {
    def springSecurityService

    def index() {}

    def issuedBooks(){

    }

    def listOfCatalogs(){
        if (springSecurityService.currentUser) {
        def catalogTypeList = CatalogType.list()
        def catalogCatagoryList = CatalogCatagory.list()
        [catalogTypeList: catalogTypeList,catalogCatagoryList: catalogCatagoryList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def overdueBooks(){
    }

    def byType(){
        if (springSecurityService.currentUser) {
        redirect(action: 'listOfCatalogs', params: [by:'byType'])
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def byCategory(){
        if (springSecurityService.currentUser) {
        redirect(action: 'listOfCatalogs', params: [by:'byCategory'])
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def byIsbn(){
        if (springSecurityService.currentUser) {
        redirect(action: 'listOfCatalogs', params: [by:'byIsbn'])
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def byTitle(){
        if (springSecurityService.currentUser) {
        redirect(action: 'listOfCatalogs', params: [by:'byTitle'])
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def byAuthor(){
        if (springSecurityService.currentUser) {
        redirect(action: 'listOfCatalogs', params: [by:'byAuthor'])
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def getIssuedBooks={
        if (springSecurityService.currentUser) {
        def finalList=[]
        def bookIns=BookIssue.findAllByIssuingPersonId(params.id)
        if(bookIns){
            bookIns.eachWithIndex{itr, i ->
                def subList=[]
                subList<<itr.catalog.title
                subList<<itr.catalogType.catalogTypeName
                subList<<itr.catalog.isbn
                subList<<itr.issuedDate.getDateString()
                finalList<<subList
            }
        }
        else{

        }

        render finalList as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def getCatalogList={
        /*action for getting catalog list*/
        if (springSecurityService.currentUser) {
        def finalList=[]
        if(params.catalogIsbn){
            /*by ISBN*/
            def catalogObj=Catalog.findByIsbnIlike("%"+params.catalogIsbn+"%")
            if(catalogObj){
            def subList=[]
            subList<<catalogObj.title
            subList<<catalogObj.publisher
            subList<<catalogObj.isbn
            subList<<catalogObj.author
            finalList<<subList
            }
        }
        if(params.catalogTitle){
            /*By Title*/
            def catalogList=Catalog.findAllByTitleIlike("%"+params.catalogTitle+"%")
           if(catalogList){
                catalogList.each{
                def subList=[]
                subList<<it.title
                subList<<it.publisher
                subList<<it.isbn
                subList<<it.author
                finalList<<subList
                }
            }
        }
//        if(params.catalogCatagory && params.catalogType){
//            def catalogCategory=CatalogCatagory.get(params.catalogCatagory)
//            def catalogType=CatalogType.get(params.catalogType)
//            def catalogObj=Catalog.findAllByCatagoryAndType(catalogCategory,catalogType)
//            if(catalogObj){
//                catalogObj.each{
//                    def subList=[]
//                    subList<<it.title
//                    subList<<it.publisher
//                    subList<<it.isbn
//                    subList<<it.author
//                    finalList<<subList
//                }
//            }
//        }
        if(params.catalogAuthor){
            /*By author*/
            def catalogObj=Catalog.findAllByAuthorIlike("%"+params.catalogAuthor+"%")
            if(catalogObj){
                catalogObj.each{
                    def subList=[]
                    subList<<it.title
                    subList<<it.publisher
                    subList<<it.isbn
                    subList<<it.author
                    finalList<<subList
                }
            }
        }
        if(params.userName){
            def catalogObj=Catalog.findAllByAuthorIlike("%"+params.catalogAuthor+"%")
            if(catalogObj){
                catalogObj.each{
                    def subList=[]
                    subList<<it.title
                    subList<<it.publisher
                    subList<<it.isbn
                    subList<<it.author
                    finalList<<subList
                }
            }
        }
        render finalList as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def searchCatalogList={
        if (springSecurityService.currentUser) {
        def finalList=[]
        if(params.catalogIsbn){
            def catalogObj=Catalog.findAllByIsbnIlike('%'+params.catalogIsbn+'%')
            if(catalogObj){
                catalogObj.each {
                    def subList = []
                    subList << it.type.catalogTypeName
                    subList << it.department.name
                    subList << it.isbn
                    subList << it.title
                    subList << it.author
                    subList << it.publisher
                    subList << it.year
                    subList << it.quantity
                    subList << it.id
                    finalList << subList
                }
            }
        }
        if(params.catalogTitle){
            def catalogList=Catalog.findAllByTitleIlike("%"+params.catalogTitle+"%")
            if(catalogList){
                catalogList.each{
                    def subList=[]
                    subList<<it.type.catalogTypeName
                    subList << it.department.name
                    subList<<it.isbn
                    subList<<it.title
                    subList<<it.author
                    subList<<it.publisher
                    subList<<it.year
                    subList<<it.quantity
                    subList<<it.id
                    finalList<<subList
                }
            }
        }

        render finalList as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def getOverDueBooks={
        if (springSecurityService.currentUser) {

        def finalList=[]
        def bookObj=BookIssue.findAllByIssuingPersonId(params.id)
        Calendar c = Calendar.getInstance();
        bookObj.each{
        c.setTime(it.issuedDate);
        c.add(Calendar.DATE, 1);
            if(c.getTime()<new Date()){
                def subList=[]
                subList<<it.catalog.title
                subList<<it.catalogType.catalogTypeName
                subList<<it.catalog.isbn
                subList<<it.issuedDate.getDateString()
                finalList<<subList
            }
        }
        render finalList as JSON
    }
        else {
            redirect(controller: "login", action: "auth")
        }
    }
def booksIssuedByUser(){
    def bookIssueList=BookIssue.list()
    def useList=bookIssueList.user.unique()
    [useList:useList,bookIssueList:bookIssueList]
}
}
