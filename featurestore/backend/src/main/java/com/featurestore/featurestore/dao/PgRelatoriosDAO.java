package com.featurestore.featurestore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

import com.featurestore.featurestore.models.*;

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
    private static final String QUANTIDADE_ACESSOS_DOWNLAODS_DATASET_QUERY =
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
    
    // HistoricoDatasetDTO
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


                                
                                
    public void create(Usuario t) throws SQLException {

    }

    public Usuario read(Integer id) throws SQLException {
        Usuario u = new Usuario();
        return u;
    }

    public void update(Usuario t) throws SQLException {

    }

    public void delete(Integer id) throws SQLException {

    }

    public List<Usuario> all() throws SQLException {
        List<Usuario> u = new ArrayList<>();
        return u;
    }   
}
