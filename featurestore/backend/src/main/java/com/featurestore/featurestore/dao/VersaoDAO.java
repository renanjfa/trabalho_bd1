package com.featurestore.featurestore.dao;

import java.sql.SQLException;
import java.util.List;
import com.featurestore.featurestore.models.Versao;
import com.featurestore.featurestore.models.Feature;

public interface VersaoDAO extends DAO<Versao> {
    
    public void insertWithoutVersaoBase(Versao v) throws SQLException;

    public void insertWithoutDescricao(Versao v) throws SQLException;

    public void insertWithoutDescricaoAndVersaoBase(Versao v) throws SQLException;

    public List<Versao> getByIdDataset(Integer idDataset) throws SQLException;

    public List<Versao> getByEmailUsuario(String emailUsuario) throws SQLException;

    public void insertFeature(Integer idVersao, Integer idFeature) throws SQLException;

    public List<Feature> getFeaturesByIdVersao(Integer idVersao) throws SQLException;

    public void deleteFeatureFromVersao(Integer idVersao, Integer idFeature) throws SQLException;
}
