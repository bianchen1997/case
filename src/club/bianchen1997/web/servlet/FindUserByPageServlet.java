package club.bianchen1997.web.servlet;

import club.bianchen1997.domain.PageBean;
import club.bianchen1997.domain.User;
import club.bianchen1997.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

//        获取参数
//        当前页码
        String currentPage = request.getParameter("currentPage");

//        每页条数
        String rows = request.getParameter("rows");

        Map<String, String[]> condition = request.getParameterMap();

        request.setAttribute("condition", condition);

        if (currentPage == null || "".equals(currentPage))
            currentPage = "1";

        if (rows == null || "".equals(currentPage))
            rows = "5";


//        调用service查询
        UserServiceImpl service = new UserServiceImpl();
        PageBean<User> pb = service.findUserByPage(currentPage, rows, condition);

//        System.out.println(pb);

        request.setAttribute("pb", pb);

        request.getRequestDispatcher("list.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
