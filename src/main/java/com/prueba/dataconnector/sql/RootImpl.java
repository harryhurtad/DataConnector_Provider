/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.dataconnector.sql;

import com.dataconnector.sql.Expression;
import com.dataconnector.sql.OperationEnum;
import com.dataconnector.sql.ParameterExpression;
import com.dataconnector.sql.Root;
import java.util.List;

/**
 *
 * @author proveedor_hhurtado
 */
public class RootImpl implements Root{

    @Override
    public Boolean isCompound() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression get(String nombreParametro) {
       
        
        return new ParameterExpression() {
             private String nombreParametro;
            @Override
            public void atributo(String nombreAtributo) {
                this.nombreParametro=nombreAtributo;
            }

            public String getNombreParametro() {
                return nombreParametro;
            }

            
            @Override
            public void setOperation(OperationEnum operation) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public OperationEnum getOperation() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setListExpression(List<Expression> listExp) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public StringBuilder getSql() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
    }
    
}
