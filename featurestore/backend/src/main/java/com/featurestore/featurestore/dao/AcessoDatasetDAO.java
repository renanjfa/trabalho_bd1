package com.featurestore.featurestore.dao;


import java.sql.SQLException;
import java.util.List;
import com.featurestore.featurestore.models.AcessoDataset;

public interface AcessoDatasetDAO extends DAO<AcessoDataset> {
    
    public List<AcessoDataset> getByIdDataset(Integer id) throws SQLException;

    public List<AcessoDataset> getByEmailUsuario(String email) throws SQLException;

    public List<AcessoDataset> getByEmailAndDatasetRecente(String email, Integer idDataset, int segundos) throws SQLException;

    
}
