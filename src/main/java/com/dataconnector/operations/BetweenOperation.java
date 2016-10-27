/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.operations;

import com.dataconnector.object.ValueExpression;
import com.dataconnector.object.ValueParam;
import com.dataconnector.object.ValueRoot;
import com.dataconnectorcommons.sql.Expression;
import com.dataconnector.sql.OperationEnum;
import com.dataconnector.sql.Predicate;
import com.dataconnector.utils.Constantes;

/**
 * Implementacion que representa el operador BETWEEN del lenguaje SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 26/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class BetweenOperation extends AbstractOperPredicate {

    public BetweenOperation() {
        super(OperationEnum.BETWEEN);
    }

    /**
     * Realiza la conversión de obj a sintaxis sql
     *
     * @param field
     * @param param1
     * @param param2
     * @return
     */
    public Predicate translateOperation(ValueRoot field, ValueParam param1, ValueParam param2) {
       
        sql.append(field.getSQLTransalte());
        sql.append(Constantes.ESPACIO);
        sql.append(operacion.getSimboloOper());
        sql.append(Constantes.ESPACIO);
        sql.append(param1.getSQLTransalte());
        sql.append(Constantes.ESPACIO);
        sql.append(OperationEnum.AND.getSimboloOper());
        sql.append(Constantes.ESPACIO);
        sql.append(param2.getSQLTransalte());
        sql.append(Constantes.ESPACIO);
       
        return this;
    }

    @Override
    public Predicate translateOperation(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
