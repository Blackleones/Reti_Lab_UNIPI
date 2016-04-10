/**
 * Created by Developer on 15/03/2016.
 */
public class Dish {
    private static String DIRTY = "dirty";
    private static String WASHED = "washed";
    private static String CLEAN = "clean";
    private String state;

    Dish() {
        state = CLEAN;
    }

    public String getState() {
        return state;
    }

    public void changeState() {
        if(state == CLEAN)
            state = DIRTY;
        else if(state == DIRTY)
                state = WASHED;
        else if(state == WASHED)
                state = CLEAN;
    }

    public boolean isDirty() {
        return state == DIRTY;
    }

    @Override
    public String toString() {
        return " status: " + state;
    }
}
