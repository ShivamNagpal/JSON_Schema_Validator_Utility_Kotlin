package dev.nagpal.shivam.json.schema.validator.cache

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaValidator

open class LocalCacheStore(cacheProperties: CacheProperties) : CacheStoreGeneric<String, SchemaValidator> {
    private val cache: Cache<String, SchemaValidator>

    init {
        var cacheBuilder: CacheBuilder<Any, Any> = CacheBuilder.newBuilder()
        cacheProperties.expireAfterWrite?.let {
            cacheBuilder = cacheBuilder.expireAfterWrite(it)
        }
        cacheProperties.expireAfterAccess?.let {
            cacheBuilder = cacheBuilder.expireAfterAccess(it)
        }
        cacheProperties.concurrencyLevel?.let {
            cacheBuilder = cacheBuilder.concurrencyLevel(it)
        }
        this.cache = cacheBuilder.build()
    }

    override fun get(key: String): SchemaValidator? {
        return cache.getIfPresent(key)
    }

    override fun put(key: String, value: SchemaValidator) {
        return cache.put(key, value)
    }

    override fun delete(key: String) {
        cache.invalidate(key)
    }

    override fun deleteAll() {
        cache.invalidateAll()
    }
}