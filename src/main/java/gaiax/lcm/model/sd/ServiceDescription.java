package gaiax.lcm.model.sd;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ServiceDescription {
    private String name;
    private String[] deployments;
}
