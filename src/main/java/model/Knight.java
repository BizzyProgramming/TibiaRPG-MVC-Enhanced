package model;

public class Knight extends TibiaCharacter {
	

    private int regularAttack;
    private double knightHeal;
    private double addArmor;

    public Knight(String name, int level, String description, boolean isPromoted, double health) {
        super(name, level, description, isPromoted, health, "Knight");
    }

    public Knight(String name, int level, String description, boolean isPromoted, double health, String vocation) {
        super(name, level, description, isPromoted, health, vocation);
    }

    public void exoriAttack(TibiaCharacter target) {
        double damage = 35 + Math.random() * 10; // 35-45
        target.setHealth(target.getHealth() - damage);
        System.out.printf("%s performs Exori Attack on %s for %.2f damage!\n", getName(), target.getName(), damage);
    }

    public void weaponAttack(TibiaCharacter target) {
        double damage = 20 + Math.random() * 5; // 20-25
        target.setHealth(target.getHealth() - damage);
        System.out.printf("%s strikes %s with a weapon for %.2f damage!\n", getName(), target.getName(), damage);
    }

    public void healTick() {
        double healAmount = 10 + Math.random() * 5; // 10-15
        setHealth(getHealth() + healAmount);
        System.out.printf("%s regenerates %.2f HP!\n", getName(), healAmount);
    }

    public void attack() {
        // Optional placeholder or can be removed if unused
    }

    @Override
    public String getFinalVocation() {
        return getIsPromoted() ? "Elite Knight" : "Knight";
    }
}
