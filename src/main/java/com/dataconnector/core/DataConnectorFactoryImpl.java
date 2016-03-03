/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.core;

import com.dataconnector.annotation.DataConnectorPOJO;
import com.dataconnector.builder.CriteriaBuilderImpl;
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
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * Factory que Realiza la creacion de las diferentes configuraciones soportadas por DataConnector
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 23/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class DataConnectorFactoryImpl extends AbstractFactoryDataConnector {

    private AbstractDataConnectorManager instanceManager;

    @Override
    public AbstractDataConnectorManager getDataConnectorManager() {
        return instanceManager;
    }

    /**
     * 
     */
    @Override
    public void initialContext() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //Evaluar varaibles de retorno
        DataConnectorHelper.getInstance().printInitDataConnector();
        
        String basePackage = "";
        final Set<String> scannedComponents = new HashSet<>();
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
        scanner.addIncludeFilter(new AnnotationTypeFilter(DataConnectorPOJO.class));
        collectComponentsInClasspath(basePackage, scannedComponents, scanner);
        System.out.println("Clases escaneada...");
        for (String nameClass : scannedComponents) {

            System.out.println("Clase:" + nameClass);
        }

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
     * @return implementacion de  AbstractDataConnectorManager 
     */
    public AbstractDataConnectorManager getDataConnectorManager(ProvidersSupportEnum suport) {

        
       CriteriaBuilder builder=new CriteriaBuilderImpl();

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
         this.instanceManager=manager;
    }
}
