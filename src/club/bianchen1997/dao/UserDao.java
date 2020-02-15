package club.bianchen1997.dao;

import club.bianchen1997.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户操作DAO
 */
public interface UserDao {
    public List<User> findAll();

    public User findUserByUsernameAndPassword(User user);

    public int add(User user);

    public int delete(String id);

    public User find(int id);

    public int update(User user);

    public int findTotalCount(Map<String, String[]> condition);

    public List<User> findByPage(int start, int rows, Map<String, String[]> condition);
}
