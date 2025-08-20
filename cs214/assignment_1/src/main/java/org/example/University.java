package org.example;

/*
 * @author
 * Rudr Prasad - S11219309
 * Ahad Ali - S11221529
 * Rishal Prasad - S11221067
 *
 */


import java.util.Comparator;
import java.util.Objects;

public class University implements Comparable<University> {
    private Integer rank;
    private String name;
    private String location;
    private Integer numberOfStudents;
    private Double ratioOfStudentToStaff;
    private String internationalStudent;
    private String maleToFemaleRatio;
    private String overAllScore;
    private String teachingScore;
    private Double researchScore;
    private Double citationScore;
    private Double industryIncomeScore;
    private Double internationalOutlookScore;

    public University(){}
    public University(Integer rank, String name, String location, Integer numberOfStudents, Double ratioOfStudentToStaff, String internationalStudent, String maleToFemaleRatio, String overAllScore, String teachingScore, Double researchScore, Double citationScore, Double industryIncomeScore, Double internationalOutlookScore) {
        this.rank = rank;
        this.name = name;
        this.location = location;
        this.numberOfStudents = numberOfStudents;
        this.ratioOfStudentToStaff = ratioOfStudentToStaff;
        this.internationalStudent = internationalStudent;
        this.maleToFemaleRatio = maleToFemaleRatio;
        this.overAllScore = overAllScore;
        this.teachingScore = teachingScore;
        this.researchScore = researchScore;
        this.citationScore = citationScore;
        this.industryIncomeScore = industryIncomeScore;
        this.internationalOutlookScore = internationalOutlookScore;
    }

    @Override
    public String toString(){
        return rank + " " + name+ " " + location+ " " + numberOfStudents+ " " + ratioOfStudentToStaff+ " " + internationalStudent+ " " + maleToFemaleRatio + " "+ overAllScore + " "+ teachingScore + " "+ researchScore + " "+ citationScore+ " " + industryIncomeScore + " "+ internationalOutlookScore;
    }

    public Integer getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Integer getNumberOfStudents() {
        return numberOfStudents;
    }

    public Double getRatioOfStudentToStaff() {
        return ratioOfStudentToStaff;
    }

    public String getInternationalStudent() {
        return internationalStudent;
    }

    public String getMaleToFemaleRatio() {
        return maleToFemaleRatio;
    }

    public String getOverAllScore() {
        return overAllScore;
    }

    public String getTeachingScore() {
        return teachingScore;
    }

    public Double getResearchScore() {
        return researchScore;
    }

    public Double getCitationScore() {
        return citationScore;
    }

    public Double getIndustryIncomeScore() {
        return industryIncomeScore;
    }

    public Double getInternationalOutlookScore() {
        return internationalOutlookScore;
    }

    @Override
    public int compareTo(University o) {
        return 0;
    }
    public static Comparator<University> getRankComparator() {
        return new Comparator<University>() {
            @Override
            public int compare(University u1, University u2) {
                if (Objects.equals(u1.getRank(), u2.getRank())) {
                    return 0;
                } else if (u1.getRank() > u2.getRank()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };
    }



}
