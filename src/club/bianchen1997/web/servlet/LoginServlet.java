package club.bianchen1997.web.servlet;

import club.bianchen1997.domain.User;
import club.bianchen1997.service.UserService;
import club.bianchen1997.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        设置编码
        request.setCharacterEncoding("utf-8");

//        获取数据
//        验证码
        String verifyCode = request.getParameter("verifyCode");
//        校验验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");

//        确认验证码仅可用一次
        session.removeAttribute("CHECKCODE_SERVER");

//             验证码不正确
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(verifyCode)) {
//             提示信息
            request.setAttribute("login_msg", "验证码错误!");
//             跳转至错误界面
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

//        获取用户填写的对象
        Map<String, String[]> map;
        map = request.getParameterMap();

//        封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

//        调用Service查询
        UserService service = new UserServiceImpl();
        User loginUser = service.login(user);

//        判断登录结果
        if (loginUser != null) {
//            登录成功
//            将用户存入session,跳转页面
            session.setAttribute("user", loginUser);
            response.sendRedirect(request.getContextPath() + "/index.jsp");


        } else {
//            登录失败
//            提示信息
            request.setAttribute("login_msg", "用户名或密码错误!");
//            跳转登录页面
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
