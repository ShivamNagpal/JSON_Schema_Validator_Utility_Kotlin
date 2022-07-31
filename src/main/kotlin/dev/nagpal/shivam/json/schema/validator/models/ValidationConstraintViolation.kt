package dev.nagpal.shivam.json.schema.validator.models

class ValidationConstraintViolation(
    val message: String,
    val type: String?,
    val path: String?,
    val schemaPath: String?,
)
