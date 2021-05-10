package com.ufcg.psoft.vacinaja.controller;

import com.ufcg.psoft.vacinaja.dto.ComorbidadeDTO;
import com.ufcg.psoft.vacinaja.exceptions.ComorbidadeInvalidaException;
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
    ComorbidadeService comorbidadeService;

    /**
     * API para cadastrar uma comorbidade no sistema.
     *
     * @param nomeComorbidade nome da comorbidade para cadastro.
     * @return comorbidade cadastrada.
     */
    @RequestMapping(value = "/comorbidade/{nomeComorbidade}", method = RequestMethod.POST)
    public ResponseEntity<?> cadastrarComorbidade(@PathVariable("nomeComorbidade") String nomeComorbidade) {
        ResponseEntity response;
        try {
            Comorbidade comorbidadeCadastrada = comorbidadeService.cadastrarComorbidade(nomeComorbidade);
            response = new ResponseEntity<>(comorbidadeCadastrada, HttpStatus.CREATED);
        }catch (ComorbidadeInvalidaException cie){
            response = new ResponseEntity(cie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            response = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    /**
     * API para listar as comorbidades cadastradas.
     *
     * @return comorbidades cadastradas.
     */
    @RequestMapping(value = "/comorbidade", method = RequestMethod.GET)
    public ResponseEntity<?> listarComorbidades() {
        List<Comorbidade> comorbidades = comorbidadeService.listarComorbidades();
        return new ResponseEntity<>(comorbidades, HttpStatus.OK);
    }

    /**
     * API para remover uma comorbidade no sistema.
     *
     * @param idComorbidade identificador único da comorbidade.
     * @return comorbidade removida.
     */
    @RequestMapping(value = "/comorbidade/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removerComorbidade(@PathVariable("id") Long idComorbidade) {
        ResponseEntity response;
        try {
            comorbidadeService.removerComorbidade(idComorbidade);
            response = new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (ComorbidadeInvalidaException cie){
            response = new ResponseEntity(cie.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            response = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    /**
     * API para atualziar uma comorbidade no sistema.
     *
     * @param idComorbidade identificador único da comorbidade.
     * @param comorbidadeDTO Data Transfer Object da comorbidade para a atualização.
     * @return comorbidade atualizada.
     */
    @RequestMapping(value = "/comorbidade/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> atualizarComorbidade(@PathVariable("id") Long idComorbidade,  @RequestBody ComorbidadeDTO comorbidadeDTO) {
        ResponseEntity response;
        try {
            Comorbidade comorbidadeCadastrada = comorbidadeService.atualizarComorbidade(idComorbidade, comorbidadeDTO);
            response =  new ResponseEntity<>(comorbidadeCadastrada, HttpStatus.OK);
        }catch (ComorbidadeInvalidaException cie) {
            response =  new ResponseEntity(cie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            response = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
