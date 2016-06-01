/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dataconnector.commons;

/**
 * Enum que represetna los elementos claves de una sentencia sql
 * @version $Revision: 1.1.1  (UTF-8)
 * @since build 25/02/2016  
 * @author proveedor_hhurtado  email: proveedor_hhurtad@ath.com.co
 */
public enum ElementSQLEnum {

   SELECT("SELECT"),WHERE("WHERE"),FROM("FROM"),ORDER_BY("ORDER BY"),
   GROUP_BY("GROUPE BY"),ON("ON"),JOIN("JOIN"),COUNT("COUNT"),ASTERISCO("*");
   private String nameElement;

    private ElementSQLEnum(String nameElement) {
        this.nameElement = nameElement;
    }

    public String getNameElement() {
        return nameElement;
    }

    public void setNameElement(String nameElement) {
        this.nameElement = nameElement;
    }
   
    
   
}
