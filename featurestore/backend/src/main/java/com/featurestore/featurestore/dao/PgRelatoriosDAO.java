package com.featurestore.featurestore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.featurestore.featurestore.dto.*;


public class PgRelatoriosDAO implements RelatoriosDAO {
    
    private final Connection connection;

    public PgRelatoriosDAO(Connection connection) {
        this.connection = connection;
    }

    // SistemaDTO
    private static final String SISTEMA_QUERY =
                                "SELECT " +
                                "   (SELECT COUNT(*) FROM featurestore.data_set) AS qnt_datasets, " +
                                "   (SELECT COUNT(*) FROM featurestore.versao) AS qnt_versoes, " +
                                "   (SELECT COUNT(*) FROM featurestore.usuario) AS qnt_usuarios; ";

    // QntVersoesDatasetDTO
    private static final String QUANTIDADE_VERSOES_CADA_DATASET_QUERY =
                                "SELECT d.nome_dataset, u.nome_usuario, COUNT(v.id_versao) AS quantidade_versoes " +
                                "FROM featurestore.data_set d " +
                                "   JOIN featurestore.usuario u ON u.email = d.email_usuario " +
                                "   LEFT JOIN featurestore.versao v ON v.id_dataset = d.id " +
                                "   GROUP BY d.nome_dataset, u.nome_usuario " +
                                "   ORDER BY quantidade_versoes DESC;";
              
    // QntAcessoDownloadDatasetDTO
    private static final String QUANTIDADE_ACESSOS_DOWNLOADS_DATASET_QUERY =
                                "SELECT " +
                                "   ( " +
                                "       SELECT COUNT(*) " +
                                "       FROM featurestore.usuario_acessa_dataset " +
                                "       WHERE id_dataset = ? " +
                                "   ) AS quantidade_acessos, " +
                                "   ( " +
                                "       SELECT COUNT(*) " +
                                "       FROM featurestore.usuario_faz_download_versao ufdv " +
                                "       JOIN featurestore.versao v " +
                                "           ON v.id_versao = ufdv.id_versao " +
                                "       WHERE v.id_dataset = ? " +
                                "   ) AS quantidade_downloads; ";
                                
    // DatasetMaisDTO
    private static final String DATASETS_MAIS_ORDER_ACESSOS_QUERY = 
                                "SELECT d.nome_dataset, u.nome_usuario, " +
                                "   COALESCE(a.qnt_acessos, 0) AS qnt_acessos, " +
                                "   COALESCE(dl.qnt_downloads, 0) AS qnt_downloads " +
                                "FROM featurestore.data_set d " +
                                "LEFT JOIN featurestore.usuario u ON u.email = d.email_usuario " +
                                "LEFT JOIN ( " +
                                "   SELECT id_dataset, COUNT(*) AS qnt_acessos " +
                                "   FROM featurestore.usuario_acessa_dataset " +
                                "   GROUP BY id_dataset " +
                                ") a ON a.id_dataset = d.id " +
                                "LEFT JOIN ( " +
                                "   SELECT v.id_dataset, COUNT(*) AS qnt_downloads " +
                                "   FROM featurestore.usuario_faz_download_versao ufdv " +
                                "   JOIN featurestore.versao v " +
                                "       ON v.id_versao = ufdv.id_versao " +
                                "   GROUP BY v.id_dataset " +
                                ") dl ON dl.id_dataset = d.id " +
                                "ORDER BY qnt_acessos DESC;";

    // DatasetMaisDTO
    private static final String DATASETS_MAIS_ORDER_DOWNLOADS_QUERY =
                                "SELECT d.nome_dataset, u.nome_usuario, " +
                                "   COALESCE(a.qnt_acessos, 0) AS qnt_acessos, " +
                                "   COALESCE(dl.qnt_downloads, 0) AS qnt_downloads " +
                                "FROM featurestore.data_set d " +
                                "LEFT JOIN featurestore.usuario u ON u.email = d.email_usuario " +
                                "LEFT JOIN ( " +
                                "   SELECT id_dataset, COUNT(*) AS qnt_acessos " +
                                "   FROM featurestore.usuario_acessa_dataset " +
                                "   GROUP BY id_dataset " +
                                ") a ON a.id_dataset = d.id " +
                                "LEFT JOIN ( " +
                                "   SELECT v.id_dataset, COUNT(*) AS qnt_downloads " +
                                "   FROM featurestore.usuario_faz_download_versao ufdv " +
                                "   JOIN featurestore.versao v " +
                                "       ON v.id_versao = ufdv.id_versao " +
                                "   GROUP BY v.id_dataset " +
                                ") dl ON dl.id_dataset = d.id " +
                                "ORDER BY qnt_downloads DESC;";

    // ContribuicaoDTO
    private static final String CONTRIBUICAO_QUERY =
                                "WITH " +
                                "total_sistema AS ( " +
                                "   SELECT " +
                                "       (SELECT COUNT(*) FROM featurestore.data_set) + " +
                                "       (SELECT COUNT(*) FROM featurestore.versao) AS total " +
                                "), " +
                                "contribuicao_usuario AS ( " +
                                "   SELECT u.email, u.nome_usuario, " +
                                "       COUNT(DISTINCT d.id) AS datasets_criados, " +
                                "       COUNT(DISTINCT v.id_versao) AS versoes_criadas " +
                                "   FROM featurestore.usuario u " +
                                "       LEFT JOIN featurestore.data_set d ON d.email_usuario = u.email " +
                                "       LEFT JOIN featurestore.versao v ON v.email_usuario = u.email " +
                                "       GROUP BY u.email, u.nome_usuario " +
                                ") " +
                                "SELECT c.nome_usuario, c.datasets_criados, c.versoes_criadas, " +
                                "   (c.datasets_criados + c.versoes_criadas) AS total_contribuicoes, " +
                                "   ROUND( " +
                                "       (c.datasets_criados + c.versoes_criadas) * 100.0 / t.total, 2 " +
                                "   ) AS contribuicao_percentual " +
                                "   FROM contribuicao_usuario c " +
                                "   CROSS JOIN total_sistema t " +
                                "   ORDER BY contribuicao_percentual DESC;";
    
    // HistoricoDatasetsDTO
    private static final String HISTORICO_ACESSOS_DOWNLOADS_DATASET_QUERY =
                                "SELECT dia, SUM(acessos) AS acessos, SUM(downloads) AS downloads " +
                                "FROM ( " +
                                "   SELECT " +
                                "       DATE(data) AS dia, " +
                                "       COUNT(*) AS acessos, " +
                                "       0 AS downloads " +
                                "   FROM featurestore.usuario_acessa_dataset " +
                                "   WHERE id_dataset = ? " +
                                "   GROUP BY DATE(data) " +
                                " " +
                                "   UNION ALL " +
                                " " +
                                "   SELECT " +
                                "       DATE(ufdv.data) AS dia, " +
                                "       0 AS acessos, " +
                                "       COUNT(*) AS downloads " +
                                "   FROM featurestore.usuario_faz_download_versao ufdv " +
                                "   JOIN featurestore.versao v ON v.id_versao = ufdv.id_versao " +
                                "   WHERE v.id_dataset = ? " +
                                "   GROUP BY DATE(ufdv.data) " +
                                ") t " +
                                "GROUP BY dia " +
                                "ORDER BY dia;";


    @Override
    public SistemaDTO getInfoSistema() throws SQLException {

        SistemaDTO sys = new SistemaDTO();

        try (PreparedStatement statement = connection.prepareStatement(SISTEMA_QUERY)) {

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    sys.setQntDatasets(result.getInt("qnt_datasets"));
                    sys.setQntVersoes(result.getInt("qnt_versoes"));
                    sys.setQntUsuarios(result.getInt("qnt_usuarios"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgRelatoriosDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao retornar informacoes relativas ao sistema.");
        }

        return sys;
    }

    @Override
    public List<QntVersoesDatasetDTO> getQuantidadeVersoesDatasets() throws SQLException {
        List<QntVersoesDatasetDTO> l = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(QUANTIDADE_VERSOES_CADA_DATASET_QUERY)) {

            try (ResultSet result = statement.executeQuery()) {

                while(result.next()) {
                    QntVersoesDatasetDTO q = new QntVersoesDatasetDTO();
                    q.setNomeDataset(result.getString("nome_dataset"));
                    q.setNomeUsuario(result.getString("nome_usuario"));
                    q.setQntVersoes(result.getInt("quantidade_versoes"));

                    l.add(q);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(PgRelatoriosDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao retornar quantidade de versoes dos datasets.");
        }

        return l;
    }

    @Override
    public QntAcessoDownloadDatasetDTO getAcessosDownloadsDataset(Integer id_dataset) throws SQLException {
        QntAcessoDownloadDatasetDTO q = new QntAcessoDownloadDatasetDTO();

        try (PreparedStatement statement = connection.prepareStatement(QUANTIDADE_ACESSOS_DOWNLOADS_DATASET_QUERY)) {

            statement.setInt(1, id_dataset);
            statement.setInt(2, id_dataset);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    q.setQntAcessos(result.getInt("quantidade_acessos"));
                    q.setQntDownloads(result.getInt("quantidade_downloads"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgRelatoriosDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao retornar acessos e downloads de um dataset especifico.");
        }

        return q;
    }

    @Override
    public List<DatasetMaisDTO> getDatasetsMais(String order_by) throws SQLException {
        List<DatasetMaisDTO> l = new ArrayList<>();

        String query;

        if("acessos".equals(order_by)) {
            query = DATASETS_MAIS_ORDER_ACESSOS_QUERY;
        }
        else if("downloads".equals(order_by)) {
            query = DATASETS_MAIS_ORDER_DOWNLOADS_QUERY;
        }
        else {
            throw new SQLException("Erro em comando order_by.");
        }
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            
            try (ResultSet result = statement.executeQuery()) {
                
                while(result.next()) {
                    DatasetMaisDTO d = new DatasetMaisDTO();
                    d.setNomeDataset(result.getString("nome_dataset"));
                    d.setNomeUsuario(result.getString("nome_usuario"));
                    d.setQntAcessos(result.getInt("qnt_acessos"));
                    d.setQntDownloads(result.getInt("qnt_downloads"));

                    l.add(d);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(PgRelatoriosDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao retornar datasets mais visualizados.");
        }

        return l;
    }

    @Override
    public List<ContribuicaoDTO> getContribuicaoUsuarios() throws SQLException {
        List<ContribuicaoDTO> l = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(CONTRIBUICAO_QUERY)) {

            try (ResultSet result = statement.executeQuery()) {

                
                while(result.next()) {
                    ContribuicaoDTO c = new ContribuicaoDTO();
                    c.setNomeUsuario(result.getString("nome_usuario"));
                    c.setDatasetsCriados(result.getInt("datasets_criados"));
                    c.setVersoesCriadas(result.getInt("versoes_criadas"));
                    c.setTotalContribuicoes(result.getInt("total_contribuicoes"));
                    c.setContribuicaoPercentual(result.getFloat("contribuicao_percentual"));

                    l.add(c);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(PgRelatoriosDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao retornar contribuicoes dos usuarios.");
        }

        return l;
    }

    @Override
    public List<HistoricoDatasetDTO> getHistoricoAcessosDownloads(Integer id_dataset) throws SQLException {
        List<HistoricoDatasetDTO> l = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(HISTORICO_ACESSOS_DOWNLOADS_DATASET_QUERY)) {

            statement.setInt(1, id_dataset);
            statement.setInt(2, id_dataset);

            try (ResultSet result = statement.executeQuery()) {

                while(result.next()) {
                    HistoricoDatasetDTO h = new HistoricoDatasetDTO();
                    h.setDia(result.getDate("dia"));
                    h.setQntAcessos(result.getInt("acessos"));
                    h.setQntDownloads(result.getInt("downloads"));
                    
                    l.add(h);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(PgRelatoriosDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao retornar historico de acessos e downloads de um dataset.");
        }

        return l;
    }
    


                                
    @Override                       
    public void create(SistemaDTO t) throws SQLException {
        throw new UnsupportedOperationException("Operação não suportada.");
    }

    @Override
    public SistemaDTO read(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Operação não suportada.");
    }

    @Override
    public void update(SistemaDTO t) throws SQLException {
        throw new UnsupportedOperationException("Operação não suportada.");
    }

    @Override
    public void delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Operação não suportada.");
    }

    @Override
    public List<SistemaDTO> all() throws SQLException {
        throw new UnsupportedOperationException("Operação não suportada.");
    }   
}
