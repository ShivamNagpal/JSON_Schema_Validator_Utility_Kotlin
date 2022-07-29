package dev.nagpal.shivam.json.schema.validator.exceptions

import dev.nagpal.shivam.json.schema.validator.enums.ResponseMessage
import dev.nagpal.shivam.json.schema.validator.models.ValidationConstraintViolation

class ValidationException(
    responseMessage: ResponseMessage,
    val validationConstraintViolations: Set<ValidationConstraintViolation>
) : RuntimeException(responseMessage.message) {
    val messageCode: String = responseMessage.messageCode

}