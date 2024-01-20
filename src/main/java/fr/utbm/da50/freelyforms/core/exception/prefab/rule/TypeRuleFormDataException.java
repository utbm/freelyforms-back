package fr.utbm.da50.freelyforms.core.exception.prefab.rule;

import lombok.NonNull;

public class TypeRuleFormDataException extends Exception{

    public TypeRuleFormDataException(@NonNull String message){
        super(message);
    }
}
