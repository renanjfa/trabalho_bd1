package com.featurestore.featurestore.dao;

import java.sql.SQLException;
import java.util.List;

import com.featurestore.featurestore.dto.*;

public interface RelatoriosDAO extends DAO<SistemaDTO> {

    public SistemaDTO getInfoSistema() throws SQLException;

    public List<QntVersoesDatasetDTO> getQuantidadeVersoesDatasets() throws SQLException;

    public QntAcessoDownloadDatasetDTO getAcessosDownloadsDataset(Integer id_dataset) throws SQLException;

    public List<DatasetMaisDTO> getDatasetsMais(String order_by) throws SQLException;

    public List<ContribuicaoDTO> getContribuicaoUsuarios() throws SQLException;

    public List<HistoricoDatasetDTO> getHistoricoAcessosDownloads(Integer id_dataset) throws SQLException;
}
