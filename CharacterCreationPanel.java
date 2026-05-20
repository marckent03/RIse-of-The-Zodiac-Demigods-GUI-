import javax.swing.*;
import java.awt.*;

public class CharacterCreationPanel extends JPanel {
    private ZodiacGUI gui;
    private GameState gameState;
    private JRadioButton aries, cancer, sagittarius;
    private JTextField nameField;
    private JTextArea infoArea;

    public CharacterCreationPanel(ZodiacGUI gui, GameState gameState) {
        this.gui = gui;
        this.gameState = gameState;
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0; gbc.gridy = 0;
        main.add(new JLabel("Choose your Zodiac Demigod:"), gbc);
        ButtonGroup group = new ButtonGroup();
        aries = new JRadioButton("Aresios (Aries) - Broadsword");
        cancer = new JRadioButton("Selinia (Cancer) - Staff");
        sagittarius = new JRadioButton("Orionis (Sagittarius) - Bow");
        group.add(aries); group.add(cancer); group.add(sagittarius);
        gbc.gridy = 1; main.add(aries, gbc);
        gbc.gridy = 2; main.add(cancer, gbc);
        gbc.gridy = 3; main.add(sagittarius, gbc);
        gbc.gridy = 4; main.add(new JLabel("Enter Character Name (Leave it blank if you want to use the default name):"), gbc);
        nameField = new JTextField(15);
        gbc.gridy = 5; main.add(nameField, gbc);
        JButton infoBtn = new JButton("View Details");
        infoBtn.addActionListener(e -> showDetails());
        gbc.gridy = 6; main.add(infoBtn, gbc);
        JButton createBtn = new JButton("Start Journey");
        createBtn.addActionListener(e -> createPlayer());
        gbc.gridy = 7; main.add(createBtn, gbc);
        add(main, BorderLayout.CENTER);

        infoArea = new JTextArea(10,40);
        infoArea.setEditable(false);
        infoArea.setBackground(Color.DARK_GRAY);
        infoArea.setForeground(Color.WHITE);
        add(new JScrollPane(infoArea), BorderLayout.SOUTH);
    }

    private void showDetails() {
        String info = "";
        if (aries.isSelected()) {
            info = getCharacterInfoString(1);
        } else if (cancer.isSelected()) {
            info = getCharacterInfoString(2);
        } else if (sagittarius.isSelected()) {
            info = getCharacterInfoString(3);
        } else {
            info = "Select a demigod first.";
        }
        JOptionPane.showMessageDialog(this, info, "Character Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private String getCharacterInfoString(int choice) {
        StringBuilder sb = new StringBuilder();
        if (choice == 1) {
            sb.append("=== ARESIOS - ARIES DEMIGOD ===\n");
            sb.append("Weapon: Broadsword\n");
            sb.append("HP: 450     ||      MP: 150\n");
            sb.append("High Attack, Aggressive Fighter\n");
            sb.append("Forged in endless wars, thrives in chaos\n\n");
            sb.append("Skills:\n");
            sb.append("1. Raging Charge (DMG: 80, MP: 0) - Surges forward with raw force\n");
            sb.append("2. Blazing Strike (DMG: 120, MP: 30) - Flame-wreathed blade\n");
            sb.append("3. Infernal Cataclysm (DMG: 200, MP: 50) - Vortex of hellfire\n");
        } else if (choice == 2) {
            sb.append("=== SELINIA - CANCER DEMIGOD ===\n");
            sb.append("Weapon: Staff\n");
            sb.append("HP: 500     ||      MP: 200\n");
            sb.append("Balanced Stats, Supportive Abilities\n");
            sb.append("Once a healer, now hardened by betrayal\n\n");
            sb.append("Skills:\n");
            sb.append("1. Tidal Wave (DMG: 70, MP: 0) - Crashes down with tidal force\n");
            sb.append("2. Moonlit Barrier (DMG: 0, MP: 30) - Heals and shields\n");
            sb.append("3. Eclipse Embrace (DMG: 180, MP: 50) - Devastating eclipse strike\n");
        } else if (choice == 3) {
            sb.append("=== ORIONIS - SAGITTARIUS DEMIGOD ===\n");
            sb.append("Weapon: Bow\n");
            sb.append("HP: 400     ||      MP: 180\n");
            sb.append("High Damage, Precision Strikes\n");
            sb.append("Master hunter with starlight arrows\n\n");
            sb.append("Skills:\n");
            sb.append("1. Piercing Arrow (DMG: 60, MP: 0) - Precise starlight arrow\n");
            sb.append("2. Rain of Stars (DMG: 90, MP: 30) - 2-3 hits\n");
            sb.append("3. Celestial Execution (DMG: 220, MP: 50) - Ultimate hunt\n");
        }
        return sb.toString();
    }

    private void createPlayer() {
        if (!aries.isSelected() && !cancer.isSelected() && !sagittarius.isSelected()) {
            infoArea.setText("Please select a demigod.");
            return;
        }
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            if (aries.isSelected()) name = "Aresios";
            else if (cancer.isSelected()) name = "Selinia";
            else name = "Orionis";
        }
        String zodiac = aries.isSelected() ? "Aries" : cancer.isSelected() ? "Cancer" : "Sagittarius";
        gameState.createPlayer(name, zodiac);
        gameState.runIntroSequence();
        gui.showScreen("MainMenu");
    }
}