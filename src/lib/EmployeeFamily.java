package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class EmployeeFamily {

    private String name;
    private String idNumber;

    private List<String> childNames;
    private List<String> childIdNumbers;


    public String getName(){
        return name;
    }

    public String getIdNumber(){
        return idNumber;
    }

    public List<String> getChildNames(){
        return childNames;
    }

    public List<String> getChildIdNumbers(){
        return childIdNumbers;
    }
}

