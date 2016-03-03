/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.manager;

import com.dataconnector.builder.CriteriaBuilderImpl;
import com.dataconnector.criteria.AbstractQuery;
import com.dataconnector.criteria.CriteriaQuery;
import com.dataconnector.criteria.delete.CommonAbstractDelete;
import com.dataconnector.criteria.delete.CriteriaDelete;
import com.dataconnector.criteria.insert.CriteriaInsert;
import com.dataconnector.criteria.update.CommonAbstractUpdate;
import com.dataconnector.criteria.update.CriteriaUpdate;
import com.dataconnector.criterial.generic.CriteriaQueryImpl;
import com.dataconnector.excecution.SelectQueryImpl;
import com.dataconnector.object.ProvidersSupportEnum;
import com.dataconnector.sql.CriteriaBuilder;
import com.dataconnector.sql.FromImpl;

/**
 * Clase que gestiona la creación de los elementos SELECT,INSERT,UPDATE,DELETE de x BD
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 23/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class DataConnectorManagerImpl implements DataConnectorManager {

    private final CriteriaBuilderImpl builder;

    public DataConnectorManagerImpl(CriteriaBuilder builder) {
        this.builder = (CriteriaBuilderImpl) builder;
        this.builder.setDriver(ProvidersSupportEnum.GENERIC);
    }

    @Override
    public Query createQuery(CriteriaQuery q) {
        //
        System.out.println("Imprimir construcción del query.....");
        CriteriaQueryImpl implQ = (CriteriaQueryImpl) q;
        FromImpl from=implQ.getFromImpl();
        from.proccessJoins();
        System.out.println(implQ.getSelectImpl().getTranslation() + " " + from.getTranslation() + " " + implQ.getWhereImpl().getTranslation());
        return new SelectQueryImpl();
    }

    @Override
    public Query createQuery(CriteriaInsert q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Query createQuery(CriteriaDelete q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Query createQuery(CriteriaUpdate q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CriteriaBuilder getCriterialBuilder() {
        return builder;
    }

    @Override
    public String QuiEst() {

        return "DataConnectorGenerico";
    }

}
