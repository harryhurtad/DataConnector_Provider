/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.sql;

import com.dataconnector.object.JoinsTypeEnum;
import com.dataconnector.object.ValueRoot;
import com.dataconnector.sql.join.JoinsImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion que representa  Root del lenguaje SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 26/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class RootImpl implements Root{

    private String alias;
    private final String nombreTabla;
    private  int countAliasJoin;
    private final List<Join>listJoins;

    public RootImpl(String alias,String nombreTabla) {
        this.countAliasJoin = 0;
        this.alias = alias;
        this.nombreTabla=nombreTabla;
        listJoins=new ArrayList<>();
    }

    @Override
    public List<Join> getListJoins() {
        return listJoins;
    }
    
    
    
    
    public Boolean isCompound() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ValueRoot get(String nombreParametro) {
        ValueRoot vr=new ValueRoot(alias, nombreParametro);
        return vr;
    }

    @Override
    public Join joinTable(String nameTableJoin,JoinsTypeEnum typeJoin) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        JoinsImpl impl=new JoinsImpl(nameTableJoin, "inerT".concat("" + countAliasJoin),typeJoin);
        listJoins.add(impl);
        countAliasJoin++;
        return impl;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String getNombreTabla() {
        return nombreTabla;
    }

   

 

    @Override
    public StringBuilder getSQLTransalte() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
    
}
