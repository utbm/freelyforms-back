package fr.utbm.da50.freelyforms.core.exception.prefab.rule;

import lombok.NonNull;

public class RuleException extends Exception{

    public RuleException(@NonNull String message){
        super(message);
    }
}
