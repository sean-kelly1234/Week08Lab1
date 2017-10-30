package servlets;

import businesslogic.NoteService;
import dataaccess.NotesDBException;
import domainmodel.Note;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 642202
 */
public class NoteServlet extends HttpServlet {    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        NoteService ns = new NoteService();
        try {
            List<Note> notes = ns.getAll();
            request.setAttribute("notes", notes);
            getServletContext().getRequestDispatcher("/WEB-INF/notes.jsp").forward(request, response);
        } catch (NotesDBException ex) {
            Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        NoteService ns = new NoteService();
        if (action != null){
            if (action.equals("add")){
                String contents = request.getParameter("contents");
                try {
                    if (!contents.equals("")){
                        ns.insert(contents);
                    }
                    else {
                        request.setAttribute("errorMessage", "contents can't be empty");
                    }
                    List<Note> notes = ns.getAll();
                    request.setAttribute("notes", notes);
                    getServletContext().getRequestDispatcher("/WEB-INF/notes.jsp").forward(request, response);
                } catch (NotesDBException ex) {
                    Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (action.equals("view")){
                try {
                    Note note = ns.getNote(Integer.parseInt(request.getParameter("selectedNote")));
                    List<Note> notes = ns.getAll();
                    request.setAttribute("selectedNote", note);
                    request.setAttribute("notes", notes);
                    getServletContext().getRequestDispatcher("/WEB-INF/notes.jsp").forward(request, response);
                } catch (NotesDBException ex) {
                    Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (action.equals("edit")){
                int noteId = Integer.parseInt(request.getParameter("noteId"));
                String contents = request.getParameter("contents");
                try {
                    ns.update(noteId, contents);
                    List<Note> notes = ns.getAll();
                    request.setAttribute("notes", notes);
                    getServletContext().getRequestDispatcher("/WEB-INF/notes.jsp").forward(request, response);
                } catch (NotesDBException ex) {
                    Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (action.equals("delete")){
                int noteId = Integer.parseInt(request.getParameter("selectedNote"));
                try {
                    ns.delete(noteId);
                    List<Note> notes = ns.getAll();
                    request.setAttribute("notes", notes);
                    getServletContext().getRequestDispatcher("/WEB-INF/notes.jsp").forward(request, response);
                } catch (NotesDBException ex) {
                    Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }

}
