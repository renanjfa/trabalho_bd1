package com.featurestore.featurestore.dao;

import java.sql.SQLException;
import com.featurestore.featurestore.models.Usuario;

public interface UsuarioDAO extends DAO<Usuario> {

    public Usuario getByEmail(String email) throws SQLException;

    public void deleteByEmail(String email) throws SQLException;

    public void authenticate(Usuario user) throws SQLException, SecurityException;
}
