package core.jdbc;

import jwp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate<T> {
    public void update(String sql, PreparedStatementSetter preparedStatementSetter) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ) {
            preparedStatementSetter.setParameters(preparedStatement);
            preparedStatement.executeUpdate();
        }
    }

    public List<T> query(String sql, RowMapper<T> rowMapper) throws SQLException {
        List<T> objects = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery();
        ) {

            while (resultSet.next()) {
                T object = rowMapper.mapRow(resultSet);
                objects.add(object);
            }
        }
        return objects;
    }

    public T queryForObject(String sql, PreparedStatementSetter preparedStatementSetter, RowMapper<T> rowMapper) throws SQLException {
        ResultSet resultSet = null;
        T object = null;

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ) {
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                object = rowMapper.mapRow(resultSet);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return object;
    }
}
