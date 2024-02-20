package br.com.fullcycle.hexagonal.application.usecases;

public abstract class UnitUseCasa<INPUT> {
    
    public abstract void execute(INPUT input);
}
