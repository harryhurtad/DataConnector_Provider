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
public class DriverConf {
    public String user;
    public String driver;
    private String password;
    private String url;
    private String name_schema;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName_schema() {
        return name_schema;
    }

    public void setName_schema(String name_schema) {
        this.name_schema = name_schema;
    }

    @Override
    public String toString() {
        return "DriverConf{" + "user=" + user + ", driver=" + driver + ", password=" + password + ", url=" + url + ", name_schema=" + name_schema + '}';
    }
    
    
}
