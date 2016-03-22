/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.translation;

import com.dataconnector.connection.MetaDataDataconnector;
import com.dataconnector.core.DataConnectorFactoryImpl;
import com.dataconnector.criteria.AbstractQuery;
import com.dataconnector.manager.DataConnector;
import com.dataconnector.manager.DataConnectorFactory;
import com.dataconnector.obj.TranslatePagination;
import com.dataconnector.sql.Selection;
import com.dataconnector.utils.Constantes;

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

    public String translateStatementByDriver(AbstractQuery query) {
        TranslateSelect translate;
        String sql = null;
        // if(element instanceof Equ)
        MetaDataDataconnector metadata = DataConnectorFactoryImpl.getDataDataconnector();
        switch (metadata.getNameClassDriver()) {
            case Constantes.DRIVER_MYSQL:
                translate = new TranslateSelectMySQL();
                sql = translate.translate(query);
                break;
            case Constantes.DRIVER_ORACLE:
                break;
            case Constantes.DRIVER_SQLSERVER:
                break;
            default:
                break;
        }

        return sql;
    }

    public TranslatePagination translatePagValueByDriver(Integer posicionInicial, Integer posicionFinal) {
        TranslateSelect translate;
        TranslatePagination pag = null;
        MetaDataDataconnector metadata = DataConnectorFactoryImpl.getDataDataconnector();
        switch (metadata.getNameClassDriver()) {
            case Constantes.DRIVER_MYSQL:
                translate = new TranslateSelectMySQL();
                pag = translate.pagination(posicionInicial, posicionFinal);
                break;
            case Constantes.DRIVER_ORACLE:
                break;
            case Constantes.DRIVER_SQLSERVER:
                break;
            default:
                break;
        }

        return pag;

    }

}
