/*
package com.payu.db.base.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.persistence.Inheritance;
import java.io.Serializable;

*/
/**
 * Created by akesh.patil on 04-03-2017.
 *//*


@Inheritance
public class BaseRepository {

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    protected <T> Serializable save(T t) {
        Session session = hibernateTemplate.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Serializable id = session.save(t);
        transaction.commit();
        session.close();
        return id;
    }
}
*/
