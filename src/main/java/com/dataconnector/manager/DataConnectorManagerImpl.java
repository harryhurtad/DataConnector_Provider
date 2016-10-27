/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.manager;


import com.dataconnector.query.Query;
import com.dataconnector.builder.AbstractCriteriaBuilderImpl;
import com.dataconnector.common.ConnectionHelper;
import com.dataconnector.criteria.CriteriaQuery;
import com.dataconnector.criteria.delete.CriteriaDelete;
import com.dataconnector.criteria.insert.CriteriaInsert;
import com.dataconnector.criteria.update.CriteriaUpdate;
import com.dataconnector.criterial.generic.CriteriaQueryImpl;
import com.dataconnector.excecution.SelectQueryImpl;
import com.dataconnector.helper.ValidateSelectQuery;
import com.dataconnector.constans.ProvidersSupportEnum;
import com.dataconnector.context.ContextDataConnectorImpl;
import com.dataconnector.sql.CriteriaBuilder;
import com.dataconnector.wrapper.DataConnectorConWrap;
import java.sql.Connection;

/**
 * Clase que gestiona la creación de los elementos SELECT,INSERT,UPDATE,DELETE de x BD
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 23/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class DataConnectorManagerImpl implements DataConnectorManager {

    private final AbstractCriteriaBuilderImpl builder; 
     private  DataConnectorConWrap connector;
     private final ContextDataConnectorImpl context;
     

    public DataConnectorManagerImpl(CriteriaBuilder builder,ContextDataConnectorImpl context) {
        this.builder = (AbstractCriteriaBuilderImpl) builder;
        this.builder.setDriver(ProvidersSupportEnum.GENERIC);
        this.context=context;
     //   this.connector=connector;
    }

    @Override
    public Query createQuery(CriteriaQuery q) {
        //
        System.out.println("Imprimir construcción del query.....");
        CriteriaQueryImpl implQ = (CriteriaQueryImpl) q;
        SelectQueryImpl impl= new SelectQueryImpl(q,this);
     //  FromImpl from=implQ.getFromImpl();
      /*  from.proccessJoins();
        System.out.println(implQ.getSelectImpl().getTranslation() + " " + from.getTranslation() + " " + implQ.getWhereImpl().getTranslation());
        SelectQueryImpl impl= new SelectQueryImpl(q);*/
        impl.extractParameters();
        //Valida la sintaxis de la sentencia sql
        ValidateSelectQuery validate=ValidateSelectQuery.getInstance();
        validate.validateQuerySelect(implQ.getSelectImpl());
        return impl;
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

    @Override
    public Connection getConnection() {
       return context.getConnection().getConnection();
    }

  
    
    @Override
    public ProvidersSupportEnum getProvidersSupportEnum() {
        return ProvidersSupportEnum.GENERIC;
    }

    @Override
    public ContextDataConnectorImpl getContext() {
        return context;
    }

}
