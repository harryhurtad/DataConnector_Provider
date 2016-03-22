/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.core;

import com.dataconnector.annotation.DataConnectorAttributes;
import com.dataconnector.annotation.DataConnectorPOJO;
import com.dataconnector.builder.CriteriaBuilderImpl;
import com.dataconnector.connection.MetaDataDataconnector;
import com.dataconnector.exceptions.InitialCtxDataConnectorException;
import com.dataconnector.sql.CriteriaBuilder;
import com.dataconnector.helper.DataConnectorHelper;
import com.dataconnector.manager.AbstractDataConnectorManager;
import com.dataconnector.manager.DataConnectorManager;
import com.dataconnector.manager.DataConnectorManagerImpl;
import com.dataconnector.manager.DataConnectorOracleManager;
import com.dataconnector.manager.DataConnectorOracleManagerImpl;
import com.dataconnector.manager.DataConnectorSQLServerManager;
import com.dataconnector.manager.DataConnectorSQLServerManagerImpl;
import com.dataconnector.object.ProvidersSupportEnum;
import com.dataconnector.interfaces.AbstractFactoryDataConnector;
import com.dataconnector.obj.DetailMapObjDataConnector;
import com.dataconnector.utils.Constantes;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

/**
 * Factory que Realiza la creacion de las diferentes configuraciones soportadas
 * por DataConnector
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 23/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class DataConnectorFactoryImpl extends AbstractFactoryDataConnector {

    private AbstractDataConnectorManager instanceManager;
    public static Map<String, Map<String, DetailMapObjDataConnector>> mapObjectProccess;
    private Connection connection;
    private static MetaDataDataconnector dataDataconnector;

    public DataConnectorFactoryImpl() {
        mapObjectProccess = new HashMap<>();
    }

    @Override
    public AbstractDataConnectorManager getDataConnectorManager() {
        return instanceManager;
    }

    /**
     *
     * @throws com.dataconnector.exceptions.InitialCtxDataConnectorException
     */
    @Override
    public void initialContext() throws InitialCtxDataConnectorException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //Obtiene los metadatos de la BD
        System.out.println("->OBTENIENDO METADATA DE LA BD.........");
        dataDataconnector = MetaDataDataconnector.getInstance();
        dataDataconnector.obtieneMetaDataBD();
        System.out.println("****FIN OBTENIENCION METADATA DE LA BD***");
        //Evaluar varaibles de retorno
        DataConnectorHelper.getInstance().printInitDataConnector();

        String basePackage = "";
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

    /**
     * Obtiene el objeto manager de acuerdo al driver seleccionado
     *
     * @param suport
     * @return implementacion de AbstractDataConnectorManager
     */
    public AbstractDataConnectorManager getDataConnectorManager(ProvidersSupportEnum suport) {

        CriteriaBuilder builder = new CriteriaBuilderImpl();

        switch (suport) {
            case ORACLE:

                DataConnectorOracleManager dataConnectorOracleManager = new DataConnectorOracleManagerImpl(builder);
                instanceManager = dataConnectorOracleManager;
                break;
            case SQLSERVER:
                DataConnectorSQLServerManager dataConnectorSQLServerManagerImpl = new DataConnectorSQLServerManagerImpl(builder);
                instanceManager = dataConnectorSQLServerManagerImpl;
                break;
            default:
                DataConnectorManager dataConnectorManagerImpl = new DataConnectorManagerImpl(builder);
                instanceManager = dataConnectorManagerImpl;
                break;
        }

        return instanceManager;
    }

    public void setDataConnectorManager(AbstractDataConnectorManager manager) {
        this.instanceManager = manager;
    }

    public static MetaDataDataconnector getDataDataconnector() {
        return dataDataconnector;
    }

    @Override
    public void initTransaction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void closeTransaction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
