/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.dataconnector.builder;

import com.dataconnector.sql.CriterialBuilder;
import com.dataconnector.sql.CriterialQuery;
import com.dataconnector.sql.Expression;
import com.dataconnector.sql.Predicate;
import com.prueba.dataconnector.operations.AndOperation;

/**
 *
 * @author proveedor_hhurtado
 */
public class CriterialBuilderImpl implements CriterialBuilder{

  

    @Override
    public Predicate getConjuncion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Predicate getDiyuncion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression equal(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression notEqual(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression mayorQue(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression mayorIgualQue(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression menorQue(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression menorIgualQue(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression between(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression isNull(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression isNotNull(Expression param1, Expression param2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression IN(String[] value) {
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
    public CriterialQuery createQuery() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

    
    
}
