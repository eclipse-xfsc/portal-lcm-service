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
public class UpdateConfigRq {
    @JsonProperty(value = "services", required = true)
    private Service[] services;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Service {
        @JsonProperty(value = "serviceId", required = true)
        private String serviceId;
        @JsonProperty(value = "lcmServiceId", required = true)
        private String lcmServiceId;
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
        @JsonProperty(value = "value", required = true)
        private String value;
    }
}