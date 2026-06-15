package com.featurestore.featurestore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.featurestore.featurestore.models.Feature;


public class PgFeatureDAO implements FeatureDAO {
    
    private final Connection connection;

    public PgFeatureDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_QUERY =
                                "INSERT INTO featurestore.feature (nome_feature, tipo, descricao) " +
                                "VALUES (?, ?, ?) RETURNING id_feature;";

    private static final String INSERT_WITHOUT_DESC_QUERY =
                                "INSERT INTO featurestore.feature (nome_feature, tipo) " +
                                "VALUES (?, ?);";

    private static final String READ_QUERY =
                                "SELECT nome_feature, tipo, descricao " +
                                "FROM featurestore.feature " +
                                "WHERE id_feature = ?;";

    private static final String UPDATE_QUERY =
                                "UPDATE featurestore.feature " +
                                "SET nome_feature = ?, tipo = ?, descricao = ? " +
                                "WHERE id_feature = ?;";

    private static final String DELETE_QUERY =
                                "DELETE FROM featurestore.feature " +
                                "WHERE id_feature = ?;";

    private static final String ALL_QUERY =
                                "SELECT id_feature, nome_feature, tipo, descricao " +
                                "FROM featurestore.feature;";

    private static final String GET_BY_TYPE_QUERY =
                                "SELECT id_feature, nome_feature, tipo, descricao " +
                                "FROM featurestore.feature " +
                                "WHERE tipo = ?;";


    @Override
    public void create(Feature ft) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, ft.getNome());
            statement.setString(2, ft.getTipo());
            statement.setString(3, ft.getDescricao());

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    ft.setId(result.getInt("id_feature"));
                }
            }

        } catch (SQLException ex)  {
            Logger.getLogger(PgFeatureDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir FEATURE: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir feature.");
            }
        }
    }

    @Override
    public void insertWithoutDescricao(Feature ft) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(INSERT_WITHOUT_DESC_QUERY)) {
            statement.setString(1, ft.getNome());
            statement.setString(2, ft.getTipo());

            statement.executeUpdate();

        } catch (SQLException ex)  {
            Logger.getLogger(PgFeatureDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir FEATURE: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir feature.");
            }
        }
    }

    @Override
    public Feature read(Integer id) throws SQLException{
        Feature ft = new Feature();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    ft.setId(id);
                    ft.setNome(result.getString("nome_feature"));
                    ft.setTipo(result.getString("tipo"));
                    ft.setDescricao(result.getString("descricao"));
                   
                } else {
                    throw new SQLException("Erro ao visualizar: feature não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgFeatureDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            if (ex.getMessage().equals("Erro ao visualizar: feature não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar feature.");
            }
        }

        return ft;
    }
    
    @Override
    public void update(Feature ft) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, ft.getNome());
            statement.setString(2, ft.getTipo());
            statement.setString(3, ft.getDescricao());
            statement.setInt(4, ft.getId());

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: feature não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgFeatureDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: feature não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar feature: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar feature.");
            }
        }
    }
    
    @Override
    public void delete(Integer id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: feature não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgFeatureDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: feature não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir feature.");
            }
        }
    }
    
    @Override
    public List<Feature> all() throws SQLException{
        List<Feature> featureList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
            ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Feature ft = new Feature();
                ft.setId(result.getInt("id_feature"));
                ft.setNome(result.getString("nome_feature"));
                ft.setTipo(result.getString("tipo"));
                ft.setDescricao(result.getString("descricao"));

                featureList.add(ft);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgFeatureDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar features.");
        }

        return featureList;
    }

    @Override
    public List<Feature> getByTipo(String tipo) throws SQLException {
        List<Feature> featureList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_BY_TYPE_QUERY)) {

            statement.setString(1, tipo);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    Feature ft = new Feature();
                    ft.setId(result.getInt("id_feature"));
                    ft.setNome(result.getString("nome_feature"));
                    ft.setTipo(result.getString("tipo"));
                    ft.setDescricao(result.getString("descricao"));
    
                    featureList.add(ft);
                }
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(PgFeatureDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar features.");
        }

        return featureList;
    }

}
