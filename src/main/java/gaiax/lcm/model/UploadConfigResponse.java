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
public class UploadConfigResponse {
    @JsonProperty(value = "services")
    private UpdateConfigRq.Service[] services;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Service {
        @JsonProperty(value = "id")
        private String id;
        @JsonProperty(value = "name")
        private String name;
        @JsonProperty(value = "fields")
        private UpdateConfigRq.Field[] fields;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Field {
        @JsonProperty(value = "id")
        private String id;
        @JsonProperty(value = "label")
        private String label;
        @JsonProperty(value = "value")
        private String value;
    }
}
