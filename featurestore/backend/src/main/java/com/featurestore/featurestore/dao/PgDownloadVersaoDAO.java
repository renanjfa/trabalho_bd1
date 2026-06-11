package com.featurestore.featurestore.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.featurestore.featurestore.models.DownloadVersao;

public class PgDownloadVersaoDAO implements DownloadVersaoDAO {
    
    private final Connection connection;

    public PgDownloadVersaoDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_QUERY =
                                "INSERT INTO featurestore.usuario_faz_download_versao (email_usuario, id_versao, data, hora) " +
                                "VALUES (?, ?, ?, ?);";

    private static final String GET_BY_ID_VERSAO_QUERY =
                                "SELECT * FROM featurestore.usuario_faz_download_versao " +
                                "WHERE id_versao = ?;";

    private static final String GET_BY_EMAIL_USER_QUERY =
                                "SELECT * FROM featurestore.usuario_faz_download_versao " +
                                "WHERE email_usuario = ?;";

    private static final String ALL_QUERY =
                                "SELECT * FROM eaturestore.usuario_faz_download_versao; ";



    @Override
    public void create(DownloadVersao dv) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {

            statement.setString(1, dv.getEmailUsuario());
            statement.setInt(2, dv.getIdVersao());
            statement.setDate(3, dv.getData());
            statement.setTime(4, dv.getHora());

            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PgDownloadVersaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("not-null")) {
                throw new SQLException(
                    "Erro ao inserir download de versao: pelo menos um campo está em branco."
                );
            } else {
                throw new SQLException(
                    "Erro ao inserir download de versao."
                );
            }
        }
    }

    @Override
    public List<DownloadVersao> all() throws SQLException {
        List<DownloadVersao> dvList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY); ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                DownloadVersao dv = new DownloadVersao();

                dv.setEmailUsuario(result.getString("email_usuario"));
                dv.setIdVersao(result.getInt("id_versao"));
                dv.setData(result.getDate("data"));
                dv.setHora(result.getTime("hora"));

                dvList.add(dv);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgDownloadVersaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException(
                "Erro ao listar downloads de versao."
            );
        }

        return dvList;
    }


    @Override
    public List<DownloadVersao> getByIdVersao(Integer id) throws SQLException {

        List<DownloadVersao> dvList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_BY_ID_VERSAO_QUERY)) {
            statement.setInt(1, id);

            try (ResultSet result =
                    statement.executeQuery()) {

                while (result.next()) {
                    DownloadVersao dv = new DownloadVersao();

                    dv.setEmailUsuario(result.getString("email_usuario"));
                    dv.setIdVersao(result.getInt("id_versao"));
                    dv.setData(result.getDate("data"));
                    dv.setHora(result.getTime("hora"));

                    dvList.add(dv);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgDownloadVersaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException(
                "Erro ao consultar acessos do dataset."
            );
        }

        return dvList;
    }

    @Override
    public List<DownloadVersao> getByEmailUsuario(String email) throws SQLException {

        List<DownloadVersao> dvList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement( GET_BY_EMAIL_USER_QUERY )) {

            statement.setString(1, email);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    DownloadVersao dv = new DownloadVersao();

                    dv.setEmailUsuario(result.getString("email_usuario"));
                    dv.setIdVersao(result.getInt("id_versao"));
                    dv.setData(result.getDate("data"));
                    dv.setHora(result.getTime("hora"));

                    dvList.add(dv);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgDownloadVersaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException(
                "Erro ao consultar acessos do usuário."
            );
        }

        return dvList;
    }

    @Override
    public DownloadVersao read(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Operação não suportada.");
    }

    @Override
    public void update(DownloadVersao t) throws SQLException {
        throw new UnsupportedOperationException("Operação não suportada.");
    }

    @Override
    public void delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Operação não suportada.");
    }
    
}


