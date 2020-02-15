package club.bianchen1997.service.impl;

import club.bianchen1997.dao.UserDao;
import club.bianchen1997.dao.impl.UserDaoImpl;
import club.bianchen1997.domain.PageBean;
import club.bianchen1997.domain.User;
import club.bianchen1997.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();

    @Override
    public User find(String id) {
        return dao.find(Integer.parseInt(id));
    }

    @Override
    public int update(User user) {
        return dao.update(user);
    }

    @Override
    public List<User> findAll() {
//        调用UserDao
        return dao.findAll();
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {
        int currentPage = 0;
        int rows = 0;
        try {
            currentPage = Integer.parseInt(_currentPage);
            rows = Integer.parseInt(_rows);
        } catch (NumberFormatException e) {
            currentPage = 1;
            rows = 5;
        }


//        创建空的pageBean对象
        PageBean<User> pb = new PageBean<>();

        if (currentPage < 1)
            currentPage = 1;


//        调用dao查询总记录数
        int totalCount = dao.findTotalCount(condition);
        pb.setTotalCount(totalCount);

        if (currentPage > totalCount / rows + 1)
            currentPage = totalCount / rows + 1;


//        设置参数
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);

//        调用dao查询list集合
//        计算start
        int start = (currentPage - 1) * rows;
        List<User> list = dao.findByPage(start, rows, condition);

//        设置list
        pb.setList(list);

//        设置totalPage
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;

        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public void deleteSelectedUsers(String[] ids) {
        if (ids != null && ids.length > 0) {
            for (String id : ids) {
                dao.delete(id);
            }
        }
    }

    @Override
    public User login(User user) {
        return dao.findUserByUsernameAndPassword(user);
    }

    @Override
    public int deleteUser(String id) {
        return dao.delete(id);
    }

    @Override
    public int add(User user) {
        return dao.add(user);
    }
}
