package dev.nagpal.shivam.json.schema.validator.test.helper

import dev.nagpal.shivam.json.schema.validator.loaders.impl.ResourceSchemaLoader
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaValidator
import dev.nagpal.shivam.json.schema.validator.vendor.impl.networknt.NetworkNTSchemaIngestionService

object TestDataHelper {

    fun getSchemaValidator(): SchemaValidator {
        val resourceSchemaLoader = ResourceSchemaLoader(pathPrefix = "schema", ".json")
        val schema: String = resourceSchemaLoader.loads("test-schema")
        val networkNTSchemaIngestionService = NetworkNTSchemaIngestionService()
        return networkNTSchemaIngestionService.ingestSchema(schema)
    }
}