/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.manager;


import com.dataconnector.query.SQLServerQuery;
import com.dataconnector.query.Query;
import com.dataconnector.builder.AbstractCriteriaBuilderImpl;
import com.dataconnector.common.ConnectionHelper;
import com.dataconnector.criteria.CriteriaQuery;

import com.dataconnector.criteria.delete.CriteriaDeleteSQLServer;
import com.dataconnector.criteria.insert.CriteriaInsertSQLServer;
import com.dataconnector.criteria.update.CriteriaUpdateSQLServer;
import com.dataconnector.criterial.generic.CriteriaQueryImpl;
import com.dataconnector.excecution.SelectQueryImpl;
import com.dataconnector.excecution.SelectSQLServerQueryImpl;
import com.dataconnector.helper.ValidateSelectQuery;
import com.dataconnector.constans.ProvidersSupportEnum;
import com.dataconnector.context.ContextDataConnectorImpl;
import com.dataconnector.sql.CriteriaBuilder;
import com.dataconnector.wrapper.DataConnectorConWrap;
import java.sql.Connection;

/**
 * {Insert class description here}
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 23/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class DataConnectorSQLServerManagerImpl implements DataConnectorSQLServerManager {

    private final AbstractCriteriaBuilderImpl builder;
    private  DataConnectorConWrap connector;
     private final ContextDataConnectorImpl context;

    public DataConnectorSQLServerManagerImpl(CriteriaBuilder builder, ContextDataConnectorImpl context) {
        this.builder = (AbstractCriteriaBuilderImpl) builder;
        this.builder.setDriver(ProvidersSupportEnum.SQLSERVER);
        this.context=context;
      //  this.connector = context.getConnection();

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

    @Override
    public Connection getConnection() {
        return context.getConnection().getConnection();
    }

    @Override
    public SQLServerQuery createQuery(CriteriaQuery q) {
        //
        System.out.println("Imprimir construcción del query.....");
        CriteriaQueryImpl implQ = (CriteriaQueryImpl) q;
        //Crea un select de tipo SQL Server
        SelectSQLServerQueryImpl impl = new SelectSQLServerQueryImpl(q, this);
     //  FromImpl from=implQ.getFromImpl();
      /*  from.proccessJoins();
         System.out.println(implQ.getSelectImpl().getTranslation() + " " + from.getTranslation() + " " + implQ.getWhereImpl().getTranslation());
         SelectQueryImpl impl= new SelectQueryImpl(q);*/
        impl.extractParameters();
        //Valida la sintaxis de la sentencia sql
        ValidateSelectQuery validate = ValidateSelectQuery.getInstance();
        validate.validateQuerySelect(implQ.getSelectImpl());
        return impl;
    }

   

    @Override
    public ProvidersSupportEnum getProvidersSupportEnum() {
        return ProvidersSupportEnum.SQLSERVER;
    }

    @Override
    public ContextDataConnectorImpl getContext() {
       return context;
    }

}
