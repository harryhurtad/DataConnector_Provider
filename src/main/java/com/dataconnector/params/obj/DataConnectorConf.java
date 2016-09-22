/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dataconnector.params.obj;


import com.dataconnector.commons.anotations.CollectionInfoDataConnector;
import java.util.ArrayList;
import java.util.List;

/**
 *{Insert class description here}
 * @version $Revision: 1.1.1  (UTF-8)
 * @since build 19/05/2016  
 * @author proveedor_hhurtado  email: proveedor_hhurtad@ath.com.co
 */
public class DataConnectorConf {
    @CollectionInfoDataConnector(nameClassGeneric = "com.dataconnector.params.obj.ContextConf")
    private List<ContextConf> context;
    private String package_base;


    public DataConnectorConf() {
        context=new ArrayList<>();
        
    }

    
    
    public List<ContextConf> getContext() {
        return context;
    }

    public void setContext(List<ContextConf> context) {
        this.context = context;
    }

    public String getPackage_base() {
        return package_base;
    }

    public void setPackage_base(String package_base) {
        this.package_base = package_base;
    }


  

    @Override
    public String toString() {
        return "DataConnectorConf{" + "context=" + context + ", name_package=" + package_base + '}';
    }

   
    
    
}
