package dev.nagpal.shivam.json.schema.validator.loaders

import dev.nagpal.shivam.json.schema.validator.enums.ResponseDetails
import dev.nagpal.shivam.json.schema.validator.exceptions.ValidationException
import dev.nagpal.shivam.json.schema.validator.loaders.impl.ResourceSchemaLoader
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

internal class SchemaLoaderTest {
    @Test
    fun fetchSchemaExists() {
        val schemaLoader = object : SchemaLoader() {
            override fun fetchSchemaById(id: String): Optional<String> {
                return Optional.of("value1")
            }
        }
        val loadedSchema: String = schemaLoader.loads("id1")
        Assertions.assertEquals("value1", loadedSchema)
    }

    @Test
    fun fetchSchemaEmpty() {
        val schemaLoader = object : SchemaLoader() {
            override fun fetchSchemaById(id: String): Optional<String> {
                return Optional.empty()
            }
        }
        val id = "id1"
        val validationException = Assertions.assertThrowsExactly(ValidationException::class.java) {
            schemaLoader.loads(id)
        }
        val responseDetails = ResponseDetails.COULD_NOT_LOAD_SCHEMA
        Assertions.assertEquals(responseDetails.messageCode, validationException.messageCode)
        Assertions.assertEquals(responseDetails.getMessage(id), validationException.message)
    }

    @Test
    fun fetchSchemaThrowValidationException() {
        val schemaLoader: SchemaLoader = ResourceSchemaLoader()
        val id = "id2"
        val validationException = Assertions.assertThrowsExactly(ValidationException::class.java) {
            schemaLoader.loads(id)
        }
        val responseDetails = ResponseDetails.COULD_NOT_LOAD_RESOURCE_SCHEMA
        Assertions.assertEquals(responseDetails.messageCode, validationException.messageCode)
        Assertions.assertEquals(responseDetails.getMessage(id), validationException.message)
    }

    @Test
    fun fetchSchemaThrowCustomException() {
        val schemaLoader = object : SchemaLoader() {
            override fun fetchSchemaById(id: String): Optional<String> {
                throw RuntimeException("Something went wrong")
            }
        }
        val id = "id1"
        val validationException = Assertions.assertThrowsExactly(ValidationException::class.java) {
            schemaLoader.loads(id)
        }
        val responseDetails = ResponseDetails.COULD_NOT_LOAD_SCHEMA
        Assertions.assertEquals(responseDetails.messageCode, validationException.messageCode)
        Assertions.assertEquals(responseDetails.getMessage(id), validationException.message)
    }
}