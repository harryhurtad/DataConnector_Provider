/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.sql;

import com.dataconnector.commons.ElementSQLEnum;
import com.dataconnector.commons.TupleSQL;
import com.dataconnector.sql.join.JoinsImpl;
import com.dataconnector.utils.Constantes;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion que representa  From del lenguaje SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 26/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class FromImpl implements TupleSQL {

    private final StringBuilder sql;
    private final List<Root> listParametros;

    public FromImpl() {
        sql = new StringBuilder();
        listParametros = new ArrayList();
    }

    /**
     *
     * @param element
     */
    public void proccess(Root element) {

        sql.append(getTypeElementSQL().getNameElement());
        sql.append(Constantes.ESPACIO);
        sql.append(element.getNombreTabla());

        sql.append(Constantes.ESPACIO);
        sql.append(element.getAlias());
        listParametros.add(element);
    }

    /**
     * 
     */
    public void proccessJoins() {
        for (Root r : listParametros) {
            List<Join> listJoin = r.getListJoins();
            if (!listJoin.isEmpty()) {
                for (Join joinImpl : listJoin) {
                    JoinsImpl impl = (JoinsImpl) joinImpl;
                    impl.proccess();
                    sql.append(impl.getSQLTransalte());
                    sql.append(Constantes.SALTO_DE_LINEA);
                }
            }

        }
    }

    @Override
    public ElementSQLEnum getTypeElementSQL() {
        return ElementSQLEnum.FROM;
    }

    @Override
    public String getTranslation() {
        return sql.toString();
    }

}
