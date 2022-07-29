package dev.nagpal.shivam.json.schema.validator.cache

import dev.nagpal.shivam.json.schema.validator.vendor.SchemaValidator

class LocalCacheStore : CacheStoreGeneric<String, SchemaValidator> {
    private val map: Map<String, SchemaValidator> = mutableMapOf() // TODO: Replace with App Cache Vendor

    override fun get(key: String): SchemaValidator? {
        TODO("Not yet implemented")
    }

    override fun put(key: String, value: SchemaValidator) {
        TODO("Not yet implemented")
    }

    override fun delete(key: String) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }
}