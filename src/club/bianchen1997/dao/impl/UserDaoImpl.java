package club.bianchen1997.dao.impl;

import club.bianchen1997.dao.UserDao;
import club.bianchen1997.domain.User;
import club.bianchen1997.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    private StringBuilder sb;
    private List<Object> params;

    @Override
    public List<User> findAll() {
//    使用JDBC操作数据库

        String sql = "select * from user";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public int delete(String id) {
        String sql = "delete from user where id=?";
        return jdbcTemplate.update(sql, Integer.parseInt(id));
    }

    @Override
    public int add(User user) {
        String sql = "insert into user values(null,?,?,?,?,?,?,null,null);";
//        System.out.println(user.toString());
        return jdbcTemplate.update(sql, user.getName(), user.getGender(), user.getAge(),
                user.getAddress(), user.getQq(), user.getEmail());
    }

    @Override
    public int update(User user) {
        String sql = "update user set name=?, gender=?, age=?, address=?, qq=?, email=? where id=?;";
        return jdbcTemplate.update(sql, user.getName(), user.getGender(), user.getAge(),
                user.getAddress(), user.getQq(), user.getEmail(), user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        String sql = "select COUNT(*) from user where 1 = 1";

        params = this.process(sql, condition);

//        System.out.println(sb.toString());
//        System.out.println(params);
//        System.out.println(jdbcTemplate.queryForObject(sb.toString(), Integer.class, params.toArray()));
        return jdbcTemplate.queryForObject(sb.toString(), Integer.class, params.toArray());
    }

    private List<Object> process(String sql, Map<String, String[]> condition) {
        sb = new StringBuilder(sql);

        params = new ArrayList<>();
        for (String key : condition.keySet()) {

            if ("currentPage".equals(key) || "rows".equals(key))
                continue;

            String value = condition.get(key)[0];

            if (value != null && !"".equals(value)) {
                sb.append(" and " + key + " like ?");
                params.add("%" + value + "%");
            }
        }

        return params;
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from user where 1 = 1";

        params = this.process(sql, condition);
        sb.append(" limit ?, ?");
//        System.out.println(sb.toString());
        params.add(start);
        params.add(rows);

//        System.out.println(sb.toString());

        return jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<User>(User.class), params.toArray());
    }

    @Override
    public User findUserByUsernameAndPassword(User user) {
        String sql = "select * from user where username=? and password=?";
        try {
            User loginUser = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
            return loginUser;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public User find(int id) {
        String sql = "select * from user where id=?;";
        try {
            User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
