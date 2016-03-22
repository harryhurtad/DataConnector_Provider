/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.obj;

import com.dataconnector.sql.ParameterExpression;

/**
 * Clase que representa los parametros pasados por SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @param <X>
 * @since build 23/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class ParameterImpl<X extends Object> implements ParameterExpression {

    private final String nombreParametro;
    private final StringBuilder sql;
    private final Class type;
    private Object value;

    public ParameterImpl(Class type, String nameParam) {
        sql = new StringBuilder();
        this.type = type;
        this.nombreParametro = nameParam;
    }

    @Override
    public String getNombreParametro() {
        return nombreParametro;
    }

    @Override
    public String getAlias() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StringBuilder getSQLTransalte() {
        sql.append(":");
        sql.append(nombreParametro);
        return sql;
    }

    @Override
    public Class<X> getParameterType() {
        return type;
    }

   

}
