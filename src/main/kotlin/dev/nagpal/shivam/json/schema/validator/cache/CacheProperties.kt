package dev.nagpal.shivam.json.schema.validator.cache

import java.time.Duration

class CacheProperties private constructor(
    val enableLocalCache: Boolean,
    val cacheStores: List<CacheStore>,
    val expireAfterWrite: Duration?,
    val expireAfterAccess: Duration?,
    val concurrencyLevel: Int?
) {
    class CachePropertiesBuilder internal constructor() {
        private var enableLocalCache: Boolean = true
        private val cacheStores = mutableListOf<CacheStore>()
        private var expireAfterWrite: Duration? = null
        private var expireAfterAccess: Duration? = null
        private var concurrencyLevel: Int? = null

        fun enableLocalCache(enable: Boolean): CachePropertiesBuilder {
            this.enableLocalCache = enable
            return this
        }

        fun addCacheStore(cacheStore: CacheStore): CachePropertiesBuilder {
            this.cacheStores.add(cacheStore)
            return this
        }

        fun expireAfterWrite(duration: Duration): CachePropertiesBuilder {
            this.expireAfterWrite = duration
            return this
        }

        fun expireAfterAccess(duration: Duration): CachePropertiesBuilder {
            this.expireAfterAccess = duration
            return this
        }

        fun concurrencyLevel(concurrencyLevel: Int): CachePropertiesBuilder {
            this.concurrencyLevel = concurrencyLevel
            return this
        }

        fun build(): CacheProperties {
            return CacheProperties(
                this.enableLocalCache,
                this.cacheStores,
                this.expireAfterWrite,
                this.expireAfterWrite,
                this.concurrencyLevel,
            )
        }
    }

    companion object {
        @JvmStatic
        fun builder(): CachePropertiesBuilder {
            return CachePropertiesBuilder()
        }
    }
}