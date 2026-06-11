package com.featurestore.featurestore.dao;

import java.sql.SQLException;
import java.util.List;
import com.featurestore.featurestore.models.DownloadVersao;

public interface DownloadVersaoDAO extends DAO<DownloadVersao> {
    
    public List<DownloadVersao> getByIdVersao(Integer id) throws SQLException;

    public List<DownloadVersao> getByEmailUsuario(String email) throws SQLException;

    
}
