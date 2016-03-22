/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dataconnector.translation;

import com.dataconnector.criteria.AbstractQuery;
import com.dataconnector.obj.TranslatePagination;

/**
 *{Insert class description here}
 * @version $Revision: 1.1.1  (UTF-8)
 * @since build 3/03/2016  
 * @author proveedor_hhurtado  email: proveedor_hhurtad@ath.com.co
 */
public interface TranslateSelect {

    String translate(AbstractQuery query);
    TranslatePagination pagination(int posicionInicial,int posicionFinal);
    
}
