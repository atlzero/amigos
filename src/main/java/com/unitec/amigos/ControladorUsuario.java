package com.unitec.amigos;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ControladorUsuario {
    //Aqui va un metodo que representa cada uno d elos estados que vamos a transferir
    //es decir, va un GET, POST, PUT Y DELETE. como minimo.

    //Aqui viene ya el uso de la inversion de control
    @Autowired RepositorioUsuario repoUsuario;



    //Implementamos el codigo para guardar un usuario en Mongo DB
    @PostMapping("/usuario")
    public Estatus guardar(@RequestBody String json)throws Exception{
    //Primero leemos y convertimos el objeto JSON a objeto Java
        ObjectMapper mapper=new ObjectMapper();
        Usuario u=mapper.readValue(json,Usuario.class);
        //Este usuario, ya en formato json lo guardamos en MONGODB
        repoUsuario.save(u);
        //Creamos un objeto de tipo Estatus y este Objeto lo retornamos al cliente (android o postman)
        Estatus estatus=new Estatus();
        estatus.setSucces(true);
        estatus.setMensaje("Tu usuario se guardo con exito!!");
        return estatus;
    }

    @GetMapping("/usuario/{id}")
    public Usuario obtenerPorId(@PathVariable String id){
        //Leemos un usario con el metodo findById pasandole como argumento el id(email que queremos
        //apoyandonos de repoUsuario
        Usuario u= repoUsuario.findById(id).get();
        return u;

    }

    @GetMapping("/usuario")
    public List<Usuario> buscarTodos(){

        return repoUsuario.findAll();
    }

    //Metodo para actualizar
    @PutMapping("/usuario")
    public Estatus actualizar(@RequestBody String json)throws Exception{
        //Primero debemos verificar que exista, por lo tanto primero lo buscamos
        ObjectMapper mapper=new ObjectMapper();
        Usuario u=mapper.readValue(json, Usuario.class);
        Estatus e=new Estatus();
            if(repoUsuario.findById(u.getEmail()).isPresent()){
                //Lo volvemos a guardar
                repoUsuario.save(u);
                e.setMensaje("Usuario se actualizo ocn exito");
                e.setSucces(true);
        }else{
                e.setMensaje("Ese usuario no existe, se actualizara");
                e.setSucces(false);
            }
            return e;
    }
    @DeleteMapping("/usuario/{id}")
    public Estatus borrar(@PathVariable String id){
        Estatus estatus=new Estatus();
        if(repoUsuario.findById(id).isPresent()){
            repoUsuario.deleteById(id);
            estatus.setSucces(true);
            estatus.setMensaje("Usuario borrado con exito");
        }else{
            estatus.setSucces(false);
            estatus.setMensaje("Ese usuario no exite");
        }
        return estatus;
    }
}
