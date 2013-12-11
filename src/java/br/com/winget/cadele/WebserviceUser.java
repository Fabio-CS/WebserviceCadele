/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.winget.cadele;

import br.com.winget.cadele.control.UsuarioDAO;
import br.com.winget.cadele.model.Usuario;
import com.google.gson.Gson;
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
    @Produces("application/json")
    public String setFriendship(@PathParam("idUser") String idUser, @PathParam("idUserFriend") String idUserFriend) {
        UsuarioDAO userDao = new UsuarioDAO();
        boolean teste = userDao.insertFriendship(Integer.parseInt(idUser), Integer.parseInt(idUserFriend));
        JSONObject json = new JSONObject();
        if(teste)
            json.put("Mensagem", "Amizade registrada com sucesso");
        else
            json.put("Mensagem", "Erro");
        return json.toString();
    }
    
    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public String userLogin(@FormParam("email") String email, @FormParam("senha") String senha) {
        UsuarioDAO userDao = new UsuarioDAO();
        JSONObject json = new JSONObject();
        int id = userDao.getUserLogin(email, senha);
        if(id != 0){
            Usuario user = userDao.getUserById(id);
            Gson gson = new Gson();
            JSONObject jsonUser = new JSONObject(gson.toJson(user));
            json.put("status", 1);
            json.put("user", jsonUser);
            return json.toString();
        }else{
            json.put("status", 0);
            json.put("mensagem", "Login ou senha incorretos");
            return json.toString();
        }
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
       return gson.toJson(user);
    }
}
