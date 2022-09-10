package dev.nagpal.shivam.json.schema.validator.loaders.impl

import dev.nagpal.shivam.json.schema.validator.enums.ResponseDetails
import dev.nagpal.shivam.json.schema.validator.exceptions.ValidationException
import dev.nagpal.shivam.json.schema.validator.loaders.SchemaLoader
import java.io.File
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*

class ResourceSchemaLoader @JvmOverloads constructor(
    pathPrefix: String = "",
    private val fileNameSuffix: String = ""
) : SchemaLoader() {
    private val pathPrefix: String

    init {
        var pathPrefixString = pathPrefix.trimEnd('/', '\\')
        if (pathPrefixString.isNotBlank()) {
            pathPrefixString += File.separator
        }
        this.pathPrefix = pathPrefixString
    }

    override fun fetchSchemaById(id: String): Optional<String> {
        val filePath = "$pathPrefix$id$fileNameSuffix"
        val inputStream: InputStream =
            Thread.currentThread().contextClassLoader.getResourceAsStream(filePath)
                ?: throw ValidationException(ResponseDetails.COULD_NOT_LOAD_RESOURCE_SCHEMA, filePath)
        val scanner: Scanner = Scanner(inputStream, StandardCharsets.UTF_8.name()).useDelimiter("\\A")
        val text: String = scanner.next()
        scanner.close()
        return Optional.of(text)
    }
}