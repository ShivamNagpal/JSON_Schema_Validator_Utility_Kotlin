package dev.nagpal.shivam.json.schema.validator.cache

import dev.nagpal.shivam.json.schema.validator.test.helper.TestDataHelper
import dev.nagpal.shivam.json.schema.validator.vendor.SchemaValidator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Duration

internal class LocalCacheStoreTest {

    @Test
    fun testCachePersistence() {
        val cacheProperties = CacheProperties.builder().build()
        val localCacheStore = LocalCacheStore(cacheProperties)
        val schemaValidator: SchemaValidator = TestDataHelper.getSchemaValidator()
        val key = "id1"
        localCacheStore.put(key, schemaValidator)
        val cachedSchemaValidator: SchemaValidator? = localCacheStore.get(key)
        Assertions.assertNotNull(cachedSchemaValidator)
        Assertions.assertEquals(schemaValidator, cachedSchemaValidator)
    }

    @Test
    fun testCacheResponseWhenNotFound() {
        val cacheProperties = CacheProperties.builder().build()
        val localCacheStore = LocalCacheStore(cacheProperties)
        val cachedSchemaValidator: SchemaValidator? = localCacheStore.get("id1")
        Assertions.assertNull(cachedSchemaValidator)
    }

    @Test
    fun testCacheDeleteKey() {
        val cacheProperties = CacheProperties.builder().build()
        val localCacheStore = LocalCacheStore(cacheProperties)
        val key = "id1"
        localCacheStore.put(key, TestDataHelper.getSchemaValidator())
        localCacheStore.delete(key)
        val cachedSchemaValidator: SchemaValidator? = localCacheStore.get(key)
        Assertions.assertNull(cachedSchemaValidator)
    }

    @Test
    fun testCacheDeleteAllKeys() {
        val cacheProperties = CacheProperties.builder().build()
        val localCacheStore = LocalCacheStore(cacheProperties)
        val key1 = "id1"
        val key2 = "id2"
        localCacheStore.put(key1, TestDataHelper.getSchemaValidator())
        localCacheStore.put(key2, TestDataHelper.getSchemaValidator())
        localCacheStore.deleteAll()
        val cachedSchemaValidator1: SchemaValidator? = localCacheStore.get(key1)
        val cachedSchemaValidator2: SchemaValidator? = localCacheStore.get(key2)
        Assertions.assertNull(cachedSchemaValidator1)
        Assertions.assertNull(cachedSchemaValidator2)
    }

    @Test
    fun testCacheProperties() {
        val cacheProperties = CacheProperties.builder()
            .expireAfterWrite(Duration.ofSeconds(300))
            .expireAfterAccess(Duration.ofSeconds(300))
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .build()
        val localCacheStore = LocalCacheStore(cacheProperties)
    }
}