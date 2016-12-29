package core;

import gui.MainController;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Created by Kamil on 27.12.2016.
 */
public class Main
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> MainController.getControllerInstance().run());
    }
}
