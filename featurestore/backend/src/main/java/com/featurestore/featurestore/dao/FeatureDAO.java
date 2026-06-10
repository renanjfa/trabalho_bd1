package com.featurestore.featurestore.dao;

import java.sql.SQLException;
import java.util.List;
import com.featurestore.featurestore.models.Feature;

public interface FeatureDAO extends DAO<Feature> {
    
    public List<Feature> getByTipo(String tipo) throws SQLException;
}
