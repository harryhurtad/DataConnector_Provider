/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.operations;

import com.dataconnector.criteria.SubQuery;
import com.dataconnector.criterial.generic.SubQueryImpl;
import com.dataconnector.sql.Expression;
import com.dataconnector.sql.OperationEnum;
import com.dataconnector.sql.Predicate;
import com.dataconnector.utils.Constantes;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Abstracta que representa el conjunto de operaciones comunes soportadas
 * por SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 26/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public abstract class AbstractOperPredicate implements Predicate {

    protected OperationEnum operacion;
    protected StringBuilder sql;
    protected List<Expression> listParameters;

    public AbstractOperPredicate(OperationEnum operacion) {
        sql = new StringBuilder();
        listParameters = new ArrayList();
        this.operacion = operacion;
    }

    public abstract Predicate translateOperation(Expression param1, Expression param2);

    /**
     *
     * @param param1
     * @param param2
     * @return
     */
    public void proccessTranslate(Expression param1, Expression param2) {

        evaluatePredicate(param1);
        evaluatePredicate(param2);
        sql.append(Constantes.PARENTECIS_IZQUIERDO);
        sql.append(Constantes.ESPACIO);
        sql.append(param1.getSQLTransalte());
        sql.append(Constantes.ESPACIO);
        sql.append(operacion.getSimboloOper());
        sql.append(Constantes.ESPACIO);
        sql.append(param2.getSQLTransalte());
        sql.append(Constantes.ESPACIO);
        sql.append(Constantes.PARENTECIS_DERECHO);
        //se adiciona los elementos
        listParameters.add(param1);
        listParameters.add(param2);

    }

    /**
     *
     * @param parameters
     */
    public void proccessTranslate(Predicate[] parameters) {
        sql.append(Constantes.PARENTECIS_IZQUIERDO);
        sql.append(Constantes.ESPACIO);
        int cantidadElemProces = parameters.length;
        int contador = 0;
        while (cantidadElemProces > 0) {
            Predicate p1 = parameters[contador++];

            evaluatePredicate(p1);

            sql.append(proccesElement(p1));
            sql.append(Constantes.ESPACIO);
            if (cantidadElemProces != 1) {
                sql.append(operacion.getSimboloOper());
                sql.append(Constantes.ESPACIO);
                listParameters.add(p1);
            }
            cantidadElemProces -= 1;
        }
        sql.append(Constantes.ESPACIO);
        sql.append(Constantes.PARENTECIS_DERECHO);

    }

    /**
     * Realiza evaluació de una instancia de predicate y realiza su
     * correspondiente procesamiento
     *
     * @param p
     */
    private void evaluatePredicate(Expression p) {

        if (p instanceof SubQuery) {
            ((SubQueryImpl) p).proccess();
        }
    }

    /**
     *
     * @param p
     * @return
     */
    protected StringBuilder proccesElement(Predicate p) {
        StringBuilder sqlTmp = null;
        if (p instanceof AbstractOperPredicate) {
            AbstractOperPredicate oper = (AbstractOperPredicate) p;
            sqlTmp = oper.getSQLTransalte();
        }

        return sqlTmp;
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
