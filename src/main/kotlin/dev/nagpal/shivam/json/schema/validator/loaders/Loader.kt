package dev.nagpal.shivam.json.schema.validator.loaders

interface Loader {
    fun loads(id: String): String
}