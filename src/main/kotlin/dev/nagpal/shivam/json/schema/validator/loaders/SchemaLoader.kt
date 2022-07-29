package dev.nagpal.shivam.json.schema.validator.loaders

interface SchemaLoader {
    fun loads(id: String): String
}