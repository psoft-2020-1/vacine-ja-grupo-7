package com.ufcg.psoft.vacinaja.controller;

import com.ufcg.psoft.vacinaja.dto.IdDTO;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.RegistroInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.VacinaInvalidaException;
import com.ufcg.psoft.vacinaja.model.RegistroVacinacao;
import com.ufcg.psoft.vacinaja.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RegistroApiController {

    @Autowired
    private RegistroService registroService;

    @RequestMapping(value = "/registro-vacinacao/{cpfCidadao}", method = RequestMethod.POST)
    public ResponseEntity<?> vacinar(@PathVariable("cpfCidadao") String cpfCidadao, @RequestBody IdDTO vacinaIdDTO) {
        ResponseEntity<?> response;
        try {
            RegistroVacinacao registro = registroService.vacinar(cpfCidadao, vacinaIdDTO.getId());
            response = new ResponseEntity<>(registro, HttpStatus.OK);
        } catch (CidadaoInvalidoException | VacinaInvalidaException | RegistroInvalidoException e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @RequestMapping(value = "/registro-vacinacao/{cpfCidadao}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletarRegistro(@PathVariable("cpfCidadao") String cpfCidadao) {
        ResponseEntity<?> response;
        try {
            registroService.deletarRegistro(cpfCidadao);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (CidadaoInvalidoException | VacinaInvalidaException | RegistroInvalidoException e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
