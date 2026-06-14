package com.featurestore.featurestore.service;
import com.featurestore.featurestore.dao.DAOFactory;
import com.featurestore.featurestore.dao.UsuarioDAO;
import com.featurestore.featurestore.models.Usuario;
import com.featurestore.featurestore.security.JwtUtil;
import com.featurestore.featurestore.models.Dataset;

import java.util.List;


public class UsuarioService {
    public Usuario registrar(String email, String nome, String senha) throws Exception{
        if(email == null || email.isBlank()){
            throw new Exception("Email obrigatorio");
        }
        if(nome == null || nome.isBlank()){
            throw new Exception("Nome é obrigatorio");
        }
        if (senha == null || senha.isBlank()){
            throw new Exception("Senha é obrigatoria");
        }
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setSenha(senha);

        try (DAOFactory daoFactory = DAOFactory.getInstance()){
            UsuarioDAO dao = daoFactory.getUsuarioDAO();
            dao.create(usuario);
        }
        return usuario;
    }
    public String autenticar(String email, String senha)throws Exception{
        if (email == null || email.isBlank() || senha == null || senha.isBlank()){
            throw new Exception("Email e senha sao obrigatorios");
        }
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);
        try (DAOFactory daoFactory = DAOFactory.getInstance()){
            UsuarioDAO dao = daoFactory.getUsuarioDAO();
            dao.authenticate(usuario);
        }
        return JwtUtil.generateToken(email);
    }
    public Usuario buscarPorEmail(String email) throws Exception{
        try (DAOFactory daoFactory = DAOFactory.getInstance()){
            UsuarioDAO dao = daoFactory.getUsuarioDAO();
            return dao.getByEmail(email);
        }
    }
    public void deletar(String email)throws Exception{
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            UsuarioDAO dao = daoFactory.getUsuarioDAO();
            dao.deleteByEmail(email);
        }
    }
    public List<Dataset> getMeuDatasets(String email)throws Exception{
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            UsuarioDAO dao = daoFactory.getUsuarioDAO();
            return dao.getMeusDatasets(usuario);
        }
    }

}
