package dev.nagpal.shivam.json.schema.validator.loaders.impl

import dev.nagpal.shivam.json.schema.validator.loaders.SchemaLoader
import java.io.File
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*

class ResourceSchemaLoader(
    pathPrefix: String = "",
    private val fileNameSuffix: String = ""
) : SchemaLoader() {
    private val pathPrefix: String

    init {
        this.pathPrefix = pathPrefix.trimEnd('/', '\\')
    }

    override fun fetchSchemaById(id: String): Optional<String> {
        val filePath = "$pathPrefix${File.separator}$id$fileNameSuffix"
        val inputStream: InputStream =
            Thread.currentThread().contextClassLoader.getResourceAsStream(filePath)
                ?: return Optional.empty()
        val scanner: Scanner = Scanner(inputStream, StandardCharsets.UTF_8.name()).useDelimiter("\\A")
        val text: String = scanner.next()
        scanner.close()
        return Optional.of(text)
    }
}