package dev.nagpal.shivam.json.schema.validator.loaders

import java.util.*

abstract class SchemaLoader {

    abstract fun fetchSchemaById(id: String): Optional<String>
    fun loads(id: String): String {
        val schemaOptional: Optional<String> = fetchSchemaById(id)
        if (schemaOptional.isPresent) {
            return schemaOptional.get()
        }
        TODO("Throw Validation Exception")
    }
}