package gaiax.lcm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ServiceOverviewDto {
  @JsonProperty(value = "serviceId", required = true)
  private String serviceId;
  @JsonProperty(value = "serviceName", required = true)
  private String serviceName;
  @JsonProperty(value = "applicableLcm", required = true)
  private ApplicableLcm[] applicableLcm;

  @ToString
  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  @Accessors(chain = true)
  public static class ApplicableLcm {
    @JsonProperty(value = "id", required = true)
    private String id;
    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty(value = "description", required = true)
    private String description;
  }
}
