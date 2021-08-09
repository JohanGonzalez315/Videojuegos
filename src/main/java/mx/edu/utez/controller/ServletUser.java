package mx.edu.utez.controller;

import com.google.gson.Gson;
import mx.edu.utez.model.user.BeanUser;
import mx.edu.utez.model.user.DaoUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ServletUser", urlPatterns = {"/readUsers", "/createUser", "/getUserById", "/findById", "/updateUser", "/deleteUser"})
public class ServletUser extends HttpServlet {
    Logger logger = LoggerFactory.getLogger(ServletUser.class);
    BeanUser beanUser = new BeanUser();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("listUsers", new DaoUser().findAll());
        request.getRequestDispatcher("/views/user/users.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        switch(action){
            case "create":
                // do something
                String nombre = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
                String descripcion = request.getParameter("descripcion");
                String fecha = request.getParameter("fecha");
                int estado = Integer.parseInt(request.getParameter("estado"));

                BeanUser beanUser = new BeanUser(0, nombre, descripcion, fecha, estado);

                if(new DaoUser().create(beanUser)){
                    request.setAttribute("message", "Usuario registrado correctamente");
                } else {
                    request.setAttribute("message", "Usuario no registrado");
                }

                doGet(request, response);
                break;

            case "getUserById":
                // do something
                int id4 = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("user", new DaoUser().findById(id4));
                request.getRequestDispatcher("/views/user/update.jsp").forward(request, response);
                break;
            case "findById":
                // do something
                try {
                    Gson gson = new Gson();
                    int id3 = Integer.parseInt(request.getParameter("id"));

                    map.put("user", new DaoUser().findById(id3));

                    response.setStatus(200);
                }catch(Exception e){
                    response.setStatus(400);
                    logger.error("Usuario no encontrado.");
                }
                write(response, map);
                break;
            case "update":
                // do something
                int id1 = Integer.parseInt(request.getParameter("id"));
                String nombre1 = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
                String descripcion1 = request.getParameter("descripcion");
                String fecha1 = request.getParameter("fecha");
                int estado1 = Integer.parseInt(request.getParameter("estado"));

                BeanUser beanUser1 = new BeanUser(id1, nombre1, descripcion1, fecha1, estado1);

                if(new DaoUser().update(beanUser1)){
                    request.setAttribute("message", "Usuario modificado correctamente");
                } else {
                    request.setAttribute("message", "Usuario no modificado");
                }

                doGet(request, response);
                break;
            case "delete":
                // do something
                int id2 = (int) Integer.parseInt(request.getParameter("id"));
                if(new DaoUser().delete(id2)){
                    request.setAttribute("message", "Usuario eliminado correctamente");
                } else {
                    request.setAttribute("message", "Usuario no eliminado");
                }
                doGet(request, response);
                break;
            default:
                // no supported
                break;
        }
    }

    private void write(HttpServletResponse response, Map<String, Object> map) throws IOException{
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(map));
    }
}

