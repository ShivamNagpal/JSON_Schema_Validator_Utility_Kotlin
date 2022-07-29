package dev.nagpal.shivam.json.schema.validator.vendor

import dev.nagpal.shivam.json.schema.validator.models.ValidationConstraintViolation

interface SchemaValidator {
    fun validate(content: String): Set<ValidationConstraintViolation>
}