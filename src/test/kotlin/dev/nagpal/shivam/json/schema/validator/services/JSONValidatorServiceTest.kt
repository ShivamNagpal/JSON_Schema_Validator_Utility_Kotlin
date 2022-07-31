package dev.nagpal.shivam.json.schema.validator.services

import dev.nagpal.shivam.json.schema.validator.loaders.SchemaLoader
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class JSONValidatorServiceTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun testBuilderCreation() {
        val schemaLoader = object : SchemaLoader() {
            override fun fetchSchemaById(id: String): Optional<String> {
                TODO("Not yet implemented")
            }
        }
        val jsonValidatorService = JsonValidatorService.builder(schemaLoader).build()
        Assertions.assertEquals(schemaLoader, jsonValidatorService.schemaLoader)
        Assertions.assertNotNull(jsonValidatorService)
    }
}
