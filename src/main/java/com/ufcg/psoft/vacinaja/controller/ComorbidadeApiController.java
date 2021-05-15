package com.ufcg.psoft.vacinaja.controller;

import com.ufcg.psoft.vacinaja.model.Comorbidade;
import com.ufcg.psoft.vacinaja.service.ComorbidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ComorbidadeApiController {

    @Autowired
    private ComorbidadeService comorbidadeService;

    /**
     * API para listar as comorbidades cadastradas.
     *
     * @return comorbidades cadastradas.
     */
    @RequestMapping(value = "/comorbidades/", method = RequestMethod.GET)
    public ResponseEntity<?> listarComorbidades() {
        List<Comorbidade> comorbidades = comorbidadeService.listarComorbidades();
        return new ResponseEntity<>(comorbidades, HttpStatus.OK);
    }

}
