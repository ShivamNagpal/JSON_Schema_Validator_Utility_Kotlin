package dev.nagpal.shivam.json.schema.validator.vendor.impl.networknt

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.networknt.schema.JsonSchema
import com.networknt.schema.ValidationMessage
import dev.nagpal.shivam.json.schema.validator.models.ValidationConstraintViolation
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaValidator

class NetworkNTSchemaValidator(private val jsonSchema: JsonSchema) : SchemaValidator {
    private val objectMapper: ObjectMapper = ObjectMapper()

    override fun validate(content: String): Set<ValidationConstraintViolation> {
        val readTree: JsonNode = objectMapper.readTree(content)
        val validationMessages: MutableSet<ValidationMessage> = jsonSchema.validate(readTree)
        return validationMessages.map { ValidationConstraintViolation() }
            .toSet() // TODO: Transform ValidationConstraintViolation
    }
}