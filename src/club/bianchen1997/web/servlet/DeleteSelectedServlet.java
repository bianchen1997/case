package club.bianchen1997.web.servlet;

import club.bianchen1997.service.UserService;
import club.bianchen1997.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteSelectedServlet")
public class DeleteSelectedServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

//        获取参数
        String[] ids = request.getParameterValues("uid");
        for (int i = 0; i < ids.length; i++) {
            System.out.println(ids[i]);
        }

//        调用service中的方法
        UserServiceImpl service = new UserServiceImpl();
        service.deleteSelectedUsers(ids);

//        跳转到查询所有servlet
        response.sendRedirect(request.getContextPath() + "/userListServlet");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
