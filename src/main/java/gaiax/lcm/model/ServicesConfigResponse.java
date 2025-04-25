package gaiax.lcm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ServicesConfigResponse {
    @JsonProperty(value = "services", required = true)
    private ServiceConfig[] services;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class ServiceConfig {
        @JsonProperty(value = "id", required = true)
        private String id;
        @JsonProperty(value = "name", required = true)
        private String name;
        @JsonProperty(value = "fields", required = true)
        private Field[] fields;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Field {
        @JsonProperty(value = "id", required = true)
        private String id;
        @JsonProperty(value = "label", required = true)
        private String label;
    }
}
