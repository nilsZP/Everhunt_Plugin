package me.nils.everhunt.data;

import me.nils.everhunt.Everhunt;
import me.nils.everhunt.constants.Job;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JobData {
    private String uuid;
    private Job job;
    private int xp;

    public JobData(String uuid, Job job, int xp) {
        this.uuid = uuid;
        this.job = job;
        this.xp = xp;

        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tbljob WHERE UUID = '" + uuid + "' AND job = '" +
                    job + "'").executeQuery();
            check.next();
            if (check.getInt(1) < 1) {
                Everhunt.getDatabase().run("INSERT INTO tbljob (uuid, job, xp, coins) VALUES ('" + uuid + "','" + job + "','" +
                        xp + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getXp(String uuid, Job job) {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT xp FROM tbljob WHERE uuid = '" + uuid + "' AND job = '" +
                    job + "'").executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return xp;
    }

    public void setXp(String uuid, Job job, int xp) {
        try {
            Everhunt.getDatabase().run("UPDATE tbljob SET xp = " + xp + " WHERE uuid = '" + uuid + "' AND job = '" +
                    job + "'").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addXp(String uuid, Job job, int xp) {
        setXp(uuid,job,getXp(uuid,job)+xp);
    }
}
