package core;

import gui.MainController;
import gui.MainModel;
import gui.MainView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Kamil on 27.12.2016.
 */
public class Main
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            try
            {
                MainController.getControllerInstance().run();
            } catch (SQLException | IllegalAccessException | NoSuchFieldException e)
            {
                e.printStackTrace();
            }
        });
    }
}
