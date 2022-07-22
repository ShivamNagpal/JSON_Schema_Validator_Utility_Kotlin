package dev.nagpal.shivam.json.schema.validator.services

import dev.nagpal.shivam.json.schema.validator.loaders.Loader

class JsonValidatorService(val loader: Loader) {
    var enableLocalCache = true

    class JsonValidatorServiceBuilder(loader: Loader) {
        private val jsonValidatorService: JsonValidatorService = JsonValidatorService(loader)

        fun enableLocalCache(enable: Boolean): JsonValidatorServiceBuilder {
            this.jsonValidatorService.enableLocalCache = enable
            return this
        }

        fun build(): JsonValidatorService {
            return jsonValidatorService
        }
    }

    companion object {
        fun builder(loader: Loader): JsonValidatorServiceBuilder {
            return JsonValidatorServiceBuilder(loader)
        }
    }
}