package dev.nagpal.shivam.json.schema.validator.services

import dev.nagpal.shivam.json.schema.validator.cache.CacheStore
import dev.nagpal.shivam.json.schema.validator.cache.LocalCacheStore
import dev.nagpal.shivam.json.schema.validator.loaders.SchemaLoader
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaIngestionService
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaValidator

class CachingHelper(
    private val schemaLoader: SchemaLoader,
    private val schemaIngestionService: SchemaIngestionService,
) {
    private val localCacheStore = LocalCacheStore()
    private val cacheStores = mutableListOf<CacheStore>()
    var enableLocalCache = true


    fun fetchSchema(id: String): SchemaValidator {
        if (enableLocalCache) {
            val schemaValidator: SchemaValidator? = localCacheStore.get(id)
            if (schemaValidator != null) {
                return schemaValidator
            }
        }
        val cacheStoresToBeRefreshed = mutableListOf<CacheStore>()
        var schemaString: String? = null
        for (cacheStore in cacheStores) {
            schemaString = cacheStore.get(id)
            if (schemaString == null) {
                cacheStoresToBeRefreshed.add(0, cacheStore)
            } else {
                break
            }
        }
        if (schemaString == null) {
            schemaString = schemaLoader.loads(id)
        }
        val schemaValidator = schemaIngestionService.ingestSchema(schemaString)
        for (cacheStore in cacheStoresToBeRefreshed) {
            cacheStore.put(id, schemaString)
        }
        if (enableLocalCache) {
            localCacheStore.put(id, schemaValidator)
        }
        return schemaValidator

    }

    fun addCacheStore(cacheStore: CacheStore) {
        cacheStores.add(cacheStore)
    }

}