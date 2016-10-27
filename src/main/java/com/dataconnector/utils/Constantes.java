/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.utils;

/**
 * Constantes comunes manejadas por la implementacion del DataConnector
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 26/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class Constantes {

    private Constantes() {

    }

    public static String ESPACIO = " ";
    public static String PARENTECIS_DERECHO = ")";
    public static String PARENTECIS_IZQUIERDO = "(";
    public static String COMA = ",";
    public static String SALTO_DE_LINEA = "\n";
    public static String ALIAS="AS";
    public static String NOMBRE_ALIAS_CAMPO="F";
    public static final String OVER_OPERATOR="OVER";
    public static final String ALIAS_PAGINATION_SQL_SERVER="NUMEROCALDA";
    public static final String ALIAS_TABLE_SQL_SERVER="TMP_TABLE";
    public static final String ALIAS_CANTIDAD="CANTIDAD";
    public static String POSICION_INICIAL="posicionInicial";
    public static String POSICION_FINAL="posicionFinal";
    
    public static String MSN_EXCEPTION_INITIAL_CONTEXT_MAPEO="ERROR AL ANALIZAR LA CLASE A MAPEAR: Verifique que los atributos que usan la anotacion @DataConnectorAttributes tengan su correspondiente accesor set( Ejemplo String deptNo ,  public void setDeptNo(String deptNo))";
    public static String MSN_EXCEPTION_VALIDATE_QUERY="No se encuentra el mapeo correspondiente en el query de los sigueintes elementos:";
    
    
    
 
    
    //Funciones
    public static final String ROWN_NUMBER_FUNCTION="ROW_NUMBER()";
    public static final String OPTION_FUNCTION="OPTION"; 
    public static final String MAXDOP_FUNCTION="MAXDOP";
    public static final String WITHNOLOCK_FUNCTION="WITH (NOLOCK)";
    
    public static final String FILE_NAME_PROPERTIES_DATACONNECTOR="conf/dataconnector.properties";
    
}
