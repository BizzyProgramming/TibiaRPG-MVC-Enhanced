package model;

public class Sorcerer extends TibiaCharacter {
	
	   public Sorcerer(String name, int level, String description, boolean isPromoted, double health) {
	        super(name, level, description, isPromoted, health, "Sorcerer");
	    }

	    public Sorcerer(String name, int level, String description, boolean isPromoted, double health, String vocation) {
	        super(name, level, description, isPromoted, health, vocation);
	    }

	    Sorcerer() {
	        // Default constructor, if needed for DB or testing
	    }

	    public void suddenDeathAttack(TibiaCharacter target) {
	        double damage = 40 + Math.random() * 20; // 40–60
	        target.setHealth(target.getHealth() - damage);
	        System.out.printf("%s casts Sudden Death on %s for %.2f damage!\n", getName(), target.getName(), damage);
	    }

	    public void healTick() {
	        double healAmount = 8 + Math.random() * 7; // 8–15
	        setHealth(getHealth() + healAmount);
	        System.out.printf("%s uses magic to heal %.2f HP!\n", getName(), healAmount);
	    }

	    @Override
	    public String getFinalVocation() {
	        return getIsPromoted() ? "Master Sorcerer" : "Sorcerer";
	    }
	}
