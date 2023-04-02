package monopoly.models;

import monopoly.models.lands.Land;

public class PlayResult {
    private boolean isPlayValid;
    private Dice dice;
    private Land landedLand;

    private PlayResultToken playResultToken;

    public PlayResult(Dice dice, Land land){
        this.isPlayValid = true;
        this.dice = dice;
        this.landedLand = land;
    }

    public PlayResult(boolean isPlayValid){
        this.isPlayValid = isPlayValid;
    }

    public boolean isPlayValid() {
        return isPlayValid;
    }

    public Dice getDice() {
        return dice;
    }

    public Land getLandedLand() {
        return landedLand;
    }

    public PlayResultToken getPlayResultToken() {
        return playResultToken;
    }

    public void setPlayResultToken(PlayResultToken playResultToken) {
        this.playResultToken = playResultToken;
    }
}

