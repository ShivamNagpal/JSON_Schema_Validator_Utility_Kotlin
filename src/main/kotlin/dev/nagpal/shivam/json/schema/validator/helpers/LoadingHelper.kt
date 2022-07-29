package dev.nagpal.shivam.json.schema.validator.helpers

import dev.nagpal.shivam.json.schema.validator.loaders.SchemaLoader
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaIngestionService
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaValidator

class LoadingHelper(
    private val schemaLoader: SchemaLoader,
    private val schemaIngestionService: SchemaIngestionService,
) {
    var enableLocalCache = true


    fun fetchSchema(id: String): SchemaValidator { // Create a custom schema object and return to hide the vendor implementation
        val schemaString: String = schemaLoader.loads(id)
        return schemaIngestionService.ingestSchema(schemaString)

    }

}