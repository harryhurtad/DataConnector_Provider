/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.core;

import com.dataconnector.builder.AbstractCriteriaBuilderImpl;
import com.dataconnector.builder.CriteriaGenericBuilderImpl;
import com.dataconnector.builder.CriteriaSQLServerBuilderImpl;
import com.dataconnector.context.InitialContextDataconnectorImpl;
import com.dataconnector.exceptions.InitialCtxDataConnectorException;
import com.dataconnector.helper.DataConnectorConWrap;
import com.dataconnector.sql.CriteriaBuilder;
import com.dataconnector.manager.AbstractDataConnectorManager;
import com.dataconnector.manager.DataConnectorManager;
import com.dataconnector.manager.DataConnectorManagerImpl;
import com.dataconnector.manager.DataConnectorOracleManager;
import com.dataconnector.manager.DataConnectorOracleManagerImpl;
import com.dataconnector.manager.DataConnectorSQLServerManager;
import com.dataconnector.manager.DataConnectorSQLServerManagerImpl;
import com.dataconnector.constans.ProvidersSupportEnum;

import com.dataconnector.manager.DataConnectorFactory;
import com.dataconnector.manager.InitialContextDataConnector;

/**
 * Factory que Realiza la creacion de las diferentes configuraciones soportadas
 * por DataConnector
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 23/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class DataConnectorFactoryImpl implements DataConnectorFactory {

    private  AbstractDataConnectorManager instanceManager;
   
    private static  InitialContextDataConnector context;
  

    public DataConnectorFactoryImpl(InitialContextDataConnector context) {
       this.context=context;
    }

 
    @Override
    public AbstractDataConnectorManager getInstanceDataConnectorManager() {
        return instanceManager;
    }

    

    /**
     * Obtiene el objeto manager de acuerdo al driver seleccionado
     *
     * @param suport
     * @param connector
     * @return implementacion de AbstractDataConnectorManager
     */
    public AbstractDataConnectorManager getDataConnectorManager(ProvidersSupportEnum suport,DataConnectorConWrap connector) {

        CriteriaBuilder builder = null;

        switch (suport) {
            case ORACLE:
                builder=new CriteriaGenericBuilderImpl();//TODO Crear Implementacion
                DataConnectorOracleManager dataConnectorOracleManager = new DataConnectorOracleManagerImpl(builder,connector);
                instanceManager = dataConnectorOracleManager;
                break;
            case SQLSERVER:
                builder=new CriteriaSQLServerBuilderImpl();
                DataConnectorSQLServerManager dataConnectorSQLServerManagerImpl = new DataConnectorSQLServerManagerImpl(builder,connector);
                instanceManager = dataConnectorSQLServerManagerImpl;
                break;
            default:
                 builder=new CriteriaGenericBuilderImpl();//TODO Crear Implementacion
                DataConnectorManager dataConnectorManagerImpl = new DataConnectorManagerImpl(builder,connector);
                instanceManager = dataConnectorManagerImpl;
                break;
        }

        return instanceManager;
    }

    public void setDataConnectorManager(AbstractDataConnectorManager manager) {
        this.instanceManager = manager;
    }

  
  
    public  static InitialContextDataconnectorImpl getInitialContext() throws InitialCtxDataConnectorException {
       return (InitialContextDataconnectorImpl)context;
    }
}
