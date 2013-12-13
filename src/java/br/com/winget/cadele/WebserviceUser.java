/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.winget.cadele;

import br.com.winget.cadele.control.LocalizacaoDAO;
import br.com.winget.cadele.control.UsuarioDAO;
import br.com.winget.cadele.model.Localizacao;
import br.com.winget.cadele.model.Usuario;
import com.google.gson.Gson;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author fmiranda
 */
@Path("/user")
public class WebserviceUser {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Webservice
     */
    public WebserviceUser() {
    }

    /**
     * Retrieves representation of an instance of br.com.winget.cadele.WebserviceUser
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJsonUser(@QueryParam("id") String id) {
        Gson gson = new Gson();
        UsuarioDAO userDao = new UsuarioDAO();
        Usuario user = userDao.getUserById(Integer.parseInt(id));
        return gson.toJson(user);
       /* }else if(action.equals("incluirLocalizacao")){
            Localizacao place = new Localizacao();
            place.setIdUser(Integer.parseInt(param1));
            place.setLatitude(Double.parseDouble(param2));
            place.setLongitude(Double.parseDouble(param3));
            place.setAltitude(Double.parseDouble(param4));
            
            LocalizacaoDAO placeDao = new LocalizacaoDAO();
            placeDao.incluir(place);
            
            json.put("Mensagem", "Localização incluída com sucesso!");
            retorno = json.toString();
        }*/
    }
    @GET
    @Path("friends/{idUser}/{idUserFriend}")
    @Produces("text/plain")
    public String setFriendship(@PathParam("idUser") String idUser, @PathParam("idUserFriend") String idUserFriend) {
        UsuarioDAO userDao = new UsuarioDAO();
        boolean teste = userDao.insertFriendship(Integer.parseInt(idUser), Integer.parseInt(idUserFriend));
        String retorno;
        if(teste)
            retorno = "Amizade registrada com sucesso";
        else
            retorno =  "Erro";
        return retorno;
    }
    
    @GET
    @Path("friends/search/{email}")
    @Produces("application/json")
    public String searchFriend(@PathParam("email") String email) {
        UsuarioDAO userDao = new UsuarioDAO();
        Usuario user = userDao.getUserSearch(email);
        Gson gson = new Gson();
            return gson.toJson(user);
    }
    
    @GET
    @Path("friends/list/{idUser}")
    @Produces("application/json")
    public String listFriends(@PathParam("idUser") String idUser) {
        UsuarioDAO userDao = new UsuarioDAO();
        ArrayList<Usuario> amigos = userDao.getUserFriends(Integer.parseInt(idUser));
        Gson gson = new Gson();
        return gson.toJson(amigos);
    }
    
    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public String userLogin(@FormParam("email") String email, @FormParam("senha") String senha) {
        UsuarioDAO userDao = new UsuarioDAO();
        int id = userDao.getUserLogin(email, senha);
        if(id != 0){
            Usuario user = userDao.getUserById(id);
            Gson gson = new Gson();
            return gson.toJson(user);
        }
        return "{null}";
    }

    /**
     * POST method for updating or creating an instance of WebserviceUser
     * @param nome
     * @param email
     * @param senha
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public String insertUser(@FormParam("nome") String nome, @FormParam("email") String email, @FormParam("senha") String senha) {
       Usuario user = new Usuario();
       Gson gson = new Gson();
       int id;
       UsuarioDAO userDao = new UsuarioDAO();
       user.setNome(nome);
       user.setEmail(email);
       user.setSenha(senha);
       user.setAtivo(1);
       id = userDao.insert(user);
       user.setId(id);
       LocalizacaoDAO localDao = new LocalizacaoDAO();
       Localizacao local = new Localizacao();
       local.setLongitude(0);
       local.setLatitude(0);
       local.setAltitude(0);
       local.setIdUser(id);
       localDao.insert(local);
       return gson.toJson(user);
    }
}
