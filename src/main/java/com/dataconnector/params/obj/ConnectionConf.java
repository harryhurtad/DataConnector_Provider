/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dataconnector.params.obj;

/**
 *{Insert class description here}
 * @version $Revision: 1.1.1  (UTF-8)
 * @since build 19/05/2016  
 * @author proveedor_hhurtado  email: proveedor_hhurtad@ath.com.co
 */
public class ConnectionConf {
    private String jndi_name;
    private String jndi_driver;
    private DriverConf driver_connection;

    public ConnectionConf() {
       
    }
    
    
    
    public String getJndi_name() {
        return jndi_name;
    }

    public void setJndi_name(String jndi_name) {
        this.jndi_name = jndi_name;
    }

    public String getJndi_driver() {
        return jndi_driver;
    }

    public void setJndi_driver(String jndi_driver) {
        this.jndi_driver = jndi_driver;
    }

    public DriverConf getDriver_connection() {
        return driver_connection;
    }

    public void setDriver_connection(DriverConf driver_connection) {
        this.driver_connection = driver_connection;
    }

    @Override
    public String toString() {
        return "ConnectionConf{" + "jndi_name=" + jndi_name + ", jndi_driver=" + jndi_driver + ", driver_connection=" + driver_connection + '}';
    }

   
    
    
}
