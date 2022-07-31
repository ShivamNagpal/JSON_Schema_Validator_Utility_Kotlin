package dev.nagpal.shivam.json.schema.validator.cache

class CacheProperties private constructor(
    val enableLocalCache: Boolean,
    val cacheStores: List<CacheStore>
) {
    class CachePropertiesBuilder internal constructor() {
        private var enableLocalCache: Boolean = true
        private val cacheStores = mutableListOf<CacheStore>()

        fun enableLocalCache(enable: Boolean): CachePropertiesBuilder {
            this.enableLocalCache = enable
            return this
        }

        fun addCacheStore(cacheStore: CacheStore): CachePropertiesBuilder {
            this.cacheStores.add(cacheStore)
            return this
        }

        fun build(): CacheProperties {
            return CacheProperties(this.enableLocalCache, this.cacheStores)
        }
    }

    companion object {
        @JvmStatic
        fun builder(): CachePropertiesBuilder {
            return CachePropertiesBuilder()
        }
    }
}