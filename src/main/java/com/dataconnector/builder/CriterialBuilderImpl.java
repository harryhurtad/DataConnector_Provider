/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.builder;

import com.dataconnector.sql.CriteriaBuilder;
import com.dataconnector.criteria.CriteriaQuery;
import com.dataconnector.sql.Expression;
import com.dataconnector.sql.Predicate;
import com.dataconnector.operations.AndOperation;

/**
 *
 * @author proveedor_hhurtado
 */
public class CriterialBuilderImpl implements CriteriaBuilder{

  

    @Override
    public Predicate getConjuncion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Predicate getDiyuncion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

    @Override
    public Predicate and(Expression param1, Expression param2) {
        Predicate andOper=new AndOperation();
        return null;
                
    }

    @Override
    public Predicate and(Predicate... parametros) {
         
        return null;
    }

    @Override
    public Predicate or(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Predicate or(Predicate... parametros) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CriteriaQuery createQuery() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Predicate equal(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Predicate notEqual(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Predicate mayorQue(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Predicate mayorIgualQue(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Predicate menorQue(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Predicate menorIgualQue(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Predicate between(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Predicate isNull(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Predicate isNotNull(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Predicate IN(String[] value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

    
    
}
