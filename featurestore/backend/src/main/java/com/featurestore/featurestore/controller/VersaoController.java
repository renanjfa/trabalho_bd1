package com.featurestore.featurestore.controller;

import com.featurestore.featurestore.service.VersaoService;
import com.featurestore.featurestore.models.Versao;
import com.featurestore.featurestore.models.Feature;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/versoes")
public class VersaoController {
    private final VersaoService versaoService = new VersaoService();

    @GetMapping("/dataset/{idDataset}")
    public ResponseEntity<?> listarPorDataset(@PathVariable Integer idDataset){
        try{
            List<Versao> versoes = versaoService.listarPorDataset(idDataset);
            return ResponseEntity.ok(versoes);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Map<String, Object> body, HttpServletRequest request) {
        try {
            String emailUsuario = (String) request.getAttribute("emailUsuario");
            Integer idDataset = (Integer) body.get("id_dataset");
            String nome = (String) body.get("nome_versao");
            String descricao = (String) body.get("descricao");
            String csv = (String) body.get("csv");
            Integer idVersaoBase = (Integer) body.get("id_versao_base");

            List<Feature> features = new ArrayList<>();
            List<Map<String, String>> featuresBody = (List<Map<String, String>>) body.get("features");

            if (featuresBody != null) {
                for (Map<String, String> f : featuresBody) {
                    Feature feature = new Feature();
                    feature.setNome(f.get("nome_feature"));
                    feature.setTipo(f.get("tipo"));
                    feature.setDescricao(f.get("descricao"));
                    features.add(feature);
                }
            }

            versaoService.criarComFeatures(emailUsuario, idDataset, nome, descricao, csv, idVersaoBase, features);

            return ResponseEntity.ok(Map.of("message", "Versão criada com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
