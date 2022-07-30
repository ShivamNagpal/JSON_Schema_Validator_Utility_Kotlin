import dev.nagpal.shivam.json.schema.validator.loaders.SchemaLoader;
import dev.nagpal.shivam.json.schema.validator.services.JsonValidatorService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class JavaAccessibilityTest {

    @Test
    void createUsingBuilder() {
        JsonValidatorService.JsonValidatorServiceBuilder builder = JsonValidatorService.builder(new SchemaLoader() {
            @NotNull
            @Override
            public Optional<String> fetchSchemaById(@NotNull String id) {
                return Optional.empty();
            }
        });
        builder.build();
    }
}
