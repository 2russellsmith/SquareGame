package squaregame.model;

import lombok.Getter;

@Getter
public class KillEvent {

    private Location location;
    private Player attacker;

    public KillEvent(Player attacker, Location location) {
        this.attacker = attacker;
        this.location = location;
    }
}
