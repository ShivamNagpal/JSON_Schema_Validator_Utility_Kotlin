package dev.nagpal.shivam.json.schema.validator.loaders

import dev.nagpal.shivam.json.schema.validator.enums.ResponseDetails
import dev.nagpal.shivam.json.schema.validator.exceptions.ValidationException
import java.util.*

abstract class SchemaLoader {

    abstract fun fetchSchemaById(id: String): Optional<String>
    fun loads(id: String): String {
        val schemaOptional: Optional<String> = fetchSchemaById(id)
        if (schemaOptional.isPresent) {
            return schemaOptional.get()
        }
        throw ValidationException(ResponseDetails.COULD_NOT_LOAD_SCHEMA, id)
    }
}