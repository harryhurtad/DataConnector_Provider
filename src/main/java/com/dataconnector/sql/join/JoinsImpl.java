/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.sql.join;

import com.dataconnector.commons.ElementSQLEnum;
import com.dataconnector.commons.metadata.MetadataFieldDataConnector;
import com.dataconnector.object.JoinsTypeEnum;
import com.dataconnector.object.ValueRoot;
import com.dataconnectorcommons.sql.Expression;
import com.dataconnector.sql.Join;
import com.dataconnector.sql.JoinPredicate;
import com.dataconnector.sql.Predicate;
import com.dataconnector.sql.WhereImpl;
import com.dataconnector.utils.Constantes;

/**
 * Implementacion que representa  Joins del lenguaje SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 26/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class JoinsImpl implements Join {

    private OnImpl onImpl;
    private WhereImpl whereImpl;
    private final String nombreTabla;
    private final String alias;
    private StringBuilder sql;
    private final JoinsTypeEnum typeJoin;

    public JoinsImpl(String nombreTabla, String alias, JoinsTypeEnum type) {
        this.nombreTabla = nombreTabla;
        this.alias = alias;

        this.typeJoin = type;
    }

    @Override
    public Join On(JoinPredicate p) {
        onImpl = new OnImpl();
        onImpl.proccess(p);
        return this;
    }

    @Override
    public Join where(Predicate p) {
        whereImpl = new WhereImpl();
        whereImpl.proccess(p);
        return this;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public String getAlias() {
        return alias;
    }

    public void proccess() {
        sql = new StringBuilder();
        sql.append(Constantes.ESPACIO);
        sql.append(getTypeJoin().getNameElement());
        sql.append(Constantes.ESPACIO);
        sql.append(ElementSQLEnum.JOIN);
        sql.append(Constantes.ESPACIO);
        sql.append(getNombreTabla());
        sql.append(Constantes.ESPACIO);
        sql.append(getAlias());
        sql.append(Constantes.ESPACIO);
        if (onImpl != null) {
            sql.append(onImpl.getTranslation());
        }
        sql.append(Constantes.ESPACIO);
        if (whereImpl != null) {
            sql.append(whereImpl.getTranslation());
        }
        sql.append(Constantes.ESPACIO);
    }

    public JoinsTypeEnum getTypeJoin() {
        return typeJoin;
    }

    @Override
    public StringBuilder getSQLTransalte() {
        return sql;
    }

    @Override
    public ValueRoot get(MetadataFieldDataConnector nombreParametro) {
        ValueRoot vr = new ValueRoot(alias, nombreParametro);
        return vr;
    }

    public OnImpl getOnImpl() {
        return onImpl;
    }

    public WhereImpl getWhereImpl() {
        return whereImpl;
    }

    
}
