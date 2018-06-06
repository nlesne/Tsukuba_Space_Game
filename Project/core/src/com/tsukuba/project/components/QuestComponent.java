package com.tsukuba.project.components;

import com.badlogic.ashley.core.Component;

public class QuestComponent implements Component {
    public enum QuestType {SURVIVE,KILL}

    public int reward = 0;
    public int objective = 0;
    public int progress = 0;
    public QuestType type = QuestType.KILL;
}
