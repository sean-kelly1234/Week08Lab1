/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic;

import dataaccess.NoteDB;
import dataaccess.NotesDBException;
import domainmodel.Note;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 642202
 */
public class NoteService {
    
    private NoteDB noteDB;
    
    public NoteService(){
        noteDB = new NoteDB();
    }
    
    public Note getNote(int noteId) throws NotesDBException{
        return noteDB.getNote(noteId);
    }
    
    public List<Note> getAll() throws NotesDBException{
        return noteDB.getAll();
    }
    
    public int update(int noteId, String contents) throws NotesDBException{
        return noteDB.update(new Note(noteId, null, contents));
    }
    
    public int delete(int noteId) throws NotesDBException{
        return noteDB.delete(new Note(noteId, null, null));
    }
    
    public int insert(String contents) throws NotesDBException{
        return noteDB.insert(new Note(0, new Date(), contents));
    }
}
