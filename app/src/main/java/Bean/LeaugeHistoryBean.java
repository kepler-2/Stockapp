package Bean;

/**
 * Created by firemoon on 16/4/18.
 */

public class LeaugeHistoryBean {

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

    public String getWiningAmount() {
        return WiningAmount;
    }

    public void setWiningAmount(String winingAmount) {
        WiningAmount = winingAmount;
    }

    public String getNoOfWinners() {
        return NoOfWinners;
    }

    public void setNoOfWinners(String noOfWinners) {
        NoOfWinners = noOfWinners;
    }

    public String getNoofTeams() {
        return NoofTeams;
    }

    public void setNoofTeams(String noofTeams) {
        NoofTeams = noofTeams;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String LeagueName;
    public String EntryFees;
    public String WiningAmount;
    public String NoOfWinners;
    public String NoofTeams;
    public String Comments;

    public String getLeagueHistorydate() {
        return leagueHistorydate;
    }

    public void setLeagueHistorydate(String leagueHistorydate) {
        this.leagueHistorydate = leagueHistorydate;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public String leagueHistorydate;
    public String Rank;
}
