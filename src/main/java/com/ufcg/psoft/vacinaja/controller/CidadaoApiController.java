package com.ufcg.psoft.vacinaja.controller;


import com.ufcg.psoft.vacinaja.dto.*;
import com.ufcg.psoft.vacinaja.exceptions.CidadaoInvalidoException;
import com.ufcg.psoft.vacinaja.exceptions.ComorbidadeInvalidaException;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Comorbidade;
import com.ufcg.psoft.vacinaja.service.CidadaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

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
     * API para atualizar um cidadão no sistema.
     *
     * @param cidadaoUpdateDTO Data Transfer Object do cidadao para a atualização.
     * @return cidadão atualizada.
     */
    @RequestMapping(value = "/cidadao/", method = RequestMethod.PUT)
    public ResponseEntity<?> atualizarCidadao(@RequestBody CidadaoUpdateDTO cidadaoUpdateDTO) {
        ResponseEntity response;
        try {
            Cidadao cidadaoAtualizado = cidadaoService.atualizarCidadao(cidadaoUpdateDTO);
            response =  new ResponseEntity<>(cidadaoAtualizado, HttpStatus.OK);
        }catch (CidadaoInvalidoException cie){
            response = new ResponseEntity(cie.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            response = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    /**
     * API para listar os cidadões no sistema.
     *
     * @return cidadões cadastrados.
     */
    @RequestMapping(value = "/cidadao/listar/", method = RequestMethod.GET)
    public ResponseEntity<?> listarCidadao() {
        ResponseEntity response;
        try {
            List<Cidadao> cidadaoList = cidadaoService.listarCidadao();
            response =  new ResponseEntity<List<Cidadao>>(cidadaoList, HttpStatus.OK);
        }catch (Exception e){
            response = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    /**
     * API para deletar um cidadão no sistema.
     *
     * @param cpfDTO Data Transfer Object do cpf do cidadão para a exclusão do cidadão.
     */
    @RequestMapping(value = "/cidadao/deletar/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletarCidadao(@RequestBody CpfDTO cpfDTO) {
        ResponseEntity response;
        try {
            cidadaoService.deletarCidadao(cpfDTO);
            response = new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            response = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

}
