package com.featurestore.featurestore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.featurestore.featurestore.models.Usuario;
import com.featurestore.featurestore.models.Dataset;


public class PgUsuarioDAO implements UsuarioDAO {
    
    private final Connection connection;

    public PgUsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_QUERY =
                                "INSERT INTO featurestore.usuario (email, nome_usuario, senha) " +
                                "VALUES (?, ?, MD5(?));";

    private static final String READ_BY_EMAIL_QUERY =
                                "SELECT email, nome_usuario " +
                                "FROM featurestore.usuario " +
                                "WHERE email = ?";

    private static final String DELETE_BY_EMAIL_QUERY =
                                "DELETE FROM featurestore.usuario " +
                                "WHERE email = ?;";

    private static final String ALL_QUERY =
                                "SELECT email, nome " +
                                "FROM featurestore.usuario;";
    
    private static final String UPDATE_QUERY =
                                "UPDATE featurestore.usuario " +
                                "SET nome_usuario = ?, senha = MD5(?) " +
                                "WHERE email = ?;";

    private static final String AUTHENTICATE_QUERY =
                                "SELECT email, nome_usuario " +
                                "FROM featurestore.usuario " +
                                "WHERE email = ? AND senha = md5(?);";

    
    @Override
    public void create(Usuario user) throws SQLException {

        try(PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getNome());
            statement.setString(3, user.getSenha());

            statement.executeUpdate();

        } catch (SQLException ex)  {
            Logger.getLogger(PgUsuarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir USUARIO: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir usuário.");
            }
        }
    }

    /* ATENCAO: Nao usar a funcao read. Primary key = email */
    @Override
    public Usuario read(Integer id) throws SQLException {

       throw new UnsupportedOperationException("Operação não suportada.");
    }

    @Override
    public Usuario getByEmail(String email) throws SQLException {
        Usuario user = new Usuario();

        try (PreparedStatement statement = connection.prepareStatement(READ_BY_EMAIL_QUERY)) {
            statement.setString(1, email);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    user.setEmail(email);
                    user.setNome(result.getString("nome_usuario"));
                } else {
                    throw new SQLException("Erro ao visualizar: usuário não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUsuarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            if (ex.getMessage().equals("Erro ao visualizar: usuário não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar usuário.");
            }
        }

        return user;
    }

    @Override
    public void update(Usuario user) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, user.getNome());
            statement.setString(2, user.getSenha());
            statement.setString(3, user.getEmail());

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: usuário não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUsuarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: usuário não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar usuário: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar usuário.");
            }
        }
    }


    /* ATENCAO: Nao usar a funcao delete. Primary key = email. Utilizar deleteByEmail */
    @Override
    public void delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Operação não suportada.");
    }

    @Override
    public void deleteByEmail(String email) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BY_EMAIL_QUERY)) {
            statement.setString(1, email);

            if(statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: usuario nao encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUsuarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: usuário não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir usuário.");
            }
        }
    }

    @Override
    public List<Usuario> all() throws SQLException { 
        List<Usuario> userList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY); ResultSet result = statement.executeQuery()) {
            while(result.next()) {
                Usuario user = new Usuario();
                user.setEmail(result.getString("email"));
                user.setNome(result.getString("nome_usuario"));

                userList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUsuarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return userList;
    }


    @Override
    public void authenticate(Usuario user) throws SQLException, SecurityException {
        try (PreparedStatement statement = connection.prepareStatement(AUTHENTICATE_QUERY)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getSenha());

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    user.setEmail(result.getString("email"));
                    user.setNome(result.getString("nome_usuario"));
                } else {
                    throw new SecurityException("Login ou senha incorretos.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUsuarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao autenticar usuário.");
        } 
    }

    @Override
    public List<Dataset> getMeusDatasets(Usuario user) throws SQLException {
        List<Dataset> meusDatasets = new ArrayList<>();
        return meusDatasets;
    }

}
