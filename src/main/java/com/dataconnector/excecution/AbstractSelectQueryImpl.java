/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.excecution;

import com.dataconnector.commons.ElementSQLEnum;
import com.dataconnector.context.InitialContextDataconnectorImpl;
import com.dataconnector.core.DataConnectorFactoryImpl;
import com.dataconnector.criteria.AbstractQuery;
import com.dataconnector.criteria.CriteriaQuery;
import com.dataconnector.criterial.generic.CriteriaQueryImpl;
import com.dataconnector.exceptions.DataConnectorResultException;
import com.dataconnector.exceptions.InitialCtxDataConnectorException;
import com.dataconnector.manager.AbstractDataConnectorManager;
import com.dataconnector.manager.DataConnectorManager;
import com.dataconnector.query.Query;
import com.dataconnector.obj.DetailMapObjDataConnector;
import com.dataconnector.obj.ParameterImpl;
import com.dataconnector.obj.TranslatePagination;
import com.dataconnector.object.TemporalTypeEnum;
import com.dataconnectorcommons.sql.Expression;
import com.dataconnector.sql.FromImpl;
import com.dataconnector.sql.Join;
import com.dataconnector.sql.ParameterExpression;
import com.dataconnector.sql.Predicate;
import com.dataconnector.sql.Root;
import com.dataconnector.sql.SelectImpl;
import com.dataconnectorcommons.sql.Selection;
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
 * {Insert class description here}
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 22/03/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public abstract class AbstractSelectQueryImpl implements Query<Object> {

    public final AbstractQuery query;
    public final Map<String, Object> mapParameter;
    private Integer maxResult;
    private int numeroRegistrosPorHilo;
    private boolean excecuteMulthiThread;
    private final AbstractDataConnectorManager manager;

    public AbstractSelectQueryImpl(AbstractQuery q, AbstractDataConnectorManager manager) {

        this.query = q;
        mapParameter = new HashMap();
        this.manager = manager;
    }

   
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
    public void proccessSelectStatements(SelectImpl select, FromImpl from, WhereImpl where) {
        //Procesando Select

        for (Selection s : select.getListParametros()) {
            proccesSelectionStatements(s);
        }
        //proccess Form

        for (Root root : from.getListParametros()) {
            for (Join join : root.getListJoins()) {
                JoinsImpl joinImpl = (JoinsImpl) join;
                //On
                proccesSelectionStatements(joinImpl.getOnImpl().getParametros());
                //Where
                if(joinImpl.getWhereImpl()!=null){
                    proccesSelectionStatements(joinImpl.getWhereImpl().getParametros());
                }
            }
        }

        //Procesando Where      
        if (where != null) {
            proccesSelectionStatements(where.getParametros());
        }

    }

    /**
     * Realiza el procesamiento de los elementos contenidos en la sentencia sql
     *
     * @param parametro
     */
    private void proccesSelectionStatements(Selection parametro) {

        if (parametro instanceof ParameterExpression) {
            ParameterExpression param = (ParameterExpression) parametro;
            mapParameter.put(param.getNombreParametro(), null);
        } else if (parametro instanceof Predicate) {
            Predicate predicate = (Predicate) parametro;
            predicate.getListExpression();
            for (Expression exp : predicate.getListExpression()) {
                proccesSelectionStatements(exp);
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

        Map<String, Class> mapValueReturn;
        try {
            mapValueReturn = extractValueReturn();
        } catch (InitialCtxDataConnectorException ex) {
            Logger.getLogger(SelectQueryImpl.class.getName()).log(Level.SEVERE, null, ex);
                    throw new DataConnectorResultException("Upss!! Problemas al cargar la dotos del InitialContext" + ex);
        }
        if (excecuteMulthiThread) {
            
            try {
                sql = TranslatorHelper.getInstance().translateStatementByDriver(query,this,manager.getContext());
            } catch (InitialCtxDataConnectorException ex) {
                Logger.getLogger(AbstractSelectQueryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println("Query Paginado: " + sql);
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
//            nameParam.append(ElementSQLEnum.COUNT);
//            nameParam.append(Constantes.PARENTECIS_IZQUIERDO);
//            nameParam.append(ElementSQLEnum.ASTERISCO.getNameElement());
//            nameParam.append(Constantes.PARENTECIS_DERECHO);
            nameParam.append(Constantes.ALIAS_CANTIDAD);
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
                        TranslatePagination pag = TranslatorHelper.getInstance().translatePagValueByDriver(posicionIni, posicionFinal,manager.getContext());

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
                } catch (InitialCtxDataConnectorException ex) {
                    Logger.getLogger(AbstractSelectQueryImpl.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
            //Cerrar servicio
            if (service != null) {
                service.shutdown();
            }
        } else {

            System.out.println("*----------Inicio Ejecucion Simple DataConnector: " + format.format(new Date()) + "---------------*");
            sql = makeSQLStatementNotPagin();

            //Ejecucion con un solo hislo
            excuteSingle = new ExcecuteSelectSingleStatementSQL(mapParameter, mapValueReturn, maxResult, manager);

            try {
                listaResultado = excuteSingle.excecuteSQLStatement(query.getClassToCreate(), sql);

            } catch (InitialCtxDataConnectorException |NoSuchMethodException | IllegalArgumentException | InvocationTargetException | SQLException | InstantiationException | IllegalAccessException ex) {
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

 
    public Map<String, Class> extractValueReturn() throws InitialCtxDataConnectorException {
        CriteriaQueryImpl impl = (CriteriaQueryImpl) query;
        SelectImpl selectImpl = impl.getSelectImpl();
        Map<String, Class> mapReturnValue = new HashMap();

        Map<String, DetailMapObjDataConnector> listDetailObjectMap = (Map<String, DetailMapObjDataConnector>)InitialContextDataconnectorImpl.mapObjectProccess.get(selectImpl.getClassToCreate().getName());
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
    public abstract String makeSQLStatementNotPagin();

    /**
     * realiza la creacion de la sentencia sql utilizando el parametro count
     *
     * @param impl
     * @return
     */
    public abstract String makedCountStatemnt(CriteriaQuery impl); 

    @Override
    public void setNumeroRegistrosHilo(int value) {
        this.numeroRegistrosPorHilo = value;
    }

}
