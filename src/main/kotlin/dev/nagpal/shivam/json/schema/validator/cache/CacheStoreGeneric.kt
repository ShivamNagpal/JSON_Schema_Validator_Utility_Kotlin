package dev.nagpal.shivam.json.schema.validator.cache

sealed interface CacheStoreGeneric<T, R> {
    fun get(key: T): R?
    fun put(key: T, value: R)
    fun delete(key: T)
    fun deleteAll()
}