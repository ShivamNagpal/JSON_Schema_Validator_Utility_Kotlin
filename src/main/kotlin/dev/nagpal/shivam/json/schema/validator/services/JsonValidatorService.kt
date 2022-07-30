package dev.nagpal.shivam.json.schema.validator.services

import dev.nagpal.shivam.json.schema.validator.enums.ResponseMessage
import dev.nagpal.shivam.json.schema.validator.exceptions.ValidationException
import dev.nagpal.shivam.json.schema.validator.loaders.SchemaLoader
import dev.nagpal.shivam.json.schema.validator.models.ValidationConstraintViolation
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaIngestionService
import dev.nagpal.shivam.json.schema.validator.vendor.impl.networknt.NetworkNTSchemaIngestionService

class JsonValidatorService private constructor(
    schemaLoader: SchemaLoader,
    schemaIngestionService: SchemaIngestionService = NetworkNTSchemaIngestionService()
) {
    private val cachingService = CachingService(schemaLoader, schemaIngestionService)


    fun validate(id: String, content: String) {
        val schemaValidator = cachingService.fetchSchema(id)
        val constraintViolations: Set<ValidationConstraintViolation> = schemaValidator.validate(content)
        if (constraintViolations.isNotEmpty()) {
            throw ValidationException(ResponseMessage.CONTENT_CONSTRAINT_VIOLATION, constraintViolations)
        }
    }

    class JsonValidatorServiceBuilder(schemaLoader: SchemaLoader) {
        private val jsonValidatorService: JsonValidatorService = JsonValidatorService(schemaLoader)

        fun enableLocalCache(enable: Boolean): JsonValidatorServiceBuilder {
            this.jsonValidatorService.cachingService.enableLocalCache = enable
            return this
        }

        fun build(): JsonValidatorService {
            return jsonValidatorService
        }
    }


    companion object {
        @JvmStatic
        fun builder(schemaLoader: SchemaLoader): JsonValidatorServiceBuilder {
            return JsonValidatorServiceBuilder(schemaLoader)
        }


    }
}