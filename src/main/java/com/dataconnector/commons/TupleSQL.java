/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dataconnector.commons;

/**
 * Interfaz que repesenta los elementos importantes en la creacioon de una sentencia sql
 * @version $Revision: 1.1.1  (UTF-8)
 * @since build 25/02/2016  
 * @author proveedor_hhurtado  email: proveedor_hhurtad@ath.com.co
 */
public interface TupleSQL {

    ElementSQLEnum getTypeElementSQL();
    String getTranslation();
    
}
