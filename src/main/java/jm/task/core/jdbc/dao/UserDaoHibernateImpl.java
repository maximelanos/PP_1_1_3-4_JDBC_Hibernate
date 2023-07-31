package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;




public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(ID INT PRIMARY KEY AUTO_INCREMENT, " +
                "NAME VARCHAR(100) NOT NULL, " +
                "LASTNAME VARCHAR(100) NOT NULL, " +
                "AGE TINYINT NOT NULL)";
        Query query = session.createSQLQuery(sql).addEntity(User.class);

        query.executeUpdate();
        session.getTransaction().commit();

        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        String sql = "DROP TABLE IF EXISTS users";
        Query query = session.createSQLQuery(sql).addEntity(User.class);

        query.executeUpdate();
        session.getTransaction().commit();

        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);

        session.save(user);
        session.getTransaction().commit();

        session.close();

        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        User user = session.get(User.class, id);

        session.delete(user);
        session.getTransaction().commit();

        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root);

        Query<User> query = session.createQuery(cq);
        List<User> userList = query.getResultList();
        for (User user : userList) {
            System.out.println(user.toString());
        }

        session.close();

        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        session.createSQLQuery("DELETE FROM users").executeUpdate();

        session.getTransaction().commit();

        session.close();
    }
}
