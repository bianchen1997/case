package club.bianchen1997.web.servlet;

import club.bianchen1997.domain.User;
import club.bianchen1997.service.UserService;
import club.bianchen1997.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        设置编码
        request.setCharacterEncoding("utf-8");

//        获取传递的id参数
        String id = request.getParameter("id");

//        调用Service查询
        UserService service = new UserServiceImpl();
        User user = service.find(id);

//        封装对象
        request.setAttribute("user", user);

//        转发到update.jsp
        request.getRequestDispatcher("/update.jsp").forward(request, response);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
