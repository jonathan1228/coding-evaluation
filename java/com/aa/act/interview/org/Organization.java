package com.aa.act.interview.org;

import java.util.Collection;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
public abstract class Organization {

    private Position root;

    public Organization() {
        root = createOrganization();
    }

    protected abstract Position createOrganization();

    /**
     * hire the given person as an employee in the position that has that title
     *
     * @param person
     * @param title
     * @return the newly filled position or empty if no position has that title
     */
    public Optional<Position> hire(Name person, String title) {
        //your code here
        Random random = new Random();
        int randomIdentifer = random.nextInt(100) + 1;

        // thought process notes for myself
        //root
        //getDirectReports has positions and reports under it
        // traverse through each node and see if the title matches and then set the employee

        //initial setting of CEO if title is CEO
        if(root.getTitle().equalsIgnoreCase(title) && root.getEmployee().isEmpty()){
            Employee employee = new Employee(randomIdentifer, person);
            root.setEmployee(Optional.of(employee));
            return Optional.of(new Position(title, employee));
        }
        Optional<Position> optionalPosition = Optional.empty();
        //loop through each direct reports
        for(Position pos : root.getDirectReports()){
            //check to see if position exists or if they has direct reports
            optionalPosition = hire(pos, person, title);
        }
        return optionalPosition;
    }

    public Optional<Position> hire(Position position, Name person, String title) {
        Optional<Position> optionalPosition = Optional.empty();
        Random random = new Random();
        int randomIdentifer = random.nextInt(100) + 1;
        //create position with name and title if title exists and employee is empty
        if (position.getTitle().equalsIgnoreCase(title) && position.getEmployee().isEmpty()) {
            Employee employee = new Employee(randomIdentifer, person);
            position.setEmployee(Optional.of(employee));
            optionalPosition = Optional.of(new Position(title, employee));
        }
        // check to see if this title has any direct reports and update their employee information
        // by doing recursion I can update the position or check for their direct reports
        if (position.getDirectReports().size() > 0) {
            for (Position pos : position.getDirectReports()) {
                optionalPosition = hire(pos, person, title);
            }
        }
        //if position's title doesn't exist then it'll return an empty optional regardless in the end
        return optionalPosition;
    }

    @Override
    public String toString() {
        return printOrganization(root, "");
    }

    private String printOrganization(Position pos, String prefix) {
        StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
        for(Position p : pos.getDirectReports()) {
            sb.append(printOrganization(p, prefix + "  "));
        }
        return sb.toString();
    }
}
