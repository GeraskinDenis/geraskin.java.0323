package geraskindenis.hw4.movie;

import geraskindenis.hw4.DbUtil;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;

public class MovieTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        MovieLoader movieLoader = new MovieLoaderImpl(dataSource);

        File dataFile = new File("src/geraskindenis/hw4/movie/film.csv");
        movieLoader.loadData(dataFile);

        /*
        * Тут написать в комментариях запрос получения всех

         SELECT *
         FROM movie
        /////////////////////////////////////////
         SELECT
	        id,
	        year ,
	        length,
	        title,
	        subject,
	        actors,
	        actress,
	        director,
	        popularity,
	        awards
        FROM
	        movie
        /////////////////////////////////////////
        SELECT
	        subject as SUBJECT,
	        SUM(1) as COUNT
        FROM
	        movie
	    GROUP BY
	        subject
        ORDER BY
            subject
        /////////////////////////////////////////
        SELECT
            subject as SUBJECT,
	        COUNT(subject) as COUNT
        FROM
	        movie
        GROUP BY
        	subject
         */
    }

    private static DataSource initDb() throws SQLException {
        String createMovieTable = "drop table if exists movie;"
                + "CREATE TABLE IF NOT EXISTS movie (\n"
                + "\tid bigserial NOT NULL,\n"
                + "\t\"year\" int4,\n"
                + "\tlength int4,\n"
                + "\ttitle varchar,\n"
                + "\tsubject varchar,\n"
                + "\tactors varchar,\n"
                + "\tactress varchar,\n"
                + "\tdirector varchar,\n"
                + "\tpopularity int4,\n"
                + "\tawards bool,\n"
                + "\tCONSTRAINT movie_pkey PRIMARY KEY (id)\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMovieTable, dataSource);
        return dataSource;
    }
}
