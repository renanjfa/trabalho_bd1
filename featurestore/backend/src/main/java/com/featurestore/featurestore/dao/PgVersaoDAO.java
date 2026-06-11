package com.featurestore.featurestore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.featurestore.featurestore.models.Versao;
import com.featurestore.featurestore.models.Feature;

public class PgVersaoDAO implements VersaoDAO {
    
    private final Connection connection;

    public PgVersaoDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_QUERY =
                                "INSERT INTO featurestore.versao (email_usuario, id_dataset, data, hora, csv, nome_versao, descricao, id_versao_base) " +
                                "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?); ";

    private static final String INSERT_WITHOUT_VERSAO_BASE_QUERY =
                                "INSERT INTO featurestore.versao (email_usuario, id_dataset, data, hora, csv, nome_versao, descricao) " +
                                "VALUES ( ?, ?, ?, ?, ?, ?, ?); ";

    private static final String INSERT_WITHOUT_DESCRICAO_QUERY =
                                "INSERT INTO featurestore.versao (email_usuario, id_dataset, data, hora, csv, nome_versao, id_versao_base) " +
                                "VALUES ( ?, ?, ?, ?, ?, ?, ?); ";

    private static final String INSERT_WITHOUT_DESCRICAO_AND_VERSAO_BASE_QUERY =
                                "INSERT INTO featurestore.versao (email_usuario, id_dataset, data, hora, csv, nome_versao) " +
                                "VALUES ( ?, ?, ?, ?, ?, ?); ";

    private static final String READ_QUERY =
                                "SELECT id_versao, email_usuario, id_dataset, data, hora, csv, nome_versao, descricao, id_versao_base " +
                                "FROM featurestore.versao " +
                                "WHERE id_versao = ?;";

    private static final String GET_BY_ID_DATASET_QUERY =
                                "SELECT id_versao, email_usuario, data, hora, csv, nome_versao, descricao, id_versao_base " +
                                "FROM featurestore.versao " +
                                "WHERE id_dataset = ?;";

    private static final String GET_BY_EMAIL_USUARIO_QUERY =
                                "SELECT id_versao, id_dataset, data, hora, csv, nome_versao, descricao, id_versao_base " +
                                "FROM featurestore.versao " +
                                "WHERE email_usuario = ?;";

    private static final String DELETE_QUERY =
                                "DELETE FROM featurestore.versao " +
                                "WHERE id_versao = ?; ";

    private static final String ALL_QUERY =
                                "SELECT id_versao, email_usuario, id_dataset, data, hora, csv, nome_versao, descricao, id_versao_base " +
                                "FROM featurestore.versao; ";

    private static final String INSERT_FEATURE_QUERY = 
                                "INSERT INTO featurestore.versao_possui_feature (id_versao, id_feature) " +
                                "VALUES (?, ?);";
        
    private static final String GET_FEATURES_BY_ID_VERSAO_QUERY = 
                                "SELECT id_feature " +
                                "FROM featurestore.versao_possui_feature " +
                                "WHERE id_versao = ?;";

    private static final String DELETE_FEATURE_FROM_VERSAO_QUERY = 
                                "DELETE FROM featurestore.versao_possui_feature " +
                                "WHERE id_versao = ? AND id_feature = ?;";


    @Override
    public void create(Versao v) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {

            statement.setString(1, v.getEmailUsuario());
            statement.setInt(2, v.getIdDataset());
            statement.setDate(3, v.getData());
            statement.setTime(4, v.getHora());
            statement.setString(5, v.getCSV());
            statement.setString(6, v.getNome());
            statement.setString(7, v.getDescricao());
            statement.setInt(8, v.getIdVersaoBase());

            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PgVersaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("not-null")) {
                throw new SQLException(
                    "Erro ao inserir versão: pelo menos um campo está em branco."
                );
            } else {
                throw new SQLException("Erro ao inserir versão.");
            }
        }
    }

    @Override
    public void insertWithoutVersaoBase(Versao v) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(INSERT_WITHOUT_VERSAO_BASE_QUERY)) {

            statement.setString(1, v.getEmailUsuario());
            statement.setInt(2, v.getIdDataset());
            statement.setDate(3, v.getData());
            statement.setTime(4, v.getHora());
            statement.setString(5, v.getCSV());
            statement.setString(6, v.getNome());
            statement.setString(7, v.getDescricao());

            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PgVersaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao inserir versão.");
        }
    }

    @Override
    public void insertWithoutDescricao(Versao v) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_WITHOUT_DESCRICAO_QUERY)) {

            statement.setString(1, v.getEmailUsuario());
            statement.setInt(2, v.getIdDataset());
            statement.setDate(3, v.getData());
            statement.setTime(4, v.getHora());
            statement.setString(5, v.getCSV());
            statement.setString(6, v.getNome());
            statement.setInt(7, v.getIdVersaoBase());

            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PgVersaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao inserir versão.");
        }
    }

    @Override
    public void insertWithoutDescricaoAndVersaoBase(Versao v) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_WITHOUT_DESCRICAO_AND_VERSAO_BASE_QUERY)) {

            statement.setString(1, v.getEmailUsuario());
            statement.setInt(2, v.getIdDataset());
            statement.setDate(3, v.getData());
            statement.setTime(4, v.getHora());
            statement.setString(5, v.getCSV());
            statement.setString(6, v.getNome());

            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PgVersaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao inserir versão.");
        }
    }

    @Override
    public Versao read(Integer id) throws SQLException {

        Versao v = new Versao();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    v.setId(result.getInt("id_versao"));
                    v.setEmailUsuario(result.getString("email_usuario"));
                    v.setIdDataset(result.getInt("id_dataset"));
                    v.setData(result.getDate("data"));
                    v.setHora(result.getTime("hora"));
                    v.setCSV(result.getString("csv"));
                    v.setNome(result.getString("nome_versao"));
                    v.setDescricao(result.getString("descricao"));
                    v.setIdVersaoBase(result.getInt("id_versao_base"));
                    v.setFeatures(getFeaturesByIdVersao(id));

                } else {
                    throw new SQLException(
                        "Erro ao visualizar: versão não encontrada.");
                }
            }

        } catch (SQLException ex) {

            Logger.getLogger(PgVersaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao visualizar: versão não encontrada.")) {
                throw ex;
            }

            throw new SQLException("Erro ao visualizar versão.");
        }

        return v;
    }

    @Override
    public List<Versao> getByIdDataset(Integer idDataset) throws SQLException {

        List<Versao> versoes = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_BY_ID_DATASET_QUERY)) {
            statement.setInt(1, idDataset);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    Versao v = new Versao();

                    v.setId(result.getInt("id_versao"));
                    v.setEmailUsuario(result.getString("email_usuario"));
                    v.setData(result.getDate("data"));
                    v.setHora(result.getTime("hora"));
                    v.setCSV(result.getString("csv"));
                    v.setNome(result.getString("nome_versao"));
                    v.setDescricao(result.getString("descricao"));
                    v.setIdVersaoBase(result.getInt("id_versao_base"));
                    v.setFeatures(getFeaturesByIdVersao(v.getId()));

                    versoes.add(v);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgVersaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar versões do dataset.");
        }

        return versoes;
    }

    @Override
    public List<Versao> getByEmailUsuario(String emailUsuario) throws SQLException {

        List<Versao> versoes = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_BY_EMAIL_USUARIO_QUERY)) {
            statement.setString(1, emailUsuario);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    Versao v = new Versao();

                    v.setId(result.getInt("id_versao"));
                    v.setIdDataset(result.getInt("id_dataset"));
                    v.setData(result.getDate("data"));
                    v.setHora(result.getTime("hora"));
                    v.setCSV(result.getString("csv"));
                    v.setNome(result.getString("nome_versao"));
                    v.setDescricao(result.getString("descricao"));
                    v.setIdVersaoBase(result.getInt("id_versao_base"));
                    v.setFeatures(getFeaturesByIdVersao(v.getId()));

                    versoes.add(v);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgVersaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar versões do usuário.");
        }

        return versoes;
    }

    @Override
    public void update(Versao t) throws SQLException {
        throw new UnsupportedOperationException("Operação não suportada.");
    }

    @Override
    public void delete(Integer id) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: versão não encontrada.");
            }

        } catch (SQLException ex) {

            Logger.getLogger(PgVersaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: versão não encontrada.")) {
                throw ex;
            }

            throw new SQLException("Erro ao excluir versão.");
        }
    }

    @Override
    public List<Versao> all() throws SQLException {

        List<Versao> vList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY); ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Versao v = new Versao();

                v.setId(result.getInt("id_versao"));
                v.setEmailUsuario(result.getString("email_usuario"));
                v.setIdDataset(result.getInt("id_dataset"));
                v.setData(result.getDate("data"));
                v.setHora(result.getTime("hora"));
                v.setCSV(result.getString("csv"));
                v.setNome(result.getString("nome_versao"));
                v.setDescricao(result.getString("descricao"));
                v.setIdVersaoBase(result.getInt("id_versao_base"));
                v.setFeatures(getFeaturesByIdVersao(v.getId()));

                vList.add(v);
            }

        } catch (SQLException ex) {

            Logger.getLogger(PgVersaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar versões.");
        }

        return vList;
    }  

    @Override
    public void insertFeature(Integer idVersao, Integer idFeature) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(INSERT_FEATURE_QUERY)) {
            statement.setInt(1, idVersao);
            statement.setInt(2, idFeature);

            statement.executeUpdate();

        } catch (SQLException ex) {

            Logger.getLogger(PgVersaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir feature na versão: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir feature na versão.");
            }
        }
    }

    @Override
    public List<Feature> getFeaturesByIdVersao(Integer idVersao) throws SQLException {

        List<Feature> features = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_FEATURES_BY_ID_VERSAO_QUERY)) {
            statement.setInt(1, idVersao);

            try (ResultSet result = statement.executeQuery()) {

                FeatureDAO featureDAO = new PgFeatureDAO(connection);

                while (result.next()) {

                    Integer idFeature = result.getInt("id_feature");
                    Feature feature = featureDAO.read(idFeature);

                    features.add(feature);
                }
            }

        } catch (SQLException ex) {

            Logger.getLogger(PgVersaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao consultar features da versão.");
        }

        return features;
    }

    @Override
    public void deleteFeatureFromVersao(Integer idVersao, Integer idFeature) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(DELETE_FEATURE_FROM_VERSAO_QUERY)) {
            statement.setInt(1, idVersao);
            statement.setInt(2, idFeature);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: feature não encontrada na versão.");
            }

        } catch (SQLException ex) {

            Logger.getLogger(PgVersaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: feature não encontrada na versão.")) {
                throw ex;
            }

            throw new SQLException("Erro ao excluir feature da versão.");
        }
    }

}

