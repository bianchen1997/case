package club.bianchen1997.web.servlet;

import club.bianchen1997.dao.impl.UserDaoImpl;
import club.bianchen1997.domain.User;
import club.bianchen1997.service.UserService;
import club.bianchen1997.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/addUserServlet")
public class AddUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//       设置编码
        request.setCharacterEncoding("utf-8");
//       获取参数
        Map<String, String[]> map = request.getParameterMap();

//        封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

//        调用Service方法
        UserService service = new UserServiceImpl();
        int result = service.add(user);
        System.out.println(result);

//        判断是否添加成功
//        直接重定向
        response.sendRedirect(request.getContextPath() + "/userListServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
