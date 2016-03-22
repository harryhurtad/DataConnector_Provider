/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dataconnector.obj;

/**
 *{Insert class description here}
 * @version $Revision: 1.1.1  (UTF-8)
 * @since build 22/03/2016  
 * @author proveedor_hhurtado  email: proveedor_hhurtad@ath.com.co
 */
public class TranslatePagination {

    private final int posicionInicial;
    private final int posicionFinal;

    public TranslatePagination(int posicionInicial, int posicionFinal) {
        this.posicionInicial = posicionInicial;
        this.posicionFinal = posicionFinal;
    }

    
    
    public int getPosicionInicial() {
        return posicionInicial;
    }

    public int getPosicionFinal() {
        return posicionFinal;
    }
    
    
}
