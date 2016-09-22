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
public class ContextConf {

    private String contextName;
    private String jar_table_location;
    private String provider;
    private ConnectionConf connection;

    public ContextConf() {
        connection=new ConnectionConf();
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

   
    public ConnectionConf getConnection() {
        return connection;
    }

    public void setConnection(ConnectionConf connection) {
        this.connection = connection;
    }

    public String getJar_table_location() {
        return jar_table_location;
    }

    public void setJar_table_location(String jar_table_location) {
        this.jar_table_location = jar_table_location;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    
    
    @Override
    public String toString() {
        return "ContextConf{" + "contextName=" + contextName + ", connection=" + connection + '}';
    }

   
    
    
    
    
   
    
    

}
