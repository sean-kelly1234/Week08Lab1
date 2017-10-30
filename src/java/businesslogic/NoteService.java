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
        Note note = noteDB.getNote(noteId);
        note.setContents(contents);
        return noteDB.update(note);
    }
    
    public int delete(int noteId) throws NotesDBException{
        return noteDB.delete(noteDB.getNote(noteId));
    }
    
    public int insert(String contents) throws NotesDBException{
        return noteDB.insert(new Note(0, new Date(), contents));
    }
}
