package dev.nagpal.shivam.json.schema.validator.enums

import java.text.MessageFormat

enum class ResponseDetails(location: Location, messageCode: String, private val message: String) {

    // Schema Error Response Details
    COULD_NOT_LOAD_SCHEMA(Location.SCHEMA, "E-001", "Could not load schema with id: {0}"),
    COULD_NOT_LOAD_RESOURCE_SCHEMA(Location.SCHEMA, "E-002", "Could not load resource schema with file path: {0}"),

    // Content Error Response Details
    CONTENT_CONSTRAINT_VIOLATION(Location.CONTENT, "E-001", "Content violated the constraints defined in the Schema"),
    ;

    val messageCode: String

    init {
        val locationType = when (location) {
            Location.SCHEMA -> 'S'
            Location.CONTENT -> 'C'
        }

        this.messageCode = "JSVU-$locationType-$messageCode"
    }

    fun getMessage(vararg arguments: Any): String {
        return MessageFormat.format(message, *arguments)
    }

    enum class Location {
        SCHEMA,
        CONTENT,
    }
}