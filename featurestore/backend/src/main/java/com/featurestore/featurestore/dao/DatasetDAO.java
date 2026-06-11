package com.featurestore.featurestore.dao;

import java.sql.SQLException;
import java.util.List;
import com.featurestore.featurestore.models.Dataset;

public interface DatasetDAO extends DAO<Dataset> {
    
    public void insertWithoutDescricao(Dataset data) throws SQLException;

    public void updateOnlyNomeAndDescricao(Dataset data) throws SQLException;

    public void insertFonte(Integer idDataset, String fonte) throws SQLException;

    public List<String> getFontesDataset(Integer idDataset) throws SQLException;
}
