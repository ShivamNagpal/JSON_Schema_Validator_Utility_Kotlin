package dev.nagpal.shivam.json.schema.validator.loaders.impl

import dev.nagpal.shivam.json.schema.validator.enums.ResponseDetails
import dev.nagpal.shivam.json.schema.validator.exceptions.ValidationException
import dev.nagpal.shivam.json.schema.validator.loaders.SchemaLoader
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

private const val s = "id2"

internal class StringSchemaLoaderTest {
    @Test
    fun fetchSchemaByIdPresentTest() {
        val schemaString = "{}"
        val schemaLoader: SchemaLoader = StringSchemaLoader(mapOf(Pair("id1", schemaString)))
        val loadedSchema = schemaLoader.loads("id1")
        Assertions.assertEquals(schemaString, loadedSchema)
    }

    @Test
    fun fetchSchemaByIdAbsentTest() {
        val schemaLoader: SchemaLoader = StringSchemaLoader(mapOf())
        val id = "id2"
        val validationException = Assertions.assertThrowsExactly(ValidationException::class.java) {
            schemaLoader.loads(id)
        }
        val responseDetails = ResponseDetails.COULD_NOT_LOAD_SCHEMA
        Assertions.assertEquals(responseDetails.messageCode, validationException.messageCode)
        Assertions.assertEquals(responseDetails.getMessage(id), validationException.message)
    }

}