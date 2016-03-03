/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.criterial.generic;

import com.dataconnector.criteria.AbstractQuery;
import com.dataconnector.criteria.SubQuery;
import com.dataconnector.sql.FromImpl;
import com.dataconnector.sql.Order;
import com.dataconnector.sql.Predicate;
import com.dataconnector.sql.Root;
import com.dataconnector.sql.RootImpl;
import com.dataconnector.sql.SelectImpl;
import com.dataconnector.sql.Selection;
import com.dataconnector.sql.WhereImpl;
import com.dataconnector.utils.Constantes;

/**
 * Interfaz que representa un subquery del lenguaje SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 29/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class SubQueryImpl implements SubQuery {

    private final SelectImpl selectImpl;
    private final FromImpl fromImpl;
    private WhereImpl whereImpl;
    private int countAliasTable = 0;
    private Class typeReturn;
    private StringBuilder sql;

    public SubQueryImpl(Class typeReturn) {
        this.typeReturn=typeReturn;
        selectImpl = new SelectImpl();
        fromImpl = new FromImpl();
        sql=new StringBuilder();
        

    }

    @Override
    public SubQuery select(Selection params) {
        //    for (Selection elemento : params) {
        selectImpl.proccess(new Selection[]{params});
        // }

        return this;
    }

    @Override
    public SubQuery where(Predicate params) {
        //  for (Predicate elemento : params) {
        whereImpl = new WhereImpl();
        whereImpl.proccess(params);
       // }
        return this;
    }

    @Override
    public Root from(String nombreTabla) {
        countAliasTable++;
        RootImpl impl = new RootImpl("sub".concat("" + countAliasTable), nombreTabla);
        fromImpl.proccess(impl);
        return impl;
    }

    public Class getTypeReturn() {
        return typeReturn;
    }

    public void setTypeReturn(Class typeReturn) {
        this.typeReturn = typeReturn;
    }

    public void proccess(){
    
        sql.append(Constantes.PARENTECIS_IZQUIERDO);
        sql.append(Constantes.ESPACIO);
        //Select
        if(selectImpl!=null){
            sql.append(selectImpl.getTranslation());
        }
        //From        
        if(fromImpl!=null){
            sql.append(Constantes.ESPACIO);
            sql.append(fromImpl.getTranslation());
            sql.append(Constantes.ESPACIO);
        }
        //where
        if(whereImpl!=null){            
            sql.append(whereImpl.getTranslation());
        }
        sql.append(Constantes.ESPACIO);
        sql.append(Constantes.PARENTECIS_DERECHO);
    }
    
    @Override
    public SubQuery orderBy(Order... ord) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAlias() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StringBuilder getSQLTransalte() {  
        return sql;
    }

   

}