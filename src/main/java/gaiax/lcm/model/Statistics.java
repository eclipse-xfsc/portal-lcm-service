package gaiax.lcm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Statistics {
    @JsonProperty(value = "results", required = true)
    private Plot[] results;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Plot {
        @JsonProperty(value = "name", required = true)
        private String name;
        @JsonProperty(value = "charts", required = true)
        private Chart[] charts;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Chart {
        @JsonProperty(value = "x", required = true)
        private String[] x;
        @JsonProperty(value = "y", required = true)
        private double[] y;
        @JsonProperty(value = "type", required = true)
        private String type;
        @JsonProperty(value = "marker", required = true)
        private Map<String, String> marker;
        @JsonProperty(value = "mode", required = true)
        private String mode;
        @JsonProperty(value = "showlegend", required = true)
        private boolean showlegend;
        @JsonProperty(value = "name", required = true)
        private String name;
    }
}
