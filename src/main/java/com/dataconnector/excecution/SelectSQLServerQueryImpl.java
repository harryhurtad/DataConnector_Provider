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
import com.dataconnector.function.sqlserver.RowNumberFuncImpl;
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
 * {Insert class description here}
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 22/03/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class SelectSQLServerQueryImpl extends AbstractSelectQueryImpl implements SQLServerQuery<Object> {

    private boolean  isSelectForRowNumber;
    private Integer maxDop;
    private boolean nolock = false;
    private ValueRoot fieldRowIndex;
    
    public SelectSQLServerQueryImpl(AbstractQuery q, DataConnectorSQLServerManager manager) {
        super(q, manager);
    }

   

    public void setIsSelectForRowNumber(boolean isSelectForRowNumber) {
        this.isSelectForRowNumber = isSelectForRowNumber;
    }

  

    public boolean isNolock() {
        return nolock;
    }

    @Override
    public void setWithNotLock(boolean root) {
        this.nolock = root;
    }

    public String makeSQLStatementWithNotLock() {

        StringBuilder builder = new StringBuilder();
        builder.append(Constantes.ESPACIO);
        builder.append(Constantes.WITHNOLOCK_FUNCTION);
        builder.append(Constantes.ESPACIO);

        return builder.toString();

    }

    /**
     * Realiza la creacion de la sentencia sql sin paginacion
     *
     * @return
     */
    @Override
    public String makeSQLStatementNotPagin() {
        StringBuilder sql = new StringBuilder();
        CriteriaQueryImpl impl = (CriteriaQueryImpl) query;
        CriteriaSQLServerBuilderImpl csqlsbi = new CriteriaSQLServerBuilderImpl();
        FromImpl from = impl.getFromImpl();
        from.proccessJoins();
        WhereImpl where = impl.getWhereImpl();

       
        //Si tiene habilida la opcion RowNumber
        if (this.isIsSelectForRowNumber()) {
            SelectSQLServerQueryImpl qSelect = ((SelectSQLServerQueryImpl) this);
            RowNumberFuncImpl exp = (RowNumberFuncImpl) csqlsbi.rowNumber(qSelect.getFieldRowIndex(), Constantes.ALIAS_PAGINATION_SQL_SERVER);
            exp.process();
            impl.getSelectImpl().getListParametros().add(exp);
            impl.getSelectImpl().proccess();
            sql.append(impl.getSelectImpl().getTranslation());
        }else{
             sql.append(impl.getSelectImpl().getTranslation());
        }
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

    public String makeSQLStatementMaxDop() {

        StringBuilder builder = new StringBuilder();
        builder.append(Constantes.ESPACIO);
        builder.append(Constantes.OPTION_FUNCTION);
        builder.append(Constantes.PARENTECIS_IZQUIERDO);
        builder.append(Constantes.MAXDOP_FUNCTION);
        builder.append(Constantes.ESPACIO);
        builder.append(getMaxDop());
        builder.append(Constantes.ESPACIO);
        builder.append(Constantes.PARENTECIS_DERECHO);
        builder.append(Constantes.ESPACIO);

        return builder.toString();
    }


    

    @Override
    public void setMaxDop(Integer noProcesor) {
        this.maxDop = noProcesor;
    }

    public Integer getMaxDop() {

        return maxDop;
    }

    @Override
    public void setFieldRowIndex(ValueRoot field) {
        fieldRowIndex=field;
    }

    public ValueRoot getFieldRowIndex() {
        return fieldRowIndex;
    }

    public boolean isIsSelectForRowNumber() {
        return isSelectForRowNumber;
    }

    @Override
    public void isSelectForRowNumber(boolean responce) {
        this.isSelectForRowNumber=responce;
    }
    
    

}
