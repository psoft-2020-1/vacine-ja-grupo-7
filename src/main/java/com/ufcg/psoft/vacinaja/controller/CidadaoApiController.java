package com.ufcg.psoft.vacinaja.controller;


import com.ufcg.psoft.vacinaja.dto.CidadaoDTO;
import com.ufcg.psoft.vacinaja.dto.ComorbidadeDTO;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.ComorbidadeInvalidaException;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Comorbidade;
import com.ufcg.psoft.vacinaja.service.CidadaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CidadaoApiController {

    @Autowired
    private CidadaoService cidadaoService;

    /**
     * API para cadastrar um cidadão no sistema.
     *
     * @param cidadaoDTO Data Transfer Object do cidadao para o cadastro.
     * @return cidadao cadastrado.
     */
    @RequestMapping(value = "/cidadao/", method = RequestMethod.POST)
    public ResponseEntity<?> cadastrarCidadao(@RequestBody CidadaoDTO cidadaoDTO) {
        ResponseEntity response;
        try {
            Cidadao cidadaoCadastrado = cidadaoService.cadastrarCidadao(cidadaoDTO);
            response = new ResponseEntity<>(cidadaoCadastrado, HttpStatus.CREATED);
        }catch (CidadaoInvalidoException cie){
            response = new ResponseEntity(cie.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            response = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    /**
     * API para atualziar um cidadão no sistema.
     *
     * @param cidadaoDTO Data Transfer Object do cidadao para a atualização.
     * @return cidadao atualizada.
     */
    @RequestMapping(value = "/cidadao/", method = RequestMethod.PUT)
    public ResponseEntity<?> atualizarComorbidade(@RequestBody CidadaoDTO cidadaoDTO) {
        ResponseEntity response;
        try {
            Cidadao cidadaoAtualizado = cidadaoService.atualizarCidadao(cidadaoDTO);
            response =  new ResponseEntity<>(cidadaoAtualizado, HttpStatus.OK);
        }catch (CidadaoInvalidoException cie){
            response = new ResponseEntity(cie.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            response = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


}
