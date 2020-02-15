package club.bianchen1997.service;

import club.bianchen1997.domain.PageBean;
import club.bianchen1997.domain.User;

import java.util.List;
import java.util.Map;

//用户管理的业务接口
public interface UserService {
    /**
     * 查询所有用户信息
     *
     * @return
     */
    public List<User> findAll();


    public User login(User user);

    public int add(User user);

    public int deleteUser(String id);

    public User find(String id);

    public int update(User user);

    public void deleteSelectedUsers(String[] ids);

    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition);
}
