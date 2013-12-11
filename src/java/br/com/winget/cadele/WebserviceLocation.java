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
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Fabio-CS
 */
@Path("/location")
public class WebserviceLocation {

    public WebserviceLocation() {
    }
    
    /**
     *
     * @param id
     * @return 
     */
    @GET
    @Produces("application/json")
    public String getJsonUserLocation(@QueryParam("id") String id){
        LocalizacaoDAO localDao = new LocalizacaoDAO();
        Gson gson = new Gson();
        Localizacao location = localDao.getLocalByUser(Integer.parseInt(id));
        return gson.toJson(location);
    }
    
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public String insertLocation(@FormParam("user") String user, @FormParam("latitude") String latitude, @FormParam("longitude") String longitude, @FormParam("altitude") String altitude ) {
       Localizacao location = new Localizacao();
       Gson gson = new Gson();
       int id;
       LocalizacaoDAO localDao = new LocalizacaoDAO();
       location.setIdUser(Integer.parseInt(user));
       location.setLatitude(Double.parseDouble(latitude));
       location.setLongitude(Double.parseDouble(longitude));
       location.setAltitude(Double.parseDouble(altitude));
       id = localDao.insert(location);
       location.setId(id);
       return gson.toJson(location);
    }
    
    @POST
    @Path("/update")
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public String updateLocation(@FormParam("id") String id, @FormParam("latitude") String latitude, @FormParam("longitude") String longitude, @FormParam("altitude") String altitude ) {
       Localizacao location = new Localizacao();
       Gson gson = new Gson();
       LocalizacaoDAO localDao = new LocalizacaoDAO();
       location.setId(Integer.parseInt(id));
       location.setLatitude(Double.parseDouble(latitude));
       location.setLongitude(Double.parseDouble(longitude));
       location.setAltitude(Double.parseDouble(altitude));
       localDao.update(location);
       return gson.toJson(location);
    }
}
