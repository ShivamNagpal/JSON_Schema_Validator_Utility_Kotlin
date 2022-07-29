package dev.nagpal.shivam.json.schema.validator.loaders

import java.util.*

abstract class SchemaLoader {

    abstract fun fetchSchemaById(id: String): Optional<String>
    fun loads(id: String): String {
        val schemaOptional = fetchSchemaById(id)
        if (schemaOptional.isEmpty) {
            TODO("Throw Validation Exception")
        }
        return schemaOptional.get()
    }
}