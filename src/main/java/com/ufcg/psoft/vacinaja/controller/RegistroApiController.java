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

    @RequestMapping(value = "/registro-vacinacao/{pessoaId}", method = RequestMethod.POST)
    public ResponseEntity<?> vacina(@PathVariable("pessoaId") String pessoaId, @RequestBody IdDTO vacinaIdDTO) {
        ResponseEntity<?> response;
        try {
            RegistroVacinacao registro = registroService.vacina(pessoaId, vacinaIdDTO.getId());
            response = new ResponseEntity<>(registro, HttpStatus.OK);
        } catch (CidadaoInvalidoException cie) {
            System.out.println(cie.getMessage());
            response = new ResponseEntity<>(cie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (VacinaInvalidaException vie) {
            System.out.println(vie.getMessage());
            response = new ResponseEntity<>(vie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RegistroInvalidoException rie) {
            System.out.println(rie.getMessage());
            response = new ResponseEntity<>(rie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @RequestMapping(value = "/registro-vacinacao/{pessoaId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletarRegistro(@PathVariable("pessoaId") String pessoaId) {
        ResponseEntity<?> response;
        try {
            registroService.deletarRegistro(pessoaId);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (CidadaoInvalidoException cie) {
            System.out.println(cie.getMessage());
            response = new ResponseEntity<>(cie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (VacinaInvalidaException vie) {
            System.out.println(vie.getMessage());
            response = new ResponseEntity<>(vie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RegistroInvalidoException rie) {
            System.out.println(rie.getMessage());
            response = new ResponseEntity<>(rie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
