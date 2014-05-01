/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mack.fci.cc.smh.ejb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.w3c.dom.Document;

/**
 *
 * @author prgoes
 */
@Stateless
public class EventoDAO {
    
    public String teste(String nome) {
        return "Ola " + nome;
    }
    
    public void gravarEvento() {
        
        MongoClient mongoClient = null;
        DBCollection coll = null;
        try {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB( "Parking" );
            coll = db.getCollection("Eventos");

            BasicDBObject doc = new BasicDBObject("DataDoEvento", new Date()).
                              append("Sensor", "Sensor3").
                              append("Tipo", "Ocupado").
                              append("Valor", "true");

            coll.insert(doc);
        } catch (UnknownHostException ex) {
        Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            mongoClient.close();
        }
    }
    
    public void gravarEvento(Document xmlEvento) {
        MongoClient mongoClient = null;
        DBCollection coll = null;
        try {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB( "Parking" );
            coll = db.getCollection("Eventos");

            BasicDBObject doc = new BasicDBObject("DataDoEvento", xmlEvento.getElementsByTagName("data").item(0).getTextContent()).
                              append("Sensor", xmlEvento.getElementsByTagName("unidadegrandeza").item(0).getTextContent()).
                              append("Tipo", "Ocupado").
                              append("Valor", xmlEvento.getElementsByTagName("valor").item(0).getTextContent());

            coll.insert(doc);
        } catch (UnknownHostException ex) {
        Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            mongoClient.close();
        }
    }
    
    public List<Evento> lerTodosOsEventos() {
        List<Evento> lista = new ArrayList<>();
        
        MongoClient mongoClient = null;
        DBCollection coll = null;
        try {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB( "Parking" );
            coll = db.getCollection("Eventos");
        
            DBCursor cursor = coll.find();
            try {
               while(cursor.hasNext()) {
                   lista.add(new Evento(cursor.next()));
               }            
            } catch(Exception ex) {
                    Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
               cursor.close();
            }
        
        } catch (UnknownHostException ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            mongoClient.close();
        }
        
        return lista;
    }
    
}
