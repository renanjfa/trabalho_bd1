package com.featurestore.featurestore.service;
import com.featurestore.featurestore.dao.DAOFactory;
import com.featurestore.featurestore.dao.RelatoriosDAO;
import com.featurestore.featurestore.dto.*;

import java.util.List;


public class RelatoriosService {
    public SistemaDTO getInfoSistema() throws Exception{
        try (DAOFactory daoFactory = DAOFactory.getInstance()){
            RelatoriosDAO dao = daoFactory.getRelatoriosDAO();
            return dao.getInfoSistema();
        }
    }

    public List<QntVersoesDatasetDTO> getQuantidadeVersoesDatasets() throws Exception {
        try(DAOFactory daoFactory = DAOFactory.getInstance()){
            RelatoriosDAO dao = daoFactory.getRelatoriosDAO();
            return dao.getQuantidadeVersoesDatasets();
        }
    }

    public QntAcessoDownloadDatasetDTO gAcessoDownloadDatasetDTO(Integer idDataSet) throws Exception {
        try (DAOFactory daoFactory = DAOFactory.getInstance()){
            RelatoriosDAO dao = daoFactory.getRelatoriosDAO();
            return dao.getAcessosDownloadsDataset(idDataSet);
        }
    }

    public List<DatasetMaisDTO> getDatasetsMais(String orderBy) throws Exception {
        if (orderBy == null || orderBy.isBlank()){
            orderBy = "acessos";
        }
        try (DAOFactory daoFactory = DAOFactory.getInstance()){
            RelatoriosDAO dao = daoFactory.getRelatoriosDAO();
            return dao.getDatasetsMais(orderBy);
        }
    }

    public List<ContribuicaoDTO> getContribuicaoUsuarios() throws Exception {
        try (DAOFactory daoFactory = DAOFactory.getInstance()){
            RelatoriosDAO dao = daoFactory.getRelatoriosDAO();
            return dao.getContribuicaoUsuarios();
        }
    }

    public List<HistoricoDatasetDTO> getHistoricoAcessosDownloads(Integer idDataset)throws Exception{
        try (DAOFactory daoFactory = DAOFactory.getInstance()){
            RelatoriosDAO dao = daoFactory.getRelatoriosDAO();
            return dao.getHistoricoAcessosDownloads(idDataset);
        }
    }


}
