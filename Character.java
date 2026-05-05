public abstract class Character {
    protected String name;
    protected String type;
    protected int currentHP;
    protected int maxHP;
    protected int currentMP;
    protected int maxMP;
    protected int skill2Cooldown;
    protected int skill3Cooldown;
    
    public Character(String name, String type, int maxHP, int maxMP) {
        this.name = name;
        this.type = type;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.maxMP = maxMP;
        this.currentMP = maxMP;
        this.skill2Cooldown = 0;
        this.skill3Cooldown = 0;
    }
    
    public String getName() { return name; }
    public String getType() { return type; }
    public int getCurrentHP() { return currentHP; }
    public int getMaxHP() { return maxHP; }
    public int getCurrentMP() { return currentMP; }
    public int getMaxMP() { return maxMP; }
    
    public boolean isAlive() {
        return currentHP > 0;
    }
    
    public void takeDamage(int damage) {
        currentHP = Math.max(0, currentHP - damage);
    }
    
    public void reduceCooldowns() {
        if (skill2Cooldown > 0) skill2Cooldown--;
        if (skill3Cooldown > 0) skill3Cooldown--;
    }
    
    public abstract String getSkillName(int skillNum);
    public abstract int getSkillDamage(int skillNum);
    public abstract int attack();
    public abstract String getAttackMessage();
}