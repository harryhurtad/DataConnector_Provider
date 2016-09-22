/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.helper;

import com.dataconnector.annotation.DataConnectorAttributes;
import com.dataconnector.commons.TupleSQL;
import com.dataconnector.context.InitialContextDataconnectorImpl;
import com.dataconnector.core.DataConnectorFactoryImpl;
import com.dataconnector.exceptions.InitialCtxDataConnectorException;
import com.dataconnector.obj.DetailMapObjDataConnector;
import com.dataconnector.sql.SelectImpl;
import com.dataconnectorcommons.sql.Selection;
import com.dataconnector.utils.Constantes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {Insert class description here}
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 15/03/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class ValidateSelectQuery {

    private static ValidateSelectQuery instance;

    private ValidateSelectQuery() {

    }

    public static ValidateSelectQuery getInstance() {
        if (instance == null) {
            instance = new ValidateSelectQuery();
        }
        return instance;

    }

    public void validateQuerySelect(SelectImpl select)  {

       
            //Obtiene el obj de retorno a mapear
            Class clase = select.getClassToCreate();
            //validar que no exista campos repetidos
            int countError = 0;
            Map<String, DetailMapObjDataConnector> listDetailObjectMap = (Map<String, DetailMapObjDataConnector>) InitialContextDataconnectorImpl.mapObjectProccess.get(clase.getName());
            
            /*  for(DetailMapObjDataConnector detail:listDetailObjectMap){
            
            DataConnectorAttributes attributes=(DataConnectorAttributes)detail.getAnotacion();
            listNameParams.add(attributes.name());
            }*/
            //
            StringBuilder builder = new StringBuilder();
            
            for (Selection selection : select.getListParametros()) {
                
                if (!listDetailObjectMap.containsKey(selection.getAlias())) {
                    if (countError == 0) {
                        builder.append(Constantes.MSN_EXCEPTION_VALIDATE_QUERY);
                        builder.append(Constantes.ESPACIO);
                    }
                    builder.append(selection.getAlias());
                    builder.append(Constantes.ESPACIO);
                }

            }

            if (!builder.toString().equals("")) {
                throw new RuntimeException(builder.toString());
            }
            //valida
            //  if 
         

    }
}
