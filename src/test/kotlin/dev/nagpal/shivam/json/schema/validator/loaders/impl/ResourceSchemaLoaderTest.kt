package dev.nagpal.shivam.json.schema.validator.loaders.impl

import dev.nagpal.shivam.json.schema.validator.enums.ResponseDetails
import dev.nagpal.shivam.json.schema.validator.exceptions.ValidationException
import dev.nagpal.shivam.json.schema.validator.loaders.SchemaLoader
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

private const val s1 = "schema"

internal class ResourceSchemaLoaderTest {
    @Test
    fun fetchSchemaByIdPresentTest() {
        val schemaLoader: SchemaLoader = ResourceSchemaLoader(pathPrefix = "schema", fileNameSuffix = ".json")
        val loadedSchema = schemaLoader.loads("test-schema")
        Assertions.assertNotNull(loadedSchema)
    }

    @Test
    fun fetchSchemaByIdAbsentTest1() {
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
    fun fetchSchemaByIdAbsentTest2() {
        val pathPrefix = "schema"
        val schemaLoader: SchemaLoader = ResourceSchemaLoader(pathPrefix = pathPrefix)
        val id = "id3"
        val validationException = Assertions.assertThrowsExactly(ValidationException::class.java) {
            schemaLoader.loads(id)
        }
        val responseDetails = ResponseDetails.COULD_NOT_LOAD_RESOURCE_SCHEMA
        Assertions.assertEquals(responseDetails.messageCode, validationException.messageCode)
        Assertions.assertEquals(
            responseDetails.getMessage("$pathPrefix${File.separator}$id"),
            validationException.message
        )
    }

    @Test
    fun fetchSchemaByIdAbsentTest3() {
        val fileNameSuffix = ".json"
        val schemaLoader: SchemaLoader = ResourceSchemaLoader(fileNameSuffix = fileNameSuffix)
        val id = "id2"
        val validationException = Assertions.assertThrowsExactly(ValidationException::class.java) {
            schemaLoader.loads(id)
        }
        val responseDetails = ResponseDetails.COULD_NOT_LOAD_RESOURCE_SCHEMA
        Assertions.assertEquals(responseDetails.messageCode, validationException.messageCode)
        Assertions.assertEquals(responseDetails.getMessage("$id$fileNameSuffix"), validationException.message)
    }

}