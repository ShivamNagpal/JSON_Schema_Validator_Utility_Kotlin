package dev.nagpal.shivam.json.schema.validator.loaders

import dev.nagpal.shivam.json.schema.validator.enums.ResponseDetails
import dev.nagpal.shivam.json.schema.validator.exceptions.ValidationException
import java.util.*

abstract class SchemaLoader {

    abstract fun fetchSchemaById(id: String): Optional<String>
    fun loads(id: String): String {
        try {
            val schemaOptional: Optional<String> = fetchSchemaById(id)
            if (schemaOptional.isPresent) {
                return schemaOptional.get()
            }
        } catch (e: ValidationException) {
            throw e
        } catch (_: Exception) {
            // Common Exception is thrown below
        }
        throw ValidationException(ResponseDetails.COULD_NOT_LOAD_SCHEMA, id)
    }
}