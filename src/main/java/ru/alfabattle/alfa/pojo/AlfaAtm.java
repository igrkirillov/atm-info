package ru.alfabattle.alfa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AlfaAtm {
    @JsonProperty("deviceId")
    private int deviceId;
}
