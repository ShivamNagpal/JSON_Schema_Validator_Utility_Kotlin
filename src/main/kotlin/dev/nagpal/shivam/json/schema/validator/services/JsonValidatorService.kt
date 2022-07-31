package dev.nagpal.shivam.json.schema.validator.services

import dev.nagpal.shivam.json.schema.validator.cache.CacheProperties
import dev.nagpal.shivam.json.schema.validator.cache.CacheStore
import dev.nagpal.shivam.json.schema.validator.cache.LocalCacheStore
import dev.nagpal.shivam.json.schema.validator.enums.ResponseDetails
import dev.nagpal.shivam.json.schema.validator.exceptions.ValidationException
import dev.nagpal.shivam.json.schema.validator.loaders.SchemaLoader
import dev.nagpal.shivam.json.schema.validator.models.ValidationConstraintViolation
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaIngestionService
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaValidator
import dev.nagpal.shivam.json.schema.validator.vendor.impl.networknt.NetworkNTSchemaIngestionService

class JsonValidatorService private constructor(
    val schemaLoader: SchemaLoader,
    val cacheProperties: CacheProperties,
    val schemaIngestionService: SchemaIngestionService,
) {
    private val cacheStores: List<CacheStore> = cacheProperties.cacheStores
    private val enableLocalCache: Boolean = cacheProperties.enableLocalCache
    internal lateinit var localCacheStore: LocalCacheStore

    init {
        if (cacheProperties.enableLocalCache) {
            this.localCacheStore = LocalCacheStore()
        }
    }

    fun validate(id: String, content: String) {
        val schemaValidator = fetchSchema(id)
        val constraintViolations: List<ValidationConstraintViolation> = schemaValidator.validate(content)
        if (constraintViolations.isNotEmpty()) {
            throw ValidationException(ResponseDetails.CONTENT_CONSTRAINT_VIOLATION, constraintViolations)
        }
    }

    private fun fetchSchema(id: String): SchemaValidator {
        if (this.enableLocalCache) {
            val schemaValidator: SchemaValidator? = this.localCacheStore.get(id)
            if (schemaValidator != null) {
                return schemaValidator
            }
        }
        val cacheStoresToBeRefreshed = mutableListOf<CacheStore>()
        var schemaString: String? = null
        for (cacheStore in this.cacheStores) {
            schemaString = cacheStore.get(id)
            if (schemaString == null) {
                cacheStoresToBeRefreshed.add(0, cacheStore)
            } else {
                break
            }
        }
        if (schemaString == null) {
            schemaString = this.schemaLoader.loads(id)
        }
        val schemaValidator = this.schemaIngestionService.ingestSchema(schemaString)
        for (cacheStore in cacheStoresToBeRefreshed) {
            cacheStore.put(id, schemaString)
        }
        if (this.enableLocalCache) {
            this.localCacheStore.put(id, schemaValidator)
        }
        return schemaValidator
    }

    class JsonValidatorServiceBuilder internal constructor(private val schemaLoader: SchemaLoader) {
        private var cacheProperties: CacheProperties = CacheProperties.builder().build()
        private var schemaIngestionService: SchemaIngestionService = NetworkNTSchemaIngestionService()

        fun cacheProperties(cacheProperties: CacheProperties): JsonValidatorServiceBuilder {
            this.cacheProperties = cacheProperties
            return this
        }

        fun schemaIngestionService(schemaIngestionService: SchemaIngestionService): JsonValidatorServiceBuilder {
            this.schemaIngestionService = schemaIngestionService
            return this
        }

        fun build(): JsonValidatorService {
            return JsonValidatorService(schemaLoader, cacheProperties, schemaIngestionService)
        }
    }

    companion object {
        @JvmStatic
        fun builder(schemaLoader: SchemaLoader): JsonValidatorServiceBuilder {
            return JsonValidatorServiceBuilder(schemaLoader)
        }
    }
}
