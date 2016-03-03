/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dataconnector.manager;

import com.dataconnector.builder.CriteriaBuilderImpl;
import com.dataconnector.criteria.CriteriaQuerySQLServer;
import com.dataconnector.criteria.delete.CriteriaDeleteSQLServer;
import com.dataconnector.criteria.insert.CriteriaInsertSQLServer;
import com.dataconnector.criteria.update.CriteriaUpdateSQLServer;
import com.dataconnector.object.ProvidersSupportEnum;
import com.dataconnector.sql.CriteriaBuilder;

/**
 *{Insert class description here}
 * @version $Revision: 1.1.1  (UTF-8)
 * @since build 23/02/2016  
 * @author proveedor_hhurtado  email: proveedor_hhurtad@ath.com.co
 */
public class DataConnectorSQLServerManagerImpl implements DataConnectorSQLServerManager {

    private CriteriaBuilderImpl builder;

    public DataConnectorSQLServerManagerImpl(CriteriaBuilder builder) {
         this.builder = (CriteriaBuilderImpl)builder;
        this.builder.setDriver(ProvidersSupportEnum.SQLSERVER);
    }
    
    
    
    @Override
    public CriteriaBuilder getCriterialBuilder() {
     return builder;   
    }

    @Override
    public String QuiEst() {
     return "DataConnectorSQLServerManager";
    }

    @Override
    public Query createQuery(CriteriaQuerySQLServer q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Query createQuery(CriteriaInsertSQLServer q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Query createQuery(CriteriaDeleteSQLServer q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Query createQuery(CriteriaUpdateSQLServer q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
