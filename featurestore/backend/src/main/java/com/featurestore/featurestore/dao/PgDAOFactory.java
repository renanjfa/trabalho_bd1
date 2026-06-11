package com.featurestore.featurestore.dao;

import java.sql.Connection;

public class PgDAOFactory extends DAOFactory {
    
    public PgDAOFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new PgUsuarioDAO(this.connection);
    }  
    
    @Override
    public FeatureDAO getFeatureDAO() {
        return new PgFeatureDAO(this.connection);
    } 

    @Override
    public DatasetDAO getDatasetDAO() {
        return new PgDatasetDAO(this.connection);
    }

    @Override
    public AcessoDatasetDAO getAcessoDatasetDAO() {
        return new PgAcessoDatasetDAO(this.connection);
    }
    
}
