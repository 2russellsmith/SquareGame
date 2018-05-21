package squaregame.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Leaderboard {

    private final Map<String, Integer> scoreboard;
    private Connection c;
    private List<AIOption> aiOptions;

    public Leaderboard(List<AIOption> aiOptions) {
        this.aiOptions = aiOptions;
        this.scoreboard = new HashMap<>();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:leaderboard.db");
            System.out.println("Opened database successfully");

            final Statement stmt = c.createStatement();
            final ResultSet rs = stmt.executeQuery("SELECT * FROM LEADERBOARD;");
            while (rs.next()) {
                final String id = rs.getString("id");
                final int mmr  = rs.getInt("mmr");
                this.scoreboard.put(id, mmr);
            }
            aiOptions.stream().filter(Objects::nonNull).forEach(aiOption -> {
                if (this.scoreboard.get(aiOption.getId()) == null) {
                    this.scoreboard.put(aiOption.getId(), 1000);
                    final String insertsql = "INSERT INTO LEADERBOARD (ID,MMR) " +
                            "VALUES (\"" + aiOption.getId() + "\", 1000);";
                    try {
                        stmt.executeUpdate(insertsql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Insert to leaderboard");
                }
            });
            stmt.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void addScore(String player, int mmr) {
        aiOptions.stream().filter(Objects::nonNull).forEach(aiOption -> {
            if (aiOption.getId().equals(player)) {
                this.scoreboard.put(aiOption.getId(), this.scoreboard.get(player) + mmr);
                final String insertsql = "UPDATE LEADERBOARD set MMR = " + this.scoreboard.get(player) + " where ID= \"" + player + "\";";
                try {
                    final Statement stmt = c.createStatement();
                    stmt.executeUpdate(insertsql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("Insert to leaderboard");
            }
        });
    }

    public int getScore(String aiId) {
        return this.scoreboard.get(aiId);
    }
}
