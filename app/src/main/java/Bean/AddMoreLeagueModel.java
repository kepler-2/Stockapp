package Bean;

/**
 * Created by firemoon on 15/2/18.
 */

public class AddMoreLeagueModel {

    public String getLeagueName() {
        return LeagueName;
    }

    public void setLeagueName(String leagueName) {
        LeagueName = leagueName;
    }

    public String getEntryFees() {
        return EntryFees;
    }

    public void setEntryFees(String entryFees) {
        EntryFees = entryFees;
    }

    public String getWinningAmount() {
        return WinningAmount;
    }

    public void setWinningAmount(String winningAmount) {
        WinningAmount = winningAmount;
    }

    public String getNumberOfWinnern() {
        return NumberOfWinnern;
    }

    public void setNumberOfWinnern(String numberOfWinnern) {
        NumberOfWinnern = numberOfWinnern;
    }

    public String getNumberOfPlayers() {
        return NumberOfPlayers;
    }

    public void setNumberOfPlayers(String numberOfPlayers) {
        NumberOfPlayers = numberOfPlayers;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getLeagueID() {
        return LeagueID;
    }

    public void setLeagueID(String leagueID) {
        LeagueID = leagueID;
    }

    private String LeagueID;
    private String LeagueName;
    private String EntryFees;
    private String WinningAmount;
    private String NumberOfWinnern;
    private String NumberOfPlayers;
    private String Rank;
    private String AMLDate;

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public String getAMLDate() {
        return AMLDate;
    }

    public void setAMLDate(String AMLDate) {
        this.AMLDate = AMLDate;
    }

    public String getCurrentTeams() {
        return CurrentTeams;
    }

    public void setCurrentTeams(String currentTeams) {
        CurrentTeams = currentTeams;
    }

    private String CurrentTeams;
    private String Comments;

}
