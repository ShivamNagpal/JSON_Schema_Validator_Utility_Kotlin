package dev.nagpal.shivam.json.schema.validator.loaders.impl

import dev.nagpal.shivam.json.schema.validator.loaders.SchemaLoader
import java.util.*

class StringSchemaLoader(private val schemaMap: Map<String, String>) : SchemaLoader() {
    override fun fetchSchemaById(id: String): Optional<String> {
        return Optional.ofNullable(schemaMap[id])
    }
}
