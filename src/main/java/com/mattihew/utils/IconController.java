package com.mattihew.utils;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IconController
{
    final static Map<String, Image> icons = new HashMap<>();

    static
    {
        IconController.icons.put("clear", new Image("/images/Stopwatch.png"));
        IconController.icons.put("white", new Image("/images/Stopwatch-white.png"));
        IconController.icons.put("green", new Image("/images/Stopwatch-green.png"));
        IconController.icons.put("red", new Image("/images/Stopwatch-red.png"));
        IconController.icons.put("blue", new Image("/images/Stopwatch-blue.png"));
        IconController.icons.put("yellow", new Image("/images/Stopwatch-yellow.png"));
    }

    public static boolean setIcon(final Stage stage, final String name)
    {
        stage.getIcons().clear();
        return stage.getIcons().add(IconController.icons.get(name));
    }

    public static Set<String> getNameList()
    {
        return IconController.icons.keySet();
    }
}
