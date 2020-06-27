package ru.alfabattle.alfa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class DataDto {
    @JsonProperty("data")
    private AlfaAtms alfaAtms;
}
