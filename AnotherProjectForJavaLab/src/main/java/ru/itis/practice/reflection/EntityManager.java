package ru.itis.practice.reflection;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class EntityManager {
    private DataSource dataSource;
    private SimpleJdbcTemplate template;

    Map<Class<?>, String> typeMap;

    public EntityManager(DataSource dataSource) {
        this.dataSource = dataSource;
        template = new SimpleJdbcTemplate(dataSource);
        typeMap = new HashMap<>();
        typeMap.put(Integer.class, "integer");
        typeMap.put(String.class, "varchar");
        typeMap.put(Character.class, "char(1)");
        typeMap.put(Boolean.class, "boolean");
        typeMap.put(Long.class, "bigint");
        typeMap.put(Float.class, "real");
        typeMap.put(Double.class, "double precision");
        typeMap.put(Short.class, "smallint");
        typeMap.put(Integer.TYPE, "integer");
        typeMap.put(Character.TYPE, "char(1)");
        typeMap.put(Boolean.TYPE, "boolean");
        typeMap.put(Long.TYPE, "bigint");
        typeMap.put(Float.TYPE, "real");
        typeMap.put(Double.TYPE, "double precision");
        typeMap.put(Short.TYPE, "smallint");
    }

    // createTable("account", User.class);
    public <T> void createTable(String tableName, Class<T> entityClass) {
        StringBuilder sql = new StringBuilder("CREATE TABLE ").append(tableName).append(" (");
        Field[] fields = entityClass.getDeclaredFields();
        Class<?> type;
        String sqlType;
        boolean firstField = true;
        for (Field f : fields) {
            type = f.getType();
            System.out.println(type);
            sqlType = typeMap.get(type);
            if (!firstField) {
                sql.append(", ");
            }
            if (sqlType != null) {
                firstField = false;
                sql.append(f.getName()).append(" ").append(sqlType);
            }
        }
        sql.append(");");
        System.out.println(sql);
        template.update(sql.toString());
        // сгенерировать CREATE TABLE на основе класса
        // create table account ( id integer, firstName varchar(255), ...))
    }

    public void save(String tableName, Object entity) {
        Class<?> classOfEntity = entity.getClass();
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tableName).append("(");
        Field[] fields = entity.getClass().getDeclaredFields();
        Class<?> type;
        String sqlType;
        boolean firstField = true;
        for (Field f : fields) {
            type = f.getType();
            sqlType = typeMap.get(type);
            if (!firstField) {
                sql.append(", ");
            }
            if (sqlType != null) {
                firstField = false;
                sql.append(f.getName());
            }
        }
        sql.append(") VALUES(");
        List<Object> fieldsValues = new ArrayList<>();
        firstField = true;
        for (Field f : fields) {
            f.setAccessible(true);
            type = f.getType();
            sqlType = typeMap.get(type);
            if (!firstField) {
                sql.append(", ");
            }
            if (sqlType != null) {
                firstField = false;
                try {
                    sql.append("?");
                    fieldsValues.add(f.get(entity));
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        sql.append(");");
        System.out.println(sql);
        System.out.println(fieldsValues);
        template.update(sql.toString(), fieldsValues.toArray());
        // генерируем insert into
    }

    // User user = entityManager.findById("account", User.class, Long.class, 10L);
    public <T, ID> T findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {
        String sql = "SELECT * FROM " + tableName + " WHERE id=?";
        RowMapper<T> r = row -> {
            Field[] fields = resultType.getDeclaredFields();
            T t;
            try {
                t = resultType.newInstance();
                for (Field f : fields){
                    f.setAccessible(true);
                    f.set(t, row.getObject(f.getName()));
                }
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
            return t;
        };
        List<T> list = template.query(sql, r, idValue);
        if(list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        String url = "jdbc:postgresql://localhost:5432/itis_practice";
        String user = "postgres";
        String password = "itis2020";
        ds.setURL(url);
        ds.setUser(user);
        ds.setPassword(password);
        EntityManager manager = new EntityManager(ds);
        System.out.println(manager.findById("user_test", User.class, Long.TYPE, 1L));
    }
}

