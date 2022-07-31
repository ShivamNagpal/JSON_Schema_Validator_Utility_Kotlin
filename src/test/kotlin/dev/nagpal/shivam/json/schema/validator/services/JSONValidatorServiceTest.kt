package dev.nagpal.shivam.json.schema.validator.services

import dev.nagpal.shivam.json.schema.validator.cache.CacheProperties
import dev.nagpal.shivam.json.schema.validator.cache.LocalCacheStore
import dev.nagpal.shivam.json.schema.validator.enums.ResponseDetails
import dev.nagpal.shivam.json.schema.validator.exceptions.ValidationException
import dev.nagpal.shivam.json.schema.validator.loaders.impl.ResourceSchemaLoader
import dev.nagpal.shivam.json.schema.validator.loaders.impl.StringSchemaLoader
import dev.nagpal.shivam.json.schema.validator.test.helper.TestDataHelper
import dev.nagpal.shivam.json.schema.validator.vendor.impl.networknt.NetworkNTSchemaIngestionService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class JSONValidatorServiceTest {
    private lateinit var localCacheStore: LocalCacheStore
    private lateinit var stringSchemaLoader: StringSchemaLoader

    @BeforeEach
    fun beforeEach() {
        localCacheStore = Mockito.mock(LocalCacheStore::class.java)
        stringSchemaLoader = Mockito.mock(StringSchemaLoader::class.java)
    }

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

    @Test
    fun testValidateSchemaWithoutCacheValidContent() {
        val schemaLoader = ResourceSchemaLoader(pathPrefix = "schema", fileNameSuffix = ".json")
        val jsonValidatorService = JsonValidatorService
            .builder(schemaLoader)
            .cacheProperties(CacheProperties.builder().enableLocalCache(false).build())
            .build()
        val content = """{"name":"temp","address":"temp"}"""
        jsonValidatorService.validate("test-schema", content)
    }

    @Test
    fun testValidateSchemaWithoutCacheInvalidContent() {
        val schemaLoader = ResourceSchemaLoader(pathPrefix = "schema", fileNameSuffix = ".json")
        val jsonValidatorService = JsonValidatorService
            .builder(schemaLoader)
            .cacheProperties(CacheProperties.builder().enableLocalCache(false).build())
            .build()
        val content = """{"address":""}"""
        val validationException = Assertions.assertThrowsExactly(ValidationException::class.java) {
            jsonValidatorService.validate("test-schema", content)
        }
        val responseDetails = ResponseDetails.CONTENT_CONSTRAINT_VIOLATION
        Assertions.assertEquals(responseDetails.messageCode, validationException.messageCode)
        Assertions.assertEquals(responseDetails.getMessage(), validationException.message)
        Assertions.assertEquals(2, validationException.validationConstraintViolations?.size)
    }

    @Test
    fun testValidateLocalCacheNotCalledWhenDisabled() {
        val schemaLoader = ResourceSchemaLoader(pathPrefix = "schema", fileNameSuffix = ".json")
        val jsonValidatorService = JsonValidatorService
            .builder(schemaLoader)
            .cacheProperties(CacheProperties.builder().enableLocalCache(false).build())
            .build()
        jsonValidatorService.localCacheStore = localCacheStore
        val content = """{"name":"temp","address":"temp"}"""
        jsonValidatorService.validate("test-schema", content)
        Mockito.verify(localCacheStore, Mockito.times(0)).get(Mockito.anyString())
    }

    @Test
    fun testValidateValueUsedFromLocalCacheIfPresent() {
        val jsonValidatorService = JsonValidatorService
            .builder(stringSchemaLoader)
            .cacheProperties(CacheProperties.builder().build())
            .build()

        val schemaValidator = TestDataHelper.getSchemaValidator()
        val key = "id1"
        Mockito.`when`(localCacheStore.get(key)).thenReturn(schemaValidator)
        jsonValidatorService.localCacheStore = localCacheStore
        val content = """{"name":"temp","address":"temp"}"""
        jsonValidatorService.validate(key, content)

        Mockito.verify(localCacheStore, Mockito.times(1)).get(Mockito.anyString())
//        Mockito.verify(stringSchemaLoader, Mockito.times(0)).loads(Mockito.anyString())
    }
}
