/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.translation;

import com.dataconnector.sql.Selection;

/**
 * {Insert class description here}
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 26/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class TranslatorHelper {

    private static TranslatorHelper instance;

    private TranslatorHelper() {

    }

    public static TranslatorHelper getInstance() {
        if (instance == null) {
            instance = new TranslatorHelper();
        }

        return instance;
    }

    public StringBuilder translateOperation(Selection element){
    
       // if(element instanceof Equ)
    
    
        return null;
    }
}
