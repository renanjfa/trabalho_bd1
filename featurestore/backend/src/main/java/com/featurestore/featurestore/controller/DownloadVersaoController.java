package com.featurestore.featurestore.controller;

import com.featurestore.featurestore.service.DownloadVersaoService;
import com.featurestore.featurestore.models.DownloadVersao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/downloads")
public class DownloadVersaoController {
    private final DownloadVersaoService downloadService = new DownloadVersaoService();

    @GetMapping("/versao/{idVersao}")
    public ResponseEntity<?> listarPorVersao(@PathVariable Integer idVersao){
        try {
            List<DownloadVersao> downloads = downloadService.listarPorVersao(idVersao);
            return ResponseEntity.ok(downloads);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));

        }
    }

    @GetMapping("/usuario")
    public ResponseEntity<?> listarPorUsuario(HttpServletRequest request) {
        try {
            String email = (String) request.getAttribute("emailUsuario");
            List<DownloadVersao> downloads = downloadService.listarPorUsuario(email);
            return ResponseEntity.ok(downloads);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
