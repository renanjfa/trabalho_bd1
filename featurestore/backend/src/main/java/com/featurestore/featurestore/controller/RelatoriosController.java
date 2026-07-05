package com.featurestore.featurestore.controller;

import com.featurestore.featurestore.service.RelatoriosService;
import com.featurestore.featurestore.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/api/relatorios")
public class RelatoriosController {
    private final RelatoriosService relatoriosService = new RelatoriosService();

    @GetMapping("/sistema")
    public ResponseEntity<?> getInfoSistema(){
        try{
            SistemaDTO info = relatoriosService.getInfoSistema();
            return ResponseEntity.ok(info);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @GetMapping("/versoes-datasets")
    public ResponseEntity<?> getQuantidadeVersoesDataSets(){
        try{
            List<QntVersoesDatasetDTO> lista = relatoriosService.getQuantidadeVersoesDatasets();
            return ResponseEntity.ok(lista);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @GetMapping("/acessos-downloads/{idDataset}")
    public ResponseEntity<?> getAcessosDownloadsDataset(@PathVariable Integer idDataset){
        try {
            QntAcessoDownloadDatasetDTO info = relatoriosService.getAcessosDownloadDatasetDTO(idDataset);
            return ResponseEntity.ok(info);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @GetMapping("/datasets-mais")
    public ResponseEntity<?> getDatasetsMais(@RequestParam(defaultValue = "acessos")String orderBy){
        try {
            List<DatasetMaisDTO> lista = relatoriosService.getDatasetsMais(orderBy);
            return ResponseEntity.ok(lista);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @GetMapping("/contribuicoes") public ResponseEntity <?> getContribuicaoUsuarios(){
        try {
            List<ContribuicaoDTO> lista = relatoriosService.getContribuicaoUsuarios();
            return ResponseEntity.ok(lista);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @GetMapping("/historicos/{idDataset}") public ResponseEntity <?> getHistoricoAcessosDownloads(@PathVariable Integer idDataset){
        try {
            List<HistoricoDatasetDTO> lista = relatoriosService.getHistoricoAcessosDownloads(idDataset);
            return ResponseEntity.ok(lista);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }
}
