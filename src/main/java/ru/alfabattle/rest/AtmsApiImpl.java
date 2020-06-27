package ru.alfabattle.rest;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.alfabattle.alfa.AlfaApi;
import ru.alfabattle.alfa.pojo.AlfaAtms;
import ru.alfabattle.alfa.pojo.DataDto;
import ru.alfabattle.api.AtmsApi;
import ru.alfabattle.dto.AtmResponse;

@Slf4j
@RestController
public class AtmsApiImpl implements AtmsApi {

    @Autowired
    private AlfaApi alfaApi;

    @Override
    public ResponseEntity<AtmResponse> getByIdUsingGET(Integer id) {
        DataDto atms = alfaApi.getAtms();
        AtmResponse response = new AtmResponse();
        response.setDeviceId(id);
        return ResponseEntity.ok(response);
    }
}