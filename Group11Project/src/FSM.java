/**
 * Finite-State Machine
 * An instance of this should be created to manage state switches for a program.
 * States should be classes which implement the FSMState interface.
 * Note: Is not stack-based; may implement stack if needed
 */
public class FSM {
    private FSMState activeState = null;

    public FSM(){
    }

    public void setState(FSMState state){
        activeState = state;
    }

    public void run(){
        if(activeState != null)
            activeState.execute();
    }
}
