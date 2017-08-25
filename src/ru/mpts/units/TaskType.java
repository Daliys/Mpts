package ru.mpts.units;

public abstract class TaskType {
    public static final int NONE = 0; // Задача: Ничего не делать, ожидать задачи
    public static final int MINE = 1; // Задача: Добывать
    public static final int MOVE = 2; // Задача: Передвигаться
    public static final int BUILD = 3; // Задача: Строить
    public static final int GET_TASK = 4; // Задача: Просмотр достумных задач
    public static final int WAIT_TASK = 5; // Задача: Ожидание ответа доступных задач
    public static final int WAIT_FIND_WAY = 6; // Задача: Ожидает ответа, происходит поиск пути
    public static final int FIND_WAY = 7; // Задача: Начать поиск пути, происходит поиск пути
    public static final int CHECK_WAY_MOVE = 8; // Задача: Начать поиск пути, происходит поиск пути
}