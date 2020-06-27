package ru.alfabattle.alfa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class AlfaAtms {
    @JsonProperty("atms")
    private List<AlfaAtm> list;
}
