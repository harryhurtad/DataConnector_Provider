/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.sql;

import com.dataconnectorcommons.sql.Selection;
import com.dataconnector.commons.ElementSQLEnum;
import com.dataconnector.commons.TupleSQL;
import com.dataconnector.criteria.SubQuery;
import com.dataconnector.criterial.generic.SubQueryImpl;
import com.dataconnector.object.ValueRoot;
import com.dataconnector.utils.Constantes;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion que representa Select del lenguaje SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 26/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class SelectImpl implements TupleSQL {

    private StringBuilder sql;
    private final List<Selection> listParametros;
    private int contadorAlias = 0;
    private final Class classToCreate;

    public SelectImpl(Class param) {
        sql = new StringBuilder();
        this.listParametros = new ArrayList();
        this.classToCreate = param;
        //proccess(listParametros);
    }

    public void proccess(Selection[] params) {
        sql.append(getTypeElementSQL().getNameElement());
        sql.append(Constantes.ESPACIO);
        //adiciona los campos al select
        for (int contador = 0; contador < params.length; contador++) {
            //Evalua para el subquery
            proccessSelection(params[contador],true);
            if (((contador + 1) < params.length) && (params.length > 1)) {
                sql.append(Constantes.COMA);
            }
            listParametros.add(params[contador]);
        }

    }

    public void proccessSelection(Selection element, boolean proccess) {

        if (element instanceof SubQuery) {
            SubQueryImpl subq = (SubQueryImpl) (element);
            //Evalua si tiene un alias
            if (subq.getAlias().equals("")) {
                subq.alias(Constantes.NOMBRE_ALIAS_CAMPO + contadorAlias);
                contadorAlias++;
            }
            if (proccess) {
                subq.proccess();
            }
        }

        if (element instanceof ValueRoot) {
            ValueRoot valueRoot = (ValueRoot) (element);
            if (valueRoot.getAlias().equals("")) {
                valueRoot.alias(Constantes.NOMBRE_ALIAS_CAMPO + contadorAlias);
                contadorAlias++;
            }
            if (proccess) {
                valueRoot.proccessAlias();
            }
        }

        sql.append(element.getSQLTransalte());

    }

    public void proccess() {
        sql = new StringBuilder();
        sql.append(getTypeElementSQL().getNameElement());
        sql.append(Constantes.ESPACIO);
        for (int contador = 0; contador < listParametros.size(); contador++) {
            proccessSelection(listParametros.get(contador),false);
            if (((contador + 1) < listParametros.size()) && (listParametros.size() > 1)) {
                sql.append(Constantes.COMA);
            }          
        }
    }

    public Class getClassToCreate() {
        return classToCreate;
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
