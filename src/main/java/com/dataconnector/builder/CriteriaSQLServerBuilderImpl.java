/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dataconnector.builder;

import com.dataconnector.context.ContextDataConnectorImpl;
import com.dataconnector.criteria.AbstractQuery;
import com.dataconnector.criteria.SubQuery;
import com.dataconnector.criteria.delete.CommonAbstractDelete;
import com.dataconnector.criteria.insert.CommonAbstractInsert;
import com.dataconnector.criteria.update.CommonAbstractUpdate;
import com.dataconnector.function.sqlserver.RowNumberFuncImpl;
import com.dataconnector.object.ValueExpression;
import com.dataconnector.object.ValueRoot;
import com.dataconnector.sql.CriteriaSQLServerBuilder;
import com.dataconnectorcommons.sql.Expression;
import com.dataconnector.sql.JoinPredicate;
import com.dataconnector.sql.ParameterExpression;
import com.dataconnector.sql.Predicate;

/**
 *{Insert class description here}
 * @version $Revision: 1.1.1  (UTF-8)
 * @since build 23/03/2016  
 * @author proveedor_hhurtado  email: proveedor_hhurtad@ath.com.co
 */
public class CriteriaSQLServerBuilderImpl extends AbstractCriteriaBuilderImpl implements CriteriaSQLServerBuilder{

    
     public CriteriaSQLServerBuilderImpl(ContextDataConnectorImpl context ){
        super(context);
    }
     
    public CriteriaSQLServerBuilderImpl(){
    
    } 
    @Override
    public Expression rowNumber(ValueRoot field,String alias) {
        RowNumberFuncImpl impl=new RowNumberFuncImpl(field, alias);
        return impl;        
    }

    @Override
    public CommonAbstractInsert createInserQuery() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CommonAbstractDelete createDeleterQueryFactory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CommonAbstractUpdate createUpdaterQueryFactory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

   
}
