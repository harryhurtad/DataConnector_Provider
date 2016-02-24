/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.operations;

import com.dataconnector.utils.Constantes;
import com.dataconnector.sql.OperationEnum;
import com.dataconnector.sql.Expression;
import com.dataconnector.sql.Parameter;
import com.dataconnector.sql.ParameterExpression;
import com.dataconnector.sql.Predicate;
import java.util.List;

/**
 *
 * @author proveedor_hhurtado
 */
public final class AndOperation implements Predicate {

    private Parameter parametro1;
    private Parameter parametro2;
    private StringBuilder sqlResultBuilder;

    public AndOperation() {
        setOperation(OperationEnum.AND);
    }

    public void translatePredicate(Expression exp1, Expression exp2) {
        ParameterExpression param1 = (ParameterExpression) exp1;
        ParameterExpression param2 = (ParameterExpression) exp2;
        this.parametro1 = param1;
        this.parametro2 = param2;
        sqlResultBuilder = new StringBuilder();
        //transformacion a sql
        sqlResultBuilder.append(Constantes.PARENTECIS_DERECHO);
        sqlResultBuilder.append(Constantes.ESPACIO);
        sqlResultBuilder.append(((Parameter) param1).getNombreParametro());
        sqlResultBuilder.append(Constantes.ESPACIO);
        sqlResultBuilder.append(OperationEnum.getSimboloOper(OperationEnum.AND));  
        sqlResultBuilder.append(Constantes.ESPACIO);
        sqlResultBuilder.append(((Parameter) param2).getNombreParametro());
        sqlResultBuilder.append(Constantes.ESPACIO);
        sqlResultBuilder.append(Constantes.PARENTECIS_IZQUIERDO);
    }

    public void translateExpression(Predicate[] params) {

    }

    public void translateExpression() {

    }

    public Parameter getParametro1() {
        return parametro1;
    }

    public Parameter getParametro2() {
        return parametro2;
    }

    @Override
    public StringBuilder getSql() {
        return sqlResultBuilder;
    }

    @Override
    public void setOperation(OperationEnum operation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OperationEnum getOperation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setListExpression(List<Expression> listExp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
