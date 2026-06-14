package com.featurestore.featurestore.controller;

import com.featurestore.featurestore.service.UsuarioService;
import com.featurestore.featurestore.models.Usuario;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    
}
