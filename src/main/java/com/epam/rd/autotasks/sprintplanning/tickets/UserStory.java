package com.epam.rd.autotasks.sprintplanning.tickets;

import java.util.Arrays;

public class UserStory extends Ticket {
    protected UserStory[] dependencies;

    public UserStory(int id, String name, int estimate, UserStory... dependsOn) {
        super(id, name, estimate);
        if (dependsOn == null || dependsOn.length == 0) {
            dependencies = new UserStory[0];
        }
        else {
            dependencies = Arrays.copyOf(dependsOn, dependsOn.length);
        }
    }

    @Override
    public void complete() {
        for (int i = 0; i < dependencies.length; i++) {
          if (!dependencies[i].isCompleted()) {
              isComplete = false;
              return;
          }
        }
        super.complete();
    }

    public UserStory[] getDependencies() {
        UserStory[] copyOfDependencies = new UserStory[dependencies.length];
        System.arraycopy(dependencies, 0, copyOfDependencies, 0, dependencies.length);
        return copyOfDependencies;
    }

    @Override
    public String toString() {
        return String.format("[US %d] %s", getId(), getName());
    }

}
