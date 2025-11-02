package core.jdbc;

import jwp.model.User;

import java.sql.*;
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

    // KeyHolder를 사용하는 update 메서드 추가
    public void update(String sql, PreparedStatementSetter preparedStatementSetter, KeyHolder keyHolder) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatementSetter.setParameters(preparedStatement);
            preparedStatement.executeUpdate();

            // 생성된 키 값을 KeyHolder에 저장
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                keyHolder.setId(rs.getLong(1));
            }
            rs.close();
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
            preparedStatementSetter.setParameters(preparedStatement);
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
