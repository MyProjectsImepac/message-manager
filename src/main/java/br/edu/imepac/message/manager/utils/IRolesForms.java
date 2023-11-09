package br.edu.imepac.message.manager.utils;

public interface IRolesForms<T> {

    T createEntityViewRepresentation();

    boolean isFieldsFormsValidate();
}
