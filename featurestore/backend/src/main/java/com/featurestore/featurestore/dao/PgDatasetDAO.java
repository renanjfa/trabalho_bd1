package com.featurestore.featurestore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.featurestore.featurestore.models.Dataset;

public class PgDatasetDAO implements DatasetDAO {
    
    private final Connection connection;

    public PgDatasetDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String CREATE_QUERY =
                                "INSERT INTO featurestore.data_set (nome_dataset, descricao, data, hora, email_usuario) " +
                                "VALUES (?, ?, ?, ?, ?) " +
                                "RETURNING id;";

    private static final String INSERT_WITHOUT_DESC_QUERY =
                                "INSERT INTO featurestore.data_set (nome_dataset, data, hora, email_usuario) " +
                                "VALUES (?, ?, ?, ?);";

    private static final String READ_QUERY =
                                "SELECT nome_dataset, descricao, data, hora, email_usuario " +
                                "FROM featurestore.data_set " +
                                "WHERE id = ?;";
                            
    private static final String UPDATE_NOME_DESC_USER_QUERY =
                                "UPDATE featurestore.data_set " +
                                "SET nome_dataset = ?, descricao = ?, email_usuario = ? " +
                                "WHERE id = ?;";

    private static final String UPDATE_NOME_DESC_QUERY =
                                "UPDATE featurestore.data_set " +
                                "SET nome_dataset = ?, descricao = ? " +
                                "WHERE id = ?;";

    private static final String DELETE_QUERY =
                                "DELETE FROM featurestore.data_set " +
                                "WHERE id = ?;";

    private static final String ALL_QUERY = 
                                "SELECT id, nome_dataset, descricao, data, hora, email_usuario " +
                                "FROM featurestore.data_set;";

    private static final String INSERT_FONTE_QUERY = 
                                "INSERT INTO featurestore.dataset_possui_fontes (id_dataset, fonte) " +
                                "VALUES (?, ?);";

    private static final String GET_FONTES_QUERY = 
                                "SELECT fonte " +
                                "FROM featurestore.dataset_possui_fontes " +
                                "WHERE id_dataset = ?;";


    
    @Override
    public void create(Dataset data) throws SQLException {

        try (PreparedStatement statement =
                connection.prepareStatement(CREATE_QUERY)) {

            statement.setString(1, data.getNome());
            statement.setString(2, data.getDescricao());
            statement.setDate(3, data.getData());
            statement.setTime(4, data.getHora());
            statement.setString(5, data.getEmailUsuario());

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    data.setId(result.getInt("id"));
                } else {
                    throw new SQLException(
                        "Erro ao inserir dataset: ID não retornado."
                    );
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgDatasetDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("not-null")) {
                throw new SQLException(
                    "Erro ao inserir DATASET: pelo menos um campo está em branco."
                );
            } else {
                throw new SQLException("Erro ao inserir dataset.");
            }
        }
    }

    @Override
    public void insertWithoutDescricao(Dataset data) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(INSERT_WITHOUT_DESC_QUERY)) {
            statement.setString(1, data.getNome());
            statement.setDate(2, data.getData());
            statement.setTime(3, data.getHora());
            statement.setString(4, data.getEmailUsuario());

            statement.executeUpdate();

        } catch (SQLException ex)  {
            Logger.getLogger(PgDatasetDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir DATASET: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir dataset.");
            }
        }
    }

    @Override
    public Dataset read(Integer id) throws SQLException {
        Dataset data = new Dataset();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    data.setId(id);
                    data.setNome(result.getString("nome_dataset"));
                    data.setDescricao(result.getString("descricao"));
                    data.setData(result.getDate("data"));
                    data.setHora(result.getTime("hora"));
                    data.setEmailUsuario(result.getString("email_usuario"));
                    data.setFontes(getFontesDataset(id));

                } else {
                    throw new SQLException("Erro ao visualizar: dataset não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgDatasetDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            if (ex.getMessage().equals("Erro ao visualizar: dataset não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar dataset.");
            }
        }

        return data;
    }

    /*
        ATENCAO: somente atualiza nome_dataset, descricao e email_usuario
    */
    @Override
    public void update(Dataset data) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_NOME_DESC_USER_QUERY)) {
            statement.setString(1, data.getNome());
            statement.setString(2, data.getDescricao());
            statement.setString(3, data.getEmailUsuario());
            statement.setInt(4, data.getId());

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: dataset não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgDatasetDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: dataset não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar dataset: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar dataset.");
            }
        }
    }

    /*
        ATENCAO: somente atualiza nome_dataset e descricao
    */
    @Override
    public void updateOnlyNomeAndDescricao(Dataset data) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(UPDATE_NOME_DESC_QUERY)) {
            statement.setString(1, data.getNome());
            statement.setString(2, data.getDescricao());
            statement.setInt(3, data.getId());

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: dataset não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgDatasetDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: dataset não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar dataset: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar dataset.");
            }
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: dataset não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgDatasetDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: dataset não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir dataset.");
            }
        }
    }


    @Override
    public List<Dataset> all() throws SQLException {
        List<Dataset> datasetList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
            ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Dataset data = new Dataset();

                data.setId(result.getInt("id"));
                data.setNome(result.getString("nome_dataset"));
                data.setDescricao(result.getString("descricao"));
                data.setData(result.getDate("data"));
                data.setHora(result.getTime("hora"));
                data.setEmailUsuario(result.getString("email_usuario"));
                data.setFontes(getFontesDataset(data.getId()));

                datasetList.add(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgDatasetDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar datasets.");
        }

        return datasetList;
    }

    @Override
    public void insertFonte(Integer idDataset, String fonte) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(INSERT_FONTE_QUERY)) {
            statement.setInt(1, idDataset);
            statement.setString(2, fonte);
            
            statement.executeUpdate();

        } catch (SQLException ex)  {
            Logger.getLogger(PgDatasetDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir FONTE em DATASET: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir fonte.");
            }
        }
    }

    @Override
    public List<String> getFontesDataset(Integer id) throws SQLException {
        List<String> fontes = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_FONTES_QUERY)) {
            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    fontes.add(result.getString("fonte"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgDatasetDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao consultar fontes do dataset.");
        }

        return fontes;
    }
}
