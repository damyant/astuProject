package IST

class Equipment {
    /*Saves Equipment details*/
    String equipmentName
    int equipmentId
    int labInventoryId
    String equipmentType
    String manufacturer
    Date dateOfPurchase
    Date issuedDate
    String description
    String warranty
    String issuedTo
    String vendorName
    String quantity

    byte[] documentImage

    static constraints = {
        equipmentName(nullable:true)
        equipmentId(nullable:true,unique: true)
        labInventoryId(nullable:true)
        equipmentType(nullable:true)
        manufacturer(nullable:true)
        dateOfPurchase(nullable:true)
        description(nullable:true)
        warranty(nullable:true)
        documentImage(nullable:true)
        issuedDate(nullable:true)
        issuedTo(nullable:true)
        vendorName(nullable:true)
        quantity(nullable:true)
    }

    static mapping = {
        equipmentName column: 'EquipmentName'
        equipmentId column: 'EquipmentId'
        labInventoryId column: 'LabInventoryId'
        equipmentType column: 'EquipmentType'
        manufacturer column: 'Manufacturer'
        dateOfPurchase column: 'DateOfPurchase'
        description column: 'Description'
        warranty column: 'Warranty'
        documentImage column: 'DocumentImage' ,sqlType: "LONGBLOB"
        issuedDate column: 'IssuedDate'
        issuedTo column: 'IssuedTo'
        vendorName column: 'VendorName'
        quantity column: 'Quantity'
    }
}
