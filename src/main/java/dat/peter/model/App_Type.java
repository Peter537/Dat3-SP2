package dat.peter.model;

import lombok.Getter;

@Getter
public enum App_Type {

    GAME("Game"),
    APP("Application"),
    TOOL("Tool"),
    UTILITY("Utility"),
    OTHER("Other");

    private String type;

    App_Type(String type) {
        this.type = type;
    }

}
