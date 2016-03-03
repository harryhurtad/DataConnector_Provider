/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.sql.join;

import com.dataconnector.commons.ElementSQLEnum;
import com.dataconnector.commons.TupleSQL;
import com.dataconnector.sql.JoinPredicate;
import com.dataconnector.utils.Constantes;

/**
 * Representa la sentencia ON del lenguaje  SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 1/03/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class OnImpl implements TupleSQL {

    private StringBuilder sql;
    private JoinPredicate parametros;

    public OnImpl() {
        sql = new StringBuilder();
    }

    @Override
    public ElementSQLEnum getTypeElementSQL() {
        return ElementSQLEnum.ON;
    }

    public void proccess(JoinPredicate p) {

        sql.append(ElementSQLEnum.ON.getNameElement());
        sql.append(Constantes.ESPACIO);
      //  sql.append(Constantes.PARENTECIS_IZQUIERDO);
        sql.append(p.getSQLTransalte());
        sql.append(Constantes.ESPACIO);
     //   sql.append(Constantes.PARENTECIS_DERECHO);
        parametros = p;
    }

    public JoinPredicate getParametros() {
        return parametros;
    }

    
    
    @Override
    public String getTranslation() {
        return sql.toString();
    }

}
