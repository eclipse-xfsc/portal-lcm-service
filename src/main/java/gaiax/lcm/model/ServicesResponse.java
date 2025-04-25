package gaiax.lcm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import gaiax.lcm.model.ServiceOverviewDto;
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
public class ServicesResponse {
    @JsonProperty(value = "services", required = true)
    private ServiceOverviewDto[] services;
}
