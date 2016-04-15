package IST

import grails.transaction.Transactional

import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class EquipmentService {

    def serviceMethod() {

    }

    def saveEquipmentInformation(params, documentImage) {
        /*Method for saving equipment details*/
        if (params.equId) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy")
            def equipmentObj = Equipment.findById(Long.parseLong(params.equId))
            equipmentObj.equipmentId = Integer.parseInt(params.equipmentId)
            equipmentObj.equipmentName = params.equipmentName
            equipmentObj.equipmentType = params.equipmentType
            equipmentObj.labInventoryId = Integer.parseInt(params.labInventoryId)
            equipmentObj.dateOfPurchase = df.parse(params.dateOfPurchase)
            equipmentObj.issuedDate = df.parse(params.issuedDate)
            equipmentObj.manufacturer = params.manufacturer
            equipmentObj.issuedTo = params.issuedTo
            equipmentObj.vendorName = params.vendorName
            equipmentObj.quantity = params.quantity
            equipmentObj.warranty = params.warranty
            equipmentObj.documentImage = documentImage.bytes
            equipmentObj.save(failOnError: true)
        }
        else{
        def equipmentObj = new Equipment(params)
        equipmentObj.documentImage = documentImage.bytes
            equipmentObj.save(failOnError: true)
        }

      }

}
