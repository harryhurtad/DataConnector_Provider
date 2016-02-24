/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.obj;

import com.dataconnector.sql.Expression;
import com.dataconnector.sql.OperationEnum;
import com.dataconnector.sql.Parameter;
import java.util.List;

/**
 *
 * @author proveedor_hhurtado
 */
public class ParameterImpl implements Parameter {

    private String nombreParametro;
    
    @Override
    public void atributo(String nombreAtributo) {
        this.nombreParametro=nombreAtributo;
    }

    public String getNombreParametro() {
        return nombreParametro;
    }

   
    
}
