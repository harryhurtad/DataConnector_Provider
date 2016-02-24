/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.provider;

import com.dataconnector.annotation.DataConnectorPOJO;
import com.dataconnector.sql.ProviderDataConnector;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 *
 * @author proveedor_hhurtado
 */
public class DataConnectorProvider implements ProviderDataConnector {

 

    public void initialContext() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //Evaluar varaibles de retorno
        String basePackage="";
        final Set<String> scannedComponents = new HashSet<String>(); 
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
        scanner.addIncludeFilter(new AnnotationTypeFilter(DataConnectorPOJO.class));
        collectComponentsInClasspath(basePackage, scannedComponents, scanner);
         System.out.println("Clases escaneada...");
        for(String nameClass: scannedComponents){
        
            System.out.println("Clase:"+nameClass);
        }
        
    }

    private void collectComponentsInClasspath(String basePackage, final Set<String> scannedComponents, ClassPathScanningCandidateComponentProvider scanner) {
        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(basePackage)) {
            scannedComponents.add(beanDefinition.getBeanClassName());
        }
    }

   /* private void removeComponentsInPackages(final Set<String> scannedComponents) {
        for (String scannedComponent : scannedComponents) {
            for (String ignoredPackage : ignoredPackages) {
                if (scannedComponent.startsWith(ignoredPackage)) {
                    ignoredClassNames.add(scannedComponent);
                }
            }
        }
    }*/
}
