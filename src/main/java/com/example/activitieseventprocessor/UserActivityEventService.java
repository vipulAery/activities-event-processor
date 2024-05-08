package com.example.activitieseventprocessor;

import com.github.tennaito.rsql.jpa.JpaCriteriaQueryVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import jakarta.persistence.Entity;

import jakarta.persistence.criteria.CriteriaQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UserActivityEventService {
    @Autowired
    EntityManager entityManager;

    public List<UserActivityEvent> searchByQuery(String queryString) {
        RSQLVisitor<CriteriaQuery<UserActivityEvent>, EntityManager> visitor = new JpaCriteriaQueryVisitor<>();
        CriteriaQuery<UserActivityEvent> query = getCriteriaQuery(queryString, visitor);
        List<UserActivityEvent> resultList = entityManager.createQuery(query).getResultList();
        if (resultList == null || resultList.isEmpty()){
            return Collections.emptyList();
        }
        return resultList;
    }

    private <T> CriteriaQuery<T> getCriteriaQuery(String queryString, RSQLVisitor<CriteriaQuery<T>, EntityManager> visitor) {
        Node rootNode;
        CriteriaQuery<T> query;
        try {
            rootNode = new RSQLParser().parse(queryString);
            query = rootNode.accept(visitor, entityManager);
        }catch (Exception e){
            log.error("An error happened while executing RSQL query", e);
            throw new IllegalArgumentException(e.getMessage());
        }
        return query;
    }
}
