import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    private ZodiacGUI gui;
    private GameState gameState;
    private JLabel progressLabel;

    public MainMenuPanel(ZodiacGUI gui, GameState gameState) {
        this.gui = gui;
        this.gameState = gameState;
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15,15,15,15);
        progressLabel = new JLabel("", SwingConstants.CENTER);
        progressLabel.setForeground(Color.YELLOW);
        gbc.gridx = 0; gbc.gridy = 0;
        add(progressLabel, gbc);

        JButton journey = new JButton("Journey");
        journey.addActionListener(e -> gameState.startWorldExploration());
        gbc.gridy = 1; add(journey, gbc);
        JButton stats = new JButton("View Stats");
        stats.addActionListener(e -> gui.showScreen("Stats"));
        gbc.gridy = 2; add(stats, gbc);
        JButton shop = new JButton("Visit Shop");
        shop.addActionListener(e -> gui.showScreen("Shop"));
        gbc.gridy = 3; add(shop, gbc);
        JButton quit = new JButton("Quit Game");
        quit.addActionListener(e -> System.exit(0));
        gbc.gridy = 4; add(quit, gbc);
    }

    public void updateMenuDisplay() {
        int worlds = gameState.getWorldCounter();
        if (gameState.isInWorldExploration()) {
            worlds = worlds - 1;
        }
        progressLabel.setText("Worlds Conquered: " + worlds + "/3");
        if (gameState.isInWorldExploration()) {
            int total = 4 + gameState.getRestPenaltyCount();
            progressLabel.setText(progressLabel.getText() + "   |   Current World Progress: Minion " +
                    (gameState.getCurrentMinionIndex() + 1) + " of " + total);
        }
    }
}