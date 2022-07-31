package dev.nagpal.shivam.json.schema.validator.cache

class CacheProperties private constructor() {
    var enableLocalCache: Boolean = true
        private set
    val cacheStores = mutableListOf<CacheStore>()

    class CachePropertiesBuilder internal constructor() {
        private val cacheProperties: CacheProperties = CacheProperties()

        fun enableCache(enable: Boolean): CachePropertiesBuilder {
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
        fun builder(): CachePropertiesBuilder {
            return CachePropertiesBuilder()
        }
    }
}