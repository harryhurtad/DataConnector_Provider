/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dataconnector.obj;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *{Insert class description here}
 * @version $Revision: 1.1.1  (UTF-8)
 * @since build 4/03/2016  
 * @author proveedor_hhurtado  email: proveedor_hhurtad@ath.com.co
 */
public class DetailMapObjDataConnector {

   private Annotation anotacion; 
   private Field campo;
   private Method metodo;

    public DetailMapObjDataConnector(Field campo, Method metodo,Annotation anotacion) {
        this.campo = campo;
        this.metodo = metodo;
        this.anotacion=anotacion;
    }

   
   
    public Field getCampo() {
        return campo;
    }

    public void setCampo(Field campo) {
        this.campo = campo;
    }

    public Method getMetodo() {
        return metodo;
    }

    public void setMetodo(Method metodo) {
        this.metodo = metodo;
    }

    public Annotation getAnotacion() {
        return anotacion;
    }
    
    

    @Override
    public String toString() {
        return "DetailMapObjDataConnector{" + "campo=" + campo.getName() + ", metodo=" + metodo.getName() + '}';
    }
   
   
}
