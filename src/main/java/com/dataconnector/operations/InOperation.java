/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.operations;

import com.dataconnector.object.ValueExpression;
import com.dataconnector.sql.Expression;
import com.dataconnector.sql.OperationEnum;
import com.dataconnector.sql.Predicate;
import com.dataconnector.utils.Constantes;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion que representa el operador IN del lenguaje SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 26/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class InOperation implements Predicate {

    protected OperationEnum operacion;
    protected StringBuilder sql;
    protected List<Expression> listParameters;

    public InOperation() {
        sql = new StringBuilder();
        listParameters = new ArrayList();
        operacion = OperationEnum.IN;

    }

    /**
     * Retorna el procesamiento de la sentecia IN en obj Predicate
     *
     * @param values
     * @return
     */
    public Predicate proccessTranslate(String[] values) {

        sql.append(Constantes.PARENTECIS_DERECHO);
        sql.append(Constantes.ESPACIO);
        for (int contador = 0; contador < values.length; contador++) {
            //Adicion los valores
            ValueExpression exp = new ValueExpression(values[contador]);
            listParameters.add(exp);
            sql.append(exp.getValue());
            if (values.length > 0 && (contador + 1 != values.length)) {
                sql.append(Constantes.COMA);
            }
        }
        sql.append(Constantes.ESPACIO);
        sql.append(Constantes.PARENTECIS_IZQUIERDO);

        return this;

    }

    @Override
    public String getAlias() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setOperation(OperationEnum operation) {
        this.operacion = operation;
    }

    @Override
    public OperationEnum getOperation() {
        return operacion;
    }

    @Override
    public StringBuilder getSQLTransalte() {
        return sql;
    }

    @Override
    public List<Expression> getListExpression() {
        return listParameters;
    }

}
