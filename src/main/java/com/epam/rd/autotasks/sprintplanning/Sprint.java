package com.epam.rd.autotasks.sprintplanning;

import com.epam.rd.autotasks.sprintplanning.tickets.Bug;
import com.epam.rd.autotasks.sprintplanning.tickets.Ticket;
import com.epam.rd.autotasks.sprintplanning.tickets.UserStory;

public class Sprint {
    protected Ticket[] tickets;
    protected int capacity;
    protected int ticketsLimit;
    protected int ticketsCount;
    protected int estimate;

    public Sprint(int capacity, int ticketsLimit) {
        this.capacity = capacity;
        this.ticketsLimit = ticketsLimit;
        tickets = new Ticket[ticketsLimit];
        this.ticketsCount = 0;
        this.estimate = 0;

    }

    public boolean addUserStory(UserStory userStory) {
        if (userStory == null || userStory.isCompleted() || (userStory.getEstimate() + estimate) > capacity || ticketsCount == ticketsLimit) {
            return false;
        }
        if (userStory.getDependencies() != null) {
            for (UserStory dep: userStory.getDependencies()) {
                boolean found = false;

                for (Ticket tk: this.getTickets()) {
                    if (dep == tk) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
        }
        tickets[ticketsCount] = userStory;
        ticketsCount++;
        estimate += userStory.getEstimate();
        return true;

    }

    public boolean addBug(Bug bugReport) {
        if (bugReport == null || bugReport.isCompleted()  || (bugReport.getEstimate() + estimate) > capacity || ticketsCount == ticketsLimit) {
            return false;
        }
        tickets[ticketsCount] = bugReport;
        ticketsCount++;
        estimate += bugReport.getEstimate();
        return true;
    }

    public Ticket[] getTickets() {
        Ticket[] copy = new Ticket[ticketsCount];
        System.arraycopy(tickets, 0, copy, 0, ticketsCount);
        return copy;
    }

    public int getTotalEstimate() {
        int sum = 0;
        for (int i = 0; i < this.getTickets().length; i++) {
            sum += tickets[i].getEstimate();
        }
        return sum;
    }

    public static void main(String[] args) {
        int nextId = 1;

        Sprint sprint = new Sprint(20, 3);

        UserStory userStory = new UserStory(nextId++, "Registration Form", 16);
        userStory.complete();


        sprint.addBug(Bug.createBug(nextId++, "Add password repeat", 8, userStory));
        sprint.addBug(Bug.createBug(nextId++, "Apply validation", 10, userStory));
        sprint.addBug(Bug.createBug(nextId++, "Add hide/show button for password", 8, userStory));

        Ticket[] tickets = sprint.getTickets();

        System.out.println("Tickets count: " + tickets.length);

//        for (int i = 0; i < tickets.length; i++) {
//            System.out.println("Ticket " + i + ": " + tickets[i].toString());
//        }

        System.out.println(sprint.getTotalEstimate());
    }
}
