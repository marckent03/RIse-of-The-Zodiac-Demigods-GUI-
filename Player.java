import java.util.ArrayList;

public class Player extends Character {
    private int eclipsium;
    private ArrayList<Potion> inventory;
    private double damageMultiplier;
    private int bossesDefeated;
    
    public Player(String name, String zodiacType) {
        super(name, zodiacType, getMaxHPForType(zodiacType), getMaxMPForType(zodiacType));
        this.eclipsium = 0;
        this.inventory = new ArrayList<>();
        this.damageMultiplier = 1.0;
        this.bossesDefeated = 0;
    }
    
    private static int getMaxHPForType(String zodiacType) {
        if (zodiacType.equals("Aries")) return 450;
        else if (zodiacType.equals("Cancer")) return 500;
        else return 400;
    }
    
    private static int getMaxMPForType(String zodiacType) {
        if (zodiacType.equals("Aries")) return 150;
        else if (zodiacType.equals("Cancer")) return 200;
        else return 180;
    }
    
    public String getZodiacType() { return type; }
    public int getEclipsium() { return eclipsium; }
    public ArrayList<Potion> getInventory() { return inventory; }
    public int getBossesDefeated() { return bossesDefeated; }
    
    public void boostStats() {
        bossesDefeated++;
        
        double damageIncrease = 0.20 + (bossesDefeated * 0.05);
        int hpIncrease = 80 + (bossesDefeated * 20);
        int mpIncrease = 30 + (bossesDefeated * 10);
        
        damageMultiplier += damageIncrease;
        maxHP += hpIncrease;
        maxMP += mpIncrease;
        currentHP = maxHP;
        currentMP = maxMP;
        
        System.out.println("\n! ! ! YOUR POWER GROWS! ! ! !");
        System.out.println("Boss " + bossesDefeated + " defeated!");
        System.out.println();
        System.out.println("!! Damage increased by " + (int)(damageIncrease * 100) + "%!");
        System.out.println("!!  Max HP increased by " + hpIncrease + "! (New Max: " + maxHP + ")");
        System.out.println("!! Max MP increased by " + mpIncrease + "! (New Max: " + maxMP + ")");
        System.out.println("!! Total Damage Multiplier: " + String.format("%.2f", damageMultiplier) + "x");
        System.out.println();
        System.out.println("You feel the power of the defeated surging through you!");
    }
    
    public void addEclipsium(int amount) {
        eclipsium += amount;
    }
    
    public boolean spendEclipsium(int amount) {
        if (eclipsium >= amount) {
            eclipsium -= amount;
            return true;
        }
        return false;
    }
    
    public void addPotion(Potion potion) {
        for (Potion p : inventory) {
            if (p.getName().equals(potion.getName())) {
                p.addQuantity(potion.getQuantity());
                return;
            }
        }
        inventory.add(potion);
    }
    
    public int getPotionCount(String potionName) {
        for (Potion p : inventory) {
            if (p.getName().equals(potionName)) {
                return p.getQuantity();
            }
        }
        return 0;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }
    
    public void restoreFullHealth() {
        this.currentHP = this.maxHP;
    }

    public void restoreFullMana() {
        this.currentMP = this.maxMP;
    }
    
    public void heal(int amount) {
        currentHP = Math.min(currentHP + amount, maxHP);
    }
    
    public void restoreMana(int amount) {
        currentMP = Math.min(currentMP + amount, maxMP);
    }
    
    public void rest() {
        currentHP = Math.min(currentHP + (maxHP / 2), maxHP);
        currentMP = Math.min(currentMP + (maxMP / 2), maxMP);
    }
    
    public void resetCooldowns() {
        skill2Cooldown = 0;
        skill3Cooldown = 0;
    }
    
    @Override
    public String getSkillName(int skillNum) {
        if (type.equals("Aries")) {
            if (skillNum == 1) return "Raging Charge";
            if (skillNum == 2) return "Blazing Strike";
            return "Infernal Cataclysm";
        } else if (type.equals("Cancer")) {
            if (skillNum == 1) return "Tidal Wave";
            if (skillNum == 2) return "Moonlit Barrier";
            return "Eclipse Embrace";
        } else {
            if (skillNum == 1) return "Piercing Arrow";
            if (skillNum == 2) return "Rain of Stars";
            return "Celestial Execution";
        }
    }
    
    public String getSkillDescription(int skillNum) {
        if (type.equals("Aries")) {
            if (skillNum == 1) return "Surges forward with raw, violent force";
            if (skillNum == 2) return "Flame-wreathed blade carves through armor";
            return "Vortex of hellfire engulfs enemies";
        } else if (type.equals("Cancer")) {
            if (skillNum == 1) return "Crashes down with tidal force";
            if (skillNum == 2) return "Heals and shields with lunar power";
            return "Devastating eclipse energy strike";
        } else {
            if (skillNum == 1) return "Precise starlight arrow";
            if (skillNum == 2) return "Rain of celestial projectiles (2-3 hits)";
            return "Ultimate hunting technique, never misses";
        }
    }
    
    public String getSkillVoiceLine(int skillNum) {
        if (type.equals("Aries")) {
            if (skillNum == 1) return "Out of my way, weakling!";
            if (skillNum == 2) return "The earth itself bleeds for me!";
            return "My rage shall scorch the heavens!";
        } else if (type.equals("Cancer")) {
            if (skillNum == 1) return "Let the waves cleanse your sins!";
            if (skillNum == 2) return "Rest under my light.";
            return "May your soul drift beneath my tide.";
        } else {
            if (skillNum == 1) return "The stars guide my aim.";
            if (skillNum == 2) return "Fall like stars, burn like judgment.";
            return "No mercy from the stars.";
        }
    }
    
    @Override
    public int getSkillDamage(int skillNum) {
        int baseDamage = 0;
        if (type.equals("Aries")) {
            if (skillNum == 1) baseDamage = 80;
            else if (skillNum == 2) baseDamage = 120;
            else baseDamage = 200;
        } else if (type.equals("Cancer")) {
            if (skillNum == 1) baseDamage = 70;
            else if (skillNum == 2) baseDamage = 0;
            else baseDamage = 180;
        } else {
            if (skillNum == 1) baseDamage = 60;
            else if (skillNum == 2) baseDamage = 90;
            else baseDamage = 220;
        }
        return (int)(baseDamage * damageMultiplier);
    }
    
    public int getSkillMpCost(int skillNum) {
        if (skillNum == 1) return 0;
        if (skillNum == 2) return 30;
        return 50;
    }
    
    public int getSkillCooldown(int skillNum) {
        if (skillNum == 1) return 0;
        if (skillNum == 2) return skill2Cooldown;
        return skill3Cooldown;
    }
    
    public void displaySkills() {
        System.out.println("\n=== YOUR SKILLS ===");
        for (int i = 1; i <= 3; i++) {
            String cooldownText = "";
            if (i == 2 && skill2Cooldown > 0) {
                cooldownText = " [COOLDOWN: " + skill2Cooldown + " turns]";
            } else if (i == 3 && skill3Cooldown > 0) {
                cooldownText = " [COOLDOWN: " + skill3Cooldown + " turns]";
            }
            
            System.out.println(i + ". " + getSkillName(i) + 
                             " (DMG: " + getSkillDamage(i) + 
                             ", MP: " + getSkillMpCost(i) + ")" + cooldownText);
        }
    }
    
    public int useSkill(int skillNum) {
        int mpCost = getSkillMpCost(skillNum);
        
        if (skillNum == 2 && skill2Cooldown > 0) {
            return -1;
        }
        if (skillNum == 3 && skill3Cooldown > 0) {
            return -1;
        }
        if (currentMP < mpCost) {
            return -2;
        }
        
        currentMP -= mpCost;
        
        if (skillNum == 2) {
            skill2Cooldown = 2;
        } else if (skillNum == 3) {
            skill3Cooldown = 3;
        }
        
        System.out.println(name + ": \"" + getSkillVoiceLine(skillNum) + "\"");
        System.out.println("You use " + getSkillName(skillNum) + "!");
        
        if (type.equals("Cancer") && skillNum == 2) {
            int healAmount = 150;
            heal(healAmount);
            System.out.println("You heal for " + healAmount + " HP!");
            return 0;
        }
        
        int baseDamage = getSkillDamage(skillNum);
        
        // Rain of Stars (skill 2) is the multi-hit ability
        if (type.equals("Sagittarius") && skillNum == 2) {
            int hits = 2 + (int)(Math.random() * 2);
            int totalDamage = 0;
            System.out.println("Multiple stars rain down!");
            for (int i = 0; i < hits; i++) {
                int hitDamage = calculateDamageWithVariation(baseDamage / hits);
                totalDamage += hitDamage;
                System.out.println("Hit " + (i + 1) + ": " + hitDamage + " damage!");
            }
            return totalDamage;
        }
        
        return calculateDamageWithVariation(baseDamage);
    }
    
    private int calculateDamageWithVariation(int baseDamage) {
        double variation = 0.85 + (Math.random() * 0.30);
        int finalDamage = (int)(baseDamage * variation);
        
        if (variation >= 1.10) {
            System.out.println("!!! CRITICAL HIT! !!!");
        }
        
        return finalDamage;
    }
    
    @Override
    public int attack() {
        return useSkill(1);
    }
    
    @Override
    public String getAttackMessage() {
        return name + " attacks with " + getSkillName(1) + "!";
    }
    
    public void showStats() {
        System.out.println("\n=== " + name.toUpperCase() + " STATS ===");
        System.out.println("Zodiac Type: " + type);
        System.out.println("Level: Boss Victories - " + bossesDefeated);
        System.out.println("HP: " + currentHP + "/" + maxHP);
        System.out.println("MP: " + currentMP + "/" + maxMP);
        System.out.println("Damage Multiplier: " + String.format("%.2f", damageMultiplier) + "x");
        System.out.println("Eclipsium: " + eclipsium);
        System.out.println("\nSkills:");
        for (int i = 1; i <= 3; i++) {
            System.out.println(i + ". " + getSkillName(i) + " (DMG: " + getSkillDamage(i) + 
                             ", MP: " + getSkillMpCost(i) + ")");
            System.out.println("   - " + getSkillDescription(i));
        }
        System.out.println("\nInventory:");
        if (inventory.isEmpty()) {
            System.out.println("  Empty");
        } else {
            for (int i = 0; i < inventory.size(); i++) {
                System.out.println("  " + (i+1) + ". " + inventory.get(i).getName() + 
                                 " x" + inventory.get(i).getQuantity());
            }
        }
        System.out.println();
    }
    
    public static void displayCharacterInfo(int choice) {
        System.out.println("\n" + "=".repeat(60));
        if (choice == 1) {
            System.out.println("\n=== ARESIOS - ARIES DEMIGOD ===");
            System.out.println("Weapon: Broadsword");
            System.out.println("HP: 450     ||      MP: 150");
            System.out.println("High Attack, Aggressive Fighter");
            System.out.println("Forged in endless wars, thrives in chaos");
            System.out.println("\nSkills:");
            System.out.println("1. Raging Charge (DMG: 80, MP: 0)");
            System.out.println("   - Surges forward with raw, violent force");
            System.out.println("2. Blazing Strike (DMG: 120, MP: 30)");
            System.out.println("   - Flame-wreathed blade carves through armor");
            System.out.println("3. Infernal Cataclysm (DMG: 200, MP: 50)");
            System.out.println("   - Vortex of hellfire engulfs enemies");
        } else if (choice == 2) {
            System.out.println("\n=== SELINIA - CANCER DEMIGOD ===");
            System.out.println("Weapon: Staff");
            System.out.println("HP: 500     ||      MP: 200");
            System.out.println("Balanced Stats, Supportive Abilities");
            System.out.println("Once a healer, now hardened by betrayal");
            System.out.println("\nSkills:");
            System.out.println("1. Tidal Wave (DMG: 70, MP: 0)");
            System.out.println("   - Crashes down with tidal force");
            System.out.println("2. Moonlit Barrier (DMG: 0, MP: 30)");
            System.out.println("   - Heals and shields with lunar power");
            System.out.println("3. Eclipse Embrace (DMG: 180, MP: 50)");
            System.out.println("   - Devastating eclipse energy strike");
        } else {
            System.out.println("\n=== ORIONIS - SAGITTARIUS DEMIGOD ===");
            System.out.println("Weapon: Bow");
            System.out.println("HP: 400     ||      MP: 180");
            System.out.println("High Damage, Precision Strikes");
            System.out.println("Master hunter with starlight arrows");
            System.out.println("\nSkills:");
            System.out.println("1. Piercing Arrow (DMG: 60, MP: 0)");
            System.out.println("   - Precise starlight arrow");
            System.out.println("2. Rain of Stars (DMG: 90, MP: 30)");
            System.out.println("   - Rain of celestial projectiles (2-3 hits)");
            System.out.println("3. Celestial Execution (DMG: 220, MP: 50)");
            System.out.println("   - Ultimate hunting technique, never misses");
        }
    }
}