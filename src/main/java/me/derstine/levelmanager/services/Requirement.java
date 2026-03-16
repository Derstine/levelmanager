package me.derstine.levelmanager.services;

/*

can either be a lifetime or level requirement

*/

public class Requirement {
    public enum Scope { LIFETIME, LEVEL }

    private Scope scope;
    private String statistic;
    private String target;
    private int goal;
    private int baseValue;
        
    Requirement(Scope scope, String statistic, String target, int goal, int baseValue) {
        // ignore basevalue if it's a lifetime achievement

        this.scope = scope;
        this.statistic = statistic;
        this.target = target;
        this.goal = goal;
        this.baseValue = baseValue;
    }

    public boolean isComplete() {
        if(scope == Scope.LIFETIME) {
            // if get player statistic
        }
        else if(scope == Scope.LEVEL) {

        }

        return false;
    }
}