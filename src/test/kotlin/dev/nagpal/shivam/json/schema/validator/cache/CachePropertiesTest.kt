package dev.nagpal.shivam.json.schema.validator.cache

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class CachePropertiesTest {
    @Test
    fun testBuilderCreation() {
        val cacheStore1: CacheStore = getDummyCacheStore()
        val cacheStore2: CacheStore = getDummyCacheStore()
        val cachePropertiesBuilder = CacheProperties.builder()
            .enableLocalCache(false)
            .addCacheStore(cacheStore1)
            .addCacheStore(cacheStore2)
        val cacheProperties = cachePropertiesBuilder.build()
        Assertions.assertNotNull(cacheProperties)
        Assertions.assertEquals(false, cacheProperties.enableLocalCache)
        Assertions.assertEquals(2, cacheProperties.cacheStores.size)
        Assertions.assertEquals(cacheStore1, cacheProperties.cacheStores[0])
        Assertions.assertEquals(cacheStore2, cacheProperties.cacheStores[1])
    }

    @Test
    fun testBuilderDefaultEnableLocalCache() {
        val cacheProperties = CacheProperties.builder().build()
        Assertions.assertNotNull(cacheProperties)
        Assertions.assertEquals(true, cacheProperties.enableLocalCache)
    }

    @Test
    fun testBuilderDefaultCacheStore() {
        val cacheProperties = CacheProperties.builder().build()
        Assertions.assertNotNull(cacheProperties)
        Assertions.assertEquals(0, cacheProperties.cacheStores.size)
    }

    private fun getDummyCacheStore() = object : CacheStore {
        override fun get(key: String): String? {
            TODO("Not yet implemented")
        }

        override fun put(key: String, value: String) {
            TODO("Not yet implemented")
        }

        override fun delete(key: String) {
            TODO("Not yet implemented")
        }

        override fun deleteAll() {
            TODO("Not yet implemented")
        }
    }
}