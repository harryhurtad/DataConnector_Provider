/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.excecution;

import com.dataconnector.commons.ElementSQLEnum;
import com.dataconnector.core.DataConnectorFactoryImpl;
import com.dataconnector.criteria.AbstractQuery;
import com.dataconnector.criteria.CriteriaQuery;
import com.dataconnector.criterial.generic.CriteriaQueryImpl;
import com.dataconnector.manager.DataConnectorManager;
import com.dataconnector.query.Query;
import com.dataconnector.obj.DetailMapObjDataConnector;
import com.dataconnector.object.TemporalTypeEnum;
import com.dataconnector.sql.FromImpl;
import com.dataconnector.sql.SelectImpl;
import com.dataconnectorcommons.sql.Selection;
import com.dataconnector.sql.WhereImpl;
import com.dataconnector.utils.Constantes;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa el obj represntante de la setencia SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 29/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class SelectQueryImpl extends AbstractSelectQueryImpl {

    public SelectQueryImpl(AbstractQuery q, DataConnectorManager manager) {

        super(q, manager);

    }

    /**
     * Realiza la extraccion de los parametros;
     *
     */
    public void extractParameters() {

        if (super.query instanceof CriteriaQuery) {
            CriteriaQueryImpl cq = (CriteriaQueryImpl) query;
            proccessSelectStatements(cq.getSelectImpl(), cq.getFromImpl(), cq.getWhereImpl());
        }
    }

  
  

    @Override
    public Object getSingleResult() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Query setParameter(String name, Object value) {
        super.setParameter(name, value);
        return this;
    }

    @Override
    public Query setParameter(String name, Calendar value, TemporalTypeEnum temporalType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
   

    

    /**
     * Realiza la creacion de la sentencia sql
     *
     * @return
     */
    @Override
    public String makeSQLStatementNotPagin() {
        StringBuilder sql = new StringBuilder();
        CriteriaQueryImpl impl = (CriteriaQueryImpl) query;
        FromImpl from = impl.getFromImpl();
        from.proccessJoins();
        WhereImpl where = impl.getWhereImpl();

        sql.append(impl.getSelectImpl().getTranslation());
        sql.append(Constantes.ESPACIO);
        sql.append(from.getTranslation());
        sql.append(Constantes.ESPACIO);
        if (where != null) {
            sql.append(Constantes.ESPACIO);
            sql.append(impl.getWhereImpl().getTranslation());
        }

        // String sql = impl.getSelectImpl().getTranslation() + " " + from.getTranslation() + " " + ;
        System.out.println(sql);

        return sql.toString();

    }

    /**
     * realiza la creacion de la sentencia sql utilizando el parametro count
     *
     * @param q
     * @return
     */
    @Override
    public String makedCountStatemnt(CriteriaQuery q) {

        CriteriaQueryImpl impl = (CriteriaQueryImpl) q;
        StringBuilder builder = new StringBuilder();
        builder.append(ElementSQLEnum.SELECT.toString());
        builder.append(Constantes.ESPACIO);
        builder.append(ElementSQLEnum.COUNT);
        builder.append(Constantes.PARENTECIS_IZQUIERDO);
        builder.append(ElementSQLEnum.ASTERISCO.getNameElement());
        builder.append(Constantes.PARENTECIS_DERECHO);
        builder.append(Constantes.ESPACIO);
        builder.append(Constantes.ALIAS);
        builder.append(Constantes.ESPACIO);
        builder.append(Constantes.ALIAS_CANTIDAD);
        builder.append(Constantes.ESPACIO);
        builder.append(impl.getFromImpl().getTranslation());
        if (impl.getWhereImpl() != null) {
            builder.append(Constantes.ESPACIO);
            builder.append(impl.getWhereImpl().getTypeElementSQL());
        }
        return builder.toString();
    }

 

}
