/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.translation;

import com.dataconnector.connection.MetaDataDataconnector;
import com.dataconnector.constans.DataConnectorConstants;
import com.dataconnector.core.DataConnectorFactoryImpl;
import com.dataconnector.criteria.AbstractQuery;
import com.dataconnector.exceptions.InitialCtxDataConnectorException;
import com.dataconnector.manager.DataConnector;
import com.dataconnector.manager.DataConnectorFactory;
import com.dataconnector.query.Query;
import com.dataconnector.obj.TranslatePagination;
import com.dataconnector.constans.ProvidersSupportEnum;
import com.dataconnector.context.ContextDataConnectorImpl;
import com.dataconnectorcommons.sql.Selection;
import com.dataconnector.utils.Constantes;
import com.dataconnector.manager.ContextDataConnector;
import com.dataconnector.manager.DataConnectorConnection;
import com.dataconnector.wrapper.DataConnectorConWrap;

/**
 * {Insert class description here}
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 26/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class TranslatorHelper {

    private static TranslatorHelper instance;

    private TranslatorHelper() {

    }

    public static TranslatorHelper getInstance() {
        if (instance == null) {
            instance = new TranslatorHelper();
        }

        return instance;
    }

    public String translateStatementByDriver(AbstractQuery query, Query postQuery,ContextDataConnector context) throws InitialCtxDataConnectorException {
        TranslateSelect translate = null;
        String sql = null;       
        ProvidersSupportEnum supportEnum = context.getProvider();
        // if(element instanceof Equ)
       // MetaDataDataconnector metadata = DataConnectorFactoryImpl.getInitialContext().getDataDataconnector();
        switch (supportEnum) {
            case SQLSERVER:
                translate = new TranslateSelectSqlServer();
               
            
            case GENERIC:               
               if(context.getConnection().getDriverName().equals(DataConnectorConstants.DRIVER_MYSQL)){
                // if (metadata.getNameTypeDriver().equals(DataConnectorConstants.DRIVER_MYSQL)) {
                    translate = new TranslateSelectMySQL();
                }
               
            case ORACLE:

           
            default:
                sql = translate.translate(query, postQuery);
                break;
        }

        return sql;
    }

    public TranslatePagination translatePagValueByDriver(Integer posicionInicial, Integer posicionFinal,ContextDataConnector context) throws InitialCtxDataConnectorException {
        TranslateSelect translate = null;
        TranslatePagination pag = null;
       
        DataConnectorConnection connection=context.getConnection();
                
        switch (connection.getDriverName()) {
            case DataConnectorConstants.DRIVER_MYSQL:
                translate = new TranslateSelectMySQL();
                break;
            case DataConnectorConstants.DRIVER_ORACLE:
                break;
            case DataConnectorConstants.DRIVER_SQLSERVER:
                translate = new TranslateSelectSqlServer();

                break;
            default:
                break;
        }
        if (translate != null) {
            pag = translate.pagination(posicionInicial, posicionFinal);
        }
        return pag;

    }

}
