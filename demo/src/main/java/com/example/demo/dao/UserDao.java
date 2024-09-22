package com.example.demo.dao;

import com.example.demo.entity.User;
import com.example.demo.util.QueryConstants;
import com.example.demo.util.TableConstants;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public User get(String username, String password){
        String queryString = QueryConstants.selectQuery
                .replaceAll(QueryConstants.colums,QueryConstants.allColumns)
                .replace(QueryConstants.tableName, TableConstants.user)
                .replace(QueryConstants.tableAlias,"u");
        var query =  sessionFactory.getCurrentSession().createQuery(queryString);
        return (User) query.uniqueResult();
    }
}
