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

/**
 *
 * @author 642202
 */
public class NoteDB {
    
    public int insert (Note note) throws NotesDBException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        
        try {
            String preparedQuery = "INSERT INTO Notes (dateCreated, contents) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(preparedQuery);
            ps.setDate(1, new java.sql.Date(note.getDateCreated().getTime()));
            ps.setString(2, note.getContents());
            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotesDBException("Could not add note");
        } finally {
            pool.freeConnection(connection);
        }
    }
    
    public int update (Note note) throws NotesDBException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            String preparedStatement = "UPDATE Notes SET "
                    + "contents = ? "
                    + "WHERE noteId = ?";
            PreparedStatement ps = connection.prepareStatement(preparedStatement);
            ps.setString(1, note.getContents());
            ps.setInt(2, note.getNoteId());
            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotesDBException("Could not update note");
        } finally {
            pool.freeConnection(connection);
        }
    }
    
    public List<Note> getAll () throws NotesDBException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Notes");
            ResultSet rs = ps.executeQuery();
            List<Note> notes = new ArrayList<Note>();
            while (rs.next()){
                notes.add(new Note(rs.getInt("noteId"), rs.getDate("dateCreated"), rs.getString("contents")));
            }
            return notes;
        } catch (SQLException ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotesDBException("Could not find notes");
        } finally {
            pool.freeConnection(connection);
        }
    }
    
    public Note getNote(int noteId) throws NotesDBException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Notes"
                    + " WHERE noteId = ?");
            ps.setInt(1, noteId);
            ResultSet rs = ps.executeQuery();
            Note note = null;
            while (rs.next()){
                note = new Note(rs.getInt("noteId"), rs.getDate("dateCreated"), rs.getString("contents"));
            }
            return note;
        } catch (SQLException ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            throw new NotesDBException("Could not find notes");
        } finally {
            pool.freeConnection(connection);
        }
    }
    
    public int delete(Note note) throws NotesDBException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        try {
            String preparedStatement = "DELETE FROM Notes "
                    + "WHERE noteId = ?";
            PreparedStatement ps = connection.prepareStatement(preparedStatement);
            ps.setInt(1, note.getNoteId());
            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotesDBException("Could not update note");
        } finally {
            pool.freeConnection(connection);
        }
    }
}
