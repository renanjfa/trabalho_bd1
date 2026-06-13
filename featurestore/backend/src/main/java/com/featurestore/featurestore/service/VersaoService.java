package com.featurestore.featurestore.service;

import com.featurestore.featurestore.dao.DAOFactory;
import com.featurestore.featurestore.dao.VersaoDAO;
import com.featurestore.featurestore.dao.FeatureDAO;
import com.featurestore.featurestore.models.Versao;
import com.featurestore.featurestore.models.Feature;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class VersaoService {
    public void criarComFeatures(String emailUsuario, Integer idDataset, String nome, String descricao, String csv, Integer idVersao, List<Feature>features) throws Exception{
        if (nome == null || nome.isBlank()) {
            throw new Exception("Nome da versão é obrigatório.");
        }
        Versao versao = new Versao();
        versao.setEmailUsuario(emailUsuario);
        versao.setIdDataset(idDataset);
        versao.setNome(nome);
        versao.setDescricao(descricao);
        versao.setCSV(csv);
        versao.setData(new Date(System.currentTimeMillis()));
        versao.setHora(new Time(System.currentTimeMillis()));

        try(DAOFactory daoFactory = DAOFactory.getInstance()){
            VersaoDAO versaoDAO = daoFactory.getVersaoDAO();
            FeatureDAO featureDAO = daoFactory.getFeatureDAO();

            daoFactory.beginTransaction();
            //selecionar o insert dependendo dos campos opcionais
            if(idVersao !=null){
                versao.setIdVersaoBase(idVersao);
                if(descricao != null && !descricao.isBlank()){
                    versaoDAO.create(versao);
                }else{
                    versaoDAO.insertWithoutDescricao(versao);
                }
            }else{
                if (descricao != null && !descricao.isBlank()) {
                    versaoDAO.insertWithoutDescricaoAndVersaoBase(versao);
                }
            }

            //criar cada feature e relacionar com a versao
            if(features != null){
                for(Feature feature: features){
                    featureDAO.create(feature);
                    versaoDAO.insertFeature(versao.getId(),feature.getId());
                }
            }
            daoFactory.commitTransaction();
            daoFactory.endTransaction();
        }
    }
    public List<Versao> listarPorDataset(Integer idDataset) throws Exception{
        try(DAOFactory daoFactory = DAOFactory.getInstance()){
            VersaoDAO dao = daoFactory.getVersaoDAO();
            return dao.getByIdDataset(idDataset);
        }
    }
    public List<Versao> listarPorUsuario(String emailUsuario) throws Exception{
        try(DAOFactory daoFactory = DAOFactory.getInstance()){
            VersaoDAO dao = daoFactory.getVersaoDAO();
            return dao.getByEmailUsuario(emailUsuario);
        }
    }
    public Versao buscaPorId(Integer id)throws Exception{
        try(DAOFactory daoFactory = DAOFactory.getInstance()){
            VersaoDAO dao = daoFactory.getVersaoDAO();
            return dao.read(id);
        }
    }
    public void deletar(Integer id)throws Exception{
        try(DAOFactory daoFactory = DAOFactory.getInstance()){
            VersaoDAO dao = daoFactory.getVersaoDAO();
            dao.delete(id);
        }
    }
}
