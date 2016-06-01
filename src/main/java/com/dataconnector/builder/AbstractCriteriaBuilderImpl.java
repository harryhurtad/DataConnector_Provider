/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataconnector.builder;

import com.dataconnector.criteria.AbstractQuery;
import com.dataconnector.sql.CriteriaBuilder;
import com.dataconnector.criteria.SubQuery;
import com.dataconnector.criteria.delete.CommonAbstractDelete;
import com.dataconnector.criteria.insert.CommonAbstractInsert;
import com.dataconnector.criteria.update.CommonAbstractUpdate;
import com.dataconnector.criterial.generic.CriteriaQueryImpl;
import com.dataconnector.criterial.generic.SubQueryImpl;
import com.dataconnector.obj.ParameterImpl;
import com.dataconnector.constans.ProvidersSupportEnum;
import com.dataconnector.object.ValueExpression;
import com.dataconnector.object.ValueRoot;
import com.dataconnector.sql.Expression;
import com.dataconnector.sql.Predicate;
import com.dataconnector.operations.AndOperation;
import com.dataconnector.operations.BetweenOperation;
import com.dataconnector.operations.EqualsOperation;
import com.dataconnector.operations.InOperation;
import com.dataconnector.operations.IsNotNullOperation;
import com.dataconnector.operations.IsNullOperation;
import com.dataconnector.operations.MayorIgualQueOperation;
import com.dataconnector.operations.MayorQueOperation;
import com.dataconnector.operations.MenorIgualQueOperation;
import com.dataconnector.operations.MenorQueOperation;
import com.dataconnector.operations.NotEqualOperation;
import com.dataconnector.operations.OrOperation;
import com.dataconnector.sql.JoinPredicate;
import com.dataconnector.sql.ParameterExpression;

/**
 * Implementación de la interfaz criterial builder, que permite la utilizacion de los predicados
 *
 * @version $Revision: 1.1.1 (UTF-8)
 * @since build 23/02/2016
 * @author proveedor_hhurtado email: proveedor_hhurtad@ath.com.co
 */
public abstract class  AbstractCriteriaBuilderImpl implements CriteriaBuilder {

    private ProvidersSupportEnum driver;

    @Override
    public Predicate getConjuncion() {
        return new AndOperation();
    }

    @Override
    public Predicate getDiyuncion() {
        return new OrOperation();
    }

    @Override
    public Predicate and(Expression param1, Expression param2) {
        AndOperation andOper = new AndOperation();
        return andOper.translateOperation(param1, param2);

    }

    @Override
    public Predicate and(Predicate... parametros) {
        AndOperation andOper = new AndOperation();
        return andOper.translateExpression(parametros);
    }

    @Override
    public Predicate or(Expression param1, Expression param2) {
        OrOperation orOper = new OrOperation();
        return orOper.translateOperation(param1, param2);
    }

    @Override
    public Predicate or(Predicate... parametros) {
        OrOperation orOper = new OrOperation();
        return orOper.translateExpression(parametros);
    }

    @Override
    public Predicate equal(Expression param1, Expression param2) {
        EqualsOperation equalsOperation = new EqualsOperation();
        return equalsOperation.translateOperation(param1, param2);
    }

    @Override
    public Predicate notEqual(Expression param1, Expression param2) {
        NotEqualOperation notEqualOperation = new NotEqualOperation();
        return notEqualOperation.translateOperation(param1, param2);
    }

    @Override
    public Predicate mayorQue(Expression param1, Expression param2) {
        MayorQueOperation mayorQueOperation = new MayorQueOperation();
        return mayorQueOperation.translateOperation(param1, param2);
    }

    @Override
    public Predicate mayorIgualQue(Expression param1, Expression param2) {
        MayorIgualQueOperation mayorIgualQueOperation = new MayorIgualQueOperation();
        return mayorIgualQueOperation.translateOperation(param1, param2);
    }

    @Override
    public Predicate menorQue(Expression param1, Expression param2) {
        MenorQueOperation menorQueOperation = new MenorQueOperation();
        return menorQueOperation.translateOperation(param1, param2);
    }

    @Override
    public Predicate menorIgualQue(Expression param1, Expression param2) {
        MenorIgualQueOperation menorIgualQueOperation = new MenorIgualQueOperation();
        return menorIgualQueOperation.translateOperation(param1, param2);
    }

    @Override
    public Predicate between(Expression param1, Expression param2) {
        BetweenOperation betweenOperation = new BetweenOperation();
        return betweenOperation.translateOperation(param1, param2);
    }

    @Override
    public Predicate isNull(Expression param1, Expression param2) {
        IsNullOperation isNullOperation = new IsNullOperation();
        return isNullOperation.translateOperation(param1, param2);
    }

    @Override
    public Predicate isNotNull(Expression param1, Expression param2) {
        IsNotNullOperation isNotNullOperation = new IsNotNullOperation();
        return isNotNullOperation.translateOperation(param1, param2);
    }

    @Override
    public Predicate IN(String[] value) {
        InOperation inOperation = new InOperation();
        return inOperation.proccessTranslate(value);
    }

    /**
     * Metodo encargado de realizar la implementación de una sentencia select
     *
     * @param param
     * @return losd hijos de AbstractQuery de acuerdo al driver seleccionado
     */
    @Override
    public AbstractQuery createQuery(Class param) {
        AbstractQuery query;
    
                query = new CriteriaQueryImpl(param);
       

        return query;
    }

    @Override
    public abstract CommonAbstractInsert createInserQuery();
    

    @Override
    public abstract CommonAbstractDelete createDeleterQueryFactory() ;

    @Override
    public abstract CommonAbstractUpdate createUpdaterQueryFactory();

    public ProvidersSupportEnum getDriver() {
        return driver;
    }

    public void setDriver(ProvidersSupportEnum driver) {
        this.driver = driver;
    }

    /**
     *
     * @param <X>
     * @param obj
     * @param nameParam
     * @return
     */
    @Override
    public <X> ParameterExpression<X> parameter(Class<X> obj, String nameParam) {
        ParameterImpl<X> impl = new ParameterImpl<>(obj, nameParam);
        return impl;
    }

    @Override
    public ValueExpression value(Class obj, Object element) {
        return new ValueExpression(element, obj);
    }

    @Override
    public JoinPredicate equal(ValueRoot param1, ValueRoot param2) {
        EqualsOperation equalsOperation = new EqualsOperation();
        return equalsOperation.translateJoinOperation(param1, param2);
    }
    
      @Override
    public JoinPredicate equal(ValueRoot param1, Expression param2) {
        EqualsOperation equalsOperation = new EqualsOperation();
        return equalsOperation.translateJoinOperation(param1, param2);
    }

    @Override
    public JoinPredicate notEqual(ValueRoot param1, ValueRoot param2) {
        NotEqualOperation notEqualOperation = new NotEqualOperation();
        return notEqualOperation.translateJoinOperation(param1, param2);

    }

    @Override
    public JoinPredicate mayorQue(ValueRoot param1, ValueRoot param2) {
        MayorQueOperation mayorQueOperation = new MayorQueOperation();
        return mayorQueOperation.translateJoinOperation(param1, param2);
    }

    @Override
    public JoinPredicate mayorIgualQue(ValueRoot param1, ValueRoot param2) {
        MayorIgualQueOperation mayorIgualQueOperation = new MayorIgualQueOperation();
        return mayorIgualQueOperation.translateJoinOperation(param1, param2);
    }

    @Override
    public JoinPredicate menorQue(ValueRoot param1, ValueRoot param2) {
        MenorQueOperation menorQueOperation = new MenorQueOperation();
        return menorQueOperation.translateJoinOperation(param1, param2);
    }

    @Override
    public JoinPredicate menorIgualQue(ValueRoot param1, ValueRoot param2) {
        MenorIgualQueOperation menorIgualQueOperation = new MenorIgualQueOperation();
        return menorIgualQueOperation.translateJoinOperation(param1, param2);
    }

    @Override
    public JoinPredicate and(JoinPredicate... parametros) {
         AndOperation andOper = new AndOperation();
         return andOper.translateExpression(parametros);
    }

    @Override
    public JoinPredicate and(JoinPredicate param1, JoinPredicate param2) {
         AndOperation andOper = new AndOperation();
         return andOper.translateOperation(param1, param2);
    }

    @Override
    public JoinPredicate or(JoinPredicate... parametros) {
        OrOperation orOper = new OrOperation();
        return orOper.translateExpression(parametros);
    }

    @Override
    public JoinPredicate or(JoinPredicate param1, JoinPredicate param2) {
         OrOperation orOper = new OrOperation();
        return orOper.translateOperation(param1, param2);
    
    }

    @Override
    public SubQuery createSubQuery(Class typeValueReturn) {
        SubQueryImpl impl=new SubQueryImpl(typeValueReturn);
        return impl;
    }

}
