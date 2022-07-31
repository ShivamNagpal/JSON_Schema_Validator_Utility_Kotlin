package dev.nagpal.shivam.json.schema.validator.loaders.impl

import dev.nagpal.shivam.json.schema.validator.enums.ResponseDetails
import dev.nagpal.shivam.json.schema.validator.exceptions.ValidationException
import dev.nagpal.shivam.json.schema.validator.loaders.SchemaLoader
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ResourceSchemaLoaderTest {
    @Test
    fun fetchSchemaByIdPresentTest() {
        val schemaLoader: SchemaLoader = ResourceSchemaLoader(pathPrefix = "schema", fileNameSuffix = ".json")
        val loadedSchema = schemaLoader.loads("test-schema")
        Assertions.assertNotNull(loadedSchema)
    }

    @Test
    fun fetchSchemaByIdAbsentTest() {
        val schemaLoader: SchemaLoader = ResourceSchemaLoader()
        val id = "id2"
        val validationException = Assertions.assertThrowsExactly(ValidationException::class.java) {
            schemaLoader.loads(id)
        }
        val responseDetails = ResponseDetails.COULD_NOT_LOAD_RESOURCE_SCHEMA
        Assertions.assertEquals(responseDetails.messageCode, validationException.messageCode)
        Assertions.assertEquals(responseDetails.getMessage(id), validationException.message)
    }

}