import dev.nagpal.shivam.json.schema.validator.cache.CacheProperties;
import dev.nagpal.shivam.json.schema.validator.cache.CacheStore;
import dev.nagpal.shivam.json.schema.validator.loaders.impl.StringSchemaLoader;
import dev.nagpal.shivam.json.schema.validator.services.JsonValidatorService;
import dev.nagpal.shivam.json.schema.validator.vendor.impl.networknt.NetworkNTSchemaIngestionService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

class JavaAccessibilityTest {

    @Test
    void createJavaValidatorServiceUsingBuilder() {
        JsonValidatorService.JsonValidatorServiceBuilder builder = JsonValidatorService
                .builder(new StringSchemaLoader("{}"))
                .cacheProperties(CacheProperties.builder().build())
                .schemaIngestionService(new NetworkNTSchemaIngestionService());
        builder.build();
    }

    @Test
    void createCachePropertiesUsingBuilder() {
        CacheProperties.CachePropertiesBuilder builder = CacheProperties.builder()
                .enableLocalCache(false)
                .addCacheStore(getDummyCacheStore());
        builder.build();
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
