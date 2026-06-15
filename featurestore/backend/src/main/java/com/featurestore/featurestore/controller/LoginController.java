package com.featurestore.featurestore.controller;

import com.featurestore.featurestore.service.UsuarioService;
import com.featurestore.featurestore.models.Usuario;
import com.featurestore.featurestore.models.Dataset;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final UsuarioService usuarioService = new UsuarioService();

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Map<String, String> body){
        try{
            String email = body.get("email");
            String nome = body.get("nome_usuario");
            String senha = body.get("senha");
            Usuario usuario = usuarioService.registrar(email, nome, senha);
            return ResponseEntity.ok(Map.of("message","Usuario registrado com sucesso","email",usuario.getEmail()));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String>body){
        try{
            String email = body.get("email");
            String senha = body.get("senha");
            String token = usuarioService.autenticar(email, senha);
            return ResponseEntity.ok(Map.of("token",token));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @GetMapping("/meus-datasets")
    public ResponseEntity<?> meusDatasets(HttpServletRequest request){
        try{
            String email = (String) request.getAttribute("emailUsuario");
            List<Dataset> datasets = usuarioService.getMeuDatasets(email);
            return ResponseEntity.ok(datasets);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request){
        try{
            String email = (String) request.getAttribute("emailUsuario");
            Usuario usuario = usuarioService.buscarPorEmail(email);
            return ResponseEntity.ok(Map.of("email",usuario.getEmail(), "nome_usuario", usuario.getNome()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    
}
