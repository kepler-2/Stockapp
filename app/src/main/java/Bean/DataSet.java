package Bean;

/**
 * Created by firemoon on 13/2/18.
 */

public class DataSet {


    public String getLeagueName() {
        return LeagueName;
    }

    public void setLeagueName(String leagueName) {
        LeagueName = leagueName;
    }

    public String getPortfolioid() {
        return Portfolioid;
    }

    public void setPortfolioid(String portfolioid) {
        Portfolioid = portfolioid;
    }

    public String getLeagueID() {
        return LeagueID;
    }

    public void setLeagueID(String leagueID) {
        LeagueID = leagueID;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getPlayedDate() {
        return PlayedDate;
    }

    public void setPlayedDate(String playedDate) {
        PlayedDate = playedDate;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public String getShareType() {
        return ShareType;
    }

    public void setShareType(String shareType) {
        ShareType = shareType;
    }

    public String getKingName() {
        return KingName;
    }

    public void setKingName(String kingName) {
        KingName = kingName;
    }

    public String getQueenName() {
        return QueenName;
    }

    public void setQueenName(String queenName) {
        QueenName = queenName;
    }

    public String getEntryFees() {
        return EntryFees;
    }

    public void setEntryFees(String entryFees) {
        EntryFees = entryFees;
    }

    public String getPlayerType() {
        return PlayerType;
    }

    public void setPlayerType(String playerType) {
        PlayerType = playerType;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    private String EntryFees;
    private String PlayerType;
    private String PlayerName;
    private String LeagueName;
    private String Portfolioid;
    private String LeagueID;

    public String getMTID() {
        return MTID;
    }

    public void setMTID(String MTID) {
        this.MTID = MTID;
    }

    private String MTID;
    private String Amount;
    private String PlayedDate;
    private String Rank;
    private String ShareType;
    private String KingName;
    private String QueenName;

}
