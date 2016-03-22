/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.excecution;

import com.dataconnector.manager.AbstractDataConnectorManager;
import com.dataconnector.obj.ParameterConstructClass;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Clase encargada de ejecutar sentencias de tipo Select Query en un simple hilo
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @param <X>
 * @since build 16/03/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class ExcecuteSelectSingleStatementSQL<X> {

    private final Map<String, Object> mapParameter;
    private final AbstractDataConnectorManager manager;
    private final Integer maxResult;
    private final Map<String, Class> mapValueReturn;
   
    public ExcecuteSelectSingleStatementSQL(Map<String, Object> mapParameter, Map<String, Class> mapValueReturn, Integer maxResult, AbstractDataConnectorManager manager) {

        this.mapParameter = mapParameter;
        this.mapValueReturn = mapValueReturn;
        this.manager = manager;
        this.maxResult = maxResult;
      
    }
    
   
    public List<X> excecuteSQLStatement(Class objReturn, String sql) throws SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {

        NamedParameterStatement namedParameterStatement;
        List<X> listObjReturn = null;

        try (Connection con = manager.getConnection()) {

            namedParameterStatement = new NamedParameterStatement(con, sql);
            ExcecuteHelper<X> helper = new ExcecuteHelper();
            helper.setParameterPrepareStatement(namedParameterStatement, mapParameter);
            Set<Map.Entry<String, Class>> listValues = mapValueReturn.entrySet();
            if (maxResult != null) {
                namedParameterStatement.setMaxResult(maxResult);
            }

            // execute select SQL stetement
            try (
                    ResultSet rs = namedParameterStatement.executeQuery()) {
                Class objClass = objReturn;
                listObjReturn = helper.resulsetQueryObjDataConnector(rs, objClass, listValues);

            }

        }

        return listObjReturn;
    }

}
