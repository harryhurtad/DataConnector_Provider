/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dataconnector.excecution;

import com.dataconnector.builder.CriteriaSQLServerBuilderImpl;
import com.dataconnector.commons.ElementSQLEnum;
import com.dataconnector.criteria.AbstractQuery;
import com.dataconnector.criteria.CriteriaQuery;
import com.dataconnector.criterial.generic.CriteriaQueryImpl;

import com.dataconnector.exceptions.DataConnectorResultException;
import com.dataconnector.manager.DataConnectorManager;
import com.dataconnector.manager.DataConnectorSQLServerManager;
import com.dataconnector.query.Query;
import com.dataconnector.query.SQLServerQuery;
import com.dataconnector.object.TemporalTypeEnum;
import com.dataconnector.object.ValueRoot;
import com.dataconnector.sql.FromImpl;
import com.dataconnector.sql.WhereImpl;
import com.dataconnector.utils.Constantes;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 *{Insert class description here}
 * @version $Revision: 1.1.1  (UTF-8)
 * @since build 22/03/2016  
 * @author proveedor_hhurtado  email: proveedor_hhurtad@ath.com.co
 */
public class SelectSQLServerQueryImpl extends AbstractSelectQueryImpl implements SQLServerQuery<Object> {

    private ValueRoot fieldRow;
    
    public SelectSQLServerQueryImpl(AbstractQuery q, DataConnectorSQLServerManager manager) {
        super(q, manager);
    }
 
     

    
    @Override
    public void setFieldRowNumber(ValueRoot field) {
        fieldRow=field;
    }

    @Override
    public void setWithNotLock(boolean root) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   /**
    * Realiza la creacion de la sentencia sql sin paginacion
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
        builder.append(Constantes.ALIAS);
        builder.append(Constantes.ESPACIO);
        builder.append(Constantes.ALIAS_CANTIDAD);
        builder.append(Constantes.ESPACIO);
        
        builder.append(impl.getFromImpl().getTranslation());
        if (impl.getWhereImpl() != null) {
            builder.append(Constantes.ESPACIO);
            builder.append(impl.getWhereImpl().getTranslation());
        }
        return builder.toString();
    }

    public void setFieldRow(ValueRoot fieldRow) {
        this.fieldRow = fieldRow;
    }

    public ValueRoot getFieldRow() {
        return fieldRow;
    }

    
}
