/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.sql;

import com.dataconnector.commons.ElementSQLEnum;
import com.dataconnector.commons.TupleSQL;
import com.dataconnector.criteria.SubQuery;
import com.dataconnector.criterial.generic.SubQueryImpl;
import com.dataconnector.utils.Constantes;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion que representa  Select del lenguaje SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 26/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class SelectImpl implements TupleSQL {

    private final StringBuilder sql;
    private final List<Selection> listParametros;

    public SelectImpl() {
        sql = new StringBuilder();
        this.listParametros = new ArrayList();
        //proccess(listParametros);
    }

    public void proccess(Selection[] params) {
        sql.append(getTypeElementSQL().getNameElement());
        sql.append(Constantes.ESPACIO);
        //adiciona los campos al select
        for (int contador = 0; contador < params.length; contador++) {
            if(params[contador] instanceof SubQuery){
                ((SubQueryImpl)(params[contador])).proccess();
            }
            sql.append(params[contador].getSQLTransalte());
            if (((contador + 1) < params.length) && (params.length > 1)) {
                sql.append(Constantes.COMA);
            }
            listParametros.add(params[contador]);
        }
        
    }

    public List<Selection> getListParametros() {
        return listParametros;
    }

    
    
    @Override
    public ElementSQLEnum getTypeElementSQL() {
        return ElementSQLEnum.SELECT;
    }

    @Override
    public String getTranslation() {
        return sql.toString();
    }

}
