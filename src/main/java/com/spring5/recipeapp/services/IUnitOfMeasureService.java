package com.spring5.recipeapp.services;

import com.spring5.recipeapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface IUnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUnitOfMeasures();
}
