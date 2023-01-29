package model;


import java.util.Random;

public class ModelCarteRobot extends ModelCarte{
    public ModelCarteRobot(){
        super();
        //setCachee(true);
    }

    public ModelNavire choisirAleatoirementUnNavire() {
        Object[] modelNaviresAsArray = getModelNaviresAsArray();
        return (ModelNavire) modelNaviresAsArray[new Random().nextInt(modelNaviresAsArray.length)];
    }
}
