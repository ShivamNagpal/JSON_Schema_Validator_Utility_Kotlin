package dev.nagpal.shivam.json.schema.validator.loaders.impl

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class StringSchemaLoaderTest {
    @Test
    fun fetchSchemaByIdPresentTest() {
        val schemaString = "{}"
        val stringSchemaLoader = StringSchemaLoader(mapOf(Pair("id1", schemaString)))
        val schemaOptional = stringSchemaLoader.fetchSchemaById("id1")
        Assertions.assertEquals(true, schemaOptional.isPresent)
        Assertions.assertEquals(schemaString, schemaOptional.get())
    }

    @Test
    fun fetchSchemaByIdAbsentTest() {
        val stringSchemaLoader = StringSchemaLoader(mapOf())
        val schemaOptional = stringSchemaLoader.fetchSchemaById("id2")
        Assertions.assertEquals(false, schemaOptional.isPresent)
    }

}