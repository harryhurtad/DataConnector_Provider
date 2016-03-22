/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.excecution;

import com.dataconnector.commons.ElementSQLEnum;
import com.dataconnector.core.DataConnectorFactoryImpl;
import com.dataconnector.criteria.AbstractQuery;
import com.dataconnector.criteria.CriteriaQuery;
import com.dataconnector.criterial.generic.CriteriaQueryImpl;
import com.dataconnector.exceptions.DataConnectorResultException;
import com.dataconnector.manager.DataConnectorManager;
import com.dataconnector.manager.Query;
import com.dataconnector.obj.DetailMapObjDataConnector;
import com.dataconnector.obj.ParameterConstructClass;
import com.dataconnector.obj.ParameterImpl;
import com.dataconnector.obj.TranslatePagination;
import com.dataconnector.object.TemporalTypeEnum;
import com.dataconnector.sql.Expression;
import com.dataconnector.sql.FromImpl;
import com.dataconnector.sql.Join;
import com.dataconnector.sql.ParameterExpression;
import com.dataconnector.sql.Predicate;
import com.dataconnector.sql.Root;
import com.dataconnector.sql.SelectImpl;
import com.dataconnector.sql.Selection;
import com.dataconnector.sql.WhereImpl;
import com.dataconnector.sql.join.JoinsImpl;
import com.dataconnector.translation.TranslatorHelper;
import com.dataconnector.utils.Constantes;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que representa el obj represntante de la setencia SQL
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 29/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public class SelectQueryImpl implements Query<Object> {

    private final AbstractQuery query;
    private final Map<String, Object> mapParameter;
    private Integer maxResult;
    private int numeroRegistrosPorHilo;
    private boolean excecuteMulthiThread;
    private final DataConnectorManager manager;

    public SelectQueryImpl(AbstractQuery q, DataConnectorManager manager) {

        this.query = q;
        mapParameter = new HashMap();
        this.manager = manager;
    }

    /**
     * Realiza la extraccion de los parametros;
     *
     * @param q
     */
    public void extractParameters() {

        if (query instanceof CriteriaQuery) {
            CriteriaQueryImpl cq = (CriteriaQueryImpl) query;
            proccessSelectStatements(cq.getSelectImpl(), cq.getFromImpl(), cq.getWhereImpl());
        }
    }

    /**
     *
     * @param select
     * @param from
     * @param where
     */
    private void proccessSelectStatements(SelectImpl select, FromImpl from, WhereImpl where) {
        //Procesando Select

        for (Selection s : select.getListParametros()) {
            proccesStatements(s);
        }
        //proccess Form

        for (Root root : from.getListParametros()) {
            for (Join join : root.getListJoins()) {
                JoinsImpl joinImpl = (JoinsImpl) join;
                //On
                proccesStatements(joinImpl.getOnImpl().getParametros());
                //Where
                proccesStatements(joinImpl.getWhereImpl().getParametros());
            }
        }

        //Procesando Where      
        if (where != null) {
            proccesStatements(where.getParametros());
        }

    }

    /**
     * Realiza el procesamiento de los elementos contenidos en la sentencia sql
     *
     * @param parametro
     */
    private void proccesStatements(Selection parametro) {

        if (parametro instanceof ParameterExpression) {
            ParameterExpression param = (ParameterExpression) parametro;
            mapParameter.put(param.getNombreParametro(), null);
        } else if (parametro instanceof Predicate) {
            Predicate predicate = (Predicate) parametro;
            predicate.getListExpression();
            for (Expression exp : predicate.getListExpression()) {
                proccesStatements(exp);
            }
        }

    }

    @Override
    public Object getSingleResult() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Query setParameter(String name, Object value) {
        if (mapParameter.containsKey(name)) {
            mapParameter.put(name, value);
        }
        return this;
    }

    @Override
    public Query setParameter(String name, Calendar value, TemporalTypeEnum temporalType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> getResultList() throws DataConnectorResultException {

        ListeningExecutorService service = null;
        List<ExcecuteSelectThreadStatementSQL<Object>> hilos = new ArrayList();
        ExcecuteSelectSingleStatementSQL excuteSingle;
         SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        int success = 0, failure = 0;
        List<Object> listaResultado = new ArrayList();
        //Obtiene la sentencia sql
        String sql = null;
       
        Map<String, Class> mapValueReturn = extractValueReturn();
        if (excecuteMulthiThread) {
            sql = TranslatorHelper.getInstance().translateStatementByDriver(query);
            System.out.println("Query Paginado: "+sql);
            //Valida que el numero de registros no sea 0
            if (numeroRegistrosPorHilo <= 0) {
                throw new DataConnectorResultException("Upss!! Argumento no valido para  numeroRegistrosPorHilo tiene que ser mayor a 0");
            }

                       //Realiza la consulta del total de registros a consultar
            String queryCount = makedCountStatemnt((CriteriaQueryImpl) query);
            System.out.println("Query Count: " + queryCount);
            //ParameterConstructClass paramConstruct=new  ParameterConstructClass(new Class[]{String.class},new Object[]{"0"} );
            List<ParameterImpl> listParam = new ArrayList();
            //TODO :Realizar mejora a esta invocacion
            StringBuilder nameParam = new StringBuilder();
            nameParam.append(ElementSQLEnum.COUNT);
            nameParam.append(Constantes.PARENTECIS_IZQUIERDO);
            nameParam.append(ElementSQLEnum.ASTERISCO.getNameElement());
            nameParam.append(Constantes.PARENTECIS_DERECHO);

            listParam.add(new ParameterImpl(Integer.class, nameParam.toString()));
            ExcecuteSelectPrimitiveStatementSQL excecutePrimitive = new ExcecuteSelectPrimitiveStatementSQL(manager, mapParameter, maxResult, listParam);
            List<Integer> restul;
            Integer size = 0;
            try {
                restul = excecutePrimitive.excecuteSQLStatement(Integer.class, queryCount);
                size = restul.get(0);
            } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | SQLException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(SelectQueryImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new DataConnectorResultException("Upss!! Problemas al ejecutar la sentencia:" + ex);
            }

            //Adicion de los parametros de paginacion:
            mapParameter.put(Constantes.POSICION_INICIAL, null);
            mapParameter.put(Constantes.POSICION_FINAL, null);

            // size = helper.totalRegistos();
            System.out.println("Tamaño registros: " + size);
            

            System.out.println("*----------Inicio Ejecucion MultiHilo DataConnector: " + format.format(new Date()) + "---------------*");

            if (size > 0) {

                try {
                    int posicionIni = 0, posicionFinal = this.numeroRegistrosPorHilo;
                    while (posicionFinal < size) {
                        if ((posicionFinal + this.numeroRegistrosPorHilo) > size) {
                            posicionFinal = size;
                        }
                        
                        //Paginación de acuerdo al driver
                        TranslatePagination pag=TranslatorHelper.getInstance().translatePagValueByDriver(posicionIni, posicionFinal);
                                                    
                        
                        ExcecuteSelectThreadStatementSQL<Object> sentencia = new ExcecuteSelectThreadStatementSQL(pag.getPosicionInicial(), pag.getPosicionFinal(), manager, sql, mapParameter, mapValueReturn, query.getClassToCreate());

                        hilos.add(sentencia);
                        posicionIni = posicionFinal;
                        // if ((posicionFinal + NO_REGISTROS_HILO) > size) {
                        //      posicionFinal = size;
                        // } else {
                        posicionFinal = (posicionFinal + numeroRegistrosPorHilo) + 1;
                        // }

                    }

                    //
                    for (ExcecuteSelectThreadStatementSQL dato : hilos) {
                        System.out.println(dato);
                    }
                    //Realiza la creacioon del pool de hilos
                    service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(20));
                    List<Future<List<Object>>> results = service.invokeAll(hilos);
                //List< Future<List>> results = service.invokeAll(hilos);

                    //Analisis de la respuesta 
                    for (Future< List<Object>> result : results) {
                        if (result.get() == null) {
                            failure++;
                        } else {
                            //   System.out.println("Registro :" + result.get().size());
                            listaResultado.addAll(result.get());
                            success++;
                        }
                    }
                    //Muestra los resultados de la ejecucion
                    System.out.println("Total Registros:" + listaResultado.size());
                    System.out.println("Total Fallas:" + failure);
                    System.out.println("Total Exitosas:" + success);
                    System.out.println("*----------Fin Ejecucion MultiHilo DataConnector:  " + format.format(new Date()) + "---------------*");
                    /*     for(Employees emp:listaResultado){
                     System.out.println(emp);
                     }*/

                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(SelectQueryImpl.class.getName()).log(Level.SEVERE, null, ex);
                    throw new DataConnectorResultException("Upss!! Problemas al ejecutar la sentencia multiHilo:" + ex);
                }
            }
            //Cerrar servicio
            if (service != null) {
                service.shutdown();
            }
        } else {
            
              System.out.println("*----------Inicio Ejecucion Simple DataConnector: " + format.format(new Date()) + "---------------*");
            sql = makeSQLStatement();
        
            //Ejecucion con un solo hislo
            excuteSingle = new ExcecuteSelectSingleStatementSQL(mapParameter, mapValueReturn, maxResult, manager);

            try {
                listaResultado = excuteSingle.excecuteSQLStatement(query.getClassToCreate(), sql);

            } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | SQLException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(SelectQueryImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new DataConnectorResultException("Upss!! Problemas al ejecutar la sentencia:" + ex);
            }
            System.out.println("*----------Fin Ejecucion Simple DataConnector:  " + format.format(new Date()) + "---------------*");
        }
        return listaResultado;
    }

    @Override
    public void setMaxResult(int result) {
        this.maxResult = result;
    }

    @Override
    public void excuteMultiThread(boolean value) {
        this.excecuteMulthiThread = value;
    }

    @Override
    public Map<String, Object> getMapParameters() {
        return mapParameter;
    }

    private Map<String, Class> extractValueReturn() {
        CriteriaQueryImpl impl = (CriteriaQueryImpl) query;
        SelectImpl selectImpl = impl.getSelectImpl();
        Map<String, Class> mapReturnValue = new HashMap();

        Map<String, DetailMapObjDataConnector> listDetailObjectMap = DataConnectorFactoryImpl.mapObjectProccess.get(selectImpl.getClassToCreate().getName());
        for (Selection params : selectImpl.getListParametros()) {
            DetailMapObjDataConnector detailObjReturn = listDetailObjectMap.get(params.getAlias());
            mapReturnValue.put(params.getAlias(), detailObjReturn.getCampo().getType());

        }

        return mapReturnValue;
    }

    /**
     * Realiza la creacion de la sentencia sql
     *
     * @return
     */
    private String makeSQLStatement() {
        StringBuilder sql = new StringBuilder();
        CriteriaQueryImpl impl = (CriteriaQueryImpl) query;
        FromImpl from = impl.getFromImpl();
        from.proccessJoins();
        WhereImpl where = impl.getWhereImpl();

        sql.append(impl.getSelectImpl().getTranslation());
        sql.append(Constantes.ESPACIO);
        sql.append(from.getTranslation());
        sql.append(Constantes.ESPACIO);
        if (where != null) {
            sql.append(Constantes.ESPACIO);
            sql.append(impl.getWhereImpl().getTranslation());
        }

        // String sql = impl.getSelectImpl().getTranslation() + " " + from.getTranslation() + " " + ;
        System.out.println(sql);

        return sql.toString();

    }

    /**
     * realiza la creacion de la sentencia sql utilizando el parametro count
     *
     * @param impl
     * @return
     */
    private String makedCountStatemnt(CriteriaQueryImpl impl) {

        StringBuilder builder = new StringBuilder();
        builder.append(ElementSQLEnum.SELECT.toString());
        builder.append(Constantes.ESPACIO);
        builder.append(ElementSQLEnum.COUNT);
        builder.append(Constantes.PARENTECIS_IZQUIERDO);
        builder.append(ElementSQLEnum.ASTERISCO.getNameElement());
        builder.append(Constantes.PARENTECIS_DERECHO);
        builder.append(Constantes.ESPACIO);
        builder.append(impl.getFromImpl().getTranslation());
        if (impl.getWhereImpl() != null) {
            builder.append(Constantes.ESPACIO);
            builder.append(impl.getWhereImpl().getTypeElementSQL());
        }
        return builder.toString();
    }

    @Override
    public void setNumeroRegistrosHilo(int value) {
        this.numeroRegistrosPorHilo = value;
    }
}
