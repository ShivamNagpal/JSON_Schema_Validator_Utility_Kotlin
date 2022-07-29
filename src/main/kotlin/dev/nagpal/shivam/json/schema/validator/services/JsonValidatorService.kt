package dev.nagpal.shivam.json.schema.validator.services

import com.fasterxml.jackson.databind.ObjectMapper
import dev.nagpal.shivam.json.schema.validator.cache.CachingHelper
import dev.nagpal.shivam.json.schema.validator.enums.ResponseMessage
import dev.nagpal.shivam.json.schema.validator.exceptions.ValidationException
import dev.nagpal.shivam.json.schema.validator.loaders.SchemaLoader
import dev.nagpal.shivam.json.schema.validator.models.ValidationConstraintViolation
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaIngestionService
import dev.nagpal.shivam.json.schema.validator.vendor.impl.networknt.NetworkNTSchemaIngestionService

class JsonValidatorService(
    schemaLoader: SchemaLoader,
    schemaIngestionService: SchemaIngestionService = NetworkNTSchemaIngestionService()
) {
    private val objectMapper = ObjectMapper()
    val cachingHelper = CachingHelper(schemaLoader, schemaIngestionService)


    fun validate(id: String, content: String) {
        val schemaValidator = cachingHelper.fetchSchema(id)
        val constraintViolations: Set<ValidationConstraintViolation> = schemaValidator.validate(content)
        if (constraintViolations.isNotEmpty()) {
            throw ValidationException(ResponseMessage.CONTENT_CONSTRAINT_VIOLATION, constraintViolations)
        }
    }

    class JsonValidatorServiceBuilder(schemaLoader: SchemaLoader) {
        private val jsonValidatorService: JsonValidatorService = JsonValidatorService(schemaLoader)

        fun enableLocalCache(enable: Boolean): JsonValidatorServiceBuilder {
            this.jsonValidatorService.cachingHelper.enableLocalCache = enable
            return this
        }

        fun build(): JsonValidatorService {
            return jsonValidatorService
        }
    }

    companion object {
        fun builder(schemaLoader: SchemaLoader): JsonValidatorServiceBuilder {
            return JsonValidatorServiceBuilder(schemaLoader)
        }
    }
}