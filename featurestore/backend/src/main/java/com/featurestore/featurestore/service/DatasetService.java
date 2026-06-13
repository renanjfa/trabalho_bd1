package com.featurestore.featurestore.service;

import com.featurestore.featurestore.dao.DAOFactory;
import com.featurestore.featurestore.dao.DatasetDAO;
import com.featurestore.featurestore.models.Dataset;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class DatasetService {
    public Dataset criar(String nome, String descricao, List<String> fontes, String emailUsuario) throws Exception{
        if (nome == null || nome.isBlank()) {
            throw new Exception("Nome do dataset é obrigatorio");
        }
        Dataset dataset = new Dataset();
        dataset.setNome(nome);
        dataset.setDescricao(descricao);
        dataset.setEmailUsuario(emailUsuario);
        dataset.setData(new Date(System.currentTimeMillis()));
        dataset.setHora(new Time(System.currentTimeMillis()));

        try(DAOFactory daoFactory = DAOFactory.getInstance()){
            DatasetDAO dao = daoFactory.getDatasetDAO();
            daoFactory.beginTransaction();
            dao.create(dataset);
            if(fontes != null){
                for(String fonte: fontes){
                    dao.insertFonte(dataset.getId(),fonte);
                }
            }
            daoFactory.commitTransaction();
            daoFactory.endTransaction();
        }
        dataset.setFontes(fontes);
        return dataset;
    }
    public List<Dataset> listarTodos()throws Exception{
        try(DAOFactory daoFactory = DAOFactory.getInstance()){
            DatasetDAO dao = daoFactory.getDatasetDAO();
            return dao.all();
        }
    }
    public Dataset buscarPorId(Integer id) throws Exception{
        try(DAOFactory daoFactory = DAOFactory.getInstance()){
            DatasetDAO dao = daoFactory.getDatasetDAO();
            return dao.read(id);
        }
    }
    public void atualizar(Integer id, String nome, String descricao)throws Exception{
        Dataset dataset = new Dataset();
        dataset.setId(id);
        dataset.setNome(nome);
        dataset.setDescricao(descricao);
        try(DAOFactory daoFactory = DAOFactory.getInstance()){
            DatasetDAO dao = daoFactory.getDatasetDAO();
            dao.updateOnlyNomeAndDescricao(dataset);
        }
    }
    public void deletar(Integer id)throws Exception{
        try(DAOFactory daoFactory = DAOFactory.getInstance()){
            DatasetDAO dao = daoFactory.getDatasetDAO();
            dao.delete(id);
        }
    }
}
