package com.featurestore.featurestore.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.featurestore.featurestore.models.AcessoDataset;

public class PgAcessoDatasetDAO implements AcessoDatasetDAO {
    
    private final Connection connection;

    public PgAcessoDatasetDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_QUERY =
                                "INSERT INTO featurestore.usuario_acessa_dataset (email_usuario, id_dataset, data, hora) " +
                                "VALUES (?, ?, ?, ?);";

    private static final String GET_BY_ID_DATASET_QUERY =
                                "SELECT * FROM featurestore.usuario_acessa_dataset " +
                                "WHERE id_dataset = ?;";

    private static final String GET_BY_EMAIL_USER_QUERY =
                                "SELECT * FROM featurestore.usuario_acessa_dataset " +
                                "WHERE email_usuario = ?;";

    private static final String GET_RECENTE_QUERY =
                                "SELECT * FROM featurestore.usuario_acessa_dataset " +
                                "WHERE email_usuario = ? AND id_dataset = ? " +
                                "AND (data + hora) > (NOW() - CAST(? || ' seconds' AS INTERVAL));";

    private static final String ALL_QUERY =
                                "SELECT * FROM featurestore.usuario_acessa_dataset; ";

    @Override
    public void create(AcessoDataset ad) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {

            statement.setString(1, ad.getEmailUsuario());
            statement.setInt(2, ad.getIdDataset());
            statement.setDate(3, ad.getData());
            statement.setTime(4, ad.getHora());

            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PgAcessoDatasetDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("not-null")) {
                throw new SQLException(
                    "Erro ao inserir acesso ao dataset: pelo menos um campo está em branco."
                );
            } else {
                throw new SQLException(
                    "Erro ao inserir acesso ao dataset."
                );
            }
        }
    }

    @Override
    public AcessoDataset read(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Operação não suportada.");
    }

    @Override
    public void update(AcessoDataset t) throws SQLException {
        throw new UnsupportedOperationException("Operação não suportada.");
    }

    @Override
    public void delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Operação não suportada.");
    }

    @Override
    public List<AcessoDataset> all() throws SQLException {
        List<AcessoDataset> adList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY); ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                AcessoDataset ad = new AcessoDataset();

                ad.setEmailUsuario(result.getString("email_usuario"));
                ad.setIdDataset(result.getInt("id_dataset"));
                ad.setData(result.getDate("data"));
                ad.setHora(result.getTime("hora"));

                adList.add(ad);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgAcessoDatasetDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException(
                "Erro ao listar acessos aos datasets."
            );
        }

        return adList;
    }

    @Override
    public List<AcessoDataset> getByIdDataset(Integer id) throws SQLException {

        List<AcessoDataset> adList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_BY_ID_DATASET_QUERY)) {
            statement.setInt(1, id);

            try (ResultSet result =
                    statement.executeQuery()) {

                while (result.next()) {
                    AcessoDataset ad = new AcessoDataset();

                    ad.setEmailUsuario(result.getString("email_usuario"));
                    ad.setIdDataset(result.getInt("id_dataset"));
                    ad.setData(result.getDate("data"));
                    ad.setHora(result.getTime("hora"));

                    adList.add(ad);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgAcessoDatasetDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException(
                "Erro ao consultar acessos do dataset."
            );
        }

        return adList;
    }

    @Override
    public List<AcessoDataset> getByEmailUsuario(String email) throws SQLException {

        List<AcessoDataset> adList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement( GET_BY_EMAIL_USER_QUERY )) {

            statement.setString(1, email);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    AcessoDataset ad = new AcessoDataset();

                    ad.setEmailUsuario(result.getString("email_usuario"));
                    ad.setIdDataset(result.getInt("id_dataset"));
                    ad.setData(result.getDate("data"));
                    ad.setHora(result.getTime("hora"));

                    adList.add(ad);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgAcessoDatasetDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException(
                "Erro ao consultar acessos do usuário."
            );
        }

        return adList;
    }

    @Override
    public List<AcessoDataset> getByEmailAndDatasetRecente(String email, Integer idDataset, int segundos) throws SQLException {
        List<AcessoDataset> adList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_RECENTE_QUERY)) {
            statement.setString(1, email);
            statement.setInt(2, idDataset);
            statement.setString(3, String.valueOf(segundos));

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    AcessoDataset ad = new AcessoDataset();
                    ad.setEmailUsuario(result.getString("email_usuario"));
                    ad.setIdDataset(result.getInt("id_dataset"));
                    ad.setData(result.getDate("data"));
                    ad.setHora(result.getTime("hora"));
                    adList.add(ad);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgAcessoDatasetDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new SQLException("Erro ao verificar acesso recente.");
        }

        return adList;
    }

}

