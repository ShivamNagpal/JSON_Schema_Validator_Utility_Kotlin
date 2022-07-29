package dev.nagpal.shivam.json.schema.validator.services

import dev.nagpal.shivam.json.schema.validator.loaders.SchemaLoader
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class JSONValidatorServiceTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun testBuilderCreation() {
        val jsonValidatorService = JsonValidatorService.builder(object : SchemaLoader {
            override fun loads(id: String): String {
                TODO("Not yet implemented")
            }
        }).build()
        Assertions.assertNotNull(jsonValidatorService)
    }
}
