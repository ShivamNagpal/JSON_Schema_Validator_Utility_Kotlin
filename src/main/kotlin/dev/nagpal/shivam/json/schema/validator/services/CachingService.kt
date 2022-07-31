package dev.nagpal.shivam.json.schema.validator.services

import dev.nagpal.shivam.json.schema.validator.cache.CacheProperties
import dev.nagpal.shivam.json.schema.validator.cache.CacheStore
import dev.nagpal.shivam.json.schema.validator.cache.LocalCacheStore
import dev.nagpal.shivam.json.schema.validator.loaders.SchemaLoader
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaIngestionService
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaValidator

internal class CachingService internal constructor(
    private val schemaLoader: SchemaLoader,
    cacheProperties: CacheProperties,
    private val schemaIngestionService: SchemaIngestionService,
) {
    private val cacheStores: MutableList<CacheStore>
    private val enableLocalCache: Boolean
    private lateinit var localCacheStore: LocalCacheStore

    init {
        this.enableLocalCache = cacheProperties.enableLocalCache
        this.cacheStores = cacheProperties.cacheStores
        if (cacheProperties.enableLocalCache) {
            this.localCacheStore = LocalCacheStore()
        }
    }


    fun fetchSchema(id: String): SchemaValidator {
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
}
