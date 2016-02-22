/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.dataconnector.factory;

import com.dataconnector.sql.CriterialBuilder;
import com.dataconnector.sql.CriterialQuery;
import com.dataconnector.sql.Query;
import com.prueba.dataconnector.interfaces.AbstractFactoryDataConnector;

/**
 *
 * @author proveedor_hhurtado
 */
public class FactoryMysqDataConnector extends AbstractFactoryDataConnector{

    @Override
    public CriterialBuilder getCriterialBuilder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Query createQuery(CriterialQuery q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
