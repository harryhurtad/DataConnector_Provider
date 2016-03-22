/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.excecution;

import com.dataconnector.manager.AbstractDataConnectorManager;
import com.dataconnector.obj.ParameterImpl;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {Insert class description here}
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 18/03/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class ExcecuteSelectPrimitiveStatementSQL<X> {

    private final AbstractDataConnectorManager manager;
    private final Integer maxResult;
    private final List<ParameterImpl> listReturnvalue;
    private final Map<String, Object> mapParameter;

    public ExcecuteSelectPrimitiveStatementSQL(AbstractDataConnectorManager manager, Map<String, Object> mapParameter, Integer maxResult, List<ParameterImpl> listReturnValue) {
        this.manager = manager;
        this.maxResult = maxResult;
        this.listReturnvalue = listReturnValue;
        this.mapParameter = mapParameter;
    }

    public List<X> excecuteSQLStatement(Class objReturn, String sql) throws SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        NamedParameterStatement namedParameterStatement;
        List<X> listObjReturn;

        try (Connection con = manager.getConnection()) {

            namedParameterStatement = new NamedParameterStatement(con, sql);
            ExcecuteHelper<X> helper = new ExcecuteHelper();
            helper.setParameterPrepareStatement(namedParameterStatement, mapParameter);

            if (maxResult != null) {
                namedParameterStatement.setMaxResult(maxResult);
            }

            // execute select SQL stetement
            try (
                    ResultSet rs = namedParameterStatement.executeQuery()) {
                Class objClass = objReturn;
                listObjReturn = helper.resulsetQueryPrimitiveDataConnector(rs, objClass, listReturnvalue);
                
            }

        }
        return listObjReturn;
    }
}
