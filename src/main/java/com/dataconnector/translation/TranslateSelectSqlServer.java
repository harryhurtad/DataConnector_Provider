/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.translation;

import com.dataconnector.builder.CriteriaSQLServerBuilderImpl;
import com.dataconnector.commons.ElementSQLEnum;
import com.dataconnector.criteria.AbstractQuery;
import com.dataconnector.criteria.CriteriaQuery;
import com.dataconnector.criterial.generic.CriteriaQueryImpl;
import com.dataconnector.excecution.SelectSQLServerQueryImpl;
import com.dataconnector.function.sqlserver.RowNumberFuncImpl;
import com.dataconnector.query.Query;
import com.dataconnector.query.SQLServerQuery;
import com.dataconnector.obj.TranslatePagination;
import com.dataconnector.sql.Expression;
import com.dataconnector.sql.FromImpl;
import com.dataconnector.sql.Selection;
import com.dataconnector.utils.Constantes;

/**
 * {Insert class description here}
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 23/03/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class TranslateSelectSqlServer implements TranslateSelect {

    private final String BETWEEN = "BETWEEN :" + Constantes.POSICION_INICIAL + " AND :" + Constantes.POSICION_FINAL;

    @Override
    public String translate(AbstractQuery query, Query selectImpl) {
        StringBuilder selectStatement = new StringBuilder();
        CriteriaQuery q = (CriteriaQuery) query;
        CriteriaQueryImpl implQ = (CriteriaQueryImpl) q;
        SelectSQLServerQueryImpl qSelect = (SelectSQLServerQueryImpl) selectImpl;
        CriteriaSQLServerBuilderImpl csqlsbi = new CriteriaSQLServerBuilderImpl();

        FromImpl from = implQ.getFromImpl();
        from.proccessJoins();
        // Elabora el select externo
        selectStatement.append(ElementSQLEnum.SELECT);
        selectStatement.append(Constantes.ESPACIO);
       selectStatement.append(ElementSQLEnum.ASTERISCO.getNameElement());
       selectStatement.append(Constantes.ESPACIO);
       selectStatement.append(ElementSQLEnum.FROM);
       selectStatement.append(Constantes.ESPACIO);      
        //Elabora el select interno
        selectStatement.append(Constantes.PARENTECIS_IZQUIERDO);
        RowNumberFuncImpl exp = (RowNumberFuncImpl)csqlsbi.rowNumber(qSelect.getFieldRow(), Constantes.ALIAS_PAGINATION_SQL_SERVER);
        exp.process();
        implQ.getSelectImpl().getListParametros().add(exp);
        implQ.getSelectImpl().proccess();
        selectStatement.append(implQ.getSelectImpl().getTranslation());
        selectStatement.append(Constantes.ESPACIO);
        selectStatement.append(from.getTranslation());
        selectStatement.append(Constantes.ESPACIO);
        if (implQ.getWhereImpl() != null) {
            selectStatement.append(implQ.getWhereImpl().getTranslation());
        }
        //Cierra el select interno
        selectStatement.append(Constantes.PARENTECIS_DERECHO);
        selectStatement.append(Constantes.ESPACIO);
        selectStatement.append(Constantes.ALIAS);
        selectStatement.append(Constantes.ESPACIO);
        selectStatement.append(Constantes.ALIAS_TABLE_SQL_SERVER);
        selectStatement.append(Constantes.ESPACIO);
        //Cierra el select externo 
        selectStatement.append(ElementSQLEnum.WHERE.getNameElement());
        selectStatement.append(Constantes.ESPACIO);
        selectStatement.append(Constantes.ALIAS_PAGINATION_SQL_SERVER);
         selectStatement.append(Constantes.ESPACIO);
        selectStatement.append(BETWEEN);
        return selectStatement.toString();
    }

    @Override
    public TranslatePagination pagination(int posicionInicial, int posicionFinal) {
        TranslatePagination pag = new TranslatePagination(posicionInicial+1, posicionFinal);

        return pag;
    }

}
