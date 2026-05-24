import java.awt.TextArea;
import javax.swing.JDialog;

public class DialogueBox{
    JDialog box;

    public DialogueBox(String title, String text, Window window){
        this.box = new JDialog(window);
        this.box.setBounds(400,400,150,70);
        this.box.toFront();
        this.box.setVisible(true);
        this.box.setTitle(title);

        TextArea boxInfo = new TextArea(text);
        boxInfo.setEditable(false);
        this.box.add(boxInfo);
    }

}