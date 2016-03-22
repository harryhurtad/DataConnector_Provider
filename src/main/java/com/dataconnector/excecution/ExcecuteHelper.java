/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.excecution;

import com.dataconnector.core.DataConnectorFactoryImpl;
import com.dataconnector.helper.DataConnectorHelper;
import com.dataconnector.obj.DetailMapObjDataConnector;
import com.dataconnector.obj.ParameterConstructClass;
import com.dataconnector.obj.ParameterImpl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {Insert class description here}
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 17/03/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class ExcecuteHelper<X> {

    public void setParameterPrepareStatement(NamedParameterStatement parameter, Map<String, Object> mapParameter) throws SQLException {
        Set<Map.Entry<String, Object>> listValues = mapParameter.entrySet();
        for (Map.Entry<String, Object> entry : listValues) {
            if (entry.getValue() instanceof String) {
                parameter.setString(entry.getKey(), (String) entry.getValue());
            } else if (entry.getValue() instanceof Integer) {

                parameter.setInt(entry.getKey(), (Integer) entry.getValue());

            } else if (entry.getValue() instanceof Double) {
                parameter.setDouble(entry.getKey(), (Double) entry.getValue());
            } else if (entry.getValue() instanceof BigDecimal) {
                parameter.setBigDecimal(entry.getKey(), (BigDecimal) entry.getValue());
            } else if (entry.getValue() instanceof Float) {
                parameter.setFloat(entry.getKey(), (Float) entry.getValue());
            } else if (entry.getValue() instanceof Time) {
                parameter.setTime(entry.getKey(), (Time) entry.getValue());
            } else if (entry.getValue() instanceof java.util.Date) {
                java.sql.Date dateTmp = new Date(((java.util.Date) entry.getValue()).getTime());
                parameter.setDate(entry.getKey(), dateTmp);
            } else if (entry.getValue() instanceof java.sql.Date) {
                parameter.setDate(entry.getKey(), (java.sql.Date) entry.getValue());
            }
        }
    }
    
    
     public List<X> resulsetQueryPrimitiveDataConnector(ResultSet rs, Class objClass, List<ParameterImpl> parameterImpl) throws SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
     
         List<X> listObjReturn = new ArrayList();
          
        while (rs.next()) {
            
           for(ParameterImpl param:parameterImpl){
               X objReturn=null;
               if (param.getParameterType().equals(String.class)) {
                     objReturn  = (X) objClass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{""+rs.getString(param.getNombreParametro())});

                } else if (param.getParameterType().equals(Integer.class)) {
                    objReturn  = (X) objClass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{""+rs.getInt(param.getNombreParametro())});
                } else if (param.getParameterType().equals(Double.class)) {
                     objReturn  = (X) objClass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{""+rs.getDouble(param.getNombreParametro())});
                } else if (param.getParameterType().equals(BigDecimal.class)) {
                   objReturn  = (X) objClass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{""+rs.getBigDecimal(param.getNombreParametro())});
                } else if (param.getParameterType().equals(Float.class)) {
                   objReturn  = (X) objClass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{""+rs.getFloat(param.getNombreParametro())});
                } else if (param.getParameterType().equals(Time.class)) {
                   objReturn  = (X) objClass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{rs.getTime(param.getNombreParametro()).getTime()});
                } else if (param.getParameterType().equals(java.util.Date.class)) {
                   objReturn  = (X) objClass.getConstructor(new Class[]{java.util.Date.class}).newInstance(new Object[]{rs.getDate(param.getNombreParametro()).getTime()});

                } else if (param.getParameterType().equals(java.sql.Date.class)) {
                   objReturn  = (X) objClass.getConstructor(new Class[]{java.sql.Date.class}).newInstance(new Object[]{""+rs.getDate(param.getNombreParametro()).getTime()});
                }
                listObjReturn.add(objReturn);
           }
          
             
        }
         return listObjReturn;
     }
    

    public List<X> resulsetQueryObjDataConnector(ResultSet rs, Class objClass, Set<Map.Entry<String, Class>> listValues) throws SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        Map<String, DetailMapObjDataConnector> mapDetailObj = DataConnectorFactoryImpl.mapObjectProccess.get(objClass.getName());
        List<X> listObjReturn = new ArrayList();
        Object valueReturn = null;
        while (rs.next()) {
            X objReturn = (X) objClass.newInstance();
           
                
              //  objReturn = (X) objClass.getConstructor(paramConstClass.getTypeParameters()).newInstance(paramConstClass.getValueParameters());
          
             
            for (Map.Entry<String, Class> entry : listValues) {
                
                DetailMapObjDataConnector detail = mapDetailObj.get(entry.getKey());

                if (entry.getValue().equals(String.class)) {
                    valueReturn = rs.getString(entry.getKey());

                } else if (entry.getValue().equals(Integer.class)) {
                    valueReturn = rs.getInt(entry.getKey());
                } else if (entry.getValue().equals(Double.class)) {
                    valueReturn = rs.getDouble(entry.getKey());
                } else if (entry.getValue().equals(BigDecimal.class)) {
                    valueReturn = rs.getBigDecimal(entry.getKey());
                } else if (entry.getValue().equals(Float.class)) {
                    valueReturn = rs.getFloat(entry.getKey());
                } else if (entry.getValue().equals(Time.class)) {
                    valueReturn = rs.getTime(entry.getKey());
                } else if (entry.getValue().equals(java.util.Date.class)) {
                    valueReturn = rs.getDate(entry.getKey());

                } else if (entry.getValue().equals(java.sql.Date.class)) {
                    valueReturn = rs.getDate(entry.getKey());
                }
                try {
                    //Set el valor retornado de la instancia
                    DataConnectorHelper.getInstance().invokeMethod(objReturn, objClass.getName(), new Class[]{detail.getCampo().getType()}, detail.getMetodo().getName(), valueReturn);

                } catch (Exception e) {

                }
                
            }
            listObjReturn.add(objReturn);
        }

        return listObjReturn;

    }

}
