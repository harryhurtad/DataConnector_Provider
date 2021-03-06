/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.operations;

import com.dataconnector.object.ValueRoot;
import com.dataconnectorcommons.sql.Expression;
import com.dataconnector.sql.JoinPredicate;
import com.dataconnector.sql.OperationEnum;
import com.dataconnector.sql.Predicate;
import com.dataconnector.utils.Constantes;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion que representa el operador NOT EQUALS(!=) del lenguaje SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 26/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public final class NotEqualOperation extends AbstractOperPredicate implements JoinPredicate  {

    public NotEqualOperation() {
        super(OperationEnum.NOTEQUALS);
       
    }

    
     /**
     * Realiza la conversión de obj a sintaxis sql
     * @param param1
     * @param param2
     * @return 
     */
    @Override
    public Predicate translateOperation(Expression param1, Expression param2) {
       proccessTranslate(param1, param2);
       return this;
    }


      public JoinPredicate translateJoinOperation(ValueRoot param1, ValueRoot param2) {
        proccessTranslate(param1, param2);
        return this;
    }
}
