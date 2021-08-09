package mx.edu.utez.model.user;

import mx.edu.utez.service.ConnectionMySQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoUser {
    Connection con;
    CallableStatement cstm;
    ResultSet rs;
    Logger logger = LoggerFactory.getLogger(DaoUser.class);

    public List<BeanUser> findAll(){
        List<BeanUser> listUsers = new ArrayList<>();
        try {
            // SELECT * FROM users AS U INNER JOIN persons AS P ON U.idPerson = P.id INNER JOIN roles AS R ON U.idRole = R.id;
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("{call sp_findAll}");
            rs = cstm.executeQuery();

            while(rs.next()){
                BeanUser user = new BeanUser();

                user.setId(rs.getInt("id"));
                user.setNombre(rs.getString("nombre"));
                user.setDescripcion(rs.getString("descripcion"));
                user.setFecha(rs.getString("fecha"));
                user.setEstado(rs.getInt("estado"));

                listUsers.add(user);
            }
        }catch (SQLException e){
            logger.error("Ha ocurrido un error: " + e.getMessage());
        } finally {
            ConnectionMySQL.closeConnections(con, cstm, rs);
        }
        return listUsers;
    }

    public BeanUser findById(int id){
        BeanUser user = null;
        try {
            // SELECT * FROM users AS U INNER JOIN persons AS P ON U.idPerson = P.id INNER JOIN roles AS R ON U.idRole = R.id;
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("{call sp_findById(?)}");
            cstm.setInt(1, id);
            rs = cstm.executeQuery();

            if(rs.next()){
                user = new BeanUser();

                user.setId(rs.getInt("id"));
                user.setNombre(rs.getString("nombre"));
                user.setDescripcion(rs.getString("descripcion"));
                user.setFecha(rs.getString("fecha"));
                user.setEstado(rs.getInt("estado"));
            }
        }catch (SQLException e){
            logger.error("Ha ocurrido un error: " + e.getMessage());
        } finally {
            ConnectionMySQL.closeConnections(con, cstm, rs);
        }
        return user;
    }

    public boolean create(BeanUser user){
        boolean flag = false;
        try{
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("{call sp_create(?,?,?,?)}");
            cstm.setString(1, user.getNombre());
            cstm.setString(2, user.getDescripcion());
            cstm.setString(3, user.getFecha());
            cstm.setInt(4, user.getEstado());
            cstm.execute();
            flag = true;
        }catch(SQLException e){
            logger.error("Ha ocurrido un error: " + e.getMessage());
        } finally {
            ConnectionMySQL.closeConnections(con, cstm);
        }
        return flag;
    }

    public boolean update(BeanUser user){
        boolean flag = false;
        try{
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("{call sp_update(?,?,?,?,?)}");
            cstm.setInt(1, (int) user.getId());
            cstm.setString(2, user.getNombre());
            cstm.setString(3, user.getDescripcion());
            cstm.setString(4, user.getFecha());
            cstm.setInt(5, user.getEstado());

            flag = cstm.execute();
        }catch(SQLException e){
            logger.error("Ha ocurrido un error: " + e.getMessage());
        }finally{
            ConnectionMySQL.closeConnections(con, cstm);
        }
        return flag;
    }

    public boolean delete(int idUser){
        boolean flag = false;
        try{
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("{call sp_delete(?)}");
            cstm.setLong(1, idUser);

            flag = cstm.execute();
        }catch(SQLException e){
            logger.error("Ha ocurrido un error: " + e.getMessage());
        }finally{
            ConnectionMySQL.closeConnections(con, cstm);
        }
        return flag;
    }
/*
    public static void main(String[] args) {
        BeanUser beanUser = new BeanUser();
        BeanPerson beanPerson = new BeanPerson();
        BeanRole beanRole = new BeanRole();
        DaoUser daoUser = new DaoUser();

        // Listando usuarios
        List<BeanUser> listUsers = new ArrayList<>();
        listUsers = daoUser.findAll();

        for (int i = 0; i < listUsers.size(); i++){
            System.out.println(listUsers.get(i).getEmail());
        }

        /*
        // Registrando usuarios
        boolean registed = false;

        beanRole.setId(1);

        beanPerson.setName("Paty");
        beanPerson.setLastname("Morales");
        beanPerson.setEdad(29);

        beanUser.setEmail("patymorales@utez.edu.mx");
        beanUser.setPassword("admin1234");

        beanUser.setIdPerson(beanPerson);
        beanUser.setIdRole(beanRole);

        registed = daoUser.create(beanUser);

        System.out.println("Se ha registrado correctamente");
        */

        // Eliminar de manera "baja lógica"
        /*
        boolean flag = false;
        flag = daoUser.delete(4);
        System.out.println("Se realizó correctamente");

    }*/
}
