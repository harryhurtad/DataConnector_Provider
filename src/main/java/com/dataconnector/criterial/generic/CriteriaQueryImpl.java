/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.criterial.generic;

import com.dataconnector.criteria.CriteriaQuery;
import com.dataconnector.sql.FromImpl;
import com.dataconnector.sql.Order;
import com.dataconnector.sql.Predicate;
import com.dataconnector.sql.Root;
import com.dataconnector.sql.RootImpl;
import com.dataconnector.sql.Selection;
import com.dataconnector.sql.SelectImpl;
import com.dataconnector.sql.WhereImpl;

/**
 * Implmentación de la interfaz CriteriaQuery que permite la creación de las tuples sql de un query SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 23/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class CriteriaQueryImpl implements CriteriaQuery {

    private final SelectImpl selectImpl;
    private final FromImpl fromImpl;
    private WhereImpl whereImpl;
    private int countAliasTable = 0;

    public CriteriaQueryImpl() {

        fromImpl = new FromImpl();
        selectImpl = new SelectImpl();
    }

    @Override
    public CriteriaQuery select(Selection[] params) {

        // for (Selection elemento : params) {
        selectImpl.proccess(params);
        //}

        return this;
    }

    @Override
    public CriteriaQuery where(Predicate params) {
        whereImpl = new WhereImpl();
        whereImpl.proccess(params);
        return this;
    }

    @Override
    public Root from(String nombreTabla) {
        countAliasTable++;
        RootImpl impl = new RootImpl("t".concat("" + countAliasTable), nombreTabla);
        fromImpl.proccess(impl);
        return impl;
    }

    @Override
    public CriteriaQuery orderBy(Order... ord) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public SelectImpl getSelectImpl() {
        return selectImpl;
    }

    public FromImpl getFromImpl() {
        return fromImpl;
    }

    public WhereImpl getWhereImpl() {
        return whereImpl;
    }

}