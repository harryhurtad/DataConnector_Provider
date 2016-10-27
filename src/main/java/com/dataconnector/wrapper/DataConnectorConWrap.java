/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.wrapper;

import com.dataconnector.commons.helper.DataConnectorHelper;
import com.dataconnector.constans.ProvidersSupportEnum;
import com.dataconnector.manager.DataConnectorConnection;
import com.dataconnector.params.obj.ConnectionConf;
import com.dataconnector.params.obj.DriverConf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * {Insert class description here}
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 26/04/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class DataConnectorConWrap implements DataConnectorConnection {

    private  DriverConf driverConf;
    private  DataSource datasource;
    private static final String FILE_NAME_PROPERTIES_DATACONNECTOR = "conf/dataconnector.properties";

    private final String driverName;

    public DataConnectorConWrap(ConnectionConf conf, DataSource datasource) {
     //   this.driverName = driverName;

        if (conf.getDriver_connection() != null) {
            this.driverConf = conf.getDriver_connection();
            this.driverName = driverConf.getDriver();
        } else {
            this.driverName = conf.getJndi_driver();
            this.datasource = datasource;
        }
        /*  Properties prop;
         try {
         prop = DataConnectorHelper.getInstance().readPropertiesDataConnector(FILE_NAME_PROPERTIES_DATACONNECTOR);
         driverNameDatasource = prop.getProperty("driver_datasource");
         } catch (IOException ex) {
         Logger.getLogger(DataConnectorConWrap.class.getName()).log(Level.SEVERE, null, ex);
         }*/

    }

    @SuppressWarnings("empty-statement")
    public Connection getConnection() {
        Connection con = null;

        try {
        
            if (driverConf != null) {
              //  String url = prop.getProperty("url");
                //   String dbUser = prop.getProperty("dbUser");;
                //   String dbPassword = prop.getProperty("dbPassword");
                String url = driverConf.getUrl();
                String dbUser = driverConf.getUser();
                String dbPassword = driverConf.getPassword();
                Class.forName(driverConf.getDriver());
                con = DriverManager.getConnection(url,
                        dbUser,
                        dbPassword);

                //   con=driver.connect(null, null)
            } else if (datasource != null) {
                con = datasource.getConnection();
                //  driverName=provider.toString();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DataConnectorConWrap.class.getName()).log(Level.SEVERE, null, ex);
        }

        return con;
    }

    @Override
    public String getDriverName() {
        return driverName;
    }

   

}
