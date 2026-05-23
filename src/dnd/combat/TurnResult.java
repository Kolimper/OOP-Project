package dnd.combat;

public class TurnResult {
    private final CombatResult result;
    private final String log;
    private final int playerHealth;
    private final int monsterHealth;

    public TurnResult(CombatResult result, String log, int heroHealth, int monsterHealth) {
        this.result = result;
        this.log = log;
        this.playerHealth = heroHealth;
        this.monsterHealth = monsterHealth;
    }

    public CombatResult getResult() { return result; }

    public String getLog() { return log; }

    public int getHeroHealth() { return playerHealth; }

    public int getMonsterHealth() { return monsterHealth; }
}
