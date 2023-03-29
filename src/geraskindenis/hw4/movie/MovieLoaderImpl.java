package geraskindenis.hw4.movie;

import geraskindenis.hw4.movie.exceptions.InvalidFormatCSVException;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class MovieLoaderImpl implements MovieLoader {

    private final static String FORMAT_CSV = "Year;Length;Title;Subject;Actor;Actress;Director;Popularity;Awards;*Image";
    private final static int NUMBER_OF_COLUMNS = FORMAT_CSV.split(";").length;
    private final static int DATA_FROM_LINE = 3;
    private DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) {
        try (Connection connection = dataSource.getConnection();
             BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int currentLine = 1;
            while (reader.ready()) {
                String dataLine = reader.readLine();
                if (currentLine == 1) {
                    if (!dataLine.equals(FORMAT_CSV)) {
                        throw new InvalidFormatCSVException(dataLine + " - Invalid format CSV");
                    }
                } else if (currentLine >= DATA_FROM_LINE) {
                    Movie movie = createMovie(dataLine);
                    saveMovie(movie, connection);
                }
                currentLine++;
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Movie createMovie(String dataLine) {
        String[] data = dataLine.split(";");
        if (data.length != NUMBER_OF_COLUMNS) {
            throw new InvalidFormatCSVException(dataLine + " - invalid format CSV");
        }
        Movie movie = new Movie();
        movie.setYear(getIntegerOrNull(data[0]));
        movie.setLength(getIntegerOrNull(data[1]));
        movie.setTitle(data[2]);
        movie.setSubject(data[3]);
        movie.setActors(data[4]);
        movie.setActress(data[5]);
        movie.setDirector(data[6]);
        movie.setPopularity(getIntegerOrNull(data[7]));
        movie.setAwards((data[8].equals("Yes")));
        return movie;
    }

    private void saveMovie(Movie movie, Connection connection) throws SQLException {
        String sql = "INSERT INTO movie (year, length, title," +
                " subject, actors, actress," +
                " director, popularity, awards)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            setIntOrNull(statement, 1, movie.getYear());
            setIntOrNull(statement, 2, movie.getLength());
            statement.setString(3, movie.getTitle());
            statement.setString(4, movie.getSubject());
            statement.setString(5, movie.getActors());
            statement.setString(6, movie.getActress());
            statement.setString(7, movie.getDirector());
            setIntOrNull(statement, 8, movie.getPopularity());
            statement.setBoolean(9, movie.getAwards());
            statement.executeUpdate();
        }
    }

    private void setIntOrNull(PreparedStatement statement, int parameterIndex, Integer value) throws SQLException {
        if (Objects.isNull(value)) {
            statement.setNull(parameterIndex, Types.INTEGER);
        } else {

            statement.setInt(parameterIndex, value);
        }
    }

    private Integer getIntegerOrNull(String s) {
        if (!s.isEmpty()) {
            return Integer.parseInt(s);
        }
        return null;
    }
}
