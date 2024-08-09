package lol.vedant.skypvp.api.stats;

public class PlayerStats {

    private int kills;
    private int deaths;
    private int voidKills;
    private int bowKills;
    private long exp;

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getVoidKills() {
        return voidKills;
    }

    public void setVoidKills(int voidKills) {
        this.voidKills = voidKills;
    }

    public int getBowKills() {
        return bowKills;
    }

    public void setBowKills(int bowKills) {
        this.bowKills = bowKills;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

}
