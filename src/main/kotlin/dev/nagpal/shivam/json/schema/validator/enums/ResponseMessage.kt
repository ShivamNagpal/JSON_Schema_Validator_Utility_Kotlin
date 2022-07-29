package dev.nagpal.shivam.json.schema.validator.enums

enum class ResponseMessage(messageCode: String, val message: String) {

    CONTENT_CONSTRAINT_VIOLATION("E-001", "Content violated the constraints defined in the Schema"),
    ;

    val messageCode: String = "JSVU-$messageCode"
}