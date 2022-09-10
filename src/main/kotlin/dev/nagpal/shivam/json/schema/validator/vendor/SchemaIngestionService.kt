package dev.nagpal.shivam.json.schema.validator.vendor

interface SchemaIngestionService {
    fun ingestSchema(schemaString: String): SchemaValidator
}