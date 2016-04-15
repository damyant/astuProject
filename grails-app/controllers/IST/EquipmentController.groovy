package IST

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

class EquipmentController {
    def springSecurityService
    def equipmentService

    def index() {}

    def createEquipment(){
        /*action for interface to add an equipment*/
        if (springSecurityService.currentUser) {
        if(params.id){
            def equipmentObj=Equipment.get(params.id)
            [equipmentObj: equipmentObj]
        }
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def listOfEquipment(){
        /*Action for showing list of equipments*/
        if (springSecurityService.currentUser) {
            def equipmentObjList=Equipment.list()
             [equipmentObjList:equipmentObjList]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
    def saveEquipment(){
        /*Action for saving an equipment*/
        if (springSecurityService.currentUser) {
        def documentImage = request.getFile("documentImage")
        def equip = equipmentService.saveEquipmentInformation(params,documentImage)
        if((equip)&&(params.id))
        {
            redirect(action: "createEquipment")
            flash.message="Equipment Updated Successfully"
        }
       else if(equip){
             redirect(action: "createEquipment")
            flash.message="Equipment Added Successfully"
        }

        else{
            redirect(action: "createEquipment")
            flash.message="Sorry! Equipment in not added"
        }
        } else {
            redirect(controller: "login", action: "auth")
        }


    }
    def viewEquipmentDetail(){
        /*Action for showing equipment detail*/
        if (springSecurityService.currentUser) {
        def equipmentObj=Equipment.get(params.id)
        [equipmentObj: equipmentObj]
        } else {
            redirect(controller: "login", action: "auth")
        }
    }

    def deleteEquipment(){
        /*Action for deleting equipment*/
        if (springSecurityService.currentUser) {
        def returnMap=[:]
        def equipmentObj=Equipment.get(params.equipmentId)
        equipmentObj.delete(failOnError:true,flush: true)
        if(Equipment.exists(params.equipmentId)){
            returnMap.status=false
        }else{
            returnMap.status=true
        }
        render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }

    }

    def showImage(){
        /*Action for showing attached document of equipment*/
        if (springSecurityService.currentUser) {
        def id= Integer.parseInt(params.id)
        def equipmentObj = Equipment.get(id)
        byte[] image = equipmentObj.documentImage
        response.setContentType(params.mime)
        response.outputStream << image
       }
      else {
        redirect(controller: "login", action: "auth")
       }
}
    def validateCode() {
        /*Action for validating equipment code
        * (To check whether same code is already exist or not )*/
        if (springSecurityService.currentUser) {
            def returnMap = [:]
            def temp = 0
            def equip = Equipment.findByEquipmentId(Integer.parseInt(params.code))
            if (equip) {
                temp = 1
            }
            returnMap.temp = temp
            render returnMap as JSON
        } else {
            redirect(controller: "login", action: "auth")
        }
    }
}