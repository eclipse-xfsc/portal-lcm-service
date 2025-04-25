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
public class TransactionResponse {
    @JsonProperty(value = "results", required = true)
    private List<TransactionItem> data;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class TransactionItem {
        @JsonProperty(value = "id", required = true)
        private String id;
        @JsonProperty(value = "title", required = true)
        private String title;
        @JsonProperty(value = "subtitle", required = true)
        private String subtitle;
        @JsonProperty(value = "date", required = true)
        private String date;
    }
}
