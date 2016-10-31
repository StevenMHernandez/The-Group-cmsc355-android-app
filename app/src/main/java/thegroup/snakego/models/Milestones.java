package thegroup.snakego.models;

public class Milestones {

    private CharSequence[] milestones;

    public Milestones() {
        milestones = new CharSequence[5];
        milestones[0] = "Just a walk in the park..";
        milestones[1] = "You're a BEAST!!";
        milestones[2] = "This eating thing is getting tiring";
        milestones[3] = "You're still playing this gaem?";
        milestones[4] = "No one's actually gotten this far before";
    }

    public CharSequence[] getMilestones() {
        return milestones;
    }

}
