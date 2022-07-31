package dev.nagpal.shivam.json.schema.validator.services

import dev.nagpal.shivam.json.schema.validator.cache.CacheProperties
import dev.nagpal.shivam.json.schema.validator.enums.ResponseMessage
import dev.nagpal.shivam.json.schema.validator.exceptions.ValidationException
import dev.nagpal.shivam.json.schema.validator.loaders.SchemaLoader
import dev.nagpal.shivam.json.schema.validator.models.ValidationConstraintViolation
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaIngestionService
import dev.nagpal.shivam.json.schema.validator.vendor.impl.networknt.NetworkNTSchemaIngestionService

class JsonValidatorService private constructor(
    val schemaLoader: SchemaLoader,
) {
    var cacheProperties: CacheProperties = CacheProperties.builder().build()
        private set
    var schemaIngestionService: SchemaIngestionService = NetworkNTSchemaIngestionService()
        private set
    lateinit var cachingService: CachingService
        private set

    private fun initializeCachingService() {
        this.cachingService = CachingService(schemaLoader, cacheProperties, schemaIngestionService)
    }

    fun validate(id: String, content: String) {
        val schemaValidator = cachingService.fetchSchema(id)
        val constraintViolations: Set<ValidationConstraintViolation> = schemaValidator.validate(content)
        if (constraintViolations.isNotEmpty()) {
            throw ValidationException(ResponseMessage.CONTENT_CONSTRAINT_VIOLATION, constraintViolations)
        }
    }

    class JsonValidatorServiceBuilder internal constructor(schemaLoader: SchemaLoader) {
        private val jsonValidatorService: JsonValidatorService = JsonValidatorService(schemaLoader)

        fun cacheProperties(cacheProperties: CacheProperties): JsonValidatorServiceBuilder {
            this.jsonValidatorService.cacheProperties = cacheProperties
            return this
        }

        fun schemaIngestionService(schemaIngestionService: SchemaIngestionService): JsonValidatorServiceBuilder {
            this.jsonValidatorService.schemaIngestionService = schemaIngestionService
            return this
        }

        fun build(): JsonValidatorService {
            this.jsonValidatorService.initializeCachingService()
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
