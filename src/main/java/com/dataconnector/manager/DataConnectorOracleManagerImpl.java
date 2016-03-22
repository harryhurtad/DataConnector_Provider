/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dataconnector.manager;

import com.dataconnector.builder.CriteriaBuilderImpl;
import com.dataconnector.criteria.CriteriaQueryOracle;
import com.dataconnector.criteria.delete.CriteriaDeleteOracle;
import com.dataconnector.criteria.insert.CriteriaInsertOracle;
import com.dataconnector.criteria.update.CriteriaUpdateOracle;
import com.dataconnector.object.ProvidersSupportEnum;
import com.dataconnector.sql.CriteriaBuilder;
import java.sql.Connection;

/**
 *{Insert class description here}
 * @version $Revision: 1.1.1  (UTF-8)
 * @since build 23/02/2016  
 * @author proveedor_hhurtado  email: proveedor_hhurtad@ath.com.co
 */
public class DataConnectorOracleManagerImpl implements DataConnectorOracleManager{

    private CriteriaBuilderImpl builder;    

    public DataConnectorOracleManagerImpl(CriteriaBuilder builder) {
        this.builder = (CriteriaBuilderImpl)builder;
        this.builder.setDriver(ProvidersSupportEnum.ORACLE);
    }
    
    
    
    
    @Override
    public  CriteriaBuilder getCriterialBuilder() {
        return builder;
    }

    @Override
    public String QuiEst() {
       return "DataConnectorOracleManager";
    }

    @Override
    public Query createQuery(CriteriaQueryOracle q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Query createQuery(CriteriaInsertOracle q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Query createQuery(CriteriaDeleteOracle q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Query createQuery(CriteriaUpdateOracle q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
