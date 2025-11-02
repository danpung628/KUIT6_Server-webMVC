package jwp.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import jwp.model.Question;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class QuestionDao {
    private final JdbcTemplate<Question> jdbcTemplate = new JdbcTemplate<>();

    /**
     * 모든 질문 목록을 조회합니다.
     */
    public List<Question> findAll() throws SQLException {
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer " +
                "FROM QUESTIONS " +
                "ORDER BY questionId DESC";

        RowMapper<Question> rowMapper = resultSet -> new Question(
                resultSet.getLong("questionId"),
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("contents"),
                resultSet.getTimestamp("createdDate"),
                resultSet.getInt("countOfAnswer")
        );

        return jdbcTemplate.query(sql, rowMapper);
    }

    /**
     * questionId로 질문을 조회합니다.
     */
    public Question findByQuestionId(Long questionId) throws SQLException {
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer " +
                "FROM QUESTIONS " +
                "WHERE questionId = ?";

        PreparedStatementSetter pss = pstmt -> {
            pstmt.setLong(1, questionId);
        };

        RowMapper<Question> rowMapper = resultSet -> new Question(
                resultSet.getLong("questionId"),
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("contents"),
                resultSet.getTimestamp("createdDate"),
                resultSet.getInt("countOfAnswer")
        );

        return jdbcTemplate.queryForObject(sql, pss, rowMapper);
    }

    /**
     * 새로운 질문을 저장하고, 저장된 질문을 반환합니다.
     */
    public Question insert(String writer, String title, String contents) throws SQLException {
        String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) " +
                "VALUES (?, ?, ?, ?, ?)";

        Timestamp now = new Timestamp(System.currentTimeMillis());
        KeyHolder keyHolder = new KeyHolder();

        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, writer);
            pstmt.setString(2, title);
            pstmt.setString(3, contents);
            pstmt.setTimestamp(4, now);
            pstmt.setInt(5, 0); // countOfAnswer 초기값 0
        };

        // INSERT 실행 및 생성된 ID 받아오기
        jdbcTemplate.update(sql, pss, keyHolder);

        // 생성된 질문을 다시 조회해서 반환
        return findByQuestionId(keyHolder.getId());
    }
}