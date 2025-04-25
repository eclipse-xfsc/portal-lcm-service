package gaiax.lcm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardServiceResponse {
    @JsonProperty(value = "results", required = true)
    private List<ServiceItem> data;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ServiceItem {
        @JsonProperty(value = "id", required = true)
        private String id;
        @JsonProperty(value = "name", required = true)
        private String name;
        @JsonProperty(value = "preview_img", required = true)
        private String previewImg;
        @JsonProperty(value = "provider_url", required = true)
        private String providerUrl;
        @JsonProperty(value = "logo", required = true)
        private String logo;
        @JsonProperty(value = "description", required = true)
        private String description;
        @JsonProperty(value = "is_activated", required = true)
        private boolean isActivated;
        @JsonProperty(value = "is_own", required = true)
        private boolean isOwn;
        @JsonProperty(value = "status", required = true)
        private String status;
    }

}
