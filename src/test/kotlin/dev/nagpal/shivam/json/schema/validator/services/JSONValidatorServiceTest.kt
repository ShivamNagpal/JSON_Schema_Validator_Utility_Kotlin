package dev.nagpal.shivam.json.schema.validator.services

import dev.nagpal.shivam.json.schema.validator.cache.CacheProperties
import dev.nagpal.shivam.json.schema.validator.loaders.impl.StringSchemaLoader
import dev.nagpal.shivam.json.schema.validator.vendor.impl.networknt.NetworkNTSchemaIngestionService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class JSONValidatorServiceTest {

    @Test
    fun testBuilderCreation() {
        val schemaLoader = StringSchemaLoader(mapOf())
        val cacheProperties = CacheProperties.builder().build()
        val schemaIngestionService = NetworkNTSchemaIngestionService()
        val jsonValidatorServiceBuilder = JsonValidatorService.builder(schemaLoader)
            .cacheProperties(cacheProperties)
            .schemaIngestionService(schemaIngestionService)
        val jsonValidatorService = jsonValidatorServiceBuilder.build()
        Assertions.assertNotNull(jsonValidatorService)
        Assertions.assertEquals(schemaLoader, jsonValidatorService.schemaLoader)
        Assertions.assertEquals(cacheProperties, jsonValidatorService.cacheProperties)
        Assertions.assertEquals(schemaIngestionService, jsonValidatorService.schemaIngestionService)
        Assertions.assertNotNull(jsonValidatorService.localCacheStore)
    }

    @Test
    fun testBuilderDefaultCacheProperties() {
        val jsonValidatorService = JsonValidatorService.builder(StringSchemaLoader(mapOf()))
            .build()
        Assertions.assertNotNull(jsonValidatorService)
        Assertions.assertNotNull(jsonValidatorService.cacheProperties)
    }

    @Test
    fun testBuilderDefaultSchemaIngestionService() {
        val jsonValidatorService = JsonValidatorService.builder(StringSchemaLoader(mapOf()))
            .build()
        Assertions.assertNotNull(jsonValidatorService)
        Assertions.assertNotNull(jsonValidatorService.schemaIngestionService)
    }

    @Test
    fun testLocalCacheNonInitializationOnDisable() {
        val jsonValidatorService = JsonValidatorService.builder(StringSchemaLoader(mapOf()))
            .cacheProperties(CacheProperties.builder().enableLocalCache(false).build())
            .build()
        Assertions.assertNotNull(jsonValidatorService)
        Assertions.assertThrowsExactly(UninitializedPropertyAccessException::class.java) {
            jsonValidatorService.localCacheStore
        }
    }
}
