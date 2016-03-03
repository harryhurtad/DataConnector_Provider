/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.operations;

import com.dataconnector.sql.Expression;
import com.dataconnector.sql.JoinPredicate;
import com.dataconnector.sql.OperationEnum;
import com.dataconnector.sql.Predicate;

/**
 * Implementacion que representa la disyuncion(O) del lenguaje SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 26/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class OrOperation extends AbstractOperPredicate implements JoinPredicate{

    public OrOperation() {
     super(OperationEnum.OR);
    }

   
    public Predicate translateExpression(Predicate[] parameters) {
        super.proccessTranslate(parameters);
        return this;
    }
    
     public JoinPredicate translateExpression(JoinPredicate[] parameters) {
        super.proccessTranslate(parameters);
        return this;
    }

    @Override
    public Predicate translateOperation(Expression param1, Expression param2) {
        proccessTranslate(param1, param2);
       return this;
    }

     public JoinPredicate translateOperation(JoinPredicate param1, JoinPredicate param2) {
        proccessTranslate(param1, param2);
       return this;
    }
    
}
