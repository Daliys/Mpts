package ru.mpts.units;

public abstract class TaskHeroType {
    public static final int NONE = 0; // Задача: Ничего не делать, ожидать задачи
    public static final int MINE = 1; // Задача: Добывать
    public static final int MOVES = 2; // Задача: Передвигаться
    public static final int BUILD = 3; // Задача: Строить
    public static final int GET_TASK = 4; // Задача: Просмотр достумных задач
    public static final int WAITE_TASK = 5; // Задача: Ожидание ответа доступных задач
}