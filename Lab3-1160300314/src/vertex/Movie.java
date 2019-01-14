package vertex;

import exception.IllegalParamsNumberException;
import exception.IllegalVertexParamsException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Describe a movie with its name, releasing year, nation and IMDB score
 *
 * @author Zhu Mingyan
 */
public class Movie extends Vertex {
    /*
    AF: Represents a node of movie in movie Graph
    RI: The releasing year of any movie is between 1900 and 2018.
        The nation of any movie is an non-empty and non-null string.
        The score of any movie is two decimal places between 0.0 and 10.0
    Safety for Rep Exposure:
        All other kinds of fields except label(name) in movie are modified by key word private. Clients can access it
        by the getter function and there no other function can changer the fields except fillVertexInfo which is
        strictly limited.
     */
    private int releaseYear;
    private String nation;
    private double score;

    public Movie(String label) throws IllegalVertexParamsException {
        super(label);
        super.checkRep();
    }

    @Override
    protected void checkRep() throws IllegalVertexParamsException {
        super.checkRep();
        if (releaseYear < 1900 || releaseYear > 2018) {
            throw new IllegalVertexParamsException("Release year is before 1900 or after 2018!");
        }
//        assert releaseYear >= 1900 && releaseYear <= 2018;
//        assert nation != null && nation.length() != 0;
        if (nation == null) {
            throw new IllegalVertexParamsException("Nation of movie is null pointer!");
        }
        Pattern pattern = Pattern.compile("^([0-9]\\.[1-9]?\\d|10|\\d|10.0|10.00)*$");
        Matcher matcher = pattern.matcher(String.valueOf(score));
//        assert matcher.matches();
        if (nation.length() == 0 || !matcher.matches()) {
            throw new IllegalVertexParamsException("Nation name is illegal!");
        }
    }

    /**
     * Get the releasing year of this movie
     *
     * @return an integer between 1900 and 2018.
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * get the releasing nation of this movie
     *
     * @return name of releasing nation
     */
    public String getNation() {
        return nation;
    }

    /**
     * Get the IMDB score of this movie
     *
     * @return decimals between 0.0 and 10.0
     */
    public double getScore() {
        return score;
    }

    /**
     * Fill other fields in movie with a string array of three strings, which are the releasing year, nation and
     * IMDB score in order.
     *
     * @param args String array
     */
    @Override
    public void fillVertexInfo(String[] args) throws IllegalParamsNumberException, IllegalVertexParamsException {
        if (args == null) {
            throw new IllegalVertexParamsException("Fill Movie vertex with a null args!");
        }
        if (args.length != 3) {
            throw new IllegalParamsNumberException("Fill Movie vertex with " + args.length + " param(s)");
        }
//        this.releaseYear = Integer.valueOf(args[0]);
        this.releaseYear = Integer.parseInt(args[0]);
        this.nation = args[1];
        this.score = Double.valueOf(args[2]);
        checkRep();
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + getLabel() + '\'' +
                ", releaseYear=" + releaseYear +
                ", nation='" + nation + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        if (!super.equals(o)) return false;
        Movie movie = (Movie) o;
        return releaseYear == movie.releaseYear &&
                Double.compare(movie.score, score) == 0 &&
                this.nation.equalsIgnoreCase(movie.nation);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), releaseYear, nation, score, "Movie");
    }
}
