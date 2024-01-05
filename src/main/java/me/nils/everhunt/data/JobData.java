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
                Everhunt.getDatabase().run("INSERT INTO tbljob (uuid, job, xp) VALUES ('" + uuid + "','" + job + "','" +
                        xp + "')").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getXp(String uuid, Job job) {
        try {
            ResultSet resultSet = Everhunt.getDatabase().run("SELECT xp FROM tbljob WHERE uuid = '" + uuid + "' AND job = '" +
                    job + "'").executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int getLevel(String uuid, Job job) {
        return getXp(uuid,job)/100;
    }

    public static void setXp(String uuid, Job job, int xp) {
        try {
            Everhunt.getDatabase().run("UPDATE tbljob SET xp = " + xp + " WHERE uuid = '" + uuid + "' AND job = '" +
                    job + "'").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addXp(String uuid, Job job, int xp) {
        setXp(uuid,job,getXp(uuid,job)+xp);
    }

    public static boolean hasJob(String uuid, Job job) {
        try {
            ResultSet check = Everhunt.getDatabase().run("SELECT count(*) FROM tbljob WHERE UUID = '" + uuid + "' AND job = '" +
                    job + "'").executeQuery();
            check.next();
            if (check.getInt(1) >= 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
