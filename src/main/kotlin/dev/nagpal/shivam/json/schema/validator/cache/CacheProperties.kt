package dev.nagpal.shivam.json.schema.validator.cache

class CacheProperties private constructor() {
    var enableLocalCache: Boolean = true
        private set
    private val cacheStores = mutableListOf<CacheStore>()

    fun getCacheStores(): List<CacheStore> {
        return cacheStores
    }

    class CachePropertiesBuilder internal constructor() {
        private val cacheProperties: CacheProperties = CacheProperties()

        fun enableLocalCache(enable: Boolean): CachePropertiesBuilder {
            cacheProperties.enableLocalCache = enable
            return this
        }

        fun addCacheStore(cacheStore: CacheStore): CachePropertiesBuilder {
            cacheProperties.cacheStores.add(cacheStore)
            return this
        }

        fun build(): CacheProperties {
            return cacheProperties
        }
    }

    companion object {
        @JvmStatic
        fun builder(): CachePropertiesBuilder {
            return CachePropertiesBuilder()
        }
    }
}