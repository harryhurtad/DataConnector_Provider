/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.context;

import com.dataconnector.annotation.DataConnectorAttributes;
import com.dataconnector.annotation.DataConnectorPOJO;
import com.dataconnector.connection.MetaDataDataconnector;
import com.dataconnector.core.DataConnectorFactoryImpl;
import com.dataconnector.exceptions.InitialCtxDataConnectorException;
import com.dataconnector.helper.DataConnectorConWrap;
import com.dataconnector.helper.DataConnectorHelper;
import com.dataconnector.manager.InitialContextDataConnector;
import com.dataconnector.obj.DetailMapObjDataConnector;
import com.dataconnector.utils.Constantes;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

/**
 * {Insert class description here}
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 27/04/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */

public class InitialContextDataconnectorImpl implements InitialContextDataConnector {

 
    public Map<String, Map<String, DetailMapObjDataConnector>> mapObjectProccess;
    private  MetaDataDataconnector dataDataconnector;
    private DataConnectorConWrap connection;

    public InitialContextDataconnectorImpl(DataConnectorConWrap connection) {
           this.connection=connection;
           mapObjectProccess = new HashMap<>();
    }


    
    
    @Override
    public Map<String, Map<String, DetailMapObjDataConnector>> getMapObjectProccess(){
    
        return mapObjectProccess;
    
    }

    @Override
    public void initialContext() throws InitialCtxDataConnectorException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       DataConnectorHelper.getInstance().printInitDataConnector();
        //Obtiene los metadatos de la BD
        System.out.println("->OBTENIENDO METADATA DE LA BD.........");
        String nameSchemma="";
        String basePackage = "";
        try {
            Properties prop = DataConnectorHelper.getInstance().readPropertiesDataConnector(Constantes.FILE_NAME_PROPERTIES_DATACONNECTOR);
            nameSchemma=prop.getProperty("nameShemma");
            basePackage=prop.getProperty("packageBase");
            if(nameSchemma==null){}
        } catch (IOException ex) {
            Logger.getLogger(InitialContextDataconnectorImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new InitialCtxDataConnectorException("Problemas el archivo de propiedades del dataconnector");
        } 
         System.out.println("****Prueba!!!***");
        dataDataconnector = new MetaDataDataconnector(nameSchemma, connection.getConnection(), connection.getDriverName(),connection.getProvider());
        dataDataconnector.obtieneMetaDataBD();
        System.out.println("****FIN OBTENIENCION METADATA DE LA BD***");
        //Evaluar varaibles de retorno
        

       
        final Set<String> scannedComponents = new HashSet<>();
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
        scanner.addIncludeFilter(new AnnotationTypeFilter(DataConnectorPOJO.class));
        collectComponentsInClasspath(basePackage, scannedComponents, scanner);
        System.out.println("***************Clases escaneadas...");
        for (String nameClass : scannedComponents) {

            System.out.println("Nombre clase:" + nameClass);
            try {
                extractFieldFromClass(nameClass);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DataConnectorFactoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(mapObjectProccess);

    }

    /**
     *
     * @param nameClass
     */
    private void extractFieldFromClass(String nameClass) throws ClassNotFoundException, InitialCtxDataConnectorException {
        boolean methodSetFind;
        Class className = Class.forName(nameClass);
        Field[] fields = className.getDeclaredFields();
        Map<String, DetailMapObjDataConnector> listaDetail = new HashMap<>();
        for (Field campo : fields) {
            Annotation[] annotatios = campo.getAnnotations();

            for (Annotation anotacion : annotatios) {
                if (anotacion instanceof DataConnectorAttributes) {
                    // Busca el método set correspondiente al campo

                    String setterName = "set" + StringUtils.capitalize(campo.getName());
                    Method metodo = null;
                    methodSetFind = false;
                    DetailMapObjDataConnector detail;
                    for (Method metodoTmp : className.getDeclaredMethods()) {
                        if (metodoTmp.getName().equals(setterName)) {
                            methodSetFind = true;
                            metodo = metodoTmp;
                            break;
                        }
                    }
                    //Si encuentra el campo correspondiente a us accessor lo inserta
                    if (methodSetFind) {
                        detail = new DetailMapObjDataConnector(campo, metodo, anotacion);
                        listaDetail.put(campo.getName(), detail);
                    } else {
                        InitialCtxDataConnectorException ex = new InitialCtxDataConnectorException(Constantes.MSN_EXCEPTION_INITIAL_CONTEXT_MAPEO);
                        throw ex;
                    }

                    //  System.out.println("Nombre campo: " + campo.getName());
                }
            }
        }
        mapObjectProccess.put(nameClass, listaDetail);
    }

    private void collectComponentsInClasspath(String basePackage, final Set<String> scannedComponents, ClassPathScanningCandidateComponentProvider scanner) {
        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(basePackage)) {
            scannedComponents.add(beanDefinition.getBeanClassName());
        }

    }
    
  
    public  MetaDataDataconnector getDataDataconnector() {
        return dataDataconnector;
    }

    @Override
    public DataConnectorConWrap getDataConnectorConWrap(){
            return this.connection;
    }
}
