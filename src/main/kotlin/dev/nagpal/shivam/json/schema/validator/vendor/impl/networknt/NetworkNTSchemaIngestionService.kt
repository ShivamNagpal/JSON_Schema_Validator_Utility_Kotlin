package dev.nagpal.shivam.json.schema.validator.vendor.impl.networknt

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.networknt.schema.JsonSchema
import com.networknt.schema.JsonSchemaFactory
import com.networknt.schema.SpecVersionDetector
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaIngestionService
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaValidator

class NetworkNTSchemaIngestionService : SchemaIngestionService {
    private val objectMapper: ObjectMapper = ObjectMapper()

    override fun ingestSchema(schemaString: String): SchemaValidator {
        val jsonNode: JsonNode = objectMapper.readTree(schemaString)
        val jsonSchemaFactory: JsonSchemaFactory = JsonSchemaFactory.getInstance(SpecVersionDetector.detect(jsonNode))
        val schema: JsonSchema = jsonSchemaFactory.getSchema(jsonNode)
        return NetworkNTSchemaValidator(schema)
    }
}