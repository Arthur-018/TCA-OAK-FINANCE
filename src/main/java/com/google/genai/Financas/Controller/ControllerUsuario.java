package com.google.genai.Financas.Controller;

import com.google.genai.Financas.BancoMySQL.Declaracao;
import com.google.genai.Financas.Modelos.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
public class ControllerUsuario {
    private final Declaracao declaracao = new Declaracao();

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody Usuario usuario) {
        String resposta = declaracao.CadastraUsuario(usuario.nome(), usuario.email(), usuario.senha());
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        String resposta = declaracao.LogarUsuario(usuario.email(), usuario.senha());
        return ResponseEntity.ok(resposta);
    }

}
