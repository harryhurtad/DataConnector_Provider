/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.translation;

import com.dataconnector.criteria.AbstractQuery;
import com.dataconnector.criteria.CriteriaQuery;
import com.dataconnector.criterial.generic.CriteriaQueryImpl;
import com.dataconnector.obj.TranslatePagination;
import com.dataconnector.sql.FromImpl;
import com.dataconnector.utils.Constantes;

/**
 * {Insert class description here}
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 14/03/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class TranslateSelectMySQL implements TranslateSelect {

    private final String LIMIT = "LIMIT :" + Constantes.POSICION_INICIAL + ",:" + Constantes.POSICION_FINAL;

    @Override
    public String translate(AbstractQuery query) {
        StringBuilder selectStatement = new StringBuilder();
        CriteriaQuery q = (CriteriaQuery) query;
        CriteriaQueryImpl implQ = (CriteriaQueryImpl) q;
        FromImpl from = implQ.getFromImpl();
        from.proccessJoins();
        //
        selectStatement.append(implQ.getSelectImpl().getTranslation());
        selectStatement.append(Constantes.ESPACIO);
        selectStatement.append(from.getTranslation());
        selectStatement.append(Constantes.ESPACIO);
        if (implQ.getWhereImpl() != null) {
            selectStatement.append(implQ.getWhereImpl().getTranslation());
        }
        selectStatement.append(LIMIT);

        return selectStatement.toString();
    }
    
    @Override
    public TranslatePagination pagination(int posicionInicial,int posicionFinal){
        TranslatePagination pag=new TranslatePagination(posicionInicial, (posicionFinal - posicionInicial));
    
        return pag;
    }

}
