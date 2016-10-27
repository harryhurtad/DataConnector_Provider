/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.sql;

import com.dataconnector.commons.ElementSQLEnum;
import com.dataconnector.commons.TupleSQL;
import com.dataconnector.utils.Constantes;

/**
 * Implementacion que representa  Where del lenguaje SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 26/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class WhereImpl implements TupleSQL {

    private final StringBuilder sql;
    private Predicate parametros;

    public WhereImpl() {
        sql = new StringBuilder();
        //this.sql=parametros.getSQLTransalte();

    }

    public Predicate getParametros() {
        return parametros;
    }

    public void proccess(Predicate parametros) {

        sql.append(getTypeElementSQL().getNameElement());
        sql.append(Constantes.ESPACIO);
        sql.append(parametros.getSQLTransalte());
        this.parametros = parametros;
    }

    
     public void proccess(Predicate[] parametros) {

        sql.append(getTypeElementSQL().getNameElement());        
        for(Predicate predicate:parametros){
          sql.append(Constantes.ESPACIO);
         sql.append(predicate.getSQLTransalte());
        }
      //  this.parametros = parametros;
    }
    
   

    @Override
    public ElementSQLEnum getTypeElementSQL() {
        return ElementSQLEnum.WHERE;
    }

    @Override
    public String getTranslation() {
        return sql.toString();

    }
}
