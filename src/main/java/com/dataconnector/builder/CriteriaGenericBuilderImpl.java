/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dataconnector.builder;

import com.dataconnector.criteria.delete.CommonAbstractDelete;
import com.dataconnector.criteria.insert.CommonAbstractInsert;
import com.dataconnector.criteria.update.CommonAbstractUpdate;

/**
 *{Insert class description here}
 * @version $Revision: 1.1.1  (UTF-8)
 * @since build 6/05/2016  
 * @author proveedor_hhurtado  email: proveedor_hhurtad@ath.com.co
 */
public class CriteriaGenericBuilderImpl extends AbstractCriteriaBuilderImpl {

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
