package IST

import com.university.User

class BookIssue {
    /*Save details of issued books*/

    String issueTo
    Catalog catalog
    String issuingPersonId
//    CatalogCatagory catalogCategory
    Department department
    CatalogType catalogType
    Date issuedDate=new Date()
    User user
    static constraints = {
    }

    static mapping = {
         issueTo column: "IssueTo"
         catalog column: "Catalog"
         issuingPersonId column: "IssuingPersonId"
         user column: "user"
         catalogType column: "CatalogType"
         issuedDate column: "IssuedDate"

    }
}
