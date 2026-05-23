package dnd.combat;

import dnd.entities.Hero;
import dnd.entities.Monster;

import java.util.Random;

public class CombatEngine {

    private static final Random random = new Random();

    private final Hero hero;
    private final Monster monster;
    private boolean active;

    public CombatEngine(Hero hero, Monster monster) {
        this.hero = hero;
        this.monster = monster;
        this.active = true;
    }

    public boolean isActive() { return active; }

    public TurnResult executeTurn(boolean heroUsesSpell) {
        if (!active) {
            throw new IllegalStateException("Combat is already over.");
        }

        int heroDmg = heroUsesSpell ? hero.getSpellAttack() : hero.getPhysicalAttack();
        boolean monsterSpell = random.nextBoolean();
        int monsterDmg = hero.applyArmor(monster.attack(monsterSpell));

        StringBuilder log = new StringBuilder();
        boolean heroGoesFirst = random.nextBoolean();

        if (heroGoesFirst) {
            log.append(doHeroAttack(heroDmg, heroUsesSpell));
            if (monster.isAlive()) {
                log.append(doMonsterAttack(monsterDmg, monsterSpell));
            }
        } else {
            log.append(doMonsterAttack(monsterDmg, monsterSpell));
            if (hero.isAlive()) {
                log.append(doHeroAttack(heroDmg, heroUsesSpell));
            }
        }

        CombatResult result = checkResult();
        if (result != CombatResult.ONGOING) {
            active = false;
            if (result == CombatResult.HERO_WINS) {
                hero.restoreHealthAfterVictory();
                log.append("Victory! HP restored to ").append(hero.getHealth()).append(".\n");
            } else {
                log.append("The hero has fallen. Game over.\n");
            }
        }

        return new TurnResult(result, log.toString(), hero.getHealth(), monster.getHealth());
    }

    private String doHeroAttack(int damage, boolean useSpell) {
        int afterArmor = monster.applyArmor(damage);
        monster.takeDamage(afterArmor);
        String type = useSpell ? "spell" : "power";
        return String.format("Hero uses %s attack for %d damage (after armor: %d). Monster HP: %d%n",
                type, damage, afterArmor, monster.getHealth());
    }

    private String doMonsterAttack(int damage, boolean useSpell) {
        hero.takeDamage(damage);
        String type = useSpell ? "spell" : "power";
        return String.format("Monster uses %s attack for %d damage (after armor). Hero HP: %d%n",
                type, damage, hero.getHealth());
    }

    private CombatResult checkResult() {
        if (!monster.isAlive()) return CombatResult.HERO_WINS;
        if (!hero.isAlive())    return CombatResult.MONSTER_WINS;
        return CombatResult.ONGOING;
    }

    public String getStatus() {
        return String.format("Hero HP: %d/%d  |  Monster HP: %d",
                hero.getHealth(), hero.getMaxHealth(), monster.getHealth());
    }
}
