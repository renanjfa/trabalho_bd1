package com.featurestore.featurestore.service;

import com.featurestore.featurestore.dao.DAOFactory;
import com.featurestore.featurestore.dao.AcessoDatasetDAO;
import com.featurestore.featurestore.models.AcessoDataset;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class AcessoDatasetService {

    public void registrarAcesso(String emailUsuario, Integer idDataset) throws Exception {
        if (emailUsuario == null || emailUsuario.isBlank()) {
            throw new Exception("Email do usuario é obrigatório.");
        }
        if (idDataset == null) {
            throw new Exception("ID do dataset é obrigatório.");
        }

        AcessoDataset acesso = new AcessoDataset();
        acesso.setEmailUsuario(emailUsuario);
        acesso.setIdDataset(idDataset);
        acesso.setData(new Date(System.currentTimeMillis()));
        acesso.setHora(new Time(System.currentTimeMillis()));

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            AcessoDatasetDAO dao = daoFactory.getAcessoDatasetDAO();
            dao.create(acesso);
        }
    }

    public List<AcessoDataset> listarPorDataset(Integer idDataset) throws Exception {
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            AcessoDatasetDAO dao = daoFactory.getAcessoDatasetDAO();
            return dao.getByIdDataset(idDataset);
        }
    }

    public List<AcessoDataset> listarPorUsuario(String email) throws Exception {
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            AcessoDatasetDAO dao = daoFactory.getAcessoDatasetDAO();
            return dao.getByEmailUsuario(email);
        }
    }
}