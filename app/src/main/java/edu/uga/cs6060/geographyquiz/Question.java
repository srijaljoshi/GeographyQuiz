package edu.uga.cs6060.geographyquiz;

/**
 * @author tripp
 * POJO class to represent a Question within our Database.
 * Also holds incorrect answers and answer given by user
 */
public class Question {
    String id;
    String country;

    String continent_answer;
    String wrong_continent_1;
    String wrong_continent_2;
    String given_continent;

    String neighbor_answer;
    String wrong_neighbor_1;
    String wrong_neighbor_2;
    String given_neighbor;

    public Question(String id, String country, String continent_answer, String wrong_continent_1,
                    String wrong_continent_2, String given_continent, String neighbor_answer,
                    String wrong_neighbor_1, String wrong_neighbor_2, String given_neighbor) {
        this.id = id;
        this.country = country;
        this.continent_answer = continent_answer;
        this.wrong_continent_1 = wrong_continent_1;
        this.wrong_continent_2 = wrong_continent_2;
        this.given_continent = given_continent;
        this.neighbor_answer = neighbor_answer;
        this.wrong_neighbor_1 = wrong_neighbor_1;
        this.wrong_neighbor_2 = wrong_neighbor_2;
        this.given_neighbor = given_neighbor;
    }

    /**
     * Constructor to set country and answer information from SQLite Database
     * @param country           name of country
     * @param continent_answer  continent country belongs to
     * @param neighbor_answer   neighbor of country
     */
    public Question(String country, String continent_answer, String neighbor_answer) {
        this.country = country;
        this.continent_answer = continent_answer;
        this.neighbor_answer = neighbor_answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContinent_answer() {
        return continent_answer;
    }

    public void setContinent_answer(String continent_answer) {
        this.continent_answer = continent_answer;
    }

    public String getWrong_continent_1() {
        return wrong_continent_1;
    }

    public void setWrong_continent_1(String wrong_continent_1) {
        this.wrong_continent_1 = wrong_continent_1;
    }

    public String getWrong_continent_2() {
        return wrong_continent_2;
    }

    public void setWrong_continent_2(String wrong_continent_2) {
        this.wrong_continent_2 = wrong_continent_2;
    }

    public String getGiven_continent() {
        return given_continent;
    }

    public void setGiven_continent(String given_continent) {
        this.given_continent = given_continent;
    }

    public String getNeighbor_answer() {
        return neighbor_answer;
    }

    public void setNeighbor_answer(String neighbor_answer) {
        this.neighbor_answer = neighbor_answer;
    }

    public String getWrong_neighbor_1() {
        return wrong_neighbor_1;
    }

    public void setWrong_neighbor_1(String wrong_neighbor_1) {
        this.wrong_neighbor_1 = wrong_neighbor_1;
    }

    public String getWrong_neighbor_2() {
        return wrong_neighbor_2;
    }

    public void setWrong_neighbor_2(String wrong_neighbor_2) {
        this.wrong_neighbor_2 = wrong_neighbor_2;
    }

    public String getGiven_neighbor() {
        return given_neighbor;
    }

    public void setGiven_neighbor(String given_neighbor) {
        this.given_neighbor = given_neighbor;
    }
}
