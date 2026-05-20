import javax.swing.*;
import java.awt.*;

public class ZodiacGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private GameState gameState;
    private BattlePanel battlePanel;

    public ZodiacGUI() {
        setTitle("Rise of the Zodiac Demigod");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        gameState = new GameState(this, null);
        battlePanel = new BattlePanel(this, gameState);
        gameState.setBattlePanel(battlePanel);
        
        TitleScreenPanel titlePanel = new TitleScreenPanel(this);
        CharacterCreationPanel charPanel = new CharacterCreationPanel(this, gameState);
        MainMenuPanel mainMenuPanel = new MainMenuPanel(this, gameState);
        ShopPanel shopPanel = new ShopPanel(this, gameState);
        StatsPanel statsPanel = new StatsPanel(this, gameState);
        
        mainPanel.add(titlePanel, "Title");
        mainPanel.add(charPanel, "CharacterCreation");
        mainPanel.add(mainMenuPanel, "MainMenu");
        mainPanel.add(battlePanel, "Battle");
        mainPanel.add(shopPanel, "Shop");
        mainPanel.add(statsPanel, "Stats");
        
        add(mainPanel);
        cardLayout.show(mainPanel, "Title");
        setVisible(true);
    }

    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
        if (screenName.equals("Stats")) {
            ((StatsPanel) mainPanel.getComponent(5)).refreshStats();
        } else if (screenName.equals("Shop")) {
            ((ShopPanel) mainPanel.getComponent(4)).refreshShop();
        } else if (screenName.equals("MainMenu")) {
            ((MainMenuPanel) mainPanel.getComponent(2)).updateMenuDisplay();
        }
    }

    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ZodiacGUI::new);
    }
}