package Bean;

/**
 * Created by firemoon on 4/5/18.
 */

public class MyTeamsDetailsBean {

    public String getMyteamsID() {
        return myteamsID;
    }

    public void setMyteamsID(String myteamsID) {
        this.myteamsID = myteamsID;
    }

    public String getSMID() {
        return SMID;
    }

    public void setSMID(String SMID) {
        this.SMID = SMID;
    }

    public String getTokenID() {
        return TokenID;
    }

    public void setTokenID(String tokenID) {
        TokenID = tokenID;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    public String getPlayerType() {
        return PlayerType;
    }

    public void setPlayerType(String playerType) {
        PlayerType = playerType;
    }

    public String getStockrate() {
        return Stockrate;
    }

    public void setStockrate(String stockrate) {
        Stockrate = stockrate;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    private String playerID;
    private String myteamsID;
    private String SMID;
    private String TokenID;
    private String PlayerName;
    private String PlayerType;
    private String Stockrate;
}
