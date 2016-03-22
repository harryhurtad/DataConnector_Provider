/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.excecution;

import com.dataconnector.manager.AbstractDataConnectorManager;
import com.dataconnector.obj.ParameterConstructClass;
import com.dataconnector.utils.Constantes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Clase encargada de ejecutar sentencias de tipo Select Query en multi hilo
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @param <X>
 * @since build 9/03/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class ExcecuteSelectThreadStatementSQL<X extends Object> implements Callable<List<X>> {

    private Map<String, Object> mapParameter;
    private final AbstractDataConnectorManager manager;
    private final String sql;
    private final Class objectoRetorno;
    private final Map<String, Class> mapValueReturn;
     

    public ExcecuteSelectThreadStatementSQL(int posicionInicial, int posicionFinal, AbstractDataConnectorManager manager, String sql, Map<String, Object> mapParameter,Map<String, Class> mapValueReturn, Class objectoRetorno) {

        this.sql = sql;
        copyMapParameters(mapParameter);
        this.manager = manager;
        this.objectoRetorno = objectoRetorno;
    
        this.mapValueReturn=mapValueReturn;
        this.mapParameter.put(Constantes.POSICION_INICIAL, posicionInicial);
        this.mapParameter.put(Constantes.POSICION_FINAL, posicionFinal);
    }

    @Override
    public List<X> call() throws Exception {

        List<X> listObjReturn;
        ExcecuteHelper helper = new ExcecuteHelper();
        try (Connection con = manager.getConnection()) {

            // st= con.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //se adicionan los parametros a la consulta
            NamedParameterStatement namedParameterStatement = new NamedParameterStatement(con, sql);
            helper.setParameterPrepareStatement(namedParameterStatement, mapParameter);
            Set<Map.Entry<String, Class>> listValues = mapValueReturn.entrySet();
            //Ejecuta la consulta
            try (
                 ResultSet rs = namedParameterStatement.executeQuery()) {
                Class objClass = objectoRetorno;
                listObjReturn = helper.resulsetQueryObjDataConnector(rs, objClass, listValues);
                //Cierra la conexion
            }
        }
        return listObjReturn;
    }

    @Override
    public String toString() {
        return "ExcecuteSelectThreadStatementSQL{" + "mapParameter=" + mapParameter + '}';
    }
    
    
    private void copyMapParameters(Map<String, Object> mapParameterTmp){
        this.mapParameter=new HashMap<>();
       Set<Entry<String,Object>> listaParametros  =mapParameterTmp.entrySet();
       for(Entry<String,Object> parametro:listaParametros){
           this.mapParameter.put(parametro.getKey(),parametro.getValue());
       }
        
       
       
    }

}
