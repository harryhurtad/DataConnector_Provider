/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.function.sqlserver;

import com.dataconnector.commons.ElementSQLEnum;
import com.dataconnector.object.ValueRoot;
import com.dataconnectorcommons.sql.AliasExpression;
import com.dataconnectorcommons.sql.Expression;
import com.dataconnectorcommons.sql.Selection;
import com.dataconnector.utils.Constantes;

/**
 * {Insert class description here}
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 23/03/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class RowNumberFuncImpl implements Expression, AliasExpression {

    private final ValueRoot field;
    private final String alias;
    private final StringBuilder concat;

    public RowNumberFuncImpl(ValueRoot field, String alias) {
        this.field = field;
        this.alias = alias;
        concat = new StringBuilder();
    }

    public void process() {
        concat.append(Constantes.ESPACIO);
        concat.append(Constantes.ROWN_NUMBER_FUNCTION);
        concat.append(Constantes.ESPACIO);
        concat.append(Constantes.OVER_OPERATOR);
        concat.append(Constantes.ESPACIO);
        concat.append(Constantes.PARENTECIS_IZQUIERDO);
        concat.append(Constantes.ESPACIO);
        concat.append(ElementSQLEnum.ORDER_BY.getNameElement());
        concat.append(Constantes.ESPACIO);
        concat.append(field.getSQLTransalte());
        concat.append(Constantes.ESPACIO);
        concat.append(Constantes.PARENTECIS_DERECHO);
        concat.append(Constantes.ESPACIO);
        concat.append(Constantes.ALIAS);
        concat.append(Constantes.ESPACIO);
        concat.append(getAlias());

    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public StringBuilder getSQLTransalte() {
        return concat;
    }

    @Override
    public Selection alias(String value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
