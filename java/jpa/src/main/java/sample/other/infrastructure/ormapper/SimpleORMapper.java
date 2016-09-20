package sample.other.infrastructure.ormapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SimpleORMapper {
    private Connection con;

    public SimpleORMapper(Connection con) {
        this.con = con;
    }

    public <T> List<T> findList(Class<T> clazz, String sql, Object... parameters) {
        try (PreparedStatement ps = this.con.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                ps.setObject(i+1, parameters[i]);
            }

            Constructor<T> constructor = clazz.getConstructor();
            Field[] fields = clazz.getDeclaredFields();

            try (ResultSet rs = ps.executeQuery()) {
                constructor.setAccessible(true);
                List<T> list = new ArrayList<>();

                while (rs.next()) {
                    T instance = constructor.newInstance();

                    for (Field field : fields) {
                        try {
                            field.setAccessible(true);
                            this.setField(rs, instance, field);
                        } finally {
                            field.setAccessible(false);
                        }
                    }

                    list.add(instance);
                }

                return list;
            } finally {
                constructor.setAccessible(false);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> void setField(ResultSet rs, T instance, Field field) throws SQLException, IllegalAccessException {
        String columnName = this.getColumnName(field);
        Class<?> type = field.getType();

        if (type.equals(String.class)) {
            String value = rs.getString(columnName);
            field.set(instance, value);
        } else if (type.equals(int.class) || type.equals(Integer.class)) {
            int value = rs.getInt(columnName);
            field.set(instance, value);
        } else if (type.equals(long.class) || type.equals(Long.class)) {
            long value = rs.getLong(columnName);
            field.set(instance, value);
        } else if (type.equals(Date.class)) {
            java.sql.Date sqlDate = rs.getDate(columnName);
            field.set(instance, new Date(sqlDate.getTime()));
        } else {
            throw new RuntimeException("unknown type : field{name=" + field.getName() + ", type=" + type + "}, rs.getObject()=" + rs.getObject(columnName));
        }
    }

    private String getColumnName(Field field) {
        String columnName;
        Column column = field.getAnnotation(Column.class);
        if (column != null) {
            columnName = column.value();
        } else {
            columnName = field.getName();
        }
        return columnName;
    }

    public <T> T find(Class<T> clazz, String sql, Object... parameters) {
        List<T> list = this.findList(clazz, sql, parameters);
        if (list.isEmpty()) {
            return null;
        } else if (1 < list.size()) {
            throw new RuntimeException("multi result exception");
        }

        return list.get(0);
    }
}
