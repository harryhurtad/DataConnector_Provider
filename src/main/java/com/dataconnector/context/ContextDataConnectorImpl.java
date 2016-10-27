/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.context;

import com.dataconnector.commons.anotations.MetadataFielInfoDataConnector;
import com.dataconnector.commons.metadata.MetdataTableDataConn;
import com.dataconnector.constans.ProvidersSupportEnum;
import com.dataconnector.exceptions.InitialCtxDataConnectorException;
import com.dataconnector.manager.ContextDataConnector;

import com.dataconnector.metadata.FieldMetadataDataConnectorImpl;
import com.dataconnector.params.obj.ConnectionConf;
import com.dataconnector.params.obj.ContextConf;
import com.dataconnector.params.obj.DataConnectorConf;
import com.dataconnector.params.obj.DriverConf;
import com.dataconnector.wrapper.DataConnectorConWrap;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * {Insert class description here}
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 9/06/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class ContextDataConnectorImpl  implements ContextDataConnector{

    private final String nameConext;
    private final ContextConf description;
    private DataConnectorConWrap connection;
    private final ProvidersSupportEnum provider;
    private final Map<String, MetdataTableDataConn> mapTables;
    private final ClassLoader classLoader;

    public ContextDataConnectorImpl( ContextConf description,ClassLoader classLoader) throws InitialCtxDataConnectorException {
        this.nameConext = description.getContextName();
        this.description = description;
        this.classLoader=classLoader;        
        mapTables=new HashMap<>();
        //
        proccesConnection(description.getConnection());
        proccessEntitiesTableFromJar(description.getJar_table_location());
        this.provider=ProvidersSupportEnum.valueOf(description.getProvider());
    }

    private void proccesConnection(ConnectionConf connection) {
        DataConnectorConWrap conWrap = null;
        if (connection.getDriver_connection() == null) {
            try {
                DataSource ds = getDataSource(connection.getJndi_name());
                conWrap = new DataConnectorConWrap(connection, ds);
            } catch (Exception ex) {
                Logger.getLogger(ContextDataConnectorImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            conWrap = new DataConnectorConWrap(connection, null);
        }
        this.connection = conWrap;
    }

    private void proccessEntitiesTableFromJar(String jarLoication) throws InitialCtxDataConnectorException {

        //List<String> profiles = new ArrayList<>();
        File jarFile = new File(jarLoication);
        if (!jarFile.exists()) {
            throw new InitialCtxDataConnectorException("Problemas al localizar el jar de la entidades del contexto: " + nameConext);
        }
        //Busca las clases contenidas en el jar
        List<String> listClass = new ArrayList<>();
        try (JarFile jar = new JarFile(jarFile);) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                String entry = entries.nextElement().getName();
                if (entry.endsWith(".class")) {
                    System.out.println(entry);
                    listClass.add(entry);
                }
            }
            //   }
            for (String className : listClass) {
                int i = className.indexOf(".class");
                String nameClass = className.replace("/", ".").substring(0, i);                
                Class classWrapper = Class.forName(nameClass,false, classLoader);
                 MetdataTableDataConn tableInst=(MetdataTableDataConn)classWrapper.newInstance();
                //  Setea el la implementación de FielDataconnector en los fields que representa la tabla 
                for (Field f : classWrapper.getFields()) {
                    MetadataFielInfoDataConnector anotacion = f.getAnnotation(MetadataFielInfoDataConnector.class);
                    Object type = anotacion.type();
                    String nameField = f.getName();
                    FieldMetadataDataConnectorImpl impl = new FieldMetadataDataConnectorImpl(type, nameField);
                    f.set(null, impl);
                }
                //Adiciona las tabla metadata a un map
                mapTables.put(nameClass, tableInst);
            }

        } catch (IOException | ClassNotFoundException | IllegalArgumentException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(ContextDataConnectorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private DataSource getDataSource(String nameJNDI) throws Exception {

        String DATASOURCE_CONTEXT = nameJNDI;
        DataSource datasource = null;
        // result = null;
        try {
            Context initialContext = new InitialContext();
            if (initialContext == null) {
                // System.out.println("Problemas con la obtencion del contexto JNDI " );
                throw new Exception("Problemas con la obtencion del contexto JNDI");
                //  log("JNDI problem. Cannot get InitialContext.");
            }
            datasource = (DataSource) initialContext.lookup(DATASOURCE_CONTEXT);

            if (datasource == null) {
                throw new Exception("Problemas al obtener el contexto");

            }
        } catch (NamingException ex) {
            //   log("Cannot get connection: " + ex);
            System.out.println("Upss Problemas:" + ex);
        }
        return datasource;
    }

    public String getNameConext() {
        return nameConext;
    }

    public ContextConf getDescription() {
        return description;
    }

    @Override
    public DataConnectorConWrap getConnection() {
        return connection;
    }

    @Override
    public ProvidersSupportEnum getProvider() {
        return provider;
    }

    public Map<String, MetdataTableDataConn> getMapTables() {
        return mapTables;
    }

    
}
