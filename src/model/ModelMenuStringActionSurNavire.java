package model;

import enums.ActionsNavirePossibleSurUneCarteEnum;

import java.util.Arrays;

public class ModelMenuStringActionSurNavire extends ModelMenuString {

    public ModelMenuStringActionSurNavire(ActionsNavirePossibleSurUneCarteEnum...actionsNavirePossibleSurUneCarteEnums) {
        super(Arrays.stream(actionsNavirePossibleSurUneCarteEnums).map(Object::toString).toArray(String[]::new));
    }
}
