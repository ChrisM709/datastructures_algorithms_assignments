import java.util.ArrayList;
import java.util.List;

public class MenuFunctions {
    private final List<String> current = new ArrayList<>();
    private final UndoRedoManager<List<String>> history = new UndoRedoManager<>();

    public MenuFunctions() {
        history.push(copyOfCurrent());
    }

    public List<String> viewMenu() {
        return List.copyOf(current);
    }

    public void addItem(String item) {
        current.add(item);
        history.push(copyOfCurrent());    
    }

    public void undo() {
        current.clear();
        current.addAll(history.undo());  
    }

    public void redo() {
        current.clear();
        current.addAll(history.redo());   
    }

    private List<String> copyOfCurrent() {
        return new ArrayList<>(current); 
    }
}
