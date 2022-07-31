package dev.nagpal.shivam.json.schema.validator.exceptions

import dev.nagpal.shivam.json.schema.validator.enums.ResponseDetails
import dev.nagpal.shivam.json.schema.validator.models.ValidationConstraintViolation

class ValidationException constructor(
    responseDetails: ResponseDetails,
    val validationConstraintViolations: List<ValidationConstraintViolation>?,
    vararg variables: String,
) : RuntimeException(responseDetails.getMessage(*variables)) {
    val messageCode: String = responseDetails.messageCode

    init {
        println()
    }

    constructor(
        responseDetails: ResponseDetails,
        vararg variables: String,
    ) : this(responseDetails, null, *variables)
}