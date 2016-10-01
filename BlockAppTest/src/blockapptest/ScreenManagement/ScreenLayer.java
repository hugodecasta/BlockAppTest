/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.ScreenManagement;

import java.util.ArrayList;

/**
 *
 * @author i3mainz
 */
public class ScreenLayer
{
    ArrayList<ScreenComponent>components,componentsToAdd,componentToRemove;
    String name;
    
    public ScreenLayer(String name)
    {
        components = new ArrayList<>();
        componentsToAdd = new ArrayList<>();
        componentToRemove = new ArrayList<>();
        this.name = name;
    }
    
    public void addComponent(ScreenComponent c)
    {
        componentsToAdd.add(c);
    }
    
    public void removeComponent(ScreenComponent c)
    {
        componentToRemove.add(c);
    }
    
    public void update()
    {
        components.addAll(componentsToAdd);
        components.removeAll(componentToRemove);
        componentToRemove.clear();
        componentsToAdd.clear();
    }
    
    public ArrayList<ScreenComponent>getComponents()
    {
        return components;
    }
}
