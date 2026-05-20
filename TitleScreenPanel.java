import javax.swing.*;
import java.awt.*;

public class TitleScreenPanel extends JPanel {
    private ZodiacGUI gui;

    public TitleScreenPanel(ZodiacGUI gui) {
        this.gui = gui;
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        JTextArea title = new JTextArea(
                "============================================================\n" +
                "        RISE OF THE ZODIAC DEMIGOD\n" +
                "============================================================\n\n" +
                "Three Zodiac-born demigods rose against Zeus...\n" +
                "But only one will awaken and break free.\n\n" +
                "Press Start to begin your journey."
        );
        title.setEditable(false);
        title.setBackground(Color.BLACK);
        title.setForeground(Color.CYAN);
        title.setFont(new Font("Monospaced", Font.BOLD, 14));
        add(title, BorderLayout.CENTER);
        JButton startBtn = new JButton("Start");
        startBtn.addActionListener(e -> gui.showScreen("CharacterCreation"));
        add(startBtn, BorderLayout.SOUTH);
    }
}