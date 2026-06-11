package com.featurestore.featurestore.dao;

import java.sql.SQLException;
import java.util.List;

import com.featurestore.featurestore.models.Usuario;
import com.featurestore.featurestore.models.Dataset;

public interface UsuarioDAO extends DAO<Usuario> {

    public Usuario getByEmail(String email) throws SQLException;

    public void deleteByEmail(String email) throws SQLException;

    public void authenticate(Usuario user) throws SQLException, SecurityException;

    public List<Dataset> getMeusDatasets(Usuario user) throws SQLException;
}
