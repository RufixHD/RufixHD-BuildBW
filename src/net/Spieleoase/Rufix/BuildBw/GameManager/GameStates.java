package net.Spieleoase.Rufix.BuildBw.GameManager;

public class GameStates {

    public static State GameState = State.LOBBY;

    public enum State {
        LOBBY,SCHUTZZEIT,INGAME,END;
    }

    public static State getGameState() {
        return GameState;
    }

    public static void setGameState(State state) {
        GameState = state;
    }
}
