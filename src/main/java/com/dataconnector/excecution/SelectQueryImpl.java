/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dataconnector.excecution;

import com.dataconnector.manager.Query;

/**
 *Clase que representa el obj represntante de la setencia SQL
 * @version $Revision: 1.1.1  (UTF-8)
 * @since build 29/02/2016  
 * @author proveedor_hhurtado  email: proveedor_hhurtad@ath.com.co
 */
public class SelectQueryImpl implements Query<Object>{

    @Override
    public Object getSingleResult() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
