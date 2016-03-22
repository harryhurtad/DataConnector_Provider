/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dataconnector.core;

import com.dataconnector.connection.MetaDataDataconnector;

/**
 *{Insert class description here}
 * @version $Revision: 1.1.1  (UTF-8)
 * @since build 9/03/2016  
 * @author proveedor_hhurtado  email: proveedor_hhurtad@ath.com.co
 */
public class DataConnectorMetaModel {

    private MetaDataDataconnector instance;
    
    
    
   public void getMetadataFromBD(){
       System.out.println("->OBTENIENDO METADATA DE LA BD.........");
       instance=MetaDataDataconnector.getInstance();
       instance.obtieneMetaDataBD();       
       System.out.println("****FIN OBTENIENCION METADATA DE LA BD***");
   }
    
}
