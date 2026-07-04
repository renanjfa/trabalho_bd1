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

    private static final String QUANTIDADE_DATASETS_SISTEMA_QUERY =
                                "SELECT COUNT(*) FROM featurestore.data_set; ";

    private static final String QUANTIDADE_VERSOES_SISTEMA_QUERY =
                                "SELECT COUNT(*) FROM featurestore.versao; ";

    private static final String QUANTIDADE_USUARIOS_SISTEMA_QUERY =
                                "SELECT COUNT(*) FROM featurestore.usuario; ";

    private static final String QUANTIDADE_VERSOES_CADA_DATASET_QUERY =
                                "SELECT d.nome_dataset, u.nome_usuario, COUNT(v.id_versao) AS quantidade_versoes " +
                                "FROM featurestore.data_set d " +
                                "   JOIN featurestore.usuario u ON u.email = d.email_usuario " +
                                "   LEFT JOIN featurestore.versao v ON v.id_dataset = d.id " +
                                "   GROUP BY d.nome_dataset, u.nome_usuario " +
                                "   ORDER BY quantidade_versoes DESC;";
                         
    private static final String QUANTIDADE_ACESSOS_DATASET_QUERY =
                                "SELECT COUNT(*) FROM featurestore.usuario_acessa_dataset " +
                                "WHERE id_dataset = ?; ";
                                
    private static final String QUANTIDADE_DOWNLOADS_VERSAO_QUERY =
                                "SELECT COUNT(*) FROM featurestore.usuario_faz_download_versao " +
                                "WHERE id_versao = ?; ";

    private static final String QUANTIDADE_DOWNLOADS_DATASET_QUERY = 
                                "SELECT COUNT(*) AS qnt_downloads " +
                                "FROM featurestore.usuario_faz_download_versao ufdv " +
                                "JOIN featurestore.versao v " +
                                "   ON ufdv.id_versao = v.id_versao " +
                                "WHERE v.id_dataset = ?; ";

    private static final String DATASETS_MAIS_VISUALIZADOS_QUERY = 
                                "SELECT d.nome_dataset, u.nome_usuario, " +
                                "   COUNT(uad.id_dataset) AS qnt_acessos " +
                                "FROM featurestore.data_set d " +
                                "LEFT JOIN featurestore.usuario_acessa_dataset uad ON uad.id_dataset=d.id " +
                                "LEFT JOIN featurestore.usuario u ON u.email=d.email_usuario " +
                                "GROUP BY d.id, d.nome_dataset, u.nome_usuario " +
                                "ORDER BY qnt_acessos DESC;";

    private static final String DATASETS_MAIS_BAIXADOS_QUERY =
                                "SELECT d.nome_dataset, u.nome_usuario, " +
                                "   COUNT(ufdv.id_versao) AS qnt_downloads " +
                                "FROM featurestore.data_set d " +
                                "   LEFT JOIN featurestore.versao v ON v.id_dataset = d.id " +
                                "   LEFT JOIN featurestore.usuario_faz_download_versao ufdv ON ufdv.id_versao = v.id_versao " +
                                "   LEFT JOIN featurestore.usuario u ON u.email = d.email_usuario " +
                                "   GROUP BY d.id, d.nome_dataset, u.nome_usuario " +
                                "   ORDER BY qnt_downloads DESC;";

    private static final String CONTRIBUICAO_USUARIO_QUERY =
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
                                
    private static final String DATA_ACESSOS_E_DOWNLOADS_DATASET_QUERY =
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
