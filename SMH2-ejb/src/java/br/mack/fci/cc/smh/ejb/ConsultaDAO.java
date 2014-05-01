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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.w3c.dom.Document;

/**
 *
 * @author prgoes
 */
@Stateless
public class ConsultaDAO {
    
    @EJB 
    EventoDAO eventos;
    
    public BasicDBObject gerarDocument(Consulta c) {
        ArrayList x = new ArrayList();
            for(Evento e : c.getEventos()) {
                BasicDBObject eventoDocument = eventos.gerarDocument(e);
                x.add(eventoDocument);
            }

            BasicDBObject doc = new BasicDBObject("DataDaConsulta", new Date()).
                              append("Dados", x).
                              append("Formula", c.getFormula());
            
        return doc;
    }
    
    public void gravarConsulta(Consulta c) {
        MongoClient mongoClient = null;
        DBCollection coll = null;
        try {
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB("Parking");
            coll = db.getCollection("Consultas");
            
            BasicDBObject doc = gerarDocument(c);

            coll.insert(doc);
        } catch (UnknownHostException ex) {
        Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            mongoClient.close();
        }
    }
    
}