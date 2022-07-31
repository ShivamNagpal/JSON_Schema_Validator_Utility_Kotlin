import dev.nagpal.shivam.json.schema.validator.cache.CacheProperties;
import dev.nagpal.shivam.json.schema.validator.cache.CacheStore;
import dev.nagpal.shivam.json.schema.validator.loaders.impl.StringSchemaLoader;
import dev.nagpal.shivam.json.schema.validator.services.JsonValidatorService;
import dev.nagpal.shivam.json.schema.validator.vendor.impl.networknt.NetworkNTSchemaIngestionService;
import kotlin.UninitializedPropertyAccessException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class JavaAccessibilityTest {

    @Test
    void createJsonValidatorServiceUsingBuilder() {
        JsonValidatorService.JsonValidatorServiceBuilder builder = JsonValidatorService
                .builder(new StringSchemaLoader(new HashMap<>()))
                .cacheProperties(CacheProperties.builder().build())
                .schemaIngestionService(new NetworkNTSchemaIngestionService());
        JsonValidatorService jsonValidatorService = builder.build();
        Assertions.assertNotNull(jsonValidatorService);
    }

    @Test
    void testJsonValidatorServiceDisableLocalCache() {
        JsonValidatorService.JsonValidatorServiceBuilder builder = JsonValidatorService
                .builder(new StringSchemaLoader(new HashMap<>()))
                .cacheProperties(CacheProperties.builder().enableLocalCache(false).build())
                .schemaIngestionService(new NetworkNTSchemaIngestionService());
        JsonValidatorService jsonValidatorService = builder.build();
        Assertions.assertNotNull(jsonValidatorService);
        Assertions.assertThrowsExactly(UninitializedPropertyAccessException.class, jsonValidatorService::getLocalCacheStore);
    }

    @Test
    void createCachePropertiesUsingBuilder() {
        CacheProperties.CachePropertiesBuilder builder = CacheProperties.builder()
                .enableLocalCache(false)
                .addCacheStore(getDummyCacheStore());
        CacheProperties cacheProperties = builder.build();
        Assertions.assertNotNull(cacheProperties);
    }

    @NotNull
    private CacheStore getDummyCacheStore() {
        return new CacheStore() {
            @Nullable
            @Override
            public String get(String key) {
                return null;
            }

            @Override
            public void put(String key, String value) {
            }

            @Override
            public void delete(String key) {
            }

            @Override
            public void deleteAll() {
            }
        };
    }
}
