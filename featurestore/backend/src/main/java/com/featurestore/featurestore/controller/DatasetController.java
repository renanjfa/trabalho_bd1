package com.featurestore.featurestore.controller;

import com.featurestore.featurestore.service.DatasetService;
import com.featurestore.featurestore.models.Dataset;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/datasets")
public class DatasetController {
    private final DatasetService datasetService= new DatasetService();

    @GetMapping
    public ResponseEntity<?> listarTodos(){
        try{
            List<Dataset> datasets=datasetService.listarTodos();
            return ResponseEntity.ok(datasets);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id){
        try{
            Dataset dataset = datasetService.buscarPorId(id);
            return ResponseEntity.ok(dataset);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Map<String,Object>body, HttpServletRequest request){
        try{
            String emailUsuario = (String) request.getAttribute("emailUsuario");
            String nome = (String) body.get("nome_dataset");
            String descricao = (String) body.get("descricao");
            List<String> fontes = (List<String>) body.get("fontes");
            Dataset dataset = datasetService.criar(nome, descricao, fontes, emailUsuario);
            return ResponseEntity.ok(Map.of("message","Dataset criado com sucesso","id",dataset.getId()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            datasetService.deletar(id);
            return ResponseEntity.ok(Map.of("message", "Dataset deletado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}

