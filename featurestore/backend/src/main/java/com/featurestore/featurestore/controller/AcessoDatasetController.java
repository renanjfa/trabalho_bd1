package com.featurestore.featurestore.controller;

import com.featurestore.featurestore.service.AcessoDatasetService;
import com.featurestore.featurestore.models.AcessoDataset;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/acessos")
public class AcessoDatasetController {
    private final AcessoDatasetService acessoService = new AcessoDatasetService();

    @PostMapping
    public ResponseEntity<?> registrarAcesso(@RequestBody Map<String, Object> body, HttpServletRequest request){
        try{
            String email = (String) request.getAttribute("emailUsuario");
            Integer idDataset = (Integer) body.get("id_dataset");
            acessoService.registrarAcesso(email, idDataset);
            return ResponseEntity.ok(Map.of("message", "Acesso Registrado com sucesso"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body((Map.of("error", e.getMessage())));
        }
    }

    @GetMapping("/dataset/{idDataset}")
    public ResponseEntity<?> listarPorDataset(@PathVariable Integer idDataset){
        try {
            List<AcessoDataset> acessos = acessoService.listarPorDataset(idDataset);
            return ResponseEntity.ok(acessos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @GetMapping("/usuario")
    public ResponseEntity<?> listarPorUsuario(HttpServletRequest request){
        try {
            String email = (String) request.getAttribute("emailUsuario");
            List<AcessoDataset> acessos = acessoService.listarPorUsuario(email);
            return ResponseEntity.ok(acessos);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }
}
