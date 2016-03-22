/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dataconnector.obj;

/**
 *{Insert class description here}
 * @version $Revision: 1.1.1  (UTF-8)
 * @since build 18/03/2016  
 * @author proveedor_hhurtado  email: proveedor_hhurtad@ath.com.co
 */
public class ParameterConstructClass {

    private final Class[] typeParameters;
    private final Object[] valueParameters;

    public ParameterConstructClass(Class[] typeParameters, Object[] valueParameters) {
        this.typeParameters = typeParameters;
        this.valueParameters = valueParameters;
    }

    public Class[] getTypeParameters() {
        return typeParameters;
    }

    public Object[] getValueParameters() {
        return valueParameters;
    }
    
    
    
}
