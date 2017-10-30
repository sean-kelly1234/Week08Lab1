/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import domainmodel.Note;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author 642202
 */
public class NoteDB {
    
    public int insert (Note note) throws NotesDBException{
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.persist(note);
            trans.commit();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, null, ex);
            trans.rollback();
            throw new NotesDBException("Could not add note");
        } finally {
            em.close();
        }
    }
    
    public int update (Note note) throws NotesDBException{
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(note);
            trans.commit();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, null, ex);
            trans.rollback();
            throw new NotesDBException("Could not update note");
        } finally {
            em.close();
        }
    }
    
    public List<Note> getAll () throws NotesDBException{
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Note> notes = em.createNamedQuery("Note.findAll", Note.class).getResultList();
            return notes;
        } catch (Exception ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotesDBException("Could not find notes");
        } finally {
            em.close();
        }
    }
    
    public Note getNote(int noteId) throws NotesDBException{
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Note note = em.find(Note.class, noteId);
            return note;
        } catch (Exception ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotesDBException("Could not find note");
        } finally {
            em.close();
        }
    }
    
    public int delete(Note note) throws NotesDBException{
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.remove(em.merge(note));
            trans.commit();
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotesDBException("Could not delete note");
        } finally {
            em.close();
        }
    }
}
