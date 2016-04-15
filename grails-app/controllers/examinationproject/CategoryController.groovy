package examinationproject


import grails.converters.JSON
import grails.plugins.springsecurity.Secured

class CategoryController {
    def springSecurityService

    def index() { }
    @Secured(["ROLE_ADMIN"])
    def createNewCategory(){
        /*Action for creating new program type*/
        if(params.categoryId){
//            println("this is the name of the category "+ params.categoryName)
            def programType = ProgramType.findById(Integer.parseInt(params.categoryId))
            [programType: programType]
           }
    }
    def saveNewCategory(){
        /*Action for saving program type*/
        if(params.categoryId){
            /*for existing program type*/
//            println("this is the name of the category "+ params.categoryName)
            def programType = ProgramType.findById(Integer.parseInt(params.categoryId))
            programType.type= params.categoryName
            programType.code= params.programCode
            if(programType.save(flush: true, failOnError: true)) {
                flash.message = "Program Type is Updated Successfully"
                redirect(action: 'createNewCategory')
            }
            else{
                flash.message = "Program Type is not updated"
            }
        }
    else{
            /*for new program type*/
//            println("this is the name of the category "+ params.categoryName)
            def programType = new ProgramType()
            programType.type= params.categoryName
            programType.code= params.programCode
            if(programType.save(flush: true, failOnError: true)) {
                flash.message = "Program Type Created Successfully"
                redirect(action: 'createNewCategory')
            }
            else{
                flash.message = "Program Type is not created"
                redirect(action: 'createNewCategory')
            }
        }
    }
    @Secured(["ROLE_ADMIN"])
    def categoryList(){
        /*This action is for showing list of program type(provide option for edit and delete)*/
        def programTypeList = ProgramType.list()
        [programTypeList:programTypeList]
    }

    def deleteCategory(){
        /*Action for deleting program type*/
        def programType = ProgramType.findById(Integer.parseInt(params.categoryId))
        try{
            programType.delete(flush: true)
//            println('category deleted successfully')
            flash.message = "Program Type is Deleted Successfully"
            redirect(action: 'categoryList')
        }
        catch(Exception e){
//            println('category not deleted successfully' + e)
            flash.message = "Program Type is not Deleted"
            redirect(action: 'categoryList')
        }
    }

    def validateName(){
        /*validating programtype name(whether other program type exist with same name)*/
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def temp = 0
            def emp = ProgramType.findByType(params.code)
            if (emp) {
                temp = 1
            }
            returnMap.temp = temp
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    def validateCode(){
        /*validating programtype code(whether other program type exist with same code)*/
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def temp = 0
            def emp = ProgramType.findByCode(params.code)
            if (emp) {
                temp = 1
            }
            returnMap.temp = temp
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }

    }
}
