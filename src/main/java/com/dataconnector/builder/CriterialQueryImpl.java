/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.builder;

import com.dataconnector.criteria.CriteriaQuery;
import com.dataconnector.sql.Predicate;
import com.dataconnector.sql.Root;
import com.dataconnector.sql.Selection;
import java.util.List;

/**
 *
 * @author proveedor_hhurtado
 */
public class CriterialQueryImpl implements CriteriaQuery{

    
    
    
    @Override
    public CriteriaQuery select(Selection[] params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CriteriaQuery where(Predicate[] params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CriteriaQuery from(List<Root> entities) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
